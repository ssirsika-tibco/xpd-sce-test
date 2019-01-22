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
import com.tibco.xpd.script.model.client.globaldata.CaseRefPaginatedListJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * The class that creates 'find' method that takes arguments as, case class ,
 * [zero based] first record number to retrieve and number of records to
 * retrieve [-1 means All Records].The method returns PaginatedList of Case
 * Refs.This class also provides java doc for this method in the content assist
 * 
 * @author aprasad
 * @since 31 Oct 2013
 */
public class CACFindWithPageSizeMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String FIND = "find"; //$NON-NLS-1$

    public CACFindWithPageSizeMethod(CaseRefJsClass caseRefJsClass,
            String cacName) {

        Class caseClass = caseRefJsClass.getUmlClass();
        CaseRefPaginatedListJsClass caseRefPaginatedJsClass =
                new CaseRefPaginatedListJsClass(caseClass);
        IScriptRelevantData scriptRelevantData =
                createScriptRelevantData(caseClass,
                        caseRefPaginatedJsClass,
                        true);
        this.methodName = FIND;
        this.comment =
                getFindJavaDocComment(caseRefPaginatedJsClass,
                        cacName,
                        caseClass);
        this.returnType =
                new CaseJsMethodParam(caseRefPaginatedJsClass.getName(), null,
                        caseRefPaginatedJsClass.getType(), true, true, 0, -1,
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
        CaseJsMethodParam caseJsMethodParam =
                new CaseJsMethodParam(caseClass.getName(), caseClass,
                        JScriptUtils.getFQType(caseClass), false, false, 1, 1,
                        null);
        /*
         * setting this criteria for isValidAssignment() to allow subType to
         * superType compatibility
         */
        caseJsMethodParam
                .setParamCoercionCriteria(ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE);
        inParam.add(caseJsMethodParam);
        inParam.add(new BaseJsMethodParam(JsConsts.INTEGER, null,
                JsConsts.INTEGER, false, false, 1, 1));
        inParam.add(new BaseJsMethodParam(JsConsts.INTEGER, null,
                JsConsts.INTEGER, false, false, 1, 1));
        return inParam;
    }

    /**
     * 
     * @param caseRefJsClass
     * @param cacName
     * @param caseClass
     * @return String
     */
    private String getFindJavaDocComment(CaseRefJsClass caseRefJsClass,
            String cacName, Class caseClass) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_find_in_range_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_find_in_range_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_find_in_range_param1
                        + "\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_find_in_range_param2
                        + "\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_find_in_range_param3
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_find_in_range_return;
        comment = String.format(comment, cacName + "." + FIND, //$NON-NLS-1$
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
