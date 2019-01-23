package com.tibco.bx.emulation.ui.actions;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.SelectionProviderAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.bx.emulation.model.EmulationElement;
import com.tibco.bx.emulation.model.NamedElement;
import com.tibco.bx.emulation.ui.Messages;
import com.tibco.bx.emulation.ui.util.EmulationUIUtil;
/*
 * to open the diagram editor and goto first selected EmulationElement
 */
public class GoToEObjectAction extends SelectionProviderAction {

	private NamedElement element;
	public GoToEObjectAction(ISelectionProvider selectionProvider) {
		super(selectionProvider, Messages.getString("GoToEObjectAction.label"));  //$NON-NLS-1$
		setToolTipText(Messages.getString("GoToEObjectAction.tooltip"));  //$NON-NLS-1$
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.ide", "icons/full/elcl16/gotoobj_tsk.gif")); //$NON-NLS-1$ //$NON-NLS-2$
		setDisabledImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui.ide", "icons/full/dlcl16/gotoobj_tsk.gif")); //$NON-NLS-1$ //$NON-NLS-2$
		//set help
		setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		IStructuredSelection selection= getStructuredSelection();
		if (selection.isEmpty()) {
			setEnabled(false);
			return;
		}
		if (element != null) {
			IEditorPart editor = EmulationUIUtil.openOriginalEditor(element);
			if(editor != null){
				EmulationUIUtil.goToEmulationElement(editor, element);
			}
			
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.SelectionProviderAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void selectionChanged(IStructuredSelection selection) {
		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			if (element instanceof EmulationElement) {
				this.element = (NamedElement)element;
				setEnabled(true);
				return;
			}
		} else {
			this.element = null;
		}
		setEnabled(false);
	}
	
}
