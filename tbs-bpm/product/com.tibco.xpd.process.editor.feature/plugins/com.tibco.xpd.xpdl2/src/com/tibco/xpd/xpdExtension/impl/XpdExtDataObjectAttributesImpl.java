/**
 * <copyright> </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdExtension.XpdExtDataObjectAttributes;
import com.tibco.xpd.xpdExtension.XpdExtProperty;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xpd Ext Data Object Attributes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.XpdExtDataObjectAttributesImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.XpdExtDataObjectAttributesImpl#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.XpdExtDataObjectAttributesImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @generated
 */
public class XpdExtDataObjectAttributesImpl extends EObjectImpl implements XpdExtDataObjectAttributes
{

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String	DESCRIPTION_EDEFAULT		= null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String				description					= DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getExternalReference() <em>External Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExternalReference()
	 * @generated
	 * @ordered
	 */
	protected static final String	EXTERNAL_REFERENCE_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getExternalReference() <em>External Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExternalReference()
	 * @generated
	 * @ordered
	 */
	protected String				externalReference			= EXTERNAL_REFERENCE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<XpdExtProperty>	properties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XpdExtDataObjectAttributesImpl()
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
		return XpdExtensionPackage.Literals.XPD_EXT_DATA_OBJECT_ATTRIBUTES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription)
	{
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExternalReference()
	{
		return externalReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExternalReference(String newExternalReference)
	{
		String oldExternalReference = externalReference;
		externalReference = newExternalReference;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__EXTERNAL_REFERENCE, oldExternalReference,
				externalReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<XpdExtProperty> getProperties()
	{
		if (properties == null)
		{
			properties = new EObjectContainmentEList<XpdExtProperty>(XpdExtProperty.class, this,
					XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES);
		}
		return properties;
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
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES:
				return ((InternalEList< ? >) getProperties()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__DESCRIPTION:
				return getDescription();
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__EXTERNAL_REFERENCE:
				return getExternalReference();
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES:
				return getProperties();
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
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__DESCRIPTION:
				setDescription((String) newValue);
				return;
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__EXTERNAL_REFERENCE:
				setExternalReference((String) newValue);
				return;
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection< ? extends XpdExtProperty>) newValue);
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
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__EXTERNAL_REFERENCE:
				setExternalReference(EXTERNAL_REFERENCE_EDEFAULT);
				return;
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES:
				getProperties().clear();
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
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__EXTERNAL_REFERENCE:
				return EXTERNAL_REFERENCE_EDEFAULT == null ? externalReference != null
						: !EXTERNAL_REFERENCE_EDEFAULT.equals(externalReference);
			case XpdExtensionPackage.XPD_EXT_DATA_OBJECT_ATTRIBUTES__PROPERTIES:
				return properties != null && !properties.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString()
	{
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (description: "); //$NON-NLS-1$
		result.append(description);
		result.append(", externalReference: "); //$NON-NLS-1$
		result.append(externalReference);
		result.append(')');
		return result.toString();
	}

} //XpdExtDataObjectAttributesImpl