/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.om.validator.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.ui.internal.indexing.OMResourceIndexProvider;
import com.tibco.xpd.om.resources.ui.types.OMTypesFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validation rule to disallow multiple organization models in a single BPM
 * project.
 * 
 * @author njpatel
 */
@SuppressWarnings("restriction")
public class MultipleOrgModelInSingleProjectRule implements IValidationRule {

    private static final String ISSUE_ID =
            "om.multipleOrgModelInSingleProject.issue"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return OrgModel.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof OrgModel) {
            OrgModel model = (OrgModel) obj;
            IFile omFile = WorkingCopyUtil.getFile(model);

            List<IndexerItem> orgModelItems =
                    getOrgModelItemsFromProject(omFile.getProject(), model);

            if (!orgModelItems.isEmpty()) {
                StringBuffer implicitLinks = new StringBuffer();

                /*
                 * Record implicit links to the duplicate Org Models so that
                 * when the problem is rectified all these OM model are
                 * re-validated.
                 */
                for (IndexerItem item : orgModelItems) {
                    if (implicitLinks.length() > 0) {
                        implicitLinks.append(","); //$NON-NLS-1$
                    }
                    implicitLinks.append(item.getURI());
                }

                List<String> messages = Collections.emptyList();

                scope.createIssue(ISSUE_ID, EcoreUtil.getID(model), EcoreUtil
                        .getURI(model).toString(), messages, Collections
                        .singletonMap(ValidationActivator.LINKED_RESOURCE,
                                implicitLinks.toString()));
            }
        }
    }

    /**
     * Get all the OrgModel indexer items present in the given project, but
     * exclude the indexer item corresponding to the given OrgModel (this will
     * only return indexer items of Org Models that are in the OM special
     * folder).
     * 
     * @param project
     * @param exclude
     * @return list of indexer items. Empty list if there is only one (exclude
     *         OrgModel) OrgModel present in this project.
     */
    @SuppressWarnings("deprecation")
    private List<IndexerItem> getOrgModelItemsFromProject(IProject project,
            OrgModel exclude) {
        List<IndexerItem> items = new ArrayList<IndexerItem>();

        IndexerItemImpl criteria =
                new IndexerItemImpl(null,
                        OMTypesFactory.TYPE_ORG_MODEL.getTypeId(), null, null);
        criteria.set(IndexerServiceImpl.ATTRIBUTE_PROJECT, project.getName());

        Collection<IndexerItem> result =
                XpdResourcesPlugin.getDefault().getIndexerService()
                        .query(OMResourceIndexProvider.INDEXER_ID, criteria);

        String uri = EcoreUtil.getURI(exclude).toString();
        for (IndexerItem item : result) {
            if (!uri.equals(item.getURI()) && isInOMSpecialFolder(item)) {
                items.add(item);
            }
        }

        return items;
    }

    /**
     * Check if the resource corresponding to the given indexer item is
     * contained in the OM special folder.
     * 
     * @param item
     * @return
     */
    private boolean isInOMSpecialFolder(IndexerItem item) {
        String path = item.get(IndexerServiceImpl.ATTRIBUTE_PATH);
        if (path != null) {
            IResource res =
                    ResourcesPlugin.getWorkspace().getRoot().findMember(path);
            if (res != null) {
                SpecialFolder sf = SpecialFolderUtil.getRootSpecialFolder(res);
                return sf != null
                        && OMResourcesActivator.OM_SPECIAL_FOLDER_KIND
                                .equals(sf.getKind());
            }
        }
        return false;
    }
}
