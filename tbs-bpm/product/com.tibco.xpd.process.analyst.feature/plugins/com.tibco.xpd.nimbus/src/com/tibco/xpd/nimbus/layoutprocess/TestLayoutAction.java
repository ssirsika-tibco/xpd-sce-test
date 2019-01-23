package com.tibco.xpd.nimbus.layoutprocess;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionDelegate;

import com.tibco.xpd.nimbus.layoutprocess.ImportNimbusProcessAutoLayout.LayoutException;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.viewer.EMFCommandWrapper;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;

public class TestLayoutAction extends ActionDelegate {

    boolean cmd_ok = false;

    ProcessWidget processWidgetImpl;

    @Override
    public void run(IAction action) {
        if (cmd_ok && processWidgetImpl != null) {
            final Process process = (Process) processWidgetImpl.getInput();

            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(process);

            RecordingCommand layoutCmd =
                    new RecordingCommand(
                            (TransactionalEditingDomain) editingDomain) {
                        @Override
                        protected void doExecute() {
                            ImportNimbusProcessAutoLayout autoLayout =
                                    new ImportNimbusProcessAutoLayout(process);

                            try {
                                autoLayout.layoutProcess();
                            } catch (LayoutException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };

            processWidgetImpl.getGraphicalViewer().getEditDomain()
                    .getCommandStack()
                    .execute(new EMFCommandWrapper(editingDomain, layoutCmd));

        }

        return;
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        cmd_ok = false;

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection sel = (IStructuredSelection) selection;
            if (sel.size() == 1) {
                if (sel.getFirstElement() instanceof GraphicalEditPart) {

                    RootEditPart rep =
                            ((GraphicalEditPart) sel.getFirstElement())
                                    .getViewer().getRootEditPart();

                    if (rep instanceof WidgetRootEditPart) {
                        processWidgetImpl =
                                (ProcessWidget) rep
                                        .getViewer()
                                        .getProperty(ProcessWidgetConstants.PROP_WIDGET);

                        cmd_ok = true;
                    }
                }
            }
        }

        // If everything is ok create the command to change the transactional
        // status of the selected subprocess activity(s)
        if (cmd_ok) {
            action.setEnabled(true);
        } else {
            action.setEnabled(false);
        }

    }

}