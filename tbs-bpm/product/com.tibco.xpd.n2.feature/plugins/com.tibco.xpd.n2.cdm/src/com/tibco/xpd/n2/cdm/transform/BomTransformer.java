/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.n2.cdm.transform;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bpm.da.dm.api.Attribute;
import com.tibco.bpm.da.dm.api.BaseType;
import com.tibco.bpm.da.dm.api.DataModel;
import com.tibco.bpm.da.dm.api.IdentifierInitialisationInfo;
import com.tibco.bpm.da.dm.api.Link;
import com.tibco.bpm.da.dm.api.LinkEnd;
import com.tibco.bpm.da.dm.api.StateModel;
import com.tibco.bpm.da.dm.api.StructuredType;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.modeler.custom.terminalstates.TerminalStateProperties;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.n2.cdm.internal.Messages;

/**
 * Transforms BOM model to Case Data Model (CDM).
 *
 * @author jarciuch
 * @since 5 Mar 2019
 */
public class BomTransformer {

    /**
     * Name of the system property that if set to 'true" will allow to retrieve
     * fully qualified type names from the unresolved proxys.
     */
    private static final String ALLOW_EXPORT_BOM_UNRESOLVED_PROXY =
            "allowExportBomUnresolvedProxy"; //$NON-NLS-1$

    /** Transforms BOM constraints to CDM constraints. */
    private static final BomConstraintTransformer CONSTRANT_TRANSFORMER =
            new BomConstraintTransformer();

    private static final TerminalStateProperties TERM_STATES_PROPS =
            new TerminalStateProperties();

    /**
     * Prefix for CDM base type.
     */
    private final static String BASE_PREFIX = "base:"; //$NON-NLS-1$

    /* CDM base type literals. */
    private final static String CDM_BASE_TEXT_TYPE =
            BASE_PREFIX + BaseType.TEXT.getName();

    private final static String CDM_BASE_NUMBER_TYPE =
            BASE_PREFIX + BaseType.NUMBER.getName();

    private final static String CDM_BASE_FIXED_POINT_NUMBER_TYPE =
            BASE_PREFIX + BaseType.FIXED_POINT_NUMBER.getName();

    private final static String CDM_BASE_DATE_TYPE =
            BASE_PREFIX + BaseType.DATE.getName();

    private final static String CDM_BASE_TIME_TYPE =
            BASE_PREFIX + BaseType.TIME.getName();

    private final static String CDM_BASE_DATE_TIME_TZ_TYPE =
            BASE_PREFIX + BaseType.DATE_TIME_TZ.getName();

    private final static String CDM_BASE_BOOLEAN_TYPE =
            BASE_PREFIX + BaseType.BOOLEAN.getName();

    private final static String CDM_BASE_URI_TYPE =
            BASE_PREFIX + BaseType.URI.getName();

    /**
     * Transforms BOM model.
     * 
     * @param bomModel
     *            the source BOM model.
     * @return the Case Data Model.
     */
    public DataModel transformBomModel(Model bomModel) {
        DataModel cdmModel = DataModel.newDataModel();
        cdmModel.setNamespace(bomModel.getName());
        for (PackageableElement packageableElement : bomModel
                .getPackagedElements()) {
            if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                Class bomClass = (Class) packageableElement;
                transformClass(bomClass, cdmModel);
            }
            if (packageableElement instanceof Association) {
                Association bomAssociation = (Association) packageableElement;
                boolean is2WayAssociation =
                        bomAssociation.getOwnedEnds().isEmpty();
                if (is2WayAssociation) {
                    transformAssociation(bomAssociation, cdmModel);
                }
            }
        }
        return cdmModel;
    }

    /**
     * Transforms BOM class to structured class.
     * 
     * @param bomClass
     *            the source BOM class.
     * @param cdmModel
     *            the Case Data Model.
     * @return StructuredType representing transformed BOM class.
     */
    private StructuredType transformClass(Class bomClass, DataModel cdmModel) {
        StructuredType cdmType = cdmModel.newStructuredType();
        cdmType.setName(bomClass.getName());
        cdmType.setLabel(getLabel(bomClass));
        cdmType.setDescription(getFirstOwnedComment(bomClass));
        cdmType.setIsCase(BOMGlobalDataUtils.isCaseClass(bomClass));

        // Attributes - only properties with aggregation="composite".
        bomClass.getAttributes().stream()
                .filter(attr -> AggregationKind.COMPOSITE_LITERAL
                        .equals(attr.getAggregation()))
                .forEach(attr -> transformAttribute(attr, cdmType));

        // Features of StructuredType like: 'stateModel' and
        // 'identifierInitialisationInfo' are set in attribute
        // transformation as they depend on specific attributes.
        return cdmType;
    }

    /**
     * Transforms BOM Property (aka attribute). The resulting attribute is also
     * added to a cdmType.
     * 
     * @param bomAttribute
     *            the BOM attribute to be transformed.
     * @param cdmType
     *            the CDM type that will contain the transformed attribute.
     * @return the CDM attribute.
     */
    private Attribute transformAttribute(Property bomAttribute,
            StructuredType cdmType) {
        Attribute cdmAttribute = cdmType.newAttribute();
        cdmAttribute.setName(bomAttribute.getName());
        cdmAttribute.setLabel(getLabel(bomAttribute));
        cdmAttribute.setDescription(getFirstOwnedComment(bomAttribute));
        String cdmAttributeType =
                transformType(bomAttribute.getType(), bomAttribute);
        cdmAttribute.setType(cdmAttributeType);
        cdmAttribute.setIsMandatory(isMandatory(bomAttribute));
        cdmAttribute.setIsArray(isArray(bomAttribute));
        CONSTRANT_TRANSFORMER.getContraints(bomAttribute).stream().forEach(
                c -> cdmAttribute.newConstraint(c.getName(), c.getValue()));

        boolean isStateAttribute = BOMGlobalDataUtils.isCaseState(bomAttribute);
        if (!isStateAttribute) {
            // Only set the allowed values for non-state attributes.
            CONSTRANT_TRANSFORMER.getAllowedValues(bomAttribute).stream()
                    .forEach(literal -> cdmAttribute.newAllowedValue(
                            /* label */ getLabel(literal),
                            /* value */ literal.getName()));
        }
        String defaultValue =
                CONSTRANT_TRANSFORMER.getDefaultValue(bomAttribute);
        if (defaultValue != null) {
            cdmAttribute.setDefaultValue(defaultValue);
        }

        // Global/Case data aspects.
        if (BOMGlobalDataUtils.isCID(bomAttribute)) {
            // Case Identifier
            cdmAttribute.setIsIdentifier(true);
            cdmAttribute.setIsSummary(true);
            cdmAttribute.setIsSearchable(true);
            if (BOMGlobalDataUtils.isAutoCID(bomAttribute)) {
                IdentifierInitialisationInfo idInfo =
                        cdmType.newIdentifierInitialisationInfo();
                // minDigits
                Object minDigits =
                        BOMGlobalDataUtils.getAutoCidPropetyValue(bomAttribute,
                                BOMGlobalDataUtils.AutoCidProperty.MIN_DIGITS);
                if (minDigits instanceof Integer) {
                    idInfo.setMinNumLength(((Integer) minDigits).intValue());
                }

                // prefix
                Object prefix =
                        BOMGlobalDataUtils.getAutoCidPropetyValue(bomAttribute,
                                BOMGlobalDataUtils.AutoCidProperty.PREFIX);
                if (prefix instanceof String) {
                    idInfo.setPrefix((String) prefix);
                }

                // suffix
                Object suffix =
                        BOMGlobalDataUtils.getAutoCidPropetyValue(bomAttribute,
                                BOMGlobalDataUtils.AutoCidProperty.SUFFIX);
                if (suffix instanceof String) {
                    idInfo.setSuffix((String) suffix);
                }
            }
        } else if (isStateAttribute) {
            // Case State
            cdmAttribute.setIsState(true);
            cdmAttribute.setIsSummary(true);
            cdmAttribute.setIsSearchable(true);

            List<EnumerationLiteral> terminalStates =
                    TERM_STATES_PROPS.getTerminalStates(bomAttribute);
            terminalStates = (terminalStates != null ? terminalStates
                    : Collections.emptyList());
            Collection<String> terminalStateNames = terminalStates.stream()
                    .map(state -> state.getName()).collect(Collectors.toSet());
            Collection<EnumerationLiteral> states =
                    CONSTRANT_TRANSFORMER.getAllowedValues(bomAttribute);
            if (!states.isEmpty()) {
                StateModel stateModel = cdmType.newStateModel();
                states.stream().forEach(state -> {
                    boolean isTerminal =
                            terminalStateNames.contains(state.getName());
                    stateModel.newState(getLabel(state),
                            state.getName(),
                            isTerminal);
                });
            }
        } else {
            // Searchable and Summary attribute facets.
            cdmAttribute.setIsSearchable(
                    BOMGlobalDataUtils.isSearchable(bomAttribute));
            cdmAttribute
                    .setIsSummary(BOMGlobalDataUtils.isSummary(bomAttribute));
        }

        return cdmAttribute;
    }

    /**
     * Transforms BOM type to CDM string type representation.
     * 
     * @param bomType
     *            the BOM type.
     * @param contextProperty
     *            context bom property that has the type.
     * @return the string representation of a CDM type.
     */
    private String transformType(Type bomType, Property contextProperty) {
        if (bomType instanceof PrimitiveType) {
            PrimitiveType baseType = PrimitivesUtil
                    .getBasePrimitiveType((PrimitiveType) bomType);
            switch (baseType.getName()) {
            case PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME:
                return CDM_BASE_TEXT_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME:
                if (isFixedPointDecimal(contextProperty)) {
                    return CDM_BASE_FIXED_POINT_NUMBER_TYPE;
                } else {
                    return CDM_BASE_NUMBER_TYPE;
                }
            case PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME:
                return CDM_BASE_FIXED_POINT_NUMBER_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME:
                return CDM_BASE_BOOLEAN_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME:
                return CDM_BASE_DATE_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME:
                return CDM_BASE_TIME_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_ENUMERATION_NAME:
                return CDM_BASE_TEXT_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME:
                return CDM_BASE_DATE_TIME_TZ_TYPE;
            case PrimitivesUtil.BOM_PRIMITIVE_URI_NAME:
                return CDM_BASE_URI_TYPE;
            default:
                return CDM_BASE_TEXT_TYPE; // $NON-NLS-1$
            }
        } else if (bomType instanceof Class) {
            if (!bomType.eIsProxy()) {
                String typePackageName = bomType.getPackage().getName();
                String typeName = bomType.getName();
                String localPackageName = getPackageName(contextProperty);
                if (localPackageName != null
                        && localPackageName.equals(typePackageName)) {
                    return typeName;
                }
                return typePackageName + BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR
                        + typeName;
            } else {

                // Unresolved proxy situation. Should never happen if all
                // necessary referenced files are in the workspace.
                String javaFQType = getTypeNameFromUnresolvedProxy(bomType);
                boolean allowBomUnresolvedProxy = Boolean.getBoolean(
                        System.getProperty(ALLOW_EXPORT_BOM_UNRESOLVED_PROXY,
                                "false")); //$NON-NLS-1$
                if (javaFQType != null && allowBomUnresolvedProxy) {
                    return javaFQType;
                }
                String errorMessage =
                        String.format(Messages.BomTransformer_cantResolveType,
                                (javaFQType != null) ? javaFQType : ""); //$NON-NLS-1$
                throw new RuntimeException(errorMessage);
            }
        } else if (bomType instanceof Enumeration) {
            return CDM_BASE_TEXT_TYPE; // $NON-NLS-1$
        }
        return null;
    }

    /**
     * Transforms BOM {@link Association} to a CDM link.
     * 
     * @param bomAssociation
     *            the BOM association.
     * @param cdmModel
     *            the context CDM model.
     * @return the {@link Link} created for the association.
     */
    private Link transformAssociation(Association bomAssociation,
            DataModel cdmModel) {
        EList<Property> memberEnds = bomAssociation.getMemberEnds();
        if (memberEnds.size() == 2) {
            Property bomEnd1 = memberEnds.get(0);
            Property bomEnd2 = memberEnds.get(1);
            if (bomEnd1 != null && bomEnd2 != null) {
                Link cdmLink = cdmModel.newLink();

                LinkEnd cdmEnd1 = cdmLink.getEnd1();
                cdmEnd1.setName(bomEnd1.getName());
                cdmEnd1.setLabel(getLabel(bomEnd1));
                cdmEnd1.setOwner(
                        transformType((Type) bomEnd1.getOwner(), bomEnd1));
                cdmEnd1.setIsArray(isArray(bomEnd1));

                LinkEnd cdmEnd2 = cdmLink.getEnd2();
                cdmEnd2.setName(bomEnd2.getName());
                cdmEnd2.setLabel(getLabel(bomEnd2));
                cdmEnd2.setOwner(
                        transformType((Type) bomEnd2.getOwner(), bomEnd2));
                cdmEnd2.setIsArray(isArray(bomEnd2));

                return cdmLink;
            }
        }
        throw new AssertionError(String.format("Invalid association: %s", //$NON-NLS-1$
                bomAssociation.toString()));
    }

    /**
     * Returns package name of the attribute or null.
     * 
     * @param attribute
     *            the attribute
     * @return package name of an attribute or null.
     */
    private String getPackageName(Property attribute) {
        if (attribute.getOwner() instanceof Type
                && ((Type) attribute.getOwner()).getPackage() != null) {
            return ((Type) attribute.getOwner()).getPackage().getName();
        }
        return null;
    }

    /**
     * Gets first comment body from an element.
     * 
     * @param element
     *            element.
     * @return body of the first element's comment or 'null'.
     */
    private String getFirstOwnedComment(Element element) {
        if (element != null && !element.getOwnedComments().isEmpty()) {
            Comment firstComment = element.getOwnedComments().get(0);
            return firstComment != null ? firstComment.getBody() : null;
        }
        return null;
    }

    /** Gets label for a BOM named element. */
    private String getLabel(NamedElement element) {
        return PrimitivesUtil.getDisplayLabel(element);
    }

    /**
     * Gets the name of the type from unresolved proxy.
     * 
     * @param proxy
     *            the proxy of a type.
     * @return fully qualified name of the type or null if the type information
     *         is missing in the proxy.
     */
    private String getTypeNameFromUnresolvedProxy(Type proxy) {
        // Try to get the type from proxy.
        URI proxyURI = ((InternalEObject) proxy).eProxyURI();
        if (proxyURI != null) {
            String fragment = URI.decode(proxyURI.fragment());
            // The fragment is in form: <objectId>?<package>::<class>?
            int startIndex = fragment.indexOf('?') + 1;
            int endIndex = fragment.lastIndexOf('?');
            if (startIndex != -1 && endIndex != -1) {
                String umlFQType = fragment.substring(startIndex, endIndex);
                String javaFQType =
                        umlFQType.replace(BOMWorkingCopy.UML_PACKAGE_SEPARATOR,
                                BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR);
                return javaFQType;
            }
        }
        return null; // $NON-NLS-1$
    }

    /**
     * Returns true if BOM attribute is mandatory.
     * 
     * @param bomAttribute
     *            the bom attribute.
     * @return true if BOM attribute is mandatory.
     */
    private boolean isMandatory(Property bomAttribute) {
        return bomAttribute.getLower() != 0;
    }

    /**
     * Returns true if BOM attribute is array.
     * 
     * @param bomAttribute
     *            the bom attribute.
     * @return true if BOM attribute is array.
     */
    private boolean isArray(Property bomAttribute) {
        int upper = bomAttribute.getUpper();
        // -1 means +infinity (unbounded).
        return upper == -1 || upper > 1;
    }

    /**
     * Returns <code>true</code> if the Decimal property has a sub-type of
     * fixedPoint.
     * 
     * @param type
     *            the base primitive type of the property.
     * @param property
     *            the property.
     * @return <code>true</code> if the Decimal property has a sub-type of
     *         fixedPoint.
     */
    /* package */ static boolean isFixedPointDecimal(Property property) {
        if (property.getType() instanceof PrimitiveType) {
            Object decimalSubType = PrimitivesUtil.getFacetPropertyValue(
                    (PrimitiveType) property.getType(),
                    PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                    property,
                    /* falbackToBaseType */ true);
            return (decimalSubType instanceof EnumerationLiteral)
                    && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT.equals(
                            ((EnumerationLiteral) decimalSubType).getName());
        }
        return false;
    }

}
