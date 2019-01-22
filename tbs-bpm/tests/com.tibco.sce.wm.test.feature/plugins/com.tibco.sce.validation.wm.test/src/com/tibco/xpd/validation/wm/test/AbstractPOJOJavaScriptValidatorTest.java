/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */
package com.tibco.xpd.validation.wm.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.pde.internal.ui.wizards.tools.ConvertProjectToPluginOperation;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectImporter;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * @author mtorres
 * @since 3.4
 */
public abstract class AbstractPOJOJavaScriptValidatorTest
        extends AbstractJavaScriptValidatorTest {

    private static final String PDE_NATURE = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$

    public AbstractPOJOJavaScriptValidatorTest(boolean isCheckProblemExists) {
        super(isCheckProblemExists);
    }

    public AbstractPOJOJavaScriptValidatorTest() {
        super(true);
    }

    @Override
    @SuppressWarnings("restriction")
    protected void setUp() throws Exception {
        super.setUp();
        // Extract the POJO project
        clean();

        ProjectImporter projectImporter =
                ProjectImporter.createPluginProjectImporter(
                        WMValidationTestActivator.PLUGIN_ID,
                        Arrays.asList(getPojoProjectPath()));
        assertTrue(projectImporter.performImport());

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

        IProject createdPOJOProject = root.getProject(getPojoProjectName());
        assertTrue("Project was not created!", createdPOJOProject.exists()); //$NON-NLS-1$
        // Convert project to Plugin project
        if (createdPOJOProject != null && createdPOJOProject.exists()
                && !createdPOJOProject.hasNature(PDE_NATURE)) {
            /*
             * Forced to used internal code as there is no public API to do this
             * at the moment
             */
            ConvertProjectToPluginOperation conversionOp =
                    new ConvertProjectToPluginOperation(
                            new IProject[] { createdPOJOProject }, false);
            conversionOp.run(new NullProgressMonitor());
        }

        IProject testProject = getTestProject("N2JavaScriptPOJOValidation");//$NON-NLS-1$
        if (testProject != null && testProject.exists()
                && createdPOJOProject != null) {
            IProjectDescription description = testProject.getDescription();
            if (description != null) {
                IProject[] referencedProjects =
                        description.getReferencedProjects();
                for (IProject ref : referencedProjects) {
                    if (ref.equals(createdPOJOProject)) {
                        return;
                    }
                }
                IProject[] newList =
                        new IProject[referencedProjects.length + 1];
                System.arraycopy(referencedProjects,
                        0,
                        newList,
                        0,
                        referencedProjects.length);
                newList[referencedProjects.length] = createdPOJOProject;
                description.setReferencedProjects(newList);
                testProject.setDescription(description,
                        new NullProgressMonitor());
                testProject.touch(new NullProgressMonitor());
                buildAndWait();
            }
        }
    }

    public abstract String getPojoProjectName();

    public abstract String getPojoProjectPath();

    @Override
    protected Set<String> getAffectedIds(IResource resource) {
        if (resource != null) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
            if (wc.getRootElement() instanceof Package) {
                Package model = (Package) wc.getRootElement();
                assertNotNull(
                        "Root element of the model is null even after migration", //$NON-NLS-1$
                        model);
                if (model != null) {
                    List<Process> processes = model.getProcesses();
                    if (processes != null) {
                        Set<String> affectedIds = new HashSet<String>();
                        for (Process process : processes) {
                            List<Activity> taskScriptList = ProcessScriptUtil
                                    .getJavaServiceTasks(process);
                            List<DataMapping> dataMappingList =
                                    new ArrayList<DataMapping>();
                            List<ScriptInformation> allScriptInformation =
                                    new ArrayList<ScriptInformation>();
                            if (taskScriptList != null) {
                                for (Iterator<Activity> iterator =
                                        taskScriptList.iterator(); iterator
                                                .hasNext();) {
                                    Activity activity = iterator.next();
                                    if (activity != null) {
                                        List<DataMapping> tempDataMappingList =
                                                ProcessScriptUtil
                                                        .getScriptDataMappingForServiceWithScriptType(
                                                                activity,
                                                                DirectionType.OUT_LITERAL,
                                                                "JavaScript");//$NON-NLS-1$
                                        if (tempDataMappingList != null) {
                                            dataMappingList.addAll(
                                                    tempDataMappingList);
                                        }
                                        List<ScriptInformation> scriptInformationTasksWithScriptType =
                                                ProcessScriptUtil
                                                        .getScriptInformationTasksWithScriptType(
                                                                activity,
                                                                "JavaScript", //$NON-NLS-1$
                                                                DirectionType.OUT_LITERAL
                                                                        .getLiteral());
                                        if (scriptInformationTasksWithScriptType != null) {
                                            allScriptInformation.addAll(
                                                    scriptInformationTasksWithScriptType);
                                        }

                                    }
                                }
                            }
                            for (DataMapping dataMapping : dataMappingList) {
                                if (dataMapping != null) {
                                    ScriptInformation scriptInformationFromDataMapping =
                                            ProcessScriptUtil
                                                    .getScriptInformationFromDataMapping(
                                                            dataMapping);
                                    if (scriptInformationFromDataMapping != null) {
                                        allScriptInformation.add(
                                                scriptInformationFromDataMapping);
                                    }
                                }
                            }
                            for (ScriptInformation scriptInformation : allScriptInformation) {
                                if (scriptInformation != null) {
                                    affectedIds.add(scriptInformation.getId());
                                }
                            }
                        }
                        return affectedIds;
                    }
                }
            }
        }
        return Collections.emptySet();
    }

    private void clean() {
        IProject proj = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(getPojoProjectName());
        if (proj.exists()) {
            TestUtil.removeProject(proj.getName());
        }
    }

}
