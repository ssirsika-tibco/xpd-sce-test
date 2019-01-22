/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.gmf.extensions.hoverinfo;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Font;

import com.tibco.xpd.gmf.extensions.hoverinfo.HoverInfo.HoverInfoProperty;

/**
 * The actual tooltip figure, it is rendered inside diagram figure tooltip. 
 * 
 * @author wzurek
 */
public class HoverDescriptionFigure extends Figure  {

	/**
	 * Custom layout manager for the tooltip 
	 */
    class HoverLayoutManager implements LayoutManager {

        public Object getConstraint(IFigure child) {
            return null;
        }

        public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
            return getPreferredSize(container, wHint, hHint);
        }

        public Dimension getPreferredSize(IFigure container, int wHint, int hHint) {
            int maxWidth = wHint;
            int height = 0;
            if (info.getTitle() != null) {
                Dimension dim = FigureUtilities.getTextExtents(info.getTitle(),
                        titleFont);
                height++;
                height += dim.height + textMargin * 2;
                height++;
                maxWidth = Math.max(maxWidth, dim.width);
            }
            if (info.getText() != null) {
                Dimension dim = FigureUtilities.getTextExtents(info.getText(),
                        textFont);
                height++;
                height += dim.height + textMargin * 2;
                height++;
                maxWidth = Math.max(maxWidth, dim.width + textMargin * 2);
            }
            if (info.getProperties() != null && info.getProperties().size() > 0) {
                maxName = 0;
                maxValue = 0;

                for (int i = 0; i < info.getProperties().size(); i++) {
                    HoverInfoProperty p = info.getProperty(i);
                    Dimension d1 = FigureUtilities.getTextExtents(p.name,
                            propNameFont);
                    maxName = Math.max(maxName, d1.width);
                    Dimension d2 = FigureUtilities.getTextExtents(p.value,
                            propValueFont);
                    maxValue = Math.max(maxValue, d2.width);
                    height++;
                    height += Math.max(d1.height, d2.height);
                }
                maxWidth = Math.max(maxWidth, maxName + maxValue + textMargin * 3);
            }
            return new Dimension(maxWidth, height);
        }

        public void layout(IFigure container) {
            // do nothing...
        }
        

        public void invalidate() {
            // do nothing...
        }

        public void remove(IFigure child) {
            // do nothing...
        }

        public void setConstraint(IFigure child, Object constraint) {
            // do nothing...
        }
    }
    
    private HoverInfo info;

    private Font titleFont;

    private Font textFont;

    private Font propNameFont;

    private Font propValueFont;

    private int textMargin = 6;

    private int maxName;

    private int maxValue;

    public HoverDescriptionFigure() {
        setLayoutManager(new HoverLayoutManager());
        setBackgroundColor(ColorConstants.tooltipBackground);
        setForegroundColor(ColorConstants.tooltipForeground);
    }

    /**
     * Paint the round rectangle background
     */
    public void paintFigure(Graphics gr) {
        gr.pushState();
        try {
            Rectangle bnds = getClientArea().getCopy();
            bnds.width--;
            bnds.height--;
            gr.fillRectangle(bnds);
            // gr.drawRectangle(bnds);

            Dimension dim = new Dimension();
            int middle = bnds.x + bnds.width / 2;
            int top = bnds.y + 1;
            if (info.getTitle() != null) {
                FigureUtilities.getTextExtents(info.getTitle(), titleFont, dim);
                int left = middle - dim.width / 2;
                gr.setFont(titleFont);
                gr.drawText(info.getTitle(), left, top);
                top += dim.height + 1;
            }
            if (info.getText() != null) {
                if (info.getTitle() != null) {
                    gr.drawLine(bnds.x, top, bnds.x + bnds.width, top);
                    top += 2;
                }
                top += textMargin;
                gr.setFont(textFont);
                FigureUtilities.getTextExtents(info.getText(), textFont, dim);
                gr.drawText(info.getText(), bnds.x + textMargin, top);
                top+=dim.height+textMargin;

            }
            if (info.getProperties() != null && !info.getProperties().isEmpty()) {
                if (info.getTitle() != null || info.getText() != null) {
                    gr.drawLine(bnds.x, top, bnds.x + bnds.width, top);
                    top += 2;
                }
                Dimension d1 = new Dimension();
                Dimension d2 = new Dimension();
                middle = bounds.x + textMargin + maxName;
                for (int i = 0; i < info.getProperties().size(); i++) {
                    HoverInfoProperty p = info.getProperty(i);
                    FigureUtilities.getTextExtents(p.name, propNameFont, d1);
                    FigureUtilities.getTextExtents(p.value, propValueFont, d2);

                    gr.setFont(propNameFont);
                    gr.drawText(p.name, middle - d1.width, top);
                    gr.setFont(propValueFont);
                    gr.drawText(p.value, middle + textMargin, top);

                    top += 2 + Math.max(d1.height, d2.height);
                }
            }

        } finally {
            gr.popState();
        }
    }


    public void setPropNameFont(Font propNameFont) {
        this.propNameFont = propNameFont;
    }

    public void setPropValueFont(Font propValueFont) {
        this.propValueFont = propValueFont;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public void setInfo(HoverInfo hoverInfo) {
        info = hoverInfo;
    }
}
