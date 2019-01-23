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
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.internal.Messages;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Class that creates 'navigateByCriteriaTo<AssociatedCaseClass>Ref' method that
 * takes Criteria as an argument. This method returns <AssociatedCaseClass>Ref.
 * This class also provides java doc for this method in the content assist.
 * 
 * @author aprasad
 * @since 06 Nov 2013
 */
public class CaseRefNavigateByCriteriaMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String REF = "Ref"; //$NON-NLS-1$

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

    public CaseRefNavigateByCriteriaMethod(Class caseClass, Property property,
            String caseRefClassName) {

        Class propertyUmlClass =
                (property.getType() instanceof Class) ? (Class) property
                        .getType() : null;
        associatedClassType = propertyUmlClass;
        this.methodName =
                NAVIGATE_BY
                        + changeCaseInitialChar(property.getName() + REF, true);

        List<JsMethodParam> inParameters = getInputParamList();

        CaseRefJsClass propUmlCaseRefJsClass =
                new CaseRefJsClass(propertyUmlClass);
        IScriptRelevantData scriptRelevantData =
                JScriptUtils
                        .convertToCaseUMLScriptRelevantData(propertyUmlClass,
                                propUmlCaseRefJsClass,
                                false);

        this.comment =
                getJavaDoc(property,
                        caseRefClassName,
                        methodName,
                        propUmlCaseRefJsClass.getName());
        this.inputParamsList = inParameters;
        this.returnType =
                new CaseJsMethodParam(propUmlCaseRefJsClass.getType(), null,
                        propUmlCaseRefJsClass.getType(), true, false, 1, 1,
                        scriptRelevantData);

    }

    /**
     * @return List<JsMethodParam>
     */
    protected List<JsMethodParam> getInputParamList() {

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();
        inParam.add(new BaseJsMethodParam(JsConsts.CRITERIA, null,
                JsConsts.CRITERIA, false, false, 1, 1));

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
    protected String getJavaDoc(Property property, String caseRefName,
            String methodName, String propRefClassName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_ref_navigate_by_criteria_return;
        comment =
                String.format(comment,
                        propRefClassName,
                        property.getName(),
                        caseRefName,
                        methodName,
                        JsConsts.CRITERIA);

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
