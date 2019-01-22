/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.internal.wsdl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.Operation;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Abstract class of the duplicate operation rule.
 * 
 * @author njpatel
 * 
 */
public abstract class DuplicateOperationRule implements IValidationRule {

    protected static final String DUPLICATE_UNBOUND_OPERATION_ISSUE_ID =
            "com.tibco.xpd.validation.bpmn.developer.issue.duplicateWsdlUnboundOperation"; //$NON-NLS-1$

    protected static final String DUPLICATE_BOUND_OPERATION_ISSUE_ID =
            "com.tibco.xpd.validation.bpmn.developer.issue.duplicateWsdlBoundOperation"; //$NON-NLS-1$

    /**
     * Get project that the given <code>EObject</code> comes from.
     * 
     * @param eo
     * @return <code>IProject</code> if found, <code>null</code> otherwise.
     */
    protected IProject getProject(EObject eo) {
        IProject project = null;

        if (eo != null) {
            project = WorkingCopyUtil.getProjectFor(eo);
        }
        return project;
    }

    /**
     * Add duplicate operation issue for the given operation.
     * 
     * @param project
     *            Project being validated
     * @param scope
     *            validation scope
     * @param operation
     *            operation to add issue about
     * @param uri
     *            uri of the operation
     * @param items
     *            indexer items that have same qualified name as operation.
     */
    @SuppressWarnings("restriction")
    protected void addOperationIssue(IProject project, IValidationScope scope,
            String issueId, Operation operation, String uri,
            Collection<IndexerItem> items) {

        if (project != null && scope != null && operation != null
                && items != null) {
            IWorkspaceRoot root = project.getWorkspace().getRoot();
            Set<String> referencedUris = new HashSet<String>();
            Set<String> allUris = new HashSet<String>();
            Set<IProject> projects = getReferencedProjects(project);
            for (IndexerItem item : items) {
                if (item.getURI().equals(uri)) {
                    continue;
                }
                allUris.add(item.getURI());
                String projectName = WsdlIndexerUtil.getProjectName(item);
                if (projectName != null) {
                    IProject indProject = root.getProject(projectName);
                    if (indProject != null && projects.contains(indProject)) {
                        referencedUris.add(item.getURI());
                    }
                }
            }

            if (!referencedUris.isEmpty()) {
                Map<String, String> issueAttrs = new HashMap<String, String>();
                issueAttrs.put(ValidationActivator.LINKED_RESOURCE,
                        serialize(referencedUris));

                scope.createIssue(issueId,
                        operation.getName(),
                        EcoreUtil.getURI(operation).toString(),
                        new ArrayList<String>(0),
                        issueAttrs);
            }

            if (!allUris.isEmpty()) {
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(operation);
                if (wc != null) {
                    Set<String> deps =
                            (Set<String>) wc.getAttributes()
                                    .get(ValidationActivator.LINKED_RESOURCE);
                    if (deps == null) {
                        deps = new HashSet<String>();
                        wc.getAttributes()
                                .put(ValidationActivator.LINKED_RESOURCE, deps);
                    }
                    deps.addAll(allUris);
                }
            }
        }
    }

    /**
     * Serialize the given collection of URIs.
     * 
     * @param uriSet
     * @return
     */
    private String serialize(Set<String> uriSet) {
        String value = ""; //$NON-NLS-1$
        int idx = 0;
        for (String uri : uriSet) {
            if (idx++ > 0) {
                value += ","; //$NON-NLS-1$
            }
            value += uri;
        }

        return value;
    }

    /**
     * Get a collection containing the project given and all projects it
     * references.
     * 
     * @param project
     * @return set of projects.
     */
    private Set<IProject> getReferencedProjects(IProject project) {
        Set<IProject> projects = new HashSet<IProject>();

        if (project != null) {
            projects.add(project);
            ProjectUtil.getReferencedProjectsHierarchy(project, projects);
        }

        return projects;
    }
}
