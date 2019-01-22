/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.deploy.provider;

import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.ServerGroupType;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

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

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.deploy.ServerGroupType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ServerGroupTypeItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ServerGroupTypeItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addNamePropertyDescriptor(object);
            addDescriptionPropertyDescriptor(object);
            addValidPropertyDescriptor(object);
            addLastExtensionIdPropertyDescriptor(object);
            addServerTypesPropertyDescriptor(object);
            addIdPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_NamedElement_name_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_NamedElement_name_feature",
                        "_UI_NamedElement_type"),
                DeployPackage.Literals.NAMED_ELEMENT__NAME, true, false, false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Description feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
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
     * This adds a property descriptor for the Valid feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addValidPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_Loadable_valid_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_Loadable_valid_feature", "_UI_Loadable_type"),
                DeployPackage.Literals.LOADABLE__VALID, true, false, false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Last Extension Id feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addLastExtensionIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_Loadable_lastExtensionId_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_Loadable_lastExtensionId_feature",
                        "_UI_Loadable_type"),
                DeployPackage.Literals.LOADABLE__LAST_EXTENSION_ID, true,
                false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
                null));
    }

    /**
     * This adds a property descriptor for the Server Types feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addServerTypesPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ServerGroupType_serverTypes_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_ServerGroupType_serverTypes_feature",
                        "_UI_ServerGroupType_type"),
                DeployPackage.Literals.SERVER_GROUP_TYPE__SERVER_TYPES, true,
                false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Id feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ServerGroupType_id_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_ServerGroupType_id_feature",
                        "_UI_ServerGroupType_type"),
                DeployPackage.Literals.SERVER_GROUP_TYPE__ID, true, false,
                false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This returns ServerGroupType.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/ServerGroupType"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((ServerGroupType) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_ServerGroupType_type")
                : getString("_UI_ServerGroupType_type") + " " + label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(ServerGroupType.class)) {
        case DeployPackage.SERVER_GROUP_TYPE__NAME:
        case DeployPackage.SERVER_GROUP_TYPE__DESCRIPTION:
        case DeployPackage.SERVER_GROUP_TYPE__VALID:
        case DeployPackage.SERVER_GROUP_TYPE__LAST_EXTENSION_ID:
        case DeployPackage.SERVER_GROUP_TYPE__ID:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(
            Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return DeployModelEditPlugin.INSTANCE;
    }

}
