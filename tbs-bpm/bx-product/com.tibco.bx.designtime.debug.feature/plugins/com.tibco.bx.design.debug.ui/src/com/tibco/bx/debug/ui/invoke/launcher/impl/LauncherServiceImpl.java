package com.tibco.bx.debug.ui.invoke.launcher.impl;

import org.eclipse.debug.internal.ui.viewers.model.provisional.IElementContentProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.wsdl.Operation;

import com.tibco.bx.debug.core.invoke.launcher.ILauncherService;
import com.tibco.bx.debug.operation.ILauncherEventHandler;
import com.tibco.bx.debug.ui.Messages;
import com.tibco.bx.debug.ui.dialogs.EndPointCollectDialog;
import com.tibco.bx.debug.ui.models.BxNodeContentProvider;
import com.tibco.bx.debug.ui.models.BxThreadContentProvider;
import com.tibco.bx.debug.ui.util.URLInputDialog;
import com.tibco.bx.debug.ui.views.internal.EmulationProcessInstanceController;

public class LauncherServiceImpl implements ILauncherService {

	private Operation operation = null;
	private static final BxNodeContentProvider BX_NODE_CONTENT_PROVIDER = new BxNodeContentProvider();
	private static final BxThreadContentProvider BX_THREAD_CONTENT_PROVIDER = new BxThreadContentProvider(); 
	@Override
	public String getEndPointDialog(){
		String endpointUrl = ""; //$NON-NLS-1$
		EndPointCollectDialog input = new EndPointCollectDialog(null, Messages.getString("SOAPProcessLauncher_Title"), //$NON-NLS-1$
				Messages.getString("SOAPProcessLauncher_EndPointCollectMsg"), //$NON-NLS-1$
				endpointUrl, new EndPointValidator());
		if (Window.OK == input.open()) {
			endpointUrl = input.getValue();
		}
		return endpointUrl;
	}

	@Override
	public boolean isEmulationProcessInstanceController(
			ILauncherEventHandler handler) {
		if(handler instanceof EmulationProcessInstanceController){
			return true;
		}else 
		return false;
	}

	@Override
	public Operation getOperationFromUrl(final EObject startActivity) {
		
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				URLInputDialog input = new URLInputDialog(null);
				input.setStartActivity(startActivity);
				if (Window.OK == input.open()) {
					 operation = (Operation) input.getOperation();
				}
			}

		});
		return operation;
	}

	@Override
	public void connectionClosed() {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				MessageDialog.openConfirm(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						Messages.getString("BxLaunchListener.MessageDialog.title"), //$NON-NLS-1$
						Messages.getString("BxLaunchListener.MessageDialog.message"));//$NON-NLS-1$
			}
		});
	}

	@Override
	public Object getStackFrameAdapter(Class adapter) {
		if(adapter.equals(IElementContentProvider.class)){
			return BX_NODE_CONTENT_PROVIDER;
		}
		return null;
	}

	@Override
	public Object getThreadAdapter(Class adapter) {
		if(adapter.equals(IElementContentProvider.class)){
			return BX_THREAD_CONTENT_PROVIDER;
		}
		return null;
	}

}
