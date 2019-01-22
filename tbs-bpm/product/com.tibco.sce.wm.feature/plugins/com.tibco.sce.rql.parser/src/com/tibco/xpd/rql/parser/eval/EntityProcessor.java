/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.rql.parser.eval;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.n2.de.rql.parser.ASTCapabilityQuery;
import com.tibco.n2.de.rql.parser.ASTGroupQuery;
import com.tibco.n2.de.rql.parser.ASTLocationQuery;
import com.tibco.n2.de.rql.parser.ASTOrgUnitQuery;
import com.tibco.n2.de.rql.parser.ASTPositionQuery;
import com.tibco.n2.de.rql.parser.ASTPrivilegeQuery;
import com.tibco.n2.de.rql.parser.ASTPropertyExpr;
import com.tibco.n2.de.rql.parser.ASTQualifierExpr;
import com.tibco.n2.de.rql.parser.base.EntityNode;
import com.tibco.n2.de.rql.parser.base.Property;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.QualifiedAssociation;
import com.tibco.xpd.rql.parser.script.RQLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;

/**
 * Identifies the associated entities of the given RQLScriptRelevantData by
 * comparing the properties of those associated entities with the given
 * {@link ASTPropertyExpr}. The type of associated entities to be compared is
 * identified by the type of entity to which the ASTPropertyExpr belongs.
 * 
 * @copyright 2011 TIBCO Software Inc.
 * @author pwatson
 * @version 1.2
 */
final class EntityProcessor {
    private static final EntityProcessor INSTANCE = new EntityProcessor();

    public static EntityProcessor getInstance() {
        return (EntityProcessor.INSTANCE);
    }

    private EntityProcessor() {
    }

    public Collection<IScriptRelevantData> process(
            RQLScriptRelevantData aEntityWrapper, ASTPropertyExpr aProperty)
            throws Exception {
        EObject model = aEntityWrapper.getModel();
        if (model instanceof Organization) {
            return (visit((Organization) model, aProperty));
        } else if (model instanceof OrgUnit) {
            return (visit((OrgUnit) model, aProperty));
        } else if (model instanceof Position) {
            return (visit((Position) model, aProperty));
        } else if (model instanceof Group) {
            return (visit((Group) model, aProperty));
        }

        return (null);
    }

    /**
     * Tests whether the given string is null, empty or contains only
     * whitespace.
     * 
     * @param aStr
     *            the string to be tested.
     * @return <code>true</code> if the string is considered to be empty.
     */
    private boolean isEmpty(String aStr) {
        if ((aStr != null) && (aStr.length() > 0)) {
            // if it only contains whitespace
            for (int i = 0, n = aStr.length(); i < n; i++) {
                if (aStr.charAt(i) != ' ') {
                    return (false);
                }
            }
        }

        return (true);
    }

    /**
     * Performs the comparison of the given org-entity's properties against the
     * ComparisonNodeComparator.
     * 
     * @param aEntity
     *            the org-entity to be compared.
     * @param aComparator
     *            the ComparisonNodeComparator used to compare the property
     *            values.
     * @return <code>true</code> if the given org-entity matches the given
     *         expression.
     */
    private boolean compareProperty(OrgElement aEntity, Property aProperty,
            ComparisonNodeComparator aComparator) {
        if (aEntity == null) {
            return (false);
        }

        // compare the entity's name
        if (aProperty == Property.NAME) {
            return (aComparator.eval(aEntity.getName()));
        }

        // compare the entity's guid
        else if (aProperty == Property.GUID) {
            return (aComparator.eval(aEntity.getId()));
        }

        // compare the entity's meta-type - by name
        else if (aProperty == Property.TYPE) {
            if (aEntity instanceof OrgTypedElement) {
                OrgElementType metaType = ((OrgTypedElement) aEntity).getType();
                if (metaType != null) {
                    return (aComparator.eval(metaType.getName()));
                }
            }
        }

        return (false);
    }

    /**
     * Compares the given qualifying values with the given
     * ComparisonNodeComparator.
     * 
     * @param aHolding
     *            the qualified holding whose values are to be compared.
     * @param aComparator
     *            the ComparisonNodeComparator used to compare the property
     *            values.
     * @return <code>true</code> if the holding's values are a match for the
     *         qualifier.
     */
    private boolean compareQualifier(QualifiedAssociation aHolding,
            ComparisonNodeComparator aComparator) {
        AttributeValue qualifier = aHolding.getQualifierValue();

        // null values always match positive
        if ((qualifier == null) || (isEmpty(qualifier.getValue()))
                || (aComparator.isNull())) {
            return (true);
        }

        if (qualifier.getType() == AttributeType.ENUM_SET) {
            EList<EnumValue> values = qualifier.getEnumSetValues();
            if (values != null) {
                for (EnumValue value : values) {
                    if (aComparator.eval(value.getValue())) {
                        return (true);
                    }
                }
            }
        } else {
            if (aComparator.eval(qualifier.getValue())) {
                return (true);
            }
        }

        return (false);
    }

    /**
     * Return the entities associated with the given Group that match the given
     * ASTPropertyExpr. The type of entities returned is determined by the type
     * of EntityNode in which the given ASTPropertyExpr is enclosed.
     */
    private Collection<IScriptRelevantData> visit(Group aGroup,
            ASTPropertyExpr aProperty) throws Exception {
        Collection<IScriptRelevantData> result =
                new ArrayList<IScriptRelevantData>();
        ComparisonNodeComparator propertyComparator =
                new ComparisonNodeComparator(aProperty);

        EntityNode entityNode = aProperty.getEntityNode();
        if (entityNode instanceof ASTGroupQuery) {
            // returns the sub-groups, of this group, that match the property
            // expression
            EList<Group> subGroups = aGroup.getSubGroups();
            if (subGroups != null) {
                for (Group subGroup : subGroups) {
                    if (compareProperty(subGroup,
                            aProperty.getProperty(),
                            propertyComparator)) {
                        result.add(new RQLScriptRelevantData(subGroup));
                    }
                }
            }
        }

        else if (entityNode instanceof ASTCapabilityQuery) {
            ASTQualifierExpr qualifier = aProperty.getQualifier();
            ComparisonNodeComparator qualifierComparator =
                    new ComparisonNodeComparator(qualifier);

            // tests whether this group holds a capability that matches the
            // property expression
            EList<CapabilityAssociation> capabilities =
                    aGroup.getCapabilitiesAssociations();
            if (capabilities != null) {
                for (CapabilityAssociation holding : capabilities) {
                    Capability capability = holding.getCapability();
                    if ((compareProperty(capability,
                            aProperty.getProperty(),
                            propertyComparator))
                            && (compareQualifier(holding, qualifierComparator))) {
                        result.add(new RQLScriptRelevantData(aGroup));
                        break;
                    }
                }
            }
        }

        else if (entityNode instanceof ASTPrivilegeQuery) {
            ASTQualifierExpr qualifier = aProperty.getQualifier();
            ComparisonNodeComparator qualifierComparator =
                    new ComparisonNodeComparator(qualifier);

            // tests whether this group holds a privilege that matches the
            // property expression
            EList<PrivilegeAssociation> privileges =
                    aGroup.getPrivilegeAssociations();
            if (privileges != null) {
                for (PrivilegeAssociation holding : privileges) {
                    Privilege privilege = holding.getPrivilege();
                    if ((compareProperty(privilege,
                            aProperty.getProperty(),
                            propertyComparator))
                            && (compareQualifier(holding, qualifierComparator))) {
                        result.add(new RQLScriptRelevantData(aGroup));
                        break;
                    }
                }
            }
        }

        return (result);
    }

    /**
     * Return the entities associated with the given Position that match the
     * given ASTPropertyExpr. The type of entities returned is determined by the
     * type of EntityNode in which the given ASTPropertyExpr is enclosed.
     */
    private Collection<IScriptRelevantData> visit(Position aPosition,
            ASTPropertyExpr aProperty) throws Exception {
        Collection<IScriptRelevantData> result =
                new ArrayList<IScriptRelevantData>();
        ComparisonNodeComparator propertyComparator =
                new ComparisonNodeComparator(aProperty);

        EntityNode entityNode = aProperty.getEntityNode();
        if (entityNode instanceof ASTLocationQuery) {
            // returns the location, held by the position, that matches the
            // property expression
            Location location = aPosition.getLocation();
            if (compareProperty(location,
                    aProperty.getProperty(),
                    propertyComparator)) {
                result.add(new RQLScriptRelevantData(location));
            }
        }

        else if (entityNode instanceof ASTOrgUnitQuery) {
            if (aPosition.eContainer() instanceof OrgUnit) {
                // returns the parent org-unit, of this position, that matches
                // the property expression
                OrgUnit orgUnit = (OrgUnit) aPosition.eContainer();
                if (compareProperty(orgUnit,
                        aProperty.getProperty(),
                        propertyComparator)) {
                    result.add(new RQLScriptRelevantData(orgUnit));
                }
            }
        }

        else if (entityNode instanceof ASTCapabilityQuery) {
            ASTQualifierExpr qualifier = aProperty.getQualifier();
            ComparisonNodeComparator qualifierComparator =
                    new ComparisonNodeComparator(qualifier);

            // tests whether this position holds a capability that matches the
            // property expression
            EList<CapabilityAssociation> capabilities =
                    aPosition.getCapabilitiesAssociations();
            if (capabilities != null) {
                for (CapabilityAssociation holding : capabilities) {
                    if ((compareProperty(holding.getCapability(),
                            aProperty.getProperty(),
                            propertyComparator))
                            && (compareQualifier(holding, qualifierComparator))) {
                        result.add(new RQLScriptRelevantData(aPosition));
                        break;
                    }
                }
            }
        }

        else if (entityNode instanceof ASTPrivilegeQuery) {
            ASTQualifierExpr qualifier = aProperty.getQualifier();
            ComparisonNodeComparator qualifierComparator =
                    new ComparisonNodeComparator(qualifier);

            // tests whether this position holds a privilege that matches the
            // property expression
            EList<PrivilegeAssociation> privileges =
                    aPosition.getPrivilegeAssociations();
            if (privileges != null) {
                for (PrivilegeAssociation holding : privileges) {
                    if ((compareProperty(holding.getPrivilege(),
                            aProperty.getProperty(),
                            propertyComparator))
                            && (compareQualifier(holding, qualifierComparator))) {
                        result.add(new RQLScriptRelevantData(aPosition));
                        break;
                    }
                }
            }
        }

        return (result);
    }

    /**
     * Return the entities associated with the given Org-Unit that match the
     * given ASTPropertyExpr. The type of entities returned is determined by the
     * type of EntityNode in which the given ASTPropertyExpr is enclosed.
     */
    private Collection<IScriptRelevantData> visit(OrgUnit aOrgUnit,
            ASTPropertyExpr aProperty) throws Exception {
        Collection<IScriptRelevantData> result =
                new ArrayList<IScriptRelevantData>();
        ComparisonNodeComparator propertyComparator =
                new ComparisonNodeComparator(aProperty);

        EntityNode entityNode = aProperty.getEntityNode();
        if (entityNode instanceof ASTLocationQuery) {
            // returns the location, held by the org-unit, that matches the
            // property expression
            Location location = aOrgUnit.getLocation();
            if (compareProperty(location,
                    aProperty.getProperty(),
                    propertyComparator)) {
                result.add(new RQLScriptRelevantData(location));
            }
        }

        else if (entityNode instanceof ASTPositionQuery) {
            // returns those Positions, within the org-unit, that match the
            // property expression
            EList<Position> positions = aOrgUnit.getPositions();
            if (positions != null) {
                for (Position position : positions) {
                    if (compareProperty(position,
                            aProperty.getProperty(),
                            propertyComparator)) {
                        result.add(new RQLScriptRelevantData(position));
                    }
                }
            }
        }

        else if (entityNode instanceof ASTPrivilegeQuery) {
            ASTQualifierExpr qualifier = aProperty.getQualifier();
            ComparisonNodeComparator qualifierComparator =
                    new ComparisonNodeComparator(qualifier);

            // tests whether this org-unit holds a privilege that matches the
            // property expression
            EList<PrivilegeAssociation> privileges =
                    aOrgUnit.getPrivilegeAssociations();
            if (privileges != null) {
                for (PrivilegeAssociation holding : privileges) {
                    if ((compareProperty(holding.getPrivilege(),
                            aProperty.getProperty(),
                            propertyComparator))
                            && (compareQualifier(holding, qualifierComparator))) {
                        result.add(new RQLScriptRelevantData(aOrgUnit));
                        break;
                    }
                }
            }
        } else if (entityNode instanceof ASTOrgUnitQuery) {
            // returns the org-units, of this org, that match the property
            // expression
            EList<OrgUnit> orgUnits = aOrgUnit.getSubUnits();
            if (orgUnits != null) {
                for (OrgUnit orgUnit : orgUnits) {
                    if (compareProperty(orgUnit,
                            aProperty.getProperty(),
                            propertyComparator)) {
                        result.add(new RQLScriptRelevantData(orgUnit));
                    }
                }
            }
        }

        return (result);
    }

    /**
     * Return the entities associated with the given Organisation that match the
     * given ASTPropertyExpr. The type of entities returned is determined by the
     * type of EntityNode in which the given ASTPropertyExpr is enclosed.
     */
    private Collection<IScriptRelevantData> visit(Organization aOrganisation,
            ASTPropertyExpr aProperty) throws Exception {
        Collection<IScriptRelevantData> result =
                new ArrayList<IScriptRelevantData>();
        ComparisonNodeComparator propertyComparator =
                new ComparisonNodeComparator(aProperty);

        EntityNode entityNode = aProperty.getEntityNode();
        if (entityNode instanceof ASTLocationQuery) {
            // returns the location, held by the org, that matches the property
            // expression
            Location location = aOrganisation.getLocation();
            if (compareProperty(location,
                    aProperty.getProperty(),
                    propertyComparator)) {
                result.add(new RQLScriptRelevantData(location));
            }
        }

        else if (entityNode instanceof ASTOrgUnitQuery) {
            // returns the org-units, of this org, that match the property
            // expression
            EList<OrgUnit> orgUnits = aOrganisation.getSubUnits();
            if (orgUnits != null) {
                for (OrgUnit orgUnit : orgUnits) {
                    if (compareProperty(orgUnit,
                            aProperty.getProperty(),
                            propertyComparator)) {
                        result.add(new RQLScriptRelevantData(orgUnit));
                    }
                }
            }
        }

        return (result);
    }
}
