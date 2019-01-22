/* 
 ** 
 **  MODULE:             $RCSfile: ConditionGenerator.java $ 
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

/**
 * Generates and adds artifacts to workflow process which are needed for
 * simulation.
 * 
 * @author jarciuch
 */
public interface SimDataGenerator {

    /**
     * 
     * Adds simulation related data for activity appending commands to compound
     * command from context using context EditingDomain and/or updates context
     * workflow parameters parameter according to informations derived from
     * activity which also could be obtained from context (@see
     * GeneratorContext#getCurrentActivity()).
     * 
     * @param context
     *            current generator context.
     */
    void generateSimDataForActivity(GeneratorContext context);
}
