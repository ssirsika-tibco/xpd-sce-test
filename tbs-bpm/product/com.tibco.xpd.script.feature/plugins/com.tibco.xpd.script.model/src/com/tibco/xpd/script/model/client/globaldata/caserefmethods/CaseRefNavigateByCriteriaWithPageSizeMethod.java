/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata.caserefmethods;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefPaginatedListJsClass;
import com.tibco.xpd.script.model.internal.Messages;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Class that creates 'navigateByCriteriaTo<AssociatedCaseClass>Refs' method
 * that takes String criteria as an argument and 2 Integer arguments, [zero
 * based] first record number to retrieve and number of records to retrieve [-1
 * means All Records].The method returns PaginatedList of <AssociatedCaseClass>
 * Refs. This class also provides java doc for this method in the content
 * assist.
 * 
 * @author aprasad
 * @since 06 Nov 2013
 */
public class CaseRefNavigateByCriteriaWithPageSizeMethod extends
        AbstractJsMethod {

    /**
     * 
     */
    private final String REFS = "Refs"; //$NON-NLS-1$

    /**
     * 
     */
    private final String NAVIGATE_BY = "navigateByCriteriaTo"; //$NON-NLS-1$

    private Class associatedClassType;

    /**
     * @return the associatedClassType
     */
    public Class getAssociatedClassType() {
        return associatedClassType;
    }

    public CaseRefNavigateByCriteriaWithPageSizeMethod(Class caseClass,
            Property property, String caseRefClassName) {

        Class propertyUmlClass =
                (property.getType() instanceof Class) ? (Class) property
                        .getType() : null;

        associatedClassType = propertyUmlClass;
        this.methodName =
                NAVIGATE_BY
                        + changeCaseInitialChar(property.getName() + REFS, true);

        List<JsMethodParam> inParameters = getInputParamList();

        CaseRefPaginatedListJsClass propUmlCaseRefPaginatedJsClass =
                new CaseRefPaginatedListJsClass(propertyUmlClass);
        IScriptRelevantData scriptRelevantData =
                JScriptUtils
                        .convertToCaseUMLScriptRelevantData(propertyUmlClass,
                                propUmlCaseRefPaginatedJsClass,
                                true);

        this.comment =
                getJavaDoc(property,
                        caseRefClassName,
                        methodName,
                        propUmlCaseRefPaginatedJsClass.getName());
        this.inputParamsList = inParameters;
        this.returnType =
                new CaseJsMethodParam(propUmlCaseRefPaginatedJsClass.getType(),
                        null, propUmlCaseRefPaginatedJsClass.getType(), true,
                        true, 0, -1, scriptRelevantData);

    }

    /**
     * @param caseClass
     * @return List<JsMethodParam>
     */
    private List<JsMethodParam> getInputParamList() {

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();
        inParam.add(new BaseJsMethodParam(JsConsts.DQL_STRING, null,
                JsConsts.DQL_STRING, false, false, 1, 1));
        inParam.add(new BaseJsMethodParam(JsConsts.INTEGER, null,
                JsConsts.INTEGER, false, false, 1, 1));
        inParam.add(new BaseJsMethodParam(JsConsts.INTEGER, null,
                JsConsts.INTEGER, false, false, 1, 1));
        return inParam;
    }

    /**
     * 
     * @param caseClass
     * @param property
     * @param caseRefName
     * @param methodName
     * @param propRefClassName
     * @return String
     */
    private String getJavaDoc(Property property, String caseRefName,
            String methodName, String propRefClassName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_in_range_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_in_range_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_in_range_param1
                        + "\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_in_range_param2
                        + "\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_in_range_param3
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_in_range_return;
        comment =
                String.format(comment,
                        propRefClassName,
                        property.getName(),
                        caseRefName,
                        methodName,
                        JsConsts.DQL_STRING,
                        JsConsts.INTEGER);

        return comment;
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
