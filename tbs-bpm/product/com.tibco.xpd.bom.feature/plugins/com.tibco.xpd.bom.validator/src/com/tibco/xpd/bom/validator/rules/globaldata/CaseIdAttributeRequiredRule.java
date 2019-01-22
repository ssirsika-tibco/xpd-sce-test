package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validates for at least one Case Identifier being present in a Case class
 * 
 * @author patkinso
 * @since 8 Oct 2013
 */
public class CaseIdAttributeRequiredRule implements IValidationRule {

    private static final String ISSUE_ID =
            "bom.globaldata.class.caseidentifier.required.issue"; //$NON-NLS-1$

    private static final String COMPOSITE_SINGLE_ISSUE_ID =
            "bom.globaldata.class.case.compositecaseidentifier.single.issue"; //$NON-NLS-1$

    private static final String COMPOSITE_MULTIPLE_ISSUE_ID =
            "bom.globaldata.class.case.compositecaseidentifier.multiplicity.issue"; //$NON-NLS-1$

    private List<org.eclipse.uml2.uml.Class> processedClasses =
            new ArrayList<org.eclipse.uml2.uml.Class>();

    // Flag to record if one Composite if has already been found in the
    // class hierarchy
    private boolean compIdsExist = false;

    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        org.eclipse.uml2.uml.Class gdClass = (org.eclipse.uml2.uml.Class) o;
        GlobalDataProfileManager gdManager =
                GlobalDataProfileManager.getInstance();

        if (gdManager.isCase(gdClass)) {
            processedClasses.clear();
            compIdsExist = false;

            // Check if there is a case ID for this class
            if (!hasCaseIdentifier(gdClass, scope)) {
                scope.createIssue(ISSUE_ID, gdClass.eClass().getName(), gdClass
                        .eResource().getURIFragment(gdClass));
            }
        }
    }

    /**
     * Check for a case identifier in a class
     * 
     * @param gdClass
     * @return
     */
    private boolean hasCaseIdentifier(org.eclipse.uml2.uml.Class gdClass,
            IValidationScope scope) {

        int numCompIds = 0;
        boolean cidFound = false;

        for (Property prop : gdClass.getOwnedAttributes()) {
            if (GlobalDataProfileManager.getInstance().isCID(prop)
                    || GlobalDataProfileManager.getInstance()
                            .isAutoCaseIdentifier(prop)) {
                cidFound = true;
            } else if (GlobalDataProfileManager.getInstance()
                    .isCompositeCaseIdentifier(prop)) {
                cidFound = true;
                numCompIds++;
            }
        }

        // Check the case where only a single composite ID has been
        // specified in a class (need min of 2)
        if (numCompIds == 1) {
            scope.createIssue(COMPOSITE_SINGLE_ISSUE_ID, gdClass.eClass()
                    .getName(), gdClass.eResource().getURIFragment(gdClass));
        }
        if ((compIdsExist == true) && (numCompIds > 0)) {
            // Already have a composite ID in this hierarchy so can not have
            // another
            scope.createIssue(COMPOSITE_MULTIPLE_ISSUE_ID, gdClass.eClass()
                    .getName(), gdClass.eResource().getURIFragment(gdClass));
        }
        if( numCompIds > 0 ) {
            compIdsExist = true;
        }

        // Now we have done one class

        // Record we have processed this class
        processedClasses.add(gdClass);

        // If we reach here then we do not have a case identifier, need to check
        // any generalisations
        for (Classifier classifier : gdClass.getGenerals()) {
            if (processedClasses.contains(classifier)) {
                // We have already processed this class, so we are
                // currently in an infinite loop, there is already
                // a validation for this, so just exit
                break;
            }
            if (classifier instanceof org.eclipse.uml2.uml.Class) {
                if (hasCaseIdentifier((org.eclipse.uml2.uml.Class) classifier, scope)) {
                    // Found a case ID - no need to continue
                    cidFound = true;
                }
            }
        }

        return cidFound;
    }
}
