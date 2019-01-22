package com.tibco.bx.debug.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.launching.IntermediateLauncherManager;
import com.tibco.bx.debug.core.launching.MultiNodesLauncherManager;
import com.tibco.bx.debug.core.models.BxDebugTarget;
import com.tibco.bx.debug.core.models.BxStackFrame;
import com.tibco.bx.debug.core.models.BxThread;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.bx.debug.ui.DebugUIActivator;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.views.internal.IProcessTabPane;
import com.tibco.bx.debug.ui.views.internal.IToolBarChangeListener;
import com.tibco.bx.debug.ui.views.internal.SOAPMessageInvokePane;
import com.tibco.bx.debug.ui.views.internal.TargetManager;

public class SoapInvokeActionProxy extends AbstractInvokeActionProxy {

	private SOAPMessageInvokePane invokePane;
	private IToolBarChangeListener toolBarChangeListener;

	public SoapInvokeActionProxy(SOAPMessageInvokePane invokePane,
			IToolBarChangeListener toolBarChangeListener) {
		super(Messages.getString("SoapInvokeActionProxy_Invoke")); //$NON-NLS-1$
		this.invokePane = invokePane;
		this.toolBarChangeListener = toolBarChangeListener;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	private String getMultiInputsProcessId(EObject startActivity)throws CoreException{
		String processId = null;
		if (!ProcessUtil.isIntermediateEvent(startActivity)) {
			String activityId = ProcessUtil.getElementId(startActivity);
			BxDebugTarget target = TargetManager.getDefault()
					.getCurrentTarget();
			IThread[] threads = target.getThreads();
			for (IThread thread : threads) {
				if (!thread.isTerminated()) {
					IStackFrame[] frames = (IStackFrame[]) thread
							.getStackFrames();
					for (IStackFrame frame : frames) {
						ProcessVisibleNode node = (ProcessVisibleNode) ((BxStackFrame) frame)
								.getProcessElement();
						if (ProcessVisibleNode.State.STARTING.equals(node
								.getState())
								&& activityId.equals(node.getIndex())) {
							BxThread bxThread = (BxThread) frame.getThread();
							processId = bxThread.getInstanceId();
							break;
						}
					}
				}
			}
		}
		return processId;
	}
	
	
	@Override
	public void run() {
		try {
			EObject startActivity = getProxy().getStartActivity();
			invokePane.cacheRequestMessage(startActivity);
			String soapMessage = invokePane.getRequestSoapMessage();
			String endpointUrl = invokePane.getEndpointUrl();
			getProxy().init(soapMessage, endpointUrl);
			String processId = getMultiInputsProcessId(startActivity);
			
			if (!ProcessUtil.isIntermediateEvent(startActivity) && processId==null) {
				IProcessTabPane processTabPane = processTabPaneCreator
						.createIProcessTabPane();
				getProxy().run(processTabPane.getController());
			} else if(processId !=null){
				 IProcessTabPane processTabPane = processTabPaneCreator.getProcessInstanceController(processId,startActivity);
				 if(processTabPane !=null){
					 ILauncherEventHandler handle = MultiNodesLauncherManager.instance
					 		.createLauncherEventHandle(getProxy()
					 				.getStartActivity(),processTabPane.getController());
				    getProxy().run(handle);
				 }else{
					 getProxy().run(MultiNodesLauncherManager.instance
						 		.createLauncherEventHandle(getProxy()
						 				.getStartActivity()));
				 }
			  } else {
				getProxy().run(
						IntermediateLauncherManager.instance
								.createLauncherEventHandle(getProxy()
										.getStartActivity()));
			}
			toolBarChangeListener.updateToolBar(true);
		} catch (CoreException e) {
			DebugUIActivator.log(e);
		}
	}

}
