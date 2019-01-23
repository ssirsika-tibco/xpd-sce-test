/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl;

import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.ClassType;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EAIJava;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.EaijavaPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EAI Java</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EAIJavaImpl#getPojo <em>Pojo</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EAIJavaImpl#getFactory <em>Factory</em>}</li>
 *   <li>{@link com.tibco.xpd.implementer.nativeservices.java.javaservice.eaijava.impl.EAIJavaImpl#getProject <em>Project</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EAIJavaImpl extends EObjectImpl implements EAIJava {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getPojo() <em>Pojo</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPojo()
     * @generated
     * @ordered
     */
    protected ClassType pojo = null;

    /**
     * The cached value of the '{@link #getFactory() <em>Factory</em>}' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getFactory()
     * @generated
     * @ordered
     */
	protected ClassType factory = null;

    /**
     * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProject()
     * @generated
     * @ordered
     */
    protected static final String PROJECT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProject()
     * @generated
     * @ordered
     */
    protected String project = PROJECT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EAIJavaImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EClass eStaticClass() {
        return EaijavaPackage.Literals.EAI_JAVA;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ClassType getPojo() {
        return pojo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPojo(ClassType newPojo, NotificationChain msgs) {
        ClassType oldPojo = pojo;
        pojo = newPojo;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EaijavaPackage.EAI_JAVA__POJO, oldPojo, newPojo);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPojo(ClassType newPojo) {
        if (newPojo != pojo) {
            NotificationChain msgs = null;
            if (pojo != null)
                msgs = ((InternalEObject)pojo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.EAI_JAVA__POJO, null, msgs);
            if (newPojo != null)
                msgs = ((InternalEObject)newPojo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.EAI_JAVA__POJO, null, msgs);
            msgs = basicSetPojo(newPojo, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EaijavaPackage.EAI_JAVA__POJO, newPojo, newPojo));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ClassType getFactory() {
        return factory;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain basicSetFactory(ClassType newFactory, NotificationChain msgs) {
        ClassType oldFactory = factory;
        factory = newFactory;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EaijavaPackage.EAI_JAVA__FACTORY, oldFactory, newFactory);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setFactory(ClassType newFactory) {
        if (newFactory != factory) {
            NotificationChain msgs = null;
            if (factory != null)
                msgs = ((InternalEObject)factory).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.EAI_JAVA__FACTORY, null, msgs);
            if (newFactory != null)
                msgs = ((InternalEObject)newFactory).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EaijavaPackage.EAI_JAVA__FACTORY, null, msgs);
            msgs = basicSetFactory(newFactory, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EaijavaPackage.EAI_JAVA__FACTORY, newFactory, newFactory));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getProject() {
        return project;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProject(String newProject) {
        String oldProject = project;
        project = newProject;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EaijavaPackage.EAI_JAVA__PROJECT, oldProject, project));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case EaijavaPackage.EAI_JAVA__POJO:
                return basicSetPojo(null, msgs);
            case EaijavaPackage.EAI_JAVA__FACTORY:
                return basicSetFactory(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case EaijavaPackage.EAI_JAVA__POJO:
                return getPojo();
            case EaijavaPackage.EAI_JAVA__FACTORY:
                return getFactory();
            case EaijavaPackage.EAI_JAVA__PROJECT:
                return getProject();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case EaijavaPackage.EAI_JAVA__POJO:
                setPojo((ClassType)newValue);
                return;
            case EaijavaPackage.EAI_JAVA__FACTORY:
                setFactory((ClassType)newValue);
                return;
            case EaijavaPackage.EAI_JAVA__PROJECT:
                setProject((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eUnset(int featureID) {
        switch (featureID) {
            case EaijavaPackage.EAI_JAVA__POJO:
                setPojo((ClassType)null);
                return;
            case EaijavaPackage.EAI_JAVA__FACTORY:
                setFactory((ClassType)null);
                return;
            case EaijavaPackage.EAI_JAVA__PROJECT:
                setProject(PROJECT_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case EaijavaPackage.EAI_JAVA__POJO:
                return pojo != null;
            case EaijavaPackage.EAI_JAVA__FACTORY:
                return factory != null;
            case EaijavaPackage.EAI_JAVA__PROJECT:
                return PROJECT_EDEFAULT == null ? project != null : !PROJECT_EDEFAULT.equals(project);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (project: "); //$NON-NLS-1$
        result.append(project);
        result.append(')');
        return result.toString();
    }

} //EAIJavaImpl