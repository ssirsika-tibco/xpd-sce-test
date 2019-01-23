/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.ValidationControl;
import com.tibco.xpd.xpdExtension.ValidationIssueOverride;
import com.tibco.xpd.xpdExtension.ValidationIssueOverrideType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utilities for the xpdl2:Process/xpdExt:ValidationControl (issue overrides
 * etc) properties.
 * 
 * @author aallway
 * @since 17 Jul 2012
 */
public class ValidationControlUtil {

    /**
     * @param validationControlContainer
     * @return The xpdExt:ValidationControl element for the given element or
     *         <code>null</code> if not set.
     */
    public static ValidationControl getValidationControl(
            OtherElementsContainer validationControlContainer) {
        if (validationControlContainer != null) {
            return (ValidationControl) Xpdl2ModelUtil
                    .getOtherElement(validationControlContainer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ValidationControl());
        }
        return null;
    }

    /**
     * Get the {@link ValidationControl} element for the given element. If it
     * does not exist create it and append command to add it to the container to
     * the given command.
     * 
     * @param validationControlContainer
     * @param editingDomain
     * @param cmd
     * 
     @return The xpdExt:ValidationControl element for the given element or
     *         <code>null</code> if not set.
     */
    public static ValidationControl getOrCreateValidationControl(
            OtherElementsContainer validationControlContainer,
            EditingDomain editingDomain, CompoundCommand cmd) {

        if (validationControlContainer != null) {
            ValidationControl validationControl =
                    getValidationControl(validationControlContainer);

            if (validationControl == null) {
                validationControl =
                        XpdExtensionFactory.eINSTANCE.createValidationControl();

                cmd.append(Xpdl2ModelUtil
                        .getAddOtherElementCommand(editingDomain,
                                validationControlContainer,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ValidationControl(),
                                validationControl));
            }
            return validationControl;
        }
        return null;
    }

    /**
     * @param validationControlContainer
     * @param validationIssueId
     * @return The validation issue override for the give element and issue-id
     *         or <code>null</code> if none set.
     */
    public static ValidationIssueOverride getValidationIssueOverride(
            OtherElementsContainer validationControlContainer,
            String validationIssueId) {
        if (validationControlContainer != null && validationIssueId != null) {
            ValidationControl validationControl =
                    getValidationControl(validationControlContainer);

            if (validationControl != null) {
                for (ValidationIssueOverride issueOverride : validationControl
                        .getValidationIssueOverrides()) {
                    if (validationIssueId.equals(issueOverride
                            .getValidationIssueId())) {
                        return issueOverride;
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param validationControlContainer
     * @param validationIssueId
     * @return The validation issue override type for the give element and
     *         issue-id or <code>null</code> if none set.
     */
    public static ValidationIssueOverrideType getValidationIssueOverrideType(
            OtherElementsContainer validationControlContainer,
            String validationIssueId) {
        ValidationIssueOverride issueOverride =
                getValidationIssueOverride(validationControlContainer,
                        validationIssueId);

        if (issueOverride != null) {
            return issueOverride.getOverrideType();
        }

        return null;
    }

    /**
     * @param editingDomain
     * @param container
     * @param issueId
     * @param overrideType
     *            Override type or <code>null</code> to remove.
     * 
     * @return Command to set the validation issue override type to the given
     *         type for the given process/activity container etc
     */
    public static Command getSetValidationIssueOverrideCommand(
            EditingDomain editingDomain, OtherElementsContainer container,
            String issueId, ValidationIssueOverrideType overrideType) {
        CompoundCommand cmd = new CompoundCommand();

        /* Set to null = remove. */
        if (overrideType == null) {
            cmd.append(getRemoveValidationIssueOverrideCommand(editingDomain,
                    container,
                    issueId));

        } else {

            /*
             * If there is already an override for this issue then change it.
             */
            ValidationIssueOverride issueOverride =
                    ValidationControlUtil.getValidationIssueOverride(container,
                            issueId);

            if (issueOverride != null) {
                cmd.append(SetCommand.create(editingDomain,
                        issueOverride,
                        XpdExtensionPackage.eINSTANCE
                                .getValidationIssueOverride_OverrideType(),
                        overrideType));
            } else {
                /*
                 * Otherwise add a new entry (and ValidationControl element if
                 * necessary.
                 */
                ValidationControl validationControl =
                        ValidationControlUtil
                                .getOrCreateValidationControl(container,
                                        editingDomain,
                                        cmd);

                issueOverride =
                        XpdExtensionFactory.eINSTANCE
                                .createValidationIssueOverride();
                issueOverride.setValidationIssueId(issueId);
                issueOverride.setOverrideType(overrideType);

                cmd.append(AddCommand
                        .create(editingDomain,
                                validationControl,
                                XpdExtensionPackage.eINSTANCE
                                        .getValidationControl_ValidationIssueOverrides(),
                                issueOverride));
            }
        }

        return cmd;
    }

    /**
     * @param editingDomain
     * @param container
     * @param issueId
     * 
     * @return Command to remove the issue override or the entire
     *         ValidationControl element if it is the last one.
     */
    public static Command getRemoveValidationIssueOverrideCommand(
            EditingDomain editingDomain, OtherElementsContainer container,
            String issueId) {

        CompoundCommand cmd = new CompoundCommand();

        ValidationIssueOverride issueOverride =
                getValidationIssueOverride(container, issueId);

        if (issueOverride != null) {
            cmd.append(RemoveCommand.create(editingDomain,
                    getValidationControl(container),
                    XpdExtensionPackage.eINSTANCE
                            .getValidationControl_ValidationIssueOverrides(),
                    issueOverride));
        }

        return cmd;
    }

}
