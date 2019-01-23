/*
 * Copyright (c) TIBCO Software Inc. 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.bom.wsdltransform.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceInitializer;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.wsdltransform.builder.WsdlToBomBuilder;
import com.tibco.xpd.bom.xsdtransform.utils.Bom2XsdUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.imports.IPostImportProjectTask;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Post import project task to remove all Generated BOMs.
 * 
 * @author njpatel
 * @since 3.5.3
 */
public class RemoveGenBOMPostImportProjectTask implements
        IPostImportProjectTask {

    public RemoveGenBOMPostImportProjectTask() {
    }

    /**
     * @see com.tibco.xpd.resources.ui.imports.IPostImportProjectTask#runPostImportTask(org.eclipse.core.resources.IProject,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    public void runPostImportTask(final IProject project,
            IProgressMonitor monitor) throws CoreException {

        try {
            monitor.beginTask("", //$NON-NLS-1$
                    1);
            monitor.setTaskName(Messages.RemoveGenBOMPostImportProjectTask_checkingGenBOM_monitor_shortdesc);

            project.refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());

            IFolder genBomFolder =
                    project.getFolder(WsdlToBomBuilder.GENERATED_BOM_FOLDER);

            if (genBomFolder != null) {

                String kind =
                        SpecialFolderUtil.getSpecialFolderKind(genBomFolder);

                if (BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND.equals(kind)) {
                    final boolean isRegenerateBomOnImport =
                            BomGenPreferenceInitializer
                                    .getPrefForRegenerateBomOnImport();

                    IResourceVisitor delVisitor = new IResourceVisitor() {

                        public boolean visit(IResource resource)
                                throws CoreException {
                            if (resource instanceof IFile) {
                                if (isRegenerateBomOnImport) {
                                    // XpdResourcesPlugin
                                    // .getDefault()
                                    // .getLogger()
                                    // .info("RegenOnImport Deleting: "
                                    // + resource.getName());
                                    resource.delete(true, null);
                                } else {

                                    if (isBomVersionAndChecksumUptoDate(resource,
                                            project)) {
                                        // XpdResourcesPlugin
                                        // .getDefault()
                                        // .getLogger()
                                        // .info("KEEPING: "
                                        // + resource.getName());
                                        /*
                                         * update the time stamp to avoid
                                         * regeneration due to this file being
                                         * older than the corresponding wsdl/xsd
                                         * files (as this file may be created
                                         * before the source wsdl/xsd files on
                                         * import)
                                         */
                                        resource.setLocalTimeStamp(System
                                                .currentTimeMillis());
                                    } else {
                                        resource.delete(true, null);

                                        String msg =
                                                String.format(Messages.RemoveGenBOMPostImportProjectTask_BomWillBeRegeneratedOnImport,
                                                        resource.getName());
                                        Activator
                                                .getDefault()
                                                .getLogger()
                                                .log(new Status(IStatus.INFO,
                                                        Activator.PLUGIN_ID,
                                                        msg));
                                    }
                                }
                            }
                            return true;
                        }
                    };

                    genBomFolder.accept(delVisitor);

                    monitor.worked(1);
                }
            }

        } finally {
            monitor.done();
        }
    }

    /**
     * @param project
     * @param res
     * @return
     */
    private boolean isBomVersionAndChecksumUptoDate(IResource bomResource,
            IProject project) {

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomResource);

        if (wc instanceof BOMWorkingCopy) {

            BOMWorkingCopy bomWorkingCopy = (BOMWorkingCopy) wc;
            EObject m = bomWorkingCopy.getRootElement();
            if (m instanceof Model) {
                Model model = (Model) m;

                String versionStr = BOMUtils.getBOMVersion(model, null);
                int version = 0;
                try {
                    version = Integer.parseInt(versionStr);
                } catch (Exception e) {
                    String msg =
                            String.format(Messages.RemoveGenBOMPostImportProjectTask_invalidBomVersionFound,
                                    bomResource.getName());
                    Activator
                            .getDefault()
                            .getLogger()
                            .log(new Status(IStatus.INFO, Activator.PLUGIN_ID,
                                    msg));
                }

                if (version < Integer.parseInt(BOMResourcesPlugin.BOM_VERSION)) {
                    return false;
                }

                String checksum = BOMUtils.getBOMCheckSum(model);
                if (checksum != null && !checksum.isEmpty()) {
                    String srcChecksum =
                            Bom2XsdUtil
                                    .calculateChecksumUsingGeneratedBomSources(bomResource);
                    if (checksum.equals(srcChecksum)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
