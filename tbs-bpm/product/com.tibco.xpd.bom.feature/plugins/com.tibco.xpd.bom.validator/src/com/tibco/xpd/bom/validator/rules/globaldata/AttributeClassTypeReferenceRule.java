/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.internal.IAdditionalInfoMarkerKeys;
import com.tibco.xpd.bom.validator.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Situations in which a {@link Class} instance has an attribute of type Class
 * are considered to be equivalent to that of modelling explicitly a
 * relationship using unidirectional composition.
 * 
 * This rule is specific to validating attribute-type relationships. The
 * validation could well remain the same as the validation used for that of
 * explicit unidirectional relationships but provides an opportunity for
 * divergence.
 * 
 * @author patkinso
 */
public class AttributeClassTypeReferenceRule implements IValidationRule {

    protected static String INTERBOM_UNIDIRECTIONAL_ISSUE_ID =
            "bom.globaldata.attribute.composition.unidirectional.interbom.issue"; //$NON-NLS-1$

    protected static String INTERBOM_UNIDIRECTIONAL_BDPNONBDP_ISSUE_ID =
            "bom.globaldata.attribute.composition.unidirectional.interbom.bdpnonbdp.issue"; //$NON-NLS-1$

    protected static final String INTRABOM_UNIDIRECTIONAL_ISSUE_ID =
            "bom.globaldata.attribute.composition.unidirectional.intrabom.issue"; //$NON-NLS-1$ 

    protected static final String INTERBOM_ENUMERATION_ISSUE_ID =
            "bom.globaldata.attribute.composition.enumeration.interbom.issue"; //$NON-NLS-1$ 

    protected static final String INTERBOM_ENUMERATION_BDPNONBDP_ISSUE_ID =
            "bom.globaldata.attribute.composition.enumeration.interbom.bdpnonbdp.issue"; //$NON-NLS-1$ 

    protected static final String INTERBOM_PRIMITIVE_TYPE_ISSUE_ID =
            "bom.globaldata.attribute.composition.primitivetype.interbom.issue"; //$NON-NLS-1$ 

    protected static final String INTERBOM_PRIMITIVE_TYPE_BDPNONBDP_ISSUE_ID =
            "bom.globaldata.attribute.composition.primitivetype.interbom.bdpnonbdp.issue"; //$NON-NLS-1$ 

    protected final String CASETYPE_ARG =
            Messages.ClassStereotypes_case_type_issue_arg_message;

    protected final String GLOBALTYPE_ARG =
            Messages.ClassStereotypes_global_type_issue_arg_message;

    protected final String LOCALTYPE_ARG =
            Messages.ClassStereotypes_local_type_issue_arg_message;

    protected final String PARTCLASS_ARG =
            Messages.AggregationEntity_partclass_issue_arg_message;

    protected final String WHOLECLASS_ARG =
            Messages.AggregationEntity_wholeclass_issue_arg_message;

    protected GlobalDataProfileManager gdManager = GlobalDataProfileManager
            .getInstance();

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public java.lang.Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     * 
     * @param scope
     * @param o
     */
    @Override
    public void validate(IValidationScope scope, Object o) {
        Class whole = (Class) o;

        // Only run this validation if the global data profile is applied
        if (!BOMGlobalDataUtils.isGlobalDataBOM(whole.getModel())) {
            return;
        }

        for (Property prop : whole.getOwnedAttributes()) {
            if (UML2ModelUtil.isCompositeProperty(prop)
                    && prop.getAssociation() == null) {
                Type compositeAttrib = prop.getType();
                if (compositeAttrib != null) {
                    if (compositeAttrib instanceof Class) {
                        Class part = (Class) compositeAttrib;
                        if (BOMUtils.hasSameProject(whole, part)) {
                            doIntraBomUnidirectionalValidation(scope,
                                    whole,
                                    part);
                        } else {
                            // check BOM resources for UML Classes can be
                            // determined
                            if (whole.eResource() != null
                                    && part.eResource() != null) {
                                doInterBomUnidirectionalValidation(scope,
                                        whole,
                                        part);
                            }
                        }
                    } else if (compositeAttrib instanceof Enumeration) {
                        // Attributes of case/global classes cannot be
                        // Enumeration type in another BOM
                        // Attributes of local classes cannot be Enumeration
                        // type in a non-Business Data Project
                        // (i.e. local classes _can_ refer to one in another
                        // BDP)

                        Enumeration part = (Enumeration) compositeAttrib;
                        // Now check to ensure they are in the same project
                        if (!BOMUtils.hasSameProject(whole, part.getOwner())) {

                            // Case/global classes are not allowed to refer to
                            // enumerations in other BOMs,
                            // whereas local classes can as long as the other
                            // BOM is in a BDP.
                            if (BOMGlobalDataUtils.isCaseClass(whole)
                                    || BOMGlobalDataUtils.isGlobalClass(whole)) {
                                // Case/global classes are not allowed to
                                // reference enums in another project
                                String displayName =
                                        PrimitivesUtil.getDisplayLabel(prop);
                                scope.createIssue(INTERBOM_ENUMERATION_ISSUE_ID,
                                        prop.eClass().getName(),
                                        prop.eResource().getURIFragment(prop),
                                        Collections.singletonList(displayName));
                            } else {
                                // Local classes can reference enums in other
                                // BOMs, as long as the other BOM is in a BDP.
                                IProject partProj =
                                        WorkingCopyUtil.getProjectFor(part);
                                if (!BOMUtils.isBusinessDataProject(partProj)) {
                                    String displayName =
                                            PrimitivesUtil
                                                    .getDisplayLabel(prop);
                                    scope.createIssue(INTERBOM_ENUMERATION_BDPNONBDP_ISSUE_ID,
                                            prop.eClass().getName(),
                                            prop.eResource()
                                                    .getURIFragment(prop),
                                            Collections
                                                    .singletonList(displayName));
                                }
                            }
                        }
                    } else if (compositeAttrib instanceof PrimitiveType) {
                        // Attributes of case/global classes cannot be Primitive
                        // Type in another BOM
                        // Attributes of local classes cannot be Primitive Type
                        // type in a non-Business Data Project
                        // (i.e. local classes _can_ refer to one in another
                        // BDP)

                        PrimitiveType part = (PrimitiveType) compositeAttrib;
                        // Check if this primitive type is a custom type
                        EList<Classifier> generals = part.getGenerals();
                        if ((generals != null) && !generals.isEmpty()) {
                            // Now check to ensure they are in the same project
                            if (!BOMUtils
                                    .hasSameProject(whole, part.getOwner())) {

                                // Case/global classes are not allowed to refer
                                // to enumerations in other BOMs,
                                // whereas local classes can as long as the
                                // other BOM is in a BDP.
                                if (BOMGlobalDataUtils.isCaseClass(whole)
                                        || BOMGlobalDataUtils
                                                .isGlobalClass(whole)) {
                                    // Case/global classes are not allowed to
                                    // reference enums in another project
                                    String displayName =
                                            PrimitivesUtil
                                                    .getDisplayLabel(prop);
                                    scope.createIssue(INTERBOM_PRIMITIVE_TYPE_ISSUE_ID,
                                            prop.eClass().getName(),
                                            prop.eResource()
                                                    .getURIFragment(prop),
                                            Collections
                                                    .singletonList(displayName));
                                } else {
                                    // Local classes can reference enums in
                                    // other BOMs, as long as the
                                    // other BOM is in a BDP.
                                    IProject partProj =
                                            WorkingCopyUtil.getProjectFor(part);
                                    if (!BOMUtils
                                            .isBusinessDataProject(partProj)) {
                                        String displayName =
                                                PrimitivesUtil
                                                        .getDisplayLabel(prop);
                                        scope.createIssue(INTERBOM_PRIMITIVE_TYPE_BDPNONBDP_ISSUE_ID,
                                                prop.eClass().getName(),
                                                prop.eResource()
                                                        .getURIFragment(prop),
                                                Collections
                                                        .singletonList(displayName));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param scope
     */
    private void doIntraBomUnidirectionalValidation(IValidationScope scope,
            Class whole, Class part) {

        Set<StereotypeKind> wholeclassTypes =
                gdManager.getAppliedStereotypeKinds(whole);
        Set<StereotypeKind> partclassTypes =
                gdManager.getAppliedStereotypeKinds(part);

        String[] msgParams = null;

        if (wholeclassTypes.contains(StereotypeKind.CASE)) {
            if (partclassTypes.contains(StereotypeKind.CASE)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, CASETYPE_ARG };
            } else if (partclassTypes.contains(StereotypeKind.GLOBAL)) {
                // allowed
            } else { // part class is LOCAL
                // disallowed: part should have mutated to 'global'
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, LOCALTYPE_ARG };
            }
        } else if (wholeclassTypes.contains(StereotypeKind.GLOBAL)) {
            if (partclassTypes.contains(StereotypeKind.CASE)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, CASETYPE_ARG };
            } else if (partclassTypes.contains(StereotypeKind.GLOBAL)) {
                // allowed
            } else { // part class is LOCAL
                // disallowed: part should have mutated to 'global'
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, LOCALTYPE_ARG };
            }
        } else { // whole class is LOCAL
            if (partclassTypes.contains(StereotypeKind.CASE)) {
                // allowed
            } else if (partclassTypes.contains(StereotypeKind.GLOBAL)) {
                // allowed
            } else { // part class is LOCAL
                // allowed
            }
        }

        if (msgParams != null && msgParams.length == 4) {

            Property compositeOwnedAttribute =
                    getCompositeOwnedAttribute(whole, part);

            Map<String, String> additionalInfo = new HashMap<String, String>();
            additionalInfo
                    .put(IAdditionalInfoMarkerKeys.TARGETS_RESOURCE_URI_LOCATION,
                            compositeOwnedAttribute.eResource().getURI()
                                    .toPlatformString(true));
            additionalInfo
                    .put(IAdditionalInfoMarkerKeys.TARGETS_FRAGMENT_URI_LOCATION,
                            compositeOwnedAttribute.eResource()
                                    .getURIFragment(compositeOwnedAttribute));

            List<String> paramList = new ArrayList<String>();
            paramList.addAll(Arrays.asList(msgParams));
            paramList.add(compositeOwnedAttribute.getName());

            scope.createIssue(INTRABOM_UNIDIRECTIONAL_ISSUE_ID,
                    whole.eClass().getName(),
                    whole.eResource().getURIFragment(compositeOwnedAttribute),
                    paramList,
                    additionalInfo);
        }

    }

    /**
     * @param scope
     * @param whole
     * @param part
     */
    private void doInterBomUnidirectionalValidation(IValidationScope scope,
            Class whole, Class part) {

        Set<StereotypeKind> wholeclassTypes =
                gdManager.getAppliedStereotypeKinds(whole);
        Set<StereotypeKind> partclassTypes =
                gdManager.getAppliedStereotypeKinds(part);

        String[] msgParams = null;

        boolean wholeBDPAndPartNonBDP = false;

        if (wholeclassTypes.contains(StereotypeKind.CASE)) {
            if (partclassTypes.contains(StereotypeKind.CASE)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, CASETYPE_ARG };
            } else if (partclassTypes.contains(StereotypeKind.GLOBAL)) {
                // Case Class To Global Compositions are allowed across Projects
            } else { // part class is LOCAL
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, CASETYPE_ARG,
                                PARTCLASS_ARG, LOCALTYPE_ARG };
            }
        } else if (wholeclassTypes.contains(StereotypeKind.GLOBAL)) {
            if (partclassTypes.contains(StereotypeKind.CASE)) {
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, CASETYPE_ARG };
            } else if (partclassTypes.contains(StereotypeKind.GLOBAL)) {
                // Global To Global Compositions are allowed across Projects
            } else { // part class is LOCAL
                // disallowed!
                msgParams =
                        new String[] { WHOLECLASS_ARG, GLOBALTYPE_ARG,
                                PARTCLASS_ARG, LOCALTYPE_ARG };
            }
        } else {
            // Whole class is local.
            // This is generally allowed, but disallowed if the whole class
            // resides in a BDP and the part class does not.
            IProject wholeclassProj = WorkingCopyUtil.getProjectFor(whole);
            if (BOMUtils.isBusinessDataProject(wholeclassProj)) {
                IProject partclassProj = WorkingCopyUtil.getProjectFor(part);
                if (!BOMUtils.isBusinessDataProject(partclassProj)) {
                    // Message is of a different format (spec stated this)
                    msgParams =
                            new String[] { whole.getName(), part.getName() };
                    // Set flag so we use correct issue id.
                    wholeBDPAndPartNonBDP = true;
                }
            }
        }

        if (msgParams != null) {

            Map<String, String> additionalInfo = new HashMap<String, String>();

            Property compositeOwnedAttribute =
                    getCompositeOwnedAttribute(whole, part);
            additionalInfo
                    .put(IAdditionalInfoMarkerKeys.SOURCE_FRAGMENT_URI_LOCATION,
                            compositeOwnedAttribute.eResource()
                                    .getURIFragment(compositeOwnedAttribute));

            List<String> paramList = new ArrayList<String>();
            paramList.addAll(Arrays.asList(msgParams));
            paramList.add(compositeOwnedAttribute.getName());

            // Use different issue id for the "whole in BDP, part in non-BDP"
            // scenario.
            String issueId =
                    wholeBDPAndPartNonBDP ? INTERBOM_UNIDIRECTIONAL_BDPNONBDP_ISSUE_ID
                            : INTERBOM_UNIDIRECTIONAL_ISSUE_ID;

            scope.createIssue(issueId,
                    whole.eClass().getName(),
                    whole.eResource().getURIFragment(compositeOwnedAttribute),
                    paramList,
                    additionalInfo);
        }
    }

    /**
     * @param whole
     * @param part
     * @return
     */
    private Property getCompositeOwnedAttribute(Class whole, Class part) {

        for (Property prop : whole.getOwnedAttributes()) {
            if (UML2ModelUtil.isCompositeProperty(prop)) {
                Type _part = prop.getType();
                if (part.equals(_part)) {
                    return prop;
                }
            }
        }

        return null;
    }

}
