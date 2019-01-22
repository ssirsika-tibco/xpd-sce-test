/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ColumnDefinition;
import com.tibco.n2.brm.api.ColumnOrder;
import com.tibco.n2.brm.api.N2BRMPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Column Order</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.ColumnOrderImpl#getColumnDef <em>Column Def</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.ColumnOrderImpl#isAscending <em>Ascending</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ColumnOrderImpl extends EObjectImpl implements ColumnOrder {
    /**
     * The cached value of the '{@link #getColumnDef() <em>Column Def</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getColumnDef()
     * @generated
     * @ordered
     */
    protected ColumnDefinition columnDef;

    /**
     * The default value of the '{@link #isAscending() <em>Ascending</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isAscending()
     * @generated
     * @ordered
     */
    protected static final boolean ASCENDING_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAscending() <em>Ascending</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isAscending()
     * @generated
     * @ordered
     */
    protected boolean ascending = ASCENDING_EDEFAULT;

    /**
     * This is true if the Ascending attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean ascendingESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ColumnOrderImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.COLUMN_ORDER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ColumnDefinition getColumnDef() {
        return columnDef;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetColumnDef(ColumnDefinition newColumnDef, NotificationChain msgs) {
        ColumnDefinition oldColumnDef = columnDef;
        columnDef = newColumnDef;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.COLUMN_ORDER__COLUMN_DEF, oldColumnDef, newColumnDef);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setColumnDef(ColumnDefinition newColumnDef) {
        if (newColumnDef != columnDef) {
            NotificationChain msgs = null;
            if (columnDef != null)
                msgs = ((InternalEObject)columnDef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.COLUMN_ORDER__COLUMN_DEF, null, msgs);
            if (newColumnDef != null)
                msgs = ((InternalEObject)newColumnDef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.COLUMN_ORDER__COLUMN_DEF, null, msgs);
            msgs = basicSetColumnDef(newColumnDef, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.COLUMN_ORDER__COLUMN_DEF, newColumnDef, newColumnDef));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isAscending() {
        return ascending;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAscending(boolean newAscending) {
        boolean oldAscending = ascending;
        ascending = newAscending;
        boolean oldAscendingESet = ascendingESet;
        ascendingESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.COLUMN_ORDER__ASCENDING, oldAscending, ascending, !oldAscendingESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAscending() {
        boolean oldAscending = ascending;
        boolean oldAscendingESet = ascendingESet;
        ascending = ASCENDING_EDEFAULT;
        ascendingESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.COLUMN_ORDER__ASCENDING, oldAscending, ASCENDING_EDEFAULT, oldAscendingESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAscending() {
        return ascendingESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.COLUMN_ORDER__COLUMN_DEF:
                return basicSetColumnDef(null, msgs);
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
            case N2BRMPackage.COLUMN_ORDER__COLUMN_DEF:
                return getColumnDef();
            case N2BRMPackage.COLUMN_ORDER__ASCENDING:
                return isAscending();
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
            case N2BRMPackage.COLUMN_ORDER__COLUMN_DEF:
                setColumnDef((ColumnDefinition)newValue);
                return;
            case N2BRMPackage.COLUMN_ORDER__ASCENDING:
                setAscending((Boolean)newValue);
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
            case N2BRMPackage.COLUMN_ORDER__COLUMN_DEF:
                setColumnDef((ColumnDefinition)null);
                return;
            case N2BRMPackage.COLUMN_ORDER__ASCENDING:
                unsetAscending();
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
            case N2BRMPackage.COLUMN_ORDER__COLUMN_DEF:
                return columnDef != null;
            case N2BRMPackage.COLUMN_ORDER__ASCENDING:
                return isSetAscending();
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
        result.append(" (ascending: ");
        if (ascendingESet) result.append(ascending); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //ColumnOrderImpl
