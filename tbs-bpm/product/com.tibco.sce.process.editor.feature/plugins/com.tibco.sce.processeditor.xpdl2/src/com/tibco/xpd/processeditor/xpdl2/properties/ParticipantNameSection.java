/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.ui.internal.Messages;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author NWilson
 * 
 */
public class ParticipantNameSection extends NamedElementSection {

    @Override
    protected String getDuplicateNameMessage(EObject duplicate) {
        String errorStr = null;
        if (duplicate != null) {
            if (duplicate.eContainer() instanceof Package) {
                errorStr =
                        Messages.ParticipantPropertySection_DuplicateInPackage_longdesc
                                + ((Package) duplicate.eContainer()).getName();
            } else if (duplicate.eContainer() instanceof Process) {
                errorStr =
                        Messages.ParticipantPropertySection_DuplicateInProcess_longdesc
                                + ((Process) duplicate.eContainer()).getName();
            } else if (duplicate.eContainer() instanceof Activity) {
                errorStr =
                        Messages.ParticipantPropertySection_DuplicateInEmbeddedSubProcess_longdesc
                                + ((Activity) duplicate.eContainer()).getName();
            }
        }
        return errorStr;
    }

    @Override
    protected void verifyName(String nameText) {
        err = checkDuplicateName(nameText);
        nameValid = err == null;
    }

    @Override
    protected boolean updateNameFromDisplayName(String text) {
        boolean changed = false;
        if (name.getText().equals(display.getText())) {
            ignoreTextChanges = true;
            name.setText(text);
            ignoreTextChanges = false;
            verifyName(text);
            changed = true;
        }
        return changed;
    }

    /**
     * Check for duplicate name of type declaration entered in name text and
     * take appropriate action.
     * 
     */
    protected String checkDuplicateName(String nameText) {
        String errorStr = checkForDuplicateOrEmptyName(nameText);

        // If we are in a new type declaration wizard then this will prevent
        // Finish being performed if we currently have duplicate name.
        setCanFinish((errorStr == null), errorStr);

        if (errorStr != null) {
            nameLabel.setImage(Xpdl2UiPlugin.getDefault().getImageRegistry()
                    .get(Xpdl2UiPlugin.IMG_ERROR));
            nameLabel.setToolTipText(errorStr);

        } else {
            nameLabel.setImage(null);
            nameLabel.setToolTipText(""); //$NON-NLS-1$

        }
        return errorStr;
    }

    /**
     * Check for duplicate name of type declaration entered in name text and
     * take appropriate action.
     * 
     * @return null if not duplicate/empty else a string containing appropriate
     *         error message.
     */
    private String checkForDuplicateOrEmptyName(String newName) {
        String errorStr = null;

        Participant inputParticipant = (Participant) getInput();
        if (inputParticipant != null) {

            if (newName != null && newName.length() > 0) {
                if (!newName.equals(newName.trim())) {
                    errorStr =
                            Messages.ParticipantPropertySection_LeadSpaceNotAllowed_longdesc;

                } else {
                    // Check for duplicate name and Update state of
                    // Finish button etc
                    EObject container = getInputContainer();

                    EObject duplicate =
                            Xpdl2ModelUtil.getDuplicateParticipant(container,
                                    inputParticipant,
                                    newName);

                    // MR 38533 - begin
                    if (null == duplicate) {
                        duplicate =
                                Xpdl2ModelUtil
                                        .getDuplicateFieldOrParam(container,
                                                null,
                                                newName);
                    }
                    // MR 38533 - end

                    if (duplicate != null) {
                        if (duplicate.eContainer() instanceof Package) {
                            errorStr =
                                    Messages.ParticipantPropertySection_DuplicateInPackage_longdesc
                                            + ((Package) duplicate.eContainer())
                                                    .getName();
                        } else if (duplicate.eContainer() instanceof Process) {
                            errorStr =
                                    Messages.ParticipantPropertySection_DuplicateInProcess_longdesc
                                            + ((Process) duplicate.eContainer())
                                                    .getName();
                            // MR 39410 - begin
                        } else if (duplicate.eContainer() instanceof Activity) {
                            errorStr =
                                    Messages.ParticipantPropertySection_DuplicateInEmbeddedSubProcess_longdesc
                                            + ((Activity) duplicate
                                                    .eContainer()).getName();
                        }
                        // MR 39410 - end
                    }
                }
            } else {
                errorStr =
                        Messages.ParticipantPropertySection_NoEmptyName_longdesc;
            }
        }

        return errorStr;
    }

}
