package com.tibco.bx.debug.core.runtime;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.common.model.IBxNodeDefinition;
import com.tibco.bx.debug.common.model.ProcessContainer;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.bx.debug.core.models.variable.IVariableHandler;

public interface IBxDebugger {

	String connect(String modelType) throws CoreException;
	
	void disconnect() throws CoreException;

	void resumeAll() throws CoreException;

	/**
	 * @param instanceId instanceId is process instance id
	 * @throws CoreException
	 */
	void resume(String instanceId) throws CoreException;

	void suspendAll() throws CoreException;

	/**
	 * @param instanceId instanceId is process instance id
	 * @throws CoreException
	 */
	void suspend(String processId) throws CoreException;

	void terminate() throws CoreException;

	/**
	 * @param instanceId instanceId is process instance id
	 * @throws CoreException
	 */
	void terminate(String instanceId) throws CoreException;

	/**
	 * @param instanceId instanceId is activity instance id
	 * @throws CoreException
	 */
	boolean canStepInto(String instanceId) throws CoreException;
	
	/**
	 * @param instanceId instanceId is activity instance id
	 * @throws CoreException
	 */
	void stepInto(String instanceId) throws CoreException;

	/**
	 * @param instanceId instanceId is activity instance id
	 * @throws CoreException
	 */
	void stepOver(String instanceId) throws CoreException;
	
	/**
	 * @param instanceId instanceId is activity instance id
	 * @throws CoreException
	 */
	boolean canStepReturn(String instanceId) throws CoreException;
	
	/**
	 * @param instanceId instanceId is activity instance id
	 * @throws CoreException
	 */
	void stepReturn(String instanceId) throws CoreException;
	
	/**
	 * @param instanceId instanceId is process instance id
	 * @throws CoreException
	 */
	void runToActivity(String instanceId, String location, BreakWhen breakWhen) throws CoreException;

	void addBreakpoint(BxBreakpoint bxBreakpoint) throws CoreException;

	void modifyBreakpoint(BxBreakpoint bxBreakpoint) throws CoreException;

	void removeBreakpoint(BxBreakpoint bxBreakpoint) throws CoreException;

	void removeAllBreakpoints() throws CoreException;

	/**
	 * @param variableHandler
	 * @param processContainer processContainer is ProcessInstance or ProcessVisibleNode
	 * @return process-level variables and scope-level local variables
	 * @throws CoreException
	 */
	ProcessVariable[] getProcessVariables(IVariableHandler variableHandler, ProcessContainer processContainer) throws CoreException;
	
	
	/**
	 * @param processInstance
	 * @return name and string value of process-level variables.
	 * @throws CoreException
	 */
	Map<String, String> getProcessVariableValues(ProcessInstance processInstance) throws CoreException;
	
	/**
	 * @param instanceId instanceId is process instance id or scope activity instance id.<p>
	 * @throws CoreException
	 */
	boolean setVariable(String instanceId, String variableName, String variableValue) throws CoreException;

	boolean setNodeDefinitions(String processId, IBxNodeDefinition[] definitions) throws CoreException;
}