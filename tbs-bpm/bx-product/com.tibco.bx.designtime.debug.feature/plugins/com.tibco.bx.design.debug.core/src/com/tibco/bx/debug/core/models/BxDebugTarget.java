package com.tibco.bx.debug.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManagerListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.bx.debug.common.model.IBxNodeDefinition;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.models.variable.IVariableHandler;
import com.tibco.bx.debug.core.runtime.IBxDebugger;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.runtime.IBxTester;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.core.runtime.IConnectionListener;
import com.tibco.bx.debug.core.runtime.INodeDefinitionFactory;
import com.tibco.bx.debug.core.runtime.ITestDelegate;
import com.tibco.bx.debug.core.runtime.ITestDelegateFactory;
import com.tibco.bx.debug.core.runtime.events.IBxRuntimeEventFactory;
import com.tibco.bx.debug.core.runtime.internal.BxRuntimeEventHandler;
import com.tibco.bx.debug.core.runtime.internal.NodeDefinitionFactoryManager;
import com.tibco.bx.debug.core.runtime.internal.TestDelegateFactoryManager;
import com.tibco.bx.debug.core.runtime.internal.VariableHandlerManager;

public class BxDebugTarget extends BxDebugElement implements IBxDebugTarget,IBreakpointManagerListener,IConnectionListener  {

	enum State {
		TERMINATED,
		RUNNING,
		SUSPENDED,
		DISCONNECTED
	}
	
	private final IConnection connection;
	private ITestDelegate testDelegate;
	private BxRuntimeEventHandler runtimeEventHandler;
	private ILaunch launch;
	protected ArrayList<IBxThread> fThreads = new ArrayList<IBxThread>();
	private IBxThread currentThread;
	private State state = State.SUSPENDED;
	boolean breakpointManagerEnabled;
	boolean isThreadsTerminated;
	
	private Map<String, ListenerList> listenerMap;//create instance only when being needed
	private ListenerList listenerList;//to restore the listeners which ignore processId
	private IVariableHandler variableManager;
	
	protected List<String> flawProcesses;
	
	public BxDebugTarget(ILaunch launch, IConnection connection, IBxRuntimeEventFactory runtimeEventFactory) throws CoreException {
		super(null, null);
		
		this.launch = launch;
		this.connection = connection;
		connection.connect();
		runtimeEventHandler = new BxRuntimeEventHandler(this, runtimeEventFactory);
		runtimeEventHandler.start();
		connection.addEventListener(runtimeEventHandler);
		connection.addConnectionListener(this);
		
		init();
	}


	protected void init() throws CoreException {
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(this);
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointManagerListener(this);
		breakpointManagerEnabled = DebugPlugin.getDefault().getBreakpointManager().isEnabled();
		flawProcesses = new ArrayList<String>();
		isThreadsTerminated = false;
		state = State.RUNNING;
		ProcessTemplate[] templates = getProcessListing().getProcessTemplates();
		Set<String> processIds = new HashSet<String>();
		for (int i = 0; i < templates.length; i++) {
			processIds.add(templates[i].getProcessId());
		}
		
		INodeDefinitionFactory factory = NodeDefinitionFactoryManager.getNodeDefinitionFactory(getModelType());
		
		for (String processId : processIds) {
			IBxNodeDefinition[] definitions = factory.createNodeDefinitions(processId);
			if (definitions.length != 0) {
				try {
					getDebugger().setNodeDefinitions(processId, definitions);
				} catch (Throwable e) {
					addToFlaws(processId);
				}
			}
		}
		
		
		variableManager = VariableHandlerManager.getVariableHandler(getModelType());
		
		IBxTester tester = getTester();
		if (tester != null) {
			ITestDelegateFactory dFactory = TestDelegateFactoryManager.getTestDelegateFactory(getModelType());
			testDelegate = dFactory.createTestDelegate(tester);
		}
		
		addBreakpoints();
		
		if(!getFlawProcesses().isEmpty()) {
			showDetails(getFlawTemplates(templates, getFlawProcesses()));
		}
	}
	
	private void addBreakpoints() throws CoreException{
		getDebugger().removeAllBreakpoints();
		IBreakpoint[] breakPts = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(getModelIdentifier());
		if(breakpointManagerEnabled){
			for (IBreakpoint breakpoint : breakPts) {
				breakpointAdded(breakpoint);
			}
		}
	}
	
	public IConnection getConnection(){
		return connection;
	}

	public IBxDebugger getDebugger() {
		return (IBxDebugger)getAdapter(IBxDebugger.class);
	}
	
	public IBxProcessListing getProcessListing() {
		return (IBxProcessListing) this.getAdapter(IBxProcessListing.class);
	}
	
	public IBxTester getTester() {
		return (IBxTester) this.getAdapter(IBxTester.class);
	}
	
	public ITestDelegate getTestDelegate() {
		return testDelegate;
	}
	
	@Override
	public IVariableHandler getVariableHandler() {
		return variableManager;
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IDebugElement#getDebugTarget()
	 */
	public IDebugTarget getDebugTarget() {
		return this;
	}

	/**
	 * @see org.eclipse.debug.core.model.IDebugElement#getLaunch()
	 */
	public ILaunch getLaunch() {
		return launch;
	}

	/**
	 * Returns the name of the process
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getName()
	 */
	public String getName() throws DebugException{
		return connection.toString();
	}

	/**
	 * return null
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getProcess()
	 */
	public IProcess getProcess() {
		return null;
	}

	/**
	 * This method returns the array of threads. 
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getThreads()
	 */
	public IThread[] getThreads() throws DebugException {
		synchronized (fThreads) {
			return isTerminated()? new IThread[0] : (IThread[])fThreads.toArray(new IThread[0]);
		}
	}

	public IBxThread getCurrentThread() throws DebugException {
		if(isTerminated()) return null;
		if (currentThread == null && fThreads.size() > 0) {
			return fThreads.get(0);
		} else {
			return currentThread;
		}
	}
	
	public void setCurrentThread(IBxThread currentThread){
		this.currentThread = currentThread;
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IDebugTarget#hasThreads()
	 */
	public boolean hasThreads() throws DebugException {
		return (getThreads() != null && getThreads().length > 0);
	}

	/**
	 * @see org.eclipse.debug.core.model.IDebugTarget#supportsBreakpoint(org.eclipse.debug.core.model.IBreakpoint)
	 */
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		return breakpoint instanceof BxBreakpoint;
	}

	public void refresh() throws CoreException{
		init();
	}
	
	public IBxThread createBxThread(ProcessInstance instance) throws CoreException{
		
		BxThread thread = new BxThread(instance, this);
		
		synchronized (fThreads) {
			fThreads.add(thread);
		}

		fireChangeEvent(DebugEvent.CONTENT);
		thread.fireCreationEvent();
		fireBxThreadChanged(thread.getInstanceId(), new BxDebugEvent(thread, BxDebugEvent.STARTED));
		
		return thread;
	}
	
	/**
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointAdded(org.eclipse.debug.core.model.IBreakpoint)
	 */
	public void breakpointAdded(IBreakpoint breakpoint) {
		if(!breakpointManagerEnabled) return;
		if(breakpoint instanceof BxBreakpoint && ((BxBreakpoint)breakpoint).getModelIdentifier().endsWith(getModelIdentifier())){
			BxBreakpoint bb = (BxBreakpoint) breakpoint;
			try {
				getDebugger().addBreakpoint(bb);
			} catch (CoreException e) {
				DebugCoreActivator.log(e);
			}
		}
		
	}

	/**
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointChanged(org.eclipse.debug.core.model.IBreakpoint,
	 *      org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		if(breakpoint instanceof BxBreakpoint && ((BxBreakpoint)breakpoint).getModelIdentifier().endsWith(getModelIdentifier())){
			BxBreakpoint bb = (BxBreakpoint) breakpoint;
			try {
				if(breakpointManagerEnabled){
					getDebugger().modifyBreakpoint(bb);
				}else{
					getDebugger().removeBreakpoint(bb);
				}
			} catch (CoreException e) {
				DebugCoreActivator.log(e);
			}
		}
	}

	/**
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointRemoved(org.eclipse.debug.core.model.IBreakpoint,
	 *      org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		if(!breakpointManagerEnabled) return;
		if(breakpoint instanceof BxBreakpoint && ((BxBreakpoint)breakpoint).getModelIdentifier().endsWith(getModelIdentifier())){
			BxBreakpoint bb = (BxBreakpoint) breakpoint;
			try {
				getDebugger().removeBreakpoint(bb);
			} catch (CoreException e) {
				DebugCoreActivator.log(e);
			}
		}
	}
	/**
	 * @see org.eclipse.debug.core.IBreakpointManagerListener#breakpointManagerEnablementChanged(boolean)
	 */
	public void breakpointManagerEnablementChanged(final boolean enabled) {
		breakpointManagerEnabled = enabled;
	}
    
	
	/**
	 * @see org.eclipse.debug.core.model.IDisconnect#isDisconnected()
	 */
	public boolean isDisconnected() {
		return isTerminated();
	}

	/**
	 * @see org.eclipse.debug.core.model.IDisconnect#canDisconnect()
	 */
	public boolean canDisconnect() {
		return !isDisconnected();
	}

	/**
	 * @see org.eclipse.debug.core.model.IDisconnect#disconnect()
	 */
	public void disconnect() throws DebugException {
		if (canDisconnect()) {
			if(!isThreadsTerminated){
				terminateThreads();
			}
			state = State.DISCONNECTED;
			doDispose();
			clear();
			try {
				connection.disconnect();
			} catch (CoreException e) {
				throw new DebugException(e.getStatus());
			}
		}
	}

	private void doDispose(){
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(this);
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointManagerListener(this);
		fireBxThreadChanged(null, new BxDebugEvent(this, BxDebugEvent.DISCONNECTED));
		clear();
		if(testDelegate != null)
			testDelegate .dispose();
		fireTerminateEvent();
	}
	
	/**
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {	
		return state == State.SUSPENDED;
	}

	/**
	 * Return true if the debugger can be suspended
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public boolean canSuspend() {
		if(state == State.RUNNING){
			for (IBxThread thread : fThreads) {
				if(!thread.isSuspended() && !thread.isTerminated()){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		IThread[] threads = getThreads();
		for(IThread thread:threads){
			if(thread.canSuspend())
				thread.suspend();
		}
		//state = State.SUSPENDED;
	}

	/**
	 * This method resume the debugger
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		IThread[] threads = getThreads();
		for(IThread thread:threads){
			if(thread.canResume())
				thread.resume();
		}
		state = State.RUNNING;
	}

	/**
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public boolean canResume() {
		if(state == State.RUNNING){
			for (IBxThread thread : fThreads) {
				if(thread.isSuspended()){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return state == State.TERMINATED || state == State.DISCONNECTED || getDebugger() == null;
	}

	/**
	 * Return true if the debugger can be terminated
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return state != State.TERMINATED && state != State.DISCONNECTED;
	}

	/**
	 * This method terminates the action of the debugger
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		if (!canTerminate()) {
			return;
		}
		terminateThreads();
		disconnect();
		state = State.TERMINATED;
	}
	
	private void terminateThreads() throws DebugException{
		synchronized (fThreads) {
			IThread[] threads = getThreads();
			for (IThread thread : threads) {
				if(!thread.isTerminated()){
					thread.terminate();
					synchronized (this) {
						try {
							wait(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			currentThread = null;
		}
		isThreadsTerminated = true;
	}
	
	/**
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#getMemoryBlock(long,
	 *      long)
	 */
	public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
		return null;
	}

	/**
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#supportsStorageRetrieval()
	 */
	public boolean supportsStorageRetrieval() {
		return false;
	}

	
    public IBxThread getThread(String instanceId){
    	synchronized(this){
	    	for (IBxThread thread : fThreads) {
	    		if(thread.getProcessElement().getInstanceId().equals(instanceId))
	    			return thread;
			}
    	}
    	return null;
    }
  
	public void dispose(){
		
	}
	
	public synchronized void addBxTheadChangedListener(String processInstanceId, IBxDebugListener listener){
		if(listenerMap == null){
			listenerMap = new HashMap<String, ListenerList>();
		}
		if(listener == null){
			throw new IllegalArgumentException();
		}
		if(processInstanceId == null){
			if(listenerList == null){
				listenerList = new ListenerList();
			}
			listenerList.add(listener);
			return;
		}
		ListenerList list = listenerMap.get(processInstanceId);
		if(list == null){
			list = new ListenerList(ListenerList.EQUALITY);
			listenerMap.put(processInstanceId, list);
		}
		list.add(listener);
	}
	
	public synchronized void removeBxTheadChangedListener(String processInstanceId, IBxDebugListener listener){
		if(listenerMap != null){
			if(listener == null){
				throw new IllegalArgumentException();
			}
			ListenerList list = listenerMap.get(processInstanceId);
			if(list != null){
				list.remove(listener);
				if(list.isEmpty())
					listenerMap.remove(processInstanceId);
			}
		}
		if(listenerList != null){
			listenerList.remove(listener);
		}
	}
	
	public synchronized void clear() {
		if(listenerMap != null){
			listenerMap.clear();
		}
		if(listenerList != null){
			listenerList.clear();
		}
	}
	
	public void fireBxThreadChanged(String processInstanceId, BxDebugEvent event){
		if (listenerMap != null) {
			ListenerList list = listenerMap.get(processInstanceId);
			if (list != null) {
				Object[] listeners = list.getListeners();
				for (int i = 0; i < listeners.length; i++) {
					((IBxDebugListener) listeners[i]).bxThreadChanged(event);
				}
			}
		}
		if (listenerList != null) {
			Object[] listeners = listenerList.getListeners();
			for (int i = 0; i < listeners.length; i++) {
				((IBxDebugListener) listeners[i]).bxThreadChanged(event);
			}
		}
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		Object result = connection.getAdapter(adapter);
		if (result != null) {
			return result;
		}
		return super.getAdapter(adapter);
	}


	@Override
	public void connectionClosed(IConnection connection) {
		state = State.DISCONNECTED;
		doDispose();
		DebugCoreActivator.getDefault().getBxLaunchListener().connectionClosed();
	}

	@Override
	public String getElementType() {
		return TARGET;
	}

	@Override
	public String getModelType() {
		return connection.getModelType();
	}
	
	protected List<ProcessTemplate> getFlawTemplates(ProcessTemplate[] templates, List<String> flawProcesses) {
		List<ProcessTemplate> flawTemplates = new ArrayList<ProcessTemplate>();
		for (ProcessTemplate template : templates) {
			for (String processId : flawProcesses) {
				if (processId.equals(template.getProcessId()) && !flawTemplates.contains(template)) {
					flawTemplates.add(template);
				}
			}
		}
		return flawTemplates;
	}
	
	protected void showDetails(final List<ProcessTemplate> flawProcessTemplates) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				MultiStatus multiStatus = new MultiStatus(DebugCoreActivator.PLUGIN_ID, 1, Messages.getString("BxDebugTarget.launchWarningDialog.message"), null); //$NON-NLS-1$
				for (ProcessTemplate template : flawProcessTemplates) {
					multiStatus.add(new Status(IStatus.INFO, DebugCoreActivator.PLUGIN_ID, 1, template.getDetails(), null));
				} 
				ErrorDialog.openError(null, Messages.getString("BxDebugTarget.launchWarningDialog.title"), null, multiStatus); //$NON-NLS-1$
			}

		});
	}


	public List<String> getFlawProcesses() {
		return flawProcesses;
	}
	
	protected void addToFlaws(String processId) {
		if (!flawProcesses.contains(processId)) {
			flawProcesses.add(processId);
		}
	}
	
	public boolean isFlawProcess(String processId) {
		return getFlawProcesses().contains(processId);
	}
	
}