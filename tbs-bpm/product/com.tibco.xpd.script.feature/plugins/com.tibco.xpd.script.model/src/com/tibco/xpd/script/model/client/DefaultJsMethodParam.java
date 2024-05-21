/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;

/**
 * 
 * <p>
 * <i>Created: 3 May 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class DefaultJsMethodParam implements JsMethodParam, IJsElementExt {

    protected Parameter parameter;

    public DefaultJsMethodParam(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public String getName() {
        return parameter.getName();
    }

    @Override
    public String getType() {
        Type paramType = parameter.getType();
        String typeName = JsConsts.UNDEFINED_DATA_TYPE;
        if (paramType != null) {
            typeName = paramType.getName();
        }
        return typeName;
    }

    @Override
    public boolean isPassedByReference() {
        return isReferenceParamType();
    }

    @Override
    public boolean isReturnType() {
        ParameterDirectionKind direction = parameter.getDirection();
        if (direction != null
                && direction.getValue() == ParameterDirectionKind.RETURN) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private boolean isReferenceParamType() {
        boolean isReference = false;
        List<Stereotype> appliedStereotypes = parameter.getAppliedStereotypes();
        if (appliedStereotypes == null) {
            return isReference;
        }
        for (Stereotype stereotype : appliedStereotypes) {
            List<Property> ownedAttributes = stereotype.getOwnedAttributes();
            if (ownedAttributes == null) {
                continue;
            }
            for (Property property : ownedAttributes) {
                String name = property.getName();
                if (JsConsts.PARAM_PASS_BY_REFERENCE.equals(name)) {
                    isReference =
                            (Boolean) parameter.getValue(stereotype, name);
                    break;
                }
            }
        }
        return isReference;
    }

    @Override
    public boolean isPassedByLiteral() {
        boolean isPassByLiteral = false;
        List<Stereotype> appliedStereotypes = parameter.getAppliedStereotypes();
        if (appliedStereotypes == null) {
            return isPassByLiteral;
        }
        for (Stereotype stereotype : appliedStereotypes) {
            List<Property> ownedAttributes = stereotype.getOwnedAttributes();
            if (ownedAttributes == null) {
                continue;
            }
            for (Property property : ownedAttributes) {
                String name = property.getName();
                if (name.equals(JsConsts.PARAM_PASS_BY_LITERAL)) {
                    isPassByLiteral =
                            (Boolean) parameter.getValue(stereotype, name);
                    break;
                }
            }
        }
        return isPassByLiteral;
    }

    @Override
    public boolean canRepeat() {
        int upper = parameter.getUpper();
        if (upper != 1) {
            return true;
        }
        return false;
    }

    @Override
    public int getMinRepeatCount() {
        return parameter.getLower();
    }

    @Override
    public int getMaxRepeatCount() {
        return parameter.getUpper();
    }

    @Override
    public Parameter getUMLParameter() {
        return parameter;
    }

    /** {@inheritDoc}. */
    @Override
    public IScriptRelevantData getScriptRelevantData() {
        return null;
    }

    @Override
    public String getBaseDataType() {
        if (parameter != null && parameter.getType() instanceof PrimitiveType) {
            PrimitiveType primitiveType = (PrimitiveType) parameter.getType();
            String baseDataType = primitiveType.getName();
            while (primitiveType != null
                    && primitiveType.getGeneralizations() != null
                    && !primitiveType.getGeneralizations().isEmpty()) {
                Generalization generalization =
                        primitiveType.getGeneralizations().iterator().next();
                primitiveType = null;
                if (generalization != null) {
                    Classifier general = generalization.getGeneral();
                    if (general instanceof PrimitiveType) {
                        primitiveType = (PrimitiveType) general;
                        baseDataType = primitiveType.getName();
                    }
                }
            }
            return baseDataType;
        }
        return getType();
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public boolean isStatic() {
        /*
         * Sid XPD-5864: treat parameter as 'static' if perent method is static
         * (so that we can handle generic 'Object' type in staics better.
         */
        if (parameter != null && parameter.eContainer() instanceof Operation) {
            return ((Operation) parameter.eContainer()).isStatic();
        }

        return false;
    }

	/**
	 * @see com.tibco.xpd.script.model.client.JsMethodParam#isSpecificTypeCaseReference()
	 *
	 * @return
	 */
	@Override
	public boolean isSpecificTypeCaseReference()
	{
		/*
		 * Method parameters that are are tagged with an eAnnotation, JsConsts.METHOD_PARAM_SPECIFIC_TYPE_CASEREF, are
		 * specific-case-type case references.
		 * 
		 * Currently this is only possible for process script library function parameters, there are handled by
		 * BpmScriptWrapperFactory
		 */
		if (parameter != null && parameter.getEAnnotation(JsConsts.METHOD_PARAM_SPECIFIC_TYPE_CASEREF) != null)
		{
			return true;
		}
		return false;
	}

}
