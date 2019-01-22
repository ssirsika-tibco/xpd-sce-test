/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.task;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.SashDividedTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * @author aallway
 * @since 3.2
 */
public class ParticipantSelectionSection extends
        AbstractFilteredTransactionalSection {

    private Text participantText;

    private Button clearParticipant;

    private Button browseParticipant;

    private Composite performerScopeComposite;

    private int performerScopeCompositeSize;

    private Button performerScopeProcess;

    private Button performerScopeLibrary;

    private Label particLabel;

    private Composite sectionRoot;

    private Composite relayoutControl;

    private static final String BROWSE_FOR_PARTICIPANT = "BrowseForParticipant"; //$NON-NLS-1$

    private static final String CLEAR_PARTICIPANT = "ClearParticipant"; //$NON-NLS-1$

    public ParticipantSelectionSection(Composite relayoutControl) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.relayoutControl = relayoutControl;

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

        sectionRoot = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout(4, false);
        gl.marginHeight = gl.marginWidth = 0;
        sectionRoot.setLayout(gl);

        particLabel =
                createLabel(sectionRoot,
                        toolkit,
                        Messages.TaskGeneralSection_PARTICIPANT_LABEL);
        gData = new GridData();
        gData.widthHint = SashDividedTransactionalSection.TEXT_WIDTH_HINT;
        particLabel.setLayoutData(gData);

        participantText = toolkit.createText(sectionRoot, ""); //$NON-NLS-1$
        participantText.setData("name", "textTaskParticipants");//$NON-NLS-1$//$NON-NLS-2$
        gData = new GridData(GridData.FILL_HORIZONTAL);
        participantText.setLayoutData(gData);
        participantText.setEditable(false);
        browseParticipant =
                toolkit.createButton(sectionRoot,
                        Messages.TaskGeneralSection_ELIPSES,
                        SWT.PUSH);
        browseParticipant.setData(BROWSE_FOR_PARTICIPANT);
        browseParticipant.setData("name", "buttonBrowseParticipant");//$NON-NLS-1$//$NON-NLS-2$
        manageControl(browseParticipant);

        clearParticipant =
                toolkit
                        .createButton(sectionRoot,
                                com.tibco.xpd.xpdl2.edit.ui.internal.Messages.ClearButton_label,
                                SWT.PUSH);
        clearParticipant.setData(CLEAR_PARTICIPANT);
        clearParticipant.setData("name", "buttonClearParticipant");//$NON-NLS-1$//$NON-NLS-2$
        manageControl(clearParticipant);

        Composite leftFill = toolkit.createComposite(sectionRoot);
        gData = new GridData();
        gData.heightHint = 0;
        leftFill.setLayoutData(gData);

        performerScopeComposite = toolkit.createComposite(sectionRoot);
        performerScopeComposite.setLayoutData(createPerformerScopeLayoutData());

        GridLayout perfScopeLayout = new GridLayout(2, false);
        perfScopeLayout.marginWidth = 0;
        perfScopeLayout.marginBottom = perfScopeLayout.marginHeight * 2;
        perfScopeLayout.marginHeight = 0;

        performerScopeComposite.setLayout(perfScopeLayout);

        performerScopeLibrary =
                toolkit.createButton(performerScopeComposite,
                        Messages.TaskGeneralSection_SetInLibrary_label,
                        SWT.RADIO);
        gData = new GridData();
        performerScopeLibrary.setLayoutData(gData);
        manageControl(performerScopeLibrary);

        performerScopeProcess =
                toolkit.createButton(performerScopeComposite,
                        Messages.TaskGeneralSection_SetInProcess_label,
                        SWT.RADIO);
        gData = new GridData();
        performerScopeProcess.setLayoutData(gData);
        manageControl(performerScopeProcess);

        performerScopeCompositeSize =
                performerScopeComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;

        return sectionRoot;
    }

    /**
     * @return
     */
    private GridData createPerformerScopeLayoutData() {
        GridData gData;
        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.horizontalSpan = 3;
        return gData;
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
        Command cmd = null;

        Activity activity = getActivity();
        Process process = getProcess();

        EditingDomain ed = getEditingDomain();

        if (activity != null && ed != null) {

            if (obj == browseParticipant) {
                cmd =
                        TaskObjectUtil
                                .selectOrClearActivityParticipantCommand(ed,
                                        process,
                                        activity,
                                        false);

            } else if (obj == clearParticipant) {
                cmd =
                        TaskObjectUtil
                                .selectOrClearActivityParticipantCommand(ed,
                                        process,
                                        activity,
                                        true);

            } else if (isTaskLibraryActivity() && obj == performerScopeLibrary) {
                CompoundCommand ccmd =
                        new CompoundCommand(
                                Messages.TaskGeneralSection_SetTaskParticScope_menu);
                ccmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SetPerformerInProcess(),
                        null));
                cmd = ccmd;

            } else if (isTaskLibraryActivity() && obj == performerScopeProcess) {
                CompoundCommand ccmd =
                        new CompoundCommand(
                                Messages.TaskGeneralSection_SetTaskParticScope_menu);
                ccmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SetPerformerInProcess(),
                        Boolean.TRUE));
                // Clear the task set performers.
                ccmd.append(TaskObjectUtil.getSetPerformersCommand(ed,
                        activity,
                        new EObject[0]));
                cmd = ccmd;
            }
        }

        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        Activity activity = getActivity();
        Process process = getProcess();

        if (activity != null && process != null) {

            boolean browseParticipantEnabled = true;
            boolean clearParticipantEnabled = true;
            boolean greyParticipantText = false;
            boolean inputIsLibraryTask = isTaskLibraryActivity();
            TaskType currTaskType = TaskObjectUtil.getTaskType(activity);
            boolean isPageflow = Xpdl2ModelUtil.isPageflow(getProcess());

            String particTextVal = ""; //$NON-NLS-1$

            // 
            // Update the performer scope controls.
            EObject[] referencedTaskPerformers = null;

            EObject[] performers =
                    TaskObjectUtil.getActivityPerformers(activity, process);

            Activity referencedLibraryTask = null;
            boolean configAsSetInProcess = false;

            if (inputIsLibraryTask
                    || (TaskType.REFERENCE_LITERAL.equals(currTaskType) && ReferenceTaskUtil
                            .isTaskLibraryTaskReference(activity))) {
                // Show the performer scope controls for task library tasks or
                // process task referencing task library task.
                if (!performerScopeComposite.isVisible()) {
                    GridData gData = createPerformerScopeLayoutData();
                    gData.heightHint = performerScopeCompositeSize;
                    performerScopeComposite.setLayoutData(gData);
                    performerScopeComposite.setVisible(true);
                    relayoutControl.layout(true);
                }

                if (!inputIsLibraryTask) {
                    // Can only set the performer scope from the library task.
                    performerScopeProcess.setEnabled(false);
                    performerScopeLibrary.setEnabled(false);
                } else {
                    performerScopeProcess.setEnabled(true);
                    performerScopeLibrary.setEnabled(true);
                }

                Activity libraryTask;

                if (inputIsLibraryTask) {
                    // We're actually editing the library task.
                    libraryTask = activity;
                } else {
                    // we're editing Reference to library task.
                    libraryTask = ReferenceTaskUtil.getReferencedTask(activity);
                    referencedLibraryTask = libraryTask;
                }

                if (libraryTask != null
                        && Boolean.TRUE
                                .equals(Xpdl2ModelUtil
                                        .getOtherAttribute(libraryTask,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_SetPerformerInProcess()))) {
                    // 
                    // Library task specifies "Set Participant In Process"
                    performerScopeProcess.setSelection(true);
                    performerScopeLibrary.setSelection(false);

                    configAsSetInProcess = true;

                    if (inputIsLibraryTask) {
                        // When doing prop's for task library and is config'd as
                        // "set in Process" then disable the browse / clear.
                        browseParticipantEnabled = false;

                        if (performers == null || performers.length == 0) {
                            clearParticipantEnabled = false;
                        }
                    }

                } else {
                    // 
                    // Library task specifies "Set Participant In Library" or
                    // lirbary task not set in referencing task.
                    performerScopeProcess.setSelection(false);
                    performerScopeLibrary.setSelection(true);

                    if (!inputIsLibraryTask) {
                        // Grey the text when the referenced library task is
                        // setting the performers (and the user hasn't got some
                        // already set in the local task)
                        if (performers == null || performers.length == 0) {

                            greyParticipantText = true;

                            // Disable clear when it is not necessary (it is
                            // only necessary if the user is in a situation
                            // where the local task has some performers set when
                            // the library task is mean to set them.
                            clearParticipantEnabled = false;
                        }

                        // Don't allow browse when task library task sets the
                        // performers.
                        browseParticipantEnabled = false;
                    }
                }

                if (libraryTask != null) {
                    referencedTaskPerformers =
                            TaskObjectUtil.getActivityPerformers(libraryTask,
                                    process);
                }

            } else {
                // Hide the performer scope controls for non library task
                // reference tasks.
                if (performerScopeComposite.isVisible()) {
                    GridData gData = createPerformerScopeLayoutData();
                    gData.heightHint = 0;
                    performerScopeComposite.setLayoutData(gData);
                    performerScopeComposite.setVisible(false);
                    relayoutControl.layout(true);
                }
            }

            if (performers == null || performers.length == 0) {
                // If we haven't got performers of our own but we reference a
                // task with performers then show those.
                performers = referencedTaskPerformers;
            }

            if (performers == null || performers.length == 0) {
                if (!inputIsLibraryTask && referencedLibraryTask != null
                        && !configAsSetInProcess) {
                    // "- not set yet in referenced task -"
                    particTextVal =
                            Messages.TaskGeneralSection_NotSetInLibraryTask_label;

                } else if (inputIsLibraryTask && configAsSetInProcess) {
                    // "- to be set in referencing tasks - "
                    particTextVal =
                            Messages.TaskGeneralSection_ToBeSetInRefTasks_label;
                } else {
                    // "- not set -"
                    particTextVal = Messages.TaskGeneralSection_NOT_SET;
                }

                greyParticipantText = true;

            } else {
                for (int i = 0; i < performers.length; i++) {
                    if (i > 0) {
                        particTextVal += "; "; //$NON-NLS-1$
                    }
                    particTextVal +=
                            String
                                    .valueOf(TaskObjectUtil
                                            .getPerformerDescriptionStatic(performers[i]));
                }
            }

            if (isPageflow && !TaskType.SERVICE_LITERAL.equals(currTaskType)
                    && !TaskType.SEND_LITERAL.equals(currTaskType)) {
                //
                // No participant selection at all for pageflow process tasks.
                participantText.setEnabled(false);

                browseParticipantEnabled = false;
                greyParticipantText = true;

                /*
                 * Only disable clear for user tasks if performers list is empty
                 * (sop that user can clear if we complain!
                 */
                if (performers == null || performers.length == 0) {
                    clearParticipantEnabled = false;
                    particTextVal =
                            Messages.TaskGeneralSection_ParticipantNotApplicable;
                }
            }

            browseParticipant.setEnabled(browseParticipantEnabled);
            clearParticipant.setEnabled(clearParticipantEnabled);

            if (greyParticipantText) {
                participantText.setForeground(ColorConstants.lightGray);
            } else {
                participantText.setForeground(ColorConstants.black);
            }

            participantText.setText(particTextVal);
            participantText.setToolTipText(particTextVal);
        }

        return;
    }

    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    private Process getProcess() {
        if (isWizard()) {
            return Xpdl2ModelUtil.getProcess(getXpdSectionContainerProvider()
                    .getInputContainer());
        } else if (getActivity() != null) {
            return getActivity().getProcess();
        }
        return null;
    }

    public boolean isWizard() {
        return getSectionContainerType() == ContainerType.WIZARD;
    }

    private boolean isTaskLibraryActivity() {
        // May be in a wizard so check whether the activity container is in a
        // task library instead.
        if (getInputContainer() != null) {
            if (XpdlFileContentPropertyTester
                    .isTasksFileContent(getInputContainer())) {
                return true;
            }
        }
        return false;
    }

    public Label getParticLabel() {
        return particLabel;
    }

}
