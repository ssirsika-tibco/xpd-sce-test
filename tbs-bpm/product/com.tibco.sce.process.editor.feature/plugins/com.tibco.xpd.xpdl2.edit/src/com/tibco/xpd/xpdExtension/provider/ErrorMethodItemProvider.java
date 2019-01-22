/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

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
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.provider.UniqueIdElementItemProvider;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.ErrorMethod} object.
 * <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * @generated
 */
public class ErrorMethodItemProvider extends UniqueIdElementItemProvider {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ErrorMethodItemProvider(AdapterFactory adapterFactory) {
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

            addDisableImplicitAssociationPropertyDescriptor(object);
            addErrorCodePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Disable Implicit Association feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDisableImplicitAssociationPropertyDescriptor(
            Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString(
                        "_UI_AssociatedParametersContainer_disableImplicitAssociation_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_AssociatedParametersContainer_disableImplicitAssociation_feature", //$NON-NLS-1$
                        "_UI_AssociatedParametersContainer_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.ASSOCIATED_PARAMETERS_CONTAINER__DISABLE_IMPLICIT_ASSOCIATION,
                true,
                false,
                false,
                ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                null,
                null));
    }

    /**
     * This adds a property descriptor for the Error Code feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addErrorCodePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add(createItemPropertyDescriptor(
                ((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                getResourceLocator(),
                getString("_UI_ErrorMethod_errorCode_feature"), //$NON-NLS-1$
                getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                        "_UI_ErrorMethod_errorCode_feature", //$NON-NLS-1$
                        "_UI_ErrorMethod_type"), //$NON-NLS-1$
                XpdExtensionPackage.Literals.ERROR_METHOD__ERROR_CODE,
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
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS);
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
     * This returns ErrorMethod.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/ErrorMethodCustom")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String getText(Object object) {
        String label = null;
        if (object instanceof ErrorMethod) {
            label = ((ErrorMethod) object).getErrorCode();
        }
        return label == null || label.length() == 0
                ? getString("_UI_ErrorMethod_type") //$NON-NLS-1$
                : label;
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

        switch (notification.getFeatureID(ErrorMethod.class)) {
        case XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
        case XpdExtensionPackage.ERROR_METHOD__ERROR_CODE:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), true, false));
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
                XpdExtensionPackage.Literals.ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS,
                XpdExtensionFactory.eINSTANCE.createAssociatedParameter()));
    }

    /**
     * Return the resource locator for this item provider's resources. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return XpdExtensionEditPlugin.INSTANCE;
    }

}
