/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.js.validation.rules;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public abstract class AbstractProcessRelevantDataScriptRule extends
        PackageValidationRule {

    protected Map<String, String> getAdditionalInfoMap(ErrorMessage errorMessage) {
        Map<String, String> additionalInfoMap = new HashMap<String, String>();
        additionalInfoMap.put("LineNumber", Integer.toString(errorMessage //$NON-NLS-1$
                .getLineNumber()));
        additionalInfoMap.put("ColumnNumber", Integer.toString(errorMessage //$NON-NLS-1$
                .getColumnNumber()));
        additionalInfoMap.put("ErrorMessage", errorMessage.getErrorMessage()); //$NON-NLS-1$
        additionalInfoMap.put("ScriptContext", getScriptContext()); //$NON-NLS-1$
        return additionalInfoMap;
    }

    protected void reportError(EObject eObject, List<ErrorMessage> errorMsgList) {
        reportIssue(eObject, errorMsgList, getErrorId());
    }

    protected void reportWarning(EObject eObject,
            List<ErrorMessage> errorMsgList) {
        reportIssue(eObject, errorMsgList, getWarningId());
    }

    protected void reportIssue(EObject eObject,
            List<ErrorMessage> issueMsgList, String issueId) {
        for (ErrorMessage errorMessage : issueMsgList) {
            List<String> tempMsgList = getSubstitutionList(errorMessage);
            if (tempMsgList == null) {
                tempMsgList = Collections.emptyList();
            }
            Map<String, String> additionalInfoMap =
                    getAdditionalInfoMap(errorMessage);
            if (eObject instanceof ScriptInformation) {
                EObject parent = eObject.eContainer();
                if (parent instanceof DataMapping) {
                    DataMapping mapping = (DataMapping) parent;
                    String target = DataMappingUtil.getTarget(mapping);
                    additionalInfoMap
                            .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                    target);
                }
            }
            addIssue(issueId, eObject, tempMsgList, additionalInfoMap);
        }
    }

    protected abstract List<String> getSubstitutionList(
            ErrorMessage errorMessage);

    protected abstract String getErrorId();

    protected abstract String getWarningId();

    protected abstract String getScriptGrammar();

    protected abstract String getScriptContext();

    /**
     * This method returns the tool from the scope and does necessary
     * initialisations before returning it. at this stage for XPDL Package scope
     * Enumerations Cache.
     * 
     * @param scriptToolClass
     * @param validateObject
     * @return ScriptTool, returns ScriptTool initialised with the
     *         {@link PackageScopeEnumCache} for the given Process Package.
     */
    protected final ScriptTool getScriptTool(
            Class<? extends ScriptTool> scriptToolClass, EObject validateObject) {
        /*
         * //XPD-4936 :Performance improvement for scripts validations Get
         * Enumeration Package Scope Cache tool and set it on whatever script
         * tool it is the caller wants to creates.
         */
        IValidationScope validationScope = getScope();

        PackageScopeEnumCache enumCache = null;

        Package pkg = Xpdl2ModelUtil.getPackage(validateObject);
        if (pkg != null) {
            // get the cache of enumerations in scope of this process package
            enumCache =
                    validationScope.getTool(PackageScopeEnumCache.class, pkg);
        }
        // Initialise the ScriptTool with the enumerations cache , which will be
        // passed-on to the data providers, to handle ambiguous situations when
        // multiple enumerations with same name exist in the process package
        // scope.
        ScriptTool tool =
                validationScope.getTool(scriptToolClass, validateObject);
        if (tool != null) {
            tool.setPackageScopeEnumCache(enumCache);
        }

        return tool;
    }

}
