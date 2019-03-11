/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.rasc.core.RascContributor;
import com.tibco.xpd.rasc.core.RascContributorLocator;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.DependencySorter;

/**
 * A helper class to discover the implentations of {@link RascContributor} from
 * the platform's plug-in register.
 *
 * @author pwatson
 * @since 26 Feb 2019
 */
final class RascContributorPluginsLocator implements RascContributorLocator {
    private static final String EXT_POINT_ID = "bpmnRascContributors"; //$NON-NLS-1$

    private static final String CONTRIBUTOR_ELEMENT = "contributor"; //$NON-NLS-1$

    private static final String CLASS_ATTR_NAME = "class"; //$NON-NLS-1$

    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    private static final String DEPENDS_ON_ELEMENT = "dependsOn"; //$NON-NLS-1$

    private static final String CONTRIBUTOR_ID_ATTRIBUTE = "contributorId"; //$NON-NLS-1$

    private static final String CLASS_NAME =
            RascContributorPluginsLocator.class.getName();

    private static final String LOG_NO_EXTPOINT =
            CLASS_NAME + ": No extension point found."; //$NON-NLS-1$

    private static final String LOG_NO_PROVIDERS =
            CLASS_NAME + ": No providers found."; //$NON-NLS-1$

    private static final String LOG_INVALID_CLASS = CLASS_NAME
            + ": Contributor ID: %1$s - Incorrectly defined extension - class must implement " //$NON-NLS-1$
            + RascContributor.class.getName();

    private static final String LOG_INSTANTION_ERROR =
            CLASS_NAME + ": CoreException: %1$s"; //$NON-NLS-1$

    private static final String LOG_UNKNOWN_DEPENDENCY =
            CLASS_NAME
                    + ": RASC Contributor '%1$s' declares dependency on non-existent contribution '%2$s'."; //$NON-NLS-1$
    private final List<RascContributor> contributors;

    public RascContributorPluginsLocator() {
        contributors = loadContributions();
    }

    /**
     * Returns list of the RascContributor implementation instances. The list
     * will be immutable and ordered according to the order in which they are to
     * be invoked.
     * 
     * @return the ordered collection of RascContributor instances.
     */
    @Override
    public List<RascContributor> getContributors() {
        return contributors;
    }

    /**
     * Discovers all RascContributor implementations and returns an immutable,
     * ordered list of their instances.
     * 
     * @return the instances of RascContributor.
     */
    private List<RascContributor> loadContributions() {
        Logger logger = Xpdl2ResourcesPlugin.getDefault().getLogger();

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint(Xpdl2ResourcesPlugin.PLUGIN_ID,
                        RascContributorPluginsLocator.EXT_POINT_ID);
        if (point == null) {
            logger.info(RascContributorPluginsLocator.LOG_NO_EXTPOINT);
            return Collections.emptyList();
        }

        IConfigurationElement[] configElements =
                point.getConfigurationElements();

        if ((configElements == null) || (configElements.length == 0)) {
            logger.info(RascContributorPluginsLocator.LOG_NO_PROVIDERS);
            return Collections.emptyList();
        }

        Map<String, Contribution> result = new HashMap<>();

        // for each contribution configuration
        for (IConfigurationElement configuration : configElements) {
            if (!RascContributorPluginsLocator.CONTRIBUTOR_ELEMENT
                    .equals(configuration.getName())) {
                continue;
            }

            // instantiate the referenced class
            Object contributorInstance = null;
            try {
                contributorInstance = configuration
                        .createExecutableExtension(CLASS_ATTR_NAME);
            } catch (CoreException | InvalidRegistryObjectException e) {
                logger.warn(e,
                        String.format(
                                RascContributorPluginsLocator.LOG_INSTANTION_ERROR,
                                e.getMessage()));
            }

            // if the referenced class is an instanceof RascContributor
            if (contributorInstance instanceof RascContributor) {
                // add it to the map - for later ordering by dependency
                Contribution contribution =
                        new Contribution((RascContributor) contributorInstance,
                        configuration);
                result.put(contribution.getId(), contribution);
            } else {
                logger.warn(
                        String.format(RascContributorPluginsLocator.LOG_INVALID_CLASS,
                                configuration.getContributor().getName()));
            }
        }

        return Collections.unmodifiableList(orderContributors(result));
    }

    /**
     * Returns list of ordered contributor classes according to declared
     * dependencies.
     * 
     * @param contributions
     *            map of contributionId to contribution.
     * @return list of ordered contributor classes according to declared
     *         dependencies.
     */
    private List<RascContributor> orderContributors(
            Map<String, Contribution> contributions) {
        Logger logger = Xpdl2ResourcesPlugin.getDefault().getLogger();

        List<DependencySorter.Arc<RascContributor>> arcs = new ArrayList<>();
        List<RascContributor> nodes = new ArrayList<>();

        // Create dependency arcs.
        for (Contribution c : contributions.values()) {
            nodes.add(c.contributor);
            for (String dependencyId : c.dependencies) {
                Contribution d = contributions.get(dependencyId);

                // check for incorrect dependency id
                if (d != null) {
                    arcs.add(new DependencySorter.Arc<RascContributor>(
                            c.contributor, d.contributor));
                } else {
                    logger.warn(String.format(
                            RascContributorPluginsLocator.LOG_UNKNOWN_DEPENDENCY,
                            c.id,
                            dependencyId));
                }
            }
        }

        return new DependencySorter<RascContributor>(arcs, nodes)
                .getOrderedList();
    }

    /**
     * A data class used to assemble contributions for dependency ordering.
     * 
     * @see RascContributorPluginsLocator#orderContributors(Map)
     */
    private static class Contribution {
        protected String id;

        protected RascContributor contributor;

        protected List<String> dependencies;

        protected Contribution(RascContributor aContributor,
                IConfigurationElement aConfiguration) {
            id = aConfiguration
                    .getAttribute(RascContributorPluginsLocator.ID_ATTRIBUTE);
            contributor = aContributor;
            dependencies = new ArrayList<>();

            IConfigurationElement[] dependencyRefs = aConfiguration
                    .getChildren(RascContributorPluginsLocator.DEPENDS_ON_ELEMENT);
            for (IConfigurationElement dependency : dependencyRefs) {
                String dependsOn = dependency.getAttribute(
                        RascContributorPluginsLocator.CONTRIBUTOR_ID_ATTRIBUTE);
                if (dependsOn != null) {
                    dependencies.add(dependsOn);
                }
            }
        }

        /**
         * Returns the contributor ID.
         */
        public String getId() {
            return id;
        }
    }
}
