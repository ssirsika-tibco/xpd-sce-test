/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.datamapper.api.DataMapperUtils;
import com.tibco.xpd.datamapper.api.VirtualLikeMapping;
import com.tibco.xpd.datamapper.infoProviders.DataMapperContentContributionHelper;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.xpdExtension.LikeMappingExclusion;
import com.tibco.xpd.xpdExtension.LikeMappingExclusions;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This class is responsible for providing UI mappings content and any related
 * content (such as like mapping etc). As it is not aware of the data types
 * being contributed by the data mapper contributors, it will always ask the
 * contributors to provide the required information.
 * <p>
 * 
 * <b>Note 1:</b> This class will return the appropriate
 * {@link VirtualLikeMapping} mappings for the virtual child content mappings
 * (same named objects on LHS / RHS) that are implied by explicit Like Mappings
 * (taking Like Mapping Exclusions into account). These mappings will contain
 * the original parent Like Mapping.
 * 
 * <p>
 * <b>Note 2:</b> This class deals with ScriptInformation, which is implemented
 * at the Data Mapper layer and is not contributed due to the nature of Mapper (
 * which expects ScriptInformation rather than any other type for Scripts).
 * Sub-Classed for each scenario must implement getScriptDataMapper() as the
 * only they know where the ScriptDataMapper element is added.
 * 
 * @author Ali / Nick / Sid
 * @since 11 Feb 2015
 */
public class DataMapperMappingContentProvider implements
        IStructuredContentProvider {

    private IScriptDataMapperProvider sdmProvider;

    private Object input;

    /**
     * Sid: XPD-8251 getElements() is expensive because every time we generate a
     * Mapping object we need to resolve the source and target paths to real
     * content objects. This resolution *can* be expensive
     * 
     * To allow us to safely cache the return from getElements() for next call
     * we keep this REPRESENTATION of the Last set of mappings returned by
     * getElements() - JUST the actual mappings (without virual 'like' mappings)
     * 
     * This is only a representation of the data mappings NOT the actual xpdl
     * mapping themselves. We do this because otherwise the xpdl mapping we
     * reference would change in our cache at the same time as it is changed by
     * a command so it would ALWAYS look like it hadn't changed to us (because
     * our cache would be the actual xpdl model)
     * 
     * So instead we cache a list of {@link CachedMapping}'s that store the
     * relevant info that determines whether the mappings have changed.
     */
    private List<CachedMapping> cachedExplicitMappings = null;

    /**
     * Last set of UI mappings returned by getElements() - all mappings
     * including virtual mappings (those implied by an explicit 'like' mapping
     */
    private Mapping cachedCompleteMappings[] = null;

    /**
     * Collection of NON-mappings between source and target complex child
     * considered to be equivalent (and therefore whose children would be
     * 'like-mapped').
     * 
     * This Collection is assigned by previous call to
     * {@link #getElements(Object)}
     */
    private Collection<VirtualLikeMapping> equivalentLikeMappingComplexChildren =
            Collections.emptyList();

    public DataMapperMappingContentProvider(
            IScriptDataMapperProvider sdmProvider) {
        this.sdmProvider = sdmProvider;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     * 
     * @param viewer
     * @param oldInput
     * @param newInput
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.input = newInput;
        equivalentLikeMappingComplexChildren = new ArrayList<>();

        /* Sid XPD-8251 Always clear the current mappings cache on input change */
        this.cachedCompleteMappings = null;
        this.cachedExplicitMappings = null;
    }

    /**
     * Owner should call this when any change may have happened to the mappings
     * in referenced input object
     * 
     * This method will check whether the cache of mappings last loaded is out
     * of date and reset it. This means that even when we get multiple refreshes
     * when there have be no changes (which happens on initial load of the
     * property tab for instance) then we don't throw away the cache
     * unnecessarily.
     * 
     * @param input
     *            The input object for the content provider (i.e. the activity)
     */
    public void refresh(Object input) {
        if (cachedExplicitMappings != null && cachedCompleteMappings != null) {
            if (input instanceof EObject) {
                ScriptDataMapper scriptDataMapper =
                        sdmProvider.getScriptDataMapper(input);

                if (scriptDataMapper != null && input instanceof EObject) {
                    List<DataMapping> xpdlMappings =
                            scriptDataMapper.getDataMappings();

                    if (doCachedMappingsMatch(xpdlMappings)) {
                        /*
                         * Cached mappings match that expected for current
                         * explicit mapping set.
                         */
                        return;
                    }
                }
            }
        }

        /*
         * Couldn't re-use current cache of mappings so throw cache away. Next
         * time getElements() is called it will rebuild the cache.
         */
        cachedCompleteMappings = null;
        cachedExplicitMappings = null;
    }

    /**
     * Compare the actual explicit xpdl mapping paths with the last set of
     * Mappings generated for them. If they haven't changed then we can keep
     * them.
     * 
     * @param xpdlMappings
     */
    private boolean doCachedMappingsMatch(List<DataMapping> xpdlMappings) {
        if (xpdlMappings == null || cachedExplicitMappings == null) {
            return false;
        }

        if (xpdlMappings.size() != cachedExplicitMappings.size()) {
            return false;
        }

        for (DataMapping dataMapping : xpdlMappings) {
            boolean foundEquiv = false;

            for (CachedMapping cachedMapping : cachedExplicitMappings) {

                if (cachedMapping.equals(dataMapping)) {
                    foundEquiv = true;
                    break;
                }
            }

            if (!foundEquiv) {
                return false;
            }
        }

        return true;
    }

    /**
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     * 
     */
    @Override
    public void dispose() {
    }

    /**
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     * 
     * @param inputElement
     * @return
     */
    @Override
    public final Object[] getElements(Object input) {

        /*
         * Sid XPD-8251 If we have a cache of the previous return (that hasn't
         * been unset by a call to refresh)
         */
        if (cachedCompleteMappings != null) {
            return cachedCompleteMappings;
        }

        ScriptDataMapper scriptDataMapper =
                sdmProvider.getScriptDataMapper(input);

        equivalentLikeMappingComplexChildren = new ArrayList<>();

        if (scriptDataMapper != null && input instanceof EObject) {
            EObject inputElement = (EObject) input;

            List<DataMapping> xpdlMappings = scriptDataMapper.getDataMappings();

            /*
             * Get the set of Mapping objects (with source and target content
             * resolved by info providers and Wrapped as
             * WrappedContributedContent
             */
            List<Mapping> explicitMappings =
                    getMappingsFromContributors(inputElement, xpdlMappings);

            /*
             * XPD-7676: get the additional 'virtual' child content mappings
             * that are implied by Like Mappings.
             */

            List<Mapping> virtualLikeMappings =
                    getVirtualLikeMappings(inputElement,
                            explicitMappings,
                            equivalentLikeMappingComplexChildren);

            /*
             * Combine the lists and get out.
             */
            List<Mapping> mappingListWithLikeMappings = new ArrayList<>();
            mappingListWithLikeMappings.addAll(explicitMappings);
            mappingListWithLikeMappings.addAll(virtualLikeMappings);

            /*
             * Sid XPD-8251 Keep a representative cache of the mappings details
             * we are about to return (for comparison on next refresh to see if
             * actual mapping cache must be thrown away) and also cache the set
             * of actual mapping to return next time we're called (if not been
             * reset by last refresh().
             */
            cachedExplicitMappings = getCachedMappings(explicitMappings);
            

            cachedCompleteMappings = mappingListWithLikeMappings
                    .toArray(new Mapping[mappingListWithLikeMappings.size()]);
            
            return cachedCompleteMappings;

        }
        return new Object[0];
    }

    /**
     * Sid XPD-8251 Create a representative list of cached mappings information
     * for the given real mappings.
     * 
     * @param mappings
     * @return a representative list of cached mappings information for the
     *         given real mappings.
     */
    private List<CachedMapping> getCachedMappings(List<Mapping> mappings) {
        List<CachedMapping> cachedMappings = new ArrayList<>();

        if (mappings != null) {
            for (Mapping mapping : mappings) {
                if (mapping.getMappingModel() instanceof DataMapping) {
                    cachedMappings.add(new CachedMapping((DataMapping) mapping
                            .getMappingModel()));
                }
            }
        }

        return cachedMappings;
    }

    /**
     * Collection of NON-mappings between source and target complex child
     * considered to be equivalent (and therefore whose children would be
     * 'like-mapped').
     * 
     * This Collection is assigned by previous call to
     * {@link #getElements(Object)}
     * 
     * @return The collection of like mapping complex children.
     */
    public Collection<VirtualLikeMapping> getEquivalentLikeMappingComplexChildren() {
        return equivalentLikeMappingComplexChildren;
    }

    /**
     * Asks the source/target contributors for each of the given xpdl mappings
     * to provide objects for the given source/target path (which are also
     * provided by contributors). Then wraps each of the source/target objects
     * and adds to the mappings list to be returned (only wraps source object if
     * its not ScriptInformation, which is never wrapped)
     * 
     * @param mapperInput
     * @param xpdlMappings
     * 
     * @return List of mappings from the xpdl mappings.
     */
    private List<Mapping> getMappingsFromContributors(EObject mapperInput,
            List<DataMapping> xpdlMappings) {

        List<Mapping> mappingList = new ArrayList<Mapping>();

        if (mapperInput != null && xpdlMappings != null) {

            for (DataMapping xpdlMapping : xpdlMappings) {

                /*
                 * Get the source contributor and ask them to provide object for
                 * the given source path (source path is also provided by
                 * contributors). Then wrap each of the source/target objects
                 * and add to mappings list (only wrap source object if its not
                 * ScriptInformation, which is never wrapped)
                 */

                Object wrappedTargetObject = null;
                Object wrappedSourceObject = null;

                ScriptInformation information =
                        (ScriptInformation) Xpdl2ModelUtil
                                .getOtherElement(xpdlMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Script());
                if (information != null) {
                    wrappedSourceObject = information;

                } else {
                    /*
                     * Get the source content contributor that provided the
                     * content in the first place.
                     * 
                     * It knows how to get the tree item object for the given
                     * path
                     */
                    AbstractDataMapperContentContributor srcContributor =
                            getSourceContributor(xpdlMapping);

                    if (srcContributor != null) {
                        /*
                         * source is always in the actual when its not a script
                         * mapping
                         */
                        Expression actual = xpdlMapping.getActual();
                        if (actual != null) {
                            String actualObjPath = actual.getText();
                            Object actualObj =
                                    srcContributor.getInfoProvider()
                                            .getObjectForPath(actualObjPath,
                                                    mapperInput);
                            if (actualObj != null) {
                                WrappedContributedContent parent =
                                        getWrappedObjectParent(srcContributor,
                                                actualObj);
                                wrappedSourceObject =
                                        new WrappedContributedContent(
                                                srcContributor, actualObj,
                                                parent, sdmProvider, input);
                            }
                        }
                    }
                }

                /*
                 * Get the target content contributor that provided the content
                 * in the first place.
                 * 
                 * It knows how to get the tree item object for the given path
                 */
                AbstractDataMapperContentContributor targetContributor =
                        getTargetContributor(xpdlMapping);

                if (targetContributor != null) {
                    // target is always in the formal
                    String targetPath = xpdlMapping.getFormal();
                    if (targetPath != null) {
                        Object formalObj =
                                targetContributor.getInfoProvider()
                                        .getObjectForPath(targetPath,
                                                mapperInput);

                        if (formalObj != null) {

                            WrappedContributedContent parent =
                                    getWrappedObjectParent(targetContributor,
                                            formalObj);
                            wrappedTargetObject =
                                    new WrappedContributedContent(
                                            targetContributor, formalObj,
                                            parent, sdmProvider, input);
                        }
                    }
                }

                Mapping wrappedMapping =
                        new Mapping(wrappedSourceObject, wrappedTargetObject,
                                xpdlMapping);

                // set like mapping annotations, if applicable
                if (isLikeMappingSet(xpdlMapping)) {
                    wrappedMapping
                            .setStartLineAnnotation(getLikeMappingImage());
                    wrappedMapping.setEndLineAnnotation(getLikeMappingImage());
                }

                mappingList.add(wrappedMapping);
            }

        }

        return mappingList;
    }

    /**
     * This method gets {@link VirtualLikeMapping} for each virtual child
     * content mapping (same named objects on LHS / RHS) that are implied by
     * explicit Like Mappings
     * <p>
     * This recursively process the RHS looking for equivalent LHS content with
     * the same name and creates {@link VirtualLikeMapping} for each one.
     * <p>
     * The virtual nmappings will <b>not include</b>
     * <li>Read only targets (there's no point!)</li>
     * <li>Targets already explicitly mapped by user (we stop stop recursing at
     * that point as a direct assignment / nested like mapping will be
     * employed).</li>
     * <li>Targets added to the target item exclusions list by the user.
     * <li>
     * 
     * @param inputElement
     * @param explicitMappings
     * @param equivalentLikeMappingComplexChildren
     *            Return Collection of Non-Mappings of source and target complex
     *            child considered to be equivalent. We don't create mappings
     *            between same-named source and target complex type children
     *            (because we will recurs into their children until we reach all
     *            the leaf-nodes). However, for validation purposes we will need
     *            to be able to compare the equivalent complex source and
     *            targets.
     * 
     * @return the virtual like mappings.
     */
    private List<Mapping> getVirtualLikeMappings(EObject inputElement,
            Collection<Mapping> explicitMappings,
            Collection<VirtualLikeMapping> equivalentLikeMappingComplexChildren) {
        /*
         * Gather the target (unwrapped) objects for all explicitly mapped
         * objects
         */
        Set<Object> explicitMappingTargets = new HashSet<>();

        for (Mapping explicitMapping : explicitMappings) {
            if (explicitMapping.getTarget() instanceof WrappedContributedContent) {
                Object contributedObject =
                        ((WrappedContributedContent) explicitMapping
                                .getTarget()).getContributedObject();
                explicitMappingTargets.add(contributedObject);
            }
        }

        /*
         * Process all Like Mappings
         */
        List<Mapping> virtualLikeMappings = new ArrayList<Mapping>();

        for (Mapping explicitMapping : explicitMappings) {

            if (DataMapperUtils.isLikeMapping(explicitMapping)) {

                List<Mapping> vLMs =
                        getVirtualLikeMappingChildren(inputElement,
                                explicitMapping,
                                explicitMappingTargets,
                                equivalentLikeMappingComplexChildren);

                virtualLikeMappings.addAll(vLMs);
            }
        }

        return virtualLikeMappings;
    }

    /**
     * This method gets {@link VirtualLikeMapping} for each virtual child
     * content mapping (same named objects on LHS / RHS) that are implied by
     * explicit Like Mappings
     * <p>
     * This recursively process the RHS looking for equivalent LHS content with
     * the same name and creates {@link VirtualLikeMapping} for each one.
     * (ignoring items that are in the mapping exclusion list AND target items
     * that are themselves explicitly mapped).
     * 
     * @param inputElement
     * @param likeMapping
     * @param explicitMappingTargets
     *            The already explicitly mapped target items (unwrapped)
     * @param equivalentLikeMappingComplexChildren
     *            Return Collection of Non-Mappings of source and target complex
     *            child considered to be equivalent. We don't create mappings
     *            between same-named source and target complex type children
     *            (because we will recurs into their children until we reach all
     *            the leaf-nodes). However, for validation purposes we will need
     *            to be able to compare the equivalent complex source and
     *            targets.
     * 
     * @return the virtual like mappings.
     */
    private List<Mapping> getVirtualLikeMappingChildren(EObject inputElement,
            Mapping likeMapping, Collection<Object> explicitMappingTargets,
            Collection<VirtualLikeMapping> equivalentLikeMappingComplexChildren) {

        List<Mapping> virtualLikeMappings = new ArrayList<Mapping>();

        if (likeMapping.getMappingModel() instanceof DataMapping
                && likeMapping.getSource() instanceof WrappedContributedContent
                && likeMapping.getTarget() instanceof WrappedContributedContent) {

            DataMapping dataMapping =
                    (DataMapping) likeMapping.getMappingModel();

            AbstractDataMapperContentContributor sourceContributor =
                    getSourceContributor(dataMapping);

            AbstractDataMapperContentContributor targetContributor =
                    getTargetContributor(dataMapping);

            if (targetContributor != null) {
                /*
                 * Get any excluded RHS like mapping children for this mapping
                 * (original unwrapped content objects)
                 */
                Collection<Object> likeMappingExclusions =
                        internalGetLikeMappingExclusions(dataMapping,
                                inputElement,
                                false);

                /*
                 * Get the source and target children and then we're ready to
                 * compare them and create mappings.
                 */
                WrappedContributedContent wrappedContentObject =
                        (WrappedContributedContent) likeMapping.getSource();

                /*
                 * Sid XPD-7839 use method getMappableChildren() that ignores
                 * aritficial objects in tree so that like named children
                 * underneath them can be mapped to similar tree structure
                 * without the same artificial object (like a 'choice' node)
                 */
                List<Object> sourceChildren =
                        getMappableChildren(sourceContributor,
                                ((WrappedContributedContent) likeMapping
                                        .getSource()).getContributedObject());

                List<Object> targetChildren =
                        getMappableChildren(targetContributor,
                                ((WrappedContributedContent) likeMapping
                                        .getTarget()).getContributedObject());

                /*
                 * Enter the recursive processing phase.
                 */
                recursiveAddVirtualLikeMappings(inputElement,
                        dataMapping,
                        sourceChildren,
                        sourceContributor,
                        targetChildren,
                        targetContributor,
                        explicitMappingTargets,
                        likeMappingExclusions,
                        virtualLikeMappings,
                        1,
                        equivalentLikeMappingComplexChildren);

            }
        }

        return virtualLikeMappings;
    }

    /**
     * Sid XPD-7839: For like mappings we need to ignore artificial parent nodes
     * (like schema derived BOM choice elements) in the tree so that you don't
     * have to have the same artificial node in the other tree.
     * <p>
     * For instance a like mapping from an object that has A.B onto an object
     * that has A.ArtificialObject.B should succesfully map the two 'B's
     * <p>
     * In order to do this we check the providers child list for the given
     * contentObject and for any that are artificial we pull up their children
     * into the main child list.
     * 
     * @param contentContributor
     * @param contentObject
     * 
     * @return The set of children taking aritficial parent node children into
     *         account.
     */
    private List<Object> getMappableChildren(
            AbstractDataMapperContentContributor contentContributor,
            Object contentObject) {
        List<Object> mappableChildren = new ArrayList<>();

        Object[] children =
                contentContributor.getInfoProvider().getContentProvider()
                        .getChildren(contentObject);

        for (Object child : children) {
            if (contentContributor.getInfoProvider().isArtificialObject(child)) {
                /*
                 * recurs and get the children of the artificial object (hence
                 * treating the level of the tree liek it is not there.
                 */
                mappableChildren.addAll(getMappableChildren(contentContributor,
                        child));

            } else {
                mappableChildren.add(child);
            }
        }

        return mappableChildren;
    }

    /**
     * This recursively processes the RHS looking for equivalent LHS content
     * with the same name and creates {@link VirtualLikeMapping} for each one.
     * (ignoring items that are in the mapping exclusion list AND target items
     * that are themselves explicitly mapped).
     * <p>
     * New {@link VirtualLikeMapping} mappings are added to the given list.
     * <p>
     * All content objects here are as ORIGINAL contributed objects(<b>not</b>
     * wrapped in {@link WrappedContributedContent}) (sourceChildren,
     * targetChildren, likeMappingsExclusions)
     * 
     * @param inputElement
     * @param dataMapping
     *            The physical xpdl {@link DataMapping} for the actual parent
     *            like mapping.
     * @param sourceChildren
     *            The sourceItems at the matching nesting level to the target
     *            items.
     * @param sourceContributor
     *            The content contributor of the source items.
     * @param targetChildren
     *            The (unwrapped) targetItems to match and map to the same named
     *            sourceItems
     * @param targetContributor
     *            The content contributor of the source items.
     * @param explicitMappingTargets
     *            The already explicitly mapped target items (unwrapped)
     * @param likeMappingExclusions
     *            The target items excluded from like mapping (unwrapped)
     * 
     * @param virtualLikeMappings
     *            The list mappings to add vitual like mappings to
     * 
     * @param recursionLevel
     *            The recursion level where <b>1 = direct children of the like
     *            mapped target object.</b>
     * 
     * @param equivalentLikeMappingComplexChildren
     *            Return Collection of Non-Mappings of source and target complex
     *            child considered to be equivalent. We don't create mappings
     *            between same-named source and target complex type children
     *            (because we will recurs into their children until we reach all
     *            the leaf-nodes). However, for validation purposes we will need
     *            to be able to compare the equivalent complex source and
     *            targets.
     */
    private void recursiveAddVirtualLikeMappings(EObject inputElement,
            DataMapping dataMapping, List<Object> sourceChildren,
            AbstractDataMapperContentContributor sourceContributor,
            List<Object> targetChildren,
            AbstractDataMapperContentContributor targetContributor,
            Collection<Object> explicitMappingTargets,
            Collection<Object> likeMappingExclusions,
            List<Mapping> virtualLikeMappings, int recursionLevel,
            Collection<VirtualLikeMapping> equivalentLikeMappingComplexChildren) {

        if (sourceChildren != null && targetChildren != null) {
            AbstractDataMapperInfoProvider sourceInfoProvider =
                    sourceContributor.getInfoProvider();
            AbstractDataMapperInfoProvider targetInfoProvider =
                    targetContributor.getInfoProvider();

            for (Object targetItem : targetChildren) {

                /*
                 * Ignore read-only targets, excluded targets and targets that
                 * are already explicitly mapped (we don't recurs into those
                 * because that will become just a straight assign, or maybe a
                 * nested Like mapping)
                 */
                if (targetInfoProvider.isReadOnlyTarget(targetItem)
                        || likeMappingExclusions.contains(targetItem)
                        || explicitMappingTargets.contains(targetItem)) {
                    continue;
                }

                /*
                 * Find source item matching the target name.
                 */
                String targetName =
                        targetInfoProvider.getObjectName(targetItem);

                if (targetName != null && !targetName.isEmpty()) {
                    Object matchingSourceItem = null;
                    for (Object sourceItem : sourceChildren) {
                        String sourceName =
                                sourceInfoProvider.getObjectName(sourceItem);

                        if (targetName.equalsIgnoreCase(sourceName)) {
                            matchingSourceItem = sourceItem;
                            break;
                        }
                    }

                    if (matchingSourceItem == null) {
                        continue;
                    }

                    /*
                     * Wrap up the contributed source and target in our usual
                     * wrappers
                     */
                    WrappedContributedContent wrappedSourceItem =
                            new WrappedContributedContent(sourceContributor,
                                    matchingSourceItem,
                                    getWrappedObjectParent(sourceContributor,
                                            matchingSourceItem), sdmProvider,
                                    inputElement);

                    WrappedContributedContent wrappedTargetItem =
                            new WrappedContributedContent(targetContributor,
                                    targetItem,
                                    getWrappedObjectParent(targetContributor,
                                            targetItem), sdmProvider,
                                    inputElement);

                    VirtualLikeMapping virtualMapping =
                            new VirtualLikeMapping(wrappedSourceItem,
                                    wrappedTargetItem, dataMapping);

                    if (targetInfoProvider.isSimpleTypeContent(targetItem)) {
                        /*
                         * This is as far down the target tree as we can go -
                         * Create the mapping (wrapping the source and target
                         * item.
                         * 
                         * We will use the original mapping model (of the main
                         * like mapping itself). Also we should ignore
                         * artificial objects at this point (rather than above
                         * where we check readonly) because there may be a use
                         * case for like mapping the content of artificial
                         * objects.
                         */
                        if (targetInfoProvider.isArtificialObject(targetItem)) {
                            continue;
                        }

                        virtualLikeMappings.add(virtualMapping);

                    } else {
                        /*
                         * Complex Type - recurs into children
                         */
                        equivalentLikeMappingComplexChildren
                                .add(new VirtualLikeMapping(wrappedSourceItem,
                                        wrappedTargetItem, dataMapping));

                        /*
                         * Sid XPD-7839 use method getMappableChildren() that
                         * ignores aritficial objects in tree so that like named
                         * children underneath them can be mapped to similar
                         * tree structure without the same artificial object
                         * (like a 'choice' node)
                         */
                        recursiveAddVirtualLikeMappings(inputElement,
                                dataMapping,
                                getMappableChildren(sourceContributor,
                                        matchingSourceItem),
                                sourceContributor,
                                getMappableChildren(targetContributor,
                                        targetItem),
                                targetContributor,
                                explicitMappingTargets,
                                likeMappingExclusions,
                                virtualLikeMappings,
                                recursionLevel + 1,
                                equivalentLikeMappingComplexChildren);
                    }
                }
            }
        }
    }

    /**
     * 
     * @param targetContributor
     * @param obj
     * 
     * @return wrapped parent object for the given object, where as recursively
     *         gets the parent object each of its parent.
     */
    private WrappedContributedContent getWrappedObjectParent(
            AbstractDataMapperContentContributor targetContributor, Object obj) {
        if (obj != null) {
            Object parent =
                    targetContributor.getInfoProvider().getContentProvider()
                            .getParent(obj);
            if (parent != null) {
                WrappedContributedContent parentObject =
                        new WrappedContributedContent(targetContributor,
                                parent,
                                getWrappedObjectParent(targetContributor,
                                        parent), sdmProvider, input);
                return parentObject;
            }
        }
        return null;
    }

    /**
     * @return Image to be used for decorating like mapping line
     */
    private Image getLikeMappingImage() {

        Image img = null;
        if (PlatformUI.isWorkbenchRunning()) {
            img =
                    DataMapperPlugin.getDefault().getImageRegistry()
                            .get(DataMapperConstants.IMG_LIKE_MAPPING);
        }
        return img;
    }

    /**
     * Returns list of like mapping exclusion objects for the given data mapping
     * <p>
     * These are returned as {@link WrappedContributedContent} objects
     * 
     * @param dataMapping
     * @param mapperInput
     * @return list of like mapping exclusion ({@link WrappedContributedContent}
     *         ) objects for the given data mapping
     */
    @SuppressWarnings("unchecked")
    public List<Object> getLikeMappingExclusionItems(DataMapping dataMapping,
            EObject mapperInput) {

        Collection<Object> exclusions =
                internalGetLikeMappingExclusions(dataMapping, mapperInput, true);

        return new ArrayList(exclusions);
    }

    /**
     * Returns list of like mapping exclusion objects for the given
     * {@link DataMapping} that is a Like Mapping
     * <p>
     * These are returned optionally as either {@link WrappedContributedContent}
     * objects or the originally contributed content objects.
     * 
     * @param dataMapping
     * @param mapperInput
     * @return list of like mapping exclusion ({@link WrappedContributedContent}
     *         ) objects for the given data mapping
     * 
     * @param dataMapping
     * @param mapperInput
     * @param wrapTheContentObjects
     *            <code>true</code> to wrap the content objects in
     *            {@link WrappedContributedContent} objects
     * 
     * @return The like mapping exclusions objects either as
     *         {@link WrappedContributedContent} objects or the originally
     *         contributed content objects.
     */
    private Collection<Object> internalGetLikeMappingExclusions(
            DataMapping dataMapping, EObject mapperInput,
            boolean wrapTheContentObjects) {
        Set<Object> items = new HashSet<Object>();

        if (mapperInput != null && dataMapping != null) {
            LikeMappingExclusions likeMappingExclusions =
                    (LikeMappingExclusions) Xpdl2ModelUtil
                            .getOtherElement(dataMapping,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_LikeMappingExclusions());
            if (likeMappingExclusions != null) {
                for (LikeMappingExclusion excl : likeMappingExclusions
                        .getExclusions()) {

                    AbstractDataMapperContentContributor contributor =
                            getTargetContributor(dataMapping);

                    if (contributor != null) {
                        Object exclusionItemObject =
                                contributor.getInfoProvider()
                                        .getObjectForPath(excl.getPath(),
                                                mapperInput);

                        if (exclusionItemObject != null) {
                            if (wrapTheContentObjects) {
                                WrappedContributedContent wrappedParentItem =
                                        getWrappedObjectParent(contributor,
                                                exclusionItemObject);

                                WrappedContributedContent wrappedItem =
                                        new WrappedContributedContent(
                                                contributor,
                                                exclusionItemObject,
                                                wrappedParentItem, sdmProvider,
                                                input);
                                items.add(wrappedItem);

                            } else {
                                items.add(exclusionItemObject);
                            }
                        }
                    }
                }
            }
        }

        return items;
    }

    /**
     * @return DataMapping based on the given source and target objects if it
     *         exists, null otherwise
     */
    public DataMapping getDataMapping(EObject owner, Object source,
            Object target) {

        DataMapping dataMapping = null;

        if (owner != null) {
            ScriptDataMapper scriptDataMapper =
                    sdmProvider.getScriptDataMapper(owner);
            if (scriptDataMapper != null
                    && source instanceof WrappedContributedContent
                    && target instanceof WrappedContributedContent) {
                WrappedContributedContent srcObject =
                        (WrappedContributedContent) source;
                WrappedContributedContent targetObject =
                        (WrappedContributedContent) target;

                for (DataMapping dm : scriptDataMapper.getDataMappings()) {
                    if (dm.getActual()
                            .getMixed()
                            .get(0)
                            .getValue()
                            .equals(srcObject
                                    .getContributor()
                                    .getInfoProvider()
                                    .getObjectPath(srcObject.getContributedObject()))
                            && dm.getFormal()
                                    .equals(targetObject
                                            .getContributor()
                                            .getInfoProvider()
                                            .getObjectPath(targetObject.getContributedObject()))) {
                        dataMapping = dm;
                        break;
                    }
                }
            }
        }
        return dataMapping;
    }

    /**
     * @return true if the given data mapping has like mapping attribute set to
     *         true, false otherwise
     */

    public boolean isLikeMappingSet(DataMapping dataMapping) {

        if (dataMapping != null) {
            return Xpdl2ModelUtil
                    .getOtherAttributeAsBoolean(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_LikeMapping());
        }
        return false;
    }

    /**
     * @param dataMapping
     * 
     * @return The content contributor for the source of the given mapping or
     *         <code>null</code> if the contributor cannot be located.
     */
    private AbstractDataMapperContentContributor getSourceContributor(
            DataMapping dataMapping) {

        String sourceId =
                (String) Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SourceContributorId());

        if (sourceId != null) {
            AbstractDataMapperContentContributor contributor =
                    DataMapperContentContributionHelper
                            .getContributor(sourceId);

            return contributor;
        }

        return null;
    }

    /**
     * @param dataMapping
     * 
     * @return The content contributor for the target of the given mapping or
     *         <code>null</code> if the contributor cannot be located.
     */
    private AbstractDataMapperContentContributor getTargetContributor(
            DataMapping dataMapping) {
        String targetId =
                (String) Xpdl2ModelUtil.getOtherAttribute(dataMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_TargetContributorId());

        if (targetId != null) {
            AbstractDataMapperContentContributor contributor =
                    DataMapperContentContributionHelper
                            .getContributor(targetId);

            return contributor;
        }

        return null;
    }

    /**
     * Sid XPD-8251 Simple cache of a data mapping's significant criteria
     * 
     * The relevant criteria are sourcePath, targetPath, whether it's a 'like
     * mapping' or not, if it's a like mapping whether the like-mapping
     * exclusions are the same or not.
     * 
     * @author aallway
     * @since 24 May 2017
     */
    private static class CachedMapping {
        private String sourcePath = ""; //$NON-NLS-1$

        private String targetPath = ""; //$NON-NLS-1$

        private boolean isLikeMapping = false;

        private List<String> likeMappingExclusionPaths = new ArrayList<>();

        public CachedMapping(DataMapping dataMapping) {

            if (dataMapping.getActual() != null) {
                sourcePath = dataMapping.getActual().getText();
            }
            if (sourcePath == null) {
                sourcePath = ""; //$NON-NLS-1$
            }

            targetPath = dataMapping.getFormal();
            if (targetPath == null) {
                targetPath = ""; //$NON-NLS-1$
            }

            isLikeMapping =
                    Xpdl2ModelUtil.getOtherAttributeAsBoolean(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_LikeMapping());

            if (isLikeMapping) {
                LikeMappingExclusions likeMappingExclusions =
                        (LikeMappingExclusions) Xpdl2ModelUtil
                                .getOtherElement(dataMapping,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_LikeMappingExclusions());
                if (likeMappingExclusions != null) {
                    for (LikeMappingExclusion lme : likeMappingExclusions
                            .getExclusions()) {
                        likeMappingExclusionPaths.add(lme.getPath());
                    }
                }
            }
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         * 
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof CachedMapping) {
                CachedMapping other = (CachedMapping) obj;
                if ((sourcePath.equals(other.sourcePath)
                        && targetPath.equals(other.targetPath) && isLikeMapping == other.isLikeMapping)) {

                    if (likeMappingExclusionPaths
                            .equals(other.likeMappingExclusionPaths)) {
                        return true;
                    }
                }

            } else if (obj instanceof DataMapping) {
                return this.equals(new CachedMapping((DataMapping) obj));
            }

            return false;
        }
    }

}
