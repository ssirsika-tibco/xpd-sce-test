/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.simulation.report.impl;

import com.tibco.simulation.report.SimRepActivities;
import com.tibco.simulation.report.SimRepCases;
import com.tibco.simulation.report.SimRepExperiment;
import com.tibco.simulation.report.SimRepExperimentData;
import com.tibco.simulation.report.SimRepPackage;
import com.tibco.simulation.report.SimRepParticipants;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Experiment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getExperimentData <em>Experiment Data</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getCases <em>Cases</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getParticipants <em>Participants</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getActivities <em>Activities</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getPackageId <em>Package Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getProcessName <em>Process Name</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getProcessLabel <em>Process Label</em>}</li>
 *   <li>{@link com.tibco.simulation.report.impl.SimRepExperimentImpl#getPackageName <em>Package Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimRepExperimentImpl extends EObjectImpl implements
        SimRepExperiment {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "TIBCO Software Inc."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getExperimentData() <em>Experiment Data</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExperimentData()
     * @generated
     * @ordered
     */
    protected SimRepExperimentData experimentData;

    /**
     * The cached value of the '{@link #getCases() <em>Cases</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCases()
     * @generated
     * @ordered
     */
    protected SimRepCases cases;

    /**
     * The cached value of the '{@link #getParticipants() <em>Participants</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParticipants()
     * @generated
     * @ordered
     */
    protected SimRepParticipants participants;

    /**
     * The cached value of the '{@link #getActivities() <em>Activities</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivities()
     * @generated
     * @ordered
     */
    protected SimRepActivities activities;

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

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
     * The default value of the '{@link #getPackageId() <em>Package Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageId()
     * @generated
     * @ordered
     */
    protected static final String PACKAGE_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPackageId() <em>Package Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageId()
     * @generated
     * @ordered
     */
    protected String packageId = PACKAGE_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_ID_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessId() <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessId()
     * @generated
     * @ordered
     */
    protected String processId = PROCESS_ID_EDEFAULT;

    /**
     * The default value of the '{@link #getProcessName() <em>Process Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessName()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessName() <em>Process Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessName()
     * @generated
     * @ordered
     */
    protected String processName = PROCESS_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getProcessLabel() <em>Process Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessLabel()
     * @generated
     * @ordered
     */
    protected static final String PROCESS_LABEL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProcessLabel() <em>Process Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProcessLabel()
     * @generated
     * @ordered
     */
    protected String processLabel = PROCESS_LABEL_EDEFAULT;

    /**
     * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageName()
     * @generated
     * @ordered
     */
    protected static final String PACKAGE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPackageName()
     * @generated
     * @ordered
     */
    protected String packageName = PACKAGE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SimRepExperimentImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimRepPackage.Literals.SIM_REP_EXPERIMENT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepExperimentData getExperimentData() {
        return experimentData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetExperimentData(
            SimRepExperimentData newExperimentData, NotificationChain msgs) {
        SimRepExperimentData oldExperimentData = experimentData;
        experimentData = newExperimentData;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA,
                            oldExperimentData, newExperimentData);
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
    public void setExperimentData(SimRepExperimentData newExperimentData) {
        if (newExperimentData != experimentData) {
            NotificationChain msgs = null;
            if (experimentData != null)
                msgs =
                        ((InternalEObject) experimentData)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA,
                                        null,
                                        msgs);
            if (newExperimentData != null)
                msgs =
                        ((InternalEObject) newExperimentData)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA,
                                        null,
                                        msgs);
            msgs = basicSetExperimentData(newExperimentData, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA,
                    newExperimentData, newExperimentData));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepCases getCases() {
        return cases;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCases(SimRepCases newCases,
            NotificationChain msgs) {
        SimRepCases oldCases = cases;
        cases = newCases;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            SimRepPackage.SIM_REP_EXPERIMENT__CASES, oldCases,
                            newCases);
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
    public void setCases(SimRepCases newCases) {
        if (newCases != cases) {
            NotificationChain msgs = null;
            if (cases != null)
                msgs =
                        ((InternalEObject) cases)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_EXPERIMENT__CASES,
                                        null,
                                        msgs);
            if (newCases != null)
                msgs =
                        ((InternalEObject) newCases)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_EXPERIMENT__CASES,
                                        null,
                                        msgs);
            msgs = basicSetCases(newCases, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__CASES, newCases, newCases));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepParticipants getParticipants() {
        return participants;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParticipants(
            SimRepParticipants newParticipants, NotificationChain msgs) {
        SimRepParticipants oldParticipants = participants;
        participants = newParticipants;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS,
                            oldParticipants, newParticipants);
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
    public void setParticipants(SimRepParticipants newParticipants) {
        if (newParticipants != participants) {
            NotificationChain msgs = null;
            if (participants != null)
                msgs =
                        ((InternalEObject) participants)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS,
                                        null,
                                        msgs);
            if (newParticipants != null)
                msgs =
                        ((InternalEObject) newParticipants)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS,
                                        null,
                                        msgs);
            msgs = basicSetParticipants(newParticipants, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS,
                    newParticipants, newParticipants));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SimRepActivities getActivities() {
        return activities;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetActivities(SimRepActivities newActivities,
            NotificationChain msgs) {
        SimRepActivities oldActivities = activities;
        activities = newActivities;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES,
                            oldActivities, newActivities);
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
    public void setActivities(SimRepActivities newActivities) {
        if (newActivities != activities) {
            NotificationChain msgs = null;
            if (activities != null)
                msgs =
                        ((InternalEObject) activities)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES,
                                        null,
                                        msgs);
            if (newActivities != null)
                msgs =
                        ((InternalEObject) newActivities)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES,
                                        null,
                                        msgs);
            msgs = basicSetActivities(newActivities, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES,
                    newActivities, newActivities));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__ID, oldId, id));
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
                    SimRepPackage.SIM_REP_EXPERIMENT__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackageId(String newPackageId) {
        String oldPackageId = packageId;
        packageId = newPackageId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_ID, oldPackageId,
                    packageId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessId(String newProcessId) {
        String oldProcessId = processId;
        processId = newProcessId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_ID, oldProcessId,
                    processId));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessName() {
        return processName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessName(String newProcessName) {
        String oldProcessName = processName;
        processName = newProcessName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_NAME,
                    oldProcessName, processName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProcessLabel() {
        return processLabel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProcessLabel(String newProcessLabel) {
        String oldProcessLabel = processLabel;
        processLabel = newProcessLabel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_LABEL,
                    oldProcessLabel, processLabel));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPackageName(String newPackageName) {
        String oldPackageName = packageName;
        packageName = newPackageName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_NAME,
                    oldPackageName, packageName));
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
        case SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA:
            return basicSetExperimentData(null, msgs);
        case SimRepPackage.SIM_REP_EXPERIMENT__CASES:
            return basicSetCases(null, msgs);
        case SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS:
            return basicSetParticipants(null, msgs);
        case SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES:
            return basicSetActivities(null, msgs);
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
        case SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA:
            return getExperimentData();
        case SimRepPackage.SIM_REP_EXPERIMENT__CASES:
            return getCases();
        case SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS:
            return getParticipants();
        case SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES:
            return getActivities();
        case SimRepPackage.SIM_REP_EXPERIMENT__ID:
            return getId();
        case SimRepPackage.SIM_REP_EXPERIMENT__NAME:
            return getName();
        case SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_ID:
            return getPackageId();
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_ID:
            return getProcessId();
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_NAME:
            return getProcessName();
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_LABEL:
            return getProcessLabel();
        case SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_NAME:
            return getPackageName();
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
        case SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA:
            setExperimentData((SimRepExperimentData) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__CASES:
            setCases((SimRepCases) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS:
            setParticipants((SimRepParticipants) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES:
            setActivities((SimRepActivities) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__ID:
            setId((String) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__NAME:
            setName((String) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_ID:
            setPackageId((String) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_ID:
            setProcessId((String) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_NAME:
            setProcessName((String) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_LABEL:
            setProcessLabel((String) newValue);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_NAME:
            setPackageName((String) newValue);
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
        case SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA:
            setExperimentData((SimRepExperimentData) null);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__CASES:
            setCases((SimRepCases) null);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS:
            setParticipants((SimRepParticipants) null);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES:
            setActivities((SimRepActivities) null);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__ID:
            setId(ID_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__NAME:
            setName(NAME_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_ID:
            setPackageId(PACKAGE_ID_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_ID:
            setProcessId(PROCESS_ID_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_NAME:
            setProcessName(PROCESS_NAME_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_LABEL:
            setProcessLabel(PROCESS_LABEL_EDEFAULT);
            return;
        case SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_NAME:
            setPackageName(PACKAGE_NAME_EDEFAULT);
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
        case SimRepPackage.SIM_REP_EXPERIMENT__EXPERIMENT_DATA:
            return experimentData != null;
        case SimRepPackage.SIM_REP_EXPERIMENT__CASES:
            return cases != null;
        case SimRepPackage.SIM_REP_EXPERIMENT__PARTICIPANTS:
            return participants != null;
        case SimRepPackage.SIM_REP_EXPERIMENT__ACTIVITIES:
            return activities != null;
        case SimRepPackage.SIM_REP_EXPERIMENT__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        case SimRepPackage.SIM_REP_EXPERIMENT__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
                    .equals(name);
        case SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_ID:
            return PACKAGE_ID_EDEFAULT == null ? packageId != null
                    : !PACKAGE_ID_EDEFAULT.equals(packageId);
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_ID:
            return PROCESS_ID_EDEFAULT == null ? processId != null
                    : !PROCESS_ID_EDEFAULT.equals(processId);
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_NAME:
            return PROCESS_NAME_EDEFAULT == null ? processName != null
                    : !PROCESS_NAME_EDEFAULT.equals(processName);
        case SimRepPackage.SIM_REP_EXPERIMENT__PROCESS_LABEL:
            return PROCESS_LABEL_EDEFAULT == null ? processLabel != null
                    : !PROCESS_LABEL_EDEFAULT.equals(processLabel);
        case SimRepPackage.SIM_REP_EXPERIMENT__PACKAGE_NAME:
            return PACKAGE_NAME_EDEFAULT == null ? packageName != null
                    : !PACKAGE_NAME_EDEFAULT.equals(packageName);
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (id: "); //$NON-NLS-1$
        result.append(id);
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(", packageId: "); //$NON-NLS-1$
        result.append(packageId);
        result.append(", processId: "); //$NON-NLS-1$
        result.append(processId);
        result.append(", processName: "); //$NON-NLS-1$
        result.append(processName);
        result.append(", processLabel: "); //$NON-NLS-1$
        result.append(processLabel);
        result.append(", packageName: "); //$NON-NLS-1$
        result.append(packageName);
        result.append(')');
        return result.toString();
    }

} //SimRepExperimentImpl
