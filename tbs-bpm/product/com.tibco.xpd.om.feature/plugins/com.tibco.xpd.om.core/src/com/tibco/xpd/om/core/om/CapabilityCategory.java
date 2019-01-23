/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capability Category</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.CapabilityCategory#getSubCategories <em>Sub Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.CapabilityCategory#getMembers <em>Members</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getCapabilityCategory()
 * @model
 * @generated
 */
public interface CapabilityCategory extends Namespace {
    /**
     * Returns the value of the '<em><b>Sub Categories</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.CapabilityCategory}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sub Categories</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sub Categories</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getCapabilityCategory_SubCategories()
     * @model containment="true"
     * @generated
     */
    EList<CapabilityCategory> getSubCategories();

    /**
     * Returns the value of the '<em><b>Members</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Capability}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.om.core.om.Capability#getCategory <em>Category</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Members</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Members</em>' reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getCapabilityCategory_Members()
     * @see com.tibco.xpd.om.core.om.Capability#getCategory
     * @model opposite="category"
     * @generated
     */
    EList<Capability> getMembers();

} // CapabilityCategory
