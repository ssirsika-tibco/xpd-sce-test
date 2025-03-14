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
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
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

import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;
import com.tibco.xpd.deploy.ServerModule;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.deploy.ServerModule} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ServerModuleItemProvider extends ItemProviderAdapter implements
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
    public ServerModuleItemProvider(AdapterFactory adapterFactory) {
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
            addParametersPropertyDescriptor(object);
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
     * @generated NOT
     */
    protected void addDescriptionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_NamedElement_description_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_NamedElement_description_feature",
                        "_UI_NamedElement_type"),
                DeployPackage.Literals.NAMED_ELEMENT__DESCRIPTION, false,
                false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
                null));
    }

    /**
     * This adds a property descriptor for the Server Element Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addServerElementTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ServerElement_serverElementType_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_ServerElement_serverElementType_feature",
                        "_UI_ServerElement_type"),
                DeployPackage.Literals.SERVER_ELEMENT__SERVER_ELEMENT_TYPE,
                true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Server Element State feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addServerElementStatePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ServerElement_serverElementState_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_ServerElement_serverElementState_feature",
                        "_UI_ServerElement_type"),
                DeployPackage.Literals.SERVER_ELEMENT__SERVER_ELEMENT_STATE,
                true, false, true, null, null, null));
    }

    /**
     * This adds a property descriptor for the Parameters feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    protected void addParametersPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ServerElement_parameters_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_ServerElement_parameters_feature",
                        "_UI_ServerElement_type"),
                DeployPackage.Literals.SERVER_ELEMENT__PARAMETERS, false,
                false, true, null, null, null));
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
     * This returns ServerModule.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/ServerModule"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((ServerModule) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_ServerModule_type")
                : getString("_UI_ServerModule_type") + " " + label;
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

        switch (notification.getFeatureID(ServerModule.class)) {
        case DeployPackage.SERVER_MODULE__NAME:
        case DeployPackage.SERVER_MODULE__DESCRIPTION:
        case DeployPackage.SERVER_MODULE__PARAMETERS:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
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

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.SERVER_ELEMENT__PARAMETERS,
                DeployFactory.eINSTANCE.createConfigParameter()));
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

}
