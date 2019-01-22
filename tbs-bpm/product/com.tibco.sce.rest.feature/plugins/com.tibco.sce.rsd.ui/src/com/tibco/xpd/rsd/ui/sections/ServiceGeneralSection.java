/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.components.MediaType;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator.RestPathIssue;
import com.tibco.xpd.rsd.ui.util.RestServicePathValidator.RestPathIssueType;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * General section for RSD's service.
 * 
 * @author jarciuch
 * @since 2 Feb 2015
 */
public class ServiceGeneralSection extends AbstractRsdSection {

    private Composite serviceControls;

    private Text pathText;

    // private ComboViewer mediaTypeViewer;

    private Label mediaTypeLabel;

    private ControlDecoration pathTextDecoration;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Service) {
            Service s = (Service) input;
            if (isValidControl(pathText)) {
                updateText(pathText, s.getContextPath());

                /* Sid XPD-7755 - display path errors in UI. */
                validatePath();
            }

            /*
             * Combo viewer is used to select media Type but as we only support
             * JSON Schema Types at the moment we just use label.
             */
            if (isValidControl(mediaTypeLabel)) {
                String mtString = s.getMediaType();
                MediaType mediaType = MediaType.getByModelValue(mtString);
                if (mediaType != null) {
                    mtString = mediaType.getLabel();
                }
                mtString = mtString != null ? mtString : ""; //$NON-NLS-1$
                mediaTypeLabel.setText(mtString);
            }
            /*
             * if (mediaTypeViewer != null &&
             * isValidControl(mediaTypeViewer.getCCombo())) { MediaType
             * mediaType = MediaType.getByModelValue(s.getMediaType()); if
             * (mediaType != null) { mediaTypeViewer .setSelection(new
             * StructuredSelection(mediaType)); } else {
             * mediaTypeViewer.getCCombo().setText(s.getMediaType()); } }
             */

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayoutFactory.swtDefaults().applyTo(root);

        serviceControls = toolkit.createComposite(root);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).applyTo(serviceControls);
        getGroupControlLayoutFactory().numColumns(2).applyTo(serviceControls);

        createLabel(serviceControls,
                toolkit,
                Messages.ServiceGeneralSection_ContextPath_label);
        pathText =
                toolkit.createText(serviceControls, "", "Service-contextPath"); //$NON-NLS-1$ //$NON-NLS-2$

        pathTextDecoration =
                new ControlDecoration(pathText, SWT.TOP | SWT.LEFT);

        /* Sid XPD-7755 - display path errors in UI. */
        setPathDecoration(FieldDecorationRegistry
                .getDefault()
                .getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL)
                .getImage(),
                Messages.ServiceGeneralSection_ContextPathDecorationLabel);

        /*
         * Do validation of field live rather than always waiting until
         * deactivate text.
         */
        pathText.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                validatePath();
            }
        });

        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).hint(30, SWT.DEFAULT).applyTo(pathText);

        manageControlUpdateOnDeactivate(pathText);

        createLabel(serviceControls,
                toolkit,
                Messages.ServiceGeneralSection_MediaType_label);

        /*
         * Combo viewer is used to select media Type but as we only support JSON
         * Schema Types at the moment we just use label.
         */
        mediaTypeLabel = toolkit.createLabel(serviceControls, "", SWT.WRAP); //$NON-NLS-1$
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).applyTo(mediaTypeLabel);
        /*
         * CCombo mediaTypeCombo = toolkit.createCCombo(serviceControls);
         * GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP) .grab(true,
         * false).hint(30, SWT.DEFAULT) .applyTo(mediaTypeCombo);
         * mediaTypeViewer = new ComboViewer(mediaTypeCombo);
         * mediaTypeViewer.setContentProvider(new ArrayContentProvider());
         * mediaTypeViewer.setLabelProvider(new LabelProvider() {
         * 
         * @Override public String getText(Object o) { return o instanceof
         * MediaType ? ((MediaType) o).getLabel() : ""; //$NON-NLS-1$ } });
         * mediaTypeViewer.setInput(MediaType.values());
         * manageControl(mediaTypeCombo);
         */
        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        EObject input = getInput();
        Command cmd = null;
        if (input instanceof Service) {
            Service service = (Service) input;
            if (obj == pathText) {
                String value = ((Text) obj).getText();
                if (!value.equals(service.getContextPath())) {
                    // Update the name
                    cmd =
                            SetCommand.create(getEditingDomain(),
                                    input,
                                    RsdPackage.eINSTANCE
                                            .getService_ContextPath(),
                                    value);
                }
            }
            /*
             * Combo viewer is used to select media Type but as we only support
             * JSON Schema Types at the moment we just use label.
             * 
             * else if (obj == mediaTypeViewer.getCCombo()) { String oldValue =
             * service.getMediaType(); IStructuredSelection selection =
             * (IStructuredSelection) mediaTypeViewer.getSelection(); String
             * newValue = null; if (selection != null && !selection.isEmpty()) {
             * Object o = selection.getFirstElement(); if (o instanceof
             * MediaType) { newValue = ((MediaType) o).getModelValue(); } } if
             * (newValue != null && !newValue.equals(oldValue)) { cmd =
             * SetCommand .create(getEditingDomain(), input,
             * RsdPackage.eINSTANCE .getService_MediaType(), newValue); } }
             */
        }
        return cmd;
    }

    /**
     * Sid XPD-7755 - validate and display path errors in UI.
     * <p>
     * Updates path control decoration.
     * 
     * @return <code>true</code> if valid or <code>false</code> not
     */
    private void validatePath() {
        if (pathTextDecoration == null
                || pathTextDecoration.getControl() == null
                || pathTextDecoration.getControl().isDisposed()) {
            return;
        }

        RestPathIssue pathIssue = null;

        EObject input = getInput();

        if (input instanceof Service) {
            Service service = (Service) input;

            RestServicePathValidator validator = new RestServicePathValidator();

            pathIssue = validator.validate(service, pathText.getText());
        }

        if (pathIssue == null
                || RestPathIssueType.OK.equals(pathIssue.getType())) {
            setPathDecoration(FieldDecorationRegistry
                    .getDefault()
                    .getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL)
                    .getImage(),
                    Messages.ServiceGeneralSection_ContextPathDecorationLabel);
            return;

        } else {
            setPathDecoration(FieldDecorationRegistry.getDefault()
                    .getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
                    .getImage(), pathIssue.getMessage());
            return;
        }

    }

    /**
     * Sid XPD-7755 - display path errors in UI.
     * <p>
     * Set the path decoration image / text (if details are different from
     * current).
     * 
     * @param image
     * @param tooltip
     */
    private void setPathDecoration(Image image, String tooltip) {
        if (image != pathTextDecoration.getImage()
                || !tooltip.equals(pathTextDecoration.getDescriptionText())) {
            pathTextDecoration.setImage(image);
            pathTextDecoration.setDescriptionText(tooltip);

        }
    }
}
