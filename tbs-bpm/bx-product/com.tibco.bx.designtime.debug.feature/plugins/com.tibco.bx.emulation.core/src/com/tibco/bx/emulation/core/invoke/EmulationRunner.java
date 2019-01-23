package com.tibco.bx.emulation.core.invoke;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.invoke.launcher.IProcessLauncher;
import com.tibco.bx.debug.core.invoke.launcher.ProcessLauncherManager;
import com.tibco.bx.debug.core.invoke.util.StringUtils;
import com.tibco.bx.debug.core.models.BxDebugElement;
import com.tibco.bx.debug.core.models.BxDebugEvent;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.models.IBxDebugListener;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.bx.emulation.core.EmulationCacheManager;
import com.tibco.bx.emulation.core.EmulationCoreActivator;
import com.tibco.bx.emulation.core.Messages;
import com.tibco.bx.emulation.core.service.IBxTestDelegateService;
import com.tibco.bx.emulation.model.EmulationData;
import com.tibco.bx.emulation.model.EmulationFactory;
import com.tibco.bx.emulation.model.EmulationPackage;
import com.tibco.bx.emulation.model.Input;
import com.tibco.bx.emulation.model.Output;
import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.bx.emulation.model.Testpoint;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class EmulationRunner implements IBxDebugListener {
	
	private ListenerList emulationRunnerlisteners = new ListenerList();
	private String instanceId;
	private String processId;
	
	private BxDebugTarget debugTarget;
	private EmulationRunStatus status;
	public void reset() {
	    status =  EmulationRunStatus.WAITING;
	}
	public EmulationRunStatus getStatus() {
        return status;
    }

    public EmulationRunner(BxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
		status = EmulationRunStatus.WAITING;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void addEmulationRunnerListener(IEmulationRunnerListener listener){
		emulationRunnerlisteners.add(listener);
	}
	
	public void removeEmulationRunnerListener(IEmulationRunnerListener listener){
		emulationRunnerlisteners.remove(listener);
	}
	
	private void fireEmulationRunnerUpdate(int eventType, String message, Object eventData) {
		Object[] listeners = emulationRunnerlisteners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			IEmulationRunnerListener listener = (IEmulationRunnerListener) listeners[i];
            listener.updateExecutionStatus(eventType, message, eventData);
		}
	}

	synchronized public void executeEmulation(ProcessNode processNode , ILauncherEventHandler handler, IProgressMonitor monitor) throws CoreException {
		
		installTestpoints(processNode);
		
		try {
		    debugTarget.addBxTheadChangedListener(null,this);
			instanceId = invokeOperation(processNode , handler);
		} catch (CoreException e) {
			MultiStatus mstatus = new MultiStatus(
					EmulationCoreActivator.PLUGIN_ID ,Status.ERROR , new IStatus[]{e.getStatus()}, 
												 Messages.getString("EmulationRunner.status0.message") ,e.getCause()) ; //$NON-NLS-1$
			status = EmulationRunStatus.FAULT;
			throw new CoreException(mstatus);
		}
		
		try {
			wait();
		} catch (InterruptedException e) {
			throw new CoreException(DebugCoreActivator.newStatus(e, Messages.getString("EmulationRunner.status1.message"))); //$NON-NLS-1$
		}
		
	}
	
	private void installTestpoints(ProcessNode processNode) throws CoreException {

		ProcessNode clonNode = (ProcessNode) EcoreUtil.copy(processNode);
		EmulationData currentData = EmulationCacheManager.getDefault().getCurrentEmulationData();
		EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(currentData);
		EStructuralFeature eStructuralFeature = EmulationPackage.eINSTANCE.getEmulationData_ProcessNodes();

		Collection<ProcessNode> toRemoveList = new ArrayList<ProcessNode>();
		for (ProcessNode pd : currentData.getProcessNodes()) {
			if (StringUtils.equal(pd.getId(), processNode.getId())) {
				toRemoveList.add(pd);
				List<Testpoint> tps = pd.getTestpoints();
				for (Testpoint tp : tps) {
					IBxTestDelegateService service = EmulationCoreActivator.createTestDelegateService();
					if(service !=null){
						service.removeTestPoint(debugTarget, tp);
					}
				}
			}
		}

		Collection<ProcessNode> toAdd = new ArrayList<ProcessNode>();
		toAdd.add(clonNode);
		for (ProcessNode pd : toAdd) {
			List<Testpoint> tps = pd.getTestpoints();
			for (Testpoint tp : tps) {
				IBxTestDelegateService service = EmulationCoreActivator.createTestDelegateService();
				if(service !=null){
					service.addTestPoint(debugTarget, tp);
				}
			}
		}

		CompoundCommand compoundCommand = new CompoundCommand();
		compoundCommand.append(RemoveCommand.create(editingDomain, currentData, eStructuralFeature, toRemoveList));
		compoundCommand.append(AddCommand.create(editingDomain, currentData, eStructuralFeature, toAdd));
		editingDomain.getCommandStack().execute(compoundCommand);

	
	}
	
	/**
	 * invoke operation to create an instance
	 * return instanceId
	 * @param operationModel
	 * @param testpoint
	 * @return
	 * @throws Throwable
	 */
	private String invokeOperation(ProcessNode processNode , ILauncherEventHandler launcherListener) throws CoreException {
		
	    Object launchResult = null;
	    processId = processNode.getId() ;
	    EObject process = ProcessUtil.getProcess(processNode.getId(), debugTarget.getModelType() );
	    IBxProcessListing processListing = (IBxProcessListing) debugTarget.getAdapter(IBxProcessListing.class);
	    
	    if(process != null){
	    	Input input = processNode.getInput();
	    	EObject startActivity = ProcessUtil.getActivity(process, input.getId());
            if(startActivity == null){
            	throw new CoreException(new Status(IStatus.ERROR,EmulationCoreActivator.PLUGIN_ID,Messages.getString("EmulationRunner.status2.message"))); //$NON-NLS-1$
            }
            if(processNode.getOutput() == null){
            	Output output = EmulationFactory.eINSTANCE.createOutput();
            	processNode.setOutput(output);
            }
            ProcessTemplate[] bxProcessTemplates = processListing.getProcessTemplates(processNode.getId());
            if(bxProcessTemplates != null && bxProcessTemplates.length > 0){

                if(startActivity != null){
                    IProcessLauncher processLauncher = ProcessLauncherManager.instance.getProcessLauncher(startActivity,bxProcessTemplates[0],processListing, null);
                   
                    if(processLauncher.isSOAPMessage() && 
                       StringUtils.isNotBlank(processNode.getInput().getSoapMessage() )) {
                        processLauncher.setSoapRequestMessage((processNode.getInput().getSoapMessage()));
                    }else {
                    	if(processNode.getInput() instanceof Input){
                        processLauncher.setInput(processNode.getInput());
                    	}
                    }
                    launchResult = processLauncher.launch(launcherListener);
            	}
            }
        }
	    
	    return launchResult == null ? null  :  launchResult.toString();
	}
	
	synchronized public void bxThreadChanged(BxDebugEvent event) {
		try {
			BxDebugElement source = (BxDebugElement) event.getSource();
			switch (event.getKind()) {
			case BxDebugEvent.STARTED:
			    if(source instanceof BxThread) {
			        if(StringUtils.equal(processId,((BxThread)source).getProcessElement().getIndex())){
			            instanceId = ((BxThread)source).getInstanceId();
			            status = EmulationRunStatus.RUNNING;
			        }
			    }
			    break;
			case BxDebugEvent.ON_EXIT:
			case BxDebugEvent.COMPLETED:
				if(source instanceof BxThread){
					if(status != EmulationRunStatus.FAULT){
						status = EmulationRunStatus.COMPLETED;
					}
					clean(source);
				}
			   
			    break;
			case BxDebugEvent.FAULT:
				if(source instanceof BxThread){
					status = EmulationRunStatus.FAULT;
				}
			    clean(source);
			    break;
			case BxDebugEvent.TERMINATED:
				if(source instanceof BxThread){
					 status = EmulationRunStatus.TERMINATED;
				}
			    clean(source);
				break;
			case BxDebugEvent.SUSPENDED:
				if (source instanceof BxThread) {
					((BxThread) source).resume();
				}
				break;
			case BxDebugEvent.DISCONNECTED:
				 clean(source);
				 notify();
				status = EmulationRunStatus.DISCONNECTED;
				break;
			default:
				break;
			}
			
			String message = ""; //$NON-NLS-1$
			if(source.getProcessElement() != null){
				message = source.getProcessElement().getIndex() + ": " + event.getKindLiteral(); //$NON-NLS-1$
			}
			fireEmulationRunnerUpdate(event.getKind(), message, source);
			
		} catch (DebugException e) {
			DebugCoreActivator.log(e.getStatus());
		}
	}

    private void clean(BxDebugElement source) {
        if (source instanceof BxThread && source.getInstanceId() != null
                && source.getInstanceId().equals(instanceId)) {
            debugTarget.removeBxTheadChangedListener(null, this);
            notify();
        }
    }

    @Override
    public boolean isAvailable() {
        return false;
    }
    
	@Override
	public void bxTargetChanged(BxDebugEvent event) {
		if(event.getKind() == BxDebugEvent.DISCONNECTED)
			fireEmulationRunnerUpdate(event.getKind(), "", event.getData()); //$NON-NLS-1$
	}

}
