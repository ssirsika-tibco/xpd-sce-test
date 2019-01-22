/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.destinations.internal.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.internal.resources.ProjectNatureDescriptor;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNatureDescriptor;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.destinations.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.builder.XpdProjectNature;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetElement;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetManager;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.validation.engine.WorkspaceResourceValidator;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * This rule checks a project for any missing natures that are required by the
 * natures that are present.
 * <p>
 * It also uses the (optional) builder specification in each nature to check
 * that the builders are present and in the order implied by the nature
 * dependency.
 * 
 * @author aallway
 * @since 3.3 (27 Apr 2010)
 */
public class RequiredNaturesRule implements WorkspaceResourceValidator {

    /** Project nature '%1$s' requires missing project nature '%2$s'. */
    private static final String ISSUE_NATURE_MISSING =
            "xpd.requiredNatureMissing"; //$NON-NLS-1$

    /** Project builder '%1$s' for nature '%2$s' is missing from project. */
    private static final String ISSUE_NATURE_BUILDER_MISSING =
            "xpd.requiredNatureBuilderMissing"; //$NON-NLS-1$

    /**
     * Project builders for nature '%1$s' are out of sequence (expected: %2$s).
     */
    private static final String ISSUE_NATURE_BUILDER_OUT_OF_SEQ =
            "xpd.requiredNatureBuilderOutOfSeq"; //$NON-NLS-1$

    public static final String ISSUE_MISSING_NATURES = "xpd.missingNatures"; //$NON-NLS-1$

    public static final String ATT_NATURES = "nature_ids"; //$NON-NLS-1$

    private static boolean warnFormsOrderProblemDone = false;

    public RequiredNaturesRule() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.engine.WorkspaceResourceValidator#validate(com
     * .tibco.xpd.validation.provider.IValidationScope,
     * org.eclipse.core.resources.IResource)
     */
    @Override
    public void validate(IValidationScope scope, IResource resource) {
        if (resource instanceof IProject) {
            IProject project = (IProject) resource;
            try {
                /* Get natures actually defined directly in project. */
                Set<String> projectNatures =
                        getProjectNatures(project.getDescription());

                // Validate required natures (based on selected assets)
                Set<String> missingNatures =
                        getMissingNatures(project, projectNatures);

                if (!missingNatures.isEmpty()) {
                    Map<String, String> info = new HashMap<String, String>();
                    info.put(ATT_NATURES, serializeList(missingNatures));
                    scope.createIssue(ISSUE_MISSING_NATURES,
                            project.getName(),
                            project.getFullPath().toString(),
                            Collections
                                    .singleton(getNatureNames(missingNatures)),
                            info);
                } else {
                    // Validate nature/builder sequence
                    List<TempIssue> issues =
                            validateNatureSequence(project, projectNatures);

                    if (!issues.isEmpty()) {
                        for (TempIssue issue : issues) {
                            addIssue(scope,
                                    issue.project,
                                    issue.issueId,
                                    issue.messages);

                        }
                    }
                }
            } catch (CoreException e) {
                XpdResourcesPlugin.getDefault().getLogger().error(e);
            }
        }

        return;
    }

    /**
     * Check asset types for required natures and then check whether all natures
     * are present in the project.
     * 
     * @param scope
     * @param project
     * @param appliedNatures
     * @return Missing natures or empty list if none missing.
     */
    private static Set<String> getMissingNatures(IProject project,
            Set<String> appliedNatures) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);
        if (config != null) {
            Set<String> missingNatures = new LinkedHashSet<String>();
            Set<String> destinations =
                    GlobalDestinationUtil.getEnabledGlobalDestinations(project,
                            false);

            EList<AssetType> types = config.getAssetTypes();

            if (types != null) {
                for (AssetType type : types) {
                    missingNatures.addAll(getMissingNatures(type.getId(),
                            destinations,
                            appliedNatures));
                }
            }

            return missingNatures;

        }
        return Collections.emptySet();
    }

    /**
     * Get a comma-separated list of the names of the natures missing.
     * 
     * @param missingNatures
     * @return
     */
    private String getNatureNames(Set<String> missingNatures) {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();

        /*
         * XPD-1698: When have multiple missing natures then list "a, b and c"
         * rather than "a,b,c"
         */
        if (missingNatures.size() > 1) {
            int idx = 0;

            String allButLast = ""; //$NON-NLS-1$
            String last = ""; //$NON-NLS-1$

            for (String id : missingNatures) {
                IProjectNatureDescriptor desc =
                        workspace.getNatureDescriptor(id);

                String label =
                        desc != null && desc.getLabel() != null ? desc
                                .getLabel() : id;

                if (idx == (missingNatures.size() - 1)) {
                    last = label;
                    break;
                }
                if (idx++ > 0) {
                    allButLast += ", "; //$NON-NLS-1$
                }
                allButLast += "'" + label + "'"; //$NON-NLS-1$ //$NON-NLS-2$
            }

            return String
                    .format(Messages.RequiredNaturesRule_MultipleItemListFormat_message,
                            allButLast,
                            last);

        } else if (missingNatures.size() > 0) {
            String id = missingNatures.iterator().next();
            IProjectNatureDescriptor desc = workspace.getNatureDescriptor(id);

            String label =
                    desc != null && desc.getLabel() != null ? desc.getLabel()
                            : id;

            return "'" + label + "'"; //$NON-NLS-1$ //$NON-NLS-2$
        }

        return ""; //$NON-NLS-1$
    }

    /**
     * Generate a comma-separated string for the list given.
     * 
     * @param values
     * @return
     */
    private String serializeList(Collection<String> values) {
        StringBuffer str = new StringBuffer();
        int idx = 0;
        for (String value : values) {
            if (idx++ > 0) {
                str.append(","); //$NON-NLS-1$
            }
            str.append(value);
        }

        return str.toString();
    }

    /**
     * Get the missing natures for the given asset type.
     * 
     * @param assetId
     * @param destinationIds
     * @param installedNatures
     * @return
     */
    private static Collection<String> getMissingNatures(final String assetId,
            final Collection<String> destinationIds,
            final Collection<String> installedNatures) {
        Set<String> missingNatures = new LinkedHashSet<String>();

        if (assetId != null) {
            ProjectAssetElement asset =
                    ProjectAssetManager.getProjectAssetMenager()
                            .getAssetById(assetId);
            if (asset != null) {
                // Check for missing natures on the asset before checking any
                // extensions
                getMissingNatures(destinationIds,
                        installedNatures,
                        asset,
                        missingNatures);

                // Check all extending assets for defined natures
                for (ProjectAssetElement extAsset : asset.getExtendingAssets()) {
                    getMissingNatures(destinationIds,
                            installedNatures,
                            extAsset,
                            missingNatures);

                    // Recurse into extending assets
                    missingNatures.addAll(getMissingNatures(extAsset.getId(),
                            destinationIds,
                            installedNatures));
                }
            }
        }

        return missingNatures;
    }

    /**
     * Get any natures that are bound to the asset but are not configured on the
     * project being validated.
     * 
     * @param destinationIds
     * @param installedNatures
     * @param missingNatures
     * @param extAsset
     */
    private static void getMissingNatures(
            final Collection<String> destinationIds,
            final Collection<String> installedNatures,
            ProjectAssetElement extAsset, Set<String> missingNatures) {
        boolean interested = false;

        // Check if this extending asset is interested in the
        // enabled global destinations
        String[] destinations = extAsset.getGlobalDestinationIds();
        if (destinations.length > 0) {
            for (String destId : destinations) {
                if (destinationIds.contains(destId)) {
                    interested = true;
                    break;
                }
            }
        } else {
            // No bound global destinations so applies to all
            interested = true;
        }

        if (interested) {
            for (String natureId : extAsset.getNatures()) {
                if (!installedNatures.contains(natureId)) {
                    missingNatures.add(natureId);
                }
            }
        }
    }

    /**
     * 
     * @param project
     * @return <code>true</code> if the project's natures and builders are in
     *         correct seuqence and that dependent natures are present etc.
     */
    public static boolean checkNatureAndBuilderSequence(IProject project) {
        try {
            IProjectDescription description = project.getDescription();
            if (description != null) {
                Set<String> projectNatures = getProjectNatures(description);

                List<TempIssue> issues =
                        validateNatureSequence(project, projectNatures);

                if (!issues.isEmpty()) {
                    return false;
                }
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }

        return true;
    }

    /**
     * check against missing natures
     * 
     * @param project
     * @return Id's of any missing natures.
     */
    public static Set<String> getMissingNatures(IProject project) {
        try {
            IProjectDescription description = project.getDescription();
            if (description != null) {
                Set<String> projectNatures = getProjectNatures(description);

                Set<String> missingNatures =
                        getMissingNatures(project, projectNatures);

                return missingNatures;
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }

        return Collections.emptySet();
    }

    /**
     * Validate the nature/builder dependencies.
     * 
     * @param scope
     * @param project
     * @param appliedNatures
     * @throws CoreException
     * 
     * @return List of issues.
     */
    private static List<TempIssue> validateNatureSequence(IProject project,
            Set<String> appliedNatures) throws CoreException {

        List<TempIssue> issues = new ArrayList<RequiredNaturesRule.TempIssue>();

        /*
         * While we're at it collect all natures that are depended upon by other
         * natures.
         */
        Set<String> allDependencyNatures = new HashSet<String>();
        IProjectDescription projectDescription = project.getDescription();

        for (String natureId : appliedNatures) {
            /* Get required natures */
            Set<String> requiredNatures = new HashSet<String>();
            gatherRequiredNatures(project, natureId, requiredNatures);

            allDependencyNatures.addAll(requiredNatures);

            /* Check all required are in project */
            for (String reqdNature : requiredNatures) {
                if (!appliedNatures.contains(reqdNature)) {
                    /*
                     * Add params for
                     * "Project nature '%1$s' requires missing nature '%1$2'."
                     */
                    List<String> params = new ArrayList<String>();
                    params.add(ProjectUtil.getNatureName(natureId));
                    params.add(ProjectUtil.getNatureName(reqdNature));

                    issues.add(new TempIssue(project, ISSUE_NATURE_MISSING,
                            params));
                }
            }
        }

        /*
         * For each nature that is not depended upon by other nature then check
         * that the order of builders implied by required natures is actually
         * true in the project file.
         */
        ICommand[] buildSpec = projectDescription.getBuildSpec();
        for (String natureId : appliedNatures) {
            if (!allDependencyNatures.contains(natureId)) {
                /*
                 * This is a nature that is top level (not depended upon by
                 * other natures so we can check it's builder order)
                 */
                checkNatureBuilders(project,
                        projectDescription,
                        buildSpec,
                        natureId,
                        issues);
            }
        }

        return issues;
    }

    /**
     * Check that the builders declared in the nature and it's required nature
     * are present and in correct order.
     * 
     * 
     * @param project
     * @param projectDescription
     * @param buildSpec
     * @param natureId
     * @param issues
     */
    private static void checkNatureBuilders(IProject project,
            IProjectDescription projectDescription, ICommand[] buildSpec,
            String natureId, List<TempIssue> issues) {

        /**
         * When there are multiple required natures in a single nature it
         * becomes necessary to each route thru the tree of natures.
         * 
         * i.e. If the natures are setup like this...
         * 
         * <pre>
         * TopLevelNature
         *    |           \
         *  NatureA1    NatureB1
         *    |            |
         *  NatureB1    NatureB2
         * 
         * </pre>
         * 
         * Then we cannot just 'lump' NatureAx builders and NatureBx into a flat
         * list because they are not interdependent we cannot guarantee the
         * order that they are configured by eclipse.
         * 
         * So we create a list representing each possible chain thru the tree
         * from top to bottom and then check each list.
         */
        List<List<String>> builderChainsForNature =
                new ArrayList<List<String>>();

        getNatureBuilderChains(project,
                natureId,
                builderChainsForNature,
                new ArrayList<String>());

        /*
         * Now we can check each builder chain (a single path thru nature
         * builder tree from to bottom).
         */
        for (List<String> builderChainForNature : builderChainsForNature) {
            /* Bit of a fudge, but validation builder should always be the first */
            if (!builderChainForNature
                    .contains(XpdProjectNature.VALIDATION_BUILDER_ID)) {
                builderChainForNature.add(0,
                        XpdProjectNature.VALIDATION_BUILDER_ID);
            }

            /*
             * buildersForNatures is in order of 'first builder to run in
             * sequence' (same as project builders) so we need to make sure they
             * are in correct order.
             */
            int previousFoundBuilderPosition = -1;
            boolean outOfSequenceIssued = false;

            for (String natureBuilder : builderChainForNature) {
                int builderPosition = indexOfBuilder(buildSpec, natureBuilder);

                if (builderPosition < 0) {
                    /* Builder not found in project builder list. */
                    List<String> params = new ArrayList<String>();
                    params.add(ProjectUtil.getBuilderName(natureBuilder));
                    params.add(ProjectUtil.getNatureName(natureId));

                    issues.add(new TempIssue(project,
                            ISSUE_NATURE_BUILDER_MISSING, params));

                } else {
                    /*
                     * If the position of this builder is prior to the position
                     * found for the previous builder then it is above it in the
                     * list and therefore out of sequence.
                     */
                    if (builderPosition < previousFoundBuilderPosition) {
                        /* Only issue outOfSequence once per nature. */
                        if (!outOfSequenceIssued) {
                            outOfSequenceIssued = true;

                            // TODO Currently the process.forms nature
                            // inserts
                            // builders before the forms nature builders and
                            // even though the nature dependencies are in a
                            // different order - this rule uses the nature
                            // dependency as an implied builder order
                            // dependency.
                            //
                            // At some point we should possibly introduce
                            // our
                            // own strategy for declaring and validating
                            // correct
                            // builder order until then we will ignore the
                            // forms.process builder not being in same order
                            // as
                            // nature
                            // dependency.
                            if ("com.tibco.xpd.forms.process.processNature".equals(natureId)) { //$NON-NLS-1$
                                if (!warnFormsOrderProblemDone) {
                                    warnFormsOrderProblemDone = true;

                                    XpdResourcesPlugin
                                            .getDefault()
                                            .getLogger()
                                            .info("com.tibco.xpd.forms.process.processNature builder order does not match projectr nature dependency order"); //$NON-NLS-1$
                                }

                            } else {

                                List<String> params = new ArrayList<String>();
                                params.add(ProjectUtil.getNatureName(natureId));
                                params.add(describeBuilderList(builderChainForNature));

                                issues.add(new TempIssue(project,
                                        ISSUE_NATURE_BUILDER_OUT_OF_SEQ, params));

                            }
                        }

                    } else {
                        /* Ok, correct sequence so far. */
                        previousFoundBuilderPosition = builderPosition;

                    }
                }
            }
        }

        return;
    }

    /**
     * @param builders
     * @return Comma separated list of builder names.
     */
    private static String describeBuilderList(Collection<String> builderIds) {
        StringBuilder s = new StringBuilder();
        for (String builderId : builderIds) {
            if (s.length() > 0) {
                s.append(", "); //$NON-NLS-1$
            }
            s.append(ProjectUtil.getBuilderName(builderId));

        }
        return s.toString();
    }

    /**
     * @param buildSpec
     * @param builderId
     * 
     * @return The location of the given builder in the buildSpec or -1 if not
     *         found
     */
    private static int indexOfBuilder(ICommand[] buildSpec, String builderId) {
        for (int i = 0; i < buildSpec.length; i++) {
            if (builderId.equals(buildSpec[i].getBuilderName())) {
                return i;
            }
        }
        return -1;
    }

    private static void getNatureBuilderChains(IProject project,
            String natureId, List<List<String>> builderChainsForNature,
            ArrayList<String> visitedNaturesInChain) {

        /*
         * Sid SCF-110: Stop processing when we reach a non-tibco derived nature
         * (don't want to do anything with natures we have no control over).
         * This is because the nature and builder-order validation rule and
         * quick-fix should only apply to tibco natures <p> This is because for
         * other project types (such as Openspace gadget GWT projects) can have
         * natures that we have no control over. For instance the google gadget
         * SDK GAE nature declares order of its builders in plugin xml
         * contribution differently to how it actually configures its project
         * builder order.
         * 
         * Change our validation rule to simply ignore non-tibco natures. The
         * only reasonable way to do this currently will be to detect the
         * presence of the word "tibco" in the nature id. So any nature without
         * tibco (case insensitive) somewhere in it's Id will be ignored.
         * 
         * The quick fix to fix the nature / build order should also ignore
         * non-tibco natures and their associated builders.
         */
        if (!isTibcoProjectNature(natureId)) {
            return;
        }

        /*
         * Recurs down thru the tree until we get to the bottom (i.e. a nature
         * that does not have any required natures OR we've gone round in a
         * circle).
         * 
         * When we do reach the bottom then add a builder chain to the list of
         * builder chains.
         */
        String[] requiredNatureIds = null;

        boolean cycleDetected = visitedNaturesInChain.contains(natureId);
        if (!cycleDetected) {
            IProjectNatureDescriptor natureDescriptor =
                    project.getWorkspace().getNatureDescriptor(natureId);

            if (natureDescriptor != null) {
                /*
                 * Recurs first so as to get the builders from the lowest part
                 * of nature dependency tree first
                 */
                requiredNatureIds = natureDescriptor.getRequiredNatureIds();
            }
        }

        if (requiredNatureIds == null || requiredNatureIds.length == 0) {
            /*
             * We've reach the bottom of this tree chain (or we have cycled) so
             * go back up thru the nature list we went thru top get here adding
             * builders in reverse order.
             */
            if (!cycleDetected) {
                /* Add the final nature onto chain. */
                visitedNaturesInChain.add(natureId);
            }

            List<String> buildersForChain =
                    createReverseNatureBuilderList(project,
                            visitedNaturesInChain);

            if (buildersForChain.size() > 0) {
                builderChainsForNature.add(buildersForChain);
            }

            if (!cycleDetected) {
                /*
                 * Remove the final nature from chain (in case it is used on a
                 * separate branch of nature tree.
                 */
                visitedNaturesInChain.remove(natureId);
            }

        } else {
            /*
             * Otherwise we need to recurs on down thru the tree.
             * 
             * If there are more than one required natures we need to treat each
             * one separately.
             */

            /*
             * This will prevent cycles in an individual nature chain path thru
             * tree
             */
            visitedNaturesInChain.add(natureId);

            for (String requiredSubNatureId : requiredNatureIds) {
                /*
                 * Recurs until we get to bottom of nature chain (path
                 * individual path thru tree).
                 */
                getNatureBuilderChains(project,
                        requiredSubNatureId,
                        builderChainsForNature,
                        visitedNaturesInChain);
            }

            /*
             * Once we're done in this chain remove this nature so that it's not
             * prevented from appearing in a different branch in the chain.
             */
            visitedNaturesInChain.remove(natureId);
        }

        return;
    }

    /**
     * @param project
     * @param visitedNaturesInChain
     * 
     * @return A list of builders in the visitedNaturesInChain in reverse order.
     */
    private static List<String> createReverseNatureBuilderList(
            IProject project, ArrayList<String> visitedNaturesInChain) {
        List<String> buildersForChain = new ArrayList<String>();

        for (int natureIdx = visitedNaturesInChain.size() - 1; natureIdx >= 0; natureIdx--) {
            String natureId = visitedNaturesInChain.get(natureIdx);

            /*
             * Have to use ProjectNatureDescriptor to be able to access
             * builders.
             */
            IProjectNatureDescriptor natureDescriptor =
                    project.getWorkspace().getNatureDescriptor(natureId);

            if (natureDescriptor != null) {

                if (natureDescriptor instanceof ProjectNatureDescriptor) {
                    ProjectNatureDescriptor projectNatureDescriptor =
                            (ProjectNatureDescriptor) natureDescriptor;

                    /*
                     * Sid XPD-2478. Although we need to assign items to the
                     * buildersForChain in reverse-order-of-nature-dependency
                     * (lowest level dependency nature upt to top-level nature)
                     * we should NOT reverse the order of builders in each
                     * nature.
                     * 
                     * The builders in each nature are in the order of 'lowest
                     * dependency first@ i.e. if builderA is listed before
                     * builderB then builderB IS dependent upon builderA - so
                     * builderA should go in the list first.
                     */
                    String[] builderIds =
                            projectNatureDescriptor.getBuilderIds();
                    if (builderIds != null) {
                        for (int builderIdx = 0; builderIdx < builderIds.length; builderIdx++) {
                            buildersForChain.add(builderIds[builderIdx]);
                        }
                    }
                }
            }
        }
        return buildersForChain;
    }

    /**
     * @param natureId
     * 
     * @return <code>true</code> if the given nature Id is considered to be a
     *         Tibco one.
     */
    public static boolean isTibcoProjectNature(String natureId) {
        if (natureId != null && natureId.toLowerCase().contains("tibco")) { //$NON-NLS-1$
            return true;
        }
        return false;
    }

    /**
     * @param projectDescription
     * @param project
     * @return set of natures actually listed in project
     * @throws CoreException
     */
    private static Set<String> getProjectNatures(
            IProjectDescription projectDescription) {
        Set<String> natures = new HashSet<String>();

        String[] natureIds = projectDescription.getNatureIds();
        for (String nId : natureIds) {
            natures.add(nId);
        }

        return natures;
    }

    /**
     * Add natures required by given nature to list.
     * 
     * @param project
     * @param natureId
     * @param requiredNatures
     */
    private static void gatherRequiredNatures(IProject project,
            String natureId, Set<String> requiredNatures) {
        IProjectNatureDescriptor natureDescriptor =
                project.getWorkspace().getNatureDescriptor(natureId);
        if (natureDescriptor != null) {

            String[] requiredNatureIds =
                    natureDescriptor.getRequiredNatureIds();
            if (requiredNatureIds != null) {
                /* Add all natures required at this level. */
                for (String reqdNature : requiredNatureIds) {
                    if (!requiredNatures.contains(reqdNature)) {
                        requiredNatures.add(reqdNature);
                        gatherRequiredNatures(project,
                                reqdNature,
                                requiredNatures);
                    }
                }

            }
        }
        return;
    }

    /**
     * Add create and add issue to the validation scope.
     * 
     * @param scope
     * @param project
     * @param issueId
     * @param messages
     */
    private void addIssue(IValidationScope scope, IProject project,
            String issueId, Collection<String> messages) {
        if (messages == null) {
            messages = Collections.emptyList();
        }

        scope.createIssue(issueId, project.getName(), project.getFullPath()
                .toString(), messages);

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.engine.WorkspaceResourceValidator#setProject
     * (org.eclipse.core.resources.IProject)
     */
    @Override
    public void setProject(IProject project) {
        // TODO Auto-generated method stub

    }

    /**
     * Temp store for info to create issue for.
     * 
     * 
     * @author aallway
     * @since 13 Jan 2012
     */
    private static class TempIssue {
        IProject project;

        /**
         * @param project
         * @param issueId
         * @param messages
         */
        public TempIssue(IProject project, String issueId,
                Collection<String> messages) {
            super();
            this.project = project;
            this.issueId = issueId;
            this.messages = messages;
        }

        String issueId;

        Collection<String> messages;
    }

}
