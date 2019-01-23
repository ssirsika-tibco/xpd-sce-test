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
 * Response section allows to pick the payload's content descriptor (depending
 * on HTTP method type) as well as response header parameters.
 *
 * @author jarciuch
 * @since 13 Feb 2015
 */
public class ResponseSection extends AbstractRsdSection implements
        ISectionContentCreator {

    private static final String PAYLOAD_SECTION = "payload_section"; //$NON-NLS-1$

    private static final String HEADER_PARAMS_SECTION = "header_params_section"; //$NON-NLS-1$

    private ScrolledComposite scrolledContainer;

    private ExpandableSectionStacker expandableHeaderController;

    private Control expandablesContainer;

    private ResponseJsonPayloadSection payloadSection;

    private ResponseHeaderParametersSection responseHeaderParamsSection;

    /**
     * Creates a new response section.
     */
    public ResponseSection() {
        setShouldUseExtraSpace(true);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     *
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (input instanceof Method) {
            Method method = (Method) input;
            /* Hide/show payload section depending on method type. */
            if (RsdEditingUtil.getMethodsWithResponsePayload()
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
            if (responseHeaderParamsSection != null) {
                responseHeaderParamsSection.refresh();
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     *
     * @param parent
     * @param toolkit
     * @return
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
                Messages.ResponseSection_PayloadSection_header,
                SWT.DEFAULT,
                true,
                false);

        expandableHeaderController.addSection(HEADER_PARAMS_SECTION,
                Messages.ResponseSection_HeaderParamsSection_header,
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
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     *
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // No need to implement as the commands are handled by subsections.
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#createExpandableSectionContent(java.lang.String,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     *
     * @param sectionId
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        if (PAYLOAD_SECTION.equals(sectionId)) {
            payloadSection = new ResponseJsonPayloadSection();
            return payloadSection.createControls(container, toolkit);
        } else if (HEADER_PARAMS_SECTION.equals(sectionId)) {
            responseHeaderParamsSection = new ResponseHeaderParametersSection();
            return responseHeaderParamsSection.createControls(container,
                    toolkit);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     *
     * @param sectionId
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
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items); // pre-process the input.

        EObject input = getInput();
        if (input instanceof Method) {
            Method method = (Method) input;

            /* Hide/show payload section depending on method type. */
            if (RsdEditingUtil.getMethodsWithResponsePayload()
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
            if (responseHeaderParamsSection != null) {
                responseHeaderParamsSection.setInput(items);
            }
        }
    }
}
