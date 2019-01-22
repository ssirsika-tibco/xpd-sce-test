/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * {@link CalendarReference} properties section for a Trigger Timer Event.
 * 
 * @author njpatel
 */
public class TriggerTimerEventCalendarReferenceSection extends
        AbstractCalendarReferenceSection {

    /**
     * @param eClass
     */
    public TriggerTimerEventCalendarReferenceSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractCalendarReferenceSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Section section =
                toolkit.createSection(parent, Section.TWISTIE
                        | Section.CLIENT_INDENT | Section.TITLE_BAR
                        | Section.EXPANDED);
        section.setText(Messages.ActivityCalendarReferenceSection_calendarReference_section_label);
        Control control = super.doCreateControls(section, toolkit);

        if (control != null) {
            section.setClient(control);
        }
        return section;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractCalendarReferenceSection#getOtherElementsContainer()
     * 
     * @return
     */
    @Override
    protected OtherElementsContainer getOtherElementsContainer() {
        Activity activity = getActivity();

        if (activity != null) {
            return EventObjectUtil.getTriggerTimer(activity);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.resource.AbstractCalendarReferenceSection#getProcessRelevantData(java.lang.String)
     * 
     * @param id
     * @return
     */
    @Override
    protected ProcessRelevantData getProcessRelevantData(String id) {
        Activity activity = getActivity();
        if (activity != null && id != null) {
            List<ProcessRelevantData> allData =
                    ProcessInterfaceUtil
                            .getAllAvailableRelevantDataForActivity(activity);

            if (allData != null) {
                for (ProcessRelevantData data : allData) {
                    if (id.equals(data.getId())) {
                        return data;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = getBaseSelectObject(toTest);
        if (eo instanceof Activity) {
            EventTriggerType type =
                    EventObjectUtil.getEventTriggerType((Activity) eo);

            if (type == EventTriggerType.EVENT_TIMER_LITERAL) {
                /*
                 * Only show this section for Business Processes
                 */
                Process process = Xpdl2ModelUtil.getProcess(eo);
                return process != null
                        && Xpdl2ModelUtil.isBusinessProcess(process);
            }
        }

        return false;
    }

    /**
     * Get the Activity input of this section.
     * 
     * @return
     */
    private Activity getActivity() {
        EObject input = getInput();
        if (input instanceof Activity) {
            return (Activity) input;
        }
        return null;
    }

}
