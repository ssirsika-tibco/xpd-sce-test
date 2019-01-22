/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.DescribedElement;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Interface Method</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getTrigger <em>Trigger</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getTriggerResultMessage <em>Trigger Result Message</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getVisibility <em>Visibility</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getErrorMethods <em>Error Methods</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getInterfaceMethod()
 * @model interface="true" abstract="true"
 *        annotation="ExtendedMetaData kind='element' name='InterfaceMethod' namespace='##targetNamespace'"
 * @generated
 */
public interface InterfaceMethod
        extends NamedElement, DescribedElement, AssociatedParametersContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Trigger</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.TriggerType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute is to store the process designers intent to 
     * have this processes content in-lined into calling processes in
     * the execution environment.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Trigger</em>' attribute.
     * @see com.tibco.xpd.xpdl2.TriggerType
     * @see #setTrigger(TriggerType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getInterfaceMethod_Trigger()
     * @model required="true"
     *        extendedMetaData="kind='attribute' name='Trigger'"
     * @generated
     */
    TriggerType getTrigger();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getTrigger <em>Trigger</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger</em>' attribute.
     * @see com.tibco.xpd.xpdl2.TriggerType
     * @see #getTrigger()
     * @generated
     */
    void setTrigger(TriggerType value);

    /**
     * Returns the value of the '<em><b>Trigger Result Message</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This attribute is used to store the trigger result message.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Trigger Result Message</em>' containment reference.
     * @see #setTriggerResultMessage(TriggerResultMessage)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getInterfaceMethod_TriggerResultMessage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TriggerResultMessage' namespace='##targetNamespace'"
     * @generated
     */
    TriggerResultMessage getTriggerResultMessage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getTriggerResultMessage <em>Trigger Result Message</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Trigger Result Message</em>' containment reference.
     * @see #getTriggerResultMessage()
     * @generated
     */
    void setTriggerResultMessage(TriggerResultMessage value);

    /**
     * Returns the value of the '<em><b>Visibility</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.Visibility}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Visibility</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Visibility</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.Visibility
     * @see #isSetVisibility()
     * @see #unsetVisibility()
     * @see #setVisibility(Visibility)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getInterfaceMethod_Visibility()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='Visibility'"
     * @generated
     */
    Visibility getVisibility();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getVisibility <em>Visibility</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Visibility</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.Visibility
     * @see #isSetVisibility()
     * @see #unsetVisibility()
     * @see #getVisibility()
     * @generated
     */
    void setVisibility(Visibility value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getVisibility <em>Visibility</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetVisibility()
     * @see #getVisibility()
     * @see #setVisibility(Visibility)
     * @generated
     */
    void unsetVisibility();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.InterfaceMethod#getVisibility <em>Visibility</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Visibility</em>' attribute is set.
     * @see #unsetVisibility()
     * @see #getVisibility()
     * @see #setVisibility(Visibility)
     * @generated
     */
    boolean isSetVisibility();

    /**
     * Returns the value of the '<em><b>Error Methods</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ErrorMethod}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Error Methods</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Error Methods</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getInterfaceMethod_ErrorMethods()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ErrorMethod' namespace='##targetNamespace' wrap='ErrorMethods'"
     * @generated
     */
    EList<ErrorMethod> getErrorMethods();

} // InterfaceMethod
