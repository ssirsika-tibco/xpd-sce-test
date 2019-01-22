/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.brm.api;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Work Item Priority Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemPriorityType#getAbsPriority <em>Abs Priority</em>}</li>
 *   <li>{@link com.tibco.n2.brm.api.WorkItemPriorityType#getOffsetPriority <em>Offset Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemPriorityType()
 * @model extendedMetaData="name='workItemPriority_._type' kind='elementOnly'"
 * @generated
 */
public interface WorkItemPriorityType extends EObject {
    /**
     * Returns the value of the '<em><b>Abs Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Abs Priority</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Abs Priority</em>' attribute.
     * @see #isSetAbsPriority()
     * @see #unsetAbsPriority()
     * @see #setAbsPriority(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemPriorityType_AbsPriority()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='element' name='absPriority'"
     * @generated
     */
    int getAbsPriority();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemPriorityType#getAbsPriority <em>Abs Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Abs Priority</em>' attribute.
     * @see #isSetAbsPriority()
     * @see #unsetAbsPriority()
     * @see #getAbsPriority()
     * @generated
     */
    void setAbsPriority(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkItemPriorityType#getAbsPriority <em>Abs Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAbsPriority()
     * @see #getAbsPriority()
     * @see #setAbsPriority(int)
     * @generated
     */
    void unsetAbsPriority();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkItemPriorityType#getAbsPriority <em>Abs Priority</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Abs Priority</em>' attribute is set.
     * @see #unsetAbsPriority()
     * @see #getAbsPriority()
     * @see #setAbsPriority(int)
     * @generated
     */
    boolean isSetAbsPriority();

    /**
     * Returns the value of the '<em><b>Offset Priority</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Offset Priority</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Offset Priority</em>' attribute.
     * @see #isSetOffsetPriority()
     * @see #unsetOffsetPriority()
     * @see #setOffsetPriority(int)
     * @see com.tibco.n2.brm.api.N2BRMPackage#getWorkItemPriorityType_OffsetPriority()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='element' name='offsetPriority'"
     * @generated
     */
    int getOffsetPriority();

    /**
     * Sets the value of the '{@link com.tibco.n2.brm.api.WorkItemPriorityType#getOffsetPriority <em>Offset Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Offset Priority</em>' attribute.
     * @see #isSetOffsetPriority()
     * @see #unsetOffsetPriority()
     * @see #getOffsetPriority()
     * @generated
     */
    void setOffsetPriority(int value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.brm.api.WorkItemPriorityType#getOffsetPriority <em>Offset Priority</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOffsetPriority()
     * @see #getOffsetPriority()
     * @see #setOffsetPriority(int)
     * @generated
     */
    void unsetOffsetPriority();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.brm.api.WorkItemPriorityType#getOffsetPriority <em>Offset Priority</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Offset Priority</em>' attribute is set.
     * @see #unsetOffsetPriority()
     * @see #getOffsetPriority()
     * @see #setOffsetPriority(int)
     * @generated
     */
    boolean isSetOffsetPriority();

} // WorkItemPriorityType
