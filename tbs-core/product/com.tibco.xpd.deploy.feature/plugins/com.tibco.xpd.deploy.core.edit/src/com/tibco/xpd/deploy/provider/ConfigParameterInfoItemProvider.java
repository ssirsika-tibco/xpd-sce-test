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

import com.tibco.xpd.deploy.ConfigParameterInfo;
import com.tibco.xpd.deploy.DeployFactory;
import com.tibco.xpd.deploy.DeployPackage;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.deploy.ConfigParameterInfo} object.
 * <!-- begin-user-doc
 * -->
 * 
 * <!-- end-user-doc -->
 * @generated
 */
public class ConfigParameterInfoItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
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
    public ConfigParameterInfoItemProvider(AdapterFactory adapterFactory) {
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
            addKeyPropertyDescriptor(object);
            addLabelPropertyDescriptor(object);
            addIconPropertyDescriptor(object);
            addParameterTypePropertyDescriptor(object);
            addDefaultValuePropertyDescriptor(object);
            addRequiredPropertyDescriptor(object);
            addAutomaticPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
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
     * This adds a property descriptor for the Key feature.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    protected void addKeyPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ConfigParameterInfo_key_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_ConfigParameterInfo_key_feature",
                        "_UI_ConfigParameterInfo_type"),
                DeployPackage.Literals.CONFIG_PARAMETER_INFO__KEY, true, false,
                false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addLabelPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ConfigParameterInfo_label_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_ConfigParameterInfo_label_feature",
                        "_UI_ConfigParameterInfo_type"),
                DeployPackage.Literals.CONFIG_PARAMETER_INFO__LABEL, true,
                false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
                null));
    }

    /**
     * This adds a property descriptor for the Icon feature.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    protected void addIconPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ConfigParameterInfo_icon_feature"), getString(
                        "_UI_PropertyDescriptor_description",
                        "_UI_ConfigParameterInfo_icon_feature",
                        "_UI_ConfigParameterInfo_type"),
                DeployPackage.Literals.CONFIG_PARAMETER_INFO__ICON, true,
                false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null,
                null));
    }

    /**
     * This adds a property descriptor for the Parameter Type feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addParameterTypePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ConfigParameterInfo_parameterType_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_ConfigParameterInfo_parameterType_feature",
                        "_UI_ConfigParameterInfo_type"),
                DeployPackage.Literals.CONFIG_PARAMETER_INFO__PARAMETER_TYPE,
                true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null, null));
    }

    /**
     * This adds a property descriptor for the Default Value feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addDefaultValuePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ConfigParameterInfo_defaultValue_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_ConfigParameterInfo_defaultValue_feature",
                        "_UI_ConfigParameterInfo_type"),
                DeployPackage.Literals.CONFIG_PARAMETER_INFO__DEFAULT_VALUE,
                true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                null, null));
    }

    /**
     * This adds a property descriptor for the Required feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addRequiredPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ConfigParameterInfo_required_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_ConfigParameterInfo_required_feature",
                        "_UI_ConfigParameterInfo_type"),
                DeployPackage.Literals.CONFIG_PARAMETER_INFO__REQUIRED, true,
                false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
                null));
    }

    /**
     * This adds a property descriptor for the Automatic feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAutomaticPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(), getResourceLocator(),
                getString("_UI_ConfigParameterInfo_automatic_feature"),
                getString("_UI_PropertyDescriptor_description",
                        "_UI_ConfigParameterInfo_automatic_feature",
                        "_UI_ConfigParameterInfo_type"),
                DeployPackage.Literals.CONFIG_PARAMETER_INFO__AUTOMATIC, true,
                false, false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null,
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
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures
                    .add(DeployPackage.Literals.CONFIG_PARAMETER_INFO__FACETS);
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
     * This returns ConfigParameterInfo.gif.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj16/ConfigParameterInfo"));
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((ConfigParameterInfo) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_ConfigParameterInfo_type")
                : getString("_UI_ConfigParameterInfo_type") + " " + label;
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

        switch (notification.getFeatureID(ConfigParameterInfo.class)) {
        case DeployPackage.CONFIG_PARAMETER_INFO__NAME:
        case DeployPackage.CONFIG_PARAMETER_INFO__DESCRIPTION:
        case DeployPackage.CONFIG_PARAMETER_INFO__KEY:
        case DeployPackage.CONFIG_PARAMETER_INFO__LABEL:
        case DeployPackage.CONFIG_PARAMETER_INFO__ICON:
        case DeployPackage.CONFIG_PARAMETER_INFO__PARAMETER_TYPE:
        case DeployPackage.CONFIG_PARAMETER_INFO__DEFAULT_VALUE:
        case DeployPackage.CONFIG_PARAMETER_INFO__REQUIRED:
        case DeployPackage.CONFIG_PARAMETER_INFO__AUTOMATIC:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case DeployPackage.CONFIG_PARAMETER_INFO__FACETS:
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

        newChildDescriptors.add(createChildParameter(
                DeployPackage.Literals.CONFIG_PARAMETER_INFO__FACETS,
                DeployFactory.eINSTANCE.createParameterFacet()));
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
