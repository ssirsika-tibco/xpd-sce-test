/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.script.descriptor;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.script.descriptor.ProcessType#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.xpd.script.descriptor.ProcessType#getPackageId <em>Package Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getProcessType()
 * @model extendedMetaData="name='process_._type' kind='elementOnly'"
 * @generated
 */
public interface ProcessType extends EObject {
    /**
     * Returns the value of the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Id</em>' attribute.
     * @see #setProcessId(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getProcessType_ProcessId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='element' name='processId'"
     * @generated
     */
    String getProcessId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.ProcessType#getProcessId <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Id</em>' attribute.
     * @see #getProcessId()
     * @generated
     */
    void setProcessId(String value);

    /**
     * Returns the value of the '<em><b>Package Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package Id</em>' attribute.
     * @see #setPackageId(String)
     * @see com.tibco.xpd.script.descriptor.DescriptorPackage#getProcessType_PackageId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='element' name='packageId'"
     * @generated
     */
    String getPackageId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.script.descriptor.ProcessType#getPackageId <em>Package Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package Id</em>' attribute.
     * @see #getPackageId()
     * @generated
     */
    void setPackageId(String value);

} // ProcessType
