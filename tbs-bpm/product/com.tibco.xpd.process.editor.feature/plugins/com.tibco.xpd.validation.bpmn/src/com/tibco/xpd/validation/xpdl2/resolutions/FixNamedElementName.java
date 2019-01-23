/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.RenameFieldOrParamCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class FixNamedElementName extends AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     *      getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     *            The editing domain.
     * @param target
     *            The target object.
     * @param marker
     *            The error marker.
     * @return The command to resolve the issue.
     * @throws ResolutionException
     *             If there was a problem generating the resolution.
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;
        if (target instanceof NamedElement) {
            NamedElement named = (NamedElement) target;
            String name = named.getName();
            boolean noLeadingNumerics =
                    named instanceof ProcessRelevantData
                            || named instanceof TypeDeclaration;
            String newName = NameUtil.getInternalName(name, noLeadingNumerics);
             if (Xpdl2ModelUtil.getDuplicateNameObject(named.eContainer(),
                    named,
                    newName) != null) {
                int i = 1;
                String suffixed = newName + i;
                while (Xpdl2ModelUtil
                        .getDuplicateNameObject(named.eContainer(),
                                named,
                                suffixed) != null) {
                    i++;
                    suffixed = newName + i;
                }
                newName = suffixed;
            }
            CompoundCommand command =
                    new CompoundCommand(Messages.FixNamedElementName_FixName);
            if (named instanceof ProcessRelevantData) {
                command.append(RenameFieldOrParamCommand.create(editingDomain,
                        (ProcessRelevantData) named,
                        named.eContainer(),
                        named.getName(),
                        newName,
                        false));
            } else {
                command.append(SetCommand.create(editingDomain,
                        named,
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        newName));
            }
            cmd = command;
        }
        return cmd;
    }

}
