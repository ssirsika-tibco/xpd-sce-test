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

package com.tibco.xpd.processwidget.figures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Layout for containers that have only two or one children - the header and
 * contents or text. The haader child has to have set HEADER constrain and
 * content has to have set CONTENT constrain, and the text - TEXT constrain. <br>
 * The figure with this layout must not call the layout for minimal size. The
 * layout is used for {@see PoolFigure} and {@see LaneFigure}
 * 
 * @author wzurek
 */
public final class HeaderLayout extends AbstractLayout {

    private Dimension margin = new Dimension();

    private Dimension contentMargin = new Dimension();

    /** constrain for header figure */
    public static final String HEADER = "HEADER"; //$NON-NLS-1$

    /** constrain for content pane figure */
    public static final String CONTENTS = "CONTENTS"; //$NON-NLS-1$

    private Map constraints = new HashMap();

    private HeaderFigureStyle headerFigureStyle;

    public HeaderLayout(HeaderFigureStyle headerFigureStyle) {
        this.headerFigureStyle = headerFigureStyle;
    }

    protected Dimension calculatePreferredSize(IFigure container, int wHint,
            int hHint) {

        IFigure header = null;
        IFigure content = null;
        for (Iterator iter = container.getChildren().iterator(); iter.hasNext();) {
            IFigure fig = (IFigure) iter.next();
            if (HEADER.equals(getConstraint(fig))) {
                header = fig;
            } else if (CONTENTS.equals(getConstraint(fig))) {
                content = fig;
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (header == null && content == null) {
            throw new IllegalArgumentException();
        }

        if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
            Dimension ps1 =
                    header != null ? header.getPreferredSize().getCopy()
                            : new Dimension();
            Dimension ps2 =
                    content != null ? content.getPreferredSize().getCopy()
                            : new Dimension();

            ps2.expand(contentMargin.width + margin.width, margin.height
                    + contentMargin.height);

            Dimension minSize = container.getMinimumSize();

            int height;
            if (minSize.height < 10) {
                height = ps1.height;
            } else {
                height = Math.max((ps1.height + ps2.height), minSize.height);
            }

            return new Dimension(ps2.width, height);

        } else {
            Dimension ps1 =
                    header != null ? header.getPreferredSize().getCopy()
                            : new Dimension();
            Dimension ps2 =
                    content != null ? content.getPreferredSize().getCopy()
                            : new Dimension();

            ps2.expand(contentMargin.width + margin.width, margin.height
                    + contentMargin.height);

            Dimension minSize = container.getMinimumSize();

            int height;
            if (minSize.height < 10) {
                int min = Math.max(ps1.height, ps2.height);
                height = Math.max(min, minSize.height);
            } else {
                height = minSize.height;
            }
            return new Dimension(ps1.width + ps2.width, height);
        }
    }

    public void layout(IFigure container) {
        IFigure header = null;
        IFigure content = null;
        for (Iterator iter = container.getChildren().iterator(); iter.hasNext();) {
            IFigure fig = (IFigure) iter.next();
            if (HEADER.equals(getConstraint(fig))) {
                header = fig;
            } else if (CONTENTS.equals(getConstraint(fig))) {
                content = fig;
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (header == null && content == null) {
            throw new IllegalArgumentException();
        }

        // Rectangle b = container.getClientArea();
        Rectangle b = container.getBounds();

        if (header != null && content != null) {
            Dimension ps1 = header.getPreferredSize();

            if (HeaderFigureStyle.HORIZONTAL.equals(headerFigureStyle)) {
                header.setBounds(new Rectangle(b.x+1, b.y+1, b.width-1, ps1.height-1));
                content.setBounds(new Rectangle(b.x+1, b.y + ps1.height, b.width-1,
                        b.height - ps1.height));
            } else {
                header.setBounds(new Rectangle(b.x, b.y, ps1.width, b.height));
                content.setBounds(new Rectangle(b.x + ps1.width, b.y, b.width
                        - ps1.width, b.height));
            }

        } else if (content != null) {
            content.setBounds(b);
        } 
        
        return;
    }

    public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
        return container.getMinimumSize(wHint, hHint);
    }

    public void setConstraint(IFigure child, Object constraint) {
        if (constraint == null) {
            constraints.remove(child);
        } else {
            constraints.put(child, constraint);
        }
    }

    public Object getConstraint(IFigure child) {
        return constraints.get(child);
    }

    public void setMargin(Dimension margin) {
        this.margin = margin;
    }

    public void setContentMargin(Dimension margin) {
        this.contentMargin = margin;
    }
}