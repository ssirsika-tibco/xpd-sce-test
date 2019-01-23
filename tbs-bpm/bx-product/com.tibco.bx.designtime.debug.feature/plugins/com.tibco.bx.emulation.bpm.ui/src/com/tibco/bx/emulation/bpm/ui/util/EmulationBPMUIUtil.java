package com.tibco.bx.emulation.bpm.ui.util;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.emulation.bpm.ui.editor.ProcessNodeEditorInput;
import com.tibco.bx.emulation.bpm.ui.editor.TestpointsEditor;
import com.tibco.bx.emulation.model.ProcessNode;

public class EmulationBPMUIUtil {

	public static ProcessNode getProcessNode(){
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(editorPart instanceof TestpointsEditor){
			ProcessNodeEditorInput input = (ProcessNodeEditorInput)((TestpointsEditor)editorPart).getEditorInput();
			return input.getProcessNode();
		}
		return null;
	}
}
