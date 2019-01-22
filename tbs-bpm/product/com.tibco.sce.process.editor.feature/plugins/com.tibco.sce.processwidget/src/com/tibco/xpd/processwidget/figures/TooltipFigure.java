/**
 * 
 */
package com.tibco.xpd.processwidget.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;

import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.processwidget.policies.HoverInfoEditPolicy.HoverProvider;
import com.tibco.xpd.quickfixtooltip.api.StickyTooltipFigure;

/**
 * Sid SNA-20. Use new StickyTooltipFigure to provide better visuals and allow
 * for clickable controls on tooltip, such as Doc URL hyperlinks.
 * 
 * @author wzurek
 * 
 */
public class TooltipFigure extends StickyTooltipFigure {

    public HoverDescriptionFigure fig;

    public HoverProvider provider;

    public TooltipFigure(HoverProvider provider) {
        super();
        this.provider = provider;
        fig = new HoverDescriptionFigure();
        setContentFigure(fig);
    }

    @Override
    public Dimension getPreferredSize(int wHint, int hHint) {
        /*
         * XPD-4911 - Sticky tooltips can have issues when mouse is over them
         * and hoverinfo-provider's edit part gets removed (usually they will
         * try to access the underlying model and that will cause issues.
         */
        if (provider instanceof EditPart
                && ((EditPart) provider).getParent() != null) {
            fig.setInfo(provider.getHoverInfo());
            return super.getPreferredSize(wHint, hHint);
        }

        return new Dimension(2, 2);

    }

    @Override
    public void paint(Graphics graphics) {
        /*
         * XPD-4911 - Sticky tooltips can have issues when mouse is over them
         * and hoverinfo-provider's edit part gets removed (usually they will
         * try to access the underlying model and that will cause issues.
         */
        if (provider instanceof EditPart
                && ((EditPart) provider).getParent() != null) {
            fig.setInfo(provider.getHoverInfo());
        } else {
            fig.setInfo(new HoverInfo("")); //$NON-NLS-1$
        }
        super.paint(graphics);
    }

}
