/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.branding.wp.samples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.internal.wizards.datatransfer.TarEntry;
import org.eclipse.ui.internal.wizards.datatransfer.ZipLeveledStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.osgi.framework.Bundle;

import com.tibco.xpd.branding.BrandingPlugin;
import com.tibco.xpd.branding.internal.Messages;
import com.tibco.xpd.branding.wp.samples.SampleProjects.Project;

public class InstallSampleProjectOperation implements IRunnableWithProgress {

    private final Context context;

    private final IOverwriteQuery query;

    private ZipLeveledStructureProvider structureProvider;

    public InstallSampleProjectOperation(Context context) {
	this.context = context;
	this.query = new ImportOverwriteQuery();
	this.structureProvider = null;
    }

    public void run(IProgressMonitor monitor) throws InvocationTargetException,
	    InterruptedException {
	try {
	    IWorkspaceRunnable op = new IWorkspaceRunnable() {
		public void run(IProgressMonitor monitor) throws CoreException {
		    try {
			monitor.beginTask(
			        Messages.InstallSampleProjectOperation_CreatingProjects,
			        4);
			importProject(context, monitor);
		    } catch (InterruptedException e) {
			throw new OperationCanceledException();
		    } catch (InvocationTargetException e) {
			throwCoreException(e);
		    } finally {
			monitor.done();
		    }
		}
	    };
	    ResourcesPlugin.getWorkspace().run(op, monitor);
	} catch (CoreException e) {
	    throw new InvocationTargetException(e);
	} catch (OperationCanceledException e) {
	    throw e;
	} finally {
	    monitor.done();
	}

    }

    private void throwCoreException(InvocationTargetException e)
	    throws CoreException {
	IStatus status = new Status(IStatus.ERROR, BrandingPlugin.PLUGIN_ID,
	        e.getLocalizedMessage(), e);
	throw new CoreException(status);
    }

    /**
     * 
     * Imports sample projects using extension point information contained in
     * the Context object.
     * 
     * @param context
     *            contains extension point information
     * @param monitor
     * @throws CoreException
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    private void importProject(Context context, IProgressMonitor monitor)
	    throws CoreException, InvocationTargetException,
	    InterruptedException {

	Project sampleProject = context.getProject();
	String path = sampleProject.getArchiveFile();
	Bundle bundle = Platform.getBundle(sampleProject.getNamespaceId());
	ZipFile zipFile = getZipFileFromURI(path, bundle);
	importFilesFromZip(zipFile, new SubProgressMonitor(monitor, 1));
	cleanup(path, zipFile);

    }

    private void cleanup(String path, ZipFile zipFile) {
	if (path.startsWith("http:")) { //$NON-NLS-1$
	    String fileName = new Path(path).lastSegment();
	    IPath tempFilePath = BrandingPlugin.getDefault().getStateLocation()
		    .append(fileName);

	    File file = tempFilePath.toFile();

	    if (file != null) {
		try {
		    zipFile.close();
		    boolean delete = file.delete();
		} catch (IOException e) {
		    // Do Nothing
		}
	    }

	}

    }

    /**
     * Returns a ZipFile object representing the zipfile at the specified URI. <br/>
     * <br/>
     * The URI may be a remote URL or a relative path to a zipfile in a bundled
     * plugin. If the zip exists at a remote URL then the file is downloaded to
     * the workspace .metadata folder and the local copy is used to instantiate
     * the ZipFile object.
     * 
     * @param zipURI
     *            The path to the zipfile
     * @param bundle
     *            The plugin bundle that this sample comes from. Only used if
     *            the zipfile is a relative URI.
     * 
     * @return java.util.zip.ZipFile
     * @throws CoreException
     */
    private ZipFile getZipFileFromURI(String zipURI, Bundle bundle)
	    throws CoreException {
	try {

	    if (zipURI.startsWith("http:")) { //$NON-NLS-1$
		// A remote URL so copy it to a staging area (workspace
		// .metadata folder) before instantiating the ZipFile
		String fileName = new Path(zipURI).lastSegment();
		IPath tempFilePath = BrandingPlugin.getDefault()
		        .getStateLocation().append(fileName);

		// Substitute %20 for spaces etc
		String encodedURL = encodeURI(zipURI);
		URL url = new URL(encodedURL);
		InputStream from = url.openStream();

		String tempFile = tempFilePath.toPortableString();
		FileOutputStream to = new FileOutputStream(tempFile);

		try {
		    byte[] buffer = new byte[4096];
		    int bytesRead;

		    while ((bytesRead = from.read(buffer)) != -1)
			to.write(buffer, 0, bytesRead); // write
		} finally {
		    if (from != null) {
			from.close();
		    }
		    if (to != null) {
			to.close();
		    }
		}

		ZipFile zf = new ZipFile(tempFile);
		return zf;

	    } else {
		// Its a bundled zipfile
		URL starterURL = FileLocator.toFileURL(bundle.getEntry(zipURI));
		return new ZipFile(FileLocator.toFileURL(starterURL).getFile());
	    }

	} catch (IOException e) {
	    String message = String.format(
		    Messages.InstallSampleProjectOperation_FileAccessError,
		    zipURI);
	    Status status = new Status(IStatus.ERROR, BrandingPlugin.PLUGIN_ID,
		    IStatus.ERROR, message, e);
	    throw new CoreException(status);
	}
    }

    /**
     * 
     * Used to report that a Project already exists in the workspace and asks
     * whether overwrite s required.
     * 
     * @param String
     *            project name
     * @return integer code representing user choice
     */
    private int openDialogProjectExists(final String file) {
	final int[] result = { IDialogConstants.CANCEL_ID };

	Display.getDefault().syncExec(new Runnable() {
	    public void run() {
		String title = Messages.InstallSampleProjectOperation_SampleWizard;
		String msg = String
		        .format(Messages.InstallSampleProjectOperation_ProjectAlreadyExists,
		                file);
		String[] options = { IDialogConstants.YES_LABEL,
		        IDialogConstants.NO_LABEL,
		        // IDialogConstants.YES_TO_ALL_LABEL,
		        IDialogConstants.CANCEL_LABEL };
		Display current = Display.getDefault();
		Shell currentShell = current.getActiveShell();
		MessageDialog dialog = new MessageDialog(currentShell, title,
		        null, msg, MessageDialog.QUESTION, options, 0);
		result[0] = dialog.open();
	    }
	});
	return result[0];
    }

    /**
     * 
     * Reads the ZipFile for projects (i.e. can handle greater than one project)
     * and imports each one into the workspace via an ImportOperation.
     * 
     * @param srcZipFile
     * @param monitor
     * @throws InvocationTargetException
     * @throws InterruptedException
     */
    private void importFilesFromZip(ZipFile srcZipFile, IProgressMonitor monitor)
	    throws InvocationTargetException, InterruptedException {

	structureProvider = new ZipLeveledStructureProvider(srcZipFile);
	structureProvider.setStrip(1);
	List<ProjectRecord> lstProjs = new ArrayList<ProjectRecord>();

	collectProjectFilesFromProvider(lstProjs, structureProvider.getRoot(),
	        0, monitor);

	for (ProjectRecord pr : lstProjs) {
	    List<Object> fileSystemObjects = structureProvider
		    .getChildren(pr.parent);
	    IPath pt = new Path(pr.getProjectName());

	    IProject project = ResourcesPlugin.getWorkspace().getRoot()
		    .getProject(pr.getProjectName());

	    if (project.exists()) {

		int res = openDialogProjectExists(project.getName());

		switch (res) {
		case 0:
		    try {
			project.delete(true, monitor);
		    } catch (CoreException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

		    }
		    break;
		case 1:
		    continue;
		case 2:
		    throw new OperationCanceledException();
		}

	    }

	    ImportOperation op = new ImportOperation(pt,
		    structureProvider.getRoot(), structureProvider, query,
		    fileSystemObjects);

	    op.run(monitor);
	}

    }

    /**
     * 
     * @author rgreen
     */
    private class ImportOverwriteQuery implements IOverwriteQuery {
	public String queryOverwrite(String file) {
	    String[] returnCodes = { YES, NO, ALL, CANCEL };

	    int returnVal = openDialogProjectExists(file);

	    return returnVal < 0 ? CANCEL : returnCodes[returnVal];
	}

    }

    /**
     * 
     * Recursively searches for .project files starting at the supplied level in
     * the entry provider. When found a ProjectRecord is created and added to
     * the projects List.
     * 
     * @param projects
     *            An empty Collection that will be populated with ProjectRecords
     *            representing the found Projects
     * 
     * @param entry
     *            the entry that this importer uses as the root sentinel
     * @param level
     *            The starting directory level in the provider
     * @param monitor
     *            The monitor to report to
     * @return boolean <code>true</code> if the operation was completed.
     */
    private boolean collectProjectFilesFromProvider(
	    Collection<ProjectRecord> projects, Object entry, int level,
	    IProgressMonitor monitor) {

	if (monitor.isCanceled()) {
	    return false;
	}
	monitor.subTask(NLS.bind(
	        DataTransferMessages.WizardProjectsImportPage_CheckingMessage,
	        structureProvider.getLabel(entry)));
	List children = structureProvider.getChildren(entry);
	if (children == null) {
	    children = new ArrayList(1);
	}
	Iterator childrenEnum = children.iterator();
	while (childrenEnum.hasNext()) {
	    Object child = childrenEnum.next();
	    if (structureProvider.isFolder(child)) {
		collectProjectFilesFromProvider(projects, child, level + 1,
		        monitor);
	    }
	    String elementLabel = structureProvider.getLabel(child);
	    if (elementLabel.equals(IProjectDescription.DESCRIPTION_FILE_NAME)) {
		projects.add(new ProjectRecord(child, entry, level));
	    }
	}
	return true;
    }

    /**
     * Class declared public only for test suite.
     * 
     */
    public class ProjectRecord {
	File projectSystemFile;

	Object projectArchiveFile;

	String projectName;

	Object parent;

	int level;

	IProjectDescription description;

	/**
	 * Create a record for a project based on the info in the file.
	 * 
	 * @param file
	 */
	ProjectRecord(File file) {
	    projectSystemFile = file;
	    setProjectName();
	}

	/**
	 * @param file
	 *            The Object representing the .project file
	 * @param parent
	 *            The parent folder of the .project file
	 * @param level
	 *            The number of levels deep in the provider the file is
	 */
	ProjectRecord(Object file, Object parent, int level) {
	    this.projectArchiveFile = file;
	    this.parent = parent;
	    this.level = level;
	    setProjectName();
	}

	/**
	 * Set the name of the project based on the projectFile.
	 */
	private void setProjectName() {
	    try {
		if (projectArchiveFile != null) {
		    InputStream stream = structureProvider
			    .getContents(projectArchiveFile);

		    // If we can get a description pull the name from there
		    if (stream == null) {
			if (projectArchiveFile instanceof ZipEntry) {
			    IPath path = new Path(
				    ((ZipEntry) projectArchiveFile).getName());
			    projectName = path.segment(path.segmentCount() - 2);
			} else if (projectArchiveFile instanceof TarEntry) {
			    IPath path = new Path(
				    ((TarEntry) projectArchiveFile).getName());
			    projectName = path.segment(path.segmentCount() - 2);
			}
		    } else {
			description = IDEWorkbenchPlugin.getPluginWorkspace()
			        .loadProjectDescription(stream);
			stream.close();
			projectName = description.getName();
		    }

		}

		// If we don't have the project name try again
		if (projectName == null) {
		    IPath path = new Path(projectSystemFile.getPath());
		    // if the file is in the default location, use the directory
		    // name as the project name
		    if (isDefaultLocation(path)) {
			projectName = path.segment(path.segmentCount() - 2);
			description = IDEWorkbenchPlugin.getPluginWorkspace()
			        .newProjectDescription(projectName);
		    } else {
			description = IDEWorkbenchPlugin.getPluginWorkspace()
			        .loadProjectDescription(path);
			projectName = description.getName();
		    }

		}
	    } catch (CoreException e) {
		// no good couldn't get the name
	    } catch (IOException e) {
		// no good couldn't get the name
	    }
	}

	/**
	 * Returns whether the given project description file path is in the
	 * default location for a project
	 * 
	 * @param path
	 *            The path to examine
	 * @return Whether the given path is the default location for a project
	 */
	private boolean isDefaultLocation(IPath path) {
	    // The project description file must at least be within the project,
	    // which is within the workspace location
	    if (path.segmentCount() < 2)
		return false;
	    return path.removeLastSegments(2).toFile()
		    .equals(Platform.getLocation().toFile());
	}

	/**
	 * Get the name of the project
	 * 
	 * @return String
	 */
	public String getProjectName() {
	    return projectName;
	}

	/**
	 * Gets the label to be used when rendering this project record in the
	 * UI.
	 * 
	 * @return String the label
	 * @since 3.4
	 */
	public String getProjectLabel() {
	    if (description == null)
		return projectName;

	    String path = projectSystemFile == null ? structureProvider
		    .getLabel(parent) : projectSystemFile.getParent();

	    return NLS.bind(
		    DataTransferMessages.WizardProjectsImportPage_projectLabel,
		    projectName, path);
	}
    }

    /**
    
*     */
    private static String encodeURI(String uriString) {
	String allowed = ":/?=#-&_.!~*'()\"";
	StringBuilder uri = new StringBuilder(); // Encoded URL

	char[] chars = uriString.toCharArray();
	for (int i = 0; i < chars.length; i++) {
	    char c = chars[i];
	    if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
		    || (c >= 'A' && c <= 'Z') || allowed.indexOf(c) != -1) {
		uri.append(c);
	    } else if (c <= 0x007f) {
		uri.append("%");
		uri.append(Integer.toHexString((int) c));
	    } else {
		try {
		    uri.append(URLEncoder.encode(Character.toString(c), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		    // IGNORE
		}
	    }
	}
	return uri.toString();
    }

}
