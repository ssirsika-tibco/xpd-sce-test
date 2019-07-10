/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.PackageHeader} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class PackageHeaderItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
        IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PackageHeaderItemProvider(AdapterFactory adapterFactory) {
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
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_DescribedElement_description_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_DescribedElement_description_feature", //$NON-NLS-1$
                                "_UI_DescribedElement_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.DESCRIBED_ELEMENT__DESCRIPTION,
                        true,
                        false,
                        false,
                        null,
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
            childrenFeatures.add(Xpdl2Package.Literals.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__XPDL_VERSION);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__VENDOR);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__CREATED);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__DOCUMENTATION);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__PRIORITY_UNIT);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__COST_UNIT);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__VENDOR_EXTENSIONS);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__LAYOUT_INFO);
            childrenFeatures.add(Xpdl2Package.Literals.PACKAGE_HEADER__MODIFICATION_DATE);
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
     * This returns PackageHeader.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/PackageHeader")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((PackageHeader) object).getXpdlVersion();
        return label == null || label.length() == 0 ? getString("_UI_PackageHeader_type") : //$NON-NLS-1$
                getString("_UI_PackageHeader_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(PackageHeader.class)) {
        case Xpdl2Package.PACKAGE_HEADER__DESCRIPTION:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.PACKAGE_HEADER__OTHER_ATTRIBUTES:
        case Xpdl2Package.PACKAGE_HEADER__VENDOR:
        case Xpdl2Package.PACKAGE_HEADER__CREATED:
        case Xpdl2Package.PACKAGE_HEADER__DOCUMENTATION:
        case Xpdl2Package.PACKAGE_HEADER__PRIORITY_UNIT:
        case Xpdl2Package.PACKAGE_HEADER__COST_UNIT:
        case Xpdl2Package.PACKAGE_HEADER__VENDOR_EXTENSIONS:
        case Xpdl2Package.PACKAGE_HEADER__LAYOUT_INFO:
        case Xpdl2Package.PACKAGE_HEADER__MODIFICATION_DATE:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
            return;
        case Xpdl2Package.PACKAGE_HEADER__XPDL_VERSION:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, true));
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

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__XPDL_VERSION, "")); //$NON-NLS-1$

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__VENDOR, "")); //$NON-NLS-1$

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__CREATED, "")); //$NON-NLS-1$

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__DOCUMENTATION,
                Xpdl2Factory.eINSTANCE.createDocumentation()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__PRIORITY_UNIT,
                Xpdl2Factory.eINSTANCE.createPriorityUnit()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__COST_UNIT,
                Xpdl2Factory.eINSTANCE.createCostUnit()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__VENDOR_EXTENSIONS,
                Xpdl2Factory.eINSTANCE.createVendorExtensions()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__LAYOUT_INFO,
                Xpdl2Factory.eINSTANCE.createLayoutInfo()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.PACKAGE_HEADER__MODIFICATION_DATE,
                Xpdl2Factory.eINSTANCE.createModificationDate()));
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return Xpdl2EditPlugin.INSTANCE;
    }

}
