/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.wsdltransform.exports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.uml2.uml.Class;
import org.eclipse.wst.wsdl.util.WSDLDiagnostic;
import org.eclipse.wst.wsdl.util.WSDLDiagnosticSeverity;
import org.eclipse.wst.wsdl.util.WSDLParser;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.OawXMLResource;
import org.openarchitectureware.xsd.XSDMetaModel;
import org.openarchitectureware.xsd.builder.OawXSDResource;
import org.openarchitectureware.xtend.XtendFacade;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.bom.wsdltransform.internal.Messages;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.exports.template.ExportTransformationData;
import com.tibco.xpd.bom.xsdtransform.internal.AbstractXtendExportTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.BaseBOMXtendTransformer;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.validation.provider.ValidationException;

/**
 * Transforms BOM Model to WSDL.
 * <p>
 * <i>Created: 24 July 2008</i>
 * </p>
 * 
 * @author Gary Lewis
 */
public class BOM2WSDLTransformer extends AbstractXtendExportTransformer {

    private static final String EXTENSION_FILE =
            "com::tibco::xpd::bom::wsdltransform::exports::template::Bom2Wsdl"; //$NON-NLS-1$

    private static final String INCREMENTAL_EXTENSION_FILE =
            "com::tibco::xpd::bom::wsdltransform::exports::template::IncrementalBom2Wsdl"; //$NON-NLS-1$

    private static final String INCREMENTAL_PRESERVE_EXTENSION_FILE =
            "com::tibco::xpd::bom::wsdltransform::exports::template::IncrementalPreserveBom2Wsdl"; //$NON-NLS-1$

    private static final String TRANSFORM_EXPRESSION = "transformToWsdl"; //$NON-NLS-1$

    private static final String INCREMENTAL_TRANSFORM_EXPRESSION =
            "incrementalTransformToWsdl"; //$NON-NLS-1$

    private static final String INCREMENTAL_PRESERVE_TRANSFORM_EXPRESSION =
            "incrementalPreserveTransformToWsdl"; //$NON-NLS-1$

    static final String JAXP_SCHEMA_LANGUAGE =
            "http://java.sun.com/xml/jaxp/properties/schemaLanguage"; //$NON-NLS-1$

    static final String W3C_XML_SCHEMA = "http://schemas.xmlsoap.org/wsdl/"; //$NON-NLS-1$

    private static final XtendFacade facade;

    private static final XtendFacade incrementalPreserveFacade;

    private static final XtendFacade incrementalFacade;

    static {
        facade = XtendFacade.create(EXTENSION_FILE);
        configureWorkflow(facade);
        incrementalPreserveFacade =
            XtendFacade.create(INCREMENTAL_PRESERVE_EXTENSION_FILE);
        configureWorkflow(incrementalPreserveFacade);
        incrementalFacade = XtendFacade.create(INCREMENTAL_EXTENSION_FILE);
        configureWorkflow(incrementalFacade);
    }

    /**
     * Performs the actual transformation
     * 
     * @param IFile
     *            source
     * @param wsdlFilePath
     * @return
     */
    public List<IStatus> transform(IFile source, IPath wsdlFilePath,
            IProgressMonitor monitor) {
        List<IStatus> result = new ArrayList<IStatus>();
        if (source != null && wsdlFilePath != null) {
            if (monitor == null) {
                monitor = new NullProgressMonitor();
            }
            monitor.beginTask(String
                    .format(Messages.BOM2WSDLTransformer_exporting_progress_shortdesc,
                            source.getFullPath().toString()),
                    5);
            try {
                try {
                    if (isDestinationAndNoErrors(source,
                            BomGenPreferenceConstants.WSDL_MARKER_TYPE,
                            BaseBOMXtendTransformer.WSDL_DESTINATION,
                            result)) {
                        monitor.worked(1);
                        ExportTransformationData data =
                                new ExportTransformationData();
                        data.setTopLevelSourceFile(source);
                        String uri = source.getFullPath().toPortableString();

                        WorkingCopy wc =
                                XpdResourcesPlugin.getDefault()
                                        .getWorkingCopy(source);
                        EObject model = wc.getRootElement();
                        monitor.worked(1);

                        EObject tempObj =
                                (EObject) runWorkflowFacade(data,
                                        model,
                                        uri,
                                        facade,
                                        result);
                        monitor.worked(1);
                        if (tempObj != null) {
                            try {
                                String filename =
                                        source.getName()
                                                .replace(".bom", ".wsdl"); //$NON-NLS-1$ //$NON-NLS-2$//                                    
                                URI tempFileURI = null;

                                IFolder folder = null;
                                if (wsdlFilePath.getDevice() == null) {
                                    folder =
                                            ResourcesPlugin.getWorkspace()
                                                    .getRoot()
                                                    .getFolder(wsdlFilePath);
                                }
                                if (folder == null
                                        || folder.getLocationURI() == null) {
                                    tempFileURI =
                                            URI.createFileURI(wsdlFilePath
                                                    .append(filename)
                                                    .toString());
                                } else {
                                    tempFileURI =
                                            URI.createPlatformResourceURI(wsdlFilePath
                                                    .append(filename)
                                                    .toString(),
                                                    true);
                                }

                                Resource res =
                                        new OawXMLResource(tempFileURI,
                                                new XSDMetaModel());
                                try {
                                    if (res.getContents() != null) {
                                        res.getContents().add(tempObj);
                                        res.save(new HashMap<Object, Object>());

                                        // Load the XSD resource so that it can
                                        // be validated
                                        OawXSDResource xsdResource =
                                                new OawXSDResource(tempFileURI);
                                        xsdResource.load(Collections.EMPTY_MAP);
                                        XSDSchema schema =
                                                xsdResource.getSchema();
                                        try {
                                            if (schema != null) {
                                                result.addAll(validateSchema(schema,
                                                        true));
                                            } else {
                                                result.add(new Status(
                                                        IStatus.ERROR,
                                                        Activator.PLUGIN_ID,
                                                        String.format(Messages.BOM2WSDLTransformer_unableToLoadSchema_error_message,
                                                                tempFileURI)));
                                            }
                                        } finally {                                            
                                            xsdResource = null;
                                        }
                                    }
                                } finally {
                                    res.unload();
                                    res = null;
                                }
                            } catch (Exception e) {
                                result.add(new Status(
                                        IStatus.ERROR,
                                        Activator.PLUGIN_ID,
                                        String.format(Messages.BOM2WSDLTransformer_problemGeneringWSDL_error_message,
                                                source.getFullPath().toString()),
                                        e));
                                return result;
                            }
                        }
                        monitor.worked(1);
                    }
                } catch (ValidationException e) {
                    result.add(new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            String.format(Messages.BOM2WSDLTransformer_validationFailed_error_message,
                                    source.getFullPath().toString()), e));
                }
            } finally {
                monitor.done();
            }
        }

        return result;
    }

    /**
     * @param model
     * @return
     */
    private Object runWorkflowFacade(ExportTransformationData data,
            EObject model, String url, XtendFacade f, List<IStatus> result) {
        if (model != null) {
            try {
                return f.call(TRANSFORM_EXPRESSION, data, model, url);
            } catch (Exception e) {
                if (result != null) {
                    result.add(new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            Messages.BOM2WSDLTransformer_errorInTransformation_error_message,
                            e));
                }
            }
        }
        return null;
    }

    /**
     * Run an incremental preserve transformation to produce the WSDL from the
     * selected class.
     * 
     * @param umlClass
     *            class to create WSDL from
     * @param wsdlUri
     *            the uri of the wsdl file to create
     * @param result
     *            return any errors/warnings reported during the transformation,
     *            can be <code>null</code> if no result is required.
     * @return
     */
    public Object incrementalPreserveTransform(final Class umlClass,
            URI wsdlUri, List<IStatus> result) {
        EObject eObj = null;
        if (umlClass != null) {
            if (result == null) {
                result = new ArrayList<IStatus>();
            }
            IFile source = WorkspaceSynchronizer.getFile(umlClass.eResource());
            try {
                if (source != null
                        && isDestinationAndNoErrors(source,
                                BomGenPreferenceConstants.WSDL_MARKER_TYPE,
                                BaseBOMXtendTransformer.WSDL_DESTINATION,
                                result)) {
                    ExportTransformationData data =
                            new ExportTransformationData();

                    data.setTopLevelSourceFile(source);

                    // Facade
                    String uri = BomUIUtil.getURI(umlClass);
                    eObj =
                            (EObject) runIncrementalPreserveWorkflow(data,
                                    umlClass,
                                    uri,
                                    incrementalPreserveFacade,
                                    result);

                    // If no errors then continue
                    if (getErrors(result).isEmpty()) {
                        result.addAll(validateWSDL(eObj, wsdlUri));
                        // If no errors then continue
                        if (getErrors(result).isEmpty()) {
                            Resource res =
                                    new OawXMLResource(wsdlUri,
                                            new XSDMetaModel());
                            try {
                                if (res.getContents() != null) {
                                    res.getContents().add(eObj);
                                    try {
                                        String uriPath = wsdlUri.toFileString();
                                        if (uriPath == null) {
                                            uriPath = wsdlUri.toString();
                                        }
                                        File file = new File(uriPath);
                                        if (file.exists()) {
                                            file.delete();
                                        }
                                        res.save(new HashMap<Object, Object>());
                                    } catch (IOException e) {
                                        result.add(new Status(
                                                IStatus.ERROR,
                                                Activator.PLUGIN_ID,
                                                String.format(Messages.BOM2WSDLTransformer_problemGeneringWSDL_error_message,
                                                        source.getFullPath()
                                                                .toString()), e));
                                    }
                                }
                            } finally {
                                res.unload();
                                res = null;
                            }
                        }
                    }
                }
            } catch (ValidationException e) {
                result.add(new Status(
                        IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        String.format(Messages.BOM2WSDLTransformer_validationFailed_error_message,
                                source.getFullPath().toString()), e));
            }
        }
        return eObj;
    }

    /**
     * Run an incremental transformation to produce the WSDL from the selected
     * class.
     * 
     * @param umlClass
     *            class to create WSDL from
     * @param wsdlUri
     *            the uri of the wsdl file to create
     * @param result
     *            return any errors/warnings reported during the transformation,
     *            can be <code>null</code>
     * @return
     */
    public Object incrementalTransform(final Class umlClass, URI wsdlUri,
            List<IStatus> result) {
        EObject eObj = null;
        if (result == null) {
            result = new ArrayList<IStatus>();
        }
        IFile source = WorkspaceSynchronizer.getFile(umlClass.eResource());
        try {
            if (source != null
                    && isDestinationAndNoErrors(source,
                            BomGenPreferenceConstants.WSDL_MARKER_TYPE,
                            BaseBOMXtendTransformer.WSDL_DESTINATION,
                            result)) {
                if (umlClass != null) {
                    ExportTransformationData data =
                            new ExportTransformationData();
                    data.setTopLevelSourceFile(source);
                    String uri = BomUIUtil.getURI(umlClass);
                    eObj =
                            (EObject) runIncrementalWorkflow(data,
                                    umlClass,
                                    uri,
                                    incrementalFacade,
                                    result);

                    // Continue if no errors
                    if (getErrors(result).isEmpty()) {
                        result.addAll(validateWSDL(eObj, wsdlUri));
                        // Continue if no errors
                        if (getErrors(result).isEmpty()) {
                            Resource res =
                                    new OawXMLResource(wsdlUri,
                                            new XSDMetaModel());
                            try {
                                if (res.getContents() != null) {
                                    res.getContents().add(eObj);
                                    try {
                                        String uriPath = wsdlUri.toFileString();
                                        if (uriPath == null) {
                                            uriPath = wsdlUri.toString();
                                        }
                                        File file = new File(uriPath);
                                        if (file.exists()) {
                                            file.delete();
                                        }
                                        res.save(new HashMap<Object, Object>());
                                    } catch (IOException e) {
                                        result.add(new Status(
                                                IStatus.ERROR,
                                                Activator.PLUGIN_ID,
                                                String.format(Messages.BOM2WSDLTransformer_problemGeneringWSDL_error_message,
                                                        source.getFullPath()
                                                                .toString()), e));
                                    }

                                }
                            } finally {
                                res.unload();
                                res = null;
                            }
                        }
                    }
                }
            }
        } catch (ValidationException e) {
            result.add(new Status(
                    IStatus.ERROR,
                    Activator.PLUGIN_ID,
                    String.format(Messages.BOM2WSDLTransformer_validationFailed_error_message,
                            source.getFullPath().toString()), e));
        }
        return eObj;
    }

    /**
     * @param model
     * @return
     */
    private Object runIncrementalPreserveWorkflow(
            ExportTransformationData data, Class umlClass, String url,
            XtendFacade f, List<IStatus> result) {
        if (umlClass != null) {
            try {
                return f.call(INCREMENTAL_PRESERVE_TRANSFORM_EXPRESSION,
                        data,
                        umlClass,
                        url);
            } catch (Exception e) {
                if (result != null) {
                    result.add(new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            Messages.BOM2WSDLTransformer_errorInTransformation_error_message,
                            e));
                }
            }
        }
        return null;
    }

    /**
     * @param model
     * @return
     */
    private Object runIncrementalWorkflow(ExportTransformationData data,
            Class umlClass, String url, XtendFacade f, List<IStatus> result) {
        if (umlClass != null) {
            try {
                return f.call(INCREMENTAL_TRANSFORM_EXPRESSION,
                        data,
                        umlClass,
                        url);
            } catch (Exception e) {
                if (result != null) {
                    result.add(new Status(
                            IStatus.ERROR,
                            Activator.PLUGIN_ID,
                            Messages.BOM2WSDLTransformer_errorInTransformation_error_message,
                            e));
                }
            }
        }
        return null;
    }

    /**
     * Run the WST validation on the given WSDL model.
     * 
     * @param eObj
     * @param wsdlUri
     * @return validation result
     */
    private List<IStatus> validateWSDL(EObject eObj, URI wsdlUri) {
        IPreferenceStore store =
                com.tibco.xpd.bom.gen.ui.Activator.getDefault()
                        .getPreferenceStore();
        boolean isWSDLValidationEnabled =
                store.getBoolean(BomGenPreferenceConstants.P_ENABLE_WSDL_VALIDATION);
        List<IStatus> result = new ArrayList<IStatus>();
        if (isWSDLValidationEnabled) {

            Resource res = new OawXMLResource(URI.createURI("/"), //$NON-NLS-1$
                    getXsdMetaModel());
            if (res.getContents() != null) {
                res.getContents().add(eObj);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ByteArrayInputStream is = null;
                try {
                    res.save(os, Collections.EMPTY_MAP);
                    is = new ByteArrayInputStream(os.toByteArray());
                    WSDLParser wsdlParser = new WSDLParser();
                    wsdlParser.parse(is);
                    Collection<?> diagnostics = wsdlParser.getDiagnostics();
                    if (diagnostics != null) {
                        for (Object diagnostic : diagnostics) {
                            if (diagnostic instanceof WSDLDiagnostic) {
                                WSDLDiagnostic d = (WSDLDiagnostic) diagnostic;
                                int errCode = IStatus.ERROR;
                                if (WSDLDiagnosticSeverity.WARNING_LITERAL
                                        .equals(d.getSeverity())) {
                                    errCode = IStatus.WARNING;
                                } else if (WSDLDiagnosticSeverity.INFORMATION_LITERAL
                                        .equals(d.getSeverity())) {
                                    errCode = IStatus.INFO;
                                }

                                result.add(new Status(errCode,
                                        Activator.PLUGIN_ID, d.getMessage()));
                            }
                        }
                    }
                    // If no errors then continue
                    if (getErrors(result).isEmpty()) {
                        IFile tempFile =
                                WorkspaceSynchronizer.getFile(new ResourceImpl(
                                        wsdlUri));
                        if (tempFile != null) {
                            result.addAll(WSDLTransformUtil
                                    .validateWSDL(tempFile));
                        }
                        // do one final check
                        /*
                         * com.tibco.xpd.bom.wsdltransform.imports.template.
                         * TransformHelper .getSchema(wsdlUri, issues,
                         * XtendTransformer.XSD_EXPORT_METAMODEL_ID);
                         */
                    }
                } catch (Exception e) {
                    result.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e
                            .getLocalizedMessage(), e));
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        os.close();
                    } catch (IOException e) {
                        // Do nothing
                    }
                    res.unload();
                    res = null;
                }
            }
        }
        return result;
    }
}
