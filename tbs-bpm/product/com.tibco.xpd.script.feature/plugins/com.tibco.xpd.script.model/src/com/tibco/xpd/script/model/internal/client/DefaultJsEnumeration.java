/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.internal.Messages;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * This class provides a wrapper for the Enumeration classifier in UML model
 * 
 * @author mtorres
 * 
 */
public class DefaultJsEnumeration extends AbstractDefaultJsDataType implements
        JsEnumeration {

    private String qualifiedName = null;

    private Enumeration umlEnumeration;

    public DefaultJsEnumeration(Enumeration umlEnumeration) {
        super(umlEnumeration);

        this.umlEnumeration = umlEnumeration;
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<JsAttribute> getAttributeList() {
        List<JsAttribute> attributeList = new ArrayList<JsAttribute>();
        // Add the uml and dynamic attributes to the list
        List<JsAttribute> umlAttributeList = getUmlAttributeList();
        if (umlAttributeList != null) {
            attributeList.addAll(umlAttributeList);
        }
        if (dynamicAttributeList != null) {
            attributeList.addAll(dynamicAttributeList);
        }
        return attributeList;
    }

    @Override
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    protected List<JsAttribute> getUmlAttributeList() {
        if (umlDataType instanceof Enumeration) {
            Enumeration umlEnumeration = (Enumeration) umlDataType;
            EList<EnumerationLiteral> ownedLiterals =
                    umlEnumeration.getOwnedLiterals();
            List<JsAttribute> attributeList = new ArrayList<JsAttribute>();
            if (ownedLiterals != null) {
                for (EnumerationLiteral enumerationLiteral : ownedLiterals) {
                    if (enumerationLiteral != null) {
                        JsEnumerationLiteral defaultJsEnumerationLiteral =
                                createJsEnumerationLiteral(enumerationLiteral);
                        attributeList.add(defaultJsEnumerationLiteral);
                    }
                }
            }
            attributeList.addAll(super.getUmlAttributeList());
            return attributeList;
        }
        return Collections.emptyList();
    }

    protected JsEnumerationLiteral createJsEnumerationLiteral(
            EnumerationLiteral enumerationLiteral) {
        DefaultJsEnumerationLiteral jsEnumerationLiteral =
                new DefaultJsEnumerationLiteral(enumerationLiteral);
        if (getContentAssistIconProvider() != null) {
            jsEnumerationLiteral
                    .setContentAssistIconProvider(getContentAssistIconProvider());
        } else {
            jsEnumerationLiteral.setIcon(getIcon());
        }
        return jsEnumerationLiteral;
    }

    /**
     * Set qualified name.If set will take precedence over name, hence SHOULD
     * NOT be set where unqualified name is to be used.
     * 
     * @param scriptingName
     *            the scriptingName to set
     */
    public void setQualifiedName(String scriptingName) {
        this.qualifiedName = scriptingName;
    }

    /**
     * Returns true is qualified name is set.
     * 
     * @return
     */
    public boolean requireQualifiedName() {
        return this.qualifiedName != null && this.qualifiedName.length() > 0;
    }

    /**
     * This method will return qualified name if set, otherwise returns the
     * unqualified name.
     * 
     * @see com.tibco.xpd.script.model.internal.client.AbstractDefaultJsDataType#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        if (requireQualifiedName()) {
            return this.qualifiedName;
        }
        return super.getName();
    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.AbstractDefaultJsDataType#getMethodList()
     * 
     * @return
     */
    @Override
    public List<JsMethod> getMethodList() {
        List<JsMethod> methodList = super.getMethodList();
        methodList.add(new EnumerationGetJsMethod());
        return methodList;
    }

    /**
     * 
     * XPD-5923: new get method on enumeration that returns the enumeration for
     * the given enum literal string
     * 
     * @author bharge
     * @since 27 Feb 2014
     */
    private class EnumerationGetJsMethod extends AbstractJsMethod {

        public EnumerationGetJsMethod() {
            super();
        }

        /**
         * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getName()
         * 
         * @return
         */
        @Override
        public String getName() {

            return "get"; //$NON-NLS-1$
        }

        /**
         * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getReturnType()
         * 
         * @return
         */
        @Override
        public JsMethodParam getReturnType() {

            if (this.returnType == null) {

                this.returnType =
                        new BaseJsMethodParam(
                                DefaultJsEnumeration.this.getName(),
                                umlEnumeration,
                                JScriptUtils.getFQType(umlEnumeration), true,
                                false, 1, 1);
            }
            return this.returnType;
        }

        /**
         * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getParameterType()
         * 
         * @return
         */
        @Override
        public List<JsMethodParam> getParameterType() {

            if (inputParamsList == null) {

                inputParamsList = new ArrayList<JsMethodParam>();
                BaseJsMethodParam baseJsMethodParam =
                        new BaseJsMethodParam(JsConsts.STRING, null,
                                JsConsts.STRING, false, false, 1, 1);

                inputParamsList.add(baseJsMethodParam);
            }
            return inputParamsList;
        }

        /**
         * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getComment()
         * 
         * @return
         */
        @Override
        public String getComment() {

            String enumName =
                    DefaultJsEnumeration.this.umlEnumeration.getName();
            return String
                    .format(Messages.DefaultJsEnumeration_EnumGetMethodJavaDoc_longdesc,
                            enumName,
                            enumName,
                            getName(),
                            JsConsts.STRING,
                            enumName);
        }

    }

}
