/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.globaldata.script.cacmethods;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * class that creates 'findBy<CID>' method that takes case id type and returns
 * case ref. also provides java doc for this method in the content assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CACFindByMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String FIND_BY = "findBy"; //$NON-NLS-1$

    /**
     * @param caseRefJsClass
     * @param cacName
     * @param property
     */
    public CACFindByMethod(CaseRefJsClass caseRefJsClass, String cacName,
            Property property) {

        Class caseClass = caseRefJsClass.getUmlClass();
        IScriptRelevantData scriptRelevantData =
                createScriptRelevantData(caseClass, caseRefJsClass, false);
        this.returnType =
                new CaseJsMethodParam(caseRefJsClass.getName(), null,
                        caseRefJsClass.getType(), true, false, 1, 1,
                        scriptRelevantData);
        String propertyType = ""; //$NON-NLS-1$
        /*
         * in case of attributes referring to user defined primitive type, we
         * need to get the actual base primitive type
         */
        if (property.getType() instanceof PrimitiveType) {

            propertyType =
                    JScriptUtils
                            .getBasePrimitiveDataType((PrimitiveType) property
                                    .getType());
        } else if (property.getType() instanceof Class) {

            propertyType = JScriptUtils.getFQType((Class) property.getType());
        } else if (property.getType() != null) {

            propertyType = property.getType().getName();
        }

        String propertyName = ""; //$NON-NLS-1$

        if (property.getType() != null) {
            propertyName = property.getName();
        }
        this.methodName = FIND_BY + changeCaseInitialChar(propertyName, true);
        this.comment =
                getFindByJavaDoc(caseRefJsClass, property, cacName, methodName);
        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();
        BaseJsMethodParam baseJsMethodParam =
                new BaseJsMethodParam(propertyName, null, propertyType, false,
                        false, 1, 1);
        inParam.add(baseJsMethodParam);
        this.inputParamsList = inParam;
    }

    /**
     * creates CaseUMLScriptRelevantData
     * 
     * @param umlClass
     * @param isArray
     * @return IScriptRelevantData
     */
    private IScriptRelevantData createScriptRelevantData(Class umlClass,
            JsClass jsClass, boolean isArray) {

        return JScriptUtils.convertToCaseUMLScriptRelevantData(umlClass,
                jsClass,
                isArray);
    }

    /**
     * 
     * @param caseRefJsClass
     * @param property
     * @param cacName
     * @param methodName
     * @return String
     */
    private String getFindByJavaDoc(CaseRefJsClass caseRefJsClass,
            Property property, String cacName, String methodName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_find_by_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_find_by_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_find_by_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_find_by_return;
        String propertyName = property.getName();

        String propertyType =
                (property.getType() != null) ? property.getType().getName()
                        : ""; //$NON-NLS-1$
        comment =
                String.format(comment,
                        methodName,
                        propertyType,
                        propertyName,
                        cacName,
                        caseRefJsClass.getName());

        return comment;
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getIcon()
     * 
     * @return
     */
    @Override
    public Image getIcon() {

        if (!XpdResourcesPlugin.isInHeadlessMode()) {

            image =
                    Activator.getDefault().getImageRegistry()
                            .get(JsConsts.CASE_REF_TYPE);
        }

        if (null != this.image) {

            return this.image;
        }
        return super.getIcon();
    }

    /**
     * @see com.tibco.xpd.n2.globaldata.script.AbstractCaseAccessJsMethod#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        return methodName;
    }

    /**
     * @see com.tibco.xpd.n2.globaldata.script.AbstractCaseAccessJsMethod#getReturnType()
     * 
     * @return
     */
    @Override
    public JsMethodParam getReturnType() {

        return returnType;
    }

    /**
     * @see com.tibco.xpd.n2.globaldata.script.AbstractCaseAccessJsMethod#getParameterType()
     * 
     * @return
     */
    @Override
    public List<JsMethodParam> getParameterType() {

        return inputParamsList;
    }

    /**
     * @see com.tibco.xpd.n2.globaldata.script.AbstractCaseAccessJsMethod#getComment()
     * 
     * @return
     */
    @Override
    public String getComment() {

        return comment;
    }

}
