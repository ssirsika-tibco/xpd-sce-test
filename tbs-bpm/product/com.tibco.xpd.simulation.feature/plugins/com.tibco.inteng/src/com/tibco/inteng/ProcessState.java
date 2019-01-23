package com.tibco.inteng;

import java.util.List;
import java.util.Map;
import com.tibco.inteng.xpdldata.XpdlData;

public interface ProcessState {
	/**
	 * getter for security state
	 * 
	 * @return boolean
	 */
	public abstract boolean isStateToBeSaved();		

	/**
	 * Return true when state is interaction the end state (after last activity)
	 * 
	 * @return if the interaction has finished
	 */
	public abstract boolean isFinished();

	/**
	 * Process' data as a map. Clients should not modify this map.
	 * 
	 * @return map of fields
	 */
	public abstract Map getFields();

	/**
	 * 
	 * @return instance of InteractionStateDocument, representing the state.
	 */
	public abstract String getStateXml();
	/**
	 * Returns current thread for ProcessState. If current thread is a
	 * invocation of subflow, method returns current thread of subflow.
	 * 
	 * Thread returnd from this method may be in <b>different</b> interaction
	 * state or even different xpdl package:
	 * 
	 * <code>state</code> may be not equal to
	 * <code>state.getCurrentThread().getInteractionState()</code> or
	 * <code>state.getInteraction()</code> may be not equal to
	 * <code>state.getCurrentThread().getIneractionState().getInteraction()</code>
	 * 
	 * @return current thread
	 */
	public abstract ProcessThread getCurrentThread();

	/**
	 * Switch to thread with given name
	 * 
	 * @param newCurrentThreadName
	 */
	public abstract void switchToThread(String newCurrentThreadName);
	
	/**
	 * Convinience method. Returns Process' XpdlData selected by given path path
	 * can be in format: 'field' - simple field name, result the same as
	 * 'getField("field")' method '/field' - selection of field, result the same
	 * as 'getField("field")' method '/field/subElem' - selection of field,
	 * result the same as 'getField("field").get("subElem")' method
	 * 
	 * @param path
	 *            selection of XpdlData
	 * @return selected xpdl data
	 */
	public XpdlData getPath(String path);
	
	/**
	 * This method will rename the thread with a new name.
	 * @param oldName
	 * @param newName
	 */	
	public void renameThread(String oldName, String newName);
	/**
	 * Return XPDL Process for this ProcessState
	 * 
	 * @return
	 */
	public abstract Process getProcess();

	/**
	 * @return Returns the sessionData.
	 */
	public abstract Map getSessionData();
	
	/**
	 * @param sessionData
	 *            The sessionData to set.
	 */
	public abstract void setSessionData(Map sessionData);
	
	/**
	 * List of thread names that can be presented to the user.This list does not
	 * include threads which cannot proceed further because of security
	 * reasons.
	 * 
	 * @return list of names if there is more then 1 thread
	 */
	public abstract List getAllUserThreadsName();
	
	/**
	 * List of 'top-level' thread names
	 * 
	 * @return List of thread names
	 */
	public List getStateThreadsName();
	
	/**
	 * This method will work on the passed threadName which could be either in 
	 * format xx::yy::zz or xx. If the passed threadName format is xx::yy::zz 
	 * then it will return the thread with name zz. If the passed threadName 
	 * format is xx then it will return the thread with name xx. 
	 * 
	 * @param threadName
	 * @return thread with given name, or null
	 */
	public ProcessThread getThreadByName(String threadName);
	
	/**
	 * Returns full name of current thread: if current thread is on subFlow it
	 * return subFlow currentThreadFullName prefixed with ths thread name, ie:
	 * 'thread::subFlowThread'
	 * 
	 * @return Full name of current thread
	 */
	public String getCurrentThreadFullName();
	
	
}