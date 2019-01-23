/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.ribbon.groups;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.layout.GridLayout;

import com.hexapixel.widgets.ribbon.RibbonButtonGroup;
import com.hexapixel.widgets.ribbon.RibbonGroup;
import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.RCPImages;
import com.tibco.xpd.rcp.internal.Messages;
import com.tibco.xpd.rcp.internal.actions.ZoomAction;
import com.tibco.xpd.rcp.internal.actions.ZoomAction.ZoomType;
import com.tibco.xpd.rcp.ribbon.AbstractRibbonGroup;

/**
 * Diagram group in the Viewer folder that allows the user to adjust the zoom
 * level in GEF editors.
 * 
 * @author njpatel
 * 
 */
public class DiagramRibbonGroup extends AbstractRibbonGroup {

    private static final ImageRegistry IMAGE_REGISTRY =
            RCPActivator.getDefault().getImageRegistry();

    public DiagramRibbonGroup() {
    }

    @Override
    protected void createContent(RibbonGroup group) {
        final ZoomAction zoomIn = new ZoomAction(getWindow(), ZoomType.IN);
        final ZoomAction zoomOut = new ZoomAction(getWindow(), ZoomType.OUT);
        final ZoomAction zoomToFit =
                new ZoomAction(getWindow(), ZoomType.FIT_ALL);
        final ZoomAction fitToWidth =
                new ZoomAction(getWindow(), ZoomType.FIT_WIDTH);
        final ZoomAction fitToHeight =
                new ZoomAction(getWindow(), ZoomType.FIT_HEIGHT);

        GridLayout layout = new GridLayout(2, false);
        layout.marginWidth = 100;
        group.setLayout(layout);

        // Zoom in action
        createAction(group,
                zoomIn,
                Messages.DiagramRibbonGroup_zoomIn_button,
                IMAGE_REGISTRY.get(RCPImages.ZOOM_IN.getPath()),
                IMAGE_REGISTRY.get(RCPImages.ZOOM_IN.getDisabledPath()),
                false);
        // Zoom out action
        createAction(group,
                zoomOut,
                Messages.DiagramRibbonGroup_zoomOut_button,
                IMAGE_REGISTRY.get(RCPImages.ZOOM_OUT.getPath()),
                IMAGE_REGISTRY.get(RCPImages.ZOOM_OUT.getDisabledPath()),
                false);

        RibbonButtonGroup btnGrp = createButtonGroup(group);

        // Zoom to fit
        createAction(btnGrp,
                zoomToFit,
                Messages.DiagramRibbonGroup_zoomFit_button,
                IMAGE_REGISTRY.get(RCPImages.ZOOM_TOFIT.getPath()),
                IMAGE_REGISTRY.get(RCPImages.ZOOM_TOFIT.getDisabledPath()),
                false);

        // Fit to width
        createAction(btnGrp,
                fitToWidth,
                Messages.DiagramRibbonGroup_zoomWidth_button,
                IMAGE_REGISTRY.get(RCPImages.ZOOM_WIDTH.getPath()),
                IMAGE_REGISTRY.get(RCPImages.ZOOM_WIDTH.getDisabledPath()),
                false);

        // Fit to height
        createAction(btnGrp,
                fitToWidth,
                Messages.DiagramRibbonGroup_zoomHeight_button,
                IMAGE_REGISTRY.get(RCPImages.ZOOM_HEIGHT.getPath()),
                IMAGE_REGISTRY.get(RCPImages.ZOOM_HEIGHT.getDisabledPath()),
                false);

        /*
         * Listen to property changes on each zoom action so we can update the
         * other if the zoom level has changed from min or max, otherwise the
         * other will remain disabled.
         */
        zoomIn.addPropertyChangeListener(new IPropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if (ZoomAction.ZOOM_LEVEL.equals(event.getProperty())) {
                    zoomOut.update();
                    zoomToFit.update();
                    fitToWidth.update();
                    fitToHeight.update();
                }
            }
        });
        zoomOut.addPropertyChangeListener(new IPropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent event) {
                if (ZoomAction.ZOOM_LEVEL.equals(event.getProperty())) {
                    zoomIn.update();
                    zoomToFit.update();
                    fitToWidth.update();
                    fitToHeight.update();
                }
            }
        });
    }

}
