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

import com.tibco.xpd.xpdl2.ApplicationType;
import com.tibco.xpd.xpdl2.BusinessRuleApplication;
import com.tibco.xpd.xpdl2.EjbApplication;
import com.tibco.xpd.xpdl2.FormApplication;
import com.tibco.xpd.xpdl2.PojoApplication;
import com.tibco.xpd.xpdl2.Script;
import com.tibco.xpd.xpdl2.WebServiceApplication;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.XsltApplication;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl#getEjb <em>Ejb</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl#getPojo <em>Pojo</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl#getXslt <em>Xslt</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl#getScript <em>Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl#getWebService <em>Web Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl#getBusinessRule <em>Business Rule</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl#getForm <em>Form</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.impl.ApplicationTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ApplicationTypeImpl extends EObjectImpl implements ApplicationType {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getEjb() <em>Ejb</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEjb()
     * @generated
     * @ordered
     */
    protected EjbApplication ejb;

    /**
     * The cached value of the '{@link #getPojo() <em>Pojo</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPojo()
     * @generated
     * @ordered
     */
    protected PojoApplication pojo;

    /**
     * The cached value of the '{@link #getXslt() <em>Xslt</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getXslt()
     * @generated
     * @ordered
     */
    protected XsltApplication xslt;

    /**
     * The cached value of the '{@link #getScript() <em>Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getScript()
     * @generated
     * @ordered
     */
    protected Script script;

    /**
     * The cached value of the '{@link #getWebService() <em>Web Service</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getWebService()
     * @generated
     * @ordered
     */
    protected WebServiceApplication webService;

    /**
     * The cached value of the '{@link #getBusinessRule() <em>Business Rule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBusinessRule()
     * @generated
     * @ordered
     */
    protected BusinessRuleApplication businessRule;

    /**
     * The cached value of the '{@link #getForm() <em>Form</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getForm()
     * @generated
     * @ordered
     */
    protected FormApplication form;

    /**
     * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAnyAttribute()
     * @generated
     * @ordered
     */
    protected FeatureMap anyAttribute;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ApplicationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return Xpdl2Package.Literals.APPLICATION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EjbApplication getEjb() {
        return ejb;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetEjb(EjbApplication newEjb, NotificationChain msgs) {
        EjbApplication oldEjb = ejb;
        ejb = newEjb;
        if (eNotificationRequired()) {
            ENotificationImpl notification =
                    new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION_TYPE__EJB, oldEjb, newEjb);
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
    public void setEjb(EjbApplication newEjb) {
        if (newEjb != ejb) {
            NotificationChain msgs = null;
            if (ejb != null)
                msgs = ((InternalEObject) ejb)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__EJB, null, msgs);
            if (newEjb != null)
                msgs = ((InternalEObject) newEjb)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__EJB, null, msgs);
            msgs = basicSetEjb(newEjb, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION_TYPE__EJB, newEjb, newEjb));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PojoApplication getPojo() {
        return pojo;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPojo(PojoApplication newPojo, NotificationChain msgs) {
        PojoApplication oldPojo = pojo;
        pojo = newPojo;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.APPLICATION_TYPE__POJO, oldPojo, newPojo);
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
    public void setPojo(PojoApplication newPojo) {
        if (newPojo != pojo) {
            NotificationChain msgs = null;
            if (pojo != null)
                msgs = ((InternalEObject) pojo)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__POJO, null, msgs);
            if (newPojo != null)
                msgs = ((InternalEObject) newPojo)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__POJO, null, msgs);
            msgs = basicSetPojo(newPojo, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION_TYPE__POJO, newPojo,
                    newPojo));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public XsltApplication getXslt() {
        return xslt;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetXslt(XsltApplication newXslt, NotificationChain msgs) {
        XsltApplication oldXslt = xslt;
        xslt = newXslt;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.APPLICATION_TYPE__XSLT, oldXslt, newXslt);
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
    public void setXslt(XsltApplication newXslt) {
        if (newXslt != xslt) {
            NotificationChain msgs = null;
            if (xslt != null)
                msgs = ((InternalEObject) xslt)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__XSLT, null, msgs);
            if (newXslt != null)
                msgs = ((InternalEObject) newXslt)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__XSLT, null, msgs);
            msgs = basicSetXslt(newXslt, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION_TYPE__XSLT, newXslt,
                    newXslt));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Script getScript() {
        return script;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetScript(Script newScript, NotificationChain msgs) {
        Script oldScript = script;
        script = newScript;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.APPLICATION_TYPE__SCRIPT, oldScript, newScript);
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
    public void setScript(Script newScript) {
        if (newScript != script) {
            NotificationChain msgs = null;
            if (script != null)
                msgs = ((InternalEObject) script).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__SCRIPT,
                        null,
                        msgs);
            if (newScript != null)
                msgs = ((InternalEObject) newScript)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__SCRIPT, null, msgs);
            msgs = basicSetScript(newScript, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION_TYPE__SCRIPT, newScript,
                    newScript));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WebServiceApplication getWebService() {
        return webService;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetWebService(WebServiceApplication newWebService, NotificationChain msgs) {
        WebServiceApplication oldWebService = webService;
        webService = newWebService;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE, oldWebService, newWebService);
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
    public void setWebService(WebServiceApplication newWebService) {
        if (newWebService != webService) {
            NotificationChain msgs = null;
            if (webService != null)
                msgs = ((InternalEObject) webService).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE,
                        null,
                        msgs);
            if (newWebService != null)
                msgs = ((InternalEObject) newWebService).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE,
                        null,
                        msgs);
            msgs = basicSetWebService(newWebService, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE,
                    newWebService, newWebService));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BusinessRuleApplication getBusinessRule() {
        return businessRule;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBusinessRule(BusinessRuleApplication newBusinessRule, NotificationChain msgs) {
        BusinessRuleApplication oldBusinessRule = businessRule;
        businessRule = newBusinessRule;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE, oldBusinessRule, newBusinessRule);
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
    public void setBusinessRule(BusinessRuleApplication newBusinessRule) {
        if (newBusinessRule != businessRule) {
            NotificationChain msgs = null;
            if (businessRule != null)
                msgs = ((InternalEObject) businessRule).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE,
                        null,
                        msgs);
            if (newBusinessRule != null)
                msgs = ((InternalEObject) newBusinessRule).eInverseAdd(this,
                        EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE,
                        null,
                        msgs);
            msgs = basicSetBusinessRule(newBusinessRule, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE,
                    newBusinessRule, newBusinessRule));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FormApplication getForm() {
        return form;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetForm(FormApplication newForm, NotificationChain msgs) {
        FormApplication oldForm = form;
        form = newForm;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    Xpdl2Package.APPLICATION_TYPE__FORM, oldForm, newForm);
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
    public void setForm(FormApplication newForm) {
        if (newForm != form) {
            NotificationChain msgs = null;
            if (form != null)
                msgs = ((InternalEObject) form)
                        .eInverseRemove(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__FORM, null, msgs);
            if (newForm != null)
                msgs = ((InternalEObject) newForm)
                        .eInverseAdd(this, EOPPOSITE_FEATURE_BASE - Xpdl2Package.APPLICATION_TYPE__FORM, null, msgs);
            msgs = basicSetForm(newForm, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, Xpdl2Package.APPLICATION_TYPE__FORM, newForm,
                    newForm));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getAnyAttribute() {
        if (anyAttribute == null) {
            anyAttribute = new BasicFeatureMap(this, Xpdl2Package.APPLICATION_TYPE__ANY_ATTRIBUTE);
        }
        return anyAttribute;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case Xpdl2Package.APPLICATION_TYPE__EJB:
            return basicSetEjb(null, msgs);
        case Xpdl2Package.APPLICATION_TYPE__POJO:
            return basicSetPojo(null, msgs);
        case Xpdl2Package.APPLICATION_TYPE__XSLT:
            return basicSetXslt(null, msgs);
        case Xpdl2Package.APPLICATION_TYPE__SCRIPT:
            return basicSetScript(null, msgs);
        case Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE:
            return basicSetWebService(null, msgs);
        case Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE:
            return basicSetBusinessRule(null, msgs);
        case Xpdl2Package.APPLICATION_TYPE__FORM:
            return basicSetForm(null, msgs);
        case Xpdl2Package.APPLICATION_TYPE__ANY_ATTRIBUTE:
            return ((InternalEList<?>) getAnyAttribute()).basicRemove(otherEnd, msgs);
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
        case Xpdl2Package.APPLICATION_TYPE__EJB:
            return getEjb();
        case Xpdl2Package.APPLICATION_TYPE__POJO:
            return getPojo();
        case Xpdl2Package.APPLICATION_TYPE__XSLT:
            return getXslt();
        case Xpdl2Package.APPLICATION_TYPE__SCRIPT:
            return getScript();
        case Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE:
            return getWebService();
        case Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE:
            return getBusinessRule();
        case Xpdl2Package.APPLICATION_TYPE__FORM:
            return getForm();
        case Xpdl2Package.APPLICATION_TYPE__ANY_ATTRIBUTE:
            if (coreType)
                return getAnyAttribute();
            return ((FeatureMap.Internal) getAnyAttribute()).getWrapper();
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
        case Xpdl2Package.APPLICATION_TYPE__EJB:
            setEjb((EjbApplication) newValue);
            return;
        case Xpdl2Package.APPLICATION_TYPE__POJO:
            setPojo((PojoApplication) newValue);
            return;
        case Xpdl2Package.APPLICATION_TYPE__XSLT:
            setXslt((XsltApplication) newValue);
            return;
        case Xpdl2Package.APPLICATION_TYPE__SCRIPT:
            setScript((Script) newValue);
            return;
        case Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE:
            setWebService((WebServiceApplication) newValue);
            return;
        case Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE:
            setBusinessRule((BusinessRuleApplication) newValue);
            return;
        case Xpdl2Package.APPLICATION_TYPE__FORM:
            setForm((FormApplication) newValue);
            return;
        case Xpdl2Package.APPLICATION_TYPE__ANY_ATTRIBUTE:
            ((FeatureMap.Internal) getAnyAttribute()).set(newValue);
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
        case Xpdl2Package.APPLICATION_TYPE__EJB:
            setEjb((EjbApplication) null);
            return;
        case Xpdl2Package.APPLICATION_TYPE__POJO:
            setPojo((PojoApplication) null);
            return;
        case Xpdl2Package.APPLICATION_TYPE__XSLT:
            setXslt((XsltApplication) null);
            return;
        case Xpdl2Package.APPLICATION_TYPE__SCRIPT:
            setScript((Script) null);
            return;
        case Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE:
            setWebService((WebServiceApplication) null);
            return;
        case Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE:
            setBusinessRule((BusinessRuleApplication) null);
            return;
        case Xpdl2Package.APPLICATION_TYPE__FORM:
            setForm((FormApplication) null);
            return;
        case Xpdl2Package.APPLICATION_TYPE__ANY_ATTRIBUTE:
            getAnyAttribute().clear();
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
        case Xpdl2Package.APPLICATION_TYPE__EJB:
            return ejb != null;
        case Xpdl2Package.APPLICATION_TYPE__POJO:
            return pojo != null;
        case Xpdl2Package.APPLICATION_TYPE__XSLT:
            return xslt != null;
        case Xpdl2Package.APPLICATION_TYPE__SCRIPT:
            return script != null;
        case Xpdl2Package.APPLICATION_TYPE__WEB_SERVICE:
            return webService != null;
        case Xpdl2Package.APPLICATION_TYPE__BUSINESS_RULE:
            return businessRule != null;
        case Xpdl2Package.APPLICATION_TYPE__FORM:
            return form != null;
        case Xpdl2Package.APPLICATION_TYPE__ANY_ATTRIBUTE:
            return anyAttribute != null && !anyAttribute.isEmpty();
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (anyAttribute: "); //$NON-NLS-1$
        result.append(anyAttribute);
        result.append(')');
        return result.toString();
    }

} //ApplicationTypeImpl
