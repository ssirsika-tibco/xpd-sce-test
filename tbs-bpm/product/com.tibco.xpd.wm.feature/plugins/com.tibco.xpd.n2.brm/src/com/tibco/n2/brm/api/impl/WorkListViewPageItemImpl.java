/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkListViewPageItem;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work List View Page Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewPageItemImpl#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewPageItemImpl#getModificationDate <em>Modification Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkListViewPageItemImpl#getWorkViewID <em>Work View ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkListViewPageItemImpl extends WorkListViewCommonImpl implements WorkListViewPageItem {
    /**
     * The default value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreationDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar CREATION_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreationDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar creationDate = CREATION_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getModificationDate() <em>Modification Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModificationDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar MODIFICATION_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getModificationDate() <em>Modification Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getModificationDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar modificationDate = MODIFICATION_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getWorkViewID() <em>Work View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkViewID()
     * @generated
     * @ordered
     */
    protected static final long WORK_VIEW_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getWorkViewID() <em>Work View ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkViewID()
     * @generated
     * @ordered
     */
    protected long workViewID = WORK_VIEW_ID_EDEFAULT;

    /**
     * This is true if the Work View ID attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean workViewIDESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkListViewPageItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_LIST_VIEW_PAGE_ITEM;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCreationDate(XMLGregorianCalendar newCreationDate) {
        XMLGregorianCalendar oldCreationDate = creationDate;
        creationDate = newCreationDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__CREATION_DATE, oldCreationDate, creationDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getModificationDate() {
        return modificationDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setModificationDate(XMLGregorianCalendar newModificationDate) {
        XMLGregorianCalendar oldModificationDate = modificationDate;
        modificationDate = newModificationDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__MODIFICATION_DATE, oldModificationDate, modificationDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getWorkViewID() {
        return workViewID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkViewID(long newWorkViewID) {
        long oldWorkViewID = workViewID;
        workViewID = newWorkViewID;
        boolean oldWorkViewIDESet = workViewIDESet;
        workViewIDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID, oldWorkViewID, workViewID, !oldWorkViewIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetWorkViewID() {
        long oldWorkViewID = workViewID;
        boolean oldWorkViewIDESet = workViewIDESet;
        workViewID = WORK_VIEW_ID_EDEFAULT;
        workViewIDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID, oldWorkViewID, WORK_VIEW_ID_EDEFAULT, oldWorkViewIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetWorkViewID() {
        return workViewIDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__CREATION_DATE:
                return getCreationDate();
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__MODIFICATION_DATE:
                return getModificationDate();
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID:
                return getWorkViewID();
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
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__CREATION_DATE:
                setCreationDate((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__MODIFICATION_DATE:
                setModificationDate((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID:
                setWorkViewID((Long)newValue);
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
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__CREATION_DATE:
                setCreationDate(CREATION_DATE_EDEFAULT);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__MODIFICATION_DATE:
                setModificationDate(MODIFICATION_DATE_EDEFAULT);
                return;
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID:
                unsetWorkViewID();
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
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__CREATION_DATE:
                return CREATION_DATE_EDEFAULT == null ? creationDate != null : !CREATION_DATE_EDEFAULT.equals(creationDate);
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__MODIFICATION_DATE:
                return MODIFICATION_DATE_EDEFAULT == null ? modificationDate != null : !MODIFICATION_DATE_EDEFAULT.equals(modificationDate);
            case N2BRMPackage.WORK_LIST_VIEW_PAGE_ITEM__WORK_VIEW_ID:
                return isSetWorkViewID();
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
        result.append(" (creationDate: ");
        result.append(creationDate);
        result.append(", modificationDate: ");
        result.append(modificationDate);
        result.append(", workViewID: ");
        if (workViewIDESet) result.append(workViewID); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkListViewPageItemImpl
