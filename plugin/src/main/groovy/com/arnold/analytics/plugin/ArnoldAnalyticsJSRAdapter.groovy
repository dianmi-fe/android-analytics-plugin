package com.arnold.analytics.plugin

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.JSRInlinerAdapter

class ArnoldAnalyticsJSRAdapter extends JSRInlinerAdapter {
    protected ArnoldAnalyticsJSRAdapter(int api, MethodVisitor mv, int access, String name, String desc, String signature, String[] exceptions) {
        super(api, mv, access, name, desc, signature, exceptions)
    }
}