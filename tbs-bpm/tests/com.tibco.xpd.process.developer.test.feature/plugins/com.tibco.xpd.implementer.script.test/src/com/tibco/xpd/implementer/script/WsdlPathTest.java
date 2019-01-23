/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.implementer.resources.xpdl2.properties.ActivityMessageMappingCommandFactory;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyFactory;

/**
 * Tests for IWsdlPath classes.
 * 
 * @author nwilson
 */
public class WsdlPathTest extends TestCase {

    private IProject project;

    private Package pckg;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        project = TestUtil.createProjectFromWizard("NestedArrayTest"); //$NON-NLS-1$
        /*
         * XPD-5179: Saket: Since now we are using
         * createProjectFromWizard(String) instead of createProject(String)
         * which is deprecated, we don't have to explicitly create special
         * folders as createProjectFromWizard(String) does that by itself.
         */

        // SpecialFolder processSpecialFolder =
        // TestUtil.createSpecialFolder(project,
        // "Process Packages",
        // Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND);
        // SpecialFolder wsdlSpecialFolder =
        // TestUtil.createSpecialFolder(project,
        // "Service Descriptors",
        // "wsdl");
        IFolder processFolder = project.getFolder("Process Packages"); //$NON-NLS-1$
        IFolder wsdlFolder = project.getFolder("Service Descriptors"); //$NON-NLS-1$
        Bundle bundle =
                Platform.getBundle("com.tibco.xpd.implementer.script.test"); //$NON-NLS-1$
        IPath processFile = new Path("resources/XPathString.xpdl"); //$NON-NLS-1$
        IPath wsdlFile = new Path("resources/Cargo_FlightPlan.wsdl"); //$NON-NLS-1$
        IFile processFileTarget = processFolder.getFile("XPathString.xpdl"); //$NON-NLS-1$
        IFile wsdlFileTarget = wsdlFolder.getFile("Cargo_FlightPlan.wsdl"); //$NON-NLS-1$
        try {
            InputStream input =
                    FileLocator.openStream(bundle, processFile, false);
            processFileTarget.create(input, true, null);
            input = FileLocator.openStream(bundle, wsdlFile, false);
            wsdlFileTarget.create(input, true, null);
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (CoreException e) {
            fail(e.getMessage());
        }

        /*
         * XPD-5179: Saket: Check if the project requires migration. If yes,
         * then migrate the project, build it and then continue with the test.
         */
        try {
            if (project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                    false,
                    IResource.DEPTH_ZERO) != null) {

                ProjectAssetMigrationManager.getInstance().migrate(project,
                        true,
                        new NullProgressMonitor());
            }

        } catch (CoreException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        TestUtil.buildAndWait();

        WorkingCopy wc =
                new Xpdl2WorkingCopyFactory().getWorkingCopy(processFileTarget);
        assertNotNull(wc);
        pckg = (Package) wc.getRootElement();
        assertNotNull(pckg);
        try {
            project.build(IncrementalProjectBuilder.FULL_BUILD, null);
        } catch (CoreException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testWsdlPathCreation() {
        String expected =
                "wso:processRequest/part:parameters/group:sequence[3]/tns:WorkDocuments[0]/group:choice[1]{0}/tns:Aircraft[0]/group:sequence[2]/tns:flightPlan[4]/tns:flightPlan{0}/@id[0]"; //$NON-NLS-1$
        Activity activity = getActivity();
        ActivityMessageMappingCommandFactory factory =
                new ActivityMessageMappingCommandFactory(MappingDirection.OUT);
        ActivityMessageProvider messageAdapter =
                ActivityMessageProviderFactory.INSTANCE
                        .getMessageProvider(activity);
        assertNotNull("Message adapter not found.", messageAdapter); //$NON-NLS-1$
        WebServiceOperation wso =
                messageAdapter.getWebServiceOperation(activity);
        assertNotNull("WebServiceOperation not found.", wso); //$NON-NLS-1$
        IWsdlPath path = WsdlPathFactory.createWsdlPath(wso, expected, false);
        assertNotNull("Path not resolved.", path); //$NON-NLS-1$
        EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);
        ConceptPath target =
                ConceptUtil.resolveConceptPath(activity, "strField2"); //$NON-NLS-1$
        Command cmd = factory.getAddMappingCommand(ed, activity, path, target);
        if (cmd.canExecute()) {
            ed.getCommandStack().execute(cmd);
        } else {
            fail("Invalid add command."); //$NON-NLS-1$
        }
        boolean found = false;
        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskService service = task.getTaskService();
            if (service != null) {
                Set<IWsdlPath> paths =
                        WsdlUtil.getAllWsdlPaths(activity,
                                DirectionType.OUT_LITERAL);
                for (IWsdlPath next : paths) {
                    String xpath = next.getPath();
                    if (expected.equals(xpath)) {
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) {
            fail("Path not found."); //$NON-NLS-1$
        }
    };

    /**
     * Tests that generated XPath expressions contain the correct array indices
     * where there are nested arrays with items mapped to the same schema
     * location but with smaller indices.
     */
    @Test
    public void testXPathNestedArrayIndices() {
        String expected1 =
                "CorticonResponse/tns:WorkDocuments/tns:Aircraft[1]/@id"; //$NON-NLS-1$
        String expected2 =
                "CorticonResponse/tns:WorkDocuments/tns:Aircraft[2]/@id"; //$NON-NLS-1$
        Collection<String> expected = new ArrayList<String>();
        expected.add(expected1);
        expected.add(expected2);
        Activity activity = getActivity();
        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskService service = task.getTaskService();
            if (service != null) {
                Set<IWsdlPath> paths =
                        WsdlUtil.getAllWsdlPaths(activity,
                                DirectionType.OUT_LITERAL);
                assertEquals("Wrong path count", 2, paths.size()); //$NON-NLS-1$
                for (IWsdlPath path : paths) {
                    String xpath = path.getXPath(paths);
                    if (expected.contains(xpath)) {
                        expected.remove(xpath);
                    } else {
                        fail("Unexpected path: " + xpath); //$NON-NLS-1$
                    }
                }
            }
        }
        for (String remaining : expected) {
            fail("Path not found: " + remaining); //$NON-NLS-1$
        }
    }

    /**
     * @return
     */
    private Activity getActivity() {
        Activity activity = null;
        List<?> processes = pckg.getProcesses();
        if (processes.size() == 1) {
            Process process = (Process) processes.get(0);
            for (Object next : process.getActivities()) {
                if (next instanceof Activity) {
                    activity = (Activity) next;
                }
            }
        }
        assertNotNull("Activity not found.", activity); //$NON-NLS-1$
        return activity;
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();

        TestUtil.removeProject("NestedArrayTest"); //$NON-NLS-1$
        TestUtil.deleteAllWorkpsaceProjects();
    }
}
