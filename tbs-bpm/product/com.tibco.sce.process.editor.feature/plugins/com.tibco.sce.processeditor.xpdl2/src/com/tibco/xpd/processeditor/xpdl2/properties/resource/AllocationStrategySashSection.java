/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.ContentProposal;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.SashSection;
import com.tibco.xpd.processeditor.xpdl2.properties.util.CommandContentAssistTextHandler;
import com.tibco.xpd.processeditor.xpdl2.properties.util.ContentAssistText;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class AllocationStrategySashSection extends
        AbstractFilteredTransactionalSection implements SashSection {

    private Button offerToAll;

    private Button offerToOne;

    private Button allocateToOne;

    /**
     * ABPM-901: Saket: Radio button for new distribution strategy:- Allocate to
     * offer-set member.
     */
    private Button allocateToOfferSetMember;

    /**
     * Text control to enter performer field that identifies the specific offer
     * set member to allocate work item to.
     */
    private Text memberIdentifierText;

    /**
     * Label for Member Identifier Field Text control.
     */
    private Label memberIdentifierFieldLabel;

    private Composite allocationComposite;

    /**
     * @param class1
     */
    public AllocationStrategySashSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @param toTest
     * @return
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        boolean ok = false;
        if (toTest instanceof Activity) {
            Activity activity = (Activity) toTest;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskUser() != null) {
                    ok = true;
                }
            }
        }
        return ok;
    }

    /**
     * @param parent
     * @param toolkit
     * @return
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @SuppressWarnings("deprecation")
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite composite = toolkit.createComposite(parent);
        composite.setLayout(new GridLayout(1, false));

        Label label =
                toolkit.createLabel(composite,
                        Messages.AllocationStrategySashSection_AllocationStrategyDescription,
                        SWT.WRAP);

        allocationComposite = toolkit.createComposite(composite);
        allocationComposite
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout allocationCompositeLayout = new GridLayout(2, false);
        allocationComposite.setLayout(allocationCompositeLayout);

        offerToAll =
                toolkit.createButton(allocationComposite,
                        Messages.AllocationStrategySashSection_OfferFirstButtonLabel,
                        SWT.RADIO,
                        "allocationType"); //$NON-NLS-1$
        GridData gd1 = new GridData(SWT.NONE, SWT.FILL, false, false);
        offerToAll.setLayoutData(gd1);
        offerToAll
                .setToolTipText(Messages.AllocationStrategySashSection_OfferToAll_tooltip);

        offerToOne =
                toolkit.createButton(allocationComposite,
                        Messages.AllocationStrategySashSection_OfferToOneButtonLabel,
                        SWT.RADIO,
                        "allocationType"); //$NON-NLS-1$
        GridData gd3 = new GridData(SWT.NONE, SWT.FILL, false, false);
        offerToOne.setLayoutData(gd3);
        offerToOne
                .setToolTipText(Messages.AllocationStrategySashSection_OfferToOne_tooltip);

        allocateToOne =
                toolkit.createButton(allocationComposite,
                        Messages.AllocationStrategySashSection_AllocateDirectlyButtonLabel,
                        SWT.RADIO,
                        "allocationType"); //$NON-NLS-1$
        GridData gd2 = new GridData(SWT.NONE, SWT.FILL, false, false);
        allocateToOne.setLayoutData(gd2);
        allocateToOne
                .setToolTipText(Messages.AllocationStrategySashSection_AllocateToOne_tooltip);

        /*
         * ABPM-901: Saket: Introducing a new distribution strategy: Allocate to
         * offer set member.
         */
        allocateToOfferSetMember =
                toolkit.createButton(allocationComposite,
                        Messages.AllocationStrategySashSection_AllocateToOfferSetMemberButtonLabel,
                        SWT.RADIO,
                        "allocationType"); //$NON-NLS-1$
        GridData gd4 = new GridData(SWT.NONE, SWT.FILL, false, false);
        allocateToOfferSetMember.setLayoutData(gd4);
        allocateToOfferSetMember
                .setToolTipText(Messages.AllocationStrategySashSection_AllocateToOfferSetMember_tooltip);

        /*
         * Filler to get the positioning right for member identifier field
         * content assisted text.
         */
        Label filler = toolkit.createLabel(allocationComposite, ""); //$NON-NLS-1$

        Composite memberIdentifierFieldComposite =
                toolkit.createComposite(allocationComposite);
        memberIdentifierFieldComposite.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        GridLayout memberIdentifierFieldCompositeLayout =
                new GridLayout(2, false);
        memberIdentifierFieldComposite
                .setLayout(memberIdentifierFieldCompositeLayout);

        /*
         * Label for Member Identifier Field Text control.
         */
        memberIdentifierFieldLabel =
                toolkit.createLabel(memberIdentifierFieldComposite,
                        Messages.AllocationStrategySashSection_MemberIdentifierFieldTextLabel);

        GridData gd5 = new GridData(GridData.BEGINNING);
        gd5.horizontalIndent = 15;
        memberIdentifierFieldLabel.setLayoutData(gd5);

        /*
         * Content assist text control to enter performer field that identifies
         * the specific offer set member to allocate work item to.
         */
        ContentAssistText contentAssistText =
                new ContentAssistText(memberIdentifierFieldComposite, toolkit,
                        new PerformerTypeProposalProvider() {
                            @Override
                            protected Activity getInput() {
                                EObject input =
                                        AllocationStrategySashSection.this
                                                .getInput();
                                return (Activity) (input instanceof Activity ? input
                                        : null);
                            }
                        });

        manageControl(contentAssistText);

        /*
         * Get the text control from the content assist text control.
         */
        memberIdentifierText = contentAssistText.getText();

        memberIdentifierText
                .setToolTipText(Messages.AllocationStrategySashSection_MemberIdentifierFieldText_tooltip);

        GridData gd6 = new GridData(GridData.FILL_HORIZONTAL);
        contentAssistText.setLayoutData(gd6);

        manageControl(offerToAll);
        manageControl(offerToOne);
        manageControl(allocateToOne);

        /*
         * ABPM-901: Saket: Introducing a new distribution strategy: Allocate to
         * offer set member.
         */
        manageControl(allocateToOfferSetMember);

        Point sz = allocationComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        GridData labelLayout = new GridData(GridData.FILL_HORIZONTAL);
        labelLayout.widthHint = sz.x;
        label.setLayoutData(labelLayout);

        return composite;
    }

    /**
     * Provides the same functionality for ContentAssistText fields as the
     * manageControl methods in AbstractXpdSection do for SWT Controls.
     * 
     * @param control
     *            The content assist control to manage.
     */
    protected void manageControl(final ContentAssistText control) {
        new CommandContentAssistTextHandler(control, this);
    }

    /**
     * @param obj
     * @return
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;
        EditingDomain ed = getEditingDomain();
        Object input = getInput();

        if (input instanceof Activity) {
            ActivityResourcePatterns patterns = null;
            AllocationStrategy allocationStrategy = null;
            String label;

            Activity activity = (Activity) input;
            label =
                    Messages.AllocationStrategySashSection_ChangeAllocationTypeCommand;
            CompoundCommand command = new CompoundCommand(label);
            EStructuralFeature feature =
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ActivityResourcePatterns();
            Object other = Xpdl2ModelUtil.getOtherElement(activity, feature);

            /*
             * If the current object is the member identifier text, then we need
             * to handle it differently than the radio buttons.
             */
            if (obj == memberIdentifierText) {

                if (other instanceof ActivityResourcePatterns) {

                    patterns = (ActivityResourcePatterns) other;

                    /*
                     * Fetch allocation strategy.
                     */
                    allocationStrategy = patterns.getAllocationStrategy();

                    if (allocationStrategy != null) {

                        /*
                         * Fetch the text which is there in the member
                         * identifier text control.
                         */
                        String memberIdentifierTxt =
                                memberIdentifierText.getText();

                        /*
                         * Update the member identifier field in the model if
                         * the text control isn't blank.
                         */
                        if (!memberIdentifierTxt.isEmpty()
                                || memberIdentifierTxt != "") { //$NON-NLS-1$
                            command.append(SetCommand
                                    .create(ed,
                                            allocationStrategy,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAllocationStrategy_AllocateToOfferSetMemberIdentifier(),
                                            memberIdentifierTxt));
                        } else {

                            /*
                             * If the text control IS blank, then unset member
                             * identifier value in the model.
                             */
                            command.append(SetCommand
                                    .create(ed,
                                            allocationStrategy,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAllocationStrategy_AllocateToOfferSetMemberIdentifier(),
                                            SetCommand.UNSET_VALUE));
                        }
                    }
                }

            } else {

                /*
                 * Normal handling of radio buttons which help to select the
                 * distribution strategy.
                 */

                if (other instanceof ActivityResourcePatterns) {
                    patterns = (ActivityResourcePatterns) other;
                    allocationStrategy = patterns.getAllocationStrategy();
                    if (null == allocationStrategy) {
                        allocationStrategy =
                                XpdExtensionFactory.eINSTANCE
                                        .createAllocationStrategy();
                        patterns.setAllocationStrategy(allocationStrategy);

                        AllocationType allocationType = getAllocationType(obj);

                        allocationStrategy.setOffer(allocationType);

                        feature =
                                XpdExtensionPackage.eINSTANCE
                                        .getActivityResourcePatterns_AllocationStrategy();

                        command.append(SetCommand.create(ed,
                                allocationStrategy,
                                feature,
                                allocationStrategy));

                        if (allocationType != AllocationType.ALLOCATE_OFFER_SET_MEMBER) {

                            command.append(SetCommand
                                    .create(ed,
                                            allocationStrategy,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAllocationStrategy_AllocateToOfferSetMemberIdentifier(),
                                            SetCommand.UNSET_VALUE));
                        }
                    } else {
                        Object value;
                        feature =
                                XpdExtensionPackage.eINSTANCE
                                        .getAllocationStrategy_Offer();
                        value = getAllocationType(obj);

                        command.append(SetCommand.create(ed,
                                allocationStrategy,
                                feature,
                                value));

                        if (value != AllocationType.ALLOCATE_OFFER_SET_MEMBER) {

                            command.append(SetCommand
                                    .create(ed,
                                            allocationStrategy,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getAllocationStrategy_AllocateToOfferSetMemberIdentifier(),
                                            SetCommand.UNSET_VALUE));
                        }
                    }
                } else {
                    patterns =
                            XpdExtensionFactory.eINSTANCE
                                    .createActivityResourcePatterns();
                    allocationStrategy =
                            XpdExtensionFactory.eINSTANCE
                                    .createAllocationStrategy();
                    patterns.setAllocationStrategy(allocationStrategy);
                    allocationStrategy.setOffer(getAllocationType(obj));
                    command.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                            activity,
                            feature,
                            patterns));
                }
            }

            cmd = command;
        }
        return cmd;
    }

    private AllocationType getAllocationType(Object obj) {
        if (obj == offerToAll) {
            return AllocationType.OFFER_ALL;
        } else if (obj == offerToOne) {
            return AllocationType.OFFER_ONE;
        } else if (obj == allocateToOne) {
            return AllocationType.ALLOCATE_ONE;
        } else if (obj == allocateToOfferSetMember) {

            /*
             * ABPM-901: Saket: Introducing a new distribution strategy:
             * Allocate to offer set member.
             */
            return AllocationType.ALLOCATE_OFFER_SET_MEMBER;
        }
        return null;
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        Object input = getInput();
        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            boolean showAllocate = false;
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskUser() != null) {
                    showAllocate = true;
                }
            } else {
                Event event = activity.getEvent();
                if (event instanceof StartEvent) {
                    StartEvent start = (StartEvent) event;
                    TriggerType type = start.getTrigger();
                    if (TriggerType.MESSAGE_LITERAL.equals(type)) {
                        showAllocate = true;
                    }
                } else if (event instanceof IntermediateEvent) {
                    IntermediateEvent intermediate = (IntermediateEvent) event;
                    TriggerType type = intermediate.getTrigger();
                    if (TriggerType.MESSAGE_LITERAL.equals(type)) {
                        showAllocate = true;
                    }
                } else if (event instanceof EndEvent) {
                    EndEvent end = (EndEvent) event;
                    ResultType type = end.getResult();
                    if (ResultType.MESSAGE_LITERAL.equals(type)) {
                        showAllocate = true;
                    }
                }
            }
            allocationComposite.setVisible(showAllocate);
            EStructuralFeature feature =
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ActivityResourcePatterns();
            Object other = Xpdl2ModelUtil.getOtherElement(activity, feature);

            /* XPD-6558 : Reset Allocation strategy */
            offerToOne.setSelection(false);
            allocateToOne.setSelection(false);
            offerToAll.setSelection(false);

            /*
             * ABPM-901: Saket: Introducing a new distribution strategy:
             * Allocate to offer set member.
             */
            allocateToOfferSetMember.setSelection(false);
            memberIdentifierText.setEnabled(false);
            memberIdentifierFieldLabel.setEnabled(false);
            memberIdentifierText.setText(""); //$NON-NLS-1$

            if (other instanceof ActivityResourcePatterns) {
                ActivityResourcePatterns patterns =
                        (ActivityResourcePatterns) other;
                AllocationStrategy allocationStrategy =
                        patterns.getAllocationStrategy();
                if (allocationStrategy != null) {
                    if (allocationStrategy.getOffer() == AllocationType.ALLOCATE_ONE) {
                        allocateToOne.setSelection(true);
                        offerToAll.setSelection(false);
                        offerToOne.setSelection(false);
                        allocateToOfferSetMember.setSelection(false);
                    } else if (allocationStrategy.getOffer() == AllocationType.OFFER_ALL) {
                        offerToAll.setSelection(true);
                        allocateToOne.setSelection(false);
                        offerToOne.setSelection(false);
                        allocateToOfferSetMember.setSelection(false);
                    } else if (allocationStrategy.getOffer() == AllocationType.OFFER_ONE) {
                        offerToOne.setSelection(true);
                        allocateToOne.setSelection(false);
                        offerToAll.setSelection(false);
                        allocateToOfferSetMember.setSelection(false);
                    } else if (allocationStrategy.getOffer() == AllocationType.ALLOCATE_OFFER_SET_MEMBER) {

                        /*
                         * ABPM-901: Saket: Introducing a new distribution
                         * strategy: Allocate to offer set member.
                         */

                        allocateToOfferSetMember.setSelection(true);
                        memberIdentifierText.setEnabled(true);
                        memberIdentifierFieldLabel.setEnabled(true);

                        String memberId =
                                allocationStrategy
                                        .getAllocateToOfferSetMemberIdentifier();
                        if (memberId != null) {
                            memberIdentifierText.setText(memberId);
                        }

                        offerToOne.setSelection(false);
                        allocateToOne.setSelection(false);
                        offerToAll.setSelection(false);
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldSashSectionRefresh(List<Notification> notifications) {
        return shouldRefresh(notifications);
    }

    /**
     * Content proposal for performer data types.
     * 
     * @author sajain
     */
    public static abstract class PerformerTypeProposalProvider implements
            IContentProposalProvider {

        @Override
        public IContentProposal[] getProposals(String contents, int position) {
            List<ContentProposal> proposals = new ArrayList<ContentProposal>();

            /*
             * Get all the data fields which match the content of the content
             * assist field
             */
            Set<ProcessRelevantData> dataInScope = getPerformerTypesInScope();

            for (ProcessRelevantData data : dataInScope) {
                String displayName = Xpdl2ModelUtil.getDisplayName(data);

                if (doesProposalMatch(data.getName(), contents, position)) {
                    proposals.add(new ContentProposal(data.getName(),
                            displayName));
                }
            }

            return proposals.toArray(new IContentProposal[proposals.size()]);
        }

        /**
         * Get the input of the section.
         * 
         * @return
         */
        protected abstract Activity getInput();

        /**
         * Get all Case class reference data types that are in scope of the
         * input object.
         * 
         * @return
         */
        private Set<ProcessRelevantData> getPerformerTypesInScope() {

            Set<ProcessRelevantData> items = new HashSet<ProcessRelevantData>();
            List<ProcessRelevantData> activityData =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(getInput());

            for (ProcessRelevantData data : activityData) {

                if (data != null) {

                    BasicType basicType =
                            BasicTypeConverterFactory.INSTANCE
                                    .getBasicType(data);

                    if (basicType != null) {

                        if (BasicTypeType.PERFORMER_LITERAL == basicType
                                .getType()) {

                            items.add(data);
                        }
                    }
                }
            }

            return items;
        }

        /**
         * Check if the value matches the content assist proposal.
         * 
         * @param value
         * @param contentAssistContents
         * @param contentAssistPosition
         * @return
         */
        public boolean doesProposalMatch(String value,
                String contentAssistContents, int contentAssistPosition) {

            if (value != null && !value.isEmpty()
                    && contentAssistContents != null
                    && !contentAssistContents.isEmpty()) {
                String toMatch = null;

                if (!contentAssistContents.isEmpty()) {
                    toMatch =
                            contentAssistPosition > 0 ? contentAssistContents
                                    .substring(0, contentAssistPosition)
                                    : contentAssistContents;
                }

                return toMatch == null || value.startsWith(toMatch);
            }

            return true;
        }

    }

}
