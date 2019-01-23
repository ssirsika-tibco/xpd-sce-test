/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api.impl;

import com.tibco.n2.brm.api.ItemContext;
import com.tibco.n2.brm.api.N2BRMPackage;
import com.tibco.n2.brm.api.WorkItemFlags;
import com.tibco.n2.brm.api.WorkItemHeader;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Item Header</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemHeaderImpl#getFlags <em>Flags</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemHeaderImpl#getItemContext <em>Item Context</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemHeaderImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.impl.WorkItemHeaderImpl#getStartDate <em>Start Date</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkItemHeaderImpl extends BaseItemInfoImpl implements WorkItemHeader {
    /**
     * The cached value of the '{@link #getFlags() <em>Flags</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFlags()
     * @generated
     * @ordered
     */
    protected WorkItemFlags flags;

    /**
     * The cached value of the '{@link #getItemContext() <em>Item Context</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getItemContext()
     * @generated
     * @ordered
     */
    protected ItemContext itemContext;

    /**
     * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar END_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEndDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar endDate = END_DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected static final XMLGregorianCalendar START_DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getStartDate() <em>Start Date</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStartDate()
     * @generated
     * @ordered
     */
    protected XMLGregorianCalendar startDate = START_DATE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkItemHeaderImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return N2BRMPackage.Literals.WORK_ITEM_HEADER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkItemFlags getFlags() {
        return flags;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetFlags(WorkItemFlags newFlags, NotificationChain msgs) {
        WorkItemFlags oldFlags = flags;
        flags = newFlags;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_HEADER__FLAGS, oldFlags, newFlags);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setFlags(WorkItemFlags newFlags) {
        if (newFlags != flags) {
            NotificationChain msgs = null;
            if (flags != null)
                msgs = ((InternalEObject)flags).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_HEADER__FLAGS, null, msgs);
            if (newFlags != null)
                msgs = ((InternalEObject)newFlags).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_HEADER__FLAGS, null, msgs);
            msgs = basicSetFlags(newFlags, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_HEADER__FLAGS, newFlags, newFlags));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ItemContext getItemContext() {
        return itemContext;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetItemContext(ItemContext newItemContext, NotificationChain msgs) {
        ItemContext oldItemContext = itemContext;
        itemContext = newItemContext;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT, oldItemContext, newItemContext);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setItemContext(ItemContext newItemContext) {
        if (newItemContext != itemContext) {
            NotificationChain msgs = null;
            if (itemContext != null)
                msgs = ((InternalEObject)itemContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT, null, msgs);
            if (newItemContext != null)
                msgs = ((InternalEObject)newItemContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT, null, msgs);
            msgs = basicSetItemContext(newItemContext, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT, newItemContext, newItemContext));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEndDate(XMLGregorianCalendar newEndDate) {
        XMLGregorianCalendar oldEndDate = endDate;
        endDate = newEndDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_HEADER__END_DATE, oldEndDate, endDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartDate(XMLGregorianCalendar newStartDate) {
        XMLGregorianCalendar oldStartDate = startDate;
        startDate = newStartDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, N2BRMPackage.WORK_ITEM_HEADER__START_DATE, oldStartDate, startDate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case N2BRMPackage.WORK_ITEM_HEADER__FLAGS:
                return basicSetFlags(null, msgs);
            case N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT:
                return basicSetItemContext(null, msgs);
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
            case N2BRMPackage.WORK_ITEM_HEADER__FLAGS:
                return getFlags();
            case N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT:
                return getItemContext();
            case N2BRMPackage.WORK_ITEM_HEADER__END_DATE:
                return getEndDate();
            case N2BRMPackage.WORK_ITEM_HEADER__START_DATE:
                return getStartDate();
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
            case N2BRMPackage.WORK_ITEM_HEADER__FLAGS:
                setFlags((WorkItemFlags)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT:
                setItemContext((ItemContext)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_HEADER__END_DATE:
                setEndDate((XMLGregorianCalendar)newValue);
                return;
            case N2BRMPackage.WORK_ITEM_HEADER__START_DATE:
                setStartDate((XMLGregorianCalendar)newValue);
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
            case N2BRMPackage.WORK_ITEM_HEADER__FLAGS:
                setFlags((WorkItemFlags)null);
                return;
            case N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT:
                setItemContext((ItemContext)null);
                return;
            case N2BRMPackage.WORK_ITEM_HEADER__END_DATE:
                setEndDate(END_DATE_EDEFAULT);
                return;
            case N2BRMPackage.WORK_ITEM_HEADER__START_DATE:
                setStartDate(START_DATE_EDEFAULT);
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
            case N2BRMPackage.WORK_ITEM_HEADER__FLAGS:
                return flags != null;
            case N2BRMPackage.WORK_ITEM_HEADER__ITEM_CONTEXT:
                return itemContext != null;
            case N2BRMPackage.WORK_ITEM_HEADER__END_DATE:
                return END_DATE_EDEFAULT == null ? endDate != null : !END_DATE_EDEFAULT.equals(endDate);
            case N2BRMPackage.WORK_ITEM_HEADER__START_DATE:
                return START_DATE_EDEFAULT == null ? startDate != null : !START_DATE_EDEFAULT.equals(startDate);
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
        result.append(" (endDate: ");
        result.append(endDate);
        result.append(", startDate: ");
        result.append(startDate);
        result.append(')');
        return result.toString();
    }

} //WorkItemHeaderImpl
