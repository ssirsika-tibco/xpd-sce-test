/*
 * Copyright (c) TIBCO Software Inc 2004, 2016. All rights reserved.
 */

package com.tibco.xpd.datamapper.infoProviders;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.datamapper.DataMapperPlugin;
import com.tibco.xpd.datamapper.api.IScriptGeneratorInfoProvider;
import com.tibco.xpd.datamapper.internal.Messages;

/**
 * Helper class to provide contributions for DataMapper Script Generator Info
 * Provider {@link IScriptGeneratorInfoProvider} contributed using extension
 * point (com.tibco.xpd.datamapper.scriptGeneratorInfoProviderContribution).
 * 
 * @author ssirsika
 * @since 04-Apr-2016
 * @see XPD-8053
 */
public class ScriptGeneratorInfoProviderContributionHelper {

    private static final String CONTRIBUTOR_KEY_NAME = "contributor"; //$NON-NLS-1$

    private static final String CONTRIBUTOR_ID_ATTRIBUTE = "contributorID"; //$NON-NLS-1$

    private static final String SCRIPT_GENERATOR_INFOPROVIDER_CONTRIBUSION_EXT_POINT_ID =
            "scriptGeneratorInfoProviderContribution"; //$NON-NLS-1$

    private static final Map<String, IScriptGeneratorInfoProvider> idToScriptGenInfoProviderMap =
            new HashMap<String, IScriptGeneratorInfoProvider>();

    static {
        readExtensionPointContributions();
    }

    /**
     * Read the extension point contributions and store it in
     * idToScriptGenInfoProviderMap
     */
    private static void readExtensionPointContributions() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DataMapperPlugin.PLUGIN_ID,
                                SCRIPT_GENERATOR_INFOPROVIDER_CONTRIBUSION_EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();
            for (IConfigurationElement contribution : contributions) {
                String contributorID =
                        contribution.getAttribute(CONTRIBUTOR_ID_ATTRIBUTE);
                if (contributorID != null && !contributorID.isEmpty()) {
                    try {
                        if (idToScriptGenInfoProviderMap
                                .containsKey(contributorID)) {
                            // If contributions is already exists against
                            // particular
                            // contributionID, then log the info message.
                            IScriptGeneratorInfoProvider existing =
                                    idToScriptGenInfoProviderMap
                                            .get(contributorID);
                            String existingName =
                                    existing != null ? existing.getClass()
                                            .getName() : ""; //$NON-NLS-1$
                            Object newContributor =
                                    contribution
                                            .createExecutableExtension(CONTRIBUTOR_KEY_NAME);
                            String newName =
                                    newContributor != null ? newContributor
                                            .getClass().getName() : ""; //$NON-NLS-1$
                            String message =
                                    String.format(Messages.ScriptGeneratorInfoProviderContributionHelper_ContributionIDIgnoreInfoMessage,
                                            contributorID,
                                            existingName,
                                            newName);
                            DataMapperPlugin.getDefault().getLogger()
                                    .info(message);
                        } else {
                            Object contributor =
                                    contribution
                                            .createExecutableExtension(CONTRIBUTOR_KEY_NAME);
                            if (contributor instanceof IScriptGeneratorInfoProvider) {
                                idToScriptGenInfoProviderMap
                                        .put(contributorID,
                                                (IScriptGeneratorInfoProvider) contributor);
                            }
                        }
                    } catch (CoreException e) {
                        DataMapperPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        Messages.ScriptGeneratorInfoProviderContributionHelper_ErrorReadingExtensionMessage);
                    }
                }
            }
        }
    }

    /**
     * Returns ScriptGeneratorInfoProvider {@link IScriptGeneratorInfoProvider}
     * for passed contributionID.
     * 
     * @param contributionID
     */
    public static IScriptGeneratorInfoProvider getScriptGeneratorInfoProvider(
            String contributionID) {
        return idToScriptGenInfoProviderMap.get(contributionID);
    }
}
