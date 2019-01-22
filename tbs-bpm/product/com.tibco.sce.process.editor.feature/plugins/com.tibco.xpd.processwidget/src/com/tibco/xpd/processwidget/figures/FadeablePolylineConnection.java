/**
 * 
 */
package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.swt.SWT;

import com.tibco.xpd.processwidget.neatstuff.IFadeableFigure;

/**
 * 
 * @author aallway
 * @since
 */
public class FadeablePolylineConnection extends PolylineConnection implements
        IFadeableFigure {
    private int alpha = 255;

    @Override
    public Integer getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != this.alpha) {
            this.alpha = alpha;
            revalidate();
            repaint();
        }
    }

    @Override
    public void paint(Graphics graphics) {
        int aa = graphics.getAntialias();
        graphics.setAntialias(SWT.ON);

        int oldAlpha = graphics.getAlpha();
        graphics.setAlpha(alpha);
        super.paint(graphics);

        graphics.setAlpha(oldAlpha);
        graphics.setAntialias(aa);
    }

}
