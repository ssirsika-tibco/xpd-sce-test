package com.tibco.xpd.om.modeler.custom.propertysection.appearance;

import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import com.tibco.xpd.om.modeler.custom.internal.Messages;
import com.tibco.xpd.om.modeler.custom.internal.OMDiagramUIPropertiesImages;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationFactory;
import com.tibco.xpd.om.resources.ui.omnotation.OmNotationPackage;

public class BorderGradientSection extends ShapeGradientSection {

    /**
     * Create gradient group
     * 
     * @param parent
     *            - parent composite
     */
    protected Group createGradientGroup(Composite parent) {
        gradientGroup =
                getWidgetFactory().createGroup(parent, "Border Gradient"); //$NON-NLS-1$
        GridLayout layout = new GridLayout(1, false);
        gradientGroup.setLayout(layout);

        createGradientControls(gradientGroup);

        return gradientGroup;
    }

    /**
     * Change gradient start color property value
     */
    protected void changeGradStartColor(SelectionEvent event) {
        // calling the deprectaed method in case a client overrides the
        // deprecated method
        gradStartColor =
                changeColor(event,
                        gradStartColorButton,
                        /* "Appearance.gradStartColor", */PackageUtil
                                .getID(OmNotationFactory.eINSTANCE
                                        .getOmNotationPackage()
                                        .getBorderGradientStyle_GradStartColor()),
                        Messages.ShapeGradientSection_startColor_message,
                        OMDiagramUIPropertiesImages.DESC_FILL_COLOR,
                        FigureUtilities.RGBToInteger(gradStartColor));
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
                                .getID(OmNotationFactory.eINSTANCE
                                        .getOmNotationPackage()
                                        .getBorderGradientStyle_GradEndColor()),
                        Messages.ShapeGradientSection_endColor_message,
                        OMDiagramUIPropertiesImages.DESC_FILL_COLOR,
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
    protected void updateColorCache() {

        executeAsReadAction(new Runnable() {

            public void run() {

                if (getSingleInput() instanceof GraphicalEditPart) {
                    GraphicalEditPart ep = (GraphicalEditPart) getSingleInput();

                    gradStartColor =
                            FigureUtilities
                                    .integerToRGB((Integer) ep
                                            .getStructuralFeatureValue(OmNotationPackage.eINSTANCE
                                                    .getBorderGradientStyle_GradStartColor()));

                    gradEndColor =
                            FigureUtilities
                                    .integerToRGB((Integer) ep
                                            .getStructuralFeatureValue(OmNotationPackage.eINSTANCE
                                                    .getBorderGradientStyle_GradEndColor()));

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
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        // Currently no need for this section

        return false;
    }
}
