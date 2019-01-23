/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import java.util.Objects;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.PayloadRefContainer;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Response;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.components.JsonRootTypePicker;
import com.tibco.xpd.rsd.ui.components.JsonTypeReference;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Picker section for picking JSON Schema reference describing JSON payload.
 * 
 * @author jarciuch
 * @since 5 Feb 2015
 */
public class JsonPayloadPickerSection extends AbstractRsdSection {

    private Composite clientControls;

    private JsonRootTypePicker picker;

    protected boolean showGroup = false;

    protected Text mediaType;

    private Label mediaTypeLabel;

    private Label arrayLabel;

    private Button arrayButton;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (isValidControl(picker) && input instanceof PayloadRefContainer) {
            PayloadRefContainer payloadRefContainer =
                    (PayloadRefContainer) input;
            picker.setRefContainer(payloadRefContainer);
            PayloadReference ref = payloadRefContainer.getPayloadReference();
            boolean canBeArray = false;
            boolean isArray = false;
            if (ref != null) {
                JsonTypeReference jsonReference =
                        JsonTypeReference.getJsonReference(ref);
                picker.setValue(jsonReference);
                if (jsonReference != null
                        && !JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE
                                .equals(jsonReference.getRef())) {
                    canBeArray = true;
                    isArray = ref.isArray();
                }
            } else {
                picker.setValue(null);
            }
            arrayLabel.setEnabled(canBeArray);
            arrayButton.setEnabled(canBeArray);
            arrayButton.setSelection(isArray);
        }
        if (isValidControl(mediaType)) {
            if (input instanceof Request) {
                mediaTypeLabel
                        .setText(Messages.JsonPayloadPickerSection_MediaTypeLabel);
                Request request = (Request) input;
                mediaType.setText(request.getContentType());
            } else if (input instanceof Response) {
                mediaTypeLabel
                        .setText(Messages.JsonPayloadPickerSection_MediaTypeAcceptLabel);
                EObject responseContainer = input.eContainer();
                if (responseContainer instanceof Method) {
                    Method method = (Method) responseContainer;
                    Request request = method.getRequest();
                    if (request != null) {
                        mediaType.setText(request.getAccept());
                    }
                }
            }
            clientControls.layout();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayoutFactory.swtDefaults().applyTo(root);

        if (showGroup) {
            clientControls =
                    toolkit.createGroup(root,
                            Messages.JsonPayloadPickerSection_Payload_label);
        } else {
            clientControls = toolkit.createComposite(root);
        }
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
                .grab(true, false).applyTo(clientControls);
        getGroupControlLayoutFactory().numColumns(4).applyTo(clientControls);

        createLabel(clientControls,
                toolkit,
                Messages.JsonPayloadPickerSection_PayloadType_label);
        picker =
                new JsonRootTypePicker(clientControls, SWT.NONE, toolkit,
                        getEditingDomain());
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).hint(30, SWT.DEFAULT).applyTo(picker);
        picker.setValue(null);

        mediaTypeLabel = toolkit.createLabel(clientControls, ""); //$NON-NLS-1$
        GridDataFactory.swtDefaults().applyTo(mediaTypeLabel);
        mediaType = toolkit.createText(clientControls, ""); //$NON-NLS-1$
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).hint(30, SWT.DEFAULT).applyTo(mediaType);
        manageControlUpdateOnDeactivate(mediaType);

        arrayLabel = toolkit.createLabel(clientControls, Messages.JsonPayloadPickerSection_PayloadArraylabel);
        GridDataFactory.swtDefaults().applyTo(arrayLabel);
        arrayButton = toolkit.createButton(clientControls, "", SWT.CHECK); //$NON-NLS-1$
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER)
                .grab(true, false).hint(30, SWT.DEFAULT).applyTo(arrayButton);
        manageControl(arrayButton);

        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        EObject input = getInput();
        if (obj == mediaType) {
            if (input instanceof Request) {
                Request request = (Request) input;
                String contentType = mediaType.getText();
                if (contentType.length() == 0) {
                    contentType = null;
                }
                if (!Objects.equals(contentType, request.getContentType())) {
                    CompoundCommand cc =
                            new CompoundCommand(
                                    Messages.JsonPayloadPickerSection_SetRequestContentTypeCmd);
                    cc.append(SetCommand.create(getEditingDomain(),
                            request,
                            RsdPackage.eINSTANCE.getRequest_ContentType(),
                            contentType == null ? SetCommand.UNSET_VALUE
                                    : contentType));
                    cmd = cc;
                }
            } else if (input instanceof Response) {
                EObject responseContainer = input.eContainer();
                if (responseContainer instanceof Method) {
                    Method method = (Method) responseContainer;
                    Request request = method.getRequest();
                    if (request != null) {
                        String contentType = mediaType.getText();
                        if (contentType.length() == 0) {
                            contentType = null;
                        }
                        if (!Objects.equals(contentType, request.getAccept())) {
                            CompoundCommand cc =
                                    new CompoundCommand(
                                            Messages.JsonPayloadPickerSection_SetResponseContentTypeCmd);
                            cc.append(SetCommand
                                    .create(getEditingDomain(),
                                            request,
                                            RsdPackage.eINSTANCE
                                                    .getRequest_Accept(),
                                            contentType == null ? SetCommand.UNSET_VALUE
                                                    : contentType));
                            cmd = cc;
                        }
                    }
                }
            }
        } else if (obj == arrayButton && input instanceof PayloadRefContainer) {
            PayloadRefContainer payloadRefContainer =
                    (PayloadRefContainer) input;
            PayloadReference ref = payloadRefContainer.getPayloadReference();
            if (ref != null) {
                CompoundCommand cc =
                        new CompoundCommand(Messages.JsonPayloadPickerSection_SetPayloadArrayCommand);
                cc.append(SetCommand.create(getEditingDomain(),
                        ref,
                        RsdPackage.eINSTANCE.getPayloadReference_Array(),
                        arrayButton.getSelection()));
                cmd = cc;
            }
        }
        return cmd;
    }
}
