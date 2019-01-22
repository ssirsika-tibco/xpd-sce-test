/**
 * Copyright (c) TIBCO Software Inc 2004-2011. All rights reserved.
 */
package com.tibco.bx.xpdl2bpel.extensions;

import java.util.List;

/**
 * Interface that contains the information regarding data mappings
 * 
 * @author mtorres
 * 
 */
public interface IActivityMappingDataModelProvider {

    /**
     * Returns a list of IMappingDataModel objects containing the Mapping Data
     * Model Information with direction input to service
     * 
     * @return a list of IMappingDataModel
     */
    public List<IMappingDataModel> getInputToServiceMappingData();
    
    /**
     * Returns the variables that need to be declared for the extension activity.
     * @return an array of ExtensionVariable
     */
    public ExtensionVariable[] getExtensionVariables();
    
    /**
     * Returns a list of IMappingDataModel objects containing the Mapping Data
     * Model Information with direction output from service
     * 
     * @return a list of IMappingDataModel
     */
    public List<IMappingDataModel> getOutputFromServiceMappingData();

}
