/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sub Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getActualParameters <em>Actual Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getExecution <em>Execution</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getInstanceDataField <em>Instance Data Field</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getPackageRefId <em>Package Ref Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getStartActivityId <em>Start Activity Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getStartActivitySetId <em>Start Activity Set Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SubFlow#getEndPoint <em>End Point</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow()
 * @model extendedMetaData="name='SubFlow_._type' kind='elementOnly' features-order='actualParameters dataMappings otherAttributes'"
 * @generated
 */
public interface SubFlow extends Implementation, OtherAttributesContainer,
        OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Actual Parameters</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Expression}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Actual Parameters</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Actual Parameters</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_ActualParameters()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ActualParameter' namespace='##targetNamespace' wrap='ActualParameters'"
     * @generated
     */
    EList<Expression> getActualParameters();

    /**
     * Returns the value of the '<em><b>Data Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.DataMapping}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Mappings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_DataMappings()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataMapping' namespace='##targetNamespace' wrap='DataMappings'"
     * @generated
     */
    EList<DataMapping> getDataMappings();

    /**
     * Returns the value of the '<em><b>Execution</b></em>' attribute.
     * The default value is <code>"SYNCHR"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ExecutionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Execution</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Execution</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ExecutionType
     * @see #isSetExecution()
     * @see #unsetExecution()
     * @see #setExecution(ExecutionType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_Execution()
     * @model default="SYNCHR" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Execution'"
     * @generated
     */
    ExecutionType getExecution();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getExecution <em>Execution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Execution</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ExecutionType
     * @see #isSetExecution()
     * @see #unsetExecution()
     * @see #getExecution()
     * @generated
     */
    void setExecution(ExecutionType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getExecution <em>Execution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExecution()
     * @see #getExecution()
     * @see #setExecution(ExecutionType)
     * @generated
     */
    void unsetExecution();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getExecution <em>Execution</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Execution</em>' attribute is set.
     * @see #unsetExecution()
     * @see #getExecution()
     * @see #setExecution(ExecutionType)
     * @generated
     */
    boolean isSetExecution();

    /**
     * Returns the value of the '<em><b>Instance Data Field</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Used to store the instance id of the subflow instantiated by the activity. This is then available later on (e.g. for correlation, messaging etc.) especially in the case of asynchronous invocation.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Instance Data Field</em>' attribute.
     * @see #setInstanceDataField(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_InstanceDataField()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='InstanceDataField'"
     * @generated
     */
    String getInstanceDataField();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getInstanceDataField <em>Instance Data Field</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Instance Data Field</em>' attribute.
     * @see #getInstanceDataField()
     * @generated
     */
    void setInstanceDataField(String value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_ProcessId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='Id'"
     * @generated
     */
    String getProcessId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getProcessId <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Id</em>' attribute.
     * @see #getProcessId()
     * @generated
     */
    void setProcessId(String value);

    /**
     * Returns the value of the '<em><b>Package Ref Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * BPMN: needed for independent subprocess
     * <!-- end-model-doc -->
     * @return the value of the '<em>Package Ref Id</em>' attribute.
     * @see #setPackageRefId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_PackageRefId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='PackageRef'"
     * @generated
     */
    String getPackageRefId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getPackageRefId <em>Package Ref Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package Ref Id</em>' attribute.
     * @see #getPackageRefId()
     * @generated
     */
    void setPackageRefId(String value);

    /**
     * Returns the value of the '<em><b>Start Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Activity Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Activity Id</em>' attribute.
     * @see #setStartActivityId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_StartActivityId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='StartActivityId'"
     * @generated
     */
    String getStartActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getStartActivityId <em>Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Activity Id</em>' attribute.
     * @see #getStartActivityId()
     * @generated
     */
    void setStartActivityId(String value);

    /**
     * Returns the value of the '<em><b>Start Activity Set Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Start Activity Set Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Start Activity Set Id</em>' attribute.
     * @see #setStartActivitySetId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_StartActivitySetId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='StartActivitySetId'"
     * @generated
     */
    String getStartActivitySetId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getStartActivitySetId <em>Start Activity Set Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Start Activity Set Id</em>' attribute.
     * @see #getStartActivitySetId()
     * @generated
     */
    void setStartActivitySetId(String value);

    /**
     * Returns the value of the '<em><b>End Point</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>End Point</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>End Point</em>' containment reference.
     * @see #setEndPoint(EndPoint)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSubFlow_EndPoint()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='EndPoint' namespace='##targetNamespace'"
     * @generated
     */
    EndPoint getEndPoint();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SubFlow#getEndPoint <em>End Point</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Point</em>' containment reference.
     * @see #getEndPoint()
     * @generated
     */
    void setEndPoint(EndPoint value);

} // SubFlow
