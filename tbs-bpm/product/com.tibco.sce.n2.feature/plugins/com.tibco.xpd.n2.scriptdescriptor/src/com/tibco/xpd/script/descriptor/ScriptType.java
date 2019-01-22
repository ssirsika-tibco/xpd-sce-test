/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Script Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a ScriptType.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.ScriptType#getProcess <em>Process</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.ScriptType#getTaskLibraryId <em>Task Library Id</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.ScriptType#getFactory <em>Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.ScriptType#getEnum <em>Enum</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.ScriptType#getCac <em>Cac</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptType()
 * @model extendedMetaData="name='ScriptType' kind='elementOnly'"
 * @generated
 */
public interface ScriptType extends EObject {
    /**
     * Returns the value of the '<em><b>Process</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process</em>' containment reference.
     * @see #setProcess(ProcessType)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptType_Process()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='process'"
     * @generated
     */
    ProcessType getProcess();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.ScriptType#getProcess <em>Process</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process</em>' containment reference.
     * @see #getProcess()
     * @generated
     */
    void setProcess(ProcessType value);

    /**
     * Returns the value of the '<em><b>Task Library Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Task Library Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Task Library Id</em>' attribute.
     * @see #setTaskLibraryId(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptType_TaskLibraryId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
     *        extendedMetaData="kind='element' name='taskLibraryId'"
     * @generated
     */
    String getTaskLibraryId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.ScriptType#getTaskLibraryId <em>Task Library Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Task Library Id</em>' attribute.
     * @see #getTaskLibraryId()
     * @generated
     */
    void setTaskLibraryId(String value);

    /**
     * Returns the value of the '<em><b>Factory</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.script.descriptor.FactoryType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Factory</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of factory types used by this script.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Factory</em>' containment reference list.
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptType_Factory()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='factory'"
     * @generated
     */
    EList<FactoryType> getFactory();

    /**
     * Returns the value of the '<em><b>Enum</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.script.descriptor.EnumType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enum</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of enum types used by this script.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Enum</em>' containment reference list.
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptType_Enum()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='enum'"
     * @generated
     */
    EList<EnumType> getEnum();

    /**
     * Returns the value of the '<em><b>Cac</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.script.descriptor.CacType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of Case Access Class(es) used by these script(s).
     * <!-- end-model-doc -->
     * @return the value of the '<em>Cac</em>' containment reference list.
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getScriptType_Cac()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='cac'"
     * @generated
     */
    EList<CacType> getCac();

} // ScriptType
