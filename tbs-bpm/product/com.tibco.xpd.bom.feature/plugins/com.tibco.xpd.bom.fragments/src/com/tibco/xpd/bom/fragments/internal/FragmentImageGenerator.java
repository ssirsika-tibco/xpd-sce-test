/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.fragments.internal;

import org.eclipse.gmf.runtime.diagram.ui.OffscreenEditPartFactory;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramImageGenerator;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Shell;

/**
 * Fragment image generator runnable that will generate an image of the given
 * {@link Diagram}.
 * 
 * @author njpatel
 * 
 */
public class FragmentImageGenerator implements Runnable {

    private final Diagram diagram;
    private ImageData imgData = null;

    /**
     * Fragment image generator runnable that will generate an image of the
     * given <code>Diagram</code>.
     * 
     * @param diagram
     */
    public FragmentImageGenerator(Diagram diagram) {
        this.diagram = diagram;
    }

    /**
     * Get the generated {@link ImageData}.
     * 
     * @return
     */
    public ImageData getImageData() {
        return imgData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        Shell shell = new Shell();
        try {
            DiagramEditPart editPart = OffscreenEditPartFactory.getInstance()
                    .createDiagramEditPart(diagram, shell);

            if (editPart != null) {
                DiagramImageGenerator imageGenerator = new DiagramImageGenerator(
                        editPart);
                ImageDescriptor imgDesc = imageGenerator
                        .createSWTImageDescriptorForDiagram();
                if (imgDesc != null) {
                    imgData = imgDesc.getImageData();
                }
            }

        } finally {
            shell.dispose();
        }
    }

}