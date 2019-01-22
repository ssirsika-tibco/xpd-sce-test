/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * Transient group object grouping OM's position types elements.
 * 
 * <p>
 * <i>Created: 4 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class PositionTypesTransientItemProvider extends TransientItemProvider {

    /**
     * The constructor.
     * 
     * @param adapterFactory
     *            the adapter factory for the model.
     * @param object
     *            parent for the group.
     */
    public PositionTypesTransientItemProvider(AdapterFactory adapterFactory,
            EObject object) {
        super(adapterFactory, object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getChildrenFeatures
     * (java.lang.Object)
     */
    @Override
    protected Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures
                    .add(OMPackage.Literals.ORG_META_MODEL__POSITION_TYPES);
        }
        return childrenFeatures;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getText(java.lang.Object
     * )
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_PositionTypesGroup_type"); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#getImage(java.lang.
     * Object)
     */
    @Override
    public Object getImage(Object object) {
        // TODO change
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/PositionTypesGroup")); //$NON-NLS-1$ 
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse
     * .emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification notification) {
        switch (notification.getFeatureID(OrgMetaModel.class)) {
        case OMPackage.ORG_META_MODEL__POSITION_TYPES:
            fireNotifyChanged(new ViewerNotification(new NotificationWrapper(
                    this, notification), this, true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.ItemProviderAdapter#collectNewChildDescriptors
     * (java.util.Collection, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        PositionType positionType = OMFactory.eINSTANCE.createPositionType();
        positionType.setDisplayName(OMUtil.getDefaultName(
                getString("_UI_DefaultName_PositionType_label"), //$NON-NLS-1$
                getDisplayNamesArray(getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(
                        OMPackage.Literals.ORG_META_MODEL__POSITION_TYPES,
                        positionType));

    }

}
