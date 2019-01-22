/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Header</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getXpdlVersion <em>Xpdl Version</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getVendor <em>Vendor</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getCreated <em>Created</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getPriorityUnit <em>Priority Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getCostUnit <em>Cost Unit</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getVendorExtensions <em>Vendor Extensions</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getLayoutInfo <em>Layout Info</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.PackageHeader#getModificationDate <em>Modification Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader()
 * @model extendedMetaData="name='PackageHeader_._type' kind='elementOnly' features-order='xPDLVersion vendor created description documentation priorityUnit costUnit vendorExtensions'"
 * @generated
 */
public interface PackageHeader extends DescribedElement,
        OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Xpdl Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Xpdl Version</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Xpdl Version</em>' attribute.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_XpdlVersion()
     * @model required="true" changeable="false"
     *        extendedMetaData="kind='element' name='XPDLVersion' namespace='##targetNamespace'"
     * @generated
     */
    String getXpdlVersion();

    /**
     * Returns the value of the '<em><b>Vendor</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Vendor</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Vendor</em>' attribute.
     * @see #setVendor(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_Vendor()
     * @model required="true"
     *        extendedMetaData="kind='element' name='Vendor' namespace='##targetNamespace'"
     * @generated
     */
    String getVendor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PackageHeader#getVendor <em>Vendor</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Vendor</em>' attribute.
     * @see #getVendor()
     * @generated
     */
    void setVendor(String value);

    /**
     * Returns the value of the '<em><b>Created</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Created</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Created</em>' attribute.
     * @see #setCreated(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_Created()
     * @model required="true"
     *        extendedMetaData="kind='element' name='Created' namespace='##targetNamespace'"
     * @generated
     */
    String getCreated();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PackageHeader#getCreated <em>Created</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Created</em>' attribute.
     * @see #getCreated()
     * @generated
     */
    void setCreated(String value);

    /**
     * Returns the value of the '<em><b>Documentation</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Documentation</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Documentation</em>' containment reference.
     * @see #setDocumentation(Documentation)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_Documentation()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Documentation' namespace='##targetNamespace'"
     * @generated
     */
    Documentation getDocumentation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PackageHeader#getDocumentation <em>Documentation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Documentation</em>' containment reference.
     * @see #getDocumentation()
     * @generated
     */
    void setDocumentation(Documentation value);

    /**
     * Returns the value of the '<em><b>Priority Unit</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Priority Unit</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Priority Unit</em>' containment reference.
     * @see #setPriorityUnit(PriorityUnit)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_PriorityUnit()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='PriorityUnit' namespace='##targetNamespace'"
     * @generated
     */
    PriorityUnit getPriorityUnit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PackageHeader#getPriorityUnit <em>Priority Unit</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Priority Unit</em>' containment reference.
     * @see #getPriorityUnit()
     * @generated
     */
    void setPriorityUnit(PriorityUnit value);

    /**
     * Returns the value of the '<em><b>Cost Unit</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cost Unit</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Cost Unit</em>' containment reference.
     * @see #setCostUnit(CostUnit)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_CostUnit()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='CostUnit' namespace='##targetNamespace'"
     * @generated
     */
    CostUnit getCostUnit();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PackageHeader#getCostUnit <em>Cost Unit</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Cost Unit</em>' containment reference.
     * @see #getCostUnit()
     * @generated
     */
    void setCostUnit(CostUnit value);

    /**
     * Returns the value of the '<em><b>Vendor Extensions</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Vendor Extensions</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Vendor Extensions</em>' containment reference.
     * @see #setVendorExtensions(VendorExtensions)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_VendorExtensions()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='VendorExtensions' namespace='##targetNamespace'"
     * @generated
     */
    VendorExtensions getVendorExtensions();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PackageHeader#getVendorExtensions <em>Vendor Extensions</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Vendor Extensions</em>' containment reference.
     * @see #getVendorExtensions()
     * @generated
     */
    void setVendorExtensions(VendorExtensions value);

    /**
     * Returns the value of the '<em><b>Layout Info</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Layout Info</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Layout Info</em>' containment reference.
     * @see #setLayoutInfo(LayoutInfo)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_LayoutInfo()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='LayoutInfo' namespace='##targetNamespace'"
     * @generated
     */
    LayoutInfo getLayoutInfo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PackageHeader#getLayoutInfo <em>Layout Info</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Layout Info</em>' containment reference.
     * @see #getLayoutInfo()
     * @generated
     */
    void setLayoutInfo(LayoutInfo value);

    /**
     * Returns the value of the '<em><b>Modification Date</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Modification Date</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Modification Date</em>' containment reference.
     * @see #setModificationDate(ModificationDate)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackageHeader_ModificationDate()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ModificationDate' namespace='##targetNamespace'"
     * @generated
     */
    ModificationDate getModificationDate();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.PackageHeader#getModificationDate <em>Modification Date</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Modification Date</em>' containment reference.
     * @see #getModificationDate()
     * @generated
     */
    void setModificationDate(ModificationDate value);

} // PackageHeader
