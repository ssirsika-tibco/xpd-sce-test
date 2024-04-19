/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.wm.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.tibco.xpd.n2.pe.internal.reader.PEJScriptProcessDefinitionReader;
import com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.UMLScriptClassWrapperRelevantData;
import com.tibco.xpd.xpdExtension.LibraryFunctionUseIn;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Data provider for Process Script Library Function Scripts depending upon the set target environment. (i.e. "useIn"
 * Property of PSL Function Script Activity)
 *
 *
 * @author cbabar
 * @since Feb 27, 2024
 */
public class ScriptLibraryTargetEnvDependentScriptDataProvider extends DefaultJavaScriptRelevantDataProvider
{

	/**
	 * PE JScript Process Definition Reader reader class instance which provides the bpm.process JavaScript class
	 * content when required.
	 */
	PEJScriptProcessDefinitionReader delegatePEJSSPDefinitionReader = new PEJScriptProcessDefinitionReader();

	/**
	 * WM JScript Process Definition Reader reader class instance which provides the bpm.workManager JavaScript class
	 * content when required.
	 */
	WMJScriptProcessDefinitionReader	delegateWMJSSPDefinitionReader	= new WMJScriptProcessDefinitionReader();

	/**
	 * ANY Taret Env JScript Process Definition Reader reader class instance which provides the
	 * bpm.caseData/dateTimeUtil/scriptUtil JavaScript class content when required.
	 */
	AnyEnvJScriptProcessDefinitionReader	delegateAnyTargetEnvJSSPDefinitionReader	= new AnyEnvJScriptProcessDefinitionReader();

	/**
	 * @see com.tibco.xpd.process.js.model.DefaultJavaScriptRelevantDataProvider#getScriptRelevantDataList()
	 *
	 * @return
	 */
	@Override
	public List<IScriptRelevantData> getScriptRelevantDataList()
	{

		Activity activity = getContextActivity();

		if (activity != null)
		{
			Collection<JsClass> classes = new ArrayList<JsClass>();

			LibraryFunctionUseIn useIn = (LibraryFunctionUseIn) Xpdl2ModelUtil.getOtherAttribute(activity,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn());

			if (LibraryFunctionUseIn.PROCESS_MANAGER.equals(useIn))
			{
				classes.addAll(delegatePEJSSPDefinitionReader.getSupportedClasses());
			}
			else if (LibraryFunctionUseIn.WORK_MANAGER.equals(useIn))
			{
				classes.addAll(delegateWMJSSPDefinitionReader.getSupportedClasses());
			}
			else if (LibraryFunctionUseIn.ALL.equals(useIn))
			{
				classes.addAll(delegateAnyTargetEnvJSSPDefinitionReader.getSupportedClasses());
			}

			List<IScriptRelevantData> scriptRelevantDataList = new ArrayList<IScriptRelevantData>();

			for (JsClass eachClass : classes)
			{
				scriptRelevantDataList.add(new UMLScriptClassWrapperRelevantData(eachClass));
			}

			return scriptRelevantDataList;

		}

		return Collections.emptyList();
	}

	/**
	 * @return the context activity.
	 */
	private Activity getContextActivity()
	{
		if (getEObject() instanceof Activity)
		{
			return (Activity) getEObject();

		}

		return null;
	}

}
