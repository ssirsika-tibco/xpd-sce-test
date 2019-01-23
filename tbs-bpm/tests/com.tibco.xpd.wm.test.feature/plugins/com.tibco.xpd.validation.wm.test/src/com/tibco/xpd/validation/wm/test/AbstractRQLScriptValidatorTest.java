/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.validation.wm.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.core.test.util.TestResourceInfo;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.core.test.validations.AbstractBaseExtendedValidationTest;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;

/** 
 * @author mtorres
 * @since 3.3
 */
public abstract class AbstractRQLScriptValidatorTest extends
        AbstractBaseExtendedValidationTest {

    public AbstractRQLScriptValidatorTest(boolean isCheckProblemExists) {
        super(isCheckProblemExists);
    }
    
    public AbstractRQLScriptValidatorTest() {
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
                            List<EObject> participants = ProcessDataUtil.getParticipants(process);
                            for (EObject object : participants) {
                                if(object instanceof Participant){
                                    Participant aParticipant = (Participant) object;
                                    affectedIds.add(aParticipant.getId());
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
