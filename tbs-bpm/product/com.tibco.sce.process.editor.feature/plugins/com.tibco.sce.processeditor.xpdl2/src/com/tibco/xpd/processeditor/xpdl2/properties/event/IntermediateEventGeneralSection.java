/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.processeditor.xpdl2.extensions.EventType;
import com.tibco.xpd.processeditor.xpdl2.properties.event.error.EventTriggerTypeCatchErrorSection;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.EventTriggerTypeSignalSection;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;

/**
 * IntermediateEventGeneralSection
 * 
 * @author aallway
 */

public class IntermediateEventGeneralSection extends BaseEventGeneralSection {
    Label targetText;

    /**
     * 
     */
    public IntermediateEventGeneralSection() {
        super(EventType.INTERMEDIATE);
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
                        EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL,
                        new EventTriggerTypeMessageSection(),
                        Messages.TriggerResultType_MessageCatch_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_MESSAGE_THROW_LITERAL,
                        new EventTriggerTypeThrowMessageSection(),
                        Messages.TriggerResultType_MessageThrow_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_TIMER_LITERAL,
                        new EventTriggerTypeTimerSection(),
                        Messages.TriggerResultType_CatchTimer);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_CONDITIONAL_LITERAL,
                        new EventTriggerTypeConditionalSection(),
                        Messages.TriggerResultType_CatchConditional);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_LINK_CATCH_LITERAL, null,
                        Messages.TriggerResultType_LinkCatch_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_LINK_THROW_LITERAL,
                        new EventTriggerTypeLinkSection(),
                        Messages.TriggerResultType_LinkThrow_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL, null,
                        Messages.TriggerResultType_MultipleCatch_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL, null,
                        Messages.TriggerResultType_MultipleThrow_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_ERROR_LITERAL,
                        new EventTriggerTypeCatchErrorSection(),
                        Messages.TriggerResultType_CatchError);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL,
                        new EventTriggerTypeSignalSection(),
                        Messages.TriggerResultType_CatchSignal_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_SIGNAL_THROW_LITERAL,
                        new EventTriggerTypeSignalSection(),
                        Messages.TriggerResultType_ThrowSignal_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL,
                        null, Messages.TriggerResultType_CatchCompensation);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL,
                        new EventTriggerTypeCompensationSection(), /** MR 37869 */
                        Messages.TriggerResultType_ThrowCompensation_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_CANCEL_LITERAL, null,
                        Messages.TriggerResultType_CatchCancelSubProc);
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
        return Messages.IntermediateEventGeneralSection_TriggerType;
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            Activity eventAct = (Activity) getBaseSelectObject(toTest);
            if (eventAct.getEvent() instanceof IntermediateEvent) {
                return true;
            }
        }
        return false;
    }

}
