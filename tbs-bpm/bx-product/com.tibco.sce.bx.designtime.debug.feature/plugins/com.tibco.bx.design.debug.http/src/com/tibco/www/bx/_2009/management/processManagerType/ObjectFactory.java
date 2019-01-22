package com.tibco.www.bx._2009.management.processManagerType;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the
 * com.tibco.bx._2009.management.processmanagertype package.
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

	private final static QName _StarterOperation_QNAME = new QName("http://www.tibco.com/bx/2009/management/processManagerType", "starterOperation");
	private final static QName _ProcessID_QNAME = new QName("http://www.tibco.com/bx/2009/management/processManagerType", "processID");
	private final static QName _Success_QNAME = new QName("http://www.tibco.com/bx/2009/management/processManagerType", "success");
	private final static QName _OperationFailedFault_QNAME = new QName("http://www.tibco.com/bx/2009/management/processManagerType",
			"operationFailedFault");
	private final static QName _IllegalArgumentFault_QNAME = new QName("http://www.tibco.com/bx/2009/management/processManagerType",
			"illegalArgumentFault");
	private final static QName _OperationInfo_QNAME = new QName("http://www.tibco.com/bx/2009/management/processManagerType", "operationInfo");
	private final static QName _QualifiedProcessName_QNAME = new QName("http://www.tibco.com/bx/2009/management/processManagerType",
			"qualifiedProcessName");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package:
	 * com.tibco.bx._2009.management.processmanagertype
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link CreateProcessInstanceInput }
	 * 
	 */
	public CreateProcessInstanceInput createCreateProcessInstanceInput() {
		return new CreateProcessInstanceInput();
	}

	/**
	 * Create an instance of {@link StarterOperation }
	 * 
	 */
	public StarterOperation createStarterOperation() {
		return new StarterOperation();
	}

	/**
	 * Create an instance of {@link BasicFaultType }
	 * 
	 */
	public BasicFaultType createBasicFaultType() {
		return new BasicFaultType();
	}

	/**
	 * Create an instance of {@link StarterOperations }
	 * 
	 */
	public StarterOperations createStarterOperations() {
		return new StarterOperations();
	}

	/**
	 * Create an instance of {@link QualifiedProcessName }
	 * 
	 */
	public QualifiedProcessName createQualifiedProcessName() {
		return new QualifiedProcessName();
	}

	/**
	 * Create an instance of {@link OperationInfo }
	 * 
	 */
	public OperationInfo createOperationInfo() {
		return new OperationInfo();
	}

	/**
	 * Create an instance of {@link CreateProcessInstanceInput.ParameterMap }
	 * 
	 */
	public CreateProcessInstanceInput.ParameterMap createCreateProcessInstanceInputParameterMap() {
		return new CreateProcessInstanceInput.ParameterMap();
	}

	/**
	 * Create an instance of {@link NameTypePair }
	 * 
	 */
	public NameTypePair createNameTypePair() {
		return new NameTypePair();
	}

	/**
	 * Create an instance of {@link TemplateAttribute }
	 * 
	 */
	public TemplateAttribute createTemplateAttribute() {
		return new TemplateAttribute();
	}

	/**
	 * Create an instance of {@link TemplateAttributes }
	 * 
	 */
	public TemplateAttributes createTemplateAttributes() {
		return new TemplateAttributes();
	}

	/**
	 * Create an instance of {@link NameValuePair }
	 * 
	 */
	public NameValuePair createNameValuePair() {
		return new NameValuePair();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link StarterOperation }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2009/management/processManagerType", name = "starterOperation")
	public JAXBElement<StarterOperation> createStarterOperation(StarterOperation value) {
		return new JAXBElement<StarterOperation>(_StarterOperation_QNAME, StarterOperation.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2009/management/processManagerType", name = "processID")
	public JAXBElement<String> createProcessID(String value) {
		return new JAXBElement<String>(_ProcessID_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2009/management/processManagerType", name = "success")
	public JAXBElement<String> createSuccess(String value) {
		return new JAXBElement<String>(_Success_QNAME, String.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link BasicFaultType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2009/management/processManagerType", name = "operationFailedFault")
	public JAXBElement<BasicFaultType> createOperationFailedFault(BasicFaultType value) {
		return new JAXBElement<BasicFaultType>(_OperationFailedFault_QNAME, BasicFaultType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link BasicFaultType }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2009/management/processManagerType", name = "illegalArgumentFault")
	public JAXBElement<BasicFaultType> createIllegalArgumentFault(BasicFaultType value) {
		return new JAXBElement<BasicFaultType>(_IllegalArgumentFault_QNAME, BasicFaultType.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link OperationInfo }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2009/management/processManagerType", name = "operationInfo")
	public JAXBElement<OperationInfo> createOperationInfo(OperationInfo value) {
		return new JAXBElement<OperationInfo>(_OperationInfo_QNAME, OperationInfo.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link QualifiedProcessName }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.tibco.com/bx/2009/management/processManagerType", name = "qualifiedProcessName")
	public JAXBElement<QualifiedProcessName> createQualifiedProcessName(QualifiedProcessName value) {
		return new JAXBElement<QualifiedProcessName>(_QualifiedProcessName_QNAME, QualifiedProcessName.class, null, value);
	}

}
