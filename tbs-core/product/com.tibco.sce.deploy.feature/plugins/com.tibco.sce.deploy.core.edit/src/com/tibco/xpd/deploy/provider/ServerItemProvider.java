/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.deploy.provider;

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

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.Server;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.deploy.Server}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ServerItemProvider extends UniqueIdElementItemProvider implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.";

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ServerItemProvider(AdapterFactory adapterFactory) {
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
            addServerTypePropertyDescriptor(object);
            addServerStatePropertyDescriptor(object);
            // addConnectionPropertyDescriptor(object);
            addRuntimePropertyDescriptor(object);
            // addServerGroupPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Workspace Modules feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addWorkspaceModulesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Server_workspaceModules_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_Server_workspaceModules_feature",
                                "_UI_Server_type"),
                        DeployPackage.Literals.SERVER__WORKSPACE_MODULES,
                        true,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Name feature. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_NamedElement_name_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_NamedElement_name_feature",
                                "_UI_NamedElement_type"),
                        DeployPackage.Literals.NAMED_ELEMENT__NAME,
                        false,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        getString("_UI_BasePropertyCategory"),
                        null));
    }

    /**
     * This adds a property descriptor for the Description feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addDescriptionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_NamedElement_description_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_NamedElement_description_feature",
                                "_UI_NamedElement_type"),
                        DeployPackage.Literals.NAMED_ELEMENT__DESCRIPTION,
                        false,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Server Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addServerTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Server_serverType_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_Server_serverType_feature",
                                "_UI_Server_type"),
                        DeployPackage.Literals.SERVER__SERVER_TYPE,
                        false,
                        false,
                        false,
                        null,
                        getString("_UI_ServerPropertyCategory"),
                        null));
    }

    /**
     * This adds a property descriptor for the Server State feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addServerStatePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Server_serverState_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_Server_serverState_feature",
                                "_UI_Server_type"),
                        DeployPackage.Literals.SERVER__SERVER_STATE,
                        false,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        getString("_UI_ServerPropertyCategory"),
                        null));
    }

    /**
     * This adds a property descriptor for the Connection feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addConnectionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Server_connection_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_Server_connection_feature",
                                "_UI_Server_type"),
                        DeployPackage.Literals.SERVER__CONNECTION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Runtime feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addRuntimePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Server_runtime_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_Server_runtime_feature",
                                "_UI_Server_type"),
                        DeployPackage.Literals.SERVER__RUNTIME,
                        false,
                        false,
                        true,
                        null,
                        getString("_UI_ServerPropertyCategory"),
                        null));
    }

    /**
     * This adds a property descriptor for the Server Group feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addServerGroupPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_Server_serverGroup_feature"),
                        getString("_UI_PropertyDescriptor_description",
                                "_UI_Server_serverGroup_feature",
                                "_UI_Server_type"),
                        DeployPackage.Literals.SERVER__SERVER_GROUP,
                        false,
                        false,
                        true,
                        null,
                        null,
                        null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to
     * deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand},
     * {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in
     * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures
                    .add(DeployPackage.Literals.SERVER__SERVER_ELEMENTS);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper
        // feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns Server.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/Server"));
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        String label = ((Server) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_Server_type")
                : label;
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to
     * update any cached children and by creating a viewer notification, which
     * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(Server.class)) {
        case DeployPackage.SERVER__NAME:
        case DeployPackage.SERVER__DESCRIPTION:
        case DeployPackage.SERVER__SERVER_TYPE:
        case DeployPackage.SERVER__SERVER_CONFIG:
        case DeployPackage.SERVER__SERVER_STATE:
        case DeployPackage.SERVER__CONNECTION:
        case DeployPackage.SERVER__REPOSITORY:
        case DeployPackage.SERVER__RUNTIME:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case DeployPackage.SERVER__SERVER_ELEMENTS:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), true, false));
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

        newChildDescriptors
                .add(createChildParameter(DeployPackage.Literals.SERVER__SERVER_CONFIG,
                        DeployFactory.eINSTANCE.createServerConfig()));

        newChildDescriptors
                .add(createChildParameter(DeployPackage.Literals.SERVER__REPOSITORY,
                        DeployFactory.eINSTANCE.createRepository()));

        newChildDescriptors
                .add(createChildParameter(DeployPackage.Literals.SERVER__SERVER_ELEMENTS,
                        DeployFactory.eINSTANCE.createServerModule()));

        newChildDescriptors
                .add(createChildParameter(DeployPackage.Literals.SERVER__SERVER_ELEMENTS,
                        DeployFactory.eINSTANCE.createModuleContainer()));

        newChildDescriptors
                .add(createChildParameter(DeployPackage.Literals.SERVER__WORKSPACE_MODULES,
                        DeployFactory.eINSTANCE.createWorkspaceModule()));
    }

}
