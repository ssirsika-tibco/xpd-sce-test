/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance;
import com.tibco.xpd.xpdl2.ConformanceClass;
import com.tibco.xpd.xpdl2.GraphConformanceType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conformance Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConformanceClassImpl#getGraphConformance <em>Graph Conformance</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ConformanceClassImpl#getBpmnModelPortabilityConformance <em>Bpmn Model Portability Conformance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConformanceClassImpl extends EObjectImpl implements
        ConformanceClass {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getGraphConformance() <em>Graph Conformance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGraphConformance()
     * @generated
     * @ordered
     */
    protected static final GraphConformanceType GRAPH_CONFORMANCE_EDEFAULT =
            GraphConformanceType.NON_BLOCKED_LITERAL;

    /**
     * The cached value of the '{@link #getGraphConformance() <em>Graph Conformance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGraphConformance()
     * @generated
     * @ordered
     */
    protected GraphConformanceType graphConformance =
            GRAPH_CONFORMANCE_EDEFAULT;

    /**
     * This is true if the Graph Conformance attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean graphConformanceESet;

    /**
     * The default value of the '{@link #getBpmnModelPortabilityConformance() <em>Bpmn Model Portability Conformance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBpmnModelPortabilityConformance()
     * @generated
     * @ordered
     */
    protected static final BPMNModelPortabilityConformance BPMN_MODEL_PORTABILITY_CONFORMANCE_EDEFAULT =
            BPMNModelPortabilityConformance.NONE;

    /**
     * The cached value of the '{@link #getBpmnModelPortabilityConformance() <em>Bpmn Model Portability Conformance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBpmnModelPortabilityConformance()
     * @generated
     * @ordered
     */
    protected BPMNModelPortabilityConformance bpmnModelPortabilityConformance =
            BPMN_MODEL_PORTABILITY_CONFORMANCE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ConformanceClassImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.CONFORMANCE_CLASS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GraphConformanceType getGraphConformance() {
        return graphConformance;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGraphConformance(GraphConformanceType newGraphConformance) {
        GraphConformanceType oldGraphConformance = graphConformance;
        graphConformance =
                newGraphConformance == null ? GRAPH_CONFORMANCE_EDEFAULT
                        : newGraphConformance;
        boolean oldGraphConformanceESet = graphConformanceESet;
        graphConformanceESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.CONFORMANCE_CLASS__GRAPH_CONFORMANCE,
                    oldGraphConformance, graphConformance,
                    !oldGraphConformanceESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetGraphConformance() {
        GraphConformanceType oldGraphConformance = graphConformance;
        boolean oldGraphConformanceESet = graphConformanceESet;
        graphConformance = GRAPH_CONFORMANCE_EDEFAULT;
        graphConformanceESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.CONFORMANCE_CLASS__GRAPH_CONFORMANCE,
                    oldGraphConformance, GRAPH_CONFORMANCE_EDEFAULT,
                    oldGraphConformanceESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetGraphConformance() {
        return graphConformanceESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BPMNModelPortabilityConformance getBpmnModelPortabilityConformance() {
        return bpmnModelPortabilityConformance;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBpmnModelPortabilityConformance(
            BPMNModelPortabilityConformance newBpmnModelPortabilityConformance) {
        BPMNModelPortabilityConformance oldBpmnModelPortabilityConformance =
                bpmnModelPortabilityConformance;
        bpmnModelPortabilityConformance =
                newBpmnModelPortabilityConformance == null ? BPMN_MODEL_PORTABILITY_CONFORMANCE_EDEFAULT
                        : newBpmnModelPortabilityConformance;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(
                    this,
                    Notification.SET,
                    Xpdl2Package.CONFORMANCE_CLASS__BPMN_MODEL_PORTABILITY_CONFORMANCE,
                    oldBpmnModelPortabilityConformance,
                    bpmnModelPortabilityConformance));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.CONFORMANCE_CLASS__GRAPH_CONFORMANCE:
            return getGraphConformance();
        case Xpdl2Package.CONFORMANCE_CLASS__BPMN_MODEL_PORTABILITY_CONFORMANCE:
            return getBpmnModelPortabilityConformance();
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
        case Xpdl2Package.CONFORMANCE_CLASS__GRAPH_CONFORMANCE:
            setGraphConformance((GraphConformanceType) newValue);
            return;
        case Xpdl2Package.CONFORMANCE_CLASS__BPMN_MODEL_PORTABILITY_CONFORMANCE:
            setBpmnModelPortabilityConformance((BPMNModelPortabilityConformance) newValue);
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
        case Xpdl2Package.CONFORMANCE_CLASS__GRAPH_CONFORMANCE:
            unsetGraphConformance();
            return;
        case Xpdl2Package.CONFORMANCE_CLASS__BPMN_MODEL_PORTABILITY_CONFORMANCE:
            setBpmnModelPortabilityConformance(BPMN_MODEL_PORTABILITY_CONFORMANCE_EDEFAULT);
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
        case Xpdl2Package.CONFORMANCE_CLASS__GRAPH_CONFORMANCE:
            return isSetGraphConformance();
        case Xpdl2Package.CONFORMANCE_CLASS__BPMN_MODEL_PORTABILITY_CONFORMANCE:
            return bpmnModelPortabilityConformance != BPMN_MODEL_PORTABILITY_CONFORMANCE_EDEFAULT;
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
        result.append(" (graphConformance: "); //$NON-NLS-1$
        if (graphConformanceESet)
            result.append(graphConformance);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", bpmnModelPortabilityConformance: "); //$NON-NLS-1$
        result.append(bpmnModelPortabilityConformance);
        result.append(')');
        return result.toString();
    }

} //ConformanceClassImpl
