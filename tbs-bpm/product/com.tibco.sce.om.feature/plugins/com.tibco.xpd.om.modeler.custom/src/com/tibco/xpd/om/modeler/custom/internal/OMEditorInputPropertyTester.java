/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.om.resources.OMResourcesActivator;

/**
 * Property tester to test whether the given {@link URIEditorInput} or
 * {@link FileEditorInput} is of an Organization Model file.
 * 
 * @author njpatel
 * 
 */
public class OMEditorInputPropertyTester extends PropertyTester {

    private static final String EDITORINPUT_PROP = "isOMEditorInput"; //$NON-NLS-1$

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {

        if (EDITORINPUT_PROP.equals(property)) {
            String fileExt = null;

            if (receiver instanceof URIEditorInput) {
                URI uri = ((URIEditorInput) receiver).getURI();
                if (uri != null) {
                    fileExt = uri.fileExtension();
                }
            } else if (receiver instanceof FileEditorInput) {
                IFile file = ((FileEditorInput) receiver).getFile();
                if (file != null) {
                    fileExt = file.getFileExtension();
                }
            }

            if (fileExt != null) {
                return fileExt
                        .equalsIgnoreCase(OMResourcesActivator.OM_FILE_EXTENSION);
            }
        }
        return false;
    }

}
