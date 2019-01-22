/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.rql.model.script.RQLContentAssistIconProvider;

/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class RQLConsts {

    public static final String RQL_MODEL_FILE_NAME = "/model/RQLScript.uml"; //$NON-NLS-1$
    
    public static final String RQL_MODEL_PRIVILEGE_CLASS = "privilege"; //$NON-NLS-1$
    
    public static final String RQL_MODEL_CAPABILITY_CLASS = "capability"; //$NON-NLS-1$
    
    public static final String RQL_MODEL_ORGANIZATION_CLASS = "organization"; //$NON-NLS-1$
    
    public static final String RQL_MODEL_ORGUNIT_CLASS = "orgunit"; //$NON-NLS-1$
    
    public static final String RQL_MODEL_POSITION_CLASS = "position"; //$NON-NLS-1$
    
    public static final String RQL_MODEL_RESOURCE_CLASS = "resource"; //$NON-NLS-1$
    
    public static final String RQL_MODEL_GROUP_CLASS = "group"; //$NON-NLS-1$
    
    public static final String RQL_MODEL_LOCATION_CLASS = "location"; //$NON-NLS-1$
    
    public static final String UNDEFINED = "Undefined"; //$NON-NLS-1$

    private static RQLContentAssistIconProvider rqlContentAssistIconProvider = null;

	public static Image getOmIcon(Class umlClass) {
	    //this is needed for the tests
        Display display = Display.getCurrent();
        if (umlClass != null && display != null) {
            String className = umlClass.getName();
            if (className != null) {
                if (className.equals(RQL_MODEL_PRIVILEGE_CLASS)) {
                    return ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_PRIVILEGE));
                } else if (className.equals(RQL_MODEL_CAPABILITY_CLASS)) {
                    return ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_CAPABILITY));
                } else if (className.equals(RQL_MODEL_ORGANIZATION_CLASS)) {
                    return ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_ORGANISATION));
                } else if (className.equals(RQL_MODEL_ORGUNIT_CLASS)) {
                    return ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_ORG_UNIT));
                } else if (className.equals(RQL_MODEL_POSITION_CLASS)) {
                    return ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_POSITION));
                } else if (className.equals(RQL_MODEL_RESOURCE_CLASS)) {
                    return ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_RESOURCE));
                } else if (className.equals(RQL_MODEL_GROUP_CLASS)) {
                    return ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_GROUP));
                } else if (className.equals(RQL_MODEL_LOCATION_CLASS)) {
                    return ExtendedImageRegistry.INSTANCE
                            .getImage(OMModelImages
                                    .getImageObject(OMModelImages.IMAGE_LOCATION));
                }
            }
        }
        return null;
    }

    public static Image getOmIcon(EObject model) {
        //this is needed for the tests
        Display display = Display.getCurrent();
        if (model != null && display != null) {
            if (model instanceof Privilege) {
                return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_PRIVILEGE));
            } else if (model instanceof Capability) {
                return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_CAPABILITY));
            } else if (model instanceof Organization) {
                return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_ORGANISATION));
            } else if (model instanceof OrgUnit) {
                return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_ORG_UNIT));
            } else if (model instanceof Position) {
                return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_POSITION));
            } else if (model instanceof Resource) {
                return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_RESOURCE));
            } else if (model instanceof Group) {
                return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_GROUP));
            } else if (model instanceof Location) {
                return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                        .getImageObject(OMModelImages.IMAGE_LOCATION));
            }
        }
        return null;
    }
    

    public static RQLContentAssistIconProvider getRQLContentAssistIconProvider() {
        if(rqlContentAssistIconProvider == null){
            rqlContentAssistIconProvider = new RQLContentAssistIconProvider();
        }
        return rqlContentAssistIconProvider;
    }
    
}
