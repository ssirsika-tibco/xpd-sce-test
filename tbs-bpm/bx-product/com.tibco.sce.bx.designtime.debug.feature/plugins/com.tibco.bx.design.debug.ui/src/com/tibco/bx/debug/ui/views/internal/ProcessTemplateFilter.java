package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.util.ProcessUtil;

public class ProcessTemplateFilter extends ViewerFilter {

	// private List<BxProcessTemplate> templatesInWS ;
	String modelType;

	public ProcessTemplateFilter(String modelType) {
		super();
		this.modelType = modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof ProcessTemplate) {
			ProcessTemplate template = (ProcessTemplate) element;
			String processId = template.getProcessId();
			boolean isFlawProcess = false;
			if (TargetManager.getDefault().getCurrentTarget() != null) {
				isFlawProcess = TargetManager.getDefault().getCurrentTarget().isFlawProcess(processId);
			}
			return ProcessUtil.getProcess(processId, modelType) != null && !isFlawProcess;
		}
		return false;
	}

}
