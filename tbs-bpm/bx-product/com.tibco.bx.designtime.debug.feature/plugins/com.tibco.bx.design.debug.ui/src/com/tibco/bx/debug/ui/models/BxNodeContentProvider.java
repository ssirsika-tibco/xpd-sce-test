package com.tibco.bx.debug.ui.models;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.model.elements.StackFrameContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate;
import org.eclipse.debug.ui.IDebugUIConstants;

@SuppressWarnings("restriction")
public class BxNodeContentProvider extends StackFrameContentProvider {
	private String contextId = ""; //$NON-NLS-1$

	@Override
	protected int getChildCount(Object element, IPresentationContext context,
			IViewerUpdate monitor) throws CoreException {
		if (contextId.equals(IDebugUIConstants.ID_DEBUG_VIEW)) {
			return ((IThread) element).getStackFrames().length;
		}
		return super.getChildCount(element, context, monitor);
	}

	@Override
	protected Object[] getChildren(Object parent, int index, int length,
			IPresentationContext context, IViewerUpdate monitor)
			throws CoreException {
		if (contextId.equals(IDebugUIConstants.ID_DEBUG_VIEW)) {
			return getElements(((IThread) parent).getStackFrames(), index, length);
		}
		return super.getChildren(parent, index, length, context, monitor);
	}

	@Override
	protected boolean hasChildren(Object element, IPresentationContext context,
			IViewerUpdate monitor) throws CoreException {
		if (contextId.equals(IDebugUIConstants.ID_DEBUG_VIEW)) {
			return ((IThread) element).hasStackFrames();
		}
		return super.hasChildren(element, context, monitor);
	}

	@Override
	protected boolean supportsContextId(String id) {
		this.contextId = id;
		return super.supportsContextId(id)
				|| IDebugUIConstants.ID_DEBUG_VIEW.equals(id);
	}
}
