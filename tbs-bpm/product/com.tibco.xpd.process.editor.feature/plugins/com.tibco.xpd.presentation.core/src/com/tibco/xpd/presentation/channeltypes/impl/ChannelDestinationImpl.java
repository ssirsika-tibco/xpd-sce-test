/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes.impl;

import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Channel Destination</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelDestinationImpl#getChannelTypes <em>Channel Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChannelDestinationImpl extends NamedElementImpl implements ChannelDestination {
    /**
     * The cached value of the '{@link #getChannelTypes() <em>Channel Types</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getChannelTypes()
     * @generated
     * @ordered
     */
    protected EList<ChannelType> channelTypes;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ChannelDestinationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChannelTypesPackage.Literals.CHANNEL_DESTINATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ChannelType> getChannelTypes() {
        if (channelTypes == null) {
            channelTypes = new EObjectWithInverseResolvingEList.ManyInverse<ChannelType>(ChannelType.class, this, ChannelTypesPackage.CHANNEL_DESTINATION__CHANNEL_TYPES, ChannelTypesPackage.CHANNEL_TYPE__DESTINATIONS);
        }
        return channelTypes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ChannelTypesPackage.CHANNEL_DESTINATION__CHANNEL_TYPES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getChannelTypes()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ChannelTypesPackage.CHANNEL_DESTINATION__CHANNEL_TYPES:
                return ((InternalEList<?>)getChannelTypes()).basicRemove(otherEnd, msgs);
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
            case ChannelTypesPackage.CHANNEL_DESTINATION__CHANNEL_TYPES:
                return getChannelTypes();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ChannelTypesPackage.CHANNEL_DESTINATION__CHANNEL_TYPES:
                getChannelTypes().clear();
                getChannelTypes().addAll((Collection<? extends ChannelType>)newValue);
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
            case ChannelTypesPackage.CHANNEL_DESTINATION__CHANNEL_TYPES:
                getChannelTypes().clear();
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
            case ChannelTypesPackage.CHANNEL_DESTINATION__CHANNEL_TYPES:
                return channelTypes != null && !channelTypes.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ChannelDestinationImpl
