/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.organisation.api.impl;

import com.tibco.n2.common.organisation.api.OrganisationPackage;
import com.tibco.n2.common.organisation.api.XmlExecuteQuery;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Xml Execute Query</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlExecuteQueryImpl#getImplementation <em>Implementation</em>}</li>
 *   <li>{@link com.tibco.n2.common.organisation.api.impl.XmlExecuteQueryImpl#isSingleRandomResult <em>Single Random Result</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class XmlExecuteQueryImpl extends XmlResourceQueryImpl implements XmlExecuteQuery {
    /**
     * The default value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected static final int IMPLEMENTATION_EDEFAULT = 1;

    /**
     * The cached value of the '{@link #getImplementation() <em>Implementation</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getImplementation()
     * @generated
     * @ordered
     */
    protected int implementation = IMPLEMENTATION_EDEFAULT;

    /**
     * This is true if the Implementation attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean implementationESet;

    /**
     * The default value of the '{@link #isSingleRandomResult() <em>Single Random Result</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSingleRandomResult()
     * @generated
     * @ordered
     */
    protected static final boolean SINGLE_RANDOM_RESULT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isSingleRandomResult() <em>Single Random Result</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSingleRandomResult()
     * @generated
     * @ordered
     */
    protected boolean singleRandomResult = SINGLE_RANDOM_RESULT_EDEFAULT;

    /**
     * This is true if the Single Random Result attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean singleRandomResultESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected XmlExecuteQueryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OrganisationPackage.Literals.XML_EXECUTE_QUERY;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public int getImplementation() {
        return implementation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setImplementation(int newImplementation) {
        int oldImplementation = implementation;
        implementation = newImplementation;
        boolean oldImplementationESet = implementationESet;
        implementationESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_EXECUTE_QUERY__IMPLEMENTATION, oldImplementation, implementation, !oldImplementationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetImplementation() {
        int oldImplementation = implementation;
        boolean oldImplementationESet = implementationESet;
        implementation = IMPLEMENTATION_EDEFAULT;
        implementationESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, OrganisationPackage.XML_EXECUTE_QUERY__IMPLEMENTATION, oldImplementation, IMPLEMENTATION_EDEFAULT, oldImplementationESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetImplementation() {
        return implementationESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSingleRandomResult() {
        return singleRandomResult;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSingleRandomResult(boolean newSingleRandomResult) {
        boolean oldSingleRandomResult = singleRandomResult;
        singleRandomResult = newSingleRandomResult;
        boolean oldSingleRandomResultESet = singleRandomResultESet;
        singleRandomResultESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OrganisationPackage.XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT, oldSingleRandomResult, singleRandomResult, !oldSingleRandomResultESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetSingleRandomResult() {
        boolean oldSingleRandomResult = singleRandomResult;
        boolean oldSingleRandomResultESet = singleRandomResultESet;
        singleRandomResult = SINGLE_RANDOM_RESULT_EDEFAULT;
        singleRandomResultESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, OrganisationPackage.XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT, oldSingleRandomResult, SINGLE_RANDOM_RESULT_EDEFAULT, oldSingleRandomResultESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetSingleRandomResult() {
        return singleRandomResultESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OrganisationPackage.XML_EXECUTE_QUERY__IMPLEMENTATION:
                return getImplementation();
            case OrganisationPackage.XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT:
                return isSingleRandomResult();
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
            case OrganisationPackage.XML_EXECUTE_QUERY__IMPLEMENTATION:
                setImplementation((Integer)newValue);
                return;
            case OrganisationPackage.XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT:
                setSingleRandomResult((Boolean)newValue);
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
            case OrganisationPackage.XML_EXECUTE_QUERY__IMPLEMENTATION:
                unsetImplementation();
                return;
            case OrganisationPackage.XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT:
                unsetSingleRandomResult();
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
            case OrganisationPackage.XML_EXECUTE_QUERY__IMPLEMENTATION:
                return isSetImplementation();
            case OrganisationPackage.XML_EXECUTE_QUERY__SINGLE_RANDOM_RESULT:
                return isSetSingleRandomResult();
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
        result.append(" (implementation: ");
        if (implementationESet) result.append(implementation); else result.append("<unset>");
        result.append(", singleRandomResult: ");
        if (singleRandomResultESet) result.append(singleRandomResult); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //XmlExecuteQueryImpl
