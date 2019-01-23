package com.tibco.bx.debug.core.models;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.invoke.launcher.ILauncherService;
import com.tibco.bx.debug.core.models.variable.BxFieldVariable;
import com.tibco.bx.debug.core.models.variable.BxLocalVariable;
import com.tibco.bx.debug.core.models.variable.BxVariable;

public class BxStackFrame extends BxDebugElement implements IBxStackFrame{
	private IThread parent;
	private IBxThread thread;
	private List<BxStackFrame> children;
	
	public BxStackFrame(ProcessVisibleNode element, IThread parent, IBxThread thread){
		super(element, (IBxDebugTarget)thread.getDebugTarget());
		this.thread = thread;
		this.parent = parent;
		if(parent instanceof BxStackFrame){
			((BxStackFrame)parent).addStackFrame(this);
		}
		thread.addStackFrame(this);
	}

	private void addStackFrame(BxStackFrame bxStackFrame){
		if(children == null){
			children = new ArrayList<BxStackFrame>();
		}
		children.add(children.size(), bxStackFrame);
	}
	
	@Override
	public IThread getParent() {
		return parent;
	}

	@Override
	public IThread getThread() {
		return thread;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		if(hasVariables()){
			((BxDebugTarget) getDebugTarget()).setCurrentThread((IBxThread)getThread());
			ProcessVariable[] processVariables;
			try {
				processVariables = getDebugger().getProcessVariables(
						((BxDebugTarget) getDebugTarget()).getVariableHandler(),
						(ProcessVisibleNode) getProcessElement());
			} catch (CoreException e) {
				throw new DebugException(e.getStatus());
			}
			BxVariable[] vars = new BxVariable[processVariables.length];
			for (int i = 0; i < processVariables.length; i++) {
				if(processVariables[i].getParent() == ((BxDebugElement)getThread()).getProcessElement()){
					vars[i] = new BxFieldVariable(((IBxDebugTarget) getDebugTarget()), processVariables[i]);
				}else{
					vars[i] = new BxLocalVariable(((IBxDebugTarget) getDebugTarget()), processVariables[i]);
				}	
			}
			return vars;
		}else{
			return new IVariable[0];
		}
	}

	@Override
	public boolean hasVariables() throws DebugException {
		//TODO
		return getThread().isSuspended();
		//&& (getBreakWhen() != null);
	}

	@Override
	public boolean canStepInto() {
		return getThread().canStepInto();
	}

	@Override
	public boolean canStepOver() {
		return getThread().canStepOver();
	}

	@Override
	public boolean canStepReturn() {
		return getThread().canStepReturn();
	}

	@Override
	public boolean isStepping() {
		return false;
	}

	@Override
	public void stepInto() throws DebugException {
		getThread().stepInto();
	}

	@Override
	public void stepOver() throws DebugException {
		getThread().stepOver();
	}

	@Override
	public void stepReturn() throws DebugException {
		getThread().stepReturn();
	}

	@Override
	public boolean canResume() {
		return getThread().canResume();
	}

	@Override
	public boolean canSuspend() {
		return getThread().canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return getThread().isSuspended();
	}

	@Override
	public void resume() throws DebugException {
		getThread().resume();
	}

	@Override
	public void suspend() throws DebugException {
		getThread().suspend();
	}

	@Override
	public boolean canTerminate() {
		return getThread().canTerminate();
	}

	@Override
	public boolean isTerminated() {
		return getThread().isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		getThread().terminate();
	}

	private BreakWhen breakWhen;

	public BreakWhen getBreakWhen() {
		return breakWhen;
	}

	public void setBreakWhen(BreakWhen breakWhen) {
		this.breakWhen = breakWhen;
	}

	private boolean isCurrent = false;

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	@Override
	public String getElementType() {
		return ((ProcessVisibleNode)getProcessElement()).getType();
	}

	@Override
	public Object getAdapter(Class adapter) {
		ILauncherService service = DebugCoreActivator.createILaunchService();
		if(service !=null && service.getStackFrameAdapter(adapter) !=null){
			return service.getStackFrameAdapter(adapter);
		}
		
		return super.getAdapter(adapter);
	}
	
	@Override
	public IStackFrame[] getStackFrames() throws DebugException {
		return children == null ? new IStackFrame[0] : children.toArray(new IStackFrame[0]) ;
	}

	@Override
	public IStackFrame getTopStackFrame() throws DebugException {
		return null;
	}

	@Override
	public boolean hasStackFrames() throws DebugException {
		return children != null;
	}
	
	@Override
	public IBreakpoint[] getBreakpoints() {
		return null;
	}

	@Override
	public int getPriority() throws DebugException {
		return 0;
	}

	@Override
	public int getCharEnd() throws DebugException {
		return 0;
	}

	@Override
	public int getCharStart() throws DebugException {
		return 0;
	}

	@Override
	public int getLineNumber() throws DebugException {
		return 0;
	}

	@Override
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	@Override
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return null;
	}
}
