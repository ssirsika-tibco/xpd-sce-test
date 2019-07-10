/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import java.math.BigDecimal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Costs</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ResourceCosts#getResourceCost <em>Resource Cost</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ResourceCosts#getCostUnitOfTime <em>Cost Unit Of Time</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResourceCosts()
 * @model
 * @generated
 */
public interface ResourceCosts extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Resource Cost</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Resource Cost</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Resource Cost</em>' attribute.
     * @see #setResourceCost(BigDecimal)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResourceCosts_ResourceCost()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Decimal"
     *        extendedMetaData="kind='attribute' name='ResourceCost'"
     * @generated
     */
    BigDecimal getResourceCost();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ResourceCosts#getResourceCost <em>Resource Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Resource Cost</em>' attribute.
     * @see #getResourceCost()
     * @generated
     */
    void setResourceCost(BigDecimal value);

    /**
     * Returns the value of the '<em><b>Cost Unit Of Time</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cost Unit Of Time</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cost Unit Of Time</em>' attribute.
     * @see #setCostUnitOfTime(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getResourceCosts_CostUnitOfTime()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='CostUnitOfTime'"
     * @generated
     */
    String getCostUnitOfTime();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ResourceCosts#getCostUnitOfTime <em>Cost Unit Of Time</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cost Unit Of Time</em>' attribute.
     * @see #getCostUnitOfTime()
     * @generated
     */
    void setCostUnitOfTime(String value);

} // ResourceCosts
