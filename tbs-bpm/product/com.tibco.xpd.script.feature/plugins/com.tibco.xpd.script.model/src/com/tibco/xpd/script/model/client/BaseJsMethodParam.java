/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.script.model.internal.client.IJsElementExt;
import com.tibco.xpd.script.model.internal.client.IUMLElement;

/**
 * BaseJsMethodParam java script class that allows to construct method
 * parameters representing simple/primitive types or complex types. Sub classes
 * can extend to construct their own js method parameters that will represent
 * the specific js class type and provide their own specific implementation
 * 
 * @author bharge
 * @since 30 Oct 2013
 */
public class BaseJsMethodParam implements JsMethodParam, IJsElementExt,
        IUMLElement {

    protected String name;

    protected String type;

    protected boolean isReturnType;

    protected boolean isPassedByReference;

    protected boolean canRepeat;

    protected int minRepeatCount;

    protected int maxRepeatCount;

    protected Element umlElement;

    protected ParameterCoercionCriteria paramCoercionCriteria;

    /**
     * @return the paramCoercionCriteria
     */
    public ParameterCoercionCriteria getParamCoercionCriteria() {
        return paramCoercionCriteria;
    }

    /**
     * set this criteria when you intend to tell the isValidAssignment() about
     * the kind of subType/superType compatibility you want
     * 
     * @param paramCoercionCriteria
     *            the paramCoercionCriteria to set
     */
    public void setParamCoercionCriteria(
            ParameterCoercionCriteria paramCoercionCriteria) {
        this.paramCoercionCriteria = paramCoercionCriteria;
    }

    /**
     * Use this constructor for a method parameter representing a
     * simple/primitive type or complex type.
     * 
     * @param name
     * @param umlType
     * @param type
     * @param isReturnType
     * @param canRepeat
     * @param minOccurence
     * @param maxOccurence
     */
    public BaseJsMethodParam(String name, Classifier umlType, String type,
            boolean isReturnType, boolean canRepeat, int minOccurence,
            int maxOccurence) {

        this.name = name;
        this.umlElement = umlType;
        this.type = type;
        this.isReturnType = isReturnType;
        this.canRepeat = canRepeat;
        this.minRepeatCount = minOccurence;
        this.maxRepeatCount = maxOccurence;
    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.IUMLElement#getElement()
     * 
     * @return
     */
    @Override
    public Element getElement() {

        return umlElement;
    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.IJsElementExt#getBaseDataType()
     * 
     * @return
     */
    @Override
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

    /**
     * @see com.tibco.xpd.script.model.internal.client.IJsElementExt#isStatic()
     * 
     * @return
     */
    @Override
    public boolean isStatic() {

        return false;
    }

    /**
     * @see com.tibco.xpd.script.model.internal.client.IJsElementExt#isReadOnly()
     * 
     * @return
     */
    @Override
    public boolean isReadOnly() {

        return false;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        return this.name;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#getType()
     * 
     * @return
     */
    @Override
    public String getType() {

        return this.type;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#isPassedByReference()
     * 
     * @return
     */
    @Override
    public boolean isPassedByReference() {

        return this.isPassedByReference;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#isPassedByLiteral()
     * 
     * @return
     */
    @Override
    public boolean isPassedByLiteral() {

        return !this.isPassedByReference;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#isReturnType()
     * 
     * @return
     */
    @Override
    public boolean isReturnType() {

        return this.isReturnType;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#canRepeat()
     * 
     * @return
     */
    @Override
    public boolean canRepeat() {

        return this.canRepeat;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#getMaxRepeatCount()
     * 
     * @return
     */
    @Override
    public int getMaxRepeatCount() {

        return this.maxRepeatCount;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#getMinRepeatCount()
     * 
     * @return
     */
    @Override
    public int getMinRepeatCount() {

        return this.minRepeatCount;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#getUMLParameter()
     * 
     * @return
     */
    @Override
    public Parameter getUMLParameter() {

        return null;
    }

    /**
     * @see com.tibco.xpd.script.model.client.JsMethodParam#getScriptRelevantData()
     * 
     * @return
     */
    @Override
    public IScriptRelevantData getScriptRelevantData() {

        return null;
    }

	/**
	 * @see com.tibco.xpd.script.model.client.JsMethodParam#isSpecificTypeCaseReference()
	 *
	 * @return
	 */
	@Override
	public boolean isSpecificTypeCaseReference()
	{
		// Sid ACE-8226 Specific type Case-reference params are handled in DefaultJsMethodParam
		return false;
	}

}
