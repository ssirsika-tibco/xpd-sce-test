package com.tibco.inteng;

import java.util.Map;

import com.tibco.inteng.exceptions.XpdlException;

public interface InteractionEngine {

    /**
     * Check interaction state, and move all threads to next manual state if
     * possible.
     * 
     * Thread can be moved to next state if: - thread is in manual activity and
     * application is subimted - thread is waiting for some other threads to go
     * to next activity and all other threads become 'waiting'
     * 
     * After last state the current activity is set to null, and
     * <code>InteractionStateImpl.isFinished()</code> returns true.
     * 
     * @param state -
     *            current interaction state
     * @throws XpdlException
     *             When you try to get next state from finished workflow.
     */
    public abstract void executeProcess(ProcessState intState);

    /**
     * Get the current application which is there on the current thread
     * 
     * @param state
     * @return an instance of Invocable
     */
    public abstract Invocable getCurrentApplication(ProcessState state);

    /**
     * This will help the user to set the parameters which will override the
     * default behaviour while executing the process.
     * 
     * @param configPrameters
     */
    public abstract void setConfigParameters(Map configPrameters);

    /**
     * Returns the overriden behaviour while executing the process.
     * 
     * @return
     */
    public abstract Map getConfigParameters();
}