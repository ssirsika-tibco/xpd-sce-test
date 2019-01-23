package com.tibco.xpd.n2.daa.test.junit.listeners;

import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.n2.daa.test.junit.DaaTestListener;

/**
 * Writes all details of the DAA test to the console.
 * 
 * @author tstephen
 * 
 */
public class ConsoleReporter implements DaaTestListener {

	public void fire(String projectName, IStatus status, Level level) {
		System.err.println(projectName + " " + status.getSeverity() + " " //$NON-NLS-1$ //$NON-NLS-2$
				+ level.toString());
		System.err.println("  " + status.getMessage()); //$NON-NLS-1$
	}

	public void fireTestComplete() {
		; // nothing required
	}

}
