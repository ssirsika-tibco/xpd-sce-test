package com.tibco.xpd.simulation.ui.popup.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.simulation.SimulationConstants;
import com.tibco.xpd.simulation.preprocess.ProcessManager;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Action performing preparing for simulation preprocess action.
 * 
 * @author jarciuch
 */
public class PrepareSimulationAction extends
        BaseSelectionListenerAction implements IObjectActionDelegate {

    private static final String ID = SimulationUIPlugin.ID
            + ".PrepareSimulationAction"; //$NON-NLS-1$

    private ISelection currentSelection;

    /**
     * Constructor.
     */
    public PrepareSimulationAction() {
        super(Messages.PrepareSimulationAction_Title);
        setId(ID);
        setImageDescriptor(SimulationUIPlugin
                .getImageDescriptor("icons/Simulation 16 n p.gif"));//$NON-NLS-1$
    }

    /**
     * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }

    /**
     * @see IActionDelegate#run(IAction)
     */
    public void run(IAction action) {
        if (currentSelection instanceof StructuredSelection) {
            Object obj = ((StructuredSelection) currentSelection)
                    .getFirstElement();
            Process process = null;
            if (obj instanceof Process) {
                process = (Process) obj;
            } else if (obj instanceof IAdaptable) {
                process = (Process) ((IAdaptable) obj)
                        .getAdapter(EObject.class);
            }
            if (process == null) {
                return;
            }
            if (!DestinationUtil.isValidationDestinationEnabled(process,
                    SimulationConstants.SIMULATION_DEST_ENV_1_2_ID)) {
                MessageDialog
                        .openWarning(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                Messages.PrepareSimulationAction_WarningTitle,
                                Messages.PrepareSimulationAction_WarningText);
            }
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(process);
            if (wc != null) {
                ProcessManager.INSTANCE.generateSimData((Package) wc
                        .getRootElement(), wc.getEditingDomain());
            } else {
                throw new RuntimeException("Process has no working copy!"); //$NON-NLS-1$
            }
        }
    }

    @Override
    public void run() {
        run(this);
    }

    /**
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        currentSelection = selection;
    }

    protected boolean updateSelection(IStructuredSelection selection) {
        if (selection.size() == 1
                && selection.getFirstElement() instanceof Process) {
            currentSelection = selection;
            return true;
        }
        return false;
    }
}
