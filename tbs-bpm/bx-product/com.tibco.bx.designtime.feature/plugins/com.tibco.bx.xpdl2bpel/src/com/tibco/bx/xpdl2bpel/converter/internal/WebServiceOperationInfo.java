package com.tibco.bx.xpdl2bpel.converter.internal;

import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Operation;
import javax.wsdl.Output;

import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.WSDLFactory;

import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdl2.WebServiceOperation;

public class WebServiceOperationInfo {
	
	private final WebServiceOperation wso;
	private org.eclipse.wst.wsdl.Definition wsdlDefinition;
	private PartnerLinkType partnerLinkType;
	private javax.wsdl.PortType portType;
	private PortTypeOperation portTypeOperation;
	private Operation wsdlOperation;

	public WebServiceOperationInfo(ConverterContext context, IProject project, WebServiceOperation wso) throws ConversionException {
		this.wso = wso;
		
        wsdlDefinition = context.getWsdlDefinition(project, wso);
		portTypeOperation = XPDLUtils.getPortTypeOperation(wso);
		portType = WSDLUtils.findPortType(wsdlDefinition, portTypeOperation);
	    
		for (Object op : portType.getOperations()) {
			javax.wsdl.Operation operation = (javax.wsdl.Operation) op;
			if (operation.getName().equals(this.getOperationName())) {
				this.wsdlOperation = operation;
				break;
			}
		}

		for (Object extension : wsdlDefinition.getExtensibilityElements()) {
    		if (extension instanceof PartnerLinkType) {
    			PartnerLinkType plt = (PartnerLinkType) extension;
    			Role role = (Role) plt.getRole().get(0);
    			javax.wsdl.PortType pt = (javax.wsdl.PortType) role.getPortType();
    			if (portType.getQName().equals(pt.getQName())) {
    				partnerLinkType = plt;
        			break;
    			}
    		}
    	}
		
		if (partnerLinkType == null) {
			//PartnerLinkType has not been defined in WSDL; add it to the WSDL
            addPartnerLinkTypeToWSDL();
        }
	}

	public URI getWSDLLocation() {
		return wsdlDefinition.eResource().getURI();
	}
	
	public WebServiceOperation getWebServiceOperation() {
		return wso;
	}

	public org.eclipse.wst.wsdl.Definition getWsdlDefinition() {
		return wsdlDefinition;
	}

	public PartnerLinkType getPartnerLinkType() {
		return partnerLinkType;
	}

	public javax.wsdl.PortType getPortType() {
		return portType;
	}

	public Input getInput() {
		return wsdlOperation != null ? wsdlOperation.getInput() : null;
	}

	public Output getOutput() {
		return wsdlOperation != null ? wsdlOperation.getOutput() : null;
	}
	
	public Fault getFault(String faultName) {
		return wsdlOperation != null ? wsdlOperation.getFault(faultName) : null;
	}
	
	public String getOperationName() {
		return portTypeOperation.getOperationName();
	}
    
	private void addPartnerLinkTypeToWSDL() throws ConversionException {
        String portTypeName = portTypeOperation.getPortTypeName();
        partnerLinkType = PartnerlinktypeFactory.eINSTANCE.createPartnerLinkType();
		partnerLinkType.setName(portTypeName + "_PLT"); //$NON-NLS-1$
		partnerLinkType.setEnclosingDefinition(wsdlDefinition);
		Role role = PartnerlinktypeFactory.eINSTANCE.createRole();
		role.setName(portTypeName + "Provider"); //$NON-NLS-1$
		role.setEnclosingDefinition(wsdlDefinition);
		role.setPortType(portType);
		partnerLinkType.getRole().add(role);
		wsdlDefinition.getExtensibilityElements().add(partnerLinkType);
	}

	public org.eclipse.wst.wsdl.Operation createOperation() {
		org.eclipse.wst.wsdl.Operation wsdlOperation = WSDLFactory.eINSTANCE.createOperation();
		wsdlOperation.setName(this.getOperationName());
		return wsdlOperation;
	}
	
	public org.eclipse.wst.wsdl.PortType createPortType() {
		org.eclipse.wst.wsdl.PortType pt = WSDLFactory.eINSTANCE.createPortType();
		pt.setQName(this.portType.getQName());
		return pt;
	}

    public org.eclipse.bpel.model.PartnerLink createPartnerLinkForWebService() {
        org.eclipse.bpel.model.PartnerLink partnerLink = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createPartnerLink();		
		String name = getPartnerLinkName();
		partnerLink.setName(name);
		partnerLink.setPartnerLinkType(partnerLinkType);
        partnerLink.setPartnerRole((Role)partnerLinkType.getRole().get(0));
        return partnerLink;
    }

    public String getPartnerLinkName() {
        String participantName = XPDLUtils.getParticipantName(wso);
		if (participantName != null) {
			return participantName;
		}
				 
		return wso.getService().getServiceName() != null ? wso.getService().getServiceName() : portTypeOperation.getPortTypeName();
    }
    
    public String getInputVariableName() {
		if (getInput() == null) {
			return null;
		}
		
		return "v_" + Integer.toHexString(getInput().getMessage().getQName().getLocalPart().hashCode());
	}
	
	public org.eclipse.bpel.model.Variable createInputVariable() {
		if (getInput() == null) {
			return null;
		}
		
		org.eclipse.bpel.model.Variable var = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		var.setName(getInputVariableName());
		var.setMessageType((org.eclipse.wst.wsdl.Message) getInput().getMessage());
		return var;
	}

	public String getOutputVariableName() {
		if (getOutput() == null) {
			return null;
		}
		
		return "v_" + Integer.toHexString(getOutput().getMessage().getQName().getLocalPart().hashCode());
	}
	
	public org.eclipse.bpel.model.Variable createOutputVariable() {
		if (getOutput() == null) {
			return null;
		}
		
		org.eclipse.bpel.model.Variable var = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		var.setName(getOutputVariableName());
		var.setMessageType((org.eclipse.wst.wsdl.Message) getOutput().getMessage());
		return var;
	}
	
	public org.eclipse.bpel.model.Variable createFaultVariable(String faultName) {
		Fault fault = getFault(faultName);
		if (fault == null) {
			return null;
		}
		
		org.eclipse.bpel.model.Variable var = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		var.setName(faultName);
		var.setMessageType((org.eclipse.wst.wsdl.Message) fault.getMessage());
		return var;
	}
	
	public String createDummyPartLiteral(org.eclipse.wst.wsdl.Part part, String[] assigns, boolean useElementName) throws ConversionException {
		WSDL2DOM xsd2dom = new WSDL2DOM(wsdlDefinition);
		String dom;
		try {
			dom = xsd2dom.getInstanceAsString(part, assigns, useElementName);
		} catch (Throwable t) {
			throw new ConversionException("Failed to create a dummy part literal for the part " + part.getName(), t);
		}
		return "<![CDATA[" + dom + "]]>"; //$NON-NLS-1$  //$NON-NLS-2$
	}

}
