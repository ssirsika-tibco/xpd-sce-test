/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.modeler.custom.propertysection.appearance;

import java.util.Collections;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.om.modeler.custom.internal.Messages;
import com.tibco.xpd.om.modeler.custom.internal.OMDiagramUIPropertiesImages;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Background selection section in the Appearance property page of a diagram.
 * 
 * @author njpatel
 * 
 */
public class DiagramBackgroundSection extends AbstractAppearanceSection {

    private Button bgColorButton;

    private RGB bgColor;

    @Override
    protected void initializeControls(Composite parent) {
        super.initializeControls(parent);

        // Create the background selection section
        Composite root = getWidgetFactory().createComposite(composite);
        root.setLayout(new GridLayout(2, false));

        getWidgetFactory().createLabel(root,
                Messages.DiagramBackgroundSection_bgColor_label);

        bgColorButton = getWidgetFactory().createButton(root, "", SWT.PUSH); //$NON-NLS-1$
        bgColorButton
                .setToolTipText(Messages.DiagramBackgroundSection_bgColor_button_tooltip);
        bgColorButton.setImage(OMDiagramUIPropertiesImages
                .get(OMDiagramUIPropertiesImages.IMG_FILL_COLOR));
        bgColorButton.getAccessible()
                .addAccessibleListener(new AccessibleAdapter() {
                    public void getName(AccessibleEvent e) {
                        e.result =
                                Messages.DiagramBackgroundSection_bgColor_accessible_shortdesc;
                    }
                });
        bgColorButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                final IGraphicalEditPart input = getSingleInput();
                if (input instanceof DiagramEditPart) {
                    Object model = input.getModel();

                    if (model instanceof View) {
                        final View view = (View) model;

                        SelectedColor selectedColor =
                                showColorPalette(bgColorButton, FigureUtilities
                                        .RGBToInteger(bgColor));

                        RGB newColor = null;
                        if (selectedColor.useDefaultColor()
                                || selectedColor.getColor() != null) {

                            if (selectedColor.useDefaultColor()) {
                                Object preferredValue =
                                        input
                                                .getPreferredValue(NotationFactory.eINSTANCE
                                                        .getNotationPackage()
                                                        .getFillStyle_FillColor());
                                if (preferredValue instanceof Integer) {
                                    newColor =
                                            FigureUtilities
                                                    .integerToRGB((Integer) preferredValue);
                                }
                            } else {
                                newColor = selectedColor.getColor();
                            }

                            if (bgColor != null && !bgColor.equals(newColor)) {
                                bgColor = newColor;
                                ICommand cmd =
                                        getChangeColorCommand(input, view);
                                if (cmd != null && cmd.canExecute()) {
                                    executeAsCompositeCommand(Messages.DiagramBackgroundSection_setBgColor_command_label,
                                            Collections.singletonList(cmd));
                                }
                            }
                        }
                    }
                }
            }

        });
    }

    /**
     * Get the command that will apply the new color setting to the diagram.
     * 
     * @param ep
     *            diagram graphical edit part
     * @param view
     *            diagram view
     * @return
     */
    private ICommand getChangeColorCommand(final IGraphicalEditPart ep,
            final View view) {
        return createCommand(Messages.DiagramBackgroundSection_setBgColor_command_label,
                view.eResource(),
                new Runnable() {

                    public void run() {
                        // Update the fill colour
                        ENamedElement element =
                                PackageUtil.getElement(PackageUtil
                                        .getID(NotationFactory.eINSTANCE
                                                .getNotationPackage()
                                                .getFillStyle_FillColor()));
                        if (element instanceof EStructuralFeature)
                            ep
                                    .setStructuralFeatureValue((EStructuralFeature) element,
                                            FigureUtilities
                                                    .RGBToInteger(bgColor));
                    }
                });
    }

    @Override
    public void refresh() {
        super.refresh();

        if (bgColorButton != null && !bgColorButton.isDisposed()) {
            Image overlayedImage =
                    new ColorOverlayImageDescriptor(
                            OMDiagramUIPropertiesImages.DESC_FILL_COLOR
                                    .getImageData(), bgColor).createImage();
            disposeImage(bgColorButton.getImage());
            bgColorButton.setImage(overlayedImage);

            /*
             * If editing a read-only resource then disable the button
             */
            IGraphicalEditPart input = getSingleInput();
            if (input != null) {
                EObject eo = (EObject) input.getAdapter(EObject.class);
                if (eo != null) {
                    WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(eo);
                    if (wc != null && wc.isReadOnly()) {
                        bgColorButton.setEnabled(false);
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        if (bgColorButton != null && !bgColorButton.isDisposed()) {
            disposeImage(bgColorButton.getImage());
        }

        super.dispose();
    }

    @Override
    protected void updateColorCache() {
        executeAsReadAction(new Runnable() {

            public void run() {

                if (getSingleInput() instanceof GraphicalEditPart) {
                    GraphicalEditPart ep = (GraphicalEditPart) getSingleInput();

                    bgColor =
                            FigureUtilities
                                    .integerToRGB((Integer) ep
                                            .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                                    .getFillStyle_FillColor()));
                } else {
                    bgColor = DEFAULT_PREF_COLOR;
                }
            }
        });
    }

}
