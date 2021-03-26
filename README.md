
执行/plugin/maven.gradle 中的task:
```groovy
uploadArchives
```
# 1. 集成 SDK
## 1.1. 引入插件
   在 project 级别的 build.gradle 文件中添加 plugin 依赖：

```groovy
buildscript {

   repositories {
   ...
       maven {
        url uri('../analytics_plugin/repo')
       }

   }
       dependencies {
   ...
        classpath 'com.arnold.analytics.android:plugin:0.0.1'
       }
   }
```

## 1.2. 引入 SDK
在主 module 的 build.gradle 文件中应用 com.arnold.analytics.android 插件、添加 SDK 依赖：
```groovy
apply plugin: 'com.android.application'
// 应用 com.arnold.analytics.android 插件
apply plugin: 'com.arnold.analytics.android'

dependencies {
    // 添加 Analytics SDK 依赖
    implementation 'com.arnoldx.ehr:analytics:0.2.0'
}
```

1.3 配置参数
在主 module 的 build.gradle 文件中配置可选参数：
```groovy
android {
    arnoldAnalytics {
        debug = true
    }
}
```

