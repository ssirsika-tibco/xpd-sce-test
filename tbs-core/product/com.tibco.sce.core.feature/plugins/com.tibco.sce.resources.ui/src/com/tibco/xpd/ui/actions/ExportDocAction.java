/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.documentation.DocumentViewerContributionHelper;
import com.tibco.xpd.ui.documentation.DocGenType;
import com.tibco.xpd.ui.documentation.DocGenUtil;
import com.tibco.xpd.ui.documentation.IDocGen;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.documentation.index.IndexBIRTSourceGenerator;
import com.tibco.xpd.ui.documentation.index.IndexDocGen;

/**
 * Actions that performs the export to Documentation format.
 * 
 * In order to export correctly this action needs to have a correct outputPath
 * and some selected resources.
 * 
 * @author mtorres
 */
public class ExportDocAction {
    /**
     * The output path to export the documentation
     */
    private IPath outputPath;

    /**
     * The output path to export the documentation temporal files
     */
    private IPath outputTempFolderPath;

    private Map<String, IPath> templatesMap = null;

    private List<IResource> selectedResources = null;

    /**
     * The constructor
     */
    public ExportDocAction() {
    }

    /**
     * Runs the export
     * 
     * @param monitor
     */
    public void run(IProgressMonitor monitor) throws InterruptedException {
        List<IDocGenInfo> allDocGenInfos = new ArrayList<IDocGenInfo>();
        Map<String, List<IResource>> selectedExtensionMap =
                new HashMap<String, List<IResource>>();
        String projectName = null;
        if (selectedResources != null && !selectedResources.isEmpty()) {
            for (IResource resource : selectedResources) {
                if (resource instanceof IFile) {
                    IFile selectedFile = (IFile) resource;
                    String fileExtension = selectedFile.getFileExtension();
                    List<IResource> resourcesForExtension =
                            selectedExtensionMap.get(fileExtension);
                    if (resourcesForExtension == null) {
                        resourcesForExtension = new ArrayList<IResource>();
                        selectedExtensionMap.put(fileExtension,
                                resourcesForExtension);
                    }
                    resourcesForExtension.add(selectedFile);
                } else {
                    return;
                }
            }
        } else {
            return;
        }
        Set<String> extensions = selectedExtensionMap.keySet();
        if (monitor != null) {
            int totalWork = calculateTotalWork(selectedExtensionMap);
            monitor.beginTask(Messages.ExportDocAction_DocumentationExport,
                    totalWork);
        }
        int workCount = 1;
        for (String extension : extensions) {
            Set<IDocGen> documentGenerator =
                    DocumentViewerContributionHelper
                            .getDocumentGenerator(extension);
            if (documentGenerator != null && !documentGenerator.isEmpty()) {
                for (IDocGen docGen : documentGenerator) {
                    if (docGen != null) {
                        List<IResource> resourcesForExtension =
                                selectedExtensionMap.get(extension);
                        if (resourcesForExtension != null
                                && !resourcesForExtension.isEmpty()) {
                            if (monitor != null) {
                                monitor.subTask(String
                                        .format(Messages.ExportDocAction_GeneratingDocumentation,
                                                extension));
                            }
                            if (projectName == null) {
                                IResource firstResource =
                                        resourcesForExtension.iterator().next();
                                if (firstResource != null
                                        && firstResource.getProject() != null) {
                                    projectName =
                                            firstResource.getProject()
                                                    .getName();
                                }
                            }
                            List<IDocGenInfo> docGenInfos =
                                    docGen.generate(resourcesForExtension,
                                            getOutputPath());
                            if (docGenInfos != null && !docGenInfos.isEmpty()) {
                                allDocGenInfos.addAll(docGenInfos);
                            }
                            if (monitor.isCanceled()) {
                                throw new OperationCanceledException();
                            }
                            if (monitor != null) {
                                monitor.worked(workCount);
                            }
                        }
                    }
                    workCount++;
                }
            }
        }
        IFile generateSource =
                IndexBIRTSourceGenerator.getInstance()
                        .generateSource(projectName,
                                allDocGenInfos,
                                getOutputTempFolderPath());
        if (generateSource != null) {
            new IndexDocGen()
                    .generate(Collections
                            .singletonList((IResource) generateSource),
                            getOutputPath());
        } else {
            XpdResourcesUIActivator
                    .getDefault()
                    .getLogger()
                    .error(Messages.ExportDocAction_ErrorGeneratingSourceFileForIndex);
            throw new InterruptedException(
                    Messages.ExportDocAction_ErrorGeneratingSourceFileForIndex);
        }
        IContainer[] workspaceOutContainers =
                ResourcesPlugin.getWorkspace().getRoot()
                        .findContainersForLocation(getOutputPath());
        for (IContainer c : workspaceOutContainers) {
            try {
                if (c.isAccessible()) {
                    // Refresh should be instantaneous so no need for progress
                    // monitor.
                    c.refreshLocal(IResource.DEPTH_INFINITE,
                            new NullProgressMonitor());
                }
            } catch (CoreException e) {
                XpdResourcesUIActivator.getDefault().getLog()
                        .log(e.getStatus());
            }
        }
        if (monitor != null) {
            monitor.done();
        }
    }

    /**
     * Calculates the total work needed for the progress monitor
     * 
     * @param selectedExtensionMap
     * @return the totalWork
     */
    private int calculateTotalWork(
            Map<String, List<IResource>> selectedExtensionMap) {
        int totalWork = 0;
        Set<String> extensions = selectedExtensionMap.keySet();
        for (String extension : extensions) {
            Set<IDocGen> documentGenerator =
                    DocumentViewerContributionHelper
                            .getDocumentGenerator(extension);
            if (documentGenerator != null && !documentGenerator.isEmpty()) {
                totalWork += documentGenerator.size();
            }
        }
        return totalWork;
    }

    /**
     * Gets the output path. If the output path is not set it will return
     * default path which is the same as the source file but with and 'xml'
     * extension.
     * 
     * @return The output path. It will be the workspace root relative path.
     */
    public IPath getOutputPath() {
        if (outputPath == null) {
            return getDefaultOutputPath();
        }
        return outputPath;
    }

    /**
     * Gets the output path. If the output path is not set it will return
     * default path which is the same as the source file but with and 'xml'
     * extension.
     * 
     * @return The output path. It will be the workspace root relative path.
     */
    public IPath getOutputTempFolderPath() {
        if (outputTempFolderPath == null) {
            if (getSelectedResources() != null) {
                for (IResource selectedResource : getSelectedResources()) {
                    if (selectedResource != null
                            && selectedResource.getProject() != null) {
                        outputTempFolderPath =
                                selectedResource
                                        .getProject()
                                        .getFullPath()
                                        .append(DocGenUtil.DOCUMENTATION_TEMP_FOLDER_NAME);
                        break;
                    }
                }
            }
        }
        return outputTempFolderPath;
    }

    /**
     * Sets the output path.
     * 
     * @param outputPath
     *            the outputPath to set. This must be workspace root relative
     *            path.
     */
    public void setOutputPath(IPath outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * Returns the default output path
     * 
     * @return
     */
    private IPath getDefaultOutputPath() {
        if (selectedResources != null && !selectedResources.isEmpty()) {
            for (IResource selectedResource : selectedResources) {
                if (!(selectedResource instanceof IFile)) {
                    return null;
                }
            }
        } else {
            return null;
        }
        final IFile modelFile = (IFile) selectedResources.iterator().next();
        return modelFile.getFullPath().removeFileExtension()
                .addFileExtension(DocGenType.HTML_OUTPUTTYPE.toLowerCase());
    }

    public Map<String, IPath> getTemplatesMap() {
        return templatesMap;
    }

    public void setTemplatesMap(Map<String, IPath> templatesMap) {
        this.templatesMap = templatesMap;
    }

    public List<IResource> getSelectedResources() {
        return selectedResources;
    }

    public void setSelectedResources(List<IResource> selectedResources) {
        this.selectedResources = selectedResources;
    }

}
