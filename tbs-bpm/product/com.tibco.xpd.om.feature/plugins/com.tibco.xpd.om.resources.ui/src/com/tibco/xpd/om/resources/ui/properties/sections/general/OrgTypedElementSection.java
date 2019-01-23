/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.FeatureTypeSection;

/**
 * Section for the {@link OrgTypedElement type} selection.
 * 
 * @author njpatel
 * 
 */
public class OrgTypedElementSection extends FeatureTypeSection {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);

        /*
         * XPD-5772: Saket: Don't show for Dynamic Organization templates.
         */
        if (eo instanceof Organization) {
            Organization org = (Organization) eo;
            if (org.isDynamic()) {
                return false;
            }
        }
        if (eo instanceof OrgTypedElement) {
            // Exclude OrgUnit and Position as they will have the Element
            // (Feature) selection control instead of this Type selection
            if (!(eo instanceof OrgUnit || eo instanceof Position)) {
                return true;
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.om.resources.ui.properties.sections.internal.general.
     * FeatureTypeSection#getType(org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected OrgElementType getType(EObject input) {
        OrgElementType type = null;

        if (input instanceof OrgTypedElement) {
            // Exclude OrgUnit and Position as they will have the Element
            // (Feature) selection control instead of this Type selection
            if (!(input instanceof OrgUnit || input instanceof Position)) {
                type = ((OrgTypedElement) input).getType();
            }
        }

        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.om.resources.ui.properties.sections.internal.general.
     * FeatureTypeSection#getEReference(org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected EReference getEReference(EObject input) {
        EReference ref = null;

        if (input instanceof OrgTypedElement) {
            ref = OMPackage.eINSTANCE.getOrgTypedElement_Type();
        }

        return ref;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.om.resources.ui.properties.sections.internal.general.
     * FeatureTypeSection#getTypeInfo(org.eclipse.emf.ecore.EObject)
     */
    @Override
    protected String getQueryType(EObject input) {
        if (input instanceof Location) {
            return OMTypeQuery.TYPE_ID_LOCATION_TYPE;
        } else if (input instanceof Resource) {
            return OMTypeQuery.TYPE_ID_RESOURCE_TYPE;
        } else if (input instanceof Organization) {
            return OMTypeQuery.TYPE_ID_ORGANIZATION_TYPE;
        } else if (input instanceof OrgUnitRelationship) {
            return OMTypeQuery.TYPE_ID_ORG_UNIT_RELATIONSHIP_TYPE;
        }

        return super.getQueryType(getInput());
    }

}
