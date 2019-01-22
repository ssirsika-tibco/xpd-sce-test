/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.provider;

import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.SimRepFactory;
import com.tibco.simulation.report.SimRepPackage;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

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
 * This is the item provider adapter for a {@link com.tibco.simulation.report.SimRepActivity} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated NOT
 */
public class SimRepActivityItemProvider extends ItemProviderAdapter implements
        IEditingDomainItemProvider, IStructuredItemContentProvider,
        ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource,
        ITableItemLabelProvider {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    private static final String MONEY_FORMAT = "0.00"; //$NON-NLS-1$

    private static final String OTHER_FORMAT = "#######0.0000"; //$NON-NLS-1$

    private static final DecimalFormat money =
            getCurrentLocaleDecimalFormat(MONEY_FORMAT);

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
    public SimRepActivityItemProvider(AdapterFactory adapterFactory) {
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

            //addIdPropertyDescriptor(object);
            addNamePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
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
                        getString("_UI_SimRepActivity_id_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepActivity_id_feature", "_UI_SimRepActivity_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE.getSimRepActivity_Id(),
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
                        getString("_UI_SimRepActivity_name_feature"), //$NON-NLS-1$
                        getString("_UI_PropertyDescriptor_description", "_UI_SimRepActivity_name_feature", "_UI_SimRepActivity_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        SimRepPackage.eINSTANCE.getSimRepActivity_Name(),
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
                    .getSimRepActivity_DurationDistribution());
            childrenFeatures.add(SimRepPackage.eINSTANCE
                    .getSimRepActivity_ActivityQueue());
            childrenFeatures.add(SimRepPackage.eINSTANCE
                    .getSimRepActivity_ActivityCost());
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns SimRepActivity.gif.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object getImage(Object object) {
        return getResourceLocator().getImage("full/obj16/SimRepActivity"); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getText(Object object) {
        String label = ((SimRepActivity) object).getName();
        return label == null || label.length() == 0 ? getString("_UI_SimRepActivity_type") : //$NON-NLS-1$
                getString("_UI_SimRepActivity_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(SimRepActivity.class)) {
        case SimRepPackage.SIM_REP_ACTIVITY__ID:
        case SimRepPackage.SIM_REP_ACTIVITY__NAME:
            fireNotifyChanged(new ViewerNotification(notification,
                    notification.getNotifier(), false, true));
            return;
        case SimRepPackage.SIM_REP_ACTIVITY__DURATION_DISTRIBUTION:
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_QUEUE:
        case SimRepPackage.SIM_REP_ACTIVITY__ACTIVITY_COST:
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
                .getSimRepActivity_DurationDistribution(),
                SimRepFactory.eINSTANCE.createSimRepDistribution()));

        newChildDescriptors.add(createChildParameter(SimRepPackage.eINSTANCE
                .getSimRepActivity_ActivityQueue(), SimRepFactory.eINSTANCE
                .createSimRepActivityQueue()));

        newChildDescriptors.add(createChildParameter(SimRepPackage.eINSTANCE
                .getSimRepActivity_ActivityCost(), SimRepFactory.eINSTANCE
                .createSimRepCost()));
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public ResourceLocator getResourceLocator() {
        return SimulationReportEditPlugin.INSTANCE;
    }

    public String getColumnText(Object object, int columnIndex) {
        if (object instanceof SimRepActivity) {
            SimRepActivity activity = (SimRepActivity) object;
            if (columnIndex == 0) {
                return activity.getName();
            } else if (columnIndex == 1) {
                return "" + activity.getActivityQueue().getObservedCases(); //$NON-NLS-1$
            } else if (columnIndex == 2) {
                return "" + activity.getActivityQueue().getCurrentSize(); //$NON-NLS-1$
            } else if (columnIndex == 3) {
                return "" + activity.getActivityQueue().getMaxSize(); //$NON-NLS-1$
            } else if (columnIndex == 4) {
                return otherDF.format(activity.getActivityQueue()
                        .getAverageSize());
            } else if (columnIndex == 5) {
                return otherDF.format(activity.getActivityQueue()
                        .getAverageWait());
            } else if (columnIndex == 6) {
                return money
                        .format(activity.getActivityCost().getAverageCost());
            } else if (columnIndex == 7) {
                return money.format(activity.getActivityCost().getMinCost());
            } else if (columnIndex == 8) {
                return money.format(activity.getActivityCost().getMaxCost());
            } else if (columnIndex == 9) {
                return money.format(activity.getActivityCost()
                        .getCumulativeAverageCost());
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
