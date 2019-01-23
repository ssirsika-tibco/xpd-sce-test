package com.tibco.bx.xpdl2bpel.converter;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.WSDLFactory;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerType;

public class XPDL2WSDL {

	/**
	 * convert an xpdl file to wsdl file
	 *
	 * @param xpdlFile:
	 *            xpdl file
	 * @param distFolder:
	 *            the dist folder, who is the parent of wsdl file
	 * @param monitor
	 * @throws CoreException
	 */
	public static void convertXPDLFile2WSDLs(IFile xpdlFile,IFolder distFolder,
			IProgressMonitor monitor) throws CoreException{
		WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(xpdlFile);
		if (wc == null || wc.getRootElement() == null) {
			return;
		}
		convertXPDLFile2WSDLs(wc, distFolder, monitor);
	}

	/**
	 * convert an xpdl model to wsdl file
	 *
	 * @param wc:
	 *            xpdl model
	 * @param distFolder:
	 *            the dist folder, who is the parent of wsdl file
	 * @param monitor
	 * @throws CoreException
	 */
	public static void convertXPDLFile2WSDLs(WorkingCopy wc, IFolder distFolder,
			IProgressMonitor monitor) throws CoreException {
		if (wc == null) {
			return;
		}

		// process all processes
		EList<Process> processes = ((Package) wc.getRootElement()).getProcesses();
		for (Process process : processes) {
			/*
			 * according to steve's idea
			 * when bpel engine is selected,do convert
			 */
			if(!XPDLUtils.hasN2Destination(process)){
				continue;
			}
			
			EList<Activity> activities = process.getActivities();
			/*
			 * read the first start event
			 * if not null and not message type
			 * then generate an operation
			 * else do nothing
			 * end break
			 */
			for(Activity activity : activities){
				Event event = activity.getEvent();
				if(event!=null&&(event instanceof StartEvent)){
					StartEvent startEvent = (StartEvent) event;
					TriggerType trigger = startEvent.getTrigger();
					if((trigger!=null&&TriggerType.MESSAGE!=trigger.getValue())){
						IFile distFile = distFolder.getFile(process.getName() + ".wsdl"); //$NON-NLS-1$
						if(distFile.exists()){
							distFile.delete(true, monitor);
						}
						XPDL2WSDL.generateWSDL(distFolder, process, activity.getName(), monitor);
					}
					break;
				}
			}
		}

		distFolder.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}
	
	/**
	 * generate wsdlfile according to a process
	 * @param distFolder: the parent of wsdl file
	 * @param process
	 * @param operationName 
	 * @param monitor
	 * @throws CoreException
	 */
	public static void generateWSDL(
			IFolder distFolder,
			Process process,
			String operationName, 
			IProgressMonitor monitor) throws CoreException {
		
		IFile wsdlFile = distFolder.getFile(process.getName() + ".wsdl"); //$NON-NLS-1$
		if (wsdlFile.exists()) {
			wsdlFile.delete(true, monitor);
		}

		Definition definition = newDefinition(process);
		PortType portType = addPortTypeIn(definition);

		generateOperation(definition, portType, process,operationName);

		String wsdlFilePath = wsdlFile.getFullPath().toString();
		try {
			URI wsdlURI = URI.createPlatformResourceURI(wsdlFilePath, false);
			WSDLUtils.saveWSDLFile(wsdlURI, definition);
		} catch (Exception e) {
			ConverterActivator.logError(Messages.getString("XPDL2WSDL.cannotSaveWSDL") + wsdlFilePath, e); //$NON-NLS-1$
		}
	}

	/**
	 * generate an operation according to a process
	 * @param definition
	 * @param portType
	 * @param process
	 * @param operationName 
	 */
	private static void generateOperation(Definition definition,
			PortType portType, Process process, String operationName) {
		/*
		 * each process generate an operation
		 */
		
		if (operationName == null || operationName.equals("")) { //$NON-NLS-1$
			operationName = "startEvent"; //$NON-NLS-1$
		}

		Operation operation = WSDLFactory.eINSTANCE.createOperation();
		operation.setName(operationName);
		portType.addOperation(operation);

		Message input_message = WSDLFactory.eINSTANCE.createMessage();
		input_message.setQName(new QName(N2PEConstants.TARGET_WSDL_NS_PREFIX+process.getId(), "inputMsg")); //$NON-NLS-1$

		Message output_message = WSDLFactory.eINSTANCE.createMessage();
		output_message.setQName(new QName(N2PEConstants.TARGET_WSDL_NS_PREFIX+process.getId(), Messages.getString("XPDL2WSDL.9"))); //$NON-NLS-1$

		Input input = WSDLFactory.eINSTANCE.createInput();
		input.setName(Messages.getString("XPDL2WSDL.10")); //$NON-NLS-1$

		Output output = WSDLFactory.eINSTANCE.createOutput();
		output.setName(Messages.getString("XPDL2WSDL.11")); //$NON-NLS-1$

		EList<FormalParameter> parameters = process.getFormalParameters();

		for (FormalParameter parameter : parameters) {
			ModeType mode = parameter.getMode();
			switch (mode.getValue()) {
			case ModeType.IN:
				addPartInMessage(parameter, input_message);
				break;
			case ModeType.OUT:
				addPartInMessage(parameter, output_message);
				break;
			case ModeType.INOUT:
				addPartInMessage(parameter, input_message);
				addPartInMessage(parameter, output_message);
				break;
			default:
				break;
			}
		}
		if (input_message.getParts() != null
				&& input_message.getParts().size() > 0) {
			input.setMessage(input_message);
			operation.setInput(input);
			definition.addMessage(input_message);
		}
		if (output_message.getParts() != null
				&& output_message.getParts().size() > 0) {
			output.setMessage(output_message);
			operation.setOutput(output);
			definition.addMessage(output_message);
		}
	}

	/**
	 * according to process, generate a definition.
	 * 
	 * @param process
	 * @return
	 */
	private static Definition newDefinition(Process process) {
		Definition definition = WSDLFactory.eINSTANCE.createDefinition();
		String name = process.getName();
		name = convertName2Normal(name);
		
		definition.setTargetNamespace(N2PEConstants.TARGET_WSDL_NS_PREFIX+process.getId());
		definition.addNamespace(Messages.getString("XPDL2WSDL.12"), N2PEConstants.XSD_NAMESPACE_URI); //$NON-NLS-1$
		definition.addNamespace(Messages.getString("XPDL2WSDL.13"), N2PEConstants.TARGET_WSDL_NS_PREFIX+process.getId()); //$NON-NLS-1$

		definition.setQName(new QName(N2PEConstants.TARGET_WSDL_NS_PREFIX+process.getId(), name));

		return definition;
	}

	/**
	 * add a portType to definition 
	 * the qname of portType is same with
	 * definition's qname
	 * 
	 * @param definition
	 * @return
	 */
	private static PortType addPortTypeIn(Definition definition) {
		PortType portType = WSDLFactory.eINSTANCE.createPortType();
		portType.setQName(definition.getQName());
		definition.addPortType(portType);
		return portType;
	}

	/**
	 * add part to message each parameter will generate a part. and the type of
	 * parameter will decide which message will be the parent of this part
	 * 
	 * @param parameter
	 * @param message:
	 *            the parent of part
	 */
	private static void addPartInMessage(FormalParameter parameter,
			Message message) {
		Part part = WSDLFactory.eINSTANCE.createPart();
		part.setName(convertName2Normal(parameter.getName()));
		part.setTypeName(new QName(
				N2PEConstants.XSD_NAMESPACE_URI, convertDataType2WSDL(parameter.getDataType())));
		message.addPart(part);
	}

	/**
	 * convert dataType to wsdl type the result is simple type name,like:
	 * <code>string</code>
	 * 			<code>boolean</code> .................... if you
	 * use the result as a type of element you should set the prefix
	 * namespace,such as: <code>*.setQname(new Qname(XSD,result));</code>
	 * 
	 * @param dataType
	 * @return
	 */
	private static String convertDataType2WSDL(DataType dataType) {
		String result = Messages.getString("XPDL2WSDL.14"); //$NON-NLS-1$
		if (dataType != null) {
			if (dataType instanceof BasicType) {
				BasicTypeType basicTypeType = ((BasicType) dataType).getType();
				if (basicTypeType != null) {
					int basicTypeTypeValue = basicTypeType.getValue();
					// These String constants should not be translated
					switch (basicTypeTypeValue) {
					case BasicTypeType.BOOLEAN:
						result = Messages.getString("XPDL2WSDL.15"); //$NON-NLS-1$
						break;
					case BasicTypeType.DATETIME:
						// TODO Becomes dateTime (how does dateTime data map?)
						result = Messages.getString("XPDL2WSDL.16"); //$NON-NLS-1$
						break;
					case BasicTypeType.FLOAT:
						result = Messages.getString("XPDL2WSDL.17"); //$NON-NLS-1$
						break;
					case BasicTypeType.INTEGER:
						result = Messages.getString("XPDL2WSDL.18"); //$NON-NLS-1$
						break;
					case BasicTypeType.PERFORMER:
						// TODO this type may be changed
						result = Messages.getString("XPDL2WSDL.19"); //$NON-NLS-1$
						break;
					case BasicTypeType.REFERENCE:
						// throw new
						// UnsupportedConversionException(ConverterActivator.createWarningStatus(
						// "Basic type of 'Reference' cannot be converted to
						// BPEL", null));
						// TODO this type may be changed
						result = Messages.getString("XPDL2WSDL.20"); //$NON-NLS-1$
						break;
					case BasicTypeType.STRING:
						result = Messages.getString("XPDL2WSDL.21"); //$NON-NLS-1$
						break;
					}// endswitch
				}
			} else {
				result = dataType.toString();
			}
		}
		return result;
	}

	/**
	 * convert a common name to normal name ,
	 * use "_" to replace all of " "
	 * in this name
	 * 
	 * @param name
	 * @return
	 */
	private static String convertName2Normal(String name) {
		return name.replace(" ", "_"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
}
