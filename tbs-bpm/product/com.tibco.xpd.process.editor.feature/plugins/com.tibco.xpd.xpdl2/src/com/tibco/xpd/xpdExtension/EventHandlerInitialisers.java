/**
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.NamedElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Handler Initialisers</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * EventHandlerInitialisers specify the 'Activity/Activities' that should be completed for event handler to be invoked/executed.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.EventHandlerInitialisers#getActivityRef <em>Activity Ref</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getEventHandlerInitialisers()
 * @model extendedMetaData="name='EventHandlerInitialisers' kind='elementOnly'"
 * @generated
 */
public interface EventHandlerInitialisers extends NamedElement {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Activity Ref</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ActivityRef}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Ref</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activity Ref</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getEventHandlerInitialisers_ActivityRef()
     * @model containment="true"
     *        annotation="http://www.eclipse.org/emf/2002/GenModel Documentation='List of initialisers for event handlers'"
     *        extendedMetaData="kind='element' name='ActivityRef' namespace='##targetNamespace'"
     * @generated
     */
    EList<ActivityRef> getActivityRef();

} // EventHandlerInitialisers
