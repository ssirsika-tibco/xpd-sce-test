package com.tibco.bx.debug.bpm.ui.annotations;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;

import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.core.models.IBxMarker;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

public class XpdlFileChangeListener implements IResourceChangeListener, IResourceDeltaVisitor{
	private final static String XPDL_FILE_EXTENSION = "xpdl";//$NON-NLS-1$
	public void resourceChanged(IResourceChangeEvent event) {
		try {
			if(event.getDelta()!=null){
				event.getDelta().accept(this);
			}
		} catch (CoreException e) {
			//can't happen
		}
	}
	
	public boolean visit(IResourceDelta delta) throws CoreException {
		IResource resource = delta.getResource();
		if(resource.getType() == IResource.FILE 
				&& delta.getKind() == IResourceDelta.CHANGED 
				&& (delta.getFlags() & IResourceDelta.CONTENT) == IResourceDelta.CONTENT
				&& null!=resource.getFileExtension()
				&& resource.getFileExtension().equalsIgnoreCase(XPDL_FILE_EXTENSION)) { 
		        removeBreakpoints(resource);
		        return false;
		}
		return true;
	}
	
	/**
	 * remove BXBreakpoints from modified xpdl file if related activities are removed.
	 * @param resource: .xpdl file 
	 * @throws CoreException
	 */
	private void removeBreakpoints(final IResource resource){
		final WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
		if (wc == null || !(wc.getRootElement() instanceof Package)) 
			return;
		//process all processes
		//removing breakpoints in main thread conflicts with saving resource;
		new Job(""){
				public IStatus run(IProgressMonitor monitor) {
				EList processes = ((Package) wc.getRootElement()).getProcesses();
				List<String> activityIds = new ArrayList<String>();
				for (int i = 0; i < processes.size(); i++) {
					Process process = (Process) processes.get(i);
					EList activities = process.getActivities();
					initActivityIds(process, activities, activityIds);
				}
				try {
					IMarker[] markers = resource.findMarkers(IBxMarker.MARKER_TYPE, true,
							IResource.DEPTH_INFINITE);
					for (int i = 0; i < markers.length; i++) {
						String xpdlId=null;
						xpdlId = (String)markers[i].getAttribute(IMarker.LOCATION);
						if(!activityIds.contains(xpdlId)){
							markers[i].delete();
						}
					}
				} catch (CoreException e) {
					DebugBPMUIActivator.log(e);
				}
				return Status.OK_STATUS;
			}
		}.schedule();
	}
	
	private void initActivityIds(Process process, List activities, List activityIds){
		for(Object object:activities){
			Activity activity = (Activity)object;
			String activityId = ((Activity) object).getId();
			activityIds.add(activityId);
			if(activity.getBlockActivity() !=null){//sub Process
				ActivitySet activitySet =process.getActivitySet(activity.getBlockActivity().getActivitySetId());
				initActivityIds(process, activitySet.getActivities(), activityIds);
			}
		}
	}
}

