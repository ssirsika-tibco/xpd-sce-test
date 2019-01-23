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
 * A representation of the model object '<em><b>Presentation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.Presentation#getBindings <em>Bindings</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getPresentation()
 * @model
 * @generated
 */
public interface Presentation extends NamedElement {
    /**
     * Returns the value of the '<em><b>Bindings</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channeltypes.ChannelType}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getPresentation <em>Presentation</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bindings</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Bindings</em>' reference list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getPresentation_Bindings()
     * @see com.tibco.xpd.presentation.channeltypes.ChannelType#getPresentation
     * @model opposite="presentation"
     * @generated
     */
    EList<ChannelType> getBindings();

} // Presentation
