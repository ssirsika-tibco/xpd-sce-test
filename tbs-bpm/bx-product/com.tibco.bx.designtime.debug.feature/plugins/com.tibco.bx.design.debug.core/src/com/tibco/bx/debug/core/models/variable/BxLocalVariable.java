package com.tibco.bx.debug.core.models.variable;

import com.tibco.bx.debug.common.model.ProcessVariable;
import com.tibco.bx.debug.core.models.IBxDebugTarget;
/**
 * an activity variable.
 */
public class BxLocalVariable extends BxFieldVariable{

	public BxLocalVariable(IBxDebugTarget debugTarget,
			ProcessVariable processVariable) {
		super(debugTarget, processVariable);
	}

}
