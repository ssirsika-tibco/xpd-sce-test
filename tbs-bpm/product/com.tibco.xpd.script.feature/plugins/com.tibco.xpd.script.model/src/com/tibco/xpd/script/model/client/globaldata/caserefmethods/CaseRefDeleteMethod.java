/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata.caserefmethods;

import java.util.Collections;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.internal.Messages;

/**
 * class that creates 'delete<CaseClass>' method that takes no argument and
 * returns void. also provides java doc for this method in the content assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CaseRefDeleteMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String DELETE = "delete"; //$NON-NLS-1$

    /**
     * Constructor to create delete<CaseClassName> method. This method when used
     * in script takes no arguments and returns void
     * 
     * @param caseClass
     * @param caseRefName
     */
    public CaseRefDeleteMethod(Class caseClass, String caseRefName) {

        this.methodName = DELETE + caseClass.getName();
        this.comment = getDeleteJavaDoc(caseClass, caseRefName, methodName);
        this.inputParamsList = Collections.<JsMethodParam> emptyList();
    }

    /**
     * 
     * @param caseClass
     * @param caseRefName
     * @param methodName
     * @return String
     */
    private String getDeleteJavaDoc(Class caseClass, String caseRefName,
            String methodName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_ref_delete_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_ref_delete_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_ref_delete_return;
        comment = String.format(comment, caseRefName, methodName);
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
        return null;
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
