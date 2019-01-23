/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.provider;

import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationFactory;
import com.tibco.xpd.simulation.SimulationPackage;

import java.math.BigInteger;

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
 * This is the item provider adapter for a {@link com.tibco.xpd.simulation.ParticipantSimulationDataType} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ParticipantSimulationDataTypeItemProvider extends
        ItemProviderAdapter implements IEditingDomainItemProvider,
        IStructuredItemContentProvider, ITreeItemContentProvider,
        IItemLabelProvider, IItemPropertySource {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParticipantSimulationDataTypeItemProvider(
            AdapterFactory adapterFactory) {
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

            addInstancesPropertyDescriptor(object);
            addSlaMinimumUtilisationPropertyDescriptor(object);
            addSlaMaximumUtilisationPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Instances feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addInstancesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ParticipantSimulationDataType_instances_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ParticipantSimulationDataType_instances_feature", "_UI_ParticipantSimulationDataType_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimulationPackage.Literals.PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Sla Minimum Utilisation feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addSlaMinimumUtilisationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ParticipantSimulationDataType_slaMinimumUtilisation_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ParticipantSimulationDataType_slaMinimumUtilisation_feature", "_UI_ParticipantSimulationDataType_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimulationPackage.Literals.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION,
                        true,
                        false,
                        false,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Sla Maximum Utilisation feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addSlaMaximumUtilisationPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_ParticipantSimulationDataType_slaMaximumUtilisation_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_ParticipantSimulationDataType_slaMaximumUtilisation_feature", "_UI_ParticipantSimulationDataType_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimulationPackage.Literals.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION,
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
                    .add(SimulationPackage.Literals.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST);
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
     * This returns ParticipantSimulationDataType.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator()
                .getImage("full/obj16/ParticipantSimulationDataType")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getText(Object object) {
        BigInteger labelValue =
                ((ParticipantSimulationDataType) object).getInstances();
        String label = labelValue == null ? null : labelValue.toString();
        return label == null || label.length() == 0 ? getString("_UI_ParticipantSimulationDataType_type") : //$NON-NLS-1$
                getString("_UI_ParticipantSimulationDataType_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(ParticipantSimulationDataType.class)) {
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__INSTANCES:
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MINIMUM_UTILISATION:
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__SLA_MAXIMUM_UTILISATION:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), false, true));
            return;
        case SimulationPackage.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST:
            fireNotifyChanged(new ViewerNotification(notification, notification
                    .getNotifier(), true, false));
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
                .add(createChildParameter(SimulationPackage.Literals.PARTICIPANT_SIMULATION_DATA_TYPE__TIME_UNIT_COST,
                        SimulationFactory.eINSTANCE.createTimeUnitCostType()));
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return SimulationExtensionsEditPlugin.INSTANCE;
    }

}
