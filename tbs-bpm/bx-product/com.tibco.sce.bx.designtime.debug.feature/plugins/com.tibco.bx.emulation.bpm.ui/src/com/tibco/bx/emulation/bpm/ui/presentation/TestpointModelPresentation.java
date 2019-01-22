package com.tibco.bx.emulation.bpm.ui.presentation;

import org.eclipse.debug.ui.ISourcePresentation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.bx.emulation.ui.EmulationUIActivator;
import com.tibco.xpd.processeditor.xpdl2.ProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.edit.util.IGotoEObject;

public class TestpointModelPresentation implements ISourcePresentation {

	
	public String getEditorId(IEditorInput input, Object objectInput) {
		return ProcessDiagramEditor.EDITOR_ID;
	}

	public IEditorInput getEditorInput(Object item) {
		Process process = null;
		if (item instanceof Process){
	    	process = (Process)item;
	    }else if (item instanceof Activity){
	    	process= ((Activity)item).getProcess();
	    }else{
	    	return null;
	    }
		WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopyFor(process);
	    return workingCopy != null ? new ProcessEditorInput(workingCopy, process) : null; 
	}

	public static Activity getActivity(Process process, String xpdlId){
		//TODO embedded subprocess
		if(process == null){
			return null;
		}
		return process.getActivity(xpdlId);
	}
	
	public static Transition getTransition(Process process, String xpdlId){
		//TODO embedded subprocess
		if(process == null){
			return null;
		}
		return process.getTransition(xpdlId);
	}
	
	public IEditorPart openEditor(Object item) {

		Process process = null;
		EObject eObject = null;
		if (item instanceof Input || item instanceof IntermediateInput || item instanceof Testpoint) {
			process = (Process)ProcessUtil.getProcess(((ProcessNode)((NamedElement)item).eContainer()).getId(),
					((ProcessNode)((NamedElement)item).eContainer()).getModelType());     
			eObject = getActivity(process, ((NamedElement)item).getId());
	    }else if (item instanceof ProcessNode) {
	    	process = (Process)ProcessUtil.getProcess(((ProcessNode)item).getId(),
	    			((ProcessNode)item).getModelType());
	    }else if(item instanceof Assertion){
	    	process = (Process)ProcessUtil.getProcess(((Assertion)item).getProcessNode().getId(),
	    			((ProcessNode)((NamedElement)item).eContainer()).getModelType());     
			eObject = getTransition(process, ((Assertion)item).getId());
	    }
		IEditorInput input = getEditorInput(process);
		IEditorPart editorPart = null;
		if (input != null) {
			editorPart = openEditor(input, getEditorId(input, item));
		}
		if (editorPart != null) {
			editorPart.setFocus(); 
			if (editorPart instanceof IGotoEObject && eObject != null) {
				((IGotoEObject)editorPart).gotoEObject(true,new EObject[]{eObject});
			} 
		}
		return editorPart;
	}
	
	/**
	 * Opens an editor in the workbench and returns the editor that was opened
	 * or <code>null</code> if an error occurred while attempting to open the
	 * editor.
	 */
	public static IEditorPart openEditor(IEditorInput input, String id) {
		IEditorPart[] editor = new IEditorPart[] { null };
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if(activeWorkbenchWindow==null||activeWorkbenchWindow.getActivePage()==null){
			return null;
		}
		IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
		if (!page.getWorkbenchWindow().getWorkbench().isClosing()) {
			try {
				editor[0] = page.openEditor(input, id, false,
						IWorkbenchPage.MATCH_ID | IWorkbenchPage.MATCH_INPUT);
			} catch (PartInitException e) {
				EmulationUIActivator.log(e);
			}
		}
		return editor[0];
	}	
	
}
