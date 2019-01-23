/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.tibco.xpd.bom.resources.ui.BOMImages;

public class ModelSummaryCustomFigure extends NodeFigure implements
        IGradientFigure {
    /**
     * 
     */
    private WrapLabel summaryNameLabel;

    private WrapLabel stereoTypeLabel;

    private GradientNodeFigure gradFigure;

    private GridData gDataGradFigure;

    private GridData gDataSummaryNameLabel;

    private GridData gDataStereoTypeLabel;

    /**
     * 
     */
    public ModelSummaryCustomFigure() {

        GridLayout layoutThis = new GridLayout();
        layoutThis.numColumns = 1;
        layoutThis.makeColumnsEqualWidth = true;
        this.setLayoutManager(layoutThis);
        layoutThis.horizontalSpacing = 5;
        layoutThis.verticalSpacing = 0;
        layoutThis.marginWidth = 0;
        layoutThis.marginHeight = 0;

        // setBorder(new RectangularDropShadowLineBorder());

        createContents();

        this.setOpaque(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.draw2d.Figure#paintBorder(org.eclipse.draw2d.Graphics)
     */
    @Override
    protected void paintBorder(Graphics gr) {
        // super.paintBorder(graphics);
        Rectangle b = getBounds().getResized(-1, -1);

        Point br = b.getBottomRight();
        Point p1 = br.getTranslated(-25, 0);
        Point p2 = br.getTranslated(0, -15);

        // clear the bottom right corner
        PointList pl = new PointList();
        pl.addPoint(p1.getTranslated(0, 1));
        pl.addPoint(p2.getTranslated(1, 0));
        pl.addPoint(br.getTranslated(1, 1));
        gr.fillPolygon(pl);

        // draw border
        gr.drawLine(b.getTopLeft(), b.getTopRight());
        gr.drawLine(b.getTopLeft(), b.getBottomLeft());

        gr.drawLine(b.getBottomLeft(), p1);
        gr.drawLine(b.getTopRight(), p2);
        gr.drawLine(p1, p2);
    }

    /**
     * 
     */
    private void createContents() {
        DiagramColorRegistry registry = DiagramColorRegistry.getInstance();
        // Base gradient node figure
        Color c1 = registry.getColor(new RGB(242, 250, 188));
        Color c2 = registry.getColor(new RGB(255, 255, 255));

        gradFigure = new GradientNodeFigure(c1, c2);
        gradFigure.setOpaque(false);
        gDataGradFigure = new GridData();
        gDataGradFigure.verticalAlignment = GridData.FILL;
        gDataGradFigure.horizontalAlignment = GridData.FILL;
        gDataGradFigure.horizontalIndent = 0;
        gDataGradFigure.horizontalSpan = 1;
        gDataGradFigure.verticalSpan = 1;
        gDataGradFigure.grabExcessHorizontalSpace = true;
        gDataGradFigure.grabExcessVerticalSpace = true;
        this.add(gradFigure, gDataGradFigure);

        GridLayout layoutGradFig = new GridLayout();
        layoutGradFig.numColumns = 1;
        layoutGradFig.makeColumnsEqualWidth = true;
        layoutGradFig.marginHeight = 5;
        layoutGradFig.verticalSpacing = 5;
        layoutGradFig.marginWidth = 5;

        gradFigure.setLayoutManager(layoutGradFig);

        // Stereotype/Title label
        stereoTypeLabel = new WrapLabel();
        stereoTypeLabel.setText(""); //$NON-NLS-1$
        stereoTypeLabel.setForegroundColor(ColorConstants.blue);

        gDataStereoTypeLabel = new GridData();
        gDataStereoTypeLabel.verticalAlignment = GridData.CENTER;
        gDataStereoTypeLabel.horizontalAlignment = GridData.CENTER;
        gDataStereoTypeLabel.horizontalIndent = 0;
        gDataStereoTypeLabel.horizontalSpan = 1;
        gDataStereoTypeLabel.verticalSpan = 1;
        gDataStereoTypeLabel.grabExcessHorizontalSpace = true;
        gDataStereoTypeLabel.grabExcessVerticalSpace = false;
        gradFigure.add(stereoTypeLabel, gDataStereoTypeLabel);

        // Model Name label
        summaryNameLabel = new WrapLabel();
        summaryNameLabel.setTextWrapAlignment(PositionConstants.CENTER);
        summaryNameLabel.setText("Model Summary: Name"); //$NON-NLS-1$
        // summaryNameLabel.setIcon(UMLElementTypes
        // .getImageDescriptor(UMLElementTypes.Package_1001).createImage(), 1);
        summaryNameLabel.setIcon(com.tibco.xpd.bom.resources.ui.Activator
                .getDefault().getImageRegistry().get(BOMImages.PACKAGE_32), 1);
        summaryNameLabel.setTextPlacement(PositionConstants.WEST);
        summaryNameLabel.setForegroundColor(ColorConstants.blue);

        gDataSummaryNameLabel = new GridData();
        gDataSummaryNameLabel.verticalAlignment = GridData.CENTER;
        gDataSummaryNameLabel.horizontalAlignment = GridData.END;
        gDataSummaryNameLabel.horizontalIndent = 0;
        gDataSummaryNameLabel.horizontalSpan = 1;
        gDataSummaryNameLabel.verticalSpan = 1;
        gDataSummaryNameLabel.grabExcessHorizontalSpace = false;
        gDataSummaryNameLabel.grabExcessVerticalSpace = false;
        this.add(summaryNameLabel, gDataSummaryNameLabel);

    }

    /**
     * 
     */
    private boolean myUseLocalCoordinates = false;

    /**
     * 
     */
    @Override
    protected boolean useLocalCoordinates() {
        return myUseLocalCoordinates;
    }

    /**
     * 
     */
    protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
        myUseLocalCoordinates = useLocalCoordinates;
    }

    public void setModelSummaryName(String name) {
        summaryNameLabel.setText(name);
    }

    public String getModelSummaryName() {
        return summaryNameLabel.getText();
    }

    public void setModelSummaryIcon(Image img) {
        summaryNameLabel.setIcon(img, 1);
    }

    public String getModelSummaryIcon() {
        return summaryNameLabel.getText();
    }

    public void setStereotypeLabel(String name) {
        stereoTypeLabel.setText(name);
    }

    public String getStereotypeName() {
        return stereoTypeLabel.getText();
    }

    public WrapLabel getStereotypeLabel() {
        return stereoTypeLabel;
    }

    @Override
    public void setGradientStart(Color gradColor1) {
        gradFigure.setGradientStart(gradColor1);
    }

    @Override
    public void setGradientEnd(Color gradColor2) {
        gradFigure.setGradientEnd(gradColor2);
    }
}
