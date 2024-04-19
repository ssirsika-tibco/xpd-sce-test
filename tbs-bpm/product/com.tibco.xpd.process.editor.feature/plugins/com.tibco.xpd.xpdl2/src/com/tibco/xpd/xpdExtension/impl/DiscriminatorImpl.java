/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Discriminator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DiscriminatorImpl#getDiscriminatorType <em>Discriminator Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.DiscriminatorImpl#getStructuredDiscriminator <em>Structured Discriminator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiscriminatorImpl extends EObjectImpl implements Discriminator
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String			copyright					= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The default value of the '{@link #getDiscriminatorType() <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorType()
	 * @generated
	 * @ordered
	 */
	protected static final String		DISCRIMINATOR_TYPE_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getDiscriminatorType() <em>Discriminator Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiscriminatorType()
	 * @generated
	 * @ordered
	 */
	protected String					discriminatorType			= DISCRIMINATOR_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStructuredDiscriminator() <em>Structured Discriminator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructuredDiscriminator()
	 * @generated
	 * @ordered
	 */
	protected StructuredDiscriminator	structuredDiscriminator;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiscriminatorImpl()
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
		return XpdExtensionPackage.Literals.DISCRIMINATOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDiscriminatorType()
	{
		return discriminatorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiscriminatorType(String newDiscriminatorType)
	{
		String oldDiscriminatorType = discriminatorType;
		discriminatorType = newDiscriminatorType;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DISCRIMINATOR__DISCRIMINATOR_TYPE, oldDiscriminatorType, discriminatorType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructuredDiscriminator getStructuredDiscriminator()
	{
		return structuredDiscriminator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStructuredDiscriminator(StructuredDiscriminator newStructuredDiscriminator,
			NotificationChain msgs)
	{
		StructuredDiscriminator oldStructuredDiscriminator = structuredDiscriminator;
		structuredDiscriminator = newStructuredDiscriminator;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR, oldStructuredDiscriminator,
					newStructuredDiscriminator);
			if (msgs == null) msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStructuredDiscriminator(StructuredDiscriminator newStructuredDiscriminator)
	{
		if (newStructuredDiscriminator != structuredDiscriminator)
		{
			NotificationChain msgs = null;
			if (structuredDiscriminator != null) msgs = ((InternalEObject) structuredDiscriminator).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR, null, msgs);
			if (newStructuredDiscriminator != null)
				msgs = ((InternalEObject) newStructuredDiscriminator).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR, null,
						msgs);
			msgs = basicSetStructuredDiscriminator(newStructuredDiscriminator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR, newStructuredDiscriminator,
				newStructuredDiscriminator));
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
			case XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR:
				return basicSetStructuredDiscriminator(null, msgs);
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
			case XpdExtensionPackage.DISCRIMINATOR__DISCRIMINATOR_TYPE:
				return getDiscriminatorType();
			case XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR:
				return getStructuredDiscriminator();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue)
	{
		switch (featureID)
		{
			case XpdExtensionPackage.DISCRIMINATOR__DISCRIMINATOR_TYPE:
				setDiscriminatorType((String) newValue);
				return;
			case XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR:
				setStructuredDiscriminator((StructuredDiscriminator) newValue);
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
			case XpdExtensionPackage.DISCRIMINATOR__DISCRIMINATOR_TYPE:
				setDiscriminatorType(DISCRIMINATOR_TYPE_EDEFAULT);
				return;
			case XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR:
				setStructuredDiscriminator((StructuredDiscriminator) null);
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
			case XpdExtensionPackage.DISCRIMINATOR__DISCRIMINATOR_TYPE:
				return DISCRIMINATOR_TYPE_EDEFAULT == null ? discriminatorType != null
						: !DISCRIMINATOR_TYPE_EDEFAULT.equals(discriminatorType);
			case XpdExtensionPackage.DISCRIMINATOR__STRUCTURED_DISCRIMINATOR:
				return structuredDiscriminator != null;
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
		result.append(" (discriminatorType: "); //$NON-NLS-1$
		result.append(discriminatorType);
		result.append(')');
		return result.toString();
	}

} //DiscriminatorImpl
