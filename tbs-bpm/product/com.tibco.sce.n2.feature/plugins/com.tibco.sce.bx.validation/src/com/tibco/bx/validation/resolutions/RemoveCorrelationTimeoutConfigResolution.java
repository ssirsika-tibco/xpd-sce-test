package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.IResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to remove the correlation timeout configuration from the activity
 * under focus.
 * 
 * 
 * @author kthombar
 * @since Dec 9, 2014
 */
public class RemoveCorrelationTimeoutConfigResolution extends
        AbstractWorkingCopyResolution implements IResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Activity) {

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.RemoveCorrelationTimeoutConfigResolution_RemoveCorrelationTimeoutCommand_label);
            /*
             * Remove the Correlation timeout config.
             */
            cmd.append(SetCommand.create(editingDomain,
                    target,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_CorrelationTimeout(),
                    SetCommand.UNSET_VALUE));

            return cmd;
        }
        return null;
    }
}
