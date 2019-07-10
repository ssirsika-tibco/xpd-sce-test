/**
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import com.tibco.xpd.xpdl2.DataMapping;

import java.util.Collection;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Signal Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.SignalDataImpl#getCorrelationMappings <em>Correlation Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.SignalDataImpl#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.SignalDataImpl#getRescheduleTimers <em>Reschedule Timers</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.SignalDataImpl#getInputScriptDataMapper <em>Input Script Data Mapper</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.SignalDataImpl#getOutputScriptDataMapper <em>Output Script Data Mapper</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SignalDataImpl extends EObjectImpl implements SignalData {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCorrelationMappings() <em>Correlation Mappings</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCorrelationMappings()
     * @generated
     * @ordered
     */
    protected CorrelationDataMappings correlationMappings;

    /**
     * The cached value of the '{@link #getDataMappings() <em>Data Mappings</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDataMappings()
     * @generated
     * @ordered
     */
    protected EList<DataMapping> dataMappings;

    /**
     * The cached value of the '{@link #getRescheduleTimers() <em>Reschedule Timers</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRescheduleTimers()
     * @generated
     * @ordered
     */
    protected RescheduleTimers rescheduleTimers;

    /**
     * The cached value of the '{@link #getInputScriptDataMapper() <em>Input Script Data Mapper</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getInputScriptDataMapper()
     * @generated
     * @ordered
     */
    protected ScriptDataMapper inputScriptDataMapper;

    /**
     * The cached value of the '{@link #getOutputScriptDataMapper() <em>Output Script Data Mapper</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutputScriptDataMapper()
     * @generated
     * @ordered
     */
    protected ScriptDataMapper outputScriptDataMapper;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SignalDataImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.SIGNAL_DATA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CorrelationDataMappings getCorrelationMappings() {
        return correlationMappings;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCorrelationMappings(CorrelationDataMappings newCorrelationMappings,
            NotificationChain msgs) {
        CorrelationDataMappings oldCorrelationMappings = correlationMappings;
        correlationMappings = newCorrelationMappings;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS,
                            oldCorrelationMappings, newCorrelationMappings);
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
    public void setCorrelationMappings(CorrelationDataMappings newCorrelationMappings) {
        if (newCorrelationMappings != correlationMappings) {
            NotificationChain msgs = null;
            if (correlationMappings != null)
                msgs = ((InternalEObject) correlationMappings).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS,
                        null,
                        msgs);
            if (newCorrelationMappings != null)
                msgs = ((InternalEObject) newCorrelationMappings).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS,
                        null,
                        msgs);
            msgs = basicSetCorrelationMappings(newCorrelationMappings, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS,
                    newCorrelationMappings, newCorrelationMappings));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<DataMapping> getDataMappings() {
        if (dataMappings == null) {
            dataMappings = new EObjectContainmentEList<DataMapping>(DataMapping.class, this,
                    XpdExtensionPackage.SIGNAL_DATA__DATA_MAPPINGS);
        }
        return dataMappings;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RescheduleTimers getRescheduleTimers() {
        return rescheduleTimers;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRescheduleTimers(RescheduleTimers newRescheduleTimers, NotificationChain msgs) {
        RescheduleTimers oldRescheduleTimers = rescheduleTimers;
        rescheduleTimers = newRescheduleTimers;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS, oldRescheduleTimers, newRescheduleTimers);
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
    public void setRescheduleTimers(RescheduleTimers newRescheduleTimers) {
        if (newRescheduleTimers != rescheduleTimers) {
            NotificationChain msgs = null;
            if (rescheduleTimers != null)
                msgs = ((InternalEObject) rescheduleTimers).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS,
                        null,
                        msgs);
            if (newRescheduleTimers != null)
                msgs = ((InternalEObject) newRescheduleTimers).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS,
                        null,
                        msgs);
            msgs = basicSetRescheduleTimers(newRescheduleTimers, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS,
                    newRescheduleTimers, newRescheduleTimers));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScriptDataMapper getInputScriptDataMapper() {
        return inputScriptDataMapper;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetInputScriptDataMapper(ScriptDataMapper newInputScriptDataMapper,
            NotificationChain msgs) {
        ScriptDataMapper oldInputScriptDataMapper = inputScriptDataMapper;
        inputScriptDataMapper = newInputScriptDataMapper;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER, oldInputScriptDataMapper,
                    newInputScriptDataMapper);
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
    public void setInputScriptDataMapper(ScriptDataMapper newInputScriptDataMapper) {
        if (newInputScriptDataMapper != inputScriptDataMapper) {
            NotificationChain msgs = null;
            if (inputScriptDataMapper != null)
                msgs = ((InternalEObject) inputScriptDataMapper).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER,
                        null,
                        msgs);
            if (newInputScriptDataMapper != null)
                msgs = ((InternalEObject) newInputScriptDataMapper).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER,
                        null,
                        msgs);
            msgs = basicSetInputScriptDataMapper(newInputScriptDataMapper, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER, newInputScriptDataMapper,
                    newInputScriptDataMapper));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ScriptDataMapper getOutputScriptDataMapper() {
        return outputScriptDataMapper;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetOutputScriptDataMapper(ScriptDataMapper newOutputScriptDataMapper,
            NotificationChain msgs) {
        ScriptDataMapper oldOutputScriptDataMapper = outputScriptDataMapper;
        outputScriptDataMapper = newOutputScriptDataMapper;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER, oldOutputScriptDataMapper,
                    newOutputScriptDataMapper);
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
    public void setOutputScriptDataMapper(ScriptDataMapper newOutputScriptDataMapper) {
        if (newOutputScriptDataMapper != outputScriptDataMapper) {
            NotificationChain msgs = null;
            if (outputScriptDataMapper != null)
                msgs = ((InternalEObject) outputScriptDataMapper).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER,
                        null,
                        msgs);
            if (newOutputScriptDataMapper != null)
                msgs = ((InternalEObject) newOutputScriptDataMapper).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER,
                        null,
                        msgs);
            msgs = basicSetOutputScriptDataMapper(newOutputScriptDataMapper, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER, newOutputScriptDataMapper,
                    newOutputScriptDataMapper));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS:
            return basicSetCorrelationMappings(null, msgs);
        case XpdExtensionPackage.SIGNAL_DATA__DATA_MAPPINGS:
            return ((InternalEList<?>) getDataMappings()).basicRemove(otherEnd, msgs);
        case XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS:
            return basicSetRescheduleTimers(null, msgs);
        case XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER:
            return basicSetInputScriptDataMapper(null, msgs);
        case XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER:
            return basicSetOutputScriptDataMapper(null, msgs);
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
        case XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS:
            return getCorrelationMappings();
        case XpdExtensionPackage.SIGNAL_DATA__DATA_MAPPINGS:
            return getDataMappings();
        case XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS:
            return getRescheduleTimers();
        case XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER:
            return getInputScriptDataMapper();
        case XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER:
            return getOutputScriptDataMapper();
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
        case XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS:
            setCorrelationMappings((CorrelationDataMappings) newValue);
            return;
        case XpdExtensionPackage.SIGNAL_DATA__DATA_MAPPINGS:
            getDataMappings().clear();
            getDataMappings().addAll((Collection<? extends DataMapping>) newValue);
            return;
        case XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS:
            setRescheduleTimers((RescheduleTimers) newValue);
            return;
        case XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER:
            setInputScriptDataMapper((ScriptDataMapper) newValue);
            return;
        case XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER:
            setOutputScriptDataMapper((ScriptDataMapper) newValue);
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
        case XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS:
            setCorrelationMappings((CorrelationDataMappings) null);
            return;
        case XpdExtensionPackage.SIGNAL_DATA__DATA_MAPPINGS:
            getDataMappings().clear();
            return;
        case XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS:
            setRescheduleTimers((RescheduleTimers) null);
            return;
        case XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER:
            setInputScriptDataMapper((ScriptDataMapper) null);
            return;
        case XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER:
            setOutputScriptDataMapper((ScriptDataMapper) null);
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
        case XpdExtensionPackage.SIGNAL_DATA__CORRELATION_MAPPINGS:
            return correlationMappings != null;
        case XpdExtensionPackage.SIGNAL_DATA__DATA_MAPPINGS:
            return dataMappings != null && !dataMappings.isEmpty();
        case XpdExtensionPackage.SIGNAL_DATA__RESCHEDULE_TIMERS:
            return rescheduleTimers != null;
        case XpdExtensionPackage.SIGNAL_DATA__INPUT_SCRIPT_DATA_MAPPER:
            return inputScriptDataMapper != null;
        case XpdExtensionPackage.SIGNAL_DATA__OUTPUT_SCRIPT_DATA_MAPPER:
            return outputScriptDataMapper != null;
        }
        return super.eIsSet(featureID);
    }

} //SignalDataImpl
