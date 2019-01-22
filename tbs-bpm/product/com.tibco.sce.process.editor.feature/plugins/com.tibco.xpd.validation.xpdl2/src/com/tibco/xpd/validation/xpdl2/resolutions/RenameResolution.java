/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.window.Window;

import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Resolution to rename an activity, data field or process.
 * 
 * @author nwilson
 */
public class RenameResolution extends AbstractWorkingCopyResolution {

    /*
     * Validation issues wishing to use this resolution can add this additional
     * info key for the default name.
     */
    public static final String ISSUE_DATA_KEY_SUGGESTED_NAME =
            "ISSUE_DATA_KEY_SUGGESTED_DATA_NAME"; //$NON-NLS-1$

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

            String oldName = Xpdl2ModelUtil.getDisplayNameOrName(named);
            String defaultName = getDefaultName(named, marker);

            String newName = rename(oldName, defaultName);

            if (newName.length() != 0 && !newName.equals(oldName)) {

                String newTokenName = NameUtil.getInternalName(newName, false);
                CompoundCommand cCmd = new CompoundCommand();
                cCmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                named,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                newName));
                cCmd.append(SetCommand.create(editingDomain,
                        named,
                        Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                        newTokenName));
                return cCmd;
            }
        }
        return null;
    }

    private String getDefaultName(NamedElement named, IMarker marker) {

        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {

            String defaultName =
                    addInfo.getProperty(ISSUE_DATA_KEY_SUGGESTED_NAME);
            if (defaultName != null && defaultName.length() > 0) {

                return defaultName;
            }
        }
        return Xpdl2ModelUtil.getDisplayNameOrName(named);
    }

    /**
     * @param name
     *            The current name.
     * @return The new name.
     */
    private String rename(String oldName, String defaultName) {

        RenameDialog dialog = new RenameDialog(null, oldName);
        dialog.setDefaultName(defaultName);
        dialog.setBlockOnOpen(true);
        int result = dialog.open();

        if (result == Window.OK) {

            return dialog.getName();
        } else {

            return ""; //$NON-NLS-1$
        }
    }

}
