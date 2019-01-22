package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.swt.graphics.Color;

/**
 * A custom Figure that extends RectangleFigure. Fills its gradient with the two
 * colors passed to the constructor.
 * 
 * @author rgreen
 * 
 */
public class GradientNodeFigure extends NodeFigure implements IGradientFigure {

    private Color gradColor1 = null;
    private Color gradColor2 = null;

    public void setGradientStart(Color gradColor1) {
        this.gradColor1 = gradColor1;
        this.invalidate();
    }

    public void setGradientEnd(Color gradColor2) {
        this.gradColor2 = gradColor2;
        this.invalidate();
    }

    public GradientNodeFigure() {
    }

    public GradientNodeFigure(Color grad1, Color grad2) {
        gradColor1 = grad1;
        gradColor2 = grad2;
    }

    @Override
    public void paintFigure(Graphics graphics) {

        if ((gradColor1 != null) && (gradColor2 != null)) {
            // Set to something so we don't crash
            graphics.setForegroundColor(gradColor1);
            graphics.setBackgroundColor(gradColor2);
        } else {
            // TODO: Set to preferences colors
            graphics.setForegroundColor(ColorConstants.lightGray);
            graphics.setBackgroundColor(ColorConstants.white);
        }
        // graphics.setBackgroundPattern(null);
        graphics.fillGradient(getBounds(), false);
        // outlineShape(graphics);
    }
}
