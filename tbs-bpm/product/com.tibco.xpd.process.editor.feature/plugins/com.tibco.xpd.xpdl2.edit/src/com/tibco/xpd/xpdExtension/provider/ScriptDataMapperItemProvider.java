/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.util.FeatureMapUtil;

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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.ScriptDataMapper} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ScriptDataMapperItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
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
	public ScriptDataMapperItemProvider(AdapterFactory adapterFactory)
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

			addMapperContextPropertyDescriptor(object);
			addMappingDirectionPropertyDescriptor(object);
			addExcludeEmptyOptionalObjectsPropertyDescriptor(object);
			addExcludeEmptyOptionalArraysPropertyDescriptor(object);
			addExcludeEmptyObjectsFromArraysPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Mapper Context feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMapperContextPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ScriptDataMapper_mapperContext_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ScriptDataMapper_mapperContext_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ScriptDataMapper_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__MAPPER_CONTEXT, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Mapping Direction feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addMappingDirectionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ScriptDataMapper_mappingDirection_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ScriptDataMapper_mappingDirection_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ScriptDataMapper_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__MAPPING_DIRECTION, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Exclude Empty Optional Objects feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExcludeEmptyOptionalObjectsPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ScriptDataMapper_excludeEmptyOptionalObjects_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
						"_UI_ScriptDataMapper_excludeEmptyOptionalObjects_feature", "_UI_ScriptDataMapper_type"), //$NON-NLS-1$ //$NON-NLS-2$
				XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_OBJECTS, true, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Exclude Empty Optional Arrays feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExcludeEmptyOptionalArraysPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ScriptDataMapper_excludeEmptyOptionalArrays_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ScriptDataMapper_excludeEmptyOptionalArrays_feature", "_UI_ScriptDataMapper_type"), //$NON-NLS-1$ //$NON-NLS-2$
						XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_ARRAYS, true, false,
						false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Exclude Empty Objects From Arrays feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addExcludeEmptyObjectsFromArraysPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ScriptDataMapper_excludeEmptyObjectsFromArrays_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
						"_UI_ScriptDataMapper_excludeEmptyObjectsFromArrays_feature", "_UI_ScriptDataMapper_type"), //$NON-NLS-1$ //$NON-NLS-2$
				XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS, true, false, false,
				ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
	public Collection< ? extends EStructuralFeature> getChildrenFeatures(Object object)
	{
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS);
			childrenFeatures.add(Xpdl2Package.Literals.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES);
			childrenFeatures.add(XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__DATA_MAPPINGS);
			childrenFeatures.add(XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS);
			childrenFeatures.add(XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child)
	{
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ScriptDataMapper.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ScriptDataMapper")); //$NON-NLS-1$
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
		String label = ((ScriptDataMapper) object).getMapperContext();
		return label == null || label.length() == 0 ? getString("_UI_ScriptDataMapper_type") : //$NON-NLS-1$
				getString("_UI_ScriptDataMapper_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ScriptDataMapper.class))
		{
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPER_CONTEXT:
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__MAPPING_DIRECTION:
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_OBJECTS:
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OPTIONAL_ARRAYS:
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__EXCLUDE_EMPTY_OBJECTS_FROM_ARRAYS:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ELEMENTS:
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__OTHER_ATTRIBUTES:
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__DATA_MAPPINGS:
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS:
			case XpdExtensionPackage.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
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

		newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS,
				FeatureMapUtil.createEntry(XpdExtensionPackage.Literals.DOCUMENT_ROOT__AUDIT,
						XpdExtensionFactory.eINSTANCE.createAudit())));

		newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS,
				FeatureMapUtil.createEntry(XpdExtensionPackage.Literals.DOCUMENT_ROOT__SIGNAL_DATA,
						XpdExtensionFactory.eINSTANCE.createSignalData())));

		newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS,
				FeatureMapUtil.createEntry(XpdExtensionPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT,
						XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript())));

		newChildDescriptors.add(createChildParameter(XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__DATA_MAPPINGS,
				Xpdl2Factory.eINSTANCE.createDataMapping()));

		newChildDescriptors.add(createChildParameter(XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__UNMAPPED_SCRIPTS,
				XpdExtensionFactory.eINSTANCE.createScriptInformation()));

		newChildDescriptors
				.add(createChildParameter(XpdExtensionPackage.Literals.SCRIPT_DATA_MAPPER__ARRAY_INFLATION_TYPE,
						XpdExtensionFactory.eINSTANCE.createDataMapperArrayInflation()));
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
