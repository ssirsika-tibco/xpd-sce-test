/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.xpd.xpdl2;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationType#getEjb <em>Ejb</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationType#getPojo <em>Pojo</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationType#getXslt <em>Xslt</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationType#getScript <em>Script</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationType#getWebService <em>Web Service</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationType#getBusinessRule <em>Business Rule</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationType#getForm <em>Form</em>}</li>
 *   <li>{@link com.tibco.xpd.xpdl2.ApplicationType#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType()
 * @model extendedMetaData="name='ApplicationType' kind='elementOnly'"
 * @generated
 */
public interface ApplicationType extends EObject {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String copyright =
            "Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved."; //$NON-NLS-1$

    /**
     * Returns the value of the '<em><b>Ejb</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Call EJB component -- There can be max one formal parameter that is OUT, if it exists it has to be the last formal parameter. no INOUT formal parameters
     * <!-- end-model-doc -->
     * @return the value of the '<em>Ejb</em>' containment reference.
     * @see #setEjb(EjbApplication)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType_Ejb()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Ejb' namespace='##targetNamespace'"
     * @generated
     */
    EjbApplication getEjb();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ApplicationType#getEjb <em>Ejb</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Ejb</em>' containment reference.
     * @see #getEjb()
     * @generated
     */
    void setEjb(EjbApplication value);

    /**
     * Returns the value of the '<em><b>Pojo</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Call method on Java class -- There can be max one formal parameter that is OUT, if it exists it has to be the last formal parameter. no INOUT formal parameters
     * <!-- end-model-doc -->
     * @return the value of the '<em>Pojo</em>' containment reference.
     * @see #setPojo(PojoApplication)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType_Pojo()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Pojo' namespace='##targetNamespace'"
     * @generated
     */
    PojoApplication getPojo();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ApplicationType#getPojo <em>Pojo</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Pojo</em>' containment reference.
     * @see #getPojo()
     * @generated
     */
    void setPojo(PojoApplication value);

    /**
     * Returns the value of the '<em><b>Xslt</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Execute Tranformation -- Formal Parameters restrictions: one IN and one OUT formal parameters or only one INOUT formal parameter
     * <!-- end-model-doc -->
     * @return the value of the '<em>Xslt</em>' containment reference.
     * @see #setXslt(XsltApplication)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType_Xslt()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Xslt' namespace='##targetNamespace'"
     * @generated
     */
    XsltApplication getXslt();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ApplicationType#getXslt <em>Xslt</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Xslt</em>' containment reference.
     * @see #getXslt()
     * @generated
     */
    void setXslt(XsltApplication value);

    /**
     * Returns the value of the '<em><b>Script</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  Execute Script -- No additional restrictions for formal parameters. The suggestion: every Formal Parameter should be registered in the script scope as a global variable
     * <!-- end-model-doc -->
     * @return the value of the '<em>Script</em>' containment reference.
     * @see #setScript(Script)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType_Script()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Script' namespace='##targetNamespace'"
     * @generated
     */
    Script getScript();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ApplicationType#getScript <em>Script</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Script</em>' containment reference.
     * @see #getScript()
     * @generated
     */
    void setScript(Script value);

    /**
     * Returns the value of the '<em><b>Web Service</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     *  For WSDL 1.2 -- Invoke WebService, all IN Fprmal Parameters will be mapped to input message, all OUT Formal Parameters will be maped from output message
     * <!-- end-model-doc -->
     * @return the value of the '<em>Web Service</em>' containment reference.
     * @see #setWebService(WebServiceApplication)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType_WebService()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='WebService' namespace='##targetNamespace'"
     * @generated
     */
    WebServiceApplication getWebService();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ApplicationType#getWebService <em>Web Service</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Web Service</em>' containment reference.
     * @see #getWebService()
     * @generated
     */
    void setWebService(WebServiceApplication value);

    /**
     * Returns the value of the '<em><b>Business Rule</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Invoke business rule
     * <!-- end-model-doc -->
     * @return the value of the '<em>Business Rule</em>' containment reference.
     * @see #setBusinessRule(BusinessRuleApplication)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType_BusinessRule()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='BusinessRule' namespace='##targetNamespace'"
     * @generated
     */
    BusinessRuleApplication getBusinessRule();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ApplicationType#getBusinessRule <em>Business Rule</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Business Rule</em>' containment reference.
     * @see #getBusinessRule()
     * @generated
     */
    void setBusinessRule(BusinessRuleApplication value);

    /**
     * Returns the value of the '<em><b>Form</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Placeholder for all form related additional information.
     * <!-- end-model-doc -->
     * @return the value of the '<em>Form</em>' containment reference.
     * @see #setForm(FormApplication)
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType_Form()
     * @model containment="true"
     *        extendedMetaData="kind='element' name='Form' namespace='##targetNamespace'"
     * @generated
     */
    FormApplication getForm();

    /**
     * Sets the value of the '{@link com.tibco.xpd.xpdl2.ApplicationType#getForm <em>Form</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Form</em>' containment reference.
     * @see #getForm()
     * @generated
     */
    void setForm(FormApplication value);

    /**
     * Returns the value of the '<em><b>Any Attribute</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Any Attribute</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Any Attribute</em>' attribute list.
     * @see com.tibco.xpd.xpdl2.Xpdl2Package#getApplicationType_AnyAttribute()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='attributeWildcard' wildcards='##other' name=':7' processing='lax'"
     * @generated
     */
    FeatureMap getAnyAttribute();

} // ApplicationType
