/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata.caserefmethods;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.ParameterCoercionCriteria;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.internal.Messages;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * class that creates 'link<AssociatedCaseClass>' method that takes list of
 * associated case ref as argument when multiplicity is > 1 and returns void.
 * also provides java doc for this method in the content assist
 * 
 * @author bharge
 * @since 30 Oct 2013
 */
public class CaseRefUnlinkAllMethod extends AbstractJsMethod {

    /**
     * 
     */
    private final String REF = "Ref"; //$NON-NLS-1$

    /**
     * 
     */
    private final String UNLINK = "unlink"; //$NON-NLS-1$

    public CaseRefUnlinkAllMethod(Class caseClass, Property property,
            boolean multiValued, String caseRefName) {

        Class propertyUmlClass =
                (property.getType() instanceof Class) ? (Class) property
                        .getType() : null;

        this.methodName =
                UNLINK + changeCaseInitialChar(property.getName(), true);
        comment =
                getUnlinkJavaDoc(caseClass,
                        property,
                        multiValued,
                        caseRefName,
                        methodName);

        CaseRefJsClass propertyUmlCaseRefJsClass =
                new CaseRefJsClass(propertyUmlClass);

        IScriptRelevantData scriptRelevantData =
                JScriptUtils
                        .convertToCaseUMLScriptRelevantData(propertyUmlClass,
                                propertyUmlCaseRefJsClass,
                                true);

        List<JsMethodParam> inParameters = new ArrayList<JsMethodParam>();

        CaseJsMethodParam caseJsMethodParam =
                new CaseJsMethodParam(caseRefName, propertyUmlClass,
                        propertyUmlCaseRefJsClass.getType(), false,
                        multiValued, 1, 1, scriptRelevantData);
        /*
         * setting this criteria for isValidAssignment() to allow subType to
         * superType compatibility
         */
        caseJsMethodParam
                .setParamCoercionCriteria(ParameterCoercionCriteria.SUB_TYPE_TO_SUPER_TYPE);
        inParameters.add(caseJsMethodParam);

        this.inputParamsList = inParameters;
        this.returnType = null;
    }

    /**
     * 
     * @param caseClass
     * @param property
     * @param isMultiple
     * @param caseRefName
     * @param methodName
     * @return String
     */
    private String getUnlinkJavaDoc(Class caseClass, Property property,
            boolean isMultiple, String caseRefName, String methodName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_ref_unlink_attributes_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_ref_unlink_attributes_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_ref_unlink_attributes_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_ref_unlink_attributes_return;
        comment =
                String.format(comment,
                        property.getName(),
                        caseRefName,
                        caseRefName,
                        methodName,
                        changeCaseInitialChar(property.getName() + REF, true),
                        changeCaseInitialChar(property.getName() + REF, true));

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
        return null;
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
