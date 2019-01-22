/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.Capable;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.util.OMUtil;

/**
 * Transient group object grouping capabilities and their categories.
 * 
 * <p>
 * <i>Created: 4 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class CapabilitiesTransientItemProvider extends TransientItemProvider {

    /**
     * The constructor.
     * 
     * @param adapterFactory
     *            the adapter factory for the model.
     * @param object
     *            parent for the group.
     */
    public CapabilitiesTransientItemProvider(AdapterFactory adapterFactory,
            EObject object) {
        super(adapterFactory, object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.om.core.om.provider.TransientItemProvider#getChildren(java
     * .lang.Object)
     */
    @Override
    public Collection<?> getChildren(Object object) {
        List<Object> children = new ArrayList<Object>();
        children.addAll(super.getChildren(object));
        for (Iterator<Object> iterator = children.iterator(); iterator
                .hasNext();) {
            EObject child = (EObject) iterator.next();
            if (child instanceof Capability) {
                if (((Capability) child).getCategory() != null) {
                    iterator.remove();
                }
            }
        }
        return children;
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
                    .add(OMPackage.Literals.ORG_MODEL__CAPABILITY_CATEGORIES);
            childrenFeatures.add(OMPackage.Literals.ORG_MODEL__CAPABILITIES);
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
        return getString("_UI_CapabilitiesGroup_type"); //$NON-NLS-1$
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
                "full/obj16/CapabilitiesGroup")); //$NON-NLS-1$ 
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
        case OMPackage.ORG_MODEL__CAPABILITY_CATEGORIES:
        case OMPackage.ORG_MODEL__CAPABILITIES:
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

        CapabilityCategory capabilityCategory = OMFactory.eINSTANCE
                .createCapabilityCategory();
        capabilityCategory.setDisplayName(OMUtil.getDefaultName(
                getString("_UI_DefaultName_CapabilityCategory_label"), //$NON-NLS-1$
                getDisplayNamesArray(getAllCategories(object))));
        newChildDescriptors.add(createChildParameter(
                OMPackage.Literals.ORG_MODEL__CAPABILITY_CATEGORIES,
                capabilityCategory));

        Capability capability = OMFactory.eINSTANCE.createCapability();
        capability.setDisplayName(OMUtil.getDefaultName(
                getString("_UI_DefaultName_Capability_label"), //$NON-NLS-1$
                getDisplayNamesArray(getAllCapabilities(object))));
        newChildDescriptors.add(createChildParameter(
                OMPackage.Literals.ORG_MODEL__CAPABILITIES, capability));
    }

    /** {@inheritDoc} */
    @Override
    protected Command createRemoveCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Collection<?> collection) {
        CompoundCommand removeWithRefCommand = new CompoundCommand();
        for (Object object : collection) {
            if (object instanceof Capability) {
                Capability c = ((Capability) object);
                ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
                        .getCrossReferenceAdapter(c);
                Collection<Setting> inverseReferences = crossReferenceAdapter
                        .getInverseReferences(c, true);
                for (Setting ref : inverseReferences) {
                    if (ref.getEObject() instanceof CapabilityAssociation
                            && ref.getEObject().eContainer() instanceof Capable) {
                        CapabilityAssociation capAssoc = ((CapabilityAssociation) ref
                                .getEObject());
                        removeWithRefCommand.append(RemoveCommand.create(
                                domain, capAssoc.eContainer(),
                                OMPackage.eINSTANCE
                                        .getCapable_CapabilitiesAssociations(),
                                Collections.singletonList(capAssoc)));

                    }
                }
            }
        }
        if (!removeWithRefCommand.isEmpty()) {
            removeWithRefCommand.append(super.createRemoveCommand(domain,
                    owner, feature, collection));
            return removeWithRefCommand;
        }
        return super.createRemoveCommand(domain, owner, feature, collection);
    }

    /**
     * Get all the Capabilities in the model.
     * 
     * @param obj
     * @return
     */
    private Collection<Capability> getAllCapabilities(Object obj) {
        if (obj instanceof TransientItemProvider) {
            return getAllCapabilities(((TransientItemProvider) obj)
                    .getParent(obj));
        } else if (obj instanceof OrgModel) {
            return ((OrgModel) obj).getCapabilities();
        }
        return new ArrayList<Capability>(0);
    }

    /**
     * Get all the capability categories in the model.
     * 
     * @param obj
     * @return
     */
    private Collection<CapabilityCategory> getAllCategories(Object obj) {

        if (obj instanceof TransientItemProvider) {
            return getAllCategories(((TransientItemProvider) obj)
                    .getParent(obj));
        } else {
            List<CapabilityCategory> categories = new BasicEList<CapabilityCategory>();
            Collection<CapabilityCategory> subCategories = null;
            if (obj instanceof OrgModel) {
                subCategories = ((OrgModel) obj).getCapabilityCategories();
            } else if (obj instanceof CapabilityCategory) {
                categories.add((CapabilityCategory) obj);
                subCategories = ((CapabilityCategory) obj).getSubCategories();
            }

            if (subCategories != null) {
                for (CapabilityCategory category : subCategories) {
                    categories.addAll(getAllCategories(category));
                }
            }
            return categories;
        }
    }
}
