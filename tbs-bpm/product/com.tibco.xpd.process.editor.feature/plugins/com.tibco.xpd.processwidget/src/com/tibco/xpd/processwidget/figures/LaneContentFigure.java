package com.tibco.xpd.processwidget.figures;

/**
 * Lane content figure (just a rectangle that paints itself with a very faint alpha blend)
 * 
 * @author Sid
 */

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PrinterGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.processwidget.internal.Messages;

public class LaneContentFigure extends BaseLogExceptionFigure {

    private int laneAlpha = 10;

    private Color laneColor = null;

    private static final String BIG_FONT =
            "com.tibco.xpd.processwidget.bigfont"; //$NON-NLS-1$

    private static FontRegistry fontRegistry;

    static {
        if (PlatformUI.isWorkbenchRunning()) {
            PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
                @Override
                public void run() {
                    Font df = JFaceResources.getDialogFont();
                    FontData fds = df.getFontData()[0];

                    FontData bigFont = new FontData();
                    bigFont.setName(fds.getName());
                    bigFont.setLocale(fds.getLocale());
                    bigFont.setHeight(72);
                    bigFont.setStyle(SWT.BOLD);

                    fontRegistry = new FontRegistry();
                    fontRegistry.put(BIG_FONT, new FontData[] { bigFont });

                }
            });
        }
    }

    /**
     * 
     */
    public LaneContentFigure() {
        super();
    }

    @Override
    protected void paintFigure(Graphics gr) {

        // Don't colour lane content for printing.
        // (p.s. if you do try and setAlpha on a printer device then SWT
        // switches to
        // using a gdip object which works in a different co-ordinate space and
        // things go in completely the wrong place!!!).
        if (!(gr instanceof PrinterGraphics)) {
            gr.pushState();
            Rectangle ca = getClientArea().getCopy();
            if (!XPDFigureUtilities.isInTaskLibraryAlternateView(this)) {

                if (laneAlpha > 0) {
                    int aa = gr.getAntialias();
                    gr.setAntialias(SWT.ON);

                    int currAlpha = gr.getAlpha();

                    gr.setAlpha(laneAlpha);

                    if (laneColor != null) {
                        gr.setBackgroundColor(laneColor);
                    }

                    gr.fillRectangle(ca);

                    gr.setAntialias(aa);

                    gr.setAlpha(currAlpha);

                }
            }

            /*
             * XPD-1140: If process edit session is read only then display background [Read Only]
             * 
             * Sid ACE-2879 Read-only is now shown in the tab header consistently with other editors (which do not
             * water-mark the background) so changing process editor to not add the read-only watermark for now for
             * consistency).
             */
//            if (XPDFigureUtilities.isReadOnly(this)) {
//                gr.setTextAntialias(SWT.ON);
//                Font bigFont = fontRegistry.get(BIG_FONT);
//                gr.setFont(bigFont);
//                gr.setForegroundColor(ColorConstants.lightGray);
//                gr.setAlpha(Math.max(72 - laneAlpha, 50));
//                gr.drawText(Messages.LaneContentFigure_ReadOnly_label,
//                        ca.x,
//                        ca.y);
//            }

            gr.popState();
        }

        return;
    }

    /**
     * Set alpha blend for content lane (0 = completely transparent, 25 =
     * completely opaque)
     * 
     * @param laneAlpha
     */
    public void setLaneAlpha(int laneAlpha) {
        this.laneAlpha = laneAlpha;
    }

    /**
     * @param laneColor
     *            the laneColor to set
     */
    public void setLaneColor(Color laneColor) {
        this.laneColor = laneColor;
    }

}
