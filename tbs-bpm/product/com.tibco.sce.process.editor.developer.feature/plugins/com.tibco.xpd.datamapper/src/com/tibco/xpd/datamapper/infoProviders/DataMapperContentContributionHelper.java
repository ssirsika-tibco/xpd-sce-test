/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.infoProviders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.datamapper.DataMapperPlugin;
import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;

/**
 * Helper class to provide contributors for the Data Mapper that contribute
 * content to be displayed in the mapper (i.e., contributions to
 * 'DataMapperContentContribution' extension point)
 * 
 * @author Ali
 * @since 18 Feb 2015
 */
public class DataMapperContentContributionHelper {

    private static final String DATA_MAPPER_CONTENT_CONTRIBUTION_EXT_POINT_ID =
            "DataMapperContentContribution"; //$NON-NLS-1$

    private static List<AbstractDataMapperContentContributor> contentContributors;

    List<AbstractDataMapperContentContributor> targetContentContributors =
            new ArrayList<>();

    /**
     * 
     * @return list of contributions extending the Data Mapper additional
     *         content extension point
     */
    public static Collection<AbstractDataMapperContentContributor> getApplicableContributions(
            String context, boolean isRHS) {

        List<AbstractDataMapperContentContributor> applicableContentContributors =
                new ArrayList<>();
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DataMapperPlugin.PLUGIN_ID,
                                DATA_MAPPER_CONTENT_CONTRIBUTION_EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();

            Map<String, Integer> contributorIdPriorityMap = new HashMap<>();
            for (IConfigurationElement contribution : contributions) {

                if ("mapperContentContributor".equals(contribution.getName())) { //$NON-NLS-1$

                    try {
                        Object contributor =
                                contribution
                                        .createExecutableExtension("mapperContribution"); //$NON-NLS-1$ 
                        if (contributor instanceof AbstractDataMapperContentContributor) {

                            // check if its for the given side
                            String applyToMapperRHS =
                                    contribution
                                            .getAttribute("applyToMapperRHS"); //$NON-NLS-1$
                            if (applyToMapperRHS != null
                                    && Boolean.parseBoolean(applyToMapperRHS) == isRHS) {

                                // check if its for given context
                                String applicableContext =
                                        contribution
                                                .getAttribute("applicableContext"); //$NON-NLS-1$
                                if (applicableContext != null
                                        && !applicableContext.isEmpty()) {

                                    if (context.matches(applicableContext)) {
                                        AbstractDataMapperContentContributor contrib =
                                                (AbstractDataMapperContentContributor) contributor;
                                        applicableContentContributors
                                                .add(contrib);

                                        String priority =
                                                contribution
                                                        .getAttribute("priority"); //$NON-NLS-1$
                                        if (priority == null || priority == "") { //$NON-NLS-1$
                                            priority = "5"; //$NON-NLS-1$
                                        }
                                        contributorIdPriorityMap.put(contrib
                                                .getContributorId(), Integer
                                                .parseInt(priority));
                                    }
                                }
                            }
                        }
                    } catch (CoreException e) {
                        e.printStackTrace();

                    }
                }
            }
            // sort contributions based on priority

            Collections.sort(applicableContentContributors,
                    new ContribComaparator(contributorIdPriorityMap));
        }

        return applicableContentContributors;
    }

    /**
     * 
     * @return list of contributions extending the Data Mapper additional
     *         content extension point
     */
    public static Collection<AbstractDataMapperContentContributor> getContributions() {
        if (contentContributors != null) {
            return contentContributors;
        }

        contentContributors = new ArrayList<>();
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DataMapperPlugin.PLUGIN_ID,
                                DATA_MAPPER_CONTENT_CONTRIBUTION_EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();

            Map<String, Integer> contributorIdPriorityMap = new HashMap<>();
            for (IConfigurationElement contribution : contributions) {

                if ("mapperContentContributor".equals(contribution.getName())) { //$NON-NLS-1$

                    try {
                        Object contributor =
                                contribution
                                        .createExecutableExtension("mapperContribution"); //$NON-NLS-1$ 
                        if (contributor instanceof AbstractDataMapperContentContributor) {

                            AbstractDataMapperContentContributor contrib =
                                    (AbstractDataMapperContentContributor) contributor;
                            contentContributors.add(contrib);

                            String priority =
                                    contribution.getAttribute("priority"); //$NON-NLS-1$
                            if (priority == null || priority == "") { //$NON-NLS-1$
                                priority = "5"; //$NON-NLS-1$
                            }
                            contributorIdPriorityMap.put(contrib
                                    .getContributorId(), Integer
                                    .parseInt(priority));
                        }
                    } catch (CoreException e) {
                        e.printStackTrace();

                    }
                }
            }
            // sort contributions based on priority
            Collections.sort(contentContributors, new ContribComaparator(
                    contributorIdPriorityMap));
        }

        return contentContributors;
    }

    /**
     * 
     * @param contributorId
     * @return Data Mapper content contributor for the given Id or null
     */
    public static AbstractDataMapperContentContributor getContributor(
            String contributorId) {

        Collection<AbstractDataMapperContentContributor> contributions =
                getContributions();
        if (contributions != null && contributorId != null) {
            for (AbstractDataMapperContentContributor contributor : contributions) {
                if (contributorId.equals(contributor.getContributorId())) {
                    return contributor;
                }
            }
        }
        return null;
    }

    /**
     * Comaprator class used for sorting contributors list
     */
    public static class ContribComaparator implements
            Comparator<AbstractDataMapperContentContributor> {

        private Map<String, Integer> contributorIdPriorityMap;

        /**
         * @param contributorPriorityMap
         */
        public ContribComaparator(Map<String, Integer> contributorIdPriorityMap) {
            this.contributorIdPriorityMap = contributorIdPriorityMap;
        }

        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         * 
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(AbstractDataMapperContentContributor o1,
                AbstractDataMapperContentContributor o2) {
            int result = 0;
            if (o1 != null && o2 != null) {

                int o1Priority = getPriority(o1);
                int o2Priority = getPriority(o2);
                result = o1Priority - o2Priority;
            }
            return result;
        }

        private int getPriority(AbstractDataMapperContentContributor o) {
            if (o != null) {
                return contributorIdPriorityMap.get(o.getContributorId());
            }
            return 0;
        }
    }

    /**
     * 
     * @return list of all contributions for mapper RHS content (irrespective of
     *         context)
     */
    public static Collection<AbstractDataMapperContentContributor> getTargetContentContributions() {

        List<AbstractDataMapperContentContributor> targetContentContributors =
                new ArrayList<>();
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(DataMapperPlugin.PLUGIN_ID,
                                DATA_MAPPER_CONTENT_CONTRIBUTION_EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();

            Map<String, Integer> contributorIdPriorityMap = new HashMap<>();
            for (IConfigurationElement contribution : contributions) {

                if ("mapperContentContributor".equals(contribution.getName())) { //$NON-NLS-1$

                    try {
                        Object contributor =
                                contribution
                                        .createExecutableExtension("mapperContribution"); //$NON-NLS-1$ 
                        if (contributor instanceof AbstractDataMapperContentContributor) {

                            // check if its for the RHS side
                            String applyToMapperRHS =
                                    contribution
                                            .getAttribute("applyToMapperRHS"); //$NON-NLS-1$
                            if (applyToMapperRHS != null
                                    && Boolean.parseBoolean(applyToMapperRHS)) {

                                AbstractDataMapperContentContributor contrib =
                                        (AbstractDataMapperContentContributor) contributor;
                                targetContentContributors.add(contrib);

                                String priority =
                                        contribution.getAttribute("priority"); //$NON-NLS-1$
                                if (priority == null || priority == "") { //$NON-NLS-1$
                                    priority = "5"; //$NON-NLS-1$
                                }
                                contributorIdPriorityMap.put(contrib
                                        .getContributorId(), Integer
                                        .parseInt(priority));
                            }
                        }
                    } catch (CoreException e) {
                        e.printStackTrace();

                    }
                }
            }
            // sort contributions based on priority

            Collections.sort(targetContentContributors, new ContribComaparator(
                    contributorIdPriorityMap));
        }

        return targetContentContributors;
    }
}
