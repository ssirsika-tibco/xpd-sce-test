/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.DataWorkItemAttributeMapping;
import com.tibco.xpd.xpdExtension.ProcessDataWorkItemAttributeMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Data Work Item Attribute Mappings</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ProcessDataWorkItemAttributeMappingsImpl#getDataWorkItemAttributeMapping <em>Data Work Item Attribute Mapping</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessDataWorkItemAttributeMappingsImpl extends EObjectImpl
		implements ProcessDataWorkItemAttributeMappings
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String						copyright	= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getDataWorkItemAttributeMapping() <em>Data Work Item Attribute Mapping</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataWorkItemAttributeMapping()
	 * @generated
	 * @ordered
	 */
	protected EList<DataWorkItemAttributeMapping>	dataWorkItemAttributeMapping;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessDataWorkItemAttributeMappingsImpl()
	{
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass()
	{
		return XpdExtensionPackage.Literals.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataWorkItemAttributeMapping> getDataWorkItemAttributeMapping()
	{
		if (dataWorkItemAttributeMapping == null)
		{
			dataWorkItemAttributeMapping = new EObjectContainmentEList<DataWorkItemAttributeMapping>(
					DataWorkItemAttributeMapping.class, this,
					XpdExtensionPackage.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING);
		}
		return dataWorkItemAttributeMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING:
				return ((InternalEList< ? >) getDataWorkItemAttributeMapping()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING:
				return getDataWorkItemAttributeMapping();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING:
				getDataWorkItemAttributeMapping().clear();
				getDataWorkItemAttributeMapping()
						.addAll((Collection< ? extends DataWorkItemAttributeMapping>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING:
				getDataWorkItemAttributeMapping().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.PROCESS_DATA_WORK_ITEM_ATTRIBUTE_MAPPINGS__DATA_WORK_ITEM_ATTRIBUTE_MAPPING:
				return dataWorkItemAttributeMapping != null && !dataWorkItemAttributeMapping.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProcessDataWorkItemAttributeMappingsImpl
