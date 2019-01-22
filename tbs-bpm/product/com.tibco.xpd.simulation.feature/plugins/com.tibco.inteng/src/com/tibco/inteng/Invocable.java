package com.tibco.inteng;

import java.util.List;

public interface Invocable extends ExtendedAttributeProvider{	
	
	/**
     * Returns a list of empty formal parameters. Each item in the list is
     * an instance of XpdlData.
     * 
     * @return List, each element is an instance of XpdlData.
     */
    public abstract List getFormalParameters();
    
    /**
     * Return list of initialized (from current thread) formal parameters.
     * 
     * @param thread Process thread
     * @return list of Formal Parameters which are instances of XpdlData.
     */
    public List getPopulatedFormalParameters(ProcessThread thread);
    
    /**
     * Validate list arguments   
     * 
     * @param state
     * @param args
     * @return
     */
	public abstract boolean validate(ProcessState state, List args);
	
    /**
     * Returns ID of the interface
     * 
     * @return
     */
    public abstract String getId();
    /**
     * Submit list of parameters to workflow data.<br>
     * Validation according to mandatory fields are perfromed when <code>validate</code> is set.
     * No data type validation is performed (it should be done on seting valies for XpdlData).
     * 
     * Ususal usage pattern:
     * <pre>
     * ...
     * List params = app.getFormalPArameters(interactionState);
     * // modify params i.e.: 
     * ((XpdlData)params.get(0)).setValue("new value");
     * if (!app.submit(interactionState,params,true)) {
     *   // show error message etc.
     * }
     * ...
     * </pre>
     * 
     * @param state interaction state
     * @param args list of formal parameters     
     */
    public abstract void submit(ProcessState state, List args);
}
