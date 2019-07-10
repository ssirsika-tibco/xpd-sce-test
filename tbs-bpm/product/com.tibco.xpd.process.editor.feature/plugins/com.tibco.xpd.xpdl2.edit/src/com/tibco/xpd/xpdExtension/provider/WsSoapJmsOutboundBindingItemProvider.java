/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

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

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.WsSoapJmsOutboundBinding} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class WsSoapJmsOutboundBindingItemProvider extends WsSoapBindingItemProvider {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WsSoapJmsOutboundBindingItemProvider(AdapterFactory adapterFactory) {
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

            addOutboundConnectionFactoryPropertyDescriptor(object);
            addInboundDestinationPropertyDescriptor(object);
            addOutboundDestinationPropertyDescriptor(object);
            addDeliveryModePropertyDescriptor(object);
            addPriorityPropertyDescriptor(object);
            addMessageExpirationPropertyDescriptor(object);
            addInvocationTimeoutPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Outbound Connection Factory feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addOutboundConnectionFactoryPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsOutboundBinding_outboundConnectionFactory_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_outboundConnectionFactory_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Inbound Destination feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addInboundDestinationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsOutboundBinding_inboundDestination_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_inboundDestination_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Outbound Destination feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addOutboundDestinationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsOutboundBinding_outboundDestination_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_outboundDestination_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Delivery Mode feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDeliveryModePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsOutboundBinding_deliveryMode_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_deliveryMode_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Priority feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addPriorityPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsOutboundBinding_priority_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_priority_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Message Expiration feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addMessageExpirationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsOutboundBinding_messageExpiration_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_messageExpiration_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Invocation Timeout feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addInvocationTimeoutPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_WsSoapJmsOutboundBinding_invocationTimeout_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_invocationTimeout_feature", //$NON-NLS-1$
                                "_UI_WsSoapJmsOutboundBinding_type"), //$NON-NLS-1$
                        XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns WsSoapJmsOutboundBinding.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/WsSoapJmsOutboundBinding")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((WsSoapJmsOutboundBinding) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_WsSoapJmsOutboundBinding_type") : //$NON-NLS-1$
                getString("_UI_WsSoapJmsOutboundBinding_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(WsSoapJmsOutboundBinding.class)) {
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_CONNECTION_FACTORY:
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INBOUND_DESTINATION:
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_DESTINATION:
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__DELIVERY_MODE:
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__PRIORITY:
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__MESSAGE_EXPIRATION:
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__INVOCATION_TIMEOUT:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case XpdExtensionPackage.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors
                .add(createChildParameter(XpdExtensionPackage.Literals.WS_SOAP_JMS_OUTBOUND_BINDING__OUTBOUND_SECURITY,
                        XpdExtensionFactory.eINSTANCE.createWsSoapSecurity()));
    }

}
