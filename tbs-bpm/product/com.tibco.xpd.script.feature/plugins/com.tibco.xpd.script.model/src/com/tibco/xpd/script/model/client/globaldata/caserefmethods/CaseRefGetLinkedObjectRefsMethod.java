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
 * Class that creates 'get<AssociatedCaseClass>Refs' method that takes 2 Integer
 * arguments, [zero based] first record number to retrieve and number of records
 * to retrieve [-1 means All Records].The method returns PaginatedList of
 * <AssociatedCaseClass> Refs. This class also provides java doc for this method
 * in the content assist.
 * 
 * @author aprasad
 * @since 05 Nov 2013
 */
public class CaseRefGetLinkedObjectRefsMethod extends AbstractJsMethod {

    private static final String REFS = "Refs"; //$NON-NLS-1$

    /**
     * 
     */
    private final String REF = "Ref"; //$NON-NLS-1$

    /**
     * 
     */
    private final String GET = "get"; //$NON-NLS-1$

    public CaseRefGetLinkedObjectRefsMethod(Class caseClass, Property property,
            String caseRefClassName) {

        Class propertyUmlClass =
                (property.getType() instanceof Class) ? (Class) property
                        .getType() : null;

        this.methodName =
                GET + changeCaseInitialChar(property.getName() + REFS, true);

        List<JsMethodParam> inParameters = getInputParamList();

        CaseRefPaginatedListJsClass propUmlCaseRefPaginatedJsClass =
                new CaseRefPaginatedListJsClass(propertyUmlClass);

        IScriptRelevantData scriptRelevantData =
                JScriptUtils
                        .convertToCaseUMLScriptRelevantData(propertyUmlClass,
                                propUmlCaseRefPaginatedJsClass,
                                true);
        this.comment =
                getLinkJavaDoc(property,
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
        BaseJsMethodParam param1 =
                new BaseJsMethodParam(JsConsts.INTEGER, null, JsConsts.INTEGER,
                        false, false, 1, 1);
        inParam.add(param1);
        BaseJsMethodParam param2 =
                new BaseJsMethodParam(JsConsts.INTEGER, null, JsConsts.INTEGER,
                        false, false, 1, 1);
        inParam.add(param2);
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
    private String getLinkJavaDoc(Property property, String caseRefName,
            String methodName, String propRefClassName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_ref_get_linked_object_refs_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_ref_get_linked_object_refs_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_ref_get_linked_object_refs_param1
                        + "\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_ref_get_linked_object_refs_param2
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_ref_get_linked_object_refs_return;
        comment =
                String.format(comment,
                        propRefClassName,
                        property.getName(),
                        caseRefName,
                        methodName,
                        JsConsts.INTEGER);

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
                            .get(JsConsts.CASE_REF_TYPE_ARRAY);
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
