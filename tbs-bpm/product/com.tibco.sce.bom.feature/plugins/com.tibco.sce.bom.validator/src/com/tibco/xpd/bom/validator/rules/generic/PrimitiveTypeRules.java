package com.tibco.xpd.bom.validator.rules.generic;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.GenericIssueIds;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

public class PrimitiveTypeRules implements IValidationRule {

    @Override
    public Class<?> getTargetClass() {
        return PrimitiveType.class;
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) o;

            PrimitiveType basePType = PrimitivesUtil.getBasePrimitiveType(pt);

            ResourceSet rSet =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet();

            if (basePType == PrimitivesUtil
                    .getStandardPrimitiveTypeByName(rSet,
                            PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME)) {

                // Check Max Text Length
                Object facetPropertyValue =
                        PrimitivesUtil.getFacetPropertyValue(pt,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);

                if (facetPropertyValue instanceof Integer) {
                    Integer maxTextLen = (Integer) facetPropertyValue;

                    if (maxTextLen == 0) {
                        scope.createIssue(GenericIssueIds.MAXTEXTLENGTH_ZERO,
                                BOMValidationUtil.getLocation(pt),
                                pt.eResource()
                                        .getURIFragment((NamedElement) pt));
                    }
                }

            }

        }

    }

}
