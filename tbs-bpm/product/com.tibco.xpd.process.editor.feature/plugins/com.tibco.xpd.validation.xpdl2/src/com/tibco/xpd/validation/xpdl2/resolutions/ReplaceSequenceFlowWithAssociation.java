package com.tibco.xpd.validation.xpdl2.resolutions;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * resolution to replace a sequence flow with association connection
 * 
 * @author bharge
 * 
 */
public class ReplaceSequenceFlowWithAssociation extends
        AbstractWorkingCopyResolution {

    /** replace sequence flow command name. */
    private static final String COMMAND =
            "replaceSequenceFlowWithAssociationCommand"; //$NON-NLS-1$

    /**
     * 
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        CompoundCommand command =
                new CompoundCommand(ResolutionMessages.getText(COMMAND));

        if (target instanceof Transition) {
            Transition transition = (Transition) target;
            FlowContainer container = transition.getFlowContainer();

            Activity sourceActivity =
                    container.getActivity(transition.getFrom());
            Activity targetActivity = container.getActivity(transition.getTo());

            /* delete the sequence flow */
            command.append(RemoveCommand.create(editingDomain,
                    container,
                    Xpdl2Package.eINSTANCE.getFlowContainer_Transitions(),
                    transition));

            Association association =
                    createAssociation(sourceActivity, targetActivity);

            /* add the association */
            command.append(AddCommand.create(editingDomain, sourceActivity
                    .getProcess().getPackage(), Xpdl2Package.eINSTANCE
                    .getPackage_Associations(), association));

            return command;

        }

        return null;
    }

    private Association createAssociation(Activity sourceActivity,
            Activity targetActivity) {

        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance(TaskObjectUtil
                        .getProcessType(sourceActivity.getProcess()))
                        .getGraphicalNodeColor(null,
                                ProcessWidgetColors.ASSOCIATION_LINE);

        return ElementsFactory.createAssociation(sourceActivity,
                targetActivity,
                null,
                null,
                null,
                lineColor.toString());

    }
}
