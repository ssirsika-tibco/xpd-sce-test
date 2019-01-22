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
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseRefPaginatedListJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * The class that creates 'findByCriteria' method that takes a Criteria String
 * and returns PaginatedList of Case Refs, [zero based] first record number to
 * retrieve and number of records to retrieve [-1 means All Records]. This class
 * also provides java doc for this method in the content assist.
 * 
 * @author aprasad
 * @since 31 Oct 2013
 */
public class CACFindByCriteriaWithPageSizeMethod extends AbstractJsMethod {

    /**
     * Method name
     */
    private final String FIND_BY_CRITERIA = "findByCriteria"; //$NON-NLS-1$

    /**
     * Constructor takes CaseRef type, CaseAccessClass Name, and
     * ScriptRelevantData.
     * 
     * @param caseRefJsClass
     * @param cacName
     * @param scriptRelevantData
     */
    public CACFindByCriteriaWithPageSizeMethod(CaseRefJsClass caseRefJsClass,
            String cacName) {

        Class caseClass = caseRefJsClass.getUmlClass();
        CaseRefPaginatedListJsClass caseRefPaginatedJsClass =
                new CaseRefPaginatedListJsClass(caseClass);

        IScriptRelevantData scriptRelevantData =
                createScriptRelevantData(caseClass,
                        caseRefPaginatedJsClass,
                        true);
        this.returnType =
                new CaseJsMethodParam(caseRefPaginatedJsClass.getName(), null,
                        caseRefPaginatedJsClass.getType(), true, true, 0, -1,
                        scriptRelevantData);
        this.methodName = FIND_BY_CRITERIA;
        this.comment =
                getFindByCriteriaJavaDoc(caseRefPaginatedJsClass,
                        cacName,
                        methodName);

        this.inputParamsList =
                getInputParam(caseRefPaginatedJsClass.getUmlClass());
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
     * @param class1
     * @return
     */
    private List<JsMethodParam> getInputParam(Class caseClass) {

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();

        BaseJsMethodParam criteriaStringParam =
                new BaseJsMethodParam(JsConsts.DQL_STRING, null,
                        JsConsts.DQL_STRING, false, false, 1, 1);
        inParam.add(criteriaStringParam);
        BaseJsMethodParam startIndexParam =
                new BaseJsMethodParam(JsConsts.INTEGER, null, JsConsts.INTEGER,
                        false, false, 1, 1);
        inParam.add(startIndexParam);
        BaseJsMethodParam numRecsParam =
                new BaseJsMethodParam(JsConsts.INTEGER, null, JsConsts.INTEGER,
                        false, false, 1, 1);
        inParam.add(numRecsParam);
        return inParam;
    }

    /**
     * 
     * @param caseRefJsClass
     * @param cacName
     * @param methodName
     * @return JavaDoc Comment for the method.
     */
    private String getFindByCriteriaJavaDoc(CaseRefJsClass caseRefJsClass,
            String cacName, String methodName) {

        comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_find_by_criteria_in_range_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_find_by_criteria_in_range_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_find_by_criteria_in_range_param1
                        + "\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_find_by_criteria_in_range_param2
                        + "\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_find_by_criteria_in_range_param3
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_find_by_criteria_in_range_return;
        comment =
                String.format(comment,
                        methodName,
                        JsConsts.DQL_STRING,
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
                            .get(JsConsts.CASE_REF_TYPE_ARRAY);
        }

        if (null != this.image) {

            return this.image;
        }
        return super.getIcon();
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        return methodName;
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getReturnType()
     * 
     * @return
     */
    @Override
    public JsMethodParam getReturnType() {

        return returnType;
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getParameterType()
     * 
     * @return
     */
    @Override
    public List<JsMethodParam> getParameterType() {

        return inputParamsList;
    }

    /**
     * @see com.tibco.xpd.script.model.client.AbstractJsMethod#getComment()
     * 
     * @return
     */
    @Override
    public String getComment() {

        return comment;
    }

}
