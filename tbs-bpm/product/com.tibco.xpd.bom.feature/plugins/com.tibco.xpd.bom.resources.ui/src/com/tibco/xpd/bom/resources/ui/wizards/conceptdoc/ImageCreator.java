/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.wizards.conceptdoc;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.importexport.exportwizard.ExportSubTask;

/**
 * 
 * <p>
 * Creates concept diagram image for documentation. <i>Created: 3 May 2007</i>
 * 
 * @author scrossle
 * 
 */
class ImageCreator implements ExportSubTask {

    Wizard parent;

    private Map<String, IPath> modelImagesMap;

    /**
     * SCF-420 : Extracted images directory name as a static constant as it is
     * referenced from various places and it remains the same everywhere.
     */
    public final static String IMAGE_DIRECTORY = "images";//$NON-NLS-1$

    /**
     * @param parent
     */
    public ImageCreator(Wizard parent) {
        this.parent = parent;
        modelImagesMap = new HashMap<String, IPath>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.importexport.exportwizard.ExportSubTask#dispose()
     */
    @Override
    public void dispose() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.importexport.exportwizard.ExportSubTask#perform(org.
     * eclipse.core.runtime.SubProgressMonitor,
     * org.eclipse.core.resources.IFile, java.io.File)
     */
    @Override
    public void perform(final SubProgressMonitor monitor, IFile inputFile,
            File outputFile) throws CoreException {
        final Diagram diagram = getDiagram(inputFile);
        if (diagram != null) {
            String id = "noID"; //$NON-NLS-1$
            if (diagram.eResource() instanceof XMIResource) {
                XMIResource res = (XMIResource) diagram.eResource();
                id = res.getID(diagram);
            }

            File imageDir =
                    createFolder(outputFile.getParentFile(), IMAGE_DIRECTORY);
            final IPath path =
                    new Path(imageDir.getAbsolutePath()).append(id + ".jpg"); //$NON-NLS-1$
            final Display display;
            if (parent == null) {
                display = XpdResourcesPlugin.getStandardDisplay();
            } else {
                display = parent.getShell().getDisplay();
            }
            addModelImageToMap(id, path);
            display.syncExec(new Runnable() {

                @Override
                public void run() {
                    try {
                        new CopyToImageUtil().copyToImage(diagram,
                                path,
                                ImageFileFormat.JPG,
                                monitor,
                                null);
                    } catch (CoreException e) {
                        Activator.getDefault().getLogger().error(e);
                    }
                }

            });

        }

    }

    /**
     * @param inputFile
     * @return
     */
    private Diagram getDiagram(IFile inputFile) {
        Diagram diagram = null;
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(inputFile);
        if (wc instanceof BOMWorkingCopy) {
            BOMWorkingCopy bomWc = (BOMWorkingCopy) wc;
            List<Diagram> diagrams = bomWc.getDiagrams();
            if (diagrams.size() > 0) {
                diagram = diagrams.get(0);

            }
        }
        return diagram;
    }

    /**
     * Create a folder with the given name under the parent location.
     * 
     * @param parent
     *            Location to create the new folder
     * @param folderName
     *            The name of the folder to create
     * @return <code>File</code> if the folder was created successfully,
     *         <b>null</b> otherwise.
     * @throws CoreException
     *             will be thrown if the folder fails to create, or a file with
     *             the same name already exists at the given location.
     */
    private File createFolder(File parent, String folderName)
            throws CoreException {
        File folder = null;

        if (parent != null && folderName != null) {
            folder = new File(parent, folderName);

            // If folder doesn't exist then create it
            if (!folder.exists()) {
                // If failed to create folder then raise exception
                if (!folder.mkdirs()) {
                    throw generateCoreException(String
                            .format(Messages.ResourceCopier_FailedToCreateFolderErr_message,
                                    folder.toString()));
                }
            } else if (!folder.isDirectory()) {
                // A file with the same name already exists so raise exception
                throw generateCoreException(String
                        .format(Messages.ResourceCopier_unableToCreateFolderErr_message,
                                folder.toString()));
            }
        }

        return folder;
    }

    /**
     * Wrap the given error message into a <code>CoreException</code>
     * 
     * @param errMsg
     * @return
     */
    private CoreException generateCoreException(String errMsg) {
        return new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                IStatus.OK, errMsg, null));
    }

    public Map<String, IPath> getModelImagesMap() {
        return modelImagesMap;
    }

    private void addModelImageToMap(String modelId, IPath imagePath) {
        if (modelId != null && imagePath != null) {
            if (modelImagesMap == null) {
                modelImagesMap = new HashMap<String, IPath>();
            }
            modelImagesMap.put(modelId, imagePath);
        }
    }

}
