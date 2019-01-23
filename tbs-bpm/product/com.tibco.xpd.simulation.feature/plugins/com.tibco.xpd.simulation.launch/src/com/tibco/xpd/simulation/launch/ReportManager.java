/* 
 ** 
 **  MODULE:             $RCSfile: ReportManager.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-12-14 $ 
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
package com.tibco.xpd.simulation.launch;

import java.net.URL;

import com.tibco.simulation.report.SimRepExperiment;

import desmoj.core.util.ExperimentListener;
import desmoj.core.util.SimClockListener;


public interface ReportManager extends SimClockListener,
        ExperimentListener {
    public abstract SimRepExperiment getSimRepExperiment();

    public abstract int getTotalCaseCount();

    public URL getRepositoryLocation();

    public String getPackageFilePath();
}