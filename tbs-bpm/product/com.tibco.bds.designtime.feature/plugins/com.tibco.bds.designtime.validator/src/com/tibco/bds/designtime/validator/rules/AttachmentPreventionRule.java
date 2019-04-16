package com.tibco.bds.designtime.validator.rules;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.bds.designtime.validator.CDSIssueIds;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.validator.util.BOMValidationUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * A rule to prevent use of the 'Attachment' type.
 * 
 * @author smorgan
 * 
 */
public class AttachmentPreventionRule implements IValidationRule,
        IExecutableExtension {

    private enum Option {
        Property, PrimitiveType;
    }

    private Option option;

    private PrimitiveType attachmentType;

    public AttachmentPreventionRule() {
        ResourceSet rSet =
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .getResourceSet();
        attachmentType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(rSet,
                        PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME);
    }

    @Override
    public Class<?> getTargetClass() {
        Class<?> result = null;
        if (option != null) {
            switch (option) {
            case Property:
                result = Property.class;
                break;
            case PrimitiveType:
                result = PrimitiveType.class;
                break;
            }
        }
        return result;
    }
    
    @Override
    public void validate(IValidationScope scope, Object o) {
        if (o instanceof Property) {
            Property prop = (Property) o;
            Type propType = prop.getType();
            if (propType != null && propType.equals(attachmentType)) {
                scope.createIssue(CDSIssueIds.ATTACHMENTTYPE_ATTRIBUTE,
                        BOMValidationUtil.getLocation(prop),
                        prop.eResource()
                        .getURIFragment(prop));
            }
        } else if (o instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) o;
            // Check its ultimate super-type isn't Attachment
            Type baseType = PrimitivesUtil.getBasePrimitiveType(pt);
            if (baseType.equals(attachmentType)) {
                scope.createIssue(CDSIssueIds.ATTACHMENTTYPE_PRIMITIVETYPE,
                        BOMValidationUtil.getLocation(pt),
                        pt.eResource().getURIFragment(pt));

            }

        }
    }

    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        if (data instanceof String) {
            option = Option.valueOf((String) data);
        }
    }
}
