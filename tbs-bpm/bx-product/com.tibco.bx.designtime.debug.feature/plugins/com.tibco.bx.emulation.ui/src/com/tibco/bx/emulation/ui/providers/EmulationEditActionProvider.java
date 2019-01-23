package com.tibco.bx.emulation.ui.providers;

import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

import com.tibco.xpd.resources.XpdResourcesPlugin;

public class EmulationEditActionProvider extends CommonActionProvider {

	private CopyAction copyAction;
	private PasteAction pasteAction;
	private DeleteAction deleteAction;
	
	public EmulationEditActionProvider() {
	}

	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
		makeActions();
	}

	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup("group.edit", copyAction);//$NON-NLS-1$
		menu.appendToGroup("group.edit", pasteAction);//$NON-NLS-1$
		menu.appendToGroup("group.edit", deleteAction);//$NON-NLS-1$
	}

	public void fillActionBars(IActionBars actionBars) {
		IStructuredSelection selection = (IStructuredSelection) getContext()
				.getSelection();
		if (!isEnabled(((IStructuredSelection) (selection))))
			selection = StructuredSelection.EMPTY;
		copyAction.selectionChanged(((IStructuredSelection) (selection)));
		pasteAction.selectionChanged(((IStructuredSelection) (selection)));
		deleteAction.selectionChanged(((IStructuredSelection) (selection)));

		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
				copyAction);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
				pasteAction);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
				deleteAction);

	}

	private boolean isEnabled(IStructuredSelection selection) {
		/*boolean flag = true;
		if (selection != null && selection.size() > 1) {
			EObject eobject = (EObject) selection.getFirstElement();
			for (Iterator iterator = selection.iterator(); iterator.hasNext()
					&& flag;) {
				EObject eobject1 = (EObject) iterator.next();
				flag = eobject1.eClass() == eobject.eClass();
				if (flag)
					flag = eobject1.eContainer() == eobject.eContainer();
			}

		}
		return selection == null ? false : flag;*/
		return true;
	}

	private void makeActions() {
		final TransactionalEditingDomain transactionalEditingDomain = XpdResourcesPlugin
				.getDefault().getEditingDomain();
		ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
		copyAction = new EmulationCopyAction(transactionalEditingDomain);
		copyAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		copyAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.COPY);
		
		pasteAction = new EmulationPasteAction(transactionalEditingDomain) ;
		pasteAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		pasteAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.PASTE);
		deleteAction = new EmulationDeleteAction(transactionalEditingDomain);
		deleteAction.setImageDescriptor(images.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		deleteAction.setActionDefinitionId(IWorkbenchActionDefinitionIds.DELETE);
	}
	
}
