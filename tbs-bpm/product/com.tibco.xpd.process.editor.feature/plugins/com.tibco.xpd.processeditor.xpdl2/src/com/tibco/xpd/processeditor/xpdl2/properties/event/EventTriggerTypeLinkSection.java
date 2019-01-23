/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldChangedListener;
import com.tibco.xpd.processeditor.xpdl2.properties.util.FixedValueFieldAssistHelper.FixedValueFieldProposalProvider;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.util.RevealProcessDiagramEObject;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * EventTriggerTypeSection
 * 
 * @author aallway
 */
public class EventTriggerTypeLinkSection extends
        AbstractFilteredTransactionalSection implements
        FixedValueFieldChangedListener {

    private String instrumentationPrefixName;

    private class LinkEventListEntry {
        private Activity eventAct;

        public LinkEventListEntry(Activity eventAct) {
            this.eventAct = eventAct;
        }

        public String toString() {
            return getName();
        }

        public String getName() {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(eventAct);

            if (name == null || name.length() == 0) {
                name = Messages.EventTriggerTypeLinkSection_UnnamedEvent_label;
            }
            return name;
        }

        public String getId() {
            return eventAct.getId();
        }
    }

    private List<LinkEventListEntry> linkEventActivityList;

    DecoratedField linkedEventField;

    private Button refEventReveal;

    private Button refEventBack;

    public EventTriggerTypeLinkSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public EventTriggerTypeLinkSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;

    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite linkEventPage = toolkit.createComposite(parent);
        GridLayout pageLayout = new GridLayout(2, false);
        pageLayout.marginBottom = pageLayout.marginHeight;
        pageLayout.marginTop = 0;
        pageLayout.marginHeight = 0;

        linkEventPage.setLayout(pageLayout);

        // Label showing current referenced event.
        Label lab =
                toolkit.createLabel(linkEventPage,
                        Messages.EventTriggerTypeLinkSection_LinksTo_label,
                        SWT.LEFT);
        lab.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

        //
        // Add content assisted text control for referenced event entry.
        FixedValueFieldProposalProvider proposalProvider =
                new FixedValueFieldAssistHelper.FixedValueFieldProposalProvider() {
                    public Object[] getProposals() {
                        if (linkEventActivityList != null) {
                            return linkEventActivityList.toArray();
                        }
                        return null;
                    }
                };

        FixedValueFieldAssistHelper refTaskHelper =
                new FixedValueFieldAssistHelper(toolkit, linkEventPage,
                        proposalProvider, true);

        refTaskHelper.addValueChangedListener(this);

        linkedEventField = refTaskHelper.getDecoratedField();
        linkedEventField.getControl()
                .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                        instrumentationPrefixName + "LinksTo"); //$NON-NLS-1$
        linkedEventField.getControl().setData("name", "textEventLinkTo"); //$NON-NLS-1$ //$NON-NLS-2$
        linkedEventField.getLayoutControl()
                .setData(XpdFormToolkit.INSTRUMENTATION_DATA_NAME,
                        "assist" + instrumentationPrefixName + "LinkTo"); //$NON-NLS-1$ //$NON-NLS-2$

        linkedEventField.getLayoutControl().setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        linkedEventField.getLayoutControl().setBackground(linkEventPage
                .getBackground());

        //
        // Dummy composite to place buttons under text box...
        Composite cmp = toolkit.createComposite(linkEventPage);
        cmp.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL));

        // And one to put buttons in.
        Composite btnHolder = toolkit.createComposite(linkEventPage);
        btnHolder
                .setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        GridLayout btnHolderLayout = new GridLayout(2, true);
        btnHolderLayout.marginBottom = btnHolderLayout.marginHeight;
        btnHolderLayout.marginTop = 0;
        btnHolderLayout.marginHeight = 0;
        btnHolder.setLayout(btnHolderLayout);

        //
        // Add the reveal and back buttons.
        GridData btnGD = new GridData(SWT.FILL, SWT.NONE, false, false);

        refEventReveal =
                toolkit.createButton(btnHolder,
                        Messages.EventTriggerTypeLinkSection_Reveal_button,
                        SWT.PUSH,
                        "Reveal"); //$NON-NLS-1$
        refEventReveal.setLayoutData(btnGD);
        manageControl(refEventReveal);

        btnGD = new GridData(SWT.FILL, SWT.NONE, false, false);
        refEventBack =
                toolkit.createButton(btnHolder,
                        Messages.EventTriggerTypeLinkSection_Back_button,
                        SWT.PUSH,
                        "Back"); //$NON-NLS-1$
        refEventBack.setLayoutData(btnGD);
        manageControl(refEventBack);

        return linkEventPage;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        Activity act = getActivity();
        if (act != null) {
            if (obj == refEventReveal) {
                String refEventId = EventObjectUtil.getLinkEventId(act);
                if (refEventId != null) {
                    revealEventInDiagram(refEventId);
                }
            } else if (obj == refEventBack) {
                revealEventInDiagram(act.getId());
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        Activity inputEvent = getActivity();
        if (inputEvent != null) {

            // XPD-3661 Content assist Pop up in throw link event's property
            // section [Link To:],
            // should list only in scope catch link events.
            // some code refactor to avoid double iteration on the list.
            Collection<Activity> eventList =
                    EventObjectUtil.getFlowContainerEvents(inputEvent
                            .getFlowContainer());
            String textValue = "";
            linkEventActivityList =
                    new ArrayList<LinkEventListEntry>(eventList.size());
            // Remove non link events.
            for (Iterator<Activity> iter = eventList.iterator(); iter.hasNext();) {
                Activity eventAct = iter.next();

                if ((!EventTriggerType.EVENT_LINK_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(eventAct)) && !EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL
                        .equals(EventObjectUtil.getEventTriggerType(eventAct)))) {
                    // XPDL21 Deal with link event of throw type - guess this
                    // is for the property section.
                    iter.remove();
                } else {

                    // It's a link event, perform further checks...

                    if (EventFlowType.FLOW_END_LITERAL.equals(EventObjectUtil
                            .getFlowType(eventAct))) {
                        // Cannot link TO end event
                        iter.remove();

                    } else if (EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL
                            .equals(EventObjectUtil
                                    .getEventTriggerType(eventAct))
                            && EventFlowType.FLOW_INTERMEDIATE_LITERAL
                                    .equals(EventObjectUtil
                                            .getFlowType(eventAct))) {
                        // Cannot link TO an Intermediate Multiple
                        iter.remove();

                    } else if (EventFlowType.FLOW_START_LITERAL
                            .equals(EventObjectUtil.getFlowType(eventAct))
                            && EventFlowType.FLOW_INTERMEDIATE_LITERAL
                                    .equals(EventObjectUtil
                                            .getFlowType(inputEvent))) {
                        // Cannot link TO Start FROM Intermediate
                        iter.remove();
                    } else {
                        // Eveything else is ok...
                        // Inter->Inter, End->Start, End->Inter
                        // MUST remove this event from the list, don't allow
                        // self-reference.
                        //$NON-NLS-1$
                        String eventId = inputEvent.getId();
                        String linkEventId =
                                EventObjectUtil.getLinkEventId(inputEvent);

                        // Don't allow self-reference.
                        if (!eventId.equals(eventAct.getId())) {
                            linkEventActivityList.add(new LinkEventListEntry(
                                    eventAct));

                            if (eventAct.getId().equals(linkEventId)) {
                                // XPD-3661 using eventAct.getName() would use
                                // cleansed names and thus make Catch1, Catch2
                                // as Catch always, to avoid this

                                textValue =
                                        Xpdl2ModelUtil
                                                .getDisplayNameOrName(eventAct);
                            }
                        }
                    }
                }
                if (linkedEventField != null && textValue != null) {
                    ((Text) linkedEventField.getControl()).setText(textValue);
                }

            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.properties.FixedValueFieldAssistHelper.
     * FixedValueFieldChangedListener#fixedValueFieldChanged(java.lang.Object)
     */
    public void fixedValueFieldChanged(Object newValue) {
        Activity eventAct = getActivity();
        EditingDomain ed = getEditingDomain();

        LinkEventListEntry eventEntry = null;

        if (eventAct != null && ed != null) {

            if (newValue instanceof LinkEventListEntry) {
                eventEntry = (LinkEventListEntry) newValue;
            }

            String curLinkEventId = EventObjectUtil.getLinkEventId(eventAct);

            // Only set the referenced event if it has changed doRefresh() has
            // to
            // set the selection to currently referenced event when it does, we
            // received a selectionChanged, if we run cmd again then doRefresh()
            // gets called again and it all goes squiffy.
            boolean haveCurrent =
                    (curLinkEventId != null && curLinkEventId.length() > 0);

            boolean haveNew =
                    (eventEntry != null && eventEntry.getId() != null && eventEntry
                            .getId().length() > 0);

            // If changed from none to something (or visa-versa) or we have a
            // both current and new values and they've changed.
            if (haveCurrent != haveNew
                    || (eventEntry != null && !eventEntry.getId()
                            .equals(curLinkEventId))) {
                // Do 2 things, set the referenced event and set the name of
                // this event to "Link To: <referenced event's name>"
                CompoundCommand cmd = new CompoundCommand();

                if (haveNew) {
                    cmd
                            .setLabel(Messages.EventTriggerTypeLinkSection_SetLinkTarget_menu);
                    cmd.append(EventObjectUtil.getSetEventLinkIdCommand(ed,
                            eventAct,
                            eventEntry.getId(),
                            Xpdl2ModelUtil.getProcess(eventAct).getId()));

                } else {
                    cmd
                            .setLabel(Messages.EventTriggerTypeLinkSection_UnsetLinkTarget_menu);
                    cmd.append(EventObjectUtil.getSetEventLinkIdCommand(ed,
                            eventAct,
                            "0", "-unknown-")); //$NON-NLS-1$ //$NON-NLS-2$
                }

                String newEventName = ""; //$NON-NLS-1$

                if (eventEntry == null || eventEntry.getId() == null
                        || eventEntry.getId().length() < 1) {
                    // XPDL21 Deal with link event of throw type - guess this is
                    // for the property section.
                    newEventName =
                            EventTriggerType.EVENT_LINK_CATCH_LITERAL
                                    .toString();
                } else {
                    newEventName =
                            Messages.ChangeEventTriggerTypeCommand_LinkTo_Label
                                    + eventEntry.getName();
                }

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        eventAct,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName(),
                        newEventName));

                if (cmd.canExecute()) {
                    ed.getCommandStack().execute(cmd);
                }
            }
        }
    }

    /**
     * Given a event id scroll the diagram to show it.
     * 
     * @param eventId
     */
    private void revealEventInDiagram(String eventId) {
        if (getActivity() != null) {
            EObject ev =
                    Xpdl2ModelUtil.getProcess(getActivity()).getPackage()
                            .findNamedElement(eventId);
            if (ev != null) {
                RevealProcessDiagramEObject.revealEObject(getSite(), ev, false);
            }
        }
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

}
