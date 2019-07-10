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

import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataFieldsContainer;
import com.tibco.xpd.xpdl2.DataObject;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Data Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataObjectImpl#getDataFields <em>Data Fields</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataObjectImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataObjectImpl#isDeprecatedProducedAtCompletion <em>Deprecated Produced At Completion</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataObjectImpl#isDeprecatedRequiredForStart <em>Deprecated Required For Start</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.DataObjectImpl#getState <em>State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DataObjectImpl extends NamedElementImpl implements DataObject {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDataFields() <em>Data Fields</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getDataFields()
     * @generated
     * @ordered
     */
    protected EList<DataField> dataFields;

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The default value of the '{@link #isDeprecatedProducedAtCompletion() <em>Deprecated Produced At Completion</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeprecatedProducedAtCompletion()
     * @generated
     * @ordered
     */
    protected static final boolean DEPRECATED_PRODUCED_AT_COMPLETION_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDeprecatedProducedAtCompletion() <em>Deprecated Produced At Completion</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeprecatedProducedAtCompletion()
     * @generated
     * @ordered
     */
    protected boolean deprecatedProducedAtCompletion = DEPRECATED_PRODUCED_AT_COMPLETION_EDEFAULT;

    /**
     * This is true if the Deprecated Produced At Completion attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean deprecatedProducedAtCompletionESet;

    /**
     * The default value of the '{@link #isDeprecatedRequiredForStart() <em>Deprecated Required For Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeprecatedRequiredForStart()
     * @generated
     * @ordered
     */
    protected static final boolean DEPRECATED_REQUIRED_FOR_START_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDeprecatedRequiredForStart() <em>Deprecated Required For Start</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeprecatedRequiredForStart()
     * @generated
     * @ordered
     */
    protected boolean deprecatedRequiredForStart = DEPRECATED_REQUIRED_FOR_START_EDEFAULT;

    /**
     * This is true if the Deprecated Required For Start attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean deprecatedRequiredForStartESet;

    /**
     * The default value of the '{@link #getState() <em>State</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getState()
     * @generated
     * @ordered
     */
    protected static final String STATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getState() <em>State</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getState()
     * @generated
     * @ordered
     */
    protected String state = STATE_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected DataObjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.DATA_OBJECT;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<DataField> getDataFields() {
        if (dataFields == null) {
            dataFields = new EObjectContainmentEList<DataField>(DataField.class, this,
                    Xpdl2Package.DATA_OBJECT__DATA_FIELDS);
        }
        return dataFields;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements = new BasicFeatureMap(this, Xpdl2Package.DATA_OBJECT__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isDeprecatedProducedAtCompletion() {
        return deprecatedProducedAtCompletion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedProducedAtCompletion(boolean newDeprecatedProducedAtCompletion) {
        boolean oldDeprecatedProducedAtCompletion = deprecatedProducedAtCompletion;
        deprecatedProducedAtCompletion = newDeprecatedProducedAtCompletion;
        boolean oldDeprecatedProducedAtCompletionESet = deprecatedProducedAtCompletionESet;
        deprecatedProducedAtCompletionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DATA_OBJECT__DEPRECATED_PRODUCED_AT_COMPLETION, oldDeprecatedProducedAtCompletion,
                    deprecatedProducedAtCompletion, !oldDeprecatedProducedAtCompletionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDeprecatedProducedAtCompletion() {
        boolean oldDeprecatedProducedAtCompletion = deprecatedProducedAtCompletion;
        boolean oldDeprecatedProducedAtCompletionESet = deprecatedProducedAtCompletionESet;
        deprecatedProducedAtCompletion = DEPRECATED_PRODUCED_AT_COMPLETION_EDEFAULT;
        deprecatedProducedAtCompletionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.DATA_OBJECT__DEPRECATED_PRODUCED_AT_COMPLETION, oldDeprecatedProducedAtCompletion,
                    DEPRECATED_PRODUCED_AT_COMPLETION_EDEFAULT, oldDeprecatedProducedAtCompletionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDeprecatedProducedAtCompletion() {
        return deprecatedProducedAtCompletionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isDeprecatedRequiredForStart() {
        return deprecatedRequiredForStart;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedRequiredForStart(boolean newDeprecatedRequiredForStart) {
        boolean oldDeprecatedRequiredForStart = deprecatedRequiredForStart;
        deprecatedRequiredForStart = newDeprecatedRequiredForStart;
        boolean oldDeprecatedRequiredForStartESet = deprecatedRequiredForStartESet;
        deprecatedRequiredForStartESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.DATA_OBJECT__DEPRECATED_REQUIRED_FOR_START, oldDeprecatedRequiredForStart,
                    deprecatedRequiredForStart, !oldDeprecatedRequiredForStartESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDeprecatedRequiredForStart() {
        boolean oldDeprecatedRequiredForStart = deprecatedRequiredForStart;
        boolean oldDeprecatedRequiredForStartESet = deprecatedRequiredForStartESet;
        deprecatedRequiredForStart = DEPRECATED_REQUIRED_FOR_START_EDEFAULT;
        deprecatedRequiredForStartESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.DATA_OBJECT__DEPRECATED_REQUIRED_FOR_START, oldDeprecatedRequiredForStart,
                    DEPRECATED_REQUIRED_FOR_START_EDEFAULT, oldDeprecatedRequiredForStartESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDeprecatedRequiredForStart() {
        return deprecatedRequiredForStartESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getState() {
        return state;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setState(String newState) {
        String oldState = state;
        state = newState;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.DATA_OBJECT__STATE, oldState, state));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EObject getOtherElement(String elementName) {
        return Xpdl2Operations.getOtherElement(this, elementName);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public DataField getDataField(String id) {
        return (DataField) EMFSearchUtil
                .findInList(getDataFields(), Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), id);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.DATA_OBJECT__DATA_FIELDS:
            return ((InternalEList<?>) getDataFields()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.DATA_OBJECT__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.DATA_OBJECT__DATA_FIELDS:
            return getDataFields();
        case Xpdl2Package.DATA_OBJECT__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.DATA_OBJECT__DEPRECATED_PRODUCED_AT_COMPLETION:
            return isDeprecatedProducedAtCompletion();
        case Xpdl2Package.DATA_OBJECT__DEPRECATED_REQUIRED_FOR_START:
            return isDeprecatedRequiredForStart();
        case Xpdl2Package.DATA_OBJECT__STATE:
            return getState();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case Xpdl2Package.DATA_OBJECT__DATA_FIELDS:
            getDataFields().clear();
            getDataFields().addAll((Collection<? extends DataField>) newValue);
            return;
        case Xpdl2Package.DATA_OBJECT__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.DATA_OBJECT__DEPRECATED_PRODUCED_AT_COMPLETION:
            setDeprecatedProducedAtCompletion((Boolean) newValue);
            return;
        case Xpdl2Package.DATA_OBJECT__DEPRECATED_REQUIRED_FOR_START:
            setDeprecatedRequiredForStart((Boolean) newValue);
            return;
        case Xpdl2Package.DATA_OBJECT__STATE:
            setState((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case Xpdl2Package.DATA_OBJECT__DATA_FIELDS:
            getDataFields().clear();
            return;
        case Xpdl2Package.DATA_OBJECT__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.DATA_OBJECT__DEPRECATED_PRODUCED_AT_COMPLETION:
            unsetDeprecatedProducedAtCompletion();
            return;
        case Xpdl2Package.DATA_OBJECT__DEPRECATED_REQUIRED_FOR_START:
            unsetDeprecatedRequiredForStart();
            return;
        case Xpdl2Package.DATA_OBJECT__STATE:
            setState(STATE_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case Xpdl2Package.DATA_OBJECT__DATA_FIELDS:
            return dataFields != null && !dataFields.isEmpty();
        case Xpdl2Package.DATA_OBJECT__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.DATA_OBJECT__DEPRECATED_PRODUCED_AT_COMPLETION:
            return isSetDeprecatedProducedAtCompletion();
        case Xpdl2Package.DATA_OBJECT__DEPRECATED_REQUIRED_FOR_START:
            return isSetDeprecatedRequiredForStart();
        case Xpdl2Package.DATA_OBJECT__STATE:
            return STATE_EDEFAULT == null ? state != null : !STATE_EDEFAULT.equals(state);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == DataFieldsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.DATA_OBJECT__DATA_FIELDS:
                return Xpdl2Package.DATA_FIELDS_CONTAINER__DATA_FIELDS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.DATA_OBJECT__OTHER_ELEMENTS:
                return Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == DataFieldsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.DATA_FIELDS_CONTAINER__DATA_FIELDS:
                return Xpdl2Package.DATA_OBJECT__DATA_FIELDS;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.DATA_OBJECT__OTHER_ELEMENTS;
            default:
                return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", deprecatedProducedAtCompletion: "); //$NON-NLS-1$
        if (deprecatedProducedAtCompletionESet)
            result.append(deprecatedProducedAtCompletion);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", deprecatedRequiredForStart: "); //$NON-NLS-1$
        if (deprecatedRequiredForStartESet)
            result.append(deprecatedRequiredForStart);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", state: "); //$NON-NLS-1$
        result.append(state);
        result.append(')');
        return result.toString();
    }

} // DataObjectImpl
