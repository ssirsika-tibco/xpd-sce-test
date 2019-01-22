/**
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.worklistfacade.model;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work List Facade</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.worklistfacade.model.WorkListFacade#getWorkItemAttributes <em>Work Item Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.worklistfacade.model.WorkListFacade#getFormatVersion <em>Format Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#getWorkListFacade()
 * @model extendedMetaData="name='WorkListFacade' kind='elementOnly'"
 * @generated
 */
public interface WorkListFacade extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Work Item Attributes</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Work Item Attributes</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Work Item Attributes</em>' containment reference.
     * @see #setWorkItemAttributes(WorkItemAttributes)
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#getWorkListFacade_WorkItemAttributes()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='WorkItemAttributes' namespace='##targetNamespace'"
     * @generated
     */
    WorkItemAttributes getWorkItemAttributes();

    /**
     * Sets the value of the '{@link com.tibco.xpd.worklistfacade.model.WorkListFacade#getWorkItemAttributes <em>Work Item Attributes</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Item Attributes</em>' containment reference.
     * @see #getWorkItemAttributes()
     * @generated
     */
    void setWorkItemAttributes(WorkItemAttributes value);

    /**
     * Returns the value of the '<em><b>Format Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Format Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Format Version</em>' attribute.
     * @see #setFormatVersion(BigInteger)
     * @see com.tibco.xpd.worklistfacade.model.WorkListFacadePackage#getWorkListFacade_FormatVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Integer"
     *        extendedMetaData="kind='attribute' name='FormatVersion'"
     * @generated
     */
    BigInteger getFormatVersion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.worklistfacade.model.WorkListFacade#getFormatVersion <em>Format Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Format Version</em>' attribute.
     * @see #getFormatVersion()
     * @generated
     */
    void setFormatVersion(BigInteger value);

} // WorkListFacade
