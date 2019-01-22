/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import com.tibco.xpd.xpdl2.DeprecatedXorType;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.GatewayType;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.XorType;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Route</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RouteImpl#getOtherElements <em>Other Elements</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RouteImpl#getGatewayType <em>Gateway Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RouteImpl#getDeprecatedXorType <em>Deprecated Xor Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RouteImpl#isDeprecatedInstantiate <em>Deprecated Instantiate</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RouteImpl#isMarkerVisible <em>Marker Visible</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RouteImpl#getIncomingCondition <em>Incoming Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RouteImpl#getOutgoingCondition <em>Outgoing Condition</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.RouteImpl#getExclusiveType <em>Exclusive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RouteImpl extends EObjectImpl implements Route {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherElements() <em>Other Elements</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherElements()
     * @generated
     * @ordered
     */
    protected FeatureMap otherElements;

    /**
     * The default value of the '{@link #getGatewayType() <em>Gateway Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGatewayType()
     * @generated
     * @ordered
     */
    protected static final JoinSplitType GATEWAY_TYPE_EDEFAULT =
            JoinSplitType.DEPRECATED_AND_LITERAL;

    /**
     * The cached value of the '{@link #getGatewayType() <em>Gateway Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGatewayType()
     * @generated
     * @ordered
     */
    protected JoinSplitType gatewayType = GATEWAY_TYPE_EDEFAULT;

    /**
     * This is true if the Gateway Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean gatewayTypeESet;

    /**
     * The default value of the '{@link #getDeprecatedXorType() <em>Deprecated Xor Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedXorType()
     * @generated
     * @ordered
     */
    protected static final DeprecatedXorType DEPRECATED_XOR_TYPE_EDEFAULT =
            DeprecatedXorType.DATA;

    /**
     * The cached value of the '{@link #getDeprecatedXorType() <em>Deprecated Xor Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDeprecatedXorType()
     * @generated
     * @ordered
     */
    protected DeprecatedXorType deprecatedXorType =
            DEPRECATED_XOR_TYPE_EDEFAULT;

    /**
     * This is true if the Deprecated Xor Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean deprecatedXorTypeESet;

    /**
     * The default value of the '{@link #isDeprecatedInstantiate() <em>Deprecated Instantiate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeprecatedInstantiate()
     * @generated
     * @ordered
     */
    protected static final boolean DEPRECATED_INSTANTIATE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDeprecatedInstantiate() <em>Deprecated Instantiate</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isDeprecatedInstantiate()
     * @generated
     * @ordered
     */
    protected boolean deprecatedInstantiate = DEPRECATED_INSTANTIATE_EDEFAULT;

    /**
     * This is true if the Deprecated Instantiate attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean deprecatedInstantiateESet;

    /**
     * The default value of the '{@link #isMarkerVisible() <em>Marker Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isMarkerVisible()
     * @generated
     * @ordered
     */
    protected static final boolean MARKER_VISIBLE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isMarkerVisible() <em>Marker Visible</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isMarkerVisible()
     * @generated
     * @ordered
     */
    protected boolean markerVisible = MARKER_VISIBLE_EDEFAULT;

    /**
     * This is true if the Marker Visible attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean markerVisibleESet;

    /**
     * The default value of the '{@link #getIncomingCondition() <em>Incoming Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingCondition()
     * @generated
     * @ordered
     */
    protected static final String INCOMING_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIncomingCondition() <em>Incoming Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingCondition()
     * @generated
     * @ordered
     */
    protected String incomingCondition = INCOMING_CONDITION_EDEFAULT;

    /**
     * The default value of the '{@link #getOutgoingCondition() <em>Outgoing Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingCondition()
     * @generated
     * @ordered
     */
    protected static final String OUTGOING_CONDITION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOutgoingCondition() <em>Outgoing Condition</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingCondition()
     * @generated
     * @ordered
     */
    protected String outgoingCondition = OUTGOING_CONDITION_EDEFAULT;

    /**
     * The default value of the '{@link #getExclusiveType() <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExclusiveType()
     * @generated
     * @ordered
     */
    protected static final ExclusiveType EXCLUSIVE_TYPE_EDEFAULT =
            ExclusiveType.DATA;

    /**
     * The cached value of the '{@link #getExclusiveType() <em>Exclusive Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getExclusiveType()
     * @generated
     * @ordered
     */
    protected ExclusiveType exclusiveType = EXCLUSIVE_TYPE_EDEFAULT;

    /**
     * This is true if the Exclusive Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean exclusiveTypeESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RouteImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.ROUTE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherElements() {
        if (otherElements == null) {
            otherElements =
                    new BasicFeatureMap(this,
                            Xpdl2Package.ROUTE__OTHER_ELEMENTS);
        }
        return otherElements;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public JoinSplitType getGatewayType() {
        return gatewayType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGatewayType(JoinSplitType newGatewayType) {
        JoinSplitType oldGatewayType = gatewayType;
        gatewayType =
                newGatewayType == null ? GATEWAY_TYPE_EDEFAULT : newGatewayType;
        boolean oldGatewayTypeESet = gatewayTypeESet;
        gatewayTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ROUTE__GATEWAY_TYPE, oldGatewayType,
                    gatewayType, !oldGatewayTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetGatewayType() {
        JoinSplitType oldGatewayType = gatewayType;
        boolean oldGatewayTypeESet = gatewayTypeESet;
        gatewayType = GATEWAY_TYPE_EDEFAULT;
        gatewayTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ROUTE__GATEWAY_TYPE, oldGatewayType,
                    GATEWAY_TYPE_EDEFAULT, oldGatewayTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetGatewayType() {
        return gatewayTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeprecatedXorType getDeprecatedXorType() {
        return deprecatedXorType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedXorType(DeprecatedXorType newDeprecatedXorType) {
        DeprecatedXorType oldDeprecatedXorType = deprecatedXorType;
        deprecatedXorType =
                newDeprecatedXorType == null ? DEPRECATED_XOR_TYPE_EDEFAULT
                        : newDeprecatedXorType;
        boolean oldDeprecatedXorTypeESet = deprecatedXorTypeESet;
        deprecatedXorTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ROUTE__DEPRECATED_XOR_TYPE,
                    oldDeprecatedXorType, deprecatedXorType,
                    !oldDeprecatedXorTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDeprecatedXorType() {
        DeprecatedXorType oldDeprecatedXorType = deprecatedXorType;
        boolean oldDeprecatedXorTypeESet = deprecatedXorTypeESet;
        deprecatedXorType = DEPRECATED_XOR_TYPE_EDEFAULT;
        deprecatedXorTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ROUTE__DEPRECATED_XOR_TYPE,
                    oldDeprecatedXorType, DEPRECATED_XOR_TYPE_EDEFAULT,
                    oldDeprecatedXorTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDeprecatedXorType() {
        return deprecatedXorTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isDeprecatedInstantiate() {
        return deprecatedInstantiate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeprecatedInstantiate(boolean newDeprecatedInstantiate) {
        boolean oldDeprecatedInstantiate = deprecatedInstantiate;
        deprecatedInstantiate = newDeprecatedInstantiate;
        boolean oldDeprecatedInstantiateESet = deprecatedInstantiateESet;
        deprecatedInstantiateESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ROUTE__DEPRECATED_INSTANTIATE,
                    oldDeprecatedInstantiate, deprecatedInstantiate,
                    !oldDeprecatedInstantiateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetDeprecatedInstantiate() {
        boolean oldDeprecatedInstantiate = deprecatedInstantiate;
        boolean oldDeprecatedInstantiateESet = deprecatedInstantiateESet;
        deprecatedInstantiate = DEPRECATED_INSTANTIATE_EDEFAULT;
        deprecatedInstantiateESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ROUTE__DEPRECATED_INSTANTIATE,
                    oldDeprecatedInstantiate, DEPRECATED_INSTANTIATE_EDEFAULT,
                    oldDeprecatedInstantiateESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetDeprecatedInstantiate() {
        return deprecatedInstantiateESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExclusiveType getExclusiveType() {
        return exclusiveType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setExclusiveType(ExclusiveType newExclusiveType) {
        ExclusiveType oldExclusiveType = exclusiveType;
        exclusiveType =
                newExclusiveType == null ? EXCLUSIVE_TYPE_EDEFAULT
                        : newExclusiveType;
        boolean oldExclusiveTypeESet = exclusiveTypeESet;
        exclusiveTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ROUTE__EXCLUSIVE_TYPE, oldExclusiveType,
                    exclusiveType, !oldExclusiveTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetExclusiveType() {
        ExclusiveType oldExclusiveType = exclusiveType;
        boolean oldExclusiveTypeESet = exclusiveTypeESet;
        exclusiveType = EXCLUSIVE_TYPE_EDEFAULT;
        exclusiveTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ROUTE__EXCLUSIVE_TYPE, oldExclusiveType,
                    EXCLUSIVE_TYPE_EDEFAULT, oldExclusiveTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetExclusiveType() {
        return exclusiveTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isMarkerVisible() {
        return markerVisible;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMarkerVisible(boolean newMarkerVisible) {
        boolean oldMarkerVisible = markerVisible;
        markerVisible = newMarkerVisible;
        boolean oldMarkerVisibleESet = markerVisibleESet;
        markerVisibleESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ROUTE__MARKER_VISIBLE, oldMarkerVisible,
                    markerVisible, !oldMarkerVisibleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetMarkerVisible() {
        boolean oldMarkerVisible = markerVisible;
        boolean oldMarkerVisibleESet = markerVisibleESet;
        markerVisible = MARKER_VISIBLE_EDEFAULT;
        markerVisibleESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    Xpdl2Package.ROUTE__MARKER_VISIBLE, oldMarkerVisible,
                    MARKER_VISIBLE_EDEFAULT, oldMarkerVisibleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetMarkerVisible() {
        return markerVisibleESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getIncomingCondition() {
        return incomingCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setIncomingCondition(String newIncomingCondition) {
        String oldIncomingCondition = incomingCondition;
        incomingCondition = newIncomingCondition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ROUTE__INCOMING_CONDITION,
                    oldIncomingCondition, incomingCondition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOutgoingCondition() {
        return outgoingCondition;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOutgoingCondition(String newOutgoingCondition) {
        String oldOutgoingCondition = outgoingCondition;
        outgoingCondition = newOutgoingCondition;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.ROUTE__OUTGOING_CONDITION,
                    oldOutgoingCondition, outgoingCondition));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EObject getOtherElement(String elementName) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.ROUTE__OTHER_ELEMENTS:
            return ((InternalEList<?>) getOtherElements())
                    .basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case Xpdl2Package.ROUTE__OTHER_ELEMENTS:
            if (coreType)
                return getOtherElements();
            return ((FeatureMap.Internal) getOtherElements()).getWrapper();
        case Xpdl2Package.ROUTE__GATEWAY_TYPE:
            return getGatewayType();
        case Xpdl2Package.ROUTE__DEPRECATED_XOR_TYPE:
            return getDeprecatedXorType();
        case Xpdl2Package.ROUTE__DEPRECATED_INSTANTIATE:
            return isDeprecatedInstantiate();
        case Xpdl2Package.ROUTE__MARKER_VISIBLE:
            return isMarkerVisible();
        case Xpdl2Package.ROUTE__INCOMING_CONDITION:
            return getIncomingCondition();
        case Xpdl2Package.ROUTE__OUTGOING_CONDITION:
            return getOutgoingCondition();
        case Xpdl2Package.ROUTE__EXCLUSIVE_TYPE:
            return getExclusiveType();
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
        case Xpdl2Package.ROUTE__OTHER_ELEMENTS:
            ((FeatureMap.Internal) getOtherElements()).set(newValue);
            return;
        case Xpdl2Package.ROUTE__GATEWAY_TYPE:
            setGatewayType((JoinSplitType) newValue);
            return;
        case Xpdl2Package.ROUTE__DEPRECATED_XOR_TYPE:
            setDeprecatedXorType((DeprecatedXorType) newValue);
            return;
        case Xpdl2Package.ROUTE__DEPRECATED_INSTANTIATE:
            setDeprecatedInstantiate((Boolean) newValue);
            return;
        case Xpdl2Package.ROUTE__MARKER_VISIBLE:
            setMarkerVisible((Boolean) newValue);
            return;
        case Xpdl2Package.ROUTE__INCOMING_CONDITION:
            setIncomingCondition((String) newValue);
            return;
        case Xpdl2Package.ROUTE__OUTGOING_CONDITION:
            setOutgoingCondition((String) newValue);
            return;
        case Xpdl2Package.ROUTE__EXCLUSIVE_TYPE:
            setExclusiveType((ExclusiveType) newValue);
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
        case Xpdl2Package.ROUTE__OTHER_ELEMENTS:
            getOtherElements().clear();
            return;
        case Xpdl2Package.ROUTE__GATEWAY_TYPE:
            unsetGatewayType();
            return;
        case Xpdl2Package.ROUTE__DEPRECATED_XOR_TYPE:
            unsetDeprecatedXorType();
            return;
        case Xpdl2Package.ROUTE__DEPRECATED_INSTANTIATE:
            unsetDeprecatedInstantiate();
            return;
        case Xpdl2Package.ROUTE__MARKER_VISIBLE:
            unsetMarkerVisible();
            return;
        case Xpdl2Package.ROUTE__INCOMING_CONDITION:
            setIncomingCondition(INCOMING_CONDITION_EDEFAULT);
            return;
        case Xpdl2Package.ROUTE__OUTGOING_CONDITION:
            setOutgoingCondition(OUTGOING_CONDITION_EDEFAULT);
            return;
        case Xpdl2Package.ROUTE__EXCLUSIVE_TYPE:
            unsetExclusiveType();
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
        case Xpdl2Package.ROUTE__OTHER_ELEMENTS:
            return otherElements != null && !otherElements.isEmpty();
        case Xpdl2Package.ROUTE__GATEWAY_TYPE:
            return isSetGatewayType();
        case Xpdl2Package.ROUTE__DEPRECATED_XOR_TYPE:
            return isSetDeprecatedXorType();
        case Xpdl2Package.ROUTE__DEPRECATED_INSTANTIATE:
            return isSetDeprecatedInstantiate();
        case Xpdl2Package.ROUTE__MARKER_VISIBLE:
            return isSetMarkerVisible();
        case Xpdl2Package.ROUTE__INCOMING_CONDITION:
            return INCOMING_CONDITION_EDEFAULT == null ? incomingCondition != null
                    : !INCOMING_CONDITION_EDEFAULT.equals(incomingCondition);
        case Xpdl2Package.ROUTE__OUTGOING_CONDITION:
            return OUTGOING_CONDITION_EDEFAULT == null ? outgoingCondition != null
                    : !OUTGOING_CONDITION_EDEFAULT.equals(outgoingCondition);
        case Xpdl2Package.ROUTE__EXCLUSIVE_TYPE:
            return isSetExclusiveType();
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
        result.append(" (otherElements: "); //$NON-NLS-1$
        result.append(otherElements);
        result.append(", gatewayType: "); //$NON-NLS-1$
        if (gatewayTypeESet)
            result.append(gatewayType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", deprecatedXorType: "); //$NON-NLS-1$
        if (deprecatedXorTypeESet)
            result.append(deprecatedXorType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", deprecatedInstantiate: "); //$NON-NLS-1$
        if (deprecatedInstantiateESet)
            result.append(deprecatedInstantiate);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", markerVisible: "); //$NON-NLS-1$
        if (markerVisibleESet)
            result.append(markerVisible);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", incomingCondition: "); //$NON-NLS-1$
        result.append(incomingCondition);
        result.append(", outgoingCondition: "); //$NON-NLS-1$
        result.append(outgoingCondition);
        result.append(", exclusiveType: "); //$NON-NLS-1$
        if (exclusiveTypeESet)
            result.append(exclusiveType);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //RouteImpl
