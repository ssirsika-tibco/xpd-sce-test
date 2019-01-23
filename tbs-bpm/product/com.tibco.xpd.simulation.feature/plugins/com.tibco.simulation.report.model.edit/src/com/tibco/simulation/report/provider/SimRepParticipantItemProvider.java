/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.provider;

import com.tibco.simulation.report.SimRepFactory;
import com.tibco.simulation.report.SimRepPackage;
import com.tibco.simulation.report.SimRepParticipant;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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
 * This is the item provider adapter for a {@link com.tibco.simulation.report.SimRepParticipant} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated NOT
 */
public class SimRepParticipantItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource,
        ITableItemLabelProvider {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    private static final String OTHER_FORMAT = "#######0.0000"; //$NON-NLS-1$         

    private static final DecimalFormat otherDF =
            getCurrentLocaleDecimalFormat(OTHER_FORMAT);

    /**
     * Returns Decimal Formatter in current Locale 
     * @param format
     * @return
     */
    public static DecimalFormat getCurrentLocaleDecimalFormat(String format) {
        DecimalFormat f =
                (DecimalFormat) DecimalFormat.getInstance(DEFAULT_LOCALE);
        f.applyPattern(format);
        return f;
    }

    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepParticipantItemProvider(AdapterFactory adapterFactory) {
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

            addNoOfInstancesPropertyDescriptor(object);
            addIdleInstancesPropertyDescriptor(object);
            addAverageIdlePropertyDescriptor(object);
            addAverageIdleTimePropertyDescriptor(object);
            addIdPropertyDescriptor(object);
            addNamePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the No Of Instances feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addNoOfInstancesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepParticipant_noOfInstances_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepParticipant_noOfInstances_feature", "_UI_SimRepParticipant_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepParticipant_NoOfInstances(),
                        true,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Idle Instances feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addIdleInstancesPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepParticipant_idleInstances_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepParticipant_idleInstances_feature", "_UI_SimRepParticipant_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepParticipant_IdleInstances(),
                        true,
                        ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Average Idle feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addAverageIdlePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepParticipant_averageIdle_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepParticipant_averageIdle_feature", "_UI_SimRepParticipant_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepParticipant_AverageIdle(),
                        true,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Average Idle Time feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addAverageIdleTimePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepParticipant_averageIdleTime_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepParticipant_averageIdleTime_feature", "_UI_SimRepParticipant_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE
                                .getSimRepParticipant_AverageIdleTime(),
                        true,
                        ItemPropertyDescriptor.REAL_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Id feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addIdPropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepParticipant_id_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepParticipant_id_feature", "_UI_SimRepParticipant_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE.getSimRepParticipant_Id(),
                        true,
                        ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                        null,
                        null));
    }

    /**
     * This adds a property descriptor for the Name feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors
                .add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory)
                        .getRootAdapterFactory(),
                        getResourceLocator(),
                        getString("_UI_SimRepParticipant_name_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepParticipant_name_feature", "_UI_SimRepParticipant_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE.getSimRepParticipant_Name(),
                        true,
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
    public Collection getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(SimRepPackage.eINSTANCE
                    .getSimRepParticipant_CostOfWorkForCase());
        }
        return childrenFeatures;
    }

    /**
     * This returns SimRepParticipant.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getImage(Object object) {
        return getResourceLocator().getImage("full/obj16/SimRepParticipant"); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getText(Object object) {
        String label = ((SimRepParticipant) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_SimRepParticipant_type") : //$NON-NLS-1$
                getString("_UI_SimRepParticipant_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(SimRepParticipant.class)) {
        case SimRepPackage.SIM_REP_PARTICIPANT__NO_OF_INSTANCES:
        case SimRepPackage.SIM_REP_PARTICIPANT__IDLE_INSTANCES:
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE:
        case SimRepPackage.SIM_REP_PARTICIPANT__AVERAGE_IDLE_TIME:
        case SimRepPackage.SIM_REP_PARTICIPANT__ID:
        case SimRepPackage.SIM_REP_PARTICIPANT__NAME:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case SimRepPackage.SIM_REP_PARTICIPANT__COST_OF_WORK_FOR_CASE:
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
                .getSimRepParticipant_CostOfWorkForCase(),
                SimRepFactory.eINSTANCE.createSimRepCost()));
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
        if (object instanceof SimRepParticipant) {
            SimRepParticipant participant = (SimRepParticipant) object;
            if (columnIndex == 0) {
                return participant.getName();
            } else if (columnIndex == 1) {
                return "" + participant.getNoOfInstances(); //$NON-NLS-1$
            } else if (columnIndex == 2) {
                return "" + participant.getIdleInstances(); //$NON-NLS-1$
            } else if (columnIndex == 3) {
                return otherDF.format(participant.getAverageIdle());
            } else if (columnIndex == 4) {
                return otherDF.format(participant.getAverageIdleTime());
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
