/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ProcessNode;

/**
 * This is the item provider adapter for a {@link com.tibco.bx.emulation.model.ProcessNode} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ProcessNodeItemProvider
	extends NamedElementItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessNodeItemProvider(AdapterFactory adapterFactory) {
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

			addAliasPropertyDescriptor(object);
			addDescriptionPropertyDescriptor(object);
			addStatePropertyDescriptor(object);
			addModelTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Alias feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAliasPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ProcessNode_alias_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ProcessNode_alias_feature", "_UI_ProcessNode_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 EmulationPackage.Literals.PROCESS_NODE__ALIAS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Description feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addDescriptionPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ProcessNode_description_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ProcessNode_description_feature", "_UI_ProcessNode_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 EmulationPackage.Literals.PROCESS_NODE__DESCRIPTION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the State feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addStatePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ProcessNode_state_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ProcessNode_state_feature", "_UI_ProcessNode_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 EmulationPackage.Literals.PROCESS_NODE__STATE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This adds a property descriptor for the Model Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addModelTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ProcessNode_modelType_feature"), //$NON-NLS-1$
				 getString("_UI_PropertyDescriptor_description", "_UI_ProcessNode_modelType_feature", "_UI_ProcessNode_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				 EmulationPackage.Literals.PROCESS_NODE__MODEL_TYPE,
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
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(EmulationPackage.Literals.PROCESS_NODE__TESTPOINTS);
			childrenFeatures.add(EmulationPackage.Literals.PROCESS_NODE__ASSERTIONS);
			childrenFeatures.add(EmulationPackage.Literals.PROCESS_NODE__INPUT);
			childrenFeatures.add(EmulationPackage.Literals.PROCESS_NODE__OUTPUT);
			childrenFeatures.add(EmulationPackage.Literals.PROCESS_NODE__ERROR_INFORMATION);
			childrenFeatures.add(EmulationPackage.Literals.PROCESS_NODE__INTERMEDIATE_INPUTS);
			childrenFeatures.add(EmulationPackage.Literals.PROCESS_NODE__MULTI_INPUT_NODES);
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
	 * This returns ProcessNode.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ProcessNode")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_ProcessNode_type") + " " + ((ProcessNode)object).getName();
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

		switch (notification.getFeatureID(ProcessNode.class)) {
			case EmulationPackage.PROCESS_NODE__ALIAS:
			case EmulationPackage.PROCESS_NODE__DESCRIPTION:
			case EmulationPackage.PROCESS_NODE__STATE:
			case EmulationPackage.PROCESS_NODE__MODEL_TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case EmulationPackage.PROCESS_NODE__TESTPOINTS:
			case EmulationPackage.PROCESS_NODE__ASSERTIONS:
			case EmulationPackage.PROCESS_NODE__INPUT:
			case EmulationPackage.PROCESS_NODE__OUTPUT:
			case EmulationPackage.PROCESS_NODE__ERROR_INFORMATION:
			case EmulationPackage.PROCESS_NODE__INTERMEDIATE_INPUTS:
			case EmulationPackage.PROCESS_NODE__MULTI_INPUT_NODES:
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
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(EmulationPackage.Literals.PROCESS_NODE__TESTPOINTS,
				 EmulationFactory.eINSTANCE.createTestpoint()));

		newChildDescriptors.add
			(createChildParameter
				(EmulationPackage.Literals.PROCESS_NODE__ASSERTIONS,
				 EmulationFactory.eINSTANCE.createAssertion()));

		newChildDescriptors.add
			(createChildParameter
				(EmulationPackage.Literals.PROCESS_NODE__INPUT,
				 EmulationFactory.eINSTANCE.createInput()));

		newChildDescriptors.add
			(createChildParameter
				(EmulationPackage.Literals.PROCESS_NODE__OUTPUT,
				 EmulationFactory.eINSTANCE.createOutput()));

		newChildDescriptors.add
			(createChildParameter
				(EmulationPackage.Literals.PROCESS_NODE__ERROR_INFORMATION,
				 EmulationFactory.eINSTANCE.createErrorInformation()));

		newChildDescriptors.add
			(createChildParameter
				(EmulationPackage.Literals.PROCESS_NODE__INTERMEDIATE_INPUTS,
				 EmulationFactory.eINSTANCE.createIntermediateInput()));

		newChildDescriptors.add
			(createChildParameter
				(EmulationPackage.Literals.PROCESS_NODE__MULTI_INPUT_NODES,
				 EmulationFactory.eINSTANCE.createMultiInput()));
	}

}
