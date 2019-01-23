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
 * A representation of the model object '<em><b>Redefinable Header</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.RedefinableHeader#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.RedefinableHeader#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.RedefinableHeader#getCodepage <em>Codepage</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.RedefinableHeader#getCountrykey <em>Countrykey</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.RedefinableHeader#getResponsibles <em>Responsibles</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.RedefinableHeader#getPublicationStatus <em>Publication Status</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRedefinableHeader()
 * @model extendedMetaData="name='RedefinableHeader_._type' kind='elementOnly'"
 * @generated
 */
public interface RedefinableHeader extends OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Author</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Author</em>' attribute.
     * @see #isSetAuthor()
     * @see #unsetAuthor()
     * @see #setAuthor(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRedefinableHeader_Author()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='element' name='Author' namespace='##targetNamespace'"
     * @generated
     */
    String getAuthor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getAuthor <em>Author</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Author</em>' attribute.
     * @see #isSetAuthor()
     * @see #unsetAuthor()
     * @see #getAuthor()
     * @generated
     */
    void setAuthor(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getAuthor <em>Author</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAuthor()
     * @see #getAuthor()
     * @see #setAuthor(String)
     * @generated
     */
    void unsetAuthor();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getAuthor <em>Author</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Author</em>' attribute is set.
     * @see #unsetAuthor()
     * @see #getAuthor()
     * @see #setAuthor(String)
     * @generated
     */
    boolean isSetAuthor();

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #isSetVersion()
     * @see #unsetVersion()
     * @see #setVersion(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRedefinableHeader_Version()
     * @model unique="false" unsettable="true"
     *        extendedMetaData="kind='element' name='Version' namespace='##targetNamespace'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #isSetVersion()
     * @see #unsetVersion()
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetVersion()
     * @see #getVersion()
     * @see #setVersion(String)
     * @generated
     */
    void unsetVersion();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getVersion <em>Version</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Version</em>' attribute is set.
     * @see #unsetVersion()
     * @see #getVersion()
     * @see #setVersion(String)
     * @generated
     */
    boolean isSetVersion();

    /**
     * Returns the value of the '<em><b>Codepage</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Codepage</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Codepage</em>' containment reference.
     * @see #setCodepage(Codepage)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRedefinableHeader_Codepage()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Codepage' namespace='##targetNamespace'"
     * @generated
     */
    Codepage getCodepage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getCodepage <em>Codepage</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Codepage</em>' containment reference.
     * @see #getCodepage()
     * @generated
     */
    void setCodepage(Codepage value);

    /**
     * Returns the value of the '<em><b>Countrykey</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Countrykey</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Countrykey</em>' containment reference.
     * @see #setCountrykey(CountryKey)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRedefinableHeader_Countrykey()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Countrykey' namespace='##targetNamespace'"
     * @generated
     */
    CountryKey getCountrykey();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getCountrykey <em>Countrykey</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Countrykey</em>' containment reference.
     * @see #getCountrykey()
     * @generated
     */
    void setCountrykey(CountryKey value);

    /**
     * Returns the value of the '<em><b>Responsibles</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Responsible}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Responsibles</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Responsibles</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRedefinableHeader_Responsibles()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Responsible' namespace='##targetNamespace' wrap='Responsibles'"
     * @generated
     */
    EList<Responsible> getResponsibles();

    /**
     * Returns the value of the '<em><b>Publication Status</b></em>' attribute.
     * The default value is <code>"UNDER_REVISION"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.PublicationStatusType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Publication Status</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Publication Status</em>' attribute.
     * @see com.tibco.xpd.xpdl2.PublicationStatusType
     * @see #isSetPublicationStatus()
     * @see #unsetPublicationStatus()
     * @see #setPublicationStatus(PublicationStatusType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getRedefinableHeader_PublicationStatus()
     * @model default="UNDER_REVISION" unique="false" unsettable="true"
     *        extendedMetaData="kind='attribute' name='PublicationStatus'"
     * @generated
     */
    PublicationStatusType getPublicationStatus();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getPublicationStatus <em>Publication Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Publication Status</em>' attribute.
     * @see com.tibco.xpd.xpdl2.PublicationStatusType
     * @see #isSetPublicationStatus()
     * @see #unsetPublicationStatus()
     * @see #getPublicationStatus()
     * @generated
     */
    void setPublicationStatus(PublicationStatusType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getPublicationStatus <em>Publication Status</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPublicationStatus()
     * @see #getPublicationStatus()
     * @see #setPublicationStatus(PublicationStatusType)
     * @generated
     */
    void unsetPublicationStatus();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.RedefinableHeader#getPublicationStatus <em>Publication Status</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Publication Status</em>' attribute is set.
     * @see #unsetPublicationStatus()
     * @see #getPublicationStatus()
     * @see #setPublicationStatus(PublicationStatusType)
     * @generated
     */
    boolean isSetPublicationStatus();

} // RedefinableHeader
