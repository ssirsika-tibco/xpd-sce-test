package com.tibco.bds.designtime.validator.ace.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.api.AutoCaseIdProperties;
import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate ACE specific case class specific rules...
 *
 * <li>Composite case identifier attributes are not supported</li>
 * <li>Case classes must have a case state attribute</li>
 * <li>Case state attribute must be mandatory and non-array (multiplicity must
 * be 1)</li>
 * <li>Case identifier attribute is mandatory and non-array (multiplicity must
 * be 1)</li>
 * <li>Case classes must have a single case identifier attribute</li>
 * <li>Case classes cannot have more than 5 searchable attributes</li>
 * <li>Only case classes can contain searchable attributes</li>
 * <li>Searchable attributes cannot be arrays</li>
 * <li>Case identifier attributes must be Text type</li>
 * <li>Auto case identifier minimum digits must not be greater than 15</li>
 *
 * @author aallway
 * @since 16 Apr 2019
 */
public class AceCaseClassRules implements IValidationRule {

    private static final AutoCaseIdProperties AUTO_CID_PROPS =
            new AutoCaseIdProperties();

	/*
	 * Sid ACE-8370 Composite case-id support - ace.bom.composite.caseid removed as covered by higher-level validation
	 * that 'must be either single case id or multiple compoisite id's
	 */

    private static final String ISSUE_ACE_CASE_MUST_HAVE_CASESTATE =
            "ace.bom.case.must.have.casestate"; //$NON-NLS-1$


    private static final String ISSUE_ACE_CASESTATE_MUST_BE_MANDATORY_NONARRAY =
            "ace.bom.casestate.must.be.mandatory.nonarray"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CASE_MUST_HAVE_CASEID =
            "ace.bom.case.must.have.caseid"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CASEID_MUST_BE_MANDATORY_NONARRAY =
            "ace.bom.caseid.must.be.mandatory.nonarray"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CASEID_MUST_BE_TEXT =
            "ace.bom.caseid.must.be.text"; //$NON-NLS-1$

    private static final String ISSUE_ACE_MAX_SEARCHABLE =
            "ace.bom.max.searchable"; //$NON-NLS-1$

    private static final String ISSUE_ACE_MAX_5_SUMMARY =
            "ace.bom.max.5.summary"; //$NON-NLS-1$

    private static final String ISSUE_ACE_SEARCHABLE_MUST_BE_NONARRAY =
            "ace.bom.searchable.must.be.nonarray"; //$NON-NLS-1$

    private static final String ISSUE_ACE_IDENTIFIER_IN_CASE_ONLY =
            "ace.bom.caseid.in.case.only"; //$NON-NLS-1$

    private static final String ISSUE_ACE_MIN_DIGITS_MAX_15 =
            "ace.bom.caseid.mindigits.max.15"; //$NON-NLS-1$

    private static final String ISSUE_ACE_SEACHABLE_INVALID_TYPE = "ace.bom.searchable.invalid.type"; //$NON-NLS-1$

    private static final String ISSUE_ACE_SUMMARY_INVALID_TYPE = "ace.bom.summary.invalid.type"; //$NON-NLS-1$

    /** The base primitive type names that searchable properties can't have. */
    private static final List<String> NON_SEARCHABLE_TYPES =
            Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME, PrimitivesUtil.BOM_PRIMITIVE_URI_NAME);

    /** The base primitive type names that summary properties can't have. */
    private static final List<String> NON_SUMMARY_TYPES =
            Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME, PrimitivesUtil.BOM_PRIMITIVE_URI_NAME);

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
            if (BOMGlobalDataUtils.isCID(property)) {
                scope.createIssue(ISSUE_ACE_IDENTIFIER_IN_CASE_ONLY,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));

            }
        }

    }

    /**
	 * Recursively finds all searchable properties in a Class. Also traverses through all compositions of the given
	 * class.
	 * 
	 * Nikita ACE-7540
	 * 
	 * @param clazz
	 * @param searchProperties
	 * @param classesAlreadyOnCompositionThread
	 */
	private void findSearchProperties(org.eclipse.uml2.uml.Class clazz, List<Property> searchProperties,
			Set<org.eclipse.uml2.uml.Class> classesAlreadyOnCompositionThread)
	{
		// ACE-7540 Search properties of a class only if not already done
		if (classesAlreadyOnCompositionThread.contains(clazz))
		{
			return;
		}

		/*
		 * PUSH this class onto 'stack' of 'already processed' classes with this class added to prevent looping
		 * recursion ALONG THIS 'THREAD' of a hierarchy of compositions
		 */
		classesAlreadyOnCompositionThread.add(clazz);

		// Consider all attributes, including attributes directly or indirectly included in the case object via
		// composition of normal classes.
		EList<Property> ownedAttributes = clazz.getOwnedAttributes();

		for (Property property : ownedAttributes) {
			Type type = property.getType();
			boolean isComposition = property.getAggregation().equals(AggregationKind.COMPOSITE_LITERAL);

			if ((type != null) && (type instanceof org.eclipse.uml2.uml.Class) && isComposition) {
				org.eclipse.uml2.uml.Class class_ = (org.eclipse.uml2.uml.Class) type;

				findSearchProperties(class_, searchProperties, classesAlreadyOnCompositionThread);

			} else if ((!BOMGlobalDataUtils.isCaseState(property)) && (!BOMGlobalDataUtils.isCID(property))
					&& (BOMGlobalDataUtils.isSearchable(property))) {
				searchProperties.add(property);
				
			}
		}

		/*
		 * POP this class onto 'stack' of 'already processed' classes
		 */
		classesAlreadyOnCompositionThread.remove(clazz);

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
        List<Property> summaryProperties = new ArrayList<>();

		/*
		 * Sid ACE-8370 Sid ACE-8370 Composite case-id support - track what types of composite we have. Then we can
		 * enforce rule
		 * 
		 * "Case classes must have either a single non-composite identifier or multiple composite case identifiers"
		 */
		int compositeCaseIdCount = 0;
		boolean hasNonCompositeId = false;

		// Nikita ACE-7540
		// Construct a set of classes to be traversed to look for searchable attributes
		// This is maintained to avoid an infinite loop(cycle) incase of class compositions
		Set<org.eclipse.uml2.uml.Class> traversedClasses = new HashSet<org.eclipse.uml2.uml.Class>();

        EList<Property> ownedAttributes = clazz.getOwnedAttributes();

        for (Property property : ownedAttributes) {
            if (BOMGlobalDataUtils.isCaseState(property)) {
                caseStateProperty = property;

            } else if (BOMGlobalDataUtils.isCID(property)) {
                caseIdProperties.add(property);

				/*
				 * Sid ACE-8370 Sid ACE-8370 Composite case-id support - track types of case-id's used
				 */
				if (GlobalDataProfileManager.getInstance().isCompositeCaseIdentifier(property))
				{
					compositeCaseIdCount++;
				}
				else
				{
					hasNonCompositeId = true;
				}

			} else if (BOMGlobalDataUtils.isSummary(property)) {
				summaryProperties.add(property);
				
            }
		}

		// Nikita ACE-7540 Find all searchable properties
		findSearchProperties(clazz, searchProperties, traversedClasses);

        // Case class must have a case state attribute.
        if (caseStateProperty == null) {
            scope.createIssue(ISSUE_ACE_CASE_MUST_HAVE_CASESTATE,
                    BOMValidationUtil.getLocation(clazz),
                    clazz.eResource().getURIFragment(clazz));

        } else {
            /*
             * Sid ACE-1326: Removed "must have enumeration set" issue here as
             * it duplicates existing one in CaseStateValidationRule.
             */

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

		/*
		 * Sid ACE-8370 Sid ACE-8370 Composite case-id support - replaced 'Case classes must have a single case
		 * identifier attribute' with...
		 * 
		 * "Case classes must have either a single non-composite identifier or multiple composite case identifiers"
		 */
		if ((hasNonCompositeId && caseIdProperties.size() != 1) || (!hasNonCompositeId && compositeCaseIdCount < 2))
		{
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
            
            // Case identifier attributes must be Text type
            PrimitiveType textPrimitiveType =
                    PrimitivesUtil.getStandardPrimitiveTypeByName(
                            XpdResourcesPlugin.getDefault().getEditingDomain()
                                    .getResourceSet(),
                    PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
            
            if (textPrimitiveType != null
                    && !textPrimitiveType.equals(caseIdProperty.getType())) {
                scope.createIssue(ISSUE_ACE_CASEID_MUST_BE_TEXT,
                        BOMValidationUtil.getLocation(caseIdProperty),
                        caseIdProperty.eResource()
                                .getURIFragment(caseIdProperty));
            }
        }

        // There can only be 30 searchable properties.
		// Nikita ACE-7540 Updated max number of searchable attributes allowed to 30
        if (searchProperties.size() > 30) {
            scope.createIssue(ISSUE_ACE_MAX_SEARCHABLE,
                    BOMValidationUtil.getLocation(clazz),
                    clazz.eResource().getURIFragment(clazz));

        }

        // There can only be 5 searchable properties.
        if (summaryProperties.size() > 5) {
            scope.createIssue(ISSUE_ACE_MAX_5_SUMMARY,
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
            if (!hasValidSearchableType(searchProperty)) {
                scope.createIssue(ISSUE_ACE_SEACHABLE_INVALID_TYPE,
                        BOMValidationUtil.getLocation(searchProperty),
                        searchProperty.eResource().getURIFragment(searchProperty));
            }
        }
        for (Property summaryProperty : summaryProperties) {
            if (!hasValidSummaryType(summaryProperty)) {
                scope.createIssue(ISSUE_ACE_SUMMARY_INVALID_TYPE,
                        BOMValidationUtil.getLocation(summaryProperty),
                        summaryProperty.eResource().getURIFragment(summaryProperty));
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
		/*
		 * Sid ACE-8370 Composite case-id support - ace.bom.composite.caseid removed as covered by higher-level
		 * validation that 'must be either single case id or multiple compoisite id's
		 */

        // Auto case identifier minimum digits must not be greater than 15
        if (BOMGlobalDataUtils.isAutoCID(property)) {
            int minDigits = AUTO_CID_PROPS.getMinDigits(property).intValue();
            if (minDigits > 15) {
                scope.createIssue(ISSUE_ACE_MIN_DIGITS_MAX_15,
                        BOMValidationUtil.getLocation(property),
                        property.eResource().getURIFragment(property));
            }
        }
    }

    /**
     * Check if the property has a valid type to be seachable. Only checks properties of primitive types.
     * 
     * @param property
     *            the property to check.
     * @return if property has an allowed searchable type.
     */
    private boolean hasValidSearchableType(Property property) {
        Type bomType = property.getType();
        if (bomType instanceof PrimitiveType) {
            PrimitiveType baseType = PrimitivesUtil
                    .getBasePrimitiveType((PrimitiveType) bomType);
            return !NON_SEARCHABLE_TYPES.contains(baseType.getName());
        }
        return true;
    }

    /**
     * Check if the property has a valid type to be summary. Only checks properties of primitive types.
     * 
     * @param property
     *            the property to check.
     * @return if property has an allowed summary type.
     */
    private boolean hasValidSummaryType(Property property) {
        Type bomType = property.getType();
        if (bomType instanceof PrimitiveType) {
            PrimitiveType baseType = PrimitivesUtil.getBasePrimitiveType((PrimitiveType) bomType);
            return !NON_SUMMARY_TYPES.contains(baseType.getName());
        }
        return true;
    }
}
