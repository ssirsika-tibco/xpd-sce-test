/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.channeltypes.ChannelTypes;
import com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage;
import com.tibco.xpd.presentation.channeltypes.Implementation;
import com.tibco.xpd.presentation.channeltypes.Presentation;
import com.tibco.xpd.presentation.channeltypes.Target;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Channel Types</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl#getTargets
 * <em>Targets</em>}</li>
 * <li>
 * {@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl#getPresentations
 * <em>Presentations</em>}</li>
 * <li>
 * {@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl#getImplementations
 * <em>Implementations</em>}</li>
 * <li>
 * {@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl#getChannelTypes
 * <em>Channel Types</em>}</li>
 * <li>
 * {@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypesImpl#getChannelDestinations
 * <em>Channel Destinations</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ChannelTypesImpl extends EObjectImpl implements ChannelTypes {
    /**
     * The cached value of the '{@link #getTargets() <em>Targets</em>}'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getTargets()
     * @generated
     * @ordered
     */
    protected EList<Target> targets;

    /**
     * The cached value of the '{@link #getPresentations()
     * <em>Presentations</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getPresentations()
     * @generated
     * @ordered
     */
    protected EList<Presentation> presentations;

    /**
     * The cached value of the '{@link #getImplementations()
     * <em>Implementations</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getImplementations()
     * @generated
     * @ordered
     */
    protected EList<Implementation> implementations;

    /**
     * The cached value of the '{@link #getChannelTypes()
     * <em>Channel Types</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getChannelTypes()
     * @generated
     * @ordered
     */
    protected EList<ChannelType> channelTypes;

    /**
     * The cached value of the '{@link #getChannelDestinations()
     * <em>Channel Destinations</em>}' reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getChannelDestinations()
     * @generated
     * @ordered
     */
    protected EList<ChannelDestination> channelDestinations;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ChannelTypesImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChannelTypesPackage.Literals.CHANNEL_TYPES;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EList<Target> getTargets() {
        if (targets == null) {
            targets =
                    new EObjectContainmentEList<Target>(Target.class, this,
                            ChannelTypesPackage.CHANNEL_TYPES__TARGETS);
        }
        return targets;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EList<Presentation> getPresentations() {
        if (presentations == null) {
            presentations =
                    new EObjectContainmentEList<Presentation>(
                            Presentation.class, this,
                            ChannelTypesPackage.CHANNEL_TYPES__PRESENTATIONS);
        }
        return presentations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EList<Implementation> getImplementations() {
        if (implementations == null) {
            implementations =
                    new EObjectContainmentEList<Implementation>(
                            Implementation.class, this,
                            ChannelTypesPackage.CHANNEL_TYPES__IMPLEMENTATIONS);
        }
        return implementations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EList<ChannelType> getChannelTypes() {
        if (channelTypes == null) {
            channelTypes =
                    new EObjectContainmentEList<ChannelType>(ChannelType.class,
                            this,
                            ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_TYPES);
        }
        return channelTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public EList<ChannelDestination> getChannelDestinations() {
        if (channelDestinations == null) {
            channelDestinations =
                    new EObjectResolvingEList<ChannelDestination>(
                            ChannelDestination.class,
                            this,
                            ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_DESTINATIONS);
        }
        return channelDestinations;
    }

    /**
     * Gets user channel type by the string identifier.
     * 
     * @return channel type or <code>null</code> if not found.
     * 
     * @generated NOT
     */
    @Override
    public ChannelType getChannelType(String channelTypeId) {
        for (ChannelType channelType : getChannelTypes()) {
            if (channelType.getId().equals(channelTypeId)) {
                return channelType;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Target getTarget(String targetId) {
        for (Target target : getTargets()) {
            if (target.getId().equals(targetId)) {
                return target;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Presentation getPresentation(String presentationId) {
        for (Presentation presentation : getPresentations()) {
            if (presentation.getId().equals(presentationId)) {
                return presentation;
            }
        }
        return null;

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public Implementation getImplementation(String implementationId) {
        for (Implementation implementation : getImplementations()) {
            if (implementation.getId().equals(implementationId)) {
                return implementation;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public EList<ChannelType> getChannelTypes(
            EList<ChannelDestination> destinations) {
        BasicEList<ChannelType> chTypes = new BasicEList<ChannelType>();
        for (ChannelType channelType : channelTypes) {
            for (ChannelDestination destination : destinations) {
                if (channelType.getDestinations().contains(destination)) {
                    chTypes.add(channelType);
                    break;
                }
            }
        }
        return ECollections.unmodifiableEList(chTypes);
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
        case ChannelTypesPackage.CHANNEL_TYPES__TARGETS:
            return ((InternalEList<?>) getTargets())
                    .basicRemove(otherEnd, msgs);
        case ChannelTypesPackage.CHANNEL_TYPES__PRESENTATIONS:
            return ((InternalEList<?>) getPresentations())
                    .basicRemove(otherEnd, msgs);
        case ChannelTypesPackage.CHANNEL_TYPES__IMPLEMENTATIONS:
            return ((InternalEList<?>) getImplementations())
                    .basicRemove(otherEnd, msgs);
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_TYPES:
            return ((InternalEList<?>) getChannelTypes()).basicRemove(otherEnd,
                    msgs);
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
        case ChannelTypesPackage.CHANNEL_TYPES__TARGETS:
            return getTargets();
        case ChannelTypesPackage.CHANNEL_TYPES__PRESENTATIONS:
            return getPresentations();
        case ChannelTypesPackage.CHANNEL_TYPES__IMPLEMENTATIONS:
            return getImplementations();
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_TYPES:
            return getChannelTypes();
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_DESTINATIONS:
            return getChannelDestinations();
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
        case ChannelTypesPackage.CHANNEL_TYPES__TARGETS:
            getTargets().clear();
            getTargets().addAll((Collection<? extends Target>) newValue);
            return;
        case ChannelTypesPackage.CHANNEL_TYPES__PRESENTATIONS:
            getPresentations().clear();
            getPresentations()
                    .addAll((Collection<? extends Presentation>) newValue);
            return;
        case ChannelTypesPackage.CHANNEL_TYPES__IMPLEMENTATIONS:
            getImplementations().clear();
            getImplementations()
                    .addAll((Collection<? extends Implementation>) newValue);
            return;
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_TYPES:
            getChannelTypes().clear();
            getChannelTypes()
                    .addAll((Collection<? extends ChannelType>) newValue);
            return;
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_DESTINATIONS:
            getChannelDestinations().clear();
            getChannelDestinations()
                    .addAll((Collection<? extends ChannelDestination>) newValue);
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
        case ChannelTypesPackage.CHANNEL_TYPES__TARGETS:
            getTargets().clear();
            return;
        case ChannelTypesPackage.CHANNEL_TYPES__PRESENTATIONS:
            getPresentations().clear();
            return;
        case ChannelTypesPackage.CHANNEL_TYPES__IMPLEMENTATIONS:
            getImplementations().clear();
            return;
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_TYPES:
            getChannelTypes().clear();
            return;
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_DESTINATIONS:
            getChannelDestinations().clear();
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
        case ChannelTypesPackage.CHANNEL_TYPES__TARGETS:
            return targets != null && !targets.isEmpty();
        case ChannelTypesPackage.CHANNEL_TYPES__PRESENTATIONS:
            return presentations != null && !presentations.isEmpty();
        case ChannelTypesPackage.CHANNEL_TYPES__IMPLEMENTATIONS:
            return implementations != null && !implementations.isEmpty();
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_TYPES:
            return channelTypes != null && !channelTypes.isEmpty();
        case ChannelTypesPackage.CHANNEL_TYPES__CHANNEL_DESTINATIONS:
            return channelDestinations != null
                    && !channelDestinations.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // ChannelTypesImpl
