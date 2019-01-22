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
 * A representation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getPackageHeader <em>Package Header</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getRedefinableHeader <em>Redefinable Header</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getConformanceClass <em>Conformance Class</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getScript <em>Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getExternalPackages <em>External Packages</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getTypeDeclarations <em>Type Declarations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getPartnerLinkTypes <em>Partner Link Types</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getPools <em>Pools</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getMessageFlows <em>Message Flows</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getAssociations <em>Associations</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getArtifacts <em>Artifacts</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getProcesses <em>Processes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getLanguage <em>Language</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Package#getQueryLanguage <em>Query Language</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage()
 * @model extendedMetaData="name='Package' kind='elementOnly' features-order='packageHeader redefinableHeader conformanceClass script externalPackages typeDeclarations participants applications dataFields partnerLinkTypes pools messageFlows associations artifacts processes extendedAttributes otherElements'"
 * @generated
 */
public interface Package extends NamedElement, ExtendedAttributesContainer,
        ApplicationsContainer, ParticipantsContainer, DataFieldsContainer,
        OtherElementsContainer, OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Package Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package Header</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package Header</em>' containment reference.
     * @see #setPackageHeader(PackageHeader)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_PackageHeader()
     * @model containment="true" required="true"
     *        extendedMetaData="kind='element' name='PackageHeader' namespace='##targetNamespace'"
     * @generated
     */
    PackageHeader getPackageHeader();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Package#getPackageHeader <em>Package Header</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package Header</em>' containment reference.
     * @see #getPackageHeader()
     * @generated
     */
    void setPackageHeader(PackageHeader value);

    /**
     * Returns the value of the '<em><b>Redefinable Header</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Redefinable Header</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Redefinable Header</em>' containment reference.
     * @see #setRedefinableHeader(RedefinableHeader)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_RedefinableHeader()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='RedefinableHeader' namespace='##targetNamespace'"
     * @generated
     */
    RedefinableHeader getRedefinableHeader();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Package#getRedefinableHeader <em>Redefinable Header</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Redefinable Header</em>' containment reference.
     * @see #getRedefinableHeader()
     * @generated
     */
    void setRedefinableHeader(RedefinableHeader value);

    /**
     * Returns the value of the '<em><b>Conformance Class</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Conformance Class</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Conformance Class</em>' containment reference.
     * @see #setConformanceClass(ConformanceClass)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_ConformanceClass()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ConformanceClass' namespace='##targetNamespace'"
     * @generated
     */
    ConformanceClass getConformanceClass();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Package#getConformanceClass <em>Conformance Class</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Conformance Class</em>' containment reference.
     * @see #getConformanceClass()
     * @generated
     */
    void setConformanceClass(ConformanceClass value);

    /**
     * Returns the value of the '<em><b>Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Script</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Script</em>' containment reference.
     * @see #setScript(Script)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_Script()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Script' namespace='##targetNamespace'"
     * @generated
     */
    Script getScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Package#getScript <em>Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script</em>' containment reference.
     * @see #getScript()
     * @generated
     */
    void setScript(Script value);

    /**
     * Returns the value of the '<em><b>External Packages</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.ExternalPackage}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>External Packages</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>External Packages</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_ExternalPackages()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ExternalPackage' namespace='##targetNamespace' wrap='ExternalPackages'"
     * @generated
     */
    EList<ExternalPackage> getExternalPackages();

    /**
     * Returns the value of the '<em><b>Type Declarations</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.TypeDeclaration}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type Declarations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type Declarations</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_TypeDeclarations()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='TypeDeclaration' namespace='##targetNamespace' wrap='TypeDeclarations'"
     * @generated
     */
    EList<TypeDeclaration> getTypeDeclarations();

    /**
     * Returns the value of the '<em><b>Partner Link Types</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.PartnerLinkType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Partner Link Types</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Partner Link Types</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_PartnerLinkTypes()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='PartnerLinkType' namespace='##targetNamespace' wrap='PartnerLinkTypes'"
     * @generated
     */
    EList<PartnerLinkType> getPartnerLinkTypes();

    /**
     * Returns the value of the '<em><b>Pools</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Pool}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Pool#getParentPackage <em>Parent Package</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Pools</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Pools</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_Pools()
     * @see com.tibco.xpd.xpdl2.Pool#getParentPackage
     * @model opposite="parentPackage" containment="true"
     *        extendedMetaData="kind='element' name='Pool' namespace='##targetNamespace' wrap='Pools'"
     * @generated
     */
    EList<Pool> getPools();

    /**
     * Returns the value of the '<em><b>Message Flows</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.MessageFlow}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.MessageFlow#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message Flows</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Message Flows</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_MessageFlows()
     * @see com.tibco.xpd.xpdl2.MessageFlow#getPackage
     * @model opposite="package" containment="true"
     *        extendedMetaData="kind='element' name='MessageFlow' namespace='##targetNamespace' wrap='MessageFlows'"
     * @generated
     */
    EList<MessageFlow> getMessageFlows();

    /**
     * Returns the value of the '<em><b>Associations</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Association}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Association#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Associations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Associations</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_Associations()
     * @see com.tibco.xpd.xpdl2.Association#getPackage
     * @model opposite="package" containment="true"
     *        extendedMetaData="kind='element' name='Association' namespace='##targetNamespace' wrap='Associations'"
     * @generated
     */
    EList<Association> getAssociations();

    /**
     * Returns the value of the '<em><b>Artifacts</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Artifact}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Artifact#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Artifacts</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Artifacts</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_Artifacts()
     * @see com.tibco.xpd.xpdl2.Artifact#getPackage
     * @model opposite="package" containment="true"
     *        extendedMetaData="kind='element' name='Artifact' namespace='##targetNamespace' wrap='Artifacts'"
     * @generated
     */
    EList<Artifact> getArtifacts();

    /**
     * Returns the value of the '<em><b>Processes</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Process}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Process#getPackage <em>Package</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Processes</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Processes</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_Processes()
     * @see com.tibco.xpd.xpdl2.Process#getPackage
     * @model opposite="package" containment="true"
     *        extendedMetaData="kind='element' name='WorkflowProcess' namespace='##targetNamespace' wrap='WorkflowProcesses'"
     * @generated
     */
    EList<com.tibco.xpd.xpdl2.Process> getProcesses();

    /**
     * Returns the value of the '<em><b>Language</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Language</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Language</em>' attribute.
     * @see #setLanguage(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_Language()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Language'"
     * @generated
     */
    String getLanguage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Package#getLanguage <em>Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Language</em>' attribute.
     * @see #getLanguage()
     * @generated
     */
    void setLanguage(String value);

    /**
     * Returns the value of the '<em><b>Query Language</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Query Language</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Query Language</em>' attribute.
     * @see #setQueryLanguage(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getPackage_QueryLanguage()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='QueryLanguage'"
     * @generated
     */
    String getQueryLanguage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Package#getQueryLanguage <em>Query Language</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Query Language</em>' attribute.
     * @see #getQueryLanguage()
     * @generated
     */
    void setQueryLanguage(String value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    com.tibco.xpd.xpdl2.Process getProcess(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    ExternalPackage getExternalPackage(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    TypeDeclaration getTypeDeclaration(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    PartnerLinkType getPartnerLinkType(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    Pool getPool(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    MessageFlow getMessageFlow(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    Association getAssociation(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    Artifact getArtifact(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EList<MessageFlow> getMessageFlowFrom(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    EList<MessageFlow> getMessageFlowTo(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    NamedElement findNamedElement(String id);

} // Package
