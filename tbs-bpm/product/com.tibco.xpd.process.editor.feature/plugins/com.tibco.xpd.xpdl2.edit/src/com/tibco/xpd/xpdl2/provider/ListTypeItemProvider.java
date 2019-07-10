/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import com.tibco.xpd.xpdl2.ListType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.ListType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ListTypeItemProvider extends DataTypeItemProvider {
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
    public ListTypeItemProvider(AdapterFactory adapterFactory) {
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

        }
        return itemPropertyDescriptors;
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
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__BASIC_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__DECLARED_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__SCHEMA_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__EXTERNAL_REFERENCE);
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__RECORD_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__UNION_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__ENUMERATION_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__ARRAY_TYPE);
            childrenFeatures.add(Xpdl2Package.Literals.LIST_TYPE__LIST_TYPE);
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
     * This returns ListType.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/ListType")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_ListType_type"); //$NON-NLS-1$
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

        switch (notification.getFeatureID(ListType.class)) {
        case Xpdl2Package.LIST_TYPE__BASIC_TYPE:
        case Xpdl2Package.LIST_TYPE__DECLARED_TYPE:
        case Xpdl2Package.LIST_TYPE__SCHEMA_TYPE:
        case Xpdl2Package.LIST_TYPE__EXTERNAL_REFERENCE:
        case Xpdl2Package.LIST_TYPE__RECORD_TYPE:
        case Xpdl2Package.LIST_TYPE__UNION_TYPE:
        case Xpdl2Package.LIST_TYPE__ENUMERATION_TYPE:
        case Xpdl2Package.LIST_TYPE__ARRAY_TYPE:
        case Xpdl2Package.LIST_TYPE__LIST_TYPE:
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

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__BASIC_TYPE,
                Xpdl2Factory.eINSTANCE.createBasicType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__DECLARED_TYPE,
                Xpdl2Factory.eINSTANCE.createDeclaredType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__SCHEMA_TYPE,
                Xpdl2Factory.eINSTANCE.createSchema()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__EXTERNAL_REFERENCE,
                Xpdl2Factory.eINSTANCE.createExternalReference()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__RECORD_TYPE,
                Xpdl2Factory.eINSTANCE.createRecordType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__UNION_TYPE,
                Xpdl2Factory.eINSTANCE.createUnionType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__ENUMERATION_TYPE,
                Xpdl2Factory.eINSTANCE.createEnumerationType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__ARRAY_TYPE,
                Xpdl2Factory.eINSTANCE.createArrayType()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.LIST_TYPE__LIST_TYPE,
                Xpdl2Factory.eINSTANCE.createListType()));
    }

}
