/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.globaldata.script.cacmethods;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.ParameterCoercionCriteria;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;

/**
 * class that creates 'deleteRefs' method that takes list of case refs to be
 * deleted as argument and returns void. also provides java doc for this method
 * in the content assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CACDeleteRefsMethod extends AbstractJsMethod {

    private final String DELETE_REFS = "deleteRefs"; //$NON-NLS-1$

    /**
     * Constructor to create deleteRefs method. This method when used in script
     * takes list of case refs to be deleted as argument and returns void
     * 
     * @param caseRefJsClass
     * @param cacName
     */
    public CACDeleteRefsMethod(CaseRefJsClass caseRefJsClass, String cacName) {

        Class caseClass = caseRefJsClass.getUmlClass();
        this.methodName = DELETE_REFS;
        this.comment = getDeleteRefsJavaDoc(caseRefJsClass, caseClass, cacName);
        this.inputParamsList = getInputParamList(caseRefJsClass, caseClass);
    }

    /**
     * @param caseRefJsClass
     * @param caseClass
     * @return List<JsMethodParam>
     */
    private List<JsMethodParam> getInputParamList(
            CaseRefJsClass caseRefJsClass, Class caseClass) {

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();

        BaseJsMethodParam baseJsMethodParam =
                new BaseJsMethodParam(caseRefJsClass.getName(), null,
                        caseRefJsClass.getType(), false, true, 0, -1);
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
    private String getDeleteRefsJavaDoc(CaseRefJsClass caseRefJsClass,
            Class caseClass, String cacName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_deleteRefs_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_deleteRefs_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_deleteRefs_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_deleteRefs_return;
        comment = String.format(comment, cacName + "." + DELETE_REFS, //$NON-NLS-1$
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

        return null;
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
