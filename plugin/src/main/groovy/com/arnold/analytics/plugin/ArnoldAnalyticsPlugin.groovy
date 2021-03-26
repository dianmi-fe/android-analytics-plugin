package com.arnold.analytics.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.reflect.Instantiator
import org.gradle.invocation.DefaultGradle


class ArnoldAnalyticsPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        Instantiator ins = ((DefaultGradle) project.getGradle()).getServices().get(Instantiator)
        def args = [ins] as Object[]
        ArnoldAnalyticsExtension extension = project.extensions.create("arnoldAnalytics", ArnoldAnalyticsExtension, args)

        boolean disableArnoldAnalyticsPlugin = false
        boolean isHookOnMethodEnter = false

        Properties properties = new Properties()
        if (project.rootProject.file('gradle.properties').exists()) {
            properties.load(project.rootProject.file('gradle.properties').newDataInputStream())
            disableArnoldAnalyticsPlugin = Boolean.parseBoolean(properties.getProperty("arnoldAnalytics.disablePlugin", "false")) ||
                    Boolean.parseBoolean(properties.getProperty("disableArnoldAnalyticsPlugin", "false"))

            isHookOnMethodEnter = Boolean.parseBoolean(properties.getProperty("arnoldAnalytics.isHookOnMethodEnter", "false"))
        }

        if (!disableArnoldAnalyticsPlugin) {
            AppExtension appExtension = project.extensions.findByType(AppExtension.class)
            ArnoldAnalyticsTransformHelper transformHelper = new ArnoldAnalyticsTransformHelper(extension, appExtension)
            transformHelper.isHookOnMethodEnter = isHookOnMethodEnter
            appExtension.registerTransform(new ArnoldAnalyticsTransform(transformHelper))
        } else {
            Logger.error("------------您已关闭了易博埋点插件--------------")
        }

    }
}