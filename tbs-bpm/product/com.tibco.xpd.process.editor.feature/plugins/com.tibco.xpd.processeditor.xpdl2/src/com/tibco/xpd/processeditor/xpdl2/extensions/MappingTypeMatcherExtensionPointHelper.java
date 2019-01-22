/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.extensions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;

/**
 *Extension point helper for
 * com.tibco.xpd.processeditor.xpdl2.mappingTypeMatcher
 * 
 * @author rsomayaj
 * @since 3.3 (10 Jun 2010)
 */
public class MappingTypeMatcherExtensionPointHelper {

    private static final String MAPPING_TYPEMATCH_EXT_POINT =
            "mappingTypeMatcher"; //$NON-NLS-1$

    private static final List<MappingTypeMatcher> CONTRIBUTED_ELEMENTS =
            new ArrayList<MappingTypeMatcher>();

    public enum TypeMatcherPriority {

        LOWEST, LOW,

        MEDIUM,

        HIGH, HIGHEST;

        /**
         * @param attribute
         */
        public static TypeMatcherPriority getPriority(String attribute) {

            if (attribute.equals("HIGHEST")) //$NON-NLS-1$
                return HIGHEST;
            if (attribute.equals("HIGH")) //$NON-NLS-1$
                return HIGH;
            if (attribute.equals("MEDIUM")) //$NON-NLS-1$
                return MEDIUM;
            if (attribute.equals("LOW")) //$NON-NLS-1$
                return LOW;

            return LOWEST;

        }
    };

    /**
     * @param enabledGlobalDestinations
     * @param scriptGrammar
     * @param contextObject
     * 
     * @return list of mappingTypeMatcher contributions filtered on the
     *         destinations, script grammar and context object. It is grouped
     *         per destinations - priority
     */
    public static List<MappingTypeMatcher> getMappingTypeMatcherContributors(
            Set<String> enabledGlobalDestinations, String scriptGrammar,
            EObject contextObject) {

        List<MappingTypeMatcher> contributions = getContributions();

        List<MappingTypeMatcher> defaultDestinationContributions =
                new ArrayList<MappingTypeMatcher>();

        List<MappingTypeMatcher> destinationSpecificContributions =
                new ArrayList<MappingTypeMatcher>();
        Boolean includeDefaults = Boolean.TRUE;
        for (MappingTypeMatcher mappingTypeMatcher : contributions) {
            // if enabledGlobalDestinations is empty then use all the
            // typeMatcher
            // contributions which are empty
            // For BPMN destination look for all typeMatcherContribution whose
            // destination is empty

            if (scriptGrammar != null
                    && scriptGrammar.equals(mappingTypeMatcher.getGrammarId())) {

                String contributionDestId =
                        mappingTypeMatcher.getDestinationId();

                if (contributionDestId == null
                        || contributionDestId.length() == 0) {
                    defaultDestinationContributions.add(mappingTypeMatcher);

                } else if (enabledGlobalDestinations
                        .contains(contributionDestId)) {
                    if (!mappingTypeMatcher.getIncludeDefault()) {
                        // Any typeMatcher contribution for a particular
                        // destination
                        // which says it shouldn't include defaults will flag
                        // includeDefaults to false.
                        includeDefaults = Boolean.FALSE;
                    }

                    destinationSpecificContributions.add(mappingTypeMatcher);
                }
            }
        }

        // Merge the destination specific and default contributions if default
        // ones are required.
        if (includeDefaults) {
            destinationSpecificContributions
                    .addAll(defaultDestinationContributions);
        }
        Collections.sort(destinationSpecificContributions,
                mappingTypeMatcherContributionComparator);
        return destinationSpecificContributions;
    }

    /**
     * This comparator is used for sorting the <code>MappingTypeMatcher</code>s.
     * Sorting is done based on whether the contribution has a destination
     * specified, and if it does, the comparison is based on the priority
     * specified in the contribution.
     */
    private static final Comparator<MappingTypeMatcher> mappingTypeMatcherContributionComparator =
            new Comparator<MappingTypeMatcher>() {

                @Override
                public int compare(MappingTypeMatcher mappingTypeMatcher1,
                        MappingTypeMatcher mappingTypeMatcher2) {
                    if (mappingTypeMatcher1.getDestinationId() == null) {
                        // If the typeMatcher1 destination is null
                        if (mappingTypeMatcher2.getDestinationId() != null) {
                            /* If the typeMatcher2 destination is not null, then
                            *  it takes priority.
                            *  
                            *  Returning 1 making it greater and while sorting puts it ahead of typematcher1
                            */
                            return 1;
                        }
                    } else {
                        // If typeMatcher1.destination is not null and
                        // typeMatcher2.destination is null, then typeMatcher1
                        // takes priority
                        if (mappingTypeMatcher2.getDestinationId() == null) {
                            return -1;
                        }
                    }

                    // Either if the destinations of both the typeMatchers are
                    // null or if the destinations of both the typeMatchers are
                    // not null, then we need to compare the priority of the
                    // typeMatchers.
                    return mappingTypeMatcher1.getTypeMatcherPriority()
                            .compareTo(mappingTypeMatcher2
                                    .getTypeMatcherPriority());
                }

            };

    /**
     * Caches the list of contributions to the extension point
     * "mappingTypeMatcher"
     * 
     * @return
     */
    public static List<MappingTypeMatcher> getContributions() {
        if (CONTRIBUTED_ELEMENTS.isEmpty()) {
            IConfigurationElement[] scriptGrammarDestinationBindings =
                    Platform
                            .getExtensionRegistry()
                            .getConfigurationElementsFor(Xpdl2ProcessEditorPlugin.ID,
                                    MAPPING_TYPEMATCH_EXT_POINT);
            for (IConfigurationElement configElement : scriptGrammarDestinationBindings) {
                MappingTypeMatcher autoMapContribution =
                        new MappingTypeMatcher(configElement);

                CONTRIBUTED_ELEMENTS.add(autoMapContribution);
            }
        }
        return CONTRIBUTED_ELEMENTS;
    }
}
