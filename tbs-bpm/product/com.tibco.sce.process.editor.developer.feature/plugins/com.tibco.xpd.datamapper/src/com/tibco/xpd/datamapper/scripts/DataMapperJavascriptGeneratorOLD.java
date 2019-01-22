/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.scripts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.xpd.datamapper.DataMapperMappingContentProvider;
import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.datamapper.api.JavaScriptStringBuilder;
import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.DataMapperContentContributionHelper;
import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class provides the utility method for Javascript generation for Data
 * Mapper mappings. This class is not thread safe, a new instance should be
 * created for every conversion.
 * 
 * @author Ali
 * @author nwilson
 * @since 9 Mar 2015
 * 
 * @deprecated This class left here ONLY for the prupose of being able to go
 *             back and double check the severely refactored
 *             DataMapperJavascriptGenerator class
 */
@Deprecated
public class DataMapperJavascriptGeneratorOLD {

    private static final String TARGET_VAR_PREFIX = "tV"; //$NON-NLS-1$

    private static final String SOURCE_VAR_PREFIX = "sV"; //$NON-NLS-1$

    private int functionIndex = 1;

    private int indexVarNameCounter = 1;

    private DataMapperLikeMappingHelperOLD mappingHelper;

    public DataMapperJavascriptGeneratorOLD() {
        mappingHelper = new DataMapperLikeMappingHelperOLD();
    }

    /**
     * This method is used to generate Javascript for any Process Data mappings.
     * It is the API entry point to allow both PE and Studio to convert Process
     * Data Mapper mappings to Javascript.
     * 
     * @param expression
     *            The expression containing the xpdExt:ScriptDataMapper element
     *            that holds the mapping definitions.
     * @return javascript generated from the mappings in the given
     *         scriptDataMapper (if the mappings have any validation errors, the
     *         scripts generated might be incorrect)
     * 
     */
    public String convertMappingsToJavascript(Expression expression) {
        final ScriptDataMapper scriptDataMapper =
                DataMapperUtils.getExistingScriptDataMapper(expression);

        return convertMappingsToJavascript(scriptDataMapper);
    }

    /**
     * This method is used to generate Javascript for any Data mappings. It is
     * the API entry point to allow both PE and Studio to convert Process Data
     * Mapper mappings to Javascript.
     * 
     * @param scriptDataMapper
     *            The xpdExt:ScriptDataMapper element containing the mapping
     *            definitions.
     * @param context
     *            The context of the mappings to convert as specified in
     *            {@link com.tibco.xpd.process.js.model.script.ProcessScriptContextConstants}
     *            .
     * @return javascript generated from the mappings in the given
     *         scriptDataMapper (if the mappings have any validation errors, the
     *         scripts generated might be incorrect)
     */
    public String convertMappingsToJavascript(
            final ScriptDataMapper scriptDataMapper) {
        if (scriptDataMapper != null) {
            String context = scriptDataMapper.getMapperContext();

            Activity activity =
                    Xpdl2ModelUtil.getParentActivity(scriptDataMapper);

            IScriptDataMapperProvider sdmProvider =
                    new IScriptDataMapperProvider() {

                        @Override
                        public ScriptDataMapper getScriptDataMapper(
                                Object activity) {
                            return scriptDataMapper;
                        }

                        @Override
                        public boolean usesDataMapperGrammer(
                                Object contextInputObject) {
                            return true;
                        }

                    };

            JavaScriptStringBuilder script = new JavaScriptStringBuilder();

            // target info provider
            ContributableDataMapperInfoProvider targetInfoProvider =
                    new ContributableDataMapperInfoProvider(sdmProvider,
                            context, true, true, true);

            // source info provider
            ContributableDataMapperInfoProvider sourceInfoProvider =
                    new ContributableDataMapperInfoProvider(sdmProvider,
                            context, false, true, true);

            // mappings provider
            DataMapperMappingContentProvider mappingContentProvider =
                    new DataMapperMappingContentProvider(sdmProvider);

            prependScripts(script, sourceInfoProvider, scriptDataMapper, true);
            prependScripts(script, targetInfoProvider, scriptDataMapper, false);

            // all mappings
            Object[] mappings = mappingContentProvider.getElements(activity);

            /*
             * Get all the mapped target items (including unmapped parent) as
             * list of TreeNode
             */
            List<DataMapperTreeNode> targetItemNodes =
                    getTargetItems(context,
                            scriptDataMapper,
                            targetInfoProvider,
                            mappings,
                            activity);

            recursiveScriptGeneration(targetItemNodes,
                    scriptDataMapper,
                    script,
                    mappings,
                    targetInfoProvider,
                    sourceInfoProvider,
                    mappingContentProvider,
                    null,
                    null);

            appendScripts(script, sourceInfoProvider, scriptDataMapper, true);
            appendScripts(script, targetInfoProvider, scriptDataMapper, false);

            return script.toString();
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Insert any script fragments at the start of scripts.
     * 
     * @param script
     * @param infoProvider
     */
    private void prependScripts(JavaScriptStringBuilder scriptBuilder,
            ContributableDataMapperInfoProvider infoProvider,
            ScriptDataMapper container, boolean isSource) {
        String script =
                infoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getScriptsToPrepend(container, isSource);
        if (script != null) {
            scriptBuilder.append(script);
        }
    }

    /**
     * @param script
     * @param infoProvider
     */
    private void appendScripts(JavaScriptStringBuilder scriptBuilder,
            ContributableDataMapperInfoProvider infoProvider,
            ScriptDataMapper sdm, boolean isSource) {
        String script =
                infoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getScriptsToAppend(sdm, isSource);
        if (script != null) {
            scriptBuilder.addLine(script);
        }
    }

    /**
     * 
     * @param object
     * @param targetInfoProvider
     * @return <code>true</code> if the object is single instance
     */
    protected boolean isSingleInstance(Object object,
            ContributableDataMapperInfoProvider targetInfoProvider) {

        if (targetInfoProvider != null
                && !targetInfoProvider.isMultiInstance(object)) {
            return true;
        }
        return false;
    }

    /**
     * Recursively generates the javascript for the given targetItemNodes
     * 
     * @param targetItemNodes
     * @param scriptDataMapper
     * @param dataMappings
     * @param script
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param mappingContentProvider
     * @param parentTargetJsVar
     * @param parentSourceJsVar
     */
    private void recursiveScriptGeneration(
            List<DataMapperTreeNode> targetItemNodes,
            ScriptDataMapper scriptDataMapper, JavaScriptStringBuilder script,
            Object[] mappings,
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            String parentSourceJsVar, String parentTargetJsVar) {

        for (DataMapperTreeNode targetNode : targetItemNodes) {

            Object targetWrappedItem = targetNode.getItem();

            if (targetWrappedItem != null) {

                if (targetNode.isMapped()) {

                    Mapping mapping =
                            mappingHelper
                                    .getMappingForTarget(targetWrappedItem,
                                            mappings);

                    Object srcWrappedItem = mapping.getSource();

                    if (isSingleInstance(targetWrappedItem, targetInfoProvider)) {

                        // mapped single instance
                        if (DataMapperUtils.isLikeMapping(mapping)) {

                            // like mapping
                            addLikeMappingComment(srcWrappedItem,
                                    targetWrappedItem,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    script);
                            Set<Mapping> virtualMappings = new HashSet<>();
                            addLikeTargetItems(sourceInfoProvider,
                                    targetInfoProvider,
                                    mappingContentProvider,
                                    mapping,
                                    targetNode,
                                    virtualMappings);
                            for (Object next : mappings) {
                                if (next instanceof Mapping) {
                                    virtualMappings.add((Mapping) next);
                                }
                            }
                            String childSourceJsVar =
                                    (parentSourceJsVar == null ? "" //$NON-NLS-1$
                                            : parentSourceJsVar + ".") //$NON-NLS-1$
                                            + sourceInfoProvider
                                                    .getObjectName(srcWrappedItem);
                            String childTargetJsVar =
                                    (parentTargetJsVar == null ? "" //$NON-NLS-1$
                                            : parentTargetJsVar + ".") //$NON-NLS-1$
                                            + targetInfoProvider
                                                    .getObjectName(targetWrappedItem);
                            script.addLine("if (" + childSourceJsVar + ") {", //$NON-NLS-1$ //$NON-NLS-2$
                                    true,
                                    false);

                            generateLikeMappingSingleInstanceScript(targetNode,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    mappingContentProvider,
                                    scriptDataMapper,
                                    mapping,
                                    virtualMappings.toArray(),
                                    script,
                                    childSourceJsVar,
                                    childTargetJsVar);
                            script.addLine("}", false, true); //$NON-NLS-1$
                        } else {

                            addMapFromComment(srcWrappedItem,
                                    targetWrappedItem,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    script);
                            generateSingleInstanceAssignmentScript(targetNode,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    mappingContentProvider,
                                    scriptDataMapper,
                                    mappings,
                                    srcWrappedItem,
                                    targetWrappedItem,
                                    script,
                                    parentSourceJsVar,
                                    parentTargetJsVar);

                        }
                    } else {

                        // mapped array
                        if (DataMapperUtils.isLikeMapping(mapping)
                                || mapping.getMappingModel() == null) {

                            // like mapping array
                            addLikeMappingComment(srcWrappedItem,
                                    targetWrappedItem,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    script);

                            generateLikeMappingMultiInstanceAssignmentScript(targetNode,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    mappingContentProvider,
                                    scriptDataMapper,
                                    mapping,
                                    mappings,
                                    script,
                                    parentSourceJsVar,
                                    parentTargetJsVar);

                        } else {

                            addMapFromComment(srcWrappedItem,
                                    targetWrappedItem,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    script);

                            generateDirectArrayAssignmentScript(targetNode,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    mappingContentProvider,
                                    scriptDataMapper,
                                    srcWrappedItem,
                                    targetWrappedItem,
                                    mapping,
                                    mappings,
                                    script,
                                    parentSourceJsVar,
                                    parentTargetJsVar);
                        }
                    }
                } else {

                    // creation of parents

                    if (isSingleInstance(targetWrappedItem, targetInfoProvider)) {

                        // single instance inflation
                        generateSingleInstanceInflationScripts(scriptDataMapper,
                                script,
                                mappings,
                                targetInfoProvider,
                                sourceInfoProvider,
                                mappingContentProvider,
                                parentSourceJsVar,
                                parentTargetJsVar,
                                targetNode,
                                targetWrappedItem);

                    } else {

                        // Array inflation
                        generateArrayInflationScripts(scriptDataMapper,
                                script,
                                mappings,
                                targetInfoProvider,
                                sourceInfoProvider,
                                mappingContentProvider,
                                parentSourceJsVar,
                                parentTargetJsVar,
                                targetNode,
                                targetWrappedItem);
                    }
                }
            }
        }
    }

    /**
     * @param scriptDataMapper
     * @param dataMappings
     * @param script
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param mappingContentProvider
     * @param parentTargetJsVar
     * @param targetNode
     * @param targetWrappedItem
     */
    private void generateSingleInstanceInflationScripts(
            ScriptDataMapper scriptDataMapper, JavaScriptStringBuilder script,
            Object[] mappings,
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            String parentSourceJsVar, String parentTargetJsVar,
            DataMapperTreeNode targetNode, Object targetWrappedItem) {

        if (targetWrappedItem != null) {
            /*
             * Either the target itself, or a variable representing the target
             * object
             */
            String sourceIdentifier = null;
            if (parentSourceJsVar != null && !parentSourceJsVar.isEmpty()) {
                sourceIdentifier = parentSourceJsVar + "." //$NON-NLS-1$
                        + targetInfoProvider.getObjectName(targetWrappedItem);
            }

            String targetIdentifier = null;
            if (parentTargetJsVar == null || parentTargetJsVar.isEmpty()) {
                targetIdentifier =
                        targetInfoProvider.getObjectPath(targetWrappedItem);
            } else {
                targetIdentifier = parentTargetJsVar + "." //$NON-NLS-1$
                        + targetInfoProvider.getObjectName(targetWrappedItem);
            }

            if (!targetInfoProvider.isSimpleTypeContent(targetWrappedItem)) {
                /*
                 * Create the target complex object (because it's content as
                 * been mapped).
                 */
                String createComplexObjectScript =
                        getCreateComplexItemScript(targetInfoProvider,
                                targetWrappedItem);

                if (createComplexObjectScript != null) {
                    /* Needs creation, check != null and create */
                    script.addComment(String
                            .format(Messages.DataMapperJavascriptGenerator_CreateTarget_GeneratedScriptComment_message,
                                    targetInfoProvider
                                            .getObjectPathDescription(targetWrappedItem)));

                    script.addLine("if (" + targetIdentifier + " == null) {", //$NON-NLS-1$ //$NON-NLS-2$
                            true,
                            false);

                    script.addLine(targetIdentifier
                            + " = " + createComplexObjectScript //$NON-NLS-1$
                            + ";"); //$NON-NLS-1$

                    script.addLine("}", false, true); //$NON-NLS-1$

                } else {
                    /* Content contributor says "no creation required". */
                }

            }

            recursiveScriptGeneration(targetNode.getChildren(),
                    scriptDataMapper,
                    script,
                    mappings,
                    targetInfoProvider,
                    sourceInfoProvider,
                    mappingContentProvider,
                    sourceIdentifier,
                    targetIdentifier);
        }

    }

    /**
     * @param scriptDataMapper
     * @param dataMappings
     * @param script
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param parentTargetJsVar
     * @param parentSourceJsVar
     * @param targetNode
     * @param targetWrappedItem
     * @return
     */
    private void generateArrayInflationScripts(
            ScriptDataMapper scriptDataMapper, JavaScriptStringBuilder script,
            Object[] mappings,
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            String parentSourceJsVar, String parentTargetJsVar,
            DataMapperTreeNode targetNode, Object targetWrappedItem) {

        Object srcMasterWrappedItem =
                getSourceMasterItem(targetNode,
                        mappings,
                        targetInfoProvider,
                        sourceInfoProvider);
        /*
         * if we can't find the parent master then don't generate script and
         * return
         */
        if (srcMasterWrappedItem == null) {
            return;
        }

        String indexVarName = "i" + indexVarNameCounter++; //$NON-NLS-1$

        String srcGetterStatement =
                sourceInfoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getCollectionElementScript(srcMasterWrappedItem,
                                indexVarName,
                                parentSourceJsVar);
        String srcArraySizeStatemnet =
                sourceInfoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getCollectionSizeScript(srcMasterWrappedItem,
                                parentSourceJsVar);

        generateArrayMappingScript(scriptDataMapper,
                script,
                mappings,
                targetInfoProvider,
                sourceInfoProvider,
                mappingContentProvider,
                parentSourceJsVar,
                parentTargetJsVar,
                targetNode,
                targetWrappedItem,
                srcGetterStatement,
                srcArraySizeStatemnet,
                indexVarName,
                false,
                false);
    }

    /**
     * @param scriptDataMapper
     * @param dataMappings
     * @param script
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param mappingContentProvider
     * @param parentSourceJsVar
     * @param parentTargetJsVar
     * @param targetNode
     * @param targetWrappedItem
     * @param srcMasterWrappedItem
     * @param srcArraySizeStatemnet
     */
    private void generateArrayMappingScript(ScriptDataMapper scriptDataMapper,
            JavaScriptStringBuilder script, Object[] mappings,
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            String parentSourceJsVar, String parentTargetJsVar,
            DataMapperTreeNode targetNode, Object targetWrappedItem,
            String srcGetterStatement, String srcArraySizeStatemnet,
            String indexVarName, boolean directArrayMapping,
            boolean isLikeMapping) {

        // Handle Array inflation type
        DataMapperArrayInflationType inflationType =
                getArrayInflationType(scriptDataMapper,
                        targetInfoProvider.getObjectPath(targetWrappedItem));

        boolean simpleTargetType =
                targetInfoProvider.isSimpleTypeContent(targetWrappedItem);
        if (!simpleTargetType) {
            StringBuilder targetJsVar =
                    new StringBuilder(parentTargetJsVar == null ? "" //$NON-NLS-1$
                            : parentTargetJsVar);
            generateCreateObjectScript(targetWrappedItem,
                    targetInfoProvider,
                    script,
                    targetJsVar);
        }

        boolean mergeList = false;
        if (DataMapperArrayInflationType.APPEND_LIST.equals(inflationType)) {
            // append
            script.addComment(Messages.DataMapperJavascriptGenerator_AppendToList_GeneratedScriptComment_message
                    + targetInfoProvider
                            .getObjectPathDescription(targetWrappedItem));

        } else if (DataMapperArrayInflationType.MERGE_LIST
                .equals(inflationType)) {
            // merge list
            script.addComment(Messages.DataMapperJavascriptGenerator_MergList_GeneratedScriptComment_message
                    + targetInfoProvider
                            .getObjectPathDescription(targetWrappedItem));
            mergeList = true;

        } else {
            // overwrite
            script.addComment(Messages.DataMapperJavascriptGenerator_OverwriteList_GeneratedScriptComment_message
                    + targetInfoProvider
                            .getObjectPathDescription(targetWrappedItem));

            script.addLine(targetInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getClearCollectionScript(targetWrappedItem,
                            parentTargetJsVar)
                    + ";"); //$NON-NLS-1$

        }

        // For loop start script
        script.addLine(getForLoopStartScript(srcArraySizeStatemnet,
                indexVarName),
                true,
                false);

        // source var

        String srcItemJsVar = SOURCE_VAR_PREFIX + indexVarName;

        script.addLine("var " + srcItemJsVar + " = " + srcGetterStatement + ";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        if (!simpleTargetType) {
            // target var
            String targetItemJsVar = TARGET_VAR_PREFIX + indexVarName;

            if (!directArrayMapping || isLikeMapping) {
                if (mergeList) {
                    String targetArraySizeStatemnet =
                            targetInfoProvider
                                    .getContribDelegatingScriptGenInfoProvider()
                                    .getCollectionSizeScript(targetWrappedItem,
                                            parentTargetJsVar);
                    script.addLine("var " + targetItemJsVar + ";"); //$NON-NLS-1$ //$NON-NLS-2$

                    script.addLine("if(" + indexVarName + " < " + targetArraySizeStatemnet //$NON-NLS-1$ //$NON-NLS-2$
                            + ") {", true, false); //$NON-NLS-1$

                    script.addLine(targetItemJsVar + " = " //$NON-NLS-1$
                            + targetInfoProvider
                                    .getContribDelegatingScriptGenInfoProvider()
                                    .getCollectionElementScript(targetWrappedItem,
                                            indexVarName,
                                            parentTargetJsVar) + ";"); //$NON-NLS-1$

                    script.addLine("} else {", true, true); //$NON-NLS-1$
                    script.addLine(targetItemJsVar
                            + " = " //$NON-NLS-1$
                            + getCreateComplexItemScript(targetInfoProvider,
                                    targetWrappedItem) + ";"); //$NON-NLS-1$

                    script.addLine("}", false, true); //$NON-NLS-1$

                } else {
                    String targetCreateElementStatement =
                            getCreateArrayInstanceScript(targetInfoProvider,
                                    targetWrappedItem);

                    script.addLine("var " + targetItemJsVar + " = " //$NON-NLS-1$ //$NON-NLS-2$
                            + targetCreateElementStatement + ";"); //$NON-NLS-1$
                }
            }

            if (isLikeMapping) {

                // do the like mapping for each array element

                Mapping mapping =
                        mappingHelper.getMappingForTarget(targetNode.getItem(),
                                mappings);
                performLikeMappingOnChildItems(targetNode,
                        sourceInfoProvider,
                        targetInfoProvider,
                        mappingContentProvider,
                        scriptDataMapper,
                        mapping,
                        mappings,
                        script,
                        srcItemJsVar,
                        targetItemJsVar);

            } else {

                recursiveScriptGeneration(targetNode.getChildren(),
                        scriptDataMapper,
                        script,
                        mappings,
                        targetInfoProvider,
                        sourceInfoProvider,
                        mappingContentProvider,
                        srcItemJsVar,
                        targetItemJsVar);
            }

            if (mergeList && (!directArrayMapping || isLikeMapping)) {
                String targetArraySizeStatemnet =
                        targetInfoProvider
                                .getContribDelegatingScriptGenInfoProvider()
                                .getCollectionSizeScript(targetWrappedItem,
                                        parentTargetJsVar);
                //            script.addLine("var " + targetItemJsVar + ";", script); //$NON-NLS-1$ //$NON-NLS-2$

                script.addLine("if(" + indexVarName + " >= " + targetArraySizeStatemnet //$NON-NLS-1$ //$NON-NLS-2$
                        + ") {", true, false); //$NON-NLS-1$

                // add to list script
                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionAddElementScript(targetWrappedItem,
                                targetItemJsVar,
                                parentTargetJsVar));

                script.addLine("}", false, true); //$NON-NLS-1$

            } else {

                if (directArrayMapping && !isLikeMapping) {
                    targetItemJsVar = srcItemJsVar;
                }
                // add to list script
                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionAddElementScript(targetWrappedItem,
                                targetItemJsVar,
                                parentTargetJsVar));
            }
        } else {
            script.addLine(targetInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getCollectionAddElementScript(targetWrappedItem,
                            srcItemJsVar,
                            parentTargetJsVar));
        }

        // For Loop end script
        script.addLine(getForLoopEndScript(), false, true);
    }

    /**
     * @param targetNode
     * @param mappings
     * @param sourceInfoProvider
     * @param parentTargetJsVar
     * @param parentSourceJsVar
     * @param sourceInfoProvider2
     * @return
     */
    private Object getSourceMasterItem(DataMapperTreeNode targetNode,
            Object[] mappings,
            ContributableDataMapperInfoProvider targetInforProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider) {

        /*
         * Find the mapped target child, get the mapping for it, get the source
         * item from the mapping and return parent master item of the source
         * item using sourceInfoProvider (only if the source/target items have
         * same level of nesting)
         */

        Object sourceMaster = null;
        DataMapperTreeNode child = findMappedTargetChild(targetNode);

        if (child != null) {

            Object source = null;
            for (Object obj : mappings) {
                if (obj instanceof Mapping) {
                    Mapping mapping = (Mapping) obj;
                    if (child.getItem().equals(mapping.getTarget())) {
                        source = mapping.getSource();
                    }
                }
            }

            // calculate nesting level to find source parent master ( only go up
            // to the level of the targteNode to find master parent),
            Object targetChild = child.getItem();
            int nestingLevel = 1;
            while (targetChild != null) {
                if (targetInforProvider.isMultiInstance(targetChild)) {
                    nestingLevel++;
                }
                targetChild =
                        targetInforProvider.getContentProvider()
                                .getParent(targetChild);

                if (targetChild != null) {

                    if (targetChild.equals(targetNode.getItem())) {
                        break;
                    }
                }
            }

            while (source != null && nestingLevel > 0) {
                if (sourceInfoProvider.isMultiInstance(source)) {
                    nestingLevel--;
                    if (nestingLevel == 0) {
                        sourceMaster = source;
                        break;
                    }
                }
                source =
                        sourceInfoProvider.getContentProvider()
                                .getParent(source);
            }
        }
        return sourceMaster;
    }

    /**
     * @param targetNode
     * @return
     */
    private DataMapperTreeNode findMappedTargetChild(
            DataMapperTreeNode targetNode) {
        DataMapperTreeNode child = null;
        for (DataMapperTreeNode childNode : targetNode.getChildren()) {
            if (childNode.isMapped()) {
                child = childNode;
            } else {
                child = findMappedTargetChild(childNode);
            }
        }
        return child;
    }

    /**
     * Generate the target complex object creator script fragment.
     * 
     * @param targetItem
     * @param infoProvider
     * @param script
     * @param jsVarAlias
     */
    private void generateCreateObjectScript(Object targetItem,
            ContributableDataMapperInfoProvider infoProvider,
            JavaScriptStringBuilder script, StringBuilder jsVarAlias) {

        if (targetItem != null) {
            String createComplexObjectScript =
                    getCreateComplexItemScript(infoProvider, targetItem);
            if (createComplexObjectScript != null) {
                if (jsVarAlias.length() == 0) {
                    jsVarAlias.append(infoProvider.getObjectPath(targetItem));
                }

                script.addLine("if(" + jsVarAlias + "== null) {", true, false); //$NON-NLS-1$ //$NON-NLS-2$
                script.addLine(jsVarAlias
                        + " = " + createComplexObjectScript + ";"); //$NON-NLS-1$ //$NON-NLS-2$

                script.addLine("}", false, true); //$NON-NLS-1$
            }
        }
    }

    /**
     * 
     * @param targetNode
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param mappingContentProvider
     * @param srcItem
     * @param targetItem
     * @param mapping
     * @param mappings
     * @param script
     * @param parentSourceJsVar
     * @param parentTargetJsVar
     */
    private void generateDirectArrayAssignmentScript(
            DataMapperTreeNode targetNode,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            ScriptDataMapper scriptDataMapper, Object srcItem,
            Object targetItem, Mapping mapping, Object[] mappings,
            JavaScriptStringBuilder script, String parentSourceJsVar,
            String parentTargetJsVar) {

        String srcGetterStatement = ""; //$NON-NLS-1$
        String srcArraySizeStatemnet = ""; //$NON-NLS-1$
        String indexVarName = "i" + indexVarNameCounter++; //$NON-NLS-1$
        if (srcItem instanceof ScriptInformation) {

            String scrInfoText =
                    getScriptInfoText((ScriptInformation) srcItem, script);

            if (targetInfoProvider.isMultiInstance(targetItem)) {

                String scriptJsVar = "scriptVar" + indexVarName; //$NON-NLS-1$
                script.addLine("var " + scriptJsVar + " = " + scrInfoText + ";"); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$

                srcArraySizeStatemnet = scriptJsVar + ".size()"; //$NON-NLS-1$
                srcGetterStatement = scriptJsVar + ".get(" + indexVarName + ")"; //$NON-NLS-1$ //$NON-NLS-2$

            } else {

                script.addLine(targetInfoProvider.getObjectPath(targetItem)
                        + " = " //$NON-NLS-1$
                        + scrInfoText + ";"); //$NON-NLS-1$
                return;
            }

        } else {

            srcGetterStatement =
                    sourceInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .getCollectionElementScript(srcItem,
                                    indexVarName,
                                    parentSourceJsVar);
            srcArraySizeStatemnet =
                    sourceInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .getCollectionSizeScript(srcItem, parentSourceJsVar);
        }

        generateArrayMappingScript(scriptDataMapper,
                script,
                mappings,
                targetInfoProvider,
                sourceInfoProvider,
                mappingContentProvider,
                parentSourceJsVar,
                parentTargetJsVar,
                targetNode,
                targetItem,
                srcGetterStatement,
                srcArraySizeStatemnet,
                indexVarName,
                true,
                false);
    }

    /**
     * Generate the javaScript statement(s) for assigning one single instance
     * object to another.
     * 
     * @param targetNode
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param srcItem
     * @param targetItem
     * @param script
     * @param sourceVarAlias
     * @param targetVarAlias
     */
    private void generateSingleInstanceAssignmentScript(
            DataMapperTreeNode targetNode,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            ScriptDataMapper scriptDataMapper, Object[] mappings,
            Object srcItem, Object targetItem, JavaScriptStringBuilder script,
            String sourceVarAlias, String targetVarAlias) {

        /*
         * Check if this mapping is direct to a target item whose parent has
         * already been mapped (aka an enclosing parent mapping) and if so
         * return false if it is from a source item tree than the enclosing
         * parent mapping.
         * 
         * If it's from a differnet source tree then we don't want to use the
         * src tree variable for this separate mapping.
         */
        boolean sameSource =
                isFromSameSourceAsParent(sourceInfoProvider,
                        targetInfoProvider,
                        mappings,
                        srcItem,
                        targetItem);

        if (!sameSource) {
            sourceVarAlias = null;
        }

        if (targetInfoProvider.isSimpleTypeContent(targetItem)) {

            // get rhs statement
            String rhsObjectStatement =
                    getGetterStatement(script,
                            sourceInfoProvider,
                            srcItem,
                            sourceVarAlias);

            // create assignment

            script.addLine(targetInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getAssignmentStatement(targetItem,
                            rhsObjectStatement,
                            targetVarAlias));

        } else if (targetNode.isInLikeMapping()) {

            /*
             * TODO - is this correct considering we may set sourceVarAlias to
             * null above?
             */

            String childSourceJsVar = sourceVarAlias + "." //$NON-NLS-1$
                    + sourceInfoProvider.getObjectName(srcItem);

            script.addLine("if (" + childSourceJsVar + ") {", true, false); //$NON-NLS-1$//$NON-NLS-2$

            // get rhs statement
            String rhsObjectStatement =
                    getCreateComplexItemScript(targetInfoProvider, targetItem);

            // create assignment
            script.addLine(targetInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getAssignmentStatement(targetItem,
                            rhsObjectStatement,
                            targetVarAlias));

            recursiveScriptGeneration(targetNode.getChildren(),
                    scriptDataMapper,
                    script,
                    mappings,
                    targetInfoProvider,
                    sourceInfoProvider,
                    mappingContentProvider,
                    childSourceJsVar,
                    targetVarAlias + "." //$NON-NLS-1$
                            + targetInfoProvider.getObjectName(targetItem));

            script.addLine("}", false, true); //$NON-NLS-1$

        } else {
            String rhsObjectStatement =
                    getGetterStatement(script,
                            sourceInfoProvider,
                            srcItem,
                            sourceVarAlias);
            script.addLine(targetInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getAssignmentStatement(targetItem,
                            rhsObjectStatement,
                            targetVarAlias));

            if (!targetNode.getChildren().isEmpty()) {

                recursiveScriptGeneration(targetNode.getChildren(),
                        scriptDataMapper,
                        script,
                        mappings,
                        targetInfoProvider,
                        sourceInfoProvider,
                        mappingContentProvider,
                        null,
                        null);
            }
        }
    }

    /**
     * 
     * @param targetItem
     * @param srcItem
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * 
     * @return <code>true</code> If the given srcItem->targetItem mapping is
     *         within an existing mapping higher up the targetItem's parent tree
     *         and this srcItem->targetItem mapping source is from the tree
     *         under that parent mapping<b>OR</b> returns <code>true</code> if
     *         there is no parent mapping.
     */
    private boolean isFromSameSourceAsParent(
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            Object[] mappings, Object srcItem, Object targetItem) {
        boolean sameSource = true;

        // Find any enclosing parent mapping
        Mapping enclosingMapping = null;
        Object parentItem = targetItem;
        while (enclosingMapping == null && parentItem != null) {
            parentItem =
                    targetInfoProvider.getContentProvider()
                            .getParent(parentItem);
            enclosingMapping =
                    findExplicitMapping(targetInfoProvider,
                            mappings,
                            parentItem);
        }

        if (enclosingMapping != null) {
            // Check if source is under parent source
            /*
             * Sid Previous check that srcItem's path started with other . What
             * if have 2 mappings from SrcField and SrcFieldXXX @ root of tree.
             * The it would could SrcField as a child of SrcFieldXXX
             * 
             * Instead we need to check the true source tree hierarchy.
             */
            Object enclosingMappingSource = enclosingMapping.getSource();

            Object srcTreeItem = srcItem;
            while (srcTreeItem != null) {
                if (srcTreeItem.equals(enclosingMappingSource)) {
                    break;
                }

                srcTreeItem =
                        sourceInfoProvider.getContentProvider()
                                .getParent(srcTreeItem);

            }

            if (srcTreeItem == null) {
                /*
                 * The given mapping is not from within the enclosing parent
                 * mapping's source tree.
                 */
                sameSource = false;
            }
        }
        return sameSource;
    }

    /**
     * 
     * @param targetNode
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param mappingContentProvider
     * @param mapping
     * @param mappings
     * @param script
     * @param parentSourceJsVar
     * @param parentTargetJsVar
     */
    private void generateLikeMappingSingleInstanceScript(
            DataMapperTreeNode targetNode,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            ScriptDataMapper scriptDataMapper, Mapping mapping,
            Object[] mappings, JavaScriptStringBuilder script,
            String parentSourceJsVar, String parentTargetJsVar) {

        if (parentTargetJsVar == null) {
            parentTargetJsVar = ""; //$NON-NLS-1$
        }

        StringBuilder targetJsVar = new StringBuilder(parentTargetJsVar);

        generateCreateObjectScript(targetNode.getItem(),
                targetInfoProvider,
                script,
                targetJsVar);

        performLikeMappingOnChildItems(targetNode,
                sourceInfoProvider,
                targetInfoProvider,
                mappingContentProvider,
                scriptDataMapper,
                mapping,
                mappings,
                script,
                parentSourceJsVar,
                parentTargetJsVar);

    }

    /**
     * @param targetNode
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param mappingContentProvider
     * @param scriptDataMapper
     * @param mapping
     * @param mappings
     * @param script
     * @param parentSourceJsVar
     * @param parentTargetJsVar
     */
    private void performLikeMappingOnChildItems(DataMapperTreeNode targetNode,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            ScriptDataMapper scriptDataMapper, Mapping mapping,
            Object[] mappings, JavaScriptStringBuilder script,
            String parentSourceJsVar, String parentTargetJsVar) {

        // Replace the virtual mapping with an explicit one if it exists.
        Mapping explicit =
                findExplicitMapping(targetInfoProvider,
                        mappings,
                        targetNode.getItem());
        boolean isLikeMapping = false;
        if (explicit != null) {
            isLikeMapping = DataMapperUtils.isLikeMapping(explicit);
        }
        if (explicit != null && !isLikeMapping) {
            Object srcItem = explicit.getSource();
            Object targetItem = explicit.getTarget();
            generateSingleInstanceAssignmentScript(targetNode,
                    sourceInfoProvider,
                    targetInfoProvider,
                    mappingContentProvider,
                    scriptDataMapper,
                    mappings,
                    srcItem,
                    targetItem,
                    script,
                    parentSourceJsVar,
                    parentTargetJsVar);
        } else {
            if (explicit != null && isLikeMapping) {
                mapping = explicit;
            }
            Map<DataMapperTreeNode, Object> likeMappedChildrenWithSourceMap =
                    mappingHelper.getMappedAndLikeMappingChildItems(targetNode,
                            sourceInfoProvider,
                            targetInfoProvider,
                            mappingContentProvider,
                            mapping,
                            mappings);

            for (DataMapperTreeNode likeMappedChildNode : likeMappedChildrenWithSourceMap
                    .keySet()) {

                Object targetItem = likeMappedChildNode.getItem();

                if (!targetInfoProvider.isReadOnlyTarget(targetItem)) {

                    Object srcItem =
                            likeMappedChildrenWithSourceMap
                                    .get(likeMappedChildNode);

                    if (targetInfoProvider.isMultiInstance(targetItem)) {

                        // array mapping
                        generateLikeMappingArrayScript(likeMappedChildNode,
                                sourceInfoProvider,
                                targetInfoProvider,
                                mappingContentProvider,
                                scriptDataMapper,
                                srcItem,
                                targetItem,
                                mapping,
                                mappings,
                                script,
                                parentSourceJsVar,
                                parentTargetJsVar);
                    } else {
                        // single instance
                        if (targetInfoProvider.isSimpleTypeContent(targetItem)) {
                            generateSingleInstanceAssignmentScript(likeMappedChildNode,
                                    sourceInfoProvider,
                                    targetInfoProvider,
                                    mappingContentProvider,
                                    scriptDataMapper,
                                    mappings,
                                    srcItem,
                                    targetItem,
                                    script,
                                    parentSourceJsVar,
                                    parentTargetJsVar);
                        } else {
                            Mapping explicit2 =
                                    findExplicitMapping(targetInfoProvider,
                                            mappings,
                                            targetItem);
                            if (explicit2 != null
                                    && !DataMapperUtils
                                            .isLikeMapping(explicit2)) {
                                generateSingleInstanceAssignmentScript(likeMappedChildNode,
                                        sourceInfoProvider,
                                        targetInfoProvider,
                                        mappingContentProvider,
                                        scriptDataMapper,
                                        mappings,
                                        srcItem,
                                        targetItem,
                                        script,
                                        parentSourceJsVar,
                                        parentTargetJsVar);
                            } else {
                                addLikeMappingComment(srcItem,
                                        targetItem,
                                        sourceInfoProvider,
                                        targetInfoProvider,
                                        script);
                                Set<Mapping> virtualMappings = new HashSet<>();
                                addLikeTargetItems(sourceInfoProvider,
                                        targetInfoProvider,
                                        mappingContentProvider,
                                        mapping,
                                        likeMappedChildNode,
                                        virtualMappings);
                                for (Object next : mappings) {
                                    if (next instanceof Mapping) {
                                        virtualMappings.add((Mapping) next);
                                    }
                                }
                                String childSourceJsVar =
                                        (parentSourceJsVar == null ? "" //$NON-NLS-1$
                                                : parentSourceJsVar + ".") //$NON-NLS-1$
                                                + sourceInfoProvider
                                                        .getObjectName(srcItem);
                                String childTargetJsVar =
                                        (parentTargetJsVar == null ? "" //$NON-NLS-1$
                                                : parentTargetJsVar + ".") //$NON-NLS-1$
                                                + targetInfoProvider
                                                        .getObjectName(targetItem);
                                script.addLine("if (" + childSourceJsVar + ") {", //$NON-NLS-1$ //$NON-NLS-2$
                                        true,
                                        false);
                                generateLikeMappingSingleInstanceScript(likeMappedChildNode,
                                        sourceInfoProvider,
                                        targetInfoProvider,
                                        mappingContentProvider,
                                        scriptDataMapper,
                                        mapping,
                                        virtualMappings.toArray(),
                                        script,
                                        childSourceJsVar,
                                        childTargetJsVar);
                                script.addLine("}", false, true); //$NON-NLS-1$
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param mappings
     * @param mapping
     * @return
     */
    private Mapping findExplicitMapping(
            ContributableDataMapperInfoProvider targetInfoProvider,
            Object[] mappings, Object target) {
        Mapping explicit = null;
        String targetPath = targetInfoProvider.getObjectPath(target);
        if (targetPath != null) {
            for (Object next : mappings) {
                if (next instanceof Mapping) {
                    Mapping nextMapping = (Mapping) next;
                    if (nextMapping.getMappingModel() != null) {
                        Object nextTarget = nextMapping.getTarget();
                        String nextTargetPath =
                                targetInfoProvider.getObjectPath(nextTarget);
                        if (targetPath.equals(nextTargetPath)) {
                            explicit = nextMapping;
                            break;
                        }
                    }
                }
            }
        }
        return explicit;
    }

    /**
     * 
     * @param targetNode
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param mappingContentProvider
     * @param mapping
     * @param mappings
     * @param script
     * @param parentSourceJsVar
     * @param parentTargetJsVar
     */
    private void generateLikeMappingMultiInstanceAssignmentScript(
            DataMapperTreeNode targetNode,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            ScriptDataMapper scriptDataMapper, Mapping mapping,
            Object[] mappings, JavaScriptStringBuilder script,
            String parentSourceJsVar, String parentTargetJsVar) {

        Object targetArrayItem = mapping.getTarget();
        Object srcArrayItem = mapping.getSource();

        String indexVarName = "i" + indexVarNameCounter++; //$NON-NLS-1$

        String srcArraySizeStatemnet =
                sourceInfoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getCollectionSizeScript(srcArrayItem,
                                parentSourceJsVar);

        String srcGetterStatement =
                sourceInfoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getCollectionElementScript(srcArrayItem,
                                indexVarName,
                                parentSourceJsVar);

        generateArrayMappingScript(scriptDataMapper,
                script,
                mappings,
                targetInfoProvider,
                sourceInfoProvider,
                mappingContentProvider,
                parentSourceJsVar,
                parentTargetJsVar,
                targetNode,
                targetArrayItem,
                srcGetterStatement,
                srcArraySizeStatemnet,
                indexVarName,
                true,
                true);

    }

    /**
     * 
     * @param targetNode
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param mappingContentProvider
     * @param srcItem
     * @param targetItem
     * @param mapping
     * @param mappings
     * @param script
     * @param parentSourceJsVar
     * @param parentTargetJsVar
     */
    private void generateLikeMappingArrayScript(DataMapperTreeNode targetNode,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            ScriptDataMapper scriptDataMapper, Object srcItem,
            Object targetItem, Mapping mapping, Object[] mappings,
            JavaScriptStringBuilder script, String parentSourceJsVar,
            String parentTargetJsVar) {

        // check inflation type and clear list if its overwrite
        boolean overwriteList = true;
        if (DataMapperArrayInflationType.APPEND_LIST
                .equals(getArrayInflationType((DataMapping) mapping
                        .getMappingModel(), targetInfoProvider, targetItem))) {
            overwriteList = false;
        }

        if (overwriteList) {
            script.addLine(targetInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getClearCollectionScript(targetItem, parentTargetJsVar)
                    + ";"); //$NON-NLS-1$
        }

        String indexVarName = "i" + indexVarNameCounter++; //$NON-NLS-1$

        if (srcItem instanceof ScriptInformation) {

            String scrInfoText =
                    getScriptInfoText((ScriptInformation) srcItem, script);

            script.addLine(targetInfoProvider.getObjectPath(targetItem) + " = " //$NON-NLS-1$
                    + scrInfoText + ";"); //$NON-NLS-1$

        } else {

            script.addLine(getForLoopStartScript(sourceInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getCollectionSizeScript(srcItem, parentSourceJsVar),
                    indexVarName),
                    true,
                    false);

            // source var
            String srcItemJsVar = SOURCE_VAR_PREFIX + indexVarName;

            String srcGetterStatement =
                    sourceInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .getCollectionElementScript(srcItem,
                                    indexVarName,
                                    parentSourceJsVar);

            script.addLine("var " + srcItemJsVar + " = " + srcGetterStatement + ";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

            if (targetInfoProvider.isSimpleTypeContent(targetItem)) {

                // simple type array element
                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionAddElementScript(targetItem,
                                srcItemJsVar,
                                parentTargetJsVar));

            } else {

                // target var
                String targetItemJsVar = TARGET_VAR_PREFIX + indexVarName;

                String targetCreateElementStatement =
                        getCreateArrayInstanceScript(targetInfoProvider,
                                targetItem);

                script.addLine("var " + targetItemJsVar + " = " //$NON-NLS-1$ //$NON-NLS-2$
                        + targetCreateElementStatement + ";"); //$NON-NLS-1$

                Set<Mapping> virtualMappings = new HashSet<>();
                addLikeTargetItems(sourceInfoProvider,
                        targetInfoProvider,
                        mappingContentProvider,
                        mapping,
                        targetNode,
                        virtualMappings);
                for (Object next : mappings) {
                    if (next instanceof Mapping) {
                        virtualMappings.add((Mapping) next);
                    }
                }
                recursiveScriptGeneration(targetNode.getChildren(),
                        scriptDataMapper,
                        script,
                        virtualMappings.toArray(),
                        targetInfoProvider,
                        sourceInfoProvider,
                        mappingContentProvider,
                        srcItemJsVar,
                        targetItemJsVar);

                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionAddElementScript(targetItem,
                                targetItemJsVar,
                                parentTargetJsVar));
            }

            script.addLine(getForLoopEndScript(), false, true);
        }

    }

    /**
     * Build and Add a "map from X to Y" comment.
     * 
     * @param srcItem
     * @param targetItem
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param script
     */
    private void addMapFromComment(Object srcItem, Object targetItem,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            JavaScriptStringBuilder script) {

        String srcPath;
        if (srcItem instanceof ScriptInformation) {
            srcPath = ((ScriptInformation) srcItem).getName();
        } else {

            srcPath = sourceInfoProvider.getObjectPathDescription(srcItem);
        }

        String targetPath =
                targetInfoProvider.getObjectPathDescription(targetItem);

        script.addComment(String
                .format(Messages.DataMapperJavascriptGenerator_MapFrom_GeneratedScriptComment_message,
                        srcPath,
                        targetPath));
    }

    /**
     * Build and Add a "Like mapping from X to Y" comment.
     * 
     * @param srcItem
     * @param targetItem
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param script
     */
    private void addLikeMappingComment(Object srcItem, Object targetItem,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            JavaScriptStringBuilder script) {

        String srcPath = sourceInfoProvider.getObjectPathDescription(srcItem);
        String targetPath =
                targetInfoProvider.getObjectPathDescription(targetItem);

        script.addComment(String
                .format(Messages.DataMapperJavascriptGenerator_LIkeMappingFrom_GeneratedScriptComment_message,
                        srcPath,
                        targetPath));
    }

    /**
     * 
     * @param script
     *            The script text builder.
     * @param sourceInfoProvider
     *            SOurce info provider
     * @param srcItem
     *            The mapping source object.
     * 
     * @param jsAliasVarName
     *            Override JavaScript variable name if required (else
     *            <code>null</code> or <code>""</code> if the original source
     *            item name/path should be used.
     * 
     * @return The getter statement for the given LHS srcItem
     */
    private String getGetterStatement(JavaScriptStringBuilder script,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            Object srcItem, String jsAliasVarName) {

        String rhsObjectStatement = null;
        if (srcItem instanceof ScriptInformation) {
            rhsObjectStatement =
                    getScriptInfoText((ScriptInformation) srcItem, script);
        } else {

            rhsObjectStatement =
                    sourceInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .getGetterStatement(srcItem, jsAliasVarName);
        }
        return rhsObjectStatement;
    }

    /**
     * Get the condition expression that can be used inside an
     * <code>if ( xxxx ) {</code> for the prevention of accessing the content of
     * null source item parents.
     * <p>
     * For instance if a mapping is made from Parent.child.grandchild then the
     * expression would be "Parent && Parent.child && Parent.child.grandchild.
     * 
     * @param sourceInfoProvider
     * @param srcItem
     * @param jsAliasVarName
     * 
     * @return
     */
    private String getGetterNullProtectionCondition(
            ContributableDataMapperInfoProvider sourceInfoProvider,
            Object srcItem, String jsAliasVarName) {
        return null;
    }

    /**
     * Recursively Get a list of all target item nodes under the parent that
     * have a valid 'like' mapping to the source.
     * 
     * @param sourceInfoProvider
     *            the source info provider.
     * @param targetInfoProvider
     *            the target info provider.
     * @param mappingContentProvider
     *            the mapping content provider.
     * @param mapping
     *            the 'like' mapping.
     * @param targetParent
     *            The target tree node item of the 'like' mapping.
     * @return A list of tree nodes for virtual mapping targets.
     */
    private void addLikeTargetItems(
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            Mapping mapping, DataMapperTreeNode targetParent,
            Collection<Mapping> mappings) {
        Object target = targetParent.getItem();
        // Get source item matching targetParent, then pass that into below
        // method.
        Object source =
                mappingHelper.getSameNamedItemInSource(target,
                        mapping,
                        sourceInfoProvider,
                        targetInfoProvider);
        DataMapping dataMapping = (DataMapping) mapping.getMappingModel();
        List<Object> mappingExclusionList =
                mappingContentProvider
                        .getLikeMappingExclusionItems(dataMapping,
                                Xpdl2ModelUtil.getParentActivity(dataMapping));

        addChildLikeTargetItems(sourceInfoProvider,
                targetInfoProvider,
                mappingExclusionList,
                source,
                targetParent,
                mappings);
    }

    /**
     * @param items
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param source
     * @param target
     */
    private void addChildLikeTargetItems(
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            List<Object> mappingExclusionList, Object source,
            DataMapperTreeNode target, Collection<Mapping> mappings) {
        Object[] sourceChildren =
                sourceInfoProvider.getContentProvider().getChildren(source);
        Object[] targetChildren =
                targetInfoProvider.getContentProvider()
                        .getChildren(target.getItem());
        for (Object targetChild : targetChildren) {
            if (!mappingExclusionList.contains(targetChild)) {
                String targetName =
                        targetInfoProvider.getObjectName(targetChild);
                Object matchingSource = null;
                for (Object sourceChild : sourceChildren) {
                    String sourceName =
                            sourceInfoProvider.getObjectName(sourceChild);
                    if (targetName.equals(sourceName)) {
                        matchingSource = sourceChild;
                        break;
                    }
                }
                if (matchingSource != null) {
                    DataMapperTreeNode targetTreeNode =
                            new DataMapperTreeNode(targetChild);
                    target.addChild(targetTreeNode);
                    targetTreeNode.setMapped(true);
                    targetTreeNode.setInLikeMapping(true);
                    Mapping mapping =
                            new Mapping(matchingSource, targetChild, null);
                    mappings.add(mapping);
                    addChildLikeTargetItems(sourceInfoProvider,
                            targetInfoProvider,
                            mappingExclusionList,
                            matchingSource,
                            targetTreeNode,
                            mappings);
                }
            }
        }
    }

    /**
     * 
     * @param context
     * @param scriptDataMapper
     * @param targetInfoProvider
     * @param mappings
     * @param mapperInput
     *            return returns all the mapped target items including their
     *            unmapped parents (contributed for the given context)
     */
    private List<DataMapperTreeNode> getTargetItems(String context,
            final ScriptDataMapper scriptDataMapper,
            ContributableDataMapperInfoProvider targetInfoProvider,
            Object[] mappings, Activity mapperInput) {

        /*
         * build a tree of mapped target items including their unmapped parents
         * using Mapping Info/Content providers
         */

        // all root level target items
        Object[] allRootTargetItems =
                targetInfoProvider.getContentProvider()
                        .getElements(mapperInput);

        // all mapped target items
        List<Object> mappedTargetItems = new ArrayList<>();
        for (Object obj : mappings) {
            if (obj instanceof Mapping) {
                Mapping mapping = (Mapping) obj;
                mappedTargetItems.add(mapping.getTarget());
            }
        }

        DataMapperTreeNode dummyRoot = new DataMapperTreeNode(null);

        buildTargetItemNodes(allRootTargetItems,
                targetInfoProvider,
                mappedTargetItems,
                dummyRoot);

        return dummyRoot.getChildren();
    }

    /**
     * recursively builds target items tree
     * 
     * @param allTargetItems
     * @param targetInfoProvider
     * @param mappedTargetItems
     * @param currentNode
     */
    private void buildTargetItemNodes(Object[] allTargetItems,
            ContributableDataMapperInfoProvider targetInfoProvider,
            List<Object> mappedTargetItems, DataMapperTreeNode currentNode) {

        if (allTargetItems != null) {

            for (Object targetItem : allTargetItems) {

                /*
                 * We are only interested if the target item is mapped or its an
                 * ancestor of a mapped item
                 */
                if (isUsedInMapping(targetItem,
                        mappedTargetItems,
                        targetInfoProvider)) {

                    DataMapperTreeNode node =
                            new DataMapperTreeNode(targetItem);

                    if (mappedTargetItems.contains(targetItem)) {
                        node.setMapped(true);
                    }

                    currentNode.addChild(node);

                    buildTargetItemNodes(targetInfoProvider
                            .getContentProvider().getChildren(targetItem),
                            targetInfoProvider,
                            mappedTargetItems,
                            node);
                }
            }
        }
    }

    /**
     * @param targetItem
     * @param mappedTargetItems
     * @param targetInfoProvider
     * @return true if the given targetItem appears in the hierarchy of any
     *         mapped item (i.e, its an ancestor of a mapped target item)
     */
    private boolean isUsedInMapping(Object targetItem,
            List<Object> mappedTargetItems,
            ContributableDataMapperInfoProvider targetInfoProvider) {

        for (Object mappedItem : mappedTargetItems) {

            if (inHierarchy(mappedItem, targetItem, targetInfoProvider)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param mappedItem
     * @param targetItem
     * @param targetInfoProvider
     * @return true if f the given mappedItem appears in the hierarchy of any
     *         mapped item (i.e, its an ancestor of a mapped target item)
     */
    private boolean inHierarchy(Object mappedItem, Object targetItem,
            ContributableDataMapperInfoProvider targetInfoProvider) {
        if (mappedItem != null) {
            if (mappedItem.equals(targetItem)) {
                return true;
            }
            Object parent =
                    targetInfoProvider.getContentProvider()
                            .getParent(mappedItem);

            if (parent != null) {
                return inHierarchy(parent, targetItem, targetInfoProvider);
            }
        }
        return false;
    }

    /**
     * @param mapping
     * @return contributor that contributed the target object in the given
     *         mapping
     */
    protected AbstractDataMapperContentContributor getTargetContributor(
            DataMapping mapping) {
        AbstractDataMapperContentContributor contributor = null;
        String targetId =
                (String) Xpdl2ModelUtil.getOtherAttribute(mapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_TargetContributorId());
        if (targetId != null) {
            contributor =
                    DataMapperContentContributionHelper
                            .getContributor(targetId);
        }
        return contributor;
    }

    /**
     * @param mapping
     * @return contributor that contributed the source object in the given
     *         mapping
     */
    protected AbstractDataMapperContentContributor getSourceContributor(
            DataMapping mapping) {
        AbstractDataMapperContentContributor contributor = null;
        String sourceId =
                (String) Xpdl2ModelUtil.getOtherAttribute(mapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SourceContributorId());
        if (sourceId != null) {
            contributor =
                    DataMapperContentContributionHelper
                            .getContributor(sourceId);
        }
        return contributor;
    }

    /**
     * 
     * @param collectionSizeText
     *            - script to get the size of the collection
     * @param uniqueVarName
     *            - variable name to be used inside the for loop
     * @return javascript text for the 'for' loop to iterate over given
     *         collection object
     */
    protected String getForLoopStartScript(String collectionSizeText,
            String uniqueVarName) {

        String text =
                "for(var " + uniqueVarName + " = 0; " + uniqueVarName + " < " + collectionSizeText + "; " + uniqueVarName + "++) { "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
        return text;
    }

    /**
     * @return
     */
    protected String getForLoopEndScript() {

        return "}"; //$NON-NLS-1$
    }

    /**
     * @param infoProvider
     * @param complexItem
     * @return The javaScript fragment required for creation of the target
     *         objectThe script for creating the the given target item or
     *         <code>null</code> if no target creation required (static target
     *         values).
     */
    protected String getCreateComplexItemScript(
            ContributableDataMapperInfoProvider infoProvider, Object complexItem) {
        String text =
                infoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getComplexObjectCreationScript(complexItem);

        if (text != null && !text.isEmpty()) {
            return text;
        }

        return null;
    }

    /**
     * @param infoProvider
     * @param complexItem
     * @return The javaScript fragment required for creation of the target
     *         objectThe script for creating the the given target item or
     *         <code>null</code> if no target creation required (static target
     *         values).
     */
    protected String getCreateArrayInstanceScript(
            ContributableDataMapperInfoProvider infoProvider, Object complexItem) {
        // String text =
        // infoProvider.getContribDelegatingScriptGenInfoProvider()
        // .getArrayInstanceCreationScript(complexItem);
        //
        // if (text != null && !text.isEmpty()) {
        // return text;
        // }

        return null;
    }

    /**
     * @param mapping
     * @param infoProvider
     * @param object
     * @return
     */
    private Object getArrayInflationType(DataMapping mapping,
            ContributableDataMapperInfoProvider infoProvider, Object object) {
        if (mapping.eContainer() instanceof ScriptDataMapper) {
            String path = infoProvider.getObjectPath(object);
            ScriptDataMapper scriptDataMapper =
                    (ScriptDataMapper) mapping.eContainer();
            for (DataMapperArrayInflation inflationType : scriptDataMapper
                    .getArrayInflationType()) {
                if (inflationType.getPath().equals(path)) {
                    return inflationType.getMappingType();
                }
            }
        }
        return null;
    }

    /**
     * @param mapping
     * @param contributor
     * @param object
     * @return
     */
    private DataMapperArrayInflationType getArrayInflationType(
            ScriptDataMapper scriptDataMapper, Object objectPath) {

        for (DataMapperArrayInflation inflationType : scriptDataMapper
                .getArrayInflationType()) {
            if (inflationType.getPath().equals(objectPath)) {
                return inflationType.getMappingType();
            }
        }

        return null;
    }

    /**
     * It creates a function with the user-defined script, appends it to given
     * generatedScript and return the function name to be used as the RHS of an
     * assignment (we are not using anonymous functions as that would require
     * the RHS variable to be treated as function rather than variable)
     * 
     * @param scriptInfo
     * @param generatedScript
     * @return
     */
    protected String getScriptInfoText(ScriptInformation scriptInfo,
            JavaScriptStringBuilder generatedScript) {
        /*
         * create a function with the user-defined script, append it to
         * generatedScript and return function call to be used as the RHS of
         * assignment
         */
        String funcName = getUniqueJsFunctionName() + "()"; //$NON-NLS-1$

        generatedScript
                .addComment(String
                        .format(Messages.DataMapperJavascriptGenerator_FunctionHeaderComment_message,
                                scriptInfo.getName()));

        generatedScript.addLine("function " + funcName + " { ", true, false); //$NON-NLS-1$//$NON-NLS-2$
        generatedScript.addLine(scriptInfo.getExpression().getText());
        generatedScript.addLine("}", false, true); //$NON-NLS-1$

        return funcName;
    }

    /**
     * @return unique function name (e.g., _f1_)
     */
    private String getUniqueJsFunctionName() {

        return "_f" + functionIndex++ + "_"; //$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * Helper methods to support like mappings.
     * 
     * @author nwilson
     * @since 16 Apr 2015
     * 
     * 
     */
    @Deprecated
    private static class DataMapperLikeMappingHelperOLD {

        /**
         * Returns a map of Target TreeNode to Source items representing all of
         * the explicit and implied mappings in the tree.
         * 
         * @param targetNode
         *            The target node
         * @param sourceInfoProvider
         *            the source info provider.
         * @param targetInfoProvider
         *            the target info provider.
         * @param mappingContentProvider
         *            the mapping content provider.
         * @param mapping
         *            The current mapping.
         * @param mappings
         *            A list of all mappings.
         * @return A map of target TreeNode items to source items.
         */
        public Map<DataMapperTreeNode, Object> getMappedAndLikeMappingTreeItems(
                DataMapperTreeNode targetNode,
                ContributableDataMapperInfoProvider sourceInfoProvider,
                ContributableDataMapperInfoProvider targetInfoProvider,
                DataMapperMappingContentProvider mappingContentProvider,
                Mapping mapping, Object[] mappings) {
            Map<DataMapperTreeNode, Object> treeMap = new LinkedHashMap<>();
            getMappedAndLikeMappingTreeItems(treeMap,
                    targetNode,
                    sourceInfoProvider,
                    targetInfoProvider,
                    mappingContentProvider,
                    mapping,
                    mappings);
            return treeMap;
        }

        /**
         * Recursive method for building up a tree of mappings.
         * 
         * @param treeMap
         *            The tree map to add mappings to.
         * @param targetNode
         *            The target node
         * @param sourceInfoProvider
         *            the source info provider.
         * @param targetInfoProvider
         *            the target info provider.
         * @param mappingContentProvider
         *            the mapping content provider.
         * @param mapping
         *            The current mapping.
         * @param mappings
         *            A list of all mappings.
         */
        private void getMappedAndLikeMappingTreeItems(
                Map<DataMapperTreeNode, Object> treeMap,
                DataMapperTreeNode targetNode,
                ContributableDataMapperInfoProvider sourceInfoProvider,
                ContributableDataMapperInfoProvider targetInfoProvider,
                DataMapperMappingContentProvider mappingContentProvider,
                Mapping mapping, Object[] mappings) {
            Map<DataMapperTreeNode, Object> childMap =
                    getMappedAndLikeMappingChildItems(targetNode,
                            sourceInfoProvider,
                            targetInfoProvider,
                            mappingContentProvider,
                            mapping,
                            mappings);
            for (Entry<DataMapperTreeNode, Object> child : childMap.entrySet()) {
                DataMapperTreeNode target = child.getKey();
                Object source = child.getValue();
                treeMap.put(target, source);
                getMappedAndLikeMappingTreeItems(treeMap,
                        target,
                        sourceInfoProvider,
                        targetInfoProvider,
                        mappingContentProvider,
                        mapping,
                        mappings);
            }
        }

        /**
         * Returns a map of Target TreeNode to Source items representing the
         * immediate children of the targetNode.
         * 
         * @param targetNode
         *            The target node
         * @param sourceInfoProvider
         *            the source info provider.
         * @param targetInfoProvider
         *            the target info provider.
         * @param mappingContentProvider
         *            the mapping content provider.
         * @param mapping
         *            The current mapping.
         * @param mappings
         *            A list of all mappings.
         * @return child content for the given targetNode that should be mapped
         *         based on the like mapping
         */
        public Map<DataMapperTreeNode, Object> getMappedAndLikeMappingChildItems(
                DataMapperTreeNode targetNode,
                ContributableDataMapperInfoProvider sourceInfoProvider,
                ContributableDataMapperInfoProvider targetInfoProvider,
                DataMapperMappingContentProvider mappingContentProvider,
                Mapping mapping, Object[] mappings) {

            // XPD-7559: Using LinkedHashMap as order is important.
            Map<DataMapperTreeNode, Object> childTargetSourceMap =
                    new LinkedHashMap<>();

            Object targetItem = targetNode.getItem();

            DataMapping dataMapping = (DataMapping) mapping.getMappingModel();

            List<Object> mappingExclusionList =
                    mappingContentProvider
                            .getLikeMappingExclusionItems(dataMapping,
                                    Xpdl2ModelUtil
                                            .getParentActivity(dataMapping));

            /*
             * get target item children, go through each child in order and see
             * if it could be like mapping candidate (i.e, either mapped
             * explicitly or has same named element in source children and is
             * not in exclusion list or is an unmapped parent of a mapped child
             * , if so, add to likeMappingItemsLists
             */
            for (Object child : targetInfoProvider.getContentProvider()
                    .getChildren(targetItem)) {

                DataMapperTreeNode childNode =
                        getChildNode(child, targetNode, targetInfoProvider);
                /*
                 * if childNode is not null, it means its an explicitly mapped
                 * child element of a like mapping parent object or has a child
                 * which is mapped and should be added to list, even if its
                 * excluded
                 */
                if (childNode != null) {
                    // explicitly mapped child

                    // Get its mapping and get source from the mapping
                    Mapping childMapping =
                            getMappingForTarget(childNode.getItem(), mappings);

                    Object sourceItem = null;
                    if (childMapping != null) {

                        sourceItem = childMapping.getSource();

                    }
                    childTargetSourceMap.put(childNode, sourceItem);

                } else if (!mappingExclusionList.contains(child)) {

                    Object sourceItem =
                            getSameNamedItemInSource(child,
                                    mapping,
                                    sourceInfoProvider,
                                    targetInfoProvider);
                    if (sourceItem != null) {

                        childNode = new DataMapperTreeNode(child);
                        childNode.setMapped(true);
                        childNode.setInLikeMapping(true);
                        childTargetSourceMap.put(childNode, sourceItem);
                    }
                }
            }
            return childTargetSourceMap;
        }

        /**
         * @param child
         * @param mapping
         * @param sourceInfoProvider
         * @param targetInfoProvider
         * @return source object or null
         */
        public Object getSameNamedItemInSource(Object child, Mapping mapping,
                ContributableDataMapperInfoProvider sourceInfoProvider,
                ContributableDataMapperInfoProvider targetInfoProvider) {

            Object target = mapping.getTarget();
            String[] relativePath =
                    getRelativePath(targetInfoProvider, target, child);

            Object srcParenItem = mapping.getSource();
            for (String pathElement : relativePath) {
                if (srcParenItem != null) {
                    Object found = null;
                    for (Object srcChildItem : sourceInfoProvider
                            .getContentProvider().getChildren(srcParenItem)) {
                        if (pathElement.equals(sourceInfoProvider
                                .getObjectName(srcChildItem))) {
                            found = srcChildItem;
                            break;
                        }
                    }
                    if (found != null) {
                        srcParenItem = found;
                    } else {
                        srcParenItem = null;
                    }
                }
            }
            return srcParenItem;
        }

        /**
         * @param target
         * @param child
         * @return
         */
        private String[] getRelativePath(
                ContributableDataMapperInfoProvider targetInfoProvider,
                Object target, Object child) {
            String[] pathItems = new String[0];
            String prefix = targetInfoProvider.getObjectPath(target) + "."; //$NON-NLS-1$
            String path = targetInfoProvider.getObjectPath(child);
            if (path.startsWith(prefix)) {
                String relative = path.substring(prefix.length());
                pathItems = relative.split("\\."); //$NON-NLS-1$
            }
            return pathItems;
        }

        /**
         * @param child
         * @param targetNode
         * @param targetInfoProvider
         * @return TreeNode for the given child if it exists in the children of
         *         targetNode
         */
        private DataMapperTreeNode getChildNode(Object child,
                DataMapperTreeNode targetNode,
                ContributableDataMapperInfoProvider targetInfoProvider) {

            for (DataMapperTreeNode childNode : targetNode.getChildren()) {
                // check equality based on path
                if (targetInfoProvider.getObjectPath(child)
                        .equals(targetInfoProvider.getObjectPath(childNode
                                .getItem()))) {

                    return childNode;

                }
            }
            return null;
        }

        /**
         * @param targetItem
         * @param dataMappings
         * @return
         */
        public Mapping getMappingForTarget(Object targetItem, Object[] mappings) {
            for (Object obj : mappings) {
                if (obj instanceof Mapping) {
                    Mapping mapping = (Mapping) obj;
                    if (targetItem.equals(mapping.getTarget())) {
                        return mapping;
                    }
                }
            }
            return null;
        }

    }

}