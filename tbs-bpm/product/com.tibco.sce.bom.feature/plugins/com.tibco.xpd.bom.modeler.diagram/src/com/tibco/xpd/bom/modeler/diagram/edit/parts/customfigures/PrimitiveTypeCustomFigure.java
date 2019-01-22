/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class PrimitiveTypeCustomFigure extends NodeFigure implements
        IGradientFigure {
    /**
     * 
     */
    private WrappingLabel primTypeNameLabel;
    private WrappingLabel superTypeNameLabel;
    private WrappingLabel stereoTypeLabel;
    private GradientNodeFigure gradFigure;

    GridData gDataSuperTypeNameLabel;
    GridData gDataStereoTypeLabel;
    GridData gDataPrimTypeTypeNameLabel;

    /**
     * 
     */
    public PrimitiveTypeCustomFigure() {

        GridLayout layoutThis = new GridLayout();
        layoutThis.numColumns = 1;
        layoutThis.makeColumnsEqualWidth = true;
        this.setLayoutManager(layoutThis);
        layoutThis.horizontalSpacing = 0;
        layoutThis.verticalSpacing = 0;
        layoutThis.marginWidth = 0;
        layoutThis.marginHeight = 0;
        createContents();
    }

    /**
     * 
     */
    private void createContents() {

        // Base gradient node figure
        gradFigure = new GradientNodeFigure(ColorConstants.lightGray,
                ColorConstants.white);

        GridData gData = new GridData();
        gData.verticalAlignment = GridData.FILL;
        gData.horizontalAlignment = GridData.FILL;
        gData.horizontalIndent = 0;
        gData.horizontalSpan = 1;
        gData.verticalSpan = 1;
        gData.grabExcessHorizontalSpace = true;
        gData.grabExcessVerticalSpace = true;
        this.add(gradFigure, gData);

        GridLayout layoutGradFigure = new GridLayout();
        layoutGradFigure.numColumns = 1;
        layoutGradFigure.makeColumnsEqualWidth = true;
        layoutGradFigure.marginHeight = 5;
        layoutGradFigure.marginWidth = 15;
        layoutGradFigure.verticalSpacing = 2;

        gradFigure.setLayoutManager(layoutGradFigure);

        // SuperType label
        superTypeNameLabel = new WrappingLabel();
        superTypeNameLabel.setLayoutManager(BOMCustomFigureUtils
                .createBOMWrapLabelLayout());
        // superTypeNameLabel.setText("<Super Type>");

        gDataSuperTypeNameLabel = new GridData();
        gDataSuperTypeNameLabel.verticalAlignment = GridData.END;
        gDataSuperTypeNameLabel.horizontalAlignment = GridData.END;
        gDataSuperTypeNameLabel.horizontalIndent = 30;
        gDataSuperTypeNameLabel.horizontalSpan = 1;
        gDataSuperTypeNameLabel.verticalSpan = 1;
        gDataSuperTypeNameLabel.grabExcessHorizontalSpace = false;
        gDataSuperTypeNameLabel.grabExcessVerticalSpace = false;
        gradFigure.add(superTypeNameLabel, gDataSuperTypeNameLabel);

        // Stereotype label
        stereoTypeLabel = new WrappingLabel();
        // stereoTypeLabel.setLayoutManager(BOMCustomFigureUtils.
        // createBOMWrappingLabelLayout());
        // stereoTypeLabel.setText("<<stereotype>>");

        gDataStereoTypeLabel = new GridData();
        // gDataStereoTypeLabel.verticalAlignment = GridData.END;
        gDataStereoTypeLabel.horizontalAlignment = GridData.CENTER;
        gDataStereoTypeLabel.horizontalIndent = 0;
        gDataStereoTypeLabel.horizontalSpan = 1;
        gDataStereoTypeLabel.verticalSpan = 1;
        gDataStereoTypeLabel.grabExcessHorizontalSpace = true;
        gDataStereoTypeLabel.grabExcessVerticalSpace = false;
        // gradFigure.add(stereoTypeLabel, gDataStereoTypeLabel);

        // Name label
        primTypeNameLabel = new WrappingLabel();
        primTypeNameLabel.setText("<Primitive Type>"); //$NON-NLS-1$

        primTypeNameLabel.setLayoutManager(BOMCustomFigureUtils
                .createBOMWrapLabelLayout());

        gDataPrimTypeTypeNameLabel = new GridData();
        gDataPrimTypeTypeNameLabel.verticalAlignment = GridData.CENTER;
        gDataPrimTypeTypeNameLabel.horizontalAlignment = GridData.CENTER;
        gDataPrimTypeTypeNameLabel.horizontalIndent = 0;
        gDataPrimTypeTypeNameLabel.horizontalSpan = 1;
        gDataPrimTypeTypeNameLabel.verticalSpan = 1;
        gDataPrimTypeTypeNameLabel.grabExcessHorizontalSpace = true;
        gDataPrimTypeTypeNameLabel.grabExcessVerticalSpace = false;
        gradFigure.add(primTypeNameLabel, gDataPrimTypeTypeNameLabel);

    }

    /**
     * 
     */
    private boolean myUseLocalCoordinates = false;

    /**
     * 
     */
    protected boolean useLocalCoordinates() {
        return myUseLocalCoordinates;
    }

    /**
     * 
     */
    protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
        myUseLocalCoordinates = useLocalCoordinates;
    }

    /**
     * 
     */
    public WrappingLabel getFigurePrimitiveTypeNameLabel() {
        return primTypeNameLabel;
    }

    public WrappingLabel getFigurePrimTypeSuperTypeNameLabel() {
        return superTypeNameLabel;
    }

    public WrappingLabel getStereoTypeLabel() {
        return stereoTypeLabel;
    }

    public void setGradientStart(Color gradColor1) {
        gradFigure.setGradientStart(gradColor1);
        invalidate();
    }

    public void setGradientEnd(Color gradColor2) {
        gradFigure.setGradientEnd(gradColor2);
    }

    public void setSuperTypeName(String superTypeName) {
        // this.removeAll();
        superTypeNameLabel.setText(superTypeName);
        // add(gradFigure);
        // gradFigure.add(superTypeNameLabel);
        // gradFigure.add(primTypeNameLabel);
    }

    public void removeSuperTypeName() {
        // TODO: Rebuild the figure with supertype name set to ""
        // this.removeAll();
        superTypeNameLabel.setText(""); //$NON-NLS-1$
        // this.add(gradFigure);
    }

    public void setStereoType(String stereoTypeName) {
        stereoTypeLabel.setText("<<" + stereoTypeName + ">>"); //$NON-NLS-1$ //$NON-NLS-2$
        stereoTypeLabel.setLayoutManager(BOMCustomFigureUtils
                .createBOMWrapLabelLayout());
    }

    public void removeStereoType() {
        stereoTypeLabel.setText(""); //$NON-NLS-1$
    }

    @SuppressWarnings("unchecked")
    public void rebuildFigureWithSuperType(boolean showSuperType) {
        boolean showStereo = false;

        // Get a list of the current children
        List<Object> prevChildren = new ArrayList<Object>();
        prevChildren.addAll(gradFigure.getChildren());

        // Remove the current children
        gradFigure.removeAll();

        // Now put them back
        if (showSuperType) {
            gradFigure.add(superTypeNameLabel, gDataSuperTypeNameLabel);

        }

        if (prevChildren != null && prevChildren.contains(stereoTypeLabel)) {
            gradFigure.add(stereoTypeLabel);
            showStereo = true;
        }

        gradFigure.add(primTypeNameLabel, gDataPrimTypeTypeNameLabel);

    }

    @SuppressWarnings("unchecked")
    public void rebuildFigureWithStereo(boolean showStereo) {
        // Get a list of the current children
        List<Object> prevChildren = new ArrayList<Object>();
        prevChildren.addAll(gradFigure.getChildren());

        // Remove the current children
        gradFigure.removeAll();

        if (prevChildren != null && prevChildren.contains(superTypeNameLabel)) {
            gradFigure.add(superTypeNameLabel);
        }

        // Now put them back
        if (showStereo) {
            gradFigure.add(stereoTypeLabel, gDataStereoTypeLabel);
        }

        // Always add the label
        gradFigure.add(primTypeNameLabel);

    }

}
