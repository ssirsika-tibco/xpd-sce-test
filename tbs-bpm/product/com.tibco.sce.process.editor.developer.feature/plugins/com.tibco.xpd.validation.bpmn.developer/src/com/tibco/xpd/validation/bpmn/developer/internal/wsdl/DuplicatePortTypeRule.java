/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.internal.wsdl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.PortType;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlIndexerAttributes;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate against two wsdls in a set of referenced/referencing projects having
 * same namespace and same port type
 * 
 * @author bharge
 * @since 9 Oct 2014
 */
public class DuplicatePortTypeRule implements IValidationRule {

    private static final String DUPLICATE_PORT_TYPE_ISSUE_ID =
            "duplicate_port_type_in_wsdls_in_referencing_projects"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {

        return PortType.class;
    }

    /**
     * Validate if a port type is defined multiple times within a wsdl or in its
     * referenced projects wsdls. We check this by getting the indexer items for
     * a given port type, its namespace and the port type name
     * 
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof PortType) {

            PortType portType = (PortType) o;
            IProject project = getProject(portType);
            if (project != null) {

                Map<WsdlIndexerAttributes, String> indexerAttrs =
                        new HashMap<WsdlIndexerAttributes, String>();
                indexerAttrs.put(WsdlIndexerAttributes.TARGET_NAMESPACE,
                        portType.getQName().getNamespaceURI());

                /* query on indexer with port type name and target namespace */
                Collection<IndexerItem> indexedItems =
                        WsdlIndexerUtil.getIndexedItems(portType.getQName()
                                .getLocalPart(),
                                WsdlElementType.PORT_TYPE,
                                null,
                                indexerAttrs,
                                true,
                                true);
                if (null != indexedItems && indexedItems.size() > 1) {

                    /*
                     * XPD-4070: Check whether the corresponding wsdl files
                     * still exist. Prior to this check sometimes this rule
                     * failed due to the fact that a indexer update (related to
                     * a deleted wsdl) was still pending
                     */
                    int wsdlCount = 0;
                    wsdlCount = getWsdlCount(indexedItems, wsdlCount);
                    if (wsdlCount > 1) {

                        /*
                         * Multiple PortTypes found in the indexer, raise an
                         * issue
                         */
                        addMultiplePortTypeIssue(project,
                                scope,
                                DUPLICATE_PORT_TYPE_ISSUE_ID,
                                portType,
                                EcoreUtil.getURI(portType).toString(),
                                indexedItems);
                    }
                }
            }
        }
    }

    /**
     * Get project that the given <code>EObject</code> comes from.
     * 
     * @param eo
     * @return <code>IProject</code> if found, <code>null</code> otherwise.
     */
    private IProject getProject(EObject eo) {

        IProject project = null;

        if (eo != null) {

            project = WorkingCopyUtil.getProjectFor(eo);
        }
        return project;
    }

    /**
     * Add duplicate port type issue as the given port type is defined multiple
     * times within a project or its referenced/referencing projects
     * 
     * @param project
     * @param scope
     * @param issueId
     * @param portType
     * @param uri
     * @param indexedItems
     */
    private void addMultiplePortTypeIssue(IProject project,
            IValidationScope scope, String issueId, PortType portType,
            String uri, Collection<IndexerItem> indexedItems) {

        if (project != null && scope != null && portType != null
                && indexedItems != null) {

            Set<String> referencedUris = new HashSet<String>();
            Set<String> allUris = new HashSet<String>();
            /* To mention all the wsdl file names in the error message */
            StringBuffer wsdlFileNames = new StringBuffer();

            IWorkspaceRoot root = project.getWorkspace().getRoot();
            Set<IProject> projects =
                    getAllReferencedAndReferencingProjects(project);

            /* get all the port type uris from the indexer items */
            for (IndexerItem item : indexedItems) {

                String path = WsdlIndexerUtil.getPath(item);
                wsdlFileNames.append("'" + path + "' "); //$NON-NLS-1$ //$NON-NLS-2$
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

            /* raise an error marker if the port type uris is not empty */
            if (!referencedUris.isEmpty()) {

                Map<String, String> issueAttrs = new HashMap<String, String>();
                issueAttrs.put(ValidationActivator.LINKED_RESOURCE,
                        serialize(referencedUris));
                scope.createIssue(issueId,
                        String.format(Messages.DuplicateUnboundOperationRule_messages_error_location_shortdesc,
                                portType.getQName().getLocalPart(),
                                wsdlFileNames.toString()),
                        EcoreUtil.getURI(portType).toString(),
                        new ArrayList<String>(0),
                        issueAttrs);
            }

            /* link the resources */
            if (!allUris.isEmpty()) {

                WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(portType);
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
     * Get a collection containing the given project and all its referenced and
     * referencing projects
     * 
     * @param project
     * @return set of projects.
     */
    private Set<IProject> getAllReferencedAndReferencingProjects(
            IProject project) {

        Set<IProject> projects = new HashSet<IProject>();
        if (project != null) {

            projects.add(project);
            ProjectUtil.getReferencedProjectsHierarchy(project, projects);
            ProjectUtil.getReferencingProjectsHierarchy(project, projects);
        }

        return projects;
    }

    /**
     * Go thru the collection of wsdl indexer items and find if the wsdl file
     * exists. If it exists increment the count.
     * 
     * @param items
     * @param wsdlCount
     * @return count of wsdl file from the wsdl indexer query result
     */
    private int getWsdlCount(Collection<IndexerItem> items, int wsdlCount) {

        for (IndexerItem indexerItem : items) {

            String wsdlFileLoc =
                    indexerItem.get(WsdlIndexerAttributes.PATH.toString());
            if (wsdlFileLoc != null && !wsdlFileLoc.trim().isEmpty()) {

                IFile wsdlFile =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getFile(new Path(wsdlFileLoc));
                if (wsdlFile != null && wsdlFile.exists()) {

                    wsdlCount++;
                }
            }
        }
        return wsdlCount;
    }

}
