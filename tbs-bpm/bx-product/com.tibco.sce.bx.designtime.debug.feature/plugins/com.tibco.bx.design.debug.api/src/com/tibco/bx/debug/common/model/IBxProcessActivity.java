package com.tibco.bx.debug.common.model;
/**
 * 
 * @author will
 *
 */
public interface IBxProcessActivity {

	public String getActivityId();

	public String getActivityName();

	public String getInstanceId();

	public String getParentInstanceId();

	public String getState();

}
