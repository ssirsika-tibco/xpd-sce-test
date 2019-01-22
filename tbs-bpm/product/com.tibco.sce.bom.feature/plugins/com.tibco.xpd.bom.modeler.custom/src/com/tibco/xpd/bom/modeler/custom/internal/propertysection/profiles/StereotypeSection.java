/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.profiles;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractTableSection;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.columns.NameColumn;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMEditPartUtils;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.resources.ui.components.actions.TableAddAction;
import com.tibco.xpd.resources.ui.components.actions.TableDeleteAction;

/**
 * BOM properties' Stereotypes tab section.
 * 
 * @author njpatel
 * 
 */
public class StereotypeSection extends AbstractTableSection {

    /**
     * Stereotype section.
     */
    public StereotypeSection() {
        super(Messages.StereotypeSection_section_title, null);
    }

    @Override
    protected void addColumns(ColumnViewer viewer) {
        new NameColumn(getEditingDomain(), viewer,
                Messages.StereotypeSection_appliedStereotypes_column_title,
                false);
        new ProfileColumn(getEditingDomain(), viewer);
        new LocationColumn(getEditingDomain(), viewer);
    }

    @Override
    protected TableAddAction getAddAction(ColumnViewer viewer) {
        return new TableAddAction(viewer,
                Messages.StereotypeSection_add_action,
                Messages.StereotypeSection_add_action_tooltip) {
            @Override
            protected Object addRow(StructuredViewer viewer) {
                EObject input = getInput();
                if (input instanceof Element) {
                    EList<Stereotype> types =
                            StereotypeUtil.getStereotypesFromPicker(getShell(),
                                    (Element) input);
                    if (types != null) {
                        try {
                            getEditingDomain()
                                    .getCommandStack()
                                    .execute(StereotypeUtil
                                            .getSetStereotypesCommand((TransactionalEditingDomain) getEditingDomain(),
                                                    (Element) input,
                                                    types));
                        } catch (CoreException e) {
                            ErrorDialog
                                    .openError(getShell(),
                                            Messages.StereotypeSection_profileError_dialog_title,
                                            Messages.StereotypeSection_profileError_dialog_message,
                                            e.getStatus());
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
                Messages.StereotypeSection_remove_action,
                Messages.StereotypeSection_remove_action_tooltip) {
            @Override
            protected void deleteRows(IStructuredSelection selection) {
                EObject input = getInput();

                if (input instanceof Element && selection != null) {
                    Set<Stereotype> stereotypes = new HashSet<Stereotype>();
                    for (Iterator<?> iter = selection.iterator(); iter
                            .hasNext();) {
                        Object next = iter.next();
                        if (next instanceof Stereotype) {
                            stereotypes.add((Stereotype) next);
                        }
                    }

                    if (!stereotypes.isEmpty()) {
                        getEditingDomain().getCommandStack()
                                .execute(new RemoveStereotypesCommand(
                                        (Element) input, stereotypes));
                    }
                }
            }
        };
    }

    @Override
    protected boolean canMove() {
        // Cannot change the order of the stereotypes in the table
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        EObject obj = resollveInput(toTest);

        if (obj instanceof Element && !(obj instanceof ProfileApplication)) {
            Model model = ((Element) obj).getModel();
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

        if (input instanceof Element) {
            setTableInput(getStereotypes((Element) input));
        }
    }

    /**
     * Get all the Stereotypes applied to the given {@link Element}. All
     * internal Stereotypes will be ignored.
     * 
     * @param element
     * @return
     */
    private EList<Stereotype> getStereotypes(Element element) {
        EList<Stereotype> types = new BasicEList<Stereotype>();

        if (element != null) {
            for (Stereotype type : element.getAppliedStereotypes()) {
                if (type.eResource() != null
                        && type.eResource().getURI().isPlatformResource()) {
                    types.add(type);
                }
            }
        }

        return types;
    }

    @Override
    protected IContentProvider getContentProvider() {
        return new ArrayContentProvider();
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (notifications != null) {
            for (Notification notification : notifications) {
                if (BOMEditPartUtils.isStereotypeBeingSet(notification)) {
                    Object featureObjectAffected =
                            BOMEditPartUtils
                                    .getFeatureObjectAffected(notification);
                    if (getInput() == featureObjectAffected) {
                        return true;
                    }
                }
            }
        }
        return super.shouldRefresh(notifications);
    }

    /**
     * Profile column of the stereotypes table.
     * 
     * @author njpatel
     * 
     */
    private class ProfileColumn extends NameColumn {

        public ProfileColumn(EditingDomain editingDomain, ColumnViewer viewer) {
            super(editingDomain, viewer,
                    Messages.StereotypeSection_profile_column_title, false);
            setShowQualifiedName(true);
        }

        @Override
        protected String getText(Object element) {
            if (element instanceof Stereotype) {
                element = ((Stereotype) element).getProfile();
            }
            return super.getText(element);
        }

        @Override
        protected Image getImage(Object element) {
            if (element instanceof Stereotype) {
                element = ((Stereotype) element).getProfile();
            }
            return super.getImage(element);
        }
    }

    /**
     * Command to remove the given Sterotypes from an element.
     * 
     * @author njpatel
     * 
     */
    private class RemoveStereotypesCommand extends AbstractCommand {

        private final Element element;

        private final Collection<Stereotype> stereotypes;

        public RemoveStereotypesCommand(Element element,
                Collection<Stereotype> stereotypes) {
            this.element = element;
            this.stereotypes = stereotypes;
            setLabel(Messages.StereotypeSection_removeStereotypes_command_label);
        }

        @Override
        protected boolean prepare() {
            if (element != null && !element.eIsProxy()) {
                if (stereotypes != null && !stereotypes.isEmpty()) {
                    for (Stereotype type : stereotypes) {
                        if (type.eIsProxy()
                                || !element.isStereotypeApplied(type)) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }

        public void execute() {
            if (element != null) {
                for (Stereotype type : stereotypes) {
                    element.unapplyStereotype(type);
                }
            }
        }

        public void redo() {
            if (prepare()) {
                execute();
            }
        }

        @Override
        public boolean canUndo() {
            if (element != null && !element.eIsProxy()) {
                if (stereotypes != null && !stereotypes.isEmpty()) {
                    Set<Profile> profiles = new HashSet<Profile>();
                    for (Stereotype type : stereotypes) {
                        if (type.eIsProxy()
                                || element.isStereotypeApplied(type)) {
                            return false;
                        }
                        profiles.add(type.getProfile());
                    }
                    Package pkg = element.getNearestPackage();
                    if (pkg != null) {
                        // Make sure the profiles are applied
                        for (Profile profile : profiles) {
                            if (!pkg.isProfileApplied(profile)) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public void undo() {
            if (element != null) {
                for (Stereotype type : stereotypes) {
                    element.applyStereotype(type);
                }
            }
        }
    }
}
