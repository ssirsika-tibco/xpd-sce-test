/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Graphics Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getCoordinates <em>Coordinates</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getBorderColor <em>Border Color</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getFillColor <em>Fill Color</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getHeight <em>Height</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#isIsVisible <em>Is Visible</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getLaneId <em>Lane Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getPage <em>Page</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getShape <em>Shape</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getToolId <em>Tool Id</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getWidth <em>Width</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getPageId <em>Page Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo()
 * @model extendedMetaData="name='NodeGraphicsInfo_._type' kind='elementOnly'"
 * @generated
 */
public interface NodeGraphicsInfo extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Coordinates</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Coordinates</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Coordinates</em>' containment reference.
     * @see #setCoordinates(Coordinates)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_Coordinates()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Coordinates' namespace='##targetNamespace'"
     * @generated
     */
    Coordinates getCoordinates();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getCoordinates <em>Coordinates</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Coordinates</em>' containment reference.
     * @see #getCoordinates()
     * @generated
     */
    void setCoordinates(Coordinates value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_BorderColor()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='BorderColor'"
     * @generated
     */
    String getBorderColor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getBorderColor <em>Border Color</em>}' attribute.
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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_FillColor()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='FillColor'"
     * @generated
     */
    String getFillColor();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getFillColor <em>Fill Color</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Fill Color</em>' attribute.
     * @see #getFillColor()
     * @generated
     */
    void setFillColor(String value);

    /**
     * Returns the value of the '<em><b>Height</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Height</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Height</em>' attribute.
     * @see #isSetHeight()
     * @see #unsetHeight()
     * @see #setHeight(double)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_Height()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
     *        extendedMetaData="kind='attribute' name='Height'"
     * @generated
     */
    double getHeight();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getHeight <em>Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Height</em>' attribute.
     * @see #isSetHeight()
     * @see #unsetHeight()
     * @see #getHeight()
     * @generated
     */
    void setHeight(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getHeight <em>Height</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetHeight()
     * @see #getHeight()
     * @see #setHeight(double)
     * @generated
     */
    void unsetHeight();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getHeight <em>Height</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Height</em>' attribute is set.
     * @see #unsetHeight()
     * @see #getHeight()
     * @see #setHeight(double)
     * @generated
     */
    boolean isSetHeight();

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_IsVisible()
     * @model default="true" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='IsVisible'"
     * @generated
     */
    boolean isIsVisible();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#isIsVisible <em>Is Visible</em>}' attribute.
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
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#isIsVisible <em>Is Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsVisible()
     * @see #isIsVisible()
     * @see #setIsVisible(boolean)
     * @generated
     */
    void unsetIsVisible();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#isIsVisible <em>Is Visible</em>}' attribute is set.
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
     * Returns the value of the '<em><b>Lane Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lane Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lane Id</em>' attribute.
     * @see #setLaneId(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_LaneId()
     * @model unique="false" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='LaneId'"
     * @generated
     */
    String getLaneId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getLaneId <em>Lane Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lane Id</em>' attribute.
     * @see #getLaneId()
     * @generated
     */
    void setLaneId(String value);

    /**
     * Returns the value of the '<em><b>Page</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Page</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Page</em>' attribute.
     * @see #setPage(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_Page()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.NMTOKEN"
     *        extendedMetaData="kind='attribute' name='Page'"
     * @generated
     */
    String getPage();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getPage <em>Page</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Page</em>' attribute.
     * @see #getPage()
     * @generated
     */
    void setPage(String value);

    /**
     * Returns the value of the '<em><b>Shape</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Shape</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Shape</em>' attribute.
     * @see #setShape(String)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_Shape()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Shape'"
     * @generated
     */
    String getShape();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getShape <em>Shape</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Shape</em>' attribute.
     * @see #getShape()
     * @generated
     */
    void setShape(String value);

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_ToolId()
     * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='ToolId'"
     * @generated
     */
    String getToolId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getToolId <em>Tool Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Tool Id</em>' attribute.
     * @see #getToolId()
     * @generated
     */
    void setToolId(String value);

    /**
     * Returns the value of the '<em><b>Width</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Width</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Width</em>' attribute.
     * @see #isSetWidth()
     * @see #unsetWidth()
     * @see #setWidth(double)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_Width()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
     *        extendedMetaData="kind='attribute' name='Width'"
     * @generated
     */
    double getWidth();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getWidth <em>Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Width</em>' attribute.
     * @see #isSetWidth()
     * @see #unsetWidth()
     * @see #getWidth()
     * @generated
     */
    void setWidth(double value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getWidth <em>Width</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetWidth()
     * @see #getWidth()
     * @see #setWidth(double)
     * @generated
     */
    void unsetWidth();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getWidth <em>Width</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Width</em>' attribute is set.
     * @see #unsetWidth()
     * @see #getWidth()
     * @see #setWidth(double)
     * @generated
     */
    boolean isSetWidth();

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
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getNodeGraphicsInfo_PageId()
     * @model dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='PageId'"
     * @generated
     */
    String getPageId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.NodeGraphicsInfo#getPageId <em>Page Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Page Id</em>' attribute.
     * @see #getPageId()
     * @generated
     */
    void setPageId(String value);

} // NodeGraphicsInfo
