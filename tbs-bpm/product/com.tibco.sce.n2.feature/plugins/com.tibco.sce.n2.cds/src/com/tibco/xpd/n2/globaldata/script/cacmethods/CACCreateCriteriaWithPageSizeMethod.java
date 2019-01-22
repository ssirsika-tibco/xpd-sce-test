/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.globaldata.script.cacmethods;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.n2.cds.CDSActivator;
import com.tibco.xpd.n2.cds.CdsConsts;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseClassCriteriaJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;

/**
 * class that creates 'createCriteria(String, Integer, Integer)' method that
 * takes DQL String, int starting page index and int page size as arguments and
 * returns Criteria object. Also provides java doc for this method in the
 * content assist
 * 
 * @author bharge
 * @since 6 Feb 2014
 */
public class CACCreateCriteriaWithPageSizeMethod extends AbstractJsMethod {

    private static final String CREATE_CRITERIA = "createCriteria"; //$NON-NLS-1$

    public CACCreateCriteriaWithPageSizeMethod(CaseRefJsClass caseRefJsClass,
            String cacName) {

        Class caseClass = caseRefJsClass.getUmlClass();
        this.inputParamsList = getInputParams(caseClass);
        this.returnType =
                new CaseClassCriteriaJsMethodParam(JsConsts.CRITERIA, null,
                        JsConsts.CRITERIA, true, false, 1, 1, caseClass);
        this.methodName = CREATE_CRITERIA;
        this.comment = getCreateCriteriaWithPageSizeJavaDoc(cacName);
    }

    /**
     * 
     * @param caseClass
     * @return
     */
    private List<JsMethodParam> getInputParams(Class caseClass) {

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();

        CaseClassCriteriaJsMethodParam criteriaStringParam =
                new CaseClassCriteriaJsMethodParam(JsConsts.DQL_STRING, null,
                        JsConsts.DQL_STRING, false, false, 1, 1, caseClass);
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
     * @param cacName
     * @return
     */
    private String getCreateCriteriaWithPageSizeJavaDoc(String cacName) {

        comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_createCriteriaWithPageSize_desc
                        + "\n\n" //$NON-NLS-1$
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_createCriteriaWithPageSize_syntax
                        + "\n\n" //$NON-NLS-1$
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_createCriteriaWithPageSize_param1
                        + "\n" //$NON-NLS-1$
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_createCriteriaWithPageSize_param2
                        + "\n" //$NON-NLS-1$
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_createCriteriaWithPageSize_param3
                        + "\n\n" //$NON-NLS-1$
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_createCriteriaWithPageSize_return;
        comment =
                String.format(comment,
                        cacName,
                        methodName,
                        JsConsts.DQL_STRING,
                        JsConsts.INTEGER,
                        JsConsts.CRITERIA);
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
                    CDSActivator.getDefault().getImageRegistry()
                            .get(CdsConsts.ICON_CREATE_CRITERIA);
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
