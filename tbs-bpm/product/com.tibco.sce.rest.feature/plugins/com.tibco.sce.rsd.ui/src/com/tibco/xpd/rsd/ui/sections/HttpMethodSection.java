/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.editorHandler.IDisplayEObject;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.ui.JsonSchemaEditorOpener;
import com.tibco.xpd.rsd.Fault;
import com.tibco.xpd.rsd.HttpMethod;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.Request;
import com.tibco.xpd.rsd.Resource;
import com.tibco.xpd.rsd.Response;
import com.tibco.xpd.rsd.Service;
import com.tibco.xpd.rsd.ui.components.JsonTypeReference;
import com.tibco.xpd.rsd.ui.components.RsdEditingUtil;
import com.tibco.xpd.rsd.ui.editor.RsdEditorOpener;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.CommandProvider;
import com.tibco.xpd.ui.properties.CommandRadioButtonFieldHandler;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * This section is for editing RSD method's HTTP method property.
 * 
 * @author jarciuch, sajain
 * @since 2 Feb 2015
 */
public class HttpMethodSection extends AbstractRsdSection {

    /**
     * Request tab when content provider is RSD Editor.
     */
    protected static final String REQUEST_TAB_FOR_RSD_EDITOR_TAB_ID =
            "com.tibco.xpd.rsdeditor.propertyTabs.request"; //$NON-NLS-1$

    /**
     * Request tab when content provider is Project Explorer.
     */
    protected static final String REQUEST_TAB_FOR_PROJECT_EXPLORER_TAB_ID =
            "com.tibco.xpd.rsdexplorer.propertyTabs.request"; //$NON-NLS-1$

    /**
     * Comma.
     */
    private static final String COMA = ", "; //$NON-NLS-1$

    /**
     * Closing bracket.
     */
    private static final String CLOSING_BRACKET = ")"; //$NON-NLS-1$

    /**
     * Opening bracket.
     */
    private static final String OPEN_BRACKET = "("; //$NON-NLS-1$

    /**
     * Slash.
     */
    private static final String SLASH = "/";//$NON-NLS-1$

    /**
     * Question mark.
     */
    private static final String QUESTION_MARK = "?";//$NON-NLS-1$

    /**
     * Initial string representing HOST and PORT in a URL.
     */
    private static final String URL_HOST_AND_PORT_STRING =
            "http(s)://{host}:{port}"; //$NON-NLS-1$

    private Composite groupControl;

    /**
     * Group to enclose 'Request overview' controls.
     */
    private Composite requestOverviewGroup;

    /**
     * URL text box.
     */
    private StyledText urlText;

    /**
     * Headers text box in request overview.
     */
    private Text headersTextInRequestOverview;

    /**
     * Payload label in request overview.
     */
    private Label payloadLabelInRequestOverview;

    /**
     * Payload label in response overview.
     */
    private Label payloadLabelInResponseOverview;

    /**
     * Payload hyperlink in request overview.
     */
    private Hyperlink payloadHyperlinkInRequestOverview;

    /**
     * Group to enclose 'Response overview' controls.
     */
    private Composite responseOverviewGroup;

    /**
     * Headers text box in response overview.
     */
    private Text headersTextInResponseOverview;

    /**
     * Payload hyperlink in response overview.
     */
    private Hyperlink payloadHyperlinkInResponseOverview;

    /**
     * Faults text box in response overview.
     */
    private Text faultsTextInResponseOverview;

    private EnumRadioGroup methodRadioGroup;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {

        for (Notification eachNotification : notifications) {

            if (eachNotification != null) {

                if (eachNotification.getNotifier() instanceof Resource
                        || eachNotification.getNotifier() instanceof Service) {
                    return true;
                }
            }

        }

        return super.shouldRefresh(notifications);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Method) {

            Method method = (Method) input;
            HttpMethod httpMethod = method.getHttpMethod();
            methodRadioGroup.select(httpMethod);

            /*
             * XPD-7248: Saket: Set URL and other fields in response and request
             * overview.
             */
            urlText.setText(getUrl(method));
            highlightParametersInUrl();

            headersTextInRequestOverview
                    .setText(getHeaderTextStringFromRequest(method.getRequest()));

            /*
             * Show payload field in 'Request overview' if the method is either
             * 'PUT' or 'POST', hide it otherwise
             */
            if (HttpMethod.PUT.equals(method.getHttpMethod())
                    || HttpMethod.POST.equals(method.getHttpMethod())) {

                showOrHidePayloadFieldsInRequestOverview(true);

                if (null != method.getRequest()) {

                    PayloadReference payloadRef =
                            method.getRequest().getPayloadReference();

                    payloadHyperlinkInRequestOverview
                            .setText(getPayloadLabel(payloadRef));

                    if (Messages.MethodGeneralSection_PayloadHyperlink_Label
                            .equals(payloadHyperlinkInRequestOverview.getText())) {

                        payloadHyperlinkInRequestOverview.setEnabled(false);

                    } else {

                        payloadHyperlinkInRequestOverview.setEnabled(true);
                    }

                }

            } else {

                showOrHidePayloadFieldsInRequestOverview(false);

            }

            headersTextInResponseOverview
                    .setText(getHeaderTextStringFromResponse(method
                            .getResponse()));

            /*
             * Show payload field in 'Response overview'
             * 
             * Sid XPD-7889 All Methods can have Response Payloads.
             */

            showOrHidePayloadFieldsInResponseOverview(true);

            if (null != method.getResponse()) {

                payloadHyperlinkInResponseOverview
                        .setText(getPayloadLabel(method.getResponse()
                                .getPayloadReference()));

                if (Messages.MethodGeneralSection_PayloadHyperlink_Label
                        .equals(payloadHyperlinkInResponseOverview.getText())) {

                    payloadHyperlinkInResponseOverview.setEnabled(false);

                } else {

                    payloadHyperlinkInResponseOverview.setEnabled(true);
                }
            }

            faultsTextInResponseOverview
                    .setText(getFaultTextStringFromMethod(method));
        }
    }

    /**
     * Show/Hide Payload fields in Request overview.
     * 
     * @param show
     *            Pass <code>true</code> if we want to show the Payload field,
     *            <code>false</code> otherwise.
     */
    private void showOrHidePayloadFieldsInRequestOverview(boolean show) {

        GridData gd1 =
                (GridData) (payloadLabelInRequestOverview.getLayoutData());

        gd1.exclude = !show;

        payloadLabelInRequestOverview.setLayoutData(gd1);

        GridData gd2 =
                (GridData) (payloadHyperlinkInRequestOverview.getLayoutData());

        gd2.exclude = !show;

        payloadHyperlinkInRequestOverview.setLayoutData(gd2);

        payloadLabelInRequestOverview.setVisible(show);
        payloadHyperlinkInRequestOverview.setVisible(show);

        requestOverviewGroup.layout();

        Composite parent1 = requestOverviewGroup.getParent();
        if (null != parent1) {
            requestOverviewGroup.getParent().layout();
        }

        Composite parent2 = parent1.getParent();
        if (null != parent2) {
            requestOverviewGroup.getParent().getParent().layout();

        }

        Composite parent3 = parent2.getParent();
        if (null != parent3) {
            requestOverviewGroup.getParent().getParent().getParent().layout();
        }
    }

    /**
     * Show/Hide Payload fields in Response overview.
     * 
     * @param show
     *            Pass <code>true</code> if we want to show the Payload field,
     *            <code>false</code> otherwise.
     */
    private void showOrHidePayloadFieldsInResponseOverview(boolean show) {

        GridData gd1 =
                (GridData) (payloadLabelInResponseOverview.getLayoutData());

        gd1.exclude = !show;

        payloadLabelInResponseOverview.setLayoutData(gd1);

        GridData gd2 =
                (GridData) (payloadHyperlinkInResponseOverview.getLayoutData());

        gd2.exclude = !show;

        payloadHyperlinkInResponseOverview.setLayoutData(gd2);

        /*
         * Sid XPD-7889. Noticed whilst investigating that this method (iof ever
         * called with show=false) would show the "Payload" label against the
         * "faults" text box. coz we were excluding a section of grid but not
         * hiding the label (used to hide the hyperlink twice here instead of
         * label and hyper
         */
        payloadLabelInResponseOverview.setVisible(show);
        payloadHyperlinkInResponseOverview.setVisible(show);

        responseOverviewGroup.layout();

        Composite parent1 = responseOverviewGroup.getParent();
        if (null != parent1) {
            responseOverviewGroup.getParent().layout();
        }

        Composite parent2 = parent1.getParent();
        if (null != parent2) {
            responseOverviewGroup.getParent().getParent().layout();

        }

        Composite parent3 = parent2.getParent();
        if (null != parent3) {
            responseOverviewGroup.getParent().getParent().getParent().layout();
        }
    }

    /**
     * Get header text string for the specified request.
     * 
     * @param request
     * 
     * @return Header text string for the specified request.
     */
    private String getHeaderTextStringFromRequest(Request request) {

        String headerTextString = new String();

        if (request != null) {
            List<Parameter> allRequestparameters = request.getParameters();

            for (Parameter eachRequestParameter : allRequestparameters) {

                if (ParameterStyle.HEADER.equals(eachRequestParameter
                        .getStyle())) {

                    if (headerTextString.isEmpty()) {

                        headerTextString += eachRequestParameter.getName();

                    } else {

                        headerTextString +=
                                COMA + eachRequestParameter.getName();

                    }
                }
            }
        }

        return headerTextString;
    }

    /**
     * Get header text string for the specified response.
     * 
     * @param response
     * 
     * @return Header text string for the specified response.
     */
    private String getHeaderTextStringFromResponse(Response response) {

        String headerTextString = new String();

        if (response != null) {

            List<Parameter> allRequestparameters = response.getParameters();

            for (Parameter eachRequestParameter : allRequestparameters) {

                if (ParameterStyle.HEADER.equals(eachRequestParameter
                        .getStyle())) {

                    if (headerTextString.isEmpty()) {

                        headerTextString += eachRequestParameter.getName();

                    } else {

                        headerTextString +=
                                COMA + eachRequestParameter.getName();

                    }
                }
            }
        }

        return headerTextString;
    }

    /**
     * Get fault text string for the specified method.
     * 
     * @param method
     * 
     * @return Fault text string for the specified method.
     */
    private String getFaultTextStringFromMethod(Method method) {

        String faultTextString = new String();

        if (method != null) {

            List<Fault> allFaults = method.getFaults();

            for (Fault eachFault : allFaults) {

                if (eachFault != null) {

                    if (faultTextString.isEmpty()) {

                        faultTextString +=
                                ((eachFault.getStatusCode() != null) ? eachFault
                                        .getStatusCode() + " " : "") + OPEN_BRACKET //$NON-NLS-1$ //$NON-NLS-2$
                                        + eachFault.getName() + CLOSING_BRACKET;

                    } else {

                        faultTextString +=
                                COMA
                                        + ((eachFault.getStatusCode() != null) ? eachFault
                                                .getStatusCode() + " " : "") + OPEN_BRACKET //$NON-NLS-1$ //$NON-NLS-2$
                                        + eachFault.getName() + CLOSING_BRACKET;

                    }
                }
            }
        }

        return faultTextString;
    }

    /**
     * Get the payload label.
     * 
     * @param payloadReference
     * 
     * @return The payload label.
     */
    private String getPayloadLabel(PayloadReference payloadReference) {

        String payloadLabel =
                Messages.MethodGeneralSection_PayloadHyperlink_Label;

        if (null != payloadReference) {

            JsonTypeReference jsonReference =
                    JsonTypeReference.getJsonReference(payloadReference);

            if (jsonReference != null) {

                payloadLabel =
                        jsonReference.getLabel(XpdResourcesPlugin.getDefault()
                                .getEditingDomain(), WorkingCopyUtil
                                .getProjectFor(payloadReference));
            }
        }

        return payloadLabel;
    }

    /**
     * Get request URL for the specified method.
     * 
     * @param method
     * 
     * @return Request URL.
     */
    private String getUrl(Method method) {

        String urlString = ""; //$NON-NLS-1$

        /*
         * Method's container must be Resource.
         */
        if (method != null && method.eContainer() instanceof Resource) {

            Resource resource = (Resource) (method.eContainer());

            /*
             * Resource's container must be Service
             */
            if (resource.eContainer() instanceof Service) {

                Service service = (Service) (resource.eContainer());

                urlString = URL_HOST_AND_PORT_STRING;

                String svcContextPath =
                        URI.encodeFragment(service.getContextPath(), false);

                /*
                 * Append Service context path to the URL.
                 */
                if (svcContextPath != null && !svcContextPath.isEmpty()) {

                    if (!urlString.endsWith(SLASH)
                            && !svcContextPath.startsWith(SLASH)) {

                        urlString += SLASH;

                    }

                    urlString += svcContextPath;
                }

                String resPathTemplate = resource.getPathTemplate();
                if (resPathTemplate == null) {
                    resPathTemplate = ""; //$NON-NLS-1$
                }
                resPathTemplate = URI.encodeFragment(resPathTemplate, false);
                resPathTemplate = resPathTemplate.replace("%7B", "{"); //$NON-NLS-1$//$NON-NLS-2$
                resPathTemplate = resPathTemplate.replace("%7D", "}"); //$NON-NLS-1$//$NON-NLS-2$

                /*
                 * Append Resource path template to the URL.
                 */
                if (resPathTemplate != null && !resPathTemplate.isEmpty()) {

                    if (!urlString.endsWith(SLASH)
                            && !resPathTemplate.startsWith(SLASH)) {

                        urlString += SLASH;
                    }

                    urlString += resPathTemplate;

                }

                /*
                 * Append Query parameter string to the URL.
                 */
                Request request = method.getRequest();

                if (request != null) {

                    List<Parameter> allRequestParameters =
                            request.getParameters();

                    if (allRequestParameters != null
                            && !allRequestParameters.isEmpty()
                            && hasQueryParameter(allRequestParameters)) {

                        if (!urlString.endsWith(QUESTION_MARK)) {

                            urlString += QUESTION_MARK;
                        }

                        for (Parameter eachRequestParameter : allRequestParameters) {

                            String name = eachRequestParameter.getName();
                            if (ParameterStyle.QUERY
                                    .equals(eachRequestParameter.getStyle())
                                    && null != name && !name.isEmpty()) {

                                if (urlString.endsWith("}")) { //$NON-NLS-1$

                                    urlString += "&"; //$NON-NLS-1$
                                }

                                urlString += (name + "=" + "{" + name + "}"); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
                            }
                        }
                    }
                }
            }
        }

        return urlString;

    }

    /**
     * Return <code>true</code> if the list of request parameters has atleast
     * one query parameter, <code>false</code> otherwise.
     * 
     * @param allRequestParameters
     * @return <code>true</code> if the list of request parameters has atleast
     *         one query parameter, <code>false</code> otherwise.
     */
    private boolean hasQueryParameter(List<Parameter> allRequestParameters) {

        for (Parameter eachRequestParameter : allRequestParameters) {

            if (ParameterStyle.QUERY.equals(eachRequestParameter.getStyle())
                    && null != eachRequestParameter.getName()
                    && !eachRequestParameter.getName().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Highlight parameters in the urlText by making them bold and blue in
     * color.
     */
    private void highlightParametersInUrl() {

        Color blue = new Color(requestOverviewGroup.getDisplay(), 32, 170, 73);

        StyleRange styleRange = new StyleRange();
        styleRange.fontStyle = SWT.BOLD;
        styleRange.foreground = blue;

        String urlString = urlText.getText();
        int brackerTracker = 0;

        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < urlString.length(); i++) {

            if ('{' == urlString.charAt(i)) {
                brackerTracker++;

                if (brackerTracker == 1) {
                    startIndex = i;
                }
            } else if ('}' == urlString.charAt(i)) {

                brackerTracker--;

                if (brackerTracker == 0) {

                    endIndex = i;

                    styleRange.start = startIndex;
                    styleRange.length = endIndex - startIndex + 1;
                    urlText.setStyleRange(styleRange);
                }
            }
        }
    }

    /**
     * The service containing the specified method.
     * 
     * @param method
     *            The REST Method.
     * @return The service containing the method.
     */
    public Service getService(Method method) {

        Service svc = null;

        Resource resource = getResource(method);

        if (resource != null) {

            Object resourceParent = resource.eContainer();

            if (resourceParent instanceof Service) {
                svc = (Service) resourceParent;
            }

            if (resourceParent instanceof Service) {

                svc = (Service) resourceParent;
            }
        }

        return svc;
    }

    /**
     * The resource containing the specified method.
     * 
     * @param method
     *            The REST Method.
     * @return The resource containing the method.
     */
    public Resource getResource(Method method) {

        Resource res = null;

        if (method != null) {

            Object methodParent = method.eContainer();

            if (methodParent instanceof Resource) {

                res = (Resource) methodParent;
            }
        }

        return res;
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
        methodRadioGroup =
                new EnumRadioGroup(groupControl, toolkit, null,
                        HttpMethod.VALUES, this);
        GridDataFactory.fillDefaults().applyTo(methodRadioGroup.getGroup());

        /*
         * XPD-7248: Saket: Adding request overview group to RSD methods.
         */
        requestOverviewGroup = toolkit.createComposite(root, SWT.BORDER);

        GridLayout gl = new GridLayout(2, false);
        requestOverviewGroup.setLayout(gl);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, false, false);
        gd.horizontalIndent = 93;
        gd.verticalIndent = 10;
        requestOverviewGroup.setLayoutData(gd);

        Label requestOverviewLabel =
                createLabel(requestOverviewGroup,
                        toolkit,
                        Messages.MethodGeneralSection_RequestOverview_label);

        gd = new GridData();
        gd.horizontalSpan = 2;
        requestOverviewLabel.setLayoutData(gd);

        /*
         * URL label and text.
         */
        createLabel(requestOverviewGroup,
                toolkit,
                Messages.MethodGeneralSection_Url_label);

        urlText = new StyledText(requestOverviewGroup, SWT.BORDER | SWT.WRAP);

        gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.heightHint = SWT.DEFAULT;
        gd.widthHint = 30;
        urlText.setLayoutData(gd);

        urlText.setEditable(false);

        /*
         * Container to show the format of the URL.
         */
        Composite urlFormatContainer =
                toolkit.createComposite(requestOverviewGroup);

        getGroupControlLayoutFactory().numColumns(8)
                .applyTo(urlFormatContainer);

        gd =
                new GridData(GridData.BEGINNING,
                        GridData.VERTICAL_ALIGN_BEGINNING, false, false);

        gd.horizontalIndent = 91;
        gd.horizontalSpan = 2;
        urlFormatContainer.setLayoutData(gd);

        /*
         * Participant Endpoint label.
         */
        Label participantEndpointLabel =
                createLabel(urlFormatContainer,
                        toolkit,
                        Messages.MethodGeneralSection_ParticipantEndpoint_Label);

        gd =
                new GridData(GridData.BEGINNING,
                        GridData.VERTICAL_ALIGN_BEGINNING, true, false);

        gd.horizontalSpan = 2;
        participantEndpointLabel.setLayoutData(gd);

        /*
         * Slash label after 'participant endpoint'.
         */
        Label slashLabel1 = createLabel(urlFormatContainer, toolkit, SLASH);

        gd =
                new GridData(GridData.BEGINNING,
                        GridData.VERTICAL_ALIGN_BEGINNING, false, false);

        slashLabel1.setLayoutData(gd);

        Hyperlink contextPathHyperlink =
                toolkit.createHyperlink(urlFormatContainer,
                        Messages.MethodGeneralSection_ContextPath_Label,
                        SWT.NONE);

        contextPathHyperlink.addHyperlinkListener(new HyperlinkAdapter() {

            @Override
            public void linkActivated(HyperlinkEvent e) {

                if (getInput() instanceof Method) {

                    Method method = (Method) getInput();

                    IFile file = WorkingCopyUtil.getFile(method);

                    if (file != null && method != null) {

                        RsdEditorOpener opener = new RsdEditorOpener();

                        IEditorPart editor = opener.openEditor(file);

                        if (editor instanceof IDisplayEObject) {

                            IDisplayEObject go = (IDisplayEObject) editor;

                            go.gotoEObject(true, getService(method));
                        }
                    }
                }

            }
        });

        /*
         * Slash label after context path.
         */
        Label slashLabel2 = createLabel(urlFormatContainer, toolkit, SLASH);

        gd =
                new GridData(GridData.BEGINNING,
                        GridData.VERTICAL_ALIGN_BEGINNING, false, false);

        slashLabel2.setLayoutData(gd);

        Hyperlink pathTemplateHyperlink =
                toolkit.createHyperlink(urlFormatContainer,
                        Messages.MethodGeneralSection_PathTemplate_Label,
                        SWT.NONE);

        pathTemplateHyperlink.addHyperlinkListener(new HyperlinkAdapter() {

            @Override
            public void linkActivated(HyperlinkEvent e) {

                if (getInput() instanceof Method) {

                    Method method = (Method) getInput();

                    IFile file = WorkingCopyUtil.getFile(method);

                    if (file != null && method != null) {

                        RsdEditorOpener opener = new RsdEditorOpener();

                        IEditorPart editor = opener.openEditor(file);

                        if (editor instanceof IDisplayEObject) {

                            IDisplayEObject go = (IDisplayEObject) editor;

                            go.gotoEObject(true, getResource(method));
                        }
                    }
                }

            }
        });

        /*
         * Question mark label after path template.
         */
        Label questionMarkLabel =
                createLabel(urlFormatContainer, toolkit, QUESTION_MARK);

        gd =
                new GridData(GridData.BEGINNING,
                        GridData.VERTICAL_ALIGN_BEGINNING, false, false);

        questionMarkLabel.setLayoutData(gd);

        Hyperlink queryParametersHyperlink =
                toolkit.createHyperlink(urlFormatContainer,
                        Messages.MethodGeneralSection_QueryParameters_Label,
                        SWT.NONE);

        queryParametersHyperlink.addHyperlinkListener(new IHyperlinkListener() {

            @Override
            public void linkActivated(HyperlinkEvent e) {

                /*
                 * Will have to attempt opening both the request tabs (one for
                 * whom content provider is RSD editor and the other whose is
                 * the Project explorer). Because at this point we don't know
                 * whether the method (which is the input) is selected in the
                 * RSD editor or in the project explorer.
                 */
                showPropertyTab(REQUEST_TAB_FOR_RSD_EDITOR_TAB_ID);
                showPropertyTab(REQUEST_TAB_FOR_PROJECT_EXPLORER_TAB_ID);
            }

            @Override
            public void linkEntered(HyperlinkEvent e) {
                // Do nothing
            }

            @Override
            public void linkExited(HyperlinkEvent e) {
                // Do nothing
            }

        });

        /*
         * Headers label and text (request overview).
         */
        createLabel(requestOverviewGroup,
                toolkit,
                Messages.MethodGeneralSection_Headers_label);

        headersTextInRequestOverview =
                toolkit.createText(requestOverviewGroup, "", SWT.WRAP); //$NON-NLS-1$

        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).hint(30, SWT.DEFAULT)
                .applyTo(headersTextInRequestOverview);

        headersTextInRequestOverview.setEditable(false);

        /*
         * Payload label and text (request overview).
         */
        payloadLabelInRequestOverview =
                createLabel(requestOverviewGroup,
                        toolkit,
                        Messages.MethodGeneralSection_Payload_label);

        gd = new GridData();
        payloadLabelInRequestOverview.setLayoutData(gd);

        payloadHyperlinkInRequestOverview =
                toolkit.createHyperlink(requestOverviewGroup,
                        Messages.MethodGeneralSection_PayloadHyperlink_Label,
                        SWT.NONE);

        payloadHyperlinkInRequestOverview
                .addHyperlinkListener(new IHyperlinkListener() {

                    @Override
                    public void linkActivated(HyperlinkEvent e) {

                        Method method = (Method) getInput();

                        if (null != method.getRequest()) {

                            PayloadReference payloadRef =
                                    method.getRequest().getPayloadReference();

                            if (null != payloadRef) {

                                JsonTypeReference jsonReference =
                                        JsonTypeReference
                                                .getJsonReference(method
                                                        .getRequest()
                                                        .getPayloadReference());

                                if (jsonReference != null) {

                                    assert payloadRef.eContainer() != null : "Reference container must be set."; //$NON-NLS-1$

                                    Classifier classifier =
                                            jsonReference
                                                    .resolveReference(XpdResourcesPlugin
                                                            .getDefault()
                                                            .getEditingDomain(),
                                                            WorkingCopyUtil
                                                                    .getProjectFor(payloadRef
                                                                            .eContainer()));

                                    if (classifier != null
                                            && classifier.eResource() != null) {

                                        IFile file =
                                                WorkingCopyUtil
                                                        .getFile(classifier);

                                        IEditorPart editor =
                                                new JsonSchemaEditorOpener()
                                                        .openEditor(file);

                                        if (editor instanceof IDisplayEObject) {

                                            IDisplayEObject go =
                                                    (IDisplayEObject) editor;

                                            go.gotoEObject(true, classifier);
                                        }
                                    }

                                }
                            }

                        }

                    }

                    @Override
                    public void linkEntered(HyperlinkEvent e) {
                        // Do nothing
                    }

                    @Override
                    public void linkExited(HyperlinkEvent e) {
                        // Do nothing
                    }

                });

        gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.widthHint = 30;
        gd.heightHint = SWT.DEFAULT;

        payloadHyperlinkInRequestOverview.setLayoutData(gd);

        manageControlUpdateOnDeactivate(urlText);
        manageControlUpdateOnDeactivate(headersTextInRequestOverview);

        /*
         * XPD-7248: Saket: Adding response overview group to RSD methods.
         */
        responseOverviewGroup = toolkit.createComposite(root, SWT.BORDER);

        gl = new GridLayout(2, false);
        responseOverviewGroup.setLayout(gl);

        gd = new GridData(SWT.FILL, SWT.TOP, false, false);
        gd.horizontalIndent = 93;
        gd.verticalIndent = 15;
        responseOverviewGroup.setLayoutData(gd);

        Label responseOverviewLabel =
                createLabel(responseOverviewGroup,
                        toolkit,
                        Messages.MethodGeneralSection_ResponseOverview_label);

        gd = new GridData();
        gd.horizontalSpan = 2;
        responseOverviewLabel.setLayoutData(gd);

        /*
         * Headers label and text (response overview).
         */
        createLabel(responseOverviewGroup,
                toolkit,
                Messages.MethodGeneralSection_Headers_label);

        headersTextInResponseOverview =
                toolkit.createText(responseOverviewGroup, "", SWT.WRAP); //$NON-NLS-1$

        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).hint(30, SWT.DEFAULT)
                .applyTo(headersTextInResponseOverview);

        headersTextInResponseOverview.setEditable(false);

        /*
         * Payload label and text (response overview).
         */
        payloadLabelInResponseOverview =
                createLabel(responseOverviewGroup,
                        toolkit,
                        Messages.MethodGeneralSection_Payload_label);

        gd = new GridData();
        payloadLabelInResponseOverview.setLayoutData(gd);

        payloadHyperlinkInResponseOverview =
                toolkit.createHyperlink(responseOverviewGroup,
                        Messages.MethodGeneralSection_PayloadHyperlink_Label,
                        SWT.NONE);

        payloadHyperlinkInResponseOverview
                .addHyperlinkListener(new IHyperlinkListener() {

                    @Override
                    public void linkActivated(HyperlinkEvent e) {

                        Method method = (Method) getInput();

                        if (null != method.getResponse()) {

                            PayloadReference payloadRef =
                                    method.getResponse().getPayloadReference();

                            if (null != payloadRef) {

                                JsonTypeReference jsonReference =
                                        JsonTypeReference
                                                .getJsonReference(method
                                                        .getResponse()
                                                        .getPayloadReference());

                                if (jsonReference != null) {

                                    assert payloadRef.eContainer() != null : "Reference container must be set."; //$NON-NLS-1$

                                    Classifier classifier =
                                            jsonReference
                                                    .resolveReference(XpdResourcesPlugin
                                                            .getDefault()
                                                            .getEditingDomain(),
                                                            WorkingCopyUtil
                                                                    .getProjectFor(payloadRef
                                                                            .eContainer()));

                                    if (classifier != null
                                            && classifier.eResource() != null) {

                                        IFile file =
                                                WorkingCopyUtil
                                                        .getFile(classifier);

                                        IEditorPart editor =
                                                new JsonSchemaEditorOpener()
                                                        .openEditor(file);

                                        if (editor instanceof IDisplayEObject) {

                                            IDisplayEObject go =
                                                    (IDisplayEObject) editor;

                                            go.gotoEObject(true, classifier);
                                        }
                                    }

                                }
                            }

                        }

                    }

                    @Override
                    public void linkEntered(HyperlinkEvent e) {
                        // Do nothing
                    }

                    @Override
                    public void linkExited(HyperlinkEvent e) {
                        // Do nothing
                    }

                });

        gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.widthHint = 30;
        gd.heightHint = SWT.DEFAULT;

        payloadHyperlinkInResponseOverview.setLayoutData(gd);

        /*
         * Faults label and text (response overview).
         */
        createLabel(responseOverviewGroup,
                toolkit,
                Messages.MethodGeneralSection_Faults_label);

        faultsTextInResponseOverview =
                toolkit.createText(responseOverviewGroup, "", SWT.WRAP); //$NON-NLS-1$

        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.TOP)
                .grab(true, false).hint(30, SWT.DEFAULT)
                .applyTo(faultsTextInResponseOverview);

        faultsTextInResponseOverview.setEditable(false);

        manageControlUpdateOnDeactivate(headersTextInResponseOverview);
        manageControlUpdateOnDeactivate(faultsTextInResponseOverview);

        return root;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Command doGetCommand(Object obj) {
        EObject input = getInput();
        Command cmd = null;
        if (input instanceof Method) {
            final Method method = (Method) input;
            Enumerator element = methodRadioGroup.getEnumFormButton(obj);

            if (element instanceof HttpMethod) {
                final HttpMethod newValue = (HttpMethod) element;
                final HttpMethod oldValue = method.getHttpMethod();

                if (!newValue.equals(oldValue)) {
                    cmd =
                            new RecordingCommand(
                                    (TransactionalEditingDomain) getEditingDomain(),
                                    Messages.MethodGeneralSection_SetHttpMethodCmd_label) {
                                @Override
                                protected void doExecute() {

                                    method.setHttpMethod(newValue);

                                    /*
                                     * Remove request/response payload ref. if
                                     * method's payload is not supported.
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
                                        Method method, HttpMethod oldValue,
                                        HttpMethod newValue) {
                                    // XOR new and old.
                                    return method.getRequest() != null
                                            && (RsdEditingUtil
                                                    .getMethodsWithRequestPayload()
                                                    .contains(newValue) ^ RsdEditingUtil
                                                    .getMethodsWithRequestPayload()
                                                    .contains(oldValue));
                                }

                                private boolean cleanResponsePayload(
                                        Method method, HttpMethod oldValue,
                                        HttpMethod newValue) {
                                    // XOR new and old.
                                    return method.getRequest() != null
                                            && (RsdEditingUtil
                                                    .getMethodsWithResponsePayload()
                                                    .contains(newValue) ^ RsdEditingUtil
                                                    .getMethodsWithResponsePayload()
                                                    .contains(oldValue));
                                }
                            };
                }
            }
        }
        return cmd;
    }

    /**
     * Class to create and manage a group of radio button based on EMF
     * Enumeration type.
     * 
     * @author jarciuch
     * @since 10 Mar 2015
     */
    private static class EnumRadioGroup {

        private int numOfColumns = 4;

        private Composite group;

        private HashMap<String, Button> buttons = new HashMap<>();

        private Enumerator currentSelection = null;

        /**
         * Creates a radio buttons group for the list of Enumerations.
         * 
         * @param parent
         *            partnt of the group.
         * @param toolkit
         *            the xpd toolkit.
         * @param label
         *            the label for the group (if <code>null</code> then a
         *            {@link Composite} will be created instead of {@link Group}
         *            ).
         * @param enumerators
         *            a list of {@link Enumerator} (usually obtained from the
         *            EnumType.VALUES)
         * @param commadProvider
         *            the command provider (usually AbstractXpdSection) will be
         *            responsible for a command creation. Use: (
         *            {@link #getEnumFormButton(Object)} to obtain Enumeration
         *            from the context control.
         */
        public EnumRadioGroup(Composite parent, XpdFormToolkit toolkit,
                String label, List<? extends Enumerator> enumerators,
                CommandProvider commadProvider) {
            if (label != null) {
                group = toolkit.createGroup(parent, label);
            } else {
                group = toolkit.createComposite(parent);
            }
            GridLayoutFactory.swtDefaults().numColumns(numOfColumns)
                    .applyTo(group);
            for (Enumerator en : enumerators) {
                Button b =
                        toolkit.createButton(group,
                                en.getName(),
                                SWT.RADIO,
                                "radio_" + en.getLiteral()); //$NON-NLS-1$
                b.setLayoutData(new GridData());
                b.setData(en);
                buttons.put(en.getLiteral(), b);

                // registering button for the event handing with the command
                // provider.
                new CommandRadioButtonFieldHandler(b, commadProvider);
            }
        }

        /**
         * Selects a button corresponding to specified enumerator within this
         * group.
         * 
         * @param en
         *            enumerator to select.
         */
        public void select(Enumerator en) {
            if (en != null) {
                Button b = buttons.get(en.getLiteral());
                if (b != null && !b.isDisposed()) {
                    b.setSelection(true);
                }
                for (Button db : buttons.values()) {
                    if (db != b && db != null && !db.isDisposed()) {
                        db.setSelection(false);
                    }
                }
            } else {
                if (currentSelection != null) {
                    for (Button db : buttons.values()) {
                        if (db != null && !db.isDisposed()) {
                            db.setSelection(false);
                        }
                    }
                }
            }
            currentSelection = en;
        }

        /**
         * Returns the Enumeration for an object if the object is a radio button
         * contained in this group.
         * 
         * @param o
         *            the context object (usually passed in the
         *            doGetCommand(Object o)
         * @return the Enumeration for an object if the object is a radio button
         *         contained in this group or <code>null</code> otherwise.
         */
        public Enumerator getEnumFormButton(Object o) {
            if (o instanceof Button) {
                Button b = (Button) o;
                if (b != null && !b.isDisposed() && b.getSelection()
                        && buttons.values().contains(b)) {
                    return (Enumerator) b.getData();
                }
            }
            return null;
        }

        /**
         * Returns the group control.
         */
        public Composite getGroup() {
            return group;
        }

    }
}
