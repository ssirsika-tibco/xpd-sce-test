package com.tibco.bx.xpdl2bpel.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Part;
import javax.wsdl.extensions.ExtensionDeserializer;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.bpel.model.Extension;
import org.eclipse.bpel.model.Extensions;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.adapters.AdapterRegistry;
import org.eclipse.bpel.model.adapters.INamespaceMap;
import org.eclipse.bpel.model.extensions.BPELActivityDeserializer;
import org.eclipse.bpel.model.extensions.BPELExtensionRegistry;
import org.eclipse.bpel.model.extensions.BPELUnknownExtensionDeserializer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.UnknownExtensibilityElement;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.internal.util.WSDLResourceFactoryImpl;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.tibco.bx.bpelExtension.BxExtensionRegistry;
import com.tibco.bx.bpelExtension.BxExtensionSerializer;
import com.tibco.bx.bpelExtension.extensions.ExtensionsFactory;
import com.tibco.bx.bpelExtension.extensions.ExtensionsPackage;
import com.tibco.bx.bpelExtension.extensions.Script;
import com.tibco.bx.bpelExtension.extensions.impl.CallProcessImpl;
import com.tibco.bx.bpelExtension.extensions.misc.AuditDescriptorSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.BxBPELResourceFactoryImpl;
import com.tibco.bx.bpelExtension.extensions.misc.CallProcessDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.CallProcessSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.GlobalSignalMappingsSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.IntermediateEventDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.OnReceiveEventExtensionElementDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.OnReceiveEventExtensionElementSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.ParametersSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.ReceiveEventActivityDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.ReceiveEventActivitySerializer;
import com.tibco.bx.bpelExtension.extensions.misc.ReplyImmediateSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.ScriptDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.SignalDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.SignalEventDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.SignalUpdateEventSerializer;
import com.tibco.bx.bpelExtension.extensions.misc.StartEventDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.TimerEventDeserializer;
import com.tibco.bx.bpelExtension.extensions.misc.VariableDescriptorSerializer;
import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.N2PEConstants;
import com.tibco.bx.xpdl2bpel.converter.internal.ConverterContext;
import com.tibco.bx.xpdl2bpel.util.internal.BxExtensibilityAttributesDeserializer;
import com.tibco.bx.xpdl2bpel.util.internal.EObjectActivityDeserializer;
import com.tibco.bx.xpdl2bpel.util.internal.EObjectActivitySerializer;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;

public class BPELUtils {

    public static final String EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions"; //$NON-NLS-1$
    public static final String DATABASE_EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions/database"; //$NON-NLS-1$
    public static final String JAVA_EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions/java"; //$NON-NLS-1$
    public static final String EMAIL_EXTENSION_NAMESPACE_URI = "http://www.tibco.com/bpel/2007/extensions/email"; //$NON-NLS-1$
    public static final String EXTENSION_PREFIX = "tibex"; //$NON-NLS-1$
    private static final String EXTENSION_ACTIVITY_LOCALNAME = "extActivity"; //$NON-NLS-1$
    private static final String DOCUMENTATION_EXTENSION = "description"; //by-pass to problem in BPEL model with extension activities and documentation element //$NON-NLS-1$
    
    private static final String ON_RECEIVE_EVENT_LOCALNAME = "OnReceiveEvent"; //$NON-NLS-1$
    private static final String RECEIVE_EVENT_LOCALNAME = "ReceiveEvent"; //$NON-NLS-1$
    private static final String SIGNAL_UPDATE_EVENT_LOCALNAME = "SignalUpdateEvent"; //$NON-NLS-1$
    private static final String VARIABLE_DESCRIPTOR_LOCALNAME = "VariableDescriptor"; //$NON-NLS-1$
    private static final String AUDIT_DESCRIPTOR_LOCALNAME = "AuditDescriptor"; //$NON-NLS-1$
    private static final String REPLY_IMMEDIATE_LOCALNAME = "ReplyImmediate"; //$NON-NLS-1$
    public static final String CONNECTION_RESOURCE_ATT = "connectionResource"; //$NON-NLS-1$
    public static final String DATABASE_JDBC_TYPE = "db:JdbcDataSource";//$NON-NLS-1$
    public static final String EMAIL_SMTP_TYPE = "smtp:SmtpConfiguration";//$NON-NLS-1$
    private static final String PARAMETERS_LOCALNAME = "Parameters"; //$NON-NLS-1$
    
	public static final String DATABASE_SR_NS = "http://xsd.tns.tibco.com/amf/models/sharedresource/jdbc";//$NON-NLS-1$
	public static final String EMAIL_SR_NS = "http://xsd.tns.tibco.com/amf/models/sharedresource/smtp";//$NON-NLS-1$
	public static final String HTTP_SR_NS = "http://xsd.tns.tibco.com/amf/models/sharedresource/httpclient";//$NON-NLS-1$
    
    /**
     * gets the source activity of the specified link
     * @param link get source of this link
     * @return  source activity
     */
    public static org.eclipse.bpel.model.Activity getSourceActivity(org.eclipse.bpel.model.Link link) {
        EList<Source> sources = link.getSources();
        if (sources !=null && sources.size()>0) {
            Source source = sources.get(0);
            if (source!=null) {
                return source.getActivity();
            }
        }
        return null;
    }

    // collects the source activities of the specified links
    public static List<Activity> getSourceActivities(List<Link> links) {
        List<Activity> result = new ArrayList<Activity>();
        for (Link link: links) {
            Activity source = getSourceActivity(link);
            if (!result.contains(source)) {
                result.add(source);
            }
        }
        return result;
    }

    /**
     * gets the target activity of the specified link
     * @param link get target of this link
     * @return  target activity
     */
    public static org.eclipse.bpel.model.Activity getTargetActivity(org.eclipse.bpel.model.Link link) {
        EList<Target> targets = link.getTargets();
        if (targets !=null && targets.size()>0) {
            Target target = targets.get(0);
            if (target!=null) {
                return target.getActivity();
            }
        }
        return null;
    }

    // collects the target activities of the specified links
    public static List<Activity> getTargetActivities(List<Link> links) {
        List<Activity> result = new ArrayList<Activity>();
        for (Link link: links) {
            Activity target = getTargetActivity(link);
            if (!result.contains(target)) {
                result.add(target);
            }
        }
        return result;
    }

    /** Find all the BPEL Activities in the BPEL Flow passed in that have no
     * incoming Links (that act as starting points for sequence flow).
     * @param bpelFlow The BPEL Flow to search for root Activities.
     * @return A List of all the root Activities in the BPEL flow passed in. */
    public static List<org.eclipse.bpel.model.Activity> getRootActivities (final org.eclipse.bpel.model.Flow bpelFlow) {
        ArrayList<org.eclipse.bpel.model.Activity>  rootActivities = new ArrayList<org.eclipse.bpel.model.Activity> ();
        List<org.eclipse.bpel.model.Activity>  flowActivities =
            (List<org.eclipse.bpel.model.Activity>) bpelFlow.getActivities ();
        if (flowActivities != null) {
            for (org.eclipse.bpel.model.Activity activity : flowActivities) {
                boolean  rootActivity = true;
                org.eclipse.bpel.model.Targets  targets = activity.getTargets ();
                if (targets != null) {
                    List<org.eclipse.bpel.model.Target>  targetList =
                        (List<org.eclipse.bpel.model.Target>) targets.getChildren ();
                    if (targetList != null && targetList.size () > 0) {
                        rootActivity = false;
                    }
                }
                if (rootActivity) {
                    rootActivities.add (activity);
                }
            }
        }
        return rootActivities;
    }


    /** Get the List of Activities that are targets of Links originating from
     * to the sourceActivity.
     * @param sourceActivity The Activity to find connected target Activities.
     * @return The List of target Activities from the requested source Activity. */
    public static List<org.eclipse.bpel.model.Activity> getTargetActivities (
            final org.eclipse.bpel.model.Activity sourceActivity) {
        List<org.eclipse.bpel.model.Activity>  targetActivities = new ArrayList<org.eclipse.bpel.model.Activity> ();
        List<org.eclipse.bpel.model.Link>  linksFromActivity = getLinksFromActivity (sourceActivity);
        if (linksFromActivity != null) {
            for (org.eclipse.bpel.model.Link link : linksFromActivity) {
                List<org.eclipse.bpel.model.Target>  targets = (List<org.eclipse.bpel.model.Target>) link.getTargets ();
                if (targets != null) {
                    // This is a little strange, multiple targets are allowed for a single link,
                    // but there should only be one target per Link for any valid BPEL model
                	for (org.eclipse.bpel.model.Target target : targets) {
                        targetActivities.add (target.getActivity());
					}
                }
            }
        }
        return targetActivities;
    }

    /** Get the List of Links that are "from" the sourceActivity (have the
     * sourceActivity as their source).
     * @param sourceActivity The Activity to find connected target Activities.
     * @return The List of target Activities from the requested source Activity. */
    public static List<org.eclipse.bpel.model.Link> getLinksFromActivity (
            final org.eclipse.bpel.model.Activity sourceActivity) {
        List<org.eclipse.bpel.model.Link>  linksFromActivity =
            new ArrayList<org.eclipse.bpel.model.Link> ();
        org.eclipse.bpel.model.Sources  activitySources = sourceActivity.getSources ();
        if (activitySources != null) {
            List<org.eclipse.bpel.model.Source>  sources = (List<org.eclipse.bpel.model.Source>) activitySources.getChildren ();
            if (sources != null) {
                for (org.eclipse.bpel.model.Source source : sources) {
                    org.eclipse.bpel.model.Link  link = source.getLink ();
                    linksFromActivity.add (link);
                }
            }
        }
        return linksFromActivity;
    }

    /**
     * Get the list of Links that are "to" the targetActivity (have the targetActivity
     * as their target).
     * @param targetActivity The activity to find it's incoming Links
     * @return The List of Links that target this activity
     */
    public static List<org.eclipse.bpel.model.Link> getLinksToActivity(
            final org.eclipse.bpel.model.Activity targetActivity) {
        List<org.eclipse.bpel.model.Link>  linksToActivity =
            new ArrayList<org.eclipse.bpel.model.Link> ();
        org.eclipse.bpel.model.Targets  targets = targetActivity.getTargets();
        if (targets!=null) {
            List<org.eclipse.bpel.model.Target> targetList = (List<org.eclipse.bpel.model.Target>) targets.getChildren ();
            for (org.eclipse.bpel.model.Target target: targetList) {
                linksToActivity.add(target.getLink());
            }
        }
        return linksToActivity;
    }

    /**
     * Use this to create an extension element that will be part of the tibco extension namespace
     * @param itemToExtend bpel contruct to extend (activity, link, etc)
     * @param elementName extension element name
     * @return extension element. This is an empty element with the correct namespace attached to the bpel construct.
     *          The caller needs to add attributes and nodes to this element as appropriate
     */
    public static Element makeExtensionElement(org.eclipse.bpel.model.ExtensibleElement itemToExtend, String elementName) {
        Element newElement = null;
        if (itemToExtend instanceof org.eclipse.bpel.model.ExtensionActivity
        		&& ((org.eclipse.bpel.model.ExtensionActivity)itemToExtend).getElement()!=null) {
            // special handling required
            //todo verify this code - not yet required
            org.eclipse.bpel.model.ExtensionActivity extAct = (org.eclipse.bpel.model.ExtensionActivity)itemToExtend;
            Element element = extAct.getElement();
            newElement = element.getOwnerDocument().createElementNS(EXTENSION_NAMESPACE_URI, EXTENSION_PREFIX + ":"+elementName); //$NON-NLS-1$
            element.appendChild(newElement);
        } else {
            UnknownExtensibilityElement unknownExt = WSDLFactory.eINSTANCE.createUnknownExtensibilityElement();
            try {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document document = builder.newDocument();
				newElement = document.createElement(EXTENSION_PREFIX +":"+elementName); //$NON-NLS-1$
				newElement.setAttribute("xmlns:"+ EXTENSION_PREFIX, EXTENSION_NAMESPACE_URI); //$NON-NLS-1$
                QName qname = new QName(EXTENSION_NAMESPACE_URI, elementName);
                unknownExt.setElementType(qname);
                unknownExt.setElement(newElement);

            } catch (ParserConfigurationException e) {
				// shouldn't happen
			}
            itemToExtend.addExtensibilityElement(unknownExt);
        }
        return newElement;
    }

    public static void setDocumentation(org.eclipse.bpel.model.ExtensibleElement targetElement, String documentation) {
        if (targetElement instanceof org.eclipse.bpel.model.ExtensionActivity) {
            // special handling required. use extension attribute since bpel model does not support documentation element
            addExtensionAttribute(targetElement, DOCUMENTATION_EXTENSION, documentation);
           // element.setAttribute(EXTENSION_PREFIX+':'+attributeName, value);
        } else {
            org.eclipse.bpel.model.Documentation bpelDocumentation = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createDocumentation();
            bpelDocumentation.setValue(documentation);
            targetElement.setDocumentation(bpelDocumentation);
        }
    }

    public static void setDocumentation(org.eclipse.bpel.model.ExtensibleElement targetElement, Documentation documentation) {
		if (documentation != null) {
			setDocumentation(targetElement, documentation.getValue());
		}
    }

    /**
     * Add required N2PE extensions - http://www.tibco.com/bpel/2007/extensions
     * @param bpelProcess
     */
	public static void addN2PEExtension(org.eclipse.bpel.model.Process bpelProcess) {
		Extension extension = BPELFactory.eINSTANCE.createExtension();
		extension.setNamespace(EXTENSION_NAMESPACE_URI);
		extension.setMustUnderstand(true);
		Extensions extensions = bpelProcess.getExtensions();
		if (extensions==null) {
		    extensions = BPELFactory.eINSTANCE.createExtensions();
		}
		extensions.getChildren().add(extension);
		bpelProcess.setExtensions(extensions);
	}

    /**
     * Use this to create an extension attribute that will be part of the tibco extension namespace
     * @param itemToExtend bpel contruct to extend (activity, link, etc)
     * @param attributeName extension attribute name
     * @param value extension attribute value
     */
    public static void addExtensionAttribute(org.eclipse.bpel.model.ExtensibleElement itemToExtend, String attributeName, String value) {
    	QName name = new QName(EXTENSION_NAMESPACE_URI, attributeName, EXTENSION_PREFIX);
    	addExtensionAttribute(itemToExtend, name, value);
	}

    /**
     * Get the extension attribute (assuming tibex namespace) set on the given object
     * <p>
     * (Added for BX-3712 as an equivalent of {@link #addExtensionAttribute(ExtensibleElement, String, String)}
     *    
     * @param extendibleElement
     * @param attributeName
     * 
     * @return Attribute value or <code>null</code> if none set.
     */
    public static String getExtensionAttribute(org.eclipse.bpel.model.ExtensibleElement extendibleElement, String attributeName) {
    	String qualifiedAttrName = EXTENSION_PREFIX + ":" + attributeName;
    	
    	EList extensibilityElements = extendibleElement.getEExtensibilityElements();
		
		for (Object object : extensibilityElements) {
			if (object instanceof ExtensibilityElement) {
				ExtensibilityElement extensibilityElement = (ExtensibilityElement)object;
				
				Element extElement = extensibilityElement.getElement();
				
				if (extElement != null) {
					Attr attribute = 
							extElement.getAttributeNode(qualifiedAttrName);
					
					if (attribute != null) {
						return attribute.getValue();
					}
				}
			}
		}
		
    	return null;
    }
    
    /**
     * Use this to create an extension attribute of any extension namespace
     * @param itemToExtend bpel contruct to extend (activity, link, etc)
     * @param attributeName extension attribute name
     * @param value extension attribute value
     */
    public static void addExtensionAttribute(org.eclipse.bpel.model.ExtensibleElement itemToExtend, QName attributeName, String value) {
    	String namespace = attributeName.getNamespaceURI();
    	String prefix = attributeName.getPrefix();
    	String localName = attributeName.getLocalPart();
    	String nameStr = prefix+':'+localName; //$NON-NLS-1$
    	
        if (itemToExtend instanceof org.eclipse.bpel.model.ExtensionActivity
        		&& ((org.eclipse.bpel.model.ExtensionActivity)itemToExtend).getElement()!=null) {
            // special handling required
        	org.eclipse.bpel.model.ExtensionActivity extAct = (org.eclipse.bpel.model.ExtensionActivity)itemToExtend;
            Element element = extAct.getElement();
            if (value != null) {
    			element.setAttribute(nameStr, value);
            } else {
    			element.removeAttribute(nameStr);
            }
        } else {
        	if (value != null) {
                org.eclipse.bpel.model.UnknownExtensibilityAttribute attr = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createUnknownExtensibilityAttribute();
                try {
    				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    				Document document = builder.newDocument();
    				Element element = document.createElement(prefix +":extensibilityAttributes"); //$NON-NLS-1$
    				element.setAttribute("xmlns:"+ prefix, namespace); //$NON-NLS-1$
    				element.setAttribute(nameStr, value);
    				attr.setElement(element);
    			} catch (ParserConfigurationException e) {
    				// shouldn't happen
    			}
                itemToExtend.addExtensibilityElement(attr);
        	} else {
        		EList extensibilityElements = itemToExtend.getEExtensibilityElements();
        		for (Object object : extensibilityElements) {
					if (object instanceof ExtensibilityElement) {
						ExtensibilityElement extensibilityElement = (ExtensibilityElement)object;
						if (extensibilityElement.getElement().hasAttribute(nameStr)) {
							extensibilityElement.getElement().removeAttribute(nameStr);
						}
					}
				}
        	}
        }
    }

   
    
    
    /**
     * Use this to add an extensions element created from an emf object that will be part of the tibco extension namespace
     * @param itemToExtend bpel contruct to extend (activity, link, etc)
     * @param emfObject  emf object to form extension element
     * @param elementName  element name
     */
    public static void addExtensionElementFromEmfObject(org.eclipse.bpel.model.ExtensibleElement itemToExtend, EObject emfObject, String elementName) {
        EObject copy = EcoreUtil.copy(emfObject);
        Element emfNode = toDOMElement(copy);
        UnknownExtensibilityElement unknownExt = WSDLFactory.eINSTANCE.createUnknownExtensibilityElement();
        QName qname = new QName(EXTENSION_NAMESPACE_URI, elementName);
        unknownExt.setElementType(qname);
        unknownExt.setElement(emfNode);
        itemToExtend.addExtensibilityElement(unknownExt);
    }

    public static Element toDOMElement(EObject emfObj) {
        XMIResourceImpl res = new XMIResourceImpl();
        res.setEncoding("UTF-8");
        res.getContents().add(emfObj);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
              res.save(baos, null);
        } catch (IOException e) {
              e.printStackTrace();
        }
        byte[] b = baos.toByteArray();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                    .newInstance();
        DocumentBuilder docBuilder = null;
        try {
              docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
              e.printStackTrace();
        }

        Document doc = null;
        try {
        	doc = docBuilder.parse(new ByteArrayInputStream(b));
        } catch (SAXException e) {
              e.printStackTrace();
        } catch (IOException e) {
              e.printStackTrace();
        }
        return doc.getDocumentElement();
    }

    
    public static org.eclipse.bpel.model.ExtensionActivity createExtensionActivityFromEmfObject(EObject emfObject, boolean createInstance) {
		//EObject copy = EcoreUtil.copy(emfObject);
    	// look up serializer from registry
		BxExtensionSerializer serializer = ConverterActivator.getBxExtensionRegistry().getSerializer(emfObject.getClass());
		if (serializer!=null) {
            // use serializer
            DocumentBuilder docBuilder = null;
            try {
                docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();  //todo handle exception case
                return null;
            }
            Document extensionActivityDoc = docBuilder.newDocument();
            Element extElement = extensionActivityDoc.createElementNS(EXTENSION_NAMESPACE_URI, EXTENSION_ACTIVITY_LOCALNAME);
            extElement.setAttribute("xmlns:"+EXTENSION_PREFIX, EXTENSION_NAMESPACE_URI); //$NON-NLS-1$
            extElement.setPrefix(EXTENSION_PREFIX);
            serializer.serialize(emfObject, extElement);
            org.eclipse.bpel.model.ExtensionActivity extAct = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createExtensionActivity();
            extAct.eClass().setName(EXTENSION_ACTIVITY_LOCALNAME);
            extAct.eClass().getEPackage().setNsURI(EXTENSION_NAMESPACE_URI);
            extAct.setElement(extElement);
            return extAct;
        } else {
            // use default serialization
        	Node emfNode = toDOMElement(emfObject);
        	return createExtensionActivityFromXML(emfNode, EXTENSION_ACTIVITY_LOCALNAME, createInstance);
		}
    }

	public static org.eclipse.bpel.model.ExtensionActivity createExtensionActivityFromXML(Node emfNode, String localName, boolean createInstance) {
        DocumentBuilder docBuilder = null;
        try {
        	docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //todo handle exception case
            return null;
        }

        Document extensionActivityDoc = docBuilder.newDocument();
        Element extElement = extensionActivityDoc.createElementNS(EXTENSION_NAMESPACE_URI, localName);
        extElement.setAttribute("xmlns:"+EXTENSION_PREFIX, EXTENSION_NAMESPACE_URI); //$NON-NLS-1$
        extElement.setPrefix(EXTENSION_PREFIX);
        if (createInstance) {
            extElement.setAttribute("createInstance","yes"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        if (emfNode!=null) {
	        Node importedNode = extensionActivityDoc.importNode(emfNode, true);
	        extElement.appendChild(importedNode);
	        //ConvertUtil.printElement(extElement);
        }

        org.eclipse.bpel.model.ExtensionActivity extAct = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createExtensionActivity();
        extAct.eClass().setName(localName);
        extAct.eClass().getEPackage().setNsURI(EXTENSION_NAMESPACE_URI);
        extAct.setElement(extElement);
        return extAct;
	}

    /** Save the BPEL process model parameter to the file path requested.
     * @param BPELFilePath The full file path of the saved file.
     * @param bpelProcess The BPEL process model to save in the file.
     * @param additionalNamespaceMap 
     * @throws IOException 
     */
    public static void saveBPELFile(String BPELFilePath, org.eclipse.bpel.model.Process bpelProcess, Map<String, String> additionalNamespaceMap) throws IOException {
		// Create resource set and resource
		ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
                "bpel", new BxBPELResourceFactoryImpl());

		org.eclipse.bpel.model.extensions.BPELExtensionRegistry extensionRegistry = org.eclipse.bpel.model.extensions.BPELExtensionRegistry.getInstance();
        QName serviceTaskQname = new QName(EXTENSION_NAMESPACE_URI, EXTENSION_ACTIVITY_LOCALNAME, EXTENSION_PREFIX);
        QName onReceiveEventQname = new QName(EXTENSION_NAMESPACE_URI, ON_RECEIVE_EVENT_LOCALNAME, EXTENSION_PREFIX);
        QName receiveEventQname = new QName(EXTENSION_NAMESPACE_URI, RECEIVE_EVENT_LOCALNAME, EXTENSION_PREFIX);
        QName signalUpdateEventQname = new QName(EXTENSION_NAMESPACE_URI, SIGNAL_UPDATE_EVENT_LOCALNAME, EXTENSION_PREFIX);
        QName variableDescriptorQname = new QName(EXTENSION_NAMESPACE_URI, VARIABLE_DESCRIPTOR_LOCALNAME, EXTENSION_PREFIX);
        QName auditDescriptorQname = new QName(EXTENSION_NAMESPACE_URI, AUDIT_DESCRIPTOR_LOCALNAME, EXTENSION_PREFIX);
        QName replyImmediateQname = new QName(EXTENSION_NAMESPACE_URI, REPLY_IMMEDIATE_LOCALNAME, EXTENSION_PREFIX);
        QName parametersQname = new QName(EXTENSION_NAMESPACE_URI, PARAMETERS_LOCALNAME, EXTENSION_PREFIX);
        QName globalSignalQname = new QName(EXTENSION_NAMESPACE_URI, ExtensionsPackage.Literals.GLOBAL_SIGNAL_MAPPINGS.getName(), EXTENSION_PREFIX);
        org.eclipse.bpel.model.extensions.BPELActivitySerializer bpelActivitySerializer = extensionRegistry.getActivitySerializer(serviceTaskQname);
        if (bpelActivitySerializer == null) {
        	BxExtensionRegistry bxExtensionRegistry = ConverterActivator.getBxExtensionRegistry();
            extensionRegistry.registerActivitySerializer(serviceTaskQname, new EObjectActivitySerializer());
            extensionRegistry.registerActivitySerializer(receiveEventQname, new ReceiveEventActivitySerializer(bxExtensionRegistry));
            extensionRegistry.registerSerializer(ExtensibleElement.class, onReceiveEventQname, new OnReceiveEventExtensionElementSerializer(bxExtensionRegistry));
            extensionRegistry.registerSerializer(ExtensibleElement.class, signalUpdateEventQname, new SignalUpdateEventSerializer());
            extensionRegistry.registerSerializer(ExtensibleElement.class, variableDescriptorQname, new VariableDescriptorSerializer());
            extensionRegistry.registerSerializer(ExtensibleElement.class, auditDescriptorQname, new AuditDescriptorSerializer());
            extensionRegistry.registerSerializer(ExtensibleElement.class, replyImmediateQname, new ReplyImmediateSerializer());
            extensionRegistry.registerSerializer(ExtensibleElement.class, parametersQname, new ParametersSerializer());
            extensionRegistry.registerSerializer(ExtensibleElement.class, globalSignalQname, new GlobalSignalMappingsSerializer());
		}

		URI bpelURI = URI.createPlatformResourceURI(BPELFilePath, false);
		Resource resource = resourceSet.createResource(bpelURI);

	
		Map<String, String> namespaceMap = org.eclipse.bpel.model.util.BPELUtils.getNamespaceMap(bpelProcess);
		namespaceMap.put(EXTENSION_PREFIX, EXTENSION_NAMESPACE_URI);
		namespaceMap.put(N2PEConstants.XSD_PREFIX, N2PEConstants.XSD_NAMESPACE_URI);
		namespaceMap.putAll(additionalNamespaceMap);
		// Add the root object to the resource
		resource.getContents().add(bpelProcess);
        AdapterFactory bxAdapterFactory = new bxAdapterFactory(org.eclipse.bpel.model.util.BPELUtils.getNamespaceMap(bpelProcess));
		AdapterRegistry.INSTANCE.registerAdapterFactory(ExtensionsPackage.eINSTANCE, bxAdapterFactory);
		
		// Serialize resource
        Map<Object, Object> options = new HashMap<Object, Object>();
        options.put(XMLResource.OPTION_ENCODING, "UTF8");
		resource.save(options);

        AdapterRegistry.INSTANCE.unregisterAdapterFactory(ExtensionsPackage.eINSTANCE, bxAdapterFactory);
		
		//set the "derived" flag
		try {
			IFile file = WorkspaceSynchronizer.getFile(resource);
			if (file != null && file.exists()) {
				file.refreshLocal(IResource.DEPTH_ZERO, null);
				file.setDerived(true);
			}
		} catch (CoreException e) {
			ConverterActivator.log(e.getStatus());
		}
	}

	public static void setXpdlId(org.eclipse.bpel.model.ExtensibleElement item, String xpdlId) {
    	addExtensionAttribute(item, N2PEConstants.XPDL_ID_TAG, xpdlId);
	}

	public static void setType(org.eclipse.bpel.model.ExtensibleElement item, String type) {
    	addExtensionAttribute(item, N2PEConstants.TYPE_TAG, type);
	}

	public static void setLabel(org.eclipse.bpel.model.ExtensibleElement item, OtherAttributesContainer container) {
		String label = XPDLUtils.getDisplayName(container);
        if (label!=null) {
        	//todo remove comment below to activate label extended attribute
        	//addExtensionAttribute(item, N2PEConstants.LABEL_TAG, label);
        }
	}
	
	/** Get a legal BPEL activity name based on the activityName parameter.
	 * Primarily this method removes characters that are illegal in a BPEL activity name.
	 * @param activityName The activity name to convert to a legal BPEL activity name.
	 * @return A legal BPEL activity name based on the activityName parameter. */
	public static String getLegalBPELActivityName(String activityName, String defaultName) {
		if (activityName == null || activityName.length() == 0) { 
			return defaultName;
		}
		// TODO Is this the correct (and complete) set of name transforms?
		return activityName.replace(' ', '_').replace('.', '_'); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/**
	 * get the Process Model of BPEL from a bpelFile
	 * 
	 * @param bpelFile
	 * @return
	 * @throws IOException 
	 */
	public static Process getProcess(IFile bpelFile) throws IOException {
		if (!bpelFile.exists()) {
			return null;
		}
		ResourceSet resourceSet = new ResourceSetImpl();

        // Register the default resource factory -- only needed for stand-alone!
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
                "bpel", new BxBPELResourceFactoryImpl());
//                "bpel", new BPELResourceFactoryImpl());

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
                "wsdl", new WSDLResourceFactoryImpl());

        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
                "xsd", new XSDResourceFactoryImpl());
       
        BPELExtensionRegistry extensionRegistry = BPELExtensionRegistry.getInstance();
        QName serviceTaskQname = new QName(EXTENSION_NAMESPACE_URI, EXTENSION_ACTIVITY_LOCALNAME, EXTENSION_PREFIX);
        QName onReceiveEventQname = new QName(EXTENSION_NAMESPACE_URI, "onReceiveEvent", EXTENSION_PREFIX);
        QName receiveEventQname = new QName(EXTENSION_NAMESPACE_URI, "receiveEvent", EXTENSION_PREFIX);
        QName signalEventQname = new QName(EXTENSION_NAMESPACE_URI, "signalEvent", EXTENSION_PREFIX);
        QName startEventQname = new QName(EXTENSION_NAMESPACE_URI, "startEvent", EXTENSION_PREFIX);
        QName intermediateEventQname = new QName(EXTENSION_NAMESPACE_URI, "intermediateEvent", EXTENSION_PREFIX);
        QName timerEventQname = new QName(EXTENSION_NAMESPACE_URI, "timerEvent", EXTENSION_PREFIX);
        QName signalQname = new QName(EXTENSION_NAMESPACE_URI, "signal", EXTENSION_PREFIX);
        QName callProcessQname = new QName(EXTENSION_NAMESPACE_URI, "callProcess", EXTENSION_PREFIX);
        QName scriptQname = new QName(EXTENSION_NAMESPACE_URI, "script", EXTENSION_PREFIX);
        QName attributeQname = new QName(EXTENSION_NAMESPACE_URI, "extensibilityAttributes", EXTENSION_PREFIX);
        BPELActivityDeserializer bpelActivityDeserializer = extensionRegistry.getActivityDeserializer(serviceTaskQname);
        if (bpelActivityDeserializer == null) {
            extensionRegistry.registerActivityDeserializer(serviceTaskQname, new EObjectActivityDeserializer());
        }

        BxExtensionRegistry bxExtensionRegistry = ConverterActivator.getBxExtensionRegistry();

        try {
            ExtensionDeserializer bpelExtensionDeserializer = extensionRegistry.queryDeserializer(ExtensibleElement.class, onReceiveEventQname);
            if (bpelExtensionDeserializer == null || bpelExtensionDeserializer instanceof BPELUnknownExtensionDeserializer) {
                extensionRegistry.registerDeserializer(ExtensibleElement.class, onReceiveEventQname, new OnReceiveEventExtensionElementDeserializer(bxExtensionRegistry));
            }
            bpelActivityDeserializer = extensionRegistry.getActivityDeserializer(receiveEventQname);
            if (bpelActivityDeserializer == null) {
                extensionRegistry.registerActivityDeserializer(receiveEventQname, new ReceiveEventActivityDeserializer(bxExtensionRegistry));
            }
            bpelExtensionDeserializer = extensionRegistry.queryDeserializer(ExtensibleElement.class, attributeQname);
            if (bpelExtensionDeserializer == null || bpelExtensionDeserializer instanceof BPELUnknownExtensionDeserializer) {
                extensionRegistry.registerDeserializer(ExtensibleElement.class, attributeQname, new BxExtensibilityAttributesDeserializer());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (bxExtensionRegistry.getDeserializer(signalEventQname)==null) {
        	bxExtensionRegistry.registerDeserializer(signalEventQname, new SignalEventDeserializer());
        	bxExtensionRegistry.registerDeserializer(startEventQname, new StartEventDeserializer());
        	bxExtensionRegistry.registerDeserializer(intermediateEventQname, new IntermediateEventDeserializer());
        	bxExtensionRegistry.registerDeserializer(timerEventQname, new TimerEventDeserializer());
        	bxExtensionRegistry.registerDeserializer(signalQname, new SignalDeserializer());
        	bxExtensionRegistry.registerDeserializer(callProcessQname, new CallProcessDeserializer());
        	bxExtensionRegistry.registerDeserializer(scriptQname, new ScriptDeserializer());
        	bxExtensionRegistry.registerSerializer(CallProcessImpl.class, new CallProcessSerializer());
        }

        URI uri = URI.createPlatformResourceURI(bpelFile.getFullPath().toString(), true);        
		Resource resource = resourceSet.getResource(uri, true);
		resource.load(Collections.EMPTY_MAP);
		EList<EObject> contents = resource.getContents();
		if (contents != null && !contents.isEmpty()) {
			EObject object = contents.get(0);
			if (object instanceof Process) {
				return (Process) object;
			}
		}
		
		return null;
	}
	
	public static String getXpdlId(EObject eObject) {
        if (eObject instanceof ExtensibleElement) {
            ExtensibleElement extensibleElement = (ExtensibleElement)eObject;
            
            String xpdlId = getXpdlIdFromElement(extensibleElement.getElement());
			if (xpdlId != null && xpdlId.length() > 0) {
				return xpdlId;
			}

			EList extensibilityElements = extensibleElement.getEExtensibilityElements();
    		for (Object object : extensibilityElements) {
    			if (object instanceof ExtensibilityElement) {
    				ExtensibilityElement extensibilityElement = (ExtensibilityElement)object;
    				xpdlId = getXpdlIdFromElement(extensibilityElement.getElement());
    				if (xpdlId != null && xpdlId.length() > 0) {
    					return xpdlId;
    				}
    			}
    		}
    		
			return getXpdlId(eObject.eContainer());
        }
        return null;
    }
	
    public static String getXpdlIdFromElement(Element element) {
        if (element == null) {
            return null;
        }
        String attr = EXTENSION_PREFIX + ":" + N2PEConstants.XPDL_ID_TAG;//$NON-NLS-1$
		return element.getAttribute(attr);
    }
    
    public static Activity createScriptTask(String text) {
		Script script = ExtensionsFactory.eINSTANCE.createScript();
		script.setExpressionLanguage(N2PEConstants.JSCRIPT_LANGUAGE);
		script.setExpression(text);
    	
	    return createExtensionActivityFromEmfObject(script, false);
    }
    
    public static From createFromLiteral(String literal) {
		org.eclipse.bpel.model.From from = BPELFactory.eINSTANCE.createFrom();
		from.setLiteral(literal);
		return from;
	}

    public static From createFromScript(String expression, String grammar) {
		org.eclipse.bpel.model.From from = BPELFactory.eINSTANCE.createFrom();
		org.eclipse.bpel.model.Expression expr = BPELFactory.eINSTANCE.createExpression();
		expr.setBody(expression);
		boolean isXPath = ScriptGrammarFactory.XPATH.equals(grammar);
		expr.setExpressionLanguage(isXPath ? N2PEConstants.XPATH_1_LANGUAGE : N2PEConstants.JSCRIPT_LANGUAGE);
		from.setExpression(expr);
		return from;
	}

    public static org.eclipse.bpel.model.From createFromVariable(String varName) {
		org.eclipse.bpel.model.From from = BPELFactory.eINSTANCE.createFrom();
		org.eclipse.bpel.model.Variable fromVar = BPELFactory.eINSTANCE.createVariable();
		fromVar.setName(varName);
		from.setVariable(fromVar);
		return from;
	}
	
    public static org.eclipse.bpel.model.From createFromVariableWithPart(String varName, String partName, String query) {
		org.eclipse.bpel.model.From from = BPELFactory.eINSTANCE.createFrom();
		org.eclipse.bpel.model.Variable fromVar = BPELFactory.eINSTANCE.createVariable();
		fromVar.setName(varName);
		from.setVariable(fromVar);

		if (partName != null) {
			org.eclipse.wst.wsdl.Part fromPart = org.eclipse.wst.wsdl.WSDLFactory.eINSTANCE.createPart();
			fromPart.setName(partName);
			from.setPart(fromPart);
		}
		
		if (query != null) {
			org.eclipse.bpel.model.Query q = BPELFactory.eINSTANCE.createQuery();
			q.setValue(query);
	    	from.setQuery(q);
		}

    	return from;
	}
	
    public static org.eclipse.bpel.model.To createToVariable(String varName) {
		org.eclipse.bpel.model.To to = BPELFactory.eINSTANCE.createTo();
		org.eclipse.bpel.model.Variable toVar = BPELFactory.eINSTANCE.createVariable();
		toVar.setName(varName);
		to.setVariable(toVar);
		return to;
	}
	
    public static org.eclipse.bpel.model.To createToVariableWithPart(String varName, String partName, String query) {
		org.eclipse.bpel.model.To to = BPELFactory.eINSTANCE.createTo();
		org.eclipse.bpel.model.Variable toVar = BPELFactory.eINSTANCE.createVariable();
		toVar.setName(varName);
		to.setVariable(toVar);
		
		if (partName != null) {
			org.eclipse.wst.wsdl.Part wstPart = org.eclipse.wst.wsdl.WSDLFactory.eINSTANCE.createPart();
			wstPart.setName(partName);
			to.setPart(wstPart);
		}

		if (query != null) {
			org.eclipse.bpel.model.Query q = BPELFactory.eINSTANCE.createQuery();
			q.setValue(query);
    		to.setQuery(q);
		}
		
		return to;
	}
	
	public static org.eclipse.bpel.model.Variable createVariableFromUmlProperty(org.eclipse.uml2.uml.Property property) {
		org.eclipse.bpel.model.Variable variable = org.eclipse.bpel.model.BPELFactory.eINSTANCE.createVariable();
		variable.setName(property.getName());
		variable.setType(DataTypeUtil.getXsdForUmlType(property.getType()));
		if (property.getUpper() > 1 || property.getUpper() == -1) {
        	BPELUtils.addExtensionAttribute(variable, "array", "yes"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return variable;
	}
	
	public static Scope wrapInScope(Activity activity, String scopeName) {
		Scope scope = BPELFactory.eINSTANCE.createScope();
		scope.setName(scopeName);
		scope.setActivity(activity);
		return scope;
	}

	public static String searchAndReplacePrefixes(ConverterContext context, Definition definition, Part part, String mappingExpression) {
		Map<String, String> namespaceToPrefixMap = context.getNamespaceToPrefixMap();
		Collection<XSDSchema> allSchemas = WSDLUtils.getSchemaImports(definition, false);

		StringBuffer result = new StringBuffer();
		String[] sections = mappingExpression.split("/"); //$NON-NLS-1$
		for (int i = 0; i < sections.length; i++) {
			String section = sections[i];
			String prefix;
			String elementName;
			int pos = section.indexOf(':'); //$NON-NLS-1$
			if (pos > 0) {
				prefix = section.substring(0, pos);
				elementName = section.substring(pos+1);
			} else {
				prefix = null;
				elementName = section;
			}

			if (prefix != null) {
				String namespace = getNamespace(definition, allSchemas, prefix);
				String newPrefix = namespaceToPrefixMap.get(namespace);
				if (newPrefix == null) newPrefix = prefix; 
				result.append(newPrefix).append(':').append(elementName);
			} else {
				result.append(elementName);
			}

			if (i < sections.length-1) {
				result.append('/'); //$NON-NLS-1$				
			}
		}
		
		return result.toString();
	}

	private static String getNamespace(Definition definition, Collection<XSDSchema> allSchemas, String prefix) {
		String namespace = definition.getNamespace(prefix);
		if (namespace != null) {
			return namespace;
		}
		
		for (XSDSchema schema : allSchemas) {
			Map<String, String> namespaceMap = schema.getQNamePrefixToNamespaceMap();
			namespace = namespaceMap.get(prefix);
			if (namespace != null) {
				return namespace;
			}
		}
		
		return null;
	}
	
	static class bxAdapterFactory implements AdapterFactory {
        INamespaceMap<String, String> namespaceMap;

        bxAdapterFactory(INamespaceMap namespaceMap) {
            this.namespaceMap = namespaceMap;
        }

        public Adapter adapt(Notifier notifier, Object o) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean isFactoryForType(Object o) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object adapt(Object o, Object o1) {
            return namespaceMap;
        }

        public Adapter adaptNew(Notifier notifier, Object o) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void adaptAllNew(Notifier notifier) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
	
	/**
     * Add a script extension element to the given parent element.
     * 
     * @param parentElement parent to add script element to
     * @param name name of script element
     * @param script the content of the script.
     */
    public static void addScriptElement(ExtensibleElement parentElement, String name, String script) {
        Element scriptElement = BPELUtils.makeExtensionElement(parentElement, name);
        
        CDATASection cdata = scriptElement.getOwnerDocument().createCDATASection(script);
        scriptElement.appendChild(cdata);
        scriptElement.setAttribute("expressionLanguage", N2PEConstants.JSCRIPT_LANGUAGE);
    }

    
    /**
     * * Sid ACE-2936 Add the dataFieldDescriptor information (this is a per-process JS file and class that is generated
     * for runtime initialisation of the process data in a "data" object - and also does JSON to JS data coercion and
     * initialisation of arrays etc.
     * 
     * In the case of activity scoped data it contains the handling for the process data AND the activity scope data
     * 
     * 
     * <li>tibex:dataFieldDescriptorClass="<project-id>.<process-package-name>.<process-name>.<task-name>"/li>
     * <li>tibex:dataFieldDescriptorScript="process-js/<process-package-name>/<process-name>/<task-name>/<task-name>.js"</li>
     * 
     * @param variableContainer
     * @param process
     */
    public static void addActivityDataFieldDescriptorInfo(ExtensibleElement variableContainer,
            com.tibco.xpd.xpdl2.Activity activity) {
        /*
         * Build the path name.
         * 
         * /process-js/<process-package-name>/<process-name>/<activity-name>/<activity-name>.js
         */
        com.tibco.xpd.xpdl2.Process process = activity.getProcess();
        String pkgName = process.getPackage().getName();
        String processName = process.getName();
        String activityName = activity.getName();

        IPath descriptorPath = new Path("process-js").append(pkgName) //$NON-NLS-1$
                .append(processName).append(activityName).append(activityName + "." + "js"); //$NON-NLS-1$ //$NON-NLS-2$

        BPELUtils.addExtensionAttribute(variableContainer,
                "dataFieldDescriptorScript", //$NON-NLS-1$
                descriptorPath.toString());

        /*
         * Build the class name.
         * 
         * <application-id>.<process-package-name>.<process-name>.<activity-name>
         */
        String projectId = ProjectUtil.getProjectId(WorkingCopyUtil.getProjectFor(process));

        String className = projectId + "." + pkgName + "." + processName + "." + activityName; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        BPELUtils.addExtensionAttribute(variableContainer,
                "dataFieldDescriptorClass", //$NON-NLS-1$
                className);
    }

}
