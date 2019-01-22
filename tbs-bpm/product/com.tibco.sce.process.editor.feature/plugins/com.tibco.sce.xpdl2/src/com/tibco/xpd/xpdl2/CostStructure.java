/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cost Structure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.CostStructure#getFixedCost <em>Fixed Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.CostStructure#getResourceCosts <em>Resource Costs</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getCostStructure()
 * @model extendedMetaData="name='CostStructure_._type' kind='elementOnly'"
 * @generated
 */
public interface CostStructure extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Fixed Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Fixed Cost</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Fixed Cost</em>' attribute.
     * @see #setFixedCost(BigInteger)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getCostStructure_FixedCost()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='FixedCost'"
     * @generated
     */
    BigInteger getFixedCost();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.CostStructure#getFixedCost <em>Fixed Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Fixed Cost</em>' attribute.
     * @see #getFixedCost()
     * @generated
     */
    void setFixedCost(BigInteger value);

    /**
     * Returns the value of the '<em><b>Resource Costs</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.ResourceCosts}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Costs</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Costs</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getCostStructure_ResourceCosts()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ResourceCosts' namespace='##targetNamespace'"
     * @generated
     */
    EList<ResourceCosts> getResourceCosts();

} // CostStructure
