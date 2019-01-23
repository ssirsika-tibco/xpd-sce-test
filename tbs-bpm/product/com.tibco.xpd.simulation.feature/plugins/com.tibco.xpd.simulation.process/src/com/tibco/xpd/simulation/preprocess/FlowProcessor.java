/* 
 ** 
 **  MODULE:             $RCSfile: WorkflowProcessor.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-15 $ 
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

import com.tibco.xpd.xpdl2.FlowContainer;

public interface FlowProcessor {
    void processFlow(SimDataGenerator[] generators,
            FlowContainer flowContainer, GeneratorContext context);
}
