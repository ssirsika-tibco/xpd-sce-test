/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.actions;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class DeleteScriptAction implements IViewActionDelegate {

    /** The selection. */
    private ISelection selection;

    /**
     * @param view
     * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
     */
    @Override
    public void init(IViewPart view) {
    }

    /**
     * @param action
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run(IAction action) {
        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;
            for (Object next : structured.toArray()) {
                if (next instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) next;
                    EditingDomain ed =
                            WorkingCopyUtil.getEditingDomain(information);
                    if (ed != null) {
                        EObject parent = information.eContainer();
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.DeleteScriptAction_DeleteScript);
                        if (parent instanceof DataMapping) {
                            cmd.append(RemoveCommand.create(ed, parent));
                        } else if (parent instanceof OtherElementsContainer) {
                            if (parent instanceof ScriptDataMapper) {
                                ScriptDataMapper scriptDataMapper = (ScriptDataMapper) parent;
                                cmd.append(RemoveCommand.create(ed, scriptDataMapper,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getScriptDataMapper_UnmappedScripts(),
                                                information));
                            } else {
                                OtherElementsContainer container =
                                        (OtherElementsContainer) parent;
                                cmd.append(Xpdl2ModelUtil
                                        .getRemoveOtherElementCommand(ed,
                                                container,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_Script(),
                                                information));
                            }
                        }
                        if (cmd.canExecute()) {
                            ed.getCommandStack().execute(cmd);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param action
     * @param selection
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

}
