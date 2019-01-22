/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.implementer.script.EventMessageAdapter;
import com.tibco.xpd.processeditor.xpdl2.properties.event.EventImplementationSection;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Properties Section for Web Service Implementation of Message Event Triggers.
 * The web service implementation is the default and supported by XPDL2
 * therefore we can use the standard event adaptor to access the model.
 * 
 * @author scrossle
 * 
 */
public class EventMessageImplSection extends
        AbstractFilteredTransactionalSection implements
        EventImplementationSection {
    protected static final String WEB_SERVICE_IMPL_NAME = "Web Service"; //$NON-NLS-1$

    protected static final String BW_SERVICE_IMPL_NAME = "BW Service"; //$NON-NLS-1$

    protected static final String UNSPECIFIED_IMPLEMENTATION_NAME =
            "Unspecified"; //$NON-NLS-1$

    private boolean isBWType;

    protected CCombo impCombo;

    private String previousType;

    private Composite root;

    private ScrolledComposite scrolledContainer;

    private AbstractXpdSection webServiceDetailsSection;

    protected Composite webServiceDetailsContainer;

    protected ScrolledPageBook detailsPageBook;

    /**
     * Constructor to set adaptor to model.
     */
    public EventMessageImplSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public EventMessageImplSection(EClass filterClass) {
        super(filterClass);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        /*
         * Have to set up webServiceDetailsSection with part and selection
         * rather than just teh slection items (as we used to do in
         * setInput(Collection items) below).
         * 
         * If we do not then we do not initliase the complete life cycle of teh
         * web-service details section and this can have adverse side-effects
         * (such as refreshTabs() will not work and actually throw exception).
         */
        super.setInput(part, selection);

        if (webServiceDetailsSection != null) {
            webServiceDetailsSection.setInput(getPart(), getSelection());
        }

    }

    @Override
    public void setInput(Collection items) {
        super.setInput(items);

        /*
         * Sid: This caused a timing-related intermittent thread lock when WSDL
         * is being generated at same time as message event selection
         * 
         * This was put in in 2008 probably before wsdl indexing and numerous
         * other changes so Nilay and I pretty sure it's superfluous!
         */
        // Activity act = getActivityInput();
        // if (act != null) {
        // // Force WSDL file to load.
        // IFile file = Xpdl2WsdlUtil.getWsdlFile(act);
        // if (file != null) {
        // WorkingCopy wc =
        // XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        // if (wc != null) {
        // wc.getRootElement();
        // }
        // }
        // }
    }

    /**
     * @param act
     * @return
     */
    private ActivityMessageProvider getActivityMessageProvider(Activity act) {
        ActivityMessageProvider activityMessage =
                ActivityMessageProviderFactory.INSTANCE.getMessageProvider(act,
                        isBWType);
        if (activityMessage == null) {
            activityMessage = new EventMessageAdapter();
        }
        return activityMessage;
    }

    /**
     * Ceraste the controls.
     * 
     * @param parent
     *            The container.
     * @param toolkit
     *            The form toolkit.
     * @return Coomposite with controls.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls
     *      (org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit tk) {
        // create scrolled composite so the section details never get lost on
        // the page
        scrolledContainer =
                tk.createScrolledComposite(parent, SWT.V_SCROLL | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        root = tk.createComposite(scrolledContainer);
        isBWType = false;

        GridLayout gLayout = new GridLayout(2, false);
        gLayout.marginHeight = 10;
        gLayout.marginWidth = 2;
        gLayout.verticalSpacing = 5;
        root.setLayout(gLayout);

        tk.createLabel(root,
                Messages.EventMessageImplSection_Implementation_label,
                SWT.NONE);

        impCombo = tk.createCCombo(root, SWT.SINGLE);
        impCombo.setData(TabbedPropertySheetWidgetFactory.KEY_DRAW_BORDER,
                TabbedPropertySheetWidgetFactory.TEXT_BORDER);
        impCombo.setEditable(false);
        GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        // gridData.horizontalSpan = 3;
        impCombo.setLayoutData(gridData);

        /*
         * BW Service is only supported by iProcess and iProcess only supports
         * it for ServiceTask. so there is no point in providing it as an option
         * for events.
         */
        impCombo.setItems(getImplementations());
        impCombo.setText(Messages.Web_Service_Impl_Name);

        impCombo.setData(Messages.Web_Service_Impl_Name, WEB_SERVICE_IMPL_NAME);
        /*
         * impCombo.setData(Messages.BW_Service_Impl_Name,
         * BW_SERVICE_IMPL_NAME);
         */
        impCombo.setData(Messages.Unspecified_Impl_Name,
                UNSPECIFIED_IMPLEMENTATION_NAME);

        manageControl(impCombo);

        detailsPageBook = tk.createPageBook(root, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, true).span(2, 1)
                .applyTo(detailsPageBook);

        //
        // Create the web service details section.
        webServiceDetailsSection = createWebServiceDetailsSection();

        webServiceDetailsContainer =
                tk.createComposite(detailsPageBook.getContainer());
        webServiceDetailsContainer.setLayout(new FillLayout());
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        webServiceDetailsContainer.setLayoutData(gd);

        webServiceDetailsSection.createControls(webServiceDetailsContainer,
                getPropertySheetPage());
        detailsPageBook.registerPage(WEB_SERVICE_IMPL_NAME,
                webServiceDetailsContainer);

        // set contents of sections on to the scrolled composite
        scrolledContainer.setContent(root);

        setScrolledContainerMinSize();

        return scrolledContainer;

    }

    /**
     * @return
     */
    protected String[] getImplementations() {
        return new String[] { Messages.Web_Service_Impl_Name,
        /* Messages.BW_Service_Impl_Name, */
        Messages.Unspecified_Impl_Name };
    }

    /**
     * Calculates and sets the min height for the scrolled container.
     */
    private void setScrolledContainerMinSize() {
        Point baseSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        Point wsSize =
                webServiceDetailsContainer
                        .computeSize(SWT.DEFAULT, SWT.DEFAULT);
        Point min =
                new Point(Math.max(baseSize.x, wsSize.x), baseSize.y + wsSize.y);

        scrolledContainer.setMinSize(min);
    }

    /**
     * Create the web service details section.
     * 
     * @return the web service details section.
     */
    protected AbstractXpdSection createWebServiceDetailsSection() {
        AbstractXpdSection section =
                new APIReqActivitiesWebServiceSectionStacker() {
                    /**
                     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.APIReqActivitiesWebServiceSectionStacker#expandableSectionStateChanged(java.lang.String)
                     * 
                     * @param sectionId
                     */
                    @Override
                    public void expandableSectionStateChanged(String sectionId) {

                        super.expandableSectionStateChanged(sectionId);
                        /*
                         * XPD-7396: Compute and set the min size of the
                         * scrolled container after the expandable section state
                         * is changed.
                         */
                        setScrolledContainerMinSize();

                    }
                };
        return section;
    }

    /**
     * Get command to change something.
     * 
     * @param obj
     *            Object that generated notification.
     * @return Command to change model.
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command command = null;
        EditingDomain ed = getEditingDomain();

        if (obj == impCombo) {
            String dataText = (String) impCombo.getData(impCombo.getText());

            if (!dataText.equals(previousType)) {

                String implementationTypeName =
                        ImplementationType.UNSPECIFIED_LITERAL.getName();
                String extImplementationType = null;

                if (dataText.equals(WEB_SERVICE_IMPL_NAME)) {
                    implementationTypeName =
                            ImplementationType.WEB_SERVICE_LITERAL.getName();
                    extImplementationType = implementationTypeName;
                } else if (dataText.equals(BW_SERVICE_IMPL_NAME)) {
                    implementationTypeName =
                            ImplementationType.WEB_SERVICE_LITERAL.getName();
                    extImplementationType = BW_SERVICE_IMPL_NAME;
                } else if (dataText
                        .equals(RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME)) {
                    implementationTypeName =
                            ImplementationType.OTHER_LITERAL.getName();
                    extImplementationType =
                            RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME;
                }

                command =
                        EventObjectUtil.getChangeEventImplementationCommand(ed,
                                getActivityInput(),
                                extImplementationType,
                                implementationTypeName);
                isBWType = false;
                if (dataText.equals(BW_SERVICE_IMPL_NAME)) {
                    isBWType = true;
                }

                refreshTabs();
            }
        }
        return command;
    }

    /**
     * Respond to a change.
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        // If controls are disposed then unregister
        if (impCombo.isDisposed()) {
            return;
        }

        Activity act = getActivityInput();
        if (act != null) {

            ActivityMessageProvider activityMessage =
                    getActivityMessageProvider(act);

            ImplementationType currImplType =
                    activityMessage.getImplementation(act);
            String extImplementationType =
                    (String) Xpdl2ModelUtil.getOtherAttribute(act.getEvent(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());

            isBWType = isBwType(act);

            if (currImplType.equals(ImplementationType.WEB_SERVICE_LITERAL)) {
                detailsPageBook.showPage(WEB_SERVICE_IMPL_NAME);

                if (isBWType) {
                    impCombo.setText(Messages.BW_Service_Impl_Name);
                } else {
                    impCombo.setText(Messages.Web_Service_Impl_Name);
                }

            } else if (currImplType.equals(ImplementationType.OTHER_LITERAL)
                    && RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME
                            .equals(extImplementationType)) {
                detailsPageBook
                        .showPage(RestServiceTaskAdapter.REST_SERVICE_IMPL_NAME);

                impCombo.setText(Messages.Rest_Service_Impl_Name);
            } else {
                detailsPageBook.showEmptyPage();
                impCombo.setText(Messages.Unspecified_Impl_Name);
            }

            previousType = (String) impCombo.getData(impCombo.getText());

            //
            // If this is a reply to upstream request activity then disable the
            // controls because the request activity defines the operation etc.
            //
            boolean isReply = ReplyActivityUtil.isReplyActivity(act);
            impCombo.setEnabled(!isReply);

            if (isReply) {
                String implLabel = impCombo.getText();

                String reqLabel =
                        ReplyActivityUtil
                                .getRequestActivityLabel(ReplyActivityUtil
                                        .getRequestActivityForReplyActivity(act));
                if (reqLabel == null || reqLabel.length() == 0) {
                    reqLabel =
                            Messages.EventMessageImplSection_UndefinedRequestActivity_label;
                }

                impCombo.setText(implLabel
                        + ": " + String.format(Messages.EventMessageImplSection_SetByRequestActivity_label, reqLabel)); //$NON-NLS-1$
            }

        }

        //
        // If this is a reply to upstream request activity then disable the
        // controls because the request activity defines the operation etc.
        //
        if (act != null
                && (ReplyActivityUtil.isReplyActivity(act) || ProcessInterfaceUtil
                        .isEventImplemented(act))) {
            impCombo.setEnabled(false);
        } else {
            impCombo.setEnabled(true);
        }

        //
        // Refresh the details section.
        webServiceDetailsSection.refresh();

        return;
    }

    private boolean isBwType(Activity act) {
        boolean isBw = false;
        Event event = act.getEvent();
        if (event != null) {
            Object other =
                    Xpdl2ModelUtil.getOtherAttribute(event,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            if (BW_SERVICE_IMPL_NAME.equals(other)) {
                isBw = true;
            }
        }
        return isBw;
    }

    /**
     * Correct type for section input.
     * 
     * @return The activity.
     */
    private Activity getActivityInput() {
        if (getInput() instanceof Activity) {
            return (Activity) getInput();
        }
        return null;
    }

    @Override
    public String getLocalId() {
        return "developer.EventMessageImpl"; //$NON-NLS-1$;
    }

    @Override
    public String getPluginId() {
        return com.tibco.xpd.implementer.resources.xpdl2.Activator.PLUGIN_ID;
    }

}
