/*
*
*      ENVIRONMENT:    Java Generic
*
*      DESCRIPTION:    TODO
*      
*      COPYRIGHT:      (C) 2007 Tibco Software Inc
*
*/
package com.tibco.xpd.bom.gen.generator;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.mapping.ecore2xml.Ecore2XMLPackage;
import org.eclipse.uml2.uml.UMLPackage;
import org.openarchitectureware.emf.EcoreUtil2;
import org.openarchitectureware.emf.Mapping;
import org.openarchitectureware.emf.Setup;

public class BOMTypesSetup extends Setup
{
	
	public BOMTypesSetup()
    {
        addEPackageClass(UMLPackage.eINSTANCE.getClass().getName());
    }

    public void setStandardUML2Setup(boolean b)
    {
        if(b)
        {
//            addExtensionMap(new Mapping("ecore", "org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl"));
//            addExtensionMap(new Mapping("uml", "org.eclipse.uml2.uml.resource.UMLResource"));
//            addExtensionMap(new Mapping("uml2", "org.eclipse.uml2.uml.resource.UML22UMLResource"));
//            String p = "metamodels/UML.metamodel.uml";
//            URI uri = EcoreUtil2.getURI(p);
//            
//            String bomUML = "model/BomPrimitiveTypes.library.uml";
//            URI uri2 = EcoreUtil2.getURI(bomUML);
//           
//            if(uri != null && !uri.toString().equals(p))
//            {
//                String path = uri.toString();
//                int mmIndex = path.lastIndexOf("/metamodels");
//                if(mmIndex < 0)
//                    throw new IllegalStateException("XXXMissing required plugin 'org.eclipse.uml2.uml.resources' in classpath.");
//                path = path.substring(0, mmIndex);
//                path = addJarProtocolIfNecessary(path);
//                
//                String bomPath = uri2.toString();
//                int bomIndex = bomPath.lastIndexOf("/model");
//                if(bomIndex < 0)
//                    throw new IllegalStateException("Missing required plugin 'com.tibco.xpd.bom.types' in classpath.");
//                bomPath = bomPath.substring(0, bomIndex);
//                bomPath = addJarProtocolIfNecessary(bomPath);
//                
//                addUriMap(new Mapping("pathmap://UML_PROFILES/", (new StringBuilder(String.valueOf(path))).append("/profiles/").toString()));
//                addUriMap(new Mapping("pathmap://UML_METAMODELS/", (new StringBuilder(String.valueOf(path))).append("/metamodels/").toString()));
//                addUriMap(new Mapping("pathmap://UML_LIBRARIES/", (new StringBuilder(String.valueOf(path))).append("/libraries/").toString()));
//                addUriMap(new Mapping("pathmap://BOM_TYPES/",(new StringBuilder(String.valueOf(bomPath))).append("/model/").toString()));
//            } 
//            else
//            {
//            	// TS Attempt to diagnose why cannot find the metadata models 
//            	// Also, why is this needed?    
//            	// This is how the EcoreUtil2 tries to do it - fails 
//            	URL url = Thread.currentThread().getContextClassLoader().getResource(p);
//            	// This is a different classloader approach - also fails 
//            	URL url2 = getClass().getClassLoader().getResource(p); 
//            	
//            	
////            	Resource.Factory.Registry.
//            	
//                throw new IllegalStateException("YYYMissing required plugin 'org.eclipse.uml2.uml.resources' in classpath.");
//            }
            org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.put("http://www.eclipse.org/uml2/2.0.0/UML", org.eclipse.emf.ecore.EPackage.Registry.INSTANCE.get(UMLPackage.eINSTANCE.getNsURI()));
            Ecore2XMLPackage.eINSTANCE.getEClassifiers();
            URI uri = EcoreUtil2.getURI("model/UML2_2_UML.ecore2xml");
            if(uri != null)
            {
                String path = addJarProtocolIfNecessary(uri.toString());
                addUriMap(new Mapping("platform:/plugin/org.eclipse.uml2.uml/model/UML2_2_UML.ecore2xml", path));
            }
        }
    }

    private String addJarProtocolIfNecessary(String path)
    {
        if(path.indexOf(".jar!") != -1 && !path.startsWith("jar:"))
            path = (new StringBuilder("jar:")).append(path).toString();
        return path;
    }

    private static final String UML2_200_NS_URI = "http://www.eclipse.org/uml2/2.0.0/UML";

}
