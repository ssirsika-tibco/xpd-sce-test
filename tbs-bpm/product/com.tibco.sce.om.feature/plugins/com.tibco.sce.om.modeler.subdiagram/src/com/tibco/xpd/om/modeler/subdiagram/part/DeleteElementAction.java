package com.tibco.xpd.om.modeler.subdiagram.part;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.actions.AbstractDeleteFromAction;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Organization;

/**
 * @generated
 */
public class DeleteElementAction extends AbstractDeleteFromAction {

	/**
	 * @generated
	 */
	public DeleteElementAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 * @generated
	 */
	public DeleteElementAction(IWorkbenchPage workbenchPage) {
		super(workbenchPage);
	}

	/**
	 * @generated
	 */
	public void init() {
		super.init();
		setId(ActionIds.ACTION_DELETE_FROM_MODEL);
		setText(DiagramUIMessages.DiagramEditor_Delete_from_Model);
		setToolTipText(DiagramUIMessages.DiagramEditor_Delete_from_ModelToolTip);
		ISharedImages workbenchImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setHoverImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setDisabledImageDescriptor(workbenchImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
	}

	/**
	 * @generated
	 */
	protected String getCommandLabel() {
		return DiagramUIMessages.DiagramEditor_Delete_from_Model;
	}

	/**
	 * @generated NOT
	 */
	protected Command getCommand(Request request) {
		List operationSet = getOperationSet();
		if (operationSet.isEmpty()) {
			return UnexecutableCommand.INSTANCE;
		}
		Iterator editParts = operationSet.iterator();
		CompositeTransactionalCommand command = new CompositeTransactionalCommand(
				getEditingDomain(), getCommandLabel());
		while (editParts.hasNext()) {
			EditPart editPart = (EditPart) editParts.next();
			Command curCommand = editPart.getCommand(request);
			if (curCommand != null) {
				command.compose(new CommandProxy(curCommand));
			}
		}
		if (command.isEmpty() || command.size() != operationSet.size()) {
			return UnexecutableCommand.INSTANCE;
		}

		if (!isCustomExecutable(request)) {
			// Returning Unexectable here will ensure that the
			// "Delete From Model" option is visible but disabled.
			return UnexecutableCommand.INSTANCE;
		}

		return new ICommandProxy(command);
	}

	/**
	 * 
	 * Custom code for extra check to determine whether the command is
	 * executable.
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean isCustomExecutable(Request request) {
		if (request instanceof EditCommandRequestWrapper) {
			EditCommandRequestWrapper wrapper = (EditCommandRequestWrapper) request;
			IEditCommandRequest editCommandRequest = wrapper
					.getEditCommandRequest();

			if (editCommandRequest instanceof DestroyElementRequest) {
				DestroyElementRequest req = (DestroyElementRequest) editCommandRequest;

				EObject elementToDestroy = req.getElementToDestroy();

				if ((elementToDestroy instanceof Organization)
						|| (elementToDestroy instanceof OrgModel)) {
					return false;
				}
			}
		}

		return true;
	}
}
