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
 * A representation of the model object '<em><b>Graphical Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.GraphicalNode#getNodeGraphicsInfos <em>Node Graphics Infos</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGraphicalNode()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GraphicalNode extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Node Graphics Infos</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.NodeGraphicsInfo}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Node Graphics Infos</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Node Graphics Infos</em>' containment reference list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getGraphicalNode_NodeGraphicsInfos()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='NodeGraphicsInfo' namespace='##targetNamespace' wrap='NodeGraphicsInfos'"
     * @generated
     */
    EList<NodeGraphicsInfo> getNodeGraphicsInfos();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @model idDataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    NodeGraphicsInfo getNodeGraphicsInfoForTool(String id);

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

} // GraphicalNode
