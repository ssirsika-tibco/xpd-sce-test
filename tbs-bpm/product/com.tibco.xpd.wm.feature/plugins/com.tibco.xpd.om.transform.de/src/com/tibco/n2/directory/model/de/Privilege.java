/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.directory.model.de;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Privilege</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *         A Privilege can be assigned to Groups, Position and Org-Units. A Position also
 *         inherits the Privileges of the Org-Unit to which it belongs. Groups inherit the
 *         Privileges held by their parent Group.
 *         Privileges define the authority of the human resources associated with those
 *         Privilege holdings. For example, the authority to approve purchase orders over
 *         a certain amount.
 *       
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.directory.model.de.Privilege#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.directory.model.de.DePackage#getPrivilege()
 * @model extendedMetaData="name='Privilege' kind='elementOnly'"
 * @generated
 */
public interface Privilege extends NamedEntity {
    /**
     * Returns the value of the '<em><b>Qualifier</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     *                 Describes the qualifier attribute that can be used to refine any Privilege
     *                 assignment. The PrivilegeHolding will provide any value(s) for this qualifier.
     *               
     * <!-- end-model-doc -->
     * @return the value of the '<em>Qualifier</em>' containment reference.
     * @see #setQualifier(Qualifier)
     * @see com.tibco.n2.directory.model.de.DePackage#getPrivilege_Qualifier()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='qualifier' namespace='##targetNamespace'"
     * @generated
     */
    Qualifier getQualifier();

    /**
     * Sets the value of the '{@link com.tibco.n2.directory.model.de.Privilege#getQualifier <em>Qualifier</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Qualifier</em>' containment reference.
     * @see #getQualifier()
     * @generated
     */
    void setQualifier(Qualifier value);

} // Privilege
