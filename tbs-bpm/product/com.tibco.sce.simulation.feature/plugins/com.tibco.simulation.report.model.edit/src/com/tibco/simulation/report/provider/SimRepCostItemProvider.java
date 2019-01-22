/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.provider;

import com.tibco.simulation.report.SimRepCost;
import com.tibco.simulation.report.SimRepPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link com.tibco.simulation.report.SimRepCost} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated NOT
 */
public class SimRepCostItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource,
        ITableItemLabelProvider {
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
    public SimRepCostItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public List getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addAverageCostPropertyDescriptor(object);
            addMinCostPropertyDescriptor(object);
            addMaxCostPropertyDescriptor(object);
            addCumulativeAverageCostPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Average Cost feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
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
     * @generated
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
     * @generated
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
     * @generated
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
     * This returns SimRepCost.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getImage(Object object) {
        return getResourceLocator().getImage("full/obj16/SimRepCost"); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public String getText(Object object) {
        return getString("_UI_SimRepCost_type"); //$NON-NLS-1$ 
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

        switch (notification.getFeatureID(SimRepCost.class)) {
        case SimRepPackage.SIM_REP_COST__AVERAGE_COST:
        case SimRepPackage.SIM_REP_COST__MIN_COST:
        case SimRepPackage.SIM_REP_COST__MAX_COST:
        case SimRepPackage.SIM_REP_COST__CUMULATIVE_AVERAGE_COST:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
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

    public String getColumnText(Object object, int columnIndex) {
        if (object instanceof SimRepCost) {
            SimRepCost cost = (SimRepCost) object;
            if (columnIndex == 0) {
                return "" + cost.getAverageCost(); //$NON-NLS-1$
            } else if (columnIndex == 1) {
                return "" + cost.getMinCost(); //$NON-NLS-1$
            } else if (columnIndex == 2) {
                return "" + cost.getMaxCost(); //$NON-NLS-1$
            } else if (columnIndex == 3) {
                return "" + cost.getCumulativeAverageCost(); //$NON-NLS-1$
            }
        }
        return null;
    }

    public Object getColumnImage(Object object, int columnIndex) {
        if (columnIndex == 0) {
            return getImage(object);
        }
        return null;
    }
}
