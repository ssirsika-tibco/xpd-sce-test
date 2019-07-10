/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Associated Parameters Container</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer#getAssociatedParameters <em>Associated Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer#isDisableImplicitAssociation <em>Disable Implicit Association</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParametersContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface AssociatedParametersContainer extends EObject {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Associated Parameters</b></em>'
     * containment reference list. The list contents are of type
     * {@link com.tibco.xpd.xpdExtension.AssociatedParameter}. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> Stores
     * the list of associated Parameter Objects linked to the Process Interface
     * whilst implementation. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Associated Parameters</em>' containment
     *         reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParametersContainer_AssociatedParameters()
     * @model containment="true" extendedMetaData=
     *        "kind='element' name='AssociatedParameter' namespace='##targetNamespace' wrap='AssociatedParameters'"
     * @generated
     */
    EList<AssociatedParameter> getAssociatedParameters();

    /**
     * Returns the value of the '<em><b>Disable Implicit Association</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Disable Implicit Association</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Disable Implicit Association</em>' attribute.
     * @see #isSetDisableImplicitAssociation()
     * @see #unsetDisableImplicitAssociation()
     * @see #setDisableImplicitAssociation(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAssociatedParametersContainer_DisableImplicitAssociation()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='DisableImplicitAssociation'"
     * @generated
     */
    boolean isDisableImplicitAssociation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer#isDisableImplicitAssociation <em>Disable Implicit Association</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Disable Implicit Association</em>' attribute.
     * @see #isSetDisableImplicitAssociation()
     * @see #unsetDisableImplicitAssociation()
     * @see #isDisableImplicitAssociation()
     * @generated
     */
    void setDisableImplicitAssociation(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer#isDisableImplicitAssociation <em>Disable Implicit Association</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDisableImplicitAssociation()
     * @see #isDisableImplicitAssociation()
     * @see #setDisableImplicitAssociation(boolean)
     * @generated
     */
    void unsetDisableImplicitAssociation();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AssociatedParametersContainer#isDisableImplicitAssociation <em>Disable Implicit Association</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Disable Implicit Association</em>' attribute is set.
     * @see #unsetDisableImplicitAssociation()
     * @see #isDisableImplicitAssociation()
     * @see #setDisableImplicitAssociation(boolean)
     * @generated
     */
    boolean isSetDisableImplicitAssociation();

} // AssociatedParametersContainer