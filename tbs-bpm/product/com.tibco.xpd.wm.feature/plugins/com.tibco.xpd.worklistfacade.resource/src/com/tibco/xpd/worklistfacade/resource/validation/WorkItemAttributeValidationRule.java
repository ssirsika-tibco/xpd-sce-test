package com.tibco.xpd.worklistfacade.resource.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.resource.workingcopy.WorkListFacadeWorkingCopy;

/**
 * 
 * Validation rule for {@link WorkItemAttribute}, validates for •Duplicate Work
 * item attribute Display Label • Display Label Length error for Work Item
 * Attribute .
 * 
 * @author aprasad
 * @since 26-Nov-2013
 */
public class WorkItemAttributeValidationRule implements IValidationRule {

    private static final String ATTRIBUTE_DUPLICATE_ISSUE =
            "duplicate.attribute.display.label.issue"; //$NON-NLS-1$

    private static final String DISPLAY_LABEL_LENGTH_ISSUE =
            "attribute.display.label.length.issue"; //$NON-NLS-1$

    private static int ALLOWED_LABEL_LENGTH = 64;

    @Override
    public Class<?> getTargetClass() {
        return WorkItemAttribute.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof WorkItemAttribute) {
            WorkItemAttribute attribute = (WorkItemAttribute) o;

            String label = attribute.getDisplayLabel();

            if (ALLOWED_LABEL_LENGTH < label.length()) {
                // Display Label Length check
                addIssue(DISPLAY_LABEL_LENGTH_ISSUE,
                        scope,
                        attribute,
                        attribute.getName());
            }

            // Duplicate Display Label check
            if (isDuplicateDisplayLabel(attribute)) {
                addIssue(ATTRIBUTE_DUPLICATE_ISSUE,
                        scope,
                        attribute,
                        attribute.getName());
            }
        }
    }

    /**
     * Returns true anothernAttribute contain same Display Label, as the given
     * attribute.
     * 
     * @param attribute
     * @return true if the containing Work List Facade has Multiple Attribute
     *         with same Display Label as the given Attribute .
     */
    private boolean isDuplicateDisplayLabel(WorkItemAttribute attribute) {

        String displayLabel = attribute.getDisplayLabel();
        if (displayLabel == null || displayLabel.isEmpty()) {
            // do not want to display duplicate error, for empty labels
            return false;
        }
        WorkingCopy workingCopy = WorkingCopyUtil.getWorkingCopyFor(attribute);

        if (workingCopy != null
                && workingCopy instanceof WorkListFacadeWorkingCopy) {

            WorkListFacade workListFacade =
                    ((WorkListFacadeWorkingCopy) workingCopy)
                            .getWorkListFacade();

            for (WorkItemAttribute attribute2 : workListFacade
                    .getWorkItemAttributes().getWorkItemAttribute()) {

                if (displayLabel.equalsIgnoreCase(attribute2.getDisplayLabel())
                        && !attribute.equals(attribute2)) {
                    // Another Attribute with same Display label exist
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * Creates Issue of the given Id for the {@link WorkItemAttribute}.
     * 
     * @param issueId
     * @param scope
     * @param attribute
     * @param label
     */
    private void addIssue(String issueId, IValidationScope scope,
            WorkItemAttribute attribute, String label) {
        Map<String, String> additionalInfo = new HashMap<String, String>();
        additionalInfo.put(issueId, label);
        scope.createIssue(issueId,
                label,
                attribute.eResource().getURIFragment(attribute),
                Collections.singletonList(label),
                additionalInfo);
    }

}
