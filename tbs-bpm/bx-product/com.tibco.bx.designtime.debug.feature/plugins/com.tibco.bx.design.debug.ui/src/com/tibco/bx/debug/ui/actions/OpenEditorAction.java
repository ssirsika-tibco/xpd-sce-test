package com.tibco.bx.debug.ui.actions;

import org.eclipse.jface.action.Action;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;

public class OpenEditorAction extends Action{

    private ProcessTemplate processTemplate ;
    private String modeType;
    @Override
    public void run() {

    	EmulationUIUtil.openOriginalEditor(processTemplate.getProcessId(), modeType);
    }

    public OpenEditorAction(ProcessTemplate processTemplate, String modeType) {
        this.processTemplate = processTemplate;
        this.modeType = modeType;
    }
}
