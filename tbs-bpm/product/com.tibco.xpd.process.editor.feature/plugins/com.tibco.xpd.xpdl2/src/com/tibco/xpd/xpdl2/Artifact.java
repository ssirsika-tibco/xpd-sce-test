/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.Artifact#getObject <em>Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Artifact#getDataObject <em>Data Object</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Artifact#getArtifactType <em>Artifact Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Artifact#getTextAnnotation <em>Text Annotation</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Artifact#getPackage <em>Package</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.Artifact#getGroup <em>Group</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifact()
 * @model extendedMetaData="name='Artifact_._type' kind='elementOnly' features-order='object dataObject nodeGraphicsInfos'"
 * @generated
 */
public interface Artifact extends NamedElement, GraphicalNode {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Object</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Object</em>' containment reference.
     * @see #setObject(com.tibco.xpd.xpdl2.Object)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifact_Object()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Object' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.xpd.xpdl2.Object getObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Artifact#getObject <em>Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Object</em>' containment reference.
     * @see #getObject()
     * @generated
     */
    void setObject(com.tibco.xpd.xpdl2.Object value);

    /**
     * Returns the value of the '<em><b>Data Object</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Object</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Object</em>' containment reference.
     * @see #setDataObject(DataObject)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifact_DataObject()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataObject' namespace='##targetNamespace'"
     * @generated
     */
    DataObject getDataObject();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Artifact#getDataObject <em>Data Object</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Data Object</em>' containment reference.
     * @see #getDataObject()
     * @generated
     */
    void setDataObject(DataObject value);

    /**
     * Returns the value of the '<em><b>Artifact Type</b></em>' attribute.
     * The default value is <code>"DataObject"</code>.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.ArtifactType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Artifact Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Artifact Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ArtifactType
     * @see #isSetArtifactType()
     * @see #unsetArtifactType()
     * @see #setArtifactType(ArtifactType)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifact_ArtifactType()
     * @model default="DataObject" unique="false" unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='ArtifactType'"
     * @generated
     */
    ArtifactType getArtifactType();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Artifact#getArtifactType <em>Artifact Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Artifact Type</em>' attribute.
     * @see com.tibco.xpd.xpdl2.ArtifactType
     * @see #isSetArtifactType()
     * @see #unsetArtifactType()
     * @see #getArtifactType()
     * @generated
     */
    void setArtifactType(ArtifactType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.Artifact#getArtifactType <em>Artifact Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetArtifactType()
     * @see #getArtifactType()
     * @see #setArtifactType(ArtifactType)
     * @generated
     */
    void unsetArtifactType();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.Artifact#getArtifactType <em>Artifact Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Artifact Type</em>' attribute is set.
     * @see #unsetArtifactType()
     * @see #getArtifactType()
     * @see #setArtifactType(ArtifactType)
     * @generated
     */
    boolean isSetArtifactType();

    /**
     * Returns the value of the '<em><b>Group</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' containment reference.
     * @see #setGroup(Group)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifact_Group()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Group' namespace='##targetNamespace'"
     * @generated
     */
    Group getGroup();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Artifact#getGroup <em>Group</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Group</em>' containment reference.
     * @see #getGroup()
     * @generated
     */
    void setGroup(Group value);

    /**
     * Returns the value of the '<em><b>Text Annotation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Text Annotation</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Text Annotation</em>' attribute.
     * @see #setTextAnnotation(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifact_TextAnnotation()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='TextAnnotation'"
     * @generated
     */
    String getTextAnnotation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Artifact#getTextAnnotation <em>Text Annotation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Text Annotation</em>' attribute.
     * @see #getTextAnnotation()
     * @generated
     */
    void setTextAnnotation(String value);

    /**
     * Returns the value of the '<em><b>Package</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.xpdl2.Package#getArtifacts <em>Artifacts</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Package</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Package</em>' container reference.
     * @see #setPackage(com.tibco.xpd.xpdl2.Package)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getArtifact_Package()
     * @see com.tibco.xpd.xpdl2.Package#getArtifacts
     * @model opposite="artifacts" transient="false"
     * @generated
     */
    com.tibco.xpd.xpdl2.Package getPackage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.Artifact#getPackage <em>Package</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Package</em>' container reference.
     * @see #getPackage()
     * @generated
     */
    void setPackage(com.tibco.xpd.xpdl2.Package value);

} // Artifact
