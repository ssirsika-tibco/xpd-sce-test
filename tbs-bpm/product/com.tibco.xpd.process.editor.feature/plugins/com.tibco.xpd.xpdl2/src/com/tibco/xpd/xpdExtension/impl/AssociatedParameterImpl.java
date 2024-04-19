/**
 * <copyright> </copyright>
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

import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.ModeType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Associated Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedParameterImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedParameterImpl#getFormalParam <em>Formal Param</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedParameterImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AssociatedParameterImpl#isMandatory <em>Mandatory</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssociatedParameterImpl extends EObjectImpl implements AssociatedParameter
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String		copyright				= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected Description			description;

	/**
	 * The default value of the '{@link #getFormalParam() <em>Formal Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormalParam()
	 * @generated
	 * @ordered
	 */
	protected static final String	FORMAL_PARAM_EDEFAULT	= null;

	/**
	 * The cached value of the '{@link #getFormalParam() <em>Formal Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormalParam()
	 * @generated
	 * @ordered
	 */
	protected String				formalParam				= FORMAL_PARAM_EDEFAULT;

	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final ModeType	MODE_EDEFAULT			= ModeType.IN_LITERAL;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected ModeType				mode					= MODE_EDEFAULT;

	/**
	 * This is true if the Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean				modeESet;

	/**
	 * The default value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected static final boolean	MANDATORY_EDEFAULT		= false;

	/**
	 * The cached value of the '{@link #isMandatory() <em>Mandatory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected boolean				mandatory				= MANDATORY_EDEFAULT;

	/**
	 * This is true if the Mandatory attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean				mandatoryESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssociatedParameterImpl()
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
		return XpdExtensionPackage.Literals.ASSOCIATED_PARAMETER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFormalParam()
	{
		return formalParam;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormalParam(String newFormalParam)
	{
		String oldFormalParam = formalParam;
		formalParam = newFormalParam;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ASSOCIATED_PARAMETER__FORMAL_PARAM, oldFormalParam, formalParam));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModeType getMode()
	{
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMode(ModeType newMode)
	{
		ModeType oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		boolean oldModeESet = modeESet;
		modeESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ASSOCIATED_PARAMETER__MODE, oldMode, mode, !oldModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMode()
	{
		ModeType oldMode = mode;
		boolean oldModeESet = modeESet;
		mode = MODE_EDEFAULT;
		modeESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.ASSOCIATED_PARAMETER__MODE, oldMode, MODE_EDEFAULT, oldModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMode()
	{
		return modeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Description getDescription()
	{
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDescription(Description newDescription, NotificationChain msgs)
	{
		Description oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION, oldDescription, newDescription);
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
	public void setDescription(Description newDescription)
	{
		if (newDescription != description)
		{
			NotificationChain msgs = null;
			if (description != null) msgs = ((InternalEObject) description).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION, null, msgs);
			if (newDescription != null) msgs = ((InternalEObject) newDescription).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION, null, msgs);
			msgs = basicSetDescription(newDescription, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION, newDescription, newDescription));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMandatory()
	{
		return mandatory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMandatory(boolean newMandatory)
	{
		boolean oldMandatory = mandatory;
		mandatory = newMandatory;
		boolean oldMandatoryESet = mandatoryESet;
		mandatoryESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ASSOCIATED_PARAMETER__MANDATORY, oldMandatory, mandatory, !oldMandatoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMandatory()
	{
		boolean oldMandatory = mandatory;
		boolean oldMandatoryESet = mandatoryESet;
		mandatory = MANDATORY_EDEFAULT;
		mandatoryESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, XpdExtensionPackage.ASSOCIATED_PARAMETER__MANDATORY,
					oldMandatory, MANDATORY_EDEFAULT, oldMandatoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMandatory()
	{
		return mandatoryESet;
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
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION:
				return basicSetDescription(null, msgs);
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
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION:
				return getDescription();
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__FORMAL_PARAM:
				return getFormalParam();
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__MODE:
				return getMode();
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__MANDATORY:
				return isMandatory();
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
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION:
				setDescription((Description) newValue);
				return;
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__FORMAL_PARAM:
				setFormalParam((String) newValue);
				return;
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__MODE:
				setMode((ModeType) newValue);
				return;
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__MANDATORY:
				setMandatory((Boolean) newValue);
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
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION:
				setDescription((Description) null);
				return;
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__FORMAL_PARAM:
				setFormalParam(FORMAL_PARAM_EDEFAULT);
				return;
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__MODE:
				unsetMode();
				return;
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__MANDATORY:
				unsetMandatory();
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
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__DESCRIPTION:
				return description != null;
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__FORMAL_PARAM:
				return FORMAL_PARAM_EDEFAULT == null ? formalParam != null : !FORMAL_PARAM_EDEFAULT.equals(formalParam);
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__MODE:
				return isSetMode();
			case XpdExtensionPackage.ASSOCIATED_PARAMETER__MANDATORY:
				return isSetMandatory();
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
		result.append(" (formalParam: "); //$NON-NLS-1$
		result.append(formalParam);
		result.append(", mode: "); //$NON-NLS-1$
		if (modeESet) result.append(mode);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", mandatory: "); //$NON-NLS-1$
		if (mandatoryESet) result.append(mandatory);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //AssociatedParameterImpl