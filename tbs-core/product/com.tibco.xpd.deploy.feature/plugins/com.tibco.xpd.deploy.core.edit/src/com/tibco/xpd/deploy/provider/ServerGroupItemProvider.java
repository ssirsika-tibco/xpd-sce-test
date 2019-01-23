/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.deploy.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.ServerGroup;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.deploy.ServerGroup} object.
 * <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * @generated
 */
public class ServerGroupItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ServerGroupItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addNamePropertyDescriptor(object);
            // addDescriptionPropertyDescriptor(object);
            addMembersPropertyDescriptor(object);
            addServerGroupTypePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_NamedElement_name_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_NamedElement_name_feature",
                        "_UI_NamedElement_type"),
                DeployPackage.Literals.NAMED_ELEMENT__NAME, false, false,
                false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                getString("_UI_BasePropertyCategory"), null));
    }

    /**
     * This adds a property descriptor for the Description feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDescriptionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_NamedElement_description_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_NamedElement_description_feature",
                        "_UI_NamedElement_type"),
                DeployPackage.Literals.NAMED_ELEMENT__DESCRIPTION, true, false,
                false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Members feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addMembersPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(
                        ((ComposeableAdapterFactory) adapterFactory)
                                .getRootAdapterFactory(), getResourceLocator(),
                        getString("_UI_ServerGroup_members_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_ServerGroup_members_feature",
                                "_UI_ServerGroup_type"),
                        DeployPackage.Literals.SERVER_GROUP__MEMBERS, false,
                        false, true, null,
                        getString("_UI_ServerGroupPropertyCategory"), null));
    }

    /**
     * This adds a property descriptor for the Server Group Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addServerGroupTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ServerGroup_serverGroupType_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_ServerGroup_serverGroupType_feature",
                        "_UI_ServerGroup_type"),
                DeployPackage.Literals.SERVER_GROUP__SERVER_GROUP_TYPE, false,
                false, true, null,
                getString("_UI_ServerGroupPropertyCategory"), null));
    }

    /**
     * This returns ServerGroup.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/ServerGroup"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((ServerGroup) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_ServerGroup_type")
                : getString("_UI_ServerGroup_type") + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to
     * update any cached children and by creating a viewer notification, which
     * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * Added notification to also refresh parent when server group members are
     * changed.
     * 
     * @generated NOT
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(ServerGroup.class)) {
        case DeployPackage.SERVER_GROUP__NAME:
        case DeployPackage.SERVER_GROUP__DESCRIPTION:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case DeployPackage.SERVER_GROUP__MEMBERS:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), true, true));
            if (notification.getNotifier() instanceof ServerGroup) {
                Object parent = getParent(notification.getNotifier());
                if (parent != null) {
                    fireNotifyChanged(new ViewerNotification(notification,
                            parent, true, true));
                }
            }
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing the children that can be created under this object. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return DeployModelEditPlugin.INSTANCE;
    }

    /**
     * {@inheritDoc}
     * 
     * @generated NOT
     */

    @Override
    protected Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(DeployPackage.Literals.SERVER_GROUP__MEMBERS);
        }
        return childrenFeatures;
    }

    /** {@inheritDoc} */
    @Override
    protected Command createRemoveCommand(EditingDomain domain, EObject owner,
            EStructuralFeature feature, Collection<?> collection) {
        // TODO Auto-generated method stub
        return super.createRemoveCommand(domain, owner, feature, collection);
    }
}
