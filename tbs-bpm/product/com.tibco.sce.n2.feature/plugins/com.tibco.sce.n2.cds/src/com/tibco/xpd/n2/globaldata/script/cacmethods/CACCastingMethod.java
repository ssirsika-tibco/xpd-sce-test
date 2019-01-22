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
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.ParameterCoercionCriteria;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * class that creates '<<sub>>Ref' method that takes super class as argument and
 * returns sub class ref. also provides java doc for this method in the content
 * assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CACCastingMethod extends AbstractJsMethod {

    public CACCastingMethod(CaseRefJsClass caseRefJsClass, String cacName,
            Class superClass) {

        String superCaseRefName = superClass.getName() + "Ref"; //$NON-NLS-1$
        String superCaseFQN = JScriptUtils.getFQType(superClass) + "Ref"; //$NON-NLS-1$

        this.methodName =
                changeCaseInitialChar(caseRefJsClass.getName(), false);
        this.comment =
                getCACCastJavaDoc(caseRefJsClass, cacName, superCaseRefName);

        Class caseClass = caseRefJsClass.getUmlClass();
        IScriptRelevantData scriptRelevantData =
                createScriptRelevantData(caseClass, caseRefJsClass, false);
        this.returnType =
                new CaseJsMethodParam(cacName, null, caseRefJsClass.getType(),
                        true, false, 1, 1, scriptRelevantData);

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();
        // XPD-5705: As input parameter is CaseRef, it should be
        // CaseJsMethodParam and not BaseJsMethodParam
        CaseRefJsClass superCaseRefJsClass = new CaseRefJsClass(superClass);

        IScriptRelevantData superScriptRelevantData =
                createScriptRelevantData(superClass, superCaseRefJsClass, false);

        CaseJsMethodParam methodParam =
                new CaseJsMethodParam(superCaseRefName, null,
                        superCaseRefJsClass.getType(), false, false, 1, 1,
                        superScriptRelevantData);
        /*
         * setting this criteria for isValidAssignment() to allow superType to
         * subType compatibility
         */
        methodParam
                .setParamCoercionCriteria(ParameterCoercionCriteria.SUPER_TYPE_TO_SUB_TYPE);
        inParam.add(methodParam);

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
     * Generates Java Doc for the cast method in the CAC type of the given Case
     * BOM Class.
     * 
     * @param caseClass
     * @param superClass
     * @return
     */
    private String getCACCastJavaDoc(CaseRefJsClass caseRefJsClass,
            String cacName, String superCaseRefName) {

        String createJavaDocComment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_cast_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_cast_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_cast_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_cast_return;
        createJavaDocComment =
                String.format(createJavaDocComment,
                        cacName,
                        changeCaseInitialChar(caseRefJsClass.getName(), false),
                        caseRefJsClass.getName(),
                        superCaseRefName);
        return createJavaDocComment;
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
