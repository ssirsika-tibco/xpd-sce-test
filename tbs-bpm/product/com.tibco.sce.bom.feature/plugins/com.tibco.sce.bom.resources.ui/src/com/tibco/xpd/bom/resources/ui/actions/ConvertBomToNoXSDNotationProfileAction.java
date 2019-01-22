/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.utils.BOMProfileUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * converts the selected bom model(s) to remove the applied xsd notation profile
 * 
 * @author bharge
 * @since 13 Mar 2012
 */
public class ConvertBomToNoXSDNotationProfileAction implements
        IObjectActionDelegate {

    private List<IFile> bomFiles = Collections.emptyList();

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     * 
     * @param action
     */
    @Override
    public void run(IAction action) {

        if (bomFiles.size() > 0) {

            Map<Model, Profile> modelProfilesMap =
                    getModelsAndProfiles(bomFiles);

            /*
             * get the confirmation from the user before removing xsd notation
             * profile
             */
            boolean removeXSDNotation =
                    MessageDialog
                            .openConfirm(Display.getDefault().getActiveShell(),
                                    Messages.RemoveXSDNotationProfile_ConvertBOM_title,
                                    Messages.RemoveXSDNotationProfile_ConvertBOM_confirmationMessage);

            if (removeXSDNotation) {

                /*
                 * ensuring that we have affected models before executing the
                 * command
                 */
                if (!modelProfilesMap.isEmpty()) {
                    TransactionalEditingDomain editingDomain =
                            XpdResourcesPlugin.getDefault().getEditingDomain();

                    Command cmd =
                            getUnapplyXSDProfileCommand(modelProfilesMap,
                                    editingDomain);

                    if (null != cmd && cmd.canExecute()) {
                        editingDomain.getCommandStack().execute(cmd);
                    }
                }
            }

            /* asking the user to save the affected models */
            if (!modelProfilesMap.isEmpty()) {
                /*
                 * the user had already agreed to remove the XSD notation
                 * profile above
                 */
                if (removeXSDNotation) {
                    saveAffectedBOMFiles(modelProfilesMap);
                }
            } else {
                /* if no models are affected inform the user of the same */
                MessageDialog
                        .openInformation(Display.getDefault().getActiveShell(),
                                Messages.RemoveXSDNotationProfile_ConvertBOM_title,
                                Messages.RemoveXSDNotationProfile_noFilesAffected_infoMessage);
            }

        }
    }

    /**
     * save the bom files that are affected after removing the xsd notation
     * profiles from them
     * 
     * @param modelProfilesMap
     */
    private void saveAffectedBOMFiles(Map<Model, Profile> modelProfilesMap) {
        MessageDialogWithToggle openYesNoQuestion =
                MessageDialogWithToggle
                        .openYesNoQuestion(Display.getDefault()
                                .getActiveShell(),
                                Messages.RemoveXSDNotationProfile_filesAffected_saveTitle,
                                Messages.RemoveXSDNotationProfile_filesAffected_infoMessage
                                        + " " //$NON-NLS-1$
                                        + modelProfilesMap.size() + " file(s)", //$NON-NLS-1$
                                Messages.RemoveXSDNotationProfile_filesAffected_saveMessage,
                                true,
                                null,
                                null);
        if (openYesNoQuestion.getToggleState()) {

            for (IFile bomFile : bomFiles) {

                if (IDialogConstants.YES_ID == openYesNoQuestion
                        .getReturnCode()) {
                    WorkingCopy bomWC =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(bomFile);

                    if (null != bomWC) {
                        try {
                            bomWC.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * iterates thru the hash map of models and profiles and unapplies the
     * associated profile for each model
     * 
     * @param modelProfilesMap
     * @param editingDomain
     * @return
     */
    private Command getUnapplyXSDProfileCommand(
            final Map<Model, Profile> modelProfilesMap,
            TransactionalEditingDomain editingDomain) {

        if (!modelProfilesMap.isEmpty()) {
            Command cmd =
                    new RecordingCommand(editingDomain,
                            Messages.RemoveXSDNotationProfile_ConvertBOM_title) {

                        @Override
                        protected void doExecute() {
                            for (Model model : modelProfilesMap.keySet()) {
                                model.unapplyProfile(modelProfilesMap
                                        .get(model));
                            }
                        }
                    };
            return cmd;
        }
        return null;
    }

    /**
     * iterates thru the selected bom files and returns the hash map of bom
     * model and its associated xsd notation profile
     * 
     * @param bomFiles
     * 
     */
    private Map<Model, Profile> getModelsAndProfiles(List<IFile> bomFiles) {

        Map<Model, Profile> modelProfileMap = new HashMap<Model, Profile>();

        for (IFile bomFile : bomFiles) {
            WorkingCopy bomWC =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);

            if (null != bomWC) {
                EObject rootElement = bomWC.getRootElement();

                if (rootElement instanceof Model) {
                    Model model = (Model) rootElement;

                    Profile xsdProfile = BOMProfileUtils.getXSDProfile(model);

                    if (null != xsdProfile) {
                        modelProfileMap.put(model, xsdProfile);
                    }
                }
            }
        }

        return modelProfileMap;
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param action
     * @param selection
     */
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        bomFiles = new ArrayList<IFile>();

        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;

            if (structured.size() > 0) {
                for (Iterator<Object> iterator = structured.iterator(); iterator
                        .hasNext();) {
                    Object item = iterator.next();
                    if (item instanceof IFile) {
                        IFile iFile = (IFile) item;

                        if (iFile.getName().endsWith(".bom")) { //$NON-NLS-1$
                            bomFiles.add(iFile);
                        }
                    }
                }
            }
        }

    }

    /**
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     * 
     * @param action
     * @param targetPart
     */
    @Override
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // do nothing
    }

}
