/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.client.globaldata.CaseRefJsClass;
import com.tibco.xpd.script.model.client.globaldata.CaseUMLScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Factory class for the creation of IScriptRelevantData
 * 
 * @author mtorres
 * 
 */
public class DefaultScriptRelevantDataFactory implements
        IScriptRelevantDataFactory {

    @Override
    public IScriptRelevantData createScriptRelevantData(String name,
            String type, boolean isArray, IScriptRelevantData genericContext,
            boolean isReadOnly, Object extendedInfo) {

        if (name != null && type != null) {
            /* check if extended info is union */
            if (extendedInfo instanceof JsAttribute) {

                JsAttribute jsAttribute = (JsAttribute) extendedInfo;
                /* return union script relevant data */
                if (JScriptUtils.isUnion(jsAttribute)) {

                    DefaultUnionScriptRelevantData unionScriptRelevantData =
                            new DefaultUnionScriptRelevantData(name, type,
                                    isArray);
                    unionScriptRelevantData
                            .setGenericContextType(genericContext);
                    unionScriptRelevantData.setReadOnly(isReadOnly);
                    unionScriptRelevantData.setExtendedInfo(extendedInfo);
                    return unionScriptRelevantData;
                }
            }
            DefaultScriptRelevantData scriptRelevantData =
                    new DefaultScriptRelevantData(name, type, isArray);

            /**
             * Sid XPD-5920: __REMOVE__ fix for XPD-5864. Which prevented static
             * Class method Object type parameters from being coerced as
             * generics to the static class itself.
             * 
             * This only worked for static classes and therefore a different fix
             * was required to work for non-static class method Object type
             * parameters (such as Criteria.setQueryParameter(String, Object).
             * 
             * XPD-5920 fixed this properly by STOPPING the coercion being
             * performed on Object parameters in the first placed (and tidying
             * up the fall out) which is the correct thing to do becuase the E
             * type is the type to use for coercion of generics behaviour.
             * 
             * So no it's ok to set generic context even for static class,
             * because nothing (i.e.
             * AbstractExporessionValidator.convertSpecificToGenericType() will
             * attempt to coerce the Object parameter to the generic context
             * anyway!
             */

            scriptRelevantData.setGenericContextType(genericContext);

            scriptRelevantData.setReadOnly(isReadOnly);
            scriptRelevantData.setExtendedInfo(extendedInfo);
            return scriptRelevantData;
        }
        return null;
    }

    @Override
    public IUMLScriptRelevantData createUMLScriptRelevantData(String name,
            String className, boolean isArray, JsClass jsClass,
            IScriptRelevantData genericContext, boolean isReadOnly,
            Object extendedInfo) {

        if (name != null && className != null && jsClass != null) {

            if (jsClass instanceof CaseRefJsClass) {
                CaseUMLScriptRelevantData caseUMLScriptRelevantData =
                        new CaseUMLScriptRelevantData(name, jsClass.getName(),
                                isArray, jsClass,
                                ((CaseRefJsClass) jsClass).getType());
                caseUMLScriptRelevantData.setGenericContextType(genericContext);
                caseUMLScriptRelevantData.setReadOnly(isReadOnly);
                caseUMLScriptRelevantData.setExtendedInfo(extendedInfo);
                return caseUMLScriptRelevantData;
            }

            DefaultUMLScriptRelevantData umlScriptRelevantData =
                    new DefaultUMLScriptRelevantData(name, className, isArray,
                            jsClass);
            umlScriptRelevantData.setGenericContextType(genericContext);
            umlScriptRelevantData.setReadOnly(isReadOnly);
            umlScriptRelevantData.setExtendedInfo(extendedInfo);
            return umlScriptRelevantData;
        }
        return null;
    }

    @Override
    public IUMLScriptRelevantData createUMLScriptRelevantData(String name,
            boolean isArray, Class umlClass,
            IScriptRelevantData genericContext, boolean isReadOnly,
            Object extendedInfo) {

        if (name != null && umlClass != null) {

            DefaultJsClass jsClass = new DefaultJsClass(umlClass);
            jsClass.setContentAssistIconProvider(JScriptUtils
                    .getJsContentAssistIconProvider());
            DefaultUMLScriptRelevantData umlScriptRelevantData =
                    new DefaultUMLScriptRelevantData(name, jsClass.getName(),
                            isArray, jsClass);
            umlScriptRelevantData.setGenericContextType(genericContext);
            umlScriptRelevantData.setReadOnly(isReadOnly);
            umlScriptRelevantData.setExtendedInfo(extendedInfo);
            return umlScriptRelevantData;
        }
        return null;
    }

}
