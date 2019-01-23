/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.internal.resources;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

import com.tibco.xpd.bom.xsdtransform.imports.template.ImportTransformationData;

/**
 * THIS SHOULD BE AN INTERFACE REALLY, THAT IS IMPLEMENTED BY EACH M2M
 * TRANSFORMER
 * 
 * @author rgreen
 * @since 24 Jul 2012
 */
public class BomTransformContext {

    private ImportTransformationData data;

    // Source parameters
    // The starting point
    private String baseWsdlFolderLocation;

    private String baseWsdlFullPathLocation;

    private String sourceFileAbsolutePath;

    /*
     * XPD-5023: Map to store parsed main and imported wsdl definitions
     */
    private Set<DynamicEObjectImpl> wsdlDefinitions =
            new HashSet<DynamicEObjectImpl>();

    // Destination parameters
    // data.setDestinationFolder(getParentFolder(bomFilePath));

    /**
     * 
     */
    public BomTransformContext(ImportTransformationData data) {
        this.data = data;
    }

    /**
     * @return the data
     */
    public ImportTransformationData getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(ImportTransformationData data) {
        this.data = data;
    }

    /**
     * @return the baseWsdlFolderLocation
     */
    public String getBaseWsdlFolderLocation() {
        return baseWsdlFolderLocation;
    }

    /**
     * @param baseWsdlFolderLocation
     *            the baseWsdlFolderLocation to set
     */
    public void setBaseWsdlFolderLocation(String baseWsdlFolderLocation) {
        this.baseWsdlFolderLocation = baseWsdlFolderLocation;
    }

    /**
     * @return the baseWsdlFullPathLocation
     */
    public String getBaseWsdlFullPathLocation() {
        return baseWsdlFullPathLocation;
    }

    /**
     * @param baseWsdlFullPathLocation
     *            the baseWsdlFullPathLocation to set
     */
    public void setBaseWsdlFullPathLocation(String baseWsdlFullPathLocation) {
        this.baseWsdlFullPathLocation = baseWsdlFullPathLocation;
    }

    /**
     * @return the wsdlImports
     */
    public Collection<DynamicEObjectImpl> getWsdlDefinitions() {
        return wsdlDefinitions;
    }

    /**
     * @param wsdlImports
     *            the wsdlImports to set
     */
    public void setWsdlDefinitions(Set<DynamicEObjectImpl> wsdlDefinitions) {
        this.wsdlDefinitions = wsdlDefinitions;
    }

    /**
     * @return the sourceFileURI
     */
    public String getSourceFileAbsolutePath() {
        return sourceFileAbsolutePath;
    }

    /**
     * @param sourceFileURI
     *            the sourceFileURI to set
     */
    public void setSourceFileAbsolutePath(String sourceFileURI) {
        this.sourceFileAbsolutePath = sourceFileURI;
    }

    public void inspectObject(Object obj) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        //            System.out.println("==>ctx:inspectObject()"); //$NON-NLS-1$
        //            System.out.println("<==>ctx:inspectObject()"); //$NON-NLS-1$
        // }
    }

    public void inspectObject(String msg, Object obj) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        //            System.out.println("==>ctx:inspectObject()"); //$NON-NLS-1$
        //            System.out.println("<==>ctx:inspectObject()"); //$NON-NLS-1$
        // }
    }

    public void inspectObject(String msg, Object obj, Object ob2) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        //            System.out.println("==>ctx:inspectObject()"); //$NON-NLS-1$
        //            System.out.println("<==>ctx:inspectObject()"); //$NON-NLS-1$
        // }
    }

    public void inspectObject(String msg, Object obj, Object ob2, Object ob3) {
        // if (Platform.inDebugMode() || Platform.inDevelopmentMode()) {
        //            System.out.println("==>ctx:inspectObject(): " + msg); //$NON-NLS-1$
        //
        // if (obj instanceof DynamicEObjectImpl) {
        // DeoXsdSchemaReader schR =
        // new DeoXsdSchemaReader((DynamicEObjectImpl) obj);
        //
        // FeatureEList<?> includes = schR.getIncludes();
        // FeatureEList<?> complexTypes = schR.getComplexTypes();
        //
        //                System.out.println(""); //$NON-NLS-1$
        //
        // }
        //
        // if (ob2 instanceof DynamicEObjectImpl) {
        // DeoXsdSchemaReader schR =
        // new DeoXsdSchemaReader((DynamicEObjectImpl) ob2);
        //
        // FeatureEList<?> includes = schR.getIncludes();
        // FeatureEList<?> complexTypes = schR.getComplexTypes();
        //
        //                System.out.println(""); //$NON-NLS-1$
        //
        // }
        //
        //            System.out.println("<==ctx:inspectObject(): " + msg); //$NON-NLS-1$
        // }
    }

}
