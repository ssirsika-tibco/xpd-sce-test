/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.simulation.impl;

import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.DocumentRoot;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.SimulationPackage;
import com.tibco.xpd.simulation.SplitSimulationDataType;
import com.tibco.xpd.simulation.StartSimulationDataType;
import com.tibco.xpd.simulation.TransitionSimulationDataType;
import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getActivitySimulationData <em>Activity Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getParticipantSimulationData <em>Participant Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getSplitSimulationData <em>Split Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getStartSimulationData <em>Start Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getTransitionSimulationData <em>Transition Simulation Data</em>}</li>
 *   <li>{@link com.tibco.xpd.simulation.impl.DocumentRootImpl#getWorkflowProcessSimulationData <em>Workflow Process Simulation Data</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright 2005 TIBCO Software Inc. "; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMixed()
     * @generated
     * @ordered
     */
    protected FeatureMap mixed;

    /**
     * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXMLNSPrefixMap()
     * @generated
     * @ordered
     */
    protected EMap<String, String> xMLNSPrefixMap;

    /**
     * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXSISchemaLocation()
     * @generated
     * @ordered
     */
    protected EMap<String, String> xSISchemaLocation;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DocumentRootImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return SimulationPackage.Literals.DOCUMENT_ROOT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getMixed() {
        if (mixed == null) {
            mixed =
                    new BasicFeatureMap(this,
                            SimulationPackage.DOCUMENT_ROOT__MIXED);
        }
        return mixed;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getXMLNSPrefixMap() {
        if (xMLNSPrefixMap == null) {
            xMLNSPrefixMap =
                    new EcoreEMap<String, String>(
                            EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY,
                            EStringToStringMapEntryImpl.class, this,
                            SimulationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        }
        return xMLNSPrefixMap;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getXSISchemaLocation() {
        if (xSISchemaLocation == null) {
            xSISchemaLocation =
                    new EcoreEMap<String, String>(
                            EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY,
                            EStringToStringMapEntryImpl.class,
                            this,
                            SimulationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        }
        return xSISchemaLocation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ActivitySimulationDataType getActivitySimulationData() {
        return (ActivitySimulationDataType) getMixed()
                .get(SimulationPackage.Literals.DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA,
                        true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetActivitySimulationData(
            ActivitySimulationDataType newActivitySimulationData,
            NotificationChain msgs) {
        return ((FeatureMap.Internal) getMixed())
                .basicAdd(SimulationPackage.Literals.DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA,
                        newActivitySimulationData,
                        msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setActivitySimulationData(
            ActivitySimulationDataType newActivitySimulationData) {
        ((FeatureMap.Internal) getMixed())
                .set(SimulationPackage.Literals.DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA,
                        newActivitySimulationData);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ParticipantSimulationDataType getParticipantSimulationData() {
        return (ParticipantSimulationDataType) getMixed()
                .get(SimulationPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA,
                        true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParticipantSimulationData(
            ParticipantSimulationDataType newParticipantSimulationData,
            NotificationChain msgs) {
        return ((FeatureMap.Internal) getMixed())
                .basicAdd(SimulationPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA,
                        newParticipantSimulationData,
                        msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParticipantSimulationData(
            ParticipantSimulationDataType newParticipantSimulationData) {
        ((FeatureMap.Internal) getMixed())
                .set(SimulationPackage.Literals.DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA,
                        newParticipantSimulationData);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SplitSimulationDataType getSplitSimulationData() {
        return (SplitSimulationDataType) getMixed()
                .get(SimulationPackage.Literals.DOCUMENT_ROOT__SPLIT_SIMULATION_DATA,
                        true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSplitSimulationData(
            SplitSimulationDataType newSplitSimulationData,
            NotificationChain msgs) {
        return ((FeatureMap.Internal) getMixed())
                .basicAdd(SimulationPackage.Literals.DOCUMENT_ROOT__SPLIT_SIMULATION_DATA,
                        newSplitSimulationData,
                        msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSplitSimulationData(
            SplitSimulationDataType newSplitSimulationData) {
        ((FeatureMap.Internal) getMixed())
                .set(SimulationPackage.Literals.DOCUMENT_ROOT__SPLIT_SIMULATION_DATA,
                        newSplitSimulationData);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public StartSimulationDataType getStartSimulationData() {
        return (StartSimulationDataType) getMixed()
                .get(SimulationPackage.Literals.DOCUMENT_ROOT__START_SIMULATION_DATA,
                        true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetStartSimulationData(
            StartSimulationDataType newStartSimulationData,
            NotificationChain msgs) {
        return ((FeatureMap.Internal) getMixed())
                .basicAdd(SimulationPackage.Literals.DOCUMENT_ROOT__START_SIMULATION_DATA,
                        newStartSimulationData,
                        msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStartSimulationData(
            StartSimulationDataType newStartSimulationData) {
        ((FeatureMap.Internal) getMixed())
                .set(SimulationPackage.Literals.DOCUMENT_ROOT__START_SIMULATION_DATA,
                        newStartSimulationData);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TransitionSimulationDataType getTransitionSimulationData() {
        return (TransitionSimulationDataType) getMixed()
                .get(SimulationPackage.Literals.DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA,
                        true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTransitionSimulationData(
            TransitionSimulationDataType newTransitionSimulationData,
            NotificationChain msgs) {
        return ((FeatureMap.Internal) getMixed())
                .basicAdd(SimulationPackage.Literals.DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA,
                        newTransitionSimulationData,
                        msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTransitionSimulationData(
            TransitionSimulationDataType newTransitionSimulationData) {
        ((FeatureMap.Internal) getMixed())
                .set(SimulationPackage.Literals.DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA,
                        newTransitionSimulationData);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkflowProcessSimulationDataType getWorkflowProcessSimulationData() {
        return (WorkflowProcessSimulationDataType) getMixed()
                .get(SimulationPackage.Literals.DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA,
                        true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkflowProcessSimulationData(
            WorkflowProcessSimulationDataType newWorkflowProcessSimulationData,
            NotificationChain msgs) {
        return ((FeatureMap.Internal) getMixed())
                .basicAdd(SimulationPackage.Literals.DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA,
                        newWorkflowProcessSimulationData,
                        msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkflowProcessSimulationData(
            WorkflowProcessSimulationDataType newWorkflowProcessSimulationData) {
        ((FeatureMap.Internal) getMixed())
                .set(SimulationPackage.Literals.DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA,
                        newWorkflowProcessSimulationData);
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
        case SimulationPackage.DOCUMENT_ROOT__MIXED:
            return ((InternalEList<?>) getMixed()).basicRemove(otherEnd, msgs);
        case SimulationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
            return ((InternalEList<?>) getXMLNSPrefixMap())
                    .basicRemove(otherEnd, msgs);
        case SimulationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
            return ((InternalEList<?>) getXSISchemaLocation())
                    .basicRemove(otherEnd, msgs);
        case SimulationPackage.DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA:
            return basicSetActivitySimulationData(null, msgs);
        case SimulationPackage.DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA:
            return basicSetParticipantSimulationData(null, msgs);
        case SimulationPackage.DOCUMENT_ROOT__SPLIT_SIMULATION_DATA:
            return basicSetSplitSimulationData(null, msgs);
        case SimulationPackage.DOCUMENT_ROOT__START_SIMULATION_DATA:
            return basicSetStartSimulationData(null, msgs);
        case SimulationPackage.DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA:
            return basicSetTransitionSimulationData(null, msgs);
        case SimulationPackage.DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA:
            return basicSetWorkflowProcessSimulationData(null, msgs);
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
        case SimulationPackage.DOCUMENT_ROOT__MIXED:
            if (coreType)
                return getMixed();
            return ((FeatureMap.Internal) getMixed()).getWrapper();
        case SimulationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
            if (coreType)
                return getXMLNSPrefixMap();
            else
                return getXMLNSPrefixMap().map();
        case SimulationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
            if (coreType)
                return getXSISchemaLocation();
            else
                return getXSISchemaLocation().map();
        case SimulationPackage.DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA:
            return getActivitySimulationData();
        case SimulationPackage.DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA:
            return getParticipantSimulationData();
        case SimulationPackage.DOCUMENT_ROOT__SPLIT_SIMULATION_DATA:
            return getSplitSimulationData();
        case SimulationPackage.DOCUMENT_ROOT__START_SIMULATION_DATA:
            return getStartSimulationData();
        case SimulationPackage.DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA:
            return getTransitionSimulationData();
        case SimulationPackage.DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA:
            return getWorkflowProcessSimulationData();
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
        case SimulationPackage.DOCUMENT_ROOT__MIXED:
            ((FeatureMap.Internal) getMixed()).set(newValue);
            return;
        case SimulationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
            ((EStructuralFeature.Setting) getXMLNSPrefixMap()).set(newValue);
            return;
        case SimulationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
            ((EStructuralFeature.Setting) getXSISchemaLocation()).set(newValue);
            return;
        case SimulationPackage.DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA:
            setActivitySimulationData((ActivitySimulationDataType) newValue);
            return;
        case SimulationPackage.DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA:
            setParticipantSimulationData((ParticipantSimulationDataType) newValue);
            return;
        case SimulationPackage.DOCUMENT_ROOT__SPLIT_SIMULATION_DATA:
            setSplitSimulationData((SplitSimulationDataType) newValue);
            return;
        case SimulationPackage.DOCUMENT_ROOT__START_SIMULATION_DATA:
            setStartSimulationData((StartSimulationDataType) newValue);
            return;
        case SimulationPackage.DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA:
            setTransitionSimulationData((TransitionSimulationDataType) newValue);
            return;
        case SimulationPackage.DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA:
            setWorkflowProcessSimulationData((WorkflowProcessSimulationDataType) newValue);
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
        case SimulationPackage.DOCUMENT_ROOT__MIXED:
            getMixed().clear();
            return;
        case SimulationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
            getXMLNSPrefixMap().clear();
            return;
        case SimulationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
            getXSISchemaLocation().clear();
            return;
        case SimulationPackage.DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA:
            setActivitySimulationData((ActivitySimulationDataType) null);
            return;
        case SimulationPackage.DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA:
            setParticipantSimulationData((ParticipantSimulationDataType) null);
            return;
        case SimulationPackage.DOCUMENT_ROOT__SPLIT_SIMULATION_DATA:
            setSplitSimulationData((SplitSimulationDataType) null);
            return;
        case SimulationPackage.DOCUMENT_ROOT__START_SIMULATION_DATA:
            setStartSimulationData((StartSimulationDataType) null);
            return;
        case SimulationPackage.DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA:
            setTransitionSimulationData((TransitionSimulationDataType) null);
            return;
        case SimulationPackage.DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA:
            setWorkflowProcessSimulationData((WorkflowProcessSimulationDataType) null);
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
        case SimulationPackage.DOCUMENT_ROOT__MIXED:
            return mixed != null && !mixed.isEmpty();
        case SimulationPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
            return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
        case SimulationPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
            return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
        case SimulationPackage.DOCUMENT_ROOT__ACTIVITY_SIMULATION_DATA:
            return getActivitySimulationData() != null;
        case SimulationPackage.DOCUMENT_ROOT__PARTICIPANT_SIMULATION_DATA:
            return getParticipantSimulationData() != null;
        case SimulationPackage.DOCUMENT_ROOT__SPLIT_SIMULATION_DATA:
            return getSplitSimulationData() != null;
        case SimulationPackage.DOCUMENT_ROOT__START_SIMULATION_DATA:
            return getStartSimulationData() != null;
        case SimulationPackage.DOCUMENT_ROOT__TRANSITION_SIMULATION_DATA:
            return getTransitionSimulationData() != null;
        case SimulationPackage.DOCUMENT_ROOT__WORKFLOW_PROCESS_SIMULATION_DATA:
            return getWorkflowProcessSimulationData() != null;
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
        result.append(" (mixed: "); //$NON-NLS-1$
        result.append(mixed);
        result.append(')');
        return result.toString();
    }

} //DocumentRootImpl