/**
 * Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved.
 */
package com.tibco.xpd.xpdExtension;

import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Script Data Mapper</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Container for data mappings element to store mappings drawn using the script data mapper.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMapperContext <em>Mapper Context</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMappingDirection <em>Mapping Direction</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getDataMappings <em>Data Mappings</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getUnmappedScripts <em>Unmapped Scripts</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getArrayInflationType <em>Array Inflation Type</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptDataMapper()
 * @model extendedMetaData="name='ScriptDataMapper' kind='empty'"
 * @generated
 */
public interface ScriptDataMapper
        extends OtherElementsContainer, OtherAttributesContainer {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Mapper Context</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The mapper context applicable to this ScriptDataMapper element 
     * (allows processors to generically, deal with (load pluggable contributions for) a 
     * ScriptDataMapper elemnt from the model element alone.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Mapper Context</em>' attribute.
     * @see #isSetMapperContext()
     * @see #unsetMapperContext()
     * @see #setMapperContext(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptDataMapper_MapperContext()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='MapperContext'"
     * @generated
     */
    String getMapperContext();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMapperContext <em>Mapper Context</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mapper Context</em>' attribute.
     * @see #isSetMapperContext()
     * @see #unsetMapperContext()
     * @see #getMapperContext()
     * @generated
     */
    void setMapperContext(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMapperContext <em>Mapper Context</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMapperContext()
     * @see #getMapperContext()
     * @see #setMapperContext(String)
     * @generated
     */
    void unsetMapperContext();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMapperContext <em>Mapper Context</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Mapper Context</em>' attribute is set.
     * @see #unsetMapperContext()
     * @see #getMapperContext()
     * @see #setMapperContext(String)
     * @generated
     */
    boolean isSetMapperContext();

    /**
     * Returns the value of the '<em><b>Mapping Direction</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdl2.DirectionType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The mapping direction in play for this ScriptDataMapper.
     * The mapping direction often plays a role when it comes to user-defined
     * mapping scripts (and specifically, the contribution of permitted JS Classes etc)
     * <!-- end-model-doc -->
     * @return the value of the '<em>Mapping Direction</em>' attribute.
     * @see com.tibco.xpd.xpdl2.DirectionType
     * @see #isSetMappingDirection()
     * @see #unsetMappingDirection()
     * @see #setMappingDirection(DirectionType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptDataMapper_MappingDirection()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='attribute' name='MappingDirection'"
     * @generated
     */
    DirectionType getMappingDirection();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMappingDirection <em>Mapping Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Mapping Direction</em>' attribute.
     * @see com.tibco.xpd.xpdl2.DirectionType
     * @see #isSetMappingDirection()
     * @see #unsetMappingDirection()
     * @see #getMappingDirection()
     * @generated
     */
    void setMappingDirection(DirectionType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMappingDirection <em>Mapping Direction</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMappingDirection()
     * @see #getMappingDirection()
     * @see #setMappingDirection(DirectionType)
     * @generated
     */
    void unsetMappingDirection();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.ScriptDataMapper#getMappingDirection <em>Mapping Direction</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Mapping Direction</em>' attribute is set.
     * @see #unsetMappingDirection()
     * @see #getMappingDirection()
     * @see #setMappingDirection(DirectionType)
     * @generated
     */
    boolean isSetMappingDirection();

    /**
     * Returns the value of the '<em><b>Data Mappings</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdl2.DataMapping}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Data Mappings</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Data Mappings</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptDataMapper_DataMappings()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataMapping' namespace='##targetNamespace' wrap='DataMappings'"
     * @generated
     */
    EList<DataMapping> getDataMappings();

    /**
     * Returns the value of the '<em><b>Unmapped Scripts</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.ScriptInformation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Unmapped Scripts</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Unmapped Scripts</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptDataMapper_UnmappedScripts()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='ScriptInformation' namespace='##targetNamespace' wrap='UnmappedScripts'"
     * @generated
     */
    EList<ScriptInformation> getUnmappedScripts();

    /**
     * Returns the value of the '<em><b>Array Inflation Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.xpd.xpdExtension.DataMapperArrayInflation}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Array Inflation Type</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Array Inflation Type</em>' containment reference list.
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getScriptDataMapper_ArrayInflationType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='DataMapperArrayInflation' namespace='##targetNamespace' wrap='ArrayInflation'"
     * @generated
     */
    EList<DataMapperArrayInflation> getArrayInflationType();

} // ScriptDataMapper
