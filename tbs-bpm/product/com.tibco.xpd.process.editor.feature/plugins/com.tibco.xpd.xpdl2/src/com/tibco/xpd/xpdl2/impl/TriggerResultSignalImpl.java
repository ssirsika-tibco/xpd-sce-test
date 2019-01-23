/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trigger Result Signal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl#getCatchThrow <em>Catch Throw</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.TriggerResultSignalImpl#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TriggerResultSignalImpl extends EObjectImpl implements
        TriggerResultSignal {
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
     * The default value of the '{@link #getCatchThrow() <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCatchThrow()
     * @generated
     * @ordered
     */
    protected static final CatchThrow CATCH_THROW_EDEFAULT = CatchThrow.CATCH;

    /**
     * The cached value of the '{@link #getCatchThrow() <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCatchThrow()
     * @generated
     * @ordered
     */
    protected CatchThrow catchThrow = CATCH_THROW_EDEFAULT;

    /**
     * This is true if the Catch Throw attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean catchThrowESet;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProperties()
     * @generated
     * @ordered
     */
    protected EList<Expression> properties;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TriggerResultSignalImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.TRIGGER_RESULT_SIGNAL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes =
                    new BasicFeatureMap(
                            this,
                            Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ATTRIBUTES);
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
                            Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Expression> getProperties() {
        if (properties == null) {
            properties =
                    new EObjectContainmentEList<Expression>(Expression.class,
                            this,
                            Xpdl2Package.TRIGGER_RESULT_SIGNAL__PROPERTIES);
        }
        return properties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CatchThrow getCatchThrow() {
        return catchThrow;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCatchThrow(CatchThrow newCatchThrow) {
        CatchThrow oldCatchThrow = catchThrow;
        catchThrow =
                newCatchThrow == null ? CATCH_THROW_EDEFAULT : newCatchThrow;
        boolean oldCatchThrowESet = catchThrowESet;
        catchThrowESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_RESULT_SIGNAL__CATCH_THROW,
                    oldCatchThrow, catchThrow, !oldCatchThrowESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetCatchThrow() {
        CatchThrow oldCatchThrow = catchThrow;
        boolean oldCatchThrowESet = catchThrowESet;
        catchThrow = CATCH_THROW_EDEFAULT;
        catchThrowESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.TRIGGER_RESULT_SIGNAL__CATCH_THROW,
                    oldCatchThrow, CATCH_THROW_EDEFAULT, oldCatchThrowESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetCatchThrow() {
        return catchThrowESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.TRIGGER_RESULT_SIGNAL__NAME, oldName, name));
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
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__PROPERTIES:
            return ((InternalEList<?>) getProperties()).basicRemove(otherEnd,
                    msgs);
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
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__CATCH_THROW:
            return getCatchThrow();
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__NAME:
            return getName();
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__PROPERTIES:
            return getProperties();
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
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__CATCH_THROW:
            setCatchThrow((CatchThrow) newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__NAME:
            setName((String) newValue);
            return;
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__PROPERTIES:
            getProperties().clear();
            getProperties().addAll((Collection<? extends Expression>) newValue);
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
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__CATCH_THROW:
            unsetCatchThrow();
            return;
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__NAME:
            setName(NAME_EDEFAULT);
            return;
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__PROPERTIES:
            getProperties().clear();
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
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__CATCH_THROW:
            return isSetCatchThrow();
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case Xpdl2Package.TRIGGER_RESULT_SIGNAL__PROPERTIES:
            return properties != null && !properties.isEmpty();
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
            case Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS:
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
                return Xpdl2Package.TRIGGER_RESULT_SIGNAL__OTHER_ELEMENTS;
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
        result.append(", catchThrow: "); //$NON-NLS-1$
        if (catchThrowESet)
            result.append(catchThrow);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //TriggerResultSignalImpl
