/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.List;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;

import com.tibco.xpd.analyst.resources.xpdl2.ReservedWords;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.actions.AbstractRenameAction;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Project Explorer's Rename action
 * 
 * @author njpatel
 * 
 */
public class RenameAction extends AbstractRenameAction {

    /**
     * Rename action
     * 
     * @param shell
     * @param tree
     */
    public RenameAction(Shell shell, Tree tree) {
        super(shell, tree);
    }

    /**
     * Special constructor of rename action that allows ctrl+c accelerator
     * copy/paste/cut etc.
     * <p>
     * For this the action bar that the action belongs to is required so it can
     * set/unset itself as the default action handler.
     * 
     * @param shell
     * @param tree
     * @param actionBars
     */
    public RenameAction(Shell shell, Tree tree, IActionBars actionBars) {
        super(shell, tree, actionBars);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.actions.AbstractRenameAction#canRename(java.lang.Object)
     */
    @Override
    protected boolean canRename(Object selectedItem) {
        if (selectedItem instanceof EObject
                && !(selectedItem instanceof INavigatorGroup)) {
            return true;
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.actions.AbstractRenameAction#doRename(java.lang.Object,
     * java.lang.String)
     */
    @Override
    protected void doRename(Object selectedItem, String newName) {
        if (selectedItem instanceof EObject) {
            if (verifyNewName((EObject) selectedItem, newName)) {
                setDisplayName((EObject) selectedItem, newName);
            }
        }
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.actions.AbstractRenameAction#getName(java.lang.Object)
     */
    @Override
    protected String getName(Object selectedItem) {
        String text = null;
        if (selectedItem instanceof EObject) {
            EObject eo = (EObject) selectedItem;
            if (eo instanceof NamedElement) {
                NamedElement named = (NamedElement) eo;
                text =
                        (String) Xpdl2ModelUtil.getOtherAttribute(named,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
            } else {
                text = WorkingCopyUtil.getText((EObject) selectedItem);
            }
        }
        return text;
    }

    protected void setDisplayName(EObject eo, String newName) {
        EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(eo);
        if (editingDomain != null && eo instanceof NamedElement) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            (NamedElement) eo,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            newName));
            if (eo instanceof ProcessRelevantData) {
                cmd.setLabel(Messages.RenameAction_Rename_menu);
            } else if (eo instanceof Process) {
                cmd.setLabel(Messages.RenameAction_RenameProcess_menu);
            } else if (eo instanceof ProcessInterface) {
                cmd.setLabel(Messages.RenameAction_RenameProcessInterface_menu);
            } else if (eo instanceof StartMethod) {
                cmd.setLabel(Messages.RenameAction_RenameStartMethod_menu);
            } else if (eo instanceof IntermediateMethod) {
                cmd
                        .setLabel(Messages.RenameAction_RenameIntermediateMethod_menu);
            } else if (eo instanceof Participant) {
                cmd.setLabel(Messages.RenameAction_RenamePartic_menu);
            } else if (eo instanceof TypeDeclaration) {
                cmd.setLabel(Messages.RenameAction_RenameType_menu);
            } else {
                cmd.setLabel(Messages.RenameAction_Rename_menu);
            }
            if (cmd.canExecute()) {
                editingDomain.getCommandStack().execute(cmd);
            }
        }
    }

    /**
     * Use command to change name of the EObject
     */
    protected boolean verifyNewName(EObject eObj, String newDisplayName) {
        if (eObj != null
                && (newDisplayName == null || newDisplayName.length() < 1)) {
            return false;
        }

        if (!(eObj instanceof NamedElement)) {
            return false;
        }

        NamedElement namedElement = (NamedElement) eObj;

        boolean stripLeadingNumerics =
                namedElement instanceof ProcessRelevantData
                        || namedElement instanceof TypeDeclaration;
        String potentialNewTokenName =
                NameUtil.getInternalName(newDisplayName, stripLeadingNumerics);

        // 
        // No that Rename changes the display name we should probably not worry
        // about leading / trailing space.#
        // So for now we won't bother.
        if (false) {
            String trimmed = newDisplayName.trim();

            if (!trimmed.equals(newDisplayName)
                    && !(namedElement instanceof Package)) {
                String msg;

                if (namedElement instanceof ProcessRelevantData) {
                    msg = Messages.RenameAction_LeadSpaceNotAllowedForField;
                } else if (namedElement instanceof TypeDeclaration) {
                    msg = Messages.RenameAction_LeadSpaceNotAllowedForTypes;
                } else if (namedElement instanceof Participant) {
                    msg = Messages.RenameAction_LeadSpaceNotAllowedForPartics;
                } else if (namedElement instanceof Process) {
                    msg = Messages.RenameAction_LeadSpaceNotAllowedForProcess;
                } else {
                    msg = Messages.RenameAction_LeadSpaceNotAllowedForGeneric;
                }

                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        Messages.RenameAction_Rename_title,
                        msg);

                return false;
            }
        }

        if (namedElement instanceof ProcessRelevantData) {
            //
            // Data Field or Parameter.
            //
            EObject duplicate =
                    Xpdl2ModelUtil.getDuplicateDisplayFieldOrParam(namedElement
                            .eContainer(),
                            (ProcessRelevantData) namedElement,
                            newDisplayName);

            // MR 38533 - begin
            if (null == duplicate) {
                duplicate =
                        Xpdl2ModelUtil
                                .getDuplicateDisplayParticipant(namedElement
                                        .eContainer(), null, newDisplayName);
            }
            // MR 38533 - end

            if (duplicate != null) {
                String msg = null;

                if (duplicate.eContainer() instanceof Package) {
                    msg =
                            Messages.RenameAction_DuplicateInPackage_longdesc
                                    + "\n\n\t" //$NON-NLS-1$
                                    + ((Package) duplicate.eContainer())
                                            .getName();

                } else if (duplicate.eContainer() instanceof Process) {
                    msg =
                            Messages.RenameAction_DuplicateInProcess_longdesc
                                    + "\n\n\t" //$NON-NLS-1$
                                    + ((Process) duplicate.eContainer())
                                            .getName();

                } else if (duplicate.eContainer() instanceof ProcessInterface) {
                    msg =
                            Messages.RenameAction_DuplicateInProcessInterface_longdesc
                                    + "\n\n\t" //$NON-NLS-1$
                                    + ((ProcessInterface) duplicate
                                            .eContainer()).getName();
                    // MR 39410 - begin
                } else if (duplicate.eContainer() instanceof Activity) {
                    msg =
                            Messages.RenameAction_DuplicateInEmbeddedSubProc_longdesc
                                    + "\n\n\t" //$NON-NLS-1$
                                    + ((Activity) duplicate.eContainer())
                                            .getName();
                }
                // MR 39410 - end
                if (msg != null) {
                    MessageDialog.openError(Display.getDefault()
                            .getActiveShell(),
                            Messages.RenameAction_Rename_title,
                            msg);

                    return false;
                }

            } else if (currTokenMatchesCurrDisplayName(namedElement)) {
                // If the current token name matches the current display name
                // then it will be changed at command execution (by
                // UpdateTokenNamePreCommitContribution) so have to check token
                // name for duplicates too.
                duplicate =
                        Xpdl2ModelUtil.getDuplicateFieldOrParam(namedElement
                                .eContainer(),
                                (ProcessRelevantData) namedElement,
                                potentialNewTokenName);

                // MR 38533 - begin
                if (null == duplicate) {
                    duplicate =
                            Xpdl2ModelUtil.getDuplicateParticipant(namedElement
                                    .eContainer(), null, newDisplayName);
                }
                // MR 38533 - end

                if (duplicate != null) {
                    String msg = null;

                    if (duplicate.eContainer() instanceof Package) {
                        msg =
                                Messages.RenameAction_DuplicateTokenInPackage_longdesc
                                        + "\n\n\t" //$NON-NLS-1$
                                        + ((Package) duplicate.eContainer())
                                                .getName();

                    } else if (duplicate.eContainer() instanceof Process) {
                        msg =
                                Messages.RenameAction_DuplicateTokenInProcess_longdesc
                                        + "\n\n\t" //$NON-NLS-1$
                                        + ((Process) duplicate.eContainer())
                                                .getName();

                    } else if (duplicate.eContainer() instanceof ProcessInterface) {
                        msg =
                                Messages.RenameAction_DuplicateTokenInProcessInterface_longdesc
                                        + "\n\n\t" //$NON-NLS-1$
                                        + ((ProcessInterface) duplicate
                                                .eContainer()).getName();
                        // MR 39410 - begin
                    } else if (duplicate.eContainer() instanceof Activity) {
                        msg =
                                Messages.RenameAction_DuplicateTokenInEmbeddedSubProc_longdesc
                                        + "\n\n\t" //$NON-NLS-1$
                                        + ((Activity) duplicate.eContainer())
                                                .getName();
                    }
                    // MR 39410 - end
                    if (msg != null) {
                        MessageDialog.openError(Display.getDefault()
                                .getActiveShell(),
                                Messages.RenameAction_Rename_title,
                                msg);

                        return false;
                    }

                } else {
                    // XPD-757
                    // if its not a duplicate, check if it is a reserved word
                    if (isReservedWord(newDisplayName)) {
                        String msg =
                                Messages.RenameAction_ReservedWord_longdesc
                                        + "\n\n\t"; //$NON-NLS-1$
                        MessageDialog.openError(Display.getDefault()
                                .getActiveShell(),
                                Messages.RenameAction_Rename_title,
                                msg);
                        return false;
                    }
                }
            }

        }

        else if (namedElement instanceof Process) {
            // Ensure that this name is not duplicate of existing (by display
            // name).
            Process process = (Process) namedElement;
            Process duplicate =
                    Xpdl2ModelUtil.getDuplicateDisplayNameProcess(process
                            .getPackage(), process, newDisplayName);

            ProcessInterface processInterface =
                    Xpdl2ModelUtil.getDuplicateDisplayProcessInterface(process
                            .getPackage(), null, newDisplayName);

            if (processInterface != null || duplicate != null) {
                String msg = Messages.RenameAction_DuplicateProcess_longdesc;
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        Messages.RenameAction_Rename_title,
                        msg);

                return false;
            }

            // If the current token name matches the current display name then
            // it will be changed at command execution (by
            // UpdateTokenNamePreCommitContribution) so have to check token name
            // for duplicates too.
            if (currTokenMatchesCurrDisplayName(namedElement)) {
                duplicate =
                        Xpdl2ModelUtil
                                .getDuplicateProcess(process.getPackage(),
                                        process,
                                        potentialNewTokenName);

                processInterface =
                        Xpdl2ModelUtil.getDuplicateProcessInterface(process
                                .getPackage(), null, potentialNewTokenName);

                if (processInterface != null || duplicate != null) {
                    String msg =
                            Messages.RenameAction_DuplicateProcessTokenName_longdesc;
                    MessageDialog.openError(Display.getDefault()
                            .getActiveShell(),
                            Messages.RenameAction_Rename_title,
                            msg);

                    return false;
                }
            }
        }
        // Process Interface duplicate check
        else if (namedElement instanceof ProcessInterface) {
            // Ensure that this name is not duplicate of existing.
            ProcessInterface processInterface = (ProcessInterface) namedElement;
            Package pkg =
                    ProcessInterfaceUtil
                            .getProcessInterfacePackage(processInterface);
            ProcessInterface duplicate =
                    Xpdl2ModelUtil.getDuplicateDisplayProcessInterface(pkg,
                            processInterface,
                            newDisplayName);
            Process processDuplicate =
                    Xpdl2ModelUtil.getDuplicateDisplayNameProcess(pkg,
                            null,
                            newDisplayName);

            if (duplicate != null || processDuplicate != null) {
                String msg =
                        Messages.RenameAction_DuplicateProcessInterface_longdesc;
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        Messages.RenameAction_Rename_title,
                        msg);

                return false;

            } else if (currTokenMatchesCurrDisplayName(namedElement)) {
                // If the current token name matches the current display name
                // then
                // it will be changed at command execution (by
                // UpdateTokenNamePreCommitContribution) so have to check token
                // name
                // for duplicates too.
                processInterface = (ProcessInterface) namedElement;
                pkg =
                        ProcessInterfaceUtil
                                .getProcessInterfacePackage(processInterface);
                duplicate =
                        Xpdl2ModelUtil.getDuplicateProcessInterface(pkg,
                                processInterface,
                                potentialNewTokenName);
                processDuplicate =
                        Xpdl2ModelUtil.getDuplicateProcess(pkg,
                                null,
                                potentialNewTokenName);

                if (duplicate != null || processDuplicate != null) {
                    String msg =
                            Messages.RenameAction_DuplicateProcessInterfaceToken_longdesc;
                    MessageDialog.openError(Display.getDefault()
                            .getActiveShell(),
                            Messages.RenameAction_Rename_title,
                            msg);

                    return false;

                }
            }
        }
        // Start Method
        else if (namedElement instanceof StartMethod) {
            // Ensure that this name is not duplicate of existing.
            StartMethod startMethod = (StartMethod) namedElement;
            StartMethod duplicate =
                    Xpdl2ModelUtil.getDuplicateDisplayStartMethod(startMethod
                            .eContainer(), startMethod, newDisplayName);

            if (duplicate != null) {
                String msg =
                        Messages.RenameAction_DuplicateStartMethod_longdesc;
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        Messages.RenameAction_Rename_title,
                        msg);

                return false;

            } else if (currTokenMatchesCurrDisplayName(namedElement)) {
                // If the current token name matches the current display name
                // then it will be changed at command execution (by
                // UpdateTokenNamePreCommitContribution) so have to check token
                // name for duplicates too.
                startMethod = (StartMethod) namedElement;
                duplicate =
                        Xpdl2ModelUtil.getDuplicateStartMethod(startMethod
                                .eContainer(),
                                startMethod,
                                potentialNewTokenName);

                if (duplicate != null) {
                    String msg =
                            Messages.RenameAction_DuplicateStartMethodToken_longdesc;
                    MessageDialog.openError(Display.getDefault()
                            .getActiveShell(),
                            Messages.RenameAction_Rename_title,
                            msg);

                    return false;

                }
            }
        }
        // Intermediate Method
        else if (namedElement instanceof IntermediateMethod) {
            // Ensure that this name is not duplicate of existing.
            IntermediateMethod intermediateMethod =
                    (IntermediateMethod) namedElement;
            IntermediateMethod duplicate =
                    Xpdl2ModelUtil
                            .getDuplicateDisplayIntermediateMethod(intermediateMethod
                                    .eContainer(),
                                    intermediateMethod,
                                    newDisplayName);

            if (duplicate != null) {
                String msg =
                        Messages.RenameAction_DuplicateIntermediateMethod_longdesc;
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        Messages.RenameAction_Rename_title,
                        msg);

                return false;

            } else if (currTokenMatchesCurrDisplayName(namedElement)) {
                // If the current token name matches the current display name
                // thenit will be changed at command execution (by
                // UpdateTokenNamePreCommitContribution) so have to check token
                // name for duplicates too.
                intermediateMethod = (IntermediateMethod) namedElement;
                duplicate =
                        Xpdl2ModelUtil
                                .getDuplicateIntermediateMethod(intermediateMethod
                                        .eContainer(),
                                        intermediateMethod,
                                        potentialNewTokenName);

                if (duplicate != null) {
                    String msg =
                            Messages.RenameAction_DuplicateIntermediateMethodToken_longdesc;
                    MessageDialog.openError(Display.getDefault()
                            .getActiveShell(),
                            Messages.RenameAction_Rename_title,
                            msg);

                    return false;

                }
            }
        }

        else if (namedElement instanceof Participant) {
            // Ensure that this name is not duplicate of existing.
            Participant participant = (Participant) namedElement;
            EObject duplicate =
                    Xpdl2ModelUtil.getDuplicateDisplayParticipant(namedElement
                            .eContainer(), participant, newDisplayName);

            String msg = null;

            // MR 38533 - begin
            if (null == duplicate) {
                duplicate =
                        Xpdl2ModelUtil
                                .getDuplicateDisplayFieldOrParam(namedElement
                                        .eContainer(), null, newDisplayName);
            }
            // MR 38533 - end

            if (duplicate != null) {
                if (duplicate.eContainer() instanceof Package) {
                    msg =
                            Messages.RenameAction_DuplicateParticipantInPackage_longdesc
                                    + "\n\n\t" //$NON-NLS-1$
                                    + ((Package) duplicate.eContainer())
                                            .getName();
                } else if (duplicate.eContainer() instanceof Process) {
                    msg =
                            Messages.RenameAction_DuplicateParticipantInProcess_longdesc
                                    + "\n\n\t" //$NON-NLS-1$
                                    + ((Process) duplicate.eContainer())
                                            .getName();
                    // MR 39410 - begin
                } else if (duplicate.eContainer() instanceof Activity) {
                    msg =
                            Messages.RenameAction_DuplicateParticipantInEmbeddedSubProc_longdesc
                                    + "\n\n\t" //$NON-NLS-1$
                                    + ((Activity) duplicate.eContainer())
                                            .getName();
                }
                // MR 39410 - end
            }

            if (msg != null) {
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        Messages.RenameAction_Rename_title,
                        msg);

                return false;

            } else if (currTokenMatchesCurrDisplayName(namedElement)) {
                // If the current token name matches the current display name
                // then it will be changed at command execution (by
                // UpdateTokenNamePreCommitContribution) so have to check token
                // name for duplicates too.
                duplicate =
                        Xpdl2ModelUtil.getDuplicateParticipant(namedElement
                                .eContainer(),
                                participant,
                                potentialNewTokenName);

                // MR 38533 - begin
                if (null == duplicate) {
                    duplicate =
                            Xpdl2ModelUtil
                                    .getDuplicateFieldOrParam(namedElement
                                            .eContainer(),
                                            null,
                                            potentialNewTokenName);
                }
                // MR 38533 - end

                msg = null;

                if (duplicate != null) {
                    if (duplicate.eContainer() instanceof Package) {
                        msg =
                                Messages.RenameAction_DuplicateParticipantTokenInPackage_longdesc
                                        + "\n\n\t" //$NON-NLS-1$
                                        + ((Package) duplicate.eContainer())
                                                .getName();
                    } else if (duplicate.eContainer() instanceof Process) {
                        msg =
                                Messages.RenameAction_DuplicateParticipantTokenInProcess_longdesc
                                        + "\n\n\t" //$NON-NLS-1$
                                        + ((Process) duplicate.eContainer())
                                                .getName();
                        // MR 39410 - begin
                    } else if (duplicate.eContainer() instanceof Activity) {
                        msg =
                                Messages.RenameAction_DuplicateParticipantTokenInEmbeddedSubProc_longdesc
                                        + "\n\n\t" //$NON-NLS-1$
                                        + ((Activity) duplicate.eContainer())
                                                .getName();
                    }
                    // MR 39410 - end
                }

                if (msg != null) {
                    MessageDialog.openError(Display.getDefault()
                            .getActiveShell(),
                            Messages.RenameAction_Rename_title,
                            msg);
                    return false;
                }

            }

        } else if (namedElement instanceof TypeDeclaration) {
            TypeDeclaration typeDeclaration = (TypeDeclaration) namedElement;

            Package pkg = (Package) namedElement.eContainer();

            TypeDeclaration duplicate =
                    Xpdl2ModelUtil.getDuplicateDisplayTypeDeclaration(pkg,
                            typeDeclaration,
                            newDisplayName);

            if (duplicate != null) {
                String msg =
                        Messages.RenameAction_DuplicateTypeDeclaration_longdesc;
                MessageDialog.openError(Display.getDefault().getActiveShell(),
                        Messages.RenameAction_Rename_title,
                        msg);

                return false;

            } else if (currTokenMatchesCurrDisplayName(namedElement)) {
                // If the current token name matches the current display name
                // then it will be changed at command execution (by
                // UpdateTokenNamePreCommitContribution) so have to check token
                // name for duplicates too.
                typeDeclaration = (TypeDeclaration) namedElement;

                pkg = (Package) namedElement.eContainer();

                duplicate =
                        Xpdl2ModelUtil.getDuplicateTypeDeclaration(pkg,
                                typeDeclaration,
                                potentialNewTokenName);

                if (duplicate != null) {
                    String msg =
                            Messages.RenameAction_DuplicateTypeDeclarationToken_longdesc;
                    MessageDialog.openError(Display.getDefault()
                            .getActiveShell(),
                            Messages.RenameAction_Rename_title,
                            msg);

                    return false;
                }
            }
        }

        return true;
    }

    /**
     * @param nameText
     * @return
     */
    private boolean isReservedWord(String nameText) {
        List<String> symbolTableKeywords =
                ReservedWords.getSymbolTableKeyWords();
        if (symbolTableKeywords.contains(nameText)) {
            return true;
        }
        return false;
    }

    private boolean currTokenMatchesCurrDisplayName(NamedElement namedElement) {
        String currentTokenName = namedElement.getName();
        if (currentTokenName == null) {
            currentTokenName = ""; //$NON-NLS-1$
        }

        String currentDisplayName = Xpdl2ModelUtil.getDisplayName(namedElement);
        if (currentDisplayName == null) {
            currentDisplayName = ""; //$NON-NLS-1$
        }

        boolean stripLeadingNumerics =
                namedElement instanceof ProcessRelevantData
                        || namedElement instanceof TypeDeclaration;
        String properTokenName =
                NameUtil.getInternalName(currentDisplayName,
                        stripLeadingNumerics);

        if (currentTokenName.equals(properTokenName)) {
            return true;
        }

        return false;
    }
}
