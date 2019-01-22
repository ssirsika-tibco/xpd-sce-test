/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Refactor activities as pageflow process.
 * 
 * @author aallway
 * @since 3.4.2 (15 Sep 2010)
 */
public class RefactorAsPageflowProcessWizard extends
        AbstractRefactorActivitiesAsProcessWizard {

    private static final String SELECT_PARTICIPANT_GROUP =
            "SelectParticipantGroup"; //$NON-NLS-1$

    private RefactorAsPageflowWizardInfo refactorInfo;

    public RefactorAsPageflowProcessWizard(
            RefactorAsPageflowWizardInfo refactorInfo,
            EditingDomain editingDomain) {
        super(
                refactorInfo,
                editingDomain,
                Messages.RefactorAsPageflowProcessWizard_RefactorAsPageflow_title,
                Messages.RefactorAsPageflowProcessWizard_RefactorAsPageflow_longdesc);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessWizard#init(java.lang.Object)
     */
    @Override
    public void init(Object inputObject) {
        super.init(inputObject);

        this.refactorInfo = (RefactorAsPageflowWizardInfo) inputObject;

        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * BaseRefactorAsSubProcWizard#getPostConfigPages(java.lang.Object)
     */
    @Override
    protected List<WizardPage> getPostConfigPages(Object inputObject) {

        List<WizardPage> pages = new ArrayList<WizardPage>(1);

        pages.add(new RefactorAsPageflowMainPage());

        return pages;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessWizard
     * #getConfigurationItems(java.lang.Object)
     */
    @Override
    protected List<ProcessRefactorConfigurationItem> getConfigurationItems(
            Object inputObject) {
        List<ProcessRefactorConfigurationItem> items =
                super.getConfigurationItems(inputObject);

        ImageRegistry ir =
                Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry();

        /*
         * If more than one participant is referenced from user/manual tasks
         * then user has to choose which one should invoke the pageflow process.
         * objects then provide option to move OR duplicate in new sub-process.
         */
        refactorInfo.pageflowTaskPerformer = null;

        Set<NamedElement> referencedPerformers = new HashSet<NamedElement>();
        for (EObject object : refactorInfo.selectedObjects) {
            if (object instanceof Activity) {
                Activity activity = (Activity) object;

                EObject[] activityPerformers =
                        TaskObjectUtil.getActivityPerformers(activity);
                for (EObject performer : activityPerformers) {
                    if (performer instanceof NamedElement) {
                        referencedPerformers.add((NamedElement) performer);
                    }
                }

                /*
                 * ABPM-911: Saket: An event subprocess should mostly behave
                 * like an embedded sub-process.
                 */
                if (TaskType.EMBEDDED_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(activity))
                        || TaskType.EVENT_SUBPROCESS_LITERAL
                                .equals(TaskObjectUtil
                                        .getTaskTypeStrict(activity))) {
                    Collection<Activity> allSubActs =
                            Xpdl2ModelUtil
                                    .getAllActivitiesInEmbeddedSubProc(activity);
                    for (Activity subAct : allSubActs) {
                        EObject[] subPerformers =
                                TaskObjectUtil.getActivityPerformers(subAct);
                        for (EObject performer : subPerformers) {
                            if (performer instanceof NamedElement) {
                                referencedPerformers
                                        .add((NamedElement) performer);
                            }
                        }
                    }
                }
            }
        }

        if (referencedPerformers.size() > 1) {
            SingleChildSelectionRefactorConfigItem participantGroup = null;

            for (NamedElement performer : referencedPerformers) {
                if (participantGroup == null) {
                    participantGroup =
                            new SingleChildSelectionRefactorConfigItem(
                                    SELECT_PARTICIPANT_GROUP,
                                    Messages.RefactorAsPageflowProcessWizard_SelectTaskPerformerGroup_button,
                                    Messages.RefactorAsPageflowProcessWizard_SelectTaskPerformer_longdesc,
                                    true,
                                    ir.get(ProcessEditorConstants.IMG_PARTICIPANT));
                }

                SelectParticipantConfigItem particItem =
                        new SelectParticipantConfigItem(participantGroup,
                                performer, ir);

                participantGroup.addChildItem(particItem);
            }

            if (participantGroup != null) {
                items.add(participantGroup);
            }

        } else if (referencedPerformers.size() == 1) {
            /*
             * If only one participant is selected then save it as the
             * participant to place on the pageflow invoking task.
             */
            refactorInfo.pageflowTaskPerformer =
                    referencedPerformers.iterator().next();
        }

        return items;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
     * AbstractRefactorActivitiesAsProcessWizard
     * #isConfigurationComplete(java.lang.Object, java.util.List)
     */
    @Override
    protected boolean isConfigurationComplete(Object inputObject,
            List<ProcessRefactorConfigurationItem> configItems) {

        if (super.isConfigurationComplete(inputObject, configItems)) {
            int selectedCount = 0;

            boolean hasParticGroupItems = false;
            for (ProcessRefactorConfigurationItem configItem : configItems) {
                if (SELECT_PARTICIPANT_GROUP
                        .equals(configItem.getInputObject())) {
                    hasParticGroupItems = true;
                    for (ProcessRefactorConfigurationItem particItem : configItem
                            .getChildItems()) {
                        if (particItem.isChecked()) {
                            selectedCount++;
                        }
                    }
                }
            }

            /*
             * SID XPD-1862: configuration is complete if there were no
             * select-one-of-many-participant items for the user to tick OR the
             * user has selected exactly one participant
             */
            if (!hasParticGroupItems || selectedCount == 1) {
                return true;
            }

        }
        return false;
    }

    /**
     * MainPage - main page for refactor as independent sub-process.
     * 
     */
    private class RefactorAsPageflowMainPage extends AbstractXpdWizardPage
            implements ModifyListener, SelectionListener {
        private Composite rootControl;

        private Text nameText;

        public RefactorAsPageflowMainPage() {
            super(""); //$NON-NLS-1$

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt
         * .widgets.Composite)
         */
        @Override
        public void createControl(Composite parent) {
            setTitle(Messages.RefactorAsPageflowProcessWizard_RefactorAsPageflow_title);
            setDescription(Messages.RefactorAsPageflowProcessWizard_RefactorAsPageflow_longdesc);

            rootControl = new Composite(parent, SWT.NONE);

            setControl(rootControl);

            GridLayout gridLayout = new GridLayout(2, false);
            gridLayout.verticalSpacing = 10;
            rootControl.setLayout(gridLayout);

            //
            // Independent sub-process name.
            Label label = new Label(rootControl, SWT.NONE);
            label.setText(Messages.BaseRefactorAsSubProcWizard_NameRefactorAsSubproc_label);
            label.setLayoutData(new GridData());

            nameText = new Text(rootControl, SWT.SINGLE | SWT.BORDER);
            nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            nameText.setText(refactorInfo.subprocName);
            nameText.selectAll();

            nameText.addModifyListener(this);

            Composite blanker = new Composite(rootControl, SWT.NONE);
            blanker.setLayoutData(new GridData());

            //
            // Disable finish until first made visible
            setPageComplete(false);

            return;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
         */
        @Override
        public void setVisible(boolean visible) {
            // Only allow finish when page is first made visible.
            if (visible) {
                setPageComplete(true);
            }

            super.setVisible(visible);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.
         * events.ModifyEvent)
         */
        @Override
        public void modifyText(ModifyEvent e) {
            if (e.widget == nameText) {
                refactorInfo.subprocName = nameText.getText();

                if (refactorInfo.subprocName.length() > 0) {
                    setPageComplete(true);
                } else {
                    setPageComplete(false);
                }
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org
         * .eclipse.swt.events.SelectionEvent)
         */
        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
         * .swt.events.SelectionEvent)
         */
        @Override
        public void widgetSelected(SelectionEvent e) {
        }

    }

    /**
     * When there are multiple participants referenced in the selection then the
     * user must select the one that they want all the user tasks to be
     * performed as.
     * <p>
     * This is because a pageflow is a single work-item performed by a single
     * user - therefore all performers are removed from refactored user/manual
     * tasks and either (a) the <i>one</i> that is referenced or (b) the one
     * that is selected by user from multiple participants referenced by tasks
     * is set on the pageflwo invocation task created in the source process.
     * 
     * 
     * @author aallway
     * @since 3.4.2 (20 Sep 2010)
     */
    private class SelectParticipantConfigItem extends
            ProcessRefactorConfigurationItem {

        ProcessRefactorConfigurationItem parentGroup;

        public SelectParticipantConfigItem(
                ProcessRefactorConfigurationItem parentGroup,
                NamedElement performer, ImageRegistry ir) {
            super(
                    performer,
                    String.format(Messages.RefactorAsPageflowProcessWizard_SetTaskPerformer_button,
                            Xpdl2ModelUtil.getDisplayNameOrName(performer)),
                    String.format(Messages.RefactorAsPageflowProcessWizard_SetTaskPerformer_longdesc),
                    false, false, ir
                            .get(ProcessEditorConstants.IMG_PARTICIPANT));

            this.parentGroup = parentGroup;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
         * ProcessRefactorConfigurationItem#setChecked(boolean)
         */
        @Override
        public void setChecked(boolean isChecked) {
            NamedElement performer = (NamedElement) getInputObject();

            if (isChecked) {
                refactorInfo.pageflowTaskPerformer = performer;
            } else {
                if (super.isChecked()) {
                    if (refactorInfo.pageflowTaskPerformer == performer) {
                        refactorInfo.pageflowTaskPerformer = null;
                    }
                }
            }

            super.setChecked(isChecked);

            return;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
         * ProcessRefactorConfigurationItem#isChecked()
         */
        @Override
        public boolean isChecked() {
            return super.isChecked();
        }

    }

}
