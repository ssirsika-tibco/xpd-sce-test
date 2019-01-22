/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.Group#getSubGroups <em>Sub Groups</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends OrgElement, Capable, Allocable, Authorizable,
        AssociableWithResources {
    /**
     * Returns the value of the '<em><b>Sub Groups</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.om.core.om.Group}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sub Groups</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Sub Groups</em>' containment reference list.
     * @see com.tibco.xpd.om.core.om.OMPackage#getGroup_SubGroups()
     * @model containment="true"
     * @generated
     */
    EList<Group> getSubGroups();

} // Group
