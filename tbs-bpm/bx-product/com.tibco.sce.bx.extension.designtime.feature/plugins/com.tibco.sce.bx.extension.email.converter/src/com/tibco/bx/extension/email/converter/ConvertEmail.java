package com.tibco.bx.extension.email.converter;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

import com.tibco.bx.extensions.email.emailExtensionsModel.BaseType;
import com.tibco.bx.extensions.email.emailExtensionsModel.BodyType;
import com.tibco.bx.extensions.email.emailExtensionsModel.CorrespondentsType;
import com.tibco.bx.extensions.email.emailExtensionsModel.Email;
import com.tibco.bx.extensions.email.emailExtensionsModel.EmailExtensionsModelFactory;
import com.tibco.bx.extensions.email.emailExtensionsModel.SendMailServer;
import com.tibco.bx.extensions.email.emailExtensionsModel.VariableType;
import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.Messages;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.AttachmentType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.ConfigurationType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.DefinitionType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.EmailType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FilesType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.FromType;
import com.tibco.xpd.implementer.nativeservices.emailservice.email.SMTPType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;

/**
 * This class transforms the xpdl configuration model to the bpel configuration model
 * 
 * @author richardy
 */
public class ConvertEmail implements IActivityConfigurationModelBuilder  {
	static private final String PATH_SEPARATOR = ";";
	static private final String SERVER_SETTING = "A-SERVER-SETTING";
	static private final String DEFAULT_SMTP_HOST = "localhost";

	public EObject transformConfigModel(Activity xpdlActivity, Map<String, Participant> participantMap) throws ConversionException {

		Implementation impl = xpdlActivity.getImplementation();
		EObject emailObj = null;
		if (impl instanceof Task) {
			Task t = (Task) impl;
			if (t.getTaskService() != null) {				
				TaskService taskService = t.getTaskService();
				ImplementationType implementationType = taskService.getImplementation();
				if (implementationType != null) {
					int implementationTypeValue = implementationType.getValue();
					switch (implementationTypeValue) {	     
					case ImplementationType.OTHER:
						FeatureMap featureMap = taskService.getOtherElements();
						FeatureMap.ValueListIterator it =featureMap.valueListIterator();
						if (it.hasNext()) {
							// emailObj is EMailTypeImpl
							emailObj = (EObject)it.next();
							break;
						}
						return null;

					case ImplementationType.WEB_SERVICE:
					case ImplementationType.UNSPECIFIED:
						return null;
					}// endswitch	
				}
			}
		}


		EmailType xpdEmail = (EmailType) emailObj;
		Email bpelEmail = EmailExtensionsModelFactory.eINSTANCE.createEmail();		

        List<Performer> list = xpdlActivity.getPerformerList();
        if (list!=null) {
            for (Performer performer: list) {
            	if (performer != null) {
	        		String performerName = performer.getValue();
	        		if (participantMap != null) {
	        			Participant participant = participantMap.get(performerName);
	        			if (participant != null) {
	        				String name = participant.getName();
	        				bpelEmail.setConnectionResource(name);
	        			}
	        		}
            	}
            }
        }

		// transform correspondents

		DefinitionType def = xpdEmail.getDefinition();
		CorrespondentsType correspondents = EmailExtensionsModelFactory.eINSTANCE.createCorrespondentsType();

		// from
		FromType fromType = def.getFrom();


		String from = SERVER_SETTING;
		if (fromType != null) {
			ConfigurationType ctype = fromType.getConfiguration();
			if (ctype.getValue() == ConfigurationType.CUSTOM) {
				from = fromType.getValue();
				if (from == null) {
					String message = String.format(Messages.getString("ConvertEmail.requiredField"), new String[] {"From - Use Custeom Configuration"}); 
					throw new ConversionException(ConverterActivator.createErrorStatus(message, null)); 					
				}
			}
		}
		BaseType baseType = setBaseType(from);
		baseType.setValue(from);
		correspondents.setFrom(baseType);

		// to
		String to = def.getTo();
		if (to == null) { 
			String message = String.format(Messages.getString("ConvertEmail.requiredField"), new String[] {"To"}); 
			throw new ConversionException(ConverterActivator.createErrorStatus(message, null)); 

		}
		baseType = setBaseType(to);
		baseType.setValue(to);
		correspondents.setTo(baseType);

		// cc
		String cc = def.getCc();
		baseType = setBaseType(cc);
		baseType.setValue(cc);
		correspondents.setCc(baseType);

		// bcc
		String bcc = def.getBcc();
		baseType = setBaseType(bcc);
		baseType.setValue(bcc);
		correspondents.setBcc(baseType);

		// reply to
		String replyto = def.getReplyTo();
		baseType = setBaseType(replyto);
		baseType.setValue(replyto);
		correspondents.setReplyTo(baseType);

		bpelEmail.setCorrespondents(correspondents);


		// set headers
		String headers = def.getHeaders();
		baseType = setBaseType(headers);
		baseType.setValue(headers);
		bpelEmail.setHeaders(baseType);


		// set priority
		String priority = def.getPriority();
		baseType = setBaseType(priority);
		baseType.setValue(priority);
		bpelEmail.setPriority(baseType);


		// set subject
		String subject = def.getSubject();
		baseType = setBaseType(subject);
		baseType.setValue(subject);
		bpelEmail.setSubject(baseType);

		// set body
		String body = xpdEmail.getBody();
		BodyType bodyType = setBodyType(body);
		bpelEmail.setBody(bodyType);


		// set attachments
		AttachmentType attach = xpdEmail.getAttachment();
		if (attach != null) {
			FilesType attachFiles = attach.getFiles();
			String attachValue = attach.getValue();
			com.tibco.bx.extensions.email.emailExtensionsModel.AttachmentType attachmentType = EmailExtensionsModelFactory.eINSTANCE.createAttachmentType();
			baseType = setBaseType(attachValue);
			baseType.setValue(attachValue);
			attachmentType.setFieldcontents(baseType);
			String stringList = null;
			if (attachFiles != null) {
				Iterator fileItr = attachFiles.getFile().listIterator();
				while (fileItr.hasNext()) {
					String file = (String)fileItr.next();
					if (stringList != null)
						stringList += PATH_SEPARATOR+file;
					else					
						stringList = file;
				}
				if (stringList != null) {
					baseType = setBaseType(stringList);
					baseType.setValue(stringList);
					attachmentType.setFiles(baseType);
				}
			}
			bpelEmail.setAttachments(attachmentType);
		}


		// set smtp config		
		SMTPType smtp = xpdEmail.getSMTP();
		if (smtp != null) {
			ConfigurationType smtpConfig = smtp.getConfiguration();
			String smtpHost = SERVER_SETTING;
			SendMailServer sendMailServer = EmailExtensionsModelFactory.eINSTANCE.createSendMailServer();
			if (smtpConfig.getValue() == ConfigurationType.CUSTOM) {

				smtpHost = smtp.getHost();
				if (smtpHost == null) {
					smtpHost = DEFAULT_SMTP_HOST;					
				}
				BigDecimal smtpPort = smtp.getPort();
				if (smtpPort == null) {
					// default value
					smtpPort = new BigDecimal(25);
				}
				String smtpPortString = smtpPort.toPlainString();
				if (smtpPort != null) {
					baseType = setBaseType(smtpPortString);
					baseType.setValue(smtpPortString);
					sendMailServer.setPort(baseType);
				}
			}
			baseType = setBaseType(smtpHost);
			baseType.setValue(smtpHost);
			sendMailServer.setHost(baseType);
			bpelEmail.setServer(sendMailServer);
		}


		return bpelEmail;
	}
	
	private BodyType setBodyType(String value) {
		BodyType bodyType = EmailExtensionsModelFactory.eINSTANCE.createBodyType();
		boolean isBodyHtml = determineIfHtml(value);
		bodyType.setIsHtml(isBodyHtml);

		BaseType baseType = setBaseType(value, true);
		baseType.setValue(value);
		bodyType.setBody(baseType);

		return bodyType;
		
	}
	
	private BaseType setBaseType(String value) {	
		return setBaseType(value, true);
	}
	
	private BaseType setBaseType(String value, boolean checkVariable) {	
		BaseType baseType = EmailExtensionsModelFactory.eINSTANCE.createBaseType();
		if (checkVariable)
			checkVariables(baseType, value);
		return baseType;
	}
	
    static private final String VARIABLE_MARKER = "%";
    static private final char   VARIABLE_MARKER_CHAR = '%';
    static private final int 	VARIABLE_MARKER_LEN = VARIABLE_MARKER.length();
	private void checkVariables(BaseType baseType, String body) {	
		
		if (body == null)
			return;

		int index = body.indexOf(VARIABLE_MARKER);
		if (body.indexOf(VARIABLE_MARKER) < 0) {
			// no variable to insert
			return;
		}

		// get list of variables
		int bIndex = -1;
		char letter;
		for (index = 0; index < body.length(); index++) {
			letter = body.charAt(index);
			if (letter == VARIABLE_MARKER_CHAR) {
				if (bIndex >= 0) {
					VariableType varType = EmailExtensionsModelFactory.eINSTANCE.createVariableType();
					varType.setName(body.substring(bIndex,index+1));
					varType.setIndex(new Integer(bIndex));
					// tricky way to add a list of variables; there is no setVariables() method; 
					// getVariables() will create a variables list if not exist
					baseType.getVariables().add(varType);
					bIndex = -1;
					continue;
				}
				if (bIndex < 0) {
					bIndex = index;
					continue;
				}

			}
		}

	}
	
    public static String HTML_BEGIN = "<HTML";
    public static String HTML_END = "HTML>";
    public static int 	 HTML_PREFIX_LENGTH = HTML_BEGIN.length();
    private boolean determineIfHtml(String part) {
    	boolean result = false;
    	if (part == null)
    		return result;
    	
    	String trimpart = part.trim();
    	int partlen = trimpart.length();
    	if (partlen < HTML_PREFIX_LENGTH)
    		return result;
    	
    	String begin = part.substring(0, HTML_PREFIX_LENGTH);
    	String end = part.substring(partlen - HTML_PREFIX_LENGTH, partlen);
    	if (begin.equalsIgnoreCase(HTML_BEGIN) && end.equalsIgnoreCase(HTML_END)) {
    		result = true;
    	}
    	return result;
    }


/*	public EObject test(Activity xpdlActivity, Map<String, Participant> participantMap) throws ConversionException {
		Implementation impl = xpdlActivity.getImplementation();
		EObject emailObj = null;
		if (impl instanceof Task) {
			Task t = (Task) impl;
			if (t.getTaskService() != null) {				
				TaskService taskService = t.getTaskService();
				ImplementationType implementationType = taskService.getImplementation();
				if (implementationType != null) {
					int implementationTypeValue = implementationType.getValue();
					switch (implementationTypeValue) {	     
					case ImplementationType.OTHER:
						FeatureMap featureMap = taskService.getOtherElements();
						FeatureMap.ValueListIterator it =featureMap.valueListIterator();
						if (it.hasNext()) {
							// emailObj is EMailTypeImpl
							emailObj = (EObject)it.next();
							break;
						}
						return null;

					case ImplementationType.WEB_SERVICE:
					case ImplementationType.UNSPECIFIED:
						return null;
					}// endswitch	
				}
			}
		}


		EmailType xpdEmail = (EmailType) emailObj;
		Email bpelEmail = EmailExtensionsModelFactory.eINSTANCE.createEmail();


		// set headers

		bpelEmail.setHeaders("headerscontent");
		bpelEmail.setBody("bodyvalue");
		bpelEmail.setPriority("priorityval");
		bpelEmail.setSubject("subjectval");

		CorrespondentsType correspondents = EmailExtensionsModelFactory.eINSTANCE.createCorrespondentsType();
		// from
		correspondents.setFrom("from");

		// to
		correspondents.setTo("to");

		// cc
		correspondents.setCc("cc");

		// bcc
		correspondents.setBcc("bcc");

		// reply to
		correspondents.setReplyto("replyto");

		bpelEmail.setCorrespondents(correspondents);

		// set attachments


		com.tibco.bx.extensions.email.emailExtensionsModel.AttachmentType attachmentType = EmailExtensionsModelFactory.eINSTANCE.createAttachmentType();
		attachmentType.setFieldcontents("attachValue");
		attachmentType.setFiles("attachFiles.getFile().toString()");
		bpelEmail.setAttachments(attachmentType);

		// set smtp config



		// smtpConfig is not used.
		//ConfigurationType smtpConfig = smtp.getConfiguration();


		SendMailServer sendMailServer = EmailExtensionsModelFactory.eINSTANCE.createSendMailServer();
		sendMailServer.setHost("smtpHost");

		sendMailServer.setPort("smtpPort.toPlainString()");
		bpelEmail.setServer(sendMailServer);		

		//  		 Create a resource set.
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		// Get the URI of the model file.
		URI fileURI = URI.createFileURI(new File("c:/tmp/email.xmi").getAbsolutePath());

		// Create a resource for this file.
		Resource resource = resourceSet.createResource(fileURI);

		// Add the book and writer objects to the contents.
		try {
			resource.getContents().add(bpelEmail);
			resource.save(Collections.EMPTY_MAP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/////////////////////////////////////////////////////////////////////

		return bpelEmail;

	}*/

}
