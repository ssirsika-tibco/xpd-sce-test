/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import com.tibco.n2.common.organisation.api.XmlParticipantId;
import com.tibco.n2.common.organisation.api.XmlResourceQuery;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Definition of a work item that is to be scheduled, providing basic, participant and work type information about the work item.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.Item#getEntities <em>Entities</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.Item#getEntityQuery <em>Entity Query</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.Item#getWorkTypeUID <em>Work Type UID</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.Item#getWorkTypeVersion <em>Work Type Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getItem()
 * @model extendedMetaData="name='Item' kind='elementOnly'"
 * @generated
 */
public interface Item extends BaseItemInfo {
    /**
     * Returns the value of the '<em><b>Entities</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.common.organisation.api.XmlParticipantId}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Details of the organization model entities for whom the work item is to be scheduled.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entities</em>' containment reference list.
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItem_Entities()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='entities'"
     * @generated
     */
    EList<XmlParticipantId> getEntities();

    /**
     * Returns the value of the '<em><b>Entity Query</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Specification of a resource query.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Entity Query</em>' containment reference.
     * @see #setEntityQuery(XmlResourceQuery)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItem_EntityQuery()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='entityQuery'"
     * @generated
     */
    XmlResourceQuery getEntityQuery();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.Item#getEntityQuery <em>Entity Query</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Entity Query</em>' containment reference.
     * @see #getEntityQuery()
     * @generated
     */
    void setEntityQuery(XmlResourceQuery value);

    /**
     * Returns the value of the '<em><b>Work Type UID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * ID of the work type that defines the data specification for this work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Type UID</em>' attribute.
     * @see #setWorkTypeUID(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItem_WorkTypeUID()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='workTypeUID'"
     * @generated
     */
    String getWorkTypeUID();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.Item#getWorkTypeUID <em>Work Type UID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type UID</em>' attribute.
     * @see #getWorkTypeUID()
     * @generated
     */
    void setWorkTypeUID(String value);

    /**
     * Returns the value of the '<em><b>Work Type Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * OSGi version range string of the work type that is to be used.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Type Version</em>' attribute.
     * @see #setWorkTypeVersion(String)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getItem_WorkTypeVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='workTypeVersion'"
     * @generated
     */
    String getWorkTypeVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.Item#getWorkTypeVersion <em>Work Type Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Work Type Version</em>' attribute.
     * @see #getWorkTypeVersion()
     * @generated
     */
    void setWorkTypeVersion(String value);

} // Item
