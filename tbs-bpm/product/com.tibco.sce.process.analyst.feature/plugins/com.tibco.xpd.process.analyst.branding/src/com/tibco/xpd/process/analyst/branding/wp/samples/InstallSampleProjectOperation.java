/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.analyst.branding.wp.samples;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
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
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.ui.wizards.datatransfer.ZipFileStructureProvider;
import org.osgi.framework.Bundle;

import com.tibco.xpd.process.analyst.branding.Messages;
import com.tibco.xpd.process.analyst.branding.wp.samples.SampleProjects.Project;
import com.tibco.xpd.resources.logger.LogStatusHelper;

public class InstallSampleProjectOperation implements IRunnableWithProgress {

	private final Context context;
	private final IOverwriteQuery query;

	private class ImportOverwriteQuery implements IOverwriteQuery {
		public String queryOverwrite(String file) {
			String[] returnCodes = {YES, NO, ALL, CANCEL};

			int returnVal = openDialog(file);


			return returnVal < 0 ? CANCEL : returnCodes[returnVal];
		}
		private int openDialog(final String file) {
			final int[] result = {IDialogConstants.CANCEL_ID};

			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					String title = Messages.InstallSampleProjectOperation_title;
					String msg = NLS.bind(Messages.InstallSampleProjectOperation_ProjectAlreadyExistsConfirmOverwrite_message, file);
					String[] options = {IDialogConstants.YES_LABEL,
							IDialogConstants.NO_LABEL,
							//IDialogConstants.YES_TO_ALL_LABEL,
							IDialogConstants.CANCEL_LABEL};
					Display current = Display.getDefault();
					Shell currentShell = current.getActiveShell();
					MessageDialog dialog = new MessageDialog(currentShell, title,
							null, msg, MessageDialog.QUESTION, options, 0);
					result[0] = dialog.open();
				}
			});
			return result[0];
		}
	}

	public InstallSampleProjectOperation(Context context) {
		this.context = context;
		this.query=new ImportOverwriteQuery();
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			IWorkspaceRunnable op = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					try {
						monitor
						.beginTask(
								Messages.InstallSampleProjectOperation_shortdesc, 4);
						importProject(context, monitor);
					} catch (InterruptedException e) {
						throw new OperationCanceledException();
					} catch (InvocationTargetException e) {
						throwCoreException(e);
					} catch (Throwable e){
						e.printStackTrace();
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

		private void throwCoreException(InvocationTargetException e) throws CoreException {
			Throwable t = e.getCause();
			IStatus status= LogStatusHelper.createError(SampleProjectsViewer.PLUGIN_ID, t, e.getMessage());
			throw new CoreException(status);
		}

	/**
	 * @param name
	 * @param config
	 * @param monitor
	 * @return
	 * @throws CoreException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 *
	 * @see org.eclipse.pde.internal.ui.samples.SampleOperation
	 */
	private void importProject(Context context,
			IProgressMonitor monitor) throws CoreException,
			InvocationTargetException, InterruptedException {
		Project sampleProject = context.getProject();
		String path = sampleProject.getArchiveFile();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		String projectName = sampleProject.getProjectName();
		IProject project = root.getProject(projectName);
		boolean skip = false;
        if (project.exists()) {
//            if (!yesToAll) {
                String returnId = query.queryOverwrite(project.getFullPath()
                    .toString());
                /*if (returnId.equals(IOverwriteQuery.ALL)) {
                    yesToAll = true;
                    skip = false;
                } else*/ if (returnId.equals(IOverwriteQuery.YES)) {
                    skip = false;
                }  else if (returnId.equals(IOverwriteQuery.NO)) {
                    skip = true;
                } else if (returnId.equals(IOverwriteQuery.CANCEL)) {
                    skip = true;
                }
//            }
            if (!skip) {
                project.delete(true, true, new SubProgressMonitor(monitor, 1));
                project = root.getProject(projectName);
            } else
                monitor.worked(1);
        }
        if (skip) {
            monitor.worked(1);
            return;
        }

		project.create(new SubProgressMonitor(monitor, 1));
		project.open(new NullProgressMonitor());
		Bundle bundle = Platform.getBundle(sampleProject.getNamespaceId());
		ZipFile zipFile = getZipFileFromPluginDir(path, bundle);
		importFilesFromZip(zipFile, project.getFullPath(),
				new SubProgressMonitor(monitor, 1));
//		return createSampleManifest(project, config, new SubProgressMonitor(
//				monitor, 1));
	}

	private ZipFile getZipFileFromPluginDir(String pluginRelativePath,
			Bundle bundle) throws CoreException {
		try {
			URL starterURL = FileLocator.toFileURL(bundle.getEntry(pluginRelativePath));
			return new ZipFile(FileLocator.toFileURL(starterURL).getFile());
		} catch (IOException e) {
			String message = pluginRelativePath + ": " + e.getMessage(); //$NON-NLS-1$
			Status status = new Status(IStatus.ERROR, SampleProjectsViewer.PLUGIN_ID,
					IStatus.ERROR, message, e);
			throw new CoreException(status);
		}
	}

	private void importFilesFromZip(ZipFile srcZipFile, IPath destPath,
			IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		ZipFileStructureProvider structureProvider = new ZipFileStructureProvider(
				srcZipFile);
		ImportOperation op = new ImportOperation(destPath, structureProvider
				.getRoot(), structureProvider, query);
		op.run(monitor);
	}

//	private IFile createSampleManifest(IProject project,
//			Context context, IProgressMonitor monitor)
//			throws CoreException {
//		IFile file = project.getFile(SAMPLE_PROPERTIES);
//		if (!file.exists()) {
//			try {
//				ByteArrayOutputStream out = new ByteArrayOutputStream();
//				Properties properties = new Properties();
//				createSampleManifestContent(context, properties); //$NON-NLS-1$
//				properties.store(out, ""); //$NON-NLS-1$
//				out.flush();
//				String contents = out.toString();
//				out.close();
//				ByteArrayInputStream stream = new ByteArrayInputStream(contents
//						.getBytes("UTF8")); //$NON-NLS-1$
//				file.create(stream, true, monitor);
//				stream.close();
//			} catch (UnsupportedEncodingException e) {
//			} catch (IOException e) {
//			}
//		}
//		return file;
//	}

//	private void createSampleManifestContent(Context context,
//			Properties properties) {
//		Project project = context.getProject();
//		properties.setProperty("id", project.getId()); //$NON-NLS-1$ //$NON-NLS-2$
//		properties.setProperty("name", project.getLabel()); //$NON-NLS-1$ //$NON-NLS-2$
//		properties.setProperty("projectName", context.get("projectName")); //$NON-NLS-1$
//
////		writeProperty(properties, "launcher", sample.getAttribute("launcher")); //$NON-NLS-1$ //$NON-NLS-2$
////		IConfigurationElement desc[] = sample.getChildren("description"); //$NON-NLS-1$
////		if (desc.length == 1) {
////			writeProperty(properties, "helpHref", desc[0] //$NON-NLS-1$
////					.getAttribute("helpHref")); //$NON-NLS-1$
////			writeProperty(properties, "description", desc[0].getValue()); //$NON-NLS-1$
////		}
//	}


}
