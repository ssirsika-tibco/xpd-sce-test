/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger Result Signal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getCatchThrow <em>Catch Throw</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getProperties <em>Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultSignal()
 * @model extendedMetaData="name='TriggerResultSignal_._type' kind='elementOnly'"
 * @generated
 */
public interface TriggerResultSignal extends OtherAttributesContainer,
        OtherElementsContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Expression}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Properties</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Properties</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultSignal_Properties()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='Properties' namespace='##targetNamespace'"
     * @generated
     */
    EList<Expression> getProperties();

    /**
     * Returns the value of the '<em><b>Catch Throw</b></em>' attribute.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultSignal_CatchThrow()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='CatchThrow'"
     * @generated
     */
    CatchThrow getCatchThrow();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getCatchThrow <em>Catch Throw</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getCatchThrow <em>Catch Throw</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetCatchThrow()
     * @see #getCatchThrow()
     * @see #setCatchThrow(CatchThrow)
     * @generated
     */
    void unsetCatchThrow();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getCatchThrow <em>Catch Throw</em>}' attribute is set.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getTriggerResultSignal_Name()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='Name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.TriggerResultSignal#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

} // TriggerResultSignal
