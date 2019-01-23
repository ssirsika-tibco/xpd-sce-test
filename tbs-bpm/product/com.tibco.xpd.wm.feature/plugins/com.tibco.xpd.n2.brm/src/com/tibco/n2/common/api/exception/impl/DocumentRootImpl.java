/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.n2.common.api.exception.impl;

import com.tibco.n2.common.api.exception.DeploymentParameterFaultType;
import com.tibco.n2.common.api.exception.DocumentRoot;
import com.tibco.n2.common.api.exception.ExceptionPackage;
import com.tibco.n2.common.api.exception.InternalServiceFaultType;
import com.tibco.n2.common.api.exception.InvalidWorkTypeFaultType;
import com.tibco.n2.common.api.exception.WorkTypeFaultType;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl#getDeploymentParameterFault <em>Deployment Parameter Fault</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl#getInternalServiceFault <em>Internal Service Fault</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl#getInvalidWorkTypeFault <em>Invalid Work Type Fault</em>}</li>
 *   <li>{@link com.tibco.n2.common.api.exception.impl.DocumentRootImpl#getWorkTypeFault <em>Work Type Fault</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
    /**
     * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMixed()
     * @generated
     * @ordered
     */
    protected FeatureMap mixed;

    /**
     * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXMLNSPrefixMap()
     * @generated
     * @ordered
     */
    protected EMap<String, String> xMLNSPrefixMap;

    /**
     * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXSISchemaLocation()
     * @generated
     * @ordered
     */
    protected EMap<String, String> xSISchemaLocation;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DocumentRootImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ExceptionPackage.Literals.DOCUMENT_ROOT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getMixed() {
        if (mixed == null) {
            mixed = new BasicFeatureMap(this, ExceptionPackage.DOCUMENT_ROOT__MIXED);
        }
        return mixed;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getXMLNSPrefixMap() {
        if (xMLNSPrefixMap == null) {
            xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ExceptionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
        }
        return xMLNSPrefixMap;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EMap<String, String> getXSISchemaLocation() {
        if (xSISchemaLocation == null) {
            xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ExceptionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
        }
        return xSISchemaLocation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeploymentParameterFaultType getDeploymentParameterFault() {
        return (DeploymentParameterFaultType)getMixed().get(ExceptionPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetDeploymentParameterFault(DeploymentParameterFaultType newDeploymentParameterFault, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(ExceptionPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT, newDeploymentParameterFault, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDeploymentParameterFault(DeploymentParameterFaultType newDeploymentParameterFault) {
        ((FeatureMap.Internal)getMixed()).set(ExceptionPackage.Literals.DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT, newDeploymentParameterFault);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InternalServiceFaultType getInternalServiceFault() {
        return (InternalServiceFaultType)getMixed().get(ExceptionPackage.Literals.DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetInternalServiceFault(InternalServiceFaultType newInternalServiceFault, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(ExceptionPackage.Literals.DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT, newInternalServiceFault, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInternalServiceFault(InternalServiceFaultType newInternalServiceFault) {
        ((FeatureMap.Internal)getMixed()).set(ExceptionPackage.Literals.DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT, newInternalServiceFault);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InvalidWorkTypeFaultType getInvalidWorkTypeFault() {
        return (InvalidWorkTypeFaultType)getMixed().get(ExceptionPackage.Literals.DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetInvalidWorkTypeFault(InvalidWorkTypeFaultType newInvalidWorkTypeFault, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(ExceptionPackage.Literals.DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT, newInvalidWorkTypeFault, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInvalidWorkTypeFault(InvalidWorkTypeFaultType newInvalidWorkTypeFault) {
        ((FeatureMap.Internal)getMixed()).set(ExceptionPackage.Literals.DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT, newInvalidWorkTypeFault);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WorkTypeFaultType getWorkTypeFault() {
        return (WorkTypeFaultType)getMixed().get(ExceptionPackage.Literals.DOCUMENT_ROOT__WORK_TYPE_FAULT, true);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWorkTypeFault(WorkTypeFaultType newWorkTypeFault, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(ExceptionPackage.Literals.DOCUMENT_ROOT__WORK_TYPE_FAULT, newWorkTypeFault, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setWorkTypeFault(WorkTypeFaultType newWorkTypeFault) {
        ((FeatureMap.Internal)getMixed()).set(ExceptionPackage.Literals.DOCUMENT_ROOT__WORK_TYPE_FAULT, newWorkTypeFault);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ExceptionPackage.DOCUMENT_ROOT__MIXED:
                return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
            case ExceptionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
            case ExceptionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
            case ExceptionPackage.DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT:
                return basicSetDeploymentParameterFault(null, msgs);
            case ExceptionPackage.DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT:
                return basicSetInternalServiceFault(null, msgs);
            case ExceptionPackage.DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT:
                return basicSetInvalidWorkTypeFault(null, msgs);
            case ExceptionPackage.DOCUMENT_ROOT__WORK_TYPE_FAULT:
                return basicSetWorkTypeFault(null, msgs);
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
            case ExceptionPackage.DOCUMENT_ROOT__MIXED:
                if (coreType) return getMixed();
                return ((FeatureMap.Internal)getMixed()).getWrapper();
            case ExceptionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                if (coreType) return getXMLNSPrefixMap();
                else return getXMLNSPrefixMap().map();
            case ExceptionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                if (coreType) return getXSISchemaLocation();
                else return getXSISchemaLocation().map();
            case ExceptionPackage.DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT:
                return getDeploymentParameterFault();
            case ExceptionPackage.DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT:
                return getInternalServiceFault();
            case ExceptionPackage.DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT:
                return getInvalidWorkTypeFault();
            case ExceptionPackage.DOCUMENT_ROOT__WORK_TYPE_FAULT:
                return getWorkTypeFault();
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
            case ExceptionPackage.DOCUMENT_ROOT__MIXED:
                ((FeatureMap.Internal)getMixed()).set(newValue);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                ((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                ((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT:
                setDeploymentParameterFault((DeploymentParameterFaultType)newValue);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT:
                setInternalServiceFault((InternalServiceFaultType)newValue);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT:
                setInvalidWorkTypeFault((InvalidWorkTypeFaultType)newValue);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__WORK_TYPE_FAULT:
                setWorkTypeFault((WorkTypeFaultType)newValue);
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
            case ExceptionPackage.DOCUMENT_ROOT__MIXED:
                getMixed().clear();
                return;
            case ExceptionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                getXMLNSPrefixMap().clear();
                return;
            case ExceptionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                getXSISchemaLocation().clear();
                return;
            case ExceptionPackage.DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT:
                setDeploymentParameterFault((DeploymentParameterFaultType)null);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT:
                setInternalServiceFault((InternalServiceFaultType)null);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT:
                setInvalidWorkTypeFault((InvalidWorkTypeFaultType)null);
                return;
            case ExceptionPackage.DOCUMENT_ROOT__WORK_TYPE_FAULT:
                setWorkTypeFault((WorkTypeFaultType)null);
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
            case ExceptionPackage.DOCUMENT_ROOT__MIXED:
                return mixed != null && !mixed.isEmpty();
            case ExceptionPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
                return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
            case ExceptionPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
                return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
            case ExceptionPackage.DOCUMENT_ROOT__DEPLOYMENT_PARAMETER_FAULT:
                return getDeploymentParameterFault() != null;
            case ExceptionPackage.DOCUMENT_ROOT__INTERNAL_SERVICE_FAULT:
                return getInternalServiceFault() != null;
            case ExceptionPackage.DOCUMENT_ROOT__INVALID_WORK_TYPE_FAULT:
                return getInvalidWorkTypeFault() != null;
            case ExceptionPackage.DOCUMENT_ROOT__WORK_TYPE_FAULT:
                return getWorkTypeFault() != null;
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
        result.append(" (mixed: ");
        result.append(mixed);
        result.append(')');
        return result.toString();
    }

} //DocumentRootImpl
