package com.tibco.bx.xpdl2bpel.converter.internal;

import java.io.IOException;
import java.util.Collections;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.BPELUtils;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.bx.xpdl2bpel.util.XPDLUtils;
import com.tibco.xpd.datamapper.scripts.DataMapperJavascriptGenerator;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.rest.datamapper.RestScriptDataMapperProvider;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Participant;

public class ConvertRestService {

	public static String REST_PASSTHROUGH_SERVICE_FILE = "TIBCO-REST-PassThrough.wsdl"; //$NON-NLS-1$
	public static String REST_PASSTHROUGH_SERVICE_NAMESPACE = "http://www.tibco.com/rsbtPassThrough/"; //$NON-NLS-1$
	public static String REST_PASSTHROUGH_SERVICE_PORTTYPE = "RESTPassThroughService"; //$NON-NLS-1$
	public static String REST_PASSTHROUGH_SERVICE_OPERATION = "invoke"; //$NON-NLS-1$

    public static org.eclipse.bpel.model.Activity convertRestTask(
    		ConverterContext context, 
    		final Activity xpdlActivity,
    		DirectionType directionType) throws ConversionException {
    	Definition restWsdl = copyPassThroughWsdl(context);
		
        org.eclipse.bpel.model.Invoke invoke = createInvoke(context, restWsdl, xpdlActivity);

        if ((directionType.ordinal() == DirectionType.IN) || (directionType.ordinal() == DirectionType.INOUT)) {
            //add the request script to invoke
    		createScript(xpdlActivity, invoke, "restRequestScript", MappingDirection.IN); //$NON-NLS-1$
        }
        if ((directionType.ordinal() == DirectionType.OUT) || (directionType.ordinal() == DirectionType.INOUT)) {
            //add the response script to invoke
    		createScript(xpdlActivity, invoke, "restResponseScript", MappingDirection.OUT); //$NON-NLS-1$
        }

        return invoke;
	}

	public static void createScript(final Activity xpdlActivity,
			org.eclipse.bpel.model.Activity activity,
			String scriptName,
			MappingDirection direction) {
		ScriptDataMapper sdm = new RestScriptDataMapperProvider(direction).getScriptDataMapper(xpdlActivity);
        String script = new DataMapperJavascriptGenerator().convertMappingsToJavascript(sdm);
        Element ele = BPELUtils.makeExtensionElement(activity, scriptName);
		CDATASection cdata = ele.getOwnerDocument().createCDATASection(script);
		ele.appendChild(cdata);
		ele.setAttribute("expressionLanguage", N2PEConstants.JSCRIPT_LANGUAGE);
	}

	private static org.eclipse.bpel.model.Invoke createInvoke(
			ConverterContext context, Definition restWsdl, final Activity xpdlActivity) {
		org.eclipse.bpel.model.Invoke invoke = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createInvoke();
        invoke.setName(xpdlActivity.getName());

    	QName qName = new QName(REST_PASSTHROUGH_SERVICE_NAMESPACE, REST_PASSTHROUGH_SERVICE_PORTTYPE);

		//set the port type for invoke
        org.eclipse.wst.wsdl.PortType portType = (org.eclipse.wst.wsdl.PortType) restWsdl.getPortType(qName);
		invoke.setPortType(portType);
        
        //set the operation for invoke
		org.eclipse.wst.wsdl.Operation op = WSDLFactory.eINSTANCE.createOperation();
		op.setName(REST_PASSTHROUGH_SERVICE_OPERATION);
        invoke.setOperation(op);

        //set the partner link for invoke
        Participant participant = XPDLUtils.resolveXpdlSystemParticipant(xpdlActivity);
        if (participant != null) {
            PartnerLinkType partnerLinkType = PartnerlinktypeFactory.eINSTANCE.createPartnerLinkType();	
            partnerLinkType.setName(REST_PASSTHROUGH_SERVICE_PORTTYPE + "_PLT"); //$NON-NLS-1$
            org.eclipse.bpel.model.PartnerLink partnerLink = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createPartnerLink();
            partnerLink.setName(participant.getId());
    		partnerLink.setPartnerLinkType(partnerLinkType);
    		Role role = PartnerlinktypeFactory.eINSTANCE.createRole();
    		role.setPortType(portType);
    		role.setName(REST_PASSTHROUGH_SERVICE_PORTTYPE + "Provider"); //$NON-NLS-1$
            partnerLink.setPartnerRole(role);
            context.addPartnerLink(partnerLink);
            invoke.setPartnerLink(partnerLink);
        }
        
        XPDLUtils.configureRetry(invoke, xpdlActivity);
		return invoke;
	}

	private static Definition copyPassThroughWsdl(ConverterContext context)
			throws ConversionException {
		URI restWsdlUri = URI.createPlatformPluginURI(ConverterActivator.PLUGIN_ID + "/" + REST_PASSTHROUGH_SERVICE_FILE, false); //$NON-NLS-1$
    	Definition restWsdl = WSDLUtils.loadWsdlDefinition(restWsdlUri);
        String destName = context.getOutputFilePath() + System.getProperty("file.separator") + REST_PASSTHROUGH_SERVICE_FILE; //$NON-NLS-1$
	    URI destURI = URI.createPlatformResourceURI(destName, false);
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(destURI);
	    resource.getContents().add(restWsdl);
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			throw new ConversionException(e.getMessage());
		}
		
		return restWsdl;
	}


}
