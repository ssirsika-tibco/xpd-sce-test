package com.tibco.bx.validation.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.globalsignal.resource.ui.commonpicker.GsdTypeQuery;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Resolution to select the global signal from external GSD project.
 * 
 * 
 * @author kthombar
 * @since Feb 18, 2015
 */
public class ReselectGlobalSignalResolution extends
        AbstractWorkingCopyResolution {

    public ReselectGlobalSignalResolution() {

    }

    @Override
    protected Command getResolutionCommand(EditingDomain ed, EObject target,
            IMarker marker) throws ResolutionException {
        if (target instanceof Activity) {
            Activity activity = (Activity) target;
            /*
             * The query for the picker.
             */
            PickerTypeQuery[] queries =
                    new PickerTypeQuery[] { new GsdTypeQuery(
                            GsdTypeQuery.QUERY_GLOBAL_SIGNAL) };

            IFilter[] filters = new IFilter[] {};

            /*
             * get the active shell
             */
            Shell activeShell = Display.getCurrent().getActiveShell();

            /*
             * open picker.
             */
            Object result =
                    PickerService
                            .getInstance()
                            .openSinglePickerDialog(Messages.ReselectGlobalSignalResolution_GlobalSignalPicker_title,
                                    activeShell,
                                    queries,
                                    null,
                                    null,
                                    null,
                                    filters,
                                    null);

            if (result instanceof GlobalSignal) {

                if (ProcessUIUtil.checkAndAddProjectReference(activeShell,
                        activity,
                        (GlobalSignal) result)) {

                    /*
                     * get the global signal model name to set.
                     */
                    String globalSignalModelNameFromGlobalSignal =
                            GlobalSignalUtil
                                    .getGlobalSignalQualifiedName((GlobalSignal) result);

                    if (globalSignalModelNameFromGlobalSignal != null) {

                        CompoundCommand ccmd = new CompoundCommand();
                        ccmd.setLabel(Messages.ReselectGlobalSignalResolution_ReselectGlobalSignalCommand_label);

                        /*
                         * get the command to set the signal name
                         */
                        ccmd.append(EventObjectUtil.getSetSignalNameCommand(ed,
                                activity,
                                globalSignalModelNameFromGlobalSignal));

                        /*
                         * XPD-7549: We have now removed the code that cleared
                         * the mapping and scripts if the global signal was
                         * re-selected via quick fix as there are more chances
                         * that the user selects the same global signal(hence
                         * clearing mappings would mean that use has to do the
                         * same mappings again). Hence we keep the mapping and
                         * scripts as is and let user deal with any broken
                         * mappings due to Global Signal Reselection.
                         */
                        return ccmd;
                    }
                }
            }
        }
        return null;
    }
}
