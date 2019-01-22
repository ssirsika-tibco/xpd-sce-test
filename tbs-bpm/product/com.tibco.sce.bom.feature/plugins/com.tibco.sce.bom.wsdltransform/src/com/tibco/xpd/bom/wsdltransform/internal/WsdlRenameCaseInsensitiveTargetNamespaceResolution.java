/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.wsdltransform.internal;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Resolution to lowercase all wsdls that are similar in name apart from their
 * case and maybe a trailing slash.
 * 
 * @author glewis
 */
public class WsdlRenameCaseInsensitiveTargetNamespaceResolution implements
        IResolution, IExecutableExtension {
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.validation.resolutions.IResolution#run(org.eclipse.core
     * .resources.IMarker)
     */
    public void run(IMarker marker) throws ResolutionException {
        if (marker != null && marker.exists()) {
            final IResource resource = marker.getResource();
            WorkspaceJob job =
                    new WorkspaceJob(
                            Messages.WsdlRenameCaseInsensitiveTargetNamespaceResolution_lowercase_targetnamespaces_shortdesc) {
                        @Override
                        public IStatus runInWorkspace(IProgressMonitor monitor)
                                throws CoreException {
                            if (resource != null) {
                                final WorkingCopy wc =
                                        WorkingCopyUtil
                                                .getWorkingCopy(resource);
                                final EObject root =
                                        wc != null ? wc.getRootElement() : null;
                                HashSet<IResource> resources =
                                        new HashSet<IResource>();

                                if (root instanceof Definition) {
                                    resources.add(resource);
                                    final Definition definition =
                                            (Definition) root;
                                    String targetNamespace =
                                            definition.getTargetNamespace();
                                    IContainer container = null;
                                    SpecialFolder tmpContainer =
                                            SpecialFolderUtil
                                                    .getRootSpecialFolder(resource);
                                    if (tmpContainer != null) {
                                        container = tmpContainer.getFolder();
                                    }
                                    if (container instanceof IContainer) {
                                        Collection<IResource> allDeepResourcesInContainer =
                                                SpecialFolderUtil
                                                        .getAllDeepResourcesInContainer(container,
                                                                com.tibco.xpd.wsdl.Activator.WSDL_FILE_EXTENSION,
                                                                true);
                                        for (IResource tmpResource : allDeepResourcesInContainer) {
                                            Definition tmpDefinition = null;
                                            if (tmpResource instanceof IFile
                                                    && !tmpResource
                                                            .getFullPath()
                                                            .equals(resource
                                                                    .getFullPath())) {
                                                WorkingCopy workingCopy =
                                                        WorkingCopyUtil
                                                                .getWorkingCopy(tmpResource);
                                                if (workingCopy != null
                                                        && workingCopy
                                                                .getRootElement() instanceof Definition) {
                                                    tmpDefinition =
                                                            (Definition) workingCopy
                                                                    .getRootElement();
                                                }
                                            }
                                            if (tmpDefinition != null) {
                                                String originalTargetNamespace =
                                                        getStrippedLastSlashTargetNamespace(targetNamespace);
                                                String tmpTargetNamespace =
                                                        getStrippedLastSlashTargetNamespace(tmpDefinition
                                                                .getTargetNamespace());
                                                if (originalTargetNamespace
                                                        .equalsIgnoreCase(tmpTargetNamespace)) { //$NON-NLS-1$
                                                    resources.add(tmpResource);
                                                }
                                            }
                                        }
                                    }

                                    EditingDomain editingDomain =
                                            wc.getEditingDomain();

                                    Command cmd =
                                            new RecordingCommand(
                                                    (TransactionalEditingDomain) editingDomain) {
                                                @Override
                                                protected void doExecute() {
                                                    String wsdlTargetNamespace =
                                                            definition
                                                                    .getTargetNamespace();
                                                    String renamedTargetNamespace =
                                                            getStrippedLastSlashTargetNamespace(wsdlTargetNamespace
                                                                    .toLowerCase());
                                                    definition
                                                            .setTargetNamespace(renamedTargetNamespace);
                                                    Collection<String> values =
                                                            definition
                                                                    .getNamespaces()
                                                                    .keySet();
                                                    for (String prefix : values) {
                                                        Object namespace =
                                                                definition
                                                                        .getNamespaces()
                                                                        .get(prefix);
                                                        if (namespace
                                                                .equals(wsdlTargetNamespace)) {
                                                            definition
                                                                    .getNamespaces()
                                                                    .put(prefix,
                                                                            renamedTargetNamespace);
                                                        }
                                                    }

                                                    if (definition.getETypes() != null) {
                                                        List<?> schemas =
                                                                definition
                                                                        .getETypes()
                                                                        .getSchemas();
                                                        for (Object obj : schemas) {
                                                            XSDSchema schema =
                                                                    (XSDSchema) obj;
                                                            if (schema != null
                                                                    && schema
                                                                            .getTargetNamespace()
                                                                            .equals(wsdlTargetNamespace)) {
                                                                schema
                                                                        .setTargetNamespace(renamedTargetNamespace);
                                                            }
                                                        }
                                                    }

                                                    definition
                                                            .updateElement(true);
                                                    try {
                                                        definition
                                                                .eResource()
                                                                .save(UMLDiagramEditorUtil
                                                                        .getSaveOptions());
                                                        wc.reLoad();
                                                    } catch (IOException e) {
                                                        throw new RuntimeException(
                                                                e.getMessage());
                                                    }
                                                }
                                            };

                                    /*
                                     * Execute the command if possible.
                                     */
                                    if (cmd != null && cmd.canExecute()) {
                                        if (editingDomain.getCommandStack() != null) {
                                            /* Execute the command */
                                            editingDomain.getCommandStack()
                                                    .execute(cmd);
                                        }
                                    }

                                }
                            }
                            return Status.OK_STATUS;
                        }
                    };

            job.setUser(true);
            job.schedule();
        }
    }

    private String getStrippedLastSlashTargetNamespace(String targetNamespace) {
        String tmpTargetNamespace = targetNamespace;
        int indexOfSlash = tmpTargetNamespace.lastIndexOf("/"); //$NON-NLS-1$
        if (indexOfSlash == tmpTargetNamespace.length() - 1) {
            tmpTargetNamespace = tmpTargetNamespace.substring(0, indexOfSlash);
        }
        return tmpTargetNamespace;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
    }

}
