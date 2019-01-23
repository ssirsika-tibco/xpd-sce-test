package com.tibco.bx.debug.http.runtime;

import com.tibco.bx.debug.common.model.IBxProcessActivity;
import com.tibco.www.bx._2010.debugging.debuggerType.Activity;

public class HTTPProcessActivity implements IBxProcessActivity{

	private Activity activity;
	public HTTPProcessActivity(Activity activity) {
		this.activity = activity;
	}

	@Override
	public String getActivityName() {
		return activity.getActivityName();
	}

	@Override
	public String getInstanceId() {
		return activity.getInstanceId();
	}

	@Override
	public String getParentInstanceId() {
		return activity.getParentInstanceId();
	}

	@Override
	public String getState() {
		return activity.getState();
	}

	@Override
	public String getActivityId() {
		return activity.getActivityId();
	}

}
