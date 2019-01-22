package com.tibco.xpd.bom.validator.rules.xsd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.XsdIssueIds;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * An N2 validation rule that raises an issue if a PrimitiveType has a default
 * value.
 * 
 * @author smorgan
 * 
 */
public class PrimitiveTypeRule implements IValidationRule {

    private static final String RESTRICTED_TYPE_STEREOTYPE_QNAME =
            "PrimitiveTypeFacets::RestrictedType"; //$NON-NLS-1$

    private static final String XSD_NOTATION_PROFILE_NAME =
            "XsdNotationProfile"; //$NON-NLS-1$

    private final Map<String, String> nameMap;

    public PrimitiveTypeRule() {
        nameMap = new HashMap<String, String>();
        nameMap.put("Text", "textDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$
        nameMap.put("Integer", "integerDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$ 
        nameMap.put("DateTime", "dateTimeDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$ 
        nameMap.put("Date", "dateDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$ 
        nameMap.put("Time", "timeDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$ 
        nameMap.put("Boolean", "booleanDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$
        nameMap.put("Decimal", "decimalDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$     
        nameMap.put("DateTimeTZ", "dateTimeTZDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$
        nameMap.put("URI", "uriDefaultValue"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public Class<?> getTargetClass() {
        return PrimitiveType.class;
    }

    public void validate(IValidationScope scope, Object o) {
        if (o instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) o;

            // Check for a PrimitiveType of type Object
            ResourceSet rSet =
                    XpdResourcesPlugin.getDefault().getEditingDomain()
                            .getResourceSet();

            PrimitiveType baseObjectType =
                    PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                            PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME);

            PrimitiveType basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType(pt);

            if (basePrimitiveType == baseObjectType
                    && !isXSDNotationProfileApplied(pt.getModel())) {
                scope.createIssue(XsdIssueIds.PRIMITIVETYPE_OBJECT_TYPE,
                        pt.getQualifiedName(),
                        pt.eResource().getURIFragment(pt));

            }

        }
    }

    private static Type findRootType(Type type) {
        Type result = type;
        Type superType = getSuperType(type);
        if (superType != null) {
            result = findRootType(superType);
        }
        return result;
    }

    private static Type getSuperType(Type type) {
        Type result = null;
        if (type instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) type;
            if (pt.getGenerals().size() != 0) {
                result = pt.getGenerals().get(0);
            }
        }
        return result;
    }

    /**
     * @param object
     * @return
     */
    private static boolean isXSDNotationProfileApplied(Package umlPackage) {
        Iterator<ProfileApplication> iter =
                umlPackage.getAllProfileApplications().iterator();
        while (iter.hasNext()) {
            Profile appliedProfile = iter.next().getAppliedProfile();
            if (appliedProfile != null
                    && appliedProfile.getName()
                            .equals(XSD_NOTATION_PROFILE_NAME)) {
                return true;
            }
        }

        return false;
    }
}
