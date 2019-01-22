/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.commands.LateExecuteRemoveCommand;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamDeleter;

/**
 * deletes local activity data fields from the unsupported activities
 * 
 * 
 * @author bharge
 * @since 3.3 (4 Aug 2010)
 */
public class DeleteLocalActivityDataFieldsResolution extends
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
        CompoundCommand cmd = new CompoundCommand();
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            for (DataField dataField : activity.getDataFields()) {

                /*
                 * XPD-5427: Use new Xpdl2FieldOrParamDeleter method that deals
                 * with all the objects that may contain references internally.
                 */
                Xpdl2FieldOrParamDeleter deleter =
                        new Xpdl2FieldOrParamDeleter();

                Command c =
                        deleter.getDeleteDataReferencesCommand(editingDomain,
                                dataField);

                if (c != null) {
                    cmd.append(c);
                }

                cmd.append(LateExecuteRemoveCommand.create(editingDomain,
                        dataField));
            }
        }
        return cmd;
    }

}
