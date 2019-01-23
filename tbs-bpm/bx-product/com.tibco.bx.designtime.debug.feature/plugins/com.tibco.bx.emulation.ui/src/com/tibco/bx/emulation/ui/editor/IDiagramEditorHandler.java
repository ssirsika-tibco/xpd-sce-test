package com.tibco.bx.emulation.ui.editor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import com.tibco.bx.emulation.model.ProcessNode;

public interface IDiagramEditorHandler {

	IDiagramEditorInput createEmulationDiagramEditorInput(ProcessNode processNode);
	IEditorInput createOriginalEditorInput(String processId, String modelType);
	/**
	 * @param editorPart
	 * @param object is Activity or Link
	 */
	void goToEObject(IEditorPart editorPart, EObject object);
}
