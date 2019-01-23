/**
 * 
 */
package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.internal.Messages;

/**
 * @author wzurek
 * 
 */
public class PaginationLayer extends FreeformLayer {

    private Dimension   pageSize;
    private Dimension   diagramSize = null;
    private double      printZoom = 1;
    private int         fitType = ProcessWidgetConstants.PRINT_FIT_ZOOM;
    private boolean     showPagination = false;
    
    public PaginationLayer() {
    }
    
    /**
     * Setup the printed page size for pagination grid lines
     * 
     * @param pageSize      This is the size of a page on selected printer in SCREEN pixels.
     */
    public void setPageSize(Dimension pageSize) {
        if (this.pageSize == null || 
                (this.pageSize.width != pageSize.width || this.pageSize.height != pageSize.height)) {
            this.pageSize = pageSize;
            
            if (showPagination) {
                repaint();
            }
        }
    }

    /**
     * Setup the diagram size
     * 
     * @param diagramSize   The extents of the process diagram (used when fitType = PRINT_FIT_PAGE/HORIZONTAL/VERTICAL
     */
    public void setDiagramSize(Dimension diagramSize) {
        if (this.diagramSize == null || 
                (this.diagramSize.width != diagramSize.width || this.diagramSize.height != diagramSize.height)) {
            this.diagramSize = diagramSize;

            if (showPagination) {
                repaint();
            }
        }
            
    }

    /**
     * Setup the print zoom level (used when fitType = PRINT_FIT_ZOOM)
     * 
     * @param printZoom     Zoom level to be applied for printing (1 = 1:1)
     */
    public void setPrintZoom(double printZoom) {
        if (this.printZoom != printZoom) {
            this.printZoom = printZoom;

            if (showPagination) {
                repaint();
            }
        }
    }
    
    /**
     * Set the print fit type
     * 
     * @param fitType   One of {@link ProcessWidgetContants}.PRINT_FIT_ values.
     */
    public void setFitType(int fitType) {
        if (this.fitType != fitType) {
            this.fitType = fitType;
            
            if (showPagination) {
                invalidate();
            }
        }
    }
    
    /**
     * Switch printed page border display on / off. 
     * @param on    true = display pagination according to last setupPagination() set parameters.
     */
    public void setShowPagination (boolean showPagination) {
        if (this.showPagination != showPagination) {
            this.showPagination = showPagination;
        
            repaint();
        }
    }

    /**
     * Is pagination currently set-up to display printed page borders etc 
     * @return
     */
    public boolean isPaginationShowing() {
        return (showPagination);
    }
    
    private Dimension getPageSizeFromFitType () {
        Dimension retSize = null;
        
        if (pageSize != null) {
        
            if (fitType == ProcessWidgetConstants.PRINT_FIT_ZOOM ) {
                // Just standard zoom print... (ProcessEditorConstants.FIT_ZOOM)
                double zoom = printZoom;
                if (zoom == 0) {
                    zoom = 1d;
                }
    
                retSize = new Dimension((int)(pageSize.width / zoom), (int)(pageSize.height / zoom));
            }
            else if (diagramSize != null) {
                
                // Fit to page, fit horizontal or fit vertical.
                double zoom = 1d;
                
                switch (fitType) {
                case ProcessWidgetConstants.PRINT_FIT_HORIZONTALLY:
                    zoom = (double)diagramSize.width / (double)pageSize.width;
                    break;
                
                case ProcessWidgetConstants.PRINT_FIT_VERTICALLY:
                    zoom = (double)diagramSize.height / (double)pageSize.height;
                    break;
                    
                case ProcessWidgetConstants.PRINT_FIT_TO_PAGE:
                    zoom = (double)diagramSize.width / (double)pageSize.width;
                    zoom = Math.max (zoom, ((double)diagramSize.height / (double)pageSize.height));
                    break;
                }
                
                retSize = new Dimension((int)(pageSize.width * zoom), (int)(pageSize.height * zoom));
            }
        }
        return (retSize);
    }
    
    protected void paintClientArea(Graphics gr) {

        if (showPagination) {
            
            // Get the real, on screen pixel size of pagination disp[lay pages from selected print fit type.
            Dimension fitSize = getPageSizeFromFitType();
            
            if (fitSize != null) {
                gr.pushState();
    
                int[] pattern = new int[] { 6,3 };
                gr.setLineDash(pattern);
    
                gr.setForegroundColor(ColorConstants.gray);
    
                Rectangle bnds = getBounds().getCopy();
                
                // If we have been given a proper diagram extent then use it
                // to set our display bounds (so that we 
                // only show pagination for the pages that will be printed.
                if (diagramSize != null) {
                    // Make sure we show whole pages.
                    if (diagramSize.width > fitSize.width) {
                        bnds.width = ((diagramSize.width / fitSize.width)+1) * fitSize.width;
                    }
                    else {
                        bnds.width = fitSize.width;
                    }
                    
                    if (diagramSize.height > fitSize.height) {
                        bnds.height = ((diagramSize.height / fitSize.height)+1) * fitSize.height;
                    }
                    else {
                        bnds.height = fitSize.height;
                    }
                }
    
                int y = 0, x = 0;
                while (y <= bnds.height) {
                    gr.drawLine(bnds.x, bnds.y + y, bnds.x + bnds.width, bnds.y + y);
                    y += fitSize.height;
                }
                while (x <= bnds.width) {
                    gr.drawLine(bnds.x + x, bnds.y, bnds.x + x, bnds.y + bnds.height);
                    x += fitSize.width;
                }
    
                gr.setAlpha(150);
                gr.setAntialias(SWT.ON);
                gr.setForegroundColor(ColorConstants.darkGray);
    
                int pageNo = 1;
                y = 0;
                gr.setLineStyle(SWT.LINE_SOLID);
                gr.setBackgroundColor(ColorConstants.lightGray);
                while (y < bnds.height) {
                    x = 0;
                    Rectangle rc = new Rectangle();
                    
                    while (x < bnds.width) {
                        String str = Messages.PaginationLayer_PageNumberPrefix_label + " " + (pageNo++); //$NON-NLS-1$
                        Dimension ext = FigureUtilities.getTextExtents(str, gr.getFont());
                        ext.expand(9,1);
                        
                        rc.setLocation(x, y);
                        rc.setSize(ext);
                        
                        gr.fillRoundRectangle (rc, 8, 8);
                        
                        ext.expand(-1, -1);
                        rc.setSize(ext);
                        gr.drawRoundRectangle(rc, 8, 8);
                        gr.drawString(str, x+4, y);
                        x += fitSize.width;
                    }
                    y += fitSize.height;
                }
                
                gr.setLineDash(new int[0]);
                gr.popState();
            }
        }

        return;
    }
    
}
