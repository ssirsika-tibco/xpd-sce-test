/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2.impl;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ExpressionImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ExpressionImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ExpressionImpl#getAny <em>Any</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ExpressionImpl#getScriptGrammar <em>Script Grammar</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExpressionImpl extends EObjectImpl implements Expression {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getMixed()
     * @generated
     * @ordered
     */
    protected FeatureMap mixed;

    /**
     * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAny()
     * @generated
     * @ordered
     */
    protected FeatureMap any;

    /**
     * The default value of the '{@link #getScriptGrammar() <em>Script Grammar</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getScriptGrammar()
     * @generated
     * @ordered
     */
    protected static final String SCRIPT_GRAMMAR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getScriptGrammar() <em>Script Grammar</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getScriptGrammar()
     * @generated
     * @ordered
     */
    protected String scriptGrammar = SCRIPT_GRAMMAR_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ExpressionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.EXPRESSION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getMixed() {
        if (mixed == null) {
            mixed = new BasicFeatureMap(this, Xpdl2Package.EXPRESSION__MIXED);
        }
        return mixed;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getGroup() {
        return (FeatureMap) getMixed()
                .<FeatureMap.Entry> list(Xpdl2Package.Literals.EXPRESSION__GROUP);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getAny() {
        if (any == null) {
            any = new BasicFeatureMap(this, Xpdl2Package.EXPRESSION__ANY);
        }
        return any;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getScriptGrammar() {
        return scriptGrammar;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setScriptGrammar(String newScriptGrammar) {
        String oldScriptGrammar = scriptGrammar;
        scriptGrammar = newScriptGrammar;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.EXPRESSION__SCRIPT_GRAMMAR, oldScriptGrammar,
                    scriptGrammar));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getText() {
        List vals =
                (List) getMixed()
                        .get(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                false);
        if (vals.size() == 0) {
            return ""; //$NON-NLS-1$
        } else if (vals.size() == 1) {
            return (String) vals.get(0);
        } else {
            StringBuffer sb = new StringBuffer();
            for (Iterator iter = vals.iterator(); iter.hasNext();) {
                String str = (String) iter.next();
                sb.append(str);
            }
            return sb.toString();
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
            int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.EXPRESSION__MIXED:
            return ((InternalEList<?>) getMixed()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.EXPRESSION__GROUP:
            return ((InternalEList<?>) getGroup()).basicRemove(otherEnd, msgs);
        case Xpdl2Package.EXPRESSION__ANY:
            return ((InternalEList<?>) getAny()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.EXPRESSION__MIXED:
            if (coreType)
                return getMixed();
            return ((FeatureMap.Internal) getMixed()).getWrapper();
        case Xpdl2Package.EXPRESSION__GROUP:
            if (coreType)
                return getGroup();
            return ((FeatureMap.Internal) getGroup()).getWrapper();
        case Xpdl2Package.EXPRESSION__ANY:
            if (coreType)
                return getAny();
            return ((FeatureMap.Internal) getAny()).getWrapper();
        case Xpdl2Package.EXPRESSION__SCRIPT_GRAMMAR:
            return getScriptGrammar();
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
        case Xpdl2Package.EXPRESSION__MIXED:
            ((FeatureMap.Internal) getMixed()).set(newValue);
            return;
        case Xpdl2Package.EXPRESSION__GROUP:
            ((FeatureMap.Internal) getGroup()).set(newValue);
            return;
        case Xpdl2Package.EXPRESSION__ANY:
            ((FeatureMap.Internal) getAny()).set(newValue);
            return;
        case Xpdl2Package.EXPRESSION__SCRIPT_GRAMMAR:
            setScriptGrammar((String) newValue);
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
        case Xpdl2Package.EXPRESSION__MIXED:
            getMixed().clear();
            return;
        case Xpdl2Package.EXPRESSION__GROUP:
            getGroup().clear();
            return;
        case Xpdl2Package.EXPRESSION__ANY:
            getAny().clear();
            return;
        case Xpdl2Package.EXPRESSION__SCRIPT_GRAMMAR:
            setScriptGrammar(SCRIPT_GRAMMAR_EDEFAULT);
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
        case Xpdl2Package.EXPRESSION__MIXED:
            return mixed != null && !mixed.isEmpty();
        case Xpdl2Package.EXPRESSION__GROUP:
            return !getGroup().isEmpty();
        case Xpdl2Package.EXPRESSION__ANY:
            return any != null && !any.isEmpty();
        case Xpdl2Package.EXPRESSION__SCRIPT_GRAMMAR:
            return SCRIPT_GRAMMAR_EDEFAULT == null ? scriptGrammar != null
                    : !SCRIPT_GRAMMAR_EDEFAULT.equals(scriptGrammar);
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

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (mixed: "); //$NON-NLS-1$
        result.append(mixed);
        result.append(", any: "); //$NON-NLS-1$
        result.append(any);
        result.append(", scriptGrammar: "); //$NON-NLS-1$
        result.append(scriptGrammar);
        result.append(')');
        return result.toString();
    }

} // ExpressionImpl
