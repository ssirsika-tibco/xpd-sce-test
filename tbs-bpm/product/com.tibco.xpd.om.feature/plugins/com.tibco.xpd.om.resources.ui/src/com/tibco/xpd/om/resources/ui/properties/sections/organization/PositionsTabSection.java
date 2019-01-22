/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.organization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.resources.ui.internal.Messages;

/**
 * Section for the positions tab for {@link OrgUnit}).
 * 
 * @author njpatel
 * 
 */
public class PositionsTabSection extends
        LocatableGroupSection<Position, PositionFeature> implements IFilter {

    public PositionsTabSection() {
        super(Messages.PositionsTabSection_positionsSection_title);
    }

    @Override
    protected List<PositionFeature> getFeatures() {
        EObject input = getInput();

        if (input instanceof OrgTypedElement) {
            OrgElementType type = ((OrgTypedElement) input).getType();

            if (type instanceof OrgUnitType) {
                return ((OrgUnitType) type).getPositionFeatures();
            }
        }

        return new ArrayList<PositionFeature>(0);
    }

    @Override
    protected PositionFeature getFeature(Object element) {
        return element instanceof Position ? ((Position) element).getFeature()
                : null;
    }

    @Override
    protected Command getSetFeatureCommand(Object element, Object feature) {
        if (element instanceof Position
                && (feature == null || feature instanceof PositionFeature)) {
            return SetCommand.create(getEditingDomain(),
                    element,
                    OMPackage.eINSTANCE.getPosition_Feature(),
                    feature != null ? feature : SetCommand.UNSET_VALUE);
        }
        return null;
    }

    @Override
    protected Position createNewElement() {
        EditingDomain ed = getEditingDomain();
        EObject input = getInput();
        ItemProviderAdapter provider = getItemProvider();

        if (ed != null && input != null && provider != null) {
            Collection<?> descriptors =
                    provider.getNewChildDescriptors(input, ed, null);

            if (descriptors != null) {
                for (Object descriptor : descriptors) {
                    if (descriptor instanceof CommandParameter
                            && ((CommandParameter) descriptor).getValue() instanceof Position) {
                        ed.getCommandStack()
                                .execute(CreateChildCommand.create(ed,
                                        input,
                                        descriptor,
                                        Collections.singletonList(input)));
                        return (Position) ((CommandParameter) descriptor)
                                .getValue();
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected boolean isChild(OrgElement child) {
        return child instanceof Position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject input = resollveInput(toTest);
        /*
         * XPD-5300: Don't show for a Dynamic OrgUnit
         */
        if (input instanceof DynamicOrgUnit) {
            return false;
        }

        return input instanceof OrgUnit;
    }

    @Override
    protected boolean isMoveSupported() {
        return true;
    }

    @Override
    protected Command getMoveCommand(EditingDomain ed, EObject element,
            int moveBy) {
        EObject input = getInput();
        if (input != null) {
            EList<? extends OrgElement> children = getChildren(input);

            if (children != null && !children.isEmpty()) {
                int idx = children.indexOf(element);
                if (idx >= 0) {
                    idx += moveBy;
                    if (idx >= 0 && idx < children.size()) {
                        return MoveCommand.create(ed,
                                input,
                                OMPackage.eINSTANCE.getOrgUnit_Positions(),
                                element,
                                idx);
                    }
                }
            }
        }
        return null;
    }
}
