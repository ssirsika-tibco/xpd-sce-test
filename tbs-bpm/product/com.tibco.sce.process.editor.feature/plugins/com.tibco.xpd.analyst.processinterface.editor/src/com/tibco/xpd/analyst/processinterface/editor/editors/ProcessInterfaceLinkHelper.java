/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.editors;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

import com.tibco.xpd.analyst.processinterface.editor.inputs.ProcessInterfaceEditorInput;
import com.tibco.xpd.analyst.processinterface.editor.inputs.ProcessInterfaceEditorInputFactory;
import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * Link editor helper for the Project Explorer to link the selected Process
 * Interface to it's editor part and vice versa
 * 
 * @author rsomayaj
 * 
 */
public class ProcessInterfaceLinkHelper implements ILinkHelper {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.navigator.ILinkHelper#activateEditor(org.eclipse.ui.IWorkbenchPage,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void activateEditor(IWorkbenchPage aPage,
			IStructuredSelection aSelection) {

		// Check if the first item selected is a Process
		if (aSelection != null && !aSelection.isEmpty()) {
			if (aSelection.getFirstElement() != null && aSelection.getFirstElement() instanceof ProcessInterface) {
				ProcessInterfaceEditorInputFactory fact = new ProcessInterfaceEditorInputFactory();

				if (fact != null) {
					IEditorInput processInterfaceEditorInput = fact
							.getEditorInputFor(aSelection.getFirstElement());

					if (processInterfaceEditorInput != null) {
						IEditorPart editor = aPage
								.findEditor(processInterfaceEditorInput);

						// If the editor is open then bring to top
						if (editor != null) {
							aPage.bringToTop(editor);
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.navigator.ILinkHelper#findSelection(org.eclipse.ui.IEditorInput)
	 */
	public IStructuredSelection findSelection(IEditorInput anInput) {

		// Check if the editor selected is the Process editor
		if (anInput != null && anInput instanceof ProcessInterfaceEditorInput) {
			ProcessInterfaceEditorInput editorInput = (ProcessInterfaceEditorInput) anInput;
			ProcessInterface processInterface = editorInput
					.getProcessInterface();

			// Select the corresponding process of the selected editor
			if (processInterface != null) {
				return new StructuredSelection(processInterface);
			}
		}

		return StructuredSelection.EMPTY;
	}

}
