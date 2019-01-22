/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Channel Type</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getPresentation <em>Presentation</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getRuntimeVersion <em>Runtime Version</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getDestinations <em>Destinations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelType()
 * @model
 * @generated
 */
public interface ChannelType extends NamedElement {
    /**
     * Returns the value of the '<em><b>Target</b></em>' reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.presentation.channeltypes.Target#getBindings <em>Bindings</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Target</em>' reference.
     * @see #setTarget(Target)
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelType_Target()
     * @see com.tibco.xpd.presentation.channeltypes.Target#getBindings
     * @model opposite="bindings" required="true"
     * @generated
     */
    Target getTarget();

    /**
     * Sets the value of the '
     * {@link com.tibco.xpd.presentation.channeltypes.ChannelType#getTarget
     * <em>Target</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @param value
     *            the new value of the '<em>Target</em>' reference.
     * @see #getTarget()
     * @generated
     */
    void setTarget(Target value);

    /**
     * Returns the value of the '<em><b>Presentation</b></em>' reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.presentation.channeltypes.Presentation#getBindings <em>Bindings</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Presentation</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Presentation</em>' reference.
     * @see #setPresentation(Presentation)
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelType_Presentation()
     * @see com.tibco.xpd.presentation.channeltypes.Presentation#getBindings
     * @model opposite="bindings" required="true"
     * @generated
     */
    Presentation getPresentation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getPresentation <em>Presentation</em>}' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param value the new value of the '<em>Presentation</em>' reference.
     * @see #getPresentation()
     * @generated
     */
    void setPresentation(Presentation value);

    /**
     * Returns the value of the '<em><b>Implementation</b></em>' reference.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.presentation.channeltypes.Implementation#getBindings <em>Bindings</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Implementation</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Implementation</em>' reference.
     * @see #setImplementation(Implementation)
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelType_Implementation()
     * @see com.tibco.xpd.presentation.channeltypes.Implementation#getBindings
     * @model opposite="bindings" required="true"
     * @generated
     */
    Implementation getImplementation();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getImplementation <em>Implementation</em>}' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param value the new value of the '<em>Implementation</em>' reference.
     * @see #getImplementation()
     * @generated
     */
    void setImplementation(Implementation value);

    /**
     * Returns the value of the '<em><b>Attributes</b></em>' containment
     * reference list. The list contents are of type
     * {@link com.tibco.xpd.presentation.channeltypes.Attribute}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Attributes</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Attributes</em>' containment reference
     *         list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelType_Attributes()
     * @model containment="true"
     * @generated
     */
    EList<Attribute> getAttributes();

    /**
     * Returns the value of the '<em><b>Runtime Version</b></em>' attribute.
     * <!-- begin-user-doc --> The version of runtime this channel type is
     * supported. If not provided then is supported in any version. The format
     * is the same as OSGI bundle version. It is can be composed of two parts:
     * minimal and maximal limit. Both limits can be specified as inclusive ('['
     * or ']') or exclusive ('(' or ')'). If only one limit is provided it means
     * the minimal version part inclusive. Version limit part is in form of:
     * [major].[minor].[update].[qaalifier] Major component is mandatory and
     * only qualifier can have non digit characters.
     * 
     * <pre>
     * Example: 
     * 1.2.3.001 - minimal version is 1.2.3.001 inclusive 
     * [1.0, 5.3) - minimal version is 1.0 inclusive, maximal is 5.3 exclusive.
     * </pre>
     * 
     * <!-- end-user-doc -->
     * @return the value of the '<em>Runtime Version</em>' attribute.
     * @see #setRuntimeVersion(String)
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelType_RuntimeVersion()
     * @model
     * @generated
     */
    String getRuntimeVersion();

    /**
     * Sets the value of the '{@link com.tibco.xpd.presentation.channeltypes.ChannelType#getRuntimeVersion <em>Runtime Version</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param value the new value of the '<em>Runtime Version</em>' attribute.
     * @see #getRuntimeVersion()
     * @generated
     */
    void setRuntimeVersion(String value);

    /**
     * Returns the value of the '<em><b>Destinations</b></em>' reference list.
     * The list contents are of type {@link com.tibco.xpd.presentation.channeltypes.ChannelDestination}.
     * It is bidirectional and its opposite is '{@link com.tibco.xpd.presentation.channeltypes.ChannelDestination#getChannelTypes <em>Channel Types</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Destinations</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Destinations</em>' reference list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelType_Destinations()
     * @see com.tibco.xpd.presentation.channeltypes.ChannelDestination#getChannelTypes
     * @model opposite="channelTypes" resolveProxies="false"
     * @generated
     */
    EList<ChannelDestination> getDestinations();



} // ChannelType
