package com.tibco.xpd.ant.tasks;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * 
 * @author mtorres
 *
 */
public class ConsoleProgressMonitor implements IProgressMonitor{

	private String currentTask;
	
    private boolean isCancelled;
	
    @Override
    public void beginTask(String aTaskName, int aTotalWork) {
        setTaskName(aTaskName);
	}

	@Override
    public void done() {
	}

	@Override
    public void worked(int work) {
    }

    @Override
    public void internalWorked(double work) {
	}

	@Override
    public boolean isCanceled() {
		return isCancelled;
	}

	@Override
    public void setCanceled(boolean value) {
		isCancelled = value;
	}

    public String getCurrentTask() {
        return currentTask;
    }

    @Override
    public void setTaskName(String name) {
		currentTask = name;
		System.out.println(name);
	}

	@Override
    public void subTask(String name) {
		System.out.println(name);
	}
}
