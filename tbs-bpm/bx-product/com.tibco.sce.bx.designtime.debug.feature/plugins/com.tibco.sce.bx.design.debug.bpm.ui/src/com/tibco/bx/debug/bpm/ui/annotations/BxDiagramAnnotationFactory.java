package com.tibco.bx.debug.bpm.ui.annotations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManagerListener;
import org.eclipse.debug.core.IBreakpointsListener;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.bpm.core.DebugBPMActivator;
import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.bpm.ui.figures.AnnotationFigure;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.xpd.processwidget.annotations.AnnotationFactory;
import com.tibco.xpd.processwidget.annotations.AnnotationListener;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.UniqueIdElement;

public class BxDiagramAnnotationFactory implements AnnotationFactory{

	private Set<AnnotationListener> listenerSet = new HashSet<AnnotationListener>();
	private Map<AnnotationListener, BxBreakpoint> abMap = new HashMap<AnnotationListener, BxBreakpoint>();
	private Set<BxBreakpoint> breakpointSet = new HashSet<BxBreakpoint>();
	private Map<AnnotationListener, AnnotationFigure> figureMap = new HashMap<AnnotationListener, AnnotationFigure>();
	
	private IBreakpointsListener breakpointsListener = new IBreakpointsListener() {
		public void breakpointsAdded(IBreakpoint[] breakpoints) {
			if(addBxBreakpoint(breakpoints)>0){
				for (AnnotationListener listener : listenerSet) {
					modifyAnnotation(listener);
				}
			}
		}
		public void breakpointsRemoved(IBreakpoint[] breakpoints, IMarkerDelta[] deltas) {
			if(removeBxBreakpoint(breakpoints)>0){
				for (AnnotationListener listener : listenerSet) {
					modifyAnnotation(listener);
				}
			}
		}
		public void breakpointsChanged(IBreakpoint[] breakpoints, IMarkerDelta[] deltas) {
			for (AnnotationListener listener : listenerSet) {
				modifyAnnotation(listener);
			}
		}
		
		private int addBxBreakpoint(IBreakpoint[] breakpoints){
			int num=0;
			for (int i = 0; i < breakpoints.length; i++) {
				if(breakpoints[i] instanceof BxBreakpoint){
					if(breakpointSet.add((BxBreakpoint)breakpoints[i]))
						num++;
				}
			}
			return num;
		}
		
		private int removeBxBreakpoint(IBreakpoint[] breakpoints){
			int num=0;
			for (int i = 0; i < breakpoints.length; i++) {
				if(breakpoints[i] instanceof BxBreakpoint){
					if(breakpointSet.remove((BxBreakpoint)breakpoints[i]))
						num++;
				}
			}
			return num;
		}
		
	};
	
	private IBreakpointManagerListener breakpointManagerListener = new IBreakpointManagerListener(){
		public void breakpointManagerEnablementChanged(boolean enabled) {
			for (AnnotationListener listener : listenerSet) {
				modifyAnnotation(listener);
			}
		}
	};
	
	public BxDiagramAnnotationFactory() {
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointManagerListener(breakpointManagerListener);
		DebugPlugin.getDefault().getBreakpointManager().addBreakpointListener(breakpointsListener);
		init();
	}
    
    public void dispose() {
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointListener(breakpointsListener);
		DebugPlugin.getDefault().getBreakpointManager().removeBreakpointManagerListener(breakpointManagerListener);
    }
    
	public IFigure createFigure(AnnotationListener listener, Object model, IFigure parent) {
		if(model instanceof Activity){
			try {
				BxBreakpoint breakpoint = findBreakPoint((UniqueIdElement)model);
				AnnotationFigure fig=null;	
				BreakWhen bw=null;
				boolean isEnabled=false;
				if(breakpoint!=null){
					bw = (breakpoint).getBreakWhen();
					isEnabled = breakpoint.isEnabled();
					abMap.put(listener, ((BxBreakpoint)breakpoint));
				}
				fig = figureMap.get(listener);
				if(fig == null){
					fig = new AnnotationFigure(parent,bw,isEnabled,null);
					figureMap.put(listener, fig);
				}else{
					fig.setBreakWhen(bw);
					fig.setBreakpointEnabled(isEnabled);
				}
				return fig;
			} catch (CoreException e) {
				DebugBPMUIActivator.log(e);
			}
		}
		return null;
	}

	public void registerListener(AnnotationListener listener) {
		listenerSet.add(listener);
		modifyAnnotation(listener);
	}

	public void unregisterListener(AnnotationListener listener) {
		listenerSet.remove(listener);
		BxBreakpoint breakpoint = abMap.get(listener);
		if(breakpoint!=null){
			EObject object = (EObject)((EditPolicy)listener).getHost().getModel();
			if(object.eContainer()==null){//deleted model 
				try {
					DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint(breakpoint, true);
				} catch (CoreException e) {
					DebugBPMUIActivator.log(e);
				}
			}
		}
		
		abMap.remove(listener);
		figureMap.remove(listener);
		listener.removeAnnotations();
	}

	private void init(){
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().
			getBreakpoints(DebugCoreActivator.ID_BX_DEBUG_MODEL+"." + DebugBPMActivator.BPM_MODEL_TYPE);//$NON-NLS-1$
		for(IBreakpoint breakpoint : breakpoints){
			breakpointSet.add((BxBreakpoint)breakpoint);
		}
		breakpoints = DebugPlugin.getDefault().getBreakpointManager().
		getBreakpoints(DebugCoreActivator.ID_BX_DEBUG_MODEL+"." + DebugBPMActivator.PAGEFLOW_MODEL_TYPE);//$NON-NLS-1$
		for(IBreakpoint breakpoint : breakpoints){
			breakpointSet.add((BxBreakpoint)breakpoint);
		}
	}
	private BxBreakpoint findBreakPoint(UniqueIdElement model){
		if(model != null && model.eResource() != null){
			String modelFilePath = model.eResource().getURI().toPlatformString(true);
			String breakpointFilePath = null;
			for(BxBreakpoint breakpoint : breakpointSet){
				try {
					String location = (String)breakpoint.getMarker().getAttribute(IMarker.LOCATION);
					breakpointFilePath = breakpoint.getMarker().getResource().getFullPath().toString();
					if(modelFilePath.equals(breakpointFilePath) &&  model.getId().equals(location))
						return breakpoint;
				} catch (CoreException e) {
					DebugBPMUIActivator.log(e);
				}
			}
		}
		return null;
	}
	
	private void modifyAnnotation(final AnnotationListener listener) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (!display.isDisposed()) {
			display.syncExec(new Runnable() {
				public void run() {
					listener.removeAnnotations();
					listener.createAnnotations();
				}
			});
		}
    }

	public void disposeFactory() {
	}
	

}
