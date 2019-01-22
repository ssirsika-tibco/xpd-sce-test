/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * This rule handles the validation for the Model's Tag attribute. 1.should be
 * unique in workspace. 2.should only contain Alphanumeric characters. 3.should
 * have max length of 6 characters.
 * 
 * @author aprasad
 * @since 30 May 2013
 */
public class ModelTagValidationRule implements IValidationRule {

    public ModelTagValidationRule() {
        indexerService = BOMResourcesPlugin.getDefault().getIndexerService();
        workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    }

    private static final String ISSUE_DUPLICATE_MODEL_TAG =
            "duplicate.model.tag.issue"; //$NON-NLS-1$

    private static final String ISSUE_INVALID_CHARACTERS_TAG =
            "model.tag.invalid.characters.issue"; //$NON-NLS-1$

    private static final String ISSUE_INVALID_LENGTH_TAG =
            "model.tag.invalid.length.issue"; //$NON-NLS-1$

    /**
     * Maximum length allowed for tags, as discussed with BDS team.
     */
    private static final int TAG_MAX_LENGTH = 6;

    private final BOMIndexerService indexerService;

    private IWorkspaceRoot workspaceRoot;

    @Override
    public Class<?> getTargetClass() {
        return Package.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof Package) {

            Package pkg = (Package) o;
            String pkgTag = BOMGlobalDataUtils.getPackageTag(pkg);
            /* Validate only when non-empty tag exist. */
            if (pkgTag != null && pkgTag.trim().length() > 0) {

                /* there is a tag in this model- validate */
                /* 1. check characters validity */
                if (!NameUtil.isValidName(pkgTag, false)) {

                    addIssue(scope,
                            pkg,
                            pkgTag,
                            null,
                            ISSUE_INVALID_CHARACTERS_TAG);
                }
                /* 2. check length */
                if (pkgTag.length() > TAG_MAX_LENGTH) {

                    addIssue(scope,
                            pkg,
                            pkgTag,
                            null,
                            ISSUE_INVALID_LENGTH_TAG);
                }
                /* 3. check duplicates */
                List<IndexerItem> modelWithSameTag =
                        getModelWithSameTag(pkgTag, pkg);

                if (modelWithSameTag.size() > 0) {
                    // raise error marker
                    String uri = BomUIUtil.getURI(pkg);
                    // It is important to set linked Resource Marker attributes,
                    // which enables validation of the resources with duplicate
                    // name, when this resource is modified
                    Map<String, String> info =
                            BOMValidationUtil
                                    .getLinkedResourcesMarkerAttribute(pkg.getModel(),
                                            uri,
                                            modelWithSameTag);
                    addIssue(scope,
                            pkg,
                            pkgTag,
                            info,
                            ISSUE_DUPLICATE_MODEL_TAG);
                }
            }
        }
    }

    /**
     * @param scope
     * @param model
     * @param pkgTag
     * @param info
     */
    private void addIssue(IValidationScope scope, Package pkg, String pkgTag,
            Map<String, String> info2, String message) {

        Map<String, String> info =
                info2 == null ? new HashMap<String, String>() : info2;
        String uri = BomUIUtil.getURI(pkg);
        scope.createIssue(message,
                BOMValidationUtil.getLocation(pkg),
                URI.createURI(uri).fragment(),
                Collections.singleton(pkgTag),
                info);
    }

    /**
     * This method calculates the list of models in the workspace, with same tag
     * value.
     * 
     * @param value
     * @param targetModel
     * @param tagStereo
     * @return
     */
    private List<IndexerItem> getModelWithSameTag(String modelTag,
            Package targetModel) {

        /* get all models from the indexer */
        IndexerItem[] modelItems = indexerService.getAllModels();
        List<IndexerItem> duplicates = new ArrayList<IndexerItem>();

        if (modelItems.length > 0) {
            /* Find all models with the same name as the target model */

            for (IndexerItem item : modelItems) {
                /* get model for this indexer entry */
                List<Package> packages = getModel(item);
                /* if not the target model */

                for (Package pkg : packages) {
                    if (!pkg.equals(targetModel)) {
                        String otherTag = BOMGlobalDataUtils.getPackageTag(pkg);

                        if (modelTag.equalsIgnoreCase(otherTag)) {
                            duplicates.add(item);
                        }
                    }
                }
            }
        }
        return duplicates;
    }

    /**
     * Get the model that item represented by the indexer item belongs to.
     * 
     * @param item
     * @return model if can be calculated, <code>null</code> otherwise.
     */
    private List<Package> getModel(IndexerItem item) {
        List<Package> allPackages = new ArrayList<Package>();
        if (item != null) {
            String uriStr = item.getURI();

            if (uriStr != null) {
                URI uri = URI.createURI(uriStr);

                if (uri.isPlatformResource()) {
                    String path = uri.toPlatformString(true);

                    if (path != null) {
                        IResource resource = workspaceRoot.findMember(path);

                        if (resource != null) {
                            EObject eobject =
                                    WorkingCopyUtil.getWorkingCopy(resource)
                                            .getRootElement();

                            if (eobject != null && eobject instanceof Model) {
                                // Get all the packages for this model, this
                                // includes the model itself and any sub-packages
                                Model model = (Model)eobject;
                                allPackages.addAll(model.getNestedPackages());
                                // Add the main model to the list
                                allPackages.add(model);
                            }
                        }
                    }
                }
            }
        }
        return allPackages;
    }
}