/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.Authorizable;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes.QualifiedAssociationTableSection;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;

/**
 * Privileges tab section for {@link Authorizable} objects. This will allow for
 * the adding of {@link PrivilegeAssociation associations} to {@link Privilege
 * privileges} and setting of their qualification values.
 * 
 * @author njpatel
 * 
 */
public class PrivilegesTabSection extends
        QualifiedAssociationTableSection<PrivilegeAssociation> implements
        IFilter {

    /**
     * Privileges tab section for {@link Authorizable} objects.
     */
    public PrivilegesTabSection() {
        super(Messages.PrivilegesTabSection_section_title,
                Messages.PrivilegesTabSection_column_title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);

        /*
         * XPD-5300: Also don't show for a Dynamic OrgUnit
         */
        if (eo instanceof OrgModel || eo instanceof DynamicOrgUnit) {
            return false;
        }

        return eo instanceof Authorizable;
    }

    @Override
    protected Attribute getQualifier(PrivilegeAssociation element) {
        if (element != null && element.getPrivilege() != null) {
            return element.getPrivilege().getQualifierAttribute();
        }
        return null;
    }

    @Override
    protected String getTypeName(PrivilegeAssociation element) {
        if (element != null && element.getPrivilege() != null) {
            return element.getPrivilege().getDisplayName();
        }
        return null;
    }

    @Override
    protected Image getTypeImage(PrivilegeAssociation element) {
        // Get the privilege image
        if (element != null && element.getPrivilege() != null) {
            return getModelLabelProvider().getImage(element.getPrivilege());
        }
        return null;
    }

    @Override
    protected EList<PrivilegeAssociation> getElements(EObject input) {
        if (input instanceof Authorizable) {
            return ((Authorizable) input).getPrivilegeAssociations();
        }
        return null;
    }

    @Override
    protected Command getAddNewElementCommand(Shell shell, EObject input,
            EditingDomain ed) {
        if (input instanceof Authorizable) {
            EList<Privilege> privileges = new BasicEList<Privilege>();
            EList<PrivilegeAssociation> associations =
                    ((Authorizable) input).getPrivilegeAssociations();

            if (associations != null) {
                for (PrivilegeAssociation assoc : associations) {
                    privileges.add(assoc.getPrivilege());
                }
            }

            // Show the privileges picker
            Object[] selection =
                    PickerService.getInstance().openMultiPickerDialog(shell,
                            new PickerTypeQuery[] { new OMTypeQuery(
                                    OMTypeQuery.TYPE_ID_PRIVILEGE) },
                            null,
                            null,
                            null,
                            new IFilter[] { new SameResourceFilter(input) },
                            privileges.toArray());

            if (selection != null) {
                if (selection.length > 0) {
                    /*
                     * Need to sync the returned list with the current list -
                     * items may have been removed and/or added.
                     */
                    EList<PrivilegeAssociation> assocsToRemove =
                            new BasicEList<PrivilegeAssociation>();
                    EList<PrivilegeAssociation> assocsToAdd =
                            new BasicEList<PrivilegeAssociation>();

                    // Build a list of new associations to add
                    for (Object sel : selection) {
                        if (sel instanceof Privilege) {
                            if (!privileges.contains(sel)) {
                                PrivilegeAssociation assoc =
                                        OMFactory.eINSTANCE
                                                .createPrivilegeAssociation();
                                assoc.setPrivilege((Privilege) sel);
                                assocsToAdd.add(assoc);
                            }
                        }
                    }

                    // Build a list of current associations to remove
                    List<Object> selList = Arrays.asList(selection);
                    for (PrivilegeAssociation assoc : associations) {
                        if (!selList.contains(assoc.getPrivilege())) {
                            assocsToRemove.add(assoc);
                        }
                    }

                    CompoundCommand cmd = new CompoundCommand();

                    if (!assocsToRemove.isEmpty()) {
                        cmd.append(RemoveCommand.create(ed,
                                input,
                                OMPackage.eINSTANCE
                                        .getAssociableWithPrivileges_PrivilegeAssociations(),
                                assocsToRemove));
                    }

                    if (!assocsToAdd.isEmpty()) {
                        cmd.append(AddCommand.create(ed,
                                input,
                                OMPackage.eINSTANCE
                                        .getAssociableWithPrivileges_PrivilegeAssociations(),
                                assocsToAdd));
                    }

                    return cmd;

                } else {
                    // All privileges have been removed so clear all
                    // privileges associations
                    return SetCommand
                            .create(ed,
                                    input,
                                    OMPackage.eINSTANCE
                                            .getAssociableWithPrivileges_PrivilegeAssociations(),
                                    SetCommand.UNSET_VALUE);
                }
            }
        }
        return null;
    }

    @Override
    protected Command getDeleteSelectionCommand(Shell shell, EObject input,
            EditingDomain ed, IStructuredSelection selection) {
        if (input != null && ed != null && selection != null
                && !selection.isEmpty()) {
            return RemoveCommand
                    .create(ed,
                            input,
                            OMPackage.eINSTANCE
                                    .getAssociableWithPrivileges_PrivilegeAssociations(),
                            selection.toList());
        }
        return null;
    }

    @Override
    protected Command getMoveCommand(EditingDomain ed,
            PrivilegeAssociation element, int moveBy) {
        EObject input = getInput();
        Command cmd = null;
        if (input != null) {
            EList<PrivilegeAssociation> elements = getElements(input);

            if (elements != null && !elements.isEmpty()) {
                int idx = elements.indexOf(element);

                if ((moveBy > 0 && idx < elements.size())
                        || (moveBy < 0 && idx > 0)) {
                    cmd =
                            MoveCommand
                                    .create(ed,
                                            input,
                                            OMPackage.eINSTANCE
                                                    .getAssociableWithPrivileges_PrivilegeAssociations(),
                                            element,
                                            idx + moveBy);
                }
            }
        }
        return cmd;
    }

}
