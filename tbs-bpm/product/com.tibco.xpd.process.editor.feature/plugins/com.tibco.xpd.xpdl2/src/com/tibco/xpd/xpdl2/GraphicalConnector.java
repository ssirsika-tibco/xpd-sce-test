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
 * A representation of the model object '<em><b>Graphical Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.GraphicalConnector#getConnectorGraphicsInfos <em>Connector Graphics Infos</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGraphicalConnector()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GraphicalConnector extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Connector Graphics Infos</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.ConnectorGraphicsInfo}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connector Graphics Infos</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connector Graphics Infos</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGraphicalConnector_ConnectorGraphicsInfos()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ConnectorGraphicsInfo' namespace='##targetNamespace' wrap='ConnectorGraphicsInfos'"
     * @generated
     */
    EList<ConnectorGraphicsInfo> getConnectorGraphicsInfos();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    ConnectorGraphicsInfo getConnectorGraphicsInfoForTool(String id);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<Association> getIncomingAssociations();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model kind="operation"
     * @generated
     */
    EList<Association> getOutgoingAssociations();

} // GraphicalConnector
