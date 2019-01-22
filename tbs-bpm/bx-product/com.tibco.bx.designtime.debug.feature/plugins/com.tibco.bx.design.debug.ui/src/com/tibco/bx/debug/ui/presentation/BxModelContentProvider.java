package com.tibco.bx.debug.ui.presentation;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.models.BxStackFrame;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.ui.DebugUIActivator;

public class BxModelContentProvider implements ITreeContentProvider {

	BxThread bxThread ;
	
	public BxModelContentProvider(BxThread thread) {
		bxThread = thread;
	}

	@Override
	public Object[] getChildren(Object parent) {
		if(parent instanceof BxDebugTarget){
			return new Object[]{bxThread};
		}else if(parent instanceof BxThread){
			return ((BxThread)parent).getStackFrames();
		}else if(parent instanceof BxStackFrame){
			try {
				return ((BxStackFrame)parent).getStackFrames();
			} catch (DebugException e) {
				DebugUIActivator.log(e);
				return new Object[]{};
			}
		}else{
			return new Object[]{parent};
		}
	}

	@Override
	public Object getParent(Object node) {
		return null;
	}

	@Override
	public boolean hasChildren(Object parent) {
		if(parent instanceof BxDebugTarget){
			return true;
		}else if(parent instanceof BxThread){
			return ((BxThread)parent).getStackFrames() != null && ((BxThread)parent).getStackFrames().length > 0;
		}else 
		if(parent instanceof BxStackFrame){
			try {
				return ((BxStackFrame)parent).hasStackFrames();
			} catch (DebugException e) {
				DebugUIActivator.log(e);
				return false;
			}
		}
		return false;
	}

	@Override
	public Object[] getElements(Object node) {
		
		if(node instanceof BxDebugTarget){
			return new Object[]{bxThread};
		}else{
			return getChildren(node);
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {

	}

}
