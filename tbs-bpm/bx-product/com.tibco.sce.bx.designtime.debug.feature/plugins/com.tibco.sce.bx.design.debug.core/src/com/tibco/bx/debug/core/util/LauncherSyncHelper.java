package com.tibco.bx.debug.core.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.debug.core.DebugCoreActivator;

public class LauncherSyncHelper {

	public static void initiateProject() {
		try {
			// check if ".com.tibco.bx.launch" exist or not
			createBxLaunchProject(null, null);

			// synchronize "BXLauncher.launch" file
			synchronizeBxLaunchProject();

		} catch (Exception e) {
			DebugCoreActivator.log(e);
		}
	}

	public static IProject createBxLaunchProject(IRunnableContext runnableContext, IPath locationPath) throws InvocationTargetException,
			InterruptedException, CoreException {
		IProject bxLaunchProject = getBxLaunchProject();
		if (bxLaunchProject != null && !bxLaunchProject.exists()) {
			CreateBxLaunchProjectOperation operation = new CreateBxLaunchProjectOperation(locationPath);

			if (runnableContext == null) {
				ModalContext.run(operation, false, new NullProgressMonitor(), Display.getDefault());
			} else {
				runnableContext.run(false, true, operation);
			}
		}
		return bxLaunchProject;
	}

	public static IProject getBxLaunchProject() throws CoreException {
		IProject bxLaunchProject = ResourcesPlugin.getWorkspace().getRoot().getProject(LauncherConstants.BX_LAUNCH_PROJECT_NAME);
		bxLaunchProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		return bxLaunchProject;
	}

	public static void synchronizeBxLaunchProject() throws URISyntaxException, IOException, CoreException {
		IProject bxLaunchProject = getBxLaunchProject();
		if (bxLaunchProject != null && bxLaunchProject.exists() && bxLaunchProject.isAccessible()) {
			// synchronize "BPM-RAD-Runtime.launch" file
			synchronizeBPMRadLaunchFile();
		}
	}

	private static void synchronizeBPMRadLaunchFile() throws URISyntaxException, IOException, CoreException {
		if (!hasBPMRadLaunchFile()) {
			IFile bxLaunchFile = getBPMRadLaunchFile();
			if (!bxLaunchFile.exists()) {
				createBPMRadLaunchFile();
			}
		}
	}

	public static IFile createBPMRadLaunchFile() throws URISyntaxException, IOException, CoreException {
		IFile bxLaunchFile = getBPMRadLaunchFile();
		deleteIFile(bxLaunchFile, true);
		String contents = getRemoteBPMRadLaunchFileContents();
		writeFile(bxLaunchFile, contents);
		return bxLaunchFile;
	}

	public static IFile getBPMRadLaunchFile() throws CoreException {
		IProject bxLaunchProject = getBxLaunchProject();
		return bxLaunchProject.getFile(LauncherConstants.BPM_RAD_RUNTIME_PATH);
	}

	private static String getRemoteBPMRadLaunchFileContents() {
		String contents = null;
		String errorMessage = "Can't load launch file: '" + LauncherConstants.REMOTE_BPM_RAD_RUNTIME_FILE_URL + "'"; //$NON-NLS-1$ //$NON-NLS-2$
		InputStream inputStream = null;
		try {
			URL url = new URL(LauncherConstants.REMOTE_BPM_RAD_RUNTIME_FILE_URL);
			inputStream = url.openConnection().getInputStream();
			contents = getInputStreamContents(inputStream);

		} catch (MalformedURLException e) {
			DebugCoreActivator.log(e, errorMessage);
		} catch (IOException e) {
			DebugCoreActivator.log(e, errorMessage);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (contents == null || contents.trim().length() == 0) {
			contents = ""; //$NON-NLS-1$
		}

		return contents;
	}

	private static String getInputStreamContents(InputStream inputStream) throws IOException {
		StringBuffer content = new StringBuffer();
		BufferedInputStream bufferedInput = null;
		try {
			bufferedInput = new BufferedInputStream(inputStream);

			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = bufferedInput.read(buffer)) != -1) {
				String chunk = new String(buffer, 0, bytesRead);
				content.append(chunk);
			}

		} finally {
			if (bufferedInput != null) {
				try {
					bufferedInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return content.toString();
	}

	private static void writeFile(IFile file, String contents) throws UnsupportedEncodingException, CoreException {
		ByteArrayInputStream stream = null;
		try {
			stream = new ByteArrayInputStream(contents.getBytes("UTF8")); //$NON-NLS-1$
			file.create(stream, false, null);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void deleteIFile(IFile file, boolean forceDelete) {
		if (file.exists()) {
			try {
				file.delete(forceDelete, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean hasBPMRadLaunchFile() throws CoreException {
		IProject bxLaunchProject = getBxLaunchProject();
		IResource[] members = bxLaunchProject.members();
		for (IResource res : members) {
			if (res instanceof IFile && LauncherConstants.BPM_RAD_RUNTIME_LAUNCH_EXT.equals(res.getFileExtension())) {
				IFile file = (IFile) res;
				ILaunchConfiguration lc = getLaunchManager().getLaunchConfiguration(file);
				if (getBPMRadLaunchConfigurationType().equals(lc.getType())) {
					return true;
				}
			}
		}
		return false;
	}

	private static ILaunchConfigurationType getBPMRadLaunchConfigurationType() {
		return getLaunchManager().getLaunchConfigurationType(LauncherConstants.BPM_RAD_LAUNCH_TYPE);
	}

	private static ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}

}
