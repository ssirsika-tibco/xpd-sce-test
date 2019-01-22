/* 
 ** 
 **  MODULE:             $RCSfile: PreprocessManager.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-12 $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.preprocess;

import java.net.URL;
import java.util.Map;

import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.simulation.model.WorkflowModel;
import com.tibco.xpd.simulation.preprocess.impl.ProcessManagerImpl;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

import desmoj.core.dist.RealDist;
import desmoj.core.simulator.Model;

public interface ProcessManager {

    public static ProcessManager INSTANCE = new ProcessManagerImpl();

    /**
     * Generates simulation data for package. This method modifies package
     * adding data which simulation needs to execute. All changes are done on
     * editing domain stack so user can undo modifications done by this method
     * using stack from passed editing domain.
     * 
     * @param xpdlPackage
     *            the package for which data schould be generated.
     * @param ed
     *            EditingDomain with stack on which the command will be
     *            executed.
     */
    public void generateSimData(Package xpdlPackage, EditingDomain ed);

    public CaseGenerator getCaseGenerator(Process process, int noOfCases);

    public WorkflowModel getWorkflowModel();

    /**
     * Creates simulation model.
     * 
     * @param repositoryLocation
     *            base repository location. This should be absolute URL to
     *            process root.
     * @param xpdlPath
     *            path of xpdl package file (relative to process root). It
     *            should always use forward shalses as segment separators and be
     *            without leading forward slash.
     * @param process the process for witch the simulation model will be created.
     * @return DesmosJ workflow simulation model. 
     */
    public WorkflowModel createWorkflowModel(URL repositoryLocation,
            String xpdlPath, Process process);

}
