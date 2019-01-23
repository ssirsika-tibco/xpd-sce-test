/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.documentation.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.xml.sax.SAXException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.documentation.IDocGenInfo;
import com.tibco.xpd.ui.importexport.exportwizard.OutputFile;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformProgressMonitorSupport;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider2;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;

/**
 * 
 * @author mtorres
 */
public abstract class AbstractXSLTDocGen extends AbstractBaseDocGen implements
        ITransformationStylesheetsProvider2, ITransformProgressMonitorSupport,
        ITransformationCharsetEncodingProvider {

    private static final String IO_ENCODING = "UTF-8"; //$NON-NLS-1$

    // Overwrite selection
    private OverwriteStatus overwriteDestFile;

    /**
     * Performs the transformation
     * 
     * @param monitor
     * @param source
     * @param docGenInfo
     * @throws CoreException
     * @throws InterruptedException
     */
    protected void performTransformation(IProgressMonitor monitor,
            IResource source, IDocGenInfo docGenInfo) throws CoreException,
            InterruptedException {
        InputStream inStream = null;
        OutputFile outputFile = null;
        try {
            ImportExportTransformer transformer = getImportExportTransformer();
            if (monitor.isCanceled()) {
                // User selected to cancel
                return;
            }

            if (source instanceof IFile) {
                IFile sourceFile = (IFile) source;
                overwriteDestFile = OverwriteStatus.ALL_FILES;
                /*
                 * If the overwrite file message was shown and NO selected then
                 * the value needs to be reset back to default, otherwise no
                 * further files will be processed
                 */
                if (overwriteDestFile == OverwriteStatus.NO)
                    overwriteDestFile = OverwriteStatus.FILE;

                // Update progress monitor
                monitor.subTask(sourceFile.getName());

                // Get the output file
                outputFile = getOutputFile(sourceFile, docGenInfo);

                if (outputFile != null) {
                    // If file exists and the user hasn't selected to
                    // overwrite all files then show dialog
                    if (outputFile.exists()
                            && overwriteDestFile != OverwriteStatus.ALL_FILES) {
                        final OutputFile outFile = outputFile;
                        final Display display =
                                XpdResourcesPlugin.getStandardDisplay();
                        // Ask user if the existing file can be
                        // overwritten
                        display.syncExec(new Runnable() {
                            @Override
                            public void run() {
                                overwriteDestFile =
                                        overwriteFileMessageDialog(outFile);
                            }
                        });
                    }

                    // Check whether we can process the file
                    if (overwriteDestFile.canOverwrite()) {

                        // // Process any subtasks
                        // if (subTasks != null) {
                        // for (ExportSubTask task : subTasks) {
                        // // Each subtask is ONE tick of main task.
                        // task.perform(new SubProgressMonitor(
                        // monitor, 1), xpdlFile, outputFile
                        // .getFile());
                        // }
                        // }

                        // // Sub-tasks may have reset the subtask name.
                        // monitor.subTask(xpdlFile.getName());

                        // Transform
                        transformer
                                .performTransformation(new SubProgressMonitor(
                                        monitor, 1),
                                        sourceFile.getContents(),
                                        outputFile.getOutputStream());

                        // Close the output file
                        outputFile.close();

                        // Close the streams
                        try {
                            if (inStream != null) {
                                inStream.close();
                                inStream = null;
                            }
                        } catch (IOException e) {
                            // Do nothing, closing streams.
                        }

                        monitor.worked(1);
                    }

                    // If the overwrite file dialog was shown and the
                    // user cancelled then throw interrupted exception
                    // to stop the export process
                    if (overwriteDestFile == OverwriteStatus.CANCEL) {
                        throw new OperationCanceledException();
                    }
                }
            }
            transformer = null;

        } catch (Exception e) {
            if (isUserCancelledException(e)) {
                throw new OperationCanceledException();
            }
            // If this is a CoreException or an InterruptedException then just
            // throw them, otherwise wrap exception in CoreException.
            else if (e instanceof CoreException) {
                throw (CoreException) e;
            } else if (e instanceof InterruptedException) {
                throw (InterruptedException) e;
            } else {
                if (e.getCause() instanceof CoreException) {
                    throw (CoreException) e.getCause();
                } else {
                    throw new CoreException(new Status(IStatus.ERROR,
                            XpdResourcesUIActivator.ID, IStatus.OK,
                            e.getLocalizedMessage(), e));
                }
            }

        } finally {
            // Close all streams
            try {
                if (inStream != null)
                    inStream.close();
            } catch (IOException e) {
                // Do nothing, closing stream
            }

            /*
             * If the export destination is the workspace then re-sync the
             * workspace with the file system if sub-tasks were executed. This
             * is needed as the sub tasks may have added more resources to the
             * export destination.
             */
            if (outputFile != null) {
                IContainer container = outputFile.getParentIContainer();

                if (container != null
                        && !container.isSynchronized(IResource.DEPTH_INFINITE)) {
                    // Synch the container with the file system
                    container.refreshLocal(IResource.DEPTH_INFINITE, monitor);
                }
            }

            if (monitor != null)
                monitor.done();
        }
    }

    /**
     * The subclass can override this if they wish to do something like caching
     * the ImportExportTransformer (effectively keeping and precompiling it).
     * 
     * @return The ImportExportTransformer
     * @throws SAXException
     * @throws IOException
     * @throws TransformerConfigurationException
     */
    protected ImportExportTransformer getImportExportTransformer()
            throws TransformerConfigurationException, IOException, SAXException {
        return new ImportExportTransformer(this);
    }

    /**
     * Returns the output file
     * 
     * @param inputFile
     * @param docGenInfo
     * @return
     * @throws CoreException
     */
    protected abstract OutputFile getOutputFile(IFile inputFile,
            IDocGenInfo docGenInfo) throws CoreException;

    @Override
    protected void disposeEngine(IDocGenInfo docGenInfo) {
        // TODO Auto-generated method stub
    }

    /**
     * Show the overwrite file message dialog
     * 
     * @param outputFile
     * @return <code>{@link OverwriteStatus}</code> value of the selection made
     *         in the dialog
     */
    protected OverwriteStatus overwriteFileMessageDialog(OutputFile outputFile) {

        String msg =
                String.format("Destination file %1$s already exists in:\n%2$s\n\nDo you wish to overwrite it?",//$NON-NLS-1$
                        outputFile.getName(),
                        outputFile.getPath().toString());
        OverwriteFileMessageDialog dialog =
                new OverwriteFileMessageDialog(XpdResourcesPlugin
                        .getStandardDisplay().getActiveShell(), "Files Exists",//$NON-NLS-1$
                        msg);

        return dialog.getOverwriteStatus(dialog.open());
    }

    /**
     * SAX parser / transformer has started wrapping up
     * InterruptedException/OperationCanceledException so we need to unravel
     * these in order to find whether the given exception is a user presed
     * cancel operation.
     * 
     * @param e
     * @return true if the given exception is a user cancelled operation
     *         exception of some kind (or a wrapped one).
     */
    protected boolean isUserCancelledException(Exception e) {
        Set<Exception> alreadyProcessed = new HashSet<Exception>();

        while (e != null) {
            // Make sure we cannot go round in circles.
            if (alreadyProcessed.contains(e)) {
                break;
            }

            alreadyProcessed.add(e);

            if (e instanceof OperationCanceledException
                    || e instanceof InterruptedException) {
                return true;
            }

            if (e instanceof SAXException) {
                e = ((SAXException) e).getException();
            } else if (e instanceof TransformerException
                    && ((TransformerException) e).getException() instanceof Exception) {
                e = (Exception) ((TransformerException) e).getException();
            } else {
                e = null;
            }
        }

        return false;
    }

    /**
     * Create project export folders in the workspace
     * 
     * @param folder
     * @throws CoreException
     */
    protected void createFolders(IFolder folder) throws CoreException {
        // Refresh the folder to align it with the file system
        if (!folder.isSynchronized(IResource.DEPTH_ONE)) {
            folder.refreshLocal(IResource.DEPTH_ONE, null);
        }

        if (!folder.exists()) {
            IContainer parent = folder.getParent();

            if (parent instanceof IFolder) {
                IFolder parentFolder = (IFolder) parent;
                // Create parent folder
                createFolders(parentFolder);
            }
            // Create current folder
            folder.create(true, true, null);
        }
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider#getCharsetEncodingForStream(com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider.EncodingForStream)
     * 
     * @param encodingForStream
     * @return
     */
    @Override
    public String getCharsetEncodingForStream(
            EncodingForStream encodingForStream) {
        return IO_ENCODING;
    }

}
