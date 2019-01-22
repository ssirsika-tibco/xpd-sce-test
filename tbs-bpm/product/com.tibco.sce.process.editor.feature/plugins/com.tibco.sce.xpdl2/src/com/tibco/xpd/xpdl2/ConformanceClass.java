/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conformance Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ConformanceClass#getGraphConformance <em>Graph Conformance</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ConformanceClass#getBpmnModelPortabilityConformance <em>Bpmn Model Portability Conformance</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConformanceClass()
 * @model extendedMetaData="name='ConformanceClass_._type' kind='elementOnly'"
 * @generated
 */
public interface ConformanceClass extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Graph Conformance</b></em>' attribute.
     * The default value is <code>"NON_BLOCKED"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.GraphConformanceType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Graph Conformance</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Graph Conformance</em>' attribute.
     * @see com.tibco.xpd.xpdl2.GraphConformanceType
     * @see #isSetGraphConformance()
     * @see #unsetGraphConformance()
     * @see #setGraphConformance(GraphConformanceType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConformanceClass_GraphConformance()
     * @model default="NON_BLOCKED" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='GraphConformance'"
     * @generated
     */
    GraphConformanceType getGraphConformance();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ConformanceClass#getGraphConformance <em>Graph Conformance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Graph Conformance</em>' attribute.
     * @see com.tibco.xpd.xpdl2.GraphConformanceType
     * @see #isSetGraphConformance()
     * @see #unsetGraphConformance()
     * @see #getGraphConformance()
     * @generated
     */
    void setGraphConformance(GraphConformanceType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.ConformanceClass#getGraphConformance <em>Graph Conformance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetGraphConformance()
     * @see #getGraphConformance()
     * @see #setGraphConformance(GraphConformanceType)
     * @generated
     */
    void unsetGraphConformance();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.ConformanceClass#getGraphConformance <em>Graph Conformance</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Graph Conformance</em>' attribute is set.
     * @see #unsetGraphConformance()
     * @see #getGraphConformance()
     * @see #setGraphConformance(GraphConformanceType)
     * @generated
     */
    boolean isSetGraphConformance();

    /**
     * Returns the value of the '<em><b>Bpmn Model Portability Conformance</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bpmn Model Portability Conformance</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bpmn Model Portability Conformance</em>' attribute.
     * @see com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance
     * @see #setBpmnModelPortabilityConformance(BPMNModelPortabilityConformance)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConformanceClass_BpmnModelPortabilityConformance()
     * @model extendedMetaData="kind='attribute' name='BPMNModelPortabilityConformance'"
     * @generated
     */
    BPMNModelPortabilityConformance getBpmnModelPortabilityConformance();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ConformanceClass#getBpmnModelPortabilityConformance <em>Bpmn Model Portability Conformance</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Bpmn Model Portability Conformance</em>' attribute.
     * @see com.tibco.xpd.xpdl2.BPMNModelPortabilityConformance
     * @see #getBpmnModelPortabilityConformance()
     * @generated
     */
    void setBpmnModelPortabilityConformance(
            BPMNModelPortabilityConformance value);

} // ConformanceClass
