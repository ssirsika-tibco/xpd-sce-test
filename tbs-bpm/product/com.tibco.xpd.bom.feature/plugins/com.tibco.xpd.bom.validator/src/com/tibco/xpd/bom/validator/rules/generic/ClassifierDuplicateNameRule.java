/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.validator.rules.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validator test to check whether class names are duplicate
 * 
 * @author rsomayaj
 * 
 */
public class ClassifierDuplicateNameRule implements IValidationRule {

    @SuppressWarnings("unchecked")
    public java.lang.Class getTargetClass() {
        return Classifier.class;
    }

    @SuppressWarnings("unchecked")
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Classifier) {
            Classifier clazz = (Classifier) o;
            if (clazz.getQualifiedName() != null) {
                String name = BOMWorkingCopy.getQualifiedClassName(clazz);
                String uri = BomUIUtil.getURI(clazz);

                IndexerItemImpl cr = new IndexerItemImpl();
                cr.setName(name);
                Collection<IndexerItem> items = XpdResourcesPlugin.getDefault()
                        .getIndexerService().query(
                                BOMResourcesPlugin.BOM_INDEXER_ID, cr);

                // another check if projects are dependent

                if (items.size() > 1) {

                    String thisProject = getProject(uri);
                    boolean duplicateDetected = false;

                    IWorkspaceRoot root = ResourcesPlugin.getWorkspace()
                            .getRoot();
                    IProject myProject = root.getProject(thisProject);
                    Set<IProject> referencedProjects = ProjectUtil
                            .getReferencedProjectsHierarchy(myProject, null);
                    // check all duplicate entries
                    for (IndexerItem indexerItem : items) {
                        String uri2 = indexerItem.getURI();

                        // don't consider this object
                        if (uri.equals(uri2)) {
                            continue;
                        }

                        String project2 = getProject(uri2);

                        // if it is in the same project break and create issue
                        if (thisProject.equals(project2)) {
                            duplicateDetected = true;
                            break;
                        }

                        // if it is in different project check the dependent
                        // projects
                        if (referencedProjects.contains(root
                                .getProject(project2))) {
                            duplicateDetected = true;
                            break;
                        }
                    }

                    // store it in temporary attribute if the dependency is
                    // in the other direction
                    Set<String> currAtt = (Set<String>) WorkingCopyUtil
                            .getWorkingCopyFor(clazz).getAttributes().get(
                                    ValidationActivator.LINKED_RESOURCE);
                    if (currAtt == null) {
                        currAtt = new HashSet<String>();
                        WorkingCopyUtil.getWorkingCopyFor(clazz)
                                .getAttributes().put(
                                        ValidationActivator.LINKED_RESOURCE,
                                        currAtt);
                    }

                    String value = ""; //$NON-NLS-1$
                    for (IndexerItem item : items) {
                        if (!item.getURI().equals(uri)) {
                            String path = item.getURI();

                            if (path != null) {
                                currAtt.add(path);
                                if (value.length() > 0) {
                                    value += ","; //$NON-NLS-1$
                                }
                                value += path;
                            }
                        }
                    }

                    Map<String, String> attrs = null;

                    if (value.length() > 0) {
                        attrs = new HashMap<String, String>();
                        attrs.put(ValidationActivator.LINKED_RESOURCE, value);
                    }

                    if (duplicateDetected) {
                        List<String> additionalMessages = new ArrayList<String>();
                        additionalMessages.add(clazz.getName());
                        
//                        scope.createIssue(
//                                GenericIssueIds.CLASSIFIER_DUPLICATENAME, name,
//                                clazz.eResource().getURIFragment(clazz),
//                                new ArrayList<String>(0), attrs);
                        
                        scope.createIssue(
                                GenericIssueIds.CLASSIFIER_DUPLICATENAME,
                                BOMValidationUtil.getLocation(clazz), clazz
                                        .eResource().getURIFragment(clazz), additionalMessages);
                        
                        
                    }
                }
            }
        }
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
