package com.tibco.bx.debug.core.invoke.transport;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDNamedComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.debug.core.invoke.datamodel.LaunchWsdlOperationElement;
import com.tibco.bx.debug.core.invoke.datamodel.SOAPMessage;
import com.tibco.bx.debug.core.invoke.util.SOAPMessageUtils;
import com.tibco.bx.debug.core.runtime.IBxProcessListing;
import com.tibco.bx.debug.core.ws.util.WsdlUtil;
import com.tibco.bx.debug.core.ws.util.XMLUtils;

public class SOAPMessageBuilder {

	/*private static final String LITERAL = "literal";//$NON-NLS-1$
	private static final String LINE_SEPARATOR = System.getProperties()
			.getProperty("line.separator");//$NON-NLS-1$
			
*/	public static final String EMPTY_STRING = "";//$NON-NLS-1$
	public static final String XSD_NS_URI_PREFIX = "http://www.w3.org";//$NON-NLS-1$
	public static SOAPMessageBuilder instance = new SOAPMessageBuilder();
	
	SOAPMessageProcessor processor = new SOAPMessageProcessor();
	private Document doc = null;
	
	private SOAPMessageBuilder() {
    }

	public ISOAPMessage buildSoapMessage(LaunchWsdlOperationElement operation,
			String username, String password)throws ParserConfigurationException {
		ISOAPMessage message = null;
		if(operation != null && operation.getBindingElement() != null){
			message = new SOAPMessage(operation, processor);
			processor.initMessage(message);
			if(username != null && !"".equals(username.trim())){//$NON-NLS-1$
				buildHeader(operation, message, username, password);//build wss header
			}
			buildContent(operation, message);
		}
		return message;
	}

	public ISOAPMessage buildSoapMessage(EObject startActivity,
			IBxProcessListing processListing, String url) throws CoreException, ParserConfigurationException {
		assert (processListing != null);
		LaunchWsdlOperationElement wsdlElement;
		wsdlElement = new LaunchWsdlOperationElement(WsdlUtil.getWSDLOperation(startActivity, processListing, url));
		String username = processListing.getConnection().getUsername();
		String password = processListing.getConnection().getPassword();
		return buildSoapMessage(wsdlElement, username, password);
	}

	private void buildContent(LaunchWsdlOperationElement operation, ISOAPMessage message) {
		setBodyContentFromModel(operation, message);
	}

	private void buildHeader(LaunchWsdlOperationElement operation, ISOAPMessage message, String username, String password) {
		Hashtable namespaceTable = new Hashtable(message.getNamespaceTable());
		SOAPMessageUtils.setHeaderContentFromModel(namespaceTable, operation, message, username, password);

	}
	
	
	private void setBodyContentFromModel(
			LaunchWsdlOperationElement operation, 
			ISOAPMessage message) {
		Vector bodyEntries = null;
		Element parent = null;
		
		try{
			if( doc == null ){
				doc = XMLUtils.createNewDocument();
				parent = doc.createElement("root");//$NON-NLS-1$
				doc.appendChild( parent );
				
			}else{
				//remove child of root element
				parent = doc.getDocumentElement();
				parent = XMLUtils.removeAllChild(parent);
			}
		}catch(ParserConfigurationException e  ){
			e.printStackTrace();
		}
				
		Iterator it = operation.getOrderedBodyParts().iterator();
		if( !message.getOperation().isDocumentStyle() ){
			bodyEntries = buildRcpRequest( operation ,doc  );
		}else {
		    bodyEntries = buildDocumentRequest( operation , doc  );	
		}
		
		if( bodyEntries != null ){
			Element[] bodyContent = new Element[bodyEntries.size()];
			bodyEntries.copyInto(bodyContent);
			message.setBodyContent(bodyContent);
		}
	}

	private Vector buildRcpRequest( LaunchWsdlOperationElement operation, Document document ){
		
		
		Vector bodyEntries = new Vector();
		//get namespace 
	    String namespace = operation.getDefinition().getTargetNamespace();	
	    for(Iterator it = operation.getOrderedBodyParts().iterator(); it.hasNext();){
	    	Part part = (Part) it.next();
	    	QName typeName = part.getTypeName();
	    		XSDNamedComponent component = operation.getSchema(part);
	    		if( component != null ){
	    			Element param = document.createElement( part.getName() );
	    			//parent.appendChild(param);
	    			//component.getTargetNamespace();
	    			Element paramNode = null;
	    			String componentNamespaceURI = component.getTargetNamespace();
	    			String elementNamespaceURI = component.getElement().getNamespaceURI();
	    			if( componentNamespaceURI != null && !componentNamespaceURI.startsWith(XSD_NS_URI_PREFIX) 
	    					&& elementNamespaceURI != null && !elementNamespaceURI.startsWith(XSD_NS_URI_PREFIX)){
	    				paramNode = document.createElementNS( componentNamespaceURI , 
	    								getNsPrefix(component.getTargetNamespace()) +  part.getName() );
	    				param.appendChild(paramNode);
	    			}else {
	    				paramNode = param;
	    			}
	    			SOAPMessageUtils.createSampleForType( component , document , paramNode );
	    			bodyEntries.add(param);
	    		}
	    }
	    
        return bodyEntries;
	}
	
	private Vector buildDocumentRequest( LaunchWsdlOperationElement operation , Document document ){
	    	
		Vector bodyEntries = new Vector();
        for(Iterator it = operation.getOrderedBodyParts().iterator(); it.hasNext();){
            Part part = (Part) it.next();
            if( part.getName() != null || part.getTypeName() != null ){
                QName elementName = part.getElementName();
                QName typeName = part.getTypeName();
                
                if( elementName != null ){
                    //create a element named with elementName
                	Element partElement = document.createElementNS(elementName.getNamespaceURI(),
                                             getNsPrefix( elementName.getNamespaceURI () ) +
                                             elementName.getLocalPart());

                	XSDNamedComponent component = operation.getSchema(part);
                	if( component != null ){
                		Element type = SOAPMessageUtils.createSampleForType( component , document , partElement );
                		bodyEntries.add(type);
                	}
                }
            }
        }
		return bodyEntries;
	}

	private String getNsPrefix(String namespaceURI){
		if( namespaceURI == null || namespaceURI.length() < 1 || namespaceURI.startsWith(XSD_NS_URI_PREFIX)){
			return EMPTY_STRING;
		}
        //http://example.com/MultipleElmDiffComplex
        Pattern ptn = Pattern.compile( "(?<=/)([\\w\\d_\\.]*)/?$" );//$NON-NLS-1$
        Matcher matcher = ptn.matcher( namespaceURI ) ;
        if( matcher.find() ) {
            String prefix = matcher.group( 1 ) ;
            return prefix.length() > 3 ? prefix.substring( 0 , 3 ).toLowerCase() + ":" : prefix.toLowerCase() + ":";//$NON-NLS-1$ //$NON-NLS-2$
        }
		return EMPTY_STRING;
	}
	
	/*static final String PROP_READ_ONLY = "prop_read_only";//$NON-NLS-1$
	static final String PROP_RAW_BYTES = "prop_raw_bytes";//$NON-NLS-1$
*/}
