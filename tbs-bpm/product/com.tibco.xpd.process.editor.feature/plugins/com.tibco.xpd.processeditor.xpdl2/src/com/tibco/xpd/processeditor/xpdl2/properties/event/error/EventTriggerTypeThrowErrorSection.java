/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event.error;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessFeaturesUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.IInternalFaultMessageNameProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.task.ConfigureReplyActivitySection.IncomingRequestActivityEntry;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessDialogUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.util.RevealProcessDiagramEObject;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * EventTriggerTypeErrorSection
 * 
 * 
 * @author (previous scrossle) Major update aallway
 * @since (previousl 2.0) Major update. 3.3 (25 Nov 2009)
 */
public class EventTriggerTypeThrowErrorSection extends
        AbstractFilteredTransactionalSection implements ISection {

    private Composite root;

    private boolean firstRefreshSinceSetInput = false;

    /*
     * Throw Process / Sub-Process Error controls...
     */
    private Button tpeRadioButton;

    private Composite tpeContainer;

    private DecoratedField tpeErrorCodeText;

    private CLabel tpeErrorCodeLabel;

    private Set<String> existingErrorCodes = null;

    /*
     * Throw Incoming Message Request Fault controls...
     */
    private Button tfmRadioButton;

    private Composite tfmContainer;

    private CLabel tfmReqActLabel;

    private DecoratedField tfmSelectRequestActivityText;

    private ArrayList<IncomingRequestActivityEntry> incomingRequestActivityList;

    private Button tfmGotoReqActButton;

    private PageBook tfmPageBook;

    /* Controls for throw error for auto-gen operation */
    private Composite tfmForAutoGenPage;

    private CLabel tfmForAutoGenErrorCodeLabel;

    private DecoratedField tfmForAutoGenErrorCodeText;

    /* Controls for throw error for user-defined operation */
    private Composite tfmForUserDefOpPage;

    private CLabel tfmForUserDefOpSelectFaultLabel;

    private DecoratedField tfmForUserDefOpFaultText;

    private Set<String> faultMessageNames = null;

    /* Controls for blank page in tfmPageBook. */
    private Composite tfmEmptyPage;

    /*
     * Controls for "Cannot pick faults in Business Analyst mode" page in
     * tfmPageBook.
     */
    private Composite tfmCantPickFaultInBizAnalystModePage;

    private FormText tfmEnableSolutionDesign;

    private static final String ENABLE_SOLUTION_DESIGN_HYPERLINK =
            "enableSolutionDesignCapability"; //$NON-NLS-1$

    private String enableSolutionDesignText =
            "<form><p vspace='false'><a href='" //$NON-NLS-1$
                    + ENABLE_SOLUTION_DESIGN_HYPERLINK
                    + "'>" //$NON-NLS-1$
                    + Messages.EventTriggerTypeErrorSection_TurnOnSolutionDesign_hyperlink
                    + "</a></p></form>"; //$NON-NLS-1$

    private Control tfmPageBookCurrentPage;

    /**
     * Construct
     */
    public EventTriggerTypeThrowErrorSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public EventTriggerTypeThrowErrorSection(String intrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java
     * .util.Collection)
     */
    @Override
    public void setInput(Collection<?> items) {
        if (items != null && items.size() > 0) {
            Object input = getBaseSelectObject(items.iterator().next());
            if (input != null && !input.equals(getInput())) {
                firstRefreshSinceSetInput = true;
            }
        }

        super.setInput(items);

        return;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(1, false));

        /*
         * Create the "Throw Process / Sub-Process Error" controls.
         */
        tpeRadioButton =
                toolkit
                        .createButton(root,
                                Messages.EventTriggerTypeErrorSection_ThrowProcessError_button,
                                SWT.RADIO);
        tpeRadioButton.setLayoutData(new GridData());
        manageControl(tpeRadioButton);

        tpeContainer = doCreateThrowProcessErrorControls(root, toolkit);
        expandCollapseContainer(tpeContainer, true);

        /*
         * Create the "Throw Incoming Message Request Fault"
         */
        tfmRadioButton =
                toolkit
                        .createButton(root,
                                Messages.EventTriggerTypeErrorSection_ThrowFaultMessage_button,
                                SWT.RADIO);
        tfmRadioButton.setLayoutData(new GridData());
        manageControl(tfmRadioButton);

        tfmContainer = doCreateThrowFaultMessageErrorControls(root, toolkit);
        expandCollapseContainer(tfmContainer, false);

        return root;
    }

    /**
     * Create controls for when the "Throw Incoming Message Request Fault" is
     * selected
     * 
     * @param parent
     * @param toolkit
     * 
     * @return controls for when the "Throw Incoming Message Request Fault" is
     *         selected
     */
    private Composite doCreateThrowFaultMessageErrorControls(Composite parent,
            XpdFormToolkit toolkit) {

        Composite container = toolkit.createComposite(parent, SWT.NONE);
        GridLayout gl = new GridLayout(3, false);
        gl.marginLeft = 13;
        gl.marginBottom = gl.marginHeight;
        gl.marginHeight = 0;
        container.setLayout(gl);

        /* Select request activity controls... */
        tfmReqActLabel =
                toolkit
                        .createCLabel(container,
                                Messages.EventTriggerTypeErrorSection_RequestActivity_label);
        tfmReqActLabel.setLayoutData(new GridData());

        /*
         * Create the select request activity content assisted field.
         */
        tfmSelectRequestActivityText =
                createContentAssistRequestActivityField(container, toolkit);

        tfmSelectRequestActivityText.getLayoutControl()
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        //
        // And the goto button
        tfmGotoReqActButton =
                toolkit.createButton(container,
                        Messages.GotoButton_Label,
                        SWT.PUSH);
        tfmGotoReqActButton.setLayoutData(new GridData());
        manageControl(tfmGotoReqActButton);
        tfmGotoReqActButton
                .setToolTipText(Messages.ConfigureReplyActivitySection_GotoRequestActivity_tooltip);

        /*
         * Controls for select fault or enter error code depending on whether
         * user-defined operation or auto-generated operation for selected
         * request activity.
         */
        int maxPageHeight = 0;
        Point sz;

        /* create a composite with lay that negates pagebooklayout's indent. */
        Composite cmp = toolkit.createComposite(container);
        gl = new GridLayout(1, false);
        gl.marginRight = -gl.marginWidth;
        gl.marginLeft = -gl.marginWidth;
        gl.marginBottom = -gl.marginHeight;
        gl.marginHeight = 0;
        cmp.setLayout(gl);

        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        cmp.setLayoutData(gd);

        tfmPageBook = new PageBook(cmp, SWT.NONE);
        tfmPageBook.setBackground(ColorConstants.green);

        /* Empty page. */
        tfmEmptyPage = toolkit.createComposite(tfmPageBook, SWT.NONE);
        tfmEmptyPage.setLayout(createNoBorderComposite(1, false));

        sz = tfmEmptyPage.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        if (sz.y > maxPageHeight) {
            maxPageHeight = sz.y;
        }

        /*
         * Cannot pick faults in Business Analyst mode page in
         */
        tfmCantPickFaultInBizAnalystModePage =
                toolkit.createComposite(tfmPageBook, SWT.NONE);
        tfmCantPickFaultInBizAnalystModePage
                .setLayout(new GridLayout(1, false));//createNoBorderComposite(1
        // , false));
        Label lab =
                toolkit
                        .createLabel(tfmCantPickFaultInBizAnalystModePage,
                                Messages.EventTriggerTypeErrorSection_MustBeInSolutionDesignToPickFault_message);
        lab.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        tfmEnableSolutionDesign =
                toolkit.createFormText(tfmCantPickFaultInBizAnalystModePage,
                        true);
        tfmEnableSolutionDesign.setText(enableSolutionDesignText, true, false);
        tfmEnableSolutionDesign.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        manageControl(tfmEnableSolutionDesign);

        sz =
                tfmCantPickFaultInBizAnalystModePage.computeSize(SWT.DEFAULT,
                        SWT.DEFAULT);
        if (sz.y > maxPageHeight) {
            maxPageHeight = sz.y;
        }

        /*
         * Page for request activity with auto-generated operation
         */
        tfmForAutoGenPage = toolkit.createComposite(tfmPageBook);
        tfmForAutoGenPage.setLayout(createNoBorderComposite(2, false));

        tfmForAutoGenErrorCodeLabel =
                toolkit
                        .createCLabel(tfmForAutoGenPage,
                                Messages.EventTriggerTypeThrowErrorSection_EnterFaultName_label,
                                SWT.NONE);

        tfmForAutoGenErrorCodeText =
                createContentAssistErrorCodeField(tfmForAutoGenPage, toolkit);
        tfmForAutoGenErrorCodeText.getLayoutControl()
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        sz = tfmForAutoGenPage.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        if (sz.y > maxPageHeight) {
            maxPageHeight = sz.y;
        }

        /*
         * Page for request activity with user-defined operation.
         */
        tfmForUserDefOpPage = toolkit.createComposite(tfmPageBook);
        tfmForUserDefOpPage.setLayout(createNoBorderComposite(3, false));

        tfmForUserDefOpSelectFaultLabel =
                toolkit
                        .createCLabel(tfmForUserDefOpPage,
                                Messages.EventTriggerTypeErrorSection_SelectFault_label);
        tfmForUserDefOpSelectFaultLabel.setLayoutData(new GridData());

        tfmForUserDefOpFaultText =
                createContentAssistFaultMessageField(tfmForUserDefOpPage,
                        toolkit);

        tfmForUserDefOpFaultText.getLayoutControl().setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        sz = tfmForUserDefOpPage.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        if (sz.y > maxPageHeight) {
            maxPageHeight = sz.y;
        }

        /*
         * Show empty page until we get refresh. And set optimum vertical size.
         */
        tfmShowPage(tfmEmptyPage);

        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = maxPageHeight;
        tfmPageBook.setLayoutData(gd);

        List<Control> labels = new ArrayList<Control>();
        labels.add(tfmReqActLabel);
        labels.add(tfmForAutoGenErrorCodeLabel);
        labels.add(tfmForUserDefOpSelectFaultLabel);
        setSameGridDataWidth(labels, null);

        return container;
    }

    /**
     * @param container
     * @param toolkit
     * 
     * @return The content assist control for selecting request activity.
     */
    private DecoratedField createContentAssistRequestActivityField(
            Composite container, XpdFormToolkit toolkit) {
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        if (incomingRequestActivityList != null) {
                            return incomingRequestActivityList.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit, container,
                        proposalProvider, true);

        refTaskHelper
                .addValueChangedListener(new FixedValueFieldChangedListener() {
                    @Override
                    public void fixedValueFieldChanged(Object newValue) {
                        doRequestActivityChanged(newValue);
                    }
                });

        DecoratedField field = refTaskHelper.getDecoratedField();

        field.getLayoutControl().setBackground(container.getBackground());
        return field;
    }

    /*
     * @param container
     * 
     * @param toolkit
     * 
     * @return The content assist control for selecting user defined operation
     * fault message name.
     */
    private DecoratedField createContentAssistFaultMessageField(
            Composite container, XpdFormToolkit toolkit) {
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        if (faultMessageNames != null) {
                            return faultMessageNames.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit, container,
                        proposalProvider, true);

        refTaskHelper
                .addValueChangedListener(new FixedValueFieldChangedListener() {
                    @Override
                    public void fixedValueFieldChanged(Object newValue) {
                        doFaultMessageChanged(newValue);
                    }
                });

        DecoratedField field = refTaskHelper.getDecoratedField();

        field.getLayoutControl().setBackground(container.getBackground());
        return field;
    }

    /**
     * Create controls for when "Throw Process / Sub-Process Error" is selected
     * 
     * @param parent
     * @param toolkit
     * 
     * @return controls for when "Throw Process / Sub-Process Error" is selected
     */
    private Composite doCreateThrowProcessErrorControls(Composite parent,
            XpdFormToolkit toolkit) {

        Composite container = toolkit.createComposite(parent, SWT.NONE);
        GridLayout gl = new GridLayout(2, false);
        gl.marginLeft = 13;
        gl.marginBottom = gl.marginHeight;
        gl.marginHeight = 0;
        container.setLayout(gl);

        tpeErrorCodeLabel =
                toolkit.createCLabel(container,
                        Messages.EventTriggerTypeErrorSection_ErrorCode_label,
                        SWT.NONE);
        setErrorCodeLabelGridData(tpeErrorCodeLabel);

        tpeErrorCodeText =
                createContentAssistErrorCodeField(container, toolkit);

        tpeErrorCodeText.getLayoutControl().setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        return container;
    }

    /**
     * Reset's the error code label grid data.
     * 
     * @param errorCodeLabel
     */
    private void setErrorCodeLabelGridData(Control errorCodeLabel) {
        errorCodeLabel.setLayoutData(new GridData());
        return;
    }

    /**
     * @param container
     * @param toolkit
     * 
     * @return Field with existing error codes content assist.
     */
    private DecoratedField createContentAssistErrorCodeField(
            Composite container, XpdFormToolkit toolkit) {
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    @Override
                    public Object[] getProposals() {
                        if (existingErrorCodes != null) {
                            return existingErrorCodes.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit, container,
                        proposalProvider, false);

        refTaskHelper
                .addValueChangedListener(new FixedValueFieldChangedListener() {
                    @Override
                    public void fixedValueFieldChanged(Object newValue) {
                        doErrorCodeChanged(newValue);
                    }
                });

        DecoratedField errorCodeText = refTaskHelper.getDecoratedField();

        errorCodeText.getLayoutControl().setBackground(container
                .getBackground());

        return errorCodeText;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Activity activity = getActivity();

        if (activity != null && activity.getProcess() != null) {
            if (ENABLE_SOLUTION_DESIGN_HYPERLINK.equals(obj)) {
                if (getSite() != null && getSite().getShell() != null) {
                    ProcessDialogUtil.enableSolutionDesignCapability(getSite()
                            .getShell(), true);
                    refresh();
                }

            } else if (obj == tfmGotoReqActButton) {
                Activity requestActivity =
                        ThrowErrorEventUtil.getRequestActivity(activity);
                if (requestActivity != null) {
                    RevealProcessDiagramEObject.revealEObject(getSite(),
                            requestActivity,
                            true);
                }
            } else if (obj == tpeRadioButton) {
                if (!ThrowErrorEventUtil.isThrowProcessErrorEvent(activity)) {
                    return ThrowErrorEventUtil
                            .getConfigureAsProcessErrorCommand(getEditingDomain(),
                                    activity,
                                    ElementsFactory
                                            .getUniqueDefaultErrorCode(activity
                                                    .getProcess()));
                }
            } else if (obj == tfmRadioButton) {
                if (!ThrowErrorEventUtil
                        .isThrowFaultMessageErrorEvent(activity)) {
                    return ThrowErrorEventUtil
                            .getConfigureAsFaultMessageErrorCommand(getEditingDomain(),
                                    activity,
                                    null,
                                    null);
                }
            }
        }

        return null;
    }

    @Override
    protected void doRefresh() {
        Activity activity = getActivity();

        if (!root.isDisposed() && activity != null) {
            boolean needsLayout = false;

            Process process = Xpdl2ModelUtil.getProcess(activity);
            if (process != null) {
                /*
                 * Cache the list of existing thrown error codes for the content
                 * assist fields.
                 */
                cacheExistingThrownErrorCodes(process);

                /*
                 * And Cache the list of possible incoming request activities.
                 */
                cacheRequestActivitiesList(process);

                /*
                 * And cache the list of possible faults for slecetd request
                 * activity's operation
                 */
                cacheFaultMessageNamesList(activity);
            }

            /*
             * At some point we have to force refresh tabs so that Output Fault
             * From Process tab is shown/hidden.
             * 
             * Best place to do this is here, so if the error is configurted in
             * model to be throw fault message and the fault message controls
             * are not visible (or visa-versa), then do a refresh tabs.
             * 
             * Don't bother when first refresh since set input as the tasb gets
             * refresh on selection change anyway.
             */
            boolean needsRefreshTabs =
                    !firstRefreshSinceSetInput
                            && ThrowErrorEventUtil
                                    .isThrowFaultMessageErrorEvent(activity) != tfmContainer
                                    .getVisible();

            /*
             * Refresh the controls for throw process error configuration.
             */
            if (doRefreshThrowProcessErrorControls(activity)) {
                needsLayout = true;
            }

            /*
             * Refresh the controls for throw fault message configuration.
             */
            if (doRefreshThrowFaultMessageErrorControls(activity)) {
                needsLayout = true;
            }

            firstRefreshSinceSetInput = false;

            if (needsLayout) {
                root.layout();
            }

            if (needsRefreshTabs) {
                refreshTabs();
            }
        }

        return;
    }

    /**
     * @param process
     */
    private void cacheExistingThrownErrorCodes(Process process) {
        Collection<Activity> eventList =
                EventObjectUtil.getProcessErrorEvents(process);

        existingErrorCodes = new HashSet<String>();

        /*
         * Full list of errors.
         */
        for (Activity errorEvent : eventList) {
            // Only interested in event throwers.
            if (errorEvent.getEvent() instanceof EndEvent) {
                String errorCode = getEventErrorCode(errorEvent);

                if (errorCode != null && errorCode.length() > 0) {
                    existingErrorCodes.add(errorCode);
                }
            }
        }

        if (existingErrorCodes.size() < 1) {
            existingErrorCodes = null;
        }

        return;
    }

    /**
     * Rebuild the content proposal list for the request activity selection
     * field.
     */
    private void cacheRequestActivitiesList(Process process) {
        incomingRequestActivityList =
                new ArrayList<IncomingRequestActivityEntry>();

        Collection<Activity> reqActivities =
                ReplyActivityUtil.getAllIncomingRequestActivities(process);

        FlowContainer flowContainer = getActivity().getFlowContainer();

        for (Activity reqAct : reqActivities) {
            if (flowContainer.equals(reqAct.getFlowContainer())) {
                incomingRequestActivityList
                        .add(new IncomingRequestActivityEntry(reqAct));
            }
        }

        return;
    }

    /**
     * Cache the fault message list.
     * 
     * @param activity
     */
    private void cacheFaultMessageNamesList(Activity activity) {
        faultMessageNames = null;

        Activity requestActivity =
                ThrowErrorEventUtil.getRequestActivity(activity);
        if (requestActivity != null) {
            /*
             * User can't select fault for auto-generated oepration request
             * activities - so only cache list if the it's a user defined wsdl
             * operation or operation created for a process interface generated
             * operation (in which case the user has to select from the
             * available faults as if it were a user-defined wsdl).
             */
            if (!Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)
                    || ProcessInterfaceUtil.isEventImplemented(requestActivity)) {
                IInternalFaultMessageNameProvider faultProvider =
                        getFaultMessageProvider();
                if (faultProvider != null) {
                    faultMessageNames =
                            faultProvider.getFaultMessageNames(requestActivity);
                }
            }
        }

        if (faultMessageNames != null && faultMessageNames.size() < 1) {
            faultMessageNames = null;
        }

        return;
    }

    /**
     * Refresh the controls for when throw process error is selected.
     * 
     * @param act
     * 
     * @return true if needs relayout.
     */
    private boolean doRefreshThrowProcessErrorControls(Activity activity) {
        boolean needsLayout = false;

        if (ThrowErrorEventUtil.isThrowProcessErrorEvent(activity)) {
            /*
             * When not configured for throw process error set controls and
             * expand section.
             */
            tpeRadioButton.setSelection(true);

            String errorCode = getEventErrorCode(activity);
            ((Text) tpeErrorCodeText.getControl()).setText(errorCode);

            /*
             * Implemented error method have their error codes text disabled,
             * because the name is set by the process interface error event -
             * set the error code label to
             */
            String oldLabelText = tpeErrorCodeLabel.getText();
            Image errorCodeLabelImg = null;
            String errorCodeTooltip =
                    Messages.EventTriggerTypeErrorSection_ErrorCodeToThrow_tooltip;

            if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                /*
                 * Implements error event defined in process interface - error
                 * code is set there.
                 */
                tpeErrorCodeLabel
                        .setText(Messages.EventTriggerTypeErrorSection_ImplementedErrorCode_label);

                setEnabled(tpeErrorCodeText.getLayoutControl(), false);

            } else {
                /* Does not implement error event defined in process interface */
                tpeErrorCodeLabel
                        .setText(Messages.EventTriggerTypeErrorSection_ErrorCode_label);

                setEnabled(tpeErrorCodeText.getLayoutControl(), true);

                /* Also Check whether needs problem icon/tooltip */
                if (errorCode == null || errorCode.length() == 0) {
                    errorCodeLabelImg =
                            Xpdl2UiPlugin.getDefault().getImageRegistry()
                                    .get(Xpdl2UiPlugin.IMG_ERROR);
                    errorCodeTooltip =
                            Messages.EventTriggerTypeErrorSection_MustEnterErrorCode_tooltip;
                }
            }

            if (!tpeErrorCodeLabel.getText().equals(oldLabelText)
                    || tpeErrorCodeLabel.getImage() != errorCodeLabelImg) {
                tpeErrorCodeLabel.setImage(errorCodeLabelImg);
                setErrorCodeLabelGridData(tpeErrorCodeLabel);
                tpeErrorCodeLabel.getParent().layout();
            }

            tpeErrorCodeLabel.setToolTipText(errorCodeTooltip);

            /*
             * Finally, if this is an Event Implementing a process interface
             * error method then don't allow user to change type to fault
             * message.
             */
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                tpeRadioButton.setEnabled(true);
                tfmRadioButton.setEnabled(false);
            } else {
                tfmRadioButton.setEnabled(true);
                tpeRadioButton.setEnabled(true);
            }

            if (!tpeContainer.getVisible()) {
                expandCollapseContainer(tpeContainer, true);
                needsLayout = true;
            }

        } else {
            /*
             * When not configured for throw process error unset controls and
             * collapse section.
             */
            tpeRadioButton.setSelection(false);

            ((Text) tpeErrorCodeText.getControl()).setText(""); //$NON-NLS-1$

            if (tpeContainer.getVisible()) {
                expandCollapseContainer(tpeContainer, false);
                needsLayout = true;
            }

        }

        return needsLayout;
    }

    /**
     * Refresh the controls for when throw fault message error is selected.
     * 
     * @param act
     * 
     * @return true if needs relayout.
     */
    private boolean doRefreshThrowFaultMessageErrorControls(Activity activity) {
        boolean needsLayout = false;

        if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
            /*
             * When not configured for throw fault message set controls and expand section.
             */
            Image reqActLabelImg = null;
            String reqActLabelTooltip =
                    Messages.EventTriggerTypeErrorSection_MustSelectRequestActivity_tooltip;

            tfmRadioButton.setSelection(true);

            Boolean isImplementingError =
                    ProcessInterfaceUtil.isEventImplemented(activity);

            Activity requestActivity =
                    ThrowErrorEventUtil.getRequestActivity(activity);

            if (requestActivity != null) {
                /*
                 * We have a request activity, check what page book we should be
                 * showing and update its controls.
                 */
                tfmGotoReqActButton.setEnabled(true);

                String requestActivityName =
                        Xpdl2ModelUtil.getDisplayNameOrName(requestActivity);

                if (requestActivityName == null
                        || requestActivityName.length() == 0) {
                    if (requestActivity.getEvent() != null) {
                        requestActivityName =
                                Messages.EventTriggerTypeLinkSection_UnnamedEvent_label;
                    } else {
                        requestActivityName =
                                Messages.TaskTypeReferenceSection_UnnamedTask_label;
                    }
                }

                updateText((Text) tfmSelectRequestActivityText.getControl(),
                        requestActivityName);

                /*
                 * Disable reuest activity selection if this is an eror revent
                 * implementing process interface error.
                 */
                if (isImplementingError) {
                    setEnabled(tfmSelectRequestActivityText.getLayoutControl(),
                            false);
                } else {
                    setEnabled(tfmSelectRequestActivityText.getLayoutControl(),
                            true);
                }

                reqActLabelTooltip =
                        Messages.EventTriggerTypeErrorSection_RequestActivitySelected_tooltip;

                if (Xpdl2ModelUtil.isGeneratedRequestActivity(requestActivity)
                        && !ProcessInterfaceUtil
                                .isEventImplemented(requestActivity)) {
                    /*
                     * Request activity is having operation auto-generated - so
                     * show the enter error code page (this is how user enters
                     * the name of the generated fault message for the operation
                     * to be generated from process request activity.).
                     * 
                     * We don't count process events implementing a process
                     * interface message method because the user defined the
                     * operation errors in the process interface (so the user
                     * should chose from the process itneface methdos generated
                     * operation faults just as if it were a user -defined
                     * wsdl).
                     */
                    String errorCode = getEventErrorCode(activity);

                    ((Text) tfmForAutoGenErrorCodeText.getControl())
                            .setText(errorCode);

                    /*
                     * Implemented error method have their error codes text
                     * disabled, because the name is set by the process
                     * interface error event.
                     */
                    String oldLabelText = tfmForAutoGenErrorCodeLabel.getText();
                    Image errorCodeLabelImg = null;
                    String errorCodeTooltip =
                            Messages.EventTriggerTypeErrorSection_ErrorCodeToUseAsFaultName_tooltip;

                    /*
                     * Does not implement error event defined in process
                     * interface - error code is set here.
                     */
                    tfmForAutoGenErrorCodeLabel
                            .setText(Messages.EventTriggerTypeThrowErrorSection_EnterFaultName_label);
                    setEnabled(tfmForAutoGenErrorCodeText.getLayoutControl(),
                            true);

                    /* Also Check whether needs problem icon/tooltip */
                    if (errorCode == null || errorCode.length() == 0) {
                        errorCodeLabelImg =
                                Xpdl2UiPlugin.getDefault().getImageRegistry()
                                        .get(Xpdl2UiPlugin.IMG_ERROR);
                        errorCodeTooltip =
                                Messages.EventTriggerTypeErrorSection_MustEnterErrorCodeForFaultName_tooltip;
                    }

                    if (!tfmForAutoGenErrorCodeLabel.getText()
                            .equals(oldLabelText)
                            || tfmForAutoGenErrorCodeLabel.getImage() != errorCodeLabelImg
                            || tfmPageBookCurrentPage != tfmForAutoGenPage) {
                        tfmForAutoGenErrorCodeLabel.setImage(errorCodeLabelImg);

                        List<Control> labels = new ArrayList<Control>();
                        labels.add(tfmReqActLabel);
                        labels.add(tfmForAutoGenErrorCodeLabel);
                        // labels.add(tfmForUserDefOpSelectFaultLabel);
                        setSameGridDataWidth(labels, null);

                        tfmForAutoGenPage.layout();
                        tfmContainer.layout();
                    }

                    tfmForAutoGenErrorCodeLabel
                            .setToolTipText(errorCodeTooltip);

                    tfmShowPage(tfmForAutoGenPage);

                } else {
                    /*
                     * Request activity has user selected operation OR it is
                     * implementing an interface event - so show the fault
                     * picker page - or if not in Solution Design mode show the
                     * can't select fault in business.
                     */
                    if (CapabilityUtil.isDeveloperActivityEnabled()
                            || isImplementingError) {
                        /*
                         * Solution design capability enabled, show fault
                         * picker.
                         */
                        String oldLabelText =
                                tfmForUserDefOpSelectFaultLabel.getText();
                        Image faultMessageLabelImg = null;
                        String faultMessageLabelTooltip =
                                Messages.EventTriggerTypeErrorSection_MustSpecifyFaultMessage_tooltip;

                        String faultName = null;

                        Message throwErrorFaultMessage =
                                ThrowErrorEventUtil
                                        .getThrowErrorFaultMessage(activity);
                        if (throwErrorFaultMessage != null) {
                            faultName = throwErrorFaultMessage.getFaultName();
                        }

                        if (faultName != null && faultName.length() > 0) {
                            updateText(((Text) tfmForUserDefOpFaultText
                                    .getControl()), faultName);
                            faultMessageLabelTooltip =
                                    Messages.EventTriggerTypeErrorSection_OpFaultMessageToThrow_tooltip;
                        } else {
                            updateText(((Text) tfmForUserDefOpFaultText
                                    .getControl()), ""); //$NON-NLS-1$
                            faultMessageLabelImg =
                                    Xpdl2UiPlugin.getDefault()
                                            .getImageRegistry()
                                            .get(Xpdl2UiPlugin.IMG_ERROR);
                        }

                        /*
                         * Do not allow pick if throw error end event is the one
                         * created to implement process interface error event
                         * (don't think this is really possible for implemented
                         * events, but just in case we'll check anyhow!).
                         * 
                         * It's ok if just the request is implemented as this
                         * means it is an extra non-implementing error event
                         * that the user has added to throw a process interface
                         * error (in whch case we will treat as if it's a user
                         * defined wsdl and select from the wsdl faults.
                         */
                        if (isImplementingError) {
                            /*
                             * Implements error event defined in process
                             * interface - error code is set there.
                             */
                            tfmForUserDefOpSelectFaultLabel
                                    .setText(Messages.EventTriggerTypeErrorSection_ImplementedErrorCode_label);
                            setEnabled(tfmForUserDefOpFaultText
                                    .getLayoutControl(), false);

                        }

                        else {
                            tfmForUserDefOpSelectFaultLabel
                                    .setText(Messages.EventTriggerTypeErrorSection_SelectFault_label);

                            /*
                             * Only enable fault message content assist picker
                             * button if something (i.e. process developer
                             * feature) has contributed fault message list
                             * provider(just in case!).
                             */
                            if (getFaultMessageProvider() == null) {
                                setEnabled(tfmForUserDefOpFaultText
                                        .getLayoutControl(), false);
                            } else {
                                setEnabled(tfmForUserDefOpFaultText
                                        .getLayoutControl(), true);
                            }
                        }

                        /* Update req activity label image / tooltip */
                        if (!tfmForUserDefOpSelectFaultLabel.getText()
                                .equals(oldLabelText)
                                || tfmForUserDefOpSelectFaultLabel.getImage() != faultMessageLabelImg
                                || tfmPageBookCurrentPage != tfmForUserDefOpPage) {
                            tfmForUserDefOpSelectFaultLabel
                                    .setImage(faultMessageLabelImg);
                            tfmForUserDefOpSelectFaultLabel
                                    .setLayoutData(new GridData());

                            List<Control> labels = new ArrayList<Control>();
                            labels.add(tfmReqActLabel);
                            // labels.add(tfmForAutoGenErrorCodeLabel);
                            labels.add(tfmForUserDefOpSelectFaultLabel);
                            setSameGridDataWidth(labels, null);

                            tfmForUserDefOpPage.layout();
                            tfmContainer.layout();
                        }
                        tfmForUserDefOpSelectFaultLabel
                                .setToolTipText(faultMessageLabelTooltip);

                        tfmShowPage(tfmForUserDefOpPage);

                    } else {
                        /*
                         * Solution design capability not enabled allow user to
                         * enable.
                         */
                        if (ProcessFeaturesUtil
                                .isProcessDeveloperFeatureInstalled()) {
                            tfmPageBook
                                    .showPage(tfmCantPickFaultInBizAnalystModePage);
                        } else {
                            tfmShowPage(tfmEmptyPage);
                        }
                    }
                }

            } else {
                /*
                 * No request activity selected yet.
                 * 
                 * Can't do anything else until request activity is selected so
                 * hide other controls.
                 */
                tfmGotoReqActButton.setEnabled(false);

                if (isImplementingError) {
                    setEnabled(tfmSelectRequestActivityText.getLayoutControl(),
                            false);
                } else {
                    setEnabled(tfmSelectRequestActivityText.getLayoutControl(),
                            true);
                }

                updateText((Text) tfmSelectRequestActivityText.getControl(), ""); //$NON-NLS-1$

                tfmShowPage(tfmEmptyPage);

                updateText(((Text) tfmSelectRequestActivityText.getControl()),
                        ""); //$NON-NLS-1$
                ((Text) tfmForAutoGenErrorCodeText.getControl()).setText(""); //$NON-NLS-1$
                updateText(((Text) tfmForUserDefOpFaultText.getControl()), ""); //$NON-NLS-1$

                reqActLabelImg =
                        Xpdl2UiPlugin.getDefault().getImageRegistry()
                                .get(Xpdl2UiPlugin.IMG_ERROR);
            }

            /* Update req activity label image / tooltip */
            if (tfmReqActLabel.getImage() != reqActLabelImg) {
                tfmReqActLabel.setImage(reqActLabelImg);
                tfmReqActLabel.setLayoutData(new GridData());
                tfmContainer.layout();
            }

            tfmReqActLabel.setToolTipText(reqActLabelTooltip);

            /*
             * Finally, if this is an Event Implementing a process interface
             * error method then don't allow user to change type to throw
             * process error.
             */
            if (isImplementingError) {
                tfmRadioButton.setEnabled(true);
                tpeRadioButton.setEnabled(false);
            } else {
                tfmRadioButton.setEnabled(true);
                tpeRadioButton.setEnabled(true);
            }

            if (!tfmContainer.getVisible()) {
                expandCollapseContainer(tfmContainer, true);
                needsLayout = true;
            }

            /*
             * Sid ACE-3895 There are currently no specific incoming message request events in ACE, so you cannot
             * currently configure throw error end events for specific start events (i.e. for a specific incoming
             * message request. So hide controls for now unless a migrated process has this currently configured (in
             * which case show it so the user can see it).
             */
            tfmRadioButton.setVisible(true);
            setEnabled(tfmSelectRequestActivityText.getLayoutControl(), false);
            setEnabled(tfmForAutoGenErrorCodeText.getLayoutControl(), false);
            setEnabled(tfmForUserDefOpFaultText.getLayoutControl(), false);

        } else {
            /*
             * When not configured for throw fault message unset controls and
             * collapse section.
             */
            tfmRadioButton.setSelection(false);

            tfmShowPage(tfmEmptyPage);

            updateText(((Text) tfmSelectRequestActivityText.getControl()), ""); //$NON-NLS-1$
            ((Text) tfmForAutoGenErrorCodeText.getControl()).setText(""); //$NON-NLS-1$
            updateText(((Text) tfmForUserDefOpFaultText.getControl()), ""); //$NON-NLS-1$

            if (tfmContainer.getVisible()) {
                expandCollapseContainer(tfmContainer, false);
                needsLayout = true;
            }

            /*
             * Sid ACE-3895 There are currently no specific incoming message request events in ACE, so you cannot
             * currently configure throw error end events for specific start events (i.e. for a specific incoming
             * message request. So hide controls for now unless a migrated process has this currently configured (in
             * which case show it so the user can see it).
             */
            tfmRadioButton.setVisible(false);
            if (tfmContainer.getVisible()) {
                needsLayout = true;
            }
            tfmContainer.setVisible(false);

        }

        return needsLayout;
    }

    /**
     * There is no easy way to get the list of fault names from the request
     * activity's operation because here in the process analyst we are not meant
     * to know about such things.
     * <p>
     * Therefore the
     * com.tibco.xpd.processeditor.xpdl2.operationInformationProvider extension
     * allows the process developer feature (if present) to plugin the list of
     * fault message names where necessary.
     * 
     * @return The contributed fault message provider.
     */
    private IInternalFaultMessageNameProvider getFaultMessageProvider() {
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                "operationInformationProvider"); //$NON-NLS-1$

        if (extensionPoint != null) {
            IConfigurationElement[] configs =
                    extensionPoint.getConfigurationElements();
            if (configs != null) {
                for (IConfigurationElement config : configs) {
                    if ("faultMessageNameProvider".equals(config.getName())) { //$NON-NLS-1$
                        try {
                            Object clazz =
                                    config.createExecutableExtension("class"); //$NON-NLS-1$
                            if ((clazz instanceof IInternalFaultMessageNameProvider)) {
                                return (IInternalFaultMessageNameProvider) clazz;
                            }

                            Xpdl2ProcessEditorPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error("Failed to load faultMessageProvider contribution from contributor: " //$NON-NLS-1$
                                            + config.getContributor().getName());

                        } catch (CoreException e) {
                            Xpdl2ProcessEditorPlugin.getDefault().getLogger()
                                    .error(e,
                                            "Failed to load faultMessageProvider contribution from contributor: " //$NON-NLS-1$
                                                    + config.getContributor()
                                                            .getName());
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @param activity
     * @return error code or ""
     */
    private String getEventErrorCode(Activity activity) {
        String errorCode = ThrowErrorEventUtil.getThrowErrorCode(activity);

        if (errorCode == null) {
            errorCode = ""; //$NON-NLS-1$
        }
        return errorCode;
    }

    /**
     * Content assisted Error code value changed
     * 
     * @param newValue
     */
    private void doErrorCodeChanged(Object newValue) {
        Activity throwErrorActivity = getActivity();
        EditingDomain ed = getEditingDomain();

        if (ed != null && throwErrorActivity != null) {
            String oldValue = getEventErrorCode(throwErrorActivity);

            if (oldValue == null) {
                oldValue = ""; //$NON-NLS-1$
            }
            if (!oldValue.equals(newValue)) {
                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.EventTriggerTypeThrowErrorSection_SetEventErrorCode_menu);

                String newErrorCode = (String) newValue;

                cmd.append(EventObjectUtil.getSetErrorCodeCommand(ed,
                        throwErrorActivity,
                        newErrorCode));

                if (cmd != null && cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                    return;
                }
            }
            refresh();

        }
        return;
    }

    /**
     * Content assisted request activity select field has changed.
     * 
     * @param newValue
     */
    private void doRequestActivityChanged(Object newValue) {
        Activity activity = getActivity();

        if (activity != null) {
            if (newValue instanceof IncomingRequestActivityEntry) {
                String newRequestActivityId =
                        ((IncomingRequestActivityEntry) newValue).getId();

                String currentRequestActId =
                        ThrowErrorEventUtil.getRequestActivityId(activity);

                if (!newRequestActivityId.equals(currentRequestActId)) {
                    /*
                     * Request activity is different doing
                     * getConfigureAsFaultMessageCommand() will completely reset
                     * it including nullifying datamappigns and selected fault
                     * if any..
                     */
                    Command cmd =
                            ThrowErrorEventUtil
                                    .getConfigureAsFaultMessageErrorCommand(getEditingDomain(),
                                            activity,
                                            newRequestActivityId,
                                            null);

                    if (cmd != null && cmd.canExecute()) {
                        getEditingDomain().getCommandStack().execute(cmd);
                        return;
                    }

                }
            }
            refresh();
        }

        return;
    }

    /**
     * Content assisted fault message field has changed.
     * 
     * @param newValue
     */
    private void doFaultMessageChanged(Object newValue) {
        Activity activity = getActivity();

        if (activity != null) {
            if (newValue instanceof String) {
                String newFaultName = newValue != null ? (String) newValue : ""; //$NON-NLS-1$

                String currentFaultName =
                        ThrowErrorEventUtil.getThrowErrorFaultName(activity);

                String requestActivityId =
                        ThrowErrorEventUtil.getRequestActivityId(activity);

                if (!newFaultName.equals(currentFaultName)
                        && requestActivityId != null) {
                    /*
                     * fault name activity is different doing
                     * getConfigureAsFaultMessageCommand() will completely reset
                     * it including nullifying datamappigns and selected fault
                     * if any.
                     */
                    Command cmd =
                            ThrowErrorEventUtil
                                    .getConfigureAsFaultMessageErrorCommand(getEditingDomain(),
                                            activity,
                                            requestActivityId,
                                            newFaultName);

                    if (cmd != null && cmd.canExecute()) {
                        getEditingDomain().getCommandStack().execute(cmd);
                        return;
                    }
                }
            }
            refresh();
        }
        return;
    }

    /**
     * Set the radio bnutton content container layout data fopr expanded /
     * collapsed state.
     */
    private void expandCollapseContainer(Composite container, boolean expanded) {
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        if (expanded) {
            container.setLayoutData(gd);
            container.setVisible(true);
        } else {
            gd.heightHint = 1;
            container.setLayoutData(gd);
            container.setVisible(false);
        }
        return;
    }

    /**
     * Set the throw fault message controls pagebook to the given page.
     * 
     * @param page
     * 
     * @return true if page changed.
     */
    private boolean tfmShowPage(Control page) {
        boolean ret = false;

        tfmPageBook.showPage(page);

        if (tfmPageBookCurrentPage != page) {
            ret = true;
            tfmPageBookCurrentPage = page;
        }

        return ret;
    }

    /**
     * @param numColumns
     * @param equalSize
     * 
     * @return Create a grid layout with no borders.
     */
    private GridLayout createNoBorderComposite(int numColumns, boolean equalSize) {
        GridLayout gl = new GridLayout(numColumns, equalSize);

        gl.marginHeight =
                gl.marginWidth =
                        gl.marginTop =
                                gl.marginBottom =
                                        gl.marginRight = gl.marginLeft = 0;

        return gl;
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    public Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        Activity inputActivity = getActivity();
        if (inputActivity != null) {
            // Get the id of the activity we're "supposed" to throw error for
            // (if any)
            String id = ThrowErrorEventUtil.getRequestActivityId(inputActivity);

            if (id != null && id.length() > 0) {
                // Go thru notifications, if any have an ancestor that is the
                // activity that is the request activity for our reply activity
                // then we need to refresh).
                for (Notification notification : notifications) {
                    Object ancestor = notification.getNotifier();
                    Activity activityAncestor = null;

                    // Find activity ancestor for this notifier.
                    while (ancestor instanceof EObject) {
                        if (ancestor instanceof Activity) {
                            activityAncestor = (Activity) ancestor;
                            break;
                        }

                        ancestor = ((EObject) ancestor).eContainer();
                    }

                    // Is the activity the request activity we're linked to.
                    if (activityAncestor != null
                            && id.equals(activityAncestor.getId())) {
                        return true;
                    }
                }
            }
        }

        return super.shouldRefresh(notifications);
    }

}
