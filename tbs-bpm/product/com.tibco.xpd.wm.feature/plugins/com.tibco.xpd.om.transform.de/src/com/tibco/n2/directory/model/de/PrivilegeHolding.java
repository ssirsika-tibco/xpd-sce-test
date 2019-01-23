/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Privilege Holding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         This decribes the holding of a Privilege (assigned to a Group, Position or Org-Unit)
 *         and any qualifying value that applies to that holding.
 *         The term "qualifier" simply means that the association may carry a value that
 *         distinguishes two holdings of the same Privilege. For example, the Privilege
 *         to approve purchase orders may be qualified with the max value to which those
 *         purchase orders are made.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.PrivilegeHolding#getPrivilege <em>Privilege</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getPrivilegeHolding()
 * @model extendedMetaData="name='PrivilegeHolding' kind='elementOnly'"
 * @generated
 */
public interface PrivilegeHolding extends QualifiedHolding {
    /**
     * Returns the value of the '<em><b>Privilege</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *           Identifies, by its ID, the Privilege that this holding refers to.
     *             
     * <!-- end-model-doc -->
     * @return the value of the '<em>Privilege</em>' reference.
     * @see #setPrivilege(Privilege)
     * @see com.tibco.n2.directory.model.de.DePackage#getPrivilegeHolding_Privilege()
     * @model resolveProxies="false" required="true"
     *        extendedMetaData="kind='attribute' name='privilege'"
     * @generated
     */
    Privilege getPrivilege();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.PrivilegeHolding#getPrivilege <em>Privilege</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Privilege</em>' reference.
     * @see #getPrivilege()
     * @generated
     */
    void setPrivilege(Privilege value);

} // PrivilegeHolding
