/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pool</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Pool#getLanes <em>Lanes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Pool#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Pool#isBoundaryVisible <em>Boundary Visible</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Pool#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Pool#getParticipantId <em>Participant Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Pool#getProcessId <em>Process Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Pool#getParentPackage <em>Parent Package</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Pool#isMainPool <em>Main Pool</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool()
 * @model extendedMetaData="name='Pool_._type' kind='elementOnly' features-order='lanes object nodeGraphicsInfos'"
 * @generated
 */
public interface Pool extends NamedElement, GraphicalNode, OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Lanes</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Lane}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Lane#getParentPool <em>Parent Pool</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lanes</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lanes</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool_Lanes()
     * @see com.tibco.xpd.xpdl2.Lane#getParentPool
     * @model opposite="parentPool" containment="true"
     *        extendedMetaData="kind='element' name='Lane' namespace='##targetNamespace' wrap='Lanes'"
     * @generated
     */
    EList<Lane> getLanes();

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Pool#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>Boundary Visible</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Boundary Visible</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Boundary Visible</em>' attribute.
     * @see #isSetBoundaryVisible()
     * @see #unsetBoundaryVisible()
     * @see #setBoundaryVisible(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool_BoundaryVisible()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
     *        extendedMetaData="kind='attribute' name='BoundaryVisible'"
     * @generated
     */
    boolean isBoundaryVisible();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Pool#isBoundaryVisible <em>Boundary Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Boundary Visible</em>' attribute.
     * @see #isSetBoundaryVisible()
     * @see #unsetBoundaryVisible()
     * @see #isBoundaryVisible()
     * @generated
     */
    void setBoundaryVisible(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Pool#isBoundaryVisible <em>Boundary Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetBoundaryVisible()
     * @see #isBoundaryVisible()
     * @see #setBoundaryVisible(boolean)
     * @generated
     */
    void unsetBoundaryVisible();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Pool#isBoundaryVisible <em>Boundary Visible</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Boundary Visible</em>' attribute is set.
     * @see #unsetBoundaryVisible()
     * @see #isBoundaryVisible()
     * @see #setBoundaryVisible(boolean)
     * @generated
     */
    boolean isSetBoundaryVisible();

    /**
     * Returns the value of the '<em><b>Orientation</b></em>' attribute.
     * The default value is <code>"HORIZONTAL"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.OrientationType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Orientation</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Orientation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.OrientationType
     * @see #isSetOrientation()
     * @see #unsetOrientation()
     * @see #setOrientation(OrientationType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool_Orientation()
     * @model default="HORIZONTAL" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='Orientation'"
     * @generated
     */
    OrientationType getOrientation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Pool#getOrientation <em>Orientation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Orientation</em>' attribute.
     * @see com.tibco.xpd.xpdl2.OrientationType
     * @see #isSetOrientation()
     * @see #unsetOrientation()
     * @see #getOrientation()
     * @generated
     */
    void setOrientation(OrientationType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Pool#getOrientation <em>Orientation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOrientation()
     * @see #getOrientation()
     * @see #setOrientation(OrientationType)
     * @generated
     */
    void unsetOrientation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Pool#getOrientation <em>Orientation</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Orientation</em>' attribute is set.
     * @see #unsetOrientation()
     * @see #getOrientation()
     * @see #setOrientation(OrientationType)
     * @generated
     */
    boolean isSetOrientation();

    /**
     * Returns the value of the '<em><b>Participant Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Participant Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Participant Id</em>' attribute.
     * @see #setParticipantId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool_ParticipantId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='Participant'"
     * @generated
     */
    String getParticipantId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Pool#getParticipantId <em>Participant Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Participant Id</em>' attribute.
     * @see #getParticipantId()
     * @generated
     */
    void setParticipantId(String value);

    /**
     * Returns the value of the '<em><b>Process Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Process Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Process Id</em>' attribute.
     * @see #setProcessId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool_ProcessId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='Process'"
     * @generated
     */
    String getProcessId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Pool#getProcessId <em>Process Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Process Id</em>' attribute.
     * @see #getProcessId()
     * @generated
     */
    void setProcessId(String value);

    /**
     * Returns the value of the '<em><b>Parent Package</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Package#getPools <em>Pools</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parent Package</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parent Package</em>' container reference.
     * @see #setParentPackage(com.tibco.xpd.xpdl2.Package)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool_ParentPackage()
     * @see com.tibco.xpd.xpdl2.Package#getPools
     * @model opposite="pools" transient="false"
     * @generated
     */
    com.tibco.xpd.xpdl2.Package getParentPackage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Pool#getParentPackage <em>Parent Package</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Parent Package</em>' container reference.
     * @see #getParentPackage()
     * @generated
     */
    void setParentPackage(com.tibco.xpd.xpdl2.Package value);

    /**
     * Returns the value of the '<em><b>Main Pool</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Main Pool</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Main Pool</em>' attribute.
     * @see #setMainPool(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPool_MainPool()
     * @model unique="false"
     *        extendedMetaData="kind='attribute' name='MainPool'"
     * @generated
     */
    boolean isMainPool();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Pool#isMainPool <em>Main Pool</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Main Pool</em>' attribute.
     * @see #isMainPool()
     * @generated
     */
    void setMainPool(boolean value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    Lane getLane(String id);

} // Pool
