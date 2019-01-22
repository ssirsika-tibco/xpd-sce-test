/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.ui.components.RsdEditingUtil;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Request section allows to pick the payload's content descriptor (depending on
 * HTTP method type) as well as query and header parameters.
 *
 * @author jarciuch
 * @since 13 Feb 2015
 */
public class RequestSection extends AbstractRsdSection implements
        ISectionContentCreator {

    private static final String HEADER_PARAMS_SECTION = "header_params_section"; //$NON-NLS-1$

    private static final String QUERY_PARAMS_SECTION = "query_params_section"; //$NON-NLS-1$

    private static final String PAYLOAD_SECTION = "payload_section"; //$NON-NLS-1$

    private ScrolledComposite scrolledContainer;

    private ExpandableSectionStacker expandableHeaderController;

    private Control expandablesContainer;

    private RequestJsonPayloadSection payloadSection;

    private RequestQueryParametersSection requestQueryParamsSection;

    private RequestHeaderParametersSection requestHeaderParamsSection;

    /**
     * Creates a new request section.
     */
    public RequestSection() {
        setShouldUseExtraSpace(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof Method) {
            Method method = (Method) input;
            /* Hide/show payload section depending on method type. */
            if (RsdEditingUtil.getMethodsWithRequestPayload()
                    .contains(method.getHttpMethod())) {
                expandableHeaderController.setSectionVisible(PAYLOAD_SECTION,
                        true);
                if (payloadSection != null) {
                    payloadSection.refresh();
                }
            } else {
                expandableHeaderController.setSectionVisible(PAYLOAD_SECTION,
                        false);
            }
            if (requestQueryParamsSection != null) {
                requestQueryParamsSection.refresh();
            }
            if (requestHeaderParamsSection != null) {
                requestHeaderParamsSection.refresh();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        Composite root = toolkit.createComposite(scrolledContainer);
        GridLayout gl = new GridLayout(1, false);
        gl.marginLeft = 0;
        gl.marginWidth = 0;
        root.setLayout(gl);

        String sectPrefId = getSectionContainerType().toString();
        expandableHeaderController = new ExpandableSectionStacker(sectPrefId);

        expandableHeaderController.addSection(PAYLOAD_SECTION,
                Messages.RequestSection_PayloadSection_header,
                SWT.DEFAULT,
                true,
                false);

        expandableHeaderController.addSection(QUERY_PARAMS_SECTION,
                Messages.RequestSection_QueryParamsSection_header,
                100,
                true,
                true);

        expandableHeaderController.addSection(HEADER_PARAMS_SECTION,
                Messages.RequestSection_HeaderParamsSection_header,
                100,
                false,
                true);

        expandablesContainer =
                expandableHeaderController.createExpandableSections(root,
                        toolkit,
                        this);

        GridData gd =
                new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                        | GridData.GRAB_HORIZONTAL);
        expandablesContainer.setLayoutData(gd);

        // set contents of sections on to the scrolled composite
        scrolledContainer.setContent(root);

        Point prefSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        setMinimumHeight(prefSize.y);

        return scrolledContainer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // No need to implement as the commands are handled by subsections.
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        if (PAYLOAD_SECTION.equals(sectionId)) {
            payloadSection = new RequestJsonPayloadSection();
            return payloadSection.createControls(container, toolkit);
        } else if (QUERY_PARAMS_SECTION.equals(sectionId)) {
            requestQueryParamsSection = new RequestQueryParametersSection();
            return requestQueryParamsSection.createControls(container, toolkit);
        } else if (HEADER_PARAMS_SECTION.equals(sectionId)) {
            requestHeaderParamsSection = new RequestHeaderParametersSection();
            return requestHeaderParamsSection
                    .createControls(container, toolkit);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void expandableSectionStateChanged(String sectionId) {
        Point prefSize =
                scrolledContainer.getContent().computeSize(SWT.DEFAULT,
                        SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        setMinimumHeight(prefSize.y);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items); // pre-process the input.

        EObject input = getInput();
        if (input instanceof Method) {
            Method method = (Method) input;

            /* Hide/show payload section depending on method type. */
            if (RsdEditingUtil.getMethodsWithRequestPayload()
                    .contains(method.getHttpMethod())) {

                expandableHeaderController.setSectionVisible(PAYLOAD_SECTION,
                        true);
                if (payloadSection != null) {
                    payloadSection.setInput(items);
                }
            } else {
                expandableHeaderController.setSectionVisible(PAYLOAD_SECTION,
                        false);
            }
            if (requestQueryParamsSection != null) {
                requestQueryParamsSection.setInput(items);
            }
            if (requestHeaderParamsSection != null) {
                requestHeaderParamsSection.setInput(items);
            }
        }
    }
}
