/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Xpdl2Factory;

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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.UserTaskScripts} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class UserTaskScriptsItemProvider extends ItemProviderAdapter
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
    public UserTaskScriptsItemProvider(AdapterFactory adapterFactory) {
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
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__OPEN_SCRIPT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__CLOSE_SCRIPT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__SUBMIT_SCRIPT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT);
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
     * This returns UserTaskScripts.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/UserTaskScripts")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_UserTaskScripts_type"); //$NON-NLS-1$
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

        switch (notification.getFeatureID(UserTaskScripts.class)) {
        case XpdExtensionPackage.USER_TASK_SCRIPTS__OPEN_SCRIPT:
        case XpdExtensionPackage.USER_TASK_SCRIPTS__CLOSE_SCRIPT:
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SUBMIT_SCRIPT:
        case XpdExtensionPackage.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT:
        case XpdExtensionPackage.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT:
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
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__OPEN_SCRIPT,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__OPEN_SCRIPT,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__CLOSE_SCRIPT,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__CLOSE_SCRIPT,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__SUBMIT_SCRIPT,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__SUBMIT_SCRIPT,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT,
                Xpdl2Factory.eINSTANCE.createExpression()));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child,
            Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify =
                childFeature == XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__OPEN_SCRIPT
                        || childFeature == XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__CLOSE_SCRIPT
                        || childFeature == XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__SUBMIT_SCRIPT
                        || childFeature == XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__SCHEDULE_SCRIPT
                        || childFeature == XpdExtensionPackage.Literals.USER_TASK_SCRIPTS__RESCHEDULE_SCRIPT;

        if (qualify) {
            return getString("_UI_CreateChild_text2", //$NON-NLS-1$
                    new Object[] { getTypeText(childObject),
                            getFeatureText(childFeature), getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
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
