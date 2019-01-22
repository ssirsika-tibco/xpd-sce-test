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
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.ParameterCoercionCriteria;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * class that creates 'read' method that takes list of case refs as argument and
 * returns list of case classes. also provides java doc for this method in the
 * content assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CACReadMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String READ = "read"; //$NON-NLS-1$

    public CACReadMethod(CaseRefJsClass caseRefJsClass, String cacName) {

        Class caseClass = caseRefJsClass.getUmlClass();
        this.methodName = READ;
        this.comment = getReadJavaDoc(caseRefJsClass, caseClass, cacName);
        IScriptRelevantData scriptRelevantData =
                JScriptUtils.convertToCaseUMLScriptRelevantData(caseClass,
                        caseRefJsClass,
                        true);
        this.returnType =
                new CaseJsMethodParam(caseClass.getName(), caseClass,
                        JScriptUtils.getFQType(caseClass), true, true, 0, -1,
                        scriptRelevantData);

        this.inputParamsList =
                getInputParamList(caseRefJsClass, caseClass, scriptRelevantData);

    }

    /**
     * @param caseRefJsClass
     * @param caseClass
     * @param scriptRelevantData
     * @return List<JsMethodParam>
     */
    private List<JsMethodParam> getInputParamList(
            CaseRefJsClass caseRefJsClass, Class caseClass,
            IScriptRelevantData scriptRelevantData) {

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();
        CaseJsMethodParam inputParamForRead =
                new CaseJsMethodParam(caseRefJsClass.getName(), caseClass,
                        caseRefJsClass.getType(), false, true, 0, -1,
                        scriptRelevantData);
        /*
         * setting this criteria for isValidAssignment() to allow subType to
         * superType compatibility
         */
        inputParamForRead
                .setParamCoercionCriteria(ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE);
        inParam.add(inputParamForRead);
        return inParam;
    }

    /**
     * 
     * @param caseRefJsClass
     * @param caseClass
     * @param cacName
     * @return String
     */
    private String getReadJavaDoc(CaseRefJsClass caseRefJsClass,
            Class caseClass, String cacName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_read_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_read_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_read_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_read_return;
        comment = String.format(comment, cacName + "." + READ, //$NON-NLS-1$
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
                            .get(JsConsts.CDS_FACTORY_ARRAY);
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
