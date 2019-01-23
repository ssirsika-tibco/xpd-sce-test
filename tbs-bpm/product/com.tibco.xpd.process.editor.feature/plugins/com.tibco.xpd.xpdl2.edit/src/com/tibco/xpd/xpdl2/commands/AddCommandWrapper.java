/**
 * 
 */
package com.tibco.xpd.xpdl2.commands;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author wzurek
 * 
 */
public interface AddCommandWrapper {

    Command createAddCommand(Command oryginalCommand, EditingDomain domain,
            EObject owner, EStructuralFeature feature, Collection collection,
            int index);
}
