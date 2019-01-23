/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.imports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.wst.validation.ValidationFramework;
import org.eclipse.wst.validation.ValidationResults;
import org.eclipse.wst.validation.ValidatorMessage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.openarchitectureware.xtend.XtendFacade;

import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.imports.template.ImportTransformationData;
import com.tibco.xpd.bom.xsdtransform.internal.AbstractXtendImportTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;

/**
 * Transforms XSD Model to BOM.
 * <p>
 * <i>Created: 24 July 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class XSD2BOMTransformer extends AbstractXtendImportTransformer {

    private static final String EXTENSION = "transformToBom"; //$NON-NLS-1$

    private static final String XSD2BOM_EXTENSION_FILE =
            "com::tibco::xpd::bom::xsdtransform::imports::template::Xsd2Bom"; //$NON-NLS-1$

    private static final XtendFacade xtendFacade;

    static {
        xtendFacade = XtendFacade.create(XSD2BOM_EXTENSION_FILE);
        configureFacade(xtendFacade);
    }

    public XSD2BOMTransformer() {
    }

    public XSD2BOMTransformer(boolean doMerge, boolean doOverwrite) {
        this(doMerge, doOverwrite, false);
    }

    public XSD2BOMTransformer(boolean doMerge, boolean doOverwrite,
            boolean doRemoveXSDNotation) {
        this.doMerge = doMerge;
        this.doOverwrite = doOverwrite;
        this.doRemoveXSDNotation = doRemoveXSDNotation;
    }

    @Override
    protected List<IStatus> validateSource(ImportTransformationData data,
            Resource resource) {
        if (resource instanceof XSDResourceImpl) {
            return performValidationChecks(data, (XSDResourceImpl) resource);
        }

        // The resource is not an XSD resource
        List<IStatus> result = new ArrayList<IStatus>(1);
        result.add(new Status(
                IStatus.ERROR,
                Activator.PLUGIN_ID,
                String.format(Messages.XSD2BOMTransformer_NotXSDResource_error_message,
                        resource.getURI().toString())));
        return result;
    }

    @Override
    protected XtendFacade getXtendFacade() {
        return xtendFacade;
    }

    @Override
    protected String getExtension() {
        return EXTENSION;
    }

    /**
     * Validate the given XSD resource and all schemas it uses.
     * 
     * @param data
     * @param resource
     * @return list of problems, empty list if no problems found.
     */
    private List<IStatus> performValidationChecks(
            ImportTransformationData data, XSDResourceImpl resource) {
        List<IStatus> statusArr = null;
        if (resource != null) {
            statusArr = validateXSD(resource);
            XSDSchema schema = resource.getSchema();
            data.initialiseFormDefaultsAndWSDLSchemaPrefixMaps();
            HashMap<String, Boolean> parsedSchemas =
                    new HashMap<String, Boolean>();
            parsedSchemas.put(schema.getSchemaLocation(), Boolean.TRUE);
            HashMap<String, IProject> namespacesSet =
                    new HashMap<String, IProject>();
            statusArr.addAll(validateSchemaContents(data,
                    schema,
                    parsedSchemas,
                    false,
                    namespacesSet));
        }
        return statusArr != null ? statusArr : new ArrayList<IStatus>(0);
    }

    /**
     * Run the WST validation on the given resource.
     * 
     * @param resource
     * @return
     */
    private List<IStatus> validateXSD(Resource resource) {
        List<IStatus> statusArr = new ArrayList<IStatus>();
        IFile linkedFile = null;
        try {
            IFile tempFile = WorkspaceSynchronizer.getFile(resource);

            if (tempFile == null && getProject() != null) {
                /*
                 * If this is validating a file in the file system then we need
                 * to create a temporary linked file so that the WST validator
                 * can validate the resource
                 */
                String path = resource.getURI().toFileString();
                if (path != null) {
                    linkedFile =
                            createTempLinkedFile(getProject(), new Path(path));
                    tempFile = linkedFile;
                }
            }

            if (tempFile == null) {
                Activator
                        .getDefault()
                        .getLogger()
                        .info(Messages.XtendTransformer_cannot_validate_external_xsd_message);
                statusArr
                        .add(new Status(
                                IStatus.WARNING,
                                Activator.PLUGIN_ID,
                                Messages.XtendTransformer_cannot_validate_external_xsd_message));

            } else {
                tempFile.refreshLocal(IResource.DEPTH_ZERO,
                        new NullProgressMonitor());

                ValidationResults validateResults =
                        ValidationFramework.getDefault().validate(tempFile,
                                new NullProgressMonitor());

                for (ValidatorMessage validatorMessage : validateResults
                        .getMessages()) {
                    int attribute =
                            validatorMessage.getAttribute(IMarker.SEVERITY,
                                    IMarker.SEVERITY_WARNING);
                    if (attribute == IMarker.SEVERITY_ERROR) {
                        String errorMsg =
                                validatorMessage.getAttribute(IMarker.MESSAGE,
                                        ""); //$NON-NLS-1$
                        if (errorMsg.trim().length() > 0) {
                            statusArr.add(new Status(IStatus.ERROR,
                                    Activator.PLUGIN_ID, errorMsg));
                        }
                    }
                }
            }
        } catch (CoreException e) {
            statusArr.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e
                    .getLocalizedMessage()));
        } finally {
            // Remove temporary linked file
            if (linkedFile != null) {
                try {
                    linkedFile.delete(true, null);
                } catch (CoreException e) {
                    // Do nothing
                }
            }
        }
        return statusArr;
    }
}
