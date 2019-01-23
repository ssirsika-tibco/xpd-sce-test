/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.daa.internal.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.daa.CompositeContributor;
import com.tibco.xpd.daa.CompositeContributor.Context;
import com.tibco.xpd.daa.DaaActivator;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.DependencySorter;
import com.tibco.xpd.resources.util.DependencySorter.Arc;

/**
 * Manages the <code>compositeContributors</code> extension.
 * 
 * @author mtorres
 */
public class CompositeContributorsExtensionManager {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

    private static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

    private static final String DEPENDS_ON_ELEMENT = "dependsOn"; //$NON-NLS-1$

    private static final String CONTRIBUTOR_ID_ATTRIBUTE = "contributorId"; //$NON-NLS-1$

    private static final String EXTENSION_NAME = "compositeContributors"; //$NON-NLS-1$

    private static final String CONTRIBUTOR_ELEMENT = "contributor"; //$NON-NLS-1$

    private static final String CONTRIBUTION_CONTEXT_ELEMENT =
            "contributionContext"; //$NON-NLS-1$

    private static final String CONTEXT_ID_ATTRIBUTE = "contextId"; //$NON-NLS-1$

    private static final String SUPPRESS_PREPARE_BEFORE_UNDEPLOY_POLICY =
            "suppressPrepareBeforeUndeployPolicy"; //$NON-NLS-1$

    /** Singleton instance. */
    private static final CompositeContributorsExtensionManager INSTANCE =
            new CompositeContributorsExtensionManager();

    private List<CompositeContributor> compositeContributors;

    private Map<String, CompositeContributor.Context> contributionContexts;

    /**
     * Private constructor to prevent instantiation.
     */
    private CompositeContributorsExtensionManager() {
    }

    public static CompositeContributorsExtensionManager getInstance() {
        return INSTANCE;
    }

    public List<CompositeContributor> getCompositeContributors() {
        init();
        return Collections.unmodifiableList(compositeContributors);
    }

    public List<CompositeContributor> getCompositeContributors(String contextId) {
        init();
        ArrayList<CompositeContributor> contributors =
                new ArrayList<CompositeContributor>();
        if (contextId != null) {
            for (CompositeContributor c : compositeContributors) {
                if (c.getContext() != null
                        && contextId.equals(c.getContext().getId())) {
                    contributors.add(c);
                }
            }
        }
        return Collections.unmodifiableList(contributors);
    }

    public List<CompositeContributor.Context> getContributionContexts() {
        init();
        return Collections
                .unmodifiableList(new ArrayList<CompositeContributor.Context>(
                        contributionContexts.values()));
    }

    /**
     * Initalialize manager from extensions.
     */
    private void init() {
        synchronized (INSTANCE) {
            if (compositeContributors == null) {
                contributionContexts = createContributionContexts();
                compositeContributors =
                        createCompositeContributors(contributionContexts);
                LOG.debug("Composite contribution order: \n" + compositeContributors); //$NON-NLS-1$
            }
        }
    }

    /**
     * Gets the ordered list of contributors.
     * 
     * @return the ordered list of contributors.
     */
    private Map<String, CompositeContributor.Context> createContributionContexts() {
        LinkedHashMap<String, CompositeContributor.Context> contexts =
                new LinkedHashMap<String, CompositeContributor.Context>();
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElements =
                extensionRegistry
                        .getConfigurationElementsFor(DaaActivator.PLUGIN_ID,
                                EXTENSION_NAME);
        for (IConfigurationElement element : configurationElements) {
            if (CONTRIBUTION_CONTEXT_ELEMENT.equals(element.getName())) {
                String id = element.getAttribute(ID_ATTRIBUTE);

                /* check for duplicates */
                if (!contexts.keySet().contains(id)) {
                    String name = element.getAttribute(NAME_ATTRIBUTE);
                    CompositeContributor.Context context =
                            new CompositeContributor.Context(id, name);
                    contexts.put(id, context);
                } else {
                    LOG.error(String
                            .format("Duplicate SCA composite contribution context id '%1$s'!.", //$NON-NLS-1$
                                    id));
                }
            }
        }
        return contexts;
    }

    /**
     * Gets the ordered list of contributors.
     * 
     * @return the ordered list of contributors.
     */
    private List<CompositeContributor> createCompositeContributors(
            final Map<String, CompositeContributor.Context> contexts) {
        // contributionId -> contribution
        LinkedHashMap<String, Contribution> contributions =
                new LinkedHashMap<String, Contribution>();
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElements =
                extensionRegistry
                        .getConfigurationElementsFor(DaaActivator.PLUGIN_ID,
                                EXTENSION_NAME);
        for (IConfigurationElement element : configurationElements) {
            if (CONTRIBUTOR_ELEMENT.equals(element.getName())) {
                try {
                    String id = element.getAttribute(ID_ATTRIBUTE);
                    /* check for duplicates */
                    if (!contributions.keySet().contains(id)) {
                        Object className =
                                element.createExecutableExtension(CLASS_ATTRIBUTE);
                        CompositeContributor contributor =
                                (CompositeContributor) className;
                        ArrayList<String> dependencies =
                                new ArrayList<String>();
                        for (IConfigurationElement dependencyElement : element
                                .getChildren(DEPENDS_ON_ELEMENT)) {
                            String dependentContrId =
                                    dependencyElement
                                            .getAttribute(CONTRIBUTOR_ID_ATTRIBUTE);
                            if (dependentContrId != null) {
                                dependencies.add(dependentContrId);
                            }
                        }
                        String contextId =
                                element.getAttribute(CONTEXT_ID_ATTRIBUTE);
                        if (contextId != null) {
                            Context context = contexts.get(contextId);
                            if (context != null) {
                                contributor.setContext(context);
                            } else {
                                LOG.error(String
                                        .format("Invalid contextId defined in extension for '%1$s' composite contributor.",
                                                id));
                            }
                        }
                        Contribution c =
                                new Contribution(id, contributor, dependencies);
                        contributions.put(id, c);
                    } else {
                        LOG.error(String
                                .format("Duplicate SCA composite contribution id '%1$s'!.", //$NON-NLS-1$
                                        id));
                    }

                } catch (CoreException e) {
                    LOG.error(e);
                }
            }
        }
        return getOrderedContributorClasses(contributions);
    }

    /**
     * 
     * @return List of Compiled {@link Pattern}'s of component names for which
     *         the Prepare Before Undeploy Policy is been suppressed.
     */
    public List<Pattern> getCompiledSuppressPrepareBeforeUndeployPolicyPatterns() {

        List<Pattern> patterns = new ArrayList<Pattern>();

        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        IConfigurationElement[] configurationElements =
                extensionRegistry
                        .getConfigurationElementsFor(DaaActivator.PLUGIN_ID,
                                EXTENSION_NAME);
        for (IConfigurationElement element : configurationElements) {
            if (CONTRIBUTOR_ELEMENT.equals(element.getName())) {

                String suppressPrepareBeforeUndeployPolicy =
                        element.getAttribute(SUPPRESS_PREPARE_BEFORE_UNDEPLOY_POLICY);

                if (suppressPrepareBeforeUndeployPolicy != null
                        && suppressPrepareBeforeUndeployPolicy.length() > 0) {
                    patterns.add(Pattern
                            .compile(suppressPrepareBeforeUndeployPolicy));
                }
            }
        }
        return patterns;
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
    private List<CompositeContributor> getOrderedContributorClasses(
            final Map<String, Contribution> contributions) {

        ArrayList<DependencySorter.Arc<CompositeContributor>> arcs =
                new ArrayList<Arc<CompositeContributor>>();
        ArrayList<CompositeContributor> nodes =
                new ArrayList<CompositeContributor>();
        // Create dependency arcs.
        for (Contribution c : contributions.values()) {
            nodes.add(c.contributor);
            for (String dependencyId : c.dependencies) {
                Contribution d = contributions.get(dependencyId);
                if (d != null) { // check for incorrect dependency id
                    arcs.add(new DependencySorter.Arc<CompositeContributor>(
                            c.contributor, d.contributor));
                } else {
                    LOG.error(String
                            .format("SCA composite contributor '%1$s' declares dependency on non existing contribution '%2$s'.", //$NON-NLS-1$
                                    c.id,
                                    dependencyId));
                }
            }
        }
        return new DependencySorter<CompositeContributor>(arcs, nodes)
                .getOrderedList();
    }

    private static class Contribution {

        protected String id;

        protected CompositeContributor contributor;

        protected List<String> dependencies;

        protected Contribution(String id, CompositeContributor contributor,
                List<String> dependencies) {
            this.id = id;
            this.contributor = contributor;
            this.dependencies = dependencies;

        }

        /**
         * Generated (based only on id). {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        /**
         * Generated (based only on id). {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Contribution other = (Contribution) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        }
    }

}
