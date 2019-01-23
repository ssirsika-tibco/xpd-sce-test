/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.xpd.xpdl2.Partner;
import com.tibco.xpd.xpdl2.Service;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Web Service Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceOperationImpl#getOtherAttributes <em>Other Attributes</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceOperationImpl#getPartner <em>Partner</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceOperationImpl#getService <em>Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.WebServiceOperationImpl#getOperationName <em>Operation Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WebServiceOperationImpl extends EObjectImpl implements
        WebServiceOperation {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getOtherAttributes() <em>Other Attributes</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOtherAttributes()
     * @generated
     * @ordered
     */
    protected FeatureMap otherAttributes;

    /**
     * The cached value of the '{@link #getPartner() <em>Partner</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPartner()
     * @generated
     * @ordered
     */
    protected Partner partner;

    /**
     * The cached value of the '{@link #getService() <em>Service</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getService()
     * @generated
     * @ordered
     */
    protected Service service;

    /**
     * The default value of the '{@link #getOperationName() <em>Operation Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOperationName()
     * @generated
     * @ordered
     */
    protected static final String OPERATION_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOperationName() <em>Operation Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOperationName()
     * @generated
     * @ordered
     */
    protected String operationName = OPERATION_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WebServiceOperationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.WEB_SERVICE_OPERATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOtherAttributes() {
        if (otherAttributes == null) {
            otherAttributes =
                    new BasicFeatureMap(
                            this,
                            Xpdl2Package.WEB_SERVICE_OPERATION__OTHER_ATTRIBUTES);
        }
        return otherAttributes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Partner getPartner() {
        return partner;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPartner(Partner newPartner,
            NotificationChain msgs) {
        Partner oldPartner = partner;
        partner = newPartner;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER,
                            oldPartner, newPartner);
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
    public void setPartner(Partner newPartner) {
        if (newPartner != partner) {
            NotificationChain msgs = null;
            if (partner != null)
                msgs =
                        ((InternalEObject) partner)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER,
                                        null,
                                        msgs);
            if (newPartner != null)
                msgs =
                        ((InternalEObject) newPartner)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER,
                                        null,
                                        msgs);
            msgs = basicSetPartner(newPartner, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER, newPartner,
                    newPartner));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Service getService() {
        return service;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetService(Service newService,
            NotificationChain msgs) {
        Service oldService = service;
        service = newService;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE,
                            oldService, newService);
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
    public void setService(Service newService) {
        if (newService != service) {
            NotificationChain msgs = null;
            if (service != null)
                msgs =
                        ((InternalEObject) service)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE,
                                        null,
                                        msgs);
            if (newService != null)
                msgs =
                        ((InternalEObject) newService)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE,
                                        null,
                                        msgs);
            msgs = basicSetService(newService, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE, newService,
                    newService));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOperationName(String newOperationName) {
        String oldOperationName = operationName;
        operationName = newOperationName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.WEB_SERVICE_OPERATION__OPERATION_NAME,
                    oldOperationName, operationName));
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
        case Xpdl2Package.WEB_SERVICE_OPERATION__OTHER_ATTRIBUTES:
            return ((InternalEList<?>) getOtherAttributes())
                    .basicRemove(otherEnd, msgs);
        case Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER:
            return basicSetPartner(null, msgs);
        case Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE:
            return basicSetService(null, msgs);
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
        case Xpdl2Package.WEB_SERVICE_OPERATION__OTHER_ATTRIBUTES:
            if (coreType)
                return getOtherAttributes();
            return ((FeatureMap.Internal) getOtherAttributes()).getWrapper();
        case Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER:
            return getPartner();
        case Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE:
            return getService();
        case Xpdl2Package.WEB_SERVICE_OPERATION__OPERATION_NAME:
            return getOperationName();
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
        case Xpdl2Package.WEB_SERVICE_OPERATION__OTHER_ATTRIBUTES:
            ((FeatureMap.Internal) getOtherAttributes()).set(newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER:
            setPartner((Partner) newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE:
            setService((Service) newValue);
            return;
        case Xpdl2Package.WEB_SERVICE_OPERATION__OPERATION_NAME:
            setOperationName((String) newValue);
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
        case Xpdl2Package.WEB_SERVICE_OPERATION__OTHER_ATTRIBUTES:
            getOtherAttributes().clear();
            return;
        case Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER:
            setPartner((Partner) null);
            return;
        case Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE:
            setService((Service) null);
            return;
        case Xpdl2Package.WEB_SERVICE_OPERATION__OPERATION_NAME:
            setOperationName(OPERATION_NAME_EDEFAULT);
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
        case Xpdl2Package.WEB_SERVICE_OPERATION__OTHER_ATTRIBUTES:
            return otherAttributes != null && !otherAttributes.isEmpty();
        case Xpdl2Package.WEB_SERVICE_OPERATION__PARTNER:
            return partner != null;
        case Xpdl2Package.WEB_SERVICE_OPERATION__SERVICE:
            return service != null;
        case Xpdl2Package.WEB_SERVICE_OPERATION__OPERATION_NAME:
            return OPERATION_NAME_EDEFAULT == null ? operationName != null
                    : !OPERATION_NAME_EDEFAULT.equals(operationName);
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
        result.append(" (otherAttributes: "); //$NON-NLS-1$
        result.append(otherAttributes);
        result.append(", operationName: "); //$NON-NLS-1$
        result.append(operationName);
        result.append(')');
        return result.toString();
    }

} //WebServiceOperationImpl
