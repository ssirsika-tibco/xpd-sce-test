package com.tibco.bx.debug.bpm.core.models;

import java.util.ArrayList;
import java.util.List;

import com.tibco.bx.debug.common.model.IBxNodeDefinition;

public class BxNodeDefinition implements IBxNodeDefinition {

	private List<String> sourceIds;
	private List<String> targetIds;
	private String parentId;
	private String activityId;
	private String activityName;
	private boolean canStepInto;
	private boolean canStepOut;
	private String type;
	
	@Override
	public boolean canStepInto() {
		return canStepInto;
	}

	@Override
	public boolean canStepOut() {
		return canStepOut;
	}

	@Override
	public String getActivityId() {
		return activityId;
	}

	@Override
	public String getActivityName() {
		return activityName;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	@Override
	public String[] getSourceIds() {
		if(sourceIds == null){
			return new String[0];
		}
		return sourceIds.toArray(new String[0]);
	}

	@Override
	public String[] getTargetIds() {
		if(targetIds == null){
			return new String[0];
		}
		return targetIds.toArray(new String[0]);
	}

	@Override
	public String getType() {
		return type;
	}

	public void addSourceId(String sourceId) {
		if(sourceIds == null)
			sourceIds = new ArrayList<String>();
		sourceIds.add(sourceId);
	}

	public void addTargetId(String targetId) {
		if(targetIds == null)
			targetIds = new ArrayList<String>();
		this.targetIds.add(targetId);;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setCanStepInto(boolean canStepInto) {
		this.canStepInto = canStepInto;
	}

	public void setCanStepOut(boolean canStepOut) {
		this.canStepOut = canStepOut;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

}
