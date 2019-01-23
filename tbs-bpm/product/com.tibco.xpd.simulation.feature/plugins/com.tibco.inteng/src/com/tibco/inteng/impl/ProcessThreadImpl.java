/* 
 ** 
 **  MODULE:             $RCSfile: InteractionThreadStateImpl.java $ 
 **                      $Revision: 1.7 $ 
 **                      $Date: 2005/03/21 09:08:02Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: InteractionThreadStateImpl.java $ 
 **    Revision 1.7  2005/03/21 09:08:02Z  tstephen 
 **    comment and spelling correction only  
 **    Revision 1.6  2005/03/18 13:54:23Z  tstephen 
 **    added logging and corrected some spellings  
 **    Revision 1.5  2005/01/26 16:32:24Z  KamleshU 
 **    Revision 1.4  2004/07/19 12:51:51Z  WojciechZ 
 **    remove dependency of InteractionImpl class to praticular WorkflowProcess in xpdl file. Now InteractionImpl class represent the whole xpdl package, and ProcessStateImpl carry name of it WorkflowProcess (first step to SubFlows) 
 **    Revision 1.3  2004/07/14 10:43:15Z  WojciechZ 
 **    list of user threads shows only threads on manual applications 
 **    Revision 1.2  2004/07/01 15:39:39Z  WojciechZ 
 **    tabbular interface, but still in progress 
 **    Revision 1.1  2004/06/28 15:52:12Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  24-Jun-2004  WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.inteng.impl;

import com.tibco.inteng.IntEngConsts;
import com.tibco.inteng.ProcessState;
import com.tibco.inteng.ProcessThread;


/**
 * Single thread of interaction.
 * Used as to hold thread position in interaction and some helper flags.
 * There is no any interaction logic in this class, all flags should be set
 * by InteractionImpl class, also all workflow relevant data are hold in
 * ProcessStateImpl class
 * 
 * @author WojciechZ
 */
public class ProcessThreadImpl implements ProcessThread {    
	
    /** 
     * interaction state 
     */
    private ProcessState interactionState;
    /** 
     * current activity id 
     */
    private String currentActivityId;    
    /** 
     * current activity 
     */
    private Activity currentActivity;
    /**
     * last error (business exception) message key
     */
    private String errorMessage;
    /**
     * activity on which last error was set
     */
    private String errorActivityId;
    /**
     * Name of the thread
     */
    private String threadName;
    /**
     * true, if current form was submitted.
     */
    private boolean submitted;
    /**
     * true, if current form is waiting for other thread
     * (either was submited, or is on manual activity with auto finish mode)
     */
    private boolean waiting;
    /**
     * true, if current thread cannot be processed further due to permissions problem      
     */
    private boolean securityWait;
    /**
     * true, is current thread is on manual activity:
     *  - was not submited
     *  - was submited, but finish mode is automatic
     */
    private boolean manual;
    
    private ProcessState subFlowProcessState;
    /**
     * Creation. Should not be instatiated outside InteractionImpl class
     * 
     * @param interactionState
     *            interaction state
     */
    ProcessThreadImpl(ProcessState interactionState) {
        this.interactionState = interactionState;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#getCurrentActivityId()
	 */
    public String getCurrentActivityId() {
        return currentActivityId;
    }
    /**
     * Setter for current state id
     * 
     * @param currentActivityId
     *            current activity
     */
    public void setCurrentActivityId(String currentActivityId) {
        this.currentActivityId = currentActivityId;
        submitted = false;
        waiting = false;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#getInteractionState()
	 */
    public ProcessState getProcessState() {
        return interactionState;
    }

    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#isFinished()
	 */
    public boolean isFinished() {
        return getCurrentActivityId() == null;
    }

    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#getErrorMessageKey()
	 */
    public String getErrorMessageKey() {
        return errorMessage;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#getErrorActivityId()
	 */
    public String getErrorActivityId() {
        return errorActivityId;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#isError(java.lang.String)
	 */
    public boolean isError(String activityId) {
        return activityId != null && getErrorMessageKey() != null
                && activityId.equals(getErrorActivityId());
    }
    /**
	 * Set error for current activity, remove previous error messages
	 * (null - no error)
	 * 
	 * @param key error message key or null (no error)
	 */
    void setErrorMessageKey(String key) {
        errorMessage = key;
        errorActivityId = getCurrentActivityId();
    }
    /**
	 * @param threadName
	 */
    void setName(String threadName) {
        this.threadName = threadName;
    }

    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#getName()
	 */
    public String getName() {
        return threadName;
    }    
    /**
	 * @param subbited
	 */
    public void setSubmitted(boolean subbited) {
        this.submitted = subbited;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#isSubmitted()
	 */
    public boolean isSubmitted() {
        return submitted;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#isWaiting()
	 */
    public boolean isWaiting() {
        return waiting;
    }

    /**
	 * @param waiting The waiting to set.
	 */
    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#isSecurityWait()
	 */
    public boolean isSecurityWait() {
        return securityWait;
    }

    /**
	 * @param securityWait The waiting to set because of security.
	 */
    void setSecurityWait(boolean securityWait) {
        this.securityWait = securityWait;
    }

    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#isManual()
	 */
    public boolean isManual() {
        return manual;
    }
    /**
	 * @param manual - if thread is on user activity
	 */
    public void setManual(boolean manual) {
        this.manual = manual;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#getSubFlowKey()
	 */
    public ProcessState getSubFlowProcessState() {
        return subFlowProcessState;
    }
    /**
     * This method will return the name of the performer associated with the
     * the current activity
     */
    public String getCurrentActivityPerformer(){
    	String toReturn = currentActivity.getPerformer();
    	if(toReturn==null){
    		toReturn = "";
    	}
    	return toReturn;
    }
    
    /**
     * This method stores the current activity of the thread
     * 
     * @param activity
     */
    public void setCurrentActivity(Activity activity){
    	this.currentActivity=activity;
    }
    
    /**
	 * @param subFlowKey The subFlowKey to set.
	 */
    public void setSubFlowProcessState(ProcessState subFlowKey) {
        this.subFlowProcessState = subFlowKey;
    }
    /* (non-Javadoc)
	 * @see com.tibco.inteng.InteractionThreadState#toString()
	 */
    public String toString() { 
        StringBuffer sb = new StringBuffer("<thread>") ;
        sb.append("<name>") ;
        sb.append(getName()) ;
        sb.append("</name>") ;
        sb.append("<currentActivity>") ;
        sb.append(getCurrentActivityId()) ;
        sb.append("</currentActivity>") ;
        sb.append("<statusFlags>") ;
        sb.append("<isSubmitted>") ;
        sb.append(isSubmitted()) ;
        sb.append("</isSubmitted>") ;
        sb.append("<isWaiting>") ;
        sb.append(isWaiting()) ;
        sb.append("</isWaiting>") ;
        sb.append("<isManual>") ;
        sb.append(isManual()) ;
        sb.append("</isManual>") ;
        sb.append("</statusFlags>") ;
        sb.append("</thread>") ;
        return sb.toString() ; 
    }
    
    String getFullName(){
        StringBuffer sb = new StringBuffer(getName());
        ProcessStateImpl subFlowKey = (ProcessStateImpl)getSubFlowProcessState();
        if (subFlowKey != null) {
            sb.append(IntEngConsts.THREAD_DELIM);
            sb.append(subFlowKey.getCurrentThreadFullName());
        }
        return sb.toString();

    }
}