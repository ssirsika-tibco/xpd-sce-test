package com.tibco.bx.debug.core;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchesListener;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.tibco.bx.debug.core.invoke.launcher.ILauncherService;

public class BxLaunchListener implements ILaunchesListener{

	@Override
	public void launchesAdded(ILaunch[] launches) {
		try {
			Assert.isTrue(launches.length>0);
			ILaunchConfigurationType type = launches[0].getLaunchConfiguration().getType();
			String id = type.getIdentifier();
			if (id.equals(DebugCoreActivator.BPEL_LAUNCH_CONFIGURATION_TYPE)){
				Display.getDefault().asyncExec(new Runnable() {
			        		public void run() {
			        			try {
			        				IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			        				IWorkbenchPage page = workbenchWindow.getActivePage();
			        				if(page != null){
			        					IPerspectiveDescriptor activePerspective = page.getPerspective();
			        			        if (activePerspective == null) {
			        			            return;
			        			        }
			        			        if (activePerspective == null || !activePerspective.getId().equals(IDebugUIConstants.ID_DEBUG_PERSPECTIVE)) {	
			        			        	PlatformUI.getWorkbench().showPerspective(IDebugUIConstants.ID_DEBUG_PERSPECTIVE, workbenchWindow);
			        			        }
			        				}
			                    } catch (WorkbenchException e) {
			                    	DebugCoreActivator.log(e);
			                    }

							}

			        	});
			}
		} catch (CoreException e) {
			DebugCoreActivator.log(e);
		}
	}

	@Override
	public void launchesChanged(ILaunch[] launches) {
	}

	@Override
	public void launchesRemoved(ILaunch[] launches) {
	}

	public void connectionClosed(){
		ILauncherService service = DebugCoreActivator.createILaunchService();
		if(service !=null){
			service.connectionClosed();
		}
	}
}
