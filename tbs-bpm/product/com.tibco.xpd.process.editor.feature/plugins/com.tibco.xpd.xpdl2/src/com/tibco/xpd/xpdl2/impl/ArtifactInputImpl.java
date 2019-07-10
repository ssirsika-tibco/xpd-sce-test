/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.ArtifactInput;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact Input</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactInputImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactInputImpl#getArtifactId <em>Artifact Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ArtifactInputImpl#isRequiredForStart <em>Required For Start</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArtifactInputImpl extends EObjectImpl implements ArtifactInput {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The default value of the '{@link #getArtifactId() <em>Artifact Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getArtifactId()
     * @generated
     * @ordered
     */
    protected static final String ARTIFACT_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getArtifactId() <em>Artifact Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getArtifactId()
     * @generated
     * @ordered
     */
    protected String artifactId = ARTIFACT_ID_EDEFAULT;

    /**
     * The default value of the '{@link #isRequiredForStart() <em>Required For Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isRequiredForStart()
     * @generated
     * @ordered
     */
    protected static final boolean REQUIRED_FOR_START_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isRequiredForStart() <em>Required For Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isRequiredForStart()
     * @generated
     * @ordered
     */
    protected boolean requiredForStart = REQUIRED_FOR_START_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ArtifactInputImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ARTIFACT_INPUT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.ARTIFACT_INPUT__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setArtifactId(String newArtifactId) {
        String oldArtifactId = artifactId;
        artifactId = newArtifactId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ARTIFACT_INPUT__ARTIFACT_ID,
                    oldArtifactId, artifactId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isRequiredForStart() {
        return requiredForStart;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRequiredForStart(boolean newRequiredForStart) {
        boolean oldRequiredForStart = requiredForStart;
        requiredForStart = newRequiredForStart;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.ARTIFACT_INPUT__REQUIRED_FOR_START,
                    oldRequiredForStart, requiredForStart));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getOtherElement(String elementName) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.ARTIFACT_INPUT__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.ARTIFACT_INPUT__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.ARTIFACT_INPUT__ARTIFACT_ID:
            return getArtifactId();
        case Xpdl2Package.ARTIFACT_INPUT__REQUIRED_FOR_START:
            return isRequiredForStart();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.ARTIFACT_INPUT__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.ARTIFACT_INPUT__ARTIFACT_ID:
            setArtifactId((String) newValue);
            return;
        case Xpdl2Package.ARTIFACT_INPUT__REQUIRED_FOR_START:
            setRequiredForStart((Boolean) newValue);
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
        case Xpdl2Package.ARTIFACT_INPUT__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.ARTIFACT_INPUT__ARTIFACT_ID:
            setArtifactId(ARTIFACT_ID_EDEFAULT);
            return;
        case Xpdl2Package.ARTIFACT_INPUT__REQUIRED_FOR_START:
            setRequiredForStart(REQUIRED_FOR_START_EDEFAULT);
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
        case Xpdl2Package.ARTIFACT_INPUT__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.ARTIFACT_INPUT__ARTIFACT_ID:
            return ARTIFACT_ID_EDEFAULT == null ? artifactId != null : !ARTIFACT_ID_EDEFAULT.equals(artifactId);
        case Xpdl2Package.ARTIFACT_INPUT__REQUIRED_FOR_START:
            return requiredForStart != REQUIRED_FOR_START_EDEFAULT;
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
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", artifactId: "); //$NON-NLS-1$
        result.append(artifactId);
        result.append(", requiredForStart: "); //$NON-NLS-1$
        result.append(requiredForStart);
        result.append(')');
        return result.toString();
    }

} //ArtifactInputImpl
