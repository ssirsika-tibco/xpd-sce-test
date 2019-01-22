/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.JdbcResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.JdbcResource} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class JdbcResourceItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public JdbcResourceItemProvider(AdapterFactory adapterFactory) {
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

            addInstanceNamePropertyDescriptor(object);
            addJdbcProfileNamePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Instance Name feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addInstanceNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_JdbcResource_instanceName_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_JdbcResource_instanceName_feature", //$NON-NLS-1$
                        "_UI_JdbcResource_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.JDBC_RESOURCE__INSTANCE_NAME,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Jdbc Profile Name feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addJdbcProfileNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_JdbcResource_jdbcProfileName_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_JdbcResource_jdbcProfileName_feature", //$NON-NLS-1$
                        "_UI_JdbcResource_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.JDBC_RESOURCE__JDBC_PROFILE_NAME,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This returns JdbcResource.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/JdbcResource")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((JdbcResource) object).getInstanceName();
        return label == null || label.length() == 0
                ? getString("_UI_JdbcResource_type") //$NON-NLS-1$
                : getString("_UI_JdbcResource_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(JdbcResource.class)) {
        case XpdExtensionPackage.JDBC_RESOURCE__INSTANCE_NAME:
        case XpdExtensionPackage.JDBC_RESOURCE__JDBC_PROFILE_NAME:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
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
        return XpdExtensionEditPlugin.INSTANCE;
    }

}
