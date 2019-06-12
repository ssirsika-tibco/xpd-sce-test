package com.tibco.bds.designtime.validator.ace.rules;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validate ACE specific general BOM class rules...
 *
 * <li>Super-classing / generalisation is not supported for classes</li>
 * <li>Class operations are not supported</li>
 * <li>You cannot use properties and compositions of Case Class type (you can
 * only use association between case types).</li>
 *
 * @author aallway
 * @since 16 Apr 2019
 */
public class AceBomClassRules implements IValidationRule {

    private static final String ISSUE_ACE_CLASS_SUPERCLASS =
            "ace.bom.class.superclass"; //$NON-NLS-1$

    private static final String ISSUE_ACE_CLASS_OPERATION =
            "ace.bom.class.operation"; //$NON-NLS-1$

    private static final String ISSUE_ACE_PROPERTY_CASE_CLASS =
            "ace.bom.class.property.is.case.class"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return NamedElement.class;
    }

    /**
     * Validate the given BOM Class
     * 
     * @param scope
     * @param obj
     */
    private void validateClass(IValidationScope scope,
            org.eclipse.uml2.uml.Class clazz) {

        if (!clazz.getGeneralizations().isEmpty()) {
            scope.createIssue(ISSUE_ACE_CLASS_SUPERCLASS,
                    BOMValidationUtil.getLocation(clazz),
                    clazz.eResource().getURIFragment(clazz));
        }
    }

    /**
     * validate against all operation's in classes.
     * 
     * @param scope
     * @param obj
     */
    private void validateOperation(IValidationScope scope,
            Operation operation) {
        scope.createIssue(ISSUE_ACE_CLASS_OPERATION,
                BOMValidationUtil.getLocation(operation),
                operation.eResource().getURIFragment(operation));
    }

    /**
     * Validate properties (cannot be of case class type)
     * 
     * @param property
     */
    protected void validateProperty(IValidationScope scope, Property property) {
        Type type = property.getType();

        if (type instanceof org.eclipse.uml2.uml.Class) {
            org.eclipse.uml2.uml.Class clazz =
                    (org.eclipse.uml2.uml.Class) type;

            if (BOMGlobalDataUtils.isCaseClass(clazz)) {
                if (property.getAggregation() != null
                        && !AggregationKind.NONE_LITERAL
                                .equals(property.getAggregation())) {
                    /*
                     * You cannot use properties and compositions of Case Class
                     * type (you can only use association between case types).
                     */
                    scope.createIssue(ISSUE_ACE_PROPERTY_CASE_CLASS,
                            BOMValidationUtil.getLocation(property),
                            property.eResource().getURIFragment(property));

                }
            }
        }
    }

    @Override
    public void validate(IValidationScope scope, Object obj) {
        if (obj instanceof org.eclipse.uml2.uml.Class) {
            validateClass(scope, (org.eclipse.uml2.uml.Class) obj);

        } else if (obj instanceof Operation) {
            validateOperation(scope, (Operation) obj);

        } else if (obj instanceof Property) {
            validateProperty(scope, (Property) obj);

        }
    }

}
