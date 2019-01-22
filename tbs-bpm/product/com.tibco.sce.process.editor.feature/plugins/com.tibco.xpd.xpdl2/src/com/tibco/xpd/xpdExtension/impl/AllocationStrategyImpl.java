/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationStrategyType;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Allocation Strategy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl#getOffer <em>Offer</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl#getStrategy <em>Strategy</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl#isReOfferOnClose <em>Re Offer On Close</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl#isReOfferOnCancel <em>Re Offer On Cancel</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.AllocationStrategyImpl#getAllocateToOfferSetMemberIdentifier <em>Allocate To Offer Set Member Identifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AllocationStrategyImpl extends EObjectImpl
        implements AllocationStrategy {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getOffer() <em>Offer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOffer()
     * @generated
     * @ordered
     */
    protected static final AllocationType OFFER_EDEFAULT =
            AllocationType.OFFER_ALL;

    /**
     * The cached value of the '{@link #getOffer() <em>Offer</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOffer()
     * @generated
     * @ordered
     */
    protected AllocationType offer = OFFER_EDEFAULT;

    /**
     * This is true if the Offer attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean offerESet;

    /**
     * The default value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStrategy()
     * @generated
     * @ordered
     */
    protected static final AllocationStrategyType STRATEGY_EDEFAULT =
            AllocationStrategyType.SYSTEM_DETERMINED;

    /**
     * The cached value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getStrategy()
     * @generated
     * @ordered
     */
    protected AllocationStrategyType strategy = STRATEGY_EDEFAULT;

    /**
     * This is true if the Strategy attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean strategyESet;

    /**
     * The default value of the '{@link #isReOfferOnClose() <em>Re Offer On Close</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReOfferOnClose()
     * @generated
     * @ordered
     */
    protected static final boolean RE_OFFER_ON_CLOSE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isReOfferOnClose() <em>Re Offer On Close</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReOfferOnClose()
     * @generated
     * @ordered
     */
    protected boolean reOfferOnClose = RE_OFFER_ON_CLOSE_EDEFAULT;

    /**
     * This is true if the Re Offer On Close attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean reOfferOnCloseESet;

    /**
     * The default value of the '{@link #isReOfferOnCancel() <em>Re Offer On Cancel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReOfferOnCancel()
     * @generated
     * @ordered
     */
    protected static final boolean RE_OFFER_ON_CANCEL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isReOfferOnCancel() <em>Re Offer On Cancel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isReOfferOnCancel()
     * @generated
     * @ordered
     */
    protected boolean reOfferOnCancel = RE_OFFER_ON_CANCEL_EDEFAULT;

    /**
     * This is true if the Re Offer On Cancel attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean reOfferOnCancelESet;

    /**
     * The default value of the '{@link #getAllocateToOfferSetMemberIdentifier() <em>Allocate To Offer Set Member Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocateToOfferSetMemberIdentifier()
     * @generated
     * @ordered
     */
    protected static final String ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER_EDEFAULT =
            null;

    /**
     * The cached value of the '{@link #getAllocateToOfferSetMemberIdentifier() <em>Allocate To Offer Set Member Identifier</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAllocateToOfferSetMemberIdentifier()
     * @generated
     * @ordered
     */
    protected String allocateToOfferSetMemberIdentifier =
            ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER_EDEFAULT;

    /**
     * This is true if the Allocate To Offer Set Member Identifier attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean allocateToOfferSetMemberIdentifierESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected AllocationStrategyImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.ALLOCATION_STRATEGY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocationType getOffer() {
        return offer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOffer(AllocationType newOffer) {
        AllocationType oldOffer = offer;
        offer = newOffer == null ? OFFER_EDEFAULT : newOffer;
        boolean oldOfferESet = offerESet;
        offerESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__OFFER, oldOffer,
                    offer, !oldOfferESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetOffer() {
        AllocationType oldOffer = offer;
        boolean oldOfferESet = offerESet;
        offer = OFFER_EDEFAULT;
        offerESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__OFFER, oldOffer,
                    OFFER_EDEFAULT, oldOfferESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetOffer() {
        return offerESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AllocationStrategyType getStrategy() {
        return strategy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setStrategy(AllocationStrategyType newStrategy) {
        AllocationStrategyType oldStrategy = strategy;
        strategy = newStrategy == null ? STRATEGY_EDEFAULT : newStrategy;
        boolean oldStrategyESet = strategyESet;
        strategyESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__STRATEGY,
                    oldStrategy, strategy, !oldStrategyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetStrategy() {
        AllocationStrategyType oldStrategy = strategy;
        boolean oldStrategyESet = strategyESet;
        strategy = STRATEGY_EDEFAULT;
        strategyESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__STRATEGY,
                    oldStrategy, STRATEGY_EDEFAULT, oldStrategyESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetStrategy() {
        return strategyESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isReOfferOnClose() {
        return reOfferOnClose;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReOfferOnClose(boolean newReOfferOnClose) {
        boolean oldReOfferOnClose = reOfferOnClose;
        reOfferOnClose = newReOfferOnClose;
        boolean oldReOfferOnCloseESet = reOfferOnCloseESet;
        reOfferOnCloseESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE,
                    oldReOfferOnClose, reOfferOnClose, !oldReOfferOnCloseESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReOfferOnClose() {
        boolean oldReOfferOnClose = reOfferOnClose;
        boolean oldReOfferOnCloseESet = reOfferOnCloseESet;
        reOfferOnClose = RE_OFFER_ON_CLOSE_EDEFAULT;
        reOfferOnCloseESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE,
                    oldReOfferOnClose, RE_OFFER_ON_CLOSE_EDEFAULT,
                    oldReOfferOnCloseESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReOfferOnClose() {
        return reOfferOnCloseESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isReOfferOnCancel() {
        return reOfferOnCancel;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setReOfferOnCancel(boolean newReOfferOnCancel) {
        boolean oldReOfferOnCancel = reOfferOnCancel;
        reOfferOnCancel = newReOfferOnCancel;
        boolean oldReOfferOnCancelESet = reOfferOnCancelESet;
        reOfferOnCancelESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL,
                    oldReOfferOnCancel, reOfferOnCancel,
                    !oldReOfferOnCancelESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetReOfferOnCancel() {
        boolean oldReOfferOnCancel = reOfferOnCancel;
        boolean oldReOfferOnCancelESet = reOfferOnCancelESet;
        reOfferOnCancel = RE_OFFER_ON_CANCEL_EDEFAULT;
        reOfferOnCancelESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL,
                    oldReOfferOnCancel, RE_OFFER_ON_CANCEL_EDEFAULT,
                    oldReOfferOnCancelESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetReOfferOnCancel() {
        return reOfferOnCancelESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getAllocateToOfferSetMemberIdentifier() {
        return allocateToOfferSetMemberIdentifier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setAllocateToOfferSetMemberIdentifier(
            String newAllocateToOfferSetMemberIdentifier) {
        String oldAllocateToOfferSetMemberIdentifier =
                allocateToOfferSetMemberIdentifier;
        allocateToOfferSetMemberIdentifier =
                newAllocateToOfferSetMemberIdentifier;
        boolean oldAllocateToOfferSetMemberIdentifierESet =
                allocateToOfferSetMemberIdentifierESet;
        allocateToOfferSetMemberIdentifierESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER,
                    oldAllocateToOfferSetMemberIdentifier,
                    allocateToOfferSetMemberIdentifier,
                    !oldAllocateToOfferSetMemberIdentifierESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetAllocateToOfferSetMemberIdentifier() {
        String oldAllocateToOfferSetMemberIdentifier =
                allocateToOfferSetMemberIdentifier;
        boolean oldAllocateToOfferSetMemberIdentifierESet =
                allocateToOfferSetMemberIdentifierESet;
        allocateToOfferSetMemberIdentifier =
                ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER_EDEFAULT;
        allocateToOfferSetMemberIdentifierESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER,
                    oldAllocateToOfferSetMemberIdentifier,
                    ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER_EDEFAULT,
                    oldAllocateToOfferSetMemberIdentifierESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetAllocateToOfferSetMemberIdentifier() {
        return allocateToOfferSetMemberIdentifierESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.ALLOCATION_STRATEGY__OFFER:
            return getOffer();
        case XpdExtensionPackage.ALLOCATION_STRATEGY__STRATEGY:
            return getStrategy();
        case XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE:
            return isReOfferOnClose();
        case XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL:
            return isReOfferOnCancel();
        case XpdExtensionPackage.ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER:
            return getAllocateToOfferSetMemberIdentifier();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case XpdExtensionPackage.ALLOCATION_STRATEGY__OFFER:
            setOffer((AllocationType) newValue);
            return;
        case XpdExtensionPackage.ALLOCATION_STRATEGY__STRATEGY:
            setStrategy((AllocationStrategyType) newValue);
            return;
        case XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE:
            setReOfferOnClose((Boolean) newValue);
            return;
        case XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL:
            setReOfferOnCancel((Boolean) newValue);
            return;
        case XpdExtensionPackage.ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER:
            setAllocateToOfferSetMemberIdentifier((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.ALLOCATION_STRATEGY__OFFER:
            unsetOffer();
            return;
        case XpdExtensionPackage.ALLOCATION_STRATEGY__STRATEGY:
            unsetStrategy();
            return;
        case XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE:
            unsetReOfferOnClose();
            return;
        case XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL:
            unsetReOfferOnCancel();
            return;
        case XpdExtensionPackage.ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER:
            unsetAllocateToOfferSetMemberIdentifier();
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case XpdExtensionPackage.ALLOCATION_STRATEGY__OFFER:
            return isSetOffer();
        case XpdExtensionPackage.ALLOCATION_STRATEGY__STRATEGY:
            return isSetStrategy();
        case XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CLOSE:
            return isSetReOfferOnClose();
        case XpdExtensionPackage.ALLOCATION_STRATEGY__RE_OFFER_ON_CANCEL:
            return isSetReOfferOnCancel();
        case XpdExtensionPackage.ALLOCATION_STRATEGY__ALLOCATE_TO_OFFER_SET_MEMBER_IDENTIFIER:
            return isSetAllocateToOfferSetMemberIdentifier();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (offer: "); //$NON-NLS-1$
        if (offerESet)
            result.append(offer);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", strategy: "); //$NON-NLS-1$
        if (strategyESet)
            result.append(strategy);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", reOfferOnClose: "); //$NON-NLS-1$
        if (reOfferOnCloseESet)
            result.append(reOfferOnClose);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", reOfferOnCancel: "); //$NON-NLS-1$
        if (reOfferOnCancelESet)
            result.append(reOfferOnCancel);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", allocateToOfferSetMemberIdentifier: "); //$NON-NLS-1$
        if (allocateToOfferSetMemberIdentifierESet)
            result.append(allocateToOfferSetMemberIdentifier);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //AllocationStrategyImpl
