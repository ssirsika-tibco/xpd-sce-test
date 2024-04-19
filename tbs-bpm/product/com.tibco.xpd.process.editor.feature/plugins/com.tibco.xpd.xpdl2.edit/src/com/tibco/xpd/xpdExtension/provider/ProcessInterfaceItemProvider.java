/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.CommandsUtils;
import com.tibco.xpd.xpdl2.provider.NamedElementItemProvider;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is the item provider adapter for a
 * {@link com.tibco.xpd.xpdExtension.ProcessInterface} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProcessInterfaceItemProvider extends NamedElementItemProvider
{
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProcessInterfaceItemProvider(AdapterFactory adapterFactory)
	{
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
	{
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addDescriptionPropertyDescriptor(object);
			addXpdInterfaceTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Description feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_DescribedElement_description_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_DescribedElement_description_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_DescribedElement_type"), //$NON-NLS-1$
						Xpdl2Package.Literals.DESCRIBED_ELEMENT__DESCRIPTION, true, false, false, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Xpd Interface Type feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addXpdInterfaceTypePropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ProcessInterface_xpdInterfaceType_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ProcessInterface_xpdInterfaceType_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ProcessInterface_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.PROCESS_INTERFACE__XPD_INTERFACE_TYPE, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection< ? extends EStructuralFeature> getChildrenFeatures(Object object)
	{
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(Xpdl2Package.Literals.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES);
			childrenFeatures.add(Xpdl2Package.Literals.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS);
			childrenFeatures.add(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS);
			childrenFeatures.add(XpdExtensionPackage.Literals.PROCESS_INTERFACE__START_METHODS);
			childrenFeatures.add(XpdExtensionPackage.Literals.PROCESS_INTERFACE__INTERMEDIATE_METHODS);
			childrenFeatures.add(XpdExtensionPackage.Literals.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * This returns ProcessInterface.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Object getImage(Object object)
	{

		if (object instanceof ProcessInterface && Xpdl2ModelUtil.isServiceProcessInterface((ProcessInterface) object))
		{

			return overlayImage(object, getResourceLocator().getImage("full/obj16/ServiceProcessInterface.png")); //$NON-NLS-1$
		}
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ProcessInterface")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getText(Object object)
	{
		return super.getText(object);
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification)
	{
		updateChildren(notification);

		switch (notification.getFeatureID(ProcessInterface.class))
		{
			case XpdExtensionPackage.PROCESS_INTERFACE__DESCRIPTION:
			case XpdExtensionPackage.PROCESS_INTERFACE__XPD_INTERFACE_TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case XpdExtensionPackage.PROCESS_INTERFACE__EXTENDED_ATTRIBUTES:
			case XpdExtensionPackage.PROCESS_INTERFACE__FORMAL_PARAMETERS:
			case XpdExtensionPackage.PROCESS_INTERFACE__OTHER_ELEMENTS:
			case XpdExtensionPackage.PROCESS_INTERFACE__START_METHODS:
			case XpdExtensionPackage.PROCESS_INTERFACE__INTERMEDIATE_METHODS:
			case XpdExtensionPackage.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
	{
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors
				.add(createChildParameter(Xpdl2Package.Literals.EXTENDED_ATTRIBUTES_CONTAINER__EXTENDED_ATTRIBUTES,
						Xpdl2Factory.eINSTANCE.createExtendedAttribute()));

		newChildDescriptors
				.add(createChildParameter(Xpdl2Package.Literals.FORMAL_PARAMETERS_CONTAINER__FORMAL_PARAMETERS,
						Xpdl2Factory.eINSTANCE.createFormalParameter()));

		newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS,
				FeatureMapUtil.createEntry(XpdExtensionPackage.Literals.DOCUMENT_ROOT__AUDIT,
						XpdExtensionFactory.eINSTANCE.createAudit())));

		newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS,
				FeatureMapUtil.createEntry(XpdExtensionPackage.Literals.DOCUMENT_ROOT__SIGNAL_DATA,
						XpdExtensionFactory.eINSTANCE.createSignalData())));

		newChildDescriptors.add(createChildParameter(Xpdl2Package.Literals.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS,
				FeatureMapUtil.createEntry(XpdExtensionPackage.Literals.DOCUMENT_ROOT__RESCHEDULE_TIMER_SCRIPT,
						XpdExtensionFactory.eINSTANCE.createRescheduleTimerScript())));

		newChildDescriptors.add(createChildParameter(XpdExtensionPackage.Literals.PROCESS_INTERFACE__START_METHODS,
				XpdExtensionFactory.eINSTANCE.createStartMethod()));

		newChildDescriptors
				.add(createChildParameter(XpdExtensionPackage.Literals.PROCESS_INTERFACE__INTERMEDIATE_METHODS,
						XpdExtensionFactory.eINSTANCE.createIntermediateMethod()));

		newChildDescriptors
				.add(createChildParameter(XpdExtensionPackage.Literals.PROCESS_INTERFACE__SERVICE_PROCESS_CONFIGURATION,
						XpdExtensionFactory.eINSTANCE.createServiceProcessConfiguration()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		return XpdExtensionEditPlugin.INSTANCE;
	}

	@Override
	protected Command createAddCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
			Collection collection, int index)
	{
		Command cmd = super.createAddCommand(domain, owner, feature, collection, index);
		cmd = CommandsUtils.checkExternalAddWrappers(cmd, domain, owner, feature, collection, index);
		return cmd;
	}

	@Override
	protected Command createRemoveCommand(EditingDomain domain, EObject owner, EStructuralFeature feature,
			Collection collection)
	{
		Command cmd = super.createRemoveCommand(domain, owner, feature, collection);
		cmd = CommandsUtils.checkExternalDeleteWrappers(cmd, domain, owner, feature, collection);
		return cmd;
	}

	@Override
	protected Command createSetCommand(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value,
			int index)
	{
		Command cmd = super.createSetCommand(domain, owner, feature, value, index);
		cmd = CommandsUtils.checkExternalSetWrappers(cmd, domain, owner, feature, value, index);
		return cmd;
	}

}
