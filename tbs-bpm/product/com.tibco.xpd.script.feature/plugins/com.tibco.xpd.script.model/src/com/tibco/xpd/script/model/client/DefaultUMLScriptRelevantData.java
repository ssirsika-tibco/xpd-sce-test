/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */package com.tibco.xpd.script.model.client;

import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.resources.XpdResourcesPlugin;

public class DefaultUMLScriptRelevantData extends AbstractUMLScriptRelevantData {

    public DefaultUMLScriptRelevantData() {

    }

    public DefaultUMLScriptRelevantData(String name, String className,
            boolean isArray, JsClass jsClass) {
        setName(name);
        setClassName(className);
        addJsClass(jsClass);
        setIsArray(isArray);
        setLoadModel(false);
        if (jsClass != null && jsClass.getIcon() != null) {
            if (!XpdResourcesPlugin.isInHeadlessMode()) {
                setIcon(jsClass.getIcon());
            }
        }
    }

    @Override
    public URI getURI() {
        return null;
    }

	/**
	 * Sid ACE-8226
	 * 
	 * @return <code>true</code> if this is a method parameter that is a specific case class case-reference type
	 *         parameter
	 */
	public boolean isSpecificTypeCaseReference()
	{
		Object extendedInfo = getExtendedInfo();

		if (extendedInfo instanceof JsMethodParam && ((JsMethodParam) extendedInfo).isSpecificTypeCaseReference())
		{
			return true;
		}
		else if (extendedInfo instanceof JsUmlAttribute
				&& ((JsUmlAttribute) extendedInfo).getQualifiedCaseReferenceClassName() != null)
		{
			return true;
		}

		return false;
	}

	/**
	 * If the data is a specific case type case reference, return the case class type
	 * 
	 * @return The fully qualified case class type name or <code>null</code> if this is not a specific case type case
	 *         reference
	 */
	public String getCaseTypeForCaseReference()
	{
		Object extendedInfo = getExtendedInfo();

		if (extendedInfo instanceof JsMethodParam && ((JsMethodParam) extendedInfo).isSpecificTypeCaseReference())
		{
			return ((JsMethodParam) extendedInfo).getUMLParameter().getType().getQualifiedName();
		}
		else if (extendedInfo instanceof JsUmlAttribute
				&& ((JsUmlAttribute) extendedInfo).getQualifiedCaseReferenceClassName() != null)
		{
			return ((JsUmlAttribute) extendedInfo).getQualifiedCaseReferenceClassName();
		}

		return null;
	}

}
