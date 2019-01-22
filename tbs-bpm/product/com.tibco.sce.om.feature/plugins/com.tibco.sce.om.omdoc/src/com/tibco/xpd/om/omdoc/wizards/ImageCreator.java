/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.omdoc.wizards;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.notation.Diagram;

import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.omdoc.Activator;
import com.tibco.xpd.om.omdoc.internal.Messages;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * <p>
 * Creates organization diagram images for documentation.
 * 
 * @author glewis
 * 
 */
public class ImageCreator {

    private Map<String, IPath> modelImagesMap;
    
    public final static String IMAGE_DIRECTORY = "images";//$NON-NLS-1$
    
    /**
     * @param parent
     */
    public ImageCreator() {
        modelImagesMap = new HashMap<String, IPath>();
    }

    /**
     * 
     */
    public void dispose() {
    }

    /**
     * @param inputFile
     * @param outputFile
     * @throws CoreException
     */
    public void create(IFile inputFile, File outputFile) throws CoreException {
        final List<Diagram> diagrams = getDiagrams(inputFile);
        if (diagrams != null && diagrams.size() > 0) {
            File imageDir = createFolder(outputFile.getParentFile(), IMAGE_DIRECTORY);

            Iterator<Diagram> diagramIter = diagrams.iterator();
            while (diagramIter.hasNext()) {
                final Diagram diagram = diagramIter.next();
                String id = "noID"; //$NON-NLS-1$
                if (diagram.getElement() instanceof ModelElement) {
                    ModelElement modelElement = (ModelElement) diagram
                            .getElement();
                    id = modelElement.getId();
                    final IPath path = new Path(imageDir.getAbsolutePath())
                            .append(id + ".png"); //$NON-NLS-1$
                    addModelImageToMap(id, path);
                    XpdResourcesPlugin.getStandardDisplay().syncExec(new Runnable() {
                        public void run() {
                            try {
                                new CopyToImageUtil().copyToImage(diagram,
                                        path,
                                        ImageFileFormat.PNG,
                                        new NullProgressMonitor(),
                                        null);
                            } catch (CoreException e) {
                                Activator.getDefault().getLogger().error(e);
                            }
                        }

                    });
                }
            }

        }

    }

    /**
     * @param inputFile
     * @return
     */
    private List<Diagram> getDiagrams(IFile inputFile) {
        List<Diagram> diagram = null;
        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(
                inputFile);
        if (wc instanceof OMWorkingCopy) {
            OMWorkingCopy omWc = (OMWorkingCopy) wc;
            List<Diagram> diagrams = omWc.getDiagrams();
            if (diagrams.size() > 0) {
                return diagrams;

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
                            .format(
                                    Messages.ResourceCopier_FailedToCreateFolderErr_message,
                                    folder.toString()));
                }
            } else if (!folder.isDirectory()) {
                // A file with the same name already exists so raise exception
                throw generateCoreException(String
                        .format(
                                Messages.ResourceCopier_unableToCreateFolderErr_message,
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
