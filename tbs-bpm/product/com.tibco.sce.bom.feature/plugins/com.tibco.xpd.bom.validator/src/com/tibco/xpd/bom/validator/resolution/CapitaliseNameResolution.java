/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.validator.resolution;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Validation issue resolution. This remedies the non-capitalised name of aa
 * <code>NamedElement</code> object.
 * 
 * @author njpatel
 * 
 */
public class CapitaliseNameResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;

        if (editingDomain != null && target instanceof NamedElement) {
            NamedElement elem = (NamedElement) target;

            String name = elem.getName();

            if (name != null) {
                char firstChar = name.charAt(0);
                firstChar = Character.toUpperCase(firstChar);

                name = String.valueOf(firstChar) + name.substring(1);

                cmd = SetCommand.create(editingDomain, elem,
                        UMLPackage.eINSTANCE.getNamedElement_Name(), name);
            }
        }

        return cmd;
    }
}
