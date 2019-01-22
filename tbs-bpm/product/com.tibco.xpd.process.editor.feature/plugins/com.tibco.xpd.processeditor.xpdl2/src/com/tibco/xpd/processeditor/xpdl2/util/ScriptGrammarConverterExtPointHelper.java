/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.IScriptGrammarConverter;

/**
 * Extension point helper for
 * <code>com.tibco.xpd.processeditor.xpdl2.ScriptGrammarConverter</code>
 * 
 * @author aallway
 * @since 25 May 2011
 */
public class ScriptGrammarConverterExtPointHelper {

    private static final String EXTPOINT_ID = "scriptGrammarConverter"; //$NON-NLS-1$

    private static final String CONVERTER_ELEMENT = "scriptGrammarConverter"; //$NON-NLS-1$

    private static final String SOURCE_GRAMMAR_ATTRIB = "sourceGrammar"; //$NON-NLS-1$

    private static final String TARGET_GRAMMAR_ATTRIB = "targetGrammar"; //$NON-NLS-1$

    private static final String PRIORITY_ATTRIB = "priority"; //$NON-NLS-1$

    private static final String CLASS_ATTRIB = "class"; //$NON-NLS-1$

    /**
     * Cache map of contributions "sourceGrammar:$:targetGrammar" --> List of
     * {@link ScriptGrammarConverterContribution} for the pair.
     * <p>
     * The list is sorted in order of contribution priority (highest first).
     */
    private static Map<String, List<ScriptGrammarConverterContribution>> contributionCache =
            null;

    /**
     * Get the script grammar converter for the given source to target grammar
     * conversion.
     * 
     * @param sourceGrammar
     *            Existing grammar.
     * @param targetGrammar
     *            Target Grammar.
     * @param contextObject
     *            The model object that is considered the owner of the script
     *            (Activity, Transition, Participant etc)
     * 
     * @return Highest priority Script grammar converter that is enabled for the
     *         given script grammar conversion or <code>null</code> if no
     *         converters are contributed.
     */
    public static IScriptGrammarConverter getScriptGrammarConverter(
            String sourceGrammar, String targetGrammar, EObject contextObject) {

        List<ScriptGrammarConverterContribution> contributionsForGrammarPair =
                getContributionsForGrammars(sourceGrammar, targetGrammar);

        if (contributionsForGrammarPair != null) {
            /*
             * Go thru and find first that says it's enabled (they're in order
             * of priority already)
             */
            for (ScriptGrammarConverterContribution contribution : contributionsForGrammarPair) {
                if (contribution.grammarConverter.isEnabled(contextObject)) {
                    return contribution.grammarConverter;
                }
            }
        }

        return null;
    }

    /**
     * @param sourceGrammar
     * @param targetGrammar
     * 
     * @return A list of script grammar converters that convert from given
     *         source to given target grammars (in sorted as highest priority
     *         first)
     */
    private static List<ScriptGrammarConverterContribution> getContributionsForGrammars(
            String sourceGrammar, String targetGrammar) {

        /*
         * Create cache first time.
         */
        if (contributionCache == null) {
            createContributionCache();
        }

        return contributionCache.get(getGrammarCacheId(sourceGrammar,
                targetGrammar));
    }

    /**
     * Create Cache of the contributions to ScriptGrammarConverter
     * <p>
     * The cache is a map of {@link #getGrammarCacheId(String, String)} --> List
     * of contributions.
     */
    private static void createContributionCache() {
        contributionCache =
                new HashMap<String, List<ScriptGrammarConverterContribution>>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                EXTPOINT_ID);

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    //
                    // Load the top level complexDataType element.
                    //
                    for (IConfigurationElement config : ext
                            .getConfigurationElements()) {
                        if (CONVERTER_ELEMENT.equals(config.getName())) {
                            try {
                                /*
                                 * Create the contribution wrapper class and add
                                 * it to the list of contributions for this
                                 * source->target grammar pair.
                                 */
                                ScriptGrammarConverterContribution contribution =
                                        new ScriptGrammarConverterContribution(
                                                config);

                                String cacheId =
                                        getGrammarCacheId(contribution.sourceGrammar,
                                                contribution.targetGrammar);

                                List<ScriptGrammarConverterContribution> cacheForGrammarPair =
                                        contributionCache.get(cacheId);

                                if (cacheForGrammarPair == null) {
                                    cacheForGrammarPair =
                                            new ArrayList<ScriptGrammarConverterExtPointHelper.ScriptGrammarConverterContribution>();
                                    contributionCache.put(cacheId,
                                            cacheForGrammarPair);
                                }

                                cacheForGrammarPair.add(contribution);

                            } catch (Exception e) {
                                Xpdl2ProcessEditorPlugin
                                        .getDefault()
                                        .getLogger()
                                        .error(e,
                                                "Exception loading ScriptGrammarConverter contribution from: " //$NON-NLS-1$
                                                        + config.getContributor()
                                                                .getName());
                            }
                        }
                    } /* Next element in ext point */

                } /* Next extension point. */

            }
        }

        /*
         * Once everything is cached then sort each list by priority (highest
         * priority first)
         */
        for (Entry<String, List<ScriptGrammarConverterContribution>> cacheEntry : contributionCache
                .entrySet()) {
            List<ScriptGrammarConverterContribution> cacheForGrammarPair =
                    cacheEntry.getValue();

            if (cacheForGrammarPair.size() > 1) {
                Collections.sort(cacheForGrammarPair,
                        new Comparator<ScriptGrammarConverterContribution>() {

                            @Override
                            public int compare(
                                    ScriptGrammarConverterContribution c1,
                                    ScriptGrammarConverterContribution c2) {
                                return c2.priority - c1.priority;
                            }
                        });
            }
        }
        return;
    }

    /**
     * @param sourceGrammar
     * @param targetGrammar
     * @return The id to use as contribution map key fo rthe given grammar pair.
     */
    private static String getGrammarCacheId(String sourceGrammar,
            String targetGrammar) {
        return sourceGrammar + ":$:" + targetGrammar; //$NON-NLS-1$
    }

    /**
     * Class representing a single script grammar converter contribution.
     * 
     * @author aallway
     * @since 25 May 2011
     */
    private static class ScriptGrammarConverterContribution {
        private String sourceGrammar;

        private String targetGrammar;

        private int priority;

        private IScriptGrammarConverter grammarConverter;

        ScriptGrammarConverterContribution(IConfigurationElement config)
                throws Exception {
            sourceGrammar = config.getAttribute(SOURCE_GRAMMAR_ATTRIB);
            if (sourceGrammar == null) {
                sourceGrammar = ""; //$NON-NLS-1$
            }

            targetGrammar = config.getAttribute(TARGET_GRAMMAR_ATTRIB);
            if (targetGrammar == null) {
                targetGrammar = ""; //$NON-NLS-1$
            }

            priority = -999999;
            String strPriority = config.getAttribute(PRIORITY_ATTRIB);
            if (strPriority != null && strPriority.length() > 0) {
                try {
                    int i = Integer.parseInt(strPriority);
                    priority = i;
                } catch (NumberFormatException e) {
                }
            }

            try {
                Object exExt = config.createExecutableExtension(CLASS_ATTRIB);
                if (!(exExt instanceof IScriptGrammarConverter)) {
                    throw new Exception(
                            String.format("Class must implement IScriptGrammarConverter class from contributor: %s", //$NON-NLS-1$
                                    config.getContributor().getName()));
                }

                grammarConverter = (IScriptGrammarConverter) exExt;

            } catch (CoreException e) {
                throw new Exception(
                        String.format("Failed loading IScriptGrammarConverter class from contributor: %s", //$NON-NLS-1$
                                config.getContributor().getName()), e);
            }
        }
    }

}
