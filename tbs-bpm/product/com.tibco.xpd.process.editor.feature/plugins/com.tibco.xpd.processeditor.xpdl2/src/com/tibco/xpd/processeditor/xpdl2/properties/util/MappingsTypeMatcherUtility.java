/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractTypeMatcher;
import com.tibco.xpd.processeditor.xpdl2.extensions.MappingTypeMatcher;
import com.tibco.xpd.processeditor.xpdl2.extensions.MappingTypeMatcherExtensionPointHelper;
import com.tibco.xpd.processeditor.xpdl2.preferences.AutoMapRulesPreferencePage;

/**
 * Utility class to provide necessary details for processing auto-map
 * 
 * @author rsomayaj
 * @since 3.3 (10 Jun 2010)
 */
public class MappingsTypeMatcherUtility {

    /**
     * Utility method that reads through the contents of the mapper content
     * providers and asks each mapper type matcher contributor whether they
     * match the given pair of source and target
     * 
     * @param enabledGlobalDestinations
     * @param scriptGrammar
     * @param contextObject
     * @param targetContentProvider
     * @param sourceContentProvider
     * @param mappingDirection
     * @param existingMappings
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Mapping> getTypeMatchMappings(
            Set<String> enabledGlobalDestinations, String scriptGrammar,
            EObject contextObject, ITreeContentProvider targetContentProvider,
            ITreeContentProvider sourceContentProvider,
            MappingDirection mappingDirection, List<Mapping> existingMappings) {

        List<Mapping> mappingsToCreate = new ArrayList<Mapping>();
        List<MappingTypeMatcher> autoMapContributors =
                MappingTypeMatcherExtensionPointHelper
                        .getMappingTypeMatcherContributors(enabledGlobalDestinations,
                                scriptGrammar,
                                contextObject);

        Set<Object> alreadyMappedTargets = new HashSet<Object>();

        for (Mapping mapping : existingMappings) {
            alreadyMappedTargets.add(mapping.getTarget());
        }

        Object[] targetElements =
                targetContentProvider.getElements(contextObject);

        gatherAutoMappings(contextObject,
                mappingDirection,
                sourceContentProvider,
                targetContentProvider,
                autoMapContributors,
                alreadyMappedTargets,
                targetElements,
                mappingsToCreate);
        return mappingsToCreate;
    }

    /**
     * @param contextObject
     * @param mappingDirection
     * @param sourceContentProvider
     * @param targetContentProvider
     * @param autoMapContributors
     * @param alreadyMappedTargets
     * @param targetContents
     * @param mappingsToCreate
     */
    private static void gatherAutoMappings(EObject contextObject,
            MappingDirection mappingDirection,
            ITreeContentProvider sourceContentProvider,
            ITreeContentProvider targetContentProvider,
            List<MappingTypeMatcher> autoMapContributors,
            Set<Object> alreadyMappedTargets, Object[] targetContents,
            List<Mapping> mappingsToCreate) {
        if (targetContents != null) {
            for (MappingTypeMatcher autoMap : autoMapContributors) {
                if (autoMap.getTypeMatcher().isApplicable(contextObject,
                        mappingDirection)
                        && !isContributorDisabled(autoMap)) {

                    for (Object target : targetContents) {

                        boolean isTargetMapped =
                                alreadyMappedTargets.contains(target);
                        //
                        if (!isTargetMapped) {
                            //

                            Object[] children =
                                    targetContentProvider.getChildren(target);

                            /*
                             * If any descendent children / grandchildren etc
                             * are mapped then we should __not__ create another
                             * mapping at this level.
                             */
                            boolean areDescendantsMapped =
                                    areDescendantsMapped(children,
                                            targetContentProvider,
                                            alreadyMappedTargets);

                            if (!areDescendantsMapped) {
                                /*
                                 * No descendents are mapped so check if there
                                 * is a matching soruce object for this target.
                                 */
                                List<Object> matchingLHS =
                                        new ArrayList<Object>();

                                findMatches(contextObject,
                                        autoMap,
                                        target,
                                        sourceContentProvider,
                                        sourceContentProvider
                                                .getElements(contextObject),
                                        matchingLHS);

                                if (!matchingLHS.isEmpty()) {
                                    /*
                                     * Found one or more matches, find the one
                                     * that matches best (the one with most
                                     * matching ascendent names).
                                     */
                                    Object bestSource =
                                            findBestMatch(contextObject,
                                                    autoMap,
                                                    target,
                                                    matchingLHS);

                                    mappingsToCreate.add(new Mapping(
                                            bestSource, target, null));
                                    alreadyMappedTargets.add(target);

                                    /*
                                     * No need to look at descendants once we've
                                     * mapped a parent element.
                                     */
                                    continue;
                                }
                            }

                            /*
                             * Could not map directly to this target, recurs and
                             * consider all it's children.
                             */
                            gatherAutoMappings(contextObject,
                                    mappingDirection,
                                    sourceContentProvider,
                                    targetContentProvider,
                                    autoMapContributors,
                                    alreadyMappedTargets,
                                    children,
                                    mappingsToCreate);
                        }
                    }
                } /* next target content. */
            }
        } /* Next contributor */
        return;
    }

    /**
     * @param autoMap
     * @return
     */
    private static boolean isContributorDisabled(MappingTypeMatcher autoMap) {
        if (autoMap.getShowInPreferencePage()) {
            boolean boolIsEnabled =
                    Xpdl2ProcessEditorPlugin
                            .getDefault()
                            .getPreferenceStore()
                            .getBoolean(AutoMapRulesPreferencePage.AUTOMAP_PREFIX
                                    + autoMap.getId());
            return !boolIsEnabled;
        }
        return false;
    }

    /**
     * @param contextObject
     * @param mappingTypeMatcherContribution
     * @param target
     * @param matchingLHS
     * @return
     */
    private static Object findBestMatch(Object contextObject,
            MappingTypeMatcher mappingTypeMatcherContribution, Object target,
            List matchingLHS) {

        MapperTreeItem targetMapperTreeItem =
                mappingTypeMatcherContribution.getTypeMatcher()
                        .getNormalizedPath(contextObject, target);

        List<MapperTreeItem> lhsMapperTreeItems =
                new ArrayList<MapperTreeItem>();

        for (Object lhsObject : matchingLHS) {
            lhsMapperTreeItems.add(mappingTypeMatcherContribution
                    .getTypeMatcher().getNormalizedPath(contextObject,
                            lhsObject));

        }

        return compareAndFindBestMatch(targetMapperTreeItem, lhsMapperTreeItems);
    }

    /**
     * Compares the tree items on either side of the tree to find the best match
     * for a given target object.
     * 
     * @param targetMapperTreeItem
     * @param lhsMapperTreeItems
     * @return
     */
    private static Object compareAndFindBestMatch(
            MapperTreeItem targetMapperTreeItem,
            List<MapperTreeItem> lhsMapperTreeItems) {

        MapperTreeItem bestMatchMapperTreeItem = null;

        // Iterate through all the lhsMapperTreeItems and compare with the
        // targetMapperTreeItem

        int mapperCount = 0;
        for (MapperTreeItem mapperTreeItem : lhsMapperTreeItems) {
            // Attempts to rank each of the mapper tree items
            MapperTreeItem targetParentMapperTreeItem =
                    targetMapperTreeItem.getParent();
            int rank = 1;
            MapperTreeItem lhsParent = mapperTreeItem.getParent();

            // Compare each matchs' parent tree and the target nodes' parent
            // tree
            while (lhsParent != null) {

                if (lhsParent.equals(targetParentMapperTreeItem)) {
                    // If the lhs-Parent is equal to the rhs-parent then that is
                    // good, so rank increases by 10 and continue iterating
                    // though the parent list - raise the rank by 10
                    lhsParent = lhsParent.getParent();
                    targetParentMapperTreeItem =
                            targetMapperTreeItem.getParent();
                    rank = rank + 10;
                } else if (targetParentMapperTreeItem != null) {
                    // If the rhs-parent is not null but not the same as the lhs
                    // parent, raise the rank by 1 and iterate through the rest
                    // of the parent tree
                    lhsParent = lhsParent.getParent();
                    targetParentMapperTreeItem =
                            targetMapperTreeItem.getParent();
                    rank = rank + 1;
                } else {
                    // If the lhs-parent is not null but the rhs parent is null,
                    // no point comparing the tree so exit the parent tree
                    // comparison loop for this match
                    break;
                }
            }// End of rhs-parent and lhs-parent tree comparison
            if (mapperTreeItem.getParent() == null
                    && targetMapperTreeItem.getParent() == null) {
                // If both the lhs-parent and the rhs-parent are on the root
                // level, then choose the first one in that combination.
                bestMatchMapperTreeItem = mapperTreeItem;
                break;
            }

            if (mapperCount < rank) {
                // Otherwise if the rank is better than the previous ones rank,
                // update the bestMatchItem and use the rank of the new best
                // match.
                bestMatchMapperTreeItem = mapperTreeItem;
                mapperCount = rank;
            }
        }// Next match comparison

        return bestMatchMapperTreeItem.getMappingObject();
    }

    /**
     * @param contextObject
     * @param mappingTypeMatcher
     * @param target
     * @param sourceContentProvider
     * @param matchingLHS
     * @return list of source content objects that match the target object in
     *         matchingLHS
     */
    private static void findMatches(EObject contextObject,
            MappingTypeMatcher mappingTypeMatcher, Object target,
            ITreeContentProvider sourceContentProvider, Object[] sourceContent,
            List<Object> matchingLHS) {

        if (sourceContent != null) {
            AbstractTypeMatcher typeMatcher =
                    mappingTypeMatcher.getTypeMatcher();

            for (Object source : sourceContent) {
                if (typeMatcher.typesMatch(source, target)) {
                    matchingLHS.add(source);
                }

                /*
                 * Check for matches in children because one of those might
                 * match as well or even better.
                 */
                findMatches(contextObject,
                        mappingTypeMatcher,
                        target,
                        sourceContentProvider,
                        sourceContentProvider.getChildren(source),
                        matchingLHS);
            }
        }

        return;
    }

    /**
     * Recursive methods traversing through the descendants to find if any is
     * mapped.
     * 
     * @param contentList
     * @param targetContentProvider
     * @param alreadyMappedTargets
     * @return true if any of the list of objects or their descendants are
     *         mapped.
     */
    private static boolean areDescendantsMapped(Object[] contentList,
            ITreeContentProvider targetContentProvider,
            Set<Object> alreadyMappedTargets) {
        if (contentList != null) {
            for (Object content : contentList) {
                if (alreadyMappedTargets.contains(content)) {
                    return true;
                }

                // Recurs and check children.
                if (areDescendantsMapped(targetContentProvider.getChildren(content),
                        targetContentProvider,
                        alreadyMappedTargets)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if there are any applicable type matchers enabled for the given
     * context.
     * 
     * @param enabledGlobalDestinations
     * @param scriptGrammar
     * @param contextObject
     * @param mappingDirection
     * 
     * @return <code>true</code> there are..
     */
    public static boolean hasEnabledAutoMappers(
            Set<String> enabledGlobalDestinations, String scriptGrammar,
            EObject contextObject, MappingDirection mappingDirection) {

        List<MappingTypeMatcher> autoMapContributors =
                MappingTypeMatcherExtensionPointHelper
                        .getMappingTypeMatcherContributors(enabledGlobalDestinations,
                                scriptGrammar,
                                contextObject);
        for (MappingTypeMatcher mappingTypeMatcher : autoMapContributors) {
            AbstractTypeMatcher typeMatcher =
                    mappingTypeMatcher.getTypeMatcher();

            if (typeMatcher.isApplicable(contextObject, mappingDirection)) {

                if (!isContributorDisabled(mappingTypeMatcher)) {
                    return true;
                }
            }
        }

        return false;
    }
}
