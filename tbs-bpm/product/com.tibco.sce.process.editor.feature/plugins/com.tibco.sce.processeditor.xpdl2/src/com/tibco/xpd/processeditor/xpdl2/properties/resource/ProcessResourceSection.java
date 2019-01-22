/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.util.PriorityValue;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.DigitTextVerifyListener;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Priority;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessHeader;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * ProcessResourceSection
 * 
 * 
 * @author bharge
 * @since 3.3 (21 Jan 2010)
 */
public class ProcessResourceSection extends
        AbstractFilteredTransactionalSection {

    /*
     * XPD-5475 - Switch to radio buttons instead of combo box for process
     * priority so that we can sensibly provide a user defined option as well as
     * indicating the 5 main options.
     */

    private PriorityValue[] priorityValues = PriorityValue
            .getProcessPriorityValues();

    private Button priorityButtons[];

    private static String CUSTOM_PRIORITY_BTN_DATA = "CUSTOM_PRIORITY_DATA"; //$NON-NLS-1$

    private Text customPriorityText;

    private Button customPriorityButton;

    /**
     * 
     */
    public ProcessResourceSection() {
        super(Xpdl2Package.eINSTANCE.getProcess());
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        /*
         * We need to ensure that once user has explicitly selected custom and
         * is same value as one of the default value, that we don't reselect
         * default on next refresh because value in text box not changed yet.
         * i.e. we don't want to deselect custom when user types one of the
         * preset value (at least not until next set input.
         * 
         * So all we need to do is ensure that we deselect customPriorityButton
         * before we start, then first refresh after doing setInput will set to
         * "custom" if a preset values is not the current priority - then
         * subsequently it will say "if custom button is already selected" then
         * select it and put value in text box in preference to preset value.
         */

        if (customPriorityButton != null) {
            if (customPriorityButton.getSelection()) {
                customPriorityButton.setSelection(false);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridData gData;

        Composite composite = toolkit.createComposite(parent);

        composite.setLayout(new GridLayout(2, false));

        Label initialPriorityLable =
                toolkit.createLabel(composite,
                        Messages.TaskGeneralSection_INITIALPRIORITY_LABEL,
                        SWT.WRAP);

        gData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        initialPriorityLable.setLayoutData(gData);

        /*
         * Add set priority value buttons.
         */
        Composite btnContainer = toolkit.createComposite(composite);
        btnContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        int btnsPerRow = priorityValues.length;

        GridLayout gl = new GridLayout(btnsPerRow, true);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        btnContainer.setLayout(gl);
        priorityButtons = new Button[priorityValues.length];

        int btnIdx = 0;
        for (PriorityValue priorityValue : priorityValues) {
            Button btn =
                    toolkit.createButton(btnContainer,
                            priorityValue.getLabel(),
                            SWT.RADIO);
            btn.setData(priorityValue);
            btn.setLayoutData(new GridData());
            manageControl(btn);

            priorityButtons[btnIdx++] = btn;
        }

        /*
         * Add custom button and text
         */
        customPriorityButton =
                toolkit.createButton(btnContainer,
                        Messages.ProcessResourceSection_CustomPriority_button_label,
                        SWT.RADIO);
        customPriorityButton.setData(CUSTOM_PRIORITY_BTN_DATA);
        customPriorityButton.setLayoutData(new GridData());
        manageControl(customPriorityButton);

        customPriorityText = toolkit.createText(btnContainer, ""); //$NON-NLS-1$
        GridData gd = new GridData();
        gd.widthHint = 50;
        customPriorityText.setLayoutData(gd);
        customPriorityText.setEnabled(false);

        customPriorityText.addVerifyListener(new DigitTextVerifyListener());
        manageControl(customPriorityText);

        return composite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Object input = getInput();

        if (input instanceof Process) {
            Process process = (Process) input;

            if (obj instanceof Button) {
                Button btn = (Button) obj;

                Object value = btn.getData();

                if (CUSTOM_PRIORITY_BTN_DATA.equals(value)) {
                    /*
                     * The value does not change immediately, so just force a
                     * refresh which will say "if custom radio is selected then
                     * setup text box".
                     */
                    refresh();

                    return null;

                } else if (value instanceof PriorityValue) {
                    PriorityValue priorityValue = (PriorityValue) value;

                    return getSetPriorityCommand(getEditingDomain(),
                            process,
                            priorityValue.getValue());
                }

            } else if (obj == customPriorityText) {
                /* Set from custom priority text. */
                String textPriority = customPriorityText.getText();

                if (textPriority == null) {
                    textPriority = ""; //$NON-NLS-1$
                }

                /* Normalise out any thing that might not be numeric. */
                try {
                    Integer intPriority = Integer.parseInt(textPriority);
                    textPriority = intPriority.toString();

                } catch (NumberFormatException e) {
                    textPriority = ""; //$NON-NLS-1$

                }

                return getSetPriorityCommand(getEditingDomain(),
                        process,
                        textPriority);
            }

        }

        return null;
    }

    /**
     * @param ed
     * @param process
     * @param priorityValue
     * 
     * @return Command to set / unset process priority or <code>null</code> if
     *         not required.
     */
    private Command getSetPriorityCommand(EditingDomain ed, Process process,
            String priorityValue) {
        String currentPriority = getPriority(process);

        if (priorityValue == null) {
            if (currentPriority == null || currentPriority.length() == 0) {
                return null;
            }
        } else if (priorityValue.equals(currentPriority)) {
            return null;
        }

        ProcessHeader processHeader = process.getProcessHeader();
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.ProcessResourceSection_SetProcessPriority_menu);

        if (priorityValue != null && priorityValue.length() > 0) {
            /*
             * Set the priority
             */
            if (processHeader == null) {
                processHeader = Xpdl2Factory.eINSTANCE.createProcessHeader();

                cmd.append(SetCommand.create(ed,
                        process,
                        Xpdl2Package.eINSTANCE.getProcess_ProcessHeader(),
                        processHeader));
            }

            Priority priority = Xpdl2Factory.eINSTANCE.createPriority();
            priority.setValue(priorityValue);

            cmd.append(SetCommand.create(ed,
                    processHeader,
                    Xpdl2Package.eINSTANCE.getProcessHeader_Priority(),
                    priority));

        } else {
            /*
             * Default = no priority element set (back to inital condition of
             * process
             */
            if (processHeader != null && processHeader.getPriority() != null) {
                cmd.append(SetCommand.create(ed,
                        processHeader,
                        Xpdl2Package.eINSTANCE.getProcessHeader_Priority(),
                        SetCommand.UNSET_VALUE));
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * 
     * @param process
     * @return Currently set priority or <code>""</code> if none set.
     */
    private String getPriority(Process process) {
        String priorityString = null;

        ProcessHeader processHeader = process.getProcessHeader();
        if (processHeader != null) {
            Priority priority = processHeader.getPriority();

            if (priority != null) {
                priorityString = priority.getValue();
            }
        }
        return priorityString != null ? priorityString : ""; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        Object input = getInput();
        if (input instanceof Process) {
            Process process = (Process) input;

            String modelPriorityValue = getPriority(process);

            if (modelPriorityValue == null) {
                modelPriorityValue = ""; //$NON-NLS-1$
            }

            /*
             * We need to ensure that once user has explicitly selected custom
             * and is same value as one of the default value, that we don't
             * reselect default on next refresh because value in text box not
             * changed yet. i.e. we don't want to deselect custom when user
             * types one of the preset value (at least not until next set input.
             * 
             * So all we need to do is ensure that we deselect
             * customPriorityButton before we start, then first refresh after
             * doing setInput will set to "custom" if a preset values is not the
             * current priority - then subsequently it will say
             * "if custom button is already selected" then select it and put
             * value in text box in preference to preset value.
             */
            if (customPriorityButton.getSelection()) {
                if (!customPriorityText.getEnabled()) {
                    customPriorityText.setEnabled(true);
                }
                updateText(customPriorityText, modelPriorityValue);

            } else {

                boolean presetSelected = false;

                for (Button btn : priorityButtons) {
                    PriorityValue priorityValue = (PriorityValue) btn.getData();

                    if (priorityValue.getValue().equals(modelPriorityValue)) {
                        presetSelected = true;
                        if (!btn.getSelection()) {
                            btn.setSelection(true);
                            btn.update();
                        }
                    } else {
                        if (btn.getSelection()) {
                            btn.setSelection(false);
                            btn.update();
                        }
                    }
                }

                if (!presetSelected) {
                    /*
                     * If not selected a preset value then select and emable
                     * custom.
                     */
                    customPriorityButton.setSelection(true);

                    if (!customPriorityText.getEnabled()) {
                        customPriorityText.setEnabled(true);
                    }

                    updateText(customPriorityText, modelPriorityValue);

                } else {
                    /*
                     * If we're not in custom edit mode then disable and empty
                     * the custom text
                     */
                    if (customPriorityText.getEnabled()) {
                        customPriorityText.setEnabled(false);
                    }

                    updateText(customPriorityText, ""); //$NON-NLS-1$
                }

            }

        }

    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean ret = super.shouldRefresh(notifications);

        return ret;
    }
}
