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
import com.tibco.n2.ut.configuration.builder.PageActivityConfigurationModelBuilder;
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
        Package pck = loadXPDLPackage("/PageActivityTest1.xpdl"); //$NON-NLS-1$
        
        Activity activity = getUserActivity( pck.getProcesses().get(0).getActivities());

        PageActivityConfigurationModelBuilder modBuild = new PageActivityConfigurationModelBuilder();
        EObject bpel = modBuild.convertToPageActivityDataModel(activity);
               
        //System.out.println(getPageActivityModelXML(bpel));
       
        assertEquals( "Generated Page Activity not as expected", getXML("PageActivityResults1.xml"), getPageActivityModelXML(bpel) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /**
     * Creates the PA Model XML from the EMF Object
     * 
     * @param emfModel      EMF object
     * @return              Sting of XML
     */
    private String getPageActivityModelXML( EObject emfModel )
    {
        Resource resource = new PageActivityResourceFactoryImpl().createResource(URI.createFileURI("dummy.xml")); //$NON-NLS-1$
        resource.getContents().add(emfModel);
        
        String returnXML = ""; //$NON-NLS-1$
        
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
