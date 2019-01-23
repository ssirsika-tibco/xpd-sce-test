/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamDeleter;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamResolver;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ParticipantReferenceResolver;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ProcessReferenceResolver;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2TypeDeclarationReferenceResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Project Explorer's Delete action
 * 
 * @author njpatel
 * 
 */
public class DeleteAction extends BaseSelectionListenerAction {

    public static final String IDE_WORKBENCH = "org.eclipse.ui.ide"; //$NON-NLS-1$

    public static final String PREFIX = IDE_WORKBENCH + "."; //$NON-NLS-1$

    // public static final String DELETE_PROJECT_DIALOG = PREFIX
    // + "delete_project_dialog_context"; //$NON-NLS-1$

    public static final String DELETE_RESOURCE_ACTION = PREFIX
            + "delete_resource_action_context"; //$NON-NLS-1$

    /**
     * The id of this action.
     */
    public static final String ID = Xpdl2ResourcesPlugin.PLUGIN_ID
            + ".DeleteResourceAction";//$NON-NLS-1$

    private CompoundCommand deleteCommand = null;

    private String cmdLabel = null;

    EditingDomain editingDomain = null;

    private List selectionList = Collections.EMPTY_LIST;

    private Shell shell;

    private static int DECISION_NA = -1;

    private static int DECISION_NO = 0;

    private static int DECISION_YES = 1;

    /**
     * Delete Action
     * 
     * @param shell
     */
    public DeleteAction(Shell shell) {
        super(Messages.DeleteAction_Menu_title);
        setToolTipText(Messages.DeleteAction_1);
        setId(ID);
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(this, DELETE_RESOURCE_ACTION);
        this.shell = shell;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
     * .eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        boolean bRet = super.updateSelection(selection);

        selectionList = Collections.EMPTY_LIST;

        if (bRet) {
            bRet = false;
            if (selection != null && selection.size() > 0) {
                List selList = selection.toList();

                // validate the selected objects.
                bRet = validateSelection(selList);
                // Make sure the selection is of the same object types

                if (bRet) {
                    // Get delete command
                    createDeleteCommand(selection.toList());

                    // Check the command
                    if (deleteCommand == null) {
                        bRet = false;
                    } else if (!deleteCommand.canExecute()) {
                        bRet = false;
                    } else {
                        // keep track of whether selection contains data
                        // fields / params selected.
                        selectionList = selList;
                    }

                    // If not successfull then clear command
                    if (!bRet) {
                        deleteCommand = null;
                    }
                }
            }
        }

        return bRet;
    }

    /**
     * @param selectionList
     * @return True if the list of selection objects is valid for copy to
     *         clipboard.
     */
    protected boolean validateSelection(List selectionList) {
        // Make sure the selection is of the same object types
        boolean bRet =
                ActionUtil.allEObjects(selectionList)
                        && ActionUtil.allObjectsOfSameType(selectionList);

        if (bRet) {
            // Make sure a logical node hasn't been selected
            bRet = !ActionUtil.isLogicalNodeInList(selectionList);

            if (bRet) {

                // Make sure the list doesn't contain a package
                bRet =
                        !ActionUtil.isClassTypeInList(selectionList,
                                Package.class);

                if (bRet) {
                    // Make sure all objects belong to the same parent
                    bRet = ActionUtil.eObjectsWithSameParent(selectionList);
                }
            }
        }
        return bRet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        if (deleteCommand == null || editingDomain == null) {
            return;
        }

        // Check and handle a data fields / params selection.
        int decision = checkAndHandleFieldDelete();

        if (decision == DECISION_NA) {
            // Check and handle a participants selection
            decision = checkAndHandleParticipantDelete();

            if (decision == DECISION_NA) {
                // Check and handle a type declaration selection
                decision = checkAndHandleTypeDeclarationDelete();

                if (decision == DECISION_NA) {

                    decision = checkAndHandleProcessDelete();

                    if (decision == DECISION_NA) {
                        // For things we check reference to we only put up a
                        // confirmation message box if anything ion selection
                        // actually IS referenced.
                        //
                        // For things we don't check references for then always
                        // ask
                        // for confirmation.
                        if (MessageDialog.openQuestion(shell,
                                Messages.DeleteAction_2,
                                Messages.DeleteAction_3)) {
                            decision = DECISION_YES;
                        } else {
                            decision = DECISION_NO;
                        }
                    }
                }

            }
        }

        if (decision == DECISION_YES) {
            // Add supplementary commands to the delete Command (only added when
            // user actually executes as this could be quite expensive.
            CompoundCommand finalCmd = new CompoundCommand();
            finalCmd.append(deleteCommand);

            Command supCmd = addSupplementCommands();
            if (supCmd != null) {
                finalCmd.append(supCmd);
            }

            if (finalCmd.canExecute()) {
                if (cmdLabel != null) {
                    finalCmd.setLabel(cmdLabel);
                }
                editingDomain.getCommandStack().execute(finalCmd);
            }
        }
    }

    /**
     * If there are fields in selection, check for references and display
     * appropriate message
     * 
     * @return DECISION_NA - no fields in selection, DECISION_NO user selected
     *         not to, DECISION_YES user said go ahead.
     */
    private int checkAndHandleFieldDelete() {
        boolean haveFields = false;

        boolean referencedFields = false;

        // If attempting to delete fields / params then change the message.
        for (Iterator iter = selectionList.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof ProcessRelevantData) {
                ProcessRelevantData field = (ProcessRelevantData) obj;

                haveFields = true;

                List<EObject> refs =
                        Xpdl2FieldOrParamResolver.getReferencingObjects(field);
                if (refs != null && refs.size() > 0) {
                    referencedFields = true;
                    break;
                }
            }
        }

        if (haveFields) {
            if (referencedFields) {
                // Fields are referenced, ask user to confirm
                if (MessageDialog.openQuestion(shell,
                        Messages.DeleteAction_RefDataDelete_title,
                        Messages.DeleteAction_ConfirmReferencedFields_MESSAGE)) {
                    return DECISION_YES;
                } else {
                    return DECISION_NO;
                }
            } else {
                // Have fields selected but none are referenced elsewhere so ok
                // to delete.
                return DECISION_YES;
            }
        }

        return DECISION_NA; // No fields selected so check other things.
    }

    /**
     * If there are participants in selection, check for references and display
     * appropriate message
     * 
     * @return DECISION_NA - no participants in selection, DECISION_NO user
     *         selected not to, DECISION_YES user said go ahead.
     */
    private int checkAndHandleParticipantDelete() {
        boolean havePartics = false;

        boolean referencedPartics = false;

        // If attempting to delete fields / params then change the message.
        for (Iterator iter = selectionList.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof Participant) {
                Participant participant = (Participant) obj;

                havePartics = true;

                // Fetch all the participant references according to scope
                // of field.
                Set<EObject> refs =
                        Xpdl2ParticipantReferenceResolver
                                .getReferencingObjects(participant);

                if (refs != null && refs.size() > 0) {
                    referencedPartics = true;

                    /*
                     * If this participant is referenced by auto-generating
                     * process API activities AND it is a default api activity
                     * endpoint participant for a process then refuse the
                     * delete.
                     */
                    if (containsGeneratedApiRequestActivities(refs)) {
                        Process process =
                                getProcessForApiEndPointParticipant(participant);
                        if (process != null) {
                            String message =
                                    String.format(Messages.DeleteAction_CantDeleteApiParticipant_message,
                                            Xpdl2ModelUtil
                                                    .getDisplayNameOrName(participant),
                                            Xpdl2ModelUtil
                                                    .getDisplayNameOrName(process));
                            MessageDialog.openError(shell,
                                    Messages.DeleteAction_DelRefPartic_title,
                                    message);
                            return DECISION_NO;
                        }
                    }

                    break;
                }
            }
        }

        if (havePartics) {
            if (referencedPartics) {
                // Fields are referenced, ask user to confirm
                if (MessageDialog.openQuestion(shell,
                        Messages.DeleteAction_DelRefPartic_title,
                        Messages.DeleteAction_DelRefPartic_longdesc)) {
                    return DECISION_YES;
                } else {
                    return DECISION_NO;
                }
            } else {
                // Have fields selected but none are referenced elsewhere so ok
                // to delete.
                return DECISION_YES;
            }
        }

        return DECISION_NA; // No fields selected so check other things.
    }

    /**
     * @param refs
     * 
     * @return true if the list contains api activities.
     */
    private boolean containsGeneratedApiRequestActivities(Set<EObject> refs) {
        for (EObject ref : refs) {
            if (ref instanceof Activity) {
                Activity activity = (Activity) ref;

                if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param participant
     * @return If the participant is referenced as the default api activity
     *         endpoint participant for a process then the process is returned
     *         else <code>null</code>
     */
    private Process getProcessForApiEndPointParticipant(Participant participant) {
        Package pkg = Xpdl2ModelUtil.getPackage(participant);
        if (pkg != null) {
            for (Process process : pkg.getProcesses()) {
                String apiParticId =
                        (String) Xpdl2ModelUtil
                                .getOtherAttribute(process,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ApiEndPointParticipant());
                if (participant.getId().equals(apiParticId)) {
                    return process;
                }
            }
        }
        return null;
    }

    /**
     * If there are processes in selection, check for references and display
     * appropriate message
     * 
     * @return DECISION_NA - no processes in selection, DECISION_NO user
     *         selected not to, DECISION_YES user said go ahead.
     */
    private int checkAndHandleProcessDelete() {
        boolean haveProcesses = false;

        boolean referencedProcesses = false;

        // If attempting to delete fields / params then change the message.
        for (Iterator iter = selectionList.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof Process) {
                Process process = (Process) obj;

                haveProcesses = true;

                // Fetch all the participant references according to scope
                // of field.
                Set<EObject> refs =
                        Xpdl2ProcessReferenceResolver
                                .getReferencingObjects(process, true);

                if (refs != null && refs.size() > 0) {
                    referencedProcesses = true;
                    break;
                }
            }
        }

        if (haveProcesses) {
            if (referencedProcesses) {
                // Fields are referenced, ask user to confirm
                if (MessageDialog.openQuestion(shell,
                        Messages.DeleteAction_DelRefProcess_title,
                        Messages.DeleteAction_DelRefProcess_longdesc2)) {
                    return DECISION_YES;
                } else {
                    return DECISION_NO;
                }
            } else {
                // Have processes selected but none are referenced IN THE SAME
                // PACKAGE.
                // Warn user it may be usde in other packages.
                if (MessageDialog.openQuestion(shell,
                        Messages.DeleteAction_DelProcess_title,
                        Messages.DeleteAction_DelProcess_longdesc)) {
                    return DECISION_YES;
                } else {
                    return DECISION_NO;
                }
            }
        }

        return DECISION_NA; // No fields selected so check other things.
    }

    /**
     * If there are TypeDeclaration in selection, check for references and
     * display appropriate message
     * 
     * @return DECISION_NA - no TypeDeclaration in selection, DECISION_NO user
     *         selected not to, DECISION_YES user said go ahead.
     */
    private int checkAndHandleTypeDeclarationDelete() {
        boolean haveTypeDeclarations = false;

        boolean referencedTypeDeclarations = false;

        // If attempting to delete fields / params then change the message.
        for (Iterator iter = selectionList.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            if (obj instanceof TypeDeclaration) {
                TypeDeclaration typeDeclaration = (TypeDeclaration) obj;

                haveTypeDeclarations = true;

                // Fetch all the TypeDeclaration references according
                Set<EObject> refs =
                        Xpdl2TypeDeclarationReferenceResolver
                                .getReferencingObjects(typeDeclaration);

                if (refs != null && refs.size() > 0) {
                    referencedTypeDeclarations = true;
                    break;
                }
            }
        }

        if (haveTypeDeclarations) {
            if (referencedTypeDeclarations) {
                // Fields are referenced, ask user to confirm
                if (MessageDialog.openQuestion(shell,
                        Messages.DeleteAction_DelRefTypeDecl_title,
                        Messages.DeleteAction_DelRefTypeDecl_longdesc)) {
                    return DECISION_YES;
                } else {
                    return DECISION_NO;
                }
            } else {
                // Have fields selected but none are referenced elsewhere so ok
                // to delete.
                return DECISION_YES;
            }
        }

        return DECISION_NA; // No fields selected so check other things.
    }

    /**
     * This method creates a RemoveCommand and returns it for selected EObject.
     * 
     * @param eObject
     * 
     * @return <code>CommandContainer</code> if successful in creating remove
     *         command, <code>null</code> otherwise.
     */
    protected void createDeleteCommand(List eObjectList) {
        deleteCommand = null;
        editingDomain = null;
        cmdLabel = null;

        if (eObjectList.size() < 1) {
            return;
        }

        Iterator iter = eObjectList.iterator();
        EObject eObject = (EObject) iter.next();
        editingDomain = WorkingCopyUtil.getEditingDomain(eObject);

        if (editingDomain != null) {
            deleteCommand = new CompoundCommand();

            deleteCommand.append(RemoveCommand.create(editingDomain,
                    eObjectList));

            boolean multi = (eObjectList.size() > 1);

            if (eObject instanceof Process) {
                if (multi) {
                    cmdLabel =
                            Messages.DeleteAction_deleteProcessesCommand_title;
                } else {
                    cmdLabel = Messages.DeleteAction_deleteProcessCommand_title;
                }
            } else if (eObject instanceof DataField) {
                if (multi) {
                    cmdLabel = Messages.DeleteAction_deleteFieldsCommand_title;
                } else {
                    cmdLabel = Messages.DeleteAction_deleteFieldCommand_title;
                }
            } else if (eObject instanceof FormalParameter) {
                if (multi) {
                    cmdLabel = Messages.DeleteAction_deleteParamsCommand_title;
                } else {
                    cmdLabel = Messages.DeleteAction_deleteParamCpmmand_title;
                }
            } else if (eObject instanceof Participant) {
                if (multi) {
                    cmdLabel =
                            Messages.DeleteAction_deleteParticipantsCommand_title;
                } else {
                    cmdLabel =
                            Messages.DeleteAction_deleteParticipantCommand_title;
                }
            } else if (eObject instanceof TypeDeclaration) {
                if (multi) {
                    cmdLabel =
                            Messages.DeleteAction_deleteTypeDeclarationsCommand_title;
                } else {
                    cmdLabel =
                            Messages.DeleteAction_deleteTypeDeclarationCommand_title;
                }
            }

            return;
        }

        return;
    }

    /**
     * Add supplementary commands to the delete Command (only added when user
     * actually executes as this could be quite expensive.
     * 
     */
    private Command addSupplementCommands() {
        CompoundCommand supplementCommand = new CompoundCommand();

        for (Iterator iter = selectionList.iterator(); iter.hasNext();) {
            Object obj = iter.next();

            // Give opportunity to Remove references to datafield / formal
            // parameters
            if (obj instanceof ProcessRelevantData) {
                ProcessRelevantData data = (ProcessRelevantData) obj;

                /*
                 * XPD-5427: Use new Xpdl2FieldOrParamDeleter method that deals
                 * with all the objects that may contain references internally.
                 */
                Xpdl2FieldOrParamDeleter deleter =
                        new Xpdl2FieldOrParamDeleter();

                Command c =
                        deleter.getDeleteDataReferencesCommand(editingDomain,
                                data);

                if (c != null) {
                    supplementCommand.append(c);
                }

            }

            // Give opportunity to Remove references to participants
            if (obj instanceof Participant) {
                Participant participant = (Participant) obj;
                // this gives the tasks/activities that has references to the
                // above participant
                Set<EObject> referencingObjects =
                        Xpdl2ParticipantReferenceResolver
                                .getReferencingObjects(participant);
                for (EObject object : referencingObjects) {
                    if (object instanceof Activity) {
                        Activity activity = (Activity) object;

                        // clearing the alias attribute for a web service
                        // operation whenever a system participant is deleted

                        WebServiceOperation wso =
                                Xpdl2ModelUtil.getWebServiceOperation(activity);
                        if (wso != null) {
                            String aliasId =
                                    (String) Xpdl2ModelUtil
                                            .getOtherAttribute(wso,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_Alias());
                            if (null != aliasId
                                    && participant.getId().equals(aliasId)) {
                                supplementCommand
                                        .append(Xpdl2ModelUtil
                                                .getSetOtherAttributeCommand(editingDomain,
                                                        wso,
                                                        XpdExtensionPackage.eINSTANCE
                                                                .getDocumentRoot_Alias(),
                                                        ""));//$NON-NLS-1$
                            }
                        }

                        // deleting the participant references to any
                        // task/activity whenever a participant is deleted
                        EList<Performer> performerList =
                                activity.getPerformerList();
                        for (Performer performer : performerList) {
                            String performerId = performer.getValue();
                            if (participant.getId().equals(performerId)) {
                                supplementCommand.append(RemoveCommand
                                        .create(editingDomain, performer));
                            }
                        }
                    }
                }
            }

            // For whole process delete, delete any associated pools, artifacts
            // and associations.
            if (obj instanceof Process) {
                Process process = (Process) obj;

                Collection<Pool> pools =
                        Xpdl2ModelUtil.getProcessPools(process);
                if (pools != null && pools.size() > 0) {
                    for (Pool pool : pools) {
                        supplementCommand.append(RemoveCommand
                                .create(editingDomain, pool));
                    }
                }

                Collection<Artifact> artifacts =
                        Xpdl2ModelUtil.getAllArtifactsInProcess(process);
                if (artifacts != null && artifacts.size() > 0) {
                    for (Artifact artifact : artifacts) {
                        supplementCommand.append(RemoveCommand
                                .create(editingDomain, artifact));
                    }
                }

                Collection<Association> associations =
                        Xpdl2ModelUtil.getAllAssociationsInProc(process);
                if (associations != null && associations.size() > 0) {
                    for (Association association : associations) {
                        supplementCommand.append(RemoveCommand
                                .create(editingDomain, association));
                    }
                }

            }

        }

        if (supplementCommand.getCommandList().size() == 0) {
            return null;
        }

        return supplementCommand;
    }

    /**
     * Utility that wraps the delete action. This is of use for xpdl object that
     * are created and deleted in group tables.
     * 
     * @param shell
     * @param xpdlObject
     */
    public static void deleteXpdlObject(Shell shell,
            IStructuredSelection selection) {
        DeleteAction deleteAction = new DeleteAction(shell);
        deleteAction.selectionChanged(selection);
        deleteAction.run();
    }
}
