/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
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
 * 
 */
public class StartMethodGroup extends AbstractAssetGroup {
    private static final String TITLE = Messages.StartMethodGroup_Group_title;

    /**
     * Default constructor
     * 
     * @param parent
     */
    public StartMethodGroup(EObject parent) {
        super(parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getFeature()
     */
    @Override
    public EStructuralFeature getFeature() {
        return XpdExtensionPackage.eINSTANCE.getProcessInterface_StartMethods();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getImage()
     */
    @Override
    public Image getImage() {
        return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                .get(Xpdl2ResourcesConsts.ICON_INTERFACE_STARTEVENT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.BpmArtefactGroup
     * #getText()
     */
    @Override
    public String getText() {
        return TITLE;
    }

    @Override
    public EClass getReferenceType() {
        return XpdExtensionPackage.eINSTANCE.getStartMethod();
    }
}
