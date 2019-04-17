package com.tibco.bds.designtime.validator.ace.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate ACE specific case class specific rules...
 *
 * <li>Composite case identifier attributes are not supported</li>
 * <li>Case classes must have a case state attribute</li>
 * <li>Case state attribute must have an enumeration type set</li>
 * <li>Case state attribute must be mandatory and non-array (multiplicity must
 * be 1)</li>
 * <li>Case identifier attribute is mandatory and non-array (multiplicity must
 * be 1)</li>
 * <li>Case classes must have a single case identifier attribute</li>
 * <li>Case classes cannot have more than 5 searchable attributes</li>
 * <li>Only case classes can contain searchable attributes</li>
 * <li>Searchable attributes cannot be arrays</li>
 *
 * @author aallway
 * @since 16 Apr 2019
 */
public class AceCaseClassRules implements IValidationRule {

    private static final String ISSUE_ACE_COMPOSITE_CASEID =
            "ace.bom.composite.caseid"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CASE_MUST_HAVE_CASESTATE =
            "ace.bom.case.must.have.casestate"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CASESTATE_MUST_HAVE_ENUM =
            "ace.bom.casestate.must.have.enum"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CASESTATE_MUST_BE_MANDATORY_NONARRAY =
            "ace.bom.casestate.must.be.mandatory.nonarray"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CASE_MUST_HAVE_CASEID =
            "ace.bom.case.must.have.caseid"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CASEID_MUST_BE_MANDATORY_NONARRAY =
            "ace.bom.caseid.must.be.mandatory.nonarray"; //$NON-NLS-1$

    private static final String ISSUE_ACE_MAX_5_SEARCHABLE =
            "ace.bom.max.5.searchable"; //$NON-NLS-1$

    private static final String ISSUE_ACE_SEARCHABLE_IN_CASE_ONLY =
            "ace.bom.searchable.in.case.only"; //$NON-NLS-1$

    private static final String ISSUE_ACE_SEARCHABLE_MUST_BE_NONARRAY =
            "ace.bom.searchable.must.be.nonarray"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof Property) {
            validateProperty(scope, (Property) obj);

        } else if (obj instanceof org.eclipse.uml2.uml.Class) {
            org.eclipse.uml2.uml.Class clazz = (org.eclipse.uml2.uml.Class) obj;

            if (BOMGlobalDataUtils.isCaseClass(clazz)) {
                validateCaseClass(scope, clazz);
            } else {
                validateNonCaseClass(scope, clazz);
            }
        }
    }

    /**
     * Validate the given non-case class
     * 
     * @param scope
     * @param clazz
     */
    private void validateNonCaseClass(IValidationScope scope,
            org.eclipse.uml2.uml.Class clazz) {

        // Searchable attributes can only be in case classes
        EList<Property> ownedAttributes = clazz.getOwnedAttributes();

        for (Property property : ownedAttributes) {
            if (BOMGlobalDataUtils.isSearchable(property)) {
                scope.createIssue(ISSUE_ACE_SEARCHABLE_IN_CASE_ONLY,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }
        }

    }

    /**
     * Validate the given case class
     * 
     * @param scope
     * @param clazz
     */
    private void validateCaseClass(IValidationScope scope,
            org.eclipse.uml2.uml.Class clazz) {

        Property caseStateProperty = null;
        List<Property> caseIdProperties = new ArrayList<>();
        List<Property> searchProperties = new ArrayList<>();

        EList<Property> ownedAttributes = clazz.getOwnedAttributes();

        for (Property property : ownedAttributes) {
            if (BOMGlobalDataUtils.isCaseState(property)) {
                caseStateProperty = property;

            } else if (BOMGlobalDataUtils.isCID(property)) {
                caseIdProperties.add(property);

            } else if (BOMGlobalDataUtils.isSearchable(property)) {
                searchProperties.add(property);
            }

        }

        // Case class must have a case state attribute.
        if (caseStateProperty == null) {
            scope.createIssue(ISSUE_ACE_CASE_MUST_HAVE_CASESTATE,
                    BOMValidationUtil.getLocation(clazz),
                    clazz.eResource().getURIFragment(clazz));

        } else {
            // Case state must have an enum set.
            if (!(caseStateProperty.getType() instanceof Enumeration)) {
                scope.createIssue(ISSUE_ACE_CASESTATE_MUST_HAVE_ENUM,
                        BOMValidationUtil.getLocation(caseStateProperty),
                        caseStateProperty.eResource()
                                .getURIFragment(caseStateProperty));
            }

            // Case state must be mandatory non-array
            if (caseStateProperty.getLower() <= 0
                    || caseStateProperty.getUpper() != 1) {
                scope.createIssue(
                        ISSUE_ACE_CASESTATE_MUST_BE_MANDATORY_NONARRAY,
                        BOMValidationUtil.getLocation(caseStateProperty),
                        caseStateProperty.eResource()
                                .getURIFragment(caseStateProperty));
            }
        }

        // There must be exactly one case identifier
        if (caseIdProperties.size() != 1) {
            scope.createIssue(ISSUE_ACE_CASE_MUST_HAVE_CASEID,
                    BOMValidationUtil.getLocation(clazz),
                    clazz.eResource().getURIFragment(clazz));

        } else {
            // Case id must be mandatory non-array
            Property caseIdProperty = caseIdProperties.get(0);

            if (caseIdProperty.getLower() <= 0
                    || caseIdProperty.getUpper() != 1) {
                scope.createIssue(ISSUE_ACE_CASEID_MUST_BE_MANDATORY_NONARRAY,
                        BOMValidationUtil.getLocation(caseIdProperty),
                        caseIdProperty.eResource()
                                .getURIFragment(caseIdProperty));
            }
        }

        // There can only be 5 searchable properties.
        if (searchProperties.size() > 5) {
            scope.createIssue(ISSUE_ACE_MAX_5_SEARCHABLE,
                    BOMValidationUtil.getLocation(clazz),
                    clazz.eResource().getURIFragment(clazz));

        }

        // Search attribute cannot be aray.
        for (Property searchProperty : searchProperties) {
            if (searchProperty.getUpper() != 1) {
                scope.createIssue(ISSUE_ACE_SEARCHABLE_MUST_BE_NONARRAY,
                        BOMValidationUtil.getLocation(searchProperty),
                        searchProperty.eResource()
                                .getURIFragment(searchProperty));
            }
        }

    }

    /**
     * Validate the given property
     * 
     * @param scope
     * @param obj
     */
    private void validateProperty(IValidationScope scope, Property property) {
        // Composite case identifiers are not supported
        if (BOMGlobalDataUtils.isCompositeCID(property)) {
            scope.createIssue(ISSUE_ACE_COMPOSITE_CASEID,
                    BOMValidationUtil.getLocation(property),
                    property.eResource().getURIFragment(property));
        }
    }

}
