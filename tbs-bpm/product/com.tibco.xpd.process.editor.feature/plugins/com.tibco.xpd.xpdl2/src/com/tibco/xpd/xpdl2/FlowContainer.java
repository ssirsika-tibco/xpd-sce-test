/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.FlowContainer#getActivities <em>Activities</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.FlowContainer#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.FlowContainer#isAdHoc <em>Ad Hoc</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.FlowContainer#getAdHocCompletionCondition <em>Ad Hoc Completion Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.FlowContainer#getAdHocOrdering <em>Ad Hoc Ordering</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.FlowContainer#getDefaultStartActivityId <em>Default Start Activity Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFlowContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface FlowContainer extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Activities</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Activity}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Activity#getFlowContainer <em>Flow Container</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activities</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activities</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFlowContainer_Activities()
     * @see com.tibco.xpd.xpdl2.Activity#getFlowContainer
     * @model opposite="flowContainer" containment="true"
     *        extendedMetaData="kind='element' name='Activity' namespace='##targetNamespace' wrap='Activities'"
     * @generated
     */
    EList<Activity> getActivities();

    /**
     * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Transition}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Transition#getFlowContainer <em>Flow Container</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Transitions</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Transitions</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFlowContainer_Transitions()
     * @see com.tibco.xpd.xpdl2.Transition#getFlowContainer
     * @model opposite="flowContainer" containment="true"
     *        extendedMetaData="kind='element' name='Transition' namespace='##targetNamespace' wrap='Transitions'"
     * @generated
     */
    EList<Transition> getTransitions();

    /**
     * Returns the value of the '<em><b>Ad Hoc</b></em>' attribute.
     * The default value is <code>"false"</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * BPMN: for Embedded subprocess
     * <!-- end-model-doc -->
     * @return the value of the '<em>Ad Hoc</em>' attribute.
     * @see #isSetAdHoc()
     * @see #unsetAdHoc()
     * @see #setAdHoc(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFlowContainer_AdHoc()
     * @model default="false" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='AdHoc'"
     * @generated
     */
    boolean isAdHoc();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.FlowContainer#isAdHoc <em>Ad Hoc</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ad Hoc</em>' attribute.
     * @see #isSetAdHoc()
     * @see #unsetAdHoc()
     * @see #isAdHoc()
     * @generated
     */
    void setAdHoc(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.FlowContainer#isAdHoc <em>Ad Hoc</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAdHoc()
     * @see #isAdHoc()
     * @see #setAdHoc(boolean)
     * @generated
     */
    void unsetAdHoc();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.FlowContainer#isAdHoc <em>Ad Hoc</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Ad Hoc</em>' attribute is set.
     * @see #unsetAdHoc()
     * @see #isAdHoc()
     * @see #setAdHoc(boolean)
     * @generated
     */
    boolean isSetAdHoc();

    /**
     * Returns the value of the '<em><b>Ad Hoc Completion Condition</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * BPMN: for Embedded subprocess
     * <!-- end-model-doc -->
     * @return the value of the '<em>Ad Hoc Completion Condition</em>' attribute.
     * @see #setAdHocCompletionCondition(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFlowContainer_AdHocCompletionCondition()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='AdHocCompletionCondition'"
     * @generated
     */
    String getAdHocCompletionCondition();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.FlowContainer#getAdHocCompletionCondition <em>Ad Hoc Completion Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ad Hoc Completion Condition</em>' attribute.
     * @see #getAdHocCompletionCondition()
     * @generated
     */
    void setAdHocCompletionCondition(String value);

    /**
     * Returns the value of the '<em><b>Ad Hoc Ordering</b></em>' attribute.
     * The default value is <code>"Parallel"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.AdHocOrderingType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * BPMN: for Embedded subprocess
     * <!-- end-model-doc -->
     * @return the value of the '<em>Ad Hoc Ordering</em>' attribute.
     * @see com.tibco.xpd.xpdl2.AdHocOrderingType
     * @see #isSetAdHocOrdering()
     * @see #unsetAdHocOrdering()
     * @see #setAdHocOrdering(AdHocOrderingType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFlowContainer_AdHocOrdering()
     * @model default="Parallel" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='AdHocOrdering'"
     * @generated
     */
    AdHocOrderingType getAdHocOrdering();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.FlowContainer#getAdHocOrdering <em>Ad Hoc Ordering</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ad Hoc Ordering</em>' attribute.
     * @see com.tibco.xpd.xpdl2.AdHocOrderingType
     * @see #isSetAdHocOrdering()
     * @see #unsetAdHocOrdering()
     * @see #getAdHocOrdering()
     * @generated
     */
    void setAdHocOrdering(AdHocOrderingType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.FlowContainer#getAdHocOrdering <em>Ad Hoc Ordering</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAdHocOrdering()
     * @see #getAdHocOrdering()
     * @see #setAdHocOrdering(AdHocOrderingType)
     * @generated
     */
    void unsetAdHocOrdering();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.FlowContainer#getAdHocOrdering <em>Ad Hoc Ordering</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Ad Hoc Ordering</em>' attribute is set.
     * @see #unsetAdHocOrdering()
     * @see #getAdHocOrdering()
     * @see #setAdHocOrdering(AdHocOrderingType)
     * @generated
     */
    boolean isSetAdHocOrdering();

    /**
     * Returns the value of the '<em><b>Default Start Activity Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Default Start Activity Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Default Start Activity Id</em>' attribute.
     * @see #setDefaultStartActivityId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getFlowContainer_DefaultStartActivityId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='DefaultStartActivityId'"
     * @generated
     */
    String getDefaultStartActivityId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.FlowContainer#getDefaultStartActivityId <em>Default Start Activity Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default Start Activity Id</em>' attribute.
     * @see #getDefaultStartActivityId()
     * @generated
     */
    void setDefaultStartActivityId(String value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    Activity getActivity(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    Transition getTransition(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model
     * @generated
     */
    EList<Activity> findStartActivities();

} // FlowContainer
