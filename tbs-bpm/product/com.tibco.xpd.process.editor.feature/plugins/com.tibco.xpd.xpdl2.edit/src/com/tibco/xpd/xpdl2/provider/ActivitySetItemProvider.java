/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.CommandsUtils;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.ActivitySet} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ActivitySetItemProvider extends NamedElementItemProvider {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ActivitySetItemProvider(AdapterFactory adapterFactory) {
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

            addAdHocPropertyDescriptor(object);
            addAdHocCompletionConditionPropertyDescriptor(object);
            addAdHocOrderingPropertyDescriptor(object);
            addDefaultStartActivityIdPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Ad Hoc feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAdHocPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FlowContainer_adHoc_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FlowContainer_adHoc_feature", //$NON-NLS-1$
                                "_UI_FlowContainer_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FLOW_CONTAINER__AD_HOC,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Ad Hoc Completion Condition feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addAdHocCompletionConditionPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FlowContainer_adHocCompletionCondition_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FlowContainer_adHocCompletionCondition_feature", //$NON-NLS-1$
                                "_UI_FlowContainer_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FLOW_CONTAINER__AD_HOC_COMPLETION_CONDITION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Ad Hoc Ordering feature. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected void addAdHocOrderingPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FlowContainer_adHocOrdering_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FlowContainer_adHocOrdering_feature", //$NON-NLS-1$
                                "_UI_FlowContainer_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FLOW_CONTAINER__AD_HOC_ORDERING,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Default Start Activity Id feature.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected void addDefaultStartActivityIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_FlowContainer_defaultStartActivityId_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
                                "_UI_FlowContainer_defaultStartActivityId_feature", //$NON-NLS-1$
                                "_UI_FlowContainer_type"), //$NON-NLS-1$
                        Xpdl2Package.Literals.FLOW_CONTAINER__DEFAULT_START_ACTIVITY_ID,
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
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(Xpdl2Package.Literals.FLOW_CONTAINER__ACTIVITIES);
            childrenFeatures.add(Xpdl2Package.Literals.FLOW_CONTAINER__TRANSITIONS);
            childrenFeatures.add(Xpdl2Package.Literals.ACTIVITY_SET__OBJECT);
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
     * This returns ActivitySet.gif. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/ActivitySet")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getText(Object object) {
        return super.getText(object);
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

        switch (notification.getFeatureID(ActivitySet.class)) {
        case Xpdl2Package.ACTIVITY_SET__AD_HOC:
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_COMPLETION_CONDITION:
        case Xpdl2Package.ACTIVITY_SET__AD_HOC_ORDERING:
        case Xpdl2Package.ACTIVITY_SET__DEFAULT_START_ACTIVITY_ID:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
            return;
        case Xpdl2Package.ACTIVITY_SET__ACTIVITIES:
        case Xpdl2Package.ACTIVITY_SET__TRANSITIONS:
        case Xpdl2Package.ACTIVITY_SET__OBJECT:
            fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.FLOW_CONTAINER__ACTIVITIES,
                Xpdl2Factory.eINSTANCE.createActivity()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.FLOW_CONTAINER__TRANSITIONS,
                Xpdl2Factory.eINSTANCE.createTransition()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.ACTIVITY_SET__OBJECT,
                Xpdl2Factory.eINSTANCE.createObject()));
    }

    @Override
    protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection, int index) {
        Command cmd = super.createAddCommand(domain, owner, feature, collection, index);
        cmd = CommandsUtils.checkExternalAddWrappers(cmd, domain, owner, feature, collection, index);
        return cmd;
    }

    @Override
    protected Command createRemoveCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
            Collection collection) {
        Command cmd = super.createRemoveCommand(domain, owner, feature, collection);
        cmd = CommandsUtils.checkExternalDeleteWrappers(cmd, domain, owner, feature, collection);
        return cmd;
    }

    @Override
    protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value,
            int index) {
        Command cmd = super.createSetCommand(domain, owner, feature, value, index);
        cmd = CommandsUtils.checkExternalSetWrappers(cmd, domain, owner, feature, value, index);
        return cmd;
    }

}
