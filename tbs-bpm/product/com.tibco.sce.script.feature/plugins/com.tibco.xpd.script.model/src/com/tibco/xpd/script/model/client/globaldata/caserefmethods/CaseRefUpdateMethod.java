/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata.caserefmethods;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.ParameterCoercionCriteria;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.internal.Messages;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * class that creates 'update<CaseClass>' method that takes case bom type
 * argument and returns case ref. also provides java doc for this method in the
 * content assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CaseRefUpdateMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String UPDATE = "update"; //$NON-NLS-1$

    public CaseRefUpdateMethod(Class caseClass, CaseRefJsClass caseRefJsClass,
            String caseRefName, String caseRefFullyQualifiedName) {

        this.methodName = UPDATE + caseClass.getName();
        this.comment = getUpdateJavaDoc(caseClass, caseRefName, methodName);

        IScriptRelevantData scriptRelevantData =
                JScriptUtils.convertToCaseUMLScriptRelevantData(caseClass,
                        caseRefJsClass,
                        false);

        this.returnType =
                new CaseJsMethodParam(caseRefName, null,
                        caseRefFullyQualifiedName, true, false, 1, 1,
                        scriptRelevantData);
        this.inputParamsList = getInputParamsList(caseClass);
    }

    /**
     * @param caseClass
     * @return List<JsMethodParam>
     */
    private List<JsMethodParam> getInputParamsList(Class caseClass) {

        List<JsMethodParam> inParameters = new ArrayList<JsMethodParam>();

        BaseJsMethodParam baseJsMethodParam =
                new BaseJsMethodParam(caseClass.getName(), caseClass,
                        JScriptUtils.getFQType(caseClass), false, false, 1, 1);
        /*
         * setting this criteria for isValidAssignment() to allow subType to
         * superType compatibility
         */
        baseJsMethodParam
                .setParamCoercionCriteria(ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE);
        inParameters.add(baseJsMethodParam);
        return inParameters;
    }

    /**
     * 
     * @param caseClass
     * @param caseRefName
     * @param methodName
     * @return String
     */
    private String getUpdateJavaDoc(Class caseClass, String caseRefName,
            String methodName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_ref_update_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_ref_update_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_ref_update_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_ref_update_return;
        comment =
                String.format(comment,
                        caseRefName,
                        methodName,
                        caseClass.getName(),
                        caseClass.getName(),
                        caseRefName);
        return comment;
    }

    /**
     * @see com.tibco.xpd.script.model.client.globaldata.AbstractCaseRefJsMethod#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        return methodName;
    }

    /**
     * @see com.tibco.xpd.script.model.client.globaldata.AbstractCaseRefJsMethod#getReturnType()
     * 
     * @return
     */
    @Override
    public JsMethodParam getReturnType() {

        return returnType;
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
     * @see com.tibco.xpd.script.model.client.globaldata.AbstractCaseRefJsMethod#getParameterType()
     * 
     * @return
     */
    @Override
    public List<JsMethodParam> getParameterType() {

        return inputParamsList;
    }

    /**
     * @see com.tibco.xpd.script.model.client.globaldata.AbstractCaseRefJsMethod#getComment()
     * 
     * @return
     */
    @Override
    public String getComment() {

        return comment;
    }

}
