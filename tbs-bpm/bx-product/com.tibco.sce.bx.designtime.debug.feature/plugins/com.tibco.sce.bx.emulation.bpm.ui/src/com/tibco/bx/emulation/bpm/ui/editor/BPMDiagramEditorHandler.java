package com.tibco.bx.emulation.bpm.ui.editor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.editor.IDiagramEditorHandler;
import com.tibco.bx.emulation.ui.editor.IDiagramEditorInput;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

public class BPMDiagramEditorHandler implements IDiagramEditorHandler{

	@Override
	public IDiagramEditorInput createEmulationDiagramEditorInput(ProcessNode processNode) {
		WorkingCopy workingcopy = WorkingCopyUtil.getWorkingCopyFor(processNode);
		ProcessNodeEditorInput editorInput = (ProcessNodeEditorInput) workingcopy.getAttributes().get(processNode);
		if (editorInput == null) {
			editorInput = new ProcessNodeEditorInput(workingcopy, processNode);
			workingcopy.getAttributes().put(processNode, editorInput);
		}
		return editorInput;
	}

	@Override
	public IEditorInput createOriginalEditorInput(String processId, String modelType) {
		com.tibco.xpd.xpdl2.Process process = Xpdl2WorkingCopyImpl.locateProcess(processId);
		Assert.isNotNull(process);
		WorkingCopy workingcopy = WorkingCopyUtil.getWorkingCopyFor(process);
		IEditorInput editorInput = (IEditorInput) workingcopy.getAttributes().get(process);
		if (editorInput == null) {
			editorInput = new ProcessEditorInput(workingcopy, process);
			workingcopy.getAttributes().put(process, editorInput);
		}
		return editorInput;
	}
	
	@Override
	public void goToEObject(IEditorPart editorPart, EObject object) {
		if(editorPart instanceof IGotoEObject){
			((IGotoEObject)editorPart).gotoEObject(true, object);
		}
	}

}
