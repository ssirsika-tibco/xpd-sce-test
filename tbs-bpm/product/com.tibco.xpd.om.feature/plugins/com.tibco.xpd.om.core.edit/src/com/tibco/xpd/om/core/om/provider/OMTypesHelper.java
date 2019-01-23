/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.core.om.provider;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.om.core.om.OrgUnitRelationshipType;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Helper class to provide default types for OM model elements.
 * <p>
 * <i>Created: 2 Jan 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public final class OMTypesHelper {

    private static final String DEFAULT_ORGANIZATION_TYPE_NAME = "StandardOrganizationType"; //$NON-NLS-1$
    private static final String DEFAULT_ORGANIZATION_UNIT_TYPE_NAME = "StandardOrganizationUnitType"; //$NON-NLS-1$
    private static final String DEFAULT_RELATIONSHIP_TYPE_NAME = "ReporteeRelationshipType"; //$NON-NLS-1$
    private static final String DEFAULT_POSITION_TYPE_NAME = "StandardPositionType"; //$NON-NLS-1$

    private static OMTypesHelper instance = new OMTypesHelper();

    private OMTypesHelper() {
    }

    public static OMTypesHelper getIstance() {
        return instance;
    }

    public OrgUnitRelationshipType getHierarchyRelationshipType(Object context) {
        String hierarchyTypeName = DEFAULT_RELATIONSHIP_TYPE_NAME;
        EditingDomain ed = AdapterFactoryEditingDomain
                .getEditingDomainFor(context);
        TreeIterator<Notifier> iter = ed.getResourceSet().getAllContents();
        while (iter.hasNext()) {
            Notifier obj = iter.next();
            if (obj instanceof OrgUnitRelationshipType
                    && hierarchyTypeName.equals(((OrgUnitRelationshipType) obj)
                            .getDisplayName())) {
                return (OrgUnitRelationshipType) obj;
            }
        }
        return null;
    }

    public OrgUnitRelationshipType getHierarchyRelationshipType() {
        String hierarchyTypeName = DEFAULT_RELATIONSHIP_TYPE_NAME;
        EditingDomain ed = XpdResourcesPlugin.getDefault().getEditingDomain();
        TreeIterator<Notifier> iter = ed.getResourceSet().getAllContents();
        while (iter.hasNext()) {
            Notifier obj = iter.next();
            if (obj instanceof OrgUnitRelationshipType
                    && hierarchyTypeName.equals(((OrgUnitRelationshipType) obj)
                            .getDisplayName())) {
                return (OrgUnitRelationshipType) obj;
            }
        }
        return null;
    }
}
