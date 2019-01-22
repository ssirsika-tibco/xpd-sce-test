/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.presentation.channeltypes.impl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.presentation.channeltypes.Attribute;
import com.tibco.xpd.presentation.channeltypes.ChannelDestination;
import com.tibco.xpd.presentation.channeltypes.ChannelType;
import com.tibco.xpd.presentation.channeltypes.ChannelTypesPackage;
import com.tibco.xpd.presentation.channeltypes.Implementation;
import com.tibco.xpd.presentation.channeltypes.Presentation;
import com.tibco.xpd.presentation.channeltypes.Target;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Channel Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl#getPresentation <em>Presentation</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl#getRuntimeVersion <em>Runtime Version</em>}</li>
 *   <li>{@link com.tibco.xpd.presentation.channeltypes.impl.ChannelTypeImpl#getDestinations <em>Destinations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChannelTypeImpl extends NamedElementImpl implements ChannelType {
    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected Target target;

    /**
     * The cached value of the '{@link #getPresentation() <em>Presentation</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPresentation()
     * @generated
     * @ordered
     */
    protected Presentation presentation;

    /**
     * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected Implementation implementation;

    /**
     * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributes()
     * @generated
     * @ordered
     */
    protected EList<Attribute> attributes;

    /**
     * The default value of the '{@link #getRuntimeVersion() <em>Runtime Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRuntimeVersion()
     * @generated
     * @ordered
     */
    protected static final String RUNTIME_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getRuntimeVersion() <em>Runtime Version</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getRuntimeVersion()
     * @generated
     * @ordered
     */
    protected String runtimeVersion = RUNTIME_VERSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getDestinations() <em>Destinations</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDestinations()
     * @generated
     * @ordered
     */
    protected EList<ChannelDestination> destinations;

    protected List<String> globalDestinations;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ChannelTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ChannelTypesPackage.Literals.CHANNEL_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Target getTarget() {
        if (target != null && target.eIsProxy()) {
            InternalEObject oldTarget = (InternalEObject)target;
            target = (Target)eResolveProxy(oldTarget);
            if (target != oldTarget) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelTypesPackage.CHANNEL_TYPE__TARGET, oldTarget, target));
            }
        }
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Target basicGetTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTarget(Target newTarget, NotificationChain msgs) {
        Target oldTarget = target;
        target = newTarget;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.CHANNEL_TYPE__TARGET, oldTarget, newTarget);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTarget(Target newTarget) {
        if (newTarget != target) {
            NotificationChain msgs = null;
            if (target != null)
                msgs = ((InternalEObject)target).eInverseRemove(this, ChannelTypesPackage.TARGET__BINDINGS, Target.class, msgs);
            if (newTarget != null)
                msgs = ((InternalEObject)newTarget).eInverseAdd(this, ChannelTypesPackage.TARGET__BINDINGS, Target.class, msgs);
            msgs = basicSetTarget(newTarget, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.CHANNEL_TYPE__TARGET, newTarget, newTarget));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Presentation getPresentation() {
        if (presentation != null && presentation.eIsProxy()) {
            InternalEObject oldPresentation = (InternalEObject)presentation;
            presentation = (Presentation)eResolveProxy(oldPresentation);
            if (presentation != oldPresentation) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION, oldPresentation, presentation));
            }
        }
        return presentation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Presentation basicGetPresentation() {
        return presentation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPresentation(Presentation newPresentation, NotificationChain msgs) {
        Presentation oldPresentation = presentation;
        presentation = newPresentation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION, oldPresentation, newPresentation);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPresentation(Presentation newPresentation) {
        if (newPresentation != presentation) {
            NotificationChain msgs = null;
            if (presentation != null)
                msgs = ((InternalEObject)presentation).eInverseRemove(this, ChannelTypesPackage.PRESENTATION__BINDINGS, Presentation.class, msgs);
            if (newPresentation != null)
                msgs = ((InternalEObject)newPresentation).eInverseAdd(this, ChannelTypesPackage.PRESENTATION__BINDINGS, Presentation.class, msgs);
            msgs = basicSetPresentation(newPresentation, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION, newPresentation, newPresentation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Implementation getImplementation() {
        if (implementation != null && implementation.eIsProxy()) {
            InternalEObject oldImplementation = (InternalEObject)implementation;
            implementation = (Implementation)eResolveProxy(oldImplementation);
            if (implementation != oldImplementation) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION, oldImplementation, implementation));
            }
        }
        return implementation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Implementation basicGetImplementation() {
        return implementation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetImplementation(Implementation newImplementation, NotificationChain msgs) {
        Implementation oldImplementation = implementation;
        implementation = newImplementation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION, oldImplementation, newImplementation);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setImplementation(Implementation newImplementation) {
        if (newImplementation != implementation) {
            NotificationChain msgs = null;
            if (implementation != null)
                msgs = ((InternalEObject)implementation).eInverseRemove(this, ChannelTypesPackage.IMPLEMENTATION__BINDINGS, Implementation.class, msgs);
            if (newImplementation != null)
                msgs = ((InternalEObject)newImplementation).eInverseAdd(this, ChannelTypesPackage.IMPLEMENTATION__BINDINGS, Implementation.class, msgs);
            msgs = basicSetImplementation(newImplementation, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION, newImplementation, newImplementation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Attribute> getAttributes() {
        if (attributes == null) {
            attributes = new EObjectContainmentEList<Attribute>(Attribute.class, this, ChannelTypesPackage.CHANNEL_TYPE__ATTRIBUTES);
        }
        return attributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getRuntimeVersion() {
        return runtimeVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRuntimeVersion(String newRuntimeVersion) {
        String oldRuntimeVersion = runtimeVersion;
        runtimeVersion = newRuntimeVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ChannelTypesPackage.CHANNEL_TYPE__RUNTIME_VERSION, oldRuntimeVersion, runtimeVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<ChannelDestination> getDestinations() {
        if (destinations == null) {
            destinations = new EObjectWithInverseEList.ManyInverse<ChannelDestination>(ChannelDestination.class, this, ChannelTypesPackage.CHANNEL_TYPE__DESTINATIONS, ChannelTypesPackage.CHANNEL_DESTINATION__CHANNEL_TYPES);
        }
        return destinations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ChannelTypesPackage.CHANNEL_TYPE__TARGET:
                if (target != null)
                    msgs = ((InternalEObject)target).eInverseRemove(this, ChannelTypesPackage.TARGET__BINDINGS, Target.class, msgs);
                return basicSetTarget((Target)otherEnd, msgs);
            case ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION:
                if (presentation != null)
                    msgs = ((InternalEObject)presentation).eInverseRemove(this, ChannelTypesPackage.PRESENTATION__BINDINGS, Presentation.class, msgs);
                return basicSetPresentation((Presentation)otherEnd, msgs);
            case ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION:
                if (implementation != null)
                    msgs = ((InternalEObject)implementation).eInverseRemove(this, ChannelTypesPackage.IMPLEMENTATION__BINDINGS, Implementation.class, msgs);
                return basicSetImplementation((Implementation)otherEnd, msgs);
            case ChannelTypesPackage.CHANNEL_TYPE__DESTINATIONS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getDestinations()).basicAdd(otherEnd, msgs);
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
            case ChannelTypesPackage.CHANNEL_TYPE__TARGET:
                return basicSetTarget(null, msgs);
            case ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION:
                return basicSetPresentation(null, msgs);
            case ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION:
                return basicSetImplementation(null, msgs);
            case ChannelTypesPackage.CHANNEL_TYPE__ATTRIBUTES:
                return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
            case ChannelTypesPackage.CHANNEL_TYPE__DESTINATIONS:
                return ((InternalEList<?>)getDestinations()).basicRemove(otherEnd, msgs);
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
            case ChannelTypesPackage.CHANNEL_TYPE__TARGET:
                if (resolve) return getTarget();
                return basicGetTarget();
            case ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION:
                if (resolve) return getPresentation();
                return basicGetPresentation();
            case ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION:
                if (resolve) return getImplementation();
                return basicGetImplementation();
            case ChannelTypesPackage.CHANNEL_TYPE__ATTRIBUTES:
                return getAttributes();
            case ChannelTypesPackage.CHANNEL_TYPE__RUNTIME_VERSION:
                return getRuntimeVersion();
            case ChannelTypesPackage.CHANNEL_TYPE__DESTINATIONS:
                return getDestinations();
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
            case ChannelTypesPackage.CHANNEL_TYPE__TARGET:
                setTarget((Target)newValue);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION:
                setPresentation((Presentation)newValue);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION:
                setImplementation((Implementation)newValue);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__ATTRIBUTES:
                getAttributes().clear();
                getAttributes().addAll((Collection<? extends Attribute>)newValue);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__RUNTIME_VERSION:
                setRuntimeVersion((String)newValue);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__DESTINATIONS:
                getDestinations().clear();
                getDestinations().addAll((Collection<? extends ChannelDestination>)newValue);
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
            case ChannelTypesPackage.CHANNEL_TYPE__TARGET:
                setTarget((Target)null);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION:
                setPresentation((Presentation)null);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION:
                setImplementation((Implementation)null);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__ATTRIBUTES:
                getAttributes().clear();
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__RUNTIME_VERSION:
                setRuntimeVersion(RUNTIME_VERSION_EDEFAULT);
                return;
            case ChannelTypesPackage.CHANNEL_TYPE__DESTINATIONS:
                getDestinations().clear();
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
            case ChannelTypesPackage.CHANNEL_TYPE__TARGET:
                return target != null;
            case ChannelTypesPackage.CHANNEL_TYPE__PRESENTATION:
                return presentation != null;
            case ChannelTypesPackage.CHANNEL_TYPE__IMPLEMENTATION:
                return implementation != null;
            case ChannelTypesPackage.CHANNEL_TYPE__ATTRIBUTES:
                return attributes != null && !attributes.isEmpty();
            case ChannelTypesPackage.CHANNEL_TYPE__RUNTIME_VERSION:
                return RUNTIME_VERSION_EDEFAULT == null ? runtimeVersion != null : !RUNTIME_VERSION_EDEFAULT.equals(runtimeVersion);
            case ChannelTypesPackage.CHANNEL_TYPE__DESTINATIONS:
                return destinations != null && !destinations.isEmpty();
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
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (runtimeVersion: ");
        result.append(runtimeVersion);
        result.append(')');
        return result.toString();
    }

    

} // ChannelTypeImpl
