/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * SelectEndpointParticipant
 * 
 * 
 * @author aallway
 * @since 3.3 (9 Oct 2009)
 */
public class SelectEndpointParticipantResolution extends AbstractWorkingCopyResolution {

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;

            ActivityMessageProvider messageProvider =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);
            if (messageProvider != null) {

                DataFilterPicker picker =
                        new DataFilterPicker(Display.getDefault()
                                .getActiveShell(), DataPickerType.PARTICIPANTS,
                                false);
                picker.setScope(activity);

                int ret = picker.open();
                if (ret == DataFilterPicker.OK) {

                    Object newPerfs = picker.getFirstResult();

                    if (newPerfs instanceof NamedElement) {
                        NamedElement particOrPerf = (NamedElement) newPerfs;

                        WebServiceOperation webServiceOperation =
                                messageProvider
                                        .getWebServiceOperation(activity);

                        CompoundCommand cmd = new CompoundCommand();
                        cmd.append(TaskObjectUtil
                                .getSetPerformersCommand(editingDomain,
                                        activity,
                                        new NamedElement[] { particOrPerf }));

                        cmd
                                .append(Xpdl2ModelUtil
                                        .getSetEndpointFromDataPickerSelectionCommand(editingDomain,
                                                particOrPerf,
                                                webServiceOperation));
                        return cmd;
                    }
                }
            }
        }

        return null;
    }
}
