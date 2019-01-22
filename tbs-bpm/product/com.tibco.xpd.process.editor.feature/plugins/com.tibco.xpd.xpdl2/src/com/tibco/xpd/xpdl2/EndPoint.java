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
 * A representation of the model object '<em><b>End Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.EndPoint#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.EndPoint#getEndPointType <em>End Point Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndPoint()
 * @model extendedMetaData="name='EndPoint_._type' kind='elementOnly'"
 * @generated
 */
public interface EndPoint extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>External Reference</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>External Reference</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>External Reference</em>' containment reference.
     * @see #setExternalReference(ExternalReference)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndPoint_ExternalReference()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='ExternalReference' namespace='##targetNamespace'"
     * @generated
     */
    ExternalReference getExternalReference();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndPoint#getExternalReference <em>External Reference</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>External Reference</em>' containment reference.
     * @see #getExternalReference()
     * @generated
     */
    void setExternalReference(ExternalReference value);

    /**
     * Returns the value of the '<em><b>End Point Type</b></em>' attribute.
     * The default value is <code>"WSDL"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.EndPointTypeType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>End Point Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>End Point Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.EndPointTypeType
     * @see #isSetEndPointType()
     * @see #unsetEndPointType()
     * @see #setEndPointType(EndPointTypeType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getEndPoint_EndPointType()
     * @model default="WSDL" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='EndPointType'"
     * @generated
     */
    EndPointTypeType getEndPointType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.EndPoint#getEndPointType <em>End Point Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>End Point Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.EndPointTypeType
     * @see #isSetEndPointType()
     * @see #unsetEndPointType()
     * @see #getEndPointType()
     * @generated
     */
    void setEndPointType(EndPointTypeType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.EndPoint#getEndPointType <em>End Point Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetEndPointType()
     * @see #getEndPointType()
     * @see #setEndPointType(EndPointTypeType)
     * @generated
     */
    void unsetEndPointType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.EndPoint#getEndPointType <em>End Point Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>End Point Type</em>' attribute is set.
     * @see #unsetEndPointType()
     * @see #getEndPointType()
     * @see #setEndPointType(EndPointTypeType)
     * @generated
     */
    boolean isSetEndPointType();

} // EndPoint
