package com.tibco.inteng;

import java.util.List;

public interface Process extends Invocable {
	
	/**
	 * This method will return a ProcesState object for this process.
	 * 
	 * @return ProcessState
	 */
	public abstract ProcessState newProcessState();
	
	/**
	 * 
	 * This method will return a ProcesState object for this process and 
	 * intialise the state object with the values from the passed list. Each 
	 * instance of the list is an XpdlData object. 
	 * 
	 * @return ProcessState
	 */
	public abstract ProcessState newProcessState(List formalParameters);
	
	/**
	 * 
	 * This method returns the Package which contains this process
	 * 
     * @return Package 
     */
    public Package getPackage();
    
    /**
     * This method returns whether the Activity represented by this id is
     * manual or not.
     * @param activityId
     * @return
     */
    public boolean isManualActivity(String activityId);
}
