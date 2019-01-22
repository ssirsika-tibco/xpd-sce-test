/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.provider;

import com.tibco.simulation.report.SimRepActivities;
import com.tibco.simulation.report.SimRepFactory;
import com.tibco.simulation.report.SimRepPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link com.tibco.simulation.report.SimRepActivities} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated NOT
 */
public class SimRepActivitiesItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider {
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
    public SimRepActivitiesItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public List getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);
            addNamePropertyDescriptor(object);
            addObservedCasesPropertyDescriptor(object);
            addCurrentSizePropertyDescriptor(object);
            addMaxSizePropertyDescriptor(object);
            addAverageSizePropertyDescriptor(object);
            addAverageWaitPropertyDescriptor(object);
            addAverageCostPropertyDescriptor(object);
            addMinCostPropertyDescriptor(object);
            addMaxCostPropertyDescriptor(object);
            addCumulativeAverageCostPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepActivity_name_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepActivity_name_feature", "_UI_SimRepActivity_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE.getSimRepActivity_Name(),
                        true,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Observed Cases feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addObservedCasesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepActivityQueue_observedCases_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepActivityQueue_observedCases_feature", "_UI_SimRepActivityQueue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepActivityQueue_ObservedCases(),
                        true,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Current Size feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    protected void addCurrentSizePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepActivityQueue_currentSize_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepActivityQueue_currentSize_feature", "_UI_SimRepActivityQueue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepActivityQueue_CurrentSize(),
                        true,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Max Size feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addMaxSizePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepActivityQueue_maxSize_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepActivityQueue_maxSize_feature", "_UI_SimRepActivityQueue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepActivityQueue_MaxSize(),
                        true,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Average Size feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addAverageSizePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepActivityQueue_averageSize_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepActivityQueue_averageSize_feature", "_UI_SimRepActivityQueue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepActivityQueue_AverageSize(),
                        true,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Average Wait feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addAverageWaitPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepActivityQueue_averageWait_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepActivityQueue_averageWait_feature", "_UI_SimRepActivityQueue_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepActivityQueue_AverageWait(),
                        true,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Average Cost feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addAverageCostPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCost_averageCost_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCost_averageCost_feature", "_UI_SimRepCost_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE.getSimRepCost_AverageCost(),
                        true,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Min Cost feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addMinCostPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCost_minCost_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCost_minCost_feature", "_UI_SimRepCost_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE.getSimRepCost_MinCost(),
                        true,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Max Cost feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addMaxCostPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCost_maxCost_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCost_maxCost_feature", "_UI_SimRepCost_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE.getSimRepCost_MaxCost(),
                        true,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Cumulative Average Cost feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    protected void addCumulativeAverageCostPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepCost_cumulativeAverageCost_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepCost_cumulativeAverageCost_feature", "_UI_SimRepCost_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepCost_CumulativeAverageCost(),
                        true,
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
    public Collection getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(SimRepPackage.eINSTANCE
                    .getSimRepActivities_Activity());
        }
        return childrenFeatures;
    }

    /**
     * This returns SimRepActivities.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getImage(Object object) {
        return getResourceLocator().getImage("full/obj16/SimRepActivities"); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getText(Object object) {
        return getString("_UI_SimRepActivities_type"); //$NON-NLS-1$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(SimRepActivities.class)) {
        case SimRepPackage.SIM_REP_ACTIVITIES__ACTIVITY:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), true, false));
            return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds to the collection of {@link org.eclipse.emf.edit.command.CommandParameter}s
     * describing all of the children that can be created under this object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void collectNewChildDescriptors(Collection newChildDescriptors,
            Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(createChildParameter(SimRepPackage.eINSTANCE
                .getSimRepActivities_Activity(), SimRepFactory.eINSTANCE
                .createSimRepActivity()));
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceLocator getResourceLocator() {
        return SimulationReportEditPlugin.INSTANCE;
    }

}
