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
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.Capable;
import com.tibco.xpd.om.core.om.OMFactory;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.attributes.QualifiedAssociationTableSection;
import com.tibco.xpd.resources.ui.picker.PickerService;
import com.tibco.xpd.resources.ui.picker.PickerTypeQuery;
import com.tibco.xpd.resources.ui.picker.filters.SameResourceFilter;

/**
 * Capabilities tab section for {@link Capable} objects. This will allow for the
 * adding of {@link CapableAssociation associations} to {@link Capability
 * capabilities} and setting of their qualification values.
 * 
 * @author njpatel
 * 
 */
public class CapabilitiesTabSection extends
        QualifiedAssociationTableSection<CapabilityAssociation> {

    /**
     * Capabilities tab section for {@link Capable} objects.
     */
    public CapabilitiesTabSection() {
        super(Messages.CapabilitiesTabSection_section_title,
                Messages.CapabilitiesTabSection_column_title);
    }

    @Override
    protected Attribute getQualifier(CapabilityAssociation element) {
        if (element != null && element.getCapability() != null) {
            return element.getCapability().getQualifierAttribute();
        }
        return null;
    }

    @Override
    protected String getTypeName(CapabilityAssociation element) {
        if (element != null && element.getCapability() != null) {
            return element.getCapability().getDisplayName();
        }
        return null;
    }

    @Override
    protected Image getTypeImage(CapabilityAssociation element) {
        // Get the capability image
        if (element != null && element.getCapability() != null) {
            return getModelLabelProvider().getImage(element.getCapability());
        }
        return null;
    }

    @Override
    protected EList<CapabilityAssociation> getElements(EObject input) {
        if (input instanceof Capable) {
            return ((Capable) input).getCapabilitiesAssociations();
        }
        return null;
    }

    @Override
    protected Command getAddNewElementCommand(Shell shell, EObject input,
            EditingDomain ed) {

        if (input instanceof Capable) {
            EList<Capability> capabilities = new BasicEList<Capability>();
            EList<CapabilityAssociation> associations =
                    ((Capable) input).getCapabilitiesAssociations();

            if (associations != null) {
                for (CapabilityAssociation assoc : associations) {
                    capabilities.add(assoc.getCapability());
                }
            }

            // Show the capability picker
            Object[] selection =
                    PickerService.getInstance()
                            .openMultiPickerDialog(shell,
                                    new PickerTypeQuery[] { new OMTypeQuery(
                                            OMTypeQuery.TYPE_ID_CAPABILITY) },
                                    null,
                                    null,
                                    null,
                                    new IFilter[] { new SameResourceFilter(
                                            getInput()) },
                                    capabilities.toArray());
            ;

            if (selection != null) {
                if (selection.length > 0) {
                    /*
                     * Need to sync the returned list with the current list -
                     * items may have been removed and/or added.
                     */
                    EList<CapabilityAssociation> assocsToRemove =
                            new BasicEList<CapabilityAssociation>();
                    EList<CapabilityAssociation> assocsToAdd =
                            new BasicEList<CapabilityAssociation>();

                    // Build a list of new associations to add
                    for (Object sel : selection) {
                        if (sel instanceof Capability) {
                            if (!capabilities.contains(sel)) {
                                CapabilityAssociation assoc =
                                        OMFactory.eINSTANCE
                                                .createCapabilityAssociation();
                                assoc.setCapability((Capability) sel);
                                assocsToAdd.add(assoc);
                            }
                        }
                    }

                    // Build a list of current associations to remove
                    List<Object> selList = Arrays.asList(selection);
                    for (CapabilityAssociation assoc : associations) {
                        if (!selList.contains(assoc.getCapability())) {
                            assocsToRemove.add(assoc);
                        }
                    }

                    CompoundCommand cmd = new CompoundCommand();

                    if (!assocsToRemove.isEmpty()) {
                        cmd.append(RemoveCommand.create(ed,
                                input,
                                OMPackage.eINSTANCE
                                        .getCapable_CapabilitiesAssociations(),
                                assocsToRemove));
                    }

                    if (!assocsToAdd.isEmpty()) {
                        cmd.append(AddCommand.create(ed,
                                input,
                                OMPackage.eINSTANCE
                                        .getCapable_CapabilitiesAssociations(),
                                assocsToAdd));
                    }

                    return cmd;

                } else {
                    // All capabilities have been removed so clear all
                    // capability associations
                    return SetCommand.create(ed,
                            input,
                            OMPackage.eINSTANCE
                                    .getCapable_CapabilitiesAssociations(),
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
            return RemoveCommand.create(ed, input, OMPackage.eINSTANCE
                    .getCapable_CapabilitiesAssociations(), selection.toList());
        }
        return null;
    }

    @Override
    protected Command getMoveCommand(EditingDomain ed,
            CapabilityAssociation element, int moveBy) {
        EObject input = getInput();
        Command cmd = null;
        if (input != null) {
            EList<CapabilityAssociation> elements = getElements(input);

            if (elements != null && !elements.isEmpty()) {
                int idx = elements.indexOf(element);

                if ((moveBy > 0 && idx < elements.size())
                        || (moveBy < 0 && idx > 0)) {
                    cmd =
                            MoveCommand
                                    .create(ed,
                                            input,
                                            OMPackage.eINSTANCE
                                                    .getCapable_CapabilitiesAssociations(),
                                            element,
                                            idx + moveBy);
                }
            }
        }
        return cmd;
    }

}
