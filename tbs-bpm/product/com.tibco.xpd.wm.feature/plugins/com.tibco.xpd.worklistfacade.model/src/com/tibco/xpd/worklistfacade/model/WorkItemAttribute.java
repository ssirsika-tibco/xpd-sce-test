/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute#getDisplayLabel <em>Display Label</em>}</li>
 *   <li>{@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#getWorkItemAttribute()
 * @model extendedMetaData="name='WorkItemAttribute' kind='elementOnly'"
 * @generated
 */
public interface WorkItemAttribute extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Display Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Display Label</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Display Label</em>' attribute.
     * @see #setDisplayLabel(String)
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#getWorkItemAttribute_DisplayLabel()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='DisplayLabel' namespace='##targetNamespace'"
     * @generated
     */
    String getDisplayLabel();

    /**
     * Sets the value of the '{@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute#getDisplayLabel <em>Display Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Display Label</em>' attribute.
     * @see #getDisplayLabel()
     * @generated
     */
    void setDisplayLabel(String value);

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#getWorkItemAttribute_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='element' name='Name' namespace='##targetNamespace'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.worklistfacade.model.WorkItemAttribute#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // WorkItemAttribute
