/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.dialogs;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.ScaledGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;

/**
 * @author wzurek
 * 
 */
public class PrintSetupDialog extends TitleAreaDialog implements Listener {

    // private static final int START = 0;
    //
    // private static final int CENTER = 1;
    //
    // private static final int END = 2;

    public static final int PRINT_ID = IDialogConstants.CLIENT_ID + 1;

    private GraphicalViewer viewer;

    private IDialogSettings dialogSettings;

    private Combo printerCombo;

    private PrinterData currentPrinter;

    private Combo scopeCombo;

    // SID DI:25382 - Eclipse Print properties/printerdata ignores # copies in print data
    //              As a temporary 'fix' don't show user num' copies control.
    // private Spinner copies;

    private Spinner startPage;

    private Spinner endPage;

    // private Button printToFileBtn;

    // private Text printToFileText;

    private boolean ignoreEvents;

    private Combo fitToPage;

    private Spinner zoomSpinner;

    // private Combo horizontalAlign;

    // private Combo verticalAlign;

    private Canvas previewCanvas;

    private Rectangle printerBounds;

    private Rectangle printableArea;

    private Point printerDPI;

    private Spinner pageNo;

    private int previewPage = 1;

    private int fitType = ProcessWidgetConstants.PRINT_FIT_ZOOM;

    private double zoom = 1d;

    public void init(GraphicalViewer viewer, IDialogSettings dialogSettings) {
        this.viewer = viewer;
        this.dialogSettings = dialogSettings;
    }

    public PrintSetupDialog(Shell parentShell) {
        super(parentShell);
    }

    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, PRINT_ID, Messages.PrintSetupDialog_Print_button, false);
        super.createButtonsForButtonBar(parent);
    }

    protected Control createDialogArea(Composite parent) {

        IPreferenceStore store = Xpdl2ProcessEditorPlugin.getDefault()
                .getPreferenceStore();
        fitType = store.getInt(ProcessEditorConstants.PRINT_PAGE_FIT_TYPE);
        zoom = store.getDouble(ProcessEditorConstants.PRINT_PAGE_ZOOM);

        Composite top = (Composite) super.createDialogArea(parent);

        Composite root = new Composite(top, SWT.NONE);
        root.setLayoutData(new GridData(GridData.FILL_BOTH));
        root.setLayout(new GridLayout(2, false));

        Control c;
        GridData data;
        c = createPrinterSection(root);
        data = new GridData(GridData.FILL_HORIZONTAL);
        c.setLayoutData(data);

        c = createPreviewSection(root);
        data = new GridData(GridData.FILL_BOTH);
        data.verticalSpan = 2;
        c.setLayoutData(data);

        c = createOptionsSection(root);
        data = new GridData(GridData.FILL_BOTH);
        c.setLayoutData(data);

        setTitle(Messages.PrintSetupDialog_SettingsGroup_label);
        setMessage(Messages.PrintSetupDialog_message);
        setTitleImage(ProcessWidgetPlugin.getDefault().getImageRegistry().get(
                ProcessWidgetConstants.IMG_PROCESS_WIZARD));
        this.getShell().setText(Messages.PrintSetupDialog_title);

        changePrinter(currentPrinter);

        return top;
    }

    protected void paintPreview(int pageNo, GC gc, Rectangle page) {
        if (viewer != null) {

            Point screenDPI = Display.getCurrent().getDPI();
            double pageSize = printerBounds.width / (double) printerDPI.x;
            double screenSize = page.width / (double) screenDPI.x;

            double baseZoom = screenSize / pageSize;

            double zoomX = screenDPI.x / (double) printerDPI.x;
            double zoomY = screenDPI.y / (double) printerDPI.y;

            double printableSizeX = printableArea.width * zoomX;
            double printableSizeY = printableArea.height * zoomY;

            Rectangle printableRegion = new Rectangle(page.x, page.y,
                    page.width, page.height);
            printableRegion.width = (int) (printableArea.width * baseZoom * zoomX);
            printableRegion.height = (int) (printableArea.height * baseZoom * zoomY);
            if (printableArea.x == 0 && printableArea.y == 0) {
                printableRegion.x += (page.width-printableRegion.width)/2;
                printableRegion.y += (page.width-printableRegion.width)/2;
            } else {
                printableRegion.x += printableArea.x * baseZoom;
                printableRegion.y += printableArea.y * baseZoom;
            }
            gc.setClipping(printableRegion);

            IFigure f = getFigure();
            
            org.eclipse.draw2d.geometry.Rectangle bnds = f.getBounds().getCopy();

            // Use diagram extent rather than viewport size (viewport is size of window if diagram is smaller than window).
            bnds.setSize(getDiagramExtent());

            double zoom = getZoom(bnds.getSize(), printableSizeX,
                    printableSizeY);

            int px = (int) Math.ceil((double) (bnds.width * zoom)
                    / printableSizeX);
            int py = (int) Math.ceil((double) (bnds.height * zoom)
                    / printableSizeY);
            if (pageNo >= px * py) {
                pageNo = 0;
            }
            int figXOffset;
            int figYOffset;
            if (pageNo == 0) {
                figXOffset = 0;
                figYOffset = 0;
            } else {
                int xPage = (pageNo) % px;
                int yPage = (pageNo) / px;
                figXOffset = (int) ((xPage) * printableSizeX/zoom);
                figYOffset = (int) ((yPage) * printableSizeY/zoom);
            }

            SWTGraphics swtGraph = new SWTGraphics(gc);
            swtGraph.translate(printableRegion.x, printableRegion.y);
            // swtGraph.translate((int) (page.x + xOffset * baseZoom),
            // (int) (page.y + yOffset * baseZoom));
            ScaledGraphics gr = new ScaledGraphics(swtGraph);
            gr.scale(zoom * baseZoom);
            gr.translate(-bnds.x - figXOffset, -bnds.y - figYOffset);
            gr.pushState();
            f.paint(gr);
            gr.popState();
            gr.dispose();
            swtGraph.dispose();
        }
    }

    private double getZoom(Dimension bnds, double width, double height) {
        double z = 1d;
        switch (fitType) {
        case ProcessWidgetConstants.PRINT_FIT_ZOOM:
            z = zoom;
            break;
        case ProcessWidgetConstants.PRINT_FIT_HORIZONTALLY:
            z = width / (double) bnds.width;
            break;
        case ProcessWidgetConstants.PRINT_FIT_VERTICALLY:
            z = height / (double) bnds.height;
            break;
        case ProcessWidgetConstants.PRINT_FIT_TO_PAGE:
            z = width / (double) bnds.width;
            z = Math.min(z, height / (double) bnds.height);
            break;
            
        default:
            z = 1;
        }
        return z;
    }

    private IFigure getFigure() {
        LayerManager lm = (LayerManager) viewer.getEditPartRegistry().get(
                LayerManager.ID);
        IFigure f = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
        return f;
    }
    
    private Dimension getDiagramExtent () {
        
        return ( ((WidgetRootEditPart)viewer.getRootEditPart()).getDiagramExtent() );
    }

    private Control createPreviewSection(Composite parent) {
        Group root = new Group(parent, SWT.NONE);
        root.setText(Messages.PrintSetupDialog_PreviewGroup_label);
        root.setLayout(new GridLayout(1, false));

        previewCanvas = new Canvas(root, SWT.BORDER);
        previewCanvas.setBackground(ColorConstants.white);
        previewCanvas.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                paintPages(e);
            }
        });

        GridData data = new GridData();
        data.heightHint = 300;
        data.widthHint = 300;
        data.horizontalAlignment = SWT.CENTER;
        data.verticalAlignment = SWT.CENTER;
        previewCanvas.setLayoutData(data);

        Composite pageNoRoot = new Composite(root, SWT.NONE);
        pageNoRoot.setLayout(new GridLayout(2, false));
        new Label(pageNoRoot, SWT.NONE).setText(Messages.PrintSetupDialog_PreviewPage_label);
        pageNo = new Spinner(pageNoRoot, SWT.BORDER);
        pageNo.addListener(SWT.Modify, this);
        pageNo.setMinimum(1);
        pageNo.setSelection(1);
        pageNo.setEnabled(viewer != null);
        return root;
    }

    private Control createOptionsSection(Composite parent) {
        GridData data;
        Group root = new Group(parent, SWT.NONE);
        root.setText(Messages.PrintSetupDialog_OptionsGroup_label);
        root.setLayout(new GridLayout(2, false));

        new Label(root, SWT.NONE).setText(Messages.PrintSetupDialog_FitToPage_label);
        fitToPage = new Combo(root, SWT.BORDER | SWT.READ_ONLY);
        fitToPage.setItems(new String[] { Messages.PrintSetupDialog_Zoom_action, Messages.PrintSetupDialog_FitHorz_action,
                Messages.PrintSetupDialog_FitVert_action, Messages.PrintSetupDialog_FitPage_action });
        fitToPage.select(fitType);
        data = new GridData();
        data.widthHint = 110;
        fitToPage.setLayoutData(data);
        fitToPage.addListener(SWT.Modify, this);

        new Label(root, SWT.NONE).setText(Messages.PrintSetupDialog_Zoom_label);
        zoomSpinner = new Spinner(root, SWT.BORDER);
        zoomSpinner.setMinimum(1);
        zoomSpinner.setMaximum(100000);
        zoomSpinner.setSelection((int) (zoom * 100));
        zoomSpinner.addListener(SWT.Modify, this);
        zoomSpinner.setEnabled(fitType == ProcessWidgetConstants.PRINT_FIT_ZOOM);

        // new Label(root, SWT.NONE).setText("Horizontal Align:");
        // horizontalAlign = new Combo(root, SWT.BORDER | SWT.READ_ONLY);
        // horizontalAlign.setItems(new String[] { "Left", "Center", "Right" });
        // horizontalAlign.select(0);
        // data = new GridData();
        // data.widthHint = 110;
        // horizontalAlign.setLayoutData(data);
        // horizontalAlign.addListener(SWT.Modify, this);

        // new Label(root, SWT.NONE).setText("Vertical Align:");
        // verticalAlign = new Combo(root, SWT.BORDER | SWT.READ_ONLY);
        // verticalAlign.setItems(new String[] { "Top", "Center", "Bottom" });
        // verticalAlign.select(0);
        // data = new GridData();
        // data.widthHint = 110;
        // verticalAlign.setLayoutData(data);
        // verticalAlign.addListener(SWT.Modify, this);

        return root;
    }

    private Control createPrinterSection(Composite parent) {

        Group root = new Group(parent, SWT.NONE);
        root.setText(Messages.PrintSetupDialog_PrinterGroup_title);
        root.setLayout(new GridLayout(2, false));

        new Label(root, SWT.NONE).setText(Messages.PrintSetupDialog_Printer_label);

        printerCombo = new Combo(root, SWT.READ_ONLY);

        PrinterData[] printerList = Printer.getPrinterList();
        currentPrinter = Printer.getDefaultPrinterData();

        int index = -1;
        String[] items = new String[printerList.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = printerList[i].name;
            if (printerList[i].name.equals(currentPrinter.name)) {
                index = i;
            }
        }
        printerCombo.setItems(items);
        printerCombo.select(index);
        printerCombo.addListener(SWT.Modify, this);

        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = 150;
        printerCombo.setLayoutData(data);

        Button properties = new Button(root, SWT.NONE);
        properties.setText(Messages.PrintSetupDialog_Properties_button);
        data = new GridData();
        data.horizontalSpan = 2;
        data.horizontalAlignment = SWT.END;
        properties.setLayoutData(data);
        properties.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                PrintDialog dial = new PrintDialog(getShell(), SWT.BORDER);
                dial.setScope(currentPrinter.scope);
                dial.setStartPage(currentPrinter.startPage);
                dial.setEndPage(currentPrinter.endPage);
                dial.setPrintToFile(currentPrinter.printToFile);
                PrinterData pd = dial.open();
                if (pd != null) {
                    changePrinter(pd);
                }
            }

            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        // SID DI:25382 - Eclipse Print properties/printerdata ignores # copies in print data
        //              As a temporary 'fix' don't show user num' copies control.
        /* new Label(root, SWT.NONE).setText("No. of Copies:");
        copies = new Spinner(root, SWT.BORDER);
        copies.setMinimum(1);
        copies.setSelection(1);
        copies.addListener(SWT.Modify, this);
        */

        new Label(root, SWT.NONE).setText(Messages.PrintSetupDialog_PrintScope_label);
        scopeCombo = new Combo(root, SWT.READ_ONLY);
        scopeCombo.setItems(new String[] { Messages.PrintSetupDialog_AllPages_action, Messages.PrintSetupDialog_PageRange_action,
                Messages.PrintSetupDialog_Selection_Action });
        scopeCombo.addListener(SWT.Modify, this);

        new Label(root, SWT.NONE).setText(Messages.PrintSetupDialog_StartPage_label);
        startPage = new Spinner(root, SWT.BORDER);
        startPage.setMinimum(1);
        startPage.addListener(SWT.Modify, this);
        new Label(root, SWT.NONE).setText(Messages.PrintSetupDialog_EndPage_label);
        endPage = new Spinner(root, SWT.BORDER);
        endPage.setMinimum(1);
        endPage.addListener(SWT.Modify, this);

        // printToFileBtn = new Button(root, SWT.CHECK);
        // printToFileBtn.setText("Print to file:");
        // printToFileBtn.addListener(SWT.Selection, this);
        // printToFileText = new Text(root, SWT.BORDER);
        // printToFileText.setEnabled(false);
        // printToFileText.addListener(SWT.Modify, this);

        return root;
    }

    protected void changePrinter(PrinterData pd) {
        try {
            ignoreEvents = true;

            int index = -1;
            PrinterData[] list = Printer.getPrinterList();
            for (int i = 0; i < list.length; i++) {
                if (list[i].name.equals(pd.name)) {
                    index = i;
                    break;
                }
            }
            printerCombo.select(index);

            boolean enabled = false;
            switch (pd.scope) {
            case PrinterData.ALL_PAGES:
                enabled = false;
                scopeCombo.select(0);
                break;
            case PrinterData.PAGE_RANGE:
                scopeCombo.select(1);
                enabled = true;
                break;
            case PrinterData.SELECTION:
                scopeCombo.select(2);
                enabled = false;
                break;
            }
            startPage.setEnabled(enabled);
            endPage.setEnabled(enabled);

            startPage.setSelection(pd.startPage);
            endPage.setSelection(pd.endPage);

            // SID DI:25382 - Eclipse Print properties/printerdata ignores # copies in print data
            //              As a temporary 'fix' don't show user num' copies control.
            // copies.setSelection(pd.copyCount);

            Printer printer = new Printer(pd);
            printerBounds = printer.getBounds();
            printableArea = printer.getClientArea();
            printerDPI = printer.getDPI();

            previewCanvas.redraw();

            currentPrinter = pd;
        } finally {
            ignoreEvents = false;
        }
    }

    public void handleEvent(Event event) {
        if (ignoreEvents) {
            return;
        }

        if (event.type == SWT.Modify) {
            if (event.widget == printerCombo) {
                changePrinter(Printer.getPrinterList()[printerCombo
                        .getSelectionIndex()]);
            } 
/*            else if (event.widget == copies) {
                currentPrinter.copyCount = copies.getSelection();
                // } else if (event.widget == horizontalAlign) {
                // previewCanvas.redraw();
            }
*/ 
            else if (event.widget == fitToPage) {
                fitType = fitToPage.getSelectionIndex();
                previewCanvas.redraw();
                zoomSpinner.setEnabled(fitType == ProcessWidgetConstants.PRINT_FIT_ZOOM);
            } else if (event.widget == pageNo) {
                previewPage = pageNo.getSelection();
                previewCanvas.redraw();
            } else if (event.widget == zoomSpinner) {
                zoom = zoomSpinner.getSelection() / 100d;
                previewCanvas.redraw();
                // } else if (event.widget == verticalAlign) {
                // previewCanvas.redraw();
            } else if (event.widget == scopeCombo) {
                switch (scopeCombo.getSelectionIndex()) {
                case 0:
                    currentPrinter.scope = PrinterData.ALL_PAGES;
                    break;
                case 1:
                    currentPrinter.scope = PrinterData.PAGE_RANGE;
                    break;
                case 2:
                    currentPrinter.scope = PrinterData.SELECTION;
                    break;
                }
                changePrinter(currentPrinter);
            } else if (event.widget == startPage) {
                currentPrinter.startPage = startPage.getSelection();
            } else if (event.widget == endPage) {
                currentPrinter.endPage = endPage.getSelection();
            }
        } else if (event.type == SWT.Selection) {
            // if (event.widget == this.printToFileBtn) {
            // boolean ptf = printToFileBtn.getSelection();
            // printToFileText.setEnabled(ptf);
            // currentPrinter.printToFile = ptf;
            // }
        }
    }

    public Printer getPrinter() {
        return new Printer(currentPrinter);
    }

    public int getFitType() {
        return fitType;
    }

    public double getZoom() {
        return zoom;
    }

    protected void paintPages(PaintEvent event) {
        Rectangle bnds = ((Control) event.widget).getBounds();

        double zoomV = (bnds.height - 40) / (double) printerBounds.height;
        double zoomH = (bnds.width - 20) / (double) printerBounds.width;
        double zoom = Math.min(zoomH, zoomV);

        int height = (int) (printerBounds.height * zoom);
        int width = (int) (printerBounds.width * zoom);

        int x = (bnds.width - width) / 2;
        int y = (bnds.height - height) / 2 - 10;

        event.gc.drawRectangle(x, y, width, height);
        event.gc.setForeground(ColorConstants.gray);
        event.gc.drawLine(x + width + 1, y + 2, x + width + 1, y + height + 1);
        event.gc.drawLine(x + 2, y + height + 1, x + width + 1, y + height + 1);
        event.gc.drawLine(x + width + 2, y + 2, x + width + 2, y + height + 2);
        event.gc.drawLine(x + 2, y + height + 2, x + width + 2, y + height + 2);

        Rectangle page = new Rectangle(x + 2, y + 2, width - 4, height - 4);

        if (viewer != null) {

            Point screenDPI = Display.getCurrent().getDPI();

            IFigure fig = getFigure();
            
            // Use diagram extent rather than size of viewport.
            Dimension size = getDiagramExtent();
            
            double zoomX = (double) printerDPI.x / (double) screenDPI.x;
            double zoomY = (double) printerDPI.y / (double) screenDPI.y;

            size.scale(zoomX, zoomY);

            double diagramZoom = getZoom(size, (int) (printableArea.width ),
                    (int) (printableArea.height ));

            System.out.println(diagramZoom);
            int px = (int) Math.ceil((double) ((int)(size.width * diagramZoom))
                    / printableArea.width);
            int py = (int) Math.ceil((double) (int)((size.height * diagramZoom))
                    / printableArea.height);

            if (px * py < previewPage) {
                previewPage = 1;
                pageNo.setSelection(previewPage);
            }

            String text = Messages.PrintSetupDialog_Page_nOFn_label + " " + (previewPage) + "/" + (px * py);  //$NON-NLS-1$//$NON-NLS-2$
            Point extend = event.gc.textExtent(text);
            event.gc.drawText(text, page.x + page.width / 2 - extend.x / 2,
                    page.y + page.height + 5);
        }
        paintPreview(previewPage - 1, event.gc, page);
    }

    protected void buttonPressed(int buttonId) {
        if (buttonId == PRINT_ID) {
            setReturnCode(buttonId);
            close();
            return;
        }
        super.buttonPressed(buttonId);
    }
}
