/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * Property tester to test whether the given {@link URIEditorInput} or
 * {@link FileEditorInput} is of an Business Object Model file
 * 
 * @author njpatel
 * @since 3.2
 */
public class BOMEditorInputPropertyTester extends PropertyTester {

    private static final String BOM_EDITOR_PROP = "isBOMEditorInput"; //$NON-NLS-1$

    /**
     * BOM Editor input property tester.
     */
    public BOMEditorInputPropertyTester() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object,
     * java.lang.String, java.lang.Object[], java.lang.Object)
     */
    @Override
    public boolean test(Object receiver, String property, Object[] args,
            Object expectedValue) {
        String fileExt = null;
        if (BOM_EDITOR_PROP.equals(property)) {
            if (receiver instanceof FileEditorInput) {
                IFile file = ((FileEditorInput) receiver).getFile();
                if (file != null && file.isAccessible()) {
                    fileExt = file.getFileExtension();
                }
            } else if (receiver instanceof URIEditorInput) {
                URI uri = ((URIEditorInput) receiver).getURI();
                if (uri != null) {
                    fileExt = uri.fileExtension();
                }
            }
        }
        return fileExt != null
                && fileExt
                        .equalsIgnoreCase(BOMResourcesPlugin.BOM_FILE_EXTENSION);
    }

}
