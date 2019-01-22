/**
 */
package com.tibco.xpd.globalSignalDefinition.provider;


import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionEditPlugin;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionPackage;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;

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

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GlobalSignalDefinitionsItemProvider
    extends ItemProviderAdapter
    implements
        IEditingDomainItemProvider,
        IStructuredItemContentProvider,
        ITreeItemContentProvider,
        IItemLabelProvider,
        IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.";

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GlobalSignalDefinitionsItemProvider(AdapterFactory adapterFactory) {
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

            addDescriptionPropertyDescriptor(object);
            addFormatVersionPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Description feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDescriptionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_GlobalSignalDefinitions_description_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_GlobalSignalDefinitions_description_feature", "_UI_GlobalSignalDefinitions_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Format Version feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addFormatVersionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_GlobalSignalDefinitions_formatVersion_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_GlobalSignalDefinitions_formatVersion_feature", "_UI_GlobalSignalDefinitions_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION,
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS);
            childrenFeatures.add(GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL_DEFINITIONS__MIXED);
            childrenFeatures.add(GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP);
            childrenFeatures.add(GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION);
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
     * This returns GlobalSignalDefinitions.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/GlobalSignalDefinitions")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((GlobalSignalDefinitions)object).getDescription();
        return label == null || label.length() == 0 ?
            getString("_UI_GlobalSignalDefinitions_type") : //$NON-NLS-1$
            getString("_UI_GlobalSignalDefinitions_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(GlobalSignalDefinitions.class)) {
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__DESCRIPTION:
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__FORMAT_VERSION:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS:
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__MIXED:
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XMLNS_PREFIX_MAP:
            case GlobalSignalDefinitionPackage.GLOBAL_SIGNAL_DEFINITIONS__XSI_SCHEMA_LOCATION:
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

        newChildDescriptors.add
            (createChildParameter
                (GlobalSignalDefinitionPackage.Literals.GLOBAL_SIGNAL_DEFINITIONS__GLOBAL_SIGNALS,
                 GlobalSignalDefinitionFactory.eINSTANCE.createGlobalSignal()));
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return GlobalSignalDefinitionEditPlugin.INSTANCE;
    }

}
