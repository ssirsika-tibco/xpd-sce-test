/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules.wsdl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDNamedComponent;

import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlElementType;
import com.tibco.xpd.util.WsdlIndexerUtil.WsdlIndexerAttributes;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.bpmn.developer.internal.Messages;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Message validation rule: Messages with the same name and TNS must have
 * identical definition. This rule is run at the {@link Definition} level and
 * will validated all messages within the wsdl.
 * 
 * @author njpatel
 */
public class MessageValidationRule implements IValidationRule {

    private static final String MESSAGE_ISSUE_ID =
            "message_with_same_name_tns_should_be_identical"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return Definition.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Definition) {

            // Key = TNS Qname, Value = Message
            Map<?, ?> messages = ((Definition) obj).getMessages();

            IProject hostProject =
                    WorkingCopyUtil.getProjectFor((Definition) obj);
            if (hostProject != null && messages != null && !messages.isEmpty()) {
                Set<IProject> allProjects = new HashSet<IProject>();
                allProjects.add(hostProject);
                allProjects
                        .addAll(getReferencedAndReferencingProjects(hostProject));

                for (Entry<?, ?> entry : messages.entrySet()) {
                    if (entry.getKey() instanceof QName
                            && entry.getValue() instanceof Message) {
                        Message msg = (Message) entry.getValue();
                        // Find any matching messages from the indexer
                        List<Message> matchingMessages =
                                getMessagesWithSameNameAndTNS(msg,
                                        (QName) entry.getKey(),
                                        allProjects);

                        /*
                         * Compare all matching messages to ensure they are
                         * identical, if not then create an issue
                         */
                        for (Message otherMsg : matchingMessages) {
                            if (!match(msg, otherMsg)) {
                                createIssue(scope, msg, matchingMessages);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Create an issue for the message error.
     * 
     * @param scope
     *            validation scope
     * @param msg
     *            message to create error on
     * @param matchingMessages
     *            other messages that should match the message (linked resource
     *            will be created for validating these resources).
     */
    private void createIssue(IValidationScope scope, Message msg,
            List<Message> matchingMessages) {
        IFile hostFile = WorkingCopyUtil.getFile(msg);
        if (hostFile != null) {
            Set<IFile> linkedFiles = new HashSet<IFile>();
            for (Message message : matchingMessages) {
                IFile file = WorkingCopyUtil.getFile(message);
                if (file != null && !file.equals(hostFile)) {
                    linkedFiles.add(file);
                }
            }

            StringBuffer linkedRes = new StringBuffer();
            for (IFile file : linkedFiles) {
                if (linkedRes.length() > 0) {
                    linkedRes.append(","); //$NON-NLS-1$
                }
                linkedRes.append(URI.createPlatformResourceURI(file
                        .getFullPath().toString(), true));
            }

            List<String> messages = Collections.emptyList();

            scope.createIssue(MESSAGE_ISSUE_ID,
                    String.format(Messages.MessageValidationRule_messages_error_location_shortdesc,
                            msg.getQName().getLocalPart(),
                            msg.getQName().getNamespaceURI()),
                    EcoreUtil.getURI(msg).toString(),
                    messages,
                    Collections
                            .singletonMap(ValidationActivator.LINKED_RESOURCE,
                                    linkedRes.toString()));
        }
    }

    /**
     * Checks if the two messages match (assumes that name and TNS already
     * match). This checks the part to make sure they match.
     * 
     * @param msg
     * @param msg1
     * @return
     */
    private boolean match(Message msg, Message msg1) {
        EList<?> parts = msg.getEParts();
        EList<?> parts1 = msg1.getEParts();

        if (parts.size() == parts1.size()) {
            /*
             * Order of parts have to be the same
             */
            for (int idx = 0; idx < parts.size(); idx++) {
                Object obj = parts.get(idx);
                Object obj1 = parts1.get(idx);

                if (obj instanceof Part && obj1 instanceof Part) {
                    if (!match((Part) obj, (Part) obj1)) {
                        // Parts don't match
                        return false;
                    }
                }
            }
        } else {
            // Number of Parts don't match
            return false;
        }
        return true;
    }

    /**
     * Check if the two parts match. This will check the name and type (by
     * name).
     * 
     * @param part
     * @param part1
     * @return
     */
    private boolean match(Part part, Part part1) {
        if (part.getName().equals(part1.getName())) {
            if (part.getTypeDefinition() != null
                    && part1.getTypeDefinition() != null) {

                if (!match(part.getTypeDefinition(), part1.getTypeDefinition())) {
                    // Type decl does not match of these parts
                    return false;
                }
            } else if (part.getElementDeclaration() != null
                    && part1.getElementDeclaration() != null) {

                if (!match(part.getElementDeclaration(),
                        part1.getElementDeclaration())) {
                    // Element decl does not match of these parts
                    return false;
                }
            } else if (part1.getElementDeclaration() != null
                    || part1.getTypeDefinition() != null) {
                /*
                 * Obviously 'part' does not have an element or type defined so
                 * if 'part1' has either then they do not match.
                 */
                return false;
            }
        } else {
            // Part names don't match
            return false;
        }
        return true;
    }

    /**
     * Compare the name and TNS of the given named components.
     * 
     * @param comp
     * @param comp1
     * @return
     */
    private boolean match(XSDNamedComponent comp, XSDNamedComponent comp1) {
        return comp.getName().equals(comp1.getName())
                && comp.getTargetNamespace().equals(comp1.getTargetNamespace());
    }

    /**
     * Get Messages from the workspace that match the given msg (match the name
     * and TNS).
     * 
     * @param msg
     *            Message
     * @param tns
     *            target namespace
     * @param relatedProjects
     *            referenced and referencing projects
     * @return
     */
    private List<Message> getMessagesWithSameNameAndTNS(Message msg, QName tns,
            Set<IProject> relatedProjects) {
        List<Message> messages = new ArrayList<Message>();

        Collection<IndexerItem> indexedItems =
                WsdlIndexerUtil
                        .getIndexedItems(msg.getQName().getLocalPart(),
                                WsdlElementType.MESSAGE,
                                null,
                                Collections
                                        .singletonMap(WsdlIndexerAttributes.TARGET_NAMESPACE,
                                                tns.getNamespaceURI()),
                                true,
                                true);

        for (IndexerItem item : indexedItems) {
            IProject project = getProject(item);
            if (project != null && relatedProjects.contains(project)) {
                EObject resolve = WsdlIndexerUtil.resolve(item);
                if (resolve instanceof Message && resolve != msg) {
                    messages.add((Message) resolve);
                }
            }
        }

        return messages;
    }

    /**
     * Get the project that contains the resource the indexer item represents.
     * 
     * @param item
     * @return
     */
    private IProject getProject(IndexerItem item) {
        String projectName = WsdlIndexerUtil.getProjectName(item);
        if (projectName != null) {
            IProject project =
                    ResourcesPlugin.getWorkspace().getRoot()
                            .getProject(projectName);
            if (project != null && project.isAccessible()) {
                return project;
            }
        }
        return null;
    }

    /**
     * Get all the downstream and upstream referenced projects of the given
     * project.
     * 
     * @param project
     * @return
     */
    private Set<IProject> getReferencedAndReferencingProjects(IProject project) {
        Set<IProject> projects = new HashSet<IProject>();

        try {
            projects.addAll(ProjectUtil2
                    .getReferencedProjectsHierarchy(project, true));
            projects.addAll(ProjectUtil2
                    .getReferencingProjectsHierarchy(project, projects));
        } catch (CyclicDependencyException e) {
            // Do nothing
        }

        return projects;
    }

}