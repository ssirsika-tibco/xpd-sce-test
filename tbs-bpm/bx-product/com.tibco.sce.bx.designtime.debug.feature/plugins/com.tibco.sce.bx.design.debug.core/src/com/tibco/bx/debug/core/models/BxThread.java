package com.tibco.bx.debug.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;

import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.invoke.launcher.ILauncherService;
import com.tibco.bx.debug.core.models.variable.BxFieldVariable;
import com.tibco.bx.debug.core.models.variable.BxLocalVariable;
import com.tibco.bx.debug.core.models.variable.BxVariable;
public class BxThread extends BxDebugElement implements IBxThread {

	private boolean fSuspended = false;
	private IBxStackFrame topStackFrame =null;//suspending at the node
	private int variableCount = -1;
	private boolean isSteping;
	private List<IBxStackFrame> children = new ArrayList<IBxStackFrame>();
	private Map<String, IBxStackFrame> allBxStackFrames = new HashMap<String, IBxStackFrame>();
	
	
	public BxThread(ProcessInstance instance, IBxDebugTarget debugTarget)throws CoreException {
		super(instance, debugTarget);
		init();
	}
	
	private void init()throws CoreException{
		//TODO
	}
	
	public void addStackFrame(IBxStackFrame stackFrame) {
		synchronized (children) {
			if(stackFrame.getParent() == this)
				children.add(stackFrame);
			allBxStackFrames.put(stackFrame.getInstanceId(), stackFrame);
		}
	}
	

	public IBxStackFrame getStackFrame(String instanceId){
		return allBxStackFrames.get(instanceId);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IThread#getStackFrames()
	 */
	public IStackFrame[] getStackFrames(){
		return children.toArray(new IStackFrame[0]);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IBxThread#getAllStackFrames()
	 */
	public IBxStackFrame[] getAllStackFrames(){
		return allBxStackFrames.values().toArray(new IBxStackFrame[0]);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IThread#hasStackFrames()
	 */
	public boolean hasStackFrames() throws DebugException {
		return !children.isEmpty();
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IThread#getPriority()
	 */
	public int getPriority() throws DebugException {
		return 0;
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IThread#getTopStackFrame()
	 */
	public IStackFrame getTopStackFrame() throws DebugException {
		if (topStackFrame != null && topStackFrame.isCurrent()) {
			return topStackFrame;
		}
		return null;
	}

	public void setTopStackFrame(IBxStackFrame topStackFrame){
		if(this.topStackFrame !=null && this.topStackFrame != topStackFrame){
			this.topStackFrame.setBreakWhen(null);
			updateCurrent(this.topStackFrame, false, false);
		}
		isSteping = false;
		this.topStackFrame = topStackFrame;
		this.topStackFrame.setCurrent(true);
		updateCurrent(this.topStackFrame, true, true);
	}
	
	private void updateCurrent(IBxStackFrame stackFrame,
			boolean isCurrent,boolean updateDebugViewer) {
		stackFrame.setCurrent(isCurrent);
		IThread parent = stackFrame.getParent();
		while(parent!=null && parent instanceof IBxStackFrame){
			((IBxStackFrame)parent).setCurrent(isCurrent);
			parent = ((IBxStackFrame)parent).getParent();
		}
		if(updateDebugViewer){
			fireSuspendEvent(DebugEvent.SUSPEND);
		}
	}

	/**
	 * @see org.eclipse.debug.core.model.IThread#getBreakpoints()
	 */
	public IBreakpoint[] getBreakpoints() {
		String modelId = getModelIdentifier();
		return DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(modelId);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public boolean canResume() {
		return fSuspended && !isTerminated();
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public boolean canSuspend() {
		return !fSuspended && !isTerminated();
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {
		return !isSteping && fSuspended && !isTerminated();
	}
	
	/**
	 * Sets whether this thread is suspended
	 * 
	 * @param suspended whether suspended
	 */
	public void setSuspended(boolean suspended) {
		fSuspended = suspended;
		
		if (!fSuspended) {
			this.fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
			((BxDebugTarget)getDebugTarget()).fireBxThreadChanged(
					this.getInstanceId(), new BxDebugEvent(this, BxDebugEvent.RESUMED));
		} else {
			this.fireSuspendEvent(DebugEvent.CLIENT_REQUEST);
			DebugCoreActivator.fireCurrentStackFrameChange(
					DebugCoreActivator.CURRENT_STACKFRAME_P, null, this.getCurrent());
		}
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		if(topStackFrame!=null){
			updateCurrent(topStackFrame,false,true);
		}
		((BxDebugTarget)getDebugTarget()).setCurrentThread(this);
		try {
			getDebugger().resume(getInstanceId());
		} catch (CoreException e) {
			throw new DebugException(e.getStatus());
		}
		fSuspended =false;
		((ProcessInstance)getProcessElement()).setState(ProcessInstance.State.ACTIVE);
		fireResumeEvent(DebugEvent.CLIENT_REQUEST);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		((BxDebugTarget)getDebugTarget()).setCurrentThread(this);
		try {
			getDebugger().suspend(getInstanceId());
		} catch (CoreException e) {
			throw new DebugException(e.getStatus());
		}
		fireSuspendEvent(DebugEvent.SUSPEND);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IStep#canStepInto()
	 */
	public boolean canStepInto() {
		if(topStackFrame!= null){
			try {
				return  isSuspended() && getDebugger().canStepInto(getInstanceId());
			} catch (CoreException e) {
				DebugCoreActivator.log(e.getStatus());
			return false;
			}
		}
		return false;
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IStep#canStepOver()
	 */
	public boolean canStepOver() {
		return isSuspended();
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IStep#canStepReturn()
	 */
	public boolean canStepReturn() {
		try {
			return  isSuspended() && getDebugger().canStepReturn(getInstanceId());
		} catch (CoreException e) {
			DebugCoreActivator.log(e.getStatus());
			return false;
		}
	}

	/**
	 * @see org.eclipse.debug.core.model.IStep#isStepping()
	 */
	public boolean isStepping() {
		return isSteping;
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IStep#stepInto()
	 */
	public void stepInto() throws DebugException {
		try {
			isSteping = true;
			getDebugger().stepInto(getInstanceId());
		} catch (CoreException e) {
			throw new DebugException(e.getStatus());
		}
		//topStackFrame.setBreakWhen(null);
		fSuspended =false;
		((ProcessInstance)getProcessElement()).setState(ProcessInstance.State.ACTIVE);
		fireResumeEvent(DebugEvent.CLIENT_REQUEST);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IStep#stepOver()
	 */
	public void stepOver() throws DebugException {
		((BxDebugTarget) getDebugTarget()).setCurrentThread(this);
		try {
			isSteping = true;
			getDebugger().stepOver(getInstanceId());
		} catch (CoreException e) {
			throw new DebugException(e.getStatus());
		}
		fSuspended = false;
		((ProcessInstance)getProcessElement()).setState(ProcessInstance.State.ACTIVE);
		fireResumeEvent(DebugEvent.CLIENT_REQUEST);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IStep#stepReturn()
	 */
	public void stepReturn() throws DebugException {
		
		try {
			isSteping = true;
			getDebugger().stepReturn(getInstanceId());
			} catch (CoreException e) {
				throw new DebugException(e.getStatus());
			}
			fSuspended =false;
			((ProcessInstance)getProcessElement()).setState(ProcessInstance.State.ACTIVE);
			fireResumeEvent(DebugEvent.CLIENT_REQUEST);
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return !isTerminated();
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return getDebugTarget().isTerminated() || ((ProcessInstance)getProcessElement()).isEnded();
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		if(topStackFrame!=null){
			updateCurrent(topStackFrame,false,true);
		}
		if(!isTerminated()){
			try {
				getDebugger().terminate(getInstanceId());
			} catch (CoreException e) {
				throw new DebugException(e.getStatus());
			}
		}
	}
	
	public IBxStackFrame getDescendantStackFrame(String instanceId) {
		return allBxStackFrames.get(instanceId);
	}
	
	public String getElementType() {
		return PROCESS;
	}
	
	public String getState(){
		return ((ProcessInstance)getProcessElement()).getState();
	}

	@Override
	public Object getAdapter(Class adapter) {
		ILauncherService service = DebugCoreActivator.createILaunchService();
		if(service !=null && service.getThreadAdapter(adapter) !=null){
			return service.getThreadAdapter(adapter);
		}
		return super.getAdapter(adapter);
	}

	/**
	 * @see org.eclipse.debug.core.model.IStackFrame#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException {
		if (hasVariables()) {
			((BxDebugTarget) getDebugTarget()).setCurrentThread(this);
			ProcessVariable[] processVariables;
			try {
				processVariables = getDebugger().getProcessVariables(
						((BxDebugTarget) getDebugTarget()).getVariableHandler(),
						(ProcessInstance) getProcessElement());
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
			variableCount = vars.length;
			return vars;
		} else {
			variableCount = -1;
			return new IVariable[0];
		}
	}
	
	public int getVariableCount(){
		return variableCount;
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IStackFrame#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		return isSuspended();
	}

	public IBxStackFrame getCurrent(){
		return topStackFrame;
	}
	
	public IThread getThread() {
		return this;
	}
	
	public int getCharEnd() throws DebugException {
		return 0;
	}

	public int getCharStart() throws DebugException {
		return 0;
	}

	public int getLineNumber() throws DebugException {
		return 0;
	}

	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return null;
	}

	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}
}