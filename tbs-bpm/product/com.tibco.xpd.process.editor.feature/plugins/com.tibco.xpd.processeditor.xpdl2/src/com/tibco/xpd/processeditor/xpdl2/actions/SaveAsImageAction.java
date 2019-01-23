/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.dialogs.SaveAsDialog;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * @author wzurek
 * 
 */
public class SaveAsImageAction implements IEditorActionDelegate {

	private IEditorPart targetEditor;

	/*
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditor = targetEditor;
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (targetEditor instanceof AbstractProcessDiagramEditor) {
			SaveAsDialog fd = new SaveAsDialog(targetEditor.getSite()
					.getShell());

			ProcessEditorInput ei = (ProcessEditorInput) targetEditor
					.getEditorInput();
			IPath path = ei.getWorkingCopy().getEclipseResources().get(0).getFullPath();
			String name = path.lastSegment();
			// remove extension
			int extIndex = name.lastIndexOf("."); //$NON-NLS-1$
			if (extIndex > 0) {
				name = name.substring(0, extIndex);
			}

			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hhmm");  //$NON-NLS-1$

			//
	        // SID: Note that we used to use BMP for Windows and JPG for Linux
	        // (because JPG on windows wasn't very good prior to eclipse Europa and
	        // visa versa for Linux).
	        // However, there is a bug in
	        // WinBMPFileFormat(unloadDataNoCompression()) which causes infinite
	        // loop if image is greater than 8000 or so pixels wide) - this is too
	        // small a limit for processes so we will saves as JPEG from now on
	        // (europa jpeg output is as goiod as bmp on windows now anyway AND a
	        // LOT smaller!).
	        
			path = path.removeLastSegments(1);
			path = path.append(name + "_" + ei.getProcess().getName() + "_" //$NON-NLS-1$ //$NON-NLS-2$
					+ format.format(now) + ".jpg"); //$NON-NLS-1$
			fd.setOriginalFile(ResourcesPlugin.getWorkspace().getRoot()
					.getFile(path));

			fd.create();
			fd.setMessage(Messages.SaveAsImageAction_SaveAsImageDialog_message);
			fd.setTitle(Messages.SaveAsImageAction_SaveAsImageDialog_title);

			if (fd.open() == Window.OK) {
				path = fd.getResult();
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
						path);
				Image img = null;
				try {
					img = (Image) ((AbstractProcessDiagramEditor) targetEditor)
							.getAdapter(Image.class);
					ImageData id = img.getImageData();

					ImageLoader loader = new ImageLoader();
					loader.data = new ImageData[] { id };
					ByteArrayOutputStream buf = new ByteArrayOutputStream();
					loader.save(buf, SWT.IMAGE_JPEG);

					file.create(new ByteArrayInputStream(buf.toByteArray()),
							true, null);
				} catch (CoreException e) {
					// TODO
				} catch (Exception e) {
					e.printStackTrace();
					// TODO
				} finally {
					if (img != null)
						img.dispose();
				}
			}

		}
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}
}
