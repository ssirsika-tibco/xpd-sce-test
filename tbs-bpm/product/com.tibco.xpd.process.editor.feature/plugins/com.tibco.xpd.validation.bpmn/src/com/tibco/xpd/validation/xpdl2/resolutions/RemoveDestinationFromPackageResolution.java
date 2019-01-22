/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.validation.bpmn.rules.PackageDestSubsetOfProjectDestRule;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.validation.rules.ValidationUtil;
import com.tibco.xpd.validations.bpmn.internal.Messages;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * RemoveDestinationFromPackage
 * 
 * 
 * @author aallway
 * @since 3.3 (16 Oct 2009)
 */
public class RemoveDestinationFromPackageResolution extends
        AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target != null) {
            Package pkg = Xpdl2ModelUtil.getPackage(target);

            String badDestName = getBadDestinationName(marker);
            if (badDestName != null && badDestName.length() > 0) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.RemoveDestinationFromPackage_RemopveProcessDest_menu);

                for (Process process : pkg.getProcesses()) {
                    if (DestinationUtil.isGlobalDestinationEnabled(process,
                            badDestName)) {
                        cmd.append(DestinationUtil
                                .getSetDestinationEnabledCommand(editingDomain,
                                        process,
                                        badDestName,
                                        false));
                    }
                }

                ProcessInterfaces processInterfaces =
                        ProcessInterfaceUtil.getProcessInterfaces(pkg);
                if (processInterfaces != null) {
                    for (ProcessInterface processInterface : processInterfaces
                            .getProcessInterface()) {
                        if (DestinationUtil
                                .isGlobalDestinationEnabled(processInterface,
                                        badDestName)) {
                            cmd
                                    .append(DestinationUtil
                                            .getSetDestinationEnabledCommand(editingDomain,
                                                    processInterface,
                                                    badDestName,
                                                    false));
                        }
                    }
                }

                return cmd;
            }
        }

        return null;
    }

    @Override
    protected String getResolutionLabel(String propertiesLabel, IMarker marker) {
        String badDestName = getBadDestinationName(marker);
        if (badDestName != null && badDestName.length() > 0) {
            return String.format(propertiesLabel, badDestName);
        }
        return super.getResolutionLabel(propertiesLabel, marker);
    }

    @Override
    protected String getResolutionDescription(String propertiesDescription,
            IMarker marker) {
        String badDestName = getBadDestinationName(marker);
        if (badDestName != null && badDestName.length() > 0) {
            return String.format(propertiesDescription, badDestName);
        }
        return super.getResolutionLabel(propertiesDescription, marker);
    }

    /**
     * @param marker
     * @return
     */
    private String getBadDestinationName(IMarker marker) {
        Properties addInfo = ValidationUtil.getAdditionalInfo(marker);
        if (addInfo != null) {
            return addInfo
                    .getProperty(PackageDestSubsetOfProjectDestRule.BAD_PKGDEST_ADDINFOKEY);
        }
        return null;
    }

}
