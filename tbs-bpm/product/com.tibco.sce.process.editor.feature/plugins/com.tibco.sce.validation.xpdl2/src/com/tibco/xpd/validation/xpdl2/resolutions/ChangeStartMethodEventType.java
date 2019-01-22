/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.xpdl2.internal.Messages;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author rsomayaj
 * 
 * 
 */
public class ChangeStartMethodEventType extends AbstractWorkingCopyResolution {

	@Override
	protected Command getResolutionCommand(EditingDomain editingDomain,
			EObject target, IMarker marker) throws ResolutionException {
		if (target instanceof StartMethod) {
			SetCommand setCommand = (SetCommand) SetCommand.create(
					editingDomain, target, XpdExtensionPackage.eINSTANCE
							.getInterfaceMethod_Trigger(),
					TriggerType.MESSAGE_LITERAL);
			setCommand.setLabel(Messages.ChangeStartMethodEventType_ChangeStartMethodTriggerTypeCmd_label);
			return setCommand;
		}
		return null;
	}

}
