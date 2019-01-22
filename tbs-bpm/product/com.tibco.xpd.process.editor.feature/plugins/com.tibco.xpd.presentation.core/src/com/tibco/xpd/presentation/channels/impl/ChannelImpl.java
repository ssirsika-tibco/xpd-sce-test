/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channels.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.presentation.channels.Channel;
import com.tibco.xpd.presentation.channels.ChannelsPackage;
import com.tibco.xpd.presentation.channels.TypeAssociation;
import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.channeltypes.impl.NamedElementImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Channel</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link com.tibco.xpd.presentation.channels.impl.ChannelImpl#getTypeAssociations
 * <em>Type Associations</em>}</li>
 * <li>{@link com.tibco.xpd.presentation.channels.impl.ChannelImpl#isDefault
 * <em>Default</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ChannelImpl extends NamedElementImpl implements Channel {
    /**
     * The cached value of the '{@link #getTypeAssociations()
     * <em>Type Associations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTypeAssociations()
     * @generated
     * @ordered
     */
    protected EList<TypeAssociation> typeAssociations;

    /**
     * The default value of the '{@link #isDefault() <em>Default</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isDefault()
     * @generated
     * @ordered
     */
    protected static final boolean DEFAULT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isDefault() <em>Default</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isDefault()
     * @generated
     * @ordered
     */
    protected boolean default_ = DEFAULT_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ChannelImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChannelsPackage.Literals.CHANNEL;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EList<TypeAssociation> getTypeAssociations() {
        if (typeAssociations == null) {
            typeAssociations =
                    new EObjectContainmentEList<TypeAssociation>(
                            TypeAssociation.class, this,
                            ChannelsPackage.CHANNEL__TYPE_ASSOCIATIONS);
        }
        return typeAssociations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean isDefault() {
        return default_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void setDefault(boolean newDefault) {
        boolean oldDefault = default_;
        default_ = newDefault;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ChannelsPackage.CHANNEL__DEFAULT, oldDefault, default_));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public EList<TypeAssociation> getTypeAssociations(
            EList<ChannelDestination> destinations) {
        BasicEList<TypeAssociation> filteredTypeAsocitaions =
                new BasicEList<TypeAssociation>();
        for (TypeAssociation tAssoc : getTypeAssociations()) {
            for (ChannelDestination destination : destinations) {
                if (tAssoc.getChannelType() != null
                        && tAssoc.getChannelType().getDestinations()
                                .contains(destination)) {
                    filteredTypeAsocitaions.add(tAssoc);
                    break;
                }
            }
        }
        return ECollections.unmodifiableEList(filteredTypeAsocitaions);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ChannelsPackage.CHANNEL__TYPE_ASSOCIATIONS:
            return ((InternalEList<?>) getTypeAssociations())
                    .basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ChannelsPackage.CHANNEL__TYPE_ASSOCIATIONS:
            return getTypeAssociations();
        case ChannelsPackage.CHANNEL__DEFAULT:
            return isDefault();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ChannelsPackage.CHANNEL__TYPE_ASSOCIATIONS:
            getTypeAssociations().clear();
            getTypeAssociations()
                    .addAll((Collection<? extends TypeAssociation>) newValue);
            return;
        case ChannelsPackage.CHANNEL__DEFAULT:
            setDefault((Boolean) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case ChannelsPackage.CHANNEL__TYPE_ASSOCIATIONS:
            getTypeAssociations().clear();
            return;
        case ChannelsPackage.CHANNEL__DEFAULT:
            setDefault(DEFAULT_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case ChannelsPackage.CHANNEL__TYPE_ASSOCIATIONS:
            return typeAssociations != null && !typeAssociations.isEmpty();
        case ChannelsPackage.CHANNEL__DEFAULT:
            return default_ != DEFAULT_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (default: ");
        result.append(default_);
        result.append(')');
        return result.toString();
    }

} // ChannelImpl
