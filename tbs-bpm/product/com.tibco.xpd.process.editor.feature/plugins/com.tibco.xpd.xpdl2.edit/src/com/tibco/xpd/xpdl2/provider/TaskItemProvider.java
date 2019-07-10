/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.provider;

import com.tibco.xpd.xpdl2.Task;
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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdl2.Task} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TaskItemProvider extends ImplementationItemProvider {
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
    public TaskItemProvider(AdapterFactory adapterFactory) {
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
            childrenFeatures.add(Xpdl2Package.Literals.TASK__TASK_SERVICE);
            childrenFeatures.add(Xpdl2Package.Literals.TASK__TASK_RECEIVE);
            childrenFeatures.add(Xpdl2Package.Literals.TASK__TASK_MANUAL);
            childrenFeatures.add(Xpdl2Package.Literals.TASK__TASK_REFERENCE);
            childrenFeatures.add(Xpdl2Package.Literals.TASK__TASK_SCRIPT);
            childrenFeatures.add(Xpdl2Package.Literals.TASK__TASK_SEND);
            childrenFeatures.add(Xpdl2Package.Literals.TASK__TASK_USER);
            childrenFeatures.add(Xpdl2Package.Literals.TASK__TASK_APPLICATION);
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
     * This returns Task.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/Task")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_Task_type"); //$NON-NLS-1$
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

        switch (notification.getFeatureID(Task.class)) {
        case Xpdl2Package.TASK__TASK_SERVICE:
        case Xpdl2Package.TASK__TASK_RECEIVE:
        case Xpdl2Package.TASK__TASK_MANUAL:
        case Xpdl2Package.TASK__TASK_REFERENCE:
        case Xpdl2Package.TASK__TASK_SCRIPT:
        case Xpdl2Package.TASK__TASK_SEND:
        case Xpdl2Package.TASK__TASK_USER:
        case Xpdl2Package.TASK__TASK_APPLICATION:
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

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TASK__TASK_SERVICE,
                Xpdl2Factory.eINSTANCE.createTaskService()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TASK__TASK_RECEIVE,
                Xpdl2Factory.eINSTANCE.createTaskReceive()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TASK__TASK_MANUAL,
                Xpdl2Factory.eINSTANCE.createTaskManual()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TASK__TASK_REFERENCE,
                Xpdl2Factory.eINSTANCE.createTaskReference()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TASK__TASK_SCRIPT,
                Xpdl2Factory.eINSTANCE.createTaskScript()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.Literals.TASK__TASK_SEND, Xpdl2Factory.eINSTANCE.createTaskSend()));

        newChildDescriptors.add(
                createChildParameter(Xpdl2Package.Literals.TASK__TASK_USER, Xpdl2Factory.eINSTANCE.createTaskUser()));

        newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.TASK__TASK_APPLICATION,
                Xpdl2Factory.eINSTANCE.createTaskApplication()));
    }

}
