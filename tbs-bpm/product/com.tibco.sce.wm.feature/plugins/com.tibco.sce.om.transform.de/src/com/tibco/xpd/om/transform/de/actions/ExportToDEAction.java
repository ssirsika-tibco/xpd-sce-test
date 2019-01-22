/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.om.transform.de.TransformDEActivator;
import com.tibco.xpd.om.transform.de.XMLModelWriter;
import com.tibco.xpd.om.transform.de.internal.Messages;
import com.tibco.xpd.om.transform.de.transform.OrgModelTransformer;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.ValidationException;

/**
 * Exports Organisation Model to Directory Engine model.
 * <p>
 * <i>Created: 13 Mar 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ExportToDEAction extends BaseSelectionListenerAction {

    /** Directory Engine Output file extension. */
    public static final String OUTPUT_FILE_EXTENSION = "xml"; //$NON-NLS-1$

    /** Unique action identifier. */
    private static final String ID = TransformDEActivator.PLUGIN_ID
            + ".generateLabelProperties"; //$NON-NLS-1$

    private String outputPath;

    private boolean isBuilderAction;

    /**
     * Ids of validation destinations to validate the model against before
     * building it.
     */
    private static final String[] DESTINATION_IDS = new String[] {
            "com.tibco.xpd.om.validator.generic.destination", //$NON-NLS-1$
            "com.tibco.xpd.om.validator.de1.x.destination" }; //$NON-NLS-1$

    private final List<Destination> destinations;

    /**
     * The constructor
     */
    public ExportToDEAction() {
        this(false, false);
    }

    public ExportToDEAction(boolean alwaysOverwrite, boolean isBuilderAction) {
        super(Messages.ExportToDEAction_exportToDE_menu);
        setId(ID);
        this.isBuilderAction = isBuilderAction;

        destinations = new ArrayList<Destination>();
        for (String id : DESTINATION_IDS) {
            Destination destination =
                    ValidationActivator.getDefault().getDestination(id);
            if (destination != null) {
                destinations.add(destination);
            } else {
                throw new IllegalArgumentException(
                        "No validation destination with id '" + id + "' found."); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
    }

    /**
     * Check if the selected resource has any validation problems.
     * 
     * @return <code>true</code> if it has validation problems.
     * @throws CoreException
     */
    public boolean isValid() throws CoreException {
        IStructuredSelection selection = getStructuredSelection();
        if (!destinations.isEmpty() && selection != null
                && !selection.isEmpty()
                && selection.getFirstElement() instanceof IFile) {
            IFile resource = (IFile) selection.getFirstElement();
            Validator validator = new Validator(destinations);
            try {
                Collection<IIssue> issues = validator.validate(resource);
                if (issues != null && !issues.isEmpty()) {
                    for (IIssue issue : issues) {
                        if (issue.getSeverity() == IMarker.SEVERITY_ERROR) {
                            // Got validation problems
                            return false;
                        }
                    }
                }
            } catch (ValidationException e) {
                throw new CoreException(new Status(IStatus.ERROR,
                        TransformDEActivator.PLUGIN_ID, IStatus.OK,
                        e.getLocalizedMessage(), e));
            }
            // Run EMF validation
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(resource);
            if (wc != null) {
                if (wc.isInvalidFile()) {
                    return false;
                }

                IStatus status = Validator.runEMFValidation(wc);
                if (!status.isOK()) {
                    return false;
                }
            }
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        // XPD-5464 :Transformation tests
        IFile modelFile = getFileFromSelection();
        if (modelFile == null) {
            return;
        }
        final IFile deExportedFile =
                ResourcesPlugin.getWorkspace().getRoot()
                        .getFile(new Path(getOutputPath()));

        if (isBuilderAction && deExportedFile.exists()
                && !deExportedFile.isDerived()) {
            OMResourcesUIActivator
                    .getDefault()
                    .getLogger()
                    .warn(String.format(Messages.ExportToDEAction_fileWillBeIgnored_message,
                            deExportedFile.getName()));
            return;
        }

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(modelFile);

        EObject resultEObject = null;
        String name = null;
        String version = null;
        if (wc instanceof OMWorkingCopy
                && ((OMWorkingCopy) wc).getRootElement() instanceof OrgModel
                && ((OMWorkingCopy) wc).getRootElement().eResource() != null) {
            EObject orgModel = ((OMWorkingCopy) wc).getRootElement();
            // Collection<EObject> result =
            // new XtendTransformer().transform(Arrays.asList(orgModel));
            OrgModelTransformer orgModelTransformer = new OrgModelTransformer();
            resultEObject =
                    orgModelTransformer
                            .transformOrgModel((OrgModel) orgModel);
            if (orgModel instanceof OrgModel) {
                name = ((OrgModel) orgModel).getName();
                version = ((OrgModel) orgModel).getVersion();
            }
        }

        final EObject directory = resultEObject;
        final String modelName = name;
        final String modelVersion = version;
        try {
            new XMLModelWriter()
                    .write(deExportedFile, Arrays.asList(directory));

            if (deExportedFile.exists()) {
                deExportedFile
                        .setPersistentProperty(TransformDEActivator.MODEL_NAME,
                                modelName);
                deExportedFile
                        .setPersistentProperty(TransformDEActivator.MODEL_VERSION,
                                modelVersion);
                deExportedFile.setDerived(isBuilderAction);
            }

        } catch (final Exception e) {
            TransformDEActivator
                    .getDefault()
                    .getLogger()
                    .error(e,
                            "Exception thrown while generating Directory Engine file."); //$NON-NLS-1$
        }
    }

    /**
     * @return
     */
    private IFile getFileFromSelection() {
        IStructuredSelection selection = getStructuredSelection();
        if (!selection.isEmpty()) {
            Object first = selection.getFirstElement();
            if (first instanceof IFile) {
                return (IFile) first;
            } else if (first instanceof IAdaptable) {
                return (IFile) ((IAdaptable) first).getAdapter(IFile.class);
            }
        }
        return null;
    }

    /**
     * Gets the output path. If the output path is not set it will return
     * default path which is the same as the source file but with and 'xml'
     * extension.
     * 
     * @return The output path. It will be the workspace root relative path.
     */
    public String getOutputPath() {
        if (outputPath == null) {
            return getDefaultOutputPath();
        }
        return outputPath;
    }

    /**
     * Sets the output path.
     * 
     * @param outputPath
     *            the outputPath to set. This must be workspace root relative
     *            path.
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @return
     */
    private String getDefaultOutputPath() {
        IFile modelFile = getFileFromSelection();
        if (modelFile == null) {
            return null;
        }
        return modelFile.getFullPath().removeFileExtension()
                .addFileExtension(OUTPUT_FILE_EXTENSION).toPortableString();

    }

}
