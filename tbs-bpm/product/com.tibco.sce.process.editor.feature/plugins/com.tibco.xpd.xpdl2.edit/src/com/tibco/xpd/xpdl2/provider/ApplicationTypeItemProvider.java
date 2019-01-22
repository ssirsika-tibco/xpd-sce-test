/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import com.tibco.xpd.xpdl2.ApplicationType;
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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.ApplicationType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ApplicationTypeItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ApplicationTypeItemProvider(AdapterFactory adapterFactory) {
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
    public Collection<? extends EStructuralFeature> getChildrenFeatures(
            Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(Xpdl2Package.Literals.APPLICATION_TYPE__EJB);
            childrenFeatures.add(Xpdl2Package.Literals.APPLICATION_TYPE__POJO);
            childrenFeatures.add(Xpdl2Package.Literals.APPLICATION_TYPE__XSLT);
            childrenFeatures
                    .add(Xpdl2Package.Literals.APPLICATION_TYPE__SCRIPT);
            childrenFeatures
                    .add(Xpdl2Package.Literals.APPLICATION_TYPE__WEB_SERVICE);
            childrenFeatures
                    .add(Xpdl2Package.Literals.APPLICATION_TYPE__BUSINESS_RULE);
            childrenFeatures.add(Xpdl2Package.Literals.APPLICATION_TYPE__FORM);
            childrenFeatures
                    .add(Xpdl2Package.Literals.APPLICATION_TYPE__ANY_ATTRIBUTE);
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
     * This returns ApplicationType.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/ApplicationType")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_ApplicationType_type"); //$NON-NLS-1$
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

        switch (notification.getFeatureID(ApplicationType.class)) {
        case Xpdl2Package.APPLICATION_TYPE__EJB:
        case Xpdl2Package.APPLICATION_TYPE__POJO:
        case Xpdl2Package.APPLICATION_TYPE__XSLT:
        case Xpdl2Package.APPLICATION_TYPE__SCRIPT:
        case Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE:
        case Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE:
        case Xpdl2Package.APPLICATION_TYPE__FORM:
        case Xpdl2Package.APPLICATION_TYPE__ANY_ATTRIBUTE:
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

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.APPLICATION_TYPE__EJB,
                        Xpdl2Factory.eINSTANCE.createEjbApplication()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.APPLICATION_TYPE__POJO,
                        Xpdl2Factory.eINSTANCE.createPojoApplication()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.APPLICATION_TYPE__XSLT,
                        Xpdl2Factory.eINSTANCE.createXsltApplication()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.APPLICATION_TYPE__SCRIPT,
                        Xpdl2Factory.eINSTANCE.createScript()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.APPLICATION_TYPE__WEB_SERVICE,
                        Xpdl2Factory.eINSTANCE.createWebServiceApplication()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.APPLICATION_TYPE__BUSINESS_RULE,
                        Xpdl2Factory.eINSTANCE.createBusinessRuleApplication()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.APPLICATION_TYPE__FORM,
                        Xpdl2Factory.eINSTANCE.createFormApplication()));
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
