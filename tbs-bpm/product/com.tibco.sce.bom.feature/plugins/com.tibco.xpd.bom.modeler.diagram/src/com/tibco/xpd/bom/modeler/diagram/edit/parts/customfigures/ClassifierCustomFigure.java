/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts.customfigures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.swt.graphics.Color;

import com.tibco.xpd.bom.modeler.diagram.part.Messages;

public class ClassifierCustomFigure extends NodeFigure implements IGradientFigure {
    /**
     * 
     */
    private WrappingLabel classifierNameLabel;
    private WrappingLabel superClassNameLabel;
    private WrappingLabel stereoTypeLabel;
    private GradientNodeFigure gradFigure;

    private GridData gDataGradFigure;
    private GridData gDataClassNameLabel;
    private GridData gDataSuperClassNameLabel;
    private GridData gDataStereoTypeLabel;

    /**
     * 
     */
    public ClassifierCustomFigure() {

        GridLayout layoutThis = new GridLayout();
        layoutThis.numColumns = 1;
        layoutThis.makeColumnsEqualWidth = true;
        this.setLayoutManager(layoutThis);
        layoutThis.horizontalSpacing = 0;
        layoutThis.verticalSpacing = 0;
        layoutThis.marginWidth = 0;
        layoutThis.marginHeight = 0;
        createContents();

        this.setOpaque(true);
    }

    /**
     * 
     */
    private void createContents() {

        // Base gradient node figure
        gradFigure = new GradientNodeFigure(ColorConstants.lightGray,
                ColorConstants.white);

        gDataGradFigure = new GridData();
        gDataGradFigure.verticalAlignment = GridData.FILL;
        gDataGradFigure.horizontalAlignment = GridData.FILL;
        gDataGradFigure.horizontalIndent = 0;
        gDataGradFigure.horizontalSpan = 1;
        gDataGradFigure.verticalSpan = 1;
        gDataGradFigure.grabExcessHorizontalSpace = true;
        gDataGradFigure.grabExcessVerticalSpace = false;
        this.add(gradFigure, gDataGradFigure);
        //this.add(gradFigure);

        GridLayout layoutClassHeader = new GridLayout();
        layoutClassHeader.numColumns = 1;
        layoutClassHeader.makeColumnsEqualWidth = true;
//        layoutClassHeader.marginHeight = 2;
//        layoutClassHeader.verticalSpacing = 2;
        layoutClassHeader.marginHeight = 3;
        layoutClassHeader.verticalSpacing = -2;
        gradFigure.setLayoutManager(layoutClassHeader);

        // SuperType label
        superClassNameLabel = new WrappingLabel();

        // superClassNameLabel.setText("<supertype>");

        gDataSuperClassNameLabel = new GridData();
        gDataSuperClassNameLabel.verticalAlignment = GridData.END;
        gDataSuperClassNameLabel.horizontalAlignment = GridData.END;
        gDataSuperClassNameLabel.horizontalIndent = 0;
        gDataSuperClassNameLabel.horizontalSpan = 1;
        gDataSuperClassNameLabel.verticalSpan = 1;
        gDataSuperClassNameLabel.grabExcessHorizontalSpace = false;
        gDataSuperClassNameLabel.grabExcessVerticalSpace = false;
        // gradFigure.add(superClassNameLabel, gDataSuperClassNameLabel);

        // Stereotype label
        stereoTypeLabel = new WrappingLabel();
        // stereoTypeLabel.setText("<<stereotype>>");

        gDataStereoTypeLabel = new GridData();
        gDataStereoTypeLabel.verticalAlignment = GridData.CENTER;
        gDataStereoTypeLabel.horizontalAlignment = GridData.CENTER;
        gDataStereoTypeLabel.horizontalIndent = 0;
        gDataStereoTypeLabel.horizontalSpan = 1;
        gDataStereoTypeLabel.verticalSpan = 1;
        gDataStereoTypeLabel.grabExcessHorizontalSpace = false;
        gDataStereoTypeLabel.grabExcessVerticalSpace = false;
        // gradFigure.add(stereoTypeLabel, gDataStereoTypeLabel);

        // Name label
        classifierNameLabel = new WrappingLabel();
        classifierNameLabel.setLayoutManager(BOMCustomFigureUtils
                .createBOMWrapLabelLayout());
        classifierNameLabel.setText(Messages.ClassCustomFigure_EmptyClassName_label);

        gDataClassNameLabel = new GridData();
        gDataClassNameLabel.verticalAlignment = GridData.CENTER;
        gDataClassNameLabel.horizontalAlignment = GridData.CENTER;
        gDataClassNameLabel.horizontalIndent = 0;
        gDataClassNameLabel.horizontalSpan = 1;
        gDataClassNameLabel.verticalSpan = 1;
        gDataClassNameLabel.grabExcessHorizontalSpace = true;
        gDataClassNameLabel.grabExcessVerticalSpace = false;
        gradFigure.add(classifierNameLabel, gDataClassNameLabel);

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
    public WrappingLabel getClassifierNameLabel() {
        return classifierNameLabel;
    }

    public WrappingLabel getSuperClassNameLabel() {
        return superClassNameLabel;
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

    public void setSuperClassName(String superTypeName) {
        superClassNameLabel.setText(superTypeName);
    }

    public void removeSuperClassName() {
        // TODO: Rebuild the figure with supertype name set to ""
        // this.removeAll();
        superClassNameLabel.setText(""); //$NON-NLS-1$
        // this.add(gradFigure);
    }

    public void setStereoType(String stereoTypeName) {
        stereoTypeLabel.setText("<<" + stereoTypeName + ">>"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void removeStereoType() {
        stereoTypeLabel.setText(""); //$NON-NLS-1$
    }

    @SuppressWarnings("unchecked")
    public void rebuildFigureWithSuperClass(boolean showSuperClass) {
        // Get a list of the current children
        List<Object> prevChildren = new ArrayList<Object>();
        prevChildren.addAll(gradFigure.getChildren());

        // Remove the current children
        gradFigure.removeAll();

        // Now put them back
        if (showSuperClass) {
            gradFigure.add(superClassNameLabel, gDataSuperClassNameLabel);

        }
        
        if (prevChildren!= null && prevChildren.contains(stereoTypeLabel)) {
            gradFigure.add(stereoTypeLabel);
        }

        // Always add the label
        gradFigure.add(classifierNameLabel);

    }
    
    @SuppressWarnings("unchecked")
    public void rebuildFigureWithStereo(boolean showStereo) {
        // Get a list of the current children
        List<Object> prevChildren = new ArrayList<Object>();
        prevChildren.addAll(gradFigure.getChildren());

        // Remove the current children
        gradFigure.removeAll();

        if (prevChildren!= null && prevChildren.contains(superClassNameLabel)) {
            gradFigure.add(superClassNameLabel);
        }
        
        // Now put them back
        if (showStereo) {            
            gradFigure.add(stereoTypeLabel, gDataStereoTypeLabel);
        }

        // Always add the label
        gradFigure.add(classifierNameLabel);

    }
    
}
