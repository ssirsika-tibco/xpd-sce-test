/*
 * Copyright (c) TIBCO Software Inc 2014. All rights reserved.
 */

package com.tibco.amxbpm.documentactivity.test;

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
public abstract class AbstractBaseTest extends TestCase {

    // This is required for EPackage Registry initialisation of XPDL
    // Although it does not appear to be used you can easily prove that it is
    // required by removing it and trying to run the Unit Tests
    static {
        @SuppressWarnings("unused")
        Xpdl2Package einstance = Xpdl2Package.eINSTANCE;
        @SuppressWarnings("unused")
        XpdExtensionPackage einstance2 = XpdExtensionPackage.eINSTANCE;
    }

    protected static final String RESOURCE_ROOT =
            "/com/tibco/amxbpm/documentactivity/test/resources/";

    /**
     * Loads an XPDL file and returns the package element
     * 
     * @param path
     *            Relative path to the XPDL file
     * @return Package object
     */
    protected Package loadXPDLPackage(String path) {

        String url =
                getClass().getResource(RESOURCE_ROOT + path).toExternalForm();

        Resource res =
                new Xpdl2ResourceFactoryImpl().createResource(URI
                        .createURI(url));

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
     * @param fileName
     *            Name of the file to read in
     * @return The contents of the file
     */
    protected static String getXML(String fileName) {
        StringBuffer b = new StringBuffer();
        InputStream i = null;
        try {
            i =
                    AbstractBaseTest.class.getClassLoader()
                            .getResourceAsStream(RESOURCE_ROOT + fileName);
            for (int d = -1; (d = i.read()) > 0;) {
                b.append((char) d);
            }
        } catch (IOException e) {
            System.out.println("Error while writing to file!" + e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                if (null != i)
                    i.close();
            } catch (Exception ex) {
                System.out.println("Error while closing input stream [" + i
                        + "]" + ex);
            }
        }

        return b.toString();
    }

    /**
     * Finds the User Activities
     * 
     * @param activities
     *            List of activities
     * @return The first user activity found
     */
    protected Activity getUserActivity(List<Activity> activities) {

        Activity activity = null;

        for (Activity anActivity : activities) {
            @SuppressWarnings("deprecation")
            TaskType taskType = TaskObjectUtil.getTaskType(anActivity);
            // Only interested in the service tasks
            if (taskType.equals(TaskType.SERVICE_LITERAL)) {
                activity = anActivity;
            }
            if (activity != null) {
                break;
            }
        }

        return activity;
    }
}
