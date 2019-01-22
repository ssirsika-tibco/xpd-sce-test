package com.tibco.bx.debug.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.handlers.HandlerUtil;

import com.tibco.bx.debug.core.models.BxBreakpoint;

public class BxBreakpointPropertiesHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (selection != null & selection instanceof IStructuredSelection) {
			final Object  firstElement = ((IStructuredSelection)selection).getFirstElement();
			if(firstElement instanceof BxBreakpoint){
				PropertyDialogAction action= 
					new PropertyDialogAction(HandlerUtil.getActiveWorkbenchWindow(
							event), new ISelectionProvider() {
						public void addSelectionChangedListener(ISelectionChangedListener listener) {
						}
						public ISelection getSelection() {
							return new StructuredSelection((BxBreakpoint)firstElement);
						}
						public void removeSelectionChangedListener(ISelectionChangedListener listener) {
						}
						public void setSelection(ISelection selection) {
						}
					});
				action.run();	
			}
		}
		return null;
	}

}
