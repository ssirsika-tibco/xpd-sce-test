/**
 * 
 */
package com.tibco.xpd.processwidget.print;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PrinterGraphics;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;

/**
 * @author wzurek
 * 
 */
public class PrintDiagramOperation {

    public final static int SCREEN_DPI = 72;

    private SWTGraphics g;

    private Color oldBGColor;

    private Printer printer;

    private GC printerGC; // Note: Only one GC instance should be created per

    // print job

    private PrinterGraphics printerGraphics;

    private Insets printMargin = new Insets(0, 0, 0, 0);

    private int printMode = ProcessWidgetConstants.PRINT_FIT_ZOOM;

    private IFigure printSource;

    private List selectedEditParts;

    private GraphicalViewer viewer;

    private double zoom = 1d;

    /**
     * Creates a new PrintDiagramOperation
     */
    private PrintDiagramOperation() {
    }

    /**
     * Creates a new PrintDiagramOperation on Printer p
     * 
     * @param p
     *            The printer to print on
     */
    /**
     * Constructor for PrintGraphicalViewerOperation
     * 
     * @param p
     *            The Printer to print to
     * @param g
     *            The viewer containing what is to be printed NOTE: The
     *            GraphicalViewer to be printed must have a
     *            {@link org.eclipse.draw2d.Layer Layer} with the {@link
     *            org.eclipse.gef.LayerConstants PRINTABLE_LAYERS} key.
     */
    public PrintDiagramOperation(Printer p, GraphicalViewer g) {
        setPrinter (p);
        viewer = g;
        LayerManager lm = (LayerManager) viewer.getEditPartRegistry().get(
                LayerManager.ID);
        IFigure f = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
        setPrintSource(f);

    }

    /**
     * Constructor for PrintFigureOperation.
     * 
     * @param p
     *            Printer to print on
     * @param srcFigure
     *            Figure to print
     */
    public PrintDiagramOperation(Printer p, IFigure srcFigure) {
        setPrintSource(srcFigure);
    }

    /**
     * Disposes the PrinterGraphics and GC objects associated with this
     * PrintDiagramOperation.
     */
    protected void cleanup() {
        if (g != null) {
            printerGraphics.dispose();
            g.dispose();
        }
        if (printerGC != null)
            printerGC.dispose();
    }

    /**
     * Returns a new PrinterGraphics setup for the Printer associated with this
     * PrintDiagramOperation.
     * 
     * @return PrinterGraphics The new PrinterGraphics
     */
    protected PrinterGraphics getFreshPrinterGraphics() {
        if (printerGraphics != null) {
            printerGraphics.dispose();
            g.dispose();
            printerGraphics = null;
            g = null;
        }
        g = new SWTGraphics(printerGC);
        printerGraphics = new PrinterGraphics(g, printer) {
            public void setAntialias(int value) {
                // super.setAntialias(value);
            }
        };
        setupGraphicsForPage(printerGraphics);
        return printerGraphics;
    }

    /**
     * @return SWT.RIGHT_TO_LEFT if the print source is mirrored;
     *         SWT.LEFT_TO_RIGHT otherwise
     * @see org.eclipse.draw2d.PrintOperation#getGraphicsOrientation()
     */
    int getGraphicsOrientation() {
        return getPrintSource().isMirrored() ? SWT.RIGHT_TO_LEFT
                : SWT.LEFT_TO_RIGHT;
    }

    /**
     * Returns the printer.
     * 
     * @return Printer
     */
    public Printer getPrinter() {
        return printer;
    }

    /**
     * @return the print mode
     */
    protected int getPrintMode() {
        return printMode;
    }

    /**
     * Returns a Rectangle that represents the region that can be printed to.
     * The x, y, height, and width values are using the printers coordinates.
     * 
     * @return the print region
     */
    public Rectangle getPrintRegion() {
        org.eclipse.swt.graphics.Rectangle trim = printer.computeTrim(0, 0, 0,
                0);
        org.eclipse.swt.graphics.Point printerDPI = printer.getDPI();
        Insets notAvailable = new Insets(-trim.y, -trim.x,
                trim.height + trim.y, trim.width + trim.x);
        Insets userPreferred = new Insets(
                (printMargin.top * printerDPI.x) / 72,
                (printMargin.left * printerDPI.x) / 72,
                (printMargin.bottom * printerDPI.x) / 72,
                (printMargin.right * printerDPI.x) / 72);
        Rectangle paperBounds = new Rectangle(printer.getBounds());
        Rectangle printRegion = paperBounds.getCropped(notAvailable);
        printRegion.intersect(paperBounds.getCropped(userPreferred));
        printRegion.translate(trim.x, trim.y);
        return printRegion;
    }

    /**
     * Returns the printSource.
     * 
     * @return IFigure The source IFigure
     */
    protected IFigure getPrintSource() {
        return printSource;
    }

    /**
     * Returns the viewer.
     * 
     * @return GraphicalViewer
     */
    public GraphicalViewer getViewer() {
        return viewer;
    }

    /**
     * @see org.eclipse.draw2d.PrintOperation#preparePrintSource()
     */
    protected void preparePrintSource() {
        oldBGColor = getPrintSource().getLocalBackgroundColor();
        getPrintSource().setBackgroundColor(ColorConstants.white);

        selectedEditParts = new ArrayList(viewer.getSelectedEditParts());
        viewer.deselectAll();
    }

    /**
     * Prints the pages based on the current print mode.
     * 
     * @see org.eclipse.draw2d.PrintOperation#printPages()
     */
    protected void printPages() {
        Graphics graphics = getFreshPrinterGraphics();
        IFigure figure = getPrintSource();
        setupPrinterGraphicsFor(graphics, figure);
        
        Rectangle bounds = getDiagramBounds(figure);

        int x = bounds.x, y = bounds.y;
        
        
        Rectangle clipRect = new Rectangle();
        int page = 1;
        PrinterData pd = printer.getPrinterData();
        while (y < bounds.y + bounds.height) {
            while (x < bounds.x + bounds.width) {
                graphics.getClip(clipRect);
                if (pd.scope != PrinterData.PAGE_RANGE
                        || (pd.startPage <= page && page <= pd.endPage)) {
                    graphics.pushState();
                    getPrinter().startPage();
                    graphics.translate(-x, -y);
                    clipRect.setLocation(x, y);
                    graphics.clipRect(clipRect);
                    figure.paint(graphics);
                    getPrinter().endPage();
                    graphics.popState();
                }
                x += clipRect.width;
                page++;
            }
            x = bounds.x;
            y += clipRect.height;
        }
    }

    /**
     * @see org.eclipse.draw2d.PrintOperation#restorePrintSource()
     */
    protected void restorePrintSource() {
        getPrintSource().setBackgroundColor(oldBGColor);
        oldBGColor = null;
        viewer.setSelection(new StructuredSelection(selectedEditParts));
    }

    /**
     * Sets the print job into motion.
     * 
     * @param jobName
     *            A String representing the name of the print job
     */
    public void run(String jobName) {
        preparePrintSource();
        if (printer.startJob(jobName)) {
            printerGC = new GC(getPrinter(), getGraphicsOrientation());
            printPages();
            printer.endJob();
        }
        restorePrintSource();
        cleanup();
    }

    /**
     * Sets the printer.
     * 
     * @param printer
     *            The printer to set
     */
    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    /**
     * Sets the page margin in pels (logical pixels) to the passed Insets.(72
     * pels == 1 inch)
     * 
     * @param margin
     *            The margin to set on the page
     */
    public void setPrintMargin(Insets margin) {
        printMargin = margin;
    }

    /**
     * @param mode
     *            the print mode
     */
    public void setPrintMode(int mode) {
        printMode = mode;
    }

    /**
     * Sets the printSource.
     * 
     * @param printSource
     *            The printSource to set
     */
    protected void setPrintSource(IFigure printSource) {
        this.printSource = printSource;
    }

    /**
     * Manipulates the PrinterGraphics to position it to paint in the desired
     * region of the page. (Default is the top left corner of the page).
     * 
     * @param pg
     *            The PrinterGraphics to setup
     */
    protected void setupGraphicsForPage(PrinterGraphics pg) {
        Rectangle printRegion = getPrintRegion();
        pg.clipRect(printRegion);
        pg.translate(printRegion.getTopLeft());
    }

    /**
     * Sets up Graphics object for the given IFigure.
     * 
     * @param graphics
     *            The Graphics to setup
     * @param figure
     *            The IFigure used to setup graphics
     */
    protected void setupPrinterGraphicsFor(Graphics graphics, IFigure figure) {
        double dpiScale = (double) getPrinter().getDPI().x
                / Display.getCurrent().getDPI().x;

        Rectangle printRegion = getPrintRegion();
        // put the print region in display coordinates
        printRegion.width /= dpiScale;
        printRegion.height /= dpiScale;

        // Use the diagram extent NOT the size of the editor viewport!
        Rectangle bounds = getDiagramBounds(figure); 
        
        
        double xScale = (double) printRegion.width / bounds.width;
        double yScale = (double) printRegion.height / bounds.height;
        switch (getPrintMode()) {
        case ProcessWidgetConstants.PRINT_FIT_ZOOM:
            graphics.scale(zoom * dpiScale);
            break;
        case ProcessWidgetConstants.PRINT_FIT_TO_PAGE:
            graphics.scale(Math.min(yScale, xScale) * dpiScale);
            break;
        case ProcessWidgetConstants.PRINT_FIT_VERTICALLY:
            graphics.scale(yScale * dpiScale);
            break;
        case ProcessWidgetConstants.PRINT_FIT_HORIZONTALLY:
            graphics.scale(xScale * dpiScale);
            break;
        default:
            graphics.scale(dpiScale);
        }
        graphics.setForegroundColor(figure.getForegroundColor());
        graphics.setBackgroundColor(figure.getBackgroundColor());
        graphics.setFont(figure.getFont());
    }

    /**
     * Sets the viewer.
     * 
     * @param viewer
     *            The viewer to set
     */
    public void setViewer(GraphicalViewer viewer) {
        this.viewer = viewer;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    private Rectangle getDiagramBounds(IFigure figPrint) {
        Rectangle bounds = figPrint.getBounds().getCopy();
        
        EditPart ep = viewer.getRootEditPart();
        if (ep instanceof WidgetRootEditPart) {
            WidgetRootEditPart root = (WidgetRootEditPart) ep;
            
            bounds.setSize(root.getDiagramExtent());
        }
        
        return (bounds);
    }
    
}
