/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ResourceFactoryImpl;

/**
 * Utility class to support JUnit tests
 *
 */
public class CommonTest extends TestCase {

    // This is required for EPackage Registry initialisation of XPDL
    // Although it does not appear to be used you can easily prove that it is
    // required by removing it and trying to run the Unit Tests
    static
    {
        @SuppressWarnings("unused")
        Xpdl2Package einstance = Xpdl2Package.eINSTANCE;
        @SuppressWarnings("unused")
        XpdExtensionPackage einstance2 = XpdExtensionPackage.eINSTANCE;
    }

    /**
     * Loads an XPDL file and returns the package element
     * 
     * @param path  Relative path to the XPDL file
     * @return      Package object
     */
    protected Package loadXPDLPackage(String path) {
        
        String url = getClass().getResource(path).toExternalForm();
        
        Resource res = new Xpdl2ResourceFactoryImpl().createResource(URI.createURI(url));
        
        try {
            res.load(null);
        } catch (IOException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        return ((DocumentRoot) res.getContents().get(0)).getPackage();
    }

    /**
     * Reads in a text file into a String
     *
     * @param fileName      Name of the file to read in
     * @return              The contents of the file
     */
    protected static String getXML(String fileName)
    {
        StringBuffer b = new StringBuffer();
        InputStream i = null;
        try
        {
            i = CommonTest.class.getClassLoader().getResourceAsStream(fileName);
            for (int d = -1; (d = i.read()) > 0;)
            {
                b.append((char) d);
            }
        }
        catch (IOException e)
        {
            System.out.println("Error while writing to file!" +  e);
            throw new RuntimeException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if (null != i) i.close();
            }
            catch (Exception ex)
            {
                System.out.println("Error while closing input stream [" + i + "]" + ex);
            }
        }

        return b.toString();
    }

    /**
     * Finds the User Activities
     * 
     * @param activities    List of activities
     * @return              The first user activity found
     */
    protected Activity getUserActivity( List<Activity> activities ) {
        
        Activity activity = null;
        
        for (Activity anActivity : activities) {
            TaskType taskType = TaskObjectUtil.getTaskType(anActivity);
            if( taskType.equals(TaskType.USER_LITERAL) ) {
                activity = anActivity;
            } else if( taskType.equals(TaskType.EMBEDDED_SUBPROCESS_LITERAL) ) {
                String activityID = anActivity.getBlockActivity().getActivitySetId();
                activity = getUserActivity(
                        anActivity.getProcess().getActivitySet(activityID).getActivities());
            }
            
            if( activity != null ) {
                break;
            }
        }
        
        return activity;
    }

}
