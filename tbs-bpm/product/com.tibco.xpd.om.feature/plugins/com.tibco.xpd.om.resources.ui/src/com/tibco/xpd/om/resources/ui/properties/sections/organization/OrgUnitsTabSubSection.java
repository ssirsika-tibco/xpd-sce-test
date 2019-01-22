/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.organization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
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
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;

/**
 * Section for the organization units tab (for {@link Organization} and
 * {@link OrgUnit}).
 * 
 * @author njpatel
 * 
 */
public class OrgUnitsTabSubSection extends
        LocatableGroupSection<OrgUnit, OrgUnitFeature> implements IFilter {

    public OrgUnitsTabSubSection() {
        super(null);
    }

    @Override
    protected OrgUnit createNewElement() {
        EditingDomain ed = getEditingDomain();
        EObject input = getInput();
        ItemProviderAdapter provider = getItemProvider();

        if (ed != null && input != null && provider != null) {
            Collection<?> descriptors =
                    provider.getNewChildDescriptors(input, ed, null);

            if (descriptors != null) {
                for (Object descriptor : descriptors) {
                    if (descriptor instanceof CommandParameter
                            && ((CommandParameter) descriptor).getValue() instanceof OrgUnit) {
                        ed.getCommandStack()
                                .execute(CreateChildCommand.create(ed,
                                        input,
                                        descriptor,
                                        Collections.singletonList(input)));
                        return (OrgUnit) ((CommandParameter) descriptor)
                                .getValue();
                    }
                }
            }
        }

        return null;
    }

    @Override
    protected List<OrgUnitFeature> getFeatures() {
        EObject input = getInput();

        if (input instanceof OrgTypedElement) {
            OrgElementType type = ((OrgTypedElement) input).getType();

            if (type instanceof OrganizationType) {
                return ((OrganizationType) type).getOrgUnitFeatures();
            } else if (type instanceof OrgUnitType) {
                return ((OrgUnitType) type).getOrgUnitFeatures();
            }
        }

        return new ArrayList<OrgUnitFeature>(0);
    }

    @Override
    protected OrgUnitFeature getFeature(Object element) {
        return element instanceof OrgUnit ? ((OrgUnit) element).getFeature()
                : null;
    }

    @Override
    protected Command getSetFeatureCommand(Object element, Object feature) {
        if (element instanceof OrgUnit
                && (feature == null || feature instanceof OrgUnitFeature)) {
            return SetCommand.create(getEditingDomain(),
                    element,
                    OMPackage.eINSTANCE.getOrgUnit_Feature(),
                    feature != null ? feature : SetCommand.UNSET_VALUE);
        }
        return null;
    }

    @Override
    protected boolean isChild(OrgElement child) {
        return child instanceof OrgUnit && !(child instanceof DynamicOrgUnit);
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
         * XPD-5300: Don't show for a Dynamic OrgUnit
         */
        if (eo instanceof DynamicOrgUnit) {
            return false;
        }

        return eo instanceof Organization || eo instanceof OrgUnit;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean shouldRefresh = super.shouldRefresh(notifications);
        /*
         * If a sub-unit has been refreshed then this section needs to be
         * updated (sub-units are not contained in this OrgUnit)
         */
        if (!shouldRefresh && notifications != null) {
            // Also refresh if any of the sub-units have been updated
            for (Notification notification : notifications) {
                if (notification.getNotifier() instanceof OrgUnit) {
                    OrgUnitRelationship relationship =
                            ((OrgUnit) notification.getNotifier())
                                    .getIncomingHierachicalRelationship();
                    if (relationship != null && relationship.getFrom() != null) {
                        return (relationship.getFrom().equals(getInput()));
                    }
                }
            }
        }

        return shouldRefresh;
    }
}
