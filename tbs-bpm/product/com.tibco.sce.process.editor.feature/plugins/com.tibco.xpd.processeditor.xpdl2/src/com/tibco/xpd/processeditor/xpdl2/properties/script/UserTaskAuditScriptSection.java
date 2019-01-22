/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Route;

/**
 * @author Miguel Torres
 */
public class UserTaskAuditScriptSection extends TaskAuditScriptSection {

    // Schedule section
    private ScheduleUserTaskScriptSection scheduleScriptSection;

    // Reschedule section
    private RescheduleUserTaskScriptSection rescheduleScriptSection;

    // Init section
    private OpenUserTaskScriptSection initScriptSection;

    // Keep section
    private CloseUserTaskScriptSection closeScriptSection;

    // Release section
    private SubmitUserTaskScriptSection submitScriptSection;

    public UserTaskAuditScriptSection() {
        super();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.AbstractAuditScriptSection#initializeSections()
     * 
     */
    @Override
    protected void initializeSections(Set<BaseScriptSection> subSections) {
        scheduleScriptSection = new ScheduleUserTaskScriptSection();
        subSections.add(scheduleScriptSection);
        rescheduleScriptSection = new RescheduleUserTaskScriptSection();
        subSections.add(rescheduleScriptSection);
        initScriptSection = new OpenUserTaskScriptSection();
        subSections.add(initScriptSection);
        closeScriptSection = new CloseUserTaskScriptSection();
        subSections.add(closeScriptSection);
        submitScriptSection = new SubmitUserTaskScriptSection();
        subSections.add(submitScriptSection);
        super.initializeSections(subSections);
    }

    @Override
    protected void doCreateMoreClientControls(CTabFolder tabFolder,
            XpdFormToolkit toolkit, boolean setSelection) {

        CTabItem tabItem = null;

        // Schedule Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.UserTaskScriptSection_ScheduleUserTaskScripts);
        scheduleScriptSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(scheduleScriptSection.createControls(tabFolder,
                toolkit));
        tabItem.setData(DATA_SECTION, scheduleScriptSection);
        tabFolder.setSelection(tabItem);

        // Reschedule Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.UserTaskAuditScriptSection_RescheduleScript_label);
        rescheduleScriptSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(rescheduleScriptSection.createControls(tabFolder,
                toolkit));
        tabItem.setData(DATA_SECTION, rescheduleScriptSection);

        // Init Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.UserTaskScriptSection_InitUserTaskScripts);
        initScriptSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(initScriptSection.createControls(tabFolder, toolkit));
        tabItem.setData(DATA_SECTION, initScriptSection);

        // Close Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.UserTaskScriptSection_CloseUserTaskScripts);
        closeScriptSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(closeScriptSection
                .createControls(tabFolder, toolkit));
        tabItem.setData(DATA_SECTION, closeScriptSection);

        // Submit Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.UserTaskScriptSection_SubmitUserTaskScripts);
        submitScriptSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(submitScriptSection.createControls(tabFolder,
                toolkit));
        tabItem.setData(DATA_SECTION, submitScriptSection);

        super.doCreateMoreClientControls(tabFolder, toolkit, false);
    }

    @Override
    public void setInput(Collection items) {
        super.setInput(items);
    }

    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    @Override
    public boolean select(Object toTest) {
        Activity act = (Activity) getBaseSelectObject(toTest);
        if (act != null) {
            Route route = act.getRoute();
            if (route == null && isUserTask(act)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isEligibleScriptSection(String id) {
        return ENGINE_SCRIPT_SECTION.equals(id)
                || CLIENT_SCRIPT_SECTION.equals(id);
    }

}
