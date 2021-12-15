/**
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.resources.projectconfig.impl;

import com.tibco.xpd.resources.projectconfig.CustomProperties;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.xpd.resources.projectconfig.Destination;
import com.tibco.xpd.resources.projectconfig.Destinations;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;
import com.tibco.xpd.resources.projectconfig.ProjectStatus;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Project Details</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl#getGlobalDestinations <em>Global Destinations</em>}</li>
 *   <li>{@link com.tibco.xpd.resources.projectconfig.impl.ProjectDetailsImpl#getCustomProperties <em>Custom Properties</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectDetailsImpl extends EObjectImpl implements ProjectDetails {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.";

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = "";

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected String id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected static final String VERSION_EDEFAULT = "1.0.0.qualifier";

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected String version = VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStatus()
     * @generated
     * @ordered
     */
    protected static final ProjectStatus STATUS_EDEFAULT = ProjectStatus.UNDER_REVISION;

    /**
     * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getStatus()
     * @generated
     * @ordered
     */
    protected ProjectStatus status = STATUS_EDEFAULT;

    /**
     * The cached value of the '{@link #getGlobalDestinations() <em>Global Destinations</em>}' containment reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getGlobalDestinations()
     * @generated
     * @ordered
     */
    protected Destinations globalDestinations;

    /**
     * The cached value of the '{@link #getCustomProperties() <em>Custom Properties</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCustomProperties()
     * @generated
     * @ordered
     */
    protected CustomProperties customProperties;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ProjectDetailsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ProjectConfigPackage.Literals.PROJECT_DETAILS;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setId(String newId) {
        String oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ProjectConfigPackage.PROJECT_DETAILS__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(String newVersion) {
        String oldVersion = version;
        version = newVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ProjectConfigPackage.PROJECT_DETAILS__VERSION,
                    oldVersion, version));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProjectStatus getStatus() {
        return status;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setStatus(ProjectStatus newStatus) {
        ProjectStatus oldStatus = status;
        status = newStatus == null ? STATUS_EDEFAULT : newStatus;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ProjectConfigPackage.PROJECT_DETAILS__STATUS,
                    oldStatus, status));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Destinations getGlobalDestinations() {
        return globalDestinations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGlobalDestinations(Destinations newGlobalDestinations, NotificationChain msgs) {
        Destinations oldGlobalDestinations = globalDestinations;
        globalDestinations = newGlobalDestinations;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS, oldGlobalDestinations,
                    newGlobalDestinations);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setGlobalDestinations(Destinations newGlobalDestinations) {
        if (newGlobalDestinations != globalDestinations) {
            NotificationChain msgs = null;
            if (globalDestinations != null)
                msgs = ((InternalEObject) globalDestinations).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS,
                        null,
                        msgs);
            if (newGlobalDestinations != null)
                msgs = ((InternalEObject) newGlobalDestinations).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS,
                        null,
                        msgs);
            msgs = basicSetGlobalDestinations(newGlobalDestinations, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS, newGlobalDestinations,
                    newGlobalDestinations));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CustomProperties getCustomProperties() {
        return customProperties;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCustomProperties(CustomProperties newCustomProperties, NotificationChain msgs) {
        CustomProperties oldCustomProperties = customProperties;
        customProperties = newCustomProperties;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES, oldCustomProperties, newCustomProperties);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setCustomProperties(CustomProperties newCustomProperties) {
        if (newCustomProperties != customProperties) {
            NotificationChain msgs = null;
            if (customProperties != null)
                msgs = ((InternalEObject) customProperties).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES,
                        null,
                        msgs);
            if (newCustomProperties != null)
                msgs = ((InternalEObject) newCustomProperties).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES,
                        null,
                        msgs);
            msgs = basicSetCustomProperties(newCustomProperties, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES, newCustomProperties, newCustomProperties));
    }

    /**
     * <!-- begin-user-doc --> Check if global destination id is enabled for
     * project.
     * 
     * @since 3.3 <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isGlobalDestinationEnabled(String globalDestinationId) {
        return getEnabledGlobalDestinationIds().contains(globalDestinationId);
    }

    /**
     * <!-- begin-user-doc --> Gets all global destination identifiers for the
     * project.
     * 
     * @since 3.3<!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public EList<String> getEnabledGlobalDestinationIds() {
        EList<String> destinations = new BasicEList<String>();
        if (getGlobalDestinations() != null && getGlobalDestinations().getDestination() != null) {
            for (Destination d : getGlobalDestinations().getDestination()) {
                destinations.add(d.getType());
            }
        }
        return destinations;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS:
            return basicSetGlobalDestinations(null, msgs);
        case ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES:
            return basicSetCustomProperties(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_DETAILS__ID:
            return getId();
        case ProjectConfigPackage.PROJECT_DETAILS__VERSION:
            return getVersion();
        case ProjectConfigPackage.PROJECT_DETAILS__STATUS:
            return getStatus();
        case ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS:
            return getGlobalDestinations();
        case ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES:
            return getCustomProperties();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_DETAILS__ID:
            setId((String) newValue);
            return;
        case ProjectConfigPackage.PROJECT_DETAILS__VERSION:
            setVersion((String) newValue);
            return;
        case ProjectConfigPackage.PROJECT_DETAILS__STATUS:
            setStatus((ProjectStatus) newValue);
            return;
        case ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS:
            setGlobalDestinations((Destinations) newValue);
            return;
        case ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES:
            setCustomProperties((CustomProperties) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_DETAILS__ID:
            setId(ID_EDEFAULT);
            return;
        case ProjectConfigPackage.PROJECT_DETAILS__VERSION:
            setVersion(VERSION_EDEFAULT);
            return;
        case ProjectConfigPackage.PROJECT_DETAILS__STATUS:
            setStatus(STATUS_EDEFAULT);
            return;
        case ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS:
            setGlobalDestinations((Destinations) null);
            return;
        case ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES:
            setCustomProperties((CustomProperties) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case ProjectConfigPackage.PROJECT_DETAILS__ID:
            return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
        case ProjectConfigPackage.PROJECT_DETAILS__VERSION:
            return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
        case ProjectConfigPackage.PROJECT_DETAILS__STATUS:
            return status != STATUS_EDEFAULT;
        case ProjectConfigPackage.PROJECT_DETAILS__GLOBAL_DESTINATIONS:
            return globalDestinations != null;
        case ProjectConfigPackage.PROJECT_DETAILS__CUSTOM_PROPERTIES:
            return customProperties != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (id: ");
        result.append(id);
        result.append(", version: ");
        result.append(version);
        result.append(", status: ");
        result.append(status);
        result.append(')');
        return result.toString();
    }

} // ProjectDetailsImpl
