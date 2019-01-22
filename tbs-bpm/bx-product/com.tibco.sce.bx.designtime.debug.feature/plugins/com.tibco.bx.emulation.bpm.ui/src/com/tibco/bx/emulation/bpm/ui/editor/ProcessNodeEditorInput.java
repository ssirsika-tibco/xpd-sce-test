package com.tibco.bx.emulation.bpm.ui.editor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.ui.editor.IDiagramEditorInput;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

public class ProcessNodeEditorInput implements IDiagramEditorInput{

	private  Process process;

    public Process getProcess() {
		return process;
	}

	public WorkingCopy getXPDWorkingCopy() {
		return xpdWorkingCopy;
	}

	private  WorkingCopy xpdWorkingCopy;
	
	private  WorkingCopy workingCopy;
    
    public WorkingCopy getWorkingCopy() {
		return workingCopy;
	}

	private ProcessNode processNode;
    
	public ProcessNode getProcessNode() {
		return processNode;
	}

	public ProcessNodeEditorInput(WorkingCopy workingCopy,ProcessNode processNode){
		this.processNode = processNode;
		this.workingCopy= workingCopy;
		process = Xpdl2WorkingCopyImpl.locateProcess(((ProcessNode)processNode).getId());
		Assert.isNotNull(process);
		xpdWorkingCopy = WorkingCopyUtil.getWorkingCopyFor(process);
		
	}
	 
	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return processNode != null ? processNode.getName() : ""; //$NON-NLS-1$
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		String nodeURI = ""; //$NON-NLS-1$
		if (processNode != null && processNode.eResource() != null && processNode.eResource().getURI() != null) {
			nodeURI = processNode.eResource().getURI().toPlatformString(true);
		}
		return String.format("%s%s%s", new Object[] { nodeURI, IPath.SEPARATOR, getName() }); //$NON-NLS-1$
	}

	public Object getAdapter(Class adapter) {
		if (adapter == EObject.class){
		      return processNode;
		}else if (adapter == WorkingCopy.class) {
		      return workingCopy;
		}/* 
		  * SID XPD-8391.  Because we were adapting to IResource here then the "Compare With" menu and content 
		  * were being shown on the right click context menu for items in the diagram.
		  * Trouble is we're not sure if adapting to IResource is required for all of Emulation capabilities.
		  * It doesn't seem so as the TestPoint menu etc still shows. 
		  * else if (adapter == IResource.class) {
		      return workingCopy.getEclipseResources().get(0);
		} */
		    return null;
	}

	/**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (o == this) {
            // most cases
            return true;
        }
        if (o instanceof ProcessNodeEditorInput) {
            return ((ProcessNodeEditorInput) o).processNode.equals(processNode);
        }
        return false;
    }
}
