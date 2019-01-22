package com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TaskBinding {

	private final String destination;
	private final Set<TaskReference> tasks;

	public TaskBinding(String taskId, Set<TaskReference> tasks) {
		destination = taskId;
		this.tasks = new HashSet<TaskReference>(tasks);

	}

	public String getDestination() {
		return destination;
	}

	public Set<TaskReference> tasks() {
		return Collections.unmodifiableSet(tasks);
	}

}
