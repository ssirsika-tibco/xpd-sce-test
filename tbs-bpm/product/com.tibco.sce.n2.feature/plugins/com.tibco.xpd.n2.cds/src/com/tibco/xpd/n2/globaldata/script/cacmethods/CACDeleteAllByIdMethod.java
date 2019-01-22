/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.globaldata.script.cacmethods;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * class that creates 'deleteBy' method that takes list of case id type as
 * argument and returns void. also provides java doc for this method in the
 * content assist
 * 
 * @author bharge
 * @since 29 Oct 2013
 */
public class CACDeleteAllByIdMethod extends AbstractJsMethod {

    private final String DELETE_BY = "deleteBy"; //$NON-NLS-1$

    /**
     * Constructor to create deleteBy<CaseIdentifier> method. This method when
     * used in script takes the case identifier list as argument and returns
     * void
     * 
     * @param caseRefJsClass
     * @param cacName
     * @param property
     */
    public CACDeleteAllByIdMethod(CaseRefJsClass caseRefJsClass,
            String cacName, Property property) {

        Class caseClass = caseRefJsClass.getUmlClass();
        String propertyType = ""; //$NON-NLS-1$
        if (property.getType() instanceof PrimitiveType) {

            propertyType =
                    JScriptUtils
                            .getBasePrimitiveDataType((PrimitiveType) property
                                    .getType());
        } else if (property.getType() instanceof Class) {

            propertyType = JScriptUtils.getFQType((Class) property.getType());
        } else if (property.getType() != null) {

            propertyType = property.getType().getName();
        }

        String propertyName = property.getName();
        this.methodName = DELETE_BY + changeCaseInitialChar(propertyName, true);
        this.comment =
                getDeleteByAllJavaDoc(caseRefJsClass,
                        caseClass,
                        property,
                        cacName,
                        methodName);

        List<JsMethodParam> inParam = new ArrayList<JsMethodParam>();
        BaseJsMethodParam baseJsMethodParam =
                new BaseJsMethodParam(propertyName, null, propertyType, false,
                        true, 0, -1);
        inParam.add(baseJsMethodParam);
        this.inputParamsList = inParam;
    }

    /**
     * 
     * @param caseRefJsClass
     * @param caseClass
     * @param property
     * @param cacName
     * @param methodName
     * @return String
     */
    private String getDeleteByAllJavaDoc(CaseRefJsClass caseRefJsClass,
            Class caseClass, Property property, String cacName,
            String methodName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_delete_by_list_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_delete_by_list_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_delete_by_list_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_delete_by_list_return;

        String propertyName = property.getName();
        String propertyType =
                (property.getType() != null) ? property.getType().getName()
                        : ""; //$NON-NLS-1$

        comment =
                String.format(comment,
                        propertyName,
                        cacName,
                        methodName,
                        propertyType,
                        propertyType);
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
