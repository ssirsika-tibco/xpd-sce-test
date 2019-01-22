/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.RescheduleDurationType;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Constant period script editor section.
 * 
 * @author aallway
 * 
 */
public class ConstantPeriodScriptSection extends AbstractScriptEditorSection {

    private Spinner yearsText = null;

    private Spinner monthsText = null;

    private Spinner weeksText = null;

    private Spinner daysText = null;

    private Spinner hoursText = null;

    private Spinner minutesText = null;

    private Spinner secondsText = null;

    private Spinner microSecondsText = null;

    /**
     * Constructor.
     */
    public ConstantPeriodScriptSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        ScrolledComposite srcComposite =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);
        srcComposite.setExpandHorizontal(true);
        srcComposite.setExpandVertical(true);
        srcComposite.setLayout(new FillLayout());

        Composite root = toolkit.createComposite(srcComposite);
        srcComposite.setContent(root);

        GridLayout layout = new GridLayout(4, false);
        layout.horizontalSpacing = 10;
        layout.verticalSpacing = 7;
        root.setLayout(layout);

        Label lab =
                toolkit.createLabel(root,
                        Messages.ConstantPeriodScriptSection_SpecifyConstantTimeout_message);
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        gd.horizontalSpan = 4;
        lab.setLayoutData(gd);

        yearsText =
                createPeriodTextControl(toolkit,
                        root,
                        Messages.ConstantPeriodScriptSection_Years_label + ":"); //$NON-NLS-1$
        hoursText =
                createPeriodTextControl(toolkit,
                        root,
                        Messages.ConstantPeriodScriptSection_Hours_label + ":"); //$NON-NLS-1$
        monthsText =
                createPeriodTextControl(toolkit,
                        root,
                        Messages.ConstantPeriodScriptSection_Months_label + ":"); //$NON-NLS-1$
        minutesText =
                createPeriodTextControl(toolkit,
                        root,
                        Messages.ConstantPeriodScriptSection_Minutes_label
                                + ":"); //$NON-NLS-1$
        weeksText =
                createPeriodTextControl(toolkit,
                        root,
                        Messages.ConstantPeriodScriptSection_Weeks_label + ":"); //$NON-NLS-1$
        secondsText =
                createPeriodTextControl(toolkit,
                        root,
                        Messages.ConstantPeriodScriptSection_Seconds_label
                                + ":"); //$NON-NLS-1$
        daysText =
                createPeriodTextControl(toolkit,
                        root,
                        Messages.ConstantPeriodScriptSection_Days_label + ":"); //$NON-NLS-1$
        microSecondsText =
                createPeriodTextControl(toolkit,
                        root,
                        Messages.ConstantPeriodScriptSection_MicroSeconds_label
                                + ":"); //$NON-NLS-1$

        root.setTabList(new Control[] { yearsText, monthsText, weeksText,
                daysText, hoursText, minutesText, secondsText, microSecondsText });

        srcComposite.setMinSize(root.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return srcComposite;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public Command doGetCommand(Object obj) {
        CompoundCommand cmd = new CompoundCommand();

        if (obj instanceof Spinner) {
            BigInteger intVal = null;

            //
            // Get the integer value from spinner.
            int val = ((Spinner) obj).getSelection();
            if (val > 0) {
                intVal = new BigInteger(String.valueOf(val));
            }

            //
            // Create the command to set it.
            ConstantPeriod constantPeriod =
                    getOrCreateConstantPeriod(getEditingDomain(), cmd);

            if (constantPeriod == null) {
                return null;
            }

            String periodName = ""; //$NON-NLS-1$

            EAttribute eAttr = null;
            if (obj == yearsText) {
                eAttr = XpdExtensionPackage.eINSTANCE.getConstantPeriod_Years();
                periodName = Messages.ConstantPeriodScriptSection_Years_label;
            } else if (obj == monthsText) {
                eAttr =
                        XpdExtensionPackage.eINSTANCE
                                .getConstantPeriod_Months();
                periodName = Messages.ConstantPeriodScriptSection_Months_label;
            } else if (obj == weeksText) {
                eAttr = XpdExtensionPackage.eINSTANCE.getConstantPeriod_Weeks();
                periodName = Messages.ConstantPeriodScriptSection_Weeks_label;
            } else if (obj == daysText) {
                eAttr = XpdExtensionPackage.eINSTANCE.getConstantPeriod_Days();
                periodName = Messages.ConstantPeriodScriptSection_Days_label;
            } else if (obj == hoursText) {
                eAttr = XpdExtensionPackage.eINSTANCE.getConstantPeriod_Hours();
                periodName = Messages.ConstantPeriodScriptSection_Hours_label;
            } else if (obj == minutesText) {
                eAttr =
                        XpdExtensionPackage.eINSTANCE
                                .getConstantPeriod_Minutes();
                periodName = Messages.ConstantPeriodScriptSection_Minutes_label;
            } else if (obj == secondsText) {
                eAttr =
                        XpdExtensionPackage.eINSTANCE
                                .getConstantPeriod_Seconds();
                periodName = Messages.ConstantPeriodScriptSection_Seconds_label;
            } else if (obj == microSecondsText) {
                eAttr =
                        XpdExtensionPackage.eINSTANCE
                                .getConstantPeriod_MicroSeconds();
                periodName =
                        Messages.ConstantPeriodScriptSection_MicroSeconds_label;
            }

            if (eAttr != null) {
                if (constantPeriod.eContainer() == null) {
                    constantPeriod.eSet(eAttr, intVal);
                } else {
                    cmd.append(SetCommand.create(getEditingDomain(),
                            constantPeriod,
                            eAttr,
                            intVal));
                }

                cmd.setLabel(String
                        .format(Messages.ConstantPeriodScriptSection_SetPeriod_menu,
                                periodName));

            } else {
                cmd = null;
            }
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    public void doRefresh() {
        if (yearsText != null && !yearsText.isDisposed()) {
            if (getInput() != null) {
                ConstantPeriod constantPeriod = getConstantPeriod();

                if (constantPeriod != null) {
                    String val;
                    BigInteger bi;

                    setSpinner(constantPeriod.getYears(), yearsText);
                    setSpinner(constantPeriod.getMonths(), monthsText);
                    setSpinner(constantPeriod.getWeeks(), weeksText);
                    setSpinner(constantPeriod.getDays(), daysText);
                    setSpinner(constantPeriod.getHours(), hoursText);
                    setSpinner(constantPeriod.getMinutes(), minutesText);
                    setSpinner(constantPeriod.getSeconds(), secondsText);
                    setSpinner(constantPeriod.getMicroSeconds(),
                            microSecondsText);

                    return;
                }
            }

            setSpinner(null, yearsText);
            setSpinner(null, monthsText);
            setSpinner(null, weeksText);
            setSpinner(null, daysText);
            setSpinner(null, hoursText);
            setSpinner(null, minutesText);
            setSpinner(null, secondsText);
            setSpinner(null, microSecondsText);

        }

        return;
    }

    /**
     * Set up one pos integer only text field and its label.
     * 
     * @param toolkit
     * @param parent
     * @param label
     * @return
     */
    private Spinner createPeriodTextControl(XpdFormToolkit toolkit,
            Composite parent, String label) {

        Label lab = toolkit.createLabel(parent, label);
        lab.setLayoutData(new GridData());

        Spinner spinner = toolkit.createSpinner(parent);

        spinner.setDigits(0);
        spinner.setIncrement(1);
        spinner.setPageIncrement(5);
        spinner.setMinimum(0);
        spinner.setMaximum(Integer.MAX_VALUE);

        GridData gd = new GridData();
        spinner.setLayoutData(gd);

        manageControl(spinner);

        return spinner;
    }

    private void setSpinner(BigInteger val, Spinner spinner) {
        if (val != null) {
            spinner.setSelection(val.intValue());
        } else {
            spinner.setSelection(0);
        }
    }

    /**
     * Get the selected activity
     * 
     * @return
     */
    private Activity getActivity() {
        EObject activity = getInput();
        if (!(activity instanceof Activity)) {
            throw new IllegalStateException(
                    "Input for constant period section must be an Activity"); //$NON-NLS-1$
        }
        return (Activity) activity;
    }

    /**
     * Get the deadline element for activity.
     * 
     * @return
     */
    private Expression getContainingExpression() {
        Expression containingExpression = null;

        if (ProcessScriptContextConstants.TIMER_EVENT
                .equals(getScriptContext())) {
            EList list = getActivity().getDeadline();
            if (list != null && list.size() > 0) {
                Deadline deadline = (Deadline) list.get(0);
                containingExpression = deadline.getDeadlineDuration();
            }

        } else if (ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT
                .equals(getScriptContext())) {
            containingExpression =
                    EventObjectUtil.getRescheduleTimerScript(getActivity());
        }

        if (containingExpression == null) {
            return null;
        }

        if (!EventObjectUtil.CONSTANTPERIOD_SCRIPTGRAMMAR
                .equals(containingExpression.getScriptGrammar())) {
            return null;
        }

        return containingExpression;
    }

    /**
     * Get the special extension constant period element under deadline duration
     * element.
     * 
     * @return
     */
    private ConstantPeriod getConstantPeriod() {
        Expression containingExpression = getContainingExpression();

        if (containingExpression != null) {
            Object cp =
                    containingExpression
                            .getMixed()
                            .get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ConstantPeriod(),
                                    false);
            if (cp instanceof List) {
                List list = (List) cp;
                if (list.size() > 0) {
                    cp = list.get(0);
                }
            }
            if (cp instanceof ConstantPeriod) {
                return (ConstantPeriod) cp;
            }
        }
        return null;

    }

    /**
     * Get the constant period element under DeadlineDuration expression, detail
     * and, if it does not already exist, append a command to create it to the
     * given command.
     * 
     * @param editingDomain
     * @param cmd
     * @return
     */
    private ConstantPeriod getOrCreateConstantPeriod(
            EditingDomain editingDomain, CompoundCommand cmd) {

        ConstantPeriod constantPeriod = getConstantPeriod();

        if (constantPeriod == null) {
            //
            // There is no constant period element in expression, so create a
            // new expression with one in.
            //

            if (ProcessScriptContextConstants.TIMER_EVENT
                    .equals(getScriptContext())) {

                Deadline curDeadline =
                        ProcessScriptUtil.getDeadline(getActivity());
                if (curDeadline != null) {
                    cmd.append(RemoveCommand.create(editingDomain, curDeadline));
                }

                constantPeriod =
                        XpdExtensionFactory.eINSTANCE.createConstantPeriod();

                Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();
                newExpr.setScriptGrammar(EventObjectUtil.CONSTANTPERIOD_SCRIPTGRAMMAR);
                newExpr.getMixed()
                        .add(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ConstantPeriod(),
                                constantPeriod);

                Deadline newDeadline = Xpdl2Factory.eINSTANCE.createDeadline();
                newDeadline.setDeadlineDuration(newExpr);

                cmd.append(AddCommand.create(editingDomain,
                        getActivity(),
                        Xpdl2Package.eINSTANCE.getActivity_Deadline(),
                        newDeadline));

            } else if (ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT
                    .equals(getScriptContext())) {

                RescheduleTimerScript resceduleTimerScript =
                        XpdExtensionFactory.eINSTANCE
                                .createRescheduleTimerScript();

                constantPeriod =
                        XpdExtensionFactory.eINSTANCE.createConstantPeriod();

                resceduleTimerScript
                        .setScriptGrammar(EventObjectUtil.CONSTANTPERIOD_SCRIPTGRAMMAR);
                resceduleTimerScript
                        .getMixed()
                        .add(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ConstantPeriod(),
                                constantPeriod);

                resceduleTimerScript
                        .setDurationRelativeTo(RescheduleDurationType.RESCHEDULE_TIME);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                (TriggerTimer) getActivity().getEvent()
                                        .getEventTriggerTypeNode(),
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_RescheduleTimerScript(),
                                resceduleTimerScript));
            }
        }

        return constantPeriod;
    }

    @Override
    protected String getScriptGrammar() {
        return EventObjectUtil.CONSTANTPERIOD_SCRIPTGRAMMAR;
    }

    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject eObject) {
        String scriptContext = getScriptContext();
        String scriptGrammar = getScriptGrammar();
        Command toReturn = null;

        if (ProcessScriptContextConstants.TIMER_EVENT.equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetTimerEventScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,
                                    false);

        } else if (ProcessScriptContextConstants.RESCHEDULE_TIMER_EVENT
                .equals(scriptContext)) {
            toReturn =
                    ProcessScriptUtil
                            .getSetRescheduleTimerEventScriptGrammarCommand(editingDomain,
                                    scriptGrammar,
                                    eObject,
                                    false,
                                    scriptContext);
        }

        return toReturn;
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "analyst.editor"; //$NON-NLS-1$
    }
}
