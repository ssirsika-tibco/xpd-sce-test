/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.test.core.validation;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.core.test.validations.AbstractBaseValidationTest;
import com.tibco.xpd.n2.test.core.N2TestCreationUtil;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * 
 * <p>
 * <b>NOTE!</b> <br/>
 * If the validated resources are spread across many projects there is probably
 * need to add project references between created projects. This can be done
 * like this:
 * 
 * <pre>
 * <code>
 *  protected void setUpBeforeBuild() {
 *         super.setUpBeforeBuild();
 *         addProjectReference("ProjectA", "ProjectB", true);
 *         ...
 *     }
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author rsomayaj
 * 
 */
public abstract class AbstractN2BaseValidationTest extends
        AbstractBaseValidationTest {

    /**
     * 
     */
    public AbstractN2BaseValidationTest() {
        this(true);
    }

    public AbstractN2BaseValidationTest(boolean isCheckProblemExists) {
        super(isCheckProblemExists);
    }

    /**
     * @see com.tibco.xpd.core.test.util.AbstractBaseResourceTest#configureProject(org.eclipse.core.resources.IProject)
     * 
     * @param testProject
     */
    @Override
    protected void configureProject(IProject testProject) {
        super.configureProject(testProject);
        N2TestCreationUtil.configureAsN2Project(testProject, getTestPlugInId());
        // TestUtil.delay(60000);
    }

    /**
     * Add a project's reference form a project to a referencedProject (it uses
     * the names of the projects and assumes that the projects exist's in the
     * workspace and are available).
     * 
     * @param projectName
     *            the name of the project to add reference.
     * @param referencedProjectName
     *            the name of the project to be referenced from the project.
     * @param log
     *            'true' if the fact should be logged to the System.out.
     */
    protected void addProjectReference(String projectName,
            String referencedProjectName, boolean log) {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject simpleRestCallProject = root.getProject(projectName);
        IProject simpleRestDefinitionProject =
                root.getProject(referencedProjectName);
        if (simpleRestCallProject.isAccessible()
                && simpleRestDefinitionProject.isAccessible()) {
            try {
                ProjectUtil.addReferenceProject(simpleRestCallProject,
                        simpleRestDefinitionProject);
                if (log) {
                    System.out
                            .println(String
                                    .format("Added project reference: ('%1$s'  ->  '%2$s').", //$NON-NLS-1$
                                            projectName,
                                            referencedProjectName));
                }
            } catch (CoreException e) {
                String msg =
                        String.format("Problem with adding project references ('%1$s'  ->  '%2$s').", //$NON-NLS-1$
                                projectName,
                                referencedProjectName);
                throw new RuntimeException(msg, e);
            }
        }
    }
}
