/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.profiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.ProjectsFilter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Util class to help in applying stereotypes.
 * 
 * @author njpatel
 * 
 */
public final class StereotypeUtil {

    /**
     * Show the user a {@link Stereotype} picker to allow them to select the
     * stereotypes to apply to the {@link Element}. The user will be asked if
     * any new {@link Profile Profiles} need to be applied to apply the
     * Stereotypes, if the user disagrees then <code>null</code> will be
     * returned.
     * 
     * @param shell
     * @param element
     * @return List of Stereotypes to apply, empty list if all applied
     *         stereotypes should be cleared and <code>null</code> if no action
     *         should be taken.
     */
    public static EList<Stereotype> getStereotypesFromPicker(Shell shell,
            Element element) {
        EList<Stereotype> types = null;
        if (element != null) {
            IFile file = WorkingCopyUtil.getFile(element);
            if (file != null) {
                EList<Stereotype> appliedStereotypes =
                        new BasicEList<Stereotype>();

                for (Stereotype type : element.getAppliedStereotypes()) {
                    if (type.eResource() != null
                            && type.eResource().getURI().isPlatformResource()) {
                        appliedStereotypes.add(type);
                    }
                }
                element.getAppliedStereotypes();

                PickerTypeQuery[] queries =
                        new PickerTypeQuery[] { new BOMTypeQuery(element
                                .eClass(), BOMTypeQuery.STEREOTYPE_TYPE) };
                IFilter[] filters =
                        new IFilter[] { new ProjectsFilter(file.getProject()) };
                Object[] result =
                        PickerService.getInstance()
                                .openMultiPickerDialog(shell,
                                        queries,
                                        null,
                                        null,
                                        null,
                                        filters,
                                        appliedStereotypes.toArray());

                if (result != null) {
                    types = new BasicEList<Stereotype>();
                    for (Object res : result) {
                        if (res instanceof Stereotype) {
                            types.add((Stereotype) res);
                        }
                    }

                    if (!types.isEmpty()) {
                        // From the selection determine which stereotypes will
                        // be added and removed
                        EList<Stereotype> toAdd =
                                getStereotypesToAdd(appliedStereotypes, types);
                        EList<Stereotype> toRemove =
                                getStereotypesToRemove(appliedStereotypes,
                                        types);
                        /*
                         * Verify that all attributes of the stereotypes to add
                         * have a type set. If they don't and the stereotype is
                         * applied it will break the bom model.
                         */
                        List<IStatus> status = new ArrayList<IStatus>();
                        Set<Stereotype> errTypes = new HashSet<Stereotype>();
                        for (Stereotype type : toAdd) {
                            EList<Property> attributes =
                                    type.getAllAttributes();
                            if (attributes != null) {
                                for (Property property : attributes) {
                                    if (property.getType() == null) {
                                        errTypes.add(type);
                                        status
                                                .add(new Status(
                                                        IStatus.ERROR,
                                                        Activator.PLUGIN_ID,
                                                        String
                                                                .format(Messages.StereotypeUtil_propertyHasNoType_shortdesc,
                                                                        property
                                                                                .getLabel(),
                                                                        type
                                                                                .getQualifiedName())));
                                    }
                                }
                            }
                        }

                        if (!status.isEmpty() && !errTypes.isEmpty()) {

                            String msg = "\n"; //$NON-NLS-1$

                            for (Stereotype type : errTypes) {
                                if (type.getProfile() != null) {
                                    msg += String.format("\n    - %1$s (%2$s)", //$NON-NLS-1$
                                            type.getLabel(),
                                            type.getProfile().getLabel());
                                } else {
                                    msg += String.format("\n    - %1$s", type //$NON-NLS-1$
                                            .getLabel());
                                }
                            }
                            msg += "\n\n"; //$NON-NLS-1$

                            /*
                             * Check whether the user should be given a chance
                             * to contiue - this will be incase there are other
                             * valid stereotypes that need to be added or
                             * removed
                             */
                            boolean showContinue =
                                    toAdd.size() != errTypes.size()
                                            || !toRemove.isEmpty();
                            String message =
                                    showContinue ? String
                                            .format(Messages.StereotypeUtil_stereotypesCannotBeApplied_errorWithOptionToContinue_longdesc,
                                                    msg)
                                            : toAdd.size() > 1 ? Messages.StereotypeUtil_multipleStereotypesCannotBeApplied_error_longdesc
                                                    : Messages.StereotypeUtil_singleStereotypeCannotBeApplied_error_longdesc;

                            ErrorDlg dlg =
                                    new ErrorDlg(
                                            shell,
                                            Messages.StereotypeUtil_stereotype_errorDialog_title,
                                            new MultiStatus(
                                                    Activator.PLUGIN_ID,
                                                    IStatus.ERROR,
                                                    status
                                                            .toArray(new IStatus[status
                                                                    .size()]),
                                                    message, null),
                                            showContinue);

                            if (dlg.open() == ErrorDlg.OK) {
                                types.removeAll(errTypes);
                            } else {
                                types = null;
                            }
                        }

                        if (types != null) {
                            EList<Profile> profiles =
                                    getNewProfilesToApply(element, types);
                            if (!profiles.isEmpty()) {
                                boolean reply =
                                        askUserIfProfilesCanBeApplied(shell,
                                                profiles);
                                if (!reply) {
                                    // User did not agree to apply profiles so
                                    // return null
                                    types = null;
                                }
                            }
                        }
                    }
                }
            }
        }
        return types;
    }

    /**
     * Get the {@link Command} to set the {@link Stereotype Stereotypes} on an
     * {@link Element}. Note that this will apply any {@link Profile Profiles}
     * required to the container {@link Package}.
     * 
     * @param editingDomain
     *            transactional editing domain
     * @param element
     *            element to set stereotypes on
     * @param stereotypes
     *            stereotypes to apply, empty or <code>null</code> if applied
     *            stereotypes should be cleared.
     * @return Command
     * @throws CoreException
     */
    public static Command getSetStereotypesCommand(
            TransactionalEditingDomain editingDomain, Element element,
            Collection<Stereotype> stereotypes) throws CoreException {
        Command cmd = null;

        if (editingDomain != null && element != null && !element.eIsProxy()) {
            Set<Profile> profiles = new HashSet<Profile>();
            for (Stereotype stereotype : stereotypes) {
                profiles.add(stereotype.getProfile());
            }
            List<Profile> changedProfiles = new ArrayList<Profile>();
            EList<Profile> allProfiles =
                    element.getNearestPackage().getAllAppliedProfiles();
            for (Profile profile : profiles) {
                if (allProfiles.contains(profile)) {
                    ProfileApplication application =
                            element.getNearestPackage()
                                    .getProfileApplication(profile);
                    if (application != null) {
                        EPackage def = application.getAppliedDefinition();
                        if (def == null || def.eIsProxy()) {
                            changedProfiles.add(profile);
                        }
                    }
                }
            }

            if (!changedProfiles.isEmpty()) {
                if (changedProfiles.size() == 1) {
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String
                                            .format(Messages.StereotypeUtil_profileMismatch_error_message,
                                                    changedProfiles.get(0)
                                                            .getName())));
                } else {
                    StringBuffer profileNames = new StringBuffer();
                    for (Profile profile : profiles) {
                        if (profileNames.length() > 0) {
                            profileNames.append(", "); //$NON-NLS-1$
                        }
                        profileNames.append(profile.getName());
                    }
                    throw new CoreException(
                            new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String
                                            .format(Messages.StereotypeUtil_profilesMismatch_error_message,
                                                    profileNames)));
                }
            }

            cmd =
                    new SetStereotypeCommand(
                            editingDomain,
                            element,
                            getStereotypesToAdd(element.getAppliedStereotypes(),
                                    stereotypes),
                            getStereotypesToRemove(element
                                    .getAppliedStereotypes(), stereotypes));
        }

        return cmd;
    }

    /**
     * Get all {@link Profile Profiles} that will have to be applied to the
     * {@link Package} containing the {@link Element} given if this
     * {@link Stereotype Stereotypes} need to be applied to the
     * <code>Element</code>.
     * 
     * @param element
     *            Element to apply stereotypes to
     * @param newStereotypesToApply
     *            the stereotypes to apply
     * @return list of any Profiles that will have to be applied to the
     *         container package, empty list if none.
     */
    private static EList<Profile> getNewProfilesToApply(Element element,
            Collection<Stereotype> newStereotypesToApply) {
        EList<Profile> profiles = new BasicEList<Profile>();

        if (element != null && newStereotypesToApply != null
                && !newStereotypesToApply.isEmpty()) {
            Package pkg = element.getNearestPackage();
            if (pkg != null) {
                EList<Stereotype> appliedStereotypes =
                        element.getAppliedStereotypes();

                for (Stereotype type : newStereotypesToApply) {
                    if (!appliedStereotypes.contains(type)) {
                        Profile profile = type.getProfile();
                        EList<Profile> appliedProfiles =
                                pkg.getAllAppliedProfiles();
                        if (profile != null
                                && !appliedProfiles.contains(profile)) {
                            if (!profiles.contains(profile)) {
                                profiles.add(profile);
                            }
                        }
                    }
                }
            }
        }
        return profiles;
    }

    /**
     * Ask the user if the given {@link Profile Profiles} can be applied to the
     * model.
     * 
     * @param shell
     * @param profiles
     * @return <code>true</code> if the user agreed, <code>false</code>
     *         otherwise.
     */
    private static boolean askUserIfProfilesCanBeApplied(Shell shell,
            Collection<Profile> profiles) {
        String msg;
        if (profiles.size() == 1) {
            msg =
                    String
                            .format(Messages.StereotypeUtil_singleProfileRequired_message,
                                    profiles.iterator().next()
                                            .getQualifiedName());
        } else {
            msg =
                    String
                            .format(Messages.StereotypeUtil_multiProfilesRequired_message);
            msg += "\n"; //$NON-NLS-1$
            for (Profile profile : profiles) {
                msg += "\n    - " + profile.getQualifiedName(); //$NON-NLS-1$
            }
        }

        return MessageDialog.openQuestion(shell,
                Messages.StereotypeUtil_profileRequired_dialog_title,
                msg);
    }

    /**
     * Get a list of Stereotypes that need to be applied that have not already
     * been applied in the given alreadyApplied list.
     * 
     * @param alreadyApplied
     *            stereotypes already applied to the element
     * @param toApply
     *            stereotypes that need to be applied to the element
     * @return list of stereotypes that need to be applied from the toApply list
     *         that haven't been already applied, empty if all have already been
     *         applied
     */
    private static EList<Stereotype> getStereotypesToAdd(
            Collection<Stereotype> alreadyApplied,
            Collection<Stereotype> toApply) {
        EList<Stereotype> toAdd = new BasicEList<Stereotype>();

        if (toApply != null && !toApply.isEmpty()) {
            if (alreadyApplied != null && !alreadyApplied.isEmpty()) {
                for (Stereotype type : toApply) {
                    if (!alreadyApplied.contains(type) && !toAdd.contains(type)
                            && !type.eIsProxy()) {
                        toAdd.add(type);
                    }
                }
            } else {
                // Need to apply all the new stereotypes
                toAdd.addAll(toApply);
            }
        }
        return toAdd;
    }

    /**
     * Get a list of Stereotypes that need to be removed as they are not
     * included in the new list of stereotypes to apply.
     * 
     * @param alreadyApplied
     *            stereotypes already applied to the element
     * @param toApply
     *            stereotypes that need to be applied to the element, empty if
     *            all stereotypes should be removed.
     * @return list of stereotypes that need to be removed as they have not been
     *         re-select to be applied, empty list if no existing stereotypes
     *         should be removed.
     */
    private static EList<Stereotype> getStereotypesToRemove(
            Collection<Stereotype> alreadyApplied,
            Collection<Stereotype> toApply) {
        EList<Stereotype> toRemove = new BasicEList<Stereotype>();

        // If the list to apply is null or empty then remove all already applied
        // stereotypes
        if (toApply == null || toApply.isEmpty()) {
            toRemove.addAll(alreadyApplied);
        } else {
            for (Stereotype type : alreadyApplied) {
                // Ignore internal stereotypes
                if (type.eResource() != null
                        && type.eResource().getURI().isPlatformResource()) {
                    if (!toApply.contains(type) && !toRemove.contains(type)) {
                        toRemove.add(type);
                    }
                }
            }
        }

        return toRemove;
    }

    /**
     * Command to set the given stereotypes on an element.
     * 
     * @author njpatel
     * 
     */
    private static class SetStereotypeCommand extends RecordingCommand {

        private final Element element;

        private final EList<Stereotype> stereotypesToAdd;

        private final EList<Stereotype> stereotypesToRemove;

        public SetStereotypeCommand(TransactionalEditingDomain domain,
                Element element, EList<Stereotype> toAdd,
                EList<Stereotype> toRemove) {
            super(domain);
            this.element = element;
            stereotypesToAdd = toAdd;
            stereotypesToRemove = toRemove;
            setLabel(Messages.StereotypeUtil_setStereotypes_command_label);
        }

        @Override
        protected boolean prepare() {
            if (element != null && !element.eIsProxy()) {
                if (!stereotypesToAdd.isEmpty()
                        || !stereotypesToRemove.isEmpty()) {
                    // There is something to do so command can go ahead
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void doExecute() {
            if (element != null
                    && !(stereotypesToAdd.isEmpty() && stereotypesToRemove
                            .isEmpty())) {
                // Remove stereotypes
                for (Stereotype type : stereotypesToRemove) {
                    element.unapplyStereotype(type);
                }
                // Add stereotypes
                for (Stereotype type : stereotypesToAdd) {
                    UMLUtil.safeApplyStereotype(element, type);
                }
            }
        }
    }

    /**
     * Error dialog with a Yes, No and Details buttons.
     * 
     * @author njpatel
     * 
     */
    private static class ErrorDlg extends ErrorDialog {

        private final boolean showContinue;

        /**
         * Error dialog with continue option.
         * 
         * @param parentShell
         * @param dialogTitle
         * @param status
         * @param showContinue
         *            <code>true</code> if option to continue should be shown,
         *            <code>false</code> otherwise.
         */
        public ErrorDlg(Shell parentShell, String dialogTitle, IStatus status,
                boolean showContinue) {
            super(parentShell, dialogTitle, null, status, IStatus.ERROR);
            this.showContinue = showContinue;
        }

        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            if (showContinue) {
                // create Yes, No and Details buttons
                createButton(parent,
                        IDialogConstants.OK_ID,
                        IDialogConstants.YES_LABEL,
                        true);
                createButton(parent,
                        IDialogConstants.CANCEL_ID,
                        IDialogConstants.NO_LABEL,
                        false);
                createDetailsButton(parent);
            } else {
                super.createButtonsForButtonBar(parent);
            }
        }
    }
}
