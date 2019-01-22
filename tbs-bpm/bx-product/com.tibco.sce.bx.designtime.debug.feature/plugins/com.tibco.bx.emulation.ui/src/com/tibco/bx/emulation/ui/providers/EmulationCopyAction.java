package com.tibco.bx.emulation.ui.providers;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tibco.bx.emulation.ui.util.EmulationUIUtil;

public class EmulationCopyAction extends CopyAction{

	public EmulationCopyAction(TransactionalEditingDomain editingDomain) {
		super(editingDomain);
	}

	@Override
	public Command createCommand(Collection<?> selection) {
		if ((selection != null) && (!selection.isEmpty())) {
			
		}
		return super.createCommand(selection);
	}

	@Override
	public boolean updateSelection(IStructuredSelection selection) {
		List list = selection.toList();
		return EmulationUIUtil.canCopy(list) && super.updateSelection(selection);
	}
}
