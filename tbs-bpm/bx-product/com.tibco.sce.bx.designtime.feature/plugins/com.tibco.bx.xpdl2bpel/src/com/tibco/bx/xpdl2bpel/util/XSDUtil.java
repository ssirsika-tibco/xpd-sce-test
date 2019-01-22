/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tibco.bx.xpdl2bpel.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.wst.wsdl.internal.impl.ImportImpl;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDConstants;



/**
 * Utility class to resolve XSD type or element declarations from a WSDL definition. This is adapted from 
 * the <code>org.eclipse.bpel.model.util.WSDLUtil</code> class.
 */

public class XSDUtil
{	 
    
    /** 
     * Use Java generics to write the "finders" of various elements, so that the general code
     * which does the lookups and follows imports does not need to be repeated.
     * 
     * @author Michal Chmielewski (michal.chmielewski@oracle.com)
     * @date Feb 27, 2007
     *
     * @param <T> the type that we are looking up (PortType, Message, PartnerLinkType)
     */
    
    interface Finder<S,T> {
    	/**
    	 * Find the thing in the container
    	 * 
    	 * @param source the source where to look (definition, schema)
    	 * @param qname the QName to lookup
    	 * @return the resolved entity, or null
    	 */
    	T find (S source, QName qname);
    }
     
    /** Type definition finder */    
    static private final Finder<XSDSchema,XSDTypeDefinition> TYPE_DEFINITION_FINDER = new Finder<XSDSchema,XSDTypeDefinition>() {

		public XSDTypeDefinition find(XSDSchema schema, QName typeName) {
	    	// Perhaps this is what we want ...
	    	//    http://www.eclipse.org/modeling/emf/faq/#dev20040602-1383194195
	    	// 
	    	XSDTypeDefinition typeDef = schema.resolveTypeDefinition(typeName.getNamespaceURI(), typeName.getLocalPart());    	    	
	    	if (typeDef.getContainer() == null) {
	    		return null;
	    	}
	    	return typeDef;
		}
    };
    
    /** Element declaration finder */
    static private final Finder<XSDSchema,XSDElementDeclaration> ELEMENT_DECLARATION_FINDER = new Finder<XSDSchema,XSDElementDeclaration> () {

		public XSDElementDeclaration find(XSDSchema schema, QName qn) {
	    	XSDElementDeclaration decl = schema.resolveElementDeclaration( qn.getNamespaceURI(), qn.getLocalPart());
	    	
	    	// TODO: (Hack) 
	    	// Why has this started returning instances of XSDElementDeclrarion that are not proxies or null
	    	// if the element declaration is missing in the schema ?
	    	// Perhaps this is what we want: http://www.eclipse.org/modeling/emf/faq/#dev20040602-1383194195
	    	
	    	if (decl.getContainer() == null) {
	    		return null;
	    	}
	    	return decl;
		}
    };
    
    /** 
     * Resolve using finder resolves the QName in the definitions passed. The return object
     * extends from EObject (so PortType, Message, PartnerLinkType, etc). The finder has the actual lookup
     * code while the general code flow that deals with following the imports is shared among the lookup
     * methods in this class.
     * 
     * @param <T> the type 
     * @param defn the definition to use as the lookup
     * @param qname the QName to lookup
     * @param finder the finder to use.
     * @param seen the seen set
     * @return the object to be resolved.
     */
 
    /**
     * Common code for resolving XSDTypeDefinitions and XSDElementDeclarations
     */
    @SuppressWarnings("restriction")
	static <T> T resolveXSD (Definition definition, QName qname, Finder<XSDSchema,T> finder, Set<Definition> seen )
    {
        T result = null;
        
        if (seen.contains(definition)) {
        	return result;
        }
        seen.add(definition);
        
        // Check for built-in types
        // TODO Slightly inefficient to evaluate this when recursing

        XSDSchema schema = null;
        String namespace = qname.getNamespaceURI();
        
        if ("".equals(namespace)) { //$NON-NLS-1$
            namespace = null;
        }
        
        if (XSDConstants.isSchemaForSchemaNamespace(namespace)) {
            schema = XSDSchemaImpl.getSchemaForSchema(namespace);
        } else if (XSDConstants.isSchemaInstanceNamespace(namespace)) {
            schema = XSDSchemaImpl.getSchemaInstance(namespace);
        }
        if (schema != null) {
            result = finder.find(schema, qname);
            if (result != null) {
                return result;
            }
        }

        // Check in-line schema
        Types types = definition.getETypes();
        if (types != null) {
        	Iterator<?> it = types.getExtensibilityElements().iterator();
        	while (it.hasNext()) {
                Object e = it.next();
                if (e instanceof XSDSchemaExtensibilityElement == false) {
                	continue;
                }
                XSDSchemaExtensibilityElement schemaEE = (XSDSchemaExtensibilityElement) e;
                schema = schemaEE.getSchema();
                if (schema != null) {
                    result = finder.find(schema, qname);
                    if (result != null)
                        return result;
                }
            }
        }

        /**
          * Check imported schemas and definitions.
          * If we are here, then result is still null
          */
        
		Collection<List<Import>> imports = definition.getImports().values();
		for (List<Import> importList : imports) {
			for (Import wsdlImport : importList) {
	            ImportImpl imp = (ImportImpl) wsdlImport;
	            imp.importDefinitionOrSchema();
	            schema = imp.getESchema();
	            if (schema != null) {
	                result = finder.find(schema, qname);
	                if (result != null)
	                    return result;
	            }
	            Definition importedDefinition = imp.getEDefinition();
	            if (importedDefinition != null) {
	                result = resolveXSD(importedDefinition, qname, finder, seen );
	                if (result != null) {
	                    return result;
	                }                
	            }
			}
		}
        
        return result;        
    }
    
    
    /**
     * Resolve type definition. Basically lookup type by QName.
     * 
     * @param definition
     * @param qname
     * @return the XSDTypeDefinition found or null if it does not exist.
     */
    
    public static XSDTypeDefinition resolveXSDTypeDefinition(Definition definition, QName qname)
    {      
        return resolveXSD(definition, qname ,TYPE_DEFINITION_FINDER, new HashSet<Definition>() );
    }
    
    /**
     * Resolve the XSDElement declaration in this definition. Basically, lookup element declaration using the
     * QName specified in the definition indicated. 
     * 
     * @param definition
     * @param qname
     * @return the XSDElement declaration if found. null otherwise. 
     */
    
    public static XSDElementDeclaration resolveXSDElementDeclaration(Definition definition, QName qname)
    {        
        return resolveXSD(definition, qname, ELEMENT_DECLARATION_FINDER, new HashSet<Definition>() );
    }

}
