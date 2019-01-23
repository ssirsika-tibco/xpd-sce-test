/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.Target#getBindings <em>Bindings</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getTarget()
 * @model
 * @generated
 */
public interface Target extends NamedElement {
    /**
     * Returns the value of the '<em><b>Bindings</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channeltypes.ChannelType}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bindings</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bindings</em>' reference list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getTarget_Bindings()
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getTarget
     * @model opposite="target" changeable="false"
     * @generated
     */
    EList<ChannelType> getBindings();

} // Target
