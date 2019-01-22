package com.tibco.bx.emulation.ui.editor;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class TestpointsEditorLinkHelper implements ILinkHelper{

	@Override
	public void activateEditor(IWorkbenchPage page, IStructuredSelection selection) {
		if ((selection != null) && (!selection.isEmpty())) {
			Object firstElement = selection.getFirstElement();

			NamedElement selectObject = null;
			if (firstElement instanceof NamedElement)
				selectObject = (NamedElement) firstElement;
			if (selectObject == null)
				return;
			ProcessNode processNode = null;
			if (selectObject instanceof ProcessNode) {
				processNode = (ProcessNode) selectObject;
			} else {
				processNode = (ProcessNode) selectObject.eContainer();
			}

			if (processNode == null) {
				return;
			}
			WorkingCopy workingcopy = WorkingCopyUtil.getWorkingCopyFor(processNode);
			if(workingcopy == null)
				return;
			IDiagramEditorInput editorInput = (IDiagramEditorInput) workingcopy
					.getAttributes().get(processNode);
			
			if (editorInput != null) {
				workingcopy.getAttributes().put(processNode, editorInput);
				IEditorPart editor = (IEditorPart)page.findEditor(editorInput);
				if(editor != null){
					page.bringToTop(editor);
					EmulationUIUtil.goToEmulationElement(editor, selectObject);
				}
			}	

		}

	}

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		if (anInput instanceof IDiagramEditorInput) {
		      return new StructuredSelection(((IDiagramEditorInput)anInput).getProcessNode());
		}
		return StructuredSelection.EMPTY;
	}

}
