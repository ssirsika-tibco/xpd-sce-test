/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getTestpoints <em>Testpoints</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getAssertions <em>Assertions</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getInput <em>Input</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getOutput <em>Output</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getState <em>State</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getErrorInformation <em>Error Information</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getIntermediateInputs <em>Intermediate Inputs</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getModelType <em>Model Type</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.ProcessNode#getMultiInputNodes <em>Multi Input Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode()
 * @model extendedMetaData="name='ProcessNode_._type' kind='elementOnly'"
 * @generated
 */
public interface ProcessNode extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Testpoints</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.bx.emulation.model.Testpoint}.
	 * It is bidirectional and its opposite is '{@link com.tibco.bx.emulation.model.Testpoint#getProcessNode <em>Process Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Testpoints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Testpoints</em>' containment reference list.
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_Testpoints()
	 * @see com.tibco.bx.emulation.model.Testpoint#getProcessNode
	 * @model opposite="processNode" containment="true" keys="id"
	 *        extendedMetaData="name='Testpoint' kind='element' wrap='Testpoints'"
	 * @generated
	 */
	EList<Testpoint> getTestpoints();

	/**
	 * Returns the value of the '<em><b>Assertions</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.bx.emulation.model.Assertion}.
	 * It is bidirectional and its opposite is '{@link com.tibco.bx.emulation.model.Assertion#getProcessNode <em>Process Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertions</em>' containment reference list.
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_Assertions()
	 * @see com.tibco.bx.emulation.model.Assertion#getProcessNode
	 * @model opposite="processNode" containment="true" keys="id"
	 *        extendedMetaData="name='Assertion' kind='element' wrap='Assertions'"
	 * @generated
	 */
	EList<Assertion> getAssertions();

	/**
	 * Returns the value of the '<em><b>Input</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input</em>' containment reference.
	 * @see #setInput(Input)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_Input()
	 * @model containment="true"
	 *        extendedMetaData="name='Input' kind='element'"
	 * @generated
	 */
	Input getInput();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ProcessNode#getInput <em>Input</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input</em>' containment reference.
	 * @see #getInput()
	 * @generated
	 */
	void setInput(Input value);

	/**
	 * Returns the value of the '<em><b>Output</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output</em>' containment reference.
	 * @see #setOutput(Output)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_Output()
	 * @model containment="true"
	 *        extendedMetaData="name='Output' kind='element'"
	 * @generated
	 */
	Output getOutput();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ProcessNode#getOutput <em>Output</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output</em>' containment reference.
	 * @see #getOutput()
	 * @generated
	 */
	void setOutput(Output value);

	/**
	 * Returns the value of the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alias</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias</em>' attribute.
	 * @see #setAlias(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_Alias()
	 * @model extendedMetaData="name='Alias' kind='attribute'"
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ProcessNode#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_Description()
	 * @model extendedMetaData="name='Description' kind='attribute'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ProcessNode#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * The default value is <code>"COMPLETED"</code>.
	 * The literals are from the enumeration {@link com.tibco.bx.emulation.model.StateType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see com.tibco.bx.emulation.model.StateType
	 * @see #setState(StateType)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_State()
	 * @model default="COMPLETED"
	 *        extendedMetaData="name='State' kind='attribute'"
	 * @generated
	 */
	StateType getState();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ProcessNode#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see com.tibco.bx.emulation.model.StateType
	 * @see #getState()
	 * @generated
	 */
	void setState(StateType value);

	/**
	 * Returns the value of the '<em><b>Error Information</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Error Information</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Error Information</em>' containment reference.
	 * @see #setErrorInformation(ErrorInformation)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_ErrorInformation()
	 * @model containment="true"
	 *        extendedMetaData="name='ErrorInformation' kind='element'"
	 * @generated
	 */
	ErrorInformation getErrorInformation();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ProcessNode#getErrorInformation <em>Error Information</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Error Information</em>' containment reference.
	 * @see #getErrorInformation()
	 * @generated
	 */
	void setErrorInformation(ErrorInformation value);

	/**
	 * Returns the value of the '<em><b>Intermediate Inputs</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.bx.emulation.model.IntermediateInput}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intermediate Inputs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intermediate Inputs</em>' containment reference list.
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_IntermediateInputs()
	 * @model containment="true"
	 *        extendedMetaData="name='IntermediateInput' kind='element' wrap='IntermediateInputs'"
	 * @generated
	 */
	EList<IntermediateInput> getIntermediateInputs();
	
	/**
	 * Returns the value of the '<em><b>Model Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Type</em>' attribute.
	 * @see #setModelType(String)
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_ModelType()
	 * @model default=""
	 *        extendedMetaData="name='ModelType' kind='attribute'"
	 * @generated
	 */
	String getModelType();

	/**
	 * Sets the value of the '{@link com.tibco.bx.emulation.model.ProcessNode#getModelType <em>Model Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Type</em>' attribute.
	 * @see #getModelType()
	 * @generated
	 */
	void setModelType(String value);

	/**
	 * Returns the value of the '<em><b>Multi Input Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.bx.emulation.model.MultiInput}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Input Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Input Nodes</em>' containment reference list.
	 * @see com.tibco.bx.emulation.model.EmulationPackage#getProcessNode_MultiInputNodes()
	 * @model containment="true"
	 *        extendedMetaData="name='MultiInput' kind='element' wrap='MultiInputs'"
	 * @generated
	 */
	EList<MultiInput> getMultiInputNodes();

	/**
	 * 
	 * @param id
	 * @return
	 * @generated not
	 */
	IntermediateInput getIntermediateById(String id);

} // ProcessNode
