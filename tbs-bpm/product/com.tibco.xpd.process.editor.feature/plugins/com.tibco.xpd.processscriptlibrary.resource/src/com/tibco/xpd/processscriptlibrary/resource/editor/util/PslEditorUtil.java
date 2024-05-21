/*
* Copyright (c) 2004-2024. Cloud Software Group, Inc. All Rights Reserved.
*/

package com.tibco.xpd.processscriptlibrary.resource.editor.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.wizards.pages.AbstractXpdlProjectSelectPage;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processscriptlibrary.resource.ProcessScriptLibraryResourcePluginActivtor;
import com.tibco.xpd.processscriptlibrary.resource.config.ProcessScriptLibraryConstants;
import com.tibco.xpd.processscriptlibrary.resource.editor.input.ProcessScriptLibraryEditorInput;
import com.tibco.xpd.processscriptlibrary.resource.internal.Messages;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.ui.wizards.newproject.BasicNewXpdResourceWizard;
import com.tibco.xpd.xpdExtension.LibraryFunctionUseIn;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.PackageHeader;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.RedefinableHeader;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl.Xpdl2FileType;
import com.tibco.xpd.xpdl2.resources.XpdlMigrate;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class for the Process Script Library Editor. Provide methods for Editor functionality support like creation
 * of psl file, default Save option.
 *
 * @author cbabar
 * @since Jan 3, 2024
 */
public class PslEditorUtil
{

	public static final String	PROCESS_SCRIPT_LIBRARY_EDITOR_ID	= "com.tibco.xpd.processscriptlibrary.editor";	//$NON-NLS-1$

	/**
	 * Fixed name of return type parameter in the PSL function.
	 */
	public static final String	RETURN_PARAMETER_NAME				= "$RETURN";									//$NON-NLS-1$

	/*
	 * Constant for 'JavaScript' grammar type.
	 */
	private static final String	JAVASCRIPT							= "JavaScript";																		//$NON-NLS-1$

	/** The destination environment xpdl2 extended attribute name. */
	public static final String	DESTINATION_ATTRIBUTE				= "Destination";																	//$NON-NLS-1$


	// Suppress default constructor for noninstantiability
	private PslEditorUtil() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}

	/**
	 * This method provides the default Save Options for the PSL resource. returns map containing the save options.
	 * 
	 * @return, Default Save options map.
	 */
	public static Map getSaveOptions()
	{

		Map saveOptions = new HashMap();
		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$

		saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);

		return saveOptions;
	}

	/**
	 * This method creates a .psl file at the given path. It sets the default label for the psl from its file name.
	 * 
	 * @param path
	 *            at which the file will be created.
	 * @param monitor
	 */
	public static Resource createPSLFile(IPath path, IProgressMonitor monitor)
	{

		if (path != null && !path.isEmpty())
		{

			URI uri = URI.createPlatformResourceURI(path.toString(), true);

			return createPSLFile(uri, monitor);
		}

		return null;

	}

	/**
	 * This method creates a .psl file for the given path.
	 * 
	 * @param path
	 *            at which the file will be created.
	 * @param monitor
	 * 
	 * 
	 * @return Returns the new .psl File, returns null if the resource could not be created.
	 */
	public static Resource createPSLFile(final URI uri, IProgressMonitor monitor)
	{

		final Resource pslResource;

		/*
		 * If this is a valid platform URI
		 */
		if (uri != null && uri.isPlatformResource())
		{

			/*
			 * If the monitor is not provided create one.
			 */
			if (monitor == null)
			{
				monitor = new NullProgressMonitor();
			}

			/*
			 * Make sure its our editing domain
			 */
			TransactionalEditingDomain editingDomain = XpdResourcesPlugin.getDefault().getEditingDomain();

			/*
			 * Create PSL Resource
			 */
			pslResource = editingDomain.getResourceSet().createResource(uri);

			AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain,
					Messages.ProcessScriptLibraryEditorUtil_CreatePSLMessage, Collections.EMPTY_LIST)
			{
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
						throws ExecutionException
				{

					/*
					 * Create initial model of the PSL, containing the root PSL element
					 */
					Package root = createPSLModel();

					DocumentRoot docRoot = Xpdl2Factory.eINSTANCE.createDocumentRoot();
					docRoot.setPackage(root);

					pslResource.getContents().add(docRoot);


					try
					{
						/*
						 * Save File
						 */
						pslResource.save(PslEditorUtil.getSaveOptions());

					}
					catch (IOException e)
					{

						ProcessScriptLibraryResourcePluginActivtor.getDefault()
								.logError(Messages.ProcessScriptLibraryEditorUtil_Error_Saving_PSL_Model, e);
					}
					return CommandResult.newOKCommandResult();
				}
			};

			/*
			 * Run the command
			 */
			try
			{
				OperationHistoryFactory.getOperationHistory().execute(command, new SubProgressMonitor(monitor, 1),
						null);
			}
			catch (ExecutionException e)
			{
				ProcessScriptLibraryResourcePluginActivtor.getDefault()
						.logError(Messages.ProcessScriptLibraryEditorUtil_Error_Creating_PSL_Model, e);
			}

			/*
			 * Return the create PSL Resource.
			 */
			return pslResource;
		}
		return null;
	}

	/**
	 * Add new Script function i.e {@link Activity} in the passed process and returned the newly added {@link Activity}.
	 * 
	 * @param aPslProcess
	 *            PSL Process in which activity need to be added.
	 * @return newly added {@link Activity} otherwise null.
	 */
	public static Activity addNewScriptFunctionInProcess(Process aPslProcess)
	{
		TransactionalEditingDomain editingDomain = XpdResourcesPlugin.getDefault().getEditingDomain();

		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain,
				Messages.PslEditorUtil_NewScriptFnCommandTitle, Collections.emptyList())
		{
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException
			{

				Activity newActivity = addNewScriptActivityToProcess(aPslProcess);

				return CommandResult.newOKCommandResult(newActivity);
			}
		};

		/*
		 * Run the command
		 */
		try
		{
			IStatus result = OperationHistoryFactory.getOperationHistory().execute(command,
					new SubProgressMonitor(new NullProgressMonitor(), 1), null);
			if(result.isOK()) {
				Object returnValue = command.getCommandResult().getReturnValue();
				if (returnValue instanceof Activity)
				{
					return (Activity) returnValue;
				}
			}
		}
		catch (ExecutionException e)
		{
			ProcessScriptLibraryResourcePluginActivtor.getDefault()
					.logError(Messages.ProcessScriptLibraryEditorUtil_Error_Creating_PSL_Model, e);
		}
		return null;
	}
	/**
	 * Opens the given .psl file in the Process Script Library editor. For Invalid Process Script Library file, logs
	 * error.
	 * 
	 * @param newPSLFile
	 */
	public static void openProcessScriptLibraryEditor(Resource newPSLFile)
	{

		/*
		 * Try to Open the Editor for the new PSL File
		 */
		try
		{

			/*
			 * Get root EObject of the new created file
			 */
			EObject eObject = newPSLFile.getContents() != null ? newPSLFile.getContents().get(0) : null;

			/*
			 * Root is absent INVALID FILE.
			 */
			if (eObject == null)
			{
				throw new InvalidFileException(Messages.ProcessScriptLibraryEditorUtil_INVALID_FILE_ISSUE);
			}

			/*
			 * Get file
			 */
			IFile file = WorkingCopyUtil.getFile(eObject);

			openEditor(file);
		}

		catch (InvalidFileException e)
		{

			ProcessScriptLibraryResourcePluginActivtor.getDefault().logError("Unable to open editor", e); //$NON-NLS-1$

		}
		catch (PartInitException e)
		{

			e.printStackTrace();

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

	}

	/**
	 * Open the file in the ProcessScriptLibrary editor and reveal the {@link Activity}.
	 * 
	 * @param aFile
	 *            Input file
	 * @throws PartInitException
	 */
	public static void openEditor(IFile aFile) throws PartInitException
	{
		if (aFile == null)
		{
			return;
		}

		ProcessScriptLibraryEditorInput editorInput = new ProcessScriptLibraryEditorInput(aFile);

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		page.openEditor(editorInput, PROCESS_SCRIPT_LIBRARY_EDITOR_ID, true);

		selectAndReveal(editorInput.getActivity());
	}

	/**
	 * This method derives a new unique File Name , at the given path. Will derive next unique name, if the given
	 * filename already exists at the path.
	 * 
	 * @param containerFullPath
	 * @param fileName
	 * @param extension
	 * @return String, unique name for the file.
	 */
	public static String getUniqueFileName(IPath containerFullPath, String fileName, String extension)
	{

		if (containerFullPath == null)
		{

			containerFullPath = new Path(""); //$NON-NLS-1$
		}

		/* Default the file name to the project name if project is selected. */
		if (!containerFullPath.isEmpty())
		{

			String firstSegment = containerFullPath.segment(0);

			if (firstSegment != null && firstSegment.length() > 0)
			{

				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(firstSegment);

				if (project != null && project.isAccessible())
				{

					fileName = project.getName();
				}
			}
		}

		if (fileName == null || fileName.trim().length() == 0)
		{

			fileName = Messages.ProcessScriptLibraryEditorUtil_DefautlFacade_FileName;
		}

		fileName += Messages.PslEditorUtil_FileNameSuffix;

		/*
		 * Check and derive the next unique filename.
		 */
		return SpecialFolderUtil.getUniqueFileName(containerFullPath, fileName, extension);

	}

	/**
	 * Create process package for default psl file contents.
	 */
	public static Package createPSLModel()
	{
		Package xpdl2Package = Xpdl2Factory.eINSTANCE.createPackage();

		PackageHeader packageHeader = Xpdl2Factory.eINSTANCE.createPackageHeader();
		packageHeader.setVendor(AbstractXpdlProjectSelectPage.VENDOR_NAME);
		packageHeader.setCreated(new Date().toString());
		xpdl2Package.setPackageHeader(packageHeader);

		RedefinableHeader redefinableHeader = Xpdl2Factory.eINSTANCE.createRedefinableHeader();
		redefinableHeader.setAuthor(System.getProperty(AbstractXpdlProjectSelectPage.USER_NAME_PROPERTY));
		redefinableHeader.setVersion(AbstractXpdlProjectSelectPage.VERSION_ID);
		xpdl2Package.setRedefinableHeader(redefinableHeader);

		ExtendedAttribute attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
		attrib.setName(AbstractXpdlProjectSelectPage.CREATEDBYATTRIB);
		attrib.setValue(AbstractXpdlProjectSelectPage.BUSINESS_STUDIO_CONST);
		xpdl2Package.getExtendedAttributes().add(attrib);

		attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
		attrib.setName(XpdlMigrate.FORMAT_VERSION_ATT_NAME);
		attrib.setValue(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
		xpdl2Package.getExtendedAttributes().add(attrib);

		/* Store the OriginalFormatVersion that xpdl was created in. */
		attrib = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
		attrib.setName(XpdlMigrate.ORIGINAL_FORMAT_VERSION_ATT_NAME);
		attrib.setValue(XpdlMigrate.FORMAT_VERSION_ATT_VALUE);
		xpdl2Package.getExtendedAttributes().add(attrib);

		Process process = createProcess();
		xpdl2Package.getProcesses().add(process);
		
		
		return xpdl2Package;
	}

	/**
	 * Get the unique script library function name with respect to passed flowContainer (process).
	 * 
	 * @param aContainer
	 *            Container to check for duplicates.
	 * @return unique function (activity) name
	 */
	public static String getUniqueFunctionName(FlowContainer aContainer)
	{
		int idx = 1;
		final String baseName = "function"; //$NON-NLS-1$
		String result = baseName + idx;
		while (isActivityNameExists(result, aContainer))
		{
			idx++;
			result = baseName + idx;
		}

		return result;
	}

	/**
	 * @return created new default {@link Process} for psl file.
	 */
	private static Process createProcess()
	{
		com.tibco.xpd.xpdl2.Process processObj = Xpdl2Factory.eINSTANCE.createProcess();

		addNewScriptActivityToProcess(processObj);

		// Set Destination environment
		ExtendedAttribute att = Xpdl2Factory.eINSTANCE.createExtendedAttribute();
		att.setName(DESTINATION_ATTRIBUTE);
		att.setValue(XpdConsts.ACE_DESTINATION_NAME);
		processObj.getExtendedAttributes().add(att);

		return processObj;
	}

	/**
	 * @param pslProcess
	 * @return
	 */
	private static Activity addNewScriptActivityToProcess(com.tibco.xpd.xpdl2.Process pslProcess)
	{
		TaskScript taskScript = Xpdl2Factory.eINSTANCE.createTaskScript();

		Expression expression = Xpdl2ModelUtil.createExpression("// Add the script function content here..."); //$NON-NLS-1$
		expression.setScriptGrammar(JAVASCRIPT);

		taskScript.setScript(expression);

		Task task = Xpdl2Factory.eINSTANCE.createTask();
		task.setTaskScript(taskScript);
		Activity activity = Xpdl2Factory.eINSTANCE.createActivity();
		activity.setName(getUniqueFunctionName(pslProcess));
		activity.setImplementation(task);
		Description activityDesc = Xpdl2Factory.eINSTANCE.createDescription();
		activityDesc.setValue("Add your function summary here..."); //$NON-NLS-1$
		activity.setDescription(activityDesc);

		Xpdl2ModelUtil.setOtherAttribute(activity, XpdExtensionPackage.eINSTANCE.getDocumentRoot_UseIn(),
				LibraryFunctionUseIn.ALL);
		
		initDataFields(activity);

		pslProcess.getActivities().add(activity);

		return activity;
	}

	/**
	 * Test the existence of the passed name in the passed container
	 * 
	 * @param aName
	 *            Name to test for duplicate
	 * @param aContainer
	 *            Container with respect to test.
	 * @return <code>true</code> if passed name exists otherwise <code>false</code>.
	 */
	private static boolean isActivityNameExists(String aName, FlowContainer aContainer)
	{
		EList<Activity> activities = aContainer.getActivities();
		for (Activity activity : activities)
		{
			if (activity.getName().equals(aName))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Create the initial Data Fields and add it to activity.
	 * 
	 * @param anActivity
	 */
	private static void initDataFields(Activity anActivity)
	{
		anActivity.getDataFields()
				.add(createDataField(RETURN_PARAMETER_NAME, "Add your return parameter summary here...")); //$NON-NLS-1$
		anActivity.getDataFields().add(createDataField("parameter", "Add your parameter summary here...")); //$NON-NLS-1$//$NON-NLS-2$
		anActivity.getDataFields().add(createDataField("parameter2", "Add your parameter summary here...")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Create and return data field with passed name and description.
	 * 
	 * @param aName
	 *            Name of new data field.
	 * @param aDescription
	 *            Description of new data field.
	 * @return new {@link DataField}
	 */
	private static DataField createDataField(String aName, String aDescription)
	{
		DataField dataField = Xpdl2Factory.eINSTANCE.createDataField();

		// Name
		dataField.setName(aName);

		// Description
		Description description = Xpdl2Factory.eINSTANCE.createDescription();
		description.setValue(aDescription);
		dataField.setDescription(description);

		// Data type
		BasicType dataType = Xpdl2Factory.eINSTANCE.createBasicType();
		dataType.setType(BasicTypeType.STRING_LITERAL);

		Length len = Xpdl2Factory.eINSTANCE.createLength();
		len.setValue("50"); //$NON-NLS-1$
		dataType.setLength(len);

		dataField.setDataType(dataType);

		return dataField;
	}

	/**
	 * 
	 * Function to determine weather the passed object is a script library function.
	 * 
	 * @param toTest
	 * @return true if the passed object is a script library function or else false.
	 */
	public static boolean isScriptLibraryFunction(Object toTest)
	{
		if (toTest instanceof Activity)
		{
			Activity act = (Activity) toTest;
			if (TaskType.SCRIPT_LITERAL.equals(TaskObjectUtil.getTaskTypeStrict(act)))
			{
				WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(act);

				if (wc instanceof Xpdl2WorkingCopyImpl)
				{
					Xpdl2FileType xpdl2FileType = ((Xpdl2WorkingCopyImpl) wc).getXpdl2FileType();

					if (Xpdl2FileType.SCRIPT_LIBRARY.equals(xpdl2FileType))
					{
						return true;
					}
				}

			}
		}

		return false;
	}

	/**
	 * Attempts to select and reveal the supplied object.
	 * 
	 * @param objectToSelect
	 *            Object to select.
	 */
	private static void selectAndReveal(Object objectToSelect)
	{
		Display.getDefault().asyncExec(() -> BasicNewXpdResourceWizard.selectAndReveal(objectToSelect,
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()));
	}
	

	/**
	 * Generates a string representation of a method signature for the specified activity. This method dynamically
	 * constructs the method signature by analyzing the activity's data fields to determine input parameters and the
	 * return type. The signature can be generated in either plain text or HTML format, and there is an option to
	 * include or exclude the return type in the generated signature.
	 *
	 * @param activity
	 *            The {@link Activity} object which represents the PSL function.
	 * @param supportHtml
	 *            If true, the method signature will be returned in HTML format. If false, the signature will be
	 *            returned as plain text.
	 * @param needReturn
	 *            If true, the return type of the method (if any) will be included at the beginning of the signature. If
	 *            the activity does not specify a return type, "Void" is used as a default. If false, the return type is
	 *            omitted from the signature.
	 * 
	 * @return A string representation of the method signature for the given activity, formatted according to the
	 *         specified parameters.
	 */
	public static String generateMethodSignature(Activity activity, boolean supportHtml, boolean needReturn)
	{
		EList<DataField> fields = activity.getDataFields();
		// function parameters collection
		List<ParameterData> fnParameters = new ArrayList<>();
		String returnType = null;
		for (DataField dataField : fields)
		{
			ParameterData data = new ParameterData(dataField);
			if (data.isReturnParameter())
			{
				returnType = data.getTypeString();
			}
			else
			{
				fnParameters.add(data);
			}
		}

		StringBuilder signature = new StringBuilder();
		if (supportHtml)
		{
			signature.append("<form style=\"font-family: monospace;\"><p>"); //$NON-NLS-1$
		}
		final String blankStr = " "; //$NON-NLS-1$
		if (needReturn)
		{
			if (returnType != null && !returnType.trim().isEmpty())
			{
				signature.append(returnType);
			}
			else
			{
				signature.append("Void"); //$NON-NLS-1$
			}
			signature.append(blankStr);
		}

		if (supportHtml)
		{
			signature.append("<b>"); //$NON-NLS-1$
		}
		signature.append(activity.getName());

		if (supportHtml)
		{
			signature.append("</b>"); //$NON-NLS-1$
		}
		signature.append(" ("); //$NON-NLS-1$
		// add function parameters
		for (int i = 0; i < fnParameters.size(); i++)
		{
			ParameterData parameter = fnParameters.get(i);
			signature.append(parameter.getTypeString()).append(blankStr).append(parameter.getName());
			if (i < fnParameters.size() - 1)
			{
				signature.append(", "); //$NON-NLS-1$
			}
		}

		// Close the function signature
		signature.append(")"); //$NON-NLS-1$
		if (supportHtml)
		{
			signature.append("</p></form>"); //$NON-NLS-1$
		}
		return signature.toString();
	}

	/**
	 * Removes the psl extension from the file name.
	 *
	 * @param fileName
	 *            The name of the file.
	 * @return The file name without the extension.
	 */
	public static String removePSLExtension(String fileName)
	{
		String extension = ProcessScriptLibraryConstants.PSL_FILE_EXTENSION;
		if (fileName == null)
		{
			return fileName;
		}

		// Check if the fileName ends with the extension
		if (fileName.toLowerCase().endsWith(extension.toLowerCase()))
		{
			// Remove the extension.
			return fileName.substring(0, fileName.length() - extension.length() - 1);
		}

		return fileName;
	}

	/**
	 *
	 * Private data class to store the Parameter info.
	 * 
	 * @author ssirsika
	 * @since 19-Feb-2024
	 */
	private static class ParameterData
	{

		private static final String	CASE_REF_TYPE		= Messages.PslEditorUtil_CaseRef_label;

		private final DataField		dataField;

		/**
		 * 
		 */
		ParameterData(final DataField aDataField)
		{
			this.dataField = Objects.requireNonNull(aDataField, "dataField cannot be null"); //$NON-NLS-1$
		}

		/**
		 * Return the string representation for data type name.
		 * 
		 * @return string representation for data type name.
		 * 
		 */
		String getTypeString()
		{
			String typeStr = null;
			DataType dataType = dataField.getDataType();
			// if it's a return parameter and dataType is null then it's a void type
			if (isReturnParameter() && dataType == null)
			{
				return ProcessScriptLibraryConstants.VOID_TYPE;
			}
			Object baseType = BasicTypeConverterFactory.INSTANCE.getBaseType(dataType, true);
			// Basic Type
			if (baseType instanceof BasicType)
			{
				typeStr = ProcessDataUtil.getBasicTypeLabel((BasicType) baseType);
			}
			// BOM class reference
			else if (baseType instanceof Classifier)
			{
				typeStr = getDisplayString(dataType, (Classifier) baseType);
			}
			// Reference can not be resolved.
			if (typeStr == null)
			{
				return Messages.PslEditorUtil_UnresolvedRefMsg;
			}

			// Handle array
			return dataField.isIsArray() ? typeStr.concat("[ ]") : typeStr; //$NON-NLS-1$
		}

		/**
		 * Return the display string using passed data type and classifier.
		 * 
		 * @param dataType
		 *            DataType
		 * @param baseType
		 *            BaseType classifier for passed dataType.
		 * @return Display {@link String}
		 */
		private String getDisplayString(DataType aDataType, Classifier aClassifer)
		{
			String name = aClassifer.getLabel() != null ? aClassifer.getLabel() : aClassifer.getName();
			if (aDataType instanceof RecordType)
			{
				return String.format("%s(%s)", name, CASE_REF_TYPE); //$NON-NLS-1$
			}
			// return name as it is, for all other condition like BOM classes.
			return name;
		}

		/**
		 * @return Name of dataField.
		 * 
		 */
		String getName()
		{
			return dataField.getName();
		}

		/**
		 * Checks if the parameter is a return parameter.
		 * 
		 * @return <code>true</code> if the dataField represents a return parameter, <code>false</code> otherwise.
		 */
		boolean isReturnParameter()
		{
			return RETURN_PARAMETER_NAME.equals(dataField.getName());

		}
	}
}
