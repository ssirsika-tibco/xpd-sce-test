package com.tibco.bds.designtime.generator;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.bds.designtime.generator.internal.Messages;
import com.tibco.xpd.bom.gen.biz.GenerationException;

public class MetaFileHelper {

    private static final String PLUGIN_XML = "plugin.xml";

    private static final String TIBCO_PLUGIN_XML = "tibco_plugin.xml";


    /**
     * Rename Plugin.xml to tibco_plugin.xml
     * 
     * @param project
     * @throws GenerationException
     */
    public void postProcessProjectMetaFiles(IProject project)
            throws GenerationException {
        IResource plugin = project.findMember(PLUGIN_XML);

        IPath fullPath = project.getFullPath();
        IPath appendedPath =
                fullPath.addTrailingSeparator().append(TIBCO_PLUGIN_XML);

        IFile pluginFile = (IFile) plugin;

        try {
            // Find and remove the tibco plugin file
            IResource member = project.findMember(TIBCO_PLUGIN_XML);
            if (member != null) {
                member.delete(true, new NullProgressMonitor());
            }
            // Check for the case where it is still on the file system
            // but the eclipse project had previously been removed with
            // out keeping the file system in sync
            File rawPluginFile = appendedPath.toFile();
            if ((rawPluginFile != null) && rawPluginFile.exists()) {
                rawPluginFile.delete();
            }

            // Now copy the file, we do not do a move as this under the covers
            // just performs a copy and then a delete. We split these up
            // as there have been cases when it was previously a move that
            // the copy part works, but the delete part fails. By splitting
            // these into different operations we can handle the deletion error
            // in a cleaner manner
            pluginFile.copy(appendedPath, true, null);

        } catch (CoreException e) {
            throw new GenerationException(Messages
                    .getString("MetaFileHelper_unableToMovePluginXML"), e);
        }

        // Now perform the delete of the old file
        try {
            pluginFile.delete(true, null);
        } catch (CoreException e) {
            boolean deleteFailed = true;
            IStatus exceptionStatus = e.getStatus();

            try {
                // Check to see if the failure was related to the delete
                if ((exceptionStatus != null)
                        && (exceptionStatus.getCode() == IResourceStatus.FAILED_DELETE_LOCAL)) {
                    // Now try and force delete it again
                    int deleteAttempts = 10;

                    while ((deleteAttempts > 0) && (deleteFailed != false)) {
                        // Refresh the project so that the changes are picked up
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);

                        try {
                            // Wait 20 milliseconds to see if it enables
                            // us to delete it, with something else
                            // releasing the lock on the file
                            Thread.sleep(20);
                        } catch (InterruptedException e1) {
                        }

                        // Delete worked, so break out
                        if (pluginFile.getLocation().toFile().delete()) {
                            deleteFailed = false;
                            // Refresh the project so that the changes are
                            // picked up
                            project
                                    .refreshLocal(IResource.DEPTH_INFINITE,
                                            null);
                            break;
                        }
                        --deleteAttempts;
                    }
                }

                if (deleteFailed != false) {
                    // Re-throw the original exception
                    throw e;
                }
            } catch (CoreException e1) {
                throw new GenerationException(Messages
                        .getString("MetaFileHelper_unableToMovePluginXML"), e);
            }
        }
    }
}
