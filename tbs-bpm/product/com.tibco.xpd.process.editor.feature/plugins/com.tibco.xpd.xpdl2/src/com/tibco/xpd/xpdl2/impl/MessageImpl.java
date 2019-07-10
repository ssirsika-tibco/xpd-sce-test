/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageImpl#getActualParameters <em>Actual Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageImpl#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageImpl#getFaultName <em>Fault Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageImpl#getFrom <em>From</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.MessageImpl#getTo <em>To</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MessageImpl extends NamedElementImpl implements Message {
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
     * The cached value of the '{@link #getActualParameters() <em>Actual Parameters</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActualParameters()
     * @generated
     * @ordered
     */
    protected EList<Expression> actualParameters;

    /**
     * The cached value of the '{@link #getDataMappings() <em>Data Mappings</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDataMappings()
     * @generated
     * @ordered
     */
    protected EList<DataMapping> dataMappings;

    /**
     * The default value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFaultName()
     * @generated
     * @ordered
     */
    protected static final String FAULT_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFaultName()
     * @generated
     * @ordered
     */
    protected String faultName = FAULT_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getFrom() <em>From</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFrom()
     * @generated
     * @ordered
     */
    protected static final String FROM_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFrom() <em>From</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFrom()
     * @generated
     * @ordered
     */
    protected String from = FROM_EDEFAULT;

    /**
     * The default value of the '{@link #getTo() <em>To</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTo()
     * @generated
     * @ordered
     */
    protected static final String TO_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTo() <em>To</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTo()
     * @generated
     * @ordered
     */
    protected String to = TO_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MessageImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.MESSAGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.MESSAGE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Expression> getActualParameters() {
        if (actualParameters == null) {
            actualParameters = new EObjectContainmentEList<Expression>(Expression.class, this,
                    Xpdl2Package.MESSAGE__ACTUAL_PARAMETERS);
        }
        return actualParameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DataMapping> getDataMappings() {
        if (dataMappings == null) {
            dataMappings = new EObjectContainmentEList<DataMapping>(DataMapping.class, this,
                    Xpdl2Package.MESSAGE__DATA_MAPPINGS);
        }
        return dataMappings;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFaultName() {
        return faultName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFaultName(String newFaultName) {
        String oldFaultName = faultName;
        faultName = newFaultName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.MESSAGE__FAULT_NAME, oldFaultName,
                    faultName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getFrom() {
        return from;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFrom(String newFrom) {
        String oldFrom = from;
        from = newFrom;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.MESSAGE__FROM, oldFrom, from));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getTo() {
        return to;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTo(String newTo) {
        String oldTo = to;
        to = newTo;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.MESSAGE__TO, oldTo, to));
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
        case Xpdl2Package.MESSAGE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.MESSAGE__ACTUAL_PARAMETERS:
            return ((InternalEList<?>) getActualParameters()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.MESSAGE__DATA_MAPPINGS:
            return ((InternalEList<?>) getDataMappings()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.MESSAGE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.MESSAGE__ACTUAL_PARAMETERS:
            return getActualParameters();
        case Xpdl2Package.MESSAGE__DATA_MAPPINGS:
            return getDataMappings();
        case Xpdl2Package.MESSAGE__FAULT_NAME:
            return getFaultName();
        case Xpdl2Package.MESSAGE__FROM:
            return getFrom();
        case Xpdl2Package.MESSAGE__TO:
            return getTo();
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
        case Xpdl2Package.MESSAGE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.MESSAGE__ACTUAL_PARAMETERS:
            getActualParameters().clear();
            getActualParameters().addAll((Collection<? extends Expression>) newValue);
            return;
        case Xpdl2Package.MESSAGE__DATA_MAPPINGS:
            getDataMappings().clear();
            getDataMappings().addAll((Collection<? extends DataMapping>) newValue);
            return;
        case Xpdl2Package.MESSAGE__FAULT_NAME:
            setFaultName((String) newValue);
            return;
        case Xpdl2Package.MESSAGE__FROM:
            setFrom((String) newValue);
            return;
        case Xpdl2Package.MESSAGE__TO:
            setTo((String) newValue);
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
        case Xpdl2Package.MESSAGE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.MESSAGE__ACTUAL_PARAMETERS:
            getActualParameters().clear();
            return;
        case Xpdl2Package.MESSAGE__DATA_MAPPINGS:
            getDataMappings().clear();
            return;
        case Xpdl2Package.MESSAGE__FAULT_NAME:
            setFaultName(FAULT_NAME_EDEFAULT);
            return;
        case Xpdl2Package.MESSAGE__FROM:
            setFrom(FROM_EDEFAULT);
            return;
        case Xpdl2Package.MESSAGE__TO:
            setTo(TO_EDEFAULT);
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
        case Xpdl2Package.MESSAGE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.MESSAGE__ACTUAL_PARAMETERS:
            return actualParameters != null && !actualParameters.isEmpty();
        case Xpdl2Package.MESSAGE__DATA_MAPPINGS:
            return dataMappings != null && !dataMappings.isEmpty();
        case Xpdl2Package.MESSAGE__FAULT_NAME:
            return FAULT_NAME_EDEFAULT == null ? faultName != null : !FAULT_NAME_EDEFAULT.equals(faultName);
        case Xpdl2Package.MESSAGE__FROM:
            return FROM_EDEFAULT == null ? from != null : !FROM_EDEFAULT.equals(from);
        case Xpdl2Package.MESSAGE__TO:
            return TO_EDEFAULT == null ? to != null : !TO_EDEFAULT.equals(to);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.MESSAGE__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.MESSAGE__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(", faultName: "); //$NON-NLS-1$
        result.append(faultName);
        result.append(", from: "); //$NON-NLS-1$
        result.append(from);
        result.append(", to: "); //$NON-NLS-1$
        result.append(to);
        result.append(')');
        return result.toString();
    }

} //MessageImpl
