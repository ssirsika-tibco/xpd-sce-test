/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.wp.archive.service;

import com.tibco.n2.common.channeltype.ImplementationType;
import com.tibco.n2.common.channeltype.PresentationType;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Channel Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getTargetChannelType <em>Target Channel Type</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getPresentationChannelType <em>Presentation Channel Type</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getImplementationType <em>Implementation Type</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getWorkType <em>Work Type</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getExtendedProperties <em>Extended Properties</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getExtensionConfig <em>Extension Config</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getBusinessService <em>Business Service</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getPageFlow <em>Page Flow</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getChannelId <em>Channel Id</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#isDefaultChannel <em>Default Channel</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.n2.wp.archive.service.ChannelType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType()
 * @model extendedMetaData="name='channel_._type' kind='elementOnly'"
 * @generated
 */
public interface ChannelType extends EObject {
    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 													Description of the
     * 													channel that is
     * 													getting deployed
     * 												
     * <!-- end-model-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #isSetDescription()
     * @see #unsetDescription()
     * @see #setDescription(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_Description()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #isSetDescription()
     * @see #unsetDescription()
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDescription()
     * @see #getDescription()
     * @see #setDescription(String)
     * @generated
     */
    void unsetDescription();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getDescription <em>Description</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Description</em>' attribute is set.
     * @see #unsetDescription()
     * @see #getDescription()
     * @see #setDescription(String)
     * @generated
     */
    boolean isSetDescription();

    /**
     * Returns the value of the '<em><b>Target Channel Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.channeltype.ChannelType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 													Target channel type
     * 													indicates the client
     * 													type of channel for
     * 													viewing the
     * 													presentation.EMAIL
     * 													channel is a channel
     * 													type which might be
     * 													capable of
     * 													displaying
     * 													JSP/Forms/MS-Word
     * 													type of
     * 													presentations
     * 												
     * <!-- end-model-doc -->
     * @return the value of the '<em>Target Channel Type</em>' attribute.
     * @see com.tibco.n2.common.channeltype.ChannelType
     * @see #isSetTargetChannelType()
     * @see #unsetTargetChannelType()
     * @see #setTargetChannelType(com.tibco.n2.common.channeltype.ChannelType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_TargetChannelType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='target-channel-type' namespace='##targetNamespace'"
     * @generated
     */
    com.tibco.n2.common.channeltype.ChannelType getTargetChannelType();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getTargetChannelType <em>Target Channel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target Channel Type</em>' attribute.
     * @see com.tibco.n2.common.channeltype.ChannelType
     * @see #isSetTargetChannelType()
     * @see #unsetTargetChannelType()
     * @see #getTargetChannelType()
     * @generated
     */
    void setTargetChannelType(com.tibco.n2.common.channeltype.ChannelType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getTargetChannelType <em>Target Channel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetTargetChannelType()
     * @see #getTargetChannelType()
     * @see #setTargetChannelType(com.tibco.n2.common.channeltype.ChannelType)
     * @generated
     */
    void unsetTargetChannelType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getTargetChannelType <em>Target Channel Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Target Channel Type</em>' attribute is set.
     * @see #unsetTargetChannelType()
     * @see #getTargetChannelType()
     * @see #setTargetChannelType(com.tibco.n2.common.channeltype.ChannelType)
     * @generated
     */
    boolean isSetTargetChannelType();

    /**
     * Returns the value of the '<em><b>Presentation Channel Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.channeltype.PresentationType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 													presentation type
     * 													specifies the
     * 													rendering engine
     * 													type for the
     * 													presentation.It can
     * 													be something like
     * 													JSP,GI,RCP..etc
     * 												
     * <!-- end-model-doc -->
     * @return the value of the '<em>Presentation Channel Type</em>' attribute.
     * @see com.tibco.n2.common.channeltype.PresentationType
     * @see #isSetPresentationChannelType()
     * @see #unsetPresentationChannelType()
     * @see #setPresentationChannelType(PresentationType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_PresentationChannelType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='presentation-channel-type' namespace='##targetNamespace'"
     * @generated
     */
    PresentationType getPresentationChannelType();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getPresentationChannelType <em>Presentation Channel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Presentation Channel Type</em>' attribute.
     * @see com.tibco.n2.common.channeltype.PresentationType
     * @see #isSetPresentationChannelType()
     * @see #unsetPresentationChannelType()
     * @see #getPresentationChannelType()
     * @generated
     */
    void setPresentationChannelType(PresentationType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getPresentationChannelType <em>Presentation Channel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetPresentationChannelType()
     * @see #getPresentationChannelType()
     * @see #setPresentationChannelType(PresentationType)
     * @generated
     */
    void unsetPresentationChannelType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getPresentationChannelType <em>Presentation Channel Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Presentation Channel Type</em>' attribute is set.
     * @see #unsetPresentationChannelType()
     * @see #getPresentationChannelType()
     * @see #setPresentationChannelType(PresentationType)
     * @generated
     */
    boolean isSetPresentationChannelType();

    /**
     * Returns the value of the '<em><b>Implementation Type</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.n2.common.channeltype.ImplementationType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 													Implementation type
     * 													specifies the
     * 													fulfillment type of
     * 													the presentation
     * 													.This fulfillment
     * 													type can be a server
     * 													push/pull
     * 												
     * <!-- end-model-doc -->
     * @return the value of the '<em>Implementation Type</em>' attribute.
     * @see com.tibco.n2.common.channeltype.ImplementationType
     * @see #isSetImplementationType()
     * @see #unsetImplementationType()
     * @see #setImplementationType(ImplementationType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_ImplementationType()
     * @model unsettable="true" required="true"
     *        extendedMetaData="kind='element' name='implementation-type' namespace='##targetNamespace'"
     * @generated
     */
    ImplementationType getImplementationType();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getImplementationType <em>Implementation Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Implementation Type</em>' attribute.
     * @see com.tibco.n2.common.channeltype.ImplementationType
     * @see #isSetImplementationType()
     * @see #unsetImplementationType()
     * @see #getImplementationType()
     * @generated
     */
    void setImplementationType(ImplementationType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getImplementationType <em>Implementation Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetImplementationType()
     * @see #getImplementationType()
     * @see #setImplementationType(ImplementationType)
     * @generated
     */
    void unsetImplementationType();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getImplementationType <em>Implementation Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Implementation Type</em>' attribute is set.
     * @see #unsetImplementationType()
     * @see #getImplementationType()
     * @see #setImplementationType(ImplementationType)
     * @generated
     */
    boolean isSetImplementationType();

    /**
     * Returns the value of the '<em><b>Work Type</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.wp.archive.service.WorkTypeType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 													WorkModel type is
     * 													the work type/work
     * 													model type defined
     * 													in BRM.It is assumed
     * 													that in runtime that
     * 													work types/ work
     * 													model types are
     * 													already deployed and
     * 													activated before the
     * 													work presentation
     * 													request is made.Same
     * 													work model type can
     * 													be associated to
     * 													different
     * 													presentation forms
     * 													based on the channel
     * 													specified
     * 												
     * <!-- end-model-doc -->
     * @return the value of the '<em>Work Type</em>' containment reference list.
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_WorkType()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='work-type' namespace='##targetNamespace'"
     * @generated
     */
    EList<WorkTypeType> getWorkType();

    /**
     * Returns the value of the '<em><b>Domain</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Domain</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Domain</em>' attribute.
     * @see #setDomain(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_Domain()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='element' name='domain' namespace='##targetNamespace'"
     * @generated
     */
    String getDomain();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getDomain <em>Domain</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Domain</em>' attribute.
     * @see #getDomain()
     * @generated
     */
    void setDomain(String value);

    /**
     * Returns the value of the '<em><b>Extended Properties</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 													It is a provision to
     * 													add custom
     * 													properties to the
     * 													work model type
     * 													which can be
     * 													interpreted by the
     * 													Work Presenation
     * 													Deployment Analyser
     * 												
     * <!-- end-model-doc -->
     * @return the value of the '<em>Extended Properties</em>' containment reference.
     * @see #isSetExtendedProperties()
     * @see #unsetExtendedProperties()
     * @see #setExtendedProperties(ExtendedPropertiesType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_ExtendedProperties()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='extended-properties' namespace='##targetNamespace'"
     * @generated
     */
    ExtendedPropertiesType getExtendedProperties();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getExtendedProperties <em>Extended Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Extended Properties</em>' containment reference.
     * @see #isSetExtendedProperties()
     * @see #unsetExtendedProperties()
     * @see #getExtendedProperties()
     * @generated
     */
    void setExtendedProperties(ExtendedPropertiesType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getExtendedProperties <em>Extended Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExtendedProperties()
     * @see #getExtendedProperties()
     * @see #setExtendedProperties(ExtendedPropertiesType)
     * @generated
     */
    void unsetExtendedProperties();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getExtendedProperties <em>Extended Properties</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Extended Properties</em>' containment reference is set.
     * @see #unsetExtendedProperties()
     * @see #getExtendedProperties()
     * @see #setExtendedProperties(ExtendedPropertiesType)
     * @generated
     */
    boolean isSetExtendedProperties();

    /**
     * Returns the value of the '<em><b>Extension Config</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 													Defines extended
     * 													channel information,
     * 													for example, Email
     * 													Channel, or for a
     * 													channel not yet
     * 													implemented in WP.
     * 												
     * <!-- end-model-doc -->
     * @return the value of the '<em>Extension Config</em>' containment reference.
     * @see #isSetExtensionConfig()
     * @see #unsetExtensionConfig()
     * @see #setExtensionConfig(ChannelExtentionType)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_ExtensionConfig()
     * @model containment="true" unsettable="true"
     *        extendedMetaData="kind='element' name='extension-config' namespace='##targetNamespace'"
     * @generated
     */
    ChannelExtentionType getExtensionConfig();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getExtensionConfig <em>Extension Config</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Extension Config</em>' containment reference.
     * @see #isSetExtensionConfig()
     * @see #unsetExtensionConfig()
     * @see #getExtensionConfig()
     * @generated
     */
    void setExtensionConfig(ChannelExtentionType value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getExtensionConfig <em>Extension Config</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetExtensionConfig()
     * @see #getExtensionConfig()
     * @see #setExtensionConfig(ChannelExtentionType)
     * @generated
     */
    void unsetExtensionConfig();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getExtensionConfig <em>Extension Config</em>}' containment reference is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Extension Config</em>' containment reference is set.
     * @see #unsetExtensionConfig()
     * @see #getExtensionConfig()
     * @see #setExtensionConfig(ChannelExtentionType)
     * @generated
     */
    boolean isSetExtensionConfig();

    /**
     * Returns the value of the '<em><b>Business Service</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.wp.archive.service.BusinessServiceType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * 
     * 													Defines page flow
     * 													business service
     * 													form related
     * 													resources.
     * 												
     * <!-- end-model-doc -->
     * @return the value of the '<em>Business Service</em>' containment reference list.
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_BusinessService()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='business-service' namespace='##targetNamespace'"
     * @generated
     */
    EList<BusinessServiceType> getBusinessService();

    /**
     * Returns the value of the '<em><b>Page Flow</b></em>' containment reference list.
     * The list contents are of type {@link com.tibco.n2.wp.archive.service.PageFlowType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Page Flow</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Page Flow</em>' containment reference list.
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_PageFlow()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='page-flow' namespace='##targetNamespace'"
     * @generated
     */
    EList<PageFlowType> getPageFlow();

    /**
     * Returns the value of the '<em><b>Channel Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Channel Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Channel Id</em>' attribute.
     * @see #setChannelId(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_ChannelId()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
     *        extendedMetaData="kind='attribute' name='channelId'"
     * @generated
     */
    String getChannelId();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getChannelId <em>Channel Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Channel Id</em>' attribute.
     * @see #getChannelId()
     * @generated
     */
    void setChannelId(String value);

    /**
     * Returns the value of the '<em><b>Default Channel</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Is this a default channel for the target channel type. Note there should be only one default channel per channel type.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Default Channel</em>' attribute.
     * @see #isSetDefaultChannel()
     * @see #unsetDefaultChannel()
     * @see #setDefaultChannel(boolean)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_DefaultChannel()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='defaultChannel'"
     * @generated
     */
    boolean isDefaultChannel();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#isDefaultChannel <em>Default Channel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Default Channel</em>' attribute.
     * @see #isSetDefaultChannel()
     * @see #unsetDefaultChannel()
     * @see #isDefaultChannel()
     * @generated
     */
    void setDefaultChannel(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#isDefaultChannel <em>Default Channel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetDefaultChannel()
     * @see #isDefaultChannel()
     * @see #setDefaultChannel(boolean)
     * @generated
     */
    void unsetDefaultChannel();

    /**
     * Returns whether the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#isDefaultChannel <em>Default Channel</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Default Channel</em>' attribute is set.
     * @see #unsetDefaultChannel()
     * @see #isDefaultChannel()
     * @see #setDefaultChannel(boolean)
     * @generated
     */
    boolean isSetDefaultChannel();

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_Name()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='name'"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Version</em>' attribute.
     * @see #setVersion(String)
     * @see com.tibco.n2.wp.archive.service.WPPackage#getChannelType_Version()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='version'"
     * @generated
     */
    String getVersion();

    /**
     * Sets the value of the '{@link com.tibco.n2.wp.archive.service.ChannelType#getVersion <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Version</em>' attribute.
     * @see #getVersion()
     * @generated
     */
    void setVersion(String value);

} // ChannelType
