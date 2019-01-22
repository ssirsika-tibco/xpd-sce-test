package com.tibco.bx.debug.ui.views.internal;

import com.tibco.bx.debug.core.models.BxDebugTarget;


public interface IDebugTargetChangedListener {
    
    public void selectionChanged(BxDebugTarget event);
    
}
