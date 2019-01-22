/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.ImplementationType;
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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.EndEvent} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class EndEventItemProvider extends EventItemProvider implements
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
    public EndEventItemProvider(AdapterFactory adapterFactory) {
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

            addImplementationPropertyDescriptor(object);
            addResultPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Implementation feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addImplementationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_EndEvent_implementation_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_EndEvent_implementation_feature", "_UI_EndEvent_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        Xpdl2Package.Literals.END_EVENT__IMPLEMENTATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Result feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addResultPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_EndEvent_result_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_EndEvent_result_feature", "_UI_EndEvent_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        Xpdl2Package.Literals.END_EVENT__RESULT,
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
            childrenFeatures
                    .add(Xpdl2Package.Literals.END_EVENT__TRIGGER_RESULT_MESSAGE);
            childrenFeatures.add(Xpdl2Package.Literals.END_EVENT__RESULT_ERROR);
            childrenFeatures
                    .add(Xpdl2Package.Literals.END_EVENT__TRIGGER_RESULT_COMPENSATION);
            childrenFeatures
                    .add(Xpdl2Package.Literals.END_EVENT__TRIGGER_RESULT_SIGNAL);
            childrenFeatures
                    .add(Xpdl2Package.Literals.END_EVENT__RESULT_MULTIPLE);
            childrenFeatures
                    .add(Xpdl2Package.Literals.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK);
            childrenFeatures
                    .add(Xpdl2Package.Literals.END_EVENT__DEPRECATED_RESULT_COMPENSATION);
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
     * This returns EndEvent.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/EndEvent")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        ImplementationType labelValue = ((EndEvent) object).getImplementation();
        String label = labelValue == null ? null : labelValue.toString();
        return label == null || label.length() == 0 ? getString("_UI_EndEvent_type") : //$NON-NLS-1$
                getString("_UI_EndEvent_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(EndEvent.class)) {
        case Xpdl2Package.END_EVENT__IMPLEMENTATION:
        case Xpdl2Package.END_EVENT__RESULT:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_MESSAGE:
        case Xpdl2Package.END_EVENT__RESULT_ERROR:
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_COMPENSATION:
        case Xpdl2Package.END_EVENT__TRIGGER_RESULT_SIGNAL:
        case Xpdl2Package.END_EVENT__RESULT_MULTIPLE:
        case Xpdl2Package.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK:
        case Xpdl2Package.END_EVENT__DEPRECATED_RESULT_COMPENSATION:
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
                .add(createChildParameter(Xpdl2Package.Literals.END_EVENT__TRIGGER_RESULT_MESSAGE,
                        Xpdl2Factory.eINSTANCE.createTriggerResultMessage()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.END_EVENT__RESULT_ERROR,
                        Xpdl2Factory.eINSTANCE.createResultError()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.END_EVENT__TRIGGER_RESULT_COMPENSATION,
                        Xpdl2Factory.eINSTANCE
                                .createTriggerResultCompensation()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.END_EVENT__TRIGGER_RESULT_SIGNAL,
                        Xpdl2Factory.eINSTANCE.createTriggerResultSignal()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.END_EVENT__RESULT_MULTIPLE,
                        Xpdl2Factory.eINSTANCE.createResultMultiple()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.END_EVENT__DEPRECATED_TRIGGER_RESULT_LINK,
                        Xpdl2Factory.eINSTANCE.createTriggerResultLink()));

        newChildDescriptors
                .add(createChildParameter(Xpdl2Package.Literals.END_EVENT__DEPRECATED_RESULT_COMPENSATION,
                        Xpdl2Factory.eINSTANCE
                                .createDeprecatedResultCompensation()));
    }

}
