/* 
 ** 
 **  MODULE:             $RCSfile: CaseGenerator.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-25 $ 
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
package com.tibco.xpd.simulation.preprocess;

import java.util.Iterator;

/**
 * Generates cases for simulation.
 *
 * @author jarciuch
 */
public interface CaseGenerator {

    /**
     * Returns case iterator. Iterator 'next()' method returns generated objects
     * of Case type.
     * 
     * @return case iterator.
     */
    public Iterator iterator();
}
