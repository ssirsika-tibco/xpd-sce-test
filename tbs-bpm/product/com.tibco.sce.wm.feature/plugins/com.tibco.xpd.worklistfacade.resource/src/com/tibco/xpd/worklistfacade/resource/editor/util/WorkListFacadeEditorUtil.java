/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.worklistfacade.resource.editor.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.worklistfacade.model.DocumentRoot;
import com.tibco.xpd.worklistfacade.model.WorkItemAttribute;
import com.tibco.xpd.worklistfacade.model.WorkItemAttributes;
import com.tibco.xpd.worklistfacade.model.WorkListFacade;
import com.tibco.xpd.worklistfacade.model.WorkListFacadeFactory;
import com.tibco.xpd.worklistfacade.resource.WorkListFacadeResourcePlugin;
import com.tibco.xpd.worklistfacade.resource.util.Messages;
import com.tibco.xpd.xpdl2.BasicType;

/**
 * Utility class for the Work List Facade Editor. Provide methods for Editor
 * functionality support like creation of facade file, default Save option,
 * 
 * @author aprasad
 * @since 26-Sep-2013
 */
public class WorkListFacadeEditorUtil {

    private static final String WORK_LIST_FACADE_EDITOR_ID =
            "com.tibco.xpd.worklistfacade.presentation.WorkListFacadeEditorID"; //$NON-NLS-1$

    /**
     * This method provides the default Save Options for the Facade resource.
     * returns map containing the save options.
     * 
     * @return, Default Save options map.
     */
    public static Map getSaveOptions() {
        Map saveOptions = new HashMap();
        saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
        saveOptions.put(Resource.OPTION_SAVE_ONLY_IF_CHANGED,
                Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
        return saveOptions;
    }

    /**
     * This method creates a Facade file at the given path. It sets the default
     * label for the facade from its file name.
     * 
     * @param path
     *            at which the file will be created.
     * @param monitor
     */
    public static Resource createWorkListFacadeFile(IPath path,
            IProgressMonitor monitor) {
        if (path != null && !path.isEmpty()) {
            URI uri = URI.createPlatformResourceURI(path.toString(), true);
            return createWorkListFacadeFile(uri, monitor);
        }
        return null;

    }

    /**
     * This method creates a Facade file for the given path.
     * 
     * @param path
     *            at which the file will be created.
     * @param monitor
     * @return Returns the new Facade File, returns null if the resource could
     *         not be created.
     */

    public static Resource createWorkListFacadeFile(final URI uri,
            IProgressMonitor monitor) {
        final Resource facadeResource;
        // if this is a valid platform URI
        if (uri != null && uri.isPlatformResource()) {
            // if the monitor is not provided create one.
            if (monitor == null) {
                monitor = new NullProgressMonitor();
            }
            // Make sure its our editing domain
            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            // create Facade Resource
            facadeResource = editingDomain.getResourceSet().createResource(uri);

            AbstractTransactionalCommand command =
                    new AbstractTransactionalCommand(
                            editingDomain,
                            Messages.WorkListFacadeEditorUtil_CreateFacadeMessage,
                            Collections.EMPTY_LIST) {
                        @Override
                        protected CommandResult doExecuteWithResult(
                                IProgressMonitor monitor, IAdaptable info)
                                throws ExecutionException {

                            // extract the label for the Facade from the file
                            // name.
                            String fileName = uri.lastSegment();

                            // create initial model of the Facade, containing
                            // the root WorkListFacade element
                            DocumentRoot root = createInitialModel(fileName);
                            facadeResource.getContents().add(root);

                            try {
                                // Save File
                                facadeResource.save(WorkListFacadeEditorUtil
                                        .getSaveOptions());

                            } catch (IOException e) {
                                WorkListFacadeResourcePlugin
                                        .getDefault()
                                        .logError(Messages.WorkListFacadeEditorUtil_Error_Saving_Facade_Model,
                                                e);
                            }
                            return CommandResult.newOKCommandResult();
                        }
                    };
            // Run the command
            try {
                OperationHistoryFactory.getOperationHistory().execute(command,
                        new SubProgressMonitor(monitor, 1),
                        null);
            } catch (ExecutionException e) {
                WorkListFacadeResourcePlugin
                        .getDefault()
                        .logError(Messages.WorkListFacadeEditorUtil_Error_Creating_Facade_Model,
                                e);
            }
            // return the create Facade Resource.
            return facadeResource;
        }
        return null;
    }

    /**
     * Creates initial Work List Facade Model.
     * 
     * @param fileName
     */
    private static DocumentRoot createInitialModel(String fileName) {
        DocumentRoot root =
                WorkListFacadeFactory.eINSTANCE.createDocumentRoot();
        WorkListFacade createWorkListFacadeType =
                WorkListFacadeFactory.eINSTANCE.createWorkListFacade();
        root.setWorkListFacade(createWorkListFacadeType);
        // set format version
        createWorkListFacadeType.setFormatVersion(new BigInteger(
                WorkListFacadeResourcePlugin.FORMAT_VERSION));
        return root;
    }

    /**
     * This method derives a new unique File Name , at the given path. Will
     * derive next unique name, if the given filename already exists at the
     * path.
     * 
     * @param containerFullPath
     * @param fileName
     * @param extension
     * @return String, unique name for the file.
     */
    public static String getUniqueFileName(IPath containerFullPath,
            String fileName, String extension) {

        if (containerFullPath == null) {
            containerFullPath = new Path(""); //$NON-NLS-1$
        }

        // /* Default the file name to the project name if project is selected.
        // */
        // if (!containerFullPath.isEmpty()) {
        // String firstSegment = containerFullPath.segment(0);
        //
        // if (firstSegment != null && firstSegment.length() > 0) {
        // IProject project =
        // ResourcesPlugin.getWorkspace().getRoot()
        // .getProject(firstSegment);
        // if (project != null && project.isAccessible()) {
        // fileName = project.getName();
        // }
        // }
        // }

        if (fileName == null || fileName.trim().length() == 0) {
            fileName = Messages.WorkListFacadeEditorUtil_DefautlFacade_FileName;
        }
        // Check and derive the next unique filename.
        return SpecialFolderUtil.getUniqueFileName(containerFullPath,
                fileName,
                extension);
    }

    /**
     * Check if the given file is a WorkListFacade file. Returns if it is a
     * valid WorkListFacade file .
     * 
     * @param file
     * @return boolean, validity of WorkListFacade file, false if it is invalid.
     */
    public static boolean isWorkListFacadeFile(IFile file) {
        return file.getFileExtension()
                .equals(WorkListFacadeResourcePlugin.WLF_FILE_EXTENSION);
    }

    /**
     * Returns the {@link WorkItemAttribute} with the given name if exists in
     * the WorkListFacade otherwise return null.
     * 
     * @param input
     * @param internalName
     * @return WorkItemAttribute, if another attribute with given name exists,
     *         null otherwise.
     */
    public static WorkItemAttribute getDuplicateAttributeAlias(
            WorkListFacade workListFacade, String internalName) {
        if (internalName == null || internalName.length() == 0) {
            return null;
        }
        WorkItemAttributes workItemAttributesContainer =
                workListFacade.getWorkItemAttributes();

        if (workItemAttributesContainer != null) {

            EList<WorkItemAttribute> attributesList =
                    workItemAttributesContainer.getWorkItemAttribute();

            if (attributesList != null)
                for (WorkItemAttribute attribute : attributesList) {
                    if (internalName.equals(attribute.getDisplayLabel())) {
                        return attribute;
                    }
                }
        }
        return null;
    }

    /**
     * Opens the given Work List Facade file in the Work List Facade editor. For
     * Invalid Work List Facade file, logs error.
     * 
     * @param newWorkListFacadeFile
     */
    public static void openWorkListFacadeEditor(Resource newWorkListFacadeFile) {
        // Try to Open the Editor for the new facade File
        try {
            // Get root EObject of the new created file
            EObject eObject =
                    newWorkListFacadeFile.getContents() != null ? newWorkListFacadeFile
                            .getContents().get(0) : null;
            // root is absent INVALID FILE.
            if (eObject == null) {
                throw new InvalidFileException(
                        Messages.WorkListFacadeEditorUtil_INVALID_FILE_ISSUE);
            }
            // get file
            IFile file = WorkingCopyUtil.getFile(eObject);
            // try to open the editor.
            IEditorInput editorInput;
            editorInput = new FileEditorInput(file);

            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();
            page.openEditor(editorInput, WORK_LIST_FACADE_EDITOR_ID);
        } catch (PartInitException ex) {
            WorkListFacadeResourcePlugin.getDefault()
                    .logError("Unable to open editor", ex); //$NON-NLS-1$
        } catch (InvalidFileException e) {
            WorkListFacadeResourcePlugin.getDefault()
                    .logError("Unable to open editor", e); //$NON-NLS-1$
        }
    }

    /**
     * Gets length restriction of the PrimitiveType for the passed property, if
     * not set, returns it from the ancestor.
     * 
     * @param property
     * @return String, representing the length Restriction of the Type of the
     *         passed property.
     */
    public static String getLengthRestriction(Property property) {

        String restrictionProperty = null;

        if (property.getType() instanceof PrimitiveType) {

            PrimitiveType type = (PrimitiveType) property.getType();
            BasicType basicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(type);

            String decimalPoint = null;

            switch (basicType.getType()) {
            case DATETIME_LITERAL:
                break;
            case FLOAT_LITERAL:
                restrictionProperty =
                        getRestrictionValue(type,
                                property,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);

                decimalPoint =
                        getRestrictionValue(type,
                                property,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);

                restrictionProperty =
                        (decimalPoint == null) ? restrictionProperty : String
                                .format("%s,%s", //$NON-NLS-1$
                                        restrictionProperty,
                                        decimalPoint);
                break;
            case INTEGER_LITERAL:
                restrictionProperty =
                        getRestrictionValue(type,
                                property,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);

                break;
            case STRING_LITERAL:
                restrictionProperty =
                        getRestrictionValue(type,
                                property,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);
            }
        }

        return restrictionProperty;
    }

    /**
     * Restriction value of the given property for the given PrimitiveType, if
     * the restrictions are not defined for given type, retrieves from the Base
     * Type.
     * 
     * @param type
     * @param property
     * @param restrictionProperty
     * @return String, value of given Restriction set for the type.
     */
    private static String getRestrictionValue(PrimitiveType type,
            Property property, String restrictionProperty) {
        if (restrictionProperty != null) {
            Object restriction =
                    PrimitivesUtil.getFacetPropertyValue(type,
                            restrictionProperty,
                            property);

            if (restriction instanceof String) {
                return (String) restriction;
            } else if (restriction instanceof Integer) {
                return String.valueOf(restriction);
            }
        }
        return null;
    }
}
