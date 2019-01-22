/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.worklistfacade.model.WorkItemAttributes#getWorkItemAttribute <em>Work Item Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#getWorkItemAttributes()
 * @model extendedMetaData="name='WorkItemAttributes' kind='elementOnly'"
 * @generated
 */
public interface WorkItemAttributes extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Work Item Attribute</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item Attribute</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item Attribute</em>' containment reference list.
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#getWorkItemAttributes_WorkItemAttribute()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WorkItemAttribute' namespace='##targetNamespace'"
     * @generated
     */
    EList<WorkItemAttribute> getWorkItemAttribute();

} // WorkItemAttributes
