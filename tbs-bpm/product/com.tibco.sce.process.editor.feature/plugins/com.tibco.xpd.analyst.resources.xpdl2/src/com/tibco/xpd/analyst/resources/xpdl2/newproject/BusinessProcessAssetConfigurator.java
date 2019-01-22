/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.newproject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.properties.wizards.BasicNewBpmResourceWizard;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.resources.ProcessPackageAddedNotificationCommand;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * <code>IAssetConfigurator</code> implementation for the Business Process
 * asset.
 * 
 * @author njpatel
 * 
 */
public class BusinessProcessAssetConfigurator extends
        SpecialFolderAssetConfigurator {

    BusinessProcessAssetConfig configurationObject;

    private IFolder folder;

    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {
        super.configure(project, configuration);

        folder = (IFolder) project.findMember(getSpecialFolderName());
        if (configuration instanceof BusinessProcessAssetConfig) {
            configurationObject = (BusinessProcessAssetConfig) configuration;
            if (configurationObject.getXpdl2Package() != null) {
                try {
                    Package xpdl2Package =
                            configurationObject.getXpdl2Package();

                    com.tibco.xpd.xpdl2.Process process = null;

                    if (configurationObject.getProcess() != null) {
                        process = configurationObject.getProcess();
                        xpdl2Package.getProcesses().add(process);
                    }

                    String packageFileName =
                            configurationObject.getPackageFileName();

                    /*
                     * XPD-182: Avoid clashes of default xpdl file name with
                     * default dflow file name,
                     */
                    packageFileName =
                            fixProcessPackageName(project, packageFileName);

                    URI uri =
                            URI.createPlatformResourceURI(packageFileName,
                                    false);

                    try {
                        IFile file = folder.getFile(packageFileName);

                        ResourceSet rset = new ResourceSetImpl();
                        Resource resource = rset.createResource(uri);
                        resource.getContents().add(xpdl2Package);
                        ByteArrayOutputStream os = new ByteArrayOutputStream();
                        resource.save(os, null);
                        file.create(new ByteArrayInputStream(os.toByteArray()),
                                true,
                                new NullProgressMonitor());

                        WorkingCopy wc =
                                XpdResourcesPlugin.getDefault()
                                        .getWorkingCopy(file);
                        Package pck = (Package) wc.getRootElement();

                        /*
                         * make a dummy edit on the file so that pre-commit
                         * listeners at least receive some kind of notification
                         * of a created package
                         */
                        if (pck != null) {
                            ProcessPackageAddedNotificationCommand packageAdded =
                                    new ProcessPackageAddedNotificationCommand(
                                            wc.getEditingDomain(), pck);
                            wc.getEditingDomain().getCommandStack()
                                    .execute(packageAdded);
                            try {
                                wc.save();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (pck.getProcesses().isEmpty()) {
                            selectAndReveal(pck);
                        } else {
                            EObject procToOpenName =
                                    EMFSearchUtil
                                            .findInList(pck.getProcesses(),
                                                    Xpdl2Package.eINSTANCE
                                                            .getNamedElement_Name(),
                                                    configurationObject
                                                            .getProcessToOpen());
                            Process p = null;
                            if (procToOpenName instanceof Process) {
                                p = (Process) procToOpenName;

                            } else if (p == null) {
                                p = pck.getProcesses().get(0);
                            }
                            IConfigurationElement facConfig =
                                    XpdResourcesUIActivator
                                            .getEditorFactoryConfigFor(p);
                            if (facConfig != null) {
                                String editorID =
                                        facConfig.getAttribute("editorID"); //$NON-NLS-1$
                                IWorkbenchPage page =
                                        PlatformUI.getWorkbench()
                                                .getActiveWorkbenchWindow()
                                                .getActivePage();
                                try {
                                    EditorInputFactory f =
                                            (EditorInputFactory) facConfig
                                                    .createExecutableExtension("factory"); //$NON-NLS-1$
                                    IEditorInput input = f.getEditorInputFor(p);
                                    page.openEditor(input, editorID);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            selectAndReveal(p);
                        }
                    } catch (Exception ex) {
                        System.err
                                .println("BusinessProcessAssetConfigurator:: " //$NON-NLS-1$
                                        + ex.getMessage());
                    }
                } catch (Exception e) {
                    System.err
                            .println("BusinessProcesAssetConfigurator:: Error in your code:" + e.getMessage()); //$NON-NLS-1$
                }
            }

        }

    }

    protected void selectAndReveal(Object objectToSelect) {
        BasicNewBpmResourceWizard.selectAndReveal(objectToSelect, PlatformUI
                .getWorkbench().getActiveWorkbenchWindow());
    }

    /**
     * XPD-182: If user has not changed the dflow file name or teh xpdl from
     * it's default (i.e. <ProjectName>.dflow then make sure we prevent name
     * duplicating XPDL file that might be created on same New project Wizard -
     * because that wil instantly add a problem markers saying the dflow cannot
     * have same name as an xpdl file (bnecause they would both generate the
     * same WSDL file name and that can't happen).
     * 
     * @param project
     * @param packageFileName
     * @return The decision flow package file name (adjusted to not clash with
     *         xpdl if it was left as default by user).
     * @throws CoreException
     */
    protected String fixProcessPackageName(IProject project,
            String packageFileName) throws CoreException {
        String nameWithoutExt =
                getFileNameWithoutExtension(packageFileName,
                        Xpdl2ResourcesConsts.XPDL_EXTENSION);

        if (nameWithoutExt.equals(project.getName())) {

            /*
             * Name is still it's default (i.e. named after project) - check if
             * there is any DFLOW file with same name
             */
            String dflowFileName =
                    nameWithoutExt
                            + "." //$NON-NLS-1$
                            + DecisionFlowUtil.DEFAULT_DECISION_FLOW_PACKAGE_FILENAME_EXT;

            List<IResource> allDflows =
                    SpecialFolderUtil
                            .getAllDeepResourcesInSpecialFolderOfKind(project,
                                    DecisionFlowUtil.DECISIONS_SPECIAL_FOLDER_KIND,
                                    DecisionFlowUtil.DEFAULT_DECISION_FLOW_PACKAGE_FILENAME_EXT,
                                    false);

            boolean clashesWithDflow = false;
            for (IResource dflow : allDflows) {
                if (dflowFileName.equals(dflow.getName())) {
                    clashesWithDflow = true;
                    break;
                }
            }

            if (clashesWithDflow) {
                packageFileName =
                        nameWithoutExt
                                + "_" //$NON-NLS-1$
                                + Messages.Xpdl2ResourcesConsts_DefaultProcessName_shortdesc
                                + "." //$NON-NLS-1$
                                + Xpdl2ResourcesConsts.XPDL_EXTENSION;
            }

        }
        return packageFileName;
    }

    /**
     * @param fileName
     * @param extension
     * @return The file name without the extension (of it has one).
     */
    private String getFileNameWithoutExtension(String fileName, String extension) {

        String dotExt = "." + extension; //$NON-NLS-1$

        if (fileName.endsWith(dotExt)) {
            fileName =
                    fileName.substring(0, fileName.length() - dotExt.length());
        }

        return fileName;
    }

}
