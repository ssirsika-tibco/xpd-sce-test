/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.validator.resolution;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.om.validator.Activator;
import com.tibco.xpd.om.validator.internal.Messages;
import com.tibco.xpd.om.validator.rules.UniqueGroupNameRule;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;

/**
 * Quick-fix resolution for the unique group name/display name for DE
 * deployment. The Group name/display name has to be unique across the Model.
 * 
 * @author njpatel
 * 
 */
public class SetUniqueGroupNameResolution extends AbstractWorkingCopyResolution {

    private Set<String> names;
    private String issueId;

    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        if (target instanceof Group) {
            try {
                issueId = (String) marker.getAttribute(IIssue.ID);

                OrgModel model = OMUtil.getModel(target);
                if (model != null) {
                    Collection<Group> groups = OMUtil.getAllGroups(model);
                    names = getNames(groups);
                }
                final String uniqueName = getUniqueName(names);
                final String result[] = new String[] { null };

                final Display display = XpdResourcesPlugin.getStandardDisplay();
                display.syncExec(new Runnable() {

                    public void run() {
                        String title;
                        String message;
                        if (UniqueGroupNameRule.DISPLAYNAME_ISSUE
                                .equals(issueId)) {
                            title = Messages.SetUniqueGroupNameResolution_renameGroupLabel_title;
                            message = Messages.SetUniqueGroupNameResolution_renameGroupLabel_message;
                        } else {
                            title = Messages.SetUniqueGroupNameResolution_renameGroupName_title;
                            message = Messages.SetUniqueGroupNameResolution_renameGroupName_message;
                        }

                        InputDialog dlg = new InputDialog(display
                                .getActiveShell(), title, message, uniqueName,
                                new IInputValidator() {

                                    public String isValid(String newText) {
                                        return doValidate(newText);
                                    }
                                });

                        if (dlg.open() == InputDialog.OK) {
                            result[0] = dlg.getValue();
                        }
                    }
                });

                if (result[0] != null) {
                    return SetCommand.create(editingDomain, target,
                            getFeatureToSet(), result[0]);
                }
            } catch (CoreException e) {
                Activator
                        .getDefault()
                        .getLogger()
                        .error(
                                e,
                                Messages.SetUniqueGroupNameResolution_NoIssueId_error_message);
            }
        }
        return null;
    }

    /**
     * Get the feature of the {@link Group} to set.
     * 
     * @return Name or Display Name feature.
     */
    protected EStructuralFeature getFeatureToSet() {
        if (UniqueGroupNameRule.DISPLAYNAME_ISSUE.equals(issueId)) {
            return OMPackage.eINSTANCE.getNamedElement_DisplayName();
        }
        return OMPackage.eINSTANCE.getNamedElement_Name();
    }

    /**
     * Get the names of the group as a collection.
     * 
     * @param groups
     * @return
     */
    protected Set<String> getNames(Collection<Group> groups) {
        Set<String> names = new HashSet<String>();

        if (groups != null) {
            for (Group group : groups) {
                names.add(getNameToCheck(group));
            }
        }

        return names;
    }

    /**
     * Get the Name or Display Name of the group to check.
     * 
     * @param group
     * @return
     */
    protected String getNameToCheck(Group group) {
        if (UniqueGroupNameRule.DISPLAYNAME_ISSUE.equals(issueId)) {
            return group.getDisplayName();
        }
        return group.getName();
    }

    /**
     * Check if the name/display name is unique.
     * 
     * @param names
     * @param name
     * @return
     */
    private boolean isNameUnique(Set<String> names, String name) {
        for (String theName : names) {
            if (theName.equals(name)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validate the input in the rename dialog.
     * 
     * @return
     */
    private String doValidate(String value) {
        // Check if the name is unique and if not then raise error
        String msg = null;
        if (value != null && value.length() > 0) {
            // If this is a name then it has to have valid characters
            if (UniqueGroupNameRule.NAME_ISSUE.equals(issueId)) {
                String name = NameUtil.getInternalName(value, false);
                if (!name.equals(value)) {
                    // Value was invalid
                    return Messages.SetUniqueGroupNameResolution_nameCanOnlyContainAlphaNumeric_error_message;
                }
            }
            if (!isNameUnique(names, value)) {
                if (UniqueGroupNameRule.DISPLAYNAME_ISSUE.equals(issueId)) {
                    msg = Messages.SetUniqueGroupNameResolution_labelAlreadyExists_error_message;
                } else {
                    msg = Messages.SetUniqueGroupNameResolution_nameAlreadyExists_error_message;
                }
            }
        } else {
            if (UniqueGroupNameRule.DISPLAYNAME_ISSUE.equals(issueId)) {
                msg = Messages.SetUniqueGroupNameResolution_blankLabel_error_message;
            } else {
                msg = Messages.SetUniqueGroupNameResolution_blankName_error_message;
            }
        }
        return msg;
    }

    /**
     * Get a unique name/display name.
     * 
     * @param names
     * @return
     */
    private String getUniqueName(Set<String> names) {
        String prefix = Messages.SetUniqueGroupNameResolution_group_prefix_label;
        String name = null;
        boolean unique = false;
        int idx = 1;

        while (!unique) {
            name = prefix + idx++;
            unique = !names.contains(name);
        }

        return name;
    }
}
