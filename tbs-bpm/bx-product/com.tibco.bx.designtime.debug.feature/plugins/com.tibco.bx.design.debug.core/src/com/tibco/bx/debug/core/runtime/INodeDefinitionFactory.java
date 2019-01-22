package com.tibco.bx.debug.core.runtime;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.common.model.IBxNodeDefinition;

public interface INodeDefinitionFactory {
	
	String getModelType();
	
	IBxNodeDefinition[] createNodeDefinitions(String processId) throws CoreException;

}
