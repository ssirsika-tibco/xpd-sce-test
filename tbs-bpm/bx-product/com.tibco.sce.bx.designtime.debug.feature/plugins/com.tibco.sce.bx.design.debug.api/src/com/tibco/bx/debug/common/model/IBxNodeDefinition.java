package com.tibco.bx.debug.common.model;
/**
 * 
 * @author will
 *
 */
public interface IBxNodeDefinition {

	String getActivityId();
	String getActivityName();
	String getParentId();
	boolean canStepInto();
	boolean canStepOut();
	String getType();
	String[] getTargetIds();
	String[] getSourceIds();
}
