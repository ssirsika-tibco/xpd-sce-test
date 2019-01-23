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
 * class that creates 'create' method that takes list of case classes as
 * argument and returns list of case refs. also provides java doc for this
 * method in the content assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CACCreateAllMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String CREATE = "create"; //$NON-NLS-1$

    public CACCreateAllMethod(CaseRefJsClass caseRefJsClass, String cacName) {

        Class caseClass = caseRefJsClass.getUmlClass();
        IScriptRelevantData scriptRelevantData =
                createScriptRelevantData(caseClass, caseRefJsClass, true);
        this.methodName = CREATE;
        this.comment = getCreateAllJavaDoc(caseRefJsClass, caseClass, cacName);
        this.returnType =
                new CaseJsMethodParam(caseRefJsClass.getName(), null,
                        caseRefJsClass.getType(), true, true, 0, -1,
                        scriptRelevantData);

        this.inputParamsList = getInputParamList(caseClass);

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
     * @param caseClass
     * @return List<JsMethodParam>
     */
    private List<JsMethodParam> getInputParamList(Class caseClass) {

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();

        BaseJsMethodParam baseJsMethodParam =
                new BaseJsMethodParam(caseClass.getName(), null,
                        JScriptUtils.getFQType(caseClass), false, true, 0, -1);

        /*
         * setting this criteria for isValidAssignment() to allow subType to
         * superType compatibility
         */
        baseJsMethodParam
                .setParamCoercionCriteria(ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE);
        inParam.add(baseJsMethodParam);
        return inParam;
    }

    /**
     * 
     * @param caseRefJsClass
     * @param caseClass
     * @param cacName
     * @return String
     */
    private String getCreateAllJavaDoc(CaseRefJsClass caseRefJsClass,
            Class caseClass, String cacName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_createList_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_createList_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_createList_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_createList_return;
        comment = String.format(comment, cacName + "." + CREATE, //$NON-NLS-1$
                caseClass.getName(),
                caseRefJsClass.getName(),
                caseClass.getName());
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
