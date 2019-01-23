package com.tibco.www.bx._2010.debugging.debuggerType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.tibco.bx._2010.debugging.debuggertype
 * package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _SessionId_QNAME = new QName("http://www.tibco.com/bx/2010/debugging/debuggerType", "sessionId");
	private final static QName _ModelType_QNAME = new QName("http://www.tibco.com/bx/2010/debugging/debuggerType", "modelType");
	private final static QName _Success_QNAME = new QName("http://www.tibco.com/bx/2010/debugging/debuggerType", "success");
	private final static QName _OperationFailedFault_QNAME = new QName("http://www.tibco.com/bx/2010/debugging/debuggerType", "operationFailedFault");
	private final static QName _InstanceId_QNAME = new QName("http://www.tibco.com/bx/2010/debugging/debuggerType", "instanceId");
	private final static QName _IsSuccess_QNAME = new QName("http://www.tibco.com/bx/2010/debugging/debuggerType", "isSuccess");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * com.tibco.bx._2010.debugging.debuggertype
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Notification }
	 * 
	 */
	public Notification createNotification() {
		return new Notification();
	}

	/**
	 * Create an instance of {@link GetVariablesOutput }
	 * 
	 */
	public GetVariablesOutput createGetVariablesOutput() {
		return new GetVariablesOutput();
	}

	/**
	 * Create an instance of {@link Variables }
	 * 
	 */
	public Variables createVariables() {
		return new Variables();
	}

	/**
	 * Create an instance of {@link GetActivitiesOutput }
	 * 
	 */
	public GetActivitiesOutput createGetActivitiesOutput() {
		return new GetActivitiesOutput();
	}

	/**
	 * Create an instance of {@link Activities }
	 * 
	 */
	public Activities createActivities() {
		return new Activities();
	}

	/**
	 * Create an instance of {@link RemoveAssertionInput }
	 * 
	 */
	public RemoveAssertionInput createRemoveAssertionInput() {
		return new RemoveAssertionInput();
	}

	/**
	 * Create an instance of {@link AddTestpointInput }
	 * 
	 */
	public AddTestpointInput createAddTestpointInput() {
		return new AddTestpointInput();
	}

	/**
	 * Create an instance of {@link SetVariableInput }
	 * 
	 */
	public SetVariableInput createSetVariableInput() {
		return new SetVariableInput();
	}

	/**
	 * Create an instance of {@link EmptyInput }
	 * 
	 */
	public EmptyInput createEmptyInput() {
		return new EmptyInput();
	}

	/**
	 * Create an instance of {@link BasicFaultType }
	 * 
	 */
	public BasicFaultType createBasicFaultType() {
		return new BasicFaultType();
	}

	/**
	 * Create an instance of {@link SetNodeDefinitionsInput }
	 * 
	 */
	public SetNodeDefinitionsInput createSetNodeDefinitionsInput() {
		return new SetNodeDefinitionsInput();
	}

	/**
	 * Create an instance of {@link NodeDefinitions }
	 * 
	 */
	public NodeDefinitions createNodeDefinitions() {
		return new NodeDefinitions();
	}

	/**
	 * Create an instance of {@link GetProcessTemplatesOutput }
	 * 
	 */
	public GetProcessTemplatesOutput createGetProcessTemplatesOutput() {
		return new GetProcessTemplatesOutput();
	}

	/**
	 * Create an instance of {@link ProcessTemplates }
	 * 
	 */
	public ProcessTemplates createProcessTemplates() {
		return new ProcessTemplates();
	}

	/**
	 * Create an instance of {@link GetNotificationsOutput }
	 * 
	 */
	public GetNotificationsOutput createGetNotificationsOutput() {
		return new GetNotificationsOutput();
	}

	/**
	 * Create an instance of {@link Notifications }
	 * 
	 */
	public Notifications createNotifications() {
		return new Notifications();
	}

	/**
	 * Create an instance of {@link RemoveTestpointInput }
	 * 
	 */
	public RemoveTestpointInput createRemoveTestpointInput() {
		return new RemoveTestpointInput();
	}

	/**
	 * Create an instance of {@link RunToActivityInput }
	 * 
	 */
	public RunToActivityInput createRunToActivityInput() {
		return new RunToActivityInput();
	}

	/**
	 * Create an instance of {@link AddBreakpointInput }
	 * 
	 */
	public AddBreakpointInput createAddBreakpointInput() {
		return new AddBreakpointInput();
	}

	/**
	 * Create an instance of {@link AddAssertionInput }
	 * 
	 */
	public AddAssertionInput createAddAssertionInput() {
		return new AddAssertionInput();
	}

	/**
	 * Create an instance of {@link RemoveBreakpointInput }
	 * 
	 */
	public RemoveBreakpointInput createRemoveBreakpointInput() {
		return new RemoveBreakpointInput();
	}

	/**
	 * Create an instance of {@link SetBreakpointEnableInput }
	 * 
	 */
	public SetBreakpointEnableInput createSetBreakpointEnableInput() {
		return new SetBreakpointEnableInput();
	}

	/**
	 * Create an instance of {@link Variable }
	 * 
	 */
	public Variable createVariable() {
		return new Variable();
	}

	/**
	 * Create an instance of {@link NameValuePair }
	 * 
	 */
	public NameValuePair createNameValuePair() {
		return new NameValuePair();
	}

	/**
	 * Create an instance of {@link NodeDefinition }
	 * 
	 */
	public NodeDefinition createNodeDefinition() {
		return new NodeDefinition();
	}

	/**
	 * Create an instance of {@link Activity }
	 * 
	 */
	public Activity createActivity() {
		return new Activity();
	}

	/**
	 * Create an instance of {@link ProcessTemplate }
	 * 
	 */
	public ProcessTemplate createProcessTemplate() {
		return new ProcessTemplate();
	}

	/**
	 * Create an instance of {@link Notification.Data }
	 * 
	 */
	public Notification.Data createNotificationData() {
		return new Notification.Data();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2010/debugging/debuggerType", name = "sessionId")
	public JAXBElement<String> createSessionId(String value) {
		return new JAXBElement<String>(_SessionId_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2010/debugging/debuggerType", name = "modelType")
	public JAXBElement<String> createModelType(String value) {
		return new JAXBElement<String>(_ModelType_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2010/debugging/debuggerType", name = "success")
	public JAXBElement<String> createSuccess(String value) {
		return new JAXBElement<String>(_Success_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link BasicFaultType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2010/debugging/debuggerType", name = "operationFailedFault")
	public JAXBElement<BasicFaultType> createOperationFailedFault(BasicFaultType value) {
		return new JAXBElement<BasicFaultType>(_OperationFailedFault_QNAME, BasicFaultType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2010/debugging/debuggerType", name = "instanceId")
	public JAXBElement<String> createInstanceId(String value) {
		return new JAXBElement<String>(_InstanceId_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2010/debugging/debuggerType", name = "isSuccess")
	public JAXBElement<Boolean> createIsSuccess(Boolean value) {
		return new JAXBElement<Boolean>(_IsSuccess_QNAME, Boolean.class, null, value);
	}

}
