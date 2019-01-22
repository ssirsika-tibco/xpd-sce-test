/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.ui.components.RsdEditingUtil;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * This section is for editing RSD method's HTTP method property.
 *
 * @author jarciuch
 * @since 2 Feb 2015
 */
public class HttpMethodComboSection extends AbstractRsdSection {

    private Composite groupControl;

    private ComboViewer httpMethodViewer;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Method && httpMethodViewer != null
                && isValidControl(httpMethodViewer.getCCombo())) {

            Method method = (Method) input;

            HttpMethod httpMethod = method.getHttpMethod();
            if (httpMethod != null) {
                httpMethodViewer.setSelection(new StructuredSelection(
                        httpMethod));
            } else {
                httpMethodViewer.setSelection(new StructuredSelection(
                        HttpMethod.GET)); // default
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        GridLayoutFactory.swtDefaults().applyTo(root);

        groupControl = toolkit.createComposite(root);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).applyTo(groupControl);
        getGroupControlLayoutFactory().numColumns(2).applyTo(groupControl);

        createLabel(groupControl,
                toolkit,
                Messages.MethodGeneralSection_HttpMethod_label);
        CCombo httpMethodCombo =
                toolkit.createCCombo(groupControl, "Method-httpMethod"); //$NON-NLS-1$
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).hint(30, SWT.DEFAULT)
                .applyTo(httpMethodCombo);
        httpMethodViewer = new ComboViewer(httpMethodCombo);
        httpMethodViewer.setContentProvider(new ArrayContentProvider());
        httpMethodViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object o) {
                return o instanceof HttpMethod ? ((HttpMethod) o).getName()
                        : ""; //$NON-NLS-1$
            }
        });
        httpMethodViewer.setInput(HttpMethod.VALUES);
        // manageControl(httpMethodCombo);
        httpMethodViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        if (!isIgnoreEvents()) {
                            ISelection selection = event.getSelection();
                            EObject input = getInput();
                            if (input instanceof Method
                                    && selection instanceof IStructuredSelection
                                    && !selection.isEmpty()) {
                                final Method method = (Method) input;
                                Object element =
                                        ((IStructuredSelection) selection)
                                                .getFirstElement();

                                if (element instanceof HttpMethod) {
                                    final HttpMethod newValue =
                                            (HttpMethod) element;
                                    final HttpMethod oldValue =
                                            ((Method) input).getHttpMethod();

                                    if (!newValue.equals(oldValue)) {
                                        Command cmd =
                                                new RecordingCommand(
                                                        (TransactionalEditingDomain) getEditingDomain(),
                                                        Messages.MethodGeneralSection_SetHttpMethodCmd_label) {
                                                    @Override
                                                    protected void doExecute() {

                                                        method.setHttpMethod(newValue);

                                                        /*
                                                         * Remove
                                                         * request/response
                                                         * payload ref. if
                                                         * method's payload is
                                                         * not supported.
                                                         */
                                                        if (cleanRequestPayload(method,
                                                                oldValue,
                                                                newValue)) {
                                                            method.getRequest()
                                                                    .setPayloadReference(null);
                                                        }
                                                        if (cleanResponsePayload(method,
                                                                oldValue,
                                                                newValue)) {
                                                            method.getResponse()
                                                                    .setPayloadReference(null);
                                                        }
                                                    }

                                                    private boolean cleanRequestPayload(
                                                            Method method,
                                                            HttpMethod oldValue,
                                                            HttpMethod newValue) {
                                                        // XOR new and old.
                                                        return method
                                                                .getRequest() != null
                                                                && (RsdEditingUtil
                                                                        .getMethodsWithRequestPayload()
                                                                        .contains(newValue) ^ RsdEditingUtil
                                                                        .getMethodsWithRequestPayload()
                                                                        .contains(oldValue));
                                                    }

                                                    private boolean cleanResponsePayload(
                                                            Method method,
                                                            HttpMethod oldValue,
                                                            HttpMethod newValue) {
                                                        // XOR new and old.
                                                        return method
                                                                .getRequest() != null
                                                                && (RsdEditingUtil
                                                                        .getMethodsWithResponsePayload()
                                                                        .contains(newValue) ^ RsdEditingUtil
                                                                        .getMethodsWithResponsePayload()
                                                                        .contains(oldValue));
                                                    }
                                                };
                                        if (cmd != null) {
                                            if (cmd.canExecute()) {
                                                if (getEditingDomain() != null
                                                        && getEditingDomain()
                                                                .getCommandStack() != null) {
                                                    getEditingDomain()
                                                            .getCommandStack()
                                                            .execute(cmd);
                                                    /*
                                                     * Update the tabs so that
                                                     * the other sections can be
                                                     * filtered correctly.
                                                     */
                                                    refreshTabs();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });

        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // Commands are handled by the combo viewer selection listener.
        return null;
    }

}
