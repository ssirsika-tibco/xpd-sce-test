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

import com.tibco.xpd.xpdl2.Script;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Script</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ScriptImpl#getAny <em>Any</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ScriptImpl#getGrammar <em>Grammar</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ScriptImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ScriptImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScriptImpl extends EObjectImpl implements Script {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAny()
     * @generated
     * @ordered
     */
    protected FeatureMap any;

    /**
     * The default value of the '{@link #getGrammar() <em>Grammar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGrammar()
     * @generated
     * @ordered
     */
    protected static final String GRAMMAR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getGrammar() <em>Grammar</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGrammar()
     * @generated
     * @ordered
     */
    protected String grammar = GRAMMAR_EDEFAULT;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final String TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getType()
     * @generated
     * @ordered
     */
    protected String type = TYPE_EDEFAULT;

    /**
     * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected static final String VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getVersion()
     * @generated
     * @ordered
     */
    protected String version = VERSION_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ScriptImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.SCRIPT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getAny() {
        if (any == null) {
            any = new BasicFeatureMap(this, Xpdl2Package.SCRIPT__ANY);
        }
        return any;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getGrammar() {
        return grammar;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGrammar(String newGrammar) {
        String oldGrammar = grammar;
        grammar = newGrammar;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SCRIPT__GRAMMAR, oldGrammar, grammar));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getType() {
        return type;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setType(String newType) {
        String oldType = type;
        type = newType;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SCRIPT__TYPE, oldType, type));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setVersion(String newVersion) {
        String oldVersion = version;
        version = newVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.SCRIPT__VERSION, oldVersion, version));
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
        case Xpdl2Package.SCRIPT__ANY:
            return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.SCRIPT__ANY:
            if (coreType)
                return getAny();
            return ((FeatureMap.Internal) getAny()).getWrapper();
        case Xpdl2Package.SCRIPT__GRAMMAR:
            return getGrammar();
        case Xpdl2Package.SCRIPT__TYPE:
            return getType();
        case Xpdl2Package.SCRIPT__VERSION:
            return getVersion();
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
        case Xpdl2Package.SCRIPT__ANY:
            ((FeatureMap.Internal) getAny()).set(newValue);
            return;
        case Xpdl2Package.SCRIPT__GRAMMAR:
            setGrammar((String) newValue);
            return;
        case Xpdl2Package.SCRIPT__TYPE:
            setType((String) newValue);
            return;
        case Xpdl2Package.SCRIPT__VERSION:
            setVersion((String) newValue);
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
        case Xpdl2Package.SCRIPT__ANY:
            getAny().clear();
            return;
        case Xpdl2Package.SCRIPT__GRAMMAR:
            setGrammar(GRAMMAR_EDEFAULT);
            return;
        case Xpdl2Package.SCRIPT__TYPE:
            setType(TYPE_EDEFAULT);
            return;
        case Xpdl2Package.SCRIPT__VERSION:
            setVersion(VERSION_EDEFAULT);
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
        case Xpdl2Package.SCRIPT__ANY:
            return any != null && !any.isEmpty();
        case Xpdl2Package.SCRIPT__GRAMMAR:
            return GRAMMAR_EDEFAULT == null ? grammar != null
                    : !GRAMMAR_EDEFAULT.equals(grammar);
        case Xpdl2Package.SCRIPT__TYPE:
            return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT
                    .equals(type);
        case Xpdl2Package.SCRIPT__VERSION:
            return VERSION_EDEFAULT == null ? version != null
                    : !VERSION_EDEFAULT.equals(version);
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
        result.append(" (any: "); //$NON-NLS-1$
        result.append(any);
        result.append(", grammar: "); //$NON-NLS-1$
        result.append(grammar);
        result.append(", type: "); //$NON-NLS-1$
        result.append(type);
        result.append(", version: "); //$NON-NLS-1$
        result.append(version);
        result.append(')');
        return result.toString();
    }

} //ScriptImpl
