package com.tibco.xpd.n2.daa.test.junit;

import org.eclipse.core.runtime.IStatus;

/**
 * Interface to implement to be notified of events during the DAA test. Intended
 * to support logging / reporting because this is a long running test.
 * 
 * @author tstephen
 */
public interface DaaTestListener {

	public enum Level {
		/**
		 * Messages indicating a simple Pass or Fail.
		 */
		SUMMARY,
		/**
		 * Messages intended for developer providing functionality under test.
		 */
		DETAIL,
		/**
		 * Messages useful only to the test maintainer.
		 */
		DEBUG;
	}

	/**
	 * Fires an event on the listener.
	 * 
	 * <p>
	 * Status and level are both provided so that listeners my choose to report
	 * a summary (pass / fail) in the event of error status or all the gory
	 * details.
	 * </p>
	 * 
	 * @param projectName
	 *            test project event relates to.
	 * @param status
	 *            status associated with the event encapsulating a message and a
	 *            severity.
	 * @param level
	 *            Indicates whether this status is considered summary, detail or
	 *            debug.
	 */
	void fire(String projectName, IStatus status, Level level);

	/**
	 * Notifies the listener that the test is complete and resources such as log
	 * files should be cleaned up.
	 */
	void fireTestComplete();
}
