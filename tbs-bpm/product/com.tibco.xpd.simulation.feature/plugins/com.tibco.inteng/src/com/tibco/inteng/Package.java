package com.tibco.inteng;

import java.util.Map;

public interface Package extends ExtendedAttributeProvider {
	/**
	 * Name of default resource bundle for interaction.  
	 */
	public static final String INTERACTION_BUNDLE = "interaction";
	/**
	 * Returns a Map of external packages which are referenced in this package, 
	 * the key of each entry in the Map contains the value of 'href' attribute 
	 * of ExternalPackage element & the value is a List of ExtendedAttributes
	 * for this package. Each item in the  list is an instance of 
	 * com.tibco.inteng.ExtendedAttribute.
	 * 
	 * @return Map
	 */
	public abstract Map getExternalPackages();

	/**
	 * This method returns the id of the package
	 * @return
	 */
	public abstract String getPackageId();
	/**
	 * This method will return the Process with the specified id.
	 * @param processId
	 * @return
	 */
	public abstract Process getProcess(String processId);

	/**
	 * This method will return a Map of processes which are there in the package
	 * the key for the Map is the id of the process and the value being an 
	 * instance of com.tibco.inteng.Process
	 * 
	 * @return Map of the process objects 
	 */
	public abstract Map getProcesses();
	
	/**
	 * Returns repository for this interaction definition
	 * 
	 * @return InteractionRepositoryImpl to where this interaction has been loaded
	 */
	public abstract InteractionRepository getInteractionRepository();	

}