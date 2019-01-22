package com.tibco.xpd.analyst.processinterface.editor.convertprocifc;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.analyst.processinterface.editor.inputs.ProcessInterfaceEditorInput;
import com.tibco.xpd.navigator.packageexplorer.editors.EditorInputFactory;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ProcessInterface;

/**
 * This command looks for and closes any open editor for the given process
 * interface. After closing the editor is re-opened.
 * <p>
 * This happens when a process interface is converted to a service process
 * interface and vice versa.
 * <p>
 * Simply append model change commands to this command as is normal for a
 * {@link CompoundCommand}
 * 
 * 
 * @author sajain
 * @since Apr 09, 2015
 */
public class CloseOpenProcessInterfaceEditorCommand extends CompoundCommand {

    private ProcessInterface procIfc;

    /**
     * Create command to close editor for process interface and re-open it after
     * the given command is executed (if it was open in the first place).
     * 
     * @param procIfc
     */
    public CloseOpenProcessInterfaceEditorCommand(ProcessInterface procIfc) {
        super("fred"); //$NON-NLS-1$
        this.procIfc = procIfc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.Command#execute()
     */
    @Override
    public void execute() {
        boolean wasOpen = closeEditor();

        super.execute();

        if (wasOpen) {
            if (WorkingCopyUtil.getFile(procIfc) != null) {
                openEditor();
            }
        }
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.CompoundCommand#undo()
     */
    @Override
    public void undo() {
        boolean wasOpen = closeEditor();

        super.undo();

        if (wasOpen) {
            if (WorkingCopyUtil.getFile(procIfc) != null) {
                openEditor();
            }
        }
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.emf.common.command.CompoundCommand#redo()
     */
    @Override
    public void redo() {
        boolean wasOpen = closeEditor();

        super.redo();

        if (wasOpen) {
            if (WorkingCopyUtil.getFile(procIfc) != null) {
                openEditor();
            }
        }
        return;
    }

    /**
     * CLose the editor for the given editor input.
     */
    private boolean closeEditor() {

        /*
         * Locate the appropriate editor.
         */
        IWorkbench workbench = PlatformUI.getWorkbench();

        if (workbench != null) {

            IWorkbenchWindow workbenchWindow =
                    workbench.getActiveWorkbenchWindow();

            if (workbenchWindow != null) {

                IWorkbenchPage activePage = workbenchWindow.getActivePage();

                if (activePage != null) {

                    IEditorReference[] editorReferences =
                            activePage.getEditorReferences();

                    for (IEditorReference editorReference : editorReferences) {

                        try {
                            IEditorInput editorInput =
                                    editorReference.getEditorInput();

                            if (editorInput instanceof ProcessInterfaceEditorInput) {

                                ProcessInterfaceEditorInput procIfcEditorInput =
                                        (ProcessInterfaceEditorInput) editorInput;

                                if (procIfc.equals(procIfcEditorInput
                                        .getProcessInterface())) {

                                    PlatformUI
                                            .getWorkbench()
                                            .getActiveWorkbenchWindow()
                                            .getActivePage()
                                            .closeEditor(editorReference.getEditor(true),
                                                    true);

                                    return true;
                                }
                            }
                        } catch (PartInitException e) {

                            Xpdl2ProcessEditorPlugin.getDefault().getLogger()
                                    .error(e);
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Open the editor for the given process .
     */
    private void openEditor() {

        /*
         * Open/activate the process editor
         */
        IConfigurationElement facConfig =
                XpdResourcesUIActivator.getEditorFactoryConfigFor(procIfc);

        if (facConfig != null) {

            String editorId = facConfig.getAttribute("editorID"); //$NON-NLS-1$
            EditorInputFactory f;

            try {

                f =
                        (EditorInputFactory) facConfig
                                .createExecutableExtension("factory"); //$NON-NLS-1$

                IEditorInput input = f.getEditorInputFor(procIfc);

                if (input != null) {

                    IWorkbenchPage page =
                            PlatformUI.getWorkbench()
                                    .getActiveWorkbenchWindow().getActivePage();

                    IDE.openEditor(page, input, editorId);
                }
            } catch (CoreException e) {

                e.printStackTrace();
            }
        }
        return;
    }

}