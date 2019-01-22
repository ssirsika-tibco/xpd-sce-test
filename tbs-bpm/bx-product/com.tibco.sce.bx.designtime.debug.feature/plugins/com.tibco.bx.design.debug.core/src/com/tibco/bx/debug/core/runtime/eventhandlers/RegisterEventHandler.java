package com.tibco.bx.debug.core.runtime.eventhandlers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;

import com.tibco.bx.debug.common.model.IBxNodeDefinition;
import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.runtime.INodeDefinitionFactory;
import com.tibco.bx.debug.core.runtime.events.BxRuntimeEvent;
import com.tibco.bx.debug.core.runtime.events.BxTemplateRegisterEvent;
import com.tibco.bx.debug.core.runtime.internal.NodeDefinitionFactoryManager;

public class RegisterEventHandler implements IRuntimeEventHandler {

	private final IBxDebugTarget debugTarget;

	public RegisterEventHandler(IBxDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	@Override
	public boolean handleRuntimeEvent(BxRuntimeEvent re) throws CoreException {
		BxTemplateRegisterEvent event = (BxTemplateRegisterEvent) re;
		
		ProcessTemplate processTemplate = new ProcessTemplate(event.getProcessId(), 
				event.getProcessName(), event.getModuleName(), event.getModuleVersion());
		IBxProcessListing processListing = debugTarget.getProcessListing();
		if (processListing != null) {
			processListing.registerProcessTemplate(processTemplate);
		}

		INodeDefinitionFactory factory = NodeDefinitionFactoryManager.getNodeDefinitionFactory(debugTarget.getModelType());
		IBxNodeDefinition[] definitions = factory.createNodeDefinitions(processTemplate.getProcessId());
		if(definitions.length != 0)
			debugTarget.getDebugger().setNodeDefinitions(processTemplate.getProcessId(), definitions);
		
		IBreakpoint[] breakPts = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(debugTarget.getModelIdentifier());
		if( DebugPlugin.getDefault().getBreakpointManager().isEnabled()){
			for (IBreakpoint breakpoint : breakPts) {
				BxBreakpoint bb = (BxBreakpoint) breakpoint;
				if(bb.getProcessId().equals(processTemplate.getProcessId()))
					debugTarget.breakpointAdded(breakpoint);
			}
		}
		//TODO add testpointa and assertions
		return true;
	}
}
