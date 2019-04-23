/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.om.transform.de.transform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;

import com.tibco.n2.directory.model.de.AllocationMethod;
import com.tibco.n2.directory.model.de.CapabilityHolding;
import com.tibco.n2.directory.model.de.DataType;
import com.tibco.n2.directory.model.de.DeFactory;
import com.tibco.n2.directory.model.de.DePackage;
import com.tibco.n2.directory.model.de.MetaModel;
import com.tibco.n2.directory.model.de.ModelTemplate;
import com.tibco.n2.directory.model.de.ModelType;
import com.tibco.n2.directory.model.de.NamedEntity;
import com.tibco.n2.directory.model.de.OrgUnitBase;
import com.tibco.n2.directory.model.de.PrivilegeHolding;
import com.tibco.n2.directory.model.de.QualifiedHolding;
import com.tibco.n2.directory.model.de.TypedEntity;
import com.tibco.xpd.om.core.om.Attribute;
import com.tibco.xpd.om.core.om.AttributeType;
import com.tibco.xpd.om.core.om.AttributeValue;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.CapabilityCategory;
import com.tibco.xpd.om.core.om.DynamicOrgIdentifier;
import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.EnumValue;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.LocationType;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.OrgTypedElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitFeature;
import com.tibco.xpd.om.core.om.OrgUnitType;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.OrganizationType;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.PositionFeature;
import com.tibco.xpd.om.core.om.PositionType;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.PrivilegeCategory;
import com.tibco.xpd.om.core.om.QualifiedAssociation;
import com.tibco.xpd.om.core.om.SystemAction;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.DateUtil;
import com.tibco.xpd.om.transform.de.TransformDEActivator;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.ui.components.calendar.DateType;

/**
 * Transforms OM root model element to DE root model element. <br/>
 * See: {@link #transformOrgModel(OrgModel)}
 * 
 * @author jarciuch
 * @since 15 Oct 2013
 */
public class OrgModelTransformer {

    @SuppressWarnings("unused")
    private final static Logger LOG = TransformDEActivator.getDefault()
            .getLogger();

    /**
     * Functional object used by
     * {@link TransfContext#create(EClass, EObject, CreateFuncton)} method.
     * 
     * @param <S>
     *            source type.
     * @param <D>
     *            destination type.
     */
    private interface CreateFuncton<S extends EObject, D extends EObject> {
        /**
         * Used to fill destination object with details of a source object.
         * 
         * @param src
         *            source object.
         * @param dest
         *            newly created destination object.
         */
        public void create(S src, D dest);
    }

    /**
     * Context of a transformation. Provides caching of created destination
     * objects so the resulting model can process cycles of references.
     * 
     * @see #create(EClass, EObject, CreateFuncton)
     */
    private static class TransfContext {
        private final EFactory f;

        public TransfContext(EFactory f) {
            this.f = f;

        }

        private HashMap<EObject, EObject> cache =
                new HashMap<EObject, EObject>();

        /**
         * Returns previously created object for a source if exists in cache,
         * otherwise creates a new instance and calls create(...) method of a
         * passed 'createFunction' argument. (S is a source, and D is a
         * destination generic type.)
         * 
         * @param c
         *            EClass to be created.
         * @param source
         *            a source object.
         * @param createFunction
         *            functional object used for create body.
         * @return destination object either from cache or a new instance built
         *         by {@link CreateFuncton#create(Object, Object)};
         */
        @SuppressWarnings("unchecked")
        public <S extends EObject, D extends EObject> D create(EClass c,
                S source, CreateFuncton<S, D> createFunction) {
            EObject eo = cache.get(source);
            if (eo == null) {
                D t = (D) f.create(c);
                cache.put(source, t);
                createFunction.create(source, t);
                return t;
            } else {
                return (D) eo;
            }
        }

        /**
         * Creates a new instance of provided EClass.
         * 
         * @param c
         *            EClass of an instance to be created.
         * @return a new instance of the specified EClass. (Instance is not
         *         cached.)
         * @see #create(EClass, EObject, CreateFuncton)
         */
        public EObject newInstance(EClass c) {
            return f.create(c);
        }
    }

    /**
     * Transforms OrgModel instance to the corresponding Directory Engine
     * ModelType.
     * 
     * @param orgModel
     *            source of transformation.
     * @return transformed DE ModelType based on orgModel.
     */
    public ModelType transformOrgModel(OrgModel orgModel, String version) {
        TransfContext tc = new TransfContext(DeFactory.eINSTANCE);
        return transformOrgModel(tc, orgModel, version);
    }

    private ModelType transformOrgModel(final TransfContext tc, OrgModel s,
            String version) {
        return tc.<OrgModel, ModelType> create(DePackage.eINSTANCE
                .getModelType(), s, new CreateFuncton<OrgModel, ModelType>() {
            @Override
            public void create(OrgModel s, ModelType d) {

                        d.setName(s.getName());
                        d.setVersion(version);

                /*
                 * Transform the Capabilities
                 */
                for (Capability sCapability : s.getCapabilities()) {
                    if (sCapability.getCategory() == null) {
                        /*
                         * XPD-7019: add only those Capabilities which do not
                         * belong to any category, as
                         * 'transformCapabilityCategory' will transform
                         * CapabilityCategories and the Capabilities contained
                         * in them.
                         */
                        d.getCapability().add(transformCapability(tc,
                                sCapability));
                    }
                }
                /*
                 * Transform the CapabilityCategory AND the Capabilities
                 * contained in them.
                 */
                for (CapabilityCategory sCapabilityCategories : s
                        .getCapabilityCategories()) {

                    d.getCapabilityCategory()
                            .add(transformCapabilityCategory(tc,
                                    sCapabilityCategories));
                }

                /*
                 * Transform the Privileges
                 */
                for (Privilege sPrivilege : s.getPrivileges()) {

                    if (sPrivilege.getCategory() == null) {
                        /*
                         * XPD-7019: add only those Privileges which do not
                         * belong to any category, as
                         * 'transformPrivilegeCategory' will transform
                         * PrivilegeCategories and the Privileges contained in
                         * them.
                         */
                        d.getPrivilege()
                                .add(transformPrivilege(tc, sPrivilege));
                    }
                }

                /*
                 * Transform the PrivilegeCategory AND the Privileges contained
                 * in them.
                 */
                for (PrivilegeCategory sPrivilegeCategory : s
                        .getPrivilegeCategories()) {

                    d.getPrivilegeCategory().add(transformPrivilegeCategory(tc,
                            sPrivilegeCategory));
                }

                for (Group sGroup : s.getGroups()) {
                    d.getGroup().add(transformGroup(tc, sGroup));
                }
                for (Location sLocation : s.getLocations()) {
                    d.getLocation().add(transformLocation(tc, sLocation));
                }
                for (Organization sOrganization : s.getOrganizations()) {
                    if (!sOrganization.isDynamic()) {
                        d.getOrganization().add(transformOrganization(tc,
                                sOrganization));
                    }
                }
                for (SystemAction sSystemAction : s.getSystemActions()) {
                    d.getSystemAction().add(transformSystemAction(tc,
                            sSystemAction));
                }
                for (Attribute sHumanResAttr : getHumanResourceTypeAttributes(s)) {
                    d.getResourceAttribute().add(transformAttribute(tc,
                            sHumanResAttr));
                }
                if (s.getEmbeddedMetamodel() != null) {
                    d.setMetaModel(transformOrgMetaModel(tc,
                            s.getEmbeddedMetamodel()));
                }
                for (DynamicOrgReference dor : s.getDynamicOrgReferences()) {
                    if (dor.getFrom() != null) {
                        d.getModelTemplate()
                                .add(transformDynamicOrgReference(tc, dor));
                    }
                }

            }

        });
    }

    /**
     * Sets the qualifier value of (capability/privilege) qualified holding.
     */
    private void setNamedElementPart(NamedElement s, NamedEntity d) {
        d.setId(s.getId());
        d.setName(s.getName());
        d.setLabel(s.getDisplayName());
    }

    /**
     * Sets the named element attributes.
     */
    private void setOrgElementPart(OrgElement s, NamedEntity d) {
        setNamedElementPart(s, d);
        d.setDescription(s.getDescription());
    }

    /**
     * Sets the qualifier value of (capability/privilege) qualified holding.
     */
    private void setQalifierValue(QualifiedAssociation s, QualifiedHolding d) {
        String value = null;
        AttributeType type = null;
        List<EnumValue> enumValues = new ArrayList<EnumValue>();

        AttributeValue attrValue = s.getQualifierValue();
        if (attrValue != null && attrValue.getType() != null) {
            value = attrValue.getValue();
            type = attrValue.getType();
            enumValues.addAll(attrValue.getEnumSetValues());
        } else { // fallback to defaultValues
            Attribute attr = null;
            if (s instanceof PrivilegeAssociation) {
                Privilege privilege = ((PrivilegeAssociation) s).getPrivilege();
                attr = privilege.getQualifierAttribute();
            } else if (s instanceof CapabilityAssociation) {
                Capability capability =
                        ((CapabilityAssociation) s).getCapability();
                attr = capability.getQualifierAttribute();
            }
            if (attr != null) {
                value = attr.getDefaultValue();
                type = attr.getType();
                enumValues.addAll(attr.getDefaultEnumSetValues());
            }
        }
        if (value != null && type != null) {
            switch (type) {
            case TEXT:
                d.setString(value);
                break;
            case INTEGER:
                d.setInteger(Long.parseLong(value));
                break;
            case DECIMAL:
                d.setDecimal(new BigDecimal(value));
                break;
            case DATE:
                d.setDate(DateUtil
                        .getXMLGregorianCalendar(value, DateType.DATE));
                break;
            case TIME:
                d.setTime(DateUtil
                        .getXMLGregorianCalendar(value, DateType.TIME));
                break;
            case DATE_TIME:
                d.setDateTime(DateUtil.getXMLGregorianCalendar(value,
                        DateType.DATETIME));
                break;
            case BOOLEAN:
                d.setBoolean(value != null ? Boolean.parseBoolean(value)
                        : false);
                break;
            }
        }
        if (type != null) {
            switch (type) {
            case ENUM:
                if (!enumValues.isEmpty()) {
                    EnumValue enumValue = enumValues.get(0);
                    if (enumValue != null) {
                        d.setEnum(enumValue.getValue());
                    }
                }
                break;
            case ENUM_SET:
                for (EnumValue enumSetValue : enumValues) {
                    if (enumSetValue != null) {
                        d.getEnumSet().add(enumSetValue.getValue());
                    }
                }
                break;
            case SET:
                if (attrValue != null) {
                    for (String setValue : attrValue.getSetValues()) {
                        if (setValue != null) {
                            d.getElement().add(setValue);
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * Sets the base part of org. unit.
     */
    private void setOrgUnitBasePart(final TransfContext tc, OrgUnit s,
            OrgUnitBase d) {
        setOrgElementPart(s, d);
        d.setAllocMethod(transformAllocationMethod(tc, s.getAllocationMethod()));
        d.setPlugin((d.getAllocMethod() == AllocationMethod.PLUGIN) ? s
                .getAllocationMethod() : null);
        if (s.getLocation() != null) {
            d.setLocation(transformLocation(tc, s.getLocation()));
        }
        for (PrivilegeAssociation pa : s.getPrivilegeAssociations()) {
            d.getPrivilegeHeld().add(transformPrivilegeAssociation(tc, pa));
        }
        for (SystemAction sa : s.getSystemActions()) {
            d.getSystemAction().add(transformSystemAction(tc, sa));
        }
        if (s.getFeature() != null) {
            d.setFeature(transformOrgUnitFeature(tc, s.getFeature()));
        }
        if (s.getType() instanceof OrgUnitType) {
            d.setDefinition(transformOrgUnitType(tc, (OrgUnitType) s.getType()));
        }
        setAttributesValues(tc, s, d);
    }

    private List<Attribute> getHumanResourceTypeAttributes(OrgModel s) {
        if (s.getHumanResourceType() == null) {
            return Collections.emptyList();
        } else {
            return s.getHumanResourceType().getAttributes();
        }
    }

    private com.tibco.n2.directory.model.de.Organization transformOrganization(
            final TransfContext tc, Organization s) {
        return tc
                .<Organization, com.tibco.n2.directory.model.de.Organization> create(DePackage.eINSTANCE
                        .getOrganization(),
                        s,
                        new CreateFuncton<Organization, com.tibco.n2.directory.model.de.Organization>() {
                            @Override
                            public void create(
                                    Organization s,
                                    com.tibco.n2.directory.model.de.Organization d) {

                                // Organization
                                setOrgElementPart(s, d);
                                if (s.getLocation() != null) {
                                    d.setLocation(transformLocation(tc,
                                            s.getLocation()));
                                }
                                d.setAllocMethod(transformAllocationMethod(tc,
                                        s.getAllocationMethod()));
                                d.setPlugin((d.getAllocMethod() == AllocationMethod.PLUGIN) ? s
                                        .getAllocationMethod() : null);
                                for (OrgUnit ou : s.getSubUnits()) {
                                    d.getOrgUnit()
                                            .add(transformOrgUnit(tc, ou));
                                }
                                if (s.getType() instanceof OrganizationType) {
                                    d.setDefinition(transformOrganizationType(tc,
                                            (OrganizationType) s.getType()));
                                }
                                setAttributesValues(tc, s, d);
                                // Organization

                            }

                        });
    }

    private com.tibco.n2.directory.model.de.Position transformPosition(
            final TransfContext tc, Position s) {
        return tc
                .<Position, com.tibco.n2.directory.model.de.Position> create(DePackage.eINSTANCE
                        .getPosition(),
                        s,
                        new CreateFuncton<Position, com.tibco.n2.directory.model.de.Position>() {
                            @Override
                            public void create(Position s,
                                    com.tibco.n2.directory.model.de.Position d) {
                                // Position
                                setOrgElementPart(s, d);
                                d.setIdealNumber(s.getIdealNumber());
                                d.setAllocMethod(transformAllocationMethod(tc,
                                        s.getAllocationMethod()));
                                d.setPlugin((d.getAllocMethod() == AllocationMethod.PLUGIN) ? s
                                        .getAllocationMethod() : null);
                                if (s.getLocation() != null) {
                                    d.setLocation(transformLocation(tc,
                                            s.getLocation()));
                                }
                                for (CapabilityAssociation ca : s
                                        .getCapabilitiesAssociations()) {
                                    d.getReqCapability()
                                            .add(transformCapabilityAssociation(tc,
                                                    ca));
                                }
                                for (PrivilegeAssociation pa : s
                                        .getPrivilegeAssociations()) {
                                    d.getPrivilegeHeld()
                                            .add(transformPrivilegeAssociation(tc,
                                                    pa));
                                }
                                for (SystemAction sa : s.getSystemActions()) {
                                    d.getSystemAction()
                                            .add(transformSystemAction(tc, sa));
                                }
                                if (s.getFeature() != null) {
                                    d.setFeature(transformPositionFeature(tc,
                                            s.getFeature()));
                                }
                                if (s.getType() instanceof PositionType) {
                                    d.setDefinition(transformPositionType(tc,
                                            (PositionType) s.getType()));
                                }
                                setAttributesValues(tc, s, d);
                                // Position
                            }
                        });
    }

    private com.tibco.n2.directory.model.de.OrgUnit transformOrgUnit(
            final TransfContext tc, OrgUnit s) {
        return tc
                .<OrgUnit, com.tibco.n2.directory.model.de.OrgUnit> create(DePackage.eINSTANCE
                        .getOrgUnit(),
                        s,
                        new CreateFuncton<OrgUnit, com.tibco.n2.directory.model.de.OrgUnit>() {
                            @Override
                            public void create(OrgUnit s,
                                    com.tibco.n2.directory.model.de.OrgUnit d) {
                                // OrgUnit
                                setOrgUnitBasePart(tc, s, d);
                                for (Position p : s.getPositions()) {
                                    d.getPosition()
                                            .add(transformPosition(tc, p));
                                }
                                for (OrgUnit ou : s.getSubUnits()) {
                                    if (!(ou instanceof DynamicOrgUnit)) {
                                        d.getOrgUnit().add(transformOrgUnit(tc,
                                                ou));
                                    }
                                }
                                // OrgUnit
                            }
                        });
    }

    private ModelTemplate transformDynamicOrgReference(final TransfContext tc,
            final DynamicOrgReference dynamicOrgRef) {
        /*
         * Gets the parent of dynamic OU ('from' reference of
         * DynamicOrgReference obj.), and links its destination counterpart to
         * ModelTemplate which is being created.
         * 
         * ModelTemplate corresponds to the root OU (should only be one) of the
         * dynamic organization ('to' reference of DynamicOrgReference is used
         * to obtain this dynamic organization.)
         */
        DynamicOrgUnit dynamicOU = dynamicOrgRef.getFrom();
        Assert.isTrue(dynamicOU != null,
                "Dynamic Org Unit is not linked to dynamic organization."); //$NON-NLS-1$
        OrgUnit parent = dynamicOU.getParentOrgUnit();
        Assert.isTrue(parent != null, "Dynamic org unit parent org is not set."); //$NON-NLS-1$
        final com.tibco.n2.directory.model.de.OrgUnit destParent =
                transformOrgUnit(tc, parent);

        final Organization dynamicOrg = dynamicOrgRef.getTo();
        EList<OrgUnit> rootUnits = dynamicOrg.getSubUnits();
        Assert.isTrue(rootUnits.size() == 1,
                "Dynamic org can only have one child OU."); //$NON-NLS-1$
        OrgUnit s = rootUnits.get(0); // Source of ModelTemplete

        ModelTemplate modelTemplate =
                tc.<OrgUnit, ModelTemplate> create(DePackage.eINSTANCE
                        .getModelTemplate(),
                        s,
                        new CreateFuncton<OrgUnit, ModelTemplate>() {
                            @Override
                            public void create(OrgUnit s, ModelTemplate d) {

                                for (DynamicOrgIdentifier dynamicOrgId : dynamicOrg
                                        .getDynamicOrgIdentifiers()) {
                                    d.getInstanceIdAttribute().add(dynamicOrgId
                                            .getName());
                                }
                                // OrgUnit
                                setOrgUnitBasePart(tc, s, d);
                                for (OrgUnit ou : s.getSubUnits()) {
                                    d.getModelOrgUnit()
                                            .add(transformModelOrgUnit(tc, ou));
                                }
                                for (Position p : s.getPositions()) {
                                    d.getModelPosition()
                                            .add(transformPosition(tc, p));
                                }
                                // OrgUnit
                            }

                        });
        destParent.setModelTemplateRef(modelTemplate);
        return modelTemplate;
    }

    private com.tibco.n2.directory.model.de.ModelOrgUnit transformModelOrgUnit(
            final TransfContext tc, OrgUnit s) {
        return tc
                .<OrgUnit, com.tibco.n2.directory.model.de.ModelOrgUnit> create(DePackage.eINSTANCE
                        .getModelOrgUnit(),
                        s,
                        new CreateFuncton<OrgUnit, com.tibco.n2.directory.model.de.ModelOrgUnit>() {
                            @Override
                            public void create(
                                    OrgUnit s,
                                    com.tibco.n2.directory.model.de.ModelOrgUnit d) {
                                // OrgUnit
                                setOrgUnitBasePart(tc, s, d);
                                for (OrgUnit ou : s.getSubUnits()) {
                                    d.getModelOrgUnit()
                                            .add(transformModelOrgUnit(tc, ou));
                                }
                                for (Position p : s.getPositions()) {
                                    d.getModelPosition()
                                            .add(transformPosition(tc, p));
                                }
                                // OrgUnit
                            }
                        });
    }

    private com.tibco.n2.directory.model.de.Location transformLocation(
            final TransfContext tc, Location s) {
        return tc
                .<Location, com.tibco.n2.directory.model.de.Location> create(DePackage.eINSTANCE
                        .getLocation(),
                        s,
                        new CreateFuncton<Location, com.tibco.n2.directory.model.de.Location>() {
                            @Override
                            public void create(Location s,
                                    com.tibco.n2.directory.model.de.Location d) {
                                setOrgElementPart(s, d);
                                d.setLocale(s.getLocale());
                                d.setTimezone(d.getTimezone());
                                if (s.getType() instanceof LocationType) {
                                    d.setDefinition(transformLocationType(tc,
                                            (LocationType) s.getType()));
                                }
                                setAttributesValues(tc, s, d);
                            }
                        });
    }

    /**
     * Sets attributes values on typed entity. It also investigates OM element
     * type and if it has attributes with default values set then the
     * corresponding attribute values will be added to destination typed entity.
     * 
     * @param tc
     *            transformation context
     * @param s
     *            source typed organization element.
     * @param d
     *            destination typed entity.
     */
    private void setAttributesValues(TransfContext tc, OrgTypedElement s,
            TypedEntity d) {
        if (s.getType() == null) { // Untyped entity
            return;
        }
        for (Attribute attr : s.getType().getAttributes()) {
            AttributeValue attrValue = findAttributeValue(s, attr);
            if (Arrays.asList(AttributeType.ENUM, AttributeType.ENUM_SET)
                    .contains(attr.getType())) {
                /* Enum types. */
                if (attrValue != null) {
                    setAttributeEnumAndOrSetValue(d,
                            tc,
                            getStringValues(attrValue.getEnumSetValues()),
                            attrValue.getType(),
                            attrValue.getAttribute());
                } else if (!attr.getDefaultEnumSetValues().isEmpty()) {
                    // default value
                    setAttributeEnumAndOrSetValue(d,
                            tc,
                            getStringValues(attr.getDefaultEnumSetValues()),
                            attr.getType(),
                            attr);
                }
            } else if (attr.getType() == AttributeType.SET) {
                if (attrValue != null) {
                    setAttributeEnumAndOrSetValue(d,
                            tc,
                            attrValue.getSetValues(),
                            attrValue.getType(),
                            attrValue.getAttribute());
                }
            } else if (attr.getType() != null) {
                /* Simple types. */
                if (attrValue != null) {
                    setAttributeSimpleValue(d,
                            tc,
                            attrValue.getValue(),
                            attrValue.getType(),
                            attrValue.getAttribute());
                } else if (attr.getDefaultValue() != null) {
                    // default value
                    setAttributeSimpleValue(d,
                            tc,
                            attr.getDefaultValue(),
                            attr.getType(),
                            attr);
                }
            }
        }
    }

    /**
     * Extract the strings from the enumvalues.
     * 
     * @param values
     * @return
     */
    private List<String> getStringValues(List<EnumValue> values) {
        List<String> toRet = new ArrayList<String>(values.size());

        for (EnumValue value : values) {
            if (value.getValue() != null) {
                toRet.add(value.getValue());
            }
        }

        return toRet;
    }

    /**
     * Finds attribute value (if exists) for an attribute on OM typed element.
     */
    private AttributeValue findAttributeValue(OrgTypedElement el, Attribute attr) {
        for (AttributeValue attrValue : el.getAttributeValues()) {
            if (attrValue.getAttribute() == attr) {
                return attrValue;
            }
        }
        return null;
    }

    /**
     * Sets simple type value on typed entity.
     */
    private void setAttributeSimpleValue(TypedEntity typedEntity,
            TransfContext tc, String value, AttributeType type,
            Attribute attribute) {
        if (value != null && type != null) {
            com.tibco.n2.directory.model.de.Attribute d =
                    (com.tibco.n2.directory.model.de.Attribute) tc
                            .newInstance(DePackage.eINSTANCE.getAttribute());
            d.setDefinition(transformAttribute(tc, attribute));
            switch (type) {
            case TEXT:
                d.setString(value);
                break;
            case INTEGER:
                d.setInteger(Long.parseLong(value));
                break;
            case DECIMAL:
                d.setDecimal(new BigDecimal(value));
                break;
            case DATE:
                d.setDate(DateUtil
                        .getXMLGregorianCalendar(value, DateType.DATE));
                break;
            case TIME:
                d.setTime(DateUtil
                        .getXMLGregorianCalendar(value, DateType.TIME));
                break;
            case DATE_TIME:
                d.setDateTime(DateUtil.getXMLGregorianCalendar(value,
                        DateType.DATETIME));
                break;
            case BOOLEAN:
                d.setBoolean(value != null ? Boolean.parseBoolean(value)
                        : false);
                break;
            }
            typedEntity.getAttributeValue().add(d);
        }
    }

    /**
     * Sets enum or enum set type value on typed entity.
     */
    private void setAttributeEnumAndOrSetValue(TypedEntity typedEntity,
            TransfContext tc, List<String> values, AttributeType type,
            Attribute attribute) {
        if (type != null && !values.isEmpty()) {
            com.tibco.n2.directory.model.de.Attribute d =
                    (com.tibco.n2.directory.model.de.Attribute) tc
                            .newInstance(DePackage.eINSTANCE.getAttribute());
            d.setDefinition(transformAttribute(tc, attribute));
            switch (type) {
            case ENUM:
                String value = values.get(0);
                d.setEnum(value);
                break;
            case ENUM_SET:
                for (String enumValue : values) {
                    d.getEnumSet().add(enumValue);
                }
                break;
            case SET:
                for (String setValue : values) {
                    d.getElement().add(setValue);
                }
                break;
            }
            typedEntity.getAttributeValue().add(d);
        }
    }

    private com.tibco.n2.directory.model.de.Group transformGroup(
            final TransfContext tc, Group s) {
        return tc
                .<Group, com.tibco.n2.directory.model.de.Group> create(DePackage.eINSTANCE
                        .getGroup(),
                        s,
                        new CreateFuncton<Group, com.tibco.n2.directory.model.de.Group>() {
                            @Override
                            public void create(Group s,
                                    com.tibco.n2.directory.model.de.Group d) {

                                setOrgElementPart(s, d);
                                for (CapabilityAssociation ca : s
                                        .getCapabilitiesAssociations()) {
                                    d.getReqCapability()
                                            .add(transformCapabilityAssociation(tc,
                                                    ca));
                                }
                                for (PrivilegeAssociation pa : s
                                        .getPrivilegeAssociations()) {
                                    d.getPrivilegeHeld()
                                            .add(transformPrivilegeAssociation(tc,
                                                    pa));
                                }
                                for (SystemAction sa : s.getSystemActions()) {
                                    d.getSystemAction()
                                            .add(transformSystemAction(tc, sa));
                                }
                                d.setAllocMethod(transformAllocationMethod(tc,
                                        s.getAllocationMethod()));
                                d.setPlugin((d.getAllocMethod() == AllocationMethod.PLUGIN) ? s
                                        .getAllocationMethod() : null);
                                for (Group ssg : s.getSubGroups()) {
                                    d.getGroup().add(transformGroup(tc, ssg));
                                }

                            }
                        });
    }

    private com.tibco.n2.directory.model.de.SystemAction transformSystemAction(
            TransfContext tc, SystemAction s) {
        com.tibco.n2.directory.model.de.SystemAction d =
                (com.tibco.n2.directory.model.de.SystemAction) tc
                        .newInstance(DePackage.eINSTANCE.getSystemAction());
        d.setName(s.getActionId());
        d.setComponent(s.getComponent());
        for (PrivilegeAssociation pa : s.getPrivilegeAssociations()) {
            d.getReqPrivilege().add(transformPrivilegeAssociation(tc, pa));
        }
        return d;
    }

    private AllocationMethod transformAllocationMethod(TransfContext tc,
            String allocationMethod) {
        if ("ANY".equals(allocationMethod)) { //$NON-NLS-1$
            return AllocationMethod.ANY;
        } else if ("NEXT".equals(allocationMethod)) { //$NON-NLS-1$
            return AllocationMethod.NEXT;
        } else if ("THIS".equals(allocationMethod)) { //$NON-NLS-1$
            return AllocationMethod.THIS;
        } else {
            return AllocationMethod.PLUGIN;
        }
    }

    private PrivilegeHolding transformPrivilegeAssociation(TransfContext tc,
            PrivilegeAssociation s) {
        PrivilegeHolding d =
                (PrivilegeHolding) tc.newInstance(DePackage.eINSTANCE
                        .getPrivilegeHolding());
        d.setPrivilege(transformPrivilege(tc, s.getPrivilege()));
        setQalifierValue(s, d);
        return d;
    }

    private CapabilityHolding transformCapabilityAssociation(TransfContext tc,
            CapabilityAssociation s) {
        CapabilityHolding d =
                (CapabilityHolding) tc.newInstance(DePackage.eINSTANCE
                        .getCapabilityHolding());
        d.setCapability(transformCapability(tc, s.getCapability()));
        setQalifierValue(s, d);
        return d;
    }

    /**
     * 
     * @param tc
     *            the TransfContext
     * @param s
     *            the Capability Category to Transform
     * @return the Directory Engine Transformed Capability Category
     */
    private com.tibco.n2.directory.model.de.CapabilityCategory transformCapabilityCategory(
            final TransfContext tc, CapabilityCategory s) {
        return tc
                .<CapabilityCategory, com.tibco.n2.directory.model.de.CapabilityCategory> create(DePackage.eINSTANCE
                        .getCapabilityCategory(),
                        s,
                        new CreateFuncton<CapabilityCategory, com.tibco.n2.directory.model.de.CapabilityCategory>() {
                            @Override
                            public void create(
                                    CapabilityCategory s,
                                    com.tibco.n2.directory.model.de.CapabilityCategory d) {
                                /*
                                 * Set the name, Id and label
                                 */
                                setNamedElementPart(s, d);
                                /*
                                 * Transform all the Capabilities contained in
                                 * this CapabilityCategory
                                 */
                                for (Capability sCapability : s.getMembers()) {
                                    d.getCapability()
                                            .add(transformCapability(tc,
                                                    sCapability));
                                }
                                /*
                                 * Reccursively call transformCapabilityCategory
                                 * untill all the Sub-Capabilities are
                                 * transformed
                                 */
                                for (CapabilityCategory sSubCapabilityCategories : s
                                        .getSubCategories()) {

                                    d.getCapabilityCategory()
                                            .add(transformCapabilityCategory(tc,
                                                    sSubCapabilityCategories));
                                }
                            }
                        });
    }

    /**
     * 
     * @param tc
     *            the TransfContext
     * @param s
     *            the Privilege Category to Transform
     * @return the Directory Engine Transformed Privilege Category
     */
    private com.tibco.n2.directory.model.de.PrivilegeCategory transformPrivilegeCategory(
            final TransfContext tc, PrivilegeCategory s) {
        return tc
                .<PrivilegeCategory, com.tibco.n2.directory.model.de.PrivilegeCategory> create(DePackage.eINSTANCE
                        .getPrivilegeCategory(),
                        s,
                        new CreateFuncton<PrivilegeCategory, com.tibco.n2.directory.model.de.PrivilegeCategory>() {
                            @Override
                            public void create(
                                    PrivilegeCategory s,
                                    com.tibco.n2.directory.model.de.PrivilegeCategory d) {
                                /*
                                 * Set the name, Id and label
                                 */
                                setNamedElementPart(s, d);

                                /*
                                 * Transform all the Privileges contained in
                                 * this PrivilegeCategory
                                 */
                                for (Privilege sPrivilege : s.getMembers()) {
                                    d.getPrivilege().add(transformPrivilege(tc,
                                            sPrivilege));
                                }

                                /*
                                 * Reccursively call transformPrivilegeCategory
                                 * untill all the Sub-Privileges are transformed
                                 */
                                for (PrivilegeCategory sSubPrivilegeCategories : s
                                        .getSubCategories()) {

                                    d.getPrivilegeCategory()
                                            .add(transformPrivilegeCategory(tc,
                                                    sSubPrivilegeCategories));
                                }
                            }
                        });

    }

    private com.tibco.n2.directory.model.de.Capability transformCapability(
            final TransfContext tc, Capability s) {
        return tc
                .<Capability, com.tibco.n2.directory.model.de.Capability> create(DePackage.eINSTANCE
                        .getCapability(),
                        s,
                        new CreateFuncton<Capability, com.tibco.n2.directory.model.de.Capability>() {
                            @Override
                            public void create(Capability s,
                                    com.tibco.n2.directory.model.de.Capability d) {

                                setOrgElementPart(s, d);
                                if (s.getQualifierAttribute() != null) {
                                    d.setQualifier(transformsQualifierAttribute(tc,
                                            s.getQualifierAttribute()));
                                }

                            }
                        });
    }

    private com.tibco.n2.directory.model.de.Privilege transformPrivilege(
            final TransfContext tc, Privilege s) {
        return tc
                .<Privilege, com.tibco.n2.directory.model.de.Privilege> create(DePackage.eINSTANCE
                        .getPrivilege(),
                        s,
                        new CreateFuncton<Privilege, com.tibco.n2.directory.model.de.Privilege>() {
                            @Override
                            public void create(Privilege s,
                                    com.tibco.n2.directory.model.de.Privilege d) {

                                setOrgElementPart(s, d);
                                if (s.getQualifierAttribute() != null) {
                                    d.setQualifier(transformsQualifierAttribute(tc,
                                            s.getQualifierAttribute()));
                                }

                            }
                        });

    }

    private com.tibco.n2.directory.model.de.Qualifier transformsQualifierAttribute(
            TransfContext tc, Attribute s) {
        com.tibco.n2.directory.model.de.Qualifier d =
                (com.tibco.n2.directory.model.de.Qualifier) tc
                        .newInstance(DePackage.eINSTANCE.getQualifier());
        d.setDataType(transformAttributeType(tc, s.getType()));
        for (EnumValue value : s.getValues()) {
            d.getEnum().add(value.getValue());
        }
        return d;
    }

    private DataType transformAttributeType(TransfContext tc, AttributeType s) {
        switch (s) {
        case TEXT:
            return DataType.STRING;
        case DECIMAL:
            return DataType.DECIMAL;
        case INTEGER:
            return DataType.INTEGER;
        case BOOLEAN:
            return DataType.BOOLEAN;
        case DATE_TIME:
            return DataType.DATE_TIME;
        case DATE:
            return DataType.DATE;
        case TIME:
            return DataType.TIME;
        case ENUM:
            return DataType.ENUM;
        case ENUM_SET:
            return DataType.ENUM_SET;
        case SET:
            return DataType.SET;
        default:
            return DataType.STRING;
        }
    }

    private MetaModel transformOrgMetaModel(final TransfContext tc,
            OrgMetaModel s) {
        return tc.<OrgMetaModel, MetaModel> create(DePackage.eINSTANCE
                .getMetaModel(),
                s,
                new CreateFuncton<OrgMetaModel, MetaModel>() {
                    @Override
                    public void create(OrgMetaModel s, MetaModel d) {

                        for (LocationType lt : s.getLocationTypes()) {
                            d.getLocationType().add(transformLocationType(tc,
                                    lt));
                        }
                        for (OrganizationType ot : s.getOrganizationTypes()) {
                            d.getOrganizationType()
                                    .add(transformOrganizationType(tc, ot));
                        }
                        for (OrgUnitType out : s.getOrgUnitTypes()) {
                            d.getOrgUnitType()
                                    .add(transformOrgUnitType(tc, out));
                        }
                        for (PositionType pt : s.getPositionTypes()) {
                            d.getPositionType().add(transformPositionType(tc,
                                    pt));
                        }
                    }

                });
    }

    private com.tibco.n2.directory.model.de.AttributeType transformAttribute(
            final TransfContext tc, Attribute s) {
        return tc
                .<Attribute, com.tibco.n2.directory.model.de.AttributeType> create(DePackage.eINSTANCE
                        .getAttributeType(),
                        s,
                        new CreateFuncton<Attribute, com.tibco.n2.directory.model.de.AttributeType>() {
                            @Override
                            public void create(
                                    Attribute s,
                                    com.tibco.n2.directory.model.de.AttributeType d) {
                                setNamedElementPart(s, d);
                                d.setDescription(s.getDescription());
                                d.setDataType(transformAttributeType(tc,
                                        s.getType()));
                                for (EnumValue value : s.getValues()) {
                                    d.getEnum().add(value.getValue());
                                }
                            }
                        });
    }

    private com.tibco.n2.directory.model.de.Feature transformOrgUnitFeature(
            final TransfContext tc, OrgUnitFeature s) {
        return tc
                .<OrgUnitFeature, com.tibco.n2.directory.model.de.Feature> create(DePackage.eINSTANCE
                        .getFeature(),
                        s,
                        new CreateFuncton<OrgUnitFeature, com.tibco.n2.directory.model.de.Feature>() {
                            @Override
                            public void create(OrgUnitFeature s,
                                    com.tibco.n2.directory.model.de.Feature d) {
                                setNamedElementPart(s, d);
                                d.setLowerBound(s.getLowerBound());
                                d.setUpperBound(s.getUpperBound());
                                d.setDefinition(transformOrgUnitType(tc,
                                        s.getFeatureType()));
                            }
                        });
    }

    private com.tibco.n2.directory.model.de.Feature transformPositionFeature(
            final TransfContext tc, PositionFeature s) {
        return tc
                .<PositionFeature, com.tibco.n2.directory.model.de.Feature> create(DePackage.eINSTANCE
                        .getFeature(),
                        s,
                        new CreateFuncton<PositionFeature, com.tibco.n2.directory.model.de.Feature>() {
                            @Override
                            public void create(PositionFeature s,
                                    com.tibco.n2.directory.model.de.Feature d) {
                                setNamedElementPart(s, d);
                                d.setLowerBound(s.getLowerBound());
                                d.setUpperBound(s.getUpperBound());
                                d.setDefinition(transformPositionType(tc,
                                        s.getFeatureType()));
                            }
                        });
    }

    private com.tibco.n2.directory.model.de.PositionType transformPositionType(
            final TransfContext tc, PositionType s) {
        return tc
                .<PositionType, com.tibco.n2.directory.model.de.PositionType> create(DePackage.eINSTANCE
                        .getPositionType(),
                        s,
                        new CreateFuncton<PositionType, com.tibco.n2.directory.model.de.PositionType>() {
                            @Override
                            public void create(
                                    PositionType s,
                                    com.tibco.n2.directory.model.de.PositionType d) {
                                setNamedElementPart(s, d);
                                for (Attribute a : s.getAttributes()) {
                                    d.getAttribute().add(transformAttribute(tc,
                                            a));
                                }
                            }
                        });
    }

    private com.tibco.n2.directory.model.de.OrgUnitType transformOrgUnitType(
            final TransfContext tc, OrgUnitType s) {
        return tc
                .<OrgUnitType, com.tibco.n2.directory.model.de.OrgUnitType> create(DePackage.eINSTANCE
                        .getOrgUnitType(),
                        s,
                        new CreateFuncton<OrgUnitType, com.tibco.n2.directory.model.de.OrgUnitType>() {
                            @Override
                            public void create(
                                    OrgUnitType s,
                                    com.tibco.n2.directory.model.de.OrgUnitType d) {
                                setNamedElementPart(s, d);
                                for (Attribute a : s.getAttributes()) {
                                    d.getAttribute().add(transformAttribute(tc,
                                            a));
                                }
                                for (OrgUnitFeature ouf : s
                                        .getOrgUnitFeatures()) {
                                    d.getOrgUnitFeature()
                                            .add(transformOrgUnitFeature(tc,
                                                    ouf));
                                }
                                for (PositionFeature pf : s
                                        .getPositionFeatures()) {
                                    d.getPositionFeature()
                                            .add(transformPositionFeature(tc,
                                                    pf));
                                }
                            }
                        });
    }

    private com.tibco.n2.directory.model.de.OrganizationType transformOrganizationType(
            final TransfContext tc, OrganizationType s) {
        return tc
                .<OrganizationType, com.tibco.n2.directory.model.de.OrganizationType> create(DePackage.eINSTANCE
                        .getOrganizationType(),
                        s,
                        new CreateFuncton<OrganizationType, com.tibco.n2.directory.model.de.OrganizationType>() {
                            @Override
                            public void create(
                                    OrganizationType s,
                                    com.tibco.n2.directory.model.de.OrganizationType d) {
                                setNamedElementPart(s, d);
                                for (Attribute a : s.getAttributes()) {
                                    d.getAttribute().add(transformAttribute(tc,
                                            a));
                                }
                                for (OrgUnitFeature ouf : s
                                        .getOrgUnitFeatures()) {
                                    d.getOrgUnitFeature()
                                            .add(transformOrgUnitFeature(tc,
                                                    ouf));
                                }
                            }
                        });
    }

    private com.tibco.n2.directory.model.de.LocationType transformLocationType(
            final TransfContext tc, LocationType s) {
        return tc
                .<LocationType, com.tibco.n2.directory.model.de.LocationType> create(DePackage.eINSTANCE
                        .getLocationType(),
                        s,
                        new CreateFuncton<LocationType, com.tibco.n2.directory.model.de.LocationType>() {
                            @Override
                            public void create(
                                    LocationType s,
                                    com.tibco.n2.directory.model.de.LocationType d) {
                                setNamedElementPart(s, d);
                                for (Attribute a : s.getAttributes()) {
                                    d.getAttribute().add(transformAttribute(tc,
                                            a));
                                }
                            }
                        });
    }

}
