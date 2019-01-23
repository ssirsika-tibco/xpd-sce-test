/* 
 ** 
 **  MODULE:             $RCSfile: ParameterBasedDistribution.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2006-05-04 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2006 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.model;

import java.util.HashMap;
import java.util.Map;

import desmoj.core.dist.RealDist;

public class ParameterBasedRealDistribution {

    private Map valueDistributionMap;

    private RealDist defaultDistribution;

    private String parameterId;

    ParameterBasedRealDistribution() {
        valueDistributionMap = new HashMap();
    }
    
    ParameterBasedRealDistribution(String parameterId) {
        this();
        this.parameterId = parameterId;
    }

    public double sample(Object value) {
        Object dist = valueDistributionMap.get(value);
        if (dist instanceof RealDist) {
            return ((RealDist) dist).sample();
        } else if (defaultDistribution != null) {
            return defaultDistribution.sample();
        }
        throw new IllegalArgumentException(
                "There is no distribution for value: " + value); //$NON-NLS-1$
    }

    public void putValue(Object value, RealDist distribution) {
        valueDistributionMap.put(value, distribution);
    }
    
    public RealDist getDistribution(Object value) {
        return (RealDist) valueDistributionMap.get(value);
    }

    public void setDefaultDistribution(RealDist distribution) {
        defaultDistribution = distribution;
    }

    public RealDist getDefaultDistribution() {
        return defaultDistribution;
    }

    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }
}
