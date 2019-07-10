/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import com.tibco.xpd.xpdl2.TriggerMultiple;
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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.TriggerMultiple} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TriggerMultipleItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
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
    public TriggerMultipleItemProvider(AdapterFactory adapterFactory) {
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
            childrenFeatures.add(Xpdl2Package.Literals.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES);
            childrenFeatures.add(Xpdl2Package.Literals.TRIGGER_MULTIPLE__TRIGGER_RESULT_MESSAGE);
            childrenFeatures.add(Xpdl2Package.Literals.TRIGGER_MULTIPLE__TRIGGER_TIMER);
            childrenFeatures.add(Xpdl2Package.Literals.TRIGGER_MULTIPLE__TRIGGER_CONDITIONAL);
            childrenFeatures.add(Xpdl2Package.Literals.TRIGGER_MULTIPLE__TRIGGER_RESULT_LINK);
            childrenFeatures.add(Xpdl2Package.Literals.TRIGGER_MULTIPLE__DEPRECATED_TRIGGER_RULE);
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
     * This returns TriggerMultiple.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/TriggerMultiple")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_TriggerMultiple_type"); //$NON-NLS-1$
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

        switch (notification.getFeatureID(TriggerMultiple.class)) {
        case Xpdl2Package.TRIGGER_MULTIPLE__OTHER_ATTRIBUTES:
        case Xpdl2Package.TRIGGER_MULTIPLE__TRIGGER_RESULT_MESSAGE:
        case Xpdl2Package.TRIGGER_MULTIPLE__TRIGGER_TIMER:
        case Xpdl2Package.TRIGGER_MULTIPLE__TRIGGER_CONDITIONAL:
        case Xpdl2Package.TRIGGER_MULTIPLE__TRIGGER_RESULT_LINK:
        case Xpdl2Package.TRIGGER_MULTIPLE__DEPRECATED_TRIGGER_RULE:
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

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TRIGGER_MULTIPLE__TRIGGER_RESULT_MESSAGE,
                Xpdl2Factory.eINSTANCE.createTriggerResultMessage()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TRIGGER_MULTIPLE__TRIGGER_TIMER,
                Xpdl2Factory.eINSTANCE.createTriggerTimer()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TRIGGER_MULTIPLE__TRIGGER_CONDITIONAL,
                Xpdl2Factory.eINSTANCE.createTriggerConditional()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TRIGGER_MULTIPLE__TRIGGER_RESULT_LINK,
                Xpdl2Factory.eINSTANCE.createTriggerResultLink()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TRIGGER_MULTIPLE__DEPRECATED_TRIGGER_RULE,
                Xpdl2Factory.eINSTANCE.createDeprecatedTriggerRule()));
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
