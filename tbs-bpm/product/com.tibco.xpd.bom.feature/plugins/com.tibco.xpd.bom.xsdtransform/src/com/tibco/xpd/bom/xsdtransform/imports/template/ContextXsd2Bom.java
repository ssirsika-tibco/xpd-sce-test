/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.imports.template;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreEMap;

/**
 * 
 * 
 * @author rgreen
 * @since 25 Apr 2012
 */
public class ContextXsd2Bom {

    private ImportTransformationData data;

    /**
     * 
     */
    public ContextXsd2Bom(ImportTransformationData data) {
        this.data = data;
    }

    public void showDetails() {
        System.out
                .println("** This method displays details of the current state of the ImportTransformationData **");

    }

    public void showPreTransformContext() {
        System.out.println("***************************");
        System.out.println("** Pre-Transform Context **");
        System.out.println("***************************");
        System.out.println("Source File: " + data.getSourceFile().getName());
        System.out.println("Destination folder: "
                + data.getDestinationFolder().getName());
        System.out.println("Diagram Resource: " + "");
        if (!data.getAttrUndeclaredForms().isEmpty()) {
            for (String formName : data.getAttrUndeclaredForms()) {
                System.out.println("Attribute Undeclared Form: " + formName);
            }
        }
        if (!data.getElemUndeclaredForms().isEmpty()) {
            for (String formName : data.getAttrUndeclaredForms()) {
                System.out.println("Element Undeclared Form: " + formName);
            }
        }

        if (!data.getInnerWSDLSchemaPrefixMap().isEmpty()) {
            Map<String, String> prefixMap = data.getInnerWSDLSchemaPrefixMap();

            System.out.println("innerWSDLSchemaPrefixMap");
            System.out.println("========================");
            Set<String> keySet = prefixMap.keySet();
            for (String key : keySet) {
                String value = prefixMap.get(key);
                System.out.println(key + "=" + value);
            }
            System.out.println("========================");
        }
        System.out.println("OAW ResourceSet: " + "");
        if (data.getPackagedElements().isEmpty()) {
            System.out.println("Base Primitive Type list NOT LOADED");
        } else {
            System.out.println("Base Primitive Type list LOADED. Size="
                    + data.getPackagedElements().size());
        }

        System.out.println("***************************");
    }

    public void showParentPrefixMap() {

        Object parentPrefixMap = data.getParentPrefixMap();

        if (parentPrefixMap instanceof EcoreEMap) {

        }

    }

    /**
     * 
     */
    public void showImportSchemaLocationStack() {
        List<URI> importSchemaLocationStack =
                data.getImportSchemaLocationStack();

        System.out
                .println("Current state of importSchemaLocationStack (bottom to top)");
        System.out
                .println("--------------------------------------------------------");
        for (URI uri : importSchemaLocationStack) {
            System.out.println(uri.toString());
        }
        System.out
                .println("--------------------------------------------------------");
    }

    /**
     * 
     */
    public void showSchemaTypes() {

        List<Object> schemaTypes = data.getSchemaTypes();

        System.out.println("Current state of schemaTypes list");
        System.out
                .println("--------------------------------------------------------");
        // for (URI uri : importSchemaLocationStack) {
        // System.out.println(uri.toString());
        // }
        System.out
                .println("--------------------------------------------------------");
    }

    /**
     * 
     */
    public void showStatus(String method) {

        System.out.println("==> data.ctx.showStatus: called from " + method);
        System.out.println("<== data.ctx.showStatus: called from " + method);
    }

    /**
     * 
     */
    public void showStatus() {

        showStatus("");
    }

    /**
     * 
     */
    public void showStatus(Object... objects) {

        showStatus("");
    }

    /**
     * 
     */
    public void showStatus(Object obj1) {

        showStatus("");

    }

    /**
     * 
     */
    public void showStatus(Object obj1, Object obj2) {

        showStatus("");
    }

    public void inspectSimpleType(Object obj1) {

        // XsdConstructWrapperSimpleType createSimpleType =
        // XsdConstructWrapperFactory.getInstance().createSimpleType(obj1);

        showStatus("inspectSimpleType");

    }

}
