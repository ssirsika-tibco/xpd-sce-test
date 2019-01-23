package com.tibco.xpd.xpdl2.commands;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;

public interface DeleteCommandWrapper {
    /**
     * 
     * @param originalCommand
     * @param domain
     * @param owner
     * @param feature
     * @param collection
     * @param index
     * @return
     */
    Command createDeleteCommand(Command originalCommand, EditingDomain domain,
            EObject owner, EStructuralFeature feature, Collection collection);
}
