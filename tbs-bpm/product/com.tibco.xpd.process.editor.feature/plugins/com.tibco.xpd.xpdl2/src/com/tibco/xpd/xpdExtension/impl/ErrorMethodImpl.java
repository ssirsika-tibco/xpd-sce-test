/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.impl.UniqueIdElementImpl;
import com.tibco.xpd.xpdl2.impl.NamedElementImpl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Method</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ErrorMethodImpl#getAssociatedParameters <em>Associated Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ErrorMethodImpl#isDisableImplicitAssociation <em>Disable Implicit Association</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.ErrorMethodImpl#getErrorCode <em>Error Code</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorMethodImpl extends UniqueIdElementImpl implements ErrorMethod
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String				copyright								= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getAssociatedParameters() <em>Associated Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssociatedParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<AssociatedParameter>	associatedParameters;

	/**
	 * The default value of the '{@link #isDisableImplicitAssociation() <em>Disable Implicit Association</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisableImplicitAssociation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean			DISABLE_IMPLICIT_ASSOCIATION_EDEFAULT	= false;

	/**
	 * The cached value of the '{@link #isDisableImplicitAssociation() <em>Disable Implicit Association</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisableImplicitAssociation()
	 * @generated
	 * @ordered
	 */
	protected boolean						disableImplicitAssociation				= DISABLE_IMPLICIT_ASSOCIATION_EDEFAULT;

	/**
	 * This is true if the Disable Implicit Association attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean						disableImplicitAssociationESet;

	/**
	 * The default value of the '{@link #getErrorCode() <em>Error Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorCode()
	 * @generated
	 * @ordered
	 */
	protected static final String			ERROR_CODE_EDEFAULT						= null;

	/**
	 * The cached value of the '{@link #getErrorCode() <em>Error Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorCode()
	 * @generated
	 * @ordered
	 */
	protected String						errorCode								= ERROR_CODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorMethodImpl()
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
		return XpdExtensionPackage.Literals.ERROR_METHOD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AssociatedParameter> getAssociatedParameters()
	{
		if (associatedParameters == null)
		{
			associatedParameters = new EObjectContainmentEList<AssociatedParameter>(AssociatedParameter.class, this,
					XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS);
		}
		return associatedParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDisableImplicitAssociation()
	{
		return disableImplicitAssociation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisableImplicitAssociation(boolean newDisableImplicitAssociation)
	{
		boolean oldDisableImplicitAssociation = disableImplicitAssociation;
		disableImplicitAssociation = newDisableImplicitAssociation;
		boolean oldDisableImplicitAssociationESet = disableImplicitAssociationESet;
		disableImplicitAssociationESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION, oldDisableImplicitAssociation,
				disableImplicitAssociation, !oldDisableImplicitAssociationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDisableImplicitAssociation()
	{
		boolean oldDisableImplicitAssociation = disableImplicitAssociation;
		boolean oldDisableImplicitAssociationESet = disableImplicitAssociationESet;
		disableImplicitAssociation = DISABLE_IMPLICIT_ASSOCIATION_EDEFAULT;
		disableImplicitAssociationESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION, oldDisableImplicitAssociation,
				DISABLE_IMPLICIT_ASSOCIATION_EDEFAULT, oldDisableImplicitAssociationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDisableImplicitAssociation()
	{
		return disableImplicitAssociationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setErrorCode(String newErrorCode)
	{
		String oldErrorCode = errorCode;
		errorCode = newErrorCode;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.ERROR_METHOD__ERROR_CODE, oldErrorCode, errorCode));
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
			case XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS:
				return ((InternalEList< ? >) getAssociatedParameters()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS:
				return getAssociatedParameters();
			case XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
				return isDisableImplicitAssociation();
			case XpdExtensionPackage.ERROR_METHOD__ERROR_CODE:
				return getErrorCode();
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
			case XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS:
				getAssociatedParameters().clear();
				getAssociatedParameters().addAll((Collection< ? extends AssociatedParameter>) newValue);
				return;
			case XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
				setDisableImplicitAssociation((Boolean) newValue);
				return;
			case XpdExtensionPackage.ERROR_METHOD__ERROR_CODE:
				setErrorCode((String) newValue);
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
			case XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS:
				getAssociatedParameters().clear();
				return;
			case XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
				unsetDisableImplicitAssociation();
				return;
			case XpdExtensionPackage.ERROR_METHOD__ERROR_CODE:
				setErrorCode(ERROR_CODE_EDEFAULT);
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
			case XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS:
				return associatedParameters != null && !associatedParameters.isEmpty();
			case XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
				return isSetDisableImplicitAssociation();
			case XpdExtensionPackage.ERROR_METHOD__ERROR_CODE:
				return ERROR_CODE_EDEFAULT == null ? errorCode != null : !ERROR_CODE_EDEFAULT.equals(errorCode);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class< ? > baseClass)
	{
		if (baseClass == AssociatedParametersContainer.class)
		{
			switch (derivedFeatureID)
			{
				case XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS:
					return XpdExtensionPackage.ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS;
				case XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
					return XpdExtensionPackage.ASSOCIATED_PARAMETERS_CONTAINER__DISABLE_IMPLICIT_ASSOCIATION;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class< ? > baseClass)
	{
		if (baseClass == AssociatedParametersContainer.class)
		{
			switch (baseFeatureID)
			{
				case XpdExtensionPackage.ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS:
					return XpdExtensionPackage.ERROR_METHOD__ASSOCIATED_PARAMETERS;
				case XpdExtensionPackage.ASSOCIATED_PARAMETERS_CONTAINER__DISABLE_IMPLICIT_ASSOCIATION:
					return XpdExtensionPackage.ERROR_METHOD__DISABLE_IMPLICIT_ASSOCIATION;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (disableImplicitAssociation: "); //$NON-NLS-1$
		if (disableImplicitAssociationESet) result.append(disableImplicitAssociation);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", errorCode: "); //$NON-NLS-1$
		result.append(errorCode);
		result.append(')');
		return result.toString();
	}

} //ErrorMethodImpl
