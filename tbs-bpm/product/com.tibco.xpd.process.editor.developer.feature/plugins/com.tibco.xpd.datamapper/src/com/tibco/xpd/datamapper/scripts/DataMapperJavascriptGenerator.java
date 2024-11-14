/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.scripts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.datamapper.DataMapperMappingContentProvider;
import com.tibco.xpd.datamapper.DataMapperPlugin;
import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider;
import com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider.CheckNullTreeExpressionType;
import com.tibco.xpd.datamapper.api.JavaScriptStringBuilder;
import com.tibco.xpd.datamapper.api.VirtualLikeMapping;
import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.DataMapperContentContributionHelper;
import com.tibco.xpd.datamapper.infoProviders.ScriptGeneratorInfoProviderContributionHelper;
import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class provides the utility method for Javascript generation for Data
 * Mapper mappings. This class is not thread safe, a new instance should be
 * created for every conversion.
 * 
 * @author Ali
 * @author nwilson
 * @since 9 Mar 2015
 */
@SuppressWarnings("nls")
public class DataMapperJavascriptGenerator {
    private int functionIndex = 1;

    private int indexVarNameCounter = 1;

    public DataMapperJavascriptGenerator() {
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
					new DataMapperMappingContentProvider(sdmProvider, context);

            prependScripts(script, sourceInfoProvider, scriptDataMapper, true);
            prependScripts(script, targetInfoProvider, scriptDataMapper, false);

            // all mappings
            Object[] mappings = mappingContentProvider.getElements(activity);

            /*
             * Sid ACE-1118 Filter out any data mappings that do not have Script
             * Info Provider (some scenarios have mappings (such as correlation)
             * that do not appear in the main mapping script
             */
            mappings = removeMappingsWithoutProvider(mappingContentProvider, mappings);

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

            /*
             * Sid XPD-7651. Initial empty aliasOfSourceParent entry that
             * doesn't alias anything (yet); just saves null checking always
             * later on.
             * 
             * Same thing for target.
             */
            JSVarAliasAndContentObject aliasOfSourceParent =
                    new JSVarAliasAndContentObject(null, null);
            JSVarAliasAndContentObject aliasOfTargetParent =
                    new JSVarAliasAndContentObject(null, null);

            recursiveScriptGeneration(targetItemNodes,
                    scriptDataMapper,
                    script,
                    mappings,
                    targetInfoProvider,
                    sourceInfoProvider,
                    mappingContentProvider,
                    aliasOfSourceParent,
                    aliasOfTargetParent);

            appendScripts(script, sourceInfoProvider, scriptDataMapper, true);
            appendScripts(script, targetInfoProvider, scriptDataMapper, false);

            return script.toString();
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Sid ACE-1118 Filter out any data mappings that do not have Script Info
     * Provider (some scenarios have mappings (such as correlation) that do not
     * appear in the main mapping script
     * 
     * @param mappingContentProvider
     * @param mappings
     * 
     * @return The filtered array.
     */
    private Object[] removeMappingsWithoutProvider(DataMapperMappingContentProvider mappingContentProvider,
            Object[] mappings) {

        List<Object> filteredMappings = new ArrayList<Object>();

        for (Object mapping : mappings) {
            /*
             * In order to be able to generate a script for the source and
             * target of a mapping then we need both the source and target
             * script generator info providers.
             * 
             * With the exception of script mappings as they only need a target
             * contributor for script generation.
             */
            String targetContributorId = mappingContentProvider.getTargetContributorId(mapping);

            if (targetContributorId == null || ScriptGeneratorInfoProviderContributionHelper
                    .getScriptGeneratorInfoProvider(targetContributorId) == null) {
                /*
                 * Don't want to convert mappings with no script generator
                 * contribution.
                 */
                continue;
            }

            /*
             * There is no source contributor id for script mappings, so they're
             * always ok
             */
            if (!isScriptMapping(mapping)) {
                String sourceContributorId = mappingContentProvider.getSourceContributorId(mapping);

                if (sourceContributorId == null || ScriptGeneratorInfoProviderContributionHelper
                        .getScriptGeneratorInfoProvider(sourceContributorId) == null) {
                    /*
                     * Don't want to convert mappings with no script generator
                     * contribution for the source for non scripted mappings.
                     */
                    continue;
                }

            }

            /*
             * We have everything we need to do this mapping, let's keep it.
             */
            filteredMappings.add(mapping);
        }

        return filteredMappings.toArray();
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
     * 
     * @param sourceObject
     * @param sourceInfoProvider
     * @param targetObject
     * @param targetInfoProvider
     * @return <code>true</code> if the two objects do not have equivalent
     *         multiplicity.
     */
    protected boolean isMixedInstance(Object sourceObject,
            ContributableDataMapperInfoProvider sourceInfoProvider, Object targetObject,
            ContributableDataMapperInfoProvider targetInfoProvider) {

        if (targetInfoProvider != null && sourceInfoProvider != null
                && ((sourceInfoProvider.isMultiInstance(sourceObject) != targetInfoProvider
                        .isMultiInstance(targetObject)))) {
            return true;
        }
        return false;
    }

    /**
     * Recursively generates the javascript for the given targetItemNodes
     * 
     * @param targetItemNodes
     * @param scriptDataMapper
     * @param script
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param mappingContentProvider
     * @param aliasOfSourceParent
     * @param aliasOfTargetParent
     */
    private void recursiveScriptGeneration(
            List<DataMapperTreeNode> targetItemNodes,
            ScriptDataMapper scriptDataMapper, JavaScriptStringBuilder script,
            Object[] mappings,
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            JSVarAliasAndContentObject aliasOfSourceParent,
            JSVarAliasAndContentObject aliasOfTargetParent) {

        for (DataMapperTreeNode targetNode : targetItemNodes) {

            Object targetWrappedItem = targetNode.getItem();

            if (targetWrappedItem != null) {
                Mapping mapping =
                        getMappingForTarget(targetWrappedItem, mappings);

                /**
                 * Sid XPD-7676. The mapping content provider now returns
                 * like-mapped child content as actual mappings
                 * (VirtualLikeMapping).
                 * 
                 * So now we should not have to do anything special at all, just
                 * process all the mappings as if the user had gone to the
                 * trouble of drawing all the mappings themselves.
                 * 
                 * So for like mappings we can ignore the mapping and drop thru
                 * to the 'creation of target item that is parent to some mapped
                 * child content'
                 */
                if (mapping != null && !DataMapperUtils.isLikeMapping(mapping)) {
                    /*
                     * Handle things when target is mapped.
                     */

                    Object srcWrappedItem = mapping.getSource();

                    /*
                     * XPD-7651: update the parentSourceJsVar to be the parent
                     * of the actually mapped source (for consistency when
                     * calling info providers).
                     * 
                     * e.g. If we are in array inflation loop then we'd have
                     * created "sVi1 = array[i]" so we will have "sVi1" in
                     * parentSourceJsVar.
                     * 
                     * When we actually find a mapping - say
                     * "sourceArray.child.grandchild" then we can't use
                     * parentSourceJsVar+mappedObjectSimpleName because that
                     * misses out the ".child." part.
                     * 
                     * Instead we need to make sure that parentSourceJsVar is
                     * changed to the parent of the mapping source object.
                     */
                    JSVarAliasAndContentObject aliasOfNewSourceParent =
                            createChildItemAlias(sourceInfoProvider,
                                    aliasOfSourceParent,
                                    sourceInfoProvider.getContentProvider()
                                            .getParent(srcWrappedItem));

                    /*
                     * Handle standard (non-like) mapping
                     */
                    addMapFromComment(srcWrappedItem,
                            targetWrappedItem,
                            sourceInfoProvider,
                            targetInfoProvider,
                            script);

                    if (isMixedInstance(srcWrappedItem, sourceInfoProvider, targetWrappedItem, targetInfoProvider)) {
                        /*
                         * Handle multi->single, single->multi top level
                         * mappings.
                         */
                        generateMixedOrSingleInstanceAssignmentScript(targetNode,
                                sourceInfoProvider,
                                targetInfoProvider,
                                mappingContentProvider,
                                scriptDataMapper,
                                mappings,
                                srcWrappedItem,
                                targetWrappedItem,
                                script,
                                aliasOfNewSourceParent,
                                aliasOfTargetParent);

                    } else if (isSingleInstance(targetWrappedItem, targetInfoProvider)) {
                        /*
                         * Handle mapping from-to single instance object
                         */
                        generateMixedOrSingleInstanceAssignmentScript(targetNode,
                                sourceInfoProvider,
                                targetInfoProvider,
                                mappingContentProvider,
                                scriptDataMapper,
                                mappings,
                                srcWrappedItem,
                                targetWrappedItem,
                                script,
                                aliasOfNewSourceParent,
                                aliasOfTargetParent);

                    } else {
                        /*
                         * Handle mapping from-to multi instance object
                         */
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
                                aliasOfNewSourceParent,
                                aliasOfTargetParent);
                    }

                } else {
                    /*
                     * The targetNode is the parent target item with some
                     * mappings underneath therefore we need to create the
                     * target node and recurs into the child mappings.
                     */
                    if (mapping != null
                            && DataMapperUtils.isLikeMapping(mapping)) {
                        /*
                         * Just add the alternative comment, then create target
                         * parent as normal.
                         */
                        addLikeMappingComment(mapping.getSource(),
                                targetWrappedItem,
                                sourceInfoProvider,
                                targetInfoProvider,
                                script);

                    }

                    if (isSingleInstance(targetWrappedItem, targetInfoProvider)) {
                        generateSingleInstanceInflationScripts(scriptDataMapper,
                                script,
                                mappings,
                                targetInfoProvider,
                                sourceInfoProvider,
                                mappingContentProvider,
                                aliasOfSourceParent,
                                aliasOfTargetParent,
                                targetNode,
                                targetWrappedItem);

                    } else {
                        generateArrayInflationScripts(scriptDataMapper,
                                script,
                                mappings,
                                targetInfoProvider,
                                sourceInfoProvider,
                                mappingContentProvider,
                                aliasOfSourceParent,
                                aliasOfTargetParent,
                                targetNode,
                                targetWrappedItem);
                    }
                }
            }
        }
    }

    /**
     * Given a child content object from a tree and (optionally) the current
     * nearest alias in play for one of the child's ancestor, create the
     * appropriate alias for the child item.
     * <p>
     * This is done for instance to keep the alias and srcItem passed to info
     * providers consistent (e.g. the alias is always a string representing the
     * parent object of srcItem)
     * 
     * @param infoProvider
     *            Info provider for the content object
     * @param currentParentItemAlias
     *            the current nearest alias in play for one of the child's
     *            ancestor OR <code>null</code> if there is none.
     * @param contentObject
     *            The content object that we need an alias for
     * 
     * @return The appropriate alias for the contentObject item within the
     *         currentParentItemAlias OR currentParentItemAlias if this is the
     *         correct alias for contentObject (i.e. child is direct descendent
     *         of alias) OR <code>null</code> if currentParentItemAlias is
     *         <code>null</code>;
     */
    private JSVarAliasAndContentObject createChildItemAlias(
            ContributableDataMapperInfoProvider infoProvider,
            JSVarAliasAndContentObject currentParentItemAlias,
            Object contentObject) {
        JSVarAliasAndContentObject childItemAlias = currentParentItemAlias;

        /* Only need to do anything if we're actually currently aliasing. */
        if (currentParentItemAlias != null
                && currentParentItemAlias.contentObject != null
                && currentParentItemAlias.jsVarAlias != null
                /*
                 * Sid XPD-7839. Skip artificial objects in tree (things like
                 * Choice)
                 */
                && !infoProvider.isArtificialObject(contentObject)) {

            if (contentObject != null) {

                /*
                 * I THINK THIS AN ADEQUATE WAY OF EVALUATING THE THE EXTRA PATH
                 * TO ADD TO EXISTING ALIAS FOR all CONTRIBUITION TYPES.
                 * 
                 * However, if we get issues (which will only happen if any info
                 * provider provides children with paths that DON'T start with
                 * an ancestors path (which would be strange).
                 */
                String pathToCurrentAlias =
                        infoProvider
                                .getObjectPath(currentParentItemAlias.contentObject);

                String pathToContentParent =
                        infoProvider.getObjectPath(contentObject);

                /*
                 * Only need to worry if the content object's parent is nested
                 * below the original alias
                 */
                if (!pathToContentParent.equals(pathToCurrentAlias)) {
                    if (pathToContentParent.startsWith(pathToCurrentAlias)) {

                        /*
                         * The new alias is the current alias + the additional
                         * extra path between the current alias's object and the
                         * contentObject's parent.
                         * 
                         * e.g. sVi1 = sourceArrayElement. So alias for
                         * sourceArrayElement.child.grandchild = "sVi1.child".
                         */
                        String contentParentAliasPath =
                                currentParentItemAlias.jsVarAlias
                                        + pathToContentParent
                                                .substring(pathToCurrentAlias
                                                        .length());
                        childItemAlias =
                                new JSVarAliasAndContentObject(
                                        contentParentAliasPath, contentObject);

                    } else {
                        /*
                         * URK!! We've been passed a child object that isn't a
                         * decendent of current parent alias.
                         */
                        DataMapperPlugin
                                .getDefault()
                                .getLogger()
                                .error(new Exception(),
                                        String.format("Unexpected contentObject '%1$s' passed - expected it to be a descendent of current aliased parent item '%2%s'.", //$NON-NLS-1$
                                                pathToContentParent,
                                                pathToCurrentAlias));
                    }

                } else {
                    /*
                     * Content object is a direct child of item represented by
                     * the currentParentItemAlias. So the alias we shoudl use is
                     * that. So nothing to do.
                     */
                }

            }
        }

        return childItemAlias;
    }

    /**
     * Generate the script for creating a target object by creating the base
     * object and then 'inflating' it from mappings made into its child content
     * 
     * @param scriptDataMapper
     * @param script
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param mappingContentProvider
     * @param aliasOfSourceParent
     * @param aliasOfTargetParent
     * @param targetNode
     * @param targetWrappedItem
     */
    private void generateSingleInstanceInflationScripts(
            ScriptDataMapper scriptDataMapper, JavaScriptStringBuilder script,
            Object[] mappings,
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            JSVarAliasAndContentObject aliasOfSourceParent,
            JSVarAliasAndContentObject aliasOfTargetParent,
            DataMapperTreeNode targetNode, Object targetWrappedItem) {

        if (targetWrappedItem != null) {
            /*
             * Sid ACE-6583 track if we added script to create new complex object.
             */
            boolean complexTargetCreated = false;

            /*
             * Sid XPD-7651 - Used to be code here that reset the
             * aliasOfSourceParent from the TARGET item path/name - this was
             * obviosuly incorrect as source tree does not always match target
             * tree content
             */

            /*
             * Reset the target tree alias (variable used for loops or current
             * target tree item path) to current.nextLevelOfTargetTree
             * 
             * NOTE THAT FOR TARGET CONTENT TREE the JSVarAliasAndContentObject
             * is always populated with either a variable-alias path to use or
             * the actual object path of the target wrapped item. This is
             * because we always step down thru each level when handling target
             * side.
             * 
             * The aliasOfSourfeParent on the other hand only has it's content
             * populated WHEN there is a variable alias in play.
             */
            JSVarAliasAndContentObject newTargetAlias = null;

            if (targetInfoProvider.isArtificialObject(targetWrappedItem)) {
                /*
                 * Sid XPD-7839: Do not attempt to create target content for
                 * artificial elements in the target tree.
                 */
                newTargetAlias = aliasOfTargetParent;

            } else {
                if (aliasOfTargetParent.jsVarAlias == null) {
                    newTargetAlias =
                            new JSVarAliasAndContentObject(
                                    targetInfoProvider
                                            .getObjectPath(targetWrappedItem),
                                    targetWrappedItem);
                } else {
                    newTargetAlias =
                            new JSVarAliasAndContentObject(
                                    aliasOfTargetParent.jsVarAlias + "." //$NON-NLS-1$
                                            + targetInfoProvider
                                                    .getObjectName(targetWrappedItem),
                                    targetWrappedItem);
                }

                if (!targetInfoProvider.isSimpleTypeContent(targetWrappedItem)) {
                    /*
                     * Create the target complex object (because it's content as
                     * been mapped).
                     */
                    complexTargetCreated = true; /* Sid ACE-6583 */

                    String createComplexObjectScript =
                            getCreateComplexItemScript(targetInfoProvider,
                                    targetWrappedItem);

                    if (createComplexObjectScript != null) {
                        /* Needs creation, check != null and create */
                        String targetPath =
                                targetInfoProvider
                                        .getContribDelegatingScriptGenInfoProvider()
                                        .resolvePath(targetWrappedItem,
                                                newTargetAlias.jsVarAlias);

                        script.addComment(String
                                .format(Messages.DataMapperJavascriptGenerator_CreateTarget_GeneratedScriptComment_message,
                                        targetInfoProvider
                                                .getObjectPathDescription(targetWrappedItem)));

                        script.addLine("if (" + targetPath + " == null) {", //$NON-NLS-1$ //$NON-NLS-2$
                                true,
                                false);

                        script.addLine(targetPath
                                + " = " + createComplexObjectScript //$NON-NLS-1$
                                + ";"); //$NON-NLS-1$

                        script.addLine("}", false, true); //$NON-NLS-1$

                    } else {
                        /* Content contributor says "no creation required". */
                    }

                }
            }

            recursiveScriptGeneration(targetNode.getChildren(),
                    scriptDataMapper,
                    script,
                    mappings,
                    targetInfoProvider,
                    sourceInfoProvider,
                    mappingContentProvider,
                    aliasOfSourceParent,
                    newTargetAlias);

            /*
             * Sid ACE-6583 Check if target data handling script requires to exclude empty complex objects for optional
             * target data (if the target data info provider supports it).
             */
            if (complexTargetCreated) {

            	// Sid ACE-8864 Switched to Boolean object handling for property (default=FALSE)
				if (Boolean.TRUE.equals(scriptDataMapper.getExcludeEmptyOptionalObjects())) {
                    /*
                     * Target data mappings are configured to Delete empty optional objects (but don't delete if root
                     * var (can't delete these in JavaScript)
                     */
					boolean isRoot = targetInfoProvider.getContentProvider().getParent(targetWrappedItem) == null;

                    if (!isRoot && targetInfoProvider.getMinimumInstances(targetWrappedItem) < 1) {
                        String isEmptyObjectConditionStatement = getDeleteEmptyObjectScript(targetInfoProvider,
                                targetWrappedItem,
                                newTargetAlias.jsVarAlias);
                        
                        if (isEmptyObjectConditionStatement != null) {
                            script.append(isEmptyObjectConditionStatement);
                        }
                    }
                }
            }
        }

    }

    /**
     * Generate the appropriate script for inflating a target array from
     * mappings into it's child items.
     * 
     * @param scriptDataMapper
     * @param script
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param mappingContentProvider
     * @param aliasOfSourceParent
     * @param aliasOfTargetParent
     * @param targetNode
     * @param targetWrappedItem
     */
    private void generateArrayInflationScripts(
            ScriptDataMapper scriptDataMapper, JavaScriptStringBuilder script,
            Object[] mappings,
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            JSVarAliasAndContentObject aliasOfSourceParent,
            JSVarAliasAndContentObject aliasOfTargetParent,
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

        /*
         * XPD-8230: update the parentSourceJsVar to be the parent of the
         * actually mapped source (for consistency when calling info providers).
         * 
         * e.g. If we are in array inflation loop then we'd have created
         * "sVi1 = array[i]" so we will have "sVi1" in parentSourceJsVar.
         * 
         * When we actually find a mapping - say "sourceArray.child.grandchild"
         * then we can't use parentSourceJsVar+mappedObjectSimpleName because
         * that misses out the ".child." part.
         * 
         * Instead we need to make sure that parentSourceJsVar is changed to the
         * parent of the mapping source object.
         */
        JSVarAliasAndContentObject aliasOfNewSourceParent =
                createChildItemAlias(sourceInfoProvider,
                        aliasOfSourceParent,
                        sourceInfoProvider.getContentProvider()
                                .getParent(srcMasterWrappedItem));

        String srcGetterStatement =
                sourceInfoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getCollectionElementScript(srcMasterWrappedItem,
                                indexVarName,
                                aliasOfNewSourceParent.jsVarAlias);

        String srcArraySizeStatemnet =
                sourceInfoProvider.getContribDelegatingScriptGenInfoProvider()
                        .getCollectionSizeScript(srcMasterWrappedItem,
                                aliasOfNewSourceParent.jsVarAlias);

        generateArrayMappingScript(scriptDataMapper,
                script,
                mappings,
                targetInfoProvider,
                sourceInfoProvider,
                mappingContentProvider,
                srcMasterWrappedItem,
                aliasOfSourceParent,
                aliasOfTargetParent,
                targetNode,
                targetWrappedItem,
                srcGetterStatement,
                srcArraySizeStatemnet,
                indexVarName,
                false);
    }

    /**
     * Generate the script for mapping to an array object (either directly or
     * inflating it from child content mappings)
     * 
     * @param scriptDataMapper
     * @param script
     * @param mappings
     * @param targetInfoProvider
     * @param sourceInfoProvider
     * @param mappingContentProvider
     * @param sourceWrappedItem
     * @param parentSourceJsVar
     * @param aliasOfTargetParent
     * @param targetNode
     * @param targetWrappedItem
     * @param srcGetterStatement
     * @param srcArraySizeStatemnet
     * @param indexVarName
     * @param directArrayMapping
     */
    private void generateArrayMappingScript(ScriptDataMapper scriptDataMapper,
            JavaScriptStringBuilder script, Object[] mappings,
            ContributableDataMapperInfoProvider targetInfoProvider,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            Object sourceWrappedItem,
            JSVarAliasAndContentObject aliasOfSourceParent,
            JSVarAliasAndContentObject aliasOfTargetParent,
            DataMapperTreeNode targetNode, Object targetWrappedItem,
            String srcGetterStatement, String srcArraySizeStatemnet,
            String indexVarName, boolean directArrayMapping) {

        // Handle Array inflation type
        DataMapperArrayInflationType inflationType =
                getArrayInflationType(scriptDataMapper,
                        targetInfoProvider.getObjectPath(targetWrappedItem));

        /**
         * For complex type lists, then given script gen info providers chance
         * to create the array/list object specifically.
         */
        boolean simpleTargetType =
                targetInfoProvider.isSimpleTypeContent(targetWrappedItem);

        /*
         * Sid XPD-7660 - Distinguish properly between attempted creation of
         * arrays and creation of single instance objects [within arrays]
         * 
         * Need to pass in the alias of the actual target array not it's parent
         * for the array creation script
         */
        JSVarAliasAndContentObject tgtArrayItemAlias =
                createChildItemAlias(targetInfoProvider,
                        aliasOfTargetParent,
                        targetWrappedItem);

        generateCreateArrayScript(targetWrappedItem,
                targetInfoProvider,
                script,
                tgtArrayItemAlias);

        /**
         * Different comments for Array Inflation Types (Overwrite | Append |
         * Merge
         */
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
                            aliasOfTargetParent.jsVarAlias)
                    + ";"); //$NON-NLS-1$

        }

        /**
         * XPD-7408: Add LHS parent tree null protection condition (after
         * clearing the target list for overwrite etc (because overwrite
         * assignment from a null list should probably empty the target.
         */
        boolean parentNullProtectAdded = false;

        JSVarAliasAndContentObject srcArrayItemAlias =
                createChildItemAlias(sourceInfoProvider,
                        aliasOfSourceParent,
                        sourceWrappedItem);

        String nullProtectionCondition =
                getCheckNullTreeExpression(sourceInfoProvider,
                        sourceWrappedItem,
                        srcArrayItemAlias.jsVarAlias,
                        CheckNullTreeExpressionType.IS_MULTI_INSTANCE_CHECK);

        if (nullProtectionCondition != null
                && nullProtectionCondition.trim().length() > 0) {
            parentNullProtectAdded = true;

            script.addLine("\nif (" + nullProtectionCondition + ") {\n", //$NON-NLS-1$ //$NON-NLS-2$
                    true,
                    false);
        }

        /**
         * Start the loop of the source array
         */
        script.addLine(getForLoopStartScript(srcArraySizeStatemnet,
                indexVarName),
                true,
                false);

        /*
         * XPD-7651: Also create an alias with srcItem object will track it -
         * which means we switch to this new alias now (if we're nested for
         * instance).
         */
        JSVarAliasAndContentObject sourceItemAlias =
                new JSVarAliasAndContentObject(
                        IScriptGeneratorInfoProvider.SOURCE_VAR_PREFIX
                                + indexVarName, sourceWrappedItem);

        script.addLine("var " + sourceItemAlias.jsVarAlias + " = " + srcGetterStatement + ";"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        if (directArrayMapping || simpleTargetType) {
            /**
             * Sid XPD-7408 - all direct collection to collection mapping
             * handling moved here as it is the same for both simple and complex
             * type objects when they are directly mapped (get from src
             * collection then set/add element in target collection.
             * 
             * Handle direct array mapping (effectively I don't think the '||
             * simpleTypeTarget is strictly necessary as these must by
             * definition be direct array mappings anyway.
             */
            if (mergeList) {
                /**
                 * When merging a direct array to array mapping, we need to
                 * overwrite existing elements until there are no more items to
                 * overwrite in target, then start appending.
                 */
                String targetArraySizeStatemnet =
                        targetInfoProvider
                                .getContribDelegatingScriptGenInfoProvider()
                                .getCollectionSizeScript(targetWrappedItem,
                                        aliasOfTargetParent.jsVarAlias);

                /* If i < target collection size. */
                script.addComment(Messages.DataMapperJavascriptGenerator_DirectArrayMapMergeComment_message);

                script.addLine("if (" + indexVarName + " < " + targetArraySizeStatemnet //$NON-NLS-1$ //$NON-NLS-2$
                        + ") {", true, false); //$NON-NLS-1$

                /* Then set target[i] = source; */
                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionSetElementScript(targetWrappedItem,
                                sourceItemAlias.jsVarAlias,
                                aliasOfTargetParent.jsVarAlias,
                                indexVarName));

                script.addLine("} else {", true, true); //$NON-NLS-1$

                /* else append source to target. */
                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionAddElementScript(targetWrappedItem,
                                sourceItemAlias.jsVarAlias,
                                aliasOfTargetParent.jsVarAlias,
                                false));

                script.addLine("}", false, true); //$NON-NLS-1$

            } else {
                /**
                 * For overwrite / append mode - just append to end of list (if
                 * it was an overwrite then list will have been cleared
                 * beforehand so is still an append here.
                 */
                script.addLine(""); //$NON-NLS-1$
                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionAddElementScript(targetWrappedItem,
                                sourceItemAlias.jsVarAlias,
                                aliasOfTargetParent.jsVarAlias,
                                false));
            }

        } else {
            /**
             * Generate script for inflating complex type arrays
             * 
             * i.e. creating target array elements from mappings into their
             * child content. each time around loop, new target element is
             * created and then 'inflated' with the content indicated by the
             * child mappings.
             */

            /*
             * Create variable representing the target array element that will
             * be created each time around loop.
             */
            JSVarAliasAndContentObject newTargetAlias =
                    new JSVarAliasAndContentObject(
                            IScriptGeneratorInfoProvider.TARGET_VAR_PREFIX
                                    + indexVarName, targetWrappedItem);

            String targetPath =
                    targetInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .resolvePath(targetWrappedItem,
                                    newTargetAlias.jsVarAlias);

            if (mergeList) {
                /**
                 * When merging lists the target variable is set to either an
                 * existing array element when current element index is less
                 * than target array size.
                 * 
                 * OR When > source elements than target elements, then after
                 * last existing target element, start creating new ones.
                 */
                String targetArraySizeStatemnet =
                        targetInfoProvider
                                .getContribDelegatingScriptGenInfoProvider()
                                .getCollectionSizeScript(targetWrappedItem,
                                        aliasOfTargetParent.jsVarAlias);

                script.addComment(Messages.DataMapperJavascriptGenerator_ArrayInflationMapMergeComment_message);

                script.addLine("var " + targetPath + ";"); //$NON-NLS-1$ //$NON-NLS-2$

                script.addLine("if (" + indexVarName + " < " + targetArraySizeStatemnet //$NON-NLS-1$ //$NON-NLS-2$
                        + ") {", true, false); //$NON-NLS-1$

                /*
                 * Sid XPD-7712: For Merge into target array allow the info
                 * provider to tell the difference between target.get() and
                 * source.get() - in some cases they may wish to generate
                 * different script.
                 */
                script.addLine(targetPath + " = " //$NON-NLS-1$
                        + targetInfoProvider
                                .getContribDelegatingScriptGenInfoProvider()
                                .getCollectionElementScriptForTargetMerge(targetWrappedItem,
                                        indexVarName,
                                        aliasOfTargetParent.jsVarAlias) + ";"); //$NON-NLS-1$

                /*
                 * Otherwise (when past end of target array), create new target
                 * element.
                 */
                script.addLine("} else {", true, true); //$NON-NLS-1$

                script.addLine(targetPath
                        + " = " //$NON-NLS-1$
                        + getCreateComplexItemScript(targetInfoProvider,
                                targetWrappedItem) + ";"); //$NON-NLS-1$

                script.addLine("}", false, true); //$NON-NLS-1$

            } else {
                /**
                 * For overwrite / append to target list always create new
                 * target element.
                 */
                /*
                 * Sid XPD-7660 - Creating a single instance is the same within
                 * an array as outside it.
                 */
                String targetCreateElementStatement =
                        getCreateComplexItemScript(targetInfoProvider,
                                targetWrappedItem);

                script.addLine("var " + targetPath + " = " //$NON-NLS-1$ //$NON-NLS-2$
                        + targetCreateElementStatement + ";"); //$NON-NLS-1$
            }

            /**
             * Inflate the target variable from the source mappings.
             */
            recursiveScriptGeneration(targetNode.getChildren(),
                    scriptDataMapper,
                    script,
                    mappings,
                    targetInfoProvider,
                    sourceInfoProvider,
                    mappingContentProvider,
                    sourceItemAlias,
                    newTargetAlias);

            if (mergeList) {
                /**
                 * When merge-inflating into target list, insert statement to
                 * see if we are beyond the last element of target array or not.
                 * 
                 * If we are then we need to add the target array element
                 * variable to the end of array.
                 * 
                 * If not then there's nothing to do because the target array
                 * element variable points to a current element onto which all
                 * the mapping assignments have been applied.
                 */
                String targetArraySizeStatemnet =
                        targetInfoProvider
                                .getContribDelegatingScriptGenInfoProvider()
                                .getCollectionSizeScript(targetWrappedItem,
                                        aliasOfTargetParent.jsVarAlias);

                script.addLine(""); //$NON-NLS-1$
                script.addLine("if (" + indexVarName + " >= " + targetArraySizeStatemnet //$NON-NLS-1$ //$NON-NLS-2$
                        + ") {", true, false); //$NON-NLS-1$

                // add to list script
                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionAddElementScript(targetWrappedItem,
                                newTargetAlias.jsVarAlias,
                                aliasOfTargetParent.jsVarAlias,
								// Sid ACE-8864 Switched to Boolean object handling for property (default=FALSE)
								Boolean.TRUE.equals(scriptDataMapper.getExcludeEmptyObjectsFromArrays()))); // Sid
																											// ACE-6538

                script.addLine("}", false, true); //$NON-NLS-1$

            } else {
                /**
                 * If not merging lists then append element to end of array
                 * (overwrite will have cleared the array first so effectively
                 * overwrite and append are the same here.
                 */
                String elementToAddToArray = null;

                /*
                 * If we are inflating an array then the element we add is the
                 * target item variable that was created and inflated from child
                 * mappings.
                 */
                elementToAddToArray = newTargetAlias.jsVarAlias;

                script.addLine(""); //$NON-NLS-1$
                script.addLine(targetInfoProvider
                        .getContribDelegatingScriptGenInfoProvider()
                        .getCollectionAddElementScript(targetWrappedItem,
                                elementToAddToArray,
                                aliasOfTargetParent.jsVarAlias,
								// Sid ACE-8864 Switched to Boolean object handling for property (default=FALSE)
								Boolean.TRUE.equals(scriptDataMapper.getExcludeEmptyObjectsFromArrays()))); // Sid
																											// ACE-6583
            }
        }

        /**
         * End of For loop
         */
        script.addLine(getForLoopEndScript(), false, true);

        /**
         * XPD-7408: close of parent null protection condition if necessary.
         */
        if (parentNullProtectAdded) {
            script.addLine("}", false, true); //$NON-NLS-1$
        }

        /*
         * Sid ACE-6583 Check if target data handling script is configured to exclude empty arrays for optional target
         * data (if the target supports it)
         */
        if (targetInfoProvider.getMinimumInstances(targetWrappedItem) < 1) {
			// Sid ACE-8864 Switched to Boolean object handling for property (default=FALSE)
			if (Boolean.TRUE.equals(scriptDataMapper.getExcludeEmptyOptionalArrays()))
			{
                /*
                 * Target data mappings are configured to Delete empty optional objects (but don't delete if root var
                 * (can't delete these in JavaScript)
                 */
				boolean isRoot = targetInfoProvider.getContentProvider().getParent(targetWrappedItem) == null;

                if (!isRoot) {
                    String deleteEmptyArrayScript = getDeleteEmptyArrayScript(targetInfoProvider,
                            targetWrappedItem,
                            tgtArrayItemAlias.jsVarAlias);

                    if (deleteEmptyArrayScript != null) {
                        script.append(deleteEmptyArrayScript);
                    }
                }
            }
        }
    }

    /**
     * This method locates the multi-instance ancestor that acts as the
     * "for i to n" control node for the given target node.
     * 
     * In other words, for any target node within an array inflation, this will
     * return the source node that is used for the for-loop iterator for the
     * given target node's level of nested arrays.
     * 
     * @param targetNode
     * @param mappings
     * @param targetInforProvider
     * @param sourceInfoProvider
     * 
     * @return The multi-instance ancestor that acts as the "for i to n" control
     *         node for the given target node.
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
     * 
     * @return the first descendent node of the given targetNode that is
     *         actually mapped.
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
     * Generate the target Array creator script fragment.
     * 
     * @param targetItem
     * @param infoProvider
     * @param script
     * @param aliasOfTargetParent
     */
    private void generateCreateArrayScript(Object targetItem,
            ContributableDataMapperInfoProvider infoProvider,
            JavaScriptStringBuilder script,
            JSVarAliasAndContentObject aliasOfTargetParent) {

        if (targetItem != null) {
            String createComplexObjectScript =
                    infoProvider.getContribDelegatingScriptGenInfoProvider()
                            .getArrayCreationScript(targetItem);

            if (createComplexObjectScript != null) {
                String targetPath = aliasOfTargetParent.jsVarAlias;

                if (targetPath == null || targetPath.length() == 0) {
                    targetPath = infoProvider.getObjectPath(targetItem);
                }
                targetPath =
                        infoProvider
                                .getContribDelegatingScriptGenInfoProvider()
                                .resolvePath(targetItem, targetPath);
                script.addLine("if (" + targetPath + " == null) {", true, false); //$NON-NLS-1$ //$NON-NLS-2$
                script.addLine(targetPath
                        + " = " + createComplexObjectScript + ";"); //$NON-NLS-1$ //$NON-NLS-2$

                script.addLine("}", false, true); //$NON-NLS-1$
            }
        }
    }

    /**
     * Generate script for a direct array to array mapping
     * 
     * @param targetNode
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param mappingContentProvider
     * @param scriptDataMapper
     * @param srcItem
     * @param targetItem
     * @param mapping
     * @param mappings
     * @param script
     * @param aliasOfSourceParent
     *            aliasOfTargetParent
     */
    private void generateDirectArrayAssignmentScript(
            DataMapperTreeNode targetNode,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            ScriptDataMapper scriptDataMapper, Object srcItem,
            Object targetItem, Mapping mapping, Object[] mappings,
            JavaScriptStringBuilder script,
            JSVarAliasAndContentObject aliasOfSourceParent,
            JSVarAliasAndContentObject aliasOfTargetParent) {

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

                DataMapperPlugin
                        .getDefault()
                        .getLogger()
                        .error(new Exception(),
                                "Unexpected: Target is not multi instance when expected to be."); //$NON-NLS-1$
                return;
            }

        } else {

            srcGetterStatement =
                    sourceInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .getCollectionElementScript(srcItem,
                                    indexVarName,
                                    aliasOfSourceParent.jsVarAlias);
            srcArraySizeStatemnet =
                    sourceInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .getCollectionSizeScript(srcItem,
                                    aliasOfSourceParent.jsVarAlias);
        }

        generateArrayMappingScript(scriptDataMapper,
                script,
                mappings,
                targetInfoProvider,
                sourceInfoProvider,
                mappingContentProvider,
                srcItem,
                aliasOfSourceParent,
                aliasOfTargetParent,
                targetNode,
                targetItem,
                srcGetterStatement,
                srcArraySizeStatemnet,
                indexVarName,
                true);
    }

    /**
     * Generate the javaScript statement(s) for assigning one single instance
     * object to another.
     * 
     * And now also where there is a mixed multi->single or single->multi.
     * 
     * @param targetNode
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param mappingContentProvider
     * @param scriptDataMapper
     * @param mappings
     * @param srcItem
     * @param targetItem
     * @param script
     * @param aliasOfSourceParent
     * @param aliasOfTargetParent
     */
    private void generateMixedOrSingleInstanceAssignmentScript(
            DataMapperTreeNode targetNode,
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            DataMapperMappingContentProvider mappingContentProvider,
            ScriptDataMapper scriptDataMapper, Object[] mappings,
            Object srcItem, Object targetItem, JavaScriptStringBuilder script,
            JSVarAliasAndContentObject aliasOfSourceParent,
            JSVarAliasAndContentObject aliasOfTargetParent) {

        /*
         * Check if this mapping is direct to a target item whose parent has
         * already been mapped (aka an enclosing parent mapping) and if so
         * return false if it is from a source item tree other than the
         * enclosing parent mapping.
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
            /* Rest to a valid (but unaliased) var and alias construct. */
            aliasOfSourceParent = new JSVarAliasAndContentObject(null, null);
        }

        /*
         * XPD-7408: Add LHS parent tree null protection condition
         */
        boolean parentNullProtectAdded = false;

        /**
         * Sid XPD-7975: We used to do a 'null check' on the *parent* of the
         * mapped source item (thinking that we didn't need to null check the
         * actual source element because that would either be a value or null
         * and the 'else' to the null check would be target=null anyway so there
         * would be no point in checking the sourceitem==null).
         * 
         * *However* some type systems have a concept of 'null' and 'not-present
         * or undefined' i.e. BOM child properties are always present BUT JSON
         * objects, properties declared in type definition may not be there at
         * all.
         * 
         * Therefore we need to do the null (or undefined) check right the way
         * down to the source of the mapping itself, to prevent us attempting
         * assignments from undefined source properties
         */
        JSVarAliasAndContentObject srcItemAlias =
                createChildItemAlias(targetInfoProvider,
                        aliasOfSourceParent,
                        srcItem);

        String nullProtectionCondition =
                getCheckNullTreeExpression(sourceInfoProvider,
                        srcItem,
                        srcItemAlias.jsVarAlias,
                        /*
                         * ACE-2088 if source is multi then need to null check
                         * right down to final element in path.
                         */
                        sourceInfoProvider.isMultiInstance(srcItem)
                                ? CheckNullTreeExpressionType.IS_MULTI_INSTANCE_CHECK
                                : CheckNullTreeExpressionType.IS_SINGLE_INSTANCE_CHECK);

        if (nullProtectionCondition != null
                && nullProtectionCondition.trim().length() > 0) {
            parentNullProtectAdded = true;

            script.addLine("if (" + nullProtectionCondition + ") {", //$NON-NLS-1$ //$NON-NLS-2$
                    true,
                    false);
        }

        if (targetInfoProvider.isSimpleTypeContent(targetItem)) {

            // get rhs statement
            String rhsObjectStatement =
                    getGetterStatement(script,
                            sourceInfoProvider,
                            srcItem,
                            aliasOfSourceParent.jsVarAlias);

            // create assignment
            script.addLine(getAssignmentStatement(sourceInfoProvider,
                    targetInfoProvider,
                    srcItem,
                    targetItem,
                    rhsObjectStatement,
                    aliasOfTargetParent));

        } else {
            /**
             * Handle complex type assignment
             */
            String rhsObjectStatement =
                    getGetterStatement(script,
                            sourceInfoProvider,
                            srcItem,
                            aliasOfSourceParent.jsVarAlias);

            script.addLine(getAssignmentStatement(sourceInfoProvider,
                    targetInfoProvider,
                    srcItem,
                    targetItem,
                    rhsObjectStatement,
                    aliasOfTargetParent));

            /**
             * Allow for mapping child content of already mapped parent (which
             * we may support one day)
             */
            if (!targetNode.getChildren().isEmpty()) {
                /*
                 * Sid XPD-7735 - Change to the target just-mapped object as the
                 * new alias parent of target, so that the nestde mappings are
                 * applied to correct target tree item.
                 * 
                 * NOTE that this is (as abov estatement) for a potential future
                 * use case. This actuall works for single instance target items
                 * with mapping to parent and child content but we will not
                 * allow it yet until all use cases (loops etc etc support it).
                 */
                JSVarAliasAndContentObject aliasOfNewTargetParent =
                        createChildItemAlias(targetInfoProvider,
                                aliasOfTargetParent,
                                targetItem);

                recursiveScriptGeneration(targetNode.getChildren(),
                        scriptDataMapper,
                        script,
                        mappings,
                        targetInfoProvider,
                        sourceInfoProvider,
                        mappingContentProvider,
                        /*
                         * Sid XPD-7651 - used to pass aliases down as null,
                         * don't think that was right.
                         */
                        aliasOfSourceParent,
                        aliasOfNewTargetParent);
            }
        }

        /*
         * XPD-7408: close of parent null protection condition if necessary.
         */
        if (parentNullProtectAdded) {
            /*
             * Sid XPD-7814 Target of mapping should be set to null if source of
             * mapping is not present.
             */
            script.addLine("} else {", true, true); //$NON-NLS-1$

            /*
             * Sid ACE-6367 Allow the script info provider to give 'unset target if source data does not exist'
             * statements
             */
            script.addLine(targetInfoProvider.getContribDelegatingScriptGenInfoProvider()
                    .getAssignmentElseStatement(targetItem, aliasOfTargetParent.jsVarAlias));
            // script.addLine(getAssignmentStatement(sourceInfoProvider,
            // targetInfoProvider,
            // srcItem,
            // targetItem,
            // "null", //$NON-NLS-1$
            // aliasOfTargetParent));

            script.addLine("}", false, true); //$NON-NLS-1$

        }
    }

    /**
     * Wraps up the given target content info provider's
     * 'getAssignmentStatement()' so that we can distinguish between a straight
     * forward one-to-one assigment and a single-instance source to
     * multi-instance target (for multi-instance sub-process output for
     * example).
     * <p>
     * These require slightly different handling.
     * 
     * @param sourceInfoProvider
     * @param targetInfoProvider
     * @param sourceItem
     * @param targetItem
     * @param rhsObjectStatement
     * @param aliasOfTargetParent
     * 
     * @return The assignment statement.
     */
    private String getAssignmentStatement(
            ContributableDataMapperInfoProvider sourceInfoProvider,
            ContributableDataMapperInfoProvider targetInfoProvider,
            Object sourceItem, Object targetItem, String rhsObjectStatement,
            JSVarAliasAndContentObject aliasOfTargetParent) {

        boolean sourceIsMulti = false;

        sourceIsMulti = sourceInfoProvider.isMultiInstance(sourceItem);

        boolean targetIsMulti = targetInfoProvider.isMultiInstance(targetItem);

        if (!sourceIsMulti && targetIsMulti) {

            return targetInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getAssignmentStatement(targetItem,
                            rhsObjectStatement,
                            aliasOfTargetParent.jsVarAlias);

        } else {

            return targetInfoProvider
                    .getContribDelegatingScriptGenInfoProvider()
                    .getAssignmentStatement(targetItem,
                            rhsObjectStatement,
                            aliasOfTargetParent.jsVarAlias);
        }

    }

    /**
     * Return true if the given mapping is a script mapping.
     * <p>
     * Nominally this method assumes that the model object for the mapping is an
     * xpdl2 {@link DataMapping}. If not then it will be necessary to override
     * this method.
     * 
     * @param mapping
     * @return true if the given mapping is a script mapping.
     */
    protected boolean isScriptMapping(Object mapping) {
        EObject modelObjectForMapping = getModelObjectForMapping(mapping);
        if (!(modelObjectForMapping instanceof DataMapping)) {
            /*
             * The sub-class's mapping content provider should be returning
             * Mapping class objects with DataMapping as the model ibject for
             * the mapping OR should have overwritten this method.
             */
            throw new IllegalStateException(
                    "Expected mapping content model object to be of class" //$NON-NLS-1$
                            + DataMapping.class.getName());
        }

        if (DataMappingUtil.isScripted((DataMapping) modelObjectForMapping)) {
            return true;
        }

        return false;
    }

    /**
     * Get the model object for the mapping (this is used when a mapping needs
     * to be added as the target object for a problem marker).
     * <p>
     * Nominally the sub-class provided mapping content provider is expected to
     * return {@link Mapping} objects, if not the sub-class will need to
     * override this method.
     * 
     * @param mapping
     * 
     * @return The model object for the mapping (this is used when a mapping
     *         needs to be added as the target object for a problem marker).
     */
    protected EObject getModelObjectForMapping(Object mapping) {
        if (!(mapping instanceof Mapping)) {
            /*
             * The sub-class's mapping content provider should be returning
             * Mapping class objects or should have overwritten this method.
             */
            throw new IllegalStateException(
                    "Expected mapping content object to be of class" //$NON-NLS-1$
                            + Mapping.class.getName());
        }

        /* Get the mapping model as an EObject if possible. */
        Object mappingModel = ((Mapping) mapping).getMappingModel();
        if (!(mappingModel instanceof EObject)) {
            if (mappingModel instanceof IAdaptable) {
                mappingModel =
                        ((IAdaptable) mappingModel).getAdapter(EObject.class);
            }
        }

        if (!(mappingModel instanceof EObject)) {
            /*
             * The sub-class's mapping content provider should be returning
             * Mapping class objects constructed with mappingModel or should
             * have overwritten this method.
             */
            throw new IllegalStateException(
                    "Expected Mapping object to be constructed with EObject (or adaptable to EObject) mappingModel. " //$NON-NLS-1$
                            + "Change MappingContentProvider to construct Mapping object's with mapping model or override this method."); //$NON-NLS-1$
        }

        return (EObject) mappingModel;
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
     * Find the mapping whose target is the given target content item in the
     * given set of mappings.
     * 
     * @param targetInfoProvider
     * @param mappings
     * @param targetItem
     * 
     * @return The mapping or <code>null</code> if the given target object is
     *         not itself mapped.
     */
    private Mapping findExplicitMapping(
            ContributableDataMapperInfoProvider targetInfoProvider,
            Object[] mappings, Object targetItem) {

        /*
         * Sid XPD-7651: We should be using equals on the content objects ratehr
         * than examining paths.
         */
        Mapping explicit = null;

        if (targetItem != null) {
            for (Object next : mappings) {
                if (next instanceof Mapping) {
                    Mapping nextMapping = (Mapping) next;

                    /*
                     * Only interested in non virtual mappings (virtual mappings
                     * created for for like-mapping child content will have null
                     * mappingModel.
                     */
                    if (!(nextMapping instanceof VirtualLikeMapping)) {
                        if (targetItem.equals(nextMapping.getTarget())) {
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
     * Wraps up the given target content info provider's
     * 'getAssignmentStatement()' so that we can distinguish between a straight
     * forward one-to-one assigment and a multi-instance source to
     * single-instance target (for multi-instance sub-process input for
     * example).
     * <p>
     * These require slightly different handling.
     * <p>
     * It also handles the different between the getter statement for a user
     * defined script mapping source item and contributed LHS content.
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

        String lhsObjectStatement = null;
        if (srcItem instanceof ScriptInformation) {
            lhsObjectStatement =
                    getScriptInfoText((ScriptInformation) srcItem, script);
        } else {
            lhsObjectStatement =
                    sourceInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .getGetterStatement(srcItem, jsAliasVarName);
        }
        return lhsObjectStatement;
    }

    /**
     * Sid XPD-7408 Get the condition expression that can be used inside an
     * <code>if ( xxxx ) {</code> for the prevention of accessing some child
     * content within a parent tree that may not be assigned.
     * <p>
     * For instance if a mapping is made from Parent.child.grandchild then the
     * expression would be <code>"Parent && Parent.child"<code/>
     * 
     * @param sourceInfoProvider
     *            The provider that provided the content srcItem in the first
     *            place.
     * @param srcItem
     *            The object whose parent tree may be accessed
     * @param jsVarAlias
     *            Override JavaScript variable name if required (else <code>null
     *            </code> or <code>""</code> if the original source item
     *            name/path should be used.
     * @param checkType
     *            The purpose for which the null check is being made.
     * 
     * @return <code>null</code> protection expression or <code>null</code> /
     *         <code>""</code> if none required.
     */
    private String getCheckNullTreeExpression(
            ContributableDataMapperInfoProvider sourceInfoProvider,
            Object srcItem, String jsAliasName,
            CheckNullTreeExpressionType checkType) {

        String srcNullProtectionExpression = null;

        /*
         * No need to protect script on LHS as we won't be trying to access
         * "ScriptReturn.child" directly.
         */
        if (!(srcItem instanceof ScriptInformation)) {
            srcNullProtectionExpression =
                    sourceInfoProvider
                            .getContribDelegatingScriptGenInfoProvider()
                            .getCheckNullTreeExpression(srcItem,
                                    jsAliasName,
                                    checkType);
        }

        return srcNullProtectionExpression;
    }

    /**
     * This method recurses thru the target tree gathering all the target tree
     * nodes that are THEMSELVES MAPPED or have a DESCENDENT MAPPED.
     * <p>
     * This forms the list of target objects that must either be created (if not
     * directly mapped) or assigned (if directly mapped).
     * 
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
                "for (var " + uniqueVarName + " = 0; " + uniqueVarName + " < " + collectionSizeText + "; " + uniqueVarName + "++) { "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
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
     * Sid ACE-6583 Get the script to delete an empty target object IF after it has been created and all descendant
     * mappings have been applied, it is still empty. Return <code>null</code> if deletion of empty target objects is
     * not required/desired.
     * 
     * @param targetPath
     * @param jsVarAlias
     *            If not <code>null</code> then this is the alternate 'temporary variable' object path to use (usually
     *            used when dealing with mappings of descendant objects inside a list).
     * 
     * @return javascript to delete an empty target object IF after it has been created and all descendant mappings have
     *         been applied, it is still empty. Return <code>null</code> if deletion of empty target objects is not
     *         required/desired.
     */
    private String getDeleteEmptyObjectScript(ContributableDataMapperInfoProvider infoProvider, Object complexItem,
            String jsVarAlias) {

        String text =
                infoProvider.getContribDelegatingScriptGenInfoProvider().getDeleteEmptyObjectScript(complexItem,
                        jsVarAlias);

        if (text != null && !text.isEmpty()) {
            return text;
        }

        return null;
    }

    /**
     * Sid ACE-6583 Get the script to delete an empty target array IF after it has been created and all descendant
     * mappings have been applied, it is still empty.
     * 
     * @param targetPath
     * @param jsVarAlias
     *            If not <code>null</code> then this is the alternate 'temporary variable' object path to use (usually
     *            used when dealing with mappings of descendant objects inside a list).
     * 
     * @return javascript to delete an empty target rray IF after it has been created and all descendant mappings have
     *         been applied, it is still empty. Return <code>null</code> if deletion of empty target objects is not
     *         required/desired.
     */
    private String getDeleteEmptyArrayScript(ContributableDataMapperInfoProvider infoProvider, Object complexItem,
            String jsVarAlias) {
        String text = infoProvider.getContribDelegatingScriptGenInfoProvider().getDeleteEmptyArrayScript(complexItem,
                jsVarAlias);

        if (text != null && !text.isEmpty()) {
            return text;
        }

        return null;
    }


    /*
     * Sid XPD-7660 - Removed getCreateArrayInstanceScript() this was called for
     * a single instance within an array for a given source item. There is no
     * difference between that and creation of a single instance object not in
     * an array.
     */

    /**
     * @param mapping
     * @param infoProvider
     * @param object
     * 
     * @return The array inflation type governing the array target (or nearest
     *         array ancestor)
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
     * 
     * @return The array inflation type that is in play for the given target
     *         path.
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
     * @param targetItem
     * @param mappings
     * 
     * @return The data mapping for the given target object
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
        String funcName = getUniqueJsFunctionName();

        generatedScript
                .addComment(String
                        .format(Messages.DataMapperJavascriptGenerator_FunctionHeaderComment_message,
                                scriptInfo.getName()));

        generatedScript.addLine("function " + funcName + "() { ", true, false); //$NON-NLS-1$//$NON-NLS-2$
        generatedScript.addLine(scriptInfo.getExpression().getText());
        generatedScript.addLine("}", false, true); //$NON-NLS-1$

        /*
         * Sid XPD-8277. We used to return the actual function "_f1_()" here.
         * However there are some (REST input) generators that use the this
         * multiple times in their assignment (for example (_f1_() != null ?
         * _f1_() : null) and so on.
         * 
         * That means that if the method is not idempotent then it would have
         * unwanted side effects.
         * 
         * So instead, we create the function and then evaluate it ONCE to a
         * temp variable and then pass back this for use as RHS of mapping
         * statement
         */
        String resultVarName = "$" + funcName + "result";
        generatedScript.addLine(resultVarName + " = " + funcName + "();"); //$NON-NLS-1$

        return resultVarName;
    }

    /**
     * @return unique function name (e.g., _f1_)
     */
    private String getUniqueJsFunctionName() {

        return "_f" + functionIndex++ + "_"; //$NON-NLS-1$//$NON-NLS-2$
    }

    /**
     * Small data class to hold a JavaScript variable[+relative child path] that
     * is used to alias a given part of the tree.
     * 
     * This is used so that the generation code can tell exactly what part of
     * the tree was being aliased (which is necessary for instance when dealing
     * with mappings from source content several child levels below where a var
     * representing a source array element was created.
     * 
     * @author aallway
     * @since 14 Jul 2015
     */
    private static class JSVarAliasAndContentObject {
        /**
         * The replacement alias for the given contentObject (i.e. the temporary
         * javaScript variable [+relative path for nested mappings under there])
         * 
         * This may be <code>null</code> if no alias has been created yet.
         */
        public String jsVarAlias;

        /**
         * The content object for the level of the tree that the alias string
         * represents
         */
        public Object contentObject;

        /**
         * @param jsVarAlias
         * @param contentObject
         */
        public JSVarAliasAndContentObject(String jsVarAlias,
                Object contentObject) {
            super();
            this.jsVarAlias = jsVarAlias;
            this.contentObject = contentObject;
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return String
                    .format("JSVarAliasAndContentObject:  Alias(%s) Object(%s)", //$NON-NLS-1$
                            jsVarAlias,
                            contentObject);
        }

    }

    /**
     * THIS METHOD IS NOT PART OF PRODUCT - IT IS AN AID to UPDATING THE
     * ProcessDataMapperScriptGenerationTests processes.
     * <p>
     * see
     * {@link DataMapperMappingScriptsToJavaScriptContribution#convertScriptForGrammar(String, String, String, Expression, String)}
     * and comment out code there to trigger this change from a manual data
     * mapper script grammar change to javaScript
     * 
     * @param process
     */
    public static void updateDataMapperScriptGenTestProcess(
            final Process process) {

        RecordingCommand cmd =
                new RecordingCommand(
                        (TransactionalEditingDomain) WorkingCopyUtil
                                .getEditingDomain(process)) {

                    @Override
                    protected void doExecute() {

                        TreeIterator<Object> allContents =
                                EcoreUtil.getAllContents(process, true);

                        List<ScriptDataMapper> sdms = new ArrayList<>();

                        for (Iterator iterator = allContents; iterator
                                .hasNext();) {
                            EObject eo = (EObject) iterator.next();

                            if (eo instanceof ScriptDataMapper) {
                                sdms.add((ScriptDataMapper) eo);
                            }
                        }

                        for (ScriptDataMapper sdm : sdms) {
                            Activity activity =
                                    (Activity) Xpdl2ModelUtil.getAncestor(sdm,
                                            Activity.class);

                            if (activity != null) {
                                String javascript =
                                        new DataMapperJavascriptGenerator()
                                                .convertMappingsToJavascript(sdm);

                                Description description =
                                        Xpdl2Factory.eINSTANCE
                                                .createDescription();
                                description.setValue(javascript);

                                activity.setDescription(description);
                            }

                        }

                    }
                };

        WorkingCopyUtil.getEditingDomain(process).getCommandStack()
                .execute(cmd);

    }

}