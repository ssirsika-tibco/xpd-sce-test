package com.tibco.inteng;

public interface ProcessThread {

	/**
	 * Getter for current activity id
	 * 
	 * @return current activity id
	 */
	public abstract String getCurrentActivityId();
	
	/**
	 * Getter for current activity performer
	 *  
	 * @return current activity performer
	 */
	public abstract String getCurrentActivityPerformer();

	/**
	 * getter for interaction
	 * 
	 * @return interaction definition
	 */
	public abstract ProcessState getProcessState();

	/**
	 * Return true when state is interaction the end state (after last activity)
	 * 
	 * @return if the interaction has finished
	 */
	public abstract boolean isFinished();

	/**
	 * Get last error message key
	 * 
	 * @return last error message key, null when there was no error
	 */
	public abstract String getErrorMessageKey();

	/**
	 * Get activityId on which last error has occured
	 * 
	 * @return
	 */
	public abstract String getErrorActivityId();

	/**
	 * returns true if last error heppend on given activity. 
	 * convinience method, equiwalent to:
	 * <code>
	 * if (state.getErrorMessageKey()!=null
	 *      &&activityId.equals(state.getErrorActivityId()) {
	 * 		// something
	 * }
	 * </code>
	 * 
	 * @param activityId activity to check
	 * @return
	 */
	public abstract boolean isError(String activityId);	
	/**
	 * @return Returns the threadName.
	 */
	public abstract String getName();

	/**
	 * @return Returns the subbited.
	 */
	public abstract boolean isSubmitted();

	/**
	 * @return
	 */
	public abstract boolean isWaiting();
	
	/**
	 * @return
	 */
	public abstract boolean isSecurityWait();	

	/**
	 * @return truem if thread is on manual activity
	 */
	public abstract boolean isManual();

	/**
	 * @return Returns the subFlowKey.
	 */
	public abstract ProcessState getSubFlowProcessState();
}