/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.profiles;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.NameColumn;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.ui.commonpicker.BOMTypeQuery;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.ProjectsFilter;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * BOM {@link Profile}s tab section.
 * 
 * @author njpatel
 * 
 */
public class ProfileSection extends AbstractTableSection {

    /**
     * {@link Profile}s section.
     */
    public ProfileSection() {
        super(Messages.ProfileSection_section_title, null);
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        NameColumn column =
                new NameColumn(getEditingDomain(), viewer,
                        Messages.ProfileSection_appliedProfiles_column_title,
                        false);
        column.setShowQualifiedName(true);
        new LocationColumn(getEditingDomain(), viewer);
    }

    @Override
    protected boolean canMove() {
        // Cannot change the order of the profiles in the table
        return false;
    }

    @Override
    protected TableAddAction getAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer, Messages.ProfileSection_set_action,
                Messages.ProfileSection_set_action_tooltip) {
            @Override
            protected Object addRow(StructuredViewer viewer) {
                EObject input = getInput();
                if (input instanceof Package) {
                    IFile file = WorkingCopyUtil.getFile(input);
                    if (file != null) {
                        final IProject project = file.getProject();
                        EList<Profile> profiles =
                                ((Package) input).getAppliedProfiles();
                        PickerTypeQuery[] queries =
                                new PickerTypeQuery[] { new BOMTypeQuery(
                                        project, BOMTypeQuery.PROFILE_TYPE) };
                        IFilter[] filters =
                                new IFilter[] { new ProjectsFilter(project) };
                        Object[] selectedProfiles =
                                PickerService.getInstance()
                                        .openMultiPickerDialog(getShell(),
                                                queries,
                                                null,
                                                null,
                                                null,
                                                filters,
                                                profiles.toArray());
                        if (selectedProfiles != null) {
                            Set<Profile> profilesToSet = new HashSet<Profile>();
                            for (Object obj : selectedProfiles) {
                                if (obj instanceof Profile) {
                                    profilesToSet.add((Profile) obj);
                                }
                            }
                            getEditingDomain()
                                    .getCommandStack()
                                    .execute(new SetProfilesCommand(
                                            (TransactionalEditingDomain) getEditingDomain(),
                                            (Package) input, profilesToSet));
                        }
                    }
                }
                return null;
            }
        };
    }

    @Override
    protected TableDeleteAction getDeleteAction(ColumnViewer viewer) {
        return new TableDeleteAction(viewer,
                Messages.ProfileSection_remove_action,
                Messages.ProfileSection_remove_action_tooltip) {
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = getInput();
                if (input instanceof Package && selection != null) {
                    Set<Profile> profiles = new HashSet<Profile>();
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof Profile) {
                            profiles.add((Profile) next);
                        }
                    }

                    if (!profiles.isEmpty()) {
                        getEditingDomain()
                                .getCommandStack()
                                .execute(new UnapplyProfilesCommand(
                                        (TransactionalEditingDomain) getEditingDomain(),
                                        (Package) input, profiles));
                    }
                }
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        EObject input = resollveInput(toTest);

        if (input instanceof Package) {
            Model model = ((Package) input).getModel();
            // Do not show section for first-class profile models
            return model != null
                    && !FirstClassProfileManager.getInstance()
                            .isFirstClassProfileApplied(model);
        }

        return false;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Package) {
            EList<Profile> appliedProfiles =
                    ((Package) input).getAppliedProfiles();
            EList<Profile> profiles = new BasicEList<Profile>();
            // Filter out the BOM base primitive type facet profile
            for (Profile profile : appliedProfiles) {
                if (profile.eResource() != null) {
                    URI uri = profile.eResource().getURI();
                    // Only include user-defined profiles (in the workspace)
                    if (uri != null && uri.isPlatformResource()) {
                        profiles.add(profile);
                    }
                } else {
                    // This profile will be marked as unreferenced as it's
                    // resource does not exist
                    profiles.add(profile);
                }
            }

            setTableInput(profiles);
        }
    }

    @Override
    protected IContentProvider getContentProvider() {
        return new ArrayContentProvider();
    }

    /**
     * Command to set profiles of a package. Any profiles already applied to the
     * package that does not exist in the new set of profiles will be removed.
     * 
     * @author njpatel
     * 
     */
    private class SetProfilesCommand extends RecordingCommand {

        private final Package pkg;

        private final Collection<Profile> profiles;

        private final Set<Profile> profilesToRemove;

        private final Set<Profile> profilesToAdd;

        public SetProfilesCommand(TransactionalEditingDomain domain,
                Package pkg, Collection<Profile> profilesToSet) {
            super(domain);
            this.pkg = pkg;
            this.profiles = profilesToSet;
            profilesToRemove = new HashSet<Profile>();
            profilesToAdd = new HashSet<Profile>();
            setLabel(Messages.ProfileSection_setProfiles_command_label);
        }

        @Override
        public boolean prepare() {
            if (pkg != null && !pkg.eIsProxy() && profiles != null) {
                for (Profile profile : profiles) {
                    if (profile.eIsProxy()) {
                        return false;
                    }
                }

                EList<Profile> appliedProfiles = pkg.getAppliedProfiles();

                if (profiles.isEmpty()) {
                    // Remove all applied profiles
                    profilesToRemove.addAll(appliedProfiles);
                } else {
                    /*
                     * Calculate which profiles need to be removed or added to
                     * the package
                     */
                    if (appliedProfiles.isEmpty()) {
                        profilesToAdd.addAll(profiles);
                    } else {
                        EList<Profile> temp =
                                new BasicEList<Profile>(appliedProfiles);

                        for (Profile profile : profiles) {
                            if (!temp.contains(profile)) {
                                profilesToAdd.add(profile);
                            } else {
                                temp.remove(profile);
                            }
                        }

                        if (!temp.isEmpty()) {
                            /*
                             * All profiles left in the applied list are to be
                             * removed (ignore first-class profiles - this will
                             * have pathmap uris and so won't be platform uris)
                             */
                            for (Profile profile : temp) {
                                URI uri = profile.eResource().getURI();
                                if (uri != null && uri.isPlatform()) {
                                    profilesToRemove.add(profile);
                                }
                            }
                        }
                    }
                }

                return !profilesToAdd.isEmpty() || !profilesToRemove.isEmpty();
            }
            return false;
        }

        @Override
        protected void doExecute() {
            if (pkg != null) {
                for (Profile profile : profilesToAdd) {
                    pkg.applyProfile(profile);
                }
                for (Profile profile : profilesToRemove) {
                    pkg.unapplyProfile(profile);
                }
            }
        }

    }

    /**
     * Command to apply profiles to a package.
     * 
     * @author njpatel
     * 
     */
    private class UnapplyProfilesCommand extends RecordingCommand {

        private final Package pkg;

        private final Collection<Profile> profiles;

        public UnapplyProfilesCommand(TransactionalEditingDomain domain,
                Package pkg, Collection<Profile> profiles) {
            super(domain);
            this.pkg = pkg;
            this.profiles = profiles;
            setLabel(Messages.ProfileSection_removeProfiles_command_label);
        }

        @Override
        protected boolean prepare() {
            if (pkg != null && !pkg.eIsProxy() && profiles != null
                    && !profiles.isEmpty()) {
                for (Profile profile : profiles) {
                    if (!pkg.isProfileApplied(profile)) {
                        return false;
                    }
                }

                return true;
            }
            return false;
        }

        @Override
        protected void doExecute() {
            if (pkg != null && profiles != null && !profiles.isEmpty()) {
                for (Profile profile : profiles) {
                    pkg.unapplyProfile(profile);
                }
            }
        }
    }

}
