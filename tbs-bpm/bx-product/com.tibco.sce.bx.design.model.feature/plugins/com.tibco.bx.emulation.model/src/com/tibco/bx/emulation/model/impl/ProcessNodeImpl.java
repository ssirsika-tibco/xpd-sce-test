/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.bx.emulation.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.ErrorInformation;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.StateType;
import com.tibco.bx.emulation.model.Testpoint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getTestpoints <em>Testpoints</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getAssertions <em>Assertions</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getInput <em>Input</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getOutput <em>Output</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getState <em>State</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getErrorInformation <em>Error Information</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getIntermediateInputs <em>Intermediate Inputs</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getModelType <em>Model Type</em>}</li>
 *   <li>{@link com.tibco.bx.emulation.model.impl.ProcessNodeImpl#getMultiInputNodes <em>Multi Input Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessNodeImpl extends NamedElementImpl implements ProcessNode {
	/**
	 * The cached value of the '{@link #getTestpoints() <em>Testpoints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestpoints()
	 * @generated
	 * @ordered
	 */
	protected EList<Testpoint> testpoints;

	/**
	 * The cached value of the '{@link #getAssertions() <em>Assertions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertions()
	 * @generated
	 * @ordered
	 */
	protected EList<Assertion> assertions;

	/**
	 * The cached value of the '{@link #getInput() <em>Input</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInput()
	 * @generated
	 * @ordered
	 */
	protected Input input;

	/**
	 * The cached value of the '{@link #getOutput() <em>Output</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutput()
	 * @generated
	 * @ordered
	 */
	protected Output output;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final StateType STATE_EDEFAULT = StateType.COMPLETED;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected StateType state = STATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getErrorInformation() <em>Error Information</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorInformation()
	 * @generated
	 * @ordered
	 */
	protected ErrorInformation errorInformation;

	/**
	 * The cached value of the '{@link #getIntermediateInputs() <em>Intermediate Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntermediateInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<IntermediateInput> intermediateInputs;

	/**
	 * The default value of the '{@link #getModelType() <em>Model Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelType()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_TYPE_EDEFAULT = ""; //$NON-NLS-1$

	/**
	 * The cached value of the '{@link #getModelType() <em>Model Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelType()
	 * @generated
	 * @ordered
	 */
	protected String modelType = MODEL_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMultiInputNodes() <em>Multi Input Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiInputNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<MultiInput> multiInputNodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmulationPackage.Literals.PROCESS_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Testpoint> getTestpoints() {
		if (testpoints == null) {
			testpoints = new EObjectContainmentWithInverseEList<Testpoint>(Testpoint.class, this, EmulationPackage.PROCESS_NODE__TESTPOINTS, EmulationPackage.TESTPOINT__PROCESS_NODE);
		}
		return testpoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Assertion> getAssertions() {
		if (assertions == null) {
			assertions = new EObjectContainmentWithInverseEList<Assertion>(Assertion.class, this, EmulationPackage.PROCESS_NODE__ASSERTIONS, EmulationPackage.ASSERTION__PROCESS_NODE);
		}
		return assertions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Input getInput() {
		return input;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInput(Input newInput, NotificationChain msgs) {
		Input oldInput = input;
		input = newInput;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__INPUT, oldInput, newInput);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInput(Input newInput) {
		if (newInput != input) {
			NotificationChain msgs = null;
			if (input != null)
				msgs = ((InternalEObject)input).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EmulationPackage.PROCESS_NODE__INPUT, null, msgs);
			if (newInput != null)
				msgs = ((InternalEObject)newInput).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EmulationPackage.PROCESS_NODE__INPUT, null, msgs);
			msgs = basicSetInput(newInput, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__INPUT, newInput, newInput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Output getOutput() {
		return output;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutput(Output newOutput, NotificationChain msgs) {
		Output oldOutput = output;
		output = newOutput;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__OUTPUT, oldOutput, newOutput);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutput(Output newOutput) {
		if (newOutput != output) {
			NotificationChain msgs = null;
			if (output != null)
				msgs = ((InternalEObject)output).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EmulationPackage.PROCESS_NODE__OUTPUT, null, msgs);
			if (newOutput != null)
				msgs = ((InternalEObject)newOutput).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EmulationPackage.PROCESS_NODE__OUTPUT, null, msgs);
			msgs = basicSetOutput(newOutput, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__OUTPUT, newOutput, newOutput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateType getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(StateType newState) {
		StateType oldState = state;
		state = newState == null ? STATE_EDEFAULT : newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__STATE, oldState, state));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorInformation getErrorInformation() {
		return errorInformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetErrorInformation(ErrorInformation newErrorInformation, NotificationChain msgs) {
		ErrorInformation oldErrorInformation = errorInformation;
		errorInformation = newErrorInformation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__ERROR_INFORMATION, oldErrorInformation, newErrorInformation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setErrorInformation(ErrorInformation newErrorInformation) {
		if (newErrorInformation != errorInformation) {
			NotificationChain msgs = null;
			if (errorInformation != null)
				msgs = ((InternalEObject)errorInformation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EmulationPackage.PROCESS_NODE__ERROR_INFORMATION, null, msgs);
			if (newErrorInformation != null)
				msgs = ((InternalEObject)newErrorInformation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EmulationPackage.PROCESS_NODE__ERROR_INFORMATION, null, msgs);
			msgs = basicSetErrorInformation(newErrorInformation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__ERROR_INFORMATION, newErrorInformation, newErrorInformation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IntermediateInput> getIntermediateInputs() {
		if (intermediateInputs == null) {
			intermediateInputs = new EObjectContainmentEList<IntermediateInput>(IntermediateInput.class, this, EmulationPackage.PROCESS_NODE__INTERMEDIATE_INPUTS);
		}
		return intermediateInputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModelType() {
		return modelType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelType(String newModelType) {
		String oldModelType = modelType;
		modelType = newModelType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmulationPackage.PROCESS_NODE__MODEL_TYPE, oldModelType, modelType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MultiInput> getMultiInputNodes() {
		if (multiInputNodes == null) {
			multiInputNodes = new EObjectContainmentEList<MultiInput>(MultiInput.class, this, EmulationPackage.PROCESS_NODE__MULTI_INPUT_NODES);
		}
		return multiInputNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmulationPackage.PROCESS_NODE__TESTPOINTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTestpoints()).basicAdd(otherEnd, msgs);
			case EmulationPackage.PROCESS_NODE__ASSERTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAssertions()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmulationPackage.PROCESS_NODE__TESTPOINTS:
				return ((InternalEList<?>)getTestpoints()).basicRemove(otherEnd, msgs);
			case EmulationPackage.PROCESS_NODE__ASSERTIONS:
				return ((InternalEList<?>)getAssertions()).basicRemove(otherEnd, msgs);
			case EmulationPackage.PROCESS_NODE__INPUT:
				return basicSetInput(null, msgs);
			case EmulationPackage.PROCESS_NODE__OUTPUT:
				return basicSetOutput(null, msgs);
			case EmulationPackage.PROCESS_NODE__ERROR_INFORMATION:
				return basicSetErrorInformation(null, msgs);
			case EmulationPackage.PROCESS_NODE__INTERMEDIATE_INPUTS:
				return ((InternalEList<?>)getIntermediateInputs()).basicRemove(otherEnd, msgs);
			case EmulationPackage.PROCESS_NODE__MULTI_INPUT_NODES:
				return ((InternalEList<?>)getMultiInputNodes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmulationPackage.PROCESS_NODE__TESTPOINTS:
				return getTestpoints();
			case EmulationPackage.PROCESS_NODE__ASSERTIONS:
				return getAssertions();
			case EmulationPackage.PROCESS_NODE__INPUT:
				return getInput();
			case EmulationPackage.PROCESS_NODE__OUTPUT:
				return getOutput();
			case EmulationPackage.PROCESS_NODE__ALIAS:
				return getAlias();
			case EmulationPackage.PROCESS_NODE__DESCRIPTION:
				return getDescription();
			case EmulationPackage.PROCESS_NODE__STATE:
				return getState();
			case EmulationPackage.PROCESS_NODE__ERROR_INFORMATION:
				return getErrorInformation();
			case EmulationPackage.PROCESS_NODE__INTERMEDIATE_INPUTS:
				return getIntermediateInputs();
			case EmulationPackage.PROCESS_NODE__MODEL_TYPE:
				return getModelType();
			case EmulationPackage.PROCESS_NODE__MULTI_INPUT_NODES:
				return getMultiInputNodes();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EmulationPackage.PROCESS_NODE__TESTPOINTS:
				getTestpoints().clear();
				getTestpoints().addAll((Collection<? extends Testpoint>)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__ASSERTIONS:
				getAssertions().clear();
				getAssertions().addAll((Collection<? extends Assertion>)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__INPUT:
				setInput((Input)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__OUTPUT:
				setOutput((Output)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__ALIAS:
				setAlias((String)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__STATE:
				setState((StateType)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__ERROR_INFORMATION:
				setErrorInformation((ErrorInformation)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__INTERMEDIATE_INPUTS:
				getIntermediateInputs().clear();
				getIntermediateInputs().addAll((Collection<? extends IntermediateInput>)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__MODEL_TYPE:
				setModelType((String)newValue);
				return;
			case EmulationPackage.PROCESS_NODE__MULTI_INPUT_NODES:
				getMultiInputNodes().clear();
				getMultiInputNodes().addAll((Collection<? extends MultiInput>)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case EmulationPackage.PROCESS_NODE__TESTPOINTS:
				getTestpoints().clear();
				return;
			case EmulationPackage.PROCESS_NODE__ASSERTIONS:
				getAssertions().clear();
				return;
			case EmulationPackage.PROCESS_NODE__INPUT:
				setInput((Input)null);
				return;
			case EmulationPackage.PROCESS_NODE__OUTPUT:
				setOutput((Output)null);
				return;
			case EmulationPackage.PROCESS_NODE__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case EmulationPackage.PROCESS_NODE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case EmulationPackage.PROCESS_NODE__STATE:
				setState(STATE_EDEFAULT);
				return;
			case EmulationPackage.PROCESS_NODE__ERROR_INFORMATION:
				setErrorInformation((ErrorInformation)null);
				return;
			case EmulationPackage.PROCESS_NODE__INTERMEDIATE_INPUTS:
				getIntermediateInputs().clear();
				return;
			case EmulationPackage.PROCESS_NODE__MODEL_TYPE:
				setModelType(MODEL_TYPE_EDEFAULT);
				return;
			case EmulationPackage.PROCESS_NODE__MULTI_INPUT_NODES:
				getMultiInputNodes().clear();
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EmulationPackage.PROCESS_NODE__TESTPOINTS:
				return testpoints != null && !testpoints.isEmpty();
			case EmulationPackage.PROCESS_NODE__ASSERTIONS:
				return assertions != null && !assertions.isEmpty();
			case EmulationPackage.PROCESS_NODE__INPUT:
				return input != null;
			case EmulationPackage.PROCESS_NODE__OUTPUT:
				return output != null;
			case EmulationPackage.PROCESS_NODE__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case EmulationPackage.PROCESS_NODE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case EmulationPackage.PROCESS_NODE__STATE:
				return state != STATE_EDEFAULT;
			case EmulationPackage.PROCESS_NODE__ERROR_INFORMATION:
				return errorInformation != null;
			case EmulationPackage.PROCESS_NODE__INTERMEDIATE_INPUTS:
				return intermediateInputs != null && !intermediateInputs.isEmpty();
			case EmulationPackage.PROCESS_NODE__MODEL_TYPE:
				return MODEL_TYPE_EDEFAULT == null ? modelType != null : !MODEL_TYPE_EDEFAULT.equals(modelType);
			case EmulationPackage.PROCESS_NODE__MULTI_INPUT_NODES:
				return multiInputNodes != null && !multiInputNodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (alias: "); //$NON-NLS-1$
		result.append(alias);
		result.append(", description: "); //$NON-NLS-1$
		result.append(description);
		result.append(", state: "); //$NON-NLS-1$
		result.append(state);
		result.append(", modelType: "); //$NON-NLS-1$
		result.append(modelType);
		result.append(')');
		return result.toString();
	}

	/**
	 * @generated not
	 */
	@Override
	public IntermediateInput getIntermediateById(String id) {

		for(IntermediateInput input : getIntermediateInputs()){
			if(id == input.getId()){
				return input;
			}
		}
		return null;
	}

} //ProcessNodeImpl
