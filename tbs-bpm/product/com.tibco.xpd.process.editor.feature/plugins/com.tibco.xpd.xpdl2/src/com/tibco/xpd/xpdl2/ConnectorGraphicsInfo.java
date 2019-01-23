/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connector Graphics Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getCoordinates <em>Coordinates</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getBorderColor <em>Border Color</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getFillColor <em>Fill Color</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#isIsVisible <em>Is Visible</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getPageId <em>Page Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getStyle <em>Style</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getToolId <em>Tool Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConnectorGraphicsInfo()
 * @model extendedMetaData="name='ConnectorGraphicsInfo_._type' kind='elementOnly'"
 * @generated
 */
public interface ConnectorGraphicsInfo extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Coordinates</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.Coordinates}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Coordinates</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Coordinates</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConnectorGraphicsInfo_Coordinates()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Coordinates' namespace='##targetNamespace'"
     * @generated
     */
    EList<Coordinates> getCoordinates();

    /**
     * Returns the value of the '<em><b>Border Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Border Color</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Border Color</em>' attribute.
     * @see #setBorderColor(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConnectorGraphicsInfo_BorderColor()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='BorderColor'"
     * @generated
     */
    String getBorderColor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getBorderColor <em>Border Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Border Color</em>' attribute.
     * @see #getBorderColor()
     * @generated
     */
    void setBorderColor(String value);

    /**
     * Returns the value of the '<em><b>Fill Color</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Fill Color</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Fill Color</em>' attribute.
     * @see #setFillColor(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConnectorGraphicsInfo_FillColor()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='FillColor'"
     * @generated
     */
    String getFillColor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getFillColor <em>Fill Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Fill Color</em>' attribute.
     * @see #getFillColor()
     * @generated
     */
    void setFillColor(String value);

    /**
     * Returns the value of the '<em><b>Is Visible</b></em>' attribute.
     * The default value is <code>"true"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Visible</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is Visible</em>' attribute.
     * @see #isSetIsVisible()
     * @see #unsetIsVisible()
     * @see #setIsVisible(boolean)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConnectorGraphicsInfo_IsVisible()
     * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='IsVisible'"
     * @generated
     */
    boolean isIsVisible();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#isIsVisible <em>Is Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Visible</em>' attribute.
     * @see #isSetIsVisible()
     * @see #unsetIsVisible()
     * @see #isIsVisible()
     * @generated
     */
    void setIsVisible(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#isIsVisible <em>Is Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsVisible()
     * @see #isIsVisible()
     * @see #setIsVisible(boolean)
     * @generated
     */
    void unsetIsVisible();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#isIsVisible <em>Is Visible</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is Visible</em>' attribute is set.
     * @see #unsetIsVisible()
     * @see #isIsVisible()
     * @see #setIsVisible(boolean)
     * @generated
     */
    boolean isSetIsVisible();

    /**
     * Returns the value of the '<em><b>Page Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Page Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Page Id</em>' attribute.
     * @see #setPageId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConnectorGraphicsInfo_PageId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='PageId'"
     * @generated
     */
    String getPageId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getPageId <em>Page Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Page Id</em>' attribute.
     * @see #getPageId()
     * @generated
     */
    void setPageId(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Style</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Style</em>' attribute.
     * @see #setStyle(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConnectorGraphicsInfo_Style()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Style'"
     * @generated
     */
    String getStyle();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getStyle <em>Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Style</em>' attribute.
     * @see #getStyle()
     * @generated
     */
    void setStyle(String value);

    /**
     * Returns the value of the '<em><b>Tool Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Tool Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Tool Id</em>' attribute.
     * @see #setToolId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getConnectorGraphicsInfo_ToolId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
     *        extendedMetaData="kind='attribute' name='ToolId'"
     * @generated
     */
    String getToolId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo#getToolId <em>Tool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Tool Id</em>' attribute.
     * @see #getToolId()
     * @generated
     */
    void setToolId(String value);

} // ConnectorGraphicsInfo
