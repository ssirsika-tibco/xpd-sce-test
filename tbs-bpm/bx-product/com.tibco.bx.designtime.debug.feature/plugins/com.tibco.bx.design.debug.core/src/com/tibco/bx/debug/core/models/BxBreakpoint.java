package com.tibco.bx.debug.core.models;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.api.ConditionLanguage;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.invoke.util.StringUtils;

public class BxBreakpoint extends Breakpoint {
	/**
	 * Default constructor is required for the breakpoint manager
	 * to re-create persisted breakpoints. After instantiating a breakpoint,
	 * the <code>setMarker(...)</code> method is called to restore
	 * this breakpoint's attributes.
	 */
	public BxBreakpoint() {
	}
	
	public BxBreakpoint(final IFile file, final String processId, final String location, final String modelType, final BreakWhen when, final boolean isEnabled, final String label) throws CoreException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = file.createMarker(IBxMarker.MARKER_TYPE);
				setMarker(marker);
				marker.setAttribute(IBreakpoint.ENABLED, isEnabled);
				marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
				marker.setAttribute(IBxMarker.PROCESS_ID, processId);
				marker.setAttribute(IMarker.LOCATION, location);
				marker.setAttribute(IBxMarker.MODEL_TYPE, modelType);
				marker.setAttribute(IBxMarker.BREAKWHEN, when.toString());
				marker.setAttribute(IMarker.MESSAGE, label);//for display in breakpoints view
				marker.setAttribute(IBxMarker.CONDITION_ENABLED, false);
				marker.setAttribute(IBxMarker.CONDITION, ""); //$NON-NLS-1$
				marker.setAttribute(IBxMarker.CONDITIONLANGUAGE, ConditionLanguage.XPATH.toString());
			}
		};
		run(getMarkerRule(file), runnable);
	}

	@Override
	public void setMarker(IMarker marker) throws CoreException {
		super.setMarker(marker);
	}
	
	public String getModelIdentifier() {
		return DebugCoreActivator.ID_BX_DEBUG_MODEL+ "." + getModelType(); //$NON-NLS-1$
	}
	
	public String getProcessId() {
		try {
			return (String) getMarker().getAttribute(IBxMarker.PROCESS_ID);
		} catch (CoreException e) {
			return null;
		}
	}

	public String getLocation() {
		try {
			return (String) getMarker().getAttribute(IMarker.LOCATION);
		} catch (CoreException e) {
			return null;
		}
	}

	public String getModelType() {
		try {
			return (String) getMarker().getAttribute(IBxMarker.MODEL_TYPE);
		} catch (CoreException e) {
			return null;
		}
	}
	
	public BreakWhen getBreakWhen() {
		try {
			return BreakWhen.valueOf((String) getMarker().getAttribute(IBxMarker.BREAKWHEN));
		} catch (CoreException e) {
			return null;
		}
	}

	public boolean isEnabled() throws CoreException{
		return (Boolean) getMarker().getAttribute(IBreakpoint.ENABLED);
	}
	
	public void setEnabled(boolean enabled) throws CoreException {
		if (enabled != isEnabled()) {
			setAttribute(IBreakpoint.ENABLED, enabled);
		}
		
	}

	public boolean isConditionEnabled(){
		try {
			return (Boolean) getMarker().getAttribute(IBxMarker.CONDITION_ENABLED);
		} catch (CoreException e) {
			return false;
		}
	}
	
	public void setConditionEnabled(boolean enableCondition) throws CoreException {
		if (enableCondition != isConditionEnabled()) {
			setAttribute(IBxMarker.CONDITION_ENABLED, enableCondition);
		}
		
	}

	public String getCondition(){
		try {
			return (String) getMarker().getAttribute(IBxMarker.CONDITION);
		} catch (CoreException e) {
			return null;
		}
	}
	
	public void setCondition(String condition) throws CoreException {
		if (!condition.equals(getCondition())) {
			setAttribute(IBxMarker.CONDITION, condition);
		}
	}
	
	public ConditionLanguage getConditionLanguage(){
		try {
			return ConditionLanguage.valueOf((String) getMarker().getAttribute(IBxMarker.CONDITIONLANGUAGE));
		} catch (CoreException e) {
			return null;
		}
	}
	
	public void setConditionLanguage(ConditionLanguage conditionLanguage) throws CoreException {
		if (!conditionLanguage.equals(getConditionLanguage())) {
			setAttribute(IBxMarker.CONDITIONLANGUAGE, conditionLanguage.toString());
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		
		if (!(o instanceof BxBreakpoint)) {
			return false;
		}
		
		BxBreakpoint other = (BxBreakpoint) o;
		if (!this.getMarker().getResource().equals(other.getMarker().getResource())) {
			return false;
		}
		
		return StringUtils.equals(this.getProcessId(), other.getProcessId())
				&& StringUtils.equals(this.getLocation(), other.getLocation())
				&& StringUtils.equals(this.getModelType(), other.getModelType())
				&& StringUtils.equals(this.getBreakWhen(), other.getBreakWhen());
	}
}
