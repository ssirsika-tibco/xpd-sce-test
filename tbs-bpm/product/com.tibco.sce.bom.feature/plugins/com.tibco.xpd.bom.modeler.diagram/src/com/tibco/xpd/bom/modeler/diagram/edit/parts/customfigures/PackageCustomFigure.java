/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.draw2d.ui.figures.RectangularDropShadowLineBorder;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class PackageCustomFigure extends NodeFigure implements IGradientFigure {

    /**
     * @generated NOT
     */
    private WrappingLabel packageNameLabel;
    private WrappingLabel stereoTypeLabel;
    private GridLayout layoutThis;
    
    private GridData gDataPackageNameFigure;
    private GridData gDataGradFigure;
    private GridData gDataStereoTypeLabel;
    
    
    /**
     * @generated NOT
     */
    private GradientNodeFigure gradFigure;

    /**
     * @generated NOT
     */
    public PackageCustomFigure() {

        layoutThis = new GridLayout();
        layoutThis.numColumns = 2;
        layoutThis.makeColumnsEqualWidth = true;
        layoutThis.horizontalSpacing = 0;
        layoutThis.verticalSpacing = -4;
        layoutThis.marginWidth = 0;
        layoutThis.marginHeight = 0;

        this.setLayoutManager(layoutThis);

        createContents();
    }

    /**
     * @generated NOT
     */
    private void createContents() {

        gradFigure = new GradientNodeFigure(new Color(Display
                .getCurrent(), new RGB(166, 202, 240)), ColorConstants.white);

        gDataGradFigure = new GridData();
        gDataGradFigure.verticalAlignment = GridData.CENTER;
        gDataGradFigure.horizontalAlignment = GridData.FILL;
        gDataGradFigure.horizontalIndent = 0;
        gDataGradFigure.horizontalSpan = 1;
        gDataGradFigure.verticalSpan = 1;
        gDataGradFigure.grabExcessHorizontalSpace = true;
        gDataGradFigure.grabExcessVerticalSpace = false;

        // So that we can have a drop shadow border we need to stack the
        // gradient figure on top of a plate (similar to the default behaviour
        // of GMF i.e. see EditPart methods CreateNodePlate(),
        // CreateNodeFigure()). This is so that the shadow is
        // applied to the background plate figure rather than the
        // gradient figure. Otherwise, the shadow will be drawn with the
        // foreground/background colors set by the gradient figure
        // and the border will not appear transparent.
        NodeFigure plateFig = new NodeFigure();
        plateFig.setLayoutManager(new StackLayout());
        plateFig.setBorder(new CustomRectangularDropShadowLineBorder(false,
                true));
        plateFig.add(gradFigure);
        this.add(plateFig, gDataGradFigure);

        GridLayout layoutGradFigure = new GridLayout();
        layoutGradFigure.numColumns = 1;
        layoutGradFigure.makeColumnsEqualWidth = true;        
        layoutGradFigure.marginHeight = 3;       
        gradFigure.setLayoutManager(layoutGradFigure);
          
        stereoTypeLabel = new WrappingLabel();

        gDataStereoTypeLabel = new GridData();
        gDataStereoTypeLabel.verticalAlignment = GridData.CENTER;
        gDataStereoTypeLabel.horizontalAlignment = GridData.CENTER;
        gDataStereoTypeLabel.horizontalIndent = 0;
        gDataStereoTypeLabel.horizontalSpan = 1;
        gDataStereoTypeLabel.verticalSpan = 1;
        gDataStereoTypeLabel.grabExcessHorizontalSpace = true;
        gDataStereoTypeLabel.grabExcessVerticalSpace = false;
//        gradFigure.add(stereoTypeLabel,
//                gDataStereoTypeLabel);
         
        packageNameLabel = new WrappingLabel();
        packageNameLabel.setText("<Package>"); //$NON-NLS-1$

        gDataPackageNameFigure = new GridData();
        gDataPackageNameFigure.verticalAlignment = GridData.CENTER;
        gDataPackageNameFigure.horizontalAlignment = GridData.CENTER;
        gDataPackageNameFigure.horizontalIndent = 0;
        gDataPackageNameFigure.horizontalSpan = 1;
        gDataPackageNameFigure.verticalSpan = 1;
        gDataPackageNameFigure.grabExcessHorizontalSpace = true;
        gDataPackageNameFigure.grabExcessVerticalSpace = false;
        gradFigure.add(packageNameLabel,
                gDataPackageNameFigure);

    }

    /**
     * @generated
     */
    private boolean myUseLocalCoordinates = false;

    /**
     * @generated
     */
    protected boolean useLocalCoordinates() {
        return myUseLocalCoordinates;
    }

    /**
     * @generated
     */
    protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
        myUseLocalCoordinates = useLocalCoordinates;
    }

    /**
     * @generated
     */
    public WrappingLabel getPackageNameLabel() {
        return packageNameLabel;
    }

    public WrappingLabel getStereoTypeLabel() {
        return stereoTypeLabel;
    }
    
    public void setGradientStart(Color gradColor1) {
        gradFigure.setGradientStart(gradColor1);
    }

    public void setGradientEnd(Color gradColor2) {
        gradFigure.setGradientEnd(gradColor2);
    }
    
    @SuppressWarnings("unchecked")
    public void rebuildFigureWithStereo(boolean showStereo) {
        // Get a list of the current children
        List<Object> prevChildren = new ArrayList<Object>();
        prevChildren.addAll(gradFigure.getChildren());

        // Remove the current children
        gradFigure.removeAll();
        
        // Now put them back
        if (showStereo) {            
            gradFigure.add(stereoTypeLabel, gDataStereoTypeLabel);
        }

        // Always add the label
        gradFigure.add(packageNameLabel);

    }
}
