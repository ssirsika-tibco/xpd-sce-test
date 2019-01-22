/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.PilingInfo;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Quick-fix resolution to unselect piling in an Activity in a chained execution
 * embedded subprocess.
 * 
 * @author njpatel
 */
public class UndoPilingInChainResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        Command cmd = null;

        if (target instanceof Activity) {
            Object other =
                    Xpdl2ModelUtil
                            .getOtherElement((OtherElementsContainer) target,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ActivityResourcePatterns());
            // Set piling to false
            if (other instanceof ActivityResourcePatterns) {
                ActivityResourcePatterns patterns =
                        (ActivityResourcePatterns) other;
                PilingInfo piling = patterns.getPiling();
                if (piling != null && piling.isPilingAllowed()) {
                    cmd =
                            SetCommand.create(editingDomain,
                                    piling,
                                    XpdExtensionPackage.eINSTANCE
                                            .getPilingInfo_PilingAllowed(),
                                    false);
                }
            }
        }
        return cmd;
    }

}
