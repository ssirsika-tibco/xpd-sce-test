/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class WsSoapJmsInboundBindingItemProvider extends WsSoapBindingItemProvider {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public WsSoapJmsInboundBindingItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addOutboundConnectionFactoryPropertyDescriptor(object);
            addInboundConnectionFactoryConfigurationPropertyDescriptor(object);
            addInboundDestinationPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Outbound Connection Factory feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addOutboundConnectionFactoryPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsInboundBinding_outboundConnectionFactory_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsInboundBinding_outboundConnectionFactory_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsInboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Inbound Connection Factory Configuration feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addInboundConnectionFactoryConfigurationPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_WsSoapJmsInboundBinding_inboundConnectionFactoryConfiguration_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_WsSoapJmsInboundBinding_inboundConnectionFactoryConfiguration_feature", //$NON-NLS-1$
                        "_UI_WsSoapJmsInboundBinding_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION,
                true,
                false,
                false,
                ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Inbound Destination feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addInboundDestinationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsInboundBinding_inboundDestination_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsInboundBinding_inboundDestination_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsInboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(XpdExtensionPackage.Literals.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns WsSoapJmsInboundBinding.gif.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/WsSoapJmsInboundBinding")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        String label = ((WsSoapJmsInboundBinding) object).getName();
        String type = getString("_UI_WsSoapJmsInboundBinding_type_2"); //$NON-NLS-1$
        return label == null || label.length() == 0 ? String.format("(%1$s)", type) //$NON-NLS-1$
                : String.format("%1$s (%2$s)", label, type); //$NON-NLS-1$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(WsSoapJmsInboundBinding.class)) {
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_CONNECTION_FACTORY_CONFIGURATION:
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_DESTINATION:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors
                .add(createChildParameter(XpdExtensionPackage.Literals.WS_SOAP_JMS_INBOUND_BINDING__INBOUND_SECURITY,
                        XpdExtensionFactory.eINSTANCE.createWsSoapSecurity()));
    }

}
