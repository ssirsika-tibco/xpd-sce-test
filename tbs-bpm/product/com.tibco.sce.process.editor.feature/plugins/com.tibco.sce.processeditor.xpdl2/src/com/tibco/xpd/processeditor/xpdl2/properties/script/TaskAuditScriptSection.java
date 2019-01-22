/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * @author Miguel Torres
 */
public class TaskAuditScriptSection extends AbstractAuditScriptSection {

    // Initiated section
    private InitiatedScriptTaskScriptSection initiatedScriptSection;

    // Completed section
    private CompletedScriptTaskScriptSection completedScriptSection;

    // Deadline Expired section
    private DeadlineExpiredScriptTaskScriptSection deadlineExpiredScriptSection;

    // Completed section
    private CancelledScriptTaskScriptSection cancelledScriptSection;

    public TaskAuditScriptSection() {
        super();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.AbstractAuditScriptSection#initializeSections()
     * 
     */
    @Override
    protected void initializeSections(Set<BaseScriptSection> subSections) {
        initiatedScriptSection = new InitiatedScriptTaskScriptSection();
        subSections.add(initiatedScriptSection);
        completedScriptSection = new CompletedScriptTaskScriptSection();
        subSections.add(completedScriptSection);
        deadlineExpiredScriptSection =
                new DeadlineExpiredScriptTaskScriptSection();
        subSections.add(deadlineExpiredScriptSection);
        cancelledScriptSection = new CancelledScriptTaskScriptSection();
        subSections.add(cancelledScriptSection);
    }

    @Override
    protected void doCreateMoreEngineControls(CTabFolder tabFolder,
            XpdFormToolkit toolkit, boolean setSelection) {

        CTabItem tabItem = null;
        // Initiated Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.AuditScriptSection_InitiatedAuditScripts);
        initiatedScriptSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(initiatedScriptSection.createControls(tabFolder,
                toolkit));
        tabItem.setData(DATA_SECTION, initiatedScriptSection);
        if (setSelection) {
            tabFolder.setSelection(tabItem);
        }

        // Completed Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.AuditScriptSection_CompletedAuditScripts);
        completedScriptSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(completedScriptSection.createControls(tabFolder,
                toolkit));
        tabItem.setData(DATA_SECTION, completedScriptSection);

        // Deadline Expired Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem
                .setText(Messages.AuditScriptSection_DeadlineExpiredAuditScripts);
        deadlineExpiredScriptSection
                .setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(deadlineExpiredScriptSection
                .createControls(tabFolder, toolkit));
        tabItem.setData(DATA_SECTION, deadlineExpiredScriptSection);

        // Cancelled Script tab
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.AuditScriptSection_CancelledAuditScripts);
        cancelledScriptSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(cancelledScriptSection.createControls(tabFolder,
                toolkit));
        tabItem.setData(DATA_SECTION, cancelledScriptSection);
    }
    
    @Override
    protected void doCreateMoreClientControls(CTabFolder tabFolder,
            XpdFormToolkit toolkit, boolean setSelection) {
        // No client controls to create here        
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
        if (super.select(toTest)) {
            EObject baseObj = getBaseSelectObject(toTest);
            if (baseObj instanceof Activity && !DecisionFlowUtil.isDecisionsContent(baseObj)) {
                Activity act = (Activity) baseObj;

                TaskType type = TaskObjectUtil.getTaskTypeStrict(act);
                if (type != null && !TaskType.USER_LITERAL.equals(type)) {
                    return true;
                }
            }
        }
        return false;
    }
}
