/*
 * Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
 */
package com.tibco.xpd.process.js.model;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.process.js.model.internal.Messages;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptFunction;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptFunctionParam;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptFunctionParamCategories;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibrary;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryProject;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryVisitor;
import com.tibco.xpd.processscriptlibrary.resource.indexer.ProcessScriptLibraryIndexProvider.IndexType;
import com.tibco.xpd.processscriptlibrary.resource.util.ProcessScriptLibraryUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.DefaultJsClass;
import com.tibco.xpd.script.model.client.DefaultUMLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.jscript.JScriptUtils;

/**
 * Script relevant data class that represents the "bpmScripts" object that wraps all process script library (PSL)
 * structure in BPMe (ACE).
 * 
 * @author ssirsika
 * @since 26 Feb 2023
 */
public class BpmScriptWrapperFactory
{

	private static final BpmScriptWrapperFactory DEFAULT = new BpmScriptWrapperFactory();

	public static BpmScriptWrapperFactory getDefault()
	{
		return DEFAULT;
	}

	/**
	 * Keep a permanent resource set. As we load the primitive type definitions and profiles etc into the resoruce set,
	 * creating a new one each time is expensive (some initial testing showed something around 0.002 per call to create
	 * wrapper object using a single resoruce set and 0.12 if we create new resource set each time.
	 */
	ResourceSetImpl wrapperResourceSet = new ResourceSetImpl();

	/**
	 * Creates the script contribution for a top level field that wraps the given PSL data. This is represented as a
	 * custom UML class with a UML property of an equivalent type.
	 * 
	 * @param wrapperObjectName
	 *            The name of the top level field
	 * @param indexType
	 *            Enumeration of {@link IndexType} which specify type of target while gathering the PSL references.
	 * 
	 * @return The script relevant data contribution for the top level field that wraps the given PSL structure.
	 */
	public IScriptRelevantData createBpmScriptWrapper(String wrapperObjectName, IndexType indexType)
	{
		/*
		 * Create a resource set and resource to place our temporary UML package in (many of our utilities currently
		 * demand this in order to apply profiles and stereotypes required for setting primitive type to fixed point
		 * etc).
		 * 
		 * We remove the resource from resource set at the end so that we don't get a build up (isn't needed for reading
		 * the stereotype facets.
		 * 
		 * We use a UUI for all in case we get called by multiple threads in parallel (so we create a unique resource
		 * (then remove it at end)
		 */
		@SuppressWarnings("restriction")
		Resource wrapperResource = UMLResourceFactoryImpl.INSTANCE
				.createResource(URI.createURI("__WRAPPER_RESOURCE_URI_" + wrapperObjectName + "_" //$NON-NLS-1$ //$NON-NLS-2$
						+ EcoreUtil.generateUUID()));

		wrapperResourceSet.getResources().add(wrapperResource);

		try
		{
			/*
			 * Have to crate a UML package to house the class that houses the properties that represent each process
			 * data. Otherwise we cannot set number facets on properties for number fields.
			 */
			org.eclipse.uml2.uml.Package wrapperPackage = UMLFactory.eINSTANCE.createModel();

			wrapperResource.getContents().add(wrapperPackage);

			/*
			 * Create the UML class that houses the properties that represent process script library.
			 */
			Class wrapperClass = UMLFactory.eINSTANCE.createClass();
			/*
			 * Sid ACE-7330 - suffix the bpmScripts-level class to indicate it is a PSL projects container, then
			 * DefaultJsClass.createJsReference() will use PSL project icon.
			 */
			String jsClassName = wrapperObjectName + DefaultJsClass.PSL_PROJECTS_CONTAINER_CLASS_SUFFIX;
			wrapperClass.setName(jsClassName);

			wrapperPackage.getPackagedElements().add(wrapperClass);

			List<ProcessScriptLibraryProject> projects = ProcessScriptLibraryUtil
					.getProcessScriptProjects(indexType);

			for (ProcessScriptLibraryProject project : projects)
			{
				project.accept(new ScriptUMLStructureBuilder(wrapperClass), null);
			}

			/*
			 * Create the appropriate structure for the script contributions (a DefaultJsClass wrapped in a
			 * DefaultUMLScriptRelevantData object.
			 */
			DefaultJsClass jsClass = new DefaultJsClass(wrapperClass);

			DefaultUMLScriptRelevantData scriptData = new DefaultUMLScriptRelevantData(wrapperObjectName, jsClassName,
					false, jsClass);
			scriptData.setReadOnly(true);

			/* Sid ACE-8307 provide some popup help guidance for static classes. */
			scriptData.setAdditionalInfo(Messages.BpmScriptWrapperFactory_BpmScripts_ContentAssist_Popup_Help);

			/*
			 * Sid ACE-5814 Noticed this throws exception for RASC generation command line (as we're running in headless
			 * mode and accessing UI components) - so conditionalised.
			 */
			if (!XpdResourcesPlugin.isInHeadlessMode())
			{
				scriptData.setIcon(JScriptUtils.getDefaultJavascriptIcon());
			}

			return scriptData;

		}
		finally
		{
			/*
			 * REMOVE that asset from the resource set (there is no tear-down lifecycle we can plugin into so we have to
			 * as we don't want a build up of resources.
			 * 
			 * As far as I can tell, once removed from resource set, it is still possible to read the primitive type
			 * facets stereo etc So downstream things using the wrapper class and properties should work fine.
			 */
			wrapperResourceSet.getResources().remove(wrapperResource);
		}
	}

	/**
	 * Visitor to build the UML Class structure to provide content assist.
	 *
	 * @author ssirsika
	 * @since 11-Mar-2024
	 */
	private final class ScriptUMLStructureBuilder implements ProcessScriptLibraryVisitor
	{
		private static final String	SPACE	= " ";	//$NON-NLS-1$
		/**
		 * 
		 */
		private final Class wrapperClass;

		/**
		 * @param wrapperClass
		 */
		private ScriptUMLStructureBuilder(Class wrapperClass)
		{
			this.wrapperClass = wrapperClass;
		}

		/**
		 * @see com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryVisitor#visit(com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryProject,
		 *      java.lang.Object)
		 *
		 * @param alibraryProject
		 * @param aContext
		 * @return
		 */
		@Override
		public Object visit(ProcessScriptLibraryProject alibraryProject, Object aContext)
		{
			Property property = UMLFactory.eINSTANCE.createProperty();
			property.setName(alibraryProject.getName());
			String a = property.getName();

			/* Sid ACE-8307 provide some popup help guidance for static classes. */
			Comment comment = UMLFactory.eINSTANCE.createComment();
			comment.setBody(
					String.format(Messages.BpmScriptWrapperFactory_BpmScriptProject_ContentAssist_Popup_Help, alibraryProject.getName()));
			property.getOwnedComments().add(comment);

			Class pslFileCollector = UMLFactory.eINSTANCE.createClass();
			// This name is not used while showing the content assist suggests.
			/*
			 * Sid ACE-7330 - suffix the project-level class to indicate it is a PSL files container, then
			 * DefaultJsClass.createJsReference() will use PSL file icon.
			 */
			pslFileCollector.setName(alibraryProject.getName() + DefaultJsClass.PSL_FILES_CONTAINER_CLASS_SUFFIX);
			property.setType(pslFileCollector);

			wrapperClass.getOwnedAttributes().add(property);

			return pslFileCollector;
		}

		/**
		 * @see com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryVisitor#visit(com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibrary,
		 *      java.lang.Object)
		 *
		 * @param aLibrary
		 * @param aContext
		 * @return
		 */
		@Override
		public Object visit(ProcessScriptLibrary aLibrary, Object aContext)
		{

			Property libProp = UMLFactory.eINSTANCE.createProperty();
			libProp.setName(aLibrary.getNameWithoutExtension());

			/* Sid ACE-8307 provide some popup help guidance for static classes. */
			Comment comment = UMLFactory.eINSTANCE.createComment();
			comment.setBody(String.format(Messages.BpmScriptWrapperFactory_BpmScriptLibrary_ContentAssist_Popup_Help, aLibrary.getName()));
			libProp.getOwnedComments().add(comment);

			Class pslFunctionCollector = UMLFactory.eINSTANCE.createClass();
			// This name is not used while showing the content assist suggests.
			/*
			 * Sid ACE-7330 - suffix the file-level class to indicate it is a PSL functions container, then
			 * DefaultJsClass.createJsReference() will use PSL function icon.
			 */
			pslFunctionCollector.setName(aLibrary.getName() + DefaultJsClass.PSL_FUNCTIONS_CONTAINER_CLASS_SUFFIX);
			libProp.setType(pslFunctionCollector);

			if (aContext instanceof Class)
			{
				((Class) aContext).getOwnedAttributes().add(libProp);
			}

			return pslFunctionCollector;
		}

		/**
		 * @see com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryVisitor#visit(com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptFunction,
		 *      java.lang.Object)
		 *
		 * @param aFunction
		 * @param aContext
		 * @return
		 */
		@Override
		public Object visit(ProcessScriptFunction aFunction, Object aContext)
		{
			Operation operation = UMLFactory.eINSTANCE.createOperation();
			operation.setName(aFunction.getName());

			Comment comment = UMLFactory.eINSTANCE.createComment();
			comment.setBody(buildMethodDescription(aFunction));

			operation.getOwnedComments().add(comment);

			if (aContext instanceof Class)
			{
				((Class) aContext).getOwnedOperations().add(operation);
			}
			return operation;
		}

		/**
		 * @see com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryVisitor#visit(com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptFunctionParam,
		 *      java.lang.Object)
		 *
		 * @param aParameter
		 * @param aContext
		 * @return
		 */
		@Override
		public Object visit(ProcessScriptFunctionParam aParameter, Object aContext)
		{
			if (aContext instanceof Operation)
			{
				Operation operation = (Operation) aContext;

				Parameter umlParam = UMLFactory.eINSTANCE.createParameter();
				umlParam.setName(aParameter.getName());
				umlParam.setType(resolveType(aParameter));
				/*
				 * Sid ACE-8226 Need to set multiplicity of parameter for correct validation of parameters when function
				 * is used.
				 */
				if (aParameter.isArray())
				{
					umlParam.setUpper(-1);
				}

				/*
				 * Sid ACE-8226 In order to support passing of specific Case class Case-Reference type to script library
				 * functions, we need to record that this parameter is a Cas-Ref for the actual Case Class type of the
				 * library function.
				 */
				if (ProcessScriptFunctionParamCategories.BOM_CASE_REF.equals(aParameter.getTypeCategory()))
				{
					umlParam.createEAnnotation(JsConsts.METHOD_PARAM_SPECIFIC_TYPE_CASEREF);
				}

				appendParamDescToOperationComment(operation, aParameter, umlParam);

				// If return parameter then set the direction
				if (aParameter.isReturnParam())
				{
					umlParam.setDirection(ParameterDirectionKind.RETURN_LITERAL);
				}
				operation.getOwnedParameters().add(umlParam);
				return umlParam;
			}
			return null;
		}

		/**
		 * This method appends the parameter related docs to operation comments.
		 * 
		 * @param anUmlParam
		 *            UML Parameter
		 * @param aParam
		 *            {@link ProcessScriptFunctionParam}
		 * @param anOperation
		 *            Operation to which comments need to be added.
		 */
		private void appendParamDescToOperationComment(Operation anOperation, ProcessScriptFunctionParam aParam,
				Parameter anUmlParam)
		{
			EList<Comment> ownedComments = anOperation.getOwnedComments();
			if (!ownedComments.isEmpty())
			{
				// Only consider first comment element.
				Comment comment = ownedComments.get(0);
				String body = comment.getBody();
				if (body != null)
				{
					String paramerDesc = buildParameterDescription(aParam, anUmlParam);
					// update existing comment
					comment.setBody(body.concat(paramerDesc));
				}
			}
		}

		/**
		 * Generate the description related to parameter by looking through PSL parameter model and UML parameter model.
		 * Returned description can be show in the content assist tooltip.
		 * 
		 * @param aParam
		 *            PSL Parameter {@link ProcessScriptFunctionParam}
		 * @param anUmlParam
		 *            UML parameter {@link Parameter}
		 * @return description related to parameter
		 */
		private String buildParameterDescription(ProcessScriptFunctionParam aParam, Parameter anUmlParam)
		{
			StringBuilder result = new StringBuilder(System.lineSeparator());
			Type type = anUmlParam.getType();

			/* Sid ACE-8226 Case references should be distinguished from BOM class in popup help */
			String typeName = ""; //$NON-NLS-1$
			
			if (type != null)
			{
				typeName = type.getName();

				if (ProcessScriptFunctionParamCategories.BOM_CASE_REF.equals(aParam.getTypeCategory()))
				{
					typeName = JsConsts.CASE_REFERENCE + "<" + typeName + ">"; //$NON-NLS-1$//$NON-NLS-2$
				}

				if (aParam.isArray())
				{
					typeName += "[]"; //$NON-NLS-1$
				}
			}
			else {
				typeName = ProcessScriptLibraryConstants.VOID_TYPE;
			}

			if (aParam.isReturnParam())
			{
				result.append("@return").append(SPACE); //$NON-NLS-1$
				result.append(typeName);

			}
			else
			{
				result.append("@param").append(SPACE); //$NON-NLS-1$
				result.append(anUmlParam.getName());
				if (type != null)
				{
					result.append(":"); //$NON-NLS-1$
					result.append(typeName);
				}
			}

			if (aParam.getDescription() != null)
			{
				result.append(SPACE).append(SPACE).append(aParam.getDescription());
			}
			return result.toString();
		}



		/**
		 * Build the method description string which can be shown in the content assist tooltip.
		 * 
		 * @param aFunction
		 *            Function for which description need to be generated.
		 * @return {@link String} description
		 */
		private String buildMethodDescription(ProcessScriptFunction aFunction)
		{
			String libName = aFunction.getProcessScriptLibrary().getNameWithoutExtension();
			StringBuilder result = new StringBuilder(
					String.format(Messages.BpmScriptFunctionSynopsis_ContentAssist_Popup_Help, libName,
							aFunction.getName()));
			String description = aFunction.getDescription();
			if (description != null && !description.trim().isEmpty())
			{
				result.append(String.format(Messages.BpmScriptFunctionUsage_ContentAssist_Popup_Help, description));
			}

			result.append(String.format(Messages.BpmScriptFunctionSyntax_ContentAssist_Popup_Help, aFunction.getSyntax()));
			return result.toString();
		}

		/**
		 * Resolve to {@link Type} from passed {@link ProcessScriptFunctionParam}
		 * 
		 * @param aParameter
		 *            {@link ProcessScriptFunctionParam}
		 * @return {@link Type}
		 */
		private Type resolveType(ProcessScriptFunctionParam aParameter)
		{
			if (aParameter == null)
			{
				return null;
			}

			ProcessScriptFunctionParamCategories typeCategory = aParameter.getTypeCategory();
			if (typeCategory == null)
			{
				return null;
			}
			// If category is BOM Class or Case Ref , then load the object from URI to read the class.
			if (ProcessScriptFunctionParamCategories.BOM_CLASS.equals(typeCategory)|| ProcessScriptFunctionParamCategories.BOM_CASE_REF.equals(typeCategory))
			{
				// Find the BOM references
				if (aParameter.getTypeId() != null)
				{
					URI uri = URI.createURI(aParameter.getTypeId());
					EObject eo = XpdResourcesPlugin.getDefault().getEditingDomain().getResourceSet().getEObject(uri,
							true);
					if (eo instanceof Class)
					{
						return (Class) eo;
					}
				}
			}
			else
			{
				String categoryLabel = typeCategory.toConstantString();
				if(ProcessScriptFunctionParamCategories.FIXED_POINT_NUMBER.equals(typeCategory) || ProcessScriptFunctionParamCategories.FLOATING_POINT_NUMBER.equals(typeCategory)) {
					categoryLabel = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
				}
				else if (ProcessScriptFunctionParamCategories.DATE_TIME_TIMEZONE.equals(typeCategory))
				{
					categoryLabel = PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME;
				}
				else if (ProcessScriptFunctionParamCategories.PERFORMER.equals(typeCategory)
						|| ProcessScriptFunctionParamCategories.URI.equals(typeCategory))
				{
					categoryLabel = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
				}
				return PrimitivesUtil.getStandardPrimitiveTypeByName(wrapperResourceSet, categoryLabel);
			}

			return null;
		}
	}
}
