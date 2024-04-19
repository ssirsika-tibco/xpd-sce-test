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

import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.CommandsUtils;
import com.tibco.xpd.xpdl2.provider.NamedElementItemProvider;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.StartMethod} object.
 * <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * @generated
 */
public class StartMethodItemProvider extends NamedElementItemProvider
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
	public StartMethodItemProvider(AdapterFactory adapterFactory)
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
			addDisableImplicitAssociationPropertyDescriptor(object);
			addTriggerPropertyDescriptor(object);
			addVisibilityPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * This adds a property descriptor for the Disable Implicit Association feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDisableImplicitAssociationPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_AssociatedParametersContainer_disableImplicitAssociation_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
						"_UI_AssociatedParametersContainer_disableImplicitAssociation_feature", //$NON-NLS-1$
						"_UI_AssociatedParametersContainer_type"), //$NON-NLS-1$
				XpdExtensionPackage.Literals.ASSOCIATED_PARAMETERS_CONTAINER__DISABLE_IMPLICIT_ASSOCIATION, true, false,
				false, ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Trigger feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTriggerPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_InterfaceMethod_trigger_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_InterfaceMethod_trigger_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_InterfaceMethod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.INTERFACE_METHOD__TRIGGER, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Visibility feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addVisibilityPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_InterfaceMethod_visibility_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_InterfaceMethod_visibility_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_InterfaceMethod_type"), //$NON-NLS-1$
						XpdExtensionPackage.Literals.INTERFACE_METHOD__VISIBILITY, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
			childrenFeatures.add(XpdExtensionPackage.Literals.ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS);
			childrenFeatures.add(XpdExtensionPackage.Literals.INTERFACE_METHOD__TRIGGER_RESULT_MESSAGE);
			childrenFeatures.add(XpdExtensionPackage.Literals.INTERFACE_METHOD__ERROR_METHODS);
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
	 * This returns StartMethod.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated NOT
	 */
	public Object getImage(Object object)
	{
		String icon = "full/obj16/StartMethodNone"; //$NON-NLS-1$
		if (object instanceof StartMethod && TriggerType.MESSAGE_LITERAL.equals(((StartMethod) object).getTrigger()))
		{
			icon = "full/obj16/StartMethodMessage"; //$NON-NLS-1$
		}
		return overlayImage(object, getResourceLocator().getImage(icon));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
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

		switch (notification.getFeatureID(StartMethod.class))
		{
			case XpdExtensionPackage.START_METHOD__DESCRIPTION:
			case XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
			case XpdExtensionPackage.START_METHOD__TRIGGER:
			case XpdExtensionPackage.START_METHOD__VISIBILITY:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS:
			case XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE:
			case XpdExtensionPackage.START_METHOD__ERROR_METHODS:
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

		newChildDescriptors.add(createChildParameter(
				XpdExtensionPackage.Literals.ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS,
				XpdExtensionFactory.eINSTANCE.createAssociatedParameter()));

		newChildDescriptors
				.add(createChildParameter(XpdExtensionPackage.Literals.INTERFACE_METHOD__TRIGGER_RESULT_MESSAGE,
						Xpdl2Factory.eINSTANCE.createTriggerResultMessage()));

		newChildDescriptors.add(createChildParameter(XpdExtensionPackage.Literals.INTERFACE_METHOD__ERROR_METHODS,
				XpdExtensionFactory.eINSTANCE.createErrorMethod()));
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
