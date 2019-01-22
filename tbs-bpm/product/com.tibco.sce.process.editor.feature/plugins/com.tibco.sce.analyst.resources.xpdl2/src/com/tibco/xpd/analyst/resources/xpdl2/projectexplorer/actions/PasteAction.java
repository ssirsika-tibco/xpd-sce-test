/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.action.ProcessClipboardUtils;
import com.tibco.xpd.analyst.resources.xpdl2.action.ProcessClipboardUtils.ProcessClipboardDataList;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.CommandContainer;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.utils.ActionUtil.ProjectReference;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * Project Explorer's Paste action
 * 
 * @author njpatel
 * 
 */
public class PasteAction extends BaseSelectionListenerAction {

    /**
     * The id of this action.
     */
    public static final String ID = Xpdl2ResourcesPlugin.PLUGIN_ID
            + ".PasteAction";//$NON-NLS-1$

    public static final String PREFIX = PlatformUI.PLUGIN_ID + "."; //$NON-NLS-1$

    public static final String PASTE_ACTION = PREFIX
            + "resource_navigator_paste_action_context"; //$NON-NLS-1$

    private CommandContainer commandContainer = null;

    /**
     * @return the commandContainer
     */
    public CommandContainer getCommandContainer() {
        return commandContainer;
    }

    /**
     * @param commandContainer
     *            the commandContainer to set
     */
    public void setCommandContainer(CommandContainer commandContainer) {
        this.commandContainer = commandContainer;
    }

    private IStructuredSelection lastSelection = null;

    /**
     * @param lastSelection
     *            the lastSelection to set
     */
    public void setLastSelection(IStructuredSelection lastSelection) {
        this.lastSelection = lastSelection;
    }

    private Collection<ProjectReference> projectReferences = null;

    /**
     * Paste action
     */
    public PasteAction() {
        super(Messages.PasteAction_Menu_title);
        setToolTipText(Messages.PasteAction_1);
        setId(ID);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, PASTE_ACTION);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org
     * .eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    @SuppressWarnings("unchecked")
    protected boolean updateSelection(IStructuredSelection selection) {
        lastSelection = selection;
        boolean bRet = super.updateSelection(selection);

        if (bRet && selection != null && selection.size() > 0) {
            List<EObject> selectionList = selection.toList();

            // Check if group selected

            Object target = selectionList.get(0);

            if (ActionUtil.isLogicalNodeInList(selectionList)) {
                Object tempSelObj = selectionList.get(0);

                if (tempSelObj instanceof AbstractAssetGroup) {
                    // Logical node in list
                    AbstractAssetGroup group = (AbstractAssetGroup) tempSelObj;
                    EObject parent = (EObject) group.getParent();

                    if (parent != null) {
                        selectionList = new ArrayList<EObject>();
                        selectionList.add(parent);
                    } else {
                        bRet = false;
                    }
                } else {
                    bRet = false;
                }
            }

            // Create paste command
            if (bRet) {
                // Make sure all objects belong to the same parent
                // Looks weird for paste BUT it allows for multi-select (say
                // data fields) then Ctrl+C then Ctrl+v

                bRet = validateSelection(selectionList);

                if (bRet) {
                    commandContainer =
                            getCommandContainer(selectionList, target);

                    // Check that we have a valid command
                    if (commandContainer == null) {
                        bRet = false;
                    } else if (!commandContainer.getCommand().canExecute()) {
                        bRet = false;
                    }

                    // If not successful then clear command
                    if (!bRet) {
                        commandContainer = null;
                    }

                }
            }
        }

        return bRet;
    }

    /**
     * @param selectionList
     * @return whether the
     */
    protected boolean validateSelection(List<EObject> selectionList) {
        return ActionUtil.allEObjects(selectionList)
                && ActionUtil.eObjectsWithSameParent(selectionList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        if (lastSelection != null) {
            updateSelection(lastSelection);
        }
        if (commandContainer != null) {
            commandContainer.executeCommand();
        }
    }

    /**
     * Get the pasted elements.
     * 
     * @return
     */
    public Collection<?> getResult() {
        return commandContainer != null ? commandContainer.getCommand()
                .getResult() : null;
    }

    /**
     * This method ascertains whether the Paste Command can be executed for the
     * selected EObject.
     * 
     * @param eObject
     * @return <code>CommandContainer</code> if Paste is available,
     *         <code>null</code> otherwise.
     */
    protected CommandContainer getCommandContainer(
            List<EObject> destEObjectList, Object target) {
        if (destEObjectList.size() < 1) {
            return null;
        }

        EObject destEObject = destEObjectList.get(0);

        EditingDomain editingDomain =
                WorkingCopyUtil.getEditingDomain(destEObject);

        if (editingDomain != null) {
            //
            // We have to be a little more clever about pasting from clipboard
            // now.
            // If we are pasting whole process then there will be ancillary
            // things like pools, artifacts and so on in the clipboard.
            Collection clipboardObjs = ProcessClipboardUtils.getClipboard();
            // XPD-3033 check requirement for new project references and ask
            // user.
            // get and filter out special
            // "project reference elements in pasteObejcts" + only use actual
            // objects to be copied in rest of code below
            //
            Collection<Object> filteredPasteObjects = new ArrayList<Object>();
            projectReferences = new ArrayList<ProjectReference>();
            ActionUtil
                    .filterProjectReferencesAndOtherObjects(filteredPasteObjects,
                            projectReferences,
                            clipboardObjs);
            if (isClipboardContentSuitableForTargetContext(destEObject,
                    filteredPasteObjects)) {
                Collection copyObjects =
                        copyClipboardObjects(editingDomain,
                                destEObjectList,
                                filteredPasteObjects);
                if (copyObjects != null && !copyObjects.isEmpty()) {
                    // merge project references to the copied objects list
                    copyObjects.addAll(projectReferences);
                    return createPasteCommand(target,
                            destEObject,
                            editingDomain,
                            copyObjects,
                            true);
                }
            }
        }

        return null;
    }

    /**
     * Check whether the source context of clipboard is valid for the target
     * context
     * 
     * @param targetContext
     * @param clipboardObjects
     * 
     * @return <code>false</code> if not <code>true</code> if it is.
     */
    protected boolean isClipboardContentSuitableForTargetContext(
            EObject targetContext, Collection<Object> clipboardObjects) {
        /*
         * Get source process / package of clipboard objects for context (if
         * available)
         */
        ProcessClipboardDataList sourceContext =
                ProcessClipboardUtils.getSourceContextForClipboardContent();

        if (sourceContext != null
                && sourceContext.getSourceProcessOrPackage() != null) {
            /*
             * Only one rule at the moment - you cannot paste from
             * ProcessPackage <--> Decision flow package.
             */
            if (DecisionFlowUtil.isDecisionsContent(targetContext) != DecisionFlowUtil
                    .isDecisionsContent(sourceContext
                            .getSourceProcessOrPackage())) {
                /*
                 * Unless the only content to paste is formal parameters (which
                 * is all they have in common.
                 */
                for (Object object : clipboardObjects) {
                    if (!(object instanceof FormalParameter)) {
                        return false;
                    }
                }
            }
        }

        /*
         * XPD-7579: Saket: Can't allow DataField derivatives (e.g.
         * PayloadDataField) to be pasted here.
         */
        if (sourceContext == null) {

            for (Object object : clipboardObjects) {

                if ((object instanceof DataField && !object.getClass()
                        .equals(DataField.class))) {

                    return false;
                }
            }
        }

        /*
         * Default to ok if cannot find a source context or both contexts were
         * decisionflow package content or not decisionflow package content.
         */
        return true;

    }

    /**
     * @param target
     * @param destEObject
     * @param editingDomain
     * @param copyObjects
     * @return
     */
    protected CommandContainer createPasteCommand(Object target,
            EObject destEObject, EditingDomain editingDomain,
            Collection copyObjects, boolean handleProjectReferences) {
        return ActionUtil.paste(destEObject,
                copyObjects,
                editingDomain,
                target,
                handleProjectReferences);
    }

    /**
     * Create a copy of the clipboard objects and perform any conversion to
     * different data types necessary.
     * 
     * @param editingDomain
     * @param explorerSelection
     * @param clipboardObjs
     * 
     * @return List of object to paste (empty list to prevent the paste).
     */
    private Collection copyClipboardObjects(EditingDomain editingDomain,
            Collection<EObject> explorerSelection,
            Collection<Object> clipboardObjs) {
        // Grab a copy of the clipboard objects.
        Command cpy = CopyCommand.create(editingDomain, clipboardObjs);
        if (!cpy.canExecute()) {
            return null;
        }

        cpy.execute();

        Collection copyObjects = cpy.getResult();
        return copyObjects;
    }

}
