/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.DurationUnitType;
import com.tibco.xpd.xpdl2.Limit;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.TimeEstimation;
import com.tibco.xpd.xpdl2.ValidFrom;
import com.tibco.xpd.xpdl2.ValidTo;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Header</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl#getCreated <em>Created</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl#getLimit <em>Limit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl#getValidFrom <em>Valid From</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl#getValidTo <em>Valid To</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl#getTimeEstimation <em>Time Estimation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ProcessHeaderImpl#getDurationUnit <em>Duration Unit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProcessHeaderImpl extends EObjectImpl implements ProcessHeader {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected Description description;

    /**
     * The default value of the '{@link #getCreated() <em>Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreated()
     * @generated
     * @ordered
     */
    protected static final String CREATED_EDEFAULT = null; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCreated() <em>Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCreated()
     * @generated
     * @ordered
     */
    protected String created = CREATED_EDEFAULT;

    /**
     * The cached value of the '{@link #getPriority() <em>Priority</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPriority()
     * @generated
     * @ordered
     */
    protected Priority priority;

    /**
     * The cached value of the '{@link #getLimit() <em>Limit</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLimit()
     * @generated
     * @ordered
     */
    protected Limit limit;

    /**
     * The cached value of the '{@link #getValidFrom() <em>Valid From</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValidFrom()
     * @generated
     * @ordered
     */
    protected ValidFrom validFrom;

    /**
     * The cached value of the '{@link #getValidTo() <em>Valid To</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getValidTo()
     * @generated
     * @ordered
     */
    protected ValidTo validTo;

    /**
     * The cached value of the '{@link #getTimeEstimation() <em>Time Estimation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTimeEstimation()
     * @generated
     * @ordered
     */
    protected TimeEstimation timeEstimation;

    /**
     * The default value of the '{@link #getDurationUnit() <em>Duration Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDurationUnit()
     * @generated
     * @ordered
     */
    protected static final DurationUnitType DURATION_UNIT_EDEFAULT = DurationUnitType.YEAR_LITERAL;

    /**
     * The cached value of the '{@link #getDurationUnit() <em>Duration Unit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDurationUnit()
     * @generated
     * @ordered
     */
    protected DurationUnitType durationUnit = DURATION_UNIT_EDEFAULT;

    /**
     * This is true if the Duration Unit attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean durationUnitESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ProcessHeaderImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.PROCESS_HEADER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getCreated() {
        return created;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCreated(String newCreated) {
        String oldCreated = created;
        created = newCreated;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PROCESS_HEADER__CREATED, oldCreated,
                    created));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Description getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDescription(Description newDescription, NotificationChain msgs) {
        Description oldDescription = description;
        description = newDescription;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS_HEADER__DESCRIPTION, oldDescription, newDescription);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(Description newDescription) {
        if (newDescription != description) {
            NotificationChain msgs = null;
            if (description != null)
                msgs = ((InternalEObject) description).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__DESCRIPTION,
                        null,
                        msgs);
            if (newDescription != null)
                msgs = ((InternalEObject) newDescription).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__DESCRIPTION,
                        null,
                        msgs);
            msgs = basicSetDescription(newDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PROCESS_HEADER__DESCRIPTION,
                    newDescription, newDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPriority(Priority newPriority, NotificationChain msgs) {
        Priority oldPriority = priority;
        priority = newPriority;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS_HEADER__PRIORITY, oldPriority, newPriority);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPriority(Priority newPriority) {
        if (newPriority != priority) {
            NotificationChain msgs = null;
            if (priority != null)
                msgs = ((InternalEObject) priority).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__PRIORITY,
                        null,
                        msgs);
            if (newPriority != null)
                msgs = ((InternalEObject) newPriority)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__PRIORITY, null, msgs);
            msgs = basicSetPriority(newPriority, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PROCESS_HEADER__PRIORITY, newPriority,
                    newPriority));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Limit getLimit() {
        return limit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetLimit(Limit newLimit, NotificationChain msgs) {
        Limit oldLimit = limit;
        limit = newLimit;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS_HEADER__LIMIT, oldLimit, newLimit);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLimit(Limit newLimit) {
        if (newLimit != limit) {
            NotificationChain msgs = null;
            if (limit != null)
                msgs = ((InternalEObject) limit)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__LIMIT, null, msgs);
            if (newLimit != null)
                msgs = ((InternalEObject) newLimit)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__LIMIT, null, msgs);
            msgs = basicSetLimit(newLimit, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PROCESS_HEADER__LIMIT, newLimit,
                    newLimit));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ValidFrom getValidFrom() {
        return validFrom;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetValidFrom(ValidFrom newValidFrom, NotificationChain msgs) {
        ValidFrom oldValidFrom = validFrom;
        validFrom = newValidFrom;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS_HEADER__VALID_FROM, oldValidFrom, newValidFrom);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setValidFrom(ValidFrom newValidFrom) {
        if (newValidFrom != validFrom) {
            NotificationChain msgs = null;
            if (validFrom != null)
                msgs = ((InternalEObject) validFrom).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__VALID_FROM,
                        null,
                        msgs);
            if (newValidFrom != null)
                msgs = ((InternalEObject) newValidFrom).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__VALID_FROM,
                        null,
                        msgs);
            msgs = basicSetValidFrom(newValidFrom, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PROCESS_HEADER__VALID_FROM, newValidFrom,
                    newValidFrom));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ValidTo getValidTo() {
        return validTo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetValidTo(ValidTo newValidTo, NotificationChain msgs) {
        ValidTo oldValidTo = validTo;
        validTo = newValidTo;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS_HEADER__VALID_TO, oldValidTo, newValidTo);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setValidTo(ValidTo newValidTo) {
        if (newValidTo != validTo) {
            NotificationChain msgs = null;
            if (validTo != null)
                msgs = ((InternalEObject) validTo).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__VALID_TO,
                        null,
                        msgs);
            if (newValidTo != null)
                msgs = ((InternalEObject) newValidTo)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__VALID_TO, null, msgs);
            msgs = basicSetValidTo(newValidTo, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PROCESS_HEADER__VALID_TO, newValidTo,
                    newValidTo));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TimeEstimation getTimeEstimation() {
        return timeEstimation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTimeEstimation(TimeEstimation newTimeEstimation, NotificationChain msgs) {
        TimeEstimation oldTimeEstimation = timeEstimation;
        timeEstimation = newTimeEstimation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION, oldTimeEstimation, newTimeEstimation);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTimeEstimation(TimeEstimation newTimeEstimation) {
        if (newTimeEstimation != timeEstimation) {
            NotificationChain msgs = null;
            if (timeEstimation != null)
                msgs = ((InternalEObject) timeEstimation).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION,
                        null,
                        msgs);
            if (newTimeEstimation != null)
                msgs = ((InternalEObject) newTimeEstimation).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION,
                        null,
                        msgs);
            msgs = basicSetTimeEstimation(newTimeEstimation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION,
                    newTimeEstimation, newTimeEstimation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DurationUnitType getDurationUnit() {
        return durationUnit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDurationUnit(DurationUnitType newDurationUnit) {
        DurationUnitType oldDurationUnit = durationUnit;
        durationUnit = newDurationUnit == null ? DURATION_UNIT_EDEFAULT : newDurationUnit;
        boolean oldDurationUnitESet = durationUnitESet;
        durationUnitESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.PROCESS_HEADER__DURATION_UNIT,
                    oldDurationUnit, durationUnit, !oldDurationUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDurationUnit() {
        DurationUnitType oldDurationUnit = durationUnit;
        boolean oldDurationUnitESet = durationUnitESet;
        durationUnit = DURATION_UNIT_EDEFAULT;
        durationUnitESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, Xpdl2Package.PROCESS_HEADER__DURATION_UNIT,
                    oldDurationUnit, DURATION_UNIT_EDEFAULT, oldDurationUnitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDurationUnit() {
        return durationUnitESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.PROCESS_HEADER__DESCRIPTION:
            return basicSetDescription(null, msgs);
        case Xpdl2Package.PROCESS_HEADER__PRIORITY:
            return basicSetPriority(null, msgs);
        case Xpdl2Package.PROCESS_HEADER__LIMIT:
            return basicSetLimit(null, msgs);
        case Xpdl2Package.PROCESS_HEADER__VALID_FROM:
            return basicSetValidFrom(null, msgs);
        case Xpdl2Package.PROCESS_HEADER__VALID_TO:
            return basicSetValidTo(null, msgs);
        case Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION:
            return basicSetTimeEstimation(null, msgs);
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
        case Xpdl2Package.PROCESS_HEADER__DESCRIPTION:
            return getDescription();
        case Xpdl2Package.PROCESS_HEADER__CREATED:
            return getCreated();
        case Xpdl2Package.PROCESS_HEADER__PRIORITY:
            return getPriority();
        case Xpdl2Package.PROCESS_HEADER__LIMIT:
            return getLimit();
        case Xpdl2Package.PROCESS_HEADER__VALID_FROM:
            return getValidFrom();
        case Xpdl2Package.PROCESS_HEADER__VALID_TO:
            return getValidTo();
        case Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION:
            return getTimeEstimation();
        case Xpdl2Package.PROCESS_HEADER__DURATION_UNIT:
            return getDurationUnit();
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
        case Xpdl2Package.PROCESS_HEADER__DESCRIPTION:
            setDescription((Description) newValue);
            return;
        case Xpdl2Package.PROCESS_HEADER__CREATED:
            setCreated((String) newValue);
            return;
        case Xpdl2Package.PROCESS_HEADER__PRIORITY:
            setPriority((Priority) newValue);
            return;
        case Xpdl2Package.PROCESS_HEADER__LIMIT:
            setLimit((Limit) newValue);
            return;
        case Xpdl2Package.PROCESS_HEADER__VALID_FROM:
            setValidFrom((ValidFrom) newValue);
            return;
        case Xpdl2Package.PROCESS_HEADER__VALID_TO:
            setValidTo((ValidTo) newValue);
            return;
        case Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION:
            setTimeEstimation((TimeEstimation) newValue);
            return;
        case Xpdl2Package.PROCESS_HEADER__DURATION_UNIT:
            setDurationUnit((DurationUnitType) newValue);
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
        case Xpdl2Package.PROCESS_HEADER__DESCRIPTION:
            setDescription((Description) null);
            return;
        case Xpdl2Package.PROCESS_HEADER__CREATED:
            setCreated(CREATED_EDEFAULT);
            return;
        case Xpdl2Package.PROCESS_HEADER__PRIORITY:
            setPriority((Priority) null);
            return;
        case Xpdl2Package.PROCESS_HEADER__LIMIT:
            setLimit((Limit) null);
            return;
        case Xpdl2Package.PROCESS_HEADER__VALID_FROM:
            setValidFrom((ValidFrom) null);
            return;
        case Xpdl2Package.PROCESS_HEADER__VALID_TO:
            setValidTo((ValidTo) null);
            return;
        case Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION:
            setTimeEstimation((TimeEstimation) null);
            return;
        case Xpdl2Package.PROCESS_HEADER__DURATION_UNIT:
            unsetDurationUnit();
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
        case Xpdl2Package.PROCESS_HEADER__DESCRIPTION:
            return description != null;
        case Xpdl2Package.PROCESS_HEADER__CREATED:
            return CREATED_EDEFAULT == null ? created != null : !CREATED_EDEFAULT.equals(created);
        case Xpdl2Package.PROCESS_HEADER__PRIORITY:
            return priority != null;
        case Xpdl2Package.PROCESS_HEADER__LIMIT:
            return limit != null;
        case Xpdl2Package.PROCESS_HEADER__VALID_FROM:
            return validFrom != null;
        case Xpdl2Package.PROCESS_HEADER__VALID_TO:
            return validTo != null;
        case Xpdl2Package.PROCESS_HEADER__TIME_ESTIMATION:
            return timeEstimation != null;
        case Xpdl2Package.PROCESS_HEADER__DURATION_UNIT:
            return isSetDurationUnit();
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
        result.append(" (created: "); //$NON-NLS-1$
        result.append(created);
        result.append(", durationUnit: "); //$NON-NLS-1$
        if (durationUnitESet)
            result.append(durationUnit);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //ProcessHeaderImpl
