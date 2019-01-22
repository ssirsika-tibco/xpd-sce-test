package com.tibco.xpd.processeditor.xpdl2.extensions.taskBinding;

import java.util.Set;

import com.tibco.xpd.processwidget.adapters.TaskType;

public interface TaskReference {

	/**
	 * Type supportec by this task.
	 * @return
	 */
	Set<TaskType> getTypes();

	/**
	 * Task id.
	 *
	 * @return
	 */
	String getId();

}
