/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkTypeList;

import com.tibco.n2.common.datamodel.WorkType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Type List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkTypeListImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkTypeListImpl#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkTypeListImpl#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkTypeListImpl extends EObjectImpl implements WorkTypeList {
    /**
     * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartPosition()
     * @generated
     * @ordered
     */
    protected static final long START_POSITION_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartPosition()
     * @generated
     * @ordered
     */
    protected long startPosition = START_POSITION_EDEFAULT;

    /**
     * This is true if the Start Position attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean startPositionESet;

    /**
     * The default value of the '{@link #getEndPosition() <em>End Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndPosition()
     * @generated
     * @ordered
     */
    protected static final long END_POSITION_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getEndPosition() <em>End Position</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndPosition()
     * @generated
     * @ordered
     */
    protected long endPosition = END_POSITION_EDEFAULT;

    /**
     * This is true if the End Position attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean endPositionESet;

    /**
     * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTypes()
     * @generated
     * @ordered
     */
    protected EList<WorkType> types;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkTypeListImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_TYPE_LIST;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getStartPosition() {
        return startPosition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartPosition(long newStartPosition) {
        long oldStartPosition = startPosition;
        startPosition = newStartPosition;
        boolean oldStartPositionESet = startPositionESet;
        startPositionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_TYPE_LIST__START_POSITION, oldStartPosition, startPosition, !oldStartPositionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetStartPosition() {
        long oldStartPosition = startPosition;
        boolean oldStartPositionESet = startPositionESet;
        startPosition = START_POSITION_EDEFAULT;
        startPositionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_TYPE_LIST__START_POSITION, oldStartPosition, START_POSITION_EDEFAULT, oldStartPositionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStartPosition() {
        return startPositionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getEndPosition() {
        return endPosition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndPosition(long newEndPosition) {
        long oldEndPosition = endPosition;
        endPosition = newEndPosition;
        boolean oldEndPositionESet = endPositionESet;
        endPositionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_TYPE_LIST__END_POSITION, oldEndPosition, endPosition, !oldEndPositionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetEndPosition() {
        long oldEndPosition = endPosition;
        boolean oldEndPositionESet = endPositionESet;
        endPosition = END_POSITION_EDEFAULT;
        endPositionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_TYPE_LIST__END_POSITION, oldEndPosition, END_POSITION_EDEFAULT, oldEndPositionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetEndPosition() {
        return endPositionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<WorkType> getTypes() {
        if (types == null) {
            types = new EObjectContainmentEList<WorkType>(WorkType.class, this, N2BRMPackage.WORK_TYPE_LIST__TYPES);
        }
        return types;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_TYPE_LIST__TYPES:
                return ((InternalEList<?>)getTypes()).basicRemove(otherEnd, msgs);
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
            case N2BRMPackage.WORK_TYPE_LIST__START_POSITION:
                return getStartPosition();
            case N2BRMPackage.WORK_TYPE_LIST__END_POSITION:
                return getEndPosition();
            case N2BRMPackage.WORK_TYPE_LIST__TYPES:
                return getTypes();
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
            case N2BRMPackage.WORK_TYPE_LIST__START_POSITION:
                setStartPosition((Long)newValue);
                return;
            case N2BRMPackage.WORK_TYPE_LIST__END_POSITION:
                setEndPosition((Long)newValue);
                return;
            case N2BRMPackage.WORK_TYPE_LIST__TYPES:
                getTypes().clear();
                getTypes().addAll((Collection<? extends WorkType>)newValue);
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
            case N2BRMPackage.WORK_TYPE_LIST__START_POSITION:
                unsetStartPosition();
                return;
            case N2BRMPackage.WORK_TYPE_LIST__END_POSITION:
                unsetEndPosition();
                return;
            case N2BRMPackage.WORK_TYPE_LIST__TYPES:
                getTypes().clear();
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
            case N2BRMPackage.WORK_TYPE_LIST__START_POSITION:
                return isSetStartPosition();
            case N2BRMPackage.WORK_TYPE_LIST__END_POSITION:
                return isSetEndPosition();
            case N2BRMPackage.WORK_TYPE_LIST__TYPES:
                return types != null && !types.isEmpty();
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
        result.append(" (startPosition: ");
        if (startPositionESet) result.append(startPosition); else result.append("<unset>");
        result.append(", endPosition: ");
        if (endPositionESet) result.append(endPosition); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkTypeListImpl
