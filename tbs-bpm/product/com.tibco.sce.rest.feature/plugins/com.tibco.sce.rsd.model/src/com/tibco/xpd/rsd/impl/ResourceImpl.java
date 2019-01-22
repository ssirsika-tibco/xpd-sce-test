/**
 */
package com.tibco.xpd.rsd.impl;

import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.RsdPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.impl.ResourceImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.ResourceImpl#getMethods <em>Methods</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.ResourceImpl#getPathTemplate <em>Path Template</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceImpl extends NamedElementImpl implements Resource {
    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected EList<Parameter> parameters;

    /**
     * The cached value of the '{@link #getMethods() <em>Methods</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMethods()
     * @generated
     * @ordered
     */
    protected EList<Method> methods;

    /**
     * The default value of the '{@link #getPathTemplate() <em>Path Template</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPathTemplate()
     * @generated
     * @ordered
     */
    protected static final String PATH_TEMPLATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPathTemplate() <em>Path Template</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPathTemplate()
     * @generated
     * @ordered
     */
    protected String pathTemplate = PATH_TEMPLATE_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ResourceImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RsdPackage.Literals.RESOURCE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Parameter> getParameters() {
        if (parameters == null) {
            parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, RsdPackage.RESOURCE__PARAMETERS);
        }
        return parameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Method> getMethods() {
        if (methods == null) {
            methods = new EObjectContainmentEList<Method>(Method.class, this, RsdPackage.RESOURCE__METHODS);
        }
        return methods;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPathTemplate() {
        return pathTemplate;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPathTemplate(String newPathTemplate) {
        String oldPathTemplate = pathTemplate;
        pathTemplate = newPathTemplate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.RESOURCE__PATH_TEMPLATE, oldPathTemplate, pathTemplate));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case RsdPackage.RESOURCE__PARAMETERS:
                return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
            case RsdPackage.RESOURCE__METHODS:
                return ((InternalEList<?>)getMethods()).basicRemove(otherEnd, msgs);
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
            case RsdPackage.RESOURCE__PARAMETERS:
                return getParameters();
            case RsdPackage.RESOURCE__METHODS:
                return getMethods();
            case RsdPackage.RESOURCE__PATH_TEMPLATE:
                return getPathTemplate();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case RsdPackage.RESOURCE__PARAMETERS:
                getParameters().clear();
                getParameters().addAll((Collection<? extends Parameter>)newValue);
                return;
            case RsdPackage.RESOURCE__METHODS:
                getMethods().clear();
                getMethods().addAll((Collection<? extends Method>)newValue);
                return;
            case RsdPackage.RESOURCE__PATH_TEMPLATE:
                setPathTemplate((String)newValue);
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
            case RsdPackage.RESOURCE__PARAMETERS:
                getParameters().clear();
                return;
            case RsdPackage.RESOURCE__METHODS:
                getMethods().clear();
                return;
            case RsdPackage.RESOURCE__PATH_TEMPLATE:
                setPathTemplate(PATH_TEMPLATE_EDEFAULT);
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
            case RsdPackage.RESOURCE__PARAMETERS:
                return parameters != null && !parameters.isEmpty();
            case RsdPackage.RESOURCE__METHODS:
                return methods != null && !methods.isEmpty();
            case RsdPackage.RESOURCE__PATH_TEMPLATE:
                return PATH_TEMPLATE_EDEFAULT == null ? pathTemplate != null : !PATH_TEMPLATE_EDEFAULT.equals(pathTemplate);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == ParameterContainer.class) {
            switch (derivedFeatureID) {
                case RsdPackage.RESOURCE__PARAMETERS: return RsdPackage.PARAMETER_CONTAINER__PARAMETERS;
                default: return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == ParameterContainer.class) {
            switch (baseFeatureID) {
                case RsdPackage.PARAMETER_CONTAINER__PARAMETERS: return RsdPackage.RESOURCE__PARAMETERS;
                default: return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (pathTemplate: ");
        result.append(pathTemplate);
        result.append(')');
        return result.toString();
    }

} //ResourceImpl
