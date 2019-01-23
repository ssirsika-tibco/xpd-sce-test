package com.tibco.xpd.xpdl2.commands;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;

public interface SetCommandWrapper {

    Command createSetCommand(Command originalCommand, EditingDomain domain,
            EObject owner, EStructuralFeature feature, Object newValue, int index);
}
