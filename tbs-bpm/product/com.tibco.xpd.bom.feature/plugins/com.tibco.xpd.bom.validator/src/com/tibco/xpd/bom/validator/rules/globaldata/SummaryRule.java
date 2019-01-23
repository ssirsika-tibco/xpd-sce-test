package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.SummaryInfoUtils;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Checks to ensure that a given property is OK to include in the Summary
 * 
 */
public class SummaryRule implements IValidationRule {

    private final String ISSUE_ID_TYPE =
            "bom.globaldata.class.case.summary.unsupported.type.issue"; //$NON-NLS-1$

    private final String ISSUE_ID_MULTIPLICITY =
            "bom.globaldata.class.case.summary.unsupported.upper.bound.issue"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public java.lang.Class<?> getTargetClass() {
        // Only properties are added to Summaries, so jst need to check for them
        return Property.class;
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
        // Only handle properties
        if (o instanceof Property) {
            Property prop = (Property) o;

            // Check to see if this is a case class
            Class mainCaseClass = prop.getClass_();

            if (mainCaseClass == null) {
                return;
            }

            // Check if this is a Case CLass, as that is all we can have Summary
            // values on
            if (!GlobalDataProfileManager.getInstance().isCase(mainCaseClass)) {
                return;
            }

            String unsupportedSummaryType = null;
            // First check if this property is valid for a summary or not
            // We do not allow properties of a class type to be used in a
            // Summary
            if (prop.getType() instanceof Class) {
                // This is Invalid
                unsupportedSummaryType = ISSUE_ID_TYPE;
            }
            // Now check to see if it has an upper bounds of more than one, as
            // that is not allowed in a summary either
            else if ((prop.getUpper() > 1) || (prop.getUpper() == -1)) {
                // This is invalid
                unsupportedSummaryType = ISSUE_ID_MULTIPLICITY;
            }

            if (unsupportedSummaryType == null) {
                // Even if it is in a summary, this is valid, so no need to go
                // any further
                return;
            }

            // Get the Classes that this property is in, this includes classes
            // that extend this one
            List<Class> allClasses = new ArrayList<Class>();
            allClasses.add(mainCaseClass);
            // Get all of the classes that extend this one
            allClasses.addAll(getExtendingClasses(mainCaseClass));

            // Now check to see if this property is included in the the summary
            // for this class
            for (final Class caseClass : allClasses) {
                // Get the existing entries that are in the summary list
                List<String> summaryItems =
                        SummaryInfoUtils.getSummaryArray(caseClass);

                // If there are no summary items specified then no need to do
                // anything as the defaults will already be used
                if (!summaryItems.isEmpty()) {
                    if (summaryItems.contains(prop.getName())) {
                        // create issue
                        scope.createIssue(unsupportedSummaryType,
                                BOMValidationUtil.getLocation(prop),
                                prop.eResource().getURIFragment(prop));
                    }
                }
            }
        }
    }

    /**
     * Gets all the Case Classes that extend the given Case Class
     * 
     * @param baseClass
     * @return
     */
    private List<Class> getExtendingClasses(Class baseClass) {
        List<Class> extendingClasses = new ArrayList<Class>();
        List<NamedElement> ownedMembers =
                baseClass.getPackage().getOwnedMembers();
        for (NamedElement namedElement : ownedMembers) {
            if (namedElement instanceof Class) {
                // Check to see if this class extends the one we are looking for
                if (((Class) namedElement).getGenerals().contains(baseClass)) {
                    // Add this class to the list
                    extendingClasses.add((Class) namedElement);
                    // Check if anything extends this class, we want everything
                    // in the tree
                    extendingClasses
                            .addAll(getExtendingClasses((Class) namedElement));
                }
            }
        }
        return extendingClasses;
    }
}
