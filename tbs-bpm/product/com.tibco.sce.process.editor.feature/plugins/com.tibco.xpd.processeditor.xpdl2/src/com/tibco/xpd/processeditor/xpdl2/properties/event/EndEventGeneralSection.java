/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xpd.processeditor.xpdl2.extensions.EventType;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.EventTriggerTypeThrowErrorSection;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.EventTriggerTypeSignalSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * EndEventGeneralSection
 * 
 * @author Sid Allway
 * 
 */

public class EndEventGeneralSection extends BaseEventGeneralSection {

    /**
     * 
     */
    public EndEventGeneralSection() {
        super(EventType.END);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.properties.general.BaseEventGeneralSection
     * #getEventTriggerTypeSections()
     */
    @Override
    public List<EventTriggerTypeSection> getEventTriggerTypeSections() {
        List<EventTriggerTypeSection> trigTypeSections =
                new ArrayList<EventTriggerTypeSection>();

        EventTriggerTypeSection tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_NONE_LITERAL, null,
                        Messages.TriggerResultType_None);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL,
                        new EventTriggerTypeThrowMessageSection(),
                        Messages.TriggerResultType_Message_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL, null,
                        Messages.TriggerResultType_Multiple_menu);
        trigTypeSections.add(tt);
        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_SIGNAL_THROW_LITERAL,
                        new EventTriggerTypeSignalSection(),
                        Messages.TriggerResultType_Signal_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_ERROR_LITERAL,
                        new EventTriggerTypeThrowErrorSection(),
                        Messages.TriggerResultType_Error);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL,
                        new EventTriggerTypeCompensationSection(), /** MR 37869 */
                        Messages.TriggerResultType_Compensation_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_CANCEL_LITERAL, null,
                        Messages.TriggerResultType_CancelSubProcess);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_TERMINATE_LITERAL, null,
                        Messages.TriggerResultType_TerminateProcess);
        trigTypeSections.add(tt);
        return trigTypeSections;
    }

    /*
     * XPD-7263: Removed the shouldShowSolutionDesignForm() method from here as
     * we were doing specific checks which were un-necessary, rather let the
     * super class decide if we want to show rhe solution design form based on
     * the entry in the 'eventImplementation' extension point
     */

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.properties.general.BaseEventGeneralSection
     * #getTriggerTypeLabelText()
     */
    @Override
    protected String getTriggerTypeLabelText() {
        return Messages.EndEventGeneralSection_ResultType;
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            Activity eventAct = (Activity) getBaseSelectObject(toTest);
            Process process = Xpdl2ModelUtil.getProcess(eventAct);
            if (!DecisionFlowUtil.isDecisionFlow(process)
                    && eventAct.getEvent() instanceof EndEvent) {
                return true;
            }
        }
        return false;
    }

}
