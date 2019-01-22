/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.custom.OrgUnitCustomEditPart;

public class OrgUnitCustomFigure extends NodeFigure implements IGradientFigure {

    private OrgUnitCustomEditPart parentEP;

    private WrappingLabel featureLabel;

    private WrappingLabel nameLabel;

    private WrappingLabel expandHierarchyLabel;

    private GradientNodeFigure gradFigure;

    private GridData gDataGradFigure;

    private GridData gDataFeatureLabel;

    private GridData gDataNameLabel;

    private GridData gDataExpandLabel;

    public static int COLLAPSE_ICON = 0;

    public static int EXPAND_ICON = 1;

    /**
     * 
     */
    public OrgUnitCustomFigure(OrgUnitCustomEditPart ep) {
        this.parentEP = ep;

        GridLayout layoutThis = new GridLayout();
        layoutThis.numColumns = 1;
        layoutThis.makeColumnsEqualWidth = true;
        this.setLayoutManager(layoutThis);
        layoutThis.horizontalSpacing = 0;
        layoutThis.verticalSpacing = 0;
        layoutThis.marginWidth = 0;
        layoutThis.marginHeight = 0;

        setBorder(new LineBorder());

        createContents();

        this.setOpaque(true);
    }

    /**
     * 
     */
    protected void createContents() {

        // Base gradient node figure
        gradFigure =
                new GradientNodeFigure(ColorConstants.white, new Color(
                        Display.getCurrent(), new RGB(241, 142, 124)));

        gDataGradFigure = new GridData();
        gDataGradFigure.verticalAlignment = GridData.FILL;
        gDataGradFigure.horizontalAlignment = GridData.FILL;
        gDataGradFigure.horizontalIndent = 0;
        gDataGradFigure.horizontalSpan = 1;
        gDataGradFigure.verticalSpan = 1;

        gDataGradFigure.grabExcessHorizontalSpace = true;
        gDataGradFigure.grabExcessVerticalSpace = false;
        this.add(gradFigure, gDataGradFigure);

        GridLayout layoutClassHeader = new GridLayout();
        layoutClassHeader.numColumns = 2;
        layoutClassHeader.makeColumnsEqualWidth = false;
        layoutClassHeader.marginHeight = 3;
        layoutClassHeader.verticalSpacing = 3;
        gradFigure.setLayoutManager(layoutClassHeader);

        // Feature Label
        featureLabel = new WrappingLabel();
        featureLabel.setText("Feature");

        gDataFeatureLabel = new GridData();
        gDataFeatureLabel.verticalAlignment = GridData.CENTER;
        gDataFeatureLabel.horizontalAlignment = GridData.CENTER;
        gDataFeatureLabel.horizontalSpan = 2;
        gDataFeatureLabel.verticalSpan = 1;
        gDataFeatureLabel.grabExcessHorizontalSpace = true;
        gDataFeatureLabel.grabExcessVerticalSpace = false;

        // gradFigure.add(featureLabel, gDataFeatureLabel);

        // SuperType label
        expandHierarchyLabel = new WrappingLabel();
        expandHierarchyLabel
                .addMouseListener(new expandMouseListener(parentEP));

        gDataExpandLabel = new GridData();
        gDataExpandLabel.verticalAlignment = GridData.END;
        gDataExpandLabel.horizontalAlignment = GridData.END;
        gDataExpandLabel.horizontalIndent = 0;
        gDataExpandLabel.horizontalSpan = 1;
        gDataExpandLabel.verticalSpan = 1;
        gDataExpandLabel.grabExcessHorizontalSpace = false;
        gDataExpandLabel.grabExcessVerticalSpace = false;

        // Name label
        nameLabel = new WrappingLabel();
        nameLabel.setLayoutManager(createBOMWrapLabelLayout());

        gDataNameLabel = new GridData();
        gDataNameLabel.verticalAlignment = GridData.CENTER;
        gDataNameLabel.horizontalAlignment = GridData.CENTER;
        gDataNameLabel.horizontalIndent = 0;
        gDataNameLabel.horizontalSpan = 1;
        gDataNameLabel.verticalSpan = 1;
        gDataNameLabel.grabExcessHorizontalSpace = true;
        gDataNameLabel.grabExcessVerticalSpace = false;
        gradFigure.add(nameLabel, gDataNameLabel);

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

    /**
     * 
     */
    public WrappingLabel getNameLabel() {
        return nameLabel;
    }

    public WrappingLabel getFeatureLabel() {
        return featureLabel;
    }

    @Override
    public void setGradientStart(Color gradColor1) {
        gradFigure.setGradientStart(gradColor1);
    }

    @Override
    public void setGradientEnd(Color gradColor2) {
        gradFigure.setGradientEnd(gradColor2);
    }

    private GridLayout createBOMWrapLabelLayout() {
        GridLayout gl = new GridLayout();
        gl.numColumns = 1;
        gl.marginHeight = 12;
        gl.marginWidth = 0;
        return gl;
    }

    private class expandMouseListener implements MouseListener {

        OrgUnitCustomEditPart ep;

        public expandMouseListener(OrgUnitCustomEditPart ep) {
            this.ep = ep;

        }

        @Override
        public void mouseDoubleClicked(MouseEvent me) {
        }

        @Override
        public void mousePressed(MouseEvent me) {
            ep.changeOrgUnitNodeHierarchy();

        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

    }

    public void setExpandIcon() {
        rebuildFigureWithExpandCollapseIcon(true,
                OrgUnitCustomFigure.EXPAND_ICON);
    }

    public void setCollapseIcon() {
        rebuildFigureWithExpandCollapseIcon(true,
                OrgUnitCustomFigure.COLLAPSE_ICON);
    }

    public void rebuildFigureWithExpandCollapseIcon(boolean showIcon,
            int iconType) {
        // Get a list of the current children
        List<Object> prevChildren = new ArrayList<Object>();
        prevChildren.addAll(gradFigure.getChildren());

        // Remove the current children
        gradFigure.removeAll();

        if (prevChildren != null && prevChildren.contains(featureLabel)) {
            gradFigure.add(featureLabel, gDataFeatureLabel);
        }

        // Now put them back
        if (showIcon) {
            Image img = null;
            if (iconType == OrgUnitCustomFigure.COLLAPSE_ICON) {
                img =
                        ExtendedImageRegistry.INSTANCE
                                .getImage(OMModelImages
                                        .getImageObject(OMModelImages.IMAGE_HIERARCHY_COLLAPSE));
            } else {
                img =
                        ExtendedImageRegistry.INSTANCE
                                .getImage(OMModelImages
                                        .getImageObject(OMModelImages.IMAGE_HIERARCHY_EXPAND));
            }

            expandHierarchyLabel.setIcon(img, 1);
            gradFigure.add(expandHierarchyLabel, gDataExpandLabel);
        }

        // Always add the label
        gradFigure.add(nameLabel);

    }

    public void rebuildFigureWithFeatureLabel(boolean showFeatureLabel) {

        // Get a list of the current children
        List<Object> prevChildren = new ArrayList<Object>();
        prevChildren.addAll(gradFigure.getChildren());

        // Remove the current children
        gradFigure.removeAll();

        // Now put them back
        if (showFeatureLabel) {
            gradFigure.add(featureLabel, gDataFeatureLabel);

        }

        if (prevChildren != null && prevChildren.contains(expandHierarchyLabel)) {
            gradFigure.add(expandHierarchyLabel, gDataExpandLabel);
        }

        gradFigure.add(nameLabel, gDataNameLabel);

    }

}
