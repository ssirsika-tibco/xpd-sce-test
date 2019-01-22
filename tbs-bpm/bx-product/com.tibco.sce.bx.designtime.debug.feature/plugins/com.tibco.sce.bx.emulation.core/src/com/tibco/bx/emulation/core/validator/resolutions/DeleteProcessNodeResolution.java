package com.tibco.bx.emulation.core.validator.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.emulation.model.ProcessNode;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

public class DeleteProcessNodeResolution extends AbstractWorkingCopyResolution{

	@Override
	protected Command getResolutionCommand(EditingDomain editingDomain, EObject eObject,
			IMarker marker) throws ResolutionException {
		Command command = null;
		if(editingDomain != null && eObject instanceof ProcessNode && eObject.eResource() != null){
			command = DeleteCommand.create(editingDomain, eObject);
		}
		return command;
	}

}
