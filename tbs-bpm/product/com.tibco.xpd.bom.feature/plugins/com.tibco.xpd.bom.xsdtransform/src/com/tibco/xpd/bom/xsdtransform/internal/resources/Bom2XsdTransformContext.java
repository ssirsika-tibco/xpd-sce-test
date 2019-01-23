/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.internal.resources;

import com.tibco.xpd.bom.xsdtransform.exports.template.ExportTransformationData;

/**
 * 
 * 
 * @author bharge
 * @since 10 Sep 2012
 */
public class Bom2XsdTransformContext {

    private ExportTransformationData data;

    /**
     * @param data
     */
    public Bom2XsdTransformContext(ExportTransformationData data) {
        this.data = data;
    }

    public void inspectObject(Object obj) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        //            System.out.println("==>ctx:inspectObject()"); //$NON-NLS-1$
        //            System.out.println("<==ctx:inspectObject()"); //$NON-NLS-1$
        // }
    }

    public void inspectObject(String msg, Object obj) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        //            System.out.println("==>ctx:inspectObject(): " + msg); //$NON-NLS-1$
        //            System.out.println("<==ctx:inspectObject(): " + msg); //$NON-NLS-1$
        // }
    }

    public void inspectObject(String msg, Object obj, Object obj2) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        //            System.out.println("==>ctx:inspectObject(): " + msg); //$NON-NLS-1$
        //            System.out.println("<==ctx:inspectObject(): " + msg); //$NON-NLS-1$
        // }
    }

    public void inspectObject(String msg, Object obj, Object obj2, Object obj3) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        //            System.out.println("==>ctx:inspectObject(): " + msg); //$NON-NLS-1$
        //            System.out.println("<==ctx:inspectObject(): " + msg); //$NON-NLS-1$
        // }
    }

}
