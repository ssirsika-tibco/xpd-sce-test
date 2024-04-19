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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.impl.NamedElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Start Method</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl#getAssociatedParameters <em>Associated Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl#isDisableImplicitAssociation <em>Disable Implicit Association</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.StartMethodImpl#getErrorMethods <em>Error Methods</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StartMethodImpl extends NamedElementImpl implements StartMethod
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String				copyright								= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected Description					description;

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
	 * The default value of the '{@link #getTrigger() <em>Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrigger()
	 * @generated
	 * @ordered
	 */
	protected static final TriggerType		TRIGGER_EDEFAULT						= TriggerType.NONE_LITERAL;

	/**
	 * The cached value of the '{@link #getTrigger() <em>Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrigger()
	 * @generated
	 * @ordered
	 */
	protected TriggerType					trigger									= TRIGGER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTriggerResultMessage() <em>Trigger Result Message</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTriggerResultMessage()
	 * @generated
	 * @ordered
	 */
	protected TriggerResultMessage			triggerResultMessage;

	/**
	 * The default value of the '{@link #getVisibility() <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected static final Visibility		VISIBILITY_EDEFAULT						= Visibility.PRIVATE;

	/**
	 * The cached value of the '{@link #getVisibility() <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected Visibility					visibility								= VISIBILITY_EDEFAULT;

	/**
	 * This is true if the Visibility attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean						visibilityESet;

	/**
	 * The cached value of the '{@link #getErrorMethods() <em>Error Methods</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorMethods()
	 * @generated
	 * @ordered
	 */
	protected EList<ErrorMethod>			errorMethods;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StartMethodImpl()
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
		return XpdExtensionPackage.Literals.START_METHOD;
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
					XpdExtensionPackage.START_METHOD__DESCRIPTION, oldDescription, newDescription);
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
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.START_METHOD__DESCRIPTION, null, msgs);
			if (newDescription != null) msgs = ((InternalEObject) newDescription).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.START_METHOD__DESCRIPTION, null, msgs);
			msgs = basicSetDescription(newDescription, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.START_METHOD__DESCRIPTION, newDescription, newDescription));
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
					XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS);
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
				XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION, oldDisableImplicitAssociation,
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
				XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION, oldDisableImplicitAssociation,
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
	public TriggerType getTrigger()
	{
		return trigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrigger(TriggerType newTrigger)
	{
		TriggerType oldTrigger = trigger;
		trigger = newTrigger == null ? TRIGGER_EDEFAULT : newTrigger;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.START_METHOD__TRIGGER, oldTrigger, trigger));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TriggerResultMessage getTriggerResultMessage()
	{
		return triggerResultMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTriggerResultMessage(TriggerResultMessage newTriggerResultMessage,
			NotificationChain msgs)
	{
		TriggerResultMessage oldTriggerResultMessage = triggerResultMessage;
		triggerResultMessage = newTriggerResultMessage;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE, oldTriggerResultMessage,
					newTriggerResultMessage);
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
	public void setTriggerResultMessage(TriggerResultMessage newTriggerResultMessage)
	{
		if (newTriggerResultMessage != triggerResultMessage)
		{
			NotificationChain msgs = null;
			if (triggerResultMessage != null) msgs = ((InternalEObject) triggerResultMessage).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE, null, msgs);
			if (newTriggerResultMessage != null) msgs = ((InternalEObject) newTriggerResultMessage).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE, null, msgs);
			msgs = basicSetTriggerResultMessage(newTriggerResultMessage, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(
				new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE,
						newTriggerResultMessage, newTriggerResultMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Visibility getVisibility()
	{
		return visibility;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisibility(Visibility newVisibility)
	{
		Visibility oldVisibility = visibility;
		visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
		boolean oldVisibilityESet = visibilityESet;
		visibilityESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.START_METHOD__VISIBILITY, oldVisibility, visibility, !oldVisibilityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetVisibility()
	{
		Visibility oldVisibility = visibility;
		boolean oldVisibilityESet = visibilityESet;
		visibility = VISIBILITY_EDEFAULT;
		visibilityESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.START_METHOD__VISIBILITY, oldVisibility, VISIBILITY_EDEFAULT, oldVisibilityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetVisibility()
	{
		return visibilityESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ErrorMethod> getErrorMethods()
	{
		if (errorMethods == null)
		{
			errorMethods = new EObjectContainmentEList<ErrorMethod>(ErrorMethod.class, this,
					XpdExtensionPackage.START_METHOD__ERROR_METHODS);
		}
		return errorMethods;
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
			case XpdExtensionPackage.START_METHOD__DESCRIPTION:
				return basicSetDescription(null, msgs);
			case XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS:
				return ((InternalEList< ? >) getAssociatedParameters()).basicRemove(otherEnd, msgs);
			case XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE:
				return basicSetTriggerResultMessage(null, msgs);
			case XpdExtensionPackage.START_METHOD__ERROR_METHODS:
				return ((InternalEList< ? >) getErrorMethods()).basicRemove(otherEnd, msgs);
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
			case XpdExtensionPackage.START_METHOD__DESCRIPTION:
				return getDescription();
			case XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS:
				return getAssociatedParameters();
			case XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
				return isDisableImplicitAssociation();
			case XpdExtensionPackage.START_METHOD__TRIGGER:
				return getTrigger();
			case XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE:
				return getTriggerResultMessage();
			case XpdExtensionPackage.START_METHOD__VISIBILITY:
				return getVisibility();
			case XpdExtensionPackage.START_METHOD__ERROR_METHODS:
				return getErrorMethods();
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
			case XpdExtensionPackage.START_METHOD__DESCRIPTION:
				setDescription((Description) newValue);
				return;
			case XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS:
				getAssociatedParameters().clear();
				getAssociatedParameters().addAll((Collection< ? extends AssociatedParameter>) newValue);
				return;
			case XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
				setDisableImplicitAssociation((Boolean) newValue);
				return;
			case XpdExtensionPackage.START_METHOD__TRIGGER:
				setTrigger((TriggerType) newValue);
				return;
			case XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE:
				setTriggerResultMessage((TriggerResultMessage) newValue);
				return;
			case XpdExtensionPackage.START_METHOD__VISIBILITY:
				setVisibility((Visibility) newValue);
				return;
			case XpdExtensionPackage.START_METHOD__ERROR_METHODS:
				getErrorMethods().clear();
				getErrorMethods().addAll((Collection< ? extends ErrorMethod>) newValue);
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
			case XpdExtensionPackage.START_METHOD__DESCRIPTION:
				setDescription((Description) null);
				return;
			case XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS:
				getAssociatedParameters().clear();
				return;
			case XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
				unsetDisableImplicitAssociation();
				return;
			case XpdExtensionPackage.START_METHOD__TRIGGER:
				setTrigger(TRIGGER_EDEFAULT);
				return;
			case XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE:
				setTriggerResultMessage((TriggerResultMessage) null);
				return;
			case XpdExtensionPackage.START_METHOD__VISIBILITY:
				unsetVisibility();
				return;
			case XpdExtensionPackage.START_METHOD__ERROR_METHODS:
				getErrorMethods().clear();
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
			case XpdExtensionPackage.START_METHOD__DESCRIPTION:
				return description != null;
			case XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS:
				return associatedParameters != null && !associatedParameters.isEmpty();
			case XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
				return isSetDisableImplicitAssociation();
			case XpdExtensionPackage.START_METHOD__TRIGGER:
				return trigger != TRIGGER_EDEFAULT;
			case XpdExtensionPackage.START_METHOD__TRIGGER_RESULT_MESSAGE:
				return triggerResultMessage != null;
			case XpdExtensionPackage.START_METHOD__VISIBILITY:
				return isSetVisibility();
			case XpdExtensionPackage.START_METHOD__ERROR_METHODS:
				return errorMethods != null && !errorMethods.isEmpty();
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
		if (baseClass == DescribedElement.class)
		{
			switch (derivedFeatureID)
			{
				case XpdExtensionPackage.START_METHOD__DESCRIPTION:
					return Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION;
				default:
					return -1;
			}
		}
		if (baseClass == AssociatedParametersContainer.class)
		{
			switch (derivedFeatureID)
			{
				case XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS:
					return XpdExtensionPackage.ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS;
				case XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION:
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
		if (baseClass == DescribedElement.class)
		{
			switch (baseFeatureID)
			{
				case Xpdl2Package.DESCRIBED_ELEMENT__DESCRIPTION:
					return XpdExtensionPackage.START_METHOD__DESCRIPTION;
				default:
					return -1;
			}
		}
		if (baseClass == AssociatedParametersContainer.class)
		{
			switch (baseFeatureID)
			{
				case XpdExtensionPackage.ASSOCIATED_PARAMETERS_CONTAINER__ASSOCIATED_PARAMETERS:
					return XpdExtensionPackage.START_METHOD__ASSOCIATED_PARAMETERS;
				case XpdExtensionPackage.ASSOCIATED_PARAMETERS_CONTAINER__DISABLE_IMPLICIT_ASSOCIATION:
					return XpdExtensionPackage.START_METHOD__DISABLE_IMPLICIT_ASSOCIATION;
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
		result.append(", trigger: "); //$NON-NLS-1$
		result.append(trigger);
		result.append(", visibility: "); //$NON-NLS-1$
		if (visibilityESet) result.append(visibility);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //StartMethodImpl