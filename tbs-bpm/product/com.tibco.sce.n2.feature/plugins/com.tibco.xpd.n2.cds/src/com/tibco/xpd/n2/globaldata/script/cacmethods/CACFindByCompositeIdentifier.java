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

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.Activator;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.AbstractJsMethod;
import com.tibco.xpd.script.model.client.BaseJsMethodParam;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseJsMethodParam;
import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * class that creates 'findByCompositeIdentifier' method that takes all the
 * composite case ids as arguments and returns case ref. also provides java doc
 * for this method in the content assist
 * 
 * @author bharge
 * @since 6 Nov 2013
 */
public class CACFindByCompositeIdentifier extends AbstractJsMethod {

    private final String METHOD_NAME = "findByCompositeIdentifier"; //$NON-NLS-1$

    /**
     * Constructor to create findByCompositeIdentifier method. This method when
     * used in script takes all the composite identifiers of the case class as
     * arguments (the order of arguments will be the order in which they are
     * created in the bom)
     * 
     * @param caseRefJsClass
     * @param cacName
     * @param compositeIdProperties
     */
    public CACFindByCompositeIdentifier(CaseRefJsClass caseRefJsClass,
            String cacName, List<Property> compositeIdProperties) {

        Class caseClass = caseRefJsClass.getUmlClass();
        this.methodName = METHOD_NAME;
        this.comment =
                getJavaDocComment(caseRefJsClass,
                        compositeIdProperties,
                        cacName);
        IScriptRelevantData scriptRelevantData =
                createScriptRelevantData(caseClass, caseRefJsClass, false);
        this.returnType =
                new CaseJsMethodParam(caseRefJsClass.getName(), null,
                        caseRefJsClass.getType(), true, false, 1, 1,
                        scriptRelevantData);
        this.inputParamsList = getInputParams(compositeIdProperties);
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
     * @param compositeIdProperties
     * @return List<JsMethodParam>
     */
    private List<JsMethodParam> getInputParams(
            List<Property> compositeIdProperties) {

        List<JsMethodParam> inputParamsList = new ArrayList<JsMethodParam>();
        for (Property property : compositeIdProperties) {

            String propertyName = property.getName();
            String propertyType = getPropertyType(property);
            BaseJsMethodParam baseJsMethodParam =
                    new BaseJsMethodParam(propertyName, null, propertyType,
                            false, false, 1, 1);
            inputParamsList.add(baseJsMethodParam);
        }
        return inputParamsList;
    }

    /**
     * @param property
     * @return String
     */
    private String getPropertyType(Property property) {

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
        return propertyType;
    }

    /**
     * @param caseRefJsClass
     * @param compositeIdProperties
     * @param cacName
     * @return String
     */
    private String getJavaDocComment(CaseRefJsClass caseRefJsClass,
            List<Property> compositeIdProperties, String cacName) {

        String comment =
                Messages.ScriptContentAssist_javadoc_comment_definition_and_usage
                        + Messages.GlobalDataGenerator_comment_cac_find_by_compositeIds_description
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_syntax
                        + Messages.GlobalDataGenerator_comment_cac_find_by_compositeIds_syntax
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_param
                        + Messages.GlobalDataGenerator_comment_cac_find_by_compositeIds_param
                        + "\n\n"
                        + Messages.ScriptContentAssist_javadoc_comment_return
                        + Messages.GlobalDataGenerator_comment_cac_find_by_compositeIds_return;
        StringBuilder sb = new StringBuilder();
        /*
         * building the list of arguments to be shown such as -
         * 
         * findByCompositeIdentifier(Text customerName, Text customerPostcode)
         */
        for (Property property : compositeIdProperties) {

            String propertyName = property.getName();
            String propertyType = getPropertyType(property);

            sb.append(propertyType);
            sb.append(" "); //$NON-NLS-1$
            sb.append(propertyName);
            sb.append(","); //$NON-NLS-1$
        }
        String argsList = sb.substring(0, sb.length() - 1);
        comment =
                String.format(comment,
                        cacName,
                        methodName,
                        argsList,
                        caseRefJsClass.getName());

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
                            .get(JsConsts.CASE_REF_TYPE);
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
