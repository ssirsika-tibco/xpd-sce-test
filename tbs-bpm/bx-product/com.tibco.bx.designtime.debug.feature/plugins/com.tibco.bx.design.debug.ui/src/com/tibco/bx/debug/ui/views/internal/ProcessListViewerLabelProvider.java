package com.tibco.bx.debug.ui.views.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.bx.debug.common.model.ProcessTemplate;
import com.tibco.bx.debug.core.util.ProcessUtil;
import com.tibco.bx.debug.ui.util.DebugUIUtil;

public class ProcessListViewerLabelProvider extends LabelProvider {

	private String modelType;
	private static final String BUSINESS_SERVICE_TYPE = "BusinessService"; //$NON-NLS-1$
	
	
	public ProcessListViewerLabelProvider(String modelType) {
		super();
		this.modelType = modelType;
	}

	public Image getImage(Object element) {
		if (modelType != null && element instanceof ProcessTemplate) {
			ProcessTemplate template = (ProcessTemplate) element;
			EObject process = ProcessUtil.getProcess(template.getProcessId(), modelType);
			if (ProcessUtil.isPublishedAsBusinessService(process)) {
				return DebugUIUtil.getProcessImage(BUSINESS_SERVICE_TYPE);
			}
			return DebugUIUtil.getProcessImage(modelType);
		}
		return super.getImage(element);
	}

	public String getText(Object element) {
		if (element instanceof ProcessTemplate) {
			return ((ProcessTemplate) element).getDetails();
		}
		return super.getText(element);
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
}