/**
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Calendar Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Calendar reference on Process and Timer Event.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.CalendarReference#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.CalendarReference#getDataFieldId <em>Data Field Id</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCalendarReference()
 * @model extendedMetaData="name='CalendarReference_._type' kind='elementOnly'"
 * @generated
 */
public interface CalendarReference extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Alias</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Alias</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Alias</em>' attribute.
     * @see #isSetAlias()
     * @see #unsetAlias()
     * @see #setAlias(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCalendarReference_Alias()
     * @model unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='Alias'"
     * @generated
     */
    String getAlias();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CalendarReference#getAlias <em>Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Alias</em>' attribute.
     * @see #isSetAlias()
     * @see #unsetAlias()
     * @see #getAlias()
     * @generated
     */
    void setAlias(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CalendarReference#getAlias <em>Alias</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAlias()
     * @see #getAlias()
     * @see #setAlias(String)
     * @generated
     */
    void unsetAlias();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.CalendarReference#getAlias <em>Alias</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Alias</em>' attribute is set.
     * @see #unsetAlias()
     * @see #getAlias()
     * @see #setAlias(String)
     * @generated
     */
    boolean isSetAlias();

    /**
     * Returns the value of the '<em><b>Data Field Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Field Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Field Id</em>' attribute.
     * @see #isSetDataFieldId()
     * @see #unsetDataFieldId()
     * @see #setDataFieldId(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getCalendarReference_DataFieldId()
     * @model unique="false" unsettable="true" dataType="com.tibco.xpd.xpdl2.IdReferenceString"
     *        extendedMetaData="kind='attribute' name='DataFieldId'"
     * @generated
     */
    String getDataFieldId();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.CalendarReference#getDataFieldId <em>Data Field Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Data Field Id</em>' attribute.
     * @see #isSetDataFieldId()
     * @see #unsetDataFieldId()
     * @see #getDataFieldId()
     * @generated
     */
    void setDataFieldId(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.CalendarReference#getDataFieldId <em>Data Field Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDataFieldId()
     * @see #getDataFieldId()
     * @see #setDataFieldId(String)
     * @generated
     */
    void unsetDataFieldId();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.CalendarReference#getDataFieldId <em>Data Field Id</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Data Field Id</em>' attribute is set.
     * @see #unsetDataFieldId()
     * @see #getDataFieldId()
     * @see #setDataFieldId(String)
     * @generated
     */
    boolean isSetDataFieldId();

} // CalendarReference
