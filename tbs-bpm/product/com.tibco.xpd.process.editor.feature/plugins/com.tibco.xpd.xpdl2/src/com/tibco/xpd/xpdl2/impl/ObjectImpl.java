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

import com.tibco.xpd.xpdl2.Category;
import com.tibco.xpd.xpdl2.Documentation;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ObjectImpl#getCategories <em>Categories</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ObjectImpl#getDocumentation <em>Documentation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObjectImpl extends NamedElementImpl implements com.tibco.xpd.xpdl2.Object {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getCategories() <em>Categories</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getCategories()
     * @generated
     * @ordered
     */
    protected Category categories;

    /**
     * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDocumentation()
     * @generated
     * @ordered
     */
    protected Documentation documentation;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ObjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.OBJECT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Category getCategories() {
        return categories;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetCategories(Category newCategories, NotificationChain msgs) {
        Category oldCategories = categories;
        categories = newCategories;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.OBJECT__CATEGORIES, oldCategories, newCategories);
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
    public void setCategories(Category newCategories) {
        if (newCategories != categories) {
            NotificationChain msgs = null;
            if (categories != null)
                msgs = ((InternalEObject) categories)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.OBJECT__CATEGORIES, null, msgs);
            if (newCategories != null)
                msgs = ((InternalEObject) newCategories)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.OBJECT__CATEGORIES, null, msgs);
            msgs = basicSetCategories(newCategories, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.OBJECT__CATEGORIES, newCategories,
                    newCategories));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Documentation getDocumentation() {
        return documentation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDocumentation(Documentation newDocumentation, NotificationChain msgs) {
        Documentation oldDocumentation = documentation;
        documentation = newDocumentation;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.OBJECT__DOCUMENTATION, oldDocumentation, newDocumentation);
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
    public void setDocumentation(Documentation newDocumentation) {
        if (newDocumentation != documentation) {
            NotificationChain msgs = null;
            if (documentation != null)
                msgs = ((InternalEObject) documentation)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.OBJECT__DOCUMENTATION, null, msgs);
            if (newDocumentation != null)
                msgs = ((InternalEObject) newDocumentation)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.OBJECT__DOCUMENTATION, null, msgs);
            msgs = basicSetDocumentation(newDocumentation, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.OBJECT__DOCUMENTATION, newDocumentation,
                    newDocumentation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.OBJECT__CATEGORIES:
            return basicSetCategories(null, msgs);
        case Xpdl2Package.OBJECT__DOCUMENTATION:
            return basicSetDocumentation(null, msgs);
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
        case Xpdl2Package.OBJECT__CATEGORIES:
            return getCategories();
        case Xpdl2Package.OBJECT__DOCUMENTATION:
            return getDocumentation();
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
        case Xpdl2Package.OBJECT__CATEGORIES:
            setCategories((Category) newValue);
            return;
        case Xpdl2Package.OBJECT__DOCUMENTATION:
            setDocumentation((Documentation) newValue);
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
        case Xpdl2Package.OBJECT__CATEGORIES:
            setCategories((Category) null);
            return;
        case Xpdl2Package.OBJECT__DOCUMENTATION:
            setDocumentation((Documentation) null);
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
        case Xpdl2Package.OBJECT__CATEGORIES:
            return categories != null;
        case Xpdl2Package.OBJECT__DOCUMENTATION:
            return documentation != null;
        }
        return super.eIsSet(featureID);
    }

} //ObjectImpl
