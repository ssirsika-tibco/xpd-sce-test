/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.internal;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupport;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportFactory;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.resources.ui.clipboard.OMClipboardSupportHelper;

/**
 * Organization Modeler's clipboard support factory.
 * 
 * @author njpatel
 * 
 */
public class OMClipboardSupportFactory implements IClipboardSupportFactory {

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportFactory#
     * newClipboardSupport(org.eclipse.emf.ecore.EPackage)
     */
    public IClipboardSupport newClipboardSupport(EPackage package1) {
        return OMPackage.eINSTANCE == package1 ? new OMClipboardSupportHelper()
                : null;
    }

}
