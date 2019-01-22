/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Model Script</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a work model script
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelScript#getScriptBody <em>Script Body</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguage <em>Script Language</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguageExtension <em>Script Language Extension</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguageVersion <em>Script Language Version</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelScript#getScriptOperation <em>Script Operation</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkModelScript#getScriptTypeID <em>Script Type ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScript()
 * @model extendedMetaData="name='WorkModelScript' kind='empty'"
 * @generated
 */
public interface WorkModelScript extends EObject {
    /**
     * Returns the value of the '<em><b>Script Body</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The body of the script to be run on the specified work item operation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script Body</em>' attribute.
     * @see #setScriptBody(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScript_ScriptBody()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='scriptBody'"
     * @generated
     */
    String getScriptBody();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptBody <em>Script Body</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script Body</em>' attribute.
     * @see #getScriptBody()
     * @generated
     */
    void setScriptBody(String value);

    /**
     * Returns the value of the '<em><b>Script Language</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.brm.api.WorkItemScriptType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The language of the script to be run e.g. JSCRIPT, JYTHON etc...
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script Language</em>' attribute.
     * @see com.tibco.n2.brm.api.WorkItemScriptType
     * @see #isSetScriptLanguage()
     * @see #unsetScriptLanguage()
     * @see #setScriptLanguage(WorkItemScriptType)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScript_ScriptLanguage()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='scriptLanguage'"
     * @generated
     */
    WorkItemScriptType getScriptLanguage();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguage <em>Script Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script Language</em>' attribute.
     * @see com.tibco.n2.brm.api.WorkItemScriptType
     * @see #isSetScriptLanguage()
     * @see #unsetScriptLanguage()
     * @see #getScriptLanguage()
     * @generated
     */
    void setScriptLanguage(WorkItemScriptType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguage <em>Script Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetScriptLanguage()
     * @see #getScriptLanguage()
     * @see #setScriptLanguage(WorkItemScriptType)
     * @generated
     */
    void unsetScriptLanguage();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguage <em>Script Language</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Script Language</em>' attribute is set.
     * @see #unsetScriptLanguage()
     * @see #getScriptLanguage()
     * @see #setScriptLanguage(WorkItemScriptType)
     * @generated
     */
    boolean isSetScriptLanguage();

    /**
     * Returns the value of the '<em><b>Script Language Extension</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The file extension of the language of the script to be run e.g. js, py etc...
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script Language Extension</em>' attribute.
     * @see #setScriptLanguageExtension(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScript_ScriptLanguageExtension()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='scriptLanguageExtension'"
     * @generated
     */
    String getScriptLanguageExtension();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguageExtension <em>Script Language Extension</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script Language Extension</em>' attribute.
     * @see #getScriptLanguageExtension()
     * @generated
     */
    void setScriptLanguageExtension(String value);

    /**
     * Returns the value of the '<em><b>Script Language Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The version of the language of the script to be run e.g. 2.2.1, 1.2a etc...
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script Language Version</em>' attribute.
     * @see #setScriptLanguageVersion(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScript_ScriptLanguageVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='scriptLanguageVersion'"
     * @generated
     */
    String getScriptLanguageVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptLanguageVersion <em>Script Language Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script Language Version</em>' attribute.
     * @see #getScriptLanguageVersion()
     * @generated
     */
    void setScriptLanguageVersion(String value);

    /**
     * Returns the value of the '<em><b>Script Operation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * What work item operation is the script for e.g. OPEN, CLOSE, COMPLETE etc...
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script Operation</em>' attribute.
     * @see #setScriptOperation(WorkItemScriptOperation)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScript_ScriptOperation()
     * @model dataType="com.tibco.n2.brm.api.ScriptOperationType" required="true"
     *        extendedMetaData="kind='attribute' name='scriptOperation'"
     * @generated
     */
    WorkItemScriptOperation getScriptOperation();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptOperation <em>Script Operation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script Operation</em>' attribute.
     * @see #getScriptOperation()
     * @generated
     */
    void setScriptOperation(WorkItemScriptOperation value);

    /**
     * Returns the value of the '<em><b>Script Type ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The ID of the script type used during the use of complex fields in scripts.
     * 					This will either be populated with a task library ID or process ID.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script Type ID</em>' attribute.
     * @see #setScriptTypeID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkModelScript_ScriptTypeID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='scriptTypeID'"
     * @generated
     */
    String getScriptTypeID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkModelScript#getScriptTypeID <em>Script Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script Type ID</em>' attribute.
     * @see #getScriptTypeID()
     * @generated
     */
    void setScriptTypeID(String value);

} // WorkModelScript
