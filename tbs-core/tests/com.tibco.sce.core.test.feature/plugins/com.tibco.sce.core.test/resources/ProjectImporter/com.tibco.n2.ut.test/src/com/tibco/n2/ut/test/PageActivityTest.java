/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.n2.pageactivity.model.util.PageActivityResourceFactoryImpl;
import com.tibco.n2.ut.configuration.builder.UserActivityConfigurationModelBuilder;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;



/**
 * JUnit test of the Page Activity Design time
 *
 */
public class PageActivityTest extends CommonTest {
  
    public void testPageActivity()
    {
        Package pck = loadXPDLPackage("/PageActivityTest1.xpdl");
        
        Activity activity = getUserActivity( pck.getProcesses().get(0).getActivities());

        UserActivityConfigurationModelBuilder modBuild = new UserActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);
               
        //System.out.println(getPageActivityModelXML(bpel));
       
        assertEquals( "Generated Page Activity not as expected", getPageActivityModelXML(bpel), getXML("PageActivityResults1.xml") );
    }
    
    /**
     * Creates the PA Model XML from the EMF Object
     * 
     * @param emfModel      EMF object
     * @return              Sting of XML
     */
    private String getPageActivityModelXML( EObject emfModel )
    {
        Resource resource = new PageActivityResourceFactoryImpl().createResource(URI.createFileURI("dummy.xml"));
        resource.getContents().add(emfModel);
        
        String returnXML = "";
        
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            resource.save(outStream, Collections.EMPTY_MAP);
            returnXML = outStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnXML;
    }

    
    
}
