/* 
** 
**  MODULE:             $RCSfile: ApplicationGenerator.java $ 
**                      $Revision: 1.0 $ 
**                      $Date: 2005-11-22 $ 
** 
**  DESCRIPTION:           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: $ 
** 
*/
package com.tibco.xpd.simulation.preprocess.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.WorkflowProcessSimulationDataType;
import com.tibco.xpd.simulation.preprocess.GeneratorContext;
import com.tibco.xpd.simulation.preprocess.SimDataGenerator;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Application;
import com.tibco.xpd.xpdl2.ApplicationType;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

public class ApplicationGeneratorImpl implements SimDataGenerator {
    
    private Set xpdlApplications;

    public void generateSimDataForActivity(
            GeneratorContext contex) {
        Activity activity = contex.getCurrentActivity();
        CompoundCommand compoundCmd = contex.getCompoundCommand();
        Command cmd = null;        
        Process workflowProcess = SimulationXpdlUtils.getWorkflowProcess(activity);
        Package pack = SimulationXpdlUtils.getPackage(workflowProcess);
        if (xpdlApplications == null) {
            createEntryApplicationSet(workflowProcess);
        }
        String applicationId = null;
        Implementation impl = activity.getImplementation();
        /*if (impl != null && impl.getTool() != null && impl.getTool().size() == 1) {
            Tool tool = (Tool) impl.getTool().get(0);
            if (tool.getType() == ApplicationType.APPLICATION_LITERAL) {
                applicationId = tool.getId();
            }
        }
        if (applicationId != null && !xpdlApplications.contains(applicationId)) {
            Application app = Xpdlr2Factory.eINSTANCE.createApplication();
            app.setId(applicationId);
            cmd = AddCommand.create(contex.getEitingDomain(), pack, Xpdlr2Package.eINSTANCE.getApplicationsContainer_Applications(), app);
            compoundCmd.append(cmd);
            xpdlApplications.add(applicationId);
        }*/
        
        //make application with no implementation a root applications.
        //TODO remove if we will be sure that interaction engine is working without this.
        /*if (activity.getImplementation().getNo() != null) {
            Route route = Xpdlr2Factory.eINSTANCE.createRoute();
            cmd = SetCommand.create(ed, activity, Xpdlr2Package.eINSTANCE.getActivity_Route(), route);
            compoundCmd.append(cmd);
        }*/
    }

    private void createEntryApplicationSet(Process process) {
        xpdlApplications = new HashSet();
        Package pack = SimulationXpdlUtils.getPackage(process);
        for (Iterator iter = pack.getApplications().iterator(); iter.hasNext();) {
            Application application = (Application) iter.next();
            xpdlApplications.add(application.getId());
        }
        /*for (Iterator iter = process.getApplications().iterator(); iter.hasNext();) {
            Application application = (Application) iter.next();
            xpdlApplications.add(application.getId());
        }*/
        
    }
    
}
