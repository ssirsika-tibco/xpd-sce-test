package com.tibco.bds.designtime.validator.ace.rules;

import java.util.Collections;

import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * A rule that creates an issue if the given attribute is a temporal type and a
 * default value defined.
 * 
 * @author pwatson
 * 
 */
public class AceTemporalDefaultValueRule implements IValidationRule {

    private static final String ISSUE_TEMPORAL_DEFAULT_VALUE =
            "ace.bom.temporal.default.value"; //$NON-NLS-1$

    private static final String ISSUE_TEMPORAL_INHERITED_DEFAULT_VALUE =
            "ace.bom.temporal.inherited.default.value"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Property.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {
        if (!(o instanceof Property)) {
            return;
        }

        Property property = (Property) o;
        Type type = property.getType();
        if (!(type instanceof PrimitiveType)) {
            return;
        }

        PrimitiveType pt = (PrimitiveType) type;
        PrimitiveType basePrimitiveType =
                PrimitivesUtil.getBasePrimitiveType(pt);

        if (!isTemporalType(pt)) {
            return;
        }

        String name =
                PrimitivesUtil.getFacetNameForDefaultValue(basePrimitiveType);
        if (name == null) {
            return;
        }

        Object defVal =
                PrimitivesUtil.getFacetPropertyValue(pt, name, property, false);

        boolean inherited = false;
        if (isEmpty(defVal)) {
            // No default defined on the property itself,
            // so see if it inherits one from its type.
            defVal = PrimitivesUtil
                    .getFacetPropertyValue(pt, name, property, true);
            inherited = true;
        }

        if (!isEmpty(defVal)) {
            scope.createIssue(
                    inherited ? ISSUE_TEMPORAL_INHERITED_DEFAULT_VALUE
                            : ISSUE_TEMPORAL_DEFAULT_VALUE,
                    BOMValidationUtil.getLocation(property),
                    property.eResource().getURIFragment(property),
                    Collections.singleton(pt.getName()));
        }
    }

    /**
     * Tests the given object to determine if it is an "empty" value. That is, a
     * null or empty string.
     * 
     * @param aValue
     *            the value to be tested.
     * @return <code>true</code> if the given value is "empty".
     */
    private boolean isEmpty(Object aValue) {
        return (aValue == null) || (aValue.equals("")); //$NON-NLS-1$
    }

    /**
     * Tests if the given type is one of date, time or date-time.
     * 
     * @param aType
     *            the type to be tested.
     * @return <code>true</code> if the given type is a temporal one.
     */
    private boolean isTemporalType(PrimitiveType aType) {
        String typeName = aType.getName();

        return typeName.equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME)
                || typeName.equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME)
                || typeName.equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME)
                || typeName.equals(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);
    }
}
