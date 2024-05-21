/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.n2.cds.script;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.internal.client.IUMLElement;

/**
 * 
 * <p>
 * <i>Created: 3 May 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class DefaultJsFactoryMethodParam implements JsMethodParam,
        IJsElementExt, IUMLElement {

    private String name;

    private String type;

    private boolean isReturnType;

    private boolean isPassedByReference;

    private boolean canRepeat;

    private int minRepeatCount;

    private int maxRepeatCount;

    private Element umlElement;

    public DefaultJsFactoryMethodParam(String name, String type,
            boolean isReturnType, boolean isPassedByReference,
            boolean canRepeat, int minRepeatCount, int maxRepeatCount,
            Element umlElement) {
        this.name = name;
        this.type = type;
        this.isReturnType = isReturnType;
        this.isPassedByReference = isPassedByReference;
        this.canRepeat = canRepeat;
        this.minRepeatCount = minRepeatCount;
        this.maxRepeatCount = maxRepeatCount;
        this.umlElement = umlElement;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public boolean isPassedByReference() {
        return this.isPassedByReference;
    }

    public boolean isReturnType() {
        return this.isReturnType;
    }

    public boolean isPassedByLiteral() {
        return !this.isPassedByReference;
    }

    public boolean canRepeat() {
        return this.canRepeat;
    }

    public int getMinRepeatCount() {
        return this.minRepeatCount;
    }

    public int getMaxRepeatCount() {
        return this.maxRepeatCount;
    }

    public Parameter getUMLParameter() {
        return null;
    }

    /** {@inheritDoc}. */
    public IScriptRelevantData getScriptRelevantData() {
        return null;
    }

    public String getBaseDataType() {
        if (getElement() instanceof PrimitiveType) {
            PrimitiveType primitiveType = (PrimitiveType) getElement();
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

    public boolean isReadOnly() {
        return false;
    }

    public boolean isStatic() {
        return false;
    }

    public Element getElement() {
        return umlElement;
    }

	/**
	 * @see com.tibco.xpd.script.model.client.JsMethodParam#isSpecificTypeCaseReference()
	 *
	 * @return
	 */
	public boolean isSpecificTypeCaseReference()
	{
		// Sid ACE-8226 Specific type Case-reference params are handled in DefaultJsMethodParam
		return false;
	}

}
