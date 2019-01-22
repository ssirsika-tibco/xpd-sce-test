/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (25 Apr 2010)
 */
public class PackageEditorInputFactory implements EditorInputFactory {

    /**
     * @see com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory#getEditorInputFor(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    public IEditorInput getEditorInputFor(Object obj) {
        if (obj instanceof com.tibco.xpd.xpdl2.Package) {
            com.tibco.xpd.xpdl2.Package xpdlPackage =
                    (com.tibco.xpd.xpdl2.Package) obj;
            IFile xpdlFile = WorkingCopyUtil.getFile(xpdlPackage);
            if (xpdlFile != null && xpdlFile.isAccessible()) {
                return new FileEditorInput(xpdlFile);
            }
        }
        return null;
    }

}
