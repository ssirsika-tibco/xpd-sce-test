/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.event;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.RescheduleDurationType;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Reschedule timer event script section.
 * 
 * @author aallway
 * @since 18 Mar 2013
 */
public class RescheduleTimerEventScriptSection extends BaseProcessScriptSection {

    private Button relativeTo;

    private Button relativeToRescheduleTime;

    private Button relativeToCurrentTimeout;

    private Label relativesLabel;

    public RescheduleTimerEventScriptSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#createGrammarAreaExtras(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Composite createGrammarAreaExtras(Composite parent,
            XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);

        GridLayout gl = new GridLayout(3, false);
        gl.marginTop = gl.marginHeight;
        gl.marginLeft = gl.marginWidth;
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        container.setLayout(gl);

        relativesLabel =
                toolkit.createLabel(container,
                        Messages.RescheduleTimerEventScriptSection_DurationsRelativeTo_label);
        relativesLabel.setLayoutData(new GridData());

        relativeToRescheduleTime =
                toolkit.createButton(container,
                        Messages.RescheduleTimerEventScriptSection_RelativeToRescheduleTime_radio_button,
                        SWT.RADIO);
        relativeToRescheduleTime
                .setToolTipText(Messages.RescheduleTimerEventScriptSection_RelativeToRescheduleTime_radio_tooltip);
        relativeToRescheduleTime.setLayoutData(new GridData());

        manageControl(relativeToRescheduleTime);

        relativeToCurrentTimeout =
                toolkit.createButton(container,
                        Messages.RescheduleTimerEventScriptSection_RelativeToCurrentTimeout_radio_button,
                        SWT.RADIO);
        relativeToCurrentTimeout
                .setToolTipText(Messages.RescheduleTimerEventScriptSection_RelativeToCurrentTimeout_radio_tooltip);
        relativeToCurrentTimeout.setLayoutData(new GridData());

        manageControl(relativeToCurrentTimeout);

        return container;
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();

        if (relativeToRescheduleTime != null
                && relativeToCurrentTimeout != null) {
            RescheduleDurationType rescheduleDurationType = null;

            RescheduleTimerScript rescheduleTimerScript =
                    EventObjectUtil.getRescheduleTimerScript(getActivity());

            if (rescheduleTimerScript != null) {
                rescheduleDurationType =
                        rescheduleTimerScript.getDurationRelativeTo();
            }

            relativeToRescheduleTime
                    .setSelection(RescheduleDurationType.RESCHEDULE_TIME
                            .equals(rescheduleDurationType));
            relativeToCurrentTimeout
                    .setSelection(RescheduleDurationType.EXISTING_TIMEOUT
                            .equals(rescheduleDurationType));

            relativeToRescheduleTime.setEnabled(rescheduleTimerScript != null);
            relativeToCurrentTimeout.setEnabled(rescheduleTimerScript != null);
            relativesLabel.setEnabled(rescheduleTimerScript != null);
        }
    }

    /**
     * @see com.tibco.xpd.script.ui.internal.BaseScriptSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        if (obj == relativeToRescheduleTime) {
            RescheduleTimerScript rescheduleTimerScript =
                    EventObjectUtil.getRescheduleTimerScript(getActivity());

            if (rescheduleTimerScript != null
                    && !RescheduleDurationType.RESCHEDULE_TIME
                            .equals(rescheduleTimerScript
                                    .getDurationRelativeTo())) {

                return SetCommand.create(getEditingDomain(),
                        rescheduleTimerScript,
                        XpdExtensionPackage.eINSTANCE
                                .getRescheduleTimerScript_DurationRelativeTo(),
                        RescheduleDurationType.RESCHEDULE_TIME);
            }

        } else if (obj == relativeToCurrentTimeout) {
            RescheduleTimerScript rescheduleTimerScript =
                    EventObjectUtil.getRescheduleTimerScript(getActivity());

            if (rescheduleTimerScript != null
                    && !RescheduleDurationType.EXISTING_TIMEOUT
                            .equals(rescheduleTimerScript
                                    .getDurationRelativeTo())) {

                return SetCommand.create(getEditingDomain(),
                        rescheduleTimerScript,
                        XpdExtensionPackage.eINSTANCE
                                .getRescheduleTimerScript_DurationRelativeTo(),
                        RescheduleDurationType.EXISTING_TIMEOUT);
            }

        } else {
            return super.doGetCommand(obj);
        }

        return null;
    }

    @Override
    public String getCurrentSetScriptGrammarId() {
        RescheduleTimerScript rescheduleTimerScript =
                getRescheduleTimerScript();

        if (rescheduleTimerScript != null) {
            return rescheduleTimerScript.getScriptGrammar();
        }

        return null;
    }

    @Override
    public String getScriptContext() {
        return ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT;
    }

    @Override
    protected Command getSetScriptGrammarCommand(String grammar,
            AbstractScriptEditorSection editorSection) {
        Command cmd = null;
        Activity eventAct = getActivity();
        EditingDomain ed = getEditingDomain();
        if (eventAct != null && ed != null && editorSection != null) {
            cmd = editorSection.getSetScriptGrammarCommand(ed, eventAct);
        }
        return cmd;
    }

    /**
     * @return The xpdExt:RescheduleTimerScript of the current input timer event
     *         activity if script is set.
     */
    private RescheduleTimerScript getRescheduleTimerScript() {
        Activity activity = getActivity();

        if (activity != null) {
            return EventObjectUtil.getRescheduleTimerScript(activity);
        }

        return null;
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

    @Override
    public boolean isLoadProcessData() {
        return false;
    }

}
