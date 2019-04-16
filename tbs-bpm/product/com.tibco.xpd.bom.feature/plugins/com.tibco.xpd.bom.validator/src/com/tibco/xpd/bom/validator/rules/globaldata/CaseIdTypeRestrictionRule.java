package com.tibco.xpd.bom.validator.rules.globaldata;

import java.util.Collections;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Validates against Case Identifier attributes to ensure their property type is
 * not set to one of the disallowed primitive types.
 * 
 * @author patkinso
 * @since 4 Oct 2013
 */
public class CaseIdTypeRestrictionRule implements IValidationRule {

    private static final String ISSUE_ID =
            "bom.globaldata.attribute.caseidentifier.typerestriction.issue"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return org.eclipse.uml2.uml.Class.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        org.eclipse.uml2.uml.Class gdClass = (org.eclipse.uml2.uml.Class) o;
        GlobalDataProfileManager gdManager =
                GlobalDataProfileManager.getInstance();

        for (Property prop : gdClass.getOwnedAttributes()) {
            if (UML2ModelUtil.isCompositeProperty(prop)
                    && gdManager.isCID(prop)
                    || UML2ModelUtil.isCompositeProperty(prop)
                    && gdManager.isCompositeCaseIdentifier(prop)) {
                Type propertyType = prop.getType();

                // If no type is defined, then skip it
                if (propertyType == null) {
                    continue;
                }

                String propName = propertyType.getName();

                // Check for the case where the case identifier type points to a
                // primitive type, in which case we need to check the base type
                // of the primitive type
                if (propertyType instanceof PrimitiveType) {
                    PrimitiveType basePrimitiveType =
                            PrimitivesUtil
                                    .getBasePrimitiveType((PrimitiveType) propertyType);

                    if (basePrimitiveType != null) {
                        propName = basePrimitiveType.getName();
                    }
                }

                if (BOMGlobalDataUtils.isDisallowedCaseIdTypeName(propName)
                        || (propertyType instanceof org.eclipse.uml2.uml.Class)
                        || (propertyType instanceof Enumeration)) {
                    String displayName =
                            PrimitivesUtil.getDisplayLabel(propertyType);
                    scope.createIssue(ISSUE_ID,
                            BOMValidationUtil.getLocation(prop),
                            prop.eResource().getURIFragment(prop),
                            Collections.singletonList(displayName));
                } else {
                }
            }
        }
    }
}
