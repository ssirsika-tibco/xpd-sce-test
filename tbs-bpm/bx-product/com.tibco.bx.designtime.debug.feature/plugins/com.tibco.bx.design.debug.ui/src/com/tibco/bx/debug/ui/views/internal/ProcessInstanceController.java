package com.tibco.bx.debug.ui.views.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.progress.UIJob;

import com.tibco.bx.debug.common.model.ProcessElement;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.invoke.launcher.ResponseBackEvent;
import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.launching.IntermediateEventHandle;
import com.tibco.bx.debug.core.launching.IntermediateLauncherManager;
import com.tibco.bx.debug.core.launching.MultiInputEventHandle;
import com.tibco.bx.debug.core.launching.MultiNodesLauncherManager;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.models.BxStackFrame;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.emulation.core.common.IInOutElement;
import com.tibco.bx.emulation.core.util.EmulationUtil;
import com.tibco.bx.emulation.model.Assertion;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.IntermediateInput;
import com.tibco.bx.emulation.model.MultiInput;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;

public class ProcessInstanceController implements IProcessInstanceController,ISelectionChangedListener{

	private IInOutElement inputElement;
	private IInOutElement outputElement;
	
	private EObject process;
	private ProcessNode processNode;
	
	private String processInstanceId;
	private String errorMessage;
	
	private boolean isAvailable = true;
	private boolean isSoapType;
	
	private int status;
	
	private IProcessTabPane processTabPane;
	private IStatus runningStatus = Status.OK_STATUS;
	private BxThread thread;
	
	private EObject startActivity;
	private Map<String,Object> currentData = new HashMap<String, Object>();
	private List<EObject> runned = new ArrayList<EObject>();//activities
	
	private TransactionalEditingDomain domain;
	
	
	/**
	 * this is for Emulation
	 * @param processNode
	 */
	public ProcessInstanceController(ProcessNode processNode){
		assert(processNode != null);
		this.processNode = processNode;
		process = ProcessUtil.getProcess(this.processNode.getId(), this.processNode.getModelType());
		if(this.processNode.getInput() != null ){
			startActivity = ProcessUtil.getActivity(process, this.processNode.getInput().getId());
			setInvokeType(ProcessUtil.isWebServiceImplementationActivity(startActivity));
			runned.add(startActivity);
		}
		updateDisplayPane(startActivity);
	}


	public ProcessInstanceController(EObject startActivity) {
		this.startActivity = startActivity;
		process = ProcessUtil.getProcess(this.startActivity);
		runned.add(this.startActivity);
	}

	@Override
	public EObject getProcess() {
		return process;
	}

	public String getProcessId(){
		if(process != null){
			return ProcessUtil.getElementId(process);
		}
		return null;
	}
	
	@Override
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	@Override
	public ProcessNode getProcessNode() {
		 if(processNode == null && process != null){
         	if(processNode == null){
         		processNode = EmulationUtil.createProcessNodeWithOutput(process);
         	}
		 }
         return processNode;
	}

	@Override
	public void setInputElement(final Object inputElement) {
		final ProcessNode node = getProcessNode();
		node.setModelType(((IInOutElement)inputElement).getProcessModelType());
		node.setInput((Input)(((IInOutElement)inputElement).getInOutDataType()));
		this.inputElement = (IInOutElement)inputElement;
	}

	@Override
	public void setOutputElement(Object outputElement) {
		this.outputElement =  (IInOutElement)outputElement;
		setTestpointsAndAssertions();
	}

	@Override
	public void afterLauncher(String response) {
		if (!isAvailable)
			return;
		if(StringUtils.isNotBlank(response.trim())){
			final ResponseBackEvent event = new ResponseBackEvent(response, null);
			processNode.getOutput().setSoapMessage(event.toXml());
		}
		setTestpointsAndAssertions();
	}

	@Override
    public void beforeLauncher(final String request) {
		final ResponseBackEvent event = new ResponseBackEvent(request, null);
		processNode.getInput().setSoapMessage(event.toXml());
    }
	
	private TransactionalEditingDomain getDomain(){
		if(domain == null && processNode != null){
			domain = TransactionUtil.getEditingDomain(processNode);
		}
		return domain;
	}
	
	protected void handleStartEvent(Object source){
		if(source instanceof BxThread){
			if( !StringUtils.isNotBlank(getProcessInstanceId()) || 
					((BxThread)source).getInstanceId().equals(getProcessInstanceId()) ){
				
				thread = (BxThread)source;
				EObject process = ProcessUtil.getProcess(thread.getProcessElement().getIndex(), ((IBxDebugTarget)thread.getDebugTarget()).getModelType());
				if(process != null && process == this.process){		   
					setProcessInstanceId(thread.getInstanceId());
				}
				
			}
		}else if(source instanceof BxStackFrame){
			handleActivityStart((BxStackFrame)source);
		}
	}
	
	protected void handleActivityStart(BxStackFrame source) {
      
	}
	
	private String getInstanceId4Event(BxDebugEvent event) {
		BxThread thread = null;
		Object source = event.getSource();
		if (source instanceof BxThread) {
			thread = (BxThread) source;
		} else if (source instanceof BxStackFrame) {
			BxStackFrame stackFrame = (BxStackFrame) source;
			thread = (BxThread) stackFrame.getThread();
		}
		return thread.getProcessElement().getInstanceId();
	}

	@Override
	public void bxThreadChanged(BxDebugEvent event) {
		Object source = event.getSource();
		final Object data = event.getData();
		if (source instanceof BxDebugTarget
			|| (getProcessInstanceId() != null && !getProcessInstanceId().equals(getInstanceId4Event(event)))) {
			return;
		}
		status = event.getKind();
		
		switch(status){
		case BxDebugEvent.STARTED:
			handleStartEvent(source);
			break;
		case BxDebugEvent.COMPLETED:
//		case BxDebugEvent.TERMINATED:
			if(source instanceof BxThread){
				handleProcessCompleted((BxThread)source , data);
			}else if( source instanceof BxStackFrame ){
				handleActivityCompleted(source);
			}
			break;
		case BxDebugEvent.ON_ENTRY:
		case BxDebugEvent.ON_EXIT:
			source = ((BxStackFrame)source).getThread();
			break;
		case BxDebugEvent.SUSPENDED:
			String eventMessage = (String)event.getData();
			errorMessage = eventMessage;
			break;
		}
		
		if(processTabPane != null && (!processTabPane.isDisposed()) && source instanceof BxThread )
			processTabPane.refresh();
	}

	protected void handleActivityCompleted(Object source) {
		String xpdlID = ((BxStackFrame)source).getProcessElement().getIndex();
		if(IntermediateLauncherManager.instance.isCurrentActivity(xpdlID)){
			IntermediateEventHandle handle = IntermediateLauncherManager.instance.popIntermediateEventHandle();
			appendIntermediateInput(handle);
		}else if(MultiNodesLauncherManager.instance.isCurrentActivity(xpdlID)){
			MultiInputEventHandle handle = MultiNodesLauncherManager.instance.popMultiNodesEventHandle();
			appendMultiInput(handle);
		}
	}

	protected void appendMultiInput(MultiInputEventHandle handle){
		runned.add(handle.getStartActivity());
		updateTab();
	}
	
	protected void handleProcessCompleted(BxThread source , Object data) {
		if(StringUtils.equal(source.getInstanceId(), getProcessInstanceId())){
			EObject endActivity = getEndActivity(source);
			if(endActivity != null){
				try {
					Output output = getProcessNode().getOutput();
					output.setId(ProcessUtil.getElementId(endActivity));
					output.setName(ProcessUtil.getDisplayName(endActivity));
					IInOutElement inOutElement = EmulationUtil.createOutputElement(output, getProcessNode());
					setOutputElement(inOutElement);
					setTestpointsAndAssertions();
					if(TargetManager.getDefault().getCurrentTarget() != null){
						((BxDebugTarget)source.getDebugTarget()).removeBxTheadChangedListener(getProcessInstanceId(), this);
					}
				} catch (Exception e) {
					DebugUIActivator.log(e);
				}	
			}
			updateDisplayPane(startActivity);
		}
		
	}

	private void setTestpointsAndAssertions(){
		
		ProcessNode node = getProcessNode();
		
		ProcessNode cacheNode = EmulationUtil.getProcessNodeFromCache(ProcessUtil.getElementId(process));
		if(cacheNode != null ){
				node.getTestpoints().clear();
				for(Testpoint testpoint : cacheNode.getTestpoints()){
					node.getTestpoints().add((Testpoint)EcoreUtil.copy(testpoint));
				}
				node.getAssertions().clear();
				for(Assertion assertion : cacheNode.getAssertions()){
					node.getAssertions().add((Assertion)EcoreUtil.copy(assertion));
				}
		}
	}
	
	@Override
	public boolean isAvailable() {
		return isAvailable;
	}

	@Override
	public void dispose() {
		isAvailable = false;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public int getStatus() {
		return status;
	}

	public void setProcessTabPane(IProcessTabPane processTabPane) {
		this.processTabPane = processTabPane;
	}

	@Override
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

    public void setInvokeType(boolean isSoapType) {
        this.isSoapType = isSoapType;
    }

    @Override
    public boolean isSoapType() {
        return isSoapType;
    }

	public BxThread getThread() {
		return thread;
	}

	public IStatus getRunningStatus() {
		return runningStatus;
	}

	public void setRunningStatus(IStatus runningStatus) {
		this.runningStatus = runningStatus;
	}

	@Override
	public void bxTargetChanged(BxDebugEvent event) {
		
	}
	
	public List<EObject> getRunnedActivity(){
		return runned;
	}
	
	private EObject getEndActivity(BxThread bxThread){
		/*ProcessLink[] links = ((ProcessInstance)bxThread.getProcessElement()).getLinks();
		for (int i = 0; i < links.length; i++) {
			com.tibco.bx.debug.common.model.ProcessNode node = links[i].getTo();
			String state = node.getState();
			if(!state.endsWith("skipped.state")){ //$NON-NLS-1$
				Activity activity = process.getActivity(node.getIndex());
				if(activity != null){
					List<Transition> tList = activity.getOutgoingTransitions();
					if(tList.isEmpty()){
						return activity;
					}
				}
			}
		}*/
		List<ProcessElement> elements = ((ProcessInstance) bxThread.getProcessElement()).getElements();
		for (ProcessElement element : elements) {
			if (element instanceof ProcessVisibleNode) {
				ProcessVisibleNode node = (ProcessVisibleNode) element;
				String state = node.getState();
				if (!state.endsWith("skipped")) { //$NON-NLS-1$
					EObject activity = ProcessUtil.getActivity(bxThread.getProcessElement().getIndex(), node.getIndex(), ((IBxDebugTarget) bxThread
							.getDebugTarget()).getModelType());
					if(activity != null && ProcessUtil.isEndActivity(activity)){
						return activity;
					}
					/*
					 * TODO if(activity != null){ List<Transition> tList =
					 * activity.getOutgoingTransitions(); if(tList.isEmpty()){
					 * return activity; } }
					 */
				}
			}
		}
		return null;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if(event.getSelection() instanceof StructuredSelection){
			StructuredSelection selection = (StructuredSelection) event.getSelection();
			EObject activity = (EObject) selection.getFirstElement();
			updateDisplayPane(activity);
		}
	}

	private void updateDisplayPane(EObject activity) {
		currentData.clear();
		if(activity == startActivity){
			currentData.put(SOAP_INPUT, processNode.getInput() != null ? processNode.getInput().getSoapMessage() : ""); //$NON-NLS-1$
			currentData.put(SOAP_OUTPUT,(processNode.getOutput() !=null && processNode.getOutput().getSoapMessage()!= null) ? processNode.getOutput().getSoapMessage(): ""); //$NON-NLS-1$
			if(!ProcessUtil.isWebServiceImplementationActivity(activity)){
				currentData.put(PARA_INPUT, getInputFromProcessNode(processNode));
				currentData.put(PARA_OUTPUT, getOutFromProcessNode(processNode));
			}
		}else if(processNode.getMultiInputNodes().size()>0){
			for(MultiInput input:processNode.getMultiInputNodes()){
				if(input.getId().equals(ProcessUtil.getElementId(activity))){
					currentData.put(SOAP_INPUT, input.getSoapMessage() != null ? input.getSoapMessage() : ""); //$NON-NLS-1$
					currentData.put(SOAP_OUTPUT,input.getResponseMessage()!=null ? input.getResponseMessage() : "");  //$NON-NLS-1$
				}
			}
		}else{
			IntermediateInput input = processNode.getIntermediateById(ProcessUtil.getElementId(activity));
			if(input != null){
				currentData.put(SOAP_INPUT, input.getRequestMessage()!=null ?  input.getRequestMessage():""); //$NON-NLS-1$
				currentData.put(SOAP_OUTPUT, input.getResponseMessage() !=null ? input.getResponseMessage():""); //$NON-NLS-1$
			}
		}
		if(processTabPane != null){
			processTabPane.refresh();
		}
	}
	
	private IInOutElement getOutFromProcessNode(ProcessNode node){
		IInOutElement outPut = null;
		if(node != null && node.getOutput() != null && StringUtils.isNotBlank(node.getOutput().getId())){
			outPut = EmulationUtil.createOutputElement(node.getOutput(), node);
		}
		return outPut;
	}
	
	private IInOutElement getInputFromProcessNode(ProcessNode node){
		IInOutElement input = null;
		if(node != null && node.getInput() != null ){
			input = EmulationUtil.createInputElement(node.getInput(), node);
		}
		return input;
	}
	
	public Object getData4Show(String key){
		return currentData.get(key);
	}

	@Override
	public IInOutElement getInputElement() {
		return inputElement;
	}
	
	private void appendIntermediateInput(IntermediateEventHandle handle){
		runned.add(handle.getActivity());
		IntermediateInput input = EmulationFactory.eINSTANCE.createIntermediateInput();
		input.setId(ProcessUtil.getElementId(handle.getActivity()));
		input.setName(ProcessUtil.getDisplayName(handle.getActivity()));
		input.setRequestMessage(handle.getRequestMsg());
		input.setResponseMessage(handle.getResponseMsg());
		processNode.getIntermediateInputs().add(input);
		updateTab();
	}
	
	private void updateTab(){
		UIJob job = new UIJob(Messages.getString("ProcessInstanceController.UIJob.label")) { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if(processTabPane instanceof InvokeProcessTabPane){
					((InvokeProcessTabPane)processTabPane).setInput(runned);
				}
				return Status.OK_STATUS;
			}
			@Override
			 public boolean shouldSchedule(){ 
				return processTabPane !=null && !processTabPane.isDisposed();
			}
		};
		job.schedule();
		
	}
	
	@Override
	public boolean isFinished() {
		if(thread != null){
			return ((ProcessInstance)thread.getProcessElement()).isEnded();
		}
		return false;
	}
}
