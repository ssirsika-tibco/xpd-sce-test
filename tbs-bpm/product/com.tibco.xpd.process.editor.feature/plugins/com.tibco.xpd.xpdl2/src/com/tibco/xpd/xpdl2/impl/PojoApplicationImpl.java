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

import com.tibco.xpd.xpdl2.Method;
import com.tibco.xpd.xpdl2.PojoApplication;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pojo Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PojoApplicationImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.PojoApplicationImpl#getMethod <em>Method</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PojoApplicationImpl extends EObjectImpl implements PojoApplication {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getClass_() <em>Class</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getClass_()
     * @generated
     * @ordered
     */
    protected com.tibco.xpd.xpdl2.Class class_;

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
    protected PojoApplicationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.POJO_APPLICATION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public com.tibco.xpd.xpdl2.Class getClass_() {
        return class_;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetClass(com.tibco.xpd.xpdl2.Class newClass, NotificationChain msgs) {
        com.tibco.xpd.xpdl2.Class oldClass = class_;
        class_ = newClass;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.POJO_APPLICATION__CLASS, oldClass, newClass);
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
    public void setClass(com.tibco.xpd.xpdl2.Class newClass) {
        if (newClass != class_) {
            NotificationChain msgs = null;
            if (class_ != null)
                msgs = ((InternalEObject) class_).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.POJO_APPLICATION__CLASS,
                        null,
                        msgs);
            if (newClass != null)
                msgs = ((InternalEObject) newClass)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.POJO_APPLICATION__CLASS, null, msgs);
            msgs = basicSetClass(newClass, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POJO_APPLICATION__CLASS, newClass,
                    newClass));
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
    public NotificationChain basicSetMethod(Method newMethod, NotificationChain msgs) {
        Method oldMethod = method;
        method = newMethod;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.POJO_APPLICATION__METHOD, oldMethod, newMethod);
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
                msgs = ((InternalEObject) method).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.POJO_APPLICATION__METHOD,
                        null,
                        msgs);
            if (newMethod != null)
                msgs = ((InternalEObject) newMethod)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.POJO_APPLICATION__METHOD, null, msgs);
            msgs = basicSetMethod(newMethod, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.POJO_APPLICATION__METHOD, newMethod,
                    newMethod));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.POJO_APPLICATION__CLASS:
            return basicSetClass(null, msgs);
        case Xpdl2Package.POJO_APPLICATION__METHOD:
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
        case Xpdl2Package.POJO_APPLICATION__CLASS:
            return getClass_();
        case Xpdl2Package.POJO_APPLICATION__METHOD:
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
        case Xpdl2Package.POJO_APPLICATION__CLASS:
            setClass((com.tibco.xpd.xpdl2.Class) newValue);
            return;
        case Xpdl2Package.POJO_APPLICATION__METHOD:
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
        case Xpdl2Package.POJO_APPLICATION__CLASS:
            setClass((com.tibco.xpd.xpdl2.Class) null);
            return;
        case Xpdl2Package.POJO_APPLICATION__METHOD:
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
        case Xpdl2Package.POJO_APPLICATION__CLASS:
            return class_ != null;
        case Xpdl2Package.POJO_APPLICATION__METHOD:
            return method != null;
        }
        return super.eIsSet(featureID);
    }

} //PojoApplicationImpl
