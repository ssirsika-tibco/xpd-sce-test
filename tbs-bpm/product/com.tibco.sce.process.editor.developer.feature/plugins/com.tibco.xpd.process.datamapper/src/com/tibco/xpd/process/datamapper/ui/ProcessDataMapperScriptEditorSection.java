/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.process.datamapper.ui;

import java.util.Collection;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.osgi.framework.Bundle;

import com.tibco.xpd.process.datamapper.ProcessDataMapperPlugin;
import com.tibco.xpd.process.datamapper.common.AbstractProcessDataMapperSection;
import com.tibco.xpd.process.datamapper.internal.Messages;
import com.tibco.xpd.process.datamapper.scripttask.ScriptTaskDataMapperTabSection;
import com.tibco.xpd.process.datamapper.scripttaskscripts.CancelScriptDataMapperSection;
import com.tibco.xpd.process.datamapper.scripttaskscripts.CompleteScriptDataMapperSection;
import com.tibco.xpd.process.datamapper.scripttaskscripts.InitiateScriptDataMapperSection;
import com.tibco.xpd.process.datamapper.scripttaskscripts.TimeoutScriptDataMapperSection;
import com.tibco.xpd.process.datamapper.usertaskscripts.CloseUserTaskDataMapperSection;
import com.tibco.xpd.process.datamapper.usertaskscripts.OpenUserTaskDataMapperSection;
import com.tibco.xpd.process.datamapper.usertaskscripts.RescheduleUserTaskDataMapperSection;
import com.tibco.xpd.process.datamapper.usertaskscripts.ScheduleUserTaskDataMapperSection;
import com.tibco.xpd.process.datamapper.usertaskscripts.SubmitUserTaskDataMapperSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * This class is responsible for creating Process Data Mapper sections when
 * 'Data Mapper' grammar is selected in a Script Editor Section. It will create
 * relevant Data Mapper section based on the current context (e.g., for Script
 * Task General section, it will create an instance of
 * ScriptTaskDataMapperTabSection and delegate various methods to it and so on
 * for other contexts). It also contributes hyperlink control to show the mapper
 * in a Data Mapper tab for Script Task General section.
 * 
 * @author Ali
 * @since 12 Jan 2015
 */
public class ProcessDataMapperScriptEditorSection extends
        AbstractScriptEditorSection {

    AbstractProcessDataMapperSection mapperSection;

    public ProcessDataMapperScriptEditorSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getScriptGrammar()
     * 
     * @return
     */
    @Override
    protected String getScriptGrammar() {
        return ScriptGrammarFactory.DATAMAPPER;
    }

    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        if (mapperSection == null) {
            createDataMapperSection();
        }

        Composite mapperCompoiste = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout();
        layout.marginHeight--;
        mapperCompoiste.setLayout(layout);

        // create process data mapper section controls
        if (mapperSection != null) {
            mapperSection.createControls(mapperCompoiste,
                    getPropertySheetPage());
            mapperSection.getControl().setLayoutData(new GridData(
                    GridData.FILL_BOTH));
        }

        return mapperCompoiste;

    }

    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject eObject) {
        if (mapperSection != null) {
            return mapperSection.getSetDataMapperGrammarCommand(editingDomain,
                    getScriptGrammar(),
                    eObject);
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        super.doRefresh();
        if (mapperSection != null) {
            mapperSection.refresh();
        }
    }

    @Override
    public void setInput(Collection<?> items) {

        super.setInput(items);
        if (mapperSection != null) {
            mapperSection.setInput(items);
        }
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#createGrammarSelectorRHSControls(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    public Control createGrammarSelectorRHSControls(Composite parent) {
        if (ProcessScriptContextConstants.SCRIPT_TASK
                .equals(getScriptContext())) {

            Hyperlink showMapperInTabLink =
                    getWidgetFactory()
                            .createHyperlink(parent,
                                    Messages.ProcessDataMapperScriptEditorSection_ShowMapperTabLinkTitle,
                                    SWT.NONE);
            showMapperInTabLink.setVisible(true);
            showMapperInTabLink.addHyperlinkListener(new IHyperlinkListener() {

                @Override
                public void linkActivated(HyperlinkEvent e) {
                    Bundle b = ProcessDataMapperPlugin.getDefault().getBundle();
                    String tabName =
                            Platform.getResourceString(b,
                                    "com.tibco.xpd.processeditor.propertyTabs.datamapper"); //$NON-NLS-1$
                    showPropertyTab(tabName);
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

            return showMapperInTabLink;
        }
        return null;
    }

    /**
     * This will create relevant mapper section based on the current script
     * context
     */
    private void createDataMapperSection() {

        String scriptContext = getScriptContext();

        if (ProcessScriptContextConstants.SCRIPT_TASK.equals(scriptContext)) {
            mapperSection = new ScriptTaskDataMapperTabSection();
            ((ScriptTaskDataMapperTabSection) mapperSection)
                    .setWantHeaderLabel(false);
        } else if (ProcessScriptContextConstants.INITIATED_SCRIPT_TASK
                .equals(scriptContext)) {
            mapperSection = new InitiateScriptDataMapperSection();
        } else if (ProcessScriptContextConstants.COMPLETED_SCRIPT_TASK
                .equals(scriptContext)) {
            mapperSection = new CompleteScriptDataMapperSection();
        } else if (ProcessScriptContextConstants.CANCELLED_SCRIPT_TASK
                .equals(scriptContext)) {
            mapperSection = new CancelScriptDataMapperSection();
        } else if (ProcessScriptContextConstants.DEADLINE_EXPIRED_SCRIPT_TASK
                .equals(scriptContext)) {
            mapperSection = new TimeoutScriptDataMapperSection();
        } else if (ProcessScriptContextConstants.SCHEDULE_USER_TASK
                .equals(scriptContext)) {
            mapperSection = new ScheduleUserTaskDataMapperSection();
        } else if (ProcessScriptContextConstants.RESCHEDULE_USER_TASK
                .equals(scriptContext)) {
            mapperSection = new RescheduleUserTaskDataMapperSection();
        } else if (ProcessScriptContextConstants.OPEN_USER_TASK
                .equals(scriptContext)) {
            mapperSection = new OpenUserTaskDataMapperSection();
        } else if (ProcessScriptContextConstants.CLOSE_USER_TASK
                .equals(scriptContext)) {
            mapperSection = new CloseUserTaskDataMapperSection();
        } else if (ProcessScriptContextConstants.SUBMIT_USER_TASK
                .equals(scriptContext)) {
            mapperSection = new SubmitUserTaskDataMapperSection();
        }
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (mapperSection != null) {
            mapperSection.dispose();
        }
        super.dispose();
    }

    /**
     * Sid XPD-7575 - Set offset margin as we have nested sections that waste a
     * lot of space.
     * 
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getSectionMarginOffset()
     * 
     * @return
     */
    @Override
    public Point getSectionMarginOffset() {
        return new Point(-5, -15);
    }

}
