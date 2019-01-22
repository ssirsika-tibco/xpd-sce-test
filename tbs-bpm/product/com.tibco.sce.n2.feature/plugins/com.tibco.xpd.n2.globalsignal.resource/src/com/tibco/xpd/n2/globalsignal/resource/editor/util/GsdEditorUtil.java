/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
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

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitionFactory;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Length;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Utility class for the Global Signal Definition Editor. Provide methods for
 * Editor functionality support like creation of gsd file, default Save option.
 * 
 * @author sajain
 * @since 27-Jan-2015
 */
public class GsdEditorUtil {

    private static final String GLOBAL_SIGNAL_DEFINITION_EDITOR_ID =
            "com.tibco.xpd.globalsignaldefinition.presentation.GlobalSignalDefinitionEditorID"; //$NON-NLS-1$

    /**
     * This method provides the default Save Options for the GSD resource.
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
     * This method creates a .gsd file at the given path. It sets the default
     * label for the gsd from its file name.
     * 
     * @param path
     *            at which the file will be created.
     * @param monitor
     */
    public static Resource createGSDFile(IPath path, IProgressMonitor monitor) {

        if (path != null && !path.isEmpty()) {

            URI uri = URI.createPlatformResourceURI(path.toString(), true);

            return createGSDFile(uri, monitor);
        }

        return null;

    }

    /**
     * Creates initial Global Signal Definition Model.
     * 
     * @param fileName
     */
    private static GlobalSignalDefinitions createInitialModel(String fileName) {

        /*
         * Create global signal definitions node.
         */
        GlobalSignalDefinitions globalSignalDefs =
                GlobalSignalDefinitionFactory.eINSTANCE
                        .createGlobalSignalDefinitions();

        /*
         * Set format version
         */
        globalSignalDefs.setFormatVersion(new BigInteger(
                GsdResourcePlugin.FORMAT_VERSION));

        /*
         * Create a global signal.
         */
        GlobalSignal gs =
                GlobalSignalDefinitionFactory.eINSTANCE.createGlobalSignal();

        /*
         * Set default name of the global signal.
         */
        gs.setName(Messages.NewGlobalSignalWizard_SignalName_value);

        /*
         * Create a Default Correlation field.
         */
        PayloadDataField pdf =
                GlobalSignalDefinitionFactory.eINSTANCE
                        .createPayloadDataField();

        pdf.setName(NameUtil
                .getInternalName(Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName,
                        true));

        Xpdl2ModelUtil.setOtherAttribute(pdf,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Messages.NewPayloadDataCreationWizard_PayloadDataDefaultName);

        /*
         * Set basic string type
         */
        BasicType basicType = Xpdl2Factory.eINSTANCE.createBasicType();
        basicType.setType(BasicTypeType.STRING_LITERAL);
        Length len = Xpdl2Factory.eINSTANCE.createLength();
        len.setValue("50"); //$NON-NLS-1$
        basicType.setLength(len);

        pdf.setDataType(basicType);
        pdf.setCorrelation(true);

        /*
         * Add default correlation field to the global signal.
         */
        gs.getPayloadDataFields().add(pdf);

        /*
         * Add the new global signal to the list of global signals in the gsd.
         */
        globalSignalDefs.getGlobalSignals().add(gs);

        return globalSignalDefs;
    }

    /**
     * This method creates a .gsd file for the given path.
     * 
     * @param path
     *            at which the file will be created.
     * @param monitor
     * 
     * @return Returns the new .gsd File, returns null if the resource could not
     *         be created.
     */
    public static Resource createGSDFile(final URI uri, IProgressMonitor monitor) {

        final Resource gsdResource;

        /*
         * If this is a valid platform URI
         */
        if (uri != null && uri.isPlatformResource()) {

            /*
             * If the monitor is not provided create one.
             */
            if (monitor == null) {
                monitor = new NullProgressMonitor();
            }

            /*
             * Make sure its our editing domain
             */
            TransactionalEditingDomain editingDomain =
                    XpdResourcesPlugin.getDefault().getEditingDomain();

            /*
             * Create GSD Resource
             */
            gsdResource = editingDomain.getResourceSet().createResource(uri);

            AbstractTransactionalCommand command =
                    new AbstractTransactionalCommand(
                            editingDomain,
                            Messages.GlobalSignalDefinitionEditorUtil_CreateGSDMessage,
                            Collections.EMPTY_LIST) {
                        @Override
                        protected CommandResult doExecuteWithResult(
                                IProgressMonitor monitor, IAdaptable info)
                                throws ExecutionException {

                            /*
                             * Extract the label for the GSD from the file name.
                             */
                            String fileName = uri.lastSegment();

                            /*
                             * Create initial model of the GSD, containing the
                             * root GSD element
                             */
                            GlobalSignalDefinitions root =
                                    createInitialModel(fileName);

                            gsdResource.getContents().add(root);

                            try {
                                /*
                                 * Save File
                                 */
                                gsdResource
                                        .save(GsdEditorUtil
                                                .getSaveOptions());

                            } catch (IOException e) {

                                GsdResourcePlugin
                                        .getDefault()
                                        .logError(Messages.GlobalSignalDefinitionEditorUtil_Error_Saving_GSD_Model,
                                                e);
                            }
                            return CommandResult.newOKCommandResult();
                        }
                    };

            /*
             * Run the command
             */
            try {
                OperationHistoryFactory.getOperationHistory().execute(command,
                        new SubProgressMonitor(monitor, 1),
                        null);
            } catch (ExecutionException e) {
                GsdResourcePlugin
                        .getDefault()
                        .logError(Messages.GlobalSignalDefinitionEditorUtil_Error_Creating_GSD_Model,
                                e);
            }

            /*
             * Return the create GSD Resource.
             */
            return gsdResource;
        }
        return null;
    }

    /**
     * Opens the given .gsd file in the Global Signal Definition editor. For
     * Invalid Global Signal Definition file, logs error.
     * 
     * @param newGSDFile
     */
    public static void openGlobalSignalDefinitionEditor(Resource newGSDFile) {

        /*
         * Try to Open the Editor for the new GSD File
         */
        try {

            /*
             * Get root EObject of the new created file
             */
            EObject eObject =
                    newGSDFile.getContents() != null ? newGSDFile.getContents()
                            .get(0) : null;

            /*
             * Root is absent INVALID FILE.
             */
            if (eObject == null) {
                throw new InvalidFileException(
                        Messages.GlobalSignalDefinitionEditorUtil_INVALID_FILE_ISSUE);
            }

            /*
             * Get file
             */
            IFile file = WorkingCopyUtil.getFile(eObject);

            /*
             * Try to open the editor.
             */
            IEditorInput editorInput = new FileEditorInput(file);

            IWorkbenchPage page =
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                            .getActivePage();

            page.openEditor(editorInput, GLOBAL_SIGNAL_DEFINITION_EDITOR_ID);

        }

        catch (InvalidFileException e) {

            GsdResourcePlugin.getDefault()
                    .logError("Unable to open editor", e); //$NON-NLS-1$

        } catch (PartInitException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();
        }
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

        /* Default the file name to the project name if project is selected. */
        if (!containerFullPath.isEmpty()) {

            String firstSegment = containerFullPath.segment(0);

            if (firstSegment != null && firstSegment.length() > 0) {

                IProject project =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .getProject(firstSegment);

                if (project != null && project.isAccessible()) {

                    fileName = project.getName();
                }
            }
        }

        if (fileName == null || fileName.trim().length() == 0) {

            fileName =
                    Messages.GlobalSignalDefinitionEditorUtil_DefautlFacade_FileName;
        }

        /*
         * Check and derive the next unique filename.
         */
        return SpecialFolderUtil.getUniqueFileName(containerFullPath,
                fileName,
                extension);
    }
}
