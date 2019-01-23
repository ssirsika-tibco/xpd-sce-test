/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.DurationCalculation;
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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.DurationCalculation} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DurationCalculationItemProvider extends ItemProviderAdapter
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
    public DurationCalculationItemProvider(AdapterFactory adapterFactory) {
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
                    XpdExtensionPackage.Literals.DURATION_CALCULATION__YEARS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DURATION_CALCULATION__MONTHS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DURATION_CALCULATION__WEEKS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DURATION_CALCULATION__DAYS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DURATION_CALCULATION__HOURS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DURATION_CALCULATION__MINUTES);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DURATION_CALCULATION__SECONDS);
            childrenFeatures.add(
                    XpdExtensionPackage.Literals.DURATION_CALCULATION__MICROSECONDS);
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
     * This returns DurationCalculation.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator()
                        .getImage("full/obj16/DurationCalculation")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        return getString("_UI_DurationCalculation_type"); //$NON-NLS-1$
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

        switch (notification.getFeatureID(DurationCalculation.class)) {
        case XpdExtensionPackage.DURATION_CALCULATION__YEARS:
        case XpdExtensionPackage.DURATION_CALCULATION__MONTHS:
        case XpdExtensionPackage.DURATION_CALCULATION__WEEKS:
        case XpdExtensionPackage.DURATION_CALCULATION__DAYS:
        case XpdExtensionPackage.DURATION_CALCULATION__HOURS:
        case XpdExtensionPackage.DURATION_CALCULATION__MINUTES:
        case XpdExtensionPackage.DURATION_CALCULATION__SECONDS:
        case XpdExtensionPackage.DURATION_CALCULATION__MICROSECONDS:
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
                XpdExtensionPackage.Literals.DURATION_CALCULATION__YEARS,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__YEARS,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__MONTHS,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__MONTHS,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__WEEKS,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__WEEKS,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__DAYS,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__DAYS,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__HOURS,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__HOURS,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__MINUTES,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__MINUTES,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__SECONDS,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__SECONDS,
                Xpdl2Factory.eINSTANCE.createExpression()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__MICROSECONDS,
                XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript()));

        newChildDescriptors.add(createChildParameter(
                XpdExtensionPackage.Literals.DURATION_CALCULATION__MICROSECONDS,
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
                childFeature == XpdExtensionPackage.Literals.DURATION_CALCULATION__YEARS
                        || childFeature == XpdExtensionPackage.Literals.DURATION_CALCULATION__MONTHS
                        || childFeature == XpdExtensionPackage.Literals.DURATION_CALCULATION__WEEKS
                        || childFeature == XpdExtensionPackage.Literals.DURATION_CALCULATION__DAYS
                        || childFeature == XpdExtensionPackage.Literals.DURATION_CALCULATION__HOURS
                        || childFeature == XpdExtensionPackage.Literals.DURATION_CALCULATION__MINUTES
                        || childFeature == XpdExtensionPackage.Literals.DURATION_CALCULATION__SECONDS
                        || childFeature == XpdExtensionPackage.Literals.DURATION_CALCULATION__MICROSECONDS;

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
