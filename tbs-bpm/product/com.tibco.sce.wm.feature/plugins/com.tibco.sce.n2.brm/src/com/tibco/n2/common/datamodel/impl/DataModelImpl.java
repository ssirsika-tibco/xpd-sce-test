/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.common.datamodel.DataModel;
import com.tibco.n2.common.datamodel.DatamodelPackage;
import com.tibco.n2.common.datamodel.FieldType;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Data Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.DataModelImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.DataModelImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.DataModelImpl#getInouts <em>Inouts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DataModelImpl extends EObjectImpl implements DataModel {
    /**
     * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInputs()
     * @generated
     * @ordered
     */
    protected EList<FieldType> inputs;

    /**
     * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutputs()
     * @generated
     * @ordered
     */
    protected EList<FieldType> outputs;

    /**
     * The cached value of the '{@link #getInouts() <em>Inouts</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInouts()
     * @generated
     * @ordered
     */
    protected EList<FieldType> inouts;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DataModelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatamodelPackage.Literals.DATA_MODEL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FieldType> getInputs() {
        if (inputs == null) {
            inputs = new EObjectContainmentEList<FieldType>(FieldType.class, this, DatamodelPackage.DATA_MODEL__INPUTS);
        }
        return inputs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FieldType> getOutputs() {
        if (outputs == null) {
            outputs = new EObjectContainmentEList<FieldType>(FieldType.class, this, DatamodelPackage.DATA_MODEL__OUTPUTS);
        }
        return outputs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<FieldType> getInouts() {
        if (inouts == null) {
            inouts = new EObjectContainmentEList<FieldType>(FieldType.class, this, DatamodelPackage.DATA_MODEL__INOUTS);
        }
        return inouts;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DatamodelPackage.DATA_MODEL__INPUTS:
                return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
            case DatamodelPackage.DATA_MODEL__OUTPUTS:
                return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
            case DatamodelPackage.DATA_MODEL__INOUTS:
                return ((InternalEList<?>)getInouts()).basicRemove(otherEnd, msgs);
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
            case DatamodelPackage.DATA_MODEL__INPUTS:
                return getInputs();
            case DatamodelPackage.DATA_MODEL__OUTPUTS:
                return getOutputs();
            case DatamodelPackage.DATA_MODEL__INOUTS:
                return getInouts();
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
            case DatamodelPackage.DATA_MODEL__INPUTS:
                getInputs().clear();
                getInputs().addAll((Collection<? extends FieldType>)newValue);
                return;
            case DatamodelPackage.DATA_MODEL__OUTPUTS:
                getOutputs().clear();
                getOutputs().addAll((Collection<? extends FieldType>)newValue);
                return;
            case DatamodelPackage.DATA_MODEL__INOUTS:
                getInouts().clear();
                getInouts().addAll((Collection<? extends FieldType>)newValue);
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
            case DatamodelPackage.DATA_MODEL__INPUTS:
                getInputs().clear();
                return;
            case DatamodelPackage.DATA_MODEL__OUTPUTS:
                getOutputs().clear();
                return;
            case DatamodelPackage.DATA_MODEL__INOUTS:
                getInouts().clear();
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
            case DatamodelPackage.DATA_MODEL__INPUTS:
                return inputs != null && !inputs.isEmpty();
            case DatamodelPackage.DATA_MODEL__OUTPUTS:
                return outputs != null && !outputs.isEmpty();
            case DatamodelPackage.DATA_MODEL__INOUTS:
                return inouts != null && !inouts.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //DataModelImpl
