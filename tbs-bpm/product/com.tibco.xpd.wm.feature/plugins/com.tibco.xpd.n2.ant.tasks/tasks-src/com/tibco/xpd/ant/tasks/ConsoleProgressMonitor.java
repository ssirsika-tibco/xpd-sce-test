package com.tibco.xpd.ant.tasks;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * 
 * @author mtorres
 *
 */
public class ConsoleProgressMonitor implements IProgressMonitor{

	public String subTaskName = ""; //$NON-NLS-1$
	public int worked = 0;
	
	private String currentTask;
	private int totalWork;
	
	private boolean isCancelled = false;
	
	public void beginTask(String name, int totalWork) {
		this.currentTask = name;
		this.totalWork = totalWork;;
		System.out.println(name);
	}

	public void done() {
		this.worked = totalWork;
		this.subTaskName = ""; //$NON-NLS-1$
	}

	public void internalWorked(double work) {
		//this.internalWorked(work);			
	}

	public boolean isCanceled() {
		return isCancelled;
	}

	public void setCanceled(boolean value) {
		isCancelled = value;
	}

	public void setTaskName(String name) {
		currentTask = name;
		System.out.println(name);
	}

	public void subTask(String name) {
		this.subTaskName = name;
		System.out.println(name);
	}

	public void worked(int work) {		
		this.worked += work;
	}
	
	public String getCurrentTask(){
		return currentTask;
	}
}
