package com.tibco.inteng;

import com.tibco.inteng.impl.InteractionEngineImpl;


public class InteractionEngineFactory {
	/**
	 * Suppress default constructor for non-instantiability
	 * 
	 */
	private InteractionEngineFactory() {
	}

	/**
	 * This class will return the repository instance required to start
	 * interaction process.
	 * 
	 * @return
	 */
	public static InteractionEngine getInteractionEngine() {
		InteractionEngine processEngine = InteractionEngineImpl.getInstance();
		return processEngine;
	}
}
