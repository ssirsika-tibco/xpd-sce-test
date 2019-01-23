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

import com.tibco.xpd.xpdl2.EjbApplication;
import com.tibco.xpd.xpdl2.HomeClass;
import com.tibco.xpd.xpdl2.JndiName;
import com.tibco.xpd.xpdl2.Method;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ejb Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EjbApplicationImpl#getJndiName <em>Jndi Name</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EjbApplicationImpl#getHomeClass <em>Home Class</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.EjbApplicationImpl#getMethod <em>Method</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EjbApplicationImpl extends EObjectImpl implements EjbApplication {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getJndiName() <em>Jndi Name</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJndiName()
     * @generated
     * @ordered
     */
    protected JndiName jndiName;

    /**
     * The cached value of the '{@link #getHomeClass() <em>Home Class</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHomeClass()
     * @generated
     * @ordered
     */
    protected HomeClass homeClass;

    /**
     * The cached value of the '{@link #getMethod() <em>Method</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMethod()
     * @generated
     * @ordered
     */
    protected Method method;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EjbApplicationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.EJB_APPLICATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public JndiName getJndiName() {
        return jndiName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetJndiName(JndiName newJndiName,
            NotificationChain msgs) {
        JndiName oldJndiName = jndiName;
        jndiName = newJndiName;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.EJB_APPLICATION__JNDI_NAME,
                            oldJndiName, newJndiName);
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
    public void setJndiName(JndiName newJndiName) {
        if (newJndiName != jndiName) {
            NotificationChain msgs = null;
            if (jndiName != null)
                msgs =
                        ((InternalEObject) jndiName)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.EJB_APPLICATION__JNDI_NAME,
                                        null,
                                        msgs);
            if (newJndiName != null)
                msgs =
                        ((InternalEObject) newJndiName)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.EJB_APPLICATION__JNDI_NAME,
                                        null,
                                        msgs);
            msgs = basicSetJndiName(newJndiName, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.EJB_APPLICATION__JNDI_NAME, newJndiName,
                    newJndiName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HomeClass getHomeClass() {
        return homeClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetHomeClass(HomeClass newHomeClass,
            NotificationChain msgs) {
        HomeClass oldHomeClass = homeClass;
        homeClass = newHomeClass;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.EJB_APPLICATION__HOME_CLASS,
                            oldHomeClass, newHomeClass);
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
    public void setHomeClass(HomeClass newHomeClass) {
        if (newHomeClass != homeClass) {
            NotificationChain msgs = null;
            if (homeClass != null)
                msgs =
                        ((InternalEObject) homeClass)
                                .eInverseRemove(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.EJB_APPLICATION__HOME_CLASS,
                                        null,
                                        msgs);
            if (newHomeClass != null)
                msgs =
                        ((InternalEObject) newHomeClass)
                                .eInverseAdd(this,
                                        EOPPOSITE_FEATURE_BASE
                                                - Xpdl2Package.EJB_APPLICATION__HOME_CLASS,
                                        null,
                                        msgs);
            msgs = basicSetHomeClass(newHomeClass, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.EJB_APPLICATION__HOME_CLASS, newHomeClass,
                    newHomeClass));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Method getMethod() {
        return method;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetMethod(Method newMethod,
            NotificationChain msgs) {
        Method oldMethod = method;
        method = newMethod;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET,
                            Xpdl2Package.EJB_APPLICATION__METHOD, oldMethod,
                            newMethod);
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
    public void setMethod(Method newMethod) {
        if (newMethod != method) {
            NotificationChain msgs = null;
            if (method != null)
                msgs =
                        ((InternalEObject) method).eInverseRemove(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.EJB_APPLICATION__METHOD,
                                null,
                                msgs);
            if (newMethod != null)
                msgs =
                        ((InternalEObject) newMethod).eInverseAdd(this,
                                EOPPOSITE_FEATURE_BASE
                                        - Xpdl2Package.EJB_APPLICATION__METHOD,
                                null,
                                msgs);
            msgs = basicSetMethod(newMethod, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.EJB_APPLICATION__METHOD, newMethod, newMethod));
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
        case Xpdl2Package.EJB_APPLICATION__JNDI_NAME:
            return basicSetJndiName(null, msgs);
        case Xpdl2Package.EJB_APPLICATION__HOME_CLASS:
            return basicSetHomeClass(null, msgs);
        case Xpdl2Package.EJB_APPLICATION__METHOD:
            return basicSetMethod(null, msgs);
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
        case Xpdl2Package.EJB_APPLICATION__JNDI_NAME:
            return getJndiName();
        case Xpdl2Package.EJB_APPLICATION__HOME_CLASS:
            return getHomeClass();
        case Xpdl2Package.EJB_APPLICATION__METHOD:
            return getMethod();
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
        case Xpdl2Package.EJB_APPLICATION__JNDI_NAME:
            setJndiName((JndiName) newValue);
            return;
        case Xpdl2Package.EJB_APPLICATION__HOME_CLASS:
            setHomeClass((HomeClass) newValue);
            return;
        case Xpdl2Package.EJB_APPLICATION__METHOD:
            setMethod((Method) newValue);
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
        case Xpdl2Package.EJB_APPLICATION__JNDI_NAME:
            setJndiName((JndiName) null);
            return;
        case Xpdl2Package.EJB_APPLICATION__HOME_CLASS:
            setHomeClass((HomeClass) null);
            return;
        case Xpdl2Package.EJB_APPLICATION__METHOD:
            setMethod((Method) null);
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
        case Xpdl2Package.EJB_APPLICATION__JNDI_NAME:
            return jndiName != null;
        case Xpdl2Package.EJB_APPLICATION__HOME_CLASS:
            return homeClass != null;
        case Xpdl2Package.EJB_APPLICATION__METHOD:
            return method != null;
        }
        return super.eIsSet(featureID);
    }

} //EjbApplicationImpl
