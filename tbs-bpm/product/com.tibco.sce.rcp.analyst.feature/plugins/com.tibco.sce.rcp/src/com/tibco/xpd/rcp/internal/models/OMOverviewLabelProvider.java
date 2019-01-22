/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.models;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.core.om.provider.TransientItemProvider;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * OM content label provider.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
/* public */class OMOverviewLabelProvider extends AbstractLabelProvider {

    @Override
    public String getText(Object element) {

        if ((element instanceof EObject && isFromOMPackage((EObject) element))
                || element instanceof TransientItemProvider) {
            return getFactoryLabelProvider().getText(element);
        }
        return super.getText(element);
    }

    @Override
    public Image getImage(Object element) {

        if ((element instanceof EObject && isFromOMPackage((EObject) element))
                || element instanceof TransientItemProvider) {

            Image img = null;
            if (element instanceof Organization) {
                Organization org = (Organization) element;
                if (org.getType() != null) {
                    img =
                            ExtendedImageRegistry.INSTANCE
                                    .getImage(OMModelImages
                                            .getImageObject(OMModelImages.IMAGE_ORGANISATION_TYPE));
                }
            } else if (element instanceof OrgUnit) {
                OrgUnit ou = (OrgUnit) element;
                if (ou.getFeature() != null) {
                    img =
                            ExtendedImageRegistry.INSTANCE
                                    .getImage(OMModelImages
                                            .getImageObject(OMModelImages.IMAGE_ORG_UNIT_FEATURE));
                }
            } else if (element instanceof Position) {
                Position pos = (Position) element;
                if (pos.getFeature() != null) {
                    img =
                            ExtendedImageRegistry.INSTANCE
                                    .getImage(OMModelImages
                                            .getImageObject(OMModelImages.IMAGE_POSITION_FEATURE));
                }
            }

            if (img != null) {
                return img;
            }

            Image image = getFactoryLabelProvider().getImage(element);

            /*
             * If Org Model then decorate the image with the file's overlays.
             */
            if (element instanceof OrgModel) {
                IFile file = WorkingCopyUtil.getFile((OrgModel) element);
                if (file != null) {
                    image = getDecorator().decorateImage(image, file);
                }
            }

            return image;
        }

        return super.getImage(element);
    }

    @Override
    public String getDescription(Object anElement) {
        if (anElement instanceof TransientItemProvider) {
            return getText(anElement);
        }
        return super.getDescription(anElement);
    }

    /**
     * Check if the given <code>EObject</code> is from the
     * <code>OMPackage</code>.
     * 
     * @param eo
     *            <code>EObject</code> to test.
     * @return <code>true</code> if from the <code>UMLPackage</code>,
     *         <code>false</code> otherwise.
     */
    private boolean isFromOMPackage(EObject eo) {
        boolean fromOMPackage = false;

        if (eo != null && eo.eClass() != null) {
            EPackage pkg = eo.eClass().getEPackage();

            fromOMPackage = pkg != null && pkg == OMPackage.eINSTANCE;
        }

        return fromOMPackage;
    }
}
