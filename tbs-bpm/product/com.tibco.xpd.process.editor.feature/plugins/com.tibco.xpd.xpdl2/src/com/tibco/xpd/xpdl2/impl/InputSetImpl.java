/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.ArtifactInput;
import com.tibco.xpd.xpdl2.Input;
import com.tibco.xpd.xpdl2.InputSet;
import com.tibco.xpd.xpdl2.PropertyInput;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.InputSetImpl#getInput <em>Input</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.InputSetImpl#getArtifactInput <em>Artifact Input</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.InputSetImpl#getPropertyInput <em>Property Input</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InputSetImpl extends EObjectImpl implements InputSet {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getInput() <em>Input</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInput()
     * @generated
     * @ordered
     */
    protected EList<Input> input;

    /**
     * The cached value of the '{@link #getArtifactInput() <em>Artifact Input</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getArtifactInput()
     * @generated
     * @ordered
     */
    protected EList<ArtifactInput> artifactInput;

    /**
     * The cached value of the '{@link #getPropertyInput() <em>Property Input</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPropertyInput()
     * @generated
     * @ordered
     */
    protected EList<PropertyInput> propertyInput;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected InputSetImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.INPUT_SET;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Input> getInput() {
        if (input == null) {
            input = new EObjectContainmentEList<Input>(Input.class, this, Xpdl2Package.INPUT_SET__INPUT);
        }
        return input;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ArtifactInput> getArtifactInput() {
        if (artifactInput == null) {
            artifactInput = new EObjectContainmentEList<ArtifactInput>(ArtifactInput.class, this,
                    Xpdl2Package.INPUT_SET__ARTIFACT_INPUT);
        }
        return artifactInput;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<PropertyInput> getPropertyInput() {
        if (propertyInput == null) {
            propertyInput = new EObjectContainmentEList<PropertyInput>(PropertyInput.class, this,
                    Xpdl2Package.INPUT_SET__PROPERTY_INPUT);
        }
        return propertyInput;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.INPUT_SET__INPUT:
            return ((InternalEList<?>) getInput()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.INPUT_SET__ARTIFACT_INPUT:
            return ((InternalEList<?>) getArtifactInput()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.INPUT_SET__PROPERTY_INPUT:
            return ((InternalEList<?>) getPropertyInput()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.INPUT_SET__INPUT:
            return getInput();
        case Xpdl2Package.INPUT_SET__ARTIFACT_INPUT:
            return getArtifactInput();
        case Xpdl2Package.INPUT_SET__PROPERTY_INPUT:
            return getPropertyInput();
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
        case Xpdl2Package.INPUT_SET__INPUT:
            getInput().clear();
            getInput().addAll((Collection<? extends Input>) newValue);
            return;
        case Xpdl2Package.INPUT_SET__ARTIFACT_INPUT:
            getArtifactInput().clear();
            getArtifactInput().addAll((Collection<? extends ArtifactInput>) newValue);
            return;
        case Xpdl2Package.INPUT_SET__PROPERTY_INPUT:
            getPropertyInput().clear();
            getPropertyInput().addAll((Collection<? extends PropertyInput>) newValue);
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
        case Xpdl2Package.INPUT_SET__INPUT:
            getInput().clear();
            return;
        case Xpdl2Package.INPUT_SET__ARTIFACT_INPUT:
            getArtifactInput().clear();
            return;
        case Xpdl2Package.INPUT_SET__PROPERTY_INPUT:
            getPropertyInput().clear();
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
        case Xpdl2Package.INPUT_SET__INPUT:
            return input != null && !input.isEmpty();
        case Xpdl2Package.INPUT_SET__ARTIFACT_INPUT:
            return artifactInput != null && !artifactInput.isEmpty();
        case Xpdl2Package.INPUT_SET__PROPERTY_INPUT:
            return propertyInput != null && !propertyInput.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //InputSetImpl
