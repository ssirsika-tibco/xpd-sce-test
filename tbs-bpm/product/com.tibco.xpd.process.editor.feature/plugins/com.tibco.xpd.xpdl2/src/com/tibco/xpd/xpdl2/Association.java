/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Association</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Association#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Association#getAssociationDirection <em>Association Direction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Association#getSource <em>Source</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Association#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Association#getPackage <em>Package</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssociation()
 * @model extendedMetaData="name='Association_._type' kind='elementOnly' features-order='object connectorGraphicsInfos'"
 * @generated
 */
public interface Association extends NamedElement, GraphicalConnector {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object</em>' containment reference.
     * @see #setObject(com.tibco.xpd.xpdl2.Object)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssociation_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Association#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>Association Direction</b></em>' attribute.
     * The default value is <code>"None"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.AssociationDirectionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Association Direction</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Association Direction</em>' attribute.
     * @see com.tibco.xpd.xpdl2.AssociationDirectionType
     * @see #isSetAssociationDirection()
     * @see #unsetAssociationDirection()
     * @see #setAssociationDirection(AssociationDirectionType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssociation_AssociationDirection()
     * @model default="None" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='AssociationDirection'"
     * @generated
     */
    AssociationDirectionType getAssociationDirection();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Association#getAssociationDirection <em>Association Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Association Direction</em>' attribute.
     * @see com.tibco.xpd.xpdl2.AssociationDirectionType
     * @see #isSetAssociationDirection()
     * @see #unsetAssociationDirection()
     * @see #getAssociationDirection()
     * @generated
     */
    void setAssociationDirection(AssociationDirectionType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Association#getAssociationDirection <em>Association Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAssociationDirection()
     * @see #getAssociationDirection()
     * @see #setAssociationDirection(AssociationDirectionType)
     * @generated
     */
    void unsetAssociationDirection();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Association#getAssociationDirection <em>Association Direction</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Association Direction</em>' attribute is set.
     * @see #unsetAssociationDirection()
     * @see #getAssociationDirection()
     * @see #setAssociationDirection(AssociationDirectionType)
     * @generated
     */
    boolean isSetAssociationDirection();

    /**
     * Returns the value of the '<em><b>Source</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Source</em>' attribute.
     * @see #setSource(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssociation_Source()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='Source'"
     * @generated
     */
    String getSource();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Association#getSource <em>Source</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source</em>' attribute.
     * @see #getSource()
     * @generated
     */
    void setSource(String value);

    /**
     * Returns the value of the '<em><b>Target</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Target</em>' attribute.
     * @see #setTarget(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssociation_Target()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='Target'"
     * @generated
     */
    String getTarget();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Association#getTarget <em>Target</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target</em>' attribute.
     * @see #getTarget()
     * @generated
     */
    void setTarget(String value);

    /**
     * Returns the value of the '<em><b>Package</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Package#getAssociations <em>Associations</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package</em>' container reference.
     * @see #setPackage(com.tibco.xpd.xpdl2.Package)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getAssociation_Package()
     * @see com.tibco.xpd.xpdl2.Package#getAssociations
     * @model opposite="associations" transient="false"
     * @generated
     */
    com.tibco.xpd.xpdl2.Package getPackage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Association#getPackage <em>Package</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package</em>' container reference.
     * @see #getPackage()
     * @generated
     */
    void setPackage(com.tibco.xpd.xpdl2.Package value);

} // Association
