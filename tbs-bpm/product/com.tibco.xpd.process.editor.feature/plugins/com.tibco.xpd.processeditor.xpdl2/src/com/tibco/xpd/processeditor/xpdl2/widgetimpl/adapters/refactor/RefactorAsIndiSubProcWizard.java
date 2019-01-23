/**
 * RefactorAsIndiSubProcWizard.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.List;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.resource.ImageRegistry;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * RefactorAsIndiSubProcWizard
 * 
 */
public class RefactorAsIndiSubProcWizard extends
        AbstractRefactorActivitiesAsProcessWizard {

    private RefactorAsIndiSubprocWizardInfo refactorInfo;

    /**
     * @param refactorInfo
     * @param editingDomain
     * @param wizardTitle
     * @param wizardDescription
     */
    public RefactorAsIndiSubProcWizard(
            RefactorAsIndiSubprocWizardInfo refactorInfo,
            EditingDomain editingDomain) {

        super(
                refactorInfo,
                editingDomain,
                Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubproc_title2,
                Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubproc_longdesc2);

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

        this.refactorInfo = (RefactorAsIndiSubprocWizardInfo) inputObject;

        return;
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

        //
        // If there are any participants that are used SOLELY in the selected
        // objects then provide option to move OR duplicate in new sub-process.
        if (refactorInfo.referencedParticipants.size() > 0) {
            ProcessRefactorConfigurationItem participantGroup = null;

            for (RefactorReferencedParticipantInfo particInfo : refactorInfo.referencedParticipants) {

                if (!particInfo.referencedElseWhere) {
                    ReferencedParticipantConfigItem particItem =
                            new ReferencedParticipantConfigItem(particInfo, ir);

                    if (participantGroup == null) {
                        participantGroup =
                                new ProcessRefactorConfigurationItem(
                                        "MoveOrDuplicateParticipantGroup", //$NON-NLS-1$
                                        Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubprocMoveAllPartic_message,
                                        Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubprocMoveAllPartic_longdesc,
                                        true,
                                        false,
                                        ir
                                                .get(ProcessEditorConstants.IMG_PARTICIPANT));
                    }

                    participantGroup.addChildItem(particItem);
                }
            }

            if (participantGroup != null) {
                items.add(participantGroup);
            }
        }

        return items;
    }

    /**
     * ReferencedParticipantConfigItem
     * 
     * Small class to join between RefactorReferencedParticipantInfo and its
     * refactor configuration item.
     * 
     * Basically, creates the appropriate refactor config item and keeps the
     * 'Move / duplicate flag up to date.
     */
    private class ReferencedParticipantConfigItem extends
            ProcessRefactorConfigurationItem {

        public ReferencedParticipantConfigItem(
                RefactorReferencedParticipantInfo particInfo, ImageRegistry ir) {
            super(
                    particInfo,
                    String
                            .format(Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubProcMovePartic_message,
                                    particInfo.participant.getName()),
                    String
                            .format(Messages.RefactorAsIndiSubProcWizard_RefactorAsIndiSubProcMovePartic_longdesc,
                                    particInfo.participant.getName()), true,
                    false, ir.get(ProcessEditorConstants.IMG_PARTICIPANT));

        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
         * ProcessRefactorConfigurationItem#setChecked(boolean)
         */
        @Override
        public void setChecked(boolean isChecked) {
            // Use the 'move or duplicate flag in original partic info for check
            // status.
            RefactorReferencedParticipantInfo particInfo =
                    (RefactorReferencedParticipantInfo) getInputObject();
            particInfo.moveParticipant = isChecked;

        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor.
         * ProcessRefactorConfigurationItem#isChecked()
         */
        @Override
        public boolean isChecked() {
            // Use the 'move or duplicate flag in original partic info for check
            // status.
            RefactorReferencedParticipantInfo particInfo =
                    (RefactorReferencedParticipantInfo) getInputObject();
            return particInfo.moveParticipant;
        }

    }

}
