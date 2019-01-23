package com.tibco.bx.debug.core.invoke.util;

import java.util.Hashtable;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xsd.XSDComplexTypeContent;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDCompositor;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.Messages;
import com.tibco.bx.debug.core.invoke.datamodel.ISOAPMessage;
import com.tibco.bx.debug.core.invoke.datamodel.LaunchWsdlOperationElement;
import com.tibco.bx.debug.core.ws.wss.header.WssHeaderBuilder;

public class SOAPMessageUtils {
	
	public static void setHeaderContentFromModel(Hashtable namespaceTable,
			LaunchWsdlOperationElement operation, ISOAPMessage message,
			String username, String password) {
	    try {
            createWssHeader(message, username, password);
        } catch (ParserConfigurationException e) {
           DebugCoreActivator.log(e);
        }
	    
	}
	
	private static void createWssHeader(ISOAPMessage message, String username, String password) 
		throws ParserConfigurationException {
         message.setHeaderContent(new Element[] {WssHeaderBuilder.instances.getWssHeader(username, password)});
	}
	
/*	public static void setBodyContentFromModel(
			Hashtable soapEnvelopeNamespaceTable,
			LaunchWsdlOperationElement operElement, ISOAPMessage soapMessage)
			throws ParserConfigurationException {
	}*/


	
	
/*	private static Element createElementFromXsd(XSDNamedComponent xsdComponent , Element parent , Document doc) throws ParserConfigurationException {
	    if(doc == null) {
	       doc = XMLUtils.createNewDocument();
	       Element docRoot = doc.createElement("root");
	       doc.appendChild(docRoot);
	       parent = doc.getDocumentElement();
	    }
	   
	    Element node = doc.createElement(xsdComponent.getName());
	    parent.appendChild(node);
	   
	    if(xsdComponent instanceof XSDComplexTypeDefinition) {
	        XSDComplexTypeDefinition complexTypeDefinition = (XSDComplexTypeDefinition) xsdComponent;
	        XSDParticle complexType = complexTypeDefinition.getComplexType();
	        handleParticle(complexType , node , doc);
	      
	    }else if( xsdComponent instanceof XSDElementDeclaration ){
	    	XSDElementDeclaration elementDeclaration = (XSDElementDeclaration) xsdComponent ; 
	    	XSDTypeDefinition definition = elementDeclaration.getTypeDefinition();
	    	createElementFromXsd(definition , node ,doc);
	    }
	    return parent;
	}*/
	
	private static void handleParticle(XSDParticle particle , Element parent , Document doc) throws ParserConfigurationException {
	    
		int loop = determineMinMaxForSample( particle , parent , doc );
		while( loop-- > 0 ){
			XSDParticleContent particleContent = particle.getContent();
			if( particleContent instanceof XSDModelGroup ){
				XSDModelGroup group = (XSDModelGroup) particleContent;
				XSDCompositor compositor = group.getCompositor();
				if( compositor == XSDCompositor.SEQUENCE_LITERAL ){
					processSequence( group , parent , doc );
				}else if( compositor == XSDCompositor.CHOICE_LITERAL ){
					processChoice( group, parent , doc );
				}else if( compositor == XSDCompositor.ALL_LITERAL ){
					processAll( group , parent , doc );
				}
			}else if( particleContent instanceof XSDElementDeclaration ){
				processElement( (XSDElementDeclaration)particleContent , parent , doc );
			}
		}

	}
	
	private static void processElement(XSDElementDeclaration particleContent,
			Element parent, Document doc) {
		Element element = doc.createElement(particleContent.getName());
		parent.appendChild(element);
		createSampleForType(particleContent , doc , element);
	}

	private static void processAll(XSDModelGroup group, Element parent,
			Document doc) throws ParserConfigurationException {
		EList<XSDParticle> children = group.getContents();
		String cStr = String.format(Messages.getString("SOAPMessageUtils.itemComment"), new Object[] {children.size()});//$NON-NLS-1$
		Comment comment = doc.createComment(cStr);
		parent.appendChild( comment );
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			XSDParticle xsdParticle = (XSDParticle) iterator.next();
			handleParticle( xsdParticle , parent, doc );
		}
	}

	private static void processChoice(XSDModelGroup group, Element parent,
			Document doc) throws ParserConfigurationException {
		EList<XSDParticle> children = group.getContents();
		String cStr = String.format(Messages.getString("SOAPMessageUtils.choiceComment"), new Object[] {children.size()});//$NON-NLS-1$
		Comment comment = doc.createComment(cStr);
		parent.appendChild( comment );
		
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			XSDParticle xsdParticle = (XSDParticle) iterator.next();
			handleParticle( xsdParticle , parent, doc );
		}
	}

	private static void processSequence(XSDModelGroup group, Element parent,
			Document doc) throws ParserConfigurationException {
		EList<XSDParticle> children = group.getParticles();
		
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			XSDParticle xsdParticle = (XSDParticle) iterator.next();
			handleParticle( xsdParticle , parent, doc );
		}
		
	}

	private static int determineMinMaxForSample(XSDParticle particle,
			Element parent, Document doc) {
		
		int result ;
		
		int max = particle.getMaxOccurs();
		int min = particle.getMinOccurs();
		
		if( min == max ){
			return min;
		}
		result = min;
		if( result == 0 ){
			result = 1;
		}
	
		return result;
	}

	/*private static List createDocumentElement(Part part,
			ISOAPMessage message, XSDNamedComponent component ) {
	    List result = new ArrayList();
	    XSDNamedComponent typeDefinition = component;
	    
	    if(typeDefinition instanceof XSDSimpleTypeDefinition){
	        //Element node = createRCPSimpleDataNode( part.getName() , message.getEnvelope(false).getOwnerDocument());
	        Node node = createDocSimpleDataNode(part.getName() , message.getEnvelope(false).getOwnerDocument());
            result.add(node);
        }else if(typeDefinition instanceof XSDComplexTypeDefinition){
        	try {
        		//String parameterValue = (String) parameters.get(part.getName());
        		Element bodyElement = null;
	       
        		bodyElement = createElementFromXsd(typeDefinition , null, null);
	        
        		if(bodyElement != null) {
        			NodeList children = bodyElement.getChildNodes();
        			for(int i = 0 ; i < children.getLength() ; i ++) {
        				if(children.item(i) instanceof Element) {
        					result.add(children.item(i));
        				}
        			}
        		}
	       
        	} catch (ParserConfigurationException e) {
        		e.printStackTrace();
        	}
       }else if( typeDefinition instanceof XSDElementDeclaration){
    	  try{ 
    	   Element bodyElement = createElementFromXsd(typeDefinition , null , null );
    	   if(bodyElement != null) {
   			NodeList children = bodyElement.getChildNodes();
   			for(int i = 0 ; i < children.getLength() ; i ++) {
   				if(children.item(i) instanceof Element) {
   					result.add(children.item(i));
   				}
   			}
   		}
    	  }catch ( ParserConfigurationException e ){
    		  
    	  }
       }
	   
	    return result;
	}
*/
	
/*	private static Object createRCPElement(String partName, ISOAPMessage message,
			XSDNamedComponent component) {
		
		if(component instanceof XSDSimpleTypeDefinition){
			return createRCPSimpleDataNode(partName,message.getEnvelope(false).getOwnerDocument());
		}else if(component instanceof XSDComplexTypeDefinition){
			
		}
		
		return null;
	}*/


/*	private static Node createDocSimpleDataNode(String partName,Document doc){
		Node instanceDocument = doc.createTextNode("?");
		return instanceDocument;
	}*/
	
/*	private static Element createRCPSimpleDataNode(String partName,Document doc) {
		
		  Element instanceDocument = doc.createElement(partName);
		  String value = "?";
	      Node textNode = doc.createTextNode(value);
	      instanceDocument.appendChild(textNode);
	      return instanceDocument;
	}*/


/*	private static Element createRCPComplexDataNode(Map parameters,
			XSDComplexTypeDefinition xsdType, boolean isUseLiteral) {
		return null;
	}*/
	
	
	public static Element createSampleForType( XSDNamedComponent xsdType , Document doc , Element parent ){
		
		if( xsdType instanceof XSDSimpleTypeDefinition){
			processSimpleType( (XSDSimpleTypeDefinition)xsdType , doc , parent );
			return parent;
		}
		
		processAttributes( xsdType , doc , parent );
		
		if( xsdType instanceof XSDComplexTypeDefinition){
			XSDComplexTypeDefinition complexType = (XSDComplexTypeDefinition) xsdType;
			XSDComplexTypeContent contentType = complexType.getContentType();
			if( contentType instanceof XSDParticle){
				try {
					handleParticle( (XSDParticle)contentType , parent, doc  );
				} catch (ParserConfigurationException e) {
					//TODO will remove this before leave work today.
					e.printStackTrace();
				}
			}else if( contentType instanceof XSDSimpleTypeDefinition ){
				processSimpleType( (XSDSimpleTypeDefinition)contentType , doc ,  parent  );
			}
		}else if( xsdType instanceof XSDElementDeclaration){
			XSDTypeDefinition xsdDefinition = ((XSDElementDeclaration)xsdType).getType(); 
			createSampleForType( xsdDefinition , doc , parent );
		}
		
		return parent;
	}

	private static void processAttributes(XSDNamedComponent xsdType,
			Document doc, Element parent) {
		
	}

	private static void processSimpleType(XSDSimpleTypeDefinition xsdType,
			Document doc, Element parent) {
		  String value = "?";
	      Node textNode = doc.createTextNode(value);
	      parent.appendChild(textNode);
	}
	
}
