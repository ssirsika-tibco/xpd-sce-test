/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * XSD Validation to ensure enum literal names abide to the parent Enumeration
 * restriction pattern and length
 * 
 * @author glewis
 */
public class EnumTextLiteralsNotMeetLengthOrPatternRule extends AbstractXsdRule {

    @Override
    public Class<?> getTargetClass() {
        return Enumeration.class;
    }

    @Override
    public void performXSDValidation(IValidationScope scope, Object o) {
        if (!enabledInCapabilities()) {
            return;
        }

        Enumeration tempEnumeration = (Enumeration) o;

        if (tempEnumeration.getGenerals().size() > 0) {
            Classifier classifier = tempEnumeration.getGenerals().get(0);

            if (classifier instanceof PrimitiveType) {
                PrimitiveType primitiveType = (PrimitiveType) classifier;

                PrimitiveType baseType =
                        PrimitivesUtil.getBasePrimitiveType(primitiveType);

                if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(baseType
                        .getName())) {

                    validateTextTypeEnum(scope,
                            o,
                            tempEnumeration,
                            primitiveType);
                }
            }
        }
    }

    /**
     * @param scope
     * @param o
     * @param tempEnumeration
     * @param primitiveType
     */
    private void validateTextTypeEnum(IValidationScope scope, Object o,
            Enumeration tempEnumeration, PrimitiveType primitiveType) {
        /* validate the pattern */
        validateTextPattern(scope, o, tempEnumeration, primitiveType);
        /* validate the length */
        validateTextLength(scope, tempEnumeration, primitiveType);
    }

    /**
     * @param scope
     * @param tempEnumeration
     * @param primitiveType
     */
    private void validateTextLength(IValidationScope scope,
            Enumeration tempEnumeration, PrimitiveType primitiveType) {

        Object lengthValue =
                PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);

        if (null != lengthValue) {
            EList<EnumerationLiteral> ownedLiterals =
                    tempEnumeration.getOwnedLiterals();

            for (EnumerationLiteral enumerationLiteral : ownedLiterals) {
                int length = enumerationLiteral.getName().length();
                Integer primitiveTypeLength =
                        new Integer((Integer) lengthValue);
                Integer enumLitLength = new Integer(length);

                if (enumLitLength > primitiveTypeLength) {
                    List<String> additionalMessages = new ArrayList<String>();
                    additionalMessages.add(enumerationLiteral.getName());
                    additionalMessages.add(primitiveTypeLength.toString());

                    String location = String.format("%s.%s", //$NON-NLS-1$
                            PrimitivesUtil.getDisplayLabel(tempEnumeration),
                            PrimitivesUtil.getDisplayLabel(enumerationLiteral));
                    String uri =
                            enumerationLiteral.eResource()
                                    .getURIFragment(enumerationLiteral);

                    scope.createIssue(XsdIssueIds.INVALID_ENUMLIT_FOR_LENGTH,
                            location,
                            uri,
                            additionalMessages);
                }
            }
        }
    }

    /**
     * @param scope
     * @param o
     * @param tempEnumeration
     * @param primitiveType
     */
    private void validateTextPattern(IValidationScope scope, Object o,
            Enumeration tempEnumeration, PrimitiveType primitiveType) {

        String regExpression =
                (String) PrimitivesUtil.getFacetPropertyValue(primitiveType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE);
        if (regExpression != null && regExpression.trim().length() > 0) {

            Pattern pattern = Pattern.compile(regExpression);
            EList<EnumerationLiteral> ownedLiterals =
                    tempEnumeration.getOwnedLiterals();
            Iterator<EnumerationLiteral> iterator = ownedLiterals.iterator();

            while (iterator.hasNext()) {
                EnumerationLiteral enumerationLiteral = iterator.next();
                String enumLitName = enumerationLiteral.getName();
                Matcher matcher = pattern.matcher(enumLitName);
                boolean isMatch = matcher.matches();
                /*
                 * XPD-3890 match complete Literal value and not part of it for
                 * matching parts wildcard characters should be used in
                 * patterns.
                 */

                if (!isMatch) {
                    List<String> additionalMessages = new ArrayList<String>();
                    additionalMessages.add(enumLitName);
                    additionalMessages.add(regExpression);
                    String uri =
                            enumerationLiteral.eResource()
                                    .getURIFragment(enumerationLiteral);
                    scope.createIssue(XsdIssueIds.INVALID_ENUMLIT_FOR_PATTERN,
                            BOMValidationUtil.getLocation((NamedElement) o),
                            uri,
                            additionalMessages);
                }
            }
        }
    }
}
