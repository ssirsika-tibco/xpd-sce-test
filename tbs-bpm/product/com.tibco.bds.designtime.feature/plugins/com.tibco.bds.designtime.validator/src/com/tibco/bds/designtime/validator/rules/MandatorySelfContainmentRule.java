package com.tibco.bds.designtime.validator.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validated for the case where there are mandatory associations and/or
 * properties that result in a loop back to the same object
 * 
 */
public class MandatorySelfContainmentRule implements IValidationRule {

    /**
     * @see com.tibco.xpd.validation.rules.IValidationRule#getTargetClass()
     * 
     * @return
     */
    @Override
    public Class<?> getTargetClass() {
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

        if (o instanceof org.eclipse.uml2.uml.Class) {
            org.eclipse.uml2.uml.Class rootClass =
                    (org.eclipse.uml2.uml.Class) o;

            List<org.eclipse.uml2.uml.Class> compareTo =
                    new ArrayList<org.eclipse.uml2.uml.Class>();

            // Make sure none of the classes linked to this are already in the
            // mandatory loop
            if (getMandatoryLinkedCompositionClasses(rootClass, compareTo)) {
                scope.createIssue(CDSIssueIds.MANDATORY_SELF_CONTAINED,
                        BOMValidationUtil.getLocation(rootClass),
                        rootClass.eResource().getURIFragment(rootClass));
            }
        }
    }

    /**
     * Returns and mandatory properties or associations that originate from the
     * given class
     * 
     * @param rootClass
     * @return
     */
    private boolean getMandatoryLinkedCompositionClasses(
            org.eclipse.uml2.uml.Class rootClass,
            List<org.eclipse.uml2.uml.Class> compareTo) {

        EList<Property> ownedAttributes = rootClass.getOwnedAttributes();
        for (Property property : ownedAttributes) {
            if (property.getLower() > 0) {
                Type type = property.getType();
                if ((type != null)
                        && (type instanceof org.eclipse.uml2.uml.Class)) {
                    org.eclipse.uml2.uml.Class class_ =
                            (org.eclipse.uml2.uml.Class) type;

                    // Check if there is a match for this class already
                    if (compareTo.contains(class_)) {
                        return true;
                    } else {
                        // Check the attribute to see if it has any others
                        List<org.eclipse.uml2.uml.Class> newCompareList =
                                new ArrayList<org.eclipse.uml2.uml.Class>();
                        newCompareList.addAll(compareTo);
                        newCompareList.add(rootClass);
                        if (getMandatoryLinkedCompositionClasses(class_,
                                newCompareList)) {
                            // If a match was found, exit now
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
