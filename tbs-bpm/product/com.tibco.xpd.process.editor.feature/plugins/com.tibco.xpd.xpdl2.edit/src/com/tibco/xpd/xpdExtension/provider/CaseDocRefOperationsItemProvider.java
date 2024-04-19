/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.provider;

import com.tibco.xpd.xpdExtension.CaseDocRefOperations;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

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
 * This is the item provider adapter for a {@link com.tibco.xpd.xpdExtension.CaseDocRefOperations} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class CaseDocRefOperationsItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
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
	public CaseDocRefOperationsItemProvider(AdapterFactory adapterFactory)
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

			addCaseDocumentRefFieldPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Case Document Ref Field feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addCaseDocumentRefFieldPropertyDescriptor(Object object)
	{
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_CaseDocRefOperations_caseDocumentRefField_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_CaseDocRefOperations_caseDocumentRefField_feature", //$NON-NLS-1$//$NON-NLS-2$
						"_UI_CaseDocRefOperations_type"), //$NON-NLS-1$
				XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD, true, false, false,
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
			childrenFeatures.add(XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION);
			childrenFeatures.add(XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION);
			childrenFeatures.add(XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION);
			childrenFeatures.add(XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION);
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
	 * This returns CaseDocRefOperations.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object)
	{
		return overlayImage(object, getResourceLocator().getImage("full/obj16/CaseDocRefOperations")); //$NON-NLS-1$
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
		String label = ((CaseDocRefOperations) object).getCaseDocumentRefField();
		return label == null || label.length() == 0 ? getString("_UI_CaseDocRefOperations_type") : //$NON-NLS-1$
				getString("_UI_CaseDocRefOperations_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(CaseDocRefOperations.class))
		{
			case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__CASE_DOCUMENT_REF_FIELD:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION:
			case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION:
			case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION:
			case XpdExtensionPackage.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION:
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

		newChildDescriptors
				.add(createChildParameter(XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__MOVE_CASE_DOC_OPERATION,
						XpdExtensionFactory.eINSTANCE.createMoveCaseDocOperation()));

		newChildDescriptors.add(
				createChildParameter(XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__UNLINK_CASE_DOC_OPERATION,
						XpdExtensionFactory.eINSTANCE.createUnlinkCaseDocOperation()));

		newChildDescriptors
				.add(createChildParameter(XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__LINK_CASE_DOC_OPERATION,
						XpdExtensionFactory.eINSTANCE.createLinkCaseDocOperation()));

		newChildDescriptors.add(
				createChildParameter(XpdExtensionPackage.Literals.CASE_DOC_REF_OPERATIONS__DELETE_CASE_DOC_OPERATION,
						XpdExtensionFactory.eINSTANCE.createDeleteCaseDocOperation()));
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
