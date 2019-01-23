/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.provider;

import com.tibco.simulation.report.SimRepDistribution;
import com.tibco.simulation.report.SimRepPackage;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

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
 * This is the item provider adapter for a {@link com.tibco.simulation.report.SimRepDistribution} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SimRepDistributionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
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
    public SimRepDistributionItemProvider(AdapterFactory adapterFactory) {
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

            addLastResetTimePropertyDescriptor(object);
            addObservedElementsPropertyDescriptor(object);
            addDistributionCategoryPropertyDescriptor(object);
            addParameter1PropertyDescriptor(object);
            addParameter2PropertyDescriptor(object);
            addSeedPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Last Reset Time feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addLastResetTimePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepDistribution_lastResetTime_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepDistribution_lastResetTime_feature", "_UI_SimRepDistribution_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_DISTRIBUTION__LAST_RESET_TIME,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Observed Elements feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addObservedElementsPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepDistribution_observedElements_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepDistribution_observedElements_feature", "_UI_SimRepDistribution_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Distribution Category feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDistributionCategoryPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepDistribution_distributionCategory_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepDistribution_distributionCategory_feature", "_UI_SimRepDistribution_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Parameter1 feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addParameter1PropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepDistribution_parameter1_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepDistribution_parameter1_feature", "_UI_SimRepDistribution_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_DISTRIBUTION__PARAMETER1,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Parameter2 feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addParameter2PropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepDistribution_parameter2_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepDistribution_parameter2_feature", "_UI_SimRepDistribution_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_DISTRIBUTION__PARAMETER2,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Seed feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addSeedPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepDistribution_seed_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepDistribution_seed_feature", "_UI_SimRepDistribution_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.Literals.SIM_REP_DISTRIBUTION__SEED,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This returns SimRepDistribution.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object,
                getResourceLocator().getImage("full/obj16/SimRepDistribution")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        SimRepDistribution simRepDistribution = (SimRepDistribution) object;
        return getString("_UI_SimRepDistribution_type") + " " + simRepDistribution.getLastResetTime(); //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(SimRepDistribution.class)) {
        case SimRepPackage.SIM_REP_DISTRIBUTION__LAST_RESET_TIME:
        case SimRepPackage.SIM_REP_DISTRIBUTION__OBSERVED_ELEMENTS:
        case SimRepPackage.SIM_REP_DISTRIBUTION__DISTRIBUTION_CATEGORY:
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER1:
        case SimRepPackage.SIM_REP_DISTRIBUTION__PARAMETER2:
        case SimRepPackage.SIM_REP_DISTRIBUTION__SEED:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
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
