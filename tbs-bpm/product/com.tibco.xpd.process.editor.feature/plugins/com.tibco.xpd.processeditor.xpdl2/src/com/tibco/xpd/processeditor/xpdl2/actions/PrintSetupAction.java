/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.printing.Printer;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.dialogs.PrintSetupDialog;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.print.PrintDiagramOperation;

/**
 * @author wzurek
 * 
 */
public class PrintSetupAction implements IEditorActionDelegate {

    private IEditorPart editor;

    public void run(IAction action) {
        final PrintSetupDialog dialog = new PrintSetupDialog(PlatformUI
                .getWorkbench().getActiveWorkbenchWindow().getShell());

        final GraphicalViewer viewer;
        if (editor != null) {
            viewer = (GraphicalViewer) editor.getAdapter(GraphicalViewer.class);
        } else {
            viewer = null;
        }
        dialog.init(viewer, null);

        IPreferenceStore store = Xpdl2ProcessEditorPlugin.getDefault()
                .getPreferenceStore();

        int result = dialog.open();
        if (result == Window.OK || result == PrintSetupDialog.PRINT_ID) {
            // save values in preferences
            store.setValue(ProcessEditorConstants.PRINT_PAGE_FIT_TYPE, dialog
                    .getFitType());
            store.setValue(ProcessEditorConstants.PRINT_PAGE_ZOOM, dialog
                    .getZoom());
            if (viewer!=null) {
                if (editor instanceof AbstractProcessDiagramEditor) {
                    ProcessWidget widget = (ProcessWidget) editor.getAdapter(ProcessWidget.class);
                    if (widget!=null ){
                        ShowPrintPagesAction.updateWidget(widget);
                    }
                }
            }
        }
        
        if (result == PrintSetupDialog.PRINT_ID && viewer != null) {

            IProgressService progressService = PlatformUI.getWorkbench()
                    .getProgressService();
            try {
                progressService.runInUI(progressService,
                        new IRunnableWithProgress() {
                            public void run(IProgressMonitor monitor) {
                                Printer printer = dialog.getPrinter();
                                PrintDiagramOperation op = new PrintDiagramOperation(
                                        printer, viewer);
                                
                                op.setZoom(dialog.getZoom());
                                op.setPrintMode(dialog.getFitType());
                                
                                op.run(editor.getTitle());
                                printer.dispose();
                            }
                        }, null);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        action.setEnabled(targetEditor != null);
        editor = targetEditor;
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }
}
