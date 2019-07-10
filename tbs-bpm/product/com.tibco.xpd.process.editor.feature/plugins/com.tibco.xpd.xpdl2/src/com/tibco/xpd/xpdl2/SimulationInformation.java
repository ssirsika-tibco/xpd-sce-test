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
 * A representation of the model object '<em><b>Simulation Information</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.SimulationInformation#getCost <em>Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SimulationInformation#getTimeEstimation <em>Time Estimation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.SimulationInformation#getInstantiation <em>Instantiation</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSimulationInformation()
 * @model extendedMetaData="name='SimulationInformation_._type' kind='elementOnly'"
 * @generated
 */
public interface SimulationInformation extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Cost</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cost</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cost</em>' containment reference.
     * @see #setCost(Cost)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSimulationInformation_Cost()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Cost' namespace='##targetNamespace'"
     * @generated
     */
    Cost getCost();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SimulationInformation#getCost <em>Cost</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cost</em>' containment reference.
     * @see #getCost()
     * @generated
     */
    void setCost(Cost value);

    /**
     * Returns the value of the '<em><b>Time Estimation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Time Estimation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Time Estimation</em>' containment reference.
     * @see #setTimeEstimation(TimeEstimation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSimulationInformation_TimeEstimation()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='TimeEstimation' namespace='##targetNamespace'"
     * @generated
     */
    TimeEstimation getTimeEstimation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SimulationInformation#getTimeEstimation <em>Time Estimation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Time Estimation</em>' containment reference.
     * @see #getTimeEstimation()
     * @generated
     */
    void setTimeEstimation(TimeEstimation value);

    /**
     * Returns the value of the '<em><b>Instantiation</b></em>' attribute.
     * The default value is <code>"ONCE"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.InstantiationType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Instantiation</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Instantiation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.InstantiationType
     * @see #isSetInstantiation()
     * @see #unsetInstantiation()
     * @see #setInstantiation(InstantiationType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getSimulationInformation_Instantiation()
     * @model default="ONCE" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Instantiation'"
     * @generated
     */
    InstantiationType getInstantiation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.SimulationInformation#getInstantiation <em>Instantiation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Instantiation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.InstantiationType
     * @see #isSetInstantiation()
     * @see #unsetInstantiation()
     * @see #getInstantiation()
     * @generated
     */
    void setInstantiation(InstantiationType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.SimulationInformation#getInstantiation <em>Instantiation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetInstantiation()
     * @see #getInstantiation()
     * @see #setInstantiation(InstantiationType)
     * @generated
     */
    void unsetInstantiation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.SimulationInformation#getInstantiation <em>Instantiation</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Instantiation</em>' attribute is set.
     * @see #unsetInstantiation()
     * @see #getInstantiation()
     * @see #setInstantiation(InstantiationType)
     * @generated
     */
    boolean isSetInstantiation();

} // SimulationInformation
