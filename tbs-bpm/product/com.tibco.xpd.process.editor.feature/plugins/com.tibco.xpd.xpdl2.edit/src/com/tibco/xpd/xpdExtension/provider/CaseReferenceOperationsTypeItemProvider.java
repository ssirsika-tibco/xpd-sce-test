/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.CaseReferenceOperationsType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Xpdl2Factory;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;

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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.CaseReferenceOperationsType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CaseReferenceOperationsTypeItemProvider extends ItemProviderAdapter
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
    public CaseReferenceOperationsTypeItemProvider(
            AdapterFactory adapterFactory) {
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

            addCaseRefFieldPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Case Ref Field feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addCaseRefFieldPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString(
                        "_UI_CaseReferenceOperationsType_caseRefField_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_CaseReferenceOperationsType_caseRefField_feature", //$NON-NLS-1$
                        "_UI_CaseReferenceOperationsType_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD,
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
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__DELETE);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS);
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
     * This returns CaseReferenceOperationsType.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator()
                        .getImage("full/obj16/CaseReferenceOperationsType")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((CaseReferenceOperationsType) object).getCaseRefField();
        return label == null || label.length() == 0
                ? getString("_UI_CaseReferenceOperationsType_type") //$NON-NLS-1$
                : getString("_UI_CaseReferenceOperationsType_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(CaseReferenceOperationsType.class)) {
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__CASE_REF_FIELD:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE:
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__DELETE:
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS:
        case XpdExtensionPackage.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), true, false));
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

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__UPDATE,
                XpdExtensionFactory.eINSTANCE.createUpdateCaseOperationType()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__DELETE,
                XpdExtensionFactory.eINSTANCE
                        .createDeleteCaseReferenceOperationType()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__ADD_LINK_ASSOCIATIONS,
                XpdExtensionFactory.eINSTANCE.createAddLinkAssociationsType()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.CASE_REFERENCE_OPERATIONS_TYPE__REMOVE_LINK_ASSOCIATIONS,
                XpdExtensionFactory.eINSTANCE
                        .createRemoveLinkAssociationsType()));
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
