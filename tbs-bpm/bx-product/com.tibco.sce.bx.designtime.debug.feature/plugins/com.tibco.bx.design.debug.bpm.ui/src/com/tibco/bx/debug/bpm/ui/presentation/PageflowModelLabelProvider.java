package com.tibco.bx.debug.bpm.ui.presentation;

import org.eclipse.swt.graphics.Image;

import com.tibco.bx.debug.bpm.ui.DebugBPMUIActivator;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;
import com.tibco.bx.debug.ui.util.DecoratorUtil;

public class PageflowModelLabelProvider extends BxModelLabelProvider {

	@Override
	public Image getImage(Object element) {
		Image image = null;
		if (element instanceof IBxThread) {
			image = DebugBPMUIActivator.getRegisteredImage(DebugBPMUIActivator.IMG_PAGEFLOW_PROCESS);
		} else if (element instanceof IBxStackFrame) {
			image = BxModelUIUtil.getImageForStackframe((IBxStackFrame) element);
			image = DecoratorUtil.addCurrentDecorator((IBxStackFrame)element, image);
		}
		return image;
	}

}
