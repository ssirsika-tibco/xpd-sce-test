package com.tibco.bx.xpdl2bpel.extensions;


/**
 * This interface defines additional API that is invoked at design time when generating a .bpel file.
 * The activity developer should implement this interface to add extra data variables and mappings that
 * are needed for the extension model.
 */
public interface IActivityConfigurationModelBuilder2 {

    /**
     * This method returns special variable(s) (e.g. <code>returnValue</code> for POJO activities)
     * for the extension activity.
     * @return special variables for the extension activity
     */
    org.eclipse.bpel.model.Variable[] getVariables();
    
    /**
     * This method returns data mappings that need to be executed before the extension activity.
     */
    org.eclipse.bpel.model.Copy[] getPreActivityMappings();
    
    /**
     * This method returns data mappings that need to be executed after the extension activity.
     */
    org.eclipse.bpel.model.Copy[] getPostActivityMappings();

}
