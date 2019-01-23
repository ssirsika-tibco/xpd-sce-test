/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to popup dialog to rename the target xpdl NamedElement.
 * <p>
 * Unlike {@link RenameResolution}, this resolution leaves the label 'as is' -
 * just renames the Name.
 * 
 * 
 * @author aallway
 * @since 3.3 (16 Mar 2010)
 */
public class RenameNoLabelChangeResolution extends
        AbstractWorkingCopyResolution {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#
     * getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     * org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof NamedElement) {
            NamedElement named = (NamedElement) target;

            String oldName = named.getName();
            String defaultName = getDefaultName(named, marker);

            String oldNameDesc =
                    String.format("%s (%s)", oldName != null ? oldName : "<no name>", Xpdl2ModelUtil //$NON-NLS-1$ //$NON-NLS-2$
                                    .getDisplayNameOrName(named));

            String newName = rename(oldName, defaultName, oldNameDesc);

            if (newName.length() != 0 && !newName.equals(oldName)) {
                return SetCommand.create(editingDomain,
                        named,
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        newName);
            }
        }
        return null;
    }

    private String getDefaultName(NamedElement named, IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            String defaultName =
                    addInfo.getProperty(RenameResolution.ISSUE_DATA_KEY_SUGGESTED_NAME);
            if (defaultName != null && defaultName.length() > 0) {
                return defaultName;
            }
        }
        return named.getName();
    }

    /**
     * @param name
     *            The current name.
     * @return The new name.
     */
    private String rename(String oldName, String defaultName, String oldNameDesc) {
        RenameDialog dialog = new RenameDialog(null, oldName);
        dialog.setDefaultName(defaultName);
        dialog.setOldNameDescription(oldNameDesc);

        dialog.setBlockOnOpen(true);
        int result = dialog.open();
        if (result == Window.OK) {
            return dialog.getName();
        } else {
            return ""; //$NON-NLS-1$
        }
    }

}
