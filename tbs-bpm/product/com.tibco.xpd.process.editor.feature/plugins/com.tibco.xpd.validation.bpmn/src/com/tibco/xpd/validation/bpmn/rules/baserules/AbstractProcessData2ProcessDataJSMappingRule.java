/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Enumeration;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.mapper.MapperContentProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractInvokedProcessParameterItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityDataFieldItemProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ActivityScriptContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.CompositeTreeContentProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DirectionType;

/**
 * This rule abstracts the similarities of requriements for validation of
 * mappings in activities that effectively map process data to process data.
 * <p>
 * Currently this includes...
 * <li>Reusable Sub-Process Task.</li>
 * <li>Decision Service Task.</li>
 * <li>Catch signal mapping</li>
 * <p>
 * The rules and restrictions applicable to these situations are likely to
 * remain identical in most aspects.
 * 
 * @author aallway
 * @since 16 May 2012
 */
public abstract class AbstractProcessData2ProcessDataJSMappingRule extends
        AbstractActivityMappingJavaScriptRule {

    private static final String ISSUE_WRONGSIZE =
            "bpmn.subProcessWrongSizeParameter"; //$NON-NLS-1$

    private static final String SUPER_TYPE_SUB_TYPE_MAPPING =
            "abstractMappingRule.superTypeToSubType.warning"; //$NON-NLS-1$

    /**
     * Size criteria of mapped formal parameter '%1$s' and data '%2$s' do not
     * match - if the source is larger than target it will cause process
     * instance to halt.
     */
    private static final String RHS_BOM_WRONG_SIZE_ISSUE =
            "warning.rhsBomPropertySizeCriteriaMismatch"; //$NON-NLS-1$

    /**
     * Assigned between {@link #objectIsApplicable(Activity)} and
     * {@link #objectValidateDone(Activity)}
     */
    private Activity currentActivity = null;

    /**
     * Result of invocation of
     * {@link #checkTypeCompatibility(Object, Object, Object)} for current
     * mapping (so that we can add additional length incompatibility warning if
     * types are compatible but not same length) during the subsequent call to
     * {@link #performAdditionalMappingValidation(Object, Object, Object)}
     */
    private MappingTypeCompatibility currentMappingTypeCompatibilityResult =
            null;

    private boolean currentMappingIsFromSuperToSubType = false;

    /**
     * And the source and target concept path that the
     * currentMappingTypeCompatibilityResult applies to.
     */
    private ConceptPath currentMappingTypeCompatibilitySource = null;

    private ConceptPath currentMappingTypeCompatibilityTarget = null;

    private AbstractInvokedProcessParameterItemProvider currentActivityTargetItemProvider;

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#objectIsApplicable(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return
     */
    @Override
    protected final boolean objectIsApplicable(EObject objectToValidate) {
        currentActivity = (Activity) objectToValidate;

        return doActivityIsApplicable((Activity) objectToValidate);
    }

    /**
     * Abstraction of the content of {@link #objectIsApplicable(Activity)}
     * 
     * @param activity
     * @return <code>true</code> if activity shoudl be validated by theis rule.
     */
    protected abstract boolean doActivityIsApplicable(Activity activity);

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#objectValidateDone(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     */
    @Override
    protected final void objectValidateDone(EObject objectToValidate) {
        doActivityValidationDone((Activity) objectToValidate);

        currentMappingTypeCompatibilityResult = null;
        currentMappingTypeCompatibilitySource = null;
        currentMappingTypeCompatibilityTarget = null;

        currentActivity = null;
    }

    /**
     * Abstraction opf the content of {@link #objectValidateDone(Activity)}
     * 
     * @param activity
     */
    protected abstract void doActivityValidationDone(Activity activity);

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#checkJavaScriptTypeCompatibility(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param sourceObjectInTree
     * @param targetObjectInTree
     * @param mapping
     * @return
     */
    @Override
    protected JavaScriptTypeCompatibilityResult checkJavaScriptTypeCompatibility(
            Object sourceObjectInTree, Object targetObjectInTree, Object mapping) {

        JavaScriptTypeCompatibilityResult compatible =
                super.checkJavaScriptTypeCompatibility(sourceObjectInTree,
                        targetObjectInTree,
                        mapping);
        /*
         * XPD-6978: Validate further only when Super has not validated any
         * scenario
         */
        if (JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO
                .equals(compatible)) {

            if (sourceObjectInTree instanceof ConceptPath
                    && targetObjectInTree instanceof ConceptPath) {

                currentMappingTypeCompatibilitySource =
                        (ConceptPath) sourceObjectInTree;

                currentMappingTypeCompatibilityTarget =
                        (ConceptPath) targetObjectInTree;

                currentMappingTypeCompatibilityResult =
                        ProcessDataMappingCompatibilityUtil
                                .checkTypeCompatibility((ConceptPath) sourceObjectInTree,
                                        (ConceptPath) targetObjectInTree,
                                        DirectionType.IN_LITERAL);

                /* XPD-7014: Add warning for Text -> Enum mapping */
                if (MappingTypeCompatibility.OK
                        .equals(currentMappingTypeCompatibilityResult)) {

                    ConceptPath srcData = (ConceptPath) sourceObjectInTree;
                    ConceptPath targetData = (ConceptPath) targetObjectInTree;

                    Object sourceType =
                            BasicTypeConverterFactory.INSTANCE
                                    .getBaseType(srcData.getItem(), true);
                    Object targetType =
                            BasicTypeConverterFactory.INSTANCE
                                    .getBaseType(targetData.getItem(), true);
                    if ((sourceType instanceof BasicType
                            && BasicTypeType.STRING_LITERAL
                                    .equals(((BasicType) sourceType).getType()) && targetType instanceof Enumeration)) {

                        /* Add warning */
                        Map<String, String> aditionalInfo =
                                new HashMap<String, String>();
                        aditionalInfo
                                .put(MapperContentProvider.DATAMAPPING_SOURCE_URI_ISSUEINFO,
                                        srcData.getPath());
                        aditionalInfo
                                .put(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        targetData.getPath());

                        addIssue("bx.textToEnum.warning", //$NON-NLS-1$
                                getModelObjectForMapping(mapping),
                                Collections.EMPTY_LIST,
                                aditionalInfo);
                    }
                }

                if (MappingTypeCompatibility.WRONGTYPE
                        .equals(currentMappingTypeCompatibilityResult)) {

                    /* XPD-2181: Check super type to sub type mapping */
                    if (ProcessDataMappingCompatibilityUtil
                            .checkSubTypeCompatibility((ConceptPath) sourceObjectInTree,
                                    (ConceptPath) targetObjectInTree,
                                    DirectionType.IN_LITERAL)) {

                        currentMappingIsFromSuperToSubType = true;

                        if (currentMappingIsFromSuperToSubType) {
                            return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                        }
                    }
                    return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                }

                /*
                 * If objects are wrong length then
                 * performAdditionalValidation() will use
                 * currentMappingTypeCompatibilityResult to check this.
                 */
            } else if (sourceObjectInTree instanceof ScriptInformation
                    && targetObjectInTree instanceof ConceptPath) {

                ConceptPath targetPath = (ConceptPath) targetObjectInTree;

                IScriptRelevantData returnType =
                        getCachedScriptReturnType(mapping);

                if (returnType != null
                        && returnType.getType() != null
                        && !returnType.getType()
                                .equals(JsConsts.UNDEFINED_DATA_TYPE)) {

                    currentMappingTypeCompatibilityResult =
                            isScriptTypeToProcessDataCompatible(returnType,
                                    targetPath);

                    if (MappingTypeCompatibility.WRONGTYPE
                            .equals(currentMappingTypeCompatibilityResult)) {

                        /* XPD-2181: Check super type to sub type mapping */
                        if (ProcessDataMappingCompatibilityUtil
                                .checkSubTypeCompatibility(returnType,
                                        (ConceptPath) targetObjectInTree)) {

                            currentMappingIsFromSuperToSubType = true;

                            if (currentMappingIsFromSuperToSubType) {

                                return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
                            }
                        }
                        return JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_FAILED;
                    }
                }
            }
        }
        /*
         * Everything we 'know' about should be a concept path - so ignore other
         * types (we'll say they're compatible cos we don't know one way or the
         * other).
         */
        return compatible;
    }

    /**
     * Checks if the script type to process type mapping is compatible
     * 
     * @param scriptType
     * @param target
     * @return
     */
    protected abstract MappingTypeCompatibility isScriptTypeToProcessDataCompatible(
            IScriptRelevantData scriptType, ConceptPath target);

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#getMappingDirection()
     * 
     * @return
     */
    @Override
    protected MappingDirection getMappingDirection() {
        return MappingDirection.OUT;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createSourceInfoProvider(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createSourceInfoProvider(
            EObject objectToValidate) {
        ITreeContentProvider provider =
                new CompositeTreeContentProvider(
                        doCreateMappingSourceItemContentProvider((Activity) objectToValidate),
                        new ActivityScriptContentProvider(MappingDirection.OUT));

        return new ProcessDataMappingRuleInfoProvider(provider);
    }

    /**
     * @return The mapping source tree item provider.
     */
    protected abstract ITreeContentProvider doCreateMappingSourceItemContentProvider(
            Activity activity);

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#createTargetInfoProvider(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param objectToValidate
     *            One of the objects returned by
     *            {@link #getObjectsToValidate(Process)}
     * @return
     */
    @Override
    protected MappingRuleContentInfoProvider createTargetInfoProvider(
            EObject objectToValidate) {
        ActivityDataFieldItemProvider provider =
                new ActivityDataFieldItemProvider(MappingDirection.OUT);

        return new ProcessDataMappingRuleInfoProvider(provider);
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#isPartialMappingCompletionSupported(java.lang.Object)
     * 
     * @param targetObjectInTree
     * @return
     */
    @Override
    protected boolean isPartialMappingCompletionSupported(
            Object targetObjectInTree) {
        return true;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#performAdditionalMappingValidation(java.lang.Object,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param mapping
     * @param sourceObjectInTree
     * @param targetObjectInTree
     */
    @Override
    protected void performAdditionalMappingValidation(Object mapping,
            Object sourceObjectInTree, Object targetObjectInTree) {
        /*
         * Use the type compatibility result from the call to
         * checkTypeCompatibility() for this mapping (which will always have
         * been before we get here IF we ever get here).
         */
        if (currentMappingTypeCompatibilityResult != null) {
            if (MappingTypeCompatibility.WRONGTYPE
                    .equals(currentMappingTypeCompatibilityResult)
                    && currentMappingIsFromSuperToSubType) {
                /* XPD-2181: add warning message */
                ArrayList<String> messages = new ArrayList<String>();

                messages.add(currentMappingTypeCompatibilityTarget.getPath());
                messages.add(currentMappingTypeCompatibilitySource.getPath());

                Map<String, String> additionalInfo =
                        Collections
                                .singletonMap(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                        currentMappingTypeCompatibilityTarget
                                                .getPath());

                Object issueTarget = null;
                if (mapping instanceof Mapping) {
                    issueTarget = ((Mapping) mapping).getMappingModel();
                }

                if (!(issueTarget instanceof EObject)) {
                    issueTarget = currentActivity;
                }
                addIssue(SUPER_TYPE_SUB_TYPE_MAPPING,
                        (EObject) issueTarget,
                        messages,
                        additionalInfo);
            } else {
                if (MappingTypeCompatibility.WRONGSIZE
                        .equals(currentMappingTypeCompatibilityResult)) {
                    ArrayList<String> messages = new ArrayList<String>();

                    messages.add(currentMappingTypeCompatibilityTarget
                            .getPath());
                    messages.add(currentMappingTypeCompatibilitySource
                            .getPath());

                    Map<String, String> additionalInfo =
                            Collections
                                    .singletonMap(MapperContentProvider.DATAMAPPING_TARGET_URI_ISSUEINFO,
                                            currentMappingTypeCompatibilityTarget
                                                    .getPath());

                    Object issueTarget = null;
                    if (mapping instanceof Mapping) {
                        issueTarget = ((Mapping) mapping).getMappingModel();
                    }

                    if (!(issueTarget instanceof EObject)) {
                        issueTarget = currentActivity;
                    }

                    if (isTargetInTreeBomAttribute(targetObjectInTree)) {
                        /*
                         * XPD-7725: If wrong size rhs bom attribute(rhs bom
                         * attribute length < lhs length) then the process
                         * instance will halt at runtime hence raise a separate
                         * warning
                         */
                        addIssue(RHS_BOM_WRONG_SIZE_ISSUE,
                                (EObject) issueTarget,
                                messages,
                                additionalInfo);

                    } else {
                        /*
                         * raise warning that says that the data might be
                         * truncated.
                         */
                        addIssue(ISSUE_WRONGSIZE,
                                (EObject) issueTarget,
                                messages,
                                additionalInfo);
                    }
                }
            }
        }

        return;
    }

    /**
     * 
     * @param targetObjectInTree
     *            the RHS target object in the mapping editor.
     * @return <code>true</code> if the target in tree is a BOM attribute (with
     *         parent uml 'Class'), else return <code>false</code>
     */
    private static boolean isTargetInTreeBomAttribute(Object targetObjectInTree) {

        if (targetObjectInTree instanceof ConceptPath) {

            /*
             * If it a bom attribute it must have a parent.
             */
            return ((ConceptPath) targetObjectInTree).getParent() != null ? true
                    : false;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.validation.bpmn.rules.baserules.AbstractMappingRuleBase#performAdditionalMappingsValidation(java.util.Collection)
     * 
     * @param mappings
     */
    @Override
    protected void performAdditionalMappingsValidation(Object objectToValidate,
            Collection<Object> mappings) {
    }

    /**
     * @return the currentActivity
     */
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    @Override
    protected String getDefaultScriptDestination() {
        return JsConsts.JSCRIPT_DESTINATION;
    }

    @Override
    protected String getScriptGrammar(EObject objectToValidate) {
        return ScriptGrammarFactory.JAVASCRIPT;
    }

    @Override
    protected String getScriptType() {
        return JsConsts.SUBPROCESS_TASK;
    }

}
