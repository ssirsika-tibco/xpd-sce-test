package com.tibco.xpd.bom.modeler.custom.internal.propertysection.appearance;

import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import com.tibco.xpd.bom.modeler.custom.internal.BOMDiagramUIPropertiesImages;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationFactory;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage;

/**
 * Section representing the gradient fill of a shape. This class is adapted from
 * the gmf standard ColorsAndFontsPropertySection which is visible on the
 * Appearance tab.
 * 
 * @author rgreen
 * 
 */
public class ShapeGradientSection extends AbstractAppearanceSection {

    protected RGB gradEndColor = null;

    protected Canvas gradientCanvas;

    protected Group gradientGroup;

    protected Button gradStartColorButton;

    protected Button gradEndColorButton;

    protected RGB gradStartColor = null;

    private RGB lineColor = null;

    private final LocalResourceManager resourceManager =
            new LocalResourceManager(JFaceResources.getResources());

    /*
     * (non-Javadoc)
     * 
     * @seeorg.eclipse.gmf.runtime.diagram.ui.properties.sections.
     * AbstractNotationPropertiesSection
     * #initializeControls(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void initializeControls(Composite parent) {
        super.initializeControls(parent);
        createGradientGroup(composite);
    }

    /**
     * Create gradient group
     * 
     * @param parent
     *            - parent composite
     */
    protected Group createGradientGroup(Composite parent) {
        gradientGroup =
                getWidgetFactory().createGroup(parent,
                        Messages.ShapeGradientSection_gradient_group_label);
        GridLayout layout = new GridLayout(1, false);
        gradientGroup.setLayout(layout);

        createGradientControls(gradientGroup);

        return gradientGroup;
    }

    /**
     * 
     * Create the controls
     * 
     * @param parent
     *            - parent composite
     * @return - font tool bar
     */
    protected Composite createGradientControls(Composite parent) {

        Composite toolBar = new Composite(parent, SWT.SHADOW_NONE);
        toolBar.setLayout(new GridLayout(3, false));
        toolBar.setBackground(parent.getBackground());

        // Add our gradient buttons
        gradStartColorButton = new Button(toolBar, SWT.PUSH);
        gradStartColorButton.setImage(BOMDiagramUIPropertiesImages
                .get(BOMDiagramUIPropertiesImages.IMG_FILL_COLOR));
        gradStartColorButton.getAccessible()
                .addAccessibleListener(new AccessibleAdapter() {
                    @Override
                    public void getName(AccessibleEvent e) {
                        e.result =
                                DiagramUIMessages.PropertyDescriptorFactory_FillColor;
                    }
                });
        gradStartColorButton.setEnabled(false);

        // Gradient start button
        gradStartColorButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent event) {
                changeGradStartColor(event);
            }
        });
        if (isReadOnly()) {
            gradStartColorButton.setEnabled(false);
        } else {
            gradStartColorButton.setEnabled(true);
        }

        // Add the gradient preview box
        // final Canvas cv = new Canvas(toolBar, SWT.NONE);
        // gradientCanvas = cv;

        gradientCanvas = new Canvas(toolBar, SWT.NONE);
        gradientCanvas.setBackground(parent.getBackground());

        GridData ld = new GridData();
        ld.widthHint = 80;
        ld.heightHint = 15;
        gradientCanvas.setLayoutData(ld);

        gradientCanvas.addControlListener(new ControlListener() {
            @Override
            public void controlMoved(ControlEvent e) {
            }

            @Override
            public void controlResized(ControlEvent e) {
                GC gc = new GC(gradientCanvas);
                updateGradientBox(gradientCanvas, gc);
                gc.dispose();
            }
        });

        gradientCanvas.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent pe) {
                updateGradientBox(gradientCanvas, pe.gc);
            }
        });

        // Gradient end colour button
        gradEndColorButton = new Button(toolBar, SWT.PUSH);
        gradEndColorButton.setImage(BOMDiagramUIPropertiesImages
                .get(BOMDiagramUIPropertiesImages.IMG_FILL_COLOR));
        gradEndColorButton.getAccessible()
                .addAccessibleListener(new AccessibleAdapter() {
                    @Override
                    public void getName(AccessibleEvent e) {
                        e.result =
                                DiagramUIMessages.PropertyDescriptorFactory_FillColor;
                    }
                });
        gradEndColorButton.setEnabled(false);

        // Gradient end button
        gradEndColorButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent event) {
                changeGradEndColor(event);
            }
        });
        if (isReadOnly()) {
            gradEndColorButton.setEnabled(false);
        } else {
            gradEndColorButton.setEnabled(true);
        }

        return toolBar;
    }

    /**
     * Redraws the gradient preview canvas which is passed in as the control.
     * Usually called in response to a redraw/repaint event.
     * 
     * @param control
     * @param gc
     */
    protected void updateGradientBox(Control control, GC gc) {
        gc.setBackground(resourceManager.createColor(lineColor));
        Rectangle rect = control.getBounds();
        gc.fillRectangle(0, 0, rect.width, rect.height);
        // Only set the gradient colour if it is not null, it can be set to null
        // if the user has opened the "change colour" property sheet, and then
        // clicked escape when the colour selector is displayed
        if (gradStartColor != null) {
            gc.setForeground(resourceManager.createColor(gradStartColor));
        }
        if (gradEndColor != null) {
            gc.setBackground(resourceManager.createColor(gradEndColor));
        }
        gc.fillGradientRectangle(1, 1, rect.width - 2, rect.height - 2, false);
    }

    /**
     * Change gradient start color property value
     */
    protected void changeGradStartColor(SelectionEvent event) {
        // calling the deprectaed method in case a client overrides the
        // deprecated method
        if( gradStartColor != null ) {
            gradStartColor =
                    changeColor(event,
                            gradStartColorButton,
                            /* "Appearance.gradStartColor", */PackageUtil
                                    .getID(BomNotationFactory.eINSTANCE
                                            .getBomNotationPackage()
                                            .getShapeGradientStyle_GradStartColor()),
                            Messages.ShapeGradientSection_startGradientColor_label,
                            BOMDiagramUIPropertiesImages.DESC_FILL_COLOR,
                            FigureUtilities.RGBToInteger(gradStartColor));
        }
        if (!gradientCanvas.isDisposed()) {
            gradientCanvas.redraw();
        }
    }

    /**
     * Change gradient start color property value
     */
    protected void changeGradEndColor(SelectionEvent event) {
        // calling the deprectaed method in case a client overrides the
        // deprecated method
        gradEndColor =
                changeColor(event,
                        gradEndColorButton,
                        /* "Appearance.gradEndColor", */PackageUtil
                                .getID(BomNotationFactory.eINSTANCE
                                        .getBomNotationPackage()
                                        .getShapeGradientStyle_GradEndColor()),
                        Messages.ShapeGradientSection_endGradientColor_label,
                        BOMDiagramUIPropertiesImages.DESC_FILL_COLOR,
                        FigureUtilities.RGBToInteger(gradEndColor));
        if (!gradientCanvas.isDisposed()) {
            gradientCanvas.redraw();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.bom.modeler.custom.propertysection.appearance.
     * AbstractAppearanceSection#updateColorCache()
     */
    @Override
    protected void updateColorCache() {

        executeAsReadAction(new Runnable() {

            @Override
            public void run() {

                if (getSingleInput() instanceof GraphicalEditPart) {
                    GraphicalEditPart ep = (GraphicalEditPart) getSingleInput();

                    gradStartColor =
                            FigureUtilities
                                    .integerToRGB((Integer) ep
                                            .getStructuralFeatureValue(BomNotationPackage.eINSTANCE
                                                    .getShapeGradientStyle_GradStartColor()));

                    gradEndColor =
                            FigureUtilities
                                    .integerToRGB((Integer) ep
                                            .getStructuralFeatureValue(BomNotationPackage.eINSTANCE
                                                    .getShapeGradientStyle_GradEndColor()));

                    lineColor =
                            FigureUtilities
                                    .integerToRGB((Integer) ep
                                            .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                                    .getLineStyle_LineColor()));

                    if (!gradientCanvas.isDisposed()) {
                        gradientCanvas.redraw();
                    }

                } else {
                    gradStartColor = DEFAULT_PREF_COLOR;
                    gradEndColor = DEFAULT_PREF_COLOR;
                    lineColor = DEFAULT_PREF_COLOR;

                    if (!gradientCanvas.isDisposed()) {
                        gradientCanvas.redraw();
                    }
                }
            }
        });

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
     */
    @Override
    public void refresh() {
        super.refresh();
        if (!isDisposed()) {
            Image overlyedImage =
                    new ColorOverlayImageDescriptor(
                            BOMDiagramUIPropertiesImages.DESC_FILL_COLOR
                                    .getImageData(),
                            gradStartColor).createImage();
            disposeImage(gradStartColorButton.getImage());
            gradStartColorButton.setImage(overlyedImage);

            overlyedImage =
                    new ColorOverlayImageDescriptor(
                            BOMDiagramUIPropertiesImages.DESC_FILL_COLOR
                                    .getImageData(),
                            gradEndColor).createImage();
            disposeImage(gradEndColorButton.getImage());
            gradEndColorButton.setImage(overlyedImage);

        }
    }

    @Override
    public void dispose() {

        if (gradStartColorButton != null && !gradStartColorButton.isDisposed()) {
            disposeImage(gradStartColorButton.getImage());
        }

        if (gradEndColorButton != null && !gradEndColorButton.isDisposed()) {
            disposeImage(gradEndColorButton.getImage());
        }

        resourceManager.dispose();

        super.dispose();
    }

}
