/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.rules.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule to check whether multiple packages has same prefix with
 * different case.
 * 
 * @author aprasad
 * 
 */
public class PackageDuplicatePrefixesRule implements IValidationRule {

    @Override
    @SuppressWarnings("unchecked")
    public Class getTargetClass() {
        return Package.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Package) {
            Package pck = (Package) o;
            if (pck.getQualifiedName() != null) {
                String uri = BomUIUtil.getURI(pck);
                IndexerItemImpl cr = new IndexerItemImpl();
                // get all indexer entries for Package
                cr.setType(ResourceItemType.PACKAGE.name());
                Collection<IndexerItem> items =
                        XpdResourcesPlugin.getDefault().getIndexerService()
                                .query(BOMResourcesPlugin.BOM_INDEXER_ID, cr);

                if (items.size() > 1) {
                    // get all models which has matching[including lead
                    // segments] package prefixes but different case
                    List<IndexerItem> matchingModels =
                            getDuplicatePrefixedPackageModels(pck, items, uri);
                    // if duplicates with different case found
                    if (matchingModels != null && matchingModels.size() > 0) {
                        // update internal links for these models such that
                        // any change in one leads to validating the other
                        Map<String, String> info =
                                BOMValidationUtil
                                        .getLinkedResourcesMarkerAttribute(pck
                                                .getModel(),
                                                uri,
                                                matchingModels);

                        // raise issue
                        scope.createIssue("packagePrefixCase.issue",
                                BOMValidationUtil.getLocation(pck),
                                pck.eResource().getURIFragment(pck),
                                Collections.singleton(pck.getName()),
                                info);
                    }
                }
            }
        }
    }

    /**
     * @param pck
     * @param items
     * @param currentProjectUri
     * @return
     */
    private List<IndexerItem> getDuplicatePrefixedPackageModels(Package pck,
            Collection<IndexerItem> items, String currentProjectUri) {
        List<IndexerItem> matchingItems = new ArrayList<IndexerItem>();
        String currentProjectName = getProject(currentProjectUri);

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject currProject = root.getProject(currentProjectName);

        /*
         * Will store all the projects that will be deployed in the same DAA
         */
        Set<IProject> allProjectsDeployedInSameDaa =
                getAllProjectsDeployedInSameDaa(currProject);

        // check all duplicate entries
        for (IndexerItem indexerItem : items) {

            String uri2 = indexerItem.getURI();

            // don't consider this object
            if (currentProjectUri.equals(uri2)) {
                continue;
            }
            // XPD-4460 skip the indexer entries , for deleted files , for which
            // indexer might not have been updated yet.
            EObject packaze =
                    BOMIndexerService.getInstance().resolve(indexerItem);

            if (packaze == null) {
                continue;
            }

            IFile bomFile = WorkingCopyUtil.getFile(packaze);
            if (bomFile.exists()) {
                String indexerProjectName =
                        indexerItem.get(IndexerServiceImpl.ATTRIBUTE_PROJECT);
                IProject indexerProject = root.getProject(indexerProjectName);
                /*
                 * if the current package and indexed package are related
                 * through reference in either direction, check the package
                 * names for same prefix with different case.
                 */
                if (allProjectsDeployedInSameDaa.contains(indexerProject)) {

                    if (samePrefixWithDifferentCase(BOMWorkingCopy.getQualifiedClassName(pck),
                            indexerItem.getName())) {
                        matchingItems.add(indexerItem);

                    }
                }

            }
        }
        return matchingItems;
    }

    /**
     * 
     * @param currProject
     *            the current project
     * @return Set of projects(refered/referencing directly/indirectly) that
     *         would be deployed in the same DAA as that of the passed project.
     */
    private Set<IProject> getAllProjectsDeployedInSameDaa(IProject currProject) {
        /*
         * Will store all the projects that will be deployed in the same DAA
         */
        Set<IProject> allProjectsDeployedInSameDaa = new HashSet<IProject>();
        /*
         * add the current project as it will definately be deployed to the same
         * daa ;)
         */
        allProjectsDeployedInSameDaa.add(currProject);

        /*
         * XPD-7116: The problem marker ' All BOM package prefixes should be
         * different and if they are same they should also be case-sensitively
         * identical.' should be raised for all case-insensitive packages that
         * will deployed to the same daa. (i.e. ProjectA separately references
         * BOMProjectA and BOMProjectB but these BOM projects do not reference
         * each other but still they should have the problem marker as they
         * would be deployed to the same daa)
         */
        /*
         * get the hierarchy of projects that reference this BOM project
         */
        Set<IProject> referencingProjects =
                ProjectUtil.getReferencingProjectsHierarchy(currProject, null);

        if (!referencingProjects.isEmpty()) {
            /*
             * For each referencing project get the hierarchy of project that it
             * references and add to the Set.
             */
            allProjectsDeployedInSameDaa.addAll(referencingProjects);

            for (IProject iProject : referencingProjects) {
                Set<IProject> referencedProjectsHierarchy =
                        ProjectUtil.getReferencedProjectsHierarchy(iProject,
                                null);
                allProjectsDeployedInSameDaa.addAll(referencedProjectsHierarchy);
            }
        } else {
            /*
             * If no projects reference this BOM project then add the hierarchy
             * of project that this BOM project references
             */
            allProjectsDeployedInSameDaa.addAll(ProjectUtil
                    .getReferencedProjectsHierarchy(currProject, null));
        }

        return allProjectsDeployedInSameDaa;
    }

    /**
     * Compares the package prefixes to be case insensitively different, returns
     * true for the first prefix which differs ONLY in case. Returns false for
     * the first mismatch in prefixes i.e prefix does not match at all,
     * continues to check the next prefix only if the previous prefixes passes
     * the case sensitive match.
     * 
     * @param package1
     * @param package2
     * @return
     */
    private boolean samePrefixWithDifferentCase(String package1, String package2) {
        if (package1 == null || package2 == null) {
            return false;
        }
        String[] package1Prefixes = package1.split("\\."); //$NON-NLS-1$
        String[] package2Prefixes = package2.split("\\."); //$NON-NLS-1$
        // Take the length of the package with min number of prefixes.
        int minNumberOfprefixes =
                (package1Prefixes.length < package2Prefixes.length) ? package1Prefixes.length
                        : package2Prefixes.length;
        for (int i = 0; i < minNumberOfprefixes; i++) {
            // if prefixes match [case insensitive]
            if (package1Prefixes[i].equalsIgnoreCase(package2Prefixes[i])) {
                // check if they match in case.
                if (!package1Prefixes[i].equals(package2Prefixes[i])) {
                    return true;
                }
            } else {
                // the prefix differs in spelling
                return false;
            }
        }
        return false;
    }

    /**
     * Returns name of the project from string that represent platform URI.
     * 
     * @param uri
     * @return name of the project or null
     */
    private String getProject(String uri) {
        try {
            URI u = URI.createURI(uri);
            if (u.isPlatformResource()) {
                // first segment is a project name
                return u.segment(1);
            }
        } catch (IllegalArgumentException e) {
            BOMValidatorActivator.getDefault().getLogger().error(e);
        }
        return null;
    }
}
