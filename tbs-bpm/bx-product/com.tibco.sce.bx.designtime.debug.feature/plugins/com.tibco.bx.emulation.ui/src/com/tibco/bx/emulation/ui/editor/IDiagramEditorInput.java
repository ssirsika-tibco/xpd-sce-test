package com.tibco.bx.emulation.ui.editor;

import org.eclipse.ui.IEditorInput;

import com.tibco.bx.emulation.model.ProcessNode;

public interface IDiagramEditorInput extends IEditorInput{

	ProcessNode getProcessNode();
}
