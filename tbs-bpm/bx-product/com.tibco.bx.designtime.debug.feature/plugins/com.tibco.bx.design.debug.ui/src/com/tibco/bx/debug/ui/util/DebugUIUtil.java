package com.tibco.bx.debug.ui.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.bx.debug.ui.DebugUIActivator;

public class DebugUIUtil {

	public static void openEmulationFile(IFile file , IWorkbenchPage page) throws PartInitException{
		IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		page.openEditor(new FileEditorInput(file), desc.getId());
	}

	public static Image getProcessImage(String modelType) {
		return DebugUIActivator.getRegisteredImage(modelType);
	}

}
