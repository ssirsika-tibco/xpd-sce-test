/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.provider;

import com.tibco.simulation.report.SimRepCases;
import com.tibco.simulation.report.SimRepFactory;
import com.tibco.simulation.report.SimRepPackage;

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
 * This is the item provider adapter for a {@link com.tibco.simulation.report.SimRepCases} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SimRepCasesItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepCasesItemProvider(AdapterFactory adapterFactory) {
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

            addStartedCasesPropertyDescriptor(object);
            addFinishedCasesPropertyDescriptor(object);
            addAverageCaseTimePropertyDescriptor(object);
            addMinCaseTimePropertyDescriptor(object);
            addMaxCaseTimePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Started Cases feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addStartedCasesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCases_startedCases_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCases_startedCases_feature", "_UI_SimRepCases_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_CASES__STARTED_CASES,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Finished Cases feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addFinishedCasesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCases_finishedCases_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCases_finishedCases_feature", "_UI_SimRepCases_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_CASES__FINISHED_CASES,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Average Case Time feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addAverageCaseTimePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCases_averageCaseTime_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCases_averageCaseTime_feature", "_UI_SimRepCases_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_CASES__AVERAGE_CASE_TIME,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Min Case Time feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addMinCaseTimePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCases_minCaseTime_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCases_minCaseTime_feature", "_UI_SimRepCases_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_CASES__MIN_CASE_TIME,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Max Case Time feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addMaxCaseTimePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCases_maxCaseTime_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCases_maxCaseTime_feature", "_UI_SimRepCases_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_CASES__MAX_CASE_TIME,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
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
                    .add(SimRepPackage.Literals.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION);
            childrenFeatures
                    .add(SimRepPackage.Literals.SIM_REP_CASES__CASE_COST);
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
     * This returns SimRepCases.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/SimRepCases")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public String getText(Object object) {
        return getString("_UI_SimRepCases_type"); //$NON-NLS-1$ 
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

        switch (notification.getFeatureID(SimRepCases.class)) {
        case SimRepPackage.SIM_REP_CASES__STARTED_CASES:
        case SimRepPackage.SIM_REP_CASES__FINISHED_CASES:
        case SimRepPackage.SIM_REP_CASES__AVERAGE_CASE_TIME:
        case SimRepPackage.SIM_REP_CASES__MIN_CASE_TIME:
        case SimRepPackage.SIM_REP_CASES__MAX_CASE_TIME:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case SimRepPackage.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION:
        case SimRepPackage.SIM_REP_CASES__CASE_COST:
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
                .add(createChildParameter(SimRepPackage.Literals.SIM_REP_CASES__CASE_START_INTERVAL_DISTRIBUTION,
                        SimRepFactory.eINSTANCE.createSimRepDistribution()));

        newChildDescriptors
                .add(createChildParameter(SimRepPackage.Literals.SIM_REP_CASES__CASE_COST,
                        SimRepFactory.eINSTANCE.createSimRepCost()));
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return SimulationReportEditPlugin.INSTANCE;
    }

}
