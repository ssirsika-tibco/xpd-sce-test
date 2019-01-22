/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * @author rsomayaj
 * 
 */
public class EndErrorGroup extends AbstractAssetGroup {

    /**
     * @param parent
     */
    public EndErrorGroup(EObject parent) {
        super(parent);
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getFeature()
     * 
     * @return
     */
    @Override
    public EStructuralFeature getFeature() {
        return XpdExtensionPackage.eINSTANCE.getInterfaceMethod_ErrorMethods();
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getImage()
     * 
     * @return
     */
    @Override
    public Image getImage() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.IMG_ERROR_EVENT_ICON12);
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getReferenceType()
     * 
     * @return
     */
    @Override
    public EClass getReferenceType() {
        return XpdExtensionPackage.eINSTANCE.getErrorMethod();
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup#getText()
     * 
     * @return
     */
    @Override
    public String getText() {
        return Messages.EndErrorGroup_ErrorEvent_title;
    }

}
