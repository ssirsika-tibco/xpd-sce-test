/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.globaldata.script.cacmethods;

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
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.ParameterCoercionCriteria;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * class that creates 'update' method that takes list of case refs and list of
 * case bom type as arguments and returns list of case ref types. also provides
 * java doc for this method in the content assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CACUpdateMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String UPDATE = "update"; //$NON-NLS-1$

    public CACUpdateMethod(CaseRefJsClass caseRefJsClass, String cacName) {

        Class caseClass = caseRefJsClass.getUmlClass();
        IScriptRelevantData scriptRelevantData =
                createScriptRelevantData(caseClass, caseRefJsClass, true);
        this.methodName = UPDATE;
        this.comment = getUpdateJavaDoc(caseRefJsClass, cacName);
        this.returnType =
                new CaseJsMethodParam(caseRefJsClass.getName(), null,
                        caseRefJsClass.getType(), true, true, 0, -1,
                        scriptRelevantData);
        this.inputParamsList = getInputParamList(caseRefJsClass, caseClass);
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
     * @param caseRefJsClass
     * @param caseClass
     * @return List<JsMethodParam>
     */
    private List<JsMethodParam> getInputParamList(
            CaseRefJsClass caseRefJsClass, Class caseClass) {

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();

        BaseJsMethodParam param1 =
                new BaseJsMethodParam(caseRefJsClass.getName(), caseClass,
                        caseRefJsClass.getType(), false, true, 0, -1);
        /*
         * setting this criteria for isValidAssignment() to allow subType to
         * superType compatibility
         */
        param1.setParamCoercionCriteria(ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE);

        BaseJsMethodParam param2 =
                new BaseJsMethodParam(caseClass.getName(), caseClass,
                        JScriptUtils.getFQType(caseClass), false, true, 0, -1);
        /*
         * setting this criteria for isValidAssignment() to allow subType to
         * superType compatibility
         */
        param2.setParamCoercionCriteria(ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE);

        inParam.add(param1);
        inParam.add(param2);
        return inParam;
    }

    /**
     * 
     * @param caseRefJsClass
     * @param cacName
     * @return String
     */
    private String getUpdateJavaDoc(CaseRefJsClass caseRefJsClass,
            String cacName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_update_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_update_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_update_param1
                        + "\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_update_param2
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_update_return;
        comment = String.format(comment, cacName + "." + UPDATE, //$NON-NLS-1$
                cacName,
                caseRefJsClass.getName(),
                caseRefJsClass.getName(),
                cacName,
                caseRefJsClass.getUmlClass().getName());

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
                            .get(JsConsts.CASE_REF_TYPE_ARRAY);
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
