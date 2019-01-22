/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.bds.designtime.validator.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.destinations.GlobalDestinationUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Check for duplicate model names across entire workspace (checking only
 * projects that have the correct destination set, i.e. BPM).
 * 
 * @author njpatel
 * 
 */
public class DuplicateModelNameRule implements IValidationRule {

    private static final String ISSUE_ID = "bom.cds.duplicateModelName.issue"; //$NON-NLS-1$

    private final BOMIndexerService indexerService;

    private IWorkspaceRoot workspaceRoot;

    public DuplicateModelNameRule() {
        indexerService = BOMResourcesPlugin.getDefault().getIndexerService();
        workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     */
    public Class<?> getTargetClass() {
        return Model.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd
     * .validation.provider.IValidationScope, java.lang.Object)
     */
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Model) {
            Model model = (Model) obj;
            String name = BOMWorkingCopy.getQualifiedClassName(model);
            String uri = BomUIUtil.getURI(model);

            if (name != null && uri != null) {
                List<IndexerItem> models = getAllModelsWithName(name);
                if (models.size() > 1) {
                    // Duplicates found
                    Map<String, String> info =
                            BOMValidationUtil.getLinkedResourcesMarkerAttribute(model,
                                    uri,
                                    models);
                    scope.createIssue(ISSUE_ID,
                            BOMValidationUtil.getLocation(model),
                            URI.createURI(uri).fragment(),
                            Collections.singleton(name),
                            info);

                }
            }
        }
    }
//Moved to Util class

    /**
     * Get all model items from the indexer with the given name. This will check
     * for all resources in projects with BPM destination enabled.
     * 
     * @param name
     * @return
     */
    private List<IndexerItem> getAllModelsWithName(String name) {
        IndexerItem[] modelItems = indexerService.getAllModels();
        List<IndexerItem> duplicates = new ArrayList<IndexerItem>();

        if (modelItems.length > 0) {
            Set<IProject> projectsWithCDSEnabled = new HashSet<IProject>();
            Set<IProject> projectsWithoutCDSEnabled = new HashSet<IProject>();
            IWorkspaceRoot ws = ResourcesPlugin.getWorkspace().getRoot();
            // Find all models with the same name as the target
            for (IndexerItem item : modelItems) {
                if (name.equalsIgnoreCase(item.getName())) {
                    IProject project = getProject(item);
                    if (project != null) {
                        boolean addToList = false;
                        if (projectsWithCDSEnabled.contains(project)) {
                            addToList = true;
                        } else if (!projectsWithoutCDSEnabled.contains(project)) {
                            // New project so check if it has CDS validation
                            // enabled
                            if (isCDSValidationEnabled(project)) {
                                addToList = true;
                                projectsWithCDSEnabled.add(project);
                            } else {
                                projectsWithoutCDSEnabled.add(project);
                            }
                        }

                        if (addToList) {
                            duplicates.add(item);
                        }
                    }
                }
            }
        }
        return duplicates;
    }

    /**
     * Check if CDS validation is enabled in the given project.
     * 
     * @param project
     * @return
     */
    private boolean isCDSValidationEnabled(IProject project) {
        if (project != null && project.isAccessible()) {
            return GlobalDestinationUtil
                    .isValidationDestinationEnabled(project,
                            BOMValidatorActivator.VALIDATION_DEST_ID_CDS);
        }
        return false;
    }

    /**
     * Get the project that item represented by the indexer item belongs to.
     * 
     * @param item
     * @return project if can be calculated, <code>null</code> otherwise.
     */
    private IProject getProject(IndexerItem item) {
        IProject project = null;

        if (item != null) {
            String uriStr = item.getURI();
            if (uriStr != null) {
                URI uri = URI.createURI(uriStr);
                if (uri.isPlatformResource()) {
                    String path = uri.toPlatformString(true);
                    if (path != null) {
                        IResource resource = workspaceRoot.findMember(path);
                        if (resource != null) {
                            project = resource.getProject();
                        }
                    }
                }
            }
        }
        return project;
    }

}
