/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * Transient group object grouping OM's location elements.
 * 
 * <p>
 * <i>Created: 4 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class OrganizationsTransientItemProvider extends TransientItemProvider {

    /**
     * The constructor.
     * 
     * @param adapterFactory
     *            the adapter factory for the model.
     * @param object
     *            parent for the group.
     */
    public OrganizationsTransientItemProvider(AdapterFactory adapterFactory,
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
            childrenFeatures.add(OMPackage.Literals.ORG_MODEL__ORGANIZATIONS);
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
        return getString("_UI_OrganizationsGroup_type"); //$NON-NLS-1$
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
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/OrganizationsGroup")); //$NON-NLS-1$ 
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
        case OMPackage.ORG_MODEL__ORGANIZATIONS:
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

        Organization organization = OMFactory.eINSTANCE.createOrganization();
        organization
                .setDisplayName(OMUtil
                        .getDefaultName(getString("_UI_DefaultName_Organization_label"), //$NON-NLS-1$
                                getDisplayNamesArray(getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__ORGANIZATIONS,
                        organization));

        // XPD-5300: Add Dynamic Organization child descriptor
        Organization dynOrg = OMFactory.eINSTANCE.createOrganization();
        dynOrg.setDynamic(true);
        dynOrg.setDisplayName(OMUtil
                .getDefaultName(getString("_UI_DefaultName_DynamicOrganisation_label"), //$NON-NLS-1$
                        getDisplayNamesArray(getChildren(object))));
        newChildDescriptors
                .add(createChildParameter(OMPackage.Literals.ORG_MODEL__ORGANIZATIONS,
                        dynOrg));

        // Add an item for each Organizatio type defined in the embedded
        // metamodel
        Notifier target2 = getTarget();

        if (target2 instanceof OrgModel) {
            OrgModel om = (OrgModel) target2;
            EList<OrganizationType> organizationTypes =
                    om.getEmbeddedMetamodel().getOrganizationTypes();

            for (OrganizationType organizationType : organizationTypes) {
                Organization org = OMFactory.eINSTANCE.createOrganization();
                org.setDisplayName(OMUtil.getDefaultName(organizationType
                        .getDisplayName(),
                        getDisplayNamesArray(getChildren(object))));
                org.setType(organizationType);
                newChildDescriptors
                        .add(createChildParameter(OMPackage.Literals.ORG_MODEL__ORGANIZATIONS,
                                org));
            }
        }
    }

    /**
     * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getCreateChildText(java.lang.Object,
     *      java.lang.Object, java.lang.Object, java.util.Collection)
     * 
     * @param owner
     * @param feature
     * @param child
     * @param selection
     * @return
     */
    @Override
    public String getCreateChildText(Object owner, Object feature,
            Object child, Collection<?> selection) {
        /* XPD-5300: Use appropriate label for a Dynamic Organization */
        if (child instanceof Organization && ((Organization) child).isDynamic()) {
            return getResourceLocator()
                    .getString("_UI_DynamicOrganisation_type"); //$NON-NLS-1$
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

}
