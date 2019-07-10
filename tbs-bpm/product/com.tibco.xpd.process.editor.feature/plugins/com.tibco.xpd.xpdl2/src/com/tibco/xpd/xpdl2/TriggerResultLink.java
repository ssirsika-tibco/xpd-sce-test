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
 * A representation of the model object '<em><b>Trigger Result Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultLink#getDeprecatedLinkId <em>Deprecated Link Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultLink#getDeprecatedProcessRef <em>Deprecated Process Ref</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultLink#getCatchThrow <em>Catch Throw</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultLink#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultLink()
 * @model extendedMetaData="name='TriggerResultLink_._type' kind='elementOnly'"
 * @generated
 */
public interface TriggerResultLink extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Deprecated Link Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Deprecated Link Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Deprecated Link Id</em>' attribute.
     * @see #setDeprecatedLinkId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultLink_DeprecatedLinkId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN" required="true"
     *        extendedMetaData="kind='attribute' name='LinkId'"
     * @generated
     */
    String getDeprecatedLinkId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getDeprecatedLinkId <em>Deprecated Link Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Link Id</em>' attribute.
     * @see #getDeprecatedLinkId()
     * @generated
     */
    void setDeprecatedLinkId(String value);

    /**
     * Returns the value of the '<em><b>Deprecated Process Ref</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This must identify a Process. Should be the Id of a process.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Deprecated Process Ref</em>' attribute.
     * @see #setDeprecatedProcessRef(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultLink_DeprecatedProcessRef()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='ProcessRef'"
     * @generated
     */
    String getDeprecatedProcessRef();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getDeprecatedProcessRef <em>Deprecated Process Ref</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Deprecated Process Ref</em>' attribute.
     * @see #getDeprecatedProcessRef()
     * @generated
     */
    void setDeprecatedProcessRef(String value);

    /**
     * Returns the value of the '<em><b>Catch Throw</b></em>' attribute.
     * The default value is <code>"CATCH"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.CatchThrow}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Catch Throw</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Catch Throw</em>' attribute.
     * @see com.tibco.xpd.xpdl2.CatchThrow
     * @see #isSetCatchThrow()
     * @see #unsetCatchThrow()
     * @see #setCatchThrow(CatchThrow)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultLink_CatchThrow()
     * @model default="CATCH" unsettable="true"
     *        extendedMetaData="kind='attribute' name='CatchThrow'"
     * @generated
     */
    CatchThrow getCatchThrow();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getCatchThrow <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Catch Throw</em>' attribute.
     * @see com.tibco.xpd.xpdl2.CatchThrow
     * @see #isSetCatchThrow()
     * @see #unsetCatchThrow()
     * @see #getCatchThrow()
     * @generated
     */
    void setCatchThrow(CatchThrow value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getCatchThrow <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCatchThrow()
     * @see #getCatchThrow()
     * @see #setCatchThrow(CatchThrow)
     * @generated
     */
    void unsetCatchThrow();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getCatchThrow <em>Catch Throw</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Catch Throw</em>' attribute is set.
     * @see #unsetCatchThrow()
     * @see #getCatchThrow()
     * @see #setCatchThrow(CatchThrow)
     * @generated
     */
    boolean isSetCatchThrow();

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultLink_Name()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString" required="true"
     *        extendedMetaData="kind='attribute' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultLink#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // TriggerResultLink
