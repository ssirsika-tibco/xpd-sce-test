/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.modeler.diagram.edit.commands.custom;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart.ClassFigure;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage;
import com.tibco.xpd.bom.resources.ui.bomnotation.ShapeGradientStyle;

/**
 * Converts the colour of a uml class to one specified
 * 
 * @author patkinso
 * @since 19 Mar 2013
 */
public class ConvertClassHeaderColourCommand extends RecordingCommand {

    private View view = null;

    private RGB newColourRGB = null;
    
    private ClassFigure custFigure = null;

    public ConvertClassHeaderColourCommand(TransactionalEditingDomain domain,
            View view, RGB newColourRGB, ClassFigure custFigure) {
        super(domain);
        this.view = view;
        this.newColourRGB = newColourRGB;
        this.custFigure = custFigure;
    }

    @Override
    protected void doExecute() {
        if (view != null) {
            Color newColour = DiagramColorRegistry.getInstance().getColor(newColourRGB);
            custFigure.setGradientStart(newColour);
            
            ShapeGradientStyle gradStyle =
                    (ShapeGradientStyle) view
                            .getStyle(BomNotationPackage.Literals.SHAPE_GRADIENT_STYLE);
            gradStyle.setGradStartColor(FigureUtilities.RGBToInteger(newColour
                    .getRGB()));
            // Force a redraw of the class in the diagram 
            custFigure.repaint();
        }
    }
}
