/**
 * ProcessPreCommitCommandHelper.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.xpdl2.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.resources.util.DependencySorter;
import com.tibco.xpd.resources.util.DependencySorter.Arc;

/**
 * ProcessPreCommitCommandHelper
 * <p>
 * Helper for the
 * <code>com.tibco.xpd.xpdl2.resources.processPreCommitCommandContributor</code>
 * extension point.
 */
public class ProcessPreCommitCommandHelper {

    // Default static instance
    private static ProcessPreCommitCommandHelper INSTANCE =
            new ProcessPreCommitCommandHelper();

    /**
     * Get the Default static instance
     * 
     * @return the Default static instance
     */
    public static ProcessPreCommitCommandHelper getDefault() {
        return INSTANCE;
    }

    // The contribution loader class itself.
    private static final String PRE_COMMIT_COMMAND_EXTPOINT_ID =
            "processPreCommitCommandContributor"; //$NON-NLS-1$ 

    private static final String WRAPPER_EL =
            "ProcessPreCommitCommandContributor"; //$NON-NLS-1$ 

    private static final String CLASS_ATTR = "class"; //$NON-NLS-1$ 

    private static final String ID_ATTR = "id"; //$NON-NLS-1$

    private static final String RUN_BEFORE_AFTER_CONTRIBUTOR_ID_ATTR =
            "runBeforeAfterContributorId"; //$NON-NLS-1$

    private static final String RUN_BEFORE_OR_AFTER_ATTR = "run"; //$NON-NLS-1$

    private static final String BEFORE = "Before"; //$NON-NLS-1$

    private static final String AFTER = "After"; //$NON-NLS-1$

    private List<ContributorData> contributions;

    /**
     * Load the contributions.
     */
    public ProcessPreCommitCommandHelper() {
        loadContributions();
    }

    /**
     * Load the pre execution command wrapper contributions.
     */
    private void loadContributions() {
        contributions = new ArrayList<ContributorData>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ResourcesPlugin.PLUGIN_ID,
                                PRE_COMMIT_COMMAND_EXTPOINT_ID);

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] elements =
                            ext.getConfigurationElements();
                    if (elements != null) {
                        for (int i = 0; i < elements.length; i++) {
                            IConfigurationElement el = elements[i];
                            if (WRAPPER_EL.equals(el.getName())) {
                                ContributorData contributorData =
                                        new ContributorData(el);
                                contributions.add(contributorData);
                            }
                        }
                    }
                }
            }
        }
        return;
    }

    /**
     * @return the sorted list of contributions based on contributors'
     *         dependencies (i.e., whether they should appear before or after
     *         specified contributors)
     */
    public List<IProcessPreCommitContributor> getContributions() {

        List<IProcessPreCommitContributor> contributionsList =
                new ArrayList<IProcessPreCommitContributor>();

        // get the list of contributors
        for (ContributorData contributorData : contributions) {
            contributionsList.add(contributorData.clazz);
        }

        // sort the list of contributors
        if (contributions != null && !contributions.isEmpty()) {

            List<Arc<IProcessPreCommitContributor>> arcs =
                    new ArrayList<DependencySorter.Arc<IProcessPreCommitContributor>>();

            for (ContributorData contribData : contributions) {

                if (contribData.runBeforeAfterContributorId != null
                        && !contribData.runBeforeAfterContributorId.isEmpty()
                        && contribData.runBeforeAfter != null
                        && !contribData.runBeforeAfter.isEmpty()) {

                    IProcessPreCommitContributor contributor =
                            getContributorUsingId(contribData.runBeforeAfterContributorId);
                    if (contributor != null) {
                        IProcessPreCommitContributor from = null;
                        IProcessPreCommitContributor to = null;

                        if (contribData.runBeforeAfter.equals(BEFORE)) {
                            from = contributor;
                            to = contribData.clazz;
                        } else if (contribData.runBeforeAfter.equals(AFTER)) {
                            from = contribData.clazz;
                            to = contributor;
                        }

                        if (from != null && to != null) {
                            Arc<IProcessPreCommitContributor> arc =
                                    new Arc<IProcessPreCommitContributor>(from,
                                            to);
                            arcs.add(arc);
                        }
                    }
                }
            }
            DependencySorter<IProcessPreCommitContributor> sorter =
                    new DependencySorter<IProcessPreCommitContributor>(arcs,
                            contributionsList);
            contributionsList = sorter.getOrderedList();
        }
        return contributionsList;
    }

    /**
     * 
     * @param runBeforeAfterContributorId
     * @return contributor for the given contributor id or null if the id is not
     *         specified
     */
    private IProcessPreCommitContributor getContributorUsingId(
            String runBeforeAfterContributorId) {

        for (ContributorData contributorData : contributions) {
            if (contributorData.id != null
                    && contributorData.id.equals(runBeforeAfterContributorId)) {
                return contributorData.clazz;
            }
        }
        return null;
    }

    /**
     * Wrapper class to hold extension contrbution's data
     * 
     */
    private class ContributorData {

        IProcessPreCommitContributor clazz;

        String id;

        String runBeforeAfterContributorId;

        String runBeforeAfter;

        /**
         * @param configElement
         */
        public ContributorData(IConfigurationElement configElement) {

            try {
                Object cls =
                        configElement.createExecutableExtension(CLASS_ATTR);
                if (cls instanceof IProcessPreCommitContributor) {

                    clazz = (IProcessPreCommitContributor) cls;
                    id = configElement.getAttribute(ID_ATTR);
                    runBeforeAfterContributorId =
                            configElement
                                    .getAttribute(RUN_BEFORE_AFTER_CONTRIBUTOR_ID_ATTR);
                    runBeforeAfter =
                            configElement
                                    .getAttribute(RUN_BEFORE_OR_AFTER_ATTR);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }
}
