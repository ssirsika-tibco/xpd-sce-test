/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ReferenceImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ReferenceImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ReferenceImpl#getActivityId <em>Activity Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReferenceImpl extends ImplementationImpl implements Reference {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherAttributes()
     * @generated
     * @ordered
     */
    protected FeatureMap otherAttributes;

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
     * The default value of the '{@link #getActivityId() <em>Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityId()
     * @generated
     * @ordered
     */
    protected static final String ACTIVITY_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getActivityId() <em>Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivityId()
     * @generated
     * @ordered
     */
    protected String activityId = ACTIVITY_ID_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ReferenceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.REFERENCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes =
                    new BasicFeatureMap(this,
                            Xpdl2Package.REFERENCE__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements =
                    new BasicFeatureMap(this,
                            Xpdl2Package.REFERENCE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivityId(String newActivityId) {
        String oldActivityId = activityId;
        activityId = newActivityId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.REFERENCE__ACTIVITY_ID, oldActivityId,
                    activityId));
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
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.REFERENCE__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.REFERENCE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.REFERENCE__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.REFERENCE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.REFERENCE__ACTIVITY_ID:
            return getActivityId();
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
        case Xpdl2Package.REFERENCE__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.REFERENCE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.REFERENCE__ACTIVITY_ID:
            setActivityId((String) newValue);
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
        case Xpdl2Package.REFERENCE__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.REFERENCE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.REFERENCE__ACTIVITY_ID:
            setActivityId(ACTIVITY_ID_EDEFAULT);
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
        case Xpdl2Package.REFERENCE__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.REFERENCE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.REFERENCE__ACTIVITY_ID:
            return ACTIVITY_ID_EDEFAULT == null ? activityId != null
                    : !ACTIVITY_ID_EDEFAULT.equals(activityId);
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
        if (baseClass == OtherAttributesContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.REFERENCE__OTHER_ATTRIBUTES:
                return Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (derivedFeatureID) {
            case Xpdl2Package.REFERENCE__OTHER_ELEMENTS:
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
        if (baseClass == OtherAttributesContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ATTRIBUTES_CONTAINER__OTHER_ATTRIBUTES:
                return Xpdl2Package.REFERENCE__OTHER_ATTRIBUTES;
            default:
                return -1;
            }
        }
        if (baseClass == OtherElementsContainer.class) {
            switch (baseFeatureID) {
            case Xpdl2Package.OTHER_ELEMENTS_CONTAINER__OTHER_ELEMENTS:
                return Xpdl2Package.REFERENCE__OTHER_ELEMENTS;
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", activityId: "); //$NON-NLS-1$
        result.append(activityId);
        result.append(')');
        return result.toString();
    }

} //ReferenceImpl
