/**
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 *
 * $Id$
 */
package com.tibco.xpd.xpdExtension.impl;

import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.WsSoapBinding;
import com.tibco.xpd.xpdExtension.WsSoapSecurity;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ws Soap Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl#getBindingStyle <em>Binding Style</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl#getSoapVersion <em>Soap Version</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdExtension.impl.WsSoapBindingImpl#getSoapSecurity <em>Soap Security</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WsSoapBindingImpl extends WsBindingImpl implements WsSoapBinding {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright =
            "Copyright (c) TIBCO Software Inc 2004 - 2014. All rights reserved."; //$NON-NLS-1$

    /**
     * The default value of the '{@link #getBindingStyle() <em>Binding Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBindingStyle()
     * @generated
     * @ordered
     */
    protected static final SoapBindingStyle BINDING_STYLE_EDEFAULT =
            SoapBindingStyle.RPC_LITERAL;

    /**
     * The cached value of the '{@link #getBindingStyle() <em>Binding Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getBindingStyle()
     * @generated
     * @ordered
     */
    protected SoapBindingStyle bindingStyle = BINDING_STYLE_EDEFAULT;

    /**
     * This is true if the Binding Style attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean bindingStyleESet;

    /**
     * The default value of the '{@link #getSoapVersion() <em>Soap Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSoapVersion()
     * @generated
     * @ordered
     */
    protected static final String SOAP_VERSION_EDEFAULT = "1.1"; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getSoapVersion() <em>Soap Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSoapVersion()
     * @generated
     * @ordered
     */
    protected String soapVersion = SOAP_VERSION_EDEFAULT;

    /**
     * This is true if the Soap Version attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean soapVersionESet;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected WsSoapBindingImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return XpdExtensionPackage.Literals.WS_SOAP_BINDING;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SoapBindingStyle getBindingStyle() {
        return bindingStyle;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBindingStyle(SoapBindingStyle newBindingStyle) {
        SoapBindingStyle oldBindingStyle = bindingStyle;
        bindingStyle = newBindingStyle == null ? BINDING_STYLE_EDEFAULT
                : newBindingStyle;
        boolean oldBindingStyleESet = bindingStyleESet;
        bindingStyleESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_BINDING__BINDING_STYLE,
                    oldBindingStyle, bindingStyle, !oldBindingStyleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetBindingStyle() {
        SoapBindingStyle oldBindingStyle = bindingStyle;
        boolean oldBindingStyleESet = bindingStyleESet;
        bindingStyle = BINDING_STYLE_EDEFAULT;
        bindingStyleESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.WS_SOAP_BINDING__BINDING_STYLE,
                    oldBindingStyle, BINDING_STYLE_EDEFAULT,
                    oldBindingStyleESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetBindingStyle() {
        return bindingStyleESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getSoapVersion() {
        return soapVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSoapVersion(String newSoapVersion) {
        String oldSoapVersion = soapVersion;
        soapVersion = newSoapVersion;
        boolean oldSoapVersionESet = soapVersionESet;
        soapVersionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    XpdExtensionPackage.WS_SOAP_BINDING__SOAP_VERSION,
                    oldSoapVersion, soapVersion, !oldSoapVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetSoapVersion() {
        String oldSoapVersion = soapVersion;
        boolean oldSoapVersionESet = soapVersionESet;
        soapVersion = SOAP_VERSION_EDEFAULT;
        soapVersionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET,
                    XpdExtensionPackage.WS_SOAP_BINDING__SOAP_VERSION,
                    oldSoapVersion, SOAP_VERSION_EDEFAULT, oldSoapVersionESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetSoapVersion() {
        return soapVersionESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public WsSoapSecurity getSoapSecurity() {
        // TODO: implement this method to return the 'Soap Security' reference
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSoapSecurity(WsSoapSecurity newSoapSecurity) {
        // TODO: implement this method to set the 'Soap Security' reference
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case XpdExtensionPackage.WS_SOAP_BINDING__BINDING_STYLE:
            return getBindingStyle();
        case XpdExtensionPackage.WS_SOAP_BINDING__SOAP_VERSION:
            return getSoapVersion();
        case XpdExtensionPackage.WS_SOAP_BINDING__SOAP_SECURITY:
            return getSoapSecurity();
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
        case XpdExtensionPackage.WS_SOAP_BINDING__BINDING_STYLE:
            setBindingStyle((SoapBindingStyle) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_BINDING__SOAP_VERSION:
            setSoapVersion((String) newValue);
            return;
        case XpdExtensionPackage.WS_SOAP_BINDING__SOAP_SECURITY:
            setSoapSecurity((WsSoapSecurity) newValue);
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
        case XpdExtensionPackage.WS_SOAP_BINDING__BINDING_STYLE:
            unsetBindingStyle();
            return;
        case XpdExtensionPackage.WS_SOAP_BINDING__SOAP_VERSION:
            unsetSoapVersion();
            return;
        case XpdExtensionPackage.WS_SOAP_BINDING__SOAP_SECURITY:
            setSoapSecurity((WsSoapSecurity) null);
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
        case XpdExtensionPackage.WS_SOAP_BINDING__BINDING_STYLE:
            return isSetBindingStyle();
        case XpdExtensionPackage.WS_SOAP_BINDING__SOAP_VERSION:
            return isSetSoapVersion();
        case XpdExtensionPackage.WS_SOAP_BINDING__SOAP_SECURITY:
            return getSoapSecurity() != null;
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
        result.append(" (bindingStyle: "); //$NON-NLS-1$
        if (bindingStyleESet)
            result.append(bindingStyle);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(", soapVersion: "); //$NON-NLS-1$
        if (soapVersionESet)
            result.append(soapVersion);
        else
            result.append("<unset>"); //$NON-NLS-1$
        result.append(')');
        return result.toString();
    }

} //WsSoapBindingImpl
