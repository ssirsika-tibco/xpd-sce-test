/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.js.validation.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.js.validation.tools.PackageScopeEnumCache;
import com.tibco.xpd.js.validation.tools.ScriptTool;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This is designed as a replacement for the old {@link AbstractScriptRule} and
 * to take advantage of the new ExpressionScopeProvider which allows us to only
 * validate EXACTLY those expressions that have ACTUALLY changed during live
 * validation.
 * <p>
 * However, because existing script validation framework is quite a tangle that
 * will be very hard to unpick without a complete re-write we are also using the
 * EXISTING {@link ScriptTool} framework to provide the acutal validation.
 * <p>
 * So when we get an Expression to validate we ask the sub-class to create the
 * script tool for the script type (ScriptTask, Open, Initiate, DataMapping and
 * so on). And it will return the existing ScriptTool that has always been
 * there.
 * 
 * @author aallway
 * @since 13 Sep 2013
 */
public abstract class AbstractExpressionRule extends Xpdl2ValidationRule {

    /**
     * If the expression is the type that the sub-class is interested in
     * validating then it should return the appropriate {@link ScriptTool}
     * implementation for type of script it validates.
     * <p>
     * Otherwise it should return <code>null</code>
     * <p>
     * NOTE that when using old {@link ScriptTool} IT is designed to go and find
     * the script of type it is interested in. So it is likely that in this case
     * the sub-class will get the expression-issue-host that it's
     * {@link ScriptTool} has always traditionally used (such as parent
     * activity) and then use that to create script tool. This may then make it
     * look odd if debugging thru because the script tool will go and look up
     * the script from the activity even though the we have it here when
     * creating the tool. JUST A FACT OF LIFE at the moment because there is
     * such a proliferation of {@link ScriptTool} classes and their factories
     * and no time to do a complete re-write.
     * 
     * @param expression
     * 
     * @return The {@link ScriptTool} for the expression or <code>null</code> if
     *         expression is not the one the sub-class validates
     */
    protected abstract ScriptTool createScriptToolIfInterested(
            Expression expression);

    /**
     * Return the host object of the expression (to be used for uniquely
     * identifying 'owner' of the expression (such as Activity, Transition,
     * ScriptInformation etc).
     * <p>
     * This is passed to the {@link ScriptTool} returned by
     * {@link #createScriptToolIfInterested(Expression)} (existing ScriptTools
     * all use this Id to cache their result because a SINGLE call to a script
     * tool for a given script will execute ALL destination validations,
     * therefore to prevent re-run of second validation for each destination
     * (including built in javaScript dest) the script tool caches all results
     * and filters by currently active destination in the validation scope.
     * 
     * @param expression
     * 
     * @return The host object for the expressoin issues and {@link ScriptTool}
     */
    protected abstract UniqueIdElement getExpressionHostForScriptTool(
            Expression expression);

    /**
     * @return The validation issue id to use for errors.
     */
    protected abstract String getErrorId();

    /**
     * @return The validation issue id to use for warnings.
     */
    protected abstract String getWarningId();

    /**
     * @return The script context (added to additional info in problem markers,
     *         values seem to be defined in
     *         {@link ProcessScriptContextConstants}
     */
    protected abstract String getScriptContext();

    /** Sid ACE-1378 Track that we only complain once above validation off. */
    private static boolean validationOffErrorDone = false;

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.Xpdl2ValidationRule#validate(java.lang.Object)
     * 
     * @param o
     */
    @Override
    protected void validate(Object o) {
        
        /*
         * Sid ACE-1378 - check the tibco.scripts.validation.off=true and
         * disable script valdiation if so.
         */
        String validationOff =
                System.getProperty("xpd.scripts.validation.off"); //$NON-NLS-1$
        if (validationOff != null && "true".equalsIgnoreCase(validationOff)) { //$NON-NLS-1$
            if (!validationOffErrorDone) {
                validationOffErrorDone = true;
                XpdResourcesPlugin.getDefault().getLogger().error(
                        "Script validations disabled (xpd.scripts.validation.off=true)"); //$NON-NLS-1$
            }
            return;
        }
        
        if (o instanceof Expression) {
            Expression expression = (Expression) o;

            /* Check is grammar we are interested in. */
            if (getScriptGrammar().equals(expression.getScriptGrammar())) {

                /*
                 * Ask sub-class for script tool (IF it handles this expression
                 * type, otherwise it'll return null).
                 */
                ScriptTool scriptTool =
                        createScriptToolIfInterested(expression);

                if (scriptTool != null) {
                    /*
                     * The existing OLD ScriptTool implementations do not work
                     * directly on Expression - instead they generally work on a
                     * nominal HOST container for the expression.
                     * 
                     * Therefore rather than re-write the entire proliferation
                     * of ScriptTool implementations we will simply ask the
                     * sub-class for the appropriate host to pass the script
                     * tool
                     */
                    UniqueIdElement expressionIssueHost =
                            getExpressionHostForScriptTool(expression);

                    if (expressionIssueHost != null) {
                        /*
                         * perform any additional configuration set up on the
                         * script tool
                         */
                        setupScriptTool(scriptTool, expression);

                        Destination currentDestination =
                                getScope().getCurrentDestination();

                        List<ErrorMessage> errorList =
                                scriptTool.getErrorList(expressionIssueHost
                                        .getId(), currentDestination);

                        if (errorList != null) {
                            reportError(expressionIssueHost, errorList);
                        }

                        List<ErrorMessage> warningList =
                                scriptTool.getWarningList(expressionIssueHost
                                        .getId(), currentDestination);

                        if (warningList != null) {
                            reportWarning(expressionIssueHost, warningList);
                        }

                        performAdditionalValidation(expression,
                                expressionIssueHost);
                    }
                }
            }
        }
    }

    /**
     * This allows sub-classes to perform additional validation and raise issues
     * as required.
     * 
     * @param expression
     * @param expressionIssueHost
     */
    protected void performAdditionalValidation(Expression expression,
            UniqueIdElement expressionIssueHost) {

    }

    /**
     * 
     * @param errorMessage
     * @return standard Additional info map to add to problems
     */
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

    /**
     * Report the error problem markers. *
     * 
     * 
     * @param eObject
     * @param errorMsgList
     */
    protected void reportError(EObject eObject, List<ErrorMessage> errorMsgList) {
        reportIssue(eObject, errorMsgList, getErrorId());
    }

    /**
     * Report the warning problem markers.
     * 
     * 
     * @param eObject
     * @param errorMsgList
     */
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

    /**
     * Return the script grammar that we are interested in. As we are in the
     * js.validation plugin then I don't suppose we're interested in anything
     * except JavaScript.
     * 
     * @return Script grammar.
     */
    protected String getScriptGrammar() {
        return ProcessJsConsts.JAVASCRIPT_GRAMMAR;
    }

    /**
     * 
     * @param errorMessage
     * @return list of substitution strings for error message.
     */
    protected List<String> getSubstitutionList(ErrorMessage errorMessage) {
        List<String> tempMsgList = new ArrayList<String>();
        tempMsgList.add(Integer.toString(errorMessage.getLineNumber()));
        tempMsgList.add(Integer.toString(errorMessage.getColumnNumber()));
        tempMsgList.add(errorMessage.getErrorMessage());
        return tempMsgList;
    }

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
    protected final void setupScriptTool(ScriptTool scriptToolClass,
            EObject validateObject) {
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

        /*
         * Initialise the ScriptTool with the enumerations cache , which will be
         * passed-on to the data providers, to handle ambiguous situations when
         * multiple enumerations with same name exist in the process package
         */

        scriptToolClass.setPackageScopeEnumCache(enumCache);

        return;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
        return Expression.class;
    }

}
