package com.tibco.bx.emulation.ui.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewPart;

import com.tibco.bx.emulation.model.EmulationData;

public interface IEmulationView extends IViewPart{
	public Viewer getViewer();
	public EmulationData getEmulationData();
	public boolean hasElements();// has any Testpoints or Assertions or Inputs or Outputs
	public void setSpecialInstanceTab(String instanceId);
}
