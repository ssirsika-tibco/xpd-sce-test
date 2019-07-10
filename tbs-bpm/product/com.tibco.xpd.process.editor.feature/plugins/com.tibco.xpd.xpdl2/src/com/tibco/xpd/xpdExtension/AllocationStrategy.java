/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Allocation Strategy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getOffer <em>Offer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getStrategy <em>Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnClose <em>Re Offer On Close</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnCancel <em>Re Offer On Cancel</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getAllocateToOfferSetMemberIdentifier <em>Allocate To Offer Set Member Identifier</em>}</li>
 * </ul>
 *
 * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAllocationStrategy()
 * @model extendedMetaData="name='AllocationStrategy' kind='elementOnly'"
 * @generated
 */
public interface AllocationStrategy extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright = "Copyright (c) TIBCO Software Inc 2004 - 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Offer</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.AllocationType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Allocation via 'Offer' implies an extra
     * step involving user choice prior to work
     * items reaching an allocated state, so if
     * this attribute is missing it implies the
     * allocation decision is the prerogative of
     * the BPMS alone.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Offer</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.AllocationType
     * @see #isSetOffer()
     * @see #unsetOffer()
     * @see #setOffer(AllocationType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAllocationStrategy_Offer()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='Offer' namespace='##targetNamespace'"
     * @generated
     */
    AllocationType getOffer();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getOffer <em>Offer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Offer</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.AllocationType
     * @see #isSetOffer()
     * @see #unsetOffer()
     * @see #getOffer()
     * @generated
     */
    void setOffer(AllocationType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getOffer <em>Offer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOffer()
     * @see #getOffer()
     * @see #setOffer(AllocationType)
     * @generated
     */
    void unsetOffer();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getOffer <em>Offer</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Offer</em>' attribute is set.
     * @see #unsetOffer()
     * @see #getOffer()
     * @see #setOffer(AllocationType)
     * @generated
     */
    boolean isSetOffer();

    /**
     * Returns the value of the '<em><b>Strategy</b></em>' attribute.
     * The literals are from the enumeration {@link com.tibco.xpd.xpdExtension.AllocationStrategyType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Strategy</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Strategy</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategyType
     * @see #isSetStrategy()
     * @see #unsetStrategy()
     * @see #setStrategy(AllocationStrategyType)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAllocationStrategy_Strategy()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='Strategy' namespace='##targetNamespace'"
     * @generated
     */
    AllocationStrategyType getStrategy();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getStrategy <em>Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Strategy</em>' attribute.
     * @see com.tibco.xpd.xpdExtension.AllocationStrategyType
     * @see #isSetStrategy()
     * @see #unsetStrategy()
     * @see #getStrategy()
     * @generated
     */
    void setStrategy(AllocationStrategyType value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getStrategy <em>Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetStrategy()
     * @see #getStrategy()
     * @see #setStrategy(AllocationStrategyType)
     * @generated
     */
    void unsetStrategy();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getStrategy <em>Strategy</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Strategy</em>' attribute is set.
     * @see #unsetStrategy()
     * @see #getStrategy()
     * @see #setStrategy(AllocationStrategyType)
     * @generated
     */
    boolean isSetStrategy();

    /**
     * Returns the value of the '<em><b>Re Offer On Close</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Whether the work item should be re-offered to others when the 
     * first opener closes the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Re Offer On Close</em>' attribute.
     * @see #isSetReOfferOnClose()
     * @see #unsetReOfferOnClose()
     * @see #setReOfferOnClose(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAllocationStrategy_ReOfferOnClose()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='ReOfferOnClose' namespace='##targetNamespace'"
     * @generated
     */
    boolean isReOfferOnClose();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnClose <em>Re Offer On Close</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Re Offer On Close</em>' attribute.
     * @see #isSetReOfferOnClose()
     * @see #unsetReOfferOnClose()
     * @see #isReOfferOnClose()
     * @generated
     */
    void setReOfferOnClose(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnClose <em>Re Offer On Close</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetReOfferOnClose()
     * @see #isReOfferOnClose()
     * @see #setReOfferOnClose(boolean)
     * @generated
     */
    void unsetReOfferOnClose();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnClose <em>Re Offer On Close</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Re Offer On Close</em>' attribute is set.
     * @see #unsetReOfferOnClose()
     * @see #isReOfferOnClose()
     * @see #setReOfferOnClose(boolean)
     * @generated
     */
    boolean isSetReOfferOnClose();

    /**
     * Returns the value of the '<em><b>Re Offer On Cancel</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Whether the work item should be re-offered to others when the 
     * first opener cancels the opening of the work item.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Re Offer On Cancel</em>' attribute.
     * @see #isSetReOfferOnCancel()
     * @see #unsetReOfferOnCancel()
     * @see #setReOfferOnCancel(boolean)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAllocationStrategy_ReOfferOnCancel()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='ReOfferOnCancel' namespace='##targetNamespace'"
     * @generated
     */
    boolean isReOfferOnCancel();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnCancel <em>Re Offer On Cancel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Re Offer On Cancel</em>' attribute.
     * @see #isSetReOfferOnCancel()
     * @see #unsetReOfferOnCancel()
     * @see #isReOfferOnCancel()
     * @generated
     */
    void setReOfferOnCancel(boolean value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnCancel <em>Re Offer On Cancel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetReOfferOnCancel()
     * @see #isReOfferOnCancel()
     * @see #setReOfferOnCancel(boolean)
     * @generated
     */
    void unsetReOfferOnCancel();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#isReOfferOnCancel <em>Re Offer On Cancel</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Re Offer On Cancel</em>' attribute is set.
     * @see #unsetReOfferOnCancel()
     * @see #isReOfferOnCancel()
     * @see #setReOfferOnCancel(boolean)
     * @generated
     */
    boolean isSetReOfferOnCancel();

    /**
     * Returns the value of the '<em><b>Allocate To Offer Set Member Identifier</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Performer field that identifies the specific offer set member to allocate work item to.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Allocate To Offer Set Member Identifier</em>' attribute.
     * @see #isSetAllocateToOfferSetMemberIdentifier()
     * @see #unsetAllocateToOfferSetMemberIdentifier()
     * @see #setAllocateToOfferSetMemberIdentifier(String)
     * @see com.tibco.xpd.xpdExtension.XpdExtensionPackage#getAllocationStrategy_AllocateToOfferSetMemberIdentifier()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='AllocateToOfferSetMemberIdentifier' namespace='##targetNamespace'"
     * @generated
     */
    String getAllocateToOfferSetMemberIdentifier();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getAllocateToOfferSetMemberIdentifier <em>Allocate To Offer Set Member Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Allocate To Offer Set Member Identifier</em>' attribute.
     * @see #isSetAllocateToOfferSetMemberIdentifier()
     * @see #unsetAllocateToOfferSetMemberIdentifier()
     * @see #getAllocateToOfferSetMemberIdentifier()
     * @generated
     */
    void setAllocateToOfferSetMemberIdentifier(String value);

    /**
     * Unsets the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getAllocateToOfferSetMemberIdentifier <em>Allocate To Offer Set Member Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetAllocateToOfferSetMemberIdentifier()
     * @see #getAllocateToOfferSetMemberIdentifier()
     * @see #setAllocateToOfferSetMemberIdentifier(String)
     * @generated
     */
    void unsetAllocateToOfferSetMemberIdentifier();

    /**
     * Returns whether the value of the '{@link com.tibco.xpd.xpdExtension.AllocationStrategy#getAllocateToOfferSetMemberIdentifier <em>Allocate To Offer Set Member Identifier</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Allocate To Offer Set Member Identifier</em>' attribute is set.
     * @see #unsetAllocateToOfferSetMemberIdentifier()
     * @see #getAllocateToOfferSetMemberIdentifier()
     * @see #setAllocateToOfferSetMemberIdentifier(String)
     * @generated
     */
    boolean isSetAllocateToOfferSetMemberIdentifier();

} // AllocationStrategy
