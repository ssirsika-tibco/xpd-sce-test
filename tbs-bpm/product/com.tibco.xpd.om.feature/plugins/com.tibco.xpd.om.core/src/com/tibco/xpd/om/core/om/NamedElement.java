/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Named Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.NamedElement#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.NamedElement#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.NamedElement#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.NamedElement#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.NamedElement#getLabelKey <em>Label Key</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.NamedElement#getDisplayName <em>Display Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.om.core.om.OMPackage#getNamedElement()
 * @model abstract="true"
 * @generated
 */
public interface NamedElement extends ModelElement {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getNamedElement_Name()
     * @model default=""
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.NamedElement#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Namespace</b></em>' reference. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Namespace</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Namespace</em>' reference.
     * @see com.tibco.xpd.om.core.om.OMPackage#getNamedElement_Namespace()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    Namespace getNamespace();

    /**
     * Returns the value of the '<em><b>Label</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Label</em>' attribute.
     * @see com.tibco.xpd.om.core.om.OMPackage#getNamedElement_Label()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    String getLabel();

    /**
     * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the value of the '<em>Qualified Name</em>' attribute.
     * @see com.tibco.xpd.om.core.om.OMPackage#getNamedElement_QualifiedName()
     * @model default="" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    String getQualifiedName();

    /**
     * Returns the value of the '<em><b>Label Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label Key</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Label Key</em>' attribute.
     * @see com.tibco.xpd.om.core.om.OMPackage#getNamedElement_LabelKey()
     * @model transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    String getLabelKey();

    /**
     * Returns the value of the '<em><b>Display Name</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Display Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Display Name</em>' attribute.
     * @see #isSetDisplayName()
     * @see #unsetDisplayName()
     * @see #setDisplayName(String)
     * @see com.tibco.xpd.om.core.om.OMPackage#getNamedElement_DisplayName()
     * @model default="" unsettable="true"
     * @generated
     */
    String getDisplayName();

    /**
     * Sets the value of the '{@link com.tibco.xpd.om.core.om.NamedElement#getDisplayName <em>Display Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Display Name</em>' attribute.
     * @see #isSetDisplayName()
     * @see #unsetDisplayName()
     * @see #getDisplayName()
     * @generated
     */
    void setDisplayName(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.om.core.om.NamedElement#getDisplayName <em>Display Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDisplayName()
     * @see #getDisplayName()
     * @see #setDisplayName(String)
     * @generated
     */
    void unsetDisplayName();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.om.core.om.NamedElement#getDisplayName <em>Display Name</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Display Name</em>' attribute is set.
     * @see #unsetDisplayName()
     * @see #getDisplayName()
     * @see #setDisplayName(String)
     * @generated
     */
    boolean isSetDisplayName();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model
     * @generated
     */
    String getLabel(boolean localize);

} // NamedElement
