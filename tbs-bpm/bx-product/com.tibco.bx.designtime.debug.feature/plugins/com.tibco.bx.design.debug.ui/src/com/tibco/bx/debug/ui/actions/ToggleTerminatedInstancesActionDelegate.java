package com.tibco.bx.debug.ui.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.Assert;
import org.eclipse.debug.internal.ui.views.launch.LaunchView;
import org.eclipse.debug.ui.AbstractDebugView;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.bx.debug.common.model.ProcessVisibleNode;
import com.tibco.bx.debug.core.DebugCoreActivator;
import com.tibco.bx.debug.core.models.IBxStackFrame;
import com.tibco.bx.debug.core.models.IBxThread;

public class ToggleTerminatedInstancesActionDelegate implements IViewActionDelegate,PropertyChangeListener {

	private TreeViewer fViewer;
	private LaunchView fView;
	private boolean isChecked = true;
	
	private ViewerFilter fViewerFilter = new ViewerFilter() {
		/**
		 * @see ViewerFilter#select(Viewer, Object, Object)
		 */
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (isChecked) {
				if (element instanceof IBxThread
						&& ((IBxThread) element).isTerminated()) {
					return false;
				} else if (element instanceof IBxStackFrame
						&& ((ProcessVisibleNode)((IBxStackFrame) element).getProcessElement()).isEnded()
						&& ((IBxStackFrame) element).getBreakWhen() == null) {
					return false;
				}
			}
			return true;
		}
	};
	
	public void init(IViewPart view) {
		Assert.isTrue(view instanceof AbstractDebugView);
	    fView = (LaunchView) view;
		fViewer = (TreeViewer) fView.getViewer();
		fViewer.addFilter(fViewerFilter);
		fViewer.refresh();
		fViewer.getTree().addDisposeListener(new DisposeListener(){
			public void widgetDisposed(DisposeEvent arg0) {
				DebugCoreActivator
						.removeCurrentStackFrameChangeListener(ToggleTerminatedInstancesActionDelegate.this);
			}
		});
		DebugCoreActivator.addCurrentStackFrameChangeListener(this);
	}

	public void run(IAction arg0) {
		isChecked = arg0.isChecked();
		fViewer.refresh();
	}

	public void selectionChanged(IAction arg0, ISelection arg1) {
	}

	public void propertyChange(PropertyChangeEvent evt) {
		synchronized (fViewer) {
			if (evt.getPropertyName().equals(
					DebugCoreActivator.CURRENT_STACKFRAME_P)) {
				final IBxStackFrame newValue = (IBxStackFrame) evt
						.getNewValue();
				if (newValue == null || (((ProcessVisibleNode)((IBxStackFrame) newValue).getProcessElement()).isEnded()
						&& newValue.getBreakWhen() == null)) {
					return;
				}
				fViewer.getTree().getDisplay().asyncExec(new Runnable() {
					public void run() {
						fViewer.refresh();
						// fViewer.expandAll();
						// fViewer.collapseAll();
						fViewer.expandToLevel(newValue.getThread(),TreeViewer.ALL_LEVELS);
						fViewer.setSelection(new StructuredSelection(new Object[] { newValue.getParent() }), true);
						fView.selectionChanged(new SelectionChangedEvent(fViewer, 
								new StructuredSelection(new Object[] { newValue })));
					}
				});
			}
		}
	}
}
