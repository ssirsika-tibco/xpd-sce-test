/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.xsdtransform.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xsd.XSDDiagnostic;
import org.eclipse.xsd.XSDDiagnosticSeverity;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;
import org.openarchitectureware.type.emf.EmfMetaModel;
import org.openarchitectureware.xtend.XtendFacade;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPreferenceConstants;
import com.tibco.xpd.bom.resources.ui.bomnotation.BomNotationPackage;
import com.tibco.xpd.bom.xsdtransform.Activator;

/**
 * Abstract class for the export OAW transformer to generate a WSDL/XSD model
 * from a given BOM.
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractXtendExportTransformer extends
        BaseBOMXtendTransformer {

    private final static EmfMetaModel ecoreMetaModel =
            new EmfMetaModel(
                    (EcorePackage) EPackage.Registry.INSTANCE
                            .get(EcorePackage.eNS_URI));

    private final static EmfMetaModel bomNotationMetaModel = new EmfMetaModel(
            BomNotationPackage.eINSTANCE);

    private final static EmfMetaModel notationMetaModel = new EmfMetaModel(
            (EPackage) EPackage.Registry.INSTANCE.get(NotationPackage.eNS_URI));

    // private final static ProfileMetaModel conceptProfileMetaModel =
    // new ProfileMetaModel(UMLExtensionHelper.getConceptProfile());

    /**
     * Get the schema from the given File.
     * 
     * @param file
     *            schema file.
     * 
     * @return schema, or <code>null</code> if schema not found.
     */
    protected XSDSchema getSchema(ByteArrayOutputStream os) throws IOException,
            CoreException {
        LOG("AbstractXtendExportTransformer: getSchema(ByteArrayOutputStream)..."); //$NON-NLS-1$
        long start = System.currentTimeMillis();
        XSDSchema schema = null;
        if (os != null) {
            ByteArrayInputStream is =
                    new ByteArrayInputStream(os.toByteArray());

            Resource res = null;
            // If resource is not created then do that and add to cache
            if (res == null) {
                res = new XSDResourceImpl(URI.createURI("/")); //$NON-NLS-1$

                if (res != null) {
                    res.load(is, new HashMap<Object, Object>());
                    // Check for any errors - if found report it
                    if (res.getErrors() != null && !res.getErrors().isEmpty()) {
                        List<IStatus> status = new ArrayList<IStatus>();
                        for (Diagnostic diagnostic : res.getErrors()) {
                            status.add(new Status(
                                    IStatus.ERROR,
                                    Activator.PLUGIN_ID,
                                    String.format(Messages.XtendTransformer_loadSchema_diagnosticError_shortdesc,
                                            diagnostic.getMessage(),
                                            diagnostic.getLine(),
                                            diagnostic.getColumn())));
                        }
                        throw new CoreException(
                                new MultiStatus(
                                        Activator.PLUGIN_ID,
                                        IStatus.ERROR,
                                        status.toArray(new IStatus[status
                                                .size()]),
                                        String.format(Messages.XtendTransformer_loadSchema_error_shortdesc,
                                                os.toString()), null));
                    }
                }
            }
            if (res != null) {
                EList<EObject> contents = res.getContents();

                if (contents != null && !contents.isEmpty()
                        && contents.get(0) instanceof XSDSchema) {
                    schema = (XSDSchema) contents.get(0);
                }
            }
        }
        LOG("AbstractXtendExportTransformer: getSchema(ByteArrayOutputStream) complete: " //$NON-NLS-1$
                + (System.currentTimeMillis() - start));
        return schema;
    }

    /**
     * Validate the given schema. This will use {@link XSDSchema#validate()} to
     * validate.
     * 
     * @param schema
     * @param usePrefValidation
     *            - If true then only validates XML Schema if ticked in
     *            preferences else it always defaults to validating it each
     *            method call
     * 
     * @return Any errors, warnings and infos will be reported. Empty list if no
     *         problems found.
     */
    protected List<IStatus> validateSchema(XSDSchema schema,
            boolean usePrefValidation) {
        LOG("AbstractXtendImportTransformer: validateSchema..."); //$NON-NLS-1$
        long start = System.currentTimeMillis();
        List<IStatus> result = new ArrayList<IStatus>();

        /*
         * SID XPD-1605: Changed so that usePrefValidaiton gets passed all the
         * way down to here rather than the BIZARRE way things used to 'work'
         * (and I use that term in the loosest possible sense) which was to set
         * the preference store value from the transform methods above!
         */
        boolean isXSDValidationEnabled = true;
        if (usePrefValidation) {
            IPreferenceStore store =
                    com.tibco.xpd.bom.gen.ui.Activator.getDefault()
                            .getPreferenceStore();

            isXSDValidationEnabled =
                    store.getBoolean(BomGenPreferenceConstants.P_ENABLE_XSD_VALIDATION);
        }

        if (schema != null && isXSDValidationEnabled) {
            schema.validate();
            EList<XSDDiagnostic> diagnostics = schema.getAllDiagnostics();
            if (diagnostics.size() > 0) {
                Iterator<XSDDiagnostic> iterator = diagnostics.iterator();
                while (iterator.hasNext()) {
                    XSDDiagnostic xsdDiagnostic = iterator.next();
                    if (!isIgnoreError(xsdDiagnostic)
                            && xsdDiagnostic
                                    .getSeverity()
                                    .equals(XSDDiagnosticSeverity.ERROR_LITERAL)
                            || xsdDiagnostic
                                    .getSeverity()
                                    .equals(XSDDiagnosticSeverity.FATAL_LITERAL)) {
                        result.add(new Status(IStatus.ERROR,
                                Activator.PLUGIN_ID, xsdDiagnostic.getMessage()));
                    } else if (xsdDiagnostic.getSeverity()
                            .equals(XSDDiagnosticSeverity.WARNING_LITERAL)) {
                        result.add(new Status(IStatus.WARNING,
                                Activator.PLUGIN_ID, xsdDiagnostic.getMessage()));
                    } else if (xsdDiagnostic.getSeverity()
                            .equals(XSDDiagnosticSeverity.INFORMATION_LITERAL)) {
                        result.add(new Status(IStatus.INFO,
                                Activator.PLUGIN_ID, xsdDiagnostic.getMessage()));
                    }
                }
            }
        }
        LOG("AbstractXtendImportTransformer: validateSchema complete: " //$NON-NLS-1$
                + (System.currentTimeMillis() - start));
        return result;
    }

    /**
     * @param xsdDiagnostic
     * @return
     */
    private boolean isIgnoreError(XSDDiagnostic xsdDiagnostic) {
        if ((xsdDiagnostic.getColumn() == 1 && xsdDiagnostic.getLine() == 1 && xsdDiagnostic
                .getKey().equals("_UI_UnresolvedTypeDefinition_message")) || xsdDiagnostic.getKey().equals("cos-applicable-facets")) { //$NON-NLS-1$ //$NON-NLS-2$
            return true;
        }
        return false;
    }

    /**
     * Configure the meta models and set up the xml reader and the xtend
     * component adding them to the workflow
     * 
     * @param f
     * 
     **/
    protected static void configureWorkflow(final XtendFacade f) {
        // Meta models registration.
        f.registerMetaModel(ecoreMetaModel);
        f.registerMetaModel(bomNotationMetaModel);
        f.registerMetaModel(notationMetaModel);
        // f.registerMetaModel(conceptProfileMetaModel);
        f.registerMetaModel(getUML2MetaModel());
        f.registerMetaModel(getXsdMetaModel());
        f.registerMetaModel(getProfileMetaModel());
        f.registerMetaModel(getJavaMetaModel());
    }
}
