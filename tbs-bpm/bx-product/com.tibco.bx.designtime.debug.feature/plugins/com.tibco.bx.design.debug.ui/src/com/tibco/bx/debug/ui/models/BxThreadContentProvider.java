package com.tibco.bx.debug.ui.models;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.internal.ui.model.elements.ThreadContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdate;
import org.eclipse.debug.ui.IDebugUIConstants;

import com.tibco.bx.debug.core.models.BxThread;

@SuppressWarnings("restriction")
public class BxThreadContentProvider extends ThreadContentProvider {
	@Override
	protected boolean supportsContextId(String id) {
		return super.supportsContextId(id)
				|| id.equals(IDebugUIConstants.ID_VARIABLE_VIEW);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.model.provisional.elements.ElementContentProvider#getChildCount(java.lang.Object,
	 *      org.eclipse.debug.internal.ui.viewers.provisional.IPresentationContext)
	 */
	protected int getChildCount(Object element, IPresentationContext context,
			IViewerUpdate monitor) throws CoreException {
		if (context.getId().equals(IDebugUIConstants.ID_VARIABLE_VIEW)) {
			int count = ((BxThread) element).getVariableCount();
			return count== -1? ((BxThread) element).getVariables().length : count;
		}
		return super.getChildCount(element, context, monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.internal.ui.viewers.model.provisional.elements.ElementContentProvider#getChildren(java.lang.Object,
	 *      int, int,
	 *      org.eclipse.debug.internal.ui.viewers.provisional.IPresentationContext)
	 */
	protected Object[] getChildren(Object parent, int index, int length,
			IPresentationContext context, IViewerUpdate monitor)
			throws CoreException {
		if (context.getId().equals(IDebugUIConstants.ID_VARIABLE_VIEW)) {
			return getElements(((BxThread) parent).getVariables(), index,
					length);
		}
		return super.getChildren(parent, index, length, context, monitor);
	}

	protected boolean hasChildren(Object element, IPresentationContext context,
			IViewerUpdate monitor) throws CoreException {
		if (context.getId().equals(IDebugUIConstants.ID_VARIABLE_VIEW)) {
			return ((BxThread) element).hasVariables();
		}
		return super.hasChildren(element, context, monitor);
	}

}
