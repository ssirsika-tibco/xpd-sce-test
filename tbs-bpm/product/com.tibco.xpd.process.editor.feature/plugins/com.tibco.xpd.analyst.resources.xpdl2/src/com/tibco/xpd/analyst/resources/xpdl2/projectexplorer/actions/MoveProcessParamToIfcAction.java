/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.Iterator;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.actions.SelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Move process parameter to a process interface
 * 
 * <p>
 * Sid XPD-8381: Converted to extend SelectionListenerAction not WorkspaceAction
 * (the latter has certain requirements of a selection, like it must be able to
 * relate to a resource properly etc which
 * org.eclipse.bpel.validator.factory.AdapterFactory spoils because it fails to
 * do so). Anyway, it's not a workspace resource change so should not have been
 * a WorkspaceAction in the first place.
 * 
 * Sid XPD-8381: This class changed for completeness. NOTE THAT THIS Refactor
 * action was not enabled previously in any way, it's refactor provider
 * (RefactorProcessParamActionProvider) was contributed with an enabledment
 * based on a test property canRefactorParam that is not handled ANYWHERE.
 * Therefore, this change is just to ensure that if we do re-enable it sometime
 * we won't get the same issue as found in XPD-8381.
 *
 * @author rsomayaj
 */
public class MoveProcessParamToIfcAction extends SelectionListenerAction {

    private static final Logger LOG =
            XpdResourcesPlugin.getDefault().getLogger();

    public MoveProcessParamToIfcAction(String text) {
        super(text);
        // Ravi-set image descriptor after we get an image.
    }

    /**
     * @see org.eclipse.ui.actions.WorkspaceAction#run()
     * 
     */
    @Override
    public void run() {
        Iterator iterator = getStructuredSelection().iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj instanceof FormalParameter) {
                FormalParameter formalParameter = (FormalParameter) obj;
                moveParamToInterface(formalParameter);
            }
        }
    }

    private void moveParamToInterface(FormalParameter param) {
        EditingDomain ed = WorkingCopyUtil.getEditingDomain(param.eContainer());

        if (param.eContainer() instanceof Process) {
            Process process = (Process) param.eContainer();
            ProcessInterface procIfc = ProcessInterfaceUtil
                    .getImplementedProcessInterface(process);
            if (procIfc != null) {
                CompoundCommand cmd = new CompoundCommand(
                        Messages.MoveProcessParamToIfcAction_MoveParamToIfc_shortdesc);
                // MR 40002 - begin
                cmd.append(SetCommand.create(ed,
                        param,
                        Xpdl2Package.eINSTANCE.getFormalParameter_Required(),
                        true));
                // MR 40002 - end
                cmd.append(RemoveCommand.create(ed, param));
                cmd.append(AddCommand.create(ed,
                        procIfc,
                        Xpdl2Package.eINSTANCE
                                .getFormalParametersContainer_FormalParameters(),
                        param));

                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);

                } else {
                    LOG.error(
                            "Move Parameter to Interface action doesn't work"); //$NON-NLS-1$
                }
                cmd.dispose();
            }
        }
    }
}
