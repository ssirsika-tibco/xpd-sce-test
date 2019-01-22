/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.actions;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processwidget.ProcessWidget;

/**
 * @author wzurek
 * 
 */
public class ShowPrintPagesAction implements IEditorActionDelegate {

    private IEditorPart editor;

    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        editor = targetEditor;
        PrinterData[] pl = Printer.getPrinterList();
        action.setEnabled(editor != null && pl != null && pl.length > 0);
        if (editor != null) {
            IPreferenceStore store = Xpdl2ProcessEditorPlugin.getDefault()
                    .getPreferenceStore();
            boolean val = store
                    .getBoolean(ProcessEditorConstants.IS_SHOWING_PAGINATION);
            action.setChecked(val);
            ProcessWidget widget = (ProcessWidget) editor
                    .getAdapter(ProcessWidget.class);
            if (val) {
                updateWidget(widget);
                widget.showPrintMargins();
            } else {
                widget.hidePrintMargins();
            }
        }
    }

    static public void updateWidget(ProcessWidget widget) {
        IPreferenceStore store = Xpdl2ProcessEditorPlugin.getDefault()
                .getPreferenceStore();
        PrinterData defaultPrinter = Printer.getDefaultPrinterData();
        
        Printer pr = new Printer(defaultPrinter);
        Rectangle pageSize = pr.getClientArea();
        Point dpi = pr.getDPI();
        pr.dispose();
        Point screenDPI = Display.getCurrent().getDPI();

        double zoomX = screenDPI.x / (double) dpi.x;
        double zoomY = screenDPI.y / (double) dpi.y;

        
        Dimension screenPageSize = new Dimension ((int)(pageSize.width * zoomX), (int)(pageSize.height * zoomY));
        int fitType = store.getInt (ProcessEditorConstants.PRINT_PAGE_FIT_TYPE);
        double printZoom = store.getDouble(ProcessEditorConstants.PRINT_PAGE_ZOOM);
        
        widget.updatePrintMargins(screenPageSize, fitType, printZoom);
    }
    
    public void run(IAction action) {
        if (editor != null) {
            ProcessWidget widget = (ProcessWidget) editor
                    .getAdapter(ProcessWidget.class);

            IPreferenceStore store = Xpdl2ProcessEditorPlugin.getDefault()
                    .getPreferenceStore();
            boolean val = store
                    .getBoolean(ProcessEditorConstants.IS_SHOWING_PAGINATION);
            val = !val;
            store.setValue(ProcessEditorConstants.IS_SHOWING_PAGINATION, val);

            if (val) {
                updateWidget(widget);
                widget.showPrintMargins();
            } else {
                widget.hidePrintMargins();
            }
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        // do nothing
    }
}
