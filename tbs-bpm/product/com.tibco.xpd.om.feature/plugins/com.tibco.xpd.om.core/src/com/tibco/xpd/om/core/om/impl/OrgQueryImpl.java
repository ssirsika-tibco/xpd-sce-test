/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.om.core.om.impl;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgQuery;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Org Query</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgQueryImpl#getGrammar <em>Grammar</em>}</li>
 *   <li>{@link com.tibco.xpd.om.core.om.impl.OrgQueryImpl#getQuery <em>Query</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrgQueryImpl extends NamedElementImpl implements OrgQuery {
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
     * The default value of the '{@link #getQuery() <em>Query</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQuery()
     * @generated
     * @ordered
     */
    protected static final String QUERY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getQuery() <em>Query</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getQuery()
     * @generated
     * @ordered
     */
    protected String query = QUERY_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OrgQueryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OMPackage.Literals.ORG_QUERY;
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
                    OMPackage.ORG_QUERY__GRAMMAR, oldGrammar, grammar));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getQuery() {
        return query;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setQuery(String newQuery) {
        String oldQuery = query;
        query = newQuery;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    OMPackage.ORG_QUERY__QUERY, oldQuery, query));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case OMPackage.ORG_QUERY__GRAMMAR:
            return getGrammar();
        case OMPackage.ORG_QUERY__QUERY:
            return getQuery();
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
        case OMPackage.ORG_QUERY__GRAMMAR:
            setGrammar((String) newValue);
            return;
        case OMPackage.ORG_QUERY__QUERY:
            setQuery((String) newValue);
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
        case OMPackage.ORG_QUERY__GRAMMAR:
            setGrammar(GRAMMAR_EDEFAULT);
            return;
        case OMPackage.ORG_QUERY__QUERY:
            setQuery(QUERY_EDEFAULT);
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
        case OMPackage.ORG_QUERY__GRAMMAR:
            return GRAMMAR_EDEFAULT == null ? grammar != null
                    : !GRAMMAR_EDEFAULT.equals(grammar);
        case OMPackage.ORG_QUERY__QUERY:
            return QUERY_EDEFAULT == null ? query != null : !QUERY_EDEFAULT
                    .equals(query);
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
        result.append(" (grammar: "); //$NON-NLS-1$
        result.append(grammar);
        result.append(", query: "); //$NON-NLS-1$
        result.append(query);
        result.append(')');
        return result.toString();
    }

} //OrgQueryImpl
