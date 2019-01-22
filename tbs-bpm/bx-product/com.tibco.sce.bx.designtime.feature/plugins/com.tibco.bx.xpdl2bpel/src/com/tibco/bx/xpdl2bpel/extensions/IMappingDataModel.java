/**
 * Copyright (c) TIBCO Software Inc 2004-2011. All rights reserved.
 */
package com.tibco.bx.xpdl2bpel.extensions;

/**
 * Interface that contains the data mapping model information
 * 
 * @author mtorres
 * 
 */
public interface IMappingDataModel {
    
    /**
     * Returns the source Data Model
     * @return IDataModel
     */
    public IDataModel getSourceDataModel();
    /**
     * Returns the target Data Model
     * @return IDataModel
     */
    public IDataModel getTargetDataModel();
    /**
     * Returns true if the mapping should be ignored when the source data is missing.
     * @return
     */
    public boolean isOptional();
    
    
}
