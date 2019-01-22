/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.print.PrintDiagramOperation;

/**
 * @author wzurek
 * 
 */
public class PrintAction extends Action {

    public PrintAction() {
        setText(Messages.PrintAction_Print_action);
        setToolTipText(Messages.PrintAction_PrintAction_tooltip);
    }

    /**
     * This method follows behaviour taken from Eclipse editors.
     * "When there is not printers installed, action is disabled"
     * 
     * @see org.eclipse.jface.action.Action#isEnabled()
     */
    @Override
    public boolean isEnabled() {
		PrinterData[] printerList= Printer.getPrinterList();
		return (printerList != null && printerList.length > 0);
    }
    
    public void run() {
        final PrintDialog dialog = new PrintDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell());

        final IEditorPart editor = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage().getActiveEditor();
        if (editor != null) {
            final GraphicalViewer viewer = (GraphicalViewer) editor
                    .getAdapter(GraphicalViewer.class);
            final PrinterData printerData = dialog.open();
            if (printerData != null) {

                IProgressService progressService = PlatformUI.getWorkbench()
                        .getProgressService();
                try {
                    progressService.runInUI(progressService,
                            new IRunnableWithProgress() {
                                public void run(IProgressMonitor monitor) {
                                    Printer printer = new Printer(printerData);
                                    
                                    PrintDiagramOperation op = new PrintDiagramOperation(
                                            printer, viewer);

                                    IPreferenceStore store = Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();

                                    op.setPrintMode(store.getInt(ProcessEditorConstants.PRINT_PAGE_FIT_TYPE));
                                    
                                    op.setZoom(store.getDouble(ProcessEditorConstants.PRINT_PAGE_ZOOM));
                                    
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
    }
}
