/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ad Hoc Task Configuration Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl#getEnablement <em>Enablement</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl#getAdHocExecutionType <em>Ad Hoc Execution Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl#isSuspendMainFlow <em>Suspend Main Flow</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl#isAllowMultipleInvocations <em>Allow Multiple Invocations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AdHocTaskConfigurationTypeImpl#getRequiredAccessPrivileges <em>Required Access Privileges</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdHocTaskConfigurationTypeImpl extends EObjectImpl implements AdHocTaskConfigurationType
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String						copyright							= "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved.";	//$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getEnablement() <em>Enablement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnablement()
	 * @generated
	 * @ordered
	 */
	protected EnablementType						enablement;

	/**
	 * The default value of the '{@link #getAdHocExecutionType() <em>Ad Hoc Execution Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdHocExecutionType()
	 * @generated
	 * @ordered
	 */
	protected static final AdHocExecutionTypeType	AD_HOC_EXECUTION_TYPE_EDEFAULT		= AdHocExecutionTypeType.AUTOMATIC;

	/**
	 * The cached value of the '{@link #getAdHocExecutionType() <em>Ad Hoc Execution Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdHocExecutionType()
	 * @generated
	 * @ordered
	 */
	protected AdHocExecutionTypeType				adHocExecutionType					= AD_HOC_EXECUTION_TYPE_EDEFAULT;

	/**
	 * This is true if the Ad Hoc Execution Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean								adHocExecutionTypeESet;

	/**
	 * The default value of the '{@link #isSuspendMainFlow() <em>Suspend Main Flow</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuspendMainFlow()
	 * @generated
	 * @ordered
	 */
	protected static final boolean					SUSPEND_MAIN_FLOW_EDEFAULT			= false;

	/**
	 * The cached value of the '{@link #isSuspendMainFlow() <em>Suspend Main Flow</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSuspendMainFlow()
	 * @generated
	 * @ordered
	 */
	protected boolean								suspendMainFlow						= SUSPEND_MAIN_FLOW_EDEFAULT;

	/**
	 * This is true if the Suspend Main Flow attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean								suspendMainFlowESet;

	/**
	 * The default value of the '{@link #isAllowMultipleInvocations() <em>Allow Multiple Invocations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowMultipleInvocations()
	 * @generated
	 * @ordered
	 */
	protected static final boolean					ALLOW_MULTIPLE_INVOCATIONS_EDEFAULT	= false;

	/**
	 * The cached value of the '{@link #isAllowMultipleInvocations() <em>Allow Multiple Invocations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowMultipleInvocations()
	 * @generated
	 * @ordered
	 */
	protected boolean								allowMultipleInvocations			= ALLOW_MULTIPLE_INVOCATIONS_EDEFAULT;

	/**
	 * This is true if the Allow Multiple Invocations attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean								allowMultipleInvocationsESet;

	/**
	 * The cached value of the '{@link #getRequiredAccessPrivileges() <em>Required Access Privileges</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredAccessPrivileges()
	 * @generated
	 * @ordered
	 */
	protected RequiredAccessPrivileges				requiredAccessPrivileges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AdHocTaskConfigurationTypeImpl()
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
		return XpdExtensionPackage.Literals.AD_HOC_TASK_CONFIGURATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnablementType getEnablement()
	{
		return enablement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnablement(EnablementType newEnablement, NotificationChain msgs)
	{
		EnablementType oldEnablement = enablement;
		enablement = newEnablement;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT, oldEnablement, newEnablement);
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
	public void setEnablement(EnablementType newEnablement)
	{
		if (newEnablement != enablement)
		{
			NotificationChain msgs = null;
			if (enablement != null) msgs = ((InternalEObject) enablement).eInverseRemove(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT, null,
					msgs);
			if (newEnablement != null) msgs = ((InternalEObject) newEnablement).eInverseAdd(this,
					EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT, null,
					msgs);
			msgs = basicSetEnablement(newEnablement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT, newEnablement, newEnablement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdHocExecutionTypeType getAdHocExecutionType()
	{
		return adHocExecutionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdHocExecutionType(AdHocExecutionTypeType newAdHocExecutionType)
	{
		AdHocExecutionTypeType oldAdHocExecutionType = adHocExecutionType;
		adHocExecutionType = newAdHocExecutionType == null ? AD_HOC_EXECUTION_TYPE_EDEFAULT : newAdHocExecutionType;
		boolean oldAdHocExecutionTypeESet = adHocExecutionTypeESet;
		adHocExecutionTypeESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE, oldAdHocExecutionType,
				adHocExecutionType, !oldAdHocExecutionTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAdHocExecutionType()
	{
		AdHocExecutionTypeType oldAdHocExecutionType = adHocExecutionType;
		boolean oldAdHocExecutionTypeESet = adHocExecutionTypeESet;
		adHocExecutionType = AD_HOC_EXECUTION_TYPE_EDEFAULT;
		adHocExecutionTypeESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE, oldAdHocExecutionType,
				AD_HOC_EXECUTION_TYPE_EDEFAULT, oldAdHocExecutionTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAdHocExecutionType()
	{
		return adHocExecutionTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSuspendMainFlow()
	{
		return suspendMainFlow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuspendMainFlow(boolean newSuspendMainFlow)
	{
		boolean oldSuspendMainFlow = suspendMainFlow;
		suspendMainFlow = newSuspendMainFlow;
		boolean oldSuspendMainFlowESet = suspendMainFlowESet;
		suspendMainFlowESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW, oldSuspendMainFlow,
				suspendMainFlow, !oldSuspendMainFlowESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSuspendMainFlow()
	{
		boolean oldSuspendMainFlow = suspendMainFlow;
		boolean oldSuspendMainFlowESet = suspendMainFlowESet;
		suspendMainFlow = SUSPEND_MAIN_FLOW_EDEFAULT;
		suspendMainFlowESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW, oldSuspendMainFlow,
				SUSPEND_MAIN_FLOW_EDEFAULT, oldSuspendMainFlowESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSuspendMainFlow()
	{
		return suspendMainFlowESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAllowMultipleInvocations()
	{
		return allowMultipleInvocations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllowMultipleInvocations(boolean newAllowMultipleInvocations)
	{
		boolean oldAllowMultipleInvocations = allowMultipleInvocations;
		allowMultipleInvocations = newAllowMultipleInvocations;
		boolean oldAllowMultipleInvocationsESet = allowMultipleInvocationsESet;
		allowMultipleInvocationsESet = true;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS,
				oldAllowMultipleInvocations, allowMultipleInvocations, !oldAllowMultipleInvocationsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAllowMultipleInvocations()
	{
		boolean oldAllowMultipleInvocations = allowMultipleInvocations;
		boolean oldAllowMultipleInvocationsESet = allowMultipleInvocationsESet;
		allowMultipleInvocations = ALLOW_MULTIPLE_INVOCATIONS_EDEFAULT;
		allowMultipleInvocationsESet = false;
		if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.UNSET,
				XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS,
				oldAllowMultipleInvocations, ALLOW_MULTIPLE_INVOCATIONS_EDEFAULT, oldAllowMultipleInvocationsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAllowMultipleInvocations()
	{
		return allowMultipleInvocationsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredAccessPrivileges getRequiredAccessPrivileges()
	{
		return requiredAccessPrivileges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequiredAccessPrivileges(RequiredAccessPrivileges newRequiredAccessPrivileges,
			NotificationChain msgs)
	{
		RequiredAccessPrivileges oldRequiredAccessPrivileges = requiredAccessPrivileges;
		requiredAccessPrivileges = newRequiredAccessPrivileges;
		if (eNotificationRequired())
		{
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES,
					oldRequiredAccessPrivileges, newRequiredAccessPrivileges);
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
	public void setRequiredAccessPrivileges(RequiredAccessPrivileges newRequiredAccessPrivileges)
	{
		if (newRequiredAccessPrivileges != requiredAccessPrivileges)
		{
			NotificationChain msgs = null;
			if (requiredAccessPrivileges != null)
				msgs = ((InternalEObject) requiredAccessPrivileges).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES,
						null, msgs);
			if (newRequiredAccessPrivileges != null)
				msgs = ((InternalEObject) newRequiredAccessPrivileges).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES,
						null, msgs);
			msgs = basicSetRequiredAccessPrivileges(newRequiredAccessPrivileges, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
				XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES,
				newRequiredAccessPrivileges, newRequiredAccessPrivileges));
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
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT:
				return basicSetEnablement(null, msgs);
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES:
				return basicSetRequiredAccessPrivileges(null, msgs);
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
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT:
				return getEnablement();
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE:
				return getAdHocExecutionType();
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW:
				return isSuspendMainFlow();
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS:
				return isAllowMultipleInvocations();
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES:
				return getRequiredAccessPrivileges();
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
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT:
				setEnablement((EnablementType) newValue);
				return;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE:
				setAdHocExecutionType((AdHocExecutionTypeType) newValue);
				return;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW:
				setSuspendMainFlow((Boolean) newValue);
				return;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS:
				setAllowMultipleInvocations((Boolean) newValue);
				return;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES:
				setRequiredAccessPrivileges((RequiredAccessPrivileges) newValue);
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
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT:
				setEnablement((EnablementType) null);
				return;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE:
				unsetAdHocExecutionType();
				return;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW:
				unsetSuspendMainFlow();
				return;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS:
				unsetAllowMultipleInvocations();
				return;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES:
				setRequiredAccessPrivileges((RequiredAccessPrivileges) null);
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
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ENABLEMENT:
				return enablement != null;
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__AD_HOC_EXECUTION_TYPE:
				return isSetAdHocExecutionType();
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__SUSPEND_MAIN_FLOW:
				return isSetSuspendMainFlow();
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__ALLOW_MULTIPLE_INVOCATIONS:
				return isSetAllowMultipleInvocations();
			case XpdExtensionPackage.AD_HOC_TASK_CONFIGURATION_TYPE__REQUIRED_ACCESS_PRIVILEGES:
				return requiredAccessPrivileges != null;
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
		result.append(" (adHocExecutionType: "); //$NON-NLS-1$
		if (adHocExecutionTypeESet) result.append(adHocExecutionType);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", suspendMainFlow: "); //$NON-NLS-1$
		if (suspendMainFlowESet) result.append(suspendMainFlow);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(", allowMultipleInvocations: "); //$NON-NLS-1$
		if (allowMultipleInvocationsESet) result.append(allowMultipleInvocations);
		else
			result.append("<unset>"); //$NON-NLS-1$
		result.append(')');
		return result.toString();
	}

} //AdHocTaskConfigurationTypeImpl
