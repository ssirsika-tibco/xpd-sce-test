/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.modeler.custom.enumlitext.DomainValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.SingleValue;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * XSD Validation to see if an Enumeration subclasses another Enumeration and
 * fails if so
 * 
 * 
 * @author glewis
 */
public class EnumerationGeneralisingEnemerationRule extends AbstractXsdRule {

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
        String uri =
                tempEnumeration.eResource().getURIFragment(tempEnumeration);

        if (tempEnumeration.getGenerals().size() > 0) {
            Classifier classifier = tempEnumeration.getGenerals().get(0);
            if (classifier instanceof Enumeration) {
                ArrayList<String> ownedListeralNames = new ArrayList<String>();
                ArrayList<String> parentOwnedListeralNames =
                        new ArrayList<String>();

                Enumeration parentEnumeration = (Enumeration) classifier;

                EList<EnumerationLiteral> ownedLiterals =
                        tempEnumeration.getOwnedLiterals();
                EList<EnumerationLiteral> parentOwnedLiterals =
                        parentEnumeration.getOwnedLiterals();

                Iterator<EnumerationLiteral> iterator =
                        ownedLiterals.iterator();
                while (iterator.hasNext()) {
                    EnumerationLiteral enumerationLiteral = iterator.next();
                    ownedListeralNames.add(enumerationLiteral.getName());
                }

                iterator = parentOwnedLiterals.iterator();
                while (iterator.hasNext()) {
                    EnumerationLiteral enumerationLiteral = iterator.next();
                    parentOwnedListeralNames.add(enumerationLiteral.getName());
                }

                Iterator<String> tmpIter = ownedListeralNames.iterator();
                while (tmpIter.hasNext()) {
                    String ownedEnumLitname = tmpIter.next();
                    if (!parentOwnedListeralNames.contains(ownedEnumLitname)) {
                        List<String> additionalMessages =
                                new ArrayList<String>();
                        additionalMessages.add(tempEnumeration.getName());
                        additionalMessages.add(parentEnumeration.getName());
                        scope.createIssue(XsdIssueIds.CHILD_ENUMLITS_NOT_IN_PARENT,
                                BOMValidationUtil.getLocation((NamedElement) o),
                                uri,
                                additionalMessages);
                        break;
                    }
                }
                /*
                 * XPD-2511: literal values in child enums must match the
                 * literal values of parent
                 */
                validateLiterals(parentEnumeration, tempEnumeration, scope);
            }
        }
    }

    /**
     * validates the literal values of parent enum with child enum
     * 
     * @param parentEnumeration
     * @param tempEnumeration
     * @param scope
     */
    private void validateLiterals(Enumeration parentEnumeration,
            Enumeration tempEnumeration, IValidationScope scope) {

        for (EnumerationLiteral literal : tempEnumeration.getOwnedLiterals()) {
            DomainValue value = EnumLitValueUtil.getDomainValue(literal);

            for (EnumerationLiteral parentLiteral : parentEnumeration
                    .getOwnedLiterals()) {

                if (parentLiteral.getName().equals(literal.getName())) {
                    DomainValue parentLitValue =
                            EnumLitValueUtil.getDomainValue(parentLiteral);
                    boolean doesLiteralValuesMatch =
                            doesLiteralValuesMatch(value, parentLitValue);
                    if (!doesLiteralValuesMatch) {
                        String uri =
                                tempEnumeration.eResource()
                                        .getURIFragment(literal);
                        List<String> additionalMessages =
                                new ArrayList<String>();
                        additionalMessages.add(value.getValue());
                        additionalMessages.add(parentLitValue.getValue());
                        scope.createIssue(XsdIssueIds.CHILD_ENUMLIT_VALUE_DOES_NOT_MATCH_PARENT,
                                BOMValidationUtil.getLocation(literal),
                                uri,
                                additionalMessages);
                        break;
                    }
                }
            }
        }

    }

    /**
     * compare parent literal value with child literal value
     * 
     * @param value
     * @param parentLitValue
     */
    private boolean doesLiteralValuesMatch(DomainValue value,
            DomainValue parentLitValue) {
        if (value instanceof SingleValue
                && parentLitValue instanceof SingleValue) {
            if (!value.getValue().equals(parentLitValue.getValue())) {
                return false;
            }
        }
        return true;
    }

}
