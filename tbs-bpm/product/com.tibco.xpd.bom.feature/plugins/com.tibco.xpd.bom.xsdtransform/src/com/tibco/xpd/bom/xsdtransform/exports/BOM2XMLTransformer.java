/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.xsdtransform.exports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Class;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.OawXMLResource;
import org.openarchitectureware.xsd.XSDMetaModel;
import org.openarchitectureware.xsd.builder.OawXSDResource;
import org.openarchitectureware.xsd.builder.OawXSDResourceSet;
import org.openarchitectureware.xtend.XtendFacade;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.xsdtransform.Activator;
import com.tibco.xpd.bom.xsdtransform.exports.template.ExportTransformationData;
import com.tibco.xpd.bom.xsdtransform.internal.AbstractXtendExportTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.BaseBOMXtendTransformer;
import com.tibco.xpd.bom.xsdtransform.internal.Messages;
import com.tibco.xpd.validation.provider.ValidationException;

/**
 * BOM 2 XSD transformer specifically for BOM 2 XML used by transform script.
 * <p>
 * TODO This should be revisited for performance at some later date. This was
 * moved out of {@link BOM2XSDTransformer} as that class has chanegd somehwat to
 * allow caching of previously transformer schemas and this transformation
 * seemed to be living in the same class but not that related.
 * 
 * @author aallway
 * @since 6 Apr 2011
 */
public class BOM2XMLTransformer extends AbstractXtendExportTransformer {

    private static final String INCREMENTAL_PRESERVE_EXTENSION_FILE =
            "com::tibco::xpd::bom::xsdtransform::exports::template::IncrementalPreserveBom2Xsd"; //$NON-NLS-1$

    private static final String INCREMENTAL_PRESERVE_TRANSFORM_EXPRESSION =
            "incrementalPreserveTransform"; //$NON-NLS-1$

    private static final String INCREMENTAL_EXTENSION_FILE =
            "com::tibco::xpd::bom::xsdtransform::exports::template::IncrementalBom2Xsd"; //$NON-NLS-1$

    private static final String INCREMENTAL_TRANSFORM_EXPRESSION =
            "incrementalTransform"; //$NON-NLS-1$

    /**
     * Transforms any BOM Class to an XML Schema and only parses the
     * dependencies of that particular class selected. If umlClass is null then
     * method returns null.
     * 
     * @param umlClass
     *            - The actual BOM Class to be transformed into XML Schema ...
     *            obviously only its dependencies are carried across to Schema
     *            and not whole BOM file in this method.
     * @param xsdFilePath
     *            - This is the full XSD file path where you wish the
     *            transformed XML Schema to reside. It can be set to null if you
     *            don't want to physically create an XSD file in a particular
     *            location.
     * @param preserveSchemas
     *            - If this is set to true then this produces just 1 XML Schema
     *            using umlClass name with .xsd suffix. Otherwise the
     *            transformation will generate an XML Schema for each BOM
     *            package that holds any dependencies of the umlClass passed in.
     * @param result
     *            - Holds any problems that occurred on transformation - user
     *            can later inspect this
     * @param useEcoreNames
     *            - determines whether the export will generate ecore names for
     *            each construct
     * @param usePrefValidation
     *            - If true then only validates XML Schema if ticked in
     *            preferences else it always defaults to validating it each
     *            method call
     * 
     * @return - Returns the XSD Schema for inspection
     */
    public Object incrementalTransformBOMToXSD(final Class umlClass,
            final String xsdFilePath, boolean preserveSchemas,
            List<IStatus> result, boolean usePrefValidation) {
        Object obj = null;

        if (preserveSchemas) {
            XtendFacade facade =
                    XtendFacade.create(INCREMENTAL_PRESERVE_EXTENSION_FILE);
            configureWorkflow(facade);

            obj =
                    incrementalPreserveTransform(umlClass,
                            xsdFilePath,
                            result,
                            facade,
                            usePrefValidation);
        } else {
            XtendFacade facade = XtendFacade.create(INCREMENTAL_EXTENSION_FILE);
            configureWorkflow(facade);

            obj =
                    incrementalTransform(umlClass,
                            xsdFilePath,
                            result,
                            facade,
                            usePrefValidation);
        }
        return obj;
    }

    /**
     * @param umlClass
     * @param xsdFilePath
     * @param result
     *            report any errors, warnings or info from the transformation
     *            process
     * @param useEcoreNames
     * @param xtendExportTransformer
     * @param incrementalFacade
     * @param usePrefValidation
     *            - If true then only validates XML Schema if ticked in
     *            preferences else it always defaults to validating it each
     *            method call
     * @return
     */
    private Object incrementalTransform(final Class umlClass,
            final String xsdFilePath, List<IStatus> result,
            XtendFacade incrementalFacade, boolean usePrefValidation) {
        URI destUri = xsdFilePath != null ? URI.createURI(xsdFilePath) : null;
        EObject eObj = null;
        ExportTransformationData data = new ExportTransformationData();
        if (result == null) {
            result = new ArrayList<IStatus>();
        }

        if (umlClass != null) {
            IFile source = WorkspaceSynchronizer.getFile(umlClass.eResource());
            try {
                if (source != null
                        && isDestinationAndNoErrors(source,
                                BomGenPreferenceConstants.XSD_MARKER_TYPE,
                                BaseBOMXtendTransformer.XSD_DESTINATION,
                                result)) {

                    data.setTopLevelSourceFile(source);

                    // Facade
                    String uri = BomUIUtil.getURI(umlClass);
                    eObj =
                            (EObject) runIncrementalWorkflow(data,
                                    umlClass,
                                    uri,
                                    incrementalFacade,
                                    result);

                    if (destUri != null) {
                        // Continue if no errors
                        if (getErrors(result).isEmpty()) {
                            Resource res =
                                    new OawXMLResource(destUri,
                                            new XSDMetaModel());
                            if (res.getContents() != null) {
                                res.getContents().add(eObj);
                                res.save(Collections.EMPTY_MAP);

                                // Reload into XSD Resource so it can be
                                // validated
                                OawXSDResource resource =
                                        new OawXSDResource(destUri);
                                resource.load(Collections.EMPTY_MAP);

                                // Validate the schema
                                XSDSchema schema = resource.getSchema();
                                if (schema != null) {
                                    result.addAll(validateSchema(schema,
                                            usePrefValidation));
                                } else {
                                    result.add(new Status(
                                            IStatus.ERROR,
                                            Activator.PLUGIN_ID,
                                            String.format(Messages.BOM2XSDTransformer_unableToLoadSchema_error_message,
                                                    destUri)));
                                }
                                // If error then delete file
                                if (schema == null
                                        || !getErrors(result).isEmpty()) {
                                    res.delete(Collections.EMPTY_MAP);
                                }
                            }
                        }
                    }
                }
            } catch (ValidationException e) {
                result.add(new Status(
                        IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        String.format(Messages.BOM2XSDTransformer_problemValidating_error_message,
                                source.getFullPath().toString()), e));
            } catch (IOException e) {
                result.add(new Status(
                        IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        String.format(Messages.BOM2XSDTransformer_saveFailed_error_message,
                                destUri), e));
            }
        }
        return eObj;
    }

    /**
     * @param umlClass
     * @param xsdFilePath
     * @param result
     * @param useEcoreNames
     * @param xtendExportTransformer
     * @param incrementalPreserveFacade
     * @param usePrefValidation
     *            - If true then only validates XML Schema if ticked in
     *            preferences else it always defaults to validating it each
     *            method call
     * @return
     */
    private Object incrementalPreserveTransform(final Class umlClass,
            final String xsdFilePath, List<IStatus> result,
            XtendFacade incrementalPreserveFacade, boolean usePrefValidation) {
        URI destUri = xsdFilePath != null ? URI.createURI(xsdFilePath) : null;
        Object ret = null;
        if (result == null) {
            result = new ArrayList<IStatus>();
        }
        ExportTransformationData data = new ExportTransformationData();
        if (umlClass != null) {
            IFile source = WorkspaceSynchronizer.getFile(umlClass.eResource());
            try {
                if (source != null
                        && isDestinationAndNoErrors(source,
                                BomGenPreferenceConstants.XSD_MARKER_TYPE,
                                BaseBOMXtendTransformer.XSD_DESTINATION,
                                result)) {

                    data.setTopLevelSourceFile(source);

                    // Facade
                    String uri = BomUIUtil.getURI(umlClass);
                    ret =
                            runIncrementalPreserveWorkflow(data,
                                    umlClass,
                                    uri,
                                    incrementalPreserveFacade,
                                    result);

                    if (destUri != null && getErrors(result).isEmpty()
                            && ret instanceof List<?>) {
                        URI tempFileURI = destUri.trimSegments(1);
                        List<?> objects = (List<?>) ret;
                        List<URI> loadedUris = new ArrayList<URI>();
                        for (Object obj : objects) {
                            if (obj instanceof EObject) {
                                EObject eo = (EObject) obj;
                                if (!eo.eContents().isEmpty()) {
                                    String namespace =
                                            getTargetNamespace(eo.eContents()
                                                    .get(0));
                                    if (namespace != null) {
                                        try {
                                            String filename =
                                                    data.getFileName(namespace);

                                            if (filename != null) {
                                                Resource res =
                                                        new OawXMLResource(
                                                                tempFileURI
                                                                        .appendSegment(filename),
                                                                new XSDMetaModel());
                                                if (res.getContents() != null) {
                                                    res.getContents().add(eo);
                                                    res.save(Collections.EMPTY_MAP);

                                                    loadedUris
                                                            .add(res.getURI());
                                                }
                                            }
                                        } catch (Exception e) {
                                            result.add(new Status(
                                                    IStatus.ERROR,
                                                    Activator.PLUGIN_ID,
                                                    Messages.BOM2XSDTransformer_problemGeneringXSD_error_message,
                                                    e));
                                        }
                                    }
                                }
                            }
                        }

                        // Validate the schemas by loading the XSD resources
                        if (!loadedUris.isEmpty()) {
                            OawXSDResourceSet rSet = new OawXSDResourceSet();
                            for (URI loadedUri : loadedUris) {
                                rSet.getResource(loadedUri, true);
                            }

                            for (Resource res : rSet.getResources()) {
                                XSDSchema schema =
                                        ((OawXSDResource) res).getSchema();
                                if (schema != null) {
                                    result.addAll(validateSchema(schema,
                                            usePrefValidation));
                                } else {
                                    result.add(new Status(
                                            IStatus.ERROR,
                                            Activator.PLUGIN_ID,
                                            String.format(Messages.BOM2XSDTransformer_unableToLoadSchema_error_message,
                                                    res.getURI())));
                                }
                                if (!getErrors(result).isEmpty()) {
                                    // Error so delete the resource
                                    try {
                                        res.delete(Collections.EMPTY_MAP);
                                    } catch (IOException e) {
                                        Activator.getDefault().getLogger()
                                                .error(e, "Problem deleting: " //$NON-NLS-1$
                                                        + res.getURI());
                                    }
                                }
                            }
                            rSet.clear();
                        }

                    }
                }
            } catch (ValidationException e) {
                result.add(new Status(
                        IStatus.ERROR,
                        Activator.PLUGIN_ID,
                        String.format(Messages.BOM2XSDTransformer_problemValidating_error_message,
                                source.getFullPath().toString()), e));
            }
        }
        return ret;
    }

    /**
     * @param data
     * @param umlClass
     * @param url
     * @param f
     * @param result
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
                            Messages.BOM2XSDTransformer_errorInBOMtoXSDTransformation_error_message,
                            e));
                }
            }
        }
        return null;
    }

    /**
     * @param data
     * @param umlClass
     * @param url
     * @param f
     * @param result
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
                            Messages.BOM2XSDTransformer_errorInBOMtoXSDTransformation_error_message,
                            e));
                }
            }
        }
        return null;
    }

    /**
     * Get the target namespace of the given XSD element.
     * 
     * @param eo
     * @return
     */
    private String getTargetNamespace(EObject eo) {
        EClass eClass = eo.eClass();
        if (eClass != null) {
            EList<EAttribute> attributes = eClass.getEAttributes();
            for (EAttribute attr : attributes) {
                if ("targetNamespace".equals(attr.getName())) { //$NON-NLS-1$
                    Object get = eo.eGet(attr);
                    if (get instanceof String) {
                        return (String) get;
                    }
                }
            }
        }
        return null;
    }
}
