/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * Transient group object grouping OM's group elements.
 * 
 * <p>
 * <i>Created: 4 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class GroupsTransientItemProvider extends TransientItemProvider {

    /**
     * The constructor.
     * 
     * @param adapterFactory
     *            the adapter factory for the model.
     * @param object
     *            parent for the group.
     */
    public GroupsTransientItemProvider(AdapterFactory adapterFactory,
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
            childrenFeatures.add(OMPackage.Literals.ORG_MODEL__GROUPS);
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
        return getString("_UI_GroupsGroup_type"); //$NON-NLS-1$
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
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/GroupsGroup")); //$NON-NLS-1$ 
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
        switch (notification.getFeatureID(OrgModel.class)) {
        case OMPackage.ORG_MODEL__GROUPS:
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
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        Group group = OMFactory.eINSTANCE.createGroup();
        group.setDisplayName(OMUtil.getDefaultName(
                getString("_UI_DefaultName_Group_label"), //$NON-NLS-1$
                getDisplayNamesArray(getAllGroups(object))));
        newChildDescriptors.add(createChildParameter(
                OMPackage.Literals.ORG_MODEL__GROUPS, group));
    }

    /**
     * Get all the groups from the given object.
     * 
     * @param obj {@link TransientItemProvider} or {@link OrgModel}
     * @return
     */
    private Collection<Group> getAllGroups(Object obj) {
        if (obj instanceof TransientItemProvider) {
            return getAllGroups(((TransientItemProvider) obj).getParent(obj));
        } else if (obj instanceof OrgModel) {
            return OMUtil.getAllGroups((OrgModel) obj);
        }
        return new ArrayList<Group>(0);
    }

}
