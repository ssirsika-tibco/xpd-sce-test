/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.inputs;

import org.eclipse.ui.IEditorInput;

import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * Editor Input factory for process form editor.
 * 
 * @author rsomayaj
 */
public class ProcessInterfaceEditorInputFactory implements EditorInputFactory {

	/**
	 * It create EditorInput for XPDL's Process model element.
	 */
	public IEditorInput getEditorInputFor(Object obj) {
		if (!(obj instanceof ProcessInterface)) {
			return null;
		}
		ProcessInterface processInterface = (ProcessInterface) obj;
		WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(processInterface);

		// check working copy cache
		ProcessInterfaceEditorInput ei = (ProcessInterfaceEditorInput) wc
				.getAttributes().get(processInterface);
		if (ei == null) {
			// create new editor input
			ei = new ProcessInterfaceEditorInput(wc, processInterface);
			wc.getAttributes().put(processInterface, ei);
		}
		return ei;
	}

}
