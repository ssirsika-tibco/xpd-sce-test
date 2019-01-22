/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Channel Types</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getTargets <em>Targets</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getPresentations <em>Presentations</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getImplementations <em>Implementations</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getChannelTypes <em>Channel Types</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.ChannelTypes#getChannelDestinations <em>Channel Destinations</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelTypes()
 * @model
 * @generated
 */
public interface ChannelTypes extends EObject {
    /**
     * Returns the value of the '<em><b>Targets</b></em>' containment reference
     * list. The list contents are of type
     * {@link com.tibco.xpd.presentation.channeltypes.Target}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Targets</em>' containment reference list isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Targets</em>' containment reference list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelTypes_Targets()
     * @model containment="true"
     * @generated
     */
    EList<Target> getTargets();

    /**
     * Returns the value of the '<em><b>Presentations</b></em>' containment
     * reference list. The list contents are of type
     * {@link com.tibco.xpd.presentation.channeltypes.Presentation}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Presentations</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Presentations</em>' containment reference
     *         list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelTypes_Presentations()
     * @model containment="true"
     * @generated
     */
    EList<Presentation> getPresentations();

    /**
     * Returns the value of the '<em><b>Implementations</b></em>' containment
     * reference list. The list contents are of type
     * {@link com.tibco.xpd.presentation.channeltypes.Implementation}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Implementations</em>' containment reference
     * list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Implementations</em>' containment reference
     *         list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelTypes_Implementations()
     * @model containment="true"
     * @generated
     */
    EList<Implementation> getImplementations();

    /**
     * Returns the value of the '<em><b>Channel Types</b></em>' containment
     * reference list. The list contents are of type
     * {@link com.tibco.xpd.presentation.channeltypes.ChannelType}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Channel Types</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Channel Types</em>' containment reference
     *         list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelTypes_ChannelTypes()
     * @model containment="true"
     * @generated
     */
    EList<ChannelType> getChannelTypes();

    /**
     * Returns the value of the '<em><b>Channel Destinations</b></em>' reference
     * list. The list contents are of type
     * {@link com.tibco.xpd.presentation.channeltypes.ChannelDestination}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Channel Destinations</em>' reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Channel Destinations</em>' reference list.
     * @see com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage#getChannelTypes_ChannelDestinations()
     * @model
     * @generated
     */
    EList<ChannelDestination> getChannelDestinations();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model
     * @generated
     */
    ChannelType getChannelType(String channelTypeId);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model
     * @generated
     */
    Target getTarget(String targetId);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model
     * @generated
     */
    Presentation getPresentation(String presentationId);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model
     * @generated
     */
    Implementation getImplementation(String implementationId);

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @model ordered="false" destinationsMany="true" destinationsOrdered="false"
     * @generated
     */
    EList<ChannelType> getChannelTypes(EList<ChannelDestination> destinations);

} // ChannelTypes
