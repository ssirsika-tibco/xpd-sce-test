/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.validation.wm.test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.AbstractBaseExtendedValidationTest;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/** 
 * @author mtorres
 * @since 3.3
 */
public abstract class AbstractJavaScriptValidatorTest extends
        AbstractBaseExtendedValidationTest {

    public AbstractJavaScriptValidatorTest(boolean isCheckProblemExists) {
        super(isCheckProblemExists);
    }
    
    public AbstractJavaScriptValidatorTest() {
        super(true);
    }    
    

    @Override
    protected void configureProject(IProject project) {
        TestUtil.addGlobalDestinationToProject(getTestPlugInId(),
                "BPM", project);//$NON-NLS-1$
    }

    protected Set<String> getAffectedIds(IResource resource) {
        if (resource != null) {
            WorkingCopy wc =
                    (WorkingCopy) XpdResourcesPlugin.getDefault()
                            .getWorkingCopy(resource);
            if (wc.getRootElement() instanceof Package) {
                Package model = (Package) wc.getRootElement();
                assertNotNull("Root element of the model is null even after migration", //$NON-NLS-1$
                        model);
                if (model != null) {
                    List<Process> processes = model.getProcesses();
                    if (processes != null) {
                        Set<String> affectedIds = new HashSet<String>();
                        for (Process process : processes) {
                            Collection<Activity> activities =
                                    Xpdl2ModelUtil
                                            .getAllActivitiesInProc(process);
                            if (activities != null) {
                                for (Activity activity : activities) {
                                    Implementation implementation =
                                            activity.getImplementation();
                                    if (implementation instanceof Task) {
                                        Task tsk = (Task) implementation;
                                        TaskScript taskScript =
                                                tsk.getTaskScript();
                                        if (taskScript != null) {

                                            Expression script =
                                                    taskScript.getScript();
                                            if (script != null) {
                                                String scriptGrammar =
                                                        script
                                                                .getScriptGrammar();
                                                if (scriptGrammar != null
                                                        && scriptGrammar
                                                                .equals("JavaScript")) {//$NON-NLS-1$
                                                    affectedIds.add(activity
                                                            .getId());
                                                }
                                            }
                                        }
                                    }
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
    
	@Override
	protected boolean shouldTestUnregisteredErrors(TestResourceInfo testResource) {
		if (testResource != null && testResource.getTokenisedPath() != null
				&& testResource.getTokenisedPath().endsWith(".xpdl")) {//$NON-NLS-1$
			return true;
		}
		return false;
	}

}
