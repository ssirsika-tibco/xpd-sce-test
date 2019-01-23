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

import com.tibco.n2.ut.configuration.builder.UserActivityConfigurationModelBuilder;
import com.tibco.n2.ut.model.util.UsertaskResourceFactoryImpl;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;



/**
 * JUnit test of the User Task Design time
 *
 */
public class UserActivityTest extends CommonTest {
  
    public void testUserTask()
    {
        Package pck = loadXPDLPackage("/UserTaskTest1.xpdl"); //$NON-NLS-1$
        
        Activity activity = getUserActivity( pck.getProcesses().get(0).getActivities());

        UserActivityConfigurationModelBuilder modBuild = new UserActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);
               
        // System.out.println(getUserTaskModelXML(bpel));
       
        assertEquals( "Generated User Task not as expected", getXML("UserTaskResults1.xml"), getUserTaskModelXML(bpel) ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void testUserTaskAllocateToOffsetSetMember()
    {
        Package pck = loadXPDLPackage("/UserTaskTest2.xpdl"); //$NON-NLS-1$
        
        Activity activity = getUserActivity( pck.getProcesses().get(0).getActivities());

        UserActivityConfigurationModelBuilder modBuild = new UserActivityConfigurationModelBuilder();
        EObject bpel = modBuild.transformConfigModel(activity, null);
               
        // System.out.println(getUserTaskModelXML(bpel));
       
        assertEquals( "Generated User Task not as expected", getXML("UserTaskResults2.xml"), getUserTaskModelXML(bpel) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
    

    /**
     * Creates the UT Model XML from the EMF Object
     * 
     * @param emfModel      EMF object
     * @return              Sting of XML
     */
    private String getUserTaskModelXML( EObject emfModel )
    {
        Resource resource = new UsertaskResourceFactoryImpl().createResource(URI.createFileURI("dummy.xml")); //$NON-NLS-1$
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
