/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Process Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Defines deployment targets for a Service Process
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToProcessRuntime <em>Deploy To Process Runtime</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToPageflowRuntime <em>Deploy To Pageflow Runtime</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getServiceProcessConfiguration()
 * @model extendedMetaData="name='ServiceProcessConfiguration' kind='empty'"
 * @generated
 */
public interface ServiceProcessConfiguration extends EObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Deploy To Process Runtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deploy To Process Runtime</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deploy To Process Runtime</em>' attribute.
	 * @see #isSetDeployToProcessRuntime()
	 * @see #unsetDeployToProcessRuntime()
	 * @see #setDeployToProcessRuntime(boolean)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getServiceProcessConfiguration_DeployToProcessRuntime()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='attribute' name='DeployToProcessRuntime' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isDeployToProcessRuntime();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToProcessRuntime <em>Deploy To Process Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deploy To Process Runtime</em>' attribute.
	 * @see #isSetDeployToProcessRuntime()
	 * @see #unsetDeployToProcessRuntime()
	 * @see #isDeployToProcessRuntime()
	 * @generated
	 */
	void setDeployToProcessRuntime(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToProcessRuntime <em>Deploy To Process Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDeployToProcessRuntime()
	 * @see #isDeployToProcessRuntime()
	 * @see #setDeployToProcessRuntime(boolean)
	 * @generated
	 */
	void unsetDeployToProcessRuntime();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToProcessRuntime <em>Deploy To Process Runtime</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Deploy To Process Runtime</em>' attribute is set.
	 * @see #unsetDeployToProcessRuntime()
	 * @see #isDeployToProcessRuntime()
	 * @see #setDeployToProcessRuntime(boolean)
	 * @generated
	 */
	boolean isSetDeployToProcessRuntime();

	/**
	 * Returns the value of the '<em><b>Deploy To Pageflow Runtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deploy To Pageflow Runtime</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deploy To Pageflow Runtime</em>' attribute.
	 * @see #isSetDeployToPageflowRuntime()
	 * @see #unsetDeployToPageflowRuntime()
	 * @see #setDeployToPageflowRuntime(boolean)
	 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getServiceProcessConfiguration_DeployToPageflowRuntime()
	 * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='attribute' name='DeployToPageflowRuntime' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isDeployToPageflowRuntime();

	/**
	 * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToPageflowRuntime <em>Deploy To Pageflow Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deploy To Pageflow Runtime</em>' attribute.
	 * @see #isSetDeployToPageflowRuntime()
	 * @see #unsetDeployToPageflowRuntime()
	 * @see #isDeployToPageflowRuntime()
	 * @generated
	 */
	void setDeployToPageflowRuntime(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToPageflowRuntime <em>Deploy To Pageflow Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDeployToPageflowRuntime()
	 * @see #isDeployToPageflowRuntime()
	 * @see #setDeployToPageflowRuntime(boolean)
	 * @generated
	 */
	void unsetDeployToPageflowRuntime();

	/**
	 * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ServiceProcessConfiguration#isDeployToPageflowRuntime <em>Deploy To Pageflow Runtime</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Deploy To Pageflow Runtime</em>' attribute is set.
	 * @see #unsetDeployToPageflowRuntime()
	 * @see #isDeployToPageflowRuntime()
	 * @see #setDeployToPageflowRuntime(boolean)
	 * @generated
	 */
	boolean isSetDeployToPageflowRuntime();

} // ServiceProcessConfiguration
