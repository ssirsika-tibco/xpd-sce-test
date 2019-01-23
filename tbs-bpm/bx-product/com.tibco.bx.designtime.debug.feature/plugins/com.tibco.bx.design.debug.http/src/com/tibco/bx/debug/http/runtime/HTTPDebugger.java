package com.tibco.bx.debug.http.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;

import com.tibco.bx.debug.api.BreakWhen;
import com.tibco.bx.debug.api.ConditionLanguage;
import com.tibco.bx.debug.api.IDebugConstants;
import com.tibco.bx.debug.common.model.IBxNodeDefinition;
import com.tibco.bx.debug.common.model.ProcessContainer;
import com.tibco.bx.debug.common.model.ProcessInstance;
import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.Tracing;
import com.tibco.bx.debug.core.models.BxBreakpoint;
import com.tibco.bx.debug.core.models.variable.IVariableHandler;
import com.tibco.bx.debug.core.runtime.IBxDebugger;
import com.tibco.bx.debug.core.runtime.IBxTester;
import com.tibco.bx.debug.core.runtime.IConnection;
import com.tibco.bx.debug.http.HTTPActivator;
import com.tibco.bx.debug.http.Messages;
import com.tibco.bx.debug.http.util.HTTPDataManager;
import com.tibco.bx.tester.Assertion;
import com.tibco.bx.tester.Testpoint;
import com.tibco.www.bx._2010.debugging.debugger.Debugger;
import com.tibco.www.bx._2010.debugging.debugger.Tester;
import com.tibco.www.bx._2010.debugging.debuggerType.AddAssertionInput;
import com.tibco.www.bx._2010.debugging.debuggerType.AddBreakpointInput;
import com.tibco.www.bx._2010.debugging.debuggerType.AddTestpointInput;
import com.tibco.www.bx._2010.debugging.debuggerType.EmptyInput;
import com.tibco.www.bx._2010.debugging.debuggerType.GetNotificationsOutput;
import com.tibco.www.bx._2010.debugging.debuggerType.GetVariablesOutput;
import com.tibco.www.bx._2010.debugging.debuggerType.NodeDefinition;
import com.tibco.www.bx._2010.debugging.debuggerType.NodeDefinitions;
import com.tibco.www.bx._2010.debugging.debuggerType.Notification;
import com.tibco.www.bx._2010.debugging.debuggerType.OperationFailedFault;
import com.tibco.www.bx._2010.debugging.debuggerType.RemoveAssertionInput;
import com.tibco.www.bx._2010.debugging.debuggerType.RemoveBreakpointInput;
import com.tibco.www.bx._2010.debugging.debuggerType.RemoveTestpointInput;
import com.tibco.www.bx._2010.debugging.debuggerType.RunToActivityInput;
import com.tibco.www.bx._2010.debugging.debuggerType.SetBreakpointEnableInput;
import com.tibco.www.bx._2010.debugging.debuggerType.SetNodeDefinitionsInput;
import com.tibco.www.bx._2010.debugging.debuggerType.SetVariableInput;
import com.tibco.www.bx._2010.debugging.debuggerType.Variable;

public class HTTPDebugger implements IBxDebugger, IBxTester, HTTPNotificationBroadcaster {

	private Debugger debugger;
	private Tester tester;
	private IConnection connection;
	private NotificationCrawler crawler;
	private List<Notification> notifications = new ArrayList<Notification>();
	private static final int INTERVAL = 200;

	public HTTPDebugger(IConnection connection, Debugger debugger, Tester tester) {
		this.connection = connection;
		this.debugger = debugger;
		this.tester = tester;
	}

	public String connect(String ModelType) throws CoreException {
		try {
			if (Tracing.ENABLED)
				Tracing.trace("Calling debugger.initialize()"); //$NON-NLS-1$
			String sessionId = debugger.initialize(connection.getModelType());
			if (IConnection.SESSION_ZERO.equals(sessionId)) {
				throw createCoreException(null, Messages.getString("HTTPDebugger.initialize_error_message"));//$NON-NLS-1$
			}
			crawler = new NotificationCrawler();
			new Thread(crawler).start();
			return sessionId;
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, Messages.getString("HTTPDebugger.initialize_error_message"));//$NON-NLS-1$
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.bx.debug.core.runtime.IBxDebugger#disconnect()
	 */
	public void disconnect() throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.close()"); //$NON-NLS-1$
		try {
			if (crawler != null)
				crawler.exit();
			debugger.close(new EmptyInput());
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, Messages.getString("HTTPDebugger.disconnect_error_message"));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.bx.debug.core.runtime.IBxDebugger#resume()
	 */
	public void resumeAll() throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.resumeAll()"); //$NON-NLS-1$
		try {
			debugger.resumeAll(new EmptyInput());
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, Messages.getString("HTTPDebugger.resumeAll_error_message"));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.bx.debug.core.runtime.IBxDebugger#resume(java.lang.String)
	 */
	public void resume(String instanceId) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.resume()"); //$NON-NLS-1$
		try {
			debugger.resume(instanceId);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, String.format(Messages.getString("HTTPDebugger.resume_error_message"), new Object[] { instanceId }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.bx.debug.core.runtime.IBxDebugger#suspend()
	 */
	public void suspendAll() throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.suspendAll()"); //$NON-NLS-1$
		try {
			debugger.suspendAll(new EmptyInput());
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, Messages.getString("HTTPDebugger.suspendAll_error_message"));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#suspend(java.lang.String)
	 */
	public void suspend(String instanceId) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.suspend()"); //$NON-NLS-1$
		try {
			debugger.suspend(instanceId);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, String.format(Messages.getString("HTTPDebugger.suspend_error_message"), new Object[] { instanceId }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.bx.debug.core.runtime.IBxDebugger#terminate()
	 */
	public void terminate() throws CoreException {
		disconnect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#terminate(java.lang.String)
	 */
	public void terminate(String instanceId) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.terminate()"); //$NON-NLS-1$
		try {
			debugger.terminate(instanceId);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, String.format(Messages.getString("HTTPDebugger.terminate_error_message"), new Object[] { instanceId }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#stepInto(java.lang.String)
	 */
	public void stepInto(String instanceId) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.stepInto()"); //$NON-NLS-1$
		try {
			debugger.stepInto(instanceId);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, String.format(Messages.getString("HTTPDebugger.stepInto_error_message"), new Object[] { instanceId }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#stepOver(java.lang.String)
	 */
	public void stepOver(String instanceId) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.stepOver(" + instanceId + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			debugger.stepOver(instanceId);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, String.format(Messages.getString("HTTPDebugger.stepOver_error_message"), new Object[] { instanceId }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#stepReturn(java.lang.String)
	 */
	public void stepReturn(String instanceId) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.stepOut()"); //$NON-NLS-1$
		try {
			debugger.stepOut(instanceId);
		} catch (Throwable e) {
			HTTPActivator.log(e);
			throw createCoreException(e, String.format(Messages.getString("HTTPDebugger.stepOut_error_message"), new Object[] { instanceId }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#runToActivity(java.lang.String
	 * , java.lang.String, com.tibco.bx.debug.BreakWhen)
	 */
	public void runToActivity(String instanceId, String location, BreakWhen breakWhen) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.runToActivity()"); //$NON-NLS-1$
		try {
			RunToActivityInput input = new RunToActivityInput();
			input.setInstanceId(instanceId);
			input.setLocation(location);
			input.setWhen(getBreakWhen(breakWhen));
			debugger.runToActivity(input);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, String.format(
					Messages.getString("HTTPDebugger.runToActivity_error_message"), new Object[] { instanceId, location, breakWhen.toString() }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#addBreakpoint(com.tibco.bx
	 * .debug.core.models.BxBreakpoint)
	 */
	public void addBreakpoint(BxBreakpoint bxBreakpoint) throws CoreException {
		if (bxBreakpoint == null) {
			return;
		}

		// add breakpoint to all related templates
		try {
			boolean enabled = bxBreakpoint.isConditionEnabled();
			if (Tracing.ENABLED) {
				Tracing.trace("Calling debugger.addBreakpoint()"); //$NON-NLS-1$
			}

			AddBreakpointInput addBreakpointInput = new AddBreakpointInput();
			addBreakpointInput.setProcessId(bxBreakpoint.getProcessId());
			addBreakpointInput.setLocation(bxBreakpoint.getLocation());
			addBreakpointInput.setWhen(getBreakWhen(bxBreakpoint.getBreakWhen()));
			addBreakpointInput.setCondExpression(enabled ? bxBreakpoint.getCondition() : null);
			addBreakpointInput.setLanguage(getConditionLanguage(bxBreakpoint.getConditionLanguage()));

			boolean isSuccess = debugger.addBreakpoint(addBreakpointInput);

			if (!isSuccess)
				throw createCoreException(null, Messages.getString("HTTPDebugger.addBreakpoint_error_message")); //$NON-NLS-1$

			if (!bxBreakpoint.isEnabled()) {
				SetBreakpointEnableInput setBreakpointEnableInput = new SetBreakpointEnableInput();

				setBreakpointEnableInput.setProcessId(bxBreakpoint.getProcessId());
				setBreakpointEnableInput.setLocation(bxBreakpoint.getLocation());
				setBreakpointEnableInput.setWhen(getBreakWhen(bxBreakpoint.getBreakWhen()));
				setBreakpointEnableInput.setIsEnable(false);

				debugger.setBreakpointEnable(setBreakpointEnableInput);

			}

		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, Messages.getString("HTTPDebugger.addBreakpoint_error_message"));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#modifyBreakpoint(com.tibco
	 * .bx.debug.core.models.BxBreakpoint)
	 */
	public void modifyBreakpoint(BxBreakpoint bxBreakpoint) throws CoreException {
		if (bxBreakpoint != null) {
			removeBreakpoint(bxBreakpoint);
			addBreakpoint(bxBreakpoint);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#removeBreakpoint(com.tibco
	 * .bx.debug.core.models.BxBreakpoint)
	 */
	public void removeBreakpoint(BxBreakpoint bxBreakpoint) throws CoreException {
		if (bxBreakpoint != null) {
			if (Tracing.ENABLED)
				Tracing.trace("Calling debugger.removeBreakpoint()"); //$NON-NLS-1$
			try {
				RemoveBreakpointInput removeBreakpointInput = new RemoveBreakpointInput();
				removeBreakpointInput.setProcessId(bxBreakpoint.getProcessId());
				removeBreakpointInput.setLocation(bxBreakpoint.getLocation());
				removeBreakpointInput.setWhen(getBreakWhen(bxBreakpoint.getBreakWhen()));
				debugger.removeBreakpoint(removeBreakpointInput);
			} catch (Throwable e) {
				// HTTPActivator.log(e);
				throw createCoreException(e, Messages.getString("HTTPDebugger.removeBreakpoint_error_message"));//$NON-NLS-1$
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.bx.debug.core.runtime.IBxDebugger#removeAllBreakpoints()
	 */
	public void removeAllBreakpoints() throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.removeAllBreakpoints()"); //$NON-NLS-1$
		try {
			debugger.removeAllBreakpoints(new EmptyInput());
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, Messages.getString("HTTPDebugger.removeAllBreakpoints_error_message"));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#getBxProcessVariables(com
	 * .tibco.bx.debug.core.common.ProcessInstance)
	 */
	public ProcessVariable[] getProcessVariables(IVariableHandler variableHandler, ProcessContainer processContainer) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.getVariables()"); //$NON-NLS-1$
		try {
			GetVariablesOutput getVariablesOutput = debugger.getVariables(processContainer.getInstanceId());
			return HTTPDataManager.getProcessVariables(variableHandler, processContainer, getVariablesOutput);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(
					e,
					String.format(
							Messages.getString("HTTPDebugger.getProcessVariables_error_message"), new Object[] { processContainer.getInstanceId() }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#getProcessVariableValues(
	 * com.tibco.bx.debug.common.model.ProcessInstance)
	 */
	public Map<String, String> getProcessVariableValues(ProcessInstance processInstance) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.getProcessVariableValues()"); //$NON-NLS-1$
		try {
			GetVariablesOutput getVariablesOutput = debugger.getVariables(processInstance.getInstanceId());
			List<Variable> variables = getVariablesOutput.getVariables().getVariable();
			HashMap<String, String> map = new HashMap<String, String>();
			for (Variable var : variables) {
				map.put(var.getVariableName(), var.getValue());
			}

			return map;
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(
					e,
					String.format(
							Messages.getString("HTTPDebugger.getProcessVariables_error_message"), new Object[] { processInstance.getInstanceId() }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxDebugger#setVariable(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public boolean setVariable(String instanceId, String variableName, String variableValue) throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.setVariable()"); //$NON-NLS-1$
		try {
			SetVariableInput setVariableInput = new SetVariableInput();
			setVariableInput.setInstanceId(instanceId);
			setVariableInput.setVariableName(variableName);
			setVariableInput.setVariableValue(variableValue);
			return debugger.setVariable(setVariableInput);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, String.format(Messages.getString("HTTPDebugger.setVariable_error_message"), new Object[] { variableName }));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxTester#addTestpoint(com.tibco.bx.test
	 * .Testpoint)
	 */
	public void addTestpoint(Testpoint testpoint) throws CoreException {
		if (testpoint != null) {
			if (Tracing.ENABLED)
				Tracing.trace("Calling debugger.addTestpoint()"); //$NON-NLS-1$
			if (tester == null) {
				throw createCoreException(null, Messages.getString("HTTPDebugger.testerValid_error_message"));//$NON-NLS-1$
			}
			try {
				AddTestpointInput addTestInput = new AddTestpointInput();
				addTestInput.setProcessId(testpoint.getProcessId());
				addTestInput.setLocation(testpoint.getLocation());
				addTestInput.setExpression(testpoint.getExpression());
				addTestInput.setLanguage(getConditionLanguage(ConditionLanguage.JSCRIPT));
				tester.addTestpoint(addTestInput);
			} catch (Throwable e) {
				// HTTPActivator.log(e);
				throw createCoreException(
						e,
						String.format(
								Messages.getString("HTTPDebugger.addTestpoint_error_message"), new Object[] { testpoint.getLocation(), testpoint.getProcessId() }));//$NON-NLS-1$
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxTester#addAssertion(com.tibco.bx.test
	 * .Assertion)
	 */
	public void addAssertion(Assertion assertion) throws CoreException {
		if (assertion != null) {
			if (Tracing.ENABLED)
				Tracing.trace("Calling debugger.addAssertion()"); //$NON-NLS-1$
			if (tester == null) {
				throw createCoreException(null, Messages.getString("HTTPDebugger.testerValid_error_message"));//$NON-NLS-1$
			}
			try {
				AddAssertionInput addAssertionInput = new AddAssertionInput();
				addAssertionInput.setProcessId(assertion.getProcessId());
				addAssertionInput.setLocation(assertion.getLocation());
				addAssertionInput.setValue(assertion.isAccessible());
				tester.addAssertion(addAssertionInput);
			} catch (Throwable e) {
				// HTTPActivator.log(e);
				throw createCoreException(
						e,
						String.format(
								Messages.getString("HTTPDebugger.addAssertion_error_message"), new Object[] { assertion.getLocation(), assertion.getProcessId() }));//$NON-NLS-1$
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.bx.debug.core.runtime.IBxTester#removeAllTestpoints()
	 */
	public void removeAllTestpoints() throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.removeAllTestpoints()"); //$NON-NLS-1$
		if (tester == null)
			throw createCoreException(null, Messages.getString("HTTPDebugger.testerValid_error_message"));//$NON-NLS-1$
		try {
			tester.removeAllTestpoints(new EmptyInput());
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, Messages.getString("HTTPDebugger.removeAllTestpoints_error_message"));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.bx.debug.core.runtime.IBxTester#removeAllAssertions()
	 */
	public void removeAllAssertions() throws CoreException {
		if (Tracing.ENABLED)
			Tracing.trace("Calling debugger.removeAllAssertions()"); //$NON-NLS-1$
		if (tester == null)
			throw createCoreException(null, Messages.getString("HTTPDebugger.testerValid_error_message"));//$NON-NLS-1$
		try {
			tester.removeAllAssertions(new EmptyInput());
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, Messages.getString("HTTPDebugger.removeAllAssertions_error_message"));//$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxTester#removeTestpoint(com.tibco.bx
	 * .test.Testpoint)
	 */
	public void removeTestpoint(Testpoint testpoint) throws CoreException {
		if (testpoint != null) {
			if (Tracing.ENABLED)
				Tracing.trace("Calling debugger.removeTestpoint()"); //$NON-NLS-1$
			if (tester == null) {
				throw createCoreException(null, Messages.getString("HTTPDebugger.testerValid_error_message"));//$NON-NLS-1$
			}
			try {
				RemoveTestpointInput removeTestpointInput = new RemoveTestpointInput();
				removeTestpointInput.setProcessId(testpoint.getProcessId());
				removeTestpointInput.setLocation(testpoint.getLocation());
				tester.removeTestpoint(removeTestpointInput);
			} catch (Throwable e) {
				// HTTPActivator.log(e);
				throw createCoreException(
						e,
						String.format(
								Messages.getString("HTTPDebugger.removeTestpoint_error_message"), new Object[] { testpoint.getLocation(), testpoint.getProcessId() }));//$NON-NLS-1$
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.bx.debug.core.runtime.IBxTester#removeAssertion(com.tibco.bx
	 * .test.Assertion)
	 */
	public void removeAssertion(Assertion assertion) throws CoreException {
		if (assertion != null) {
			if (Tracing.ENABLED)
				Tracing.trace("Calling debugger.removeAssertion()"); //$NON-NLS-1$
			if (tester == null) {
				throw createCoreException(null, Messages.getString("HTTPDebugger.testerDisable_error_message"));//$NON-NLS-1$
			}
			try {
				RemoveAssertionInput removeAssertionInput = new RemoveAssertionInput();
				removeAssertionInput.setProcessId(assertion.getProcessId());
				removeAssertionInput.setLocation(assertion.getLocation());
				tester.removeAssertion(removeAssertionInput);
			} catch (Throwable e) {
				// HTTPActivator.log(e);
				throw createCoreException(
						e,
						String.format(
								Messages.getString("HTTPDebugger.removeAssertion_error_message"), new Object[] { assertion.getLocation(), assertion.getProcessId() }));//$NON-NLS-1$
			}
		}
	}

	private CoreException createCoreException(Throwable e, String message) {
		return (CoreException) new CoreException(HTTPActivator.newStatus(e, message)).initCause(e);
	}

	private com.tibco.www.bx._2010.debugging.debuggerType.BreakWhen getBreakWhen(BreakWhen when) {
		com.tibco.www.bx._2010.debugging.debuggerType.BreakWhen ret = null;
		if (when != null) {
			switch (when) {
			case ENTRY:
				ret = com.tibco.www.bx._2010.debugging.debuggerType.BreakWhen.ENTRY;
				break;
			case RETURN:
				ret = com.tibco.www.bx._2010.debugging.debuggerType.BreakWhen.RETURN;
				break;
			case BOTH:
				ret = com.tibco.www.bx._2010.debugging.debuggerType.BreakWhen.BOTH;
				break;
			}
		}
		return ret;
	}

	private com.tibco.www.bx._2010.debugging.debuggerType.ConditionLanguage getConditionLanguage(ConditionLanguage language) {
		com.tibco.www.bx._2010.debugging.debuggerType.ConditionLanguage ret = null;
		if (language != null) {
			switch (language) {
			case JSCRIPT:
				ret = com.tibco.www.bx._2010.debugging.debuggerType.ConditionLanguage.JSCRIPT;
				break;
			case XPATH:
				ret = com.tibco.www.bx._2010.debugging.debuggerType.ConditionLanguage.XPATH;
				break;
			}
		}
		return ret;
	}

	private List<HTTPNotificationListener> listeners = new ArrayList<HTTPNotificationListener>();

	private void sendNotification(Notification notification) {
		for (HTTPNotificationListener listener : listeners) {
			listener.handleNotification(notification);
		}
	}

	class NotificationCrawler implements Runnable {
		boolean exit = false;
		private int failNumber = 0;
		private static final int FAILMAX = 10;

		public void run() {
			Notification notification = null;
			while (!exit) {
				try {
					if (failNumber >= FAILMAX) {
						// connection failed
						exit();
						((HTTPConnection) connection).fireConnectionClosed();
						continue;
					}
					// sleep
					Thread.sleep(INTERVAL);
					// getNotifications from server side
					GetNotificationsOutput getNotificationsOutput = debugger.getNotifications(connection.getSessionId());
					List<Notification> notificationArray = getNotificationsOutput.getNotifications().getNotification();
					for (Notification aNotification : notificationArray) {
						notifications.add(aNotification);
					}

					// send notifications to listeners
					while (notifications.size() > 0) {
						notification = notifications.remove(0);
						sendNotification(notification);
					}
				} catch (Exception e) {
					if (e instanceof OperationFailedFault && ((OperationFailedFault) e).getFaultInfo().getReason().contains("Session"))
						failNumber = 10;// kick off by another session
					else
						failNumber++;// 404 error
					HTTPActivator.log(e);
				}
			}
		}

		public synchronized void exit() {
			exit = true;
		}
	}

	@Override
	public void addNotificationListener(HTTPNotificationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeNotificationListener(HTTPNotificationListener listener) {
		listeners.remove(listener);
	}

	@Override
	public boolean canStepInto(String activityInstanceId) throws CoreException {
		try {
			return debugger.canStepInto(activityInstanceId);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e,
					String.format(Messages.getString("HTTPDebugger.canStepInto_error_message"), new Object[] { activityInstanceId }));//$NON-NLS-1$
		}
	}

	@Override
	public boolean canStepReturn(String instanceId) throws CoreException {
		try {
			return debugger.canStepOut(instanceId);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e, String.format(Messages.getString("HTTPDebugger.canStepOut_error_message"), new Object[] { instanceId }));//$NON-NLS-1$
		}
	}

	@Override
	public boolean setNodeDefinitions(String processId, IBxNodeDefinition[] definitions) throws CoreException {
		SetNodeDefinitionsInput input = new SetNodeDefinitionsInput();
		input.setProcessId(processId);
		NodeDefinitions nodeDefinitions = new NodeDefinitions();
		for (int i = 0; i < definitions.length; i++) {
			NodeDefinition definition = new NodeDefinition();
			definition.setActivityId(definitions[i].getActivityId());
			definition.setActivityName(definitions[i].getActivityName());
			definition.setActivityType(definitions[i].getType());
			definition.setCanStepInto(definitions[i].canStepInto());
			definition.setCanStepOut(definitions[i].canStepOut());
			String parentId = definitions[i].getParentId();

			definition.setParentActivityId(parentId == null ? IDebugConstants.NULL_ATTR : parentId);
			definition.setSourceIds(spellString(definitions[i].getSourceIds()));
			definition.setTargetIds(spellString(definitions[i].getTargetIds()));
			nodeDefinitions.getNodeDefinition().add(definition);
			// nodeDefinitions[i] = definition;
		}

		input.setNodeDefinitions(nodeDefinitions);
		try {
			return debugger.setNodeDefinitions(input);
		} catch (Throwable e) {
			// HTTPActivator.log(e);
			throw createCoreException(e,
					String.format(Messages.getString("HTTPDebugger.setNodeDefinitions_error_message"), new Object[] { processId }));//$NON-NLS-1$
		}
	}

	private static String spellString(String[] strs) {
		if (strs.length == 0) {
			return IDebugConstants.NULL_ATTR;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			buffer.append(strs[i]);
			if (i != strs.length - 1)
				buffer.append(';');
		}
		return buffer.toString();
	}

	@Override
	public boolean isTesterValid() {
		return tester != null;
	}

}