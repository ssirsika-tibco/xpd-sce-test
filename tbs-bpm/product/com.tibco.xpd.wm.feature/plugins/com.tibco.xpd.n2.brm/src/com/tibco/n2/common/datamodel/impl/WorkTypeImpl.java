/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.datamodel.impl;

import com.tibco.n2.common.datamodel.DataModel;
import com.tibco.n2.common.datamodel.DatamodelPackage;
import com.tibco.n2.common.datamodel.WorkType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Work Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#getWorkTypeID <em>Work Type ID</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#getWorkTypeUID <em>Work Type UID</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#getWorkTypeDescription <em>Work Type Description</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#getDataModel <em>Data Model</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#isTypePiled <em>Type Piled</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#getPilingLimit <em>Piling Limit</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#isIgnoreIncomingData <em>Ignore Incoming Data</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#isReofferOnClose <em>Reoffer On Close</em>}</li>
 *   <li>{@link com.tibco.n2.common.datamodel.impl.WorkTypeImpl#isReofferOnCancel <em>Reoffer On Cancel</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkTypeImpl extends EObjectImpl implements WorkType {
    /**
     * The default value of the '{@link #getWorkTypeID() <em>Work Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeID()
     * @generated
     * @ordered
     */
    protected static final long WORK_TYPE_ID_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getWorkTypeID() <em>Work Type ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeID()
     * @generated
     * @ordered
     */
    protected long workTypeID = WORK_TYPE_ID_EDEFAULT;

    /**
     * This is true if the Work Type ID attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean workTypeIDESet;

    /**
     * The default value of the '{@link #getWorkTypeUID() <em>Work Type UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeUID()
     * @generated
     * @ordered
     */
    protected static final String WORK_TYPE_UID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkTypeUID() <em>Work Type UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeUID()
     * @generated
     * @ordered
     */
    protected String workTypeUID = WORK_TYPE_UID_EDEFAULT;

    /**
     * The default value of the '{@link #getWorkTypeDescription() <em>Work Type Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeDescription()
     * @generated
     * @ordered
     */
    protected static final String WORK_TYPE_DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWorkTypeDescription() <em>Work Type Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWorkTypeDescription()
     * @generated
     * @ordered
     */
    protected String workTypeDescription = WORK_TYPE_DESCRIPTION_EDEFAULT;

    /**
     * The cached value of the '{@link #getDataModel() <em>Data Model</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDataModel()
     * @generated
     * @ordered
     */
    protected DataModel dataModel;

    /**
     * The default value of the '{@link #isTypePiled() <em>Type Piled</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isTypePiled()
     * @generated
     * @ordered
     */
    protected static final boolean TYPE_PILED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isTypePiled() <em>Type Piled</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isTypePiled()
     * @generated
     * @ordered
     */
    protected boolean typePiled = TYPE_PILED_EDEFAULT;

    /**
     * This is true if the Type Piled attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean typePiledESet;

    /**
     * The default value of the '{@link #getPilingLimit() <em>Piling Limit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPilingLimit()
     * @generated
     * @ordered
     */
    protected static final int PILING_LIMIT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getPilingLimit() <em>Piling Limit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPilingLimit()
     * @generated
     * @ordered
     */
    protected int pilingLimit = PILING_LIMIT_EDEFAULT;

    /**
     * This is true if the Piling Limit attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean pilingLimitESet;

    /**
     * The default value of the '{@link #isIgnoreIncomingData() <em>Ignore Incoming Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIgnoreIncomingData()
     * @generated
     * @ordered
     */
    protected static final boolean IGNORE_INCOMING_DATA_EDEFAULT = true;

    /**
     * The cached value of the '{@link #isIgnoreIncomingData() <em>Ignore Incoming Data</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIgnoreIncomingData()
     * @generated
     * @ordered
     */
    protected boolean ignoreIncomingData = IGNORE_INCOMING_DATA_EDEFAULT;

    /**
     * This is true if the Ignore Incoming Data attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean ignoreIncomingDataESet;

    /**
     * The default value of the '{@link #isReofferOnClose() <em>Reoffer On Close</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReofferOnClose()
     * @generated
     * @ordered
     */
    protected static final boolean REOFFER_ON_CLOSE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isReofferOnClose() <em>Reoffer On Close</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReofferOnClose()
     * @generated
     * @ordered
     */
    protected boolean reofferOnClose = REOFFER_ON_CLOSE_EDEFAULT;

    /**
     * This is true if the Reoffer On Close attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean reofferOnCloseESet;

    /**
     * The default value of the '{@link #isReofferOnCancel() <em>Reoffer On Cancel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReofferOnCancel()
     * @generated
     * @ordered
     */
    protected static final boolean REOFFER_ON_CANCEL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isReofferOnCancel() <em>Reoffer On Cancel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReofferOnCancel()
     * @generated
     * @ordered
     */
    protected boolean reofferOnCancel = REOFFER_ON_CANCEL_EDEFAULT;

    /**
     * This is true if the Reoffer On Cancel attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean reofferOnCancelESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WorkTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DatamodelPackage.Literals.WORK_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getWorkTypeID() {
        return workTypeID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeID(long newWorkTypeID) {
        long oldWorkTypeID = workTypeID;
        workTypeID = newWorkTypeID;
        boolean oldWorkTypeIDESet = workTypeIDESet;
        workTypeIDESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__WORK_TYPE_ID, oldWorkTypeID, workTypeID, !oldWorkTypeIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetWorkTypeID() {
        long oldWorkTypeID = workTypeID;
        boolean oldWorkTypeIDESet = workTypeIDESet;
        workTypeID = WORK_TYPE_ID_EDEFAULT;
        workTypeIDESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.WORK_TYPE__WORK_TYPE_ID, oldWorkTypeID, WORK_TYPE_ID_EDEFAULT, oldWorkTypeIDESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetWorkTypeID() {
        return workTypeIDESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkTypeUID() {
        return workTypeUID;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeUID(String newWorkTypeUID) {
        String oldWorkTypeUID = workTypeUID;
        workTypeUID = newWorkTypeUID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__WORK_TYPE_UID, oldWorkTypeUID, workTypeUID));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getWorkTypeDescription() {
        return workTypeDescription;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeDescription(String newWorkTypeDescription) {
        String oldWorkTypeDescription = workTypeDescription;
        workTypeDescription = newWorkTypeDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__WORK_TYPE_DESCRIPTION, oldWorkTypeDescription, workTypeDescription));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DataModel getDataModel() {
        return dataModel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDataModel(DataModel newDataModel, NotificationChain msgs) {
        DataModel oldDataModel = dataModel;
        dataModel = newDataModel;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__DATA_MODEL, oldDataModel, newDataModel);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDataModel(DataModel newDataModel) {
        if (newDataModel != dataModel) {
            NotificationChain msgs = null;
            if (dataModel != null)
                msgs = ((InternalEObject)dataModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DatamodelPackage.WORK_TYPE__DATA_MODEL, null, msgs);
            if (newDataModel != null)
                msgs = ((InternalEObject)newDataModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DatamodelPackage.WORK_TYPE__DATA_MODEL, null, msgs);
            msgs = basicSetDataModel(newDataModel, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__DATA_MODEL, newDataModel, newDataModel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isTypePiled() {
        return typePiled;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTypePiled(boolean newTypePiled) {
        boolean oldTypePiled = typePiled;
        typePiled = newTypePiled;
        boolean oldTypePiledESet = typePiledESet;
        typePiledESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__TYPE_PILED, oldTypePiled, typePiled, !oldTypePiledESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetTypePiled() {
        boolean oldTypePiled = typePiled;
        boolean oldTypePiledESet = typePiledESet;
        typePiled = TYPE_PILED_EDEFAULT;
        typePiledESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.WORK_TYPE__TYPE_PILED, oldTypePiled, TYPE_PILED_EDEFAULT, oldTypePiledESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetTypePiled() {
        return typePiledESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getPilingLimit() {
        return pilingLimit;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPilingLimit(int newPilingLimit) {
        int oldPilingLimit = pilingLimit;
        pilingLimit = newPilingLimit;
        boolean oldPilingLimitESet = pilingLimitESet;
        pilingLimitESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__PILING_LIMIT, oldPilingLimit, pilingLimit, !oldPilingLimitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetPilingLimit() {
        int oldPilingLimit = pilingLimit;
        boolean oldPilingLimitESet = pilingLimitESet;
        pilingLimit = PILING_LIMIT_EDEFAULT;
        pilingLimitESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.WORK_TYPE__PILING_LIMIT, oldPilingLimit, PILING_LIMIT_EDEFAULT, oldPilingLimitESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetPilingLimit() {
        return pilingLimitESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isIgnoreIncomingData() {
        return ignoreIncomingData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIgnoreIncomingData(boolean newIgnoreIncomingData) {
        boolean oldIgnoreIncomingData = ignoreIncomingData;
        ignoreIncomingData = newIgnoreIncomingData;
        boolean oldIgnoreIncomingDataESet = ignoreIncomingDataESet;
        ignoreIncomingDataESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__IGNORE_INCOMING_DATA, oldIgnoreIncomingData, ignoreIncomingData, !oldIgnoreIncomingDataESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetIgnoreIncomingData() {
        boolean oldIgnoreIncomingData = ignoreIncomingData;
        boolean oldIgnoreIncomingDataESet = ignoreIncomingDataESet;
        ignoreIncomingData = IGNORE_INCOMING_DATA_EDEFAULT;
        ignoreIncomingDataESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.WORK_TYPE__IGNORE_INCOMING_DATA, oldIgnoreIncomingData, IGNORE_INCOMING_DATA_EDEFAULT, oldIgnoreIncomingDataESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetIgnoreIncomingData() {
        return ignoreIncomingDataESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isReofferOnClose() {
        return reofferOnClose;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReofferOnClose(boolean newReofferOnClose) {
        boolean oldReofferOnClose = reofferOnClose;
        reofferOnClose = newReofferOnClose;
        boolean oldReofferOnCloseESet = reofferOnCloseESet;
        reofferOnCloseESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__REOFFER_ON_CLOSE, oldReofferOnClose, reofferOnClose, !oldReofferOnCloseESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReofferOnClose() {
        boolean oldReofferOnClose = reofferOnClose;
        boolean oldReofferOnCloseESet = reofferOnCloseESet;
        reofferOnClose = REOFFER_ON_CLOSE_EDEFAULT;
        reofferOnCloseESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.WORK_TYPE__REOFFER_ON_CLOSE, oldReofferOnClose, REOFFER_ON_CLOSE_EDEFAULT, oldReofferOnCloseESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReofferOnClose() {
        return reofferOnCloseESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isReofferOnCancel() {
        return reofferOnCancel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReofferOnCancel(boolean newReofferOnCancel) {
        boolean oldReofferOnCancel = reofferOnCancel;
        reofferOnCancel = newReofferOnCancel;
        boolean oldReofferOnCancelESet = reofferOnCancelESet;
        reofferOnCancelESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, DatamodelPackage.WORK_TYPE__REOFFER_ON_CANCEL, oldReofferOnCancel, reofferOnCancel, !oldReofferOnCancelESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReofferOnCancel() {
        boolean oldReofferOnCancel = reofferOnCancel;
        boolean oldReofferOnCancelESet = reofferOnCancelESet;
        reofferOnCancel = REOFFER_ON_CANCEL_EDEFAULT;
        reofferOnCancelESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, DatamodelPackage.WORK_TYPE__REOFFER_ON_CANCEL, oldReofferOnCancel, REOFFER_ON_CANCEL_EDEFAULT, oldReofferOnCancelESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReofferOnCancel() {
        return reofferOnCancelESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DatamodelPackage.WORK_TYPE__DATA_MODEL:
                return basicSetDataModel(null, msgs);
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
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_ID:
                return getWorkTypeID();
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_UID:
                return getWorkTypeUID();
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_DESCRIPTION:
                return getWorkTypeDescription();
            case DatamodelPackage.WORK_TYPE__DATA_MODEL:
                return getDataModel();
            case DatamodelPackage.WORK_TYPE__TYPE_PILED:
                return isTypePiled();
            case DatamodelPackage.WORK_TYPE__PILING_LIMIT:
                return getPilingLimit();
            case DatamodelPackage.WORK_TYPE__IGNORE_INCOMING_DATA:
                return isIgnoreIncomingData();
            case DatamodelPackage.WORK_TYPE__REOFFER_ON_CLOSE:
                return isReofferOnClose();
            case DatamodelPackage.WORK_TYPE__REOFFER_ON_CANCEL:
                return isReofferOnCancel();
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
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_ID:
                setWorkTypeID((Long)newValue);
                return;
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_UID:
                setWorkTypeUID((String)newValue);
                return;
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_DESCRIPTION:
                setWorkTypeDescription((String)newValue);
                return;
            case DatamodelPackage.WORK_TYPE__DATA_MODEL:
                setDataModel((DataModel)newValue);
                return;
            case DatamodelPackage.WORK_TYPE__TYPE_PILED:
                setTypePiled((Boolean)newValue);
                return;
            case DatamodelPackage.WORK_TYPE__PILING_LIMIT:
                setPilingLimit((Integer)newValue);
                return;
            case DatamodelPackage.WORK_TYPE__IGNORE_INCOMING_DATA:
                setIgnoreIncomingData((Boolean)newValue);
                return;
            case DatamodelPackage.WORK_TYPE__REOFFER_ON_CLOSE:
                setReofferOnClose((Boolean)newValue);
                return;
            case DatamodelPackage.WORK_TYPE__REOFFER_ON_CANCEL:
                setReofferOnCancel((Boolean)newValue);
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
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_ID:
                unsetWorkTypeID();
                return;
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_UID:
                setWorkTypeUID(WORK_TYPE_UID_EDEFAULT);
                return;
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_DESCRIPTION:
                setWorkTypeDescription(WORK_TYPE_DESCRIPTION_EDEFAULT);
                return;
            case DatamodelPackage.WORK_TYPE__DATA_MODEL:
                setDataModel((DataModel)null);
                return;
            case DatamodelPackage.WORK_TYPE__TYPE_PILED:
                unsetTypePiled();
                return;
            case DatamodelPackage.WORK_TYPE__PILING_LIMIT:
                unsetPilingLimit();
                return;
            case DatamodelPackage.WORK_TYPE__IGNORE_INCOMING_DATA:
                unsetIgnoreIncomingData();
                return;
            case DatamodelPackage.WORK_TYPE__REOFFER_ON_CLOSE:
                unsetReofferOnClose();
                return;
            case DatamodelPackage.WORK_TYPE__REOFFER_ON_CANCEL:
                unsetReofferOnCancel();
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
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_ID:
                return isSetWorkTypeID();
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_UID:
                return WORK_TYPE_UID_EDEFAULT == null ? workTypeUID != null : !WORK_TYPE_UID_EDEFAULT.equals(workTypeUID);
            case DatamodelPackage.WORK_TYPE__WORK_TYPE_DESCRIPTION:
                return WORK_TYPE_DESCRIPTION_EDEFAULT == null ? workTypeDescription != null : !WORK_TYPE_DESCRIPTION_EDEFAULT.equals(workTypeDescription);
            case DatamodelPackage.WORK_TYPE__DATA_MODEL:
                return dataModel != null;
            case DatamodelPackage.WORK_TYPE__TYPE_PILED:
                return isSetTypePiled();
            case DatamodelPackage.WORK_TYPE__PILING_LIMIT:
                return isSetPilingLimit();
            case DatamodelPackage.WORK_TYPE__IGNORE_INCOMING_DATA:
                return isSetIgnoreIncomingData();
            case DatamodelPackage.WORK_TYPE__REOFFER_ON_CLOSE:
                return isSetReofferOnClose();
            case DatamodelPackage.WORK_TYPE__REOFFER_ON_CANCEL:
                return isSetReofferOnCancel();
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
        result.append(" (workTypeID: ");
        if (workTypeIDESet) result.append(workTypeID); else result.append("<unset>");
        result.append(", workTypeUID: ");
        result.append(workTypeUID);
        result.append(", workTypeDescription: ");
        result.append(workTypeDescription);
        result.append(", typePiled: ");
        if (typePiledESet) result.append(typePiled); else result.append("<unset>");
        result.append(", pilingLimit: ");
        if (pilingLimitESet) result.append(pilingLimit); else result.append("<unset>");
        result.append(", ignoreIncomingData: ");
        if (ignoreIncomingDataESet) result.append(ignoreIncomingData); else result.append("<unset>");
        result.append(", reofferOnClose: ");
        if (reofferOnCloseESet) result.append(reofferOnClose); else result.append("<unset>");
        result.append(", reofferOnCancel: ");
        if (reofferOnCancelESet) result.append(reofferOnCancel); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //WorkTypeImpl
