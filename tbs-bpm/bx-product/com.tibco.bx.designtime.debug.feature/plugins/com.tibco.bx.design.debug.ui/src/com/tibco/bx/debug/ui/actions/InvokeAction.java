package com.tibco.bx.debug.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.invoke.launcher.IProcessLauncher;
import com.tibco.bx.debug.core.invoke.launcher.ProcessLauncherManager;
import com.tibco.bx.debug.core.launching.SOAPProcessLauncher;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.runtime.IProcessInstanceController;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.bx.debug.ui.invoke.launcher.IResponseBackListener;
import com.tibco.bx.debug.ui.views.internal.TargetManager;
import com.tibco.bx.emulation.core.common.IActivityElement;

public class InvokeAction extends Action implements ISelectionChangedListener {

	private IProcessLauncher processLauncher;
	private ProcessTemplate processTemplate;
	private boolean initialized = false;
	private EObject startActivity;
	private ListenerList responseBackListeners;
	private static final String BPM_TYPE = "bpm"; //$NON-NLS-1$

	public InvokeAction() {
		super();
		responseBackListeners = new ListenerList();
	}

	public InvokeAction(EObject startActivity) {
		this.startActivity = startActivity;
	}

	public void addResponseBackListeners(IResponseBackListener listener) {
		responseBackListeners.add(listener);
	}

	public void removeResponseBackListeners(IResponseBackListener listener) {
		responseBackListeners.remove(listener);
	}

	@Override
	public boolean isEnabled() {
		return !(getProcessListing() == null || processTemplate == null || !isInitialized());
	}

	private boolean isInitialized() {
		return initialized;
	}

	public void init(IActivityElement inputParameter) throws CoreException {
		assert (getProcessListing() != null);
		processLauncher = ProcessLauncherManager.instance.getProcessLauncher(startActivity, processTemplate, getProcessListing(), null);
		if (BPM_TYPE.equals(getProcessListing().getConnection().getModelType())) {
			processLauncher.setProcessListing(getProcessListing());
		}
		processLauncher.setInput(inputParameter);
		initialized = true;
	}

	public void init(String requestSoapMessage, String endpointUrl) throws CoreException {
		assert (getProcessListing() != null);
		if(processTemplate ==null){
			String id = ((com.tibco.xpd.xpdl2.Process)ProcessUtil.getProcess(startActivity)).getId();
			IBxProcessListing listing  = getProcessListing();
			ProcessTemplate[] temples = listing.getProcessTemplates(id);
			if(temples.length>0){
				processTemplate = temples[0];
			}
		}
		processLauncher = ProcessLauncherManager.instance.getProcessLauncher(startActivity, processTemplate, getProcessListing(), endpointUrl);
		// this just for soap launcher
		if (processLauncher instanceof SOAPProcessLauncher) {
			((SOAPProcessLauncher) processLauncher).setSoapRequestMessage(requestSoapMessage);
		}
		initialized = true;
	}

	@Override
	public void run() {
	}

	public Object run(ILauncherEventHandler responseHandle) {
		// after launch , i must save the runtime response .
		Object result = null;
		if (processLauncher != null) {
			result = processLauncher.launch(responseHandle);
			if (result == null && responseHandle instanceof IProcessInstanceController) {
				TargetManager.getDefault().getCurrentTarget().addBxTheadChangedListener(null, (IProcessInstanceController) responseHandle);
			} else if (result instanceof String && !"".equals(((String) result).trim()) && //$NON-NLS-1$
					responseHandle instanceof IProcessInstanceController) {
				TargetManager.getDefault().getCurrentTarget().addBxTheadChangedListener((String) result, (IProcessInstanceController) responseHandle);
			}
		}
		return result;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (!event.getSelection().isEmpty()) {
			Object firstElement = ((IStructuredSelection) event.getSelection()).getFirstElement();

			if (firstElement instanceof EObject) {
				startActivity = (EObject) firstElement;
			} else if (firstElement instanceof ProcessTemplate) {
				processTemplate = (ProcessTemplate) firstElement;
			}
		}
	}

	public EObject getStartActivity() {
		return startActivity;
	}

	public IBxProcessListing getProcessListing() {
		if (TargetManager.getDefault().getCurrentTarget() != null) {
			return TargetManager.getDefault().getCurrentTarget().getProcessListing();
		}
		return null;
	}

}
