/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.OtherElementsContainer;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ad Hoc Task Configuration Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Element for Ad-Hoc task configuration settings, used for an Activity set to Adhoc.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getEnablement <em>Enablement</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getAdHocExecutionType <em>Ad Hoc Execution Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isSuspendMainFlow <em>Suspend Main Flow</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isAllowMultipleInvocations <em>Allow Multiple Invocations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getRequiredAccessPrivileges <em>Required Access Privileges</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAdHocTaskConfigurationType()
 * @model extendedMetaData="name='AdHocTaskConfiguration_._type' kind='elementOnly'"
 * @generated
 */
public interface AdHocTaskConfigurationType extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Enablement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Ad-hoc activity enablement criteria.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Enablement</em>' containment reference.
	 * @see #setEnablement(EnablementType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAdHocTaskConfigurationType_Enablement()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Enablement' namespace='##targetNamespace'"
	 * @generated
	 */
	EnablementType getEnablement();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getEnablement <em>Enablement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enablement</em>' containment reference.
	 * @see #getEnablement()
	 * @generated
	 */
	void setEnablement(EnablementType value);

	/**
	 * Returns the value of the '<em><b>Ad Hoc Execution Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.AdHocExecutionTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Ad-hoc execution type - Automatic (on enablement) | Manual 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ad Hoc Execution Type</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.AdHocExecutionTypeType
	 * @see #isSetAdHocExecutionType()
	 * @see #unsetAdHocExecutionType()
	 * @see #setAdHocExecutionType(AdHocExecutionTypeType)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAdHocTaskConfigurationType_AdHocExecutionType()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='AdHocExecutionType'"
	 * @generated
	 */
	AdHocExecutionTypeType getAdHocExecutionType();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getAdHocExecutionType <em>Ad Hoc Execution Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ad Hoc Execution Type</em>' attribute.
	 * @see com.tibco.xpd.xpdExtension.AdHocExecutionTypeType
	 * @see #isSetAdHocExecutionType()
	 * @see #unsetAdHocExecutionType()
	 * @see #getAdHocExecutionType()
	 * @generated
	 */
	void setAdHocExecutionType(AdHocExecutionTypeType value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getAdHocExecutionType <em>Ad Hoc Execution Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAdHocExecutionType()
	 * @see #getAdHocExecutionType()
	 * @see #setAdHocExecutionType(AdHocExecutionTypeType)
	 * @generated
	 */
	void unsetAdHocExecutionType();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getAdHocExecutionType <em>Ad Hoc Execution Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Ad Hoc Execution Type</em>' attribute is set.
	 * @see #unsetAdHocExecutionType()
	 * @see #getAdHocExecutionType()
	 * @see #setAdHocExecutionType(AdHocExecutionTypeType)
	 * @generated
	 */
	boolean isSetAdHocExecutionType();

	/**
	 * Returns the value of the '<em><b>Suspend Main Flow</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * True if continuation of the main process flow should be suspended on execution of the ad-hoc activity.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Suspend Main Flow</em>' attribute.
	 * @see #isSetSuspendMainFlow()
	 * @see #unsetSuspendMainFlow()
	 * @see #setSuspendMainFlow(boolean)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAdHocTaskConfigurationType_SuspendMainFlow()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='attribute' name='SuspendMainFlow'"
	 * @generated
	 */
	boolean isSuspendMainFlow();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isSuspendMainFlow <em>Suspend Main Flow</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Suspend Main Flow</em>' attribute.
	 * @see #isSetSuspendMainFlow()
	 * @see #unsetSuspendMainFlow()
	 * @see #isSuspendMainFlow()
	 * @generated
	 */
	void setSuspendMainFlow(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isSuspendMainFlow <em>Suspend Main Flow</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSuspendMainFlow()
	 * @see #isSuspendMainFlow()
	 * @see #setSuspendMainFlow(boolean)
	 * @generated
	 */
	void unsetSuspendMainFlow();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isSuspendMainFlow <em>Suspend Main Flow</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Suspend Main Flow</em>' attribute is set.
	 * @see #unsetSuspendMainFlow()
	 * @see #isSuspendMainFlow()
	 * @see #setSuspendMainFlow(boolean)
	 * @generated
	 */
	boolean isSetSuspendMainFlow();

	/**
	 * Returns the value of the '<em><b>Allow Multiple Invocations</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If True then the AdHoc activity can be invoked more than once. If False then the Adhoc activity can be invoked only once. If enabled only for Manual AdHoc Activities.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Allow Multiple Invocations</em>' attribute.
	 * @see #isSetAllowMultipleInvocations()
	 * @see #unsetAllowMultipleInvocations()
	 * @see #setAllowMultipleInvocations(boolean)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAdHocTaskConfigurationType_AllowMultipleInvocations()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='AllowMultipleInvocations'"
	 * @generated
	 */
	boolean isAllowMultipleInvocations();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isAllowMultipleInvocations <em>Allow Multiple Invocations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Allow Multiple Invocations</em>' attribute.
	 * @see #isSetAllowMultipleInvocations()
	 * @see #unsetAllowMultipleInvocations()
	 * @see #isAllowMultipleInvocations()
	 * @generated
	 */
	void setAllowMultipleInvocations(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isAllowMultipleInvocations <em>Allow Multiple Invocations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAllowMultipleInvocations()
	 * @see #isAllowMultipleInvocations()
	 * @see #setAllowMultipleInvocations(boolean)
	 * @generated
	 */
	void unsetAllowMultipleInvocations();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#isAllowMultipleInvocations <em>Allow Multiple Invocations</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Allow Multiple Invocations</em>' attribute is set.
	 * @see #unsetAllowMultipleInvocations()
	 * @see #isAllowMultipleInvocations()
	 * @see #setAllowMultipleInvocations(boolean)
	 * @generated
	 */
	boolean isSetAllowMultipleInvocations();

	/**
	 * Returns the value of the '<em><b>Required Access Privileges</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Ad-hoc activity required access privileges.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Required Access Privileges</em>' containment reference.
	 * @see #setRequiredAccessPrivileges(RequiredAccessPrivileges)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAdHocTaskConfigurationType_RequiredAccessPrivileges()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='RequiredAccessPrivileges' namespace='##targetNamespace'"
	 * @generated
	 */
	RequiredAccessPrivileges getRequiredAccessPrivileges();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType#getRequiredAccessPrivileges <em>Required Access Privileges</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Access Privileges</em>' containment reference.
	 * @see #getRequiredAccessPrivileges()
	 * @generated
	 */
	void setRequiredAccessPrivileges(RequiredAccessPrivileges value);

} // AdHocTaskConfigurationType
