package com.arnold.analytics.plugin

import com.android.annotations.NonNull
import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Status
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.ide.common.internal.WaitableExecutor
import groovy.io.FileType
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.concurrent.Callable
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

class ArnoldAnalyticsTransform extends Transform {
    private ArnoldAnalyticsTransformHelper transformHelper
    private WaitableExecutor waitableExecutor
    private URLClassLoader urlClassLoader

    ArnoldAnalyticsTransform(ArnoldAnalyticsTransformHelper transformHelper) {
        this.transformHelper = transformHelper
        waitableExecutor = WaitableExecutor.useGlobalSharedThreadPool()
    }

    @Override
    String getName() {
        return "ArnoldAnalyticsAutoTrack"
    }

    /**
     * 需要处理的数据类型，有两种枚举类型
     *
     * CLASSES
     * 代表处理的 java 的 class 文件，返回TransformManager.CONTENT_CLASS
     *
     * RESOURCES
     * 代表要处理 java 的资源，返回TransformManager.CONTENT_RESOURCES
     *
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /***
     * 指 Transform 要操作内容的范围，官方文档 Scope 有 7 种类型：
     *
     * EXTERNAL_LIBRARIES ： 只有外部库
     * PROJECT ： 只有项目内容
     * PROJECT_LOCAL_DEPS ： 只有项目的本地依赖(本地jar)
     * PROVIDED_ONLY ： 只提供本地或远程依赖项
     * SUB_PROJECTS ： 只有子项目
     * SUB_PROJECTS_LOCAL_DEPS： 只有子项目的本地依赖项(本地jar)
     * TESTED_CODE ：由当前变量(包括依赖项)测试的代码
     *
     * 如果要处理所有的class字节码，返回TransformManager.SCOPE_FULL_PROJECT
     *
     * @return
     */
    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 增量编译开关
     *
     * 当我们开启增量编译的时候，相当input包含了changed/removed/added三种状态，实际上还有notchanged。需要做的操作如下：
     *
     * NOTCHANGED: 当前文件不需处理，甚至复制操作都不用；
     * ADDED、CHANGED: 正常处理，输出给下一个任务；
     * REMOVED: 移除outputProvider获取路径对应的文件。
     *
     * @return
     */
    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(@NonNull TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        beforeTransform(transformInvocation)
        transformClass(transformInvocation.context, transformInvocation.inputs, transformInvocation.outputProvider, transformInvocation.incremental)
        afterTransform()
    }

    private void beforeTransform(TransformInvocation transformInvocation) {
        //打印提示信息
        Logger.printCopyright()
        Logger.setDebug(transformHelper.extension.debug)
        transformHelper.onTransform()
        println("[ArnoldAnalytics]: =======================Arnold=========================")
        println("[ArnoldAnalytics]: 是否在方法进入时插入代码:${transformHelper.isHookOnMethodEnter}")
        println("[ArnoldAnalytics]: 此次是否增量编译:$transformInvocation.incremental")
        println("[ArnoldAnalytics]: ======================================================")

        traverseForClassLoader(transformInvocation)
    }

    private void transformClass(Context context, Collection<TransformInput> inputs, TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {

        long startTime = System.currentTimeMillis()
        if (!isIncremental) {
            outputProvider.deleteAll()
        }


        //遍历输入文件
        inputs.each { TransformInput input ->
            //遍历 jar  是指以jar包方式参与项目编译的所有本地jar包和远程jar包（此处的jar包包括aar）
            input.jarInputs.each { JarInput jarInput ->
                if (waitableExecutor) {
                    waitableExecutor.execute(new Callable<Object>() {
                        @Override
                        Object call() throws Exception {
                            forEachJar(isIncremental, jarInput, outputProvider, context)
                            return null
                        }
                    })
                } else {
                    forEachJar(isIncremental, jarInput, outputProvider, context)
                }
            }

            //遍历目录  是指以源码的方式参与项目编译的所有目录结构及其目录下的源码文件
            input.directoryInputs.each { DirectoryInput directoryInput ->
                if (waitableExecutor) {
                    waitableExecutor.execute(new Callable<Object>() {
                        @Override
                        Object call() throws Exception {
                            forEachDirectory(isIncremental, directoryInput, outputProvider, context)
                            return null
                        }
                    })
                } else {
                    forEachDirectory(isIncremental, directoryInput, outputProvider, context)
                }
            }
        }

        if (waitableExecutor) {
            waitableExecutor.waitForTasksWithQuickFail(true)
        }
        Logger.info("[ArnoldAnalytics]: 此次编译共耗时:${System.currentTimeMillis() - startTime}毫秒")
    }




    private void afterTransform() {
        try {
            if (urlClassLoader != null) {
                urlClassLoader.close()
                urlClassLoader = null
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }


    private void traverseForClassLoader(TransformInvocation transformInvocation) {
        def urlList = []
        def androidJar = transformHelper.androidJar()
        urlList << androidJar.toURI().toURL()
        transformInvocation.inputs.each { transformInput ->
            transformInput.jarInputs.each { jarInput ->
                urlList << jarInput.getFile().toURI().toURL()
            }

            transformInput.directoryInputs.each { directoryInput ->
                urlList << directoryInput.getFile().toURI().toURL()
            }
        }
        def urlArray = urlList as URL[]
        urlClassLoader = new URLClassLoader(urlArray)
        transformHelper.urlClassLoader = urlClassLoader
    }


    void forEachJar(boolean isIncremental, JarInput jarInput, TransformOutputProvider outputProvider, Context context) {
        String destName = jarInput.file.name
        //截取文件路径的 md5 值重命名输出文件，因为可能同名，会覆盖
        def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath).substring(0, 8)
        if (destName.endsWith(".jar")) {
            destName = destName.substring(0, destName.length() - 4)
        }
        //获得输出文件
        File destFile = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        if (isIncremental) {
            Status status = jarInput.getStatus()
            switch (status) {
                case Status.NOTCHANGED:
                    break
                case Status.ADDED:
                case Status.CHANGED:
                    Logger.info("jar status = $status:$destFile.absolutePath")
                    transformJar(destFile, jarInput, context)
                    break
                case Status.REMOVED:
                    Logger.info("jar status = $status:$destFile.absolutePath")
                    if (destFile.exists()) {
                        FileUtils.forceDelete(destFile)
                    }
                    break
                default:
                    break
            }
        } else {
            transformJar(destFile, jarInput, context)
        }
    }

    void transformJar(File dest, JarInput jarInput, Context context) {
        def modifiedJar = null
        if (!transformHelper.extension.disableJar || jarInput.file.absolutePath.contains('SensorsAnalyticsSDK')) {
            Logger.info("开始遍历 jar：" + jarInput.file.absolutePath)
            modifiedJar = modifyJarFile(jarInput.file, context.getTemporaryDir())
            Logger.info("结束遍历 jar：" + jarInput.file.absolutePath)
        }
        if (modifiedJar == null) {
            modifiedJar = jarInput.file
        }
        FileUtils.copyFile(modifiedJar, dest)
    }


    /**
     * 修改 jar 文件中对应字节码
     */
    private File modifyJarFile(File jarFile, File tempDir) {
        if (jarFile) {
            return modifyJar(jarFile, tempDir, true)

        }
        return null
    }


    private File modifyJar(File jarFile, File tempDir, boolean isNameHex) {
        //FIX: ZipException: zip file is empty
        if (jarFile == null || jarFile.length() == 0) {
            return null
        }
        //取原 jar, verify 参数传 false, 代表对 jar 包不进行签名校验
        def file = new JarFile(jarFile, false)
        //设置输出到的 jar
        def tmpNameHex = ""
        if (isNameHex) {
            tmpNameHex = DigestUtils.md5Hex(jarFile.absolutePath).substring(0, 8)
        }
        def outputJar = new File(tempDir, tmpNameHex + jarFile.name)
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(outputJar))
        Enumeration enumeration = file.entries()

        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumeration.nextElement()
            InputStream inputStream
            try {
                inputStream = file.getInputStream(jarEntry)
            } catch (Exception e) {
                if (inputStream!=null){
                    IOUtils.closeQuietly(inputStream)
                }
                e.printStackTrace()
                return null
            }
            String entryName = jarEntry.getName()
            if (entryName.endsWith(".DSA") || entryName.endsWith(".SF")) {
                //ignore
            } else {
                String className
                JarEntry entry = new JarEntry(entryName)
                byte[] modifiedClassBytes = null
                byte[] sourceClassBytes
                try {
                    jarOutputStream.putNextEntry(entry)
                    sourceClassBytes = ArnoldAnalyticsUtil.toByteArrayAndAutoCloseStream(inputStream)
                } catch (Exception e) {
                    Logger.error("Exception encountered while processing jar: " + jarFile.getAbsolutePath())
                    IOUtils.closeQuietly(file)
                    IOUtils.closeQuietly(jarOutputStream)
                    e.printStackTrace()
                    return null
                }
                if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
                    className = entryName.replace("/", ".").replace(".class", "")
                    ClassNameAnalytics classNameAnalytics = transformHelper.analytics(className)
                    if (classNameAnalytics.isShouldModify) {
                        modifiedClassBytes = modifyClass(sourceClassBytes, classNameAnalytics)
                    }
                }
                if (modifiedClassBytes == null) {
                    jarOutputStream.write(sourceClassBytes)
                } else {
                    jarOutputStream.write(modifiedClassBytes)
                }
                jarOutputStream.closeEntry()
            }
        }
        jarOutputStream.close()
        file.close()
        return outputJar
    }

    void forEachDirectory(boolean isIncremental, DirectoryInput directoryInput, TransformOutputProvider outputProvider, Context context) {
        File dir = directoryInput.file
        File dest = outputProvider.getContentLocation(directoryInput.getName(),
                directoryInput.getContentTypes(), directoryInput.getScopes(),
                Format.DIRECTORY)
        FileUtils.forceMkdir(dest)
        String srcDirPath = dir.absolutePath
        String destDirPath = dest.absolutePath
        if (isIncremental) {
            Map<File, Status> fileStatusMap = directoryInput.getChangedFiles()
            for (Map.Entry<File, Status> changedFile : fileStatusMap.entrySet()) {
                Status status = changedFile.getValue()
                File inputFile = changedFile.getKey()
                String destFilePath = inputFile.absolutePath.replace(srcDirPath, destDirPath)
                File destFile = new File(destFilePath)
                switch (status) {
                    case Status.NOTCHANGED:
                        break
                    case Status.REMOVED:
                        Logger.info("目录 status = $status:$inputFile.absolutePath")
                        if (destFile.exists()) {
                            //noinspection ResultOfMethodCallIgnored
                            destFile.delete()
                        }
                        break
                    case Status.ADDED:
                    case Status.CHANGED:
                        Logger.info("目录 status = $status:$inputFile.absolutePath")
                        File modified = modifyClassFile(dir, inputFile, context.getTemporaryDir())
                        if (destFile.exists()) {
                            destFile.delete()
                        }
                        if (modified != null) {
                            FileUtils.copyFile(modified, destFile)
                            modified.delete()
                        } else {
                            FileUtils.copyFile(inputFile, destFile)
                        }
                        break
                    default:
                        break
                }
            }
        } else {
            FileUtils.copyDirectory(dir, dest)
            dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                File inputFile ->
                    forEachDir(dir, inputFile, context, srcDirPath, destDirPath)
            }
        }
    }

    void forEachDir(File dir, File inputFile, Context context, String srcDirPath, String destDirPath) {
        File modified = modifyClassFile(dir, inputFile, context.getTemporaryDir())
        if (modified != null) {
            File target = new File(inputFile.absolutePath.replace(srcDirPath, destDirPath))
            if (target.exists()) {
                target.delete()
            }
            FileUtils.copyFile(modified, target)
            modified.delete()
        }
    }


    /**
     * 目录文件中修改对应字节码
     */
    private File modifyClassFile(File dir, File classFile, File tempDir) {
        File modified = null
        FileOutputStream outputStream = null
        try {
            String className = path2ClassName(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))
            ClassNameAnalytics classNameAnalytics = transformHelper.analytics(className)
            if (classNameAnalytics.isShouldModify) {
                byte[] sourceClassBytes = ArnoldAnalyticsUtil.toByteArrayAndAutoCloseStream(new FileInputStream(classFile))
                byte[] modifiedClassBytes = modifyClass(sourceClassBytes, classNameAnalytics)
                if (modifiedClassBytes) {
                    modified = new File(tempDir, className.replace('.', '') + '.class')
                    if (modified.exists()) {
                        modified.delete()
                    }
                    modified.createNewFile()
                    outputStream = new FileOutputStream(modified)
                    outputStream.write(modifiedClassBytes)
                }
            } else {
                return classFile
            }
        } catch (Exception e) {
            e.printStackTrace()
        } finally {
            IOUtils.closeQuietly(outputStream)
        }
        return modified
    }


    /**
     * 真正修改类中方法字节码
     */
    private byte[] modifyClass(byte[] srcClass, ClassNameAnalytics classNameAnalytics) {
        try {
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
            ClassVisitor classVisitor = new ArnoldAnalyticsClassVisitor(classWriter, classNameAnalytics, transformHelper)
            ClassReader cr = new ClassReader(srcClass)
            cr.accept(classVisitor, ClassReader.EXPAND_FRAMES + ClassReader.SKIP_FRAMES)
            return classWriter.toByteArray()
        } catch (Exception ex) {
            Logger.error("$classNameAnalytics.className 类执行 modifyClass 方法出现异常")
            ex.printStackTrace()
            if (transformHelper.extension.debug) {
                throw new Error()
            }
            return srcClass
        }
    }

    private static String path2ClassName(String pathName) {
        pathName.replace(File.separator, ".").replace(".class", "")
    }

}