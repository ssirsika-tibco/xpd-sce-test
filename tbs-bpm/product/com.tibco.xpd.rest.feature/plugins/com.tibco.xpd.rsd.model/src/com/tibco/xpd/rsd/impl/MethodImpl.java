/**
 */
package com.tibco.xpd.rsd.impl;

import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Response;
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
 * An implementation of the model object '<em><b>Method</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.xpd.rsd.impl.MethodImpl#getRequest <em>Request</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.MethodImpl#getHttpMethod <em>Http Method</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.MethodImpl#getFaults <em>Faults</em>}</li>
 *   <li>{@link com.tibco.xpd.rsd.impl.MethodImpl#getResponse <em>Response</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MethodImpl extends NamedElementImpl implements Method {
    /**
     * The cached value of the '{@link #getRequest() <em>Request</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getRequest()
     * @generated
     * @ordered
     */
    protected Request request;

    /**
     * The default value of the '{@link #getHttpMethod() <em>Http Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHttpMethod()
     * @generated
     * @ordered
     */
    protected static final HttpMethod HTTP_METHOD_EDEFAULT = HttpMethod.GET;

    /**
     * The cached value of the '{@link #getHttpMethod() <em>Http Method</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getHttpMethod()
     * @generated
     * @ordered
     */
    protected HttpMethod httpMethod = HTTP_METHOD_EDEFAULT;

    /**
     * The cached value of the '{@link #getFaults() <em>Faults</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getFaults()
     * @generated
     * @ordered
     */
    protected EList<Fault> faults;

    /**
     * The cached value of the '{@link #getResponse() <em>Response</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getResponse()
     * @generated
     * @ordered
     */
    protected Response response;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MethodImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return RsdPackage.Literals.METHOD;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Request getRequest() {
        return request;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetRequest(Request newRequest, NotificationChain msgs) {
        Request oldRequest = request;
        request = newRequest;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RsdPackage.METHOD__REQUEST, oldRequest, newRequest);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setRequest(Request newRequest) {
        if (newRequest != request) {
            NotificationChain msgs = null;
            if (request != null)
                msgs = ((InternalEObject)request).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RsdPackage.METHOD__REQUEST, null, msgs);
            if (newRequest != null)
                msgs = ((InternalEObject)newRequest).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RsdPackage.METHOD__REQUEST, null, msgs);
            msgs = basicSetRequest(newRequest, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.METHOD__REQUEST, newRequest, newRequest));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setHttpMethod(HttpMethod newHttpMethod) {
        HttpMethod oldHttpMethod = httpMethod;
        httpMethod = newHttpMethod == null ? HTTP_METHOD_EDEFAULT : newHttpMethod;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.METHOD__HTTP_METHOD, oldHttpMethod, httpMethod));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Fault> getFaults() {
        if (faults == null) {
            faults = new EObjectContainmentEList<Fault>(Fault.class, this, RsdPackage.METHOD__FAULTS);
        }
        return faults;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Response getResponse() {
        return response;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetResponse(Response newResponse, NotificationChain msgs) {
        Response oldResponse = response;
        response = newResponse;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RsdPackage.METHOD__RESPONSE, oldResponse, newResponse);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setResponse(Response newResponse) {
        if (newResponse != response) {
            NotificationChain msgs = null;
            if (response != null)
                msgs = ((InternalEObject)response).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RsdPackage.METHOD__RESPONSE, null, msgs);
            if (newResponse != null)
                msgs = ((InternalEObject)newResponse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RsdPackage.METHOD__RESPONSE, null, msgs);
            msgs = basicSetResponse(newResponse, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, RsdPackage.METHOD__RESPONSE, newResponse, newResponse));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case RsdPackage.METHOD__REQUEST:
                return basicSetRequest(null, msgs);
            case RsdPackage.METHOD__FAULTS:
                return ((InternalEList<?>)getFaults()).basicRemove(otherEnd, msgs);
            case RsdPackage.METHOD__RESPONSE:
                return basicSetResponse(null, msgs);
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
            case RsdPackage.METHOD__REQUEST:
                return getRequest();
            case RsdPackage.METHOD__HTTP_METHOD:
                return getHttpMethod();
            case RsdPackage.METHOD__FAULTS:
                return getFaults();
            case RsdPackage.METHOD__RESPONSE:
                return getResponse();
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
            case RsdPackage.METHOD__REQUEST:
                setRequest((Request)newValue);
                return;
            case RsdPackage.METHOD__HTTP_METHOD:
                setHttpMethod((HttpMethod)newValue);
                return;
            case RsdPackage.METHOD__FAULTS:
                getFaults().clear();
                getFaults().addAll((Collection<? extends Fault>)newValue);
                return;
            case RsdPackage.METHOD__RESPONSE:
                setResponse((Response)newValue);
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
            case RsdPackage.METHOD__REQUEST:
                setRequest((Request)null);
                return;
            case RsdPackage.METHOD__HTTP_METHOD:
                setHttpMethod(HTTP_METHOD_EDEFAULT);
                return;
            case RsdPackage.METHOD__FAULTS:
                getFaults().clear();
                return;
            case RsdPackage.METHOD__RESPONSE:
                setResponse((Response)null);
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
            case RsdPackage.METHOD__REQUEST:
                return request != null;
            case RsdPackage.METHOD__HTTP_METHOD:
                return httpMethod != HTTP_METHOD_EDEFAULT;
            case RsdPackage.METHOD__FAULTS:
                return faults != null && !faults.isEmpty();
            case RsdPackage.METHOD__RESPONSE:
                return response != null;
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
        result.append(" (httpMethod: ");
        result.append(httpMethod);
        result.append(')');
        return result.toString();
    }

} //MethodImpl
