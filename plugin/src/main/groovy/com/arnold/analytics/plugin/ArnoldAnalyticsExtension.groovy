package com.arnold.analytics.plugin

import org.gradle.internal.reflect.Instantiator

class ArnoldAnalyticsExtension{
    public boolean debug = false
    public boolean disableJar = false
    public boolean lambdaEnabled = true
    public boolean useInclude = false
    public ArrayList<String> exclude = []
    public ArrayList<String> include = []

    ArnoldAnalyticsExtension(Instantiator ins) {

    }
}