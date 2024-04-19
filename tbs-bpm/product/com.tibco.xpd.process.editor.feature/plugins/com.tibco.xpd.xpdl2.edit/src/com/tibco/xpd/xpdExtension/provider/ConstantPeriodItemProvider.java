/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.math.BigInteger;

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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.ConstantPeriod} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ConstantPeriodItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstantPeriodItemProvider(AdapterFactory adapterFactory)
	{
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
	{
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addDaysPropertyDescriptor(object);
			addHoursPropertyDescriptor(object);
			addMicroSecondsPropertyDescriptor(object);
			addMinutesPropertyDescriptor(object);
			addMonthsPropertyDescriptor(object);
			addSecondsPropertyDescriptor(object);
			addWeeksPropertyDescriptor(object);
			addYearsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Days feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDaysPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ConstantPeriod_days_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ConstantPeriod_days_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ConstantPeriod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.CONSTANT_PERIOD__DAYS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Hours feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addHoursPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ConstantPeriod_hours_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ConstantPeriod_hours_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ConstantPeriod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.CONSTANT_PERIOD__HOURS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Micro Seconds feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMicroSecondsPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ConstantPeriod_microSeconds_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ConstantPeriod_microSeconds_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ConstantPeriod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.CONSTANT_PERIOD__MICRO_SECONDS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Minutes feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMinutesPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ConstantPeriod_minutes_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ConstantPeriod_minutes_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ConstantPeriod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.CONSTANT_PERIOD__MINUTES, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Months feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMonthsPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ConstantPeriod_months_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ConstantPeriod_months_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ConstantPeriod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.CONSTANT_PERIOD__MONTHS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Seconds feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addSecondsPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ConstantPeriod_seconds_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ConstantPeriod_seconds_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ConstantPeriod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.CONSTANT_PERIOD__SECONDS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Weeks feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addWeeksPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ConstantPeriod_weeks_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ConstantPeriod_weeks_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ConstantPeriod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.CONSTANT_PERIOD__WEEKS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Years feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addYearsPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ConstantPeriod_years_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ConstantPeriod_years_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ConstantPeriod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.CONSTANT_PERIOD__YEARS, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This returns ConstantPeriod.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ConstantPeriod")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object)
	{
		BigInteger labelValue = ((ConstantPeriod) object).getDays();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ? getString("_UI_ConstantPeriod_type") : //$NON-NLS-1$
				getString("_UI_ConstantPeriod_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification)
	{
		updateChildren(notification);

		switch (notification.getFeatureID(ConstantPeriod.class))
		{
			case XpdExtensionPackage.CONSTANT_PERIOD__DAYS:
			case XpdExtensionPackage.CONSTANT_PERIOD__HOURS:
			case XpdExtensionPackage.CONSTANT_PERIOD__MICRO_SECONDS:
			case XpdExtensionPackage.CONSTANT_PERIOD__MINUTES:
			case XpdExtensionPackage.CONSTANT_PERIOD__MONTHS:
			case XpdExtensionPackage.CONSTANT_PERIOD__SECONDS:
			case XpdExtensionPackage.CONSTANT_PERIOD__WEEKS:
			case XpdExtensionPackage.CONSTANT_PERIOD__YEARS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
	{
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		return XpdExtensionEditPlugin.INSTANCE;
	}

}
