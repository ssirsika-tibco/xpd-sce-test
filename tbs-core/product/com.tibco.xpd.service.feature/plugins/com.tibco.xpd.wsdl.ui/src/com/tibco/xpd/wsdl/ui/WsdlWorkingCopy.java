/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.wsdl.Types;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.wst.wsdl.validation.internal.IValidationMessage;
import org.eclipse.wst.wsdl.validation.internal.IValidationReport;
import org.eclipse.wst.wsdl.validation.internal.WSDLValidationConfiguration;
import org.eclipse.wst.wsdl.validation.internal.eclipse.WSDLValidator;
import org.eclipse.wst.wsdl.validation.internal.resolver.IExtensibleURIResolver;
import org.eclipse.wst.wsdl.validation.internal.resolver.IURIResolutionResult;
import org.eclipse.xsd.XSDSchema;

import com.tibco.xpd.resources.util.UnprotectedWriteOperation;
import com.tibco.xpd.resources.wc.InvalidFileException;
import com.tibco.xpd.wsdl.ui.internal.AbstractExtModelWorkingCopy;
import com.tibco.xpd.wsdl.ui.internal.CheckSumUtil;
import com.tibco.xpd.wsdl.ui.internal.preferences.WSDLSchemaValidationPreferencePage;

/**
 * WSDL model working copy implementation.
 * <p>
 * Since 3.5 this provides a {@link #getCheckSum() checksum}.
 * </p>
 * 
 * @author nwilson
 */
@SuppressWarnings("restriction")
public class WsdlWorkingCopy extends AbstractExtModelWorkingCopy {

    private IStatus validationResult;

    private long lastValidationModificationStamp = 0;

    private String checkSum;

    private long lastCheckSumModificationStamp;

    private static WSDLValidator wsdlValidator = null;

    {
        /*
         * XPD-1686: Add our URI resolver once to the WSDLValidator. It is done
         * this way to try and get our resolver to be added at the end of all
         * the other WST resolvers. This will affect all WSDL validations run
         * through the WST framework (eg, WSDL2BOM transformation).
         */
        if (wsdlValidator == null) {
            wsdlValidator = WSDLValidator.getInstance();
            wsdlValidator.addURIResolver(new Resolver());
        }
    }

    @Override
    protected EObject doLoadModel() throws InvalidFileException {
        /*
         * When a new WSDL file is created using the Eclipse wizard it will
         * create an empty file before applying the content. This will cause the
         * working copy to be loaded and will attempt to load the model causing
         * an error. If there is no file content then the model should not be
         * loaded.
         */
        IResource resource = getFirstResource();
        // System.out.println("==> #### WSDLWORKINGCOPY do LoadModel: "
        // + resource.getFullPath());
        if (resource != null) {
            IPath location = resource.getLocation();
            if (location != null) {
                File file = new File(location.toOSString());
                if (file != null && file.exists()) {
                    if (file.length() == 0) {
                        return null;
                    }
                }
            }
        }

        EObject ret = super.doLoadModel();

        // System.out.println("<== #### WSDLWORKINGCOPY do LoadModel: "
        // + resource.getFullPath());

        return ret;
    }

    /**
     * @see com.tibco.xpd.wsdl.ui.internal.AbstractExtModelWorkingCopy#resetSchema()
     * 
     */
    @Override
    protected void resetSchema() {
        EObject eo = getRootElement();
        if (eo instanceof Definition) {
            final Set<XSDSchema> schemasToReset = new HashSet<XSDSchema>();

            Types types = ((Definition) eo).getTypes();
            if (types != null) {
                for (Object next : types.getExtensibilityElements()) {
                    if (next instanceof XSDSchemaExtensibilityElement) {
                        XSDSchema schema =
                                ((XSDSchemaExtensibilityElement) next)
                                        .getSchema();
                        if (schema != null) {
                            schemasToReset.add(schema);
                        }
                    }
                }
            }

            if (!schemasToReset.isEmpty()) {
                UnprotectedWriteOperation op =
                        new UnprotectedWriteOperation(
                                (TransactionalEditingDomain) getEditingDomain()) {

                            @Override
                            protected Object doExecute() {
                                for (XSDSchema schema : schemasToReset) {
                                    /*
                                     * Reset all references. This will cause all
                                     * externally referenced elements to
                                     * re-resolve the references.
                                     */
                                    schema.reset();
                                }
                                return null;
                            }
                        };
                op.execute();
                fireWCModelChanged();
            }
        }
    }

    /**
     * @see com.tibco.xpd.resources.wc.AbstractWorkingCopy#uninstallAdapters(org.eclipse.emf.ecore.resource.Resource)
     * 
     * @param res
     */
    @Override
    protected void uninstallAdapters(Resource res) {
        // Need to clear all adapters from the resource, otherwise the WSDL
        // resource will not be removed from memory.
        res.eAdapters().clear();
    }

    /**
     * Check if this WSDL is generated from a Process.
     * 
     * @param res
     * @return
     */
    public boolean isWsdlStudioGenerated() {
        Definition definition = (Definition) getRootElement();

        if (definition != null && definition.getElement() != null) {
            String tibexAttrib =
                    definition.getElement()
                            .getAttribute(WsdlUIPlugin.TIBEX_XPDL_PLACEHOLDER);
            if (tibexAttrib != null && tibexAttrib.length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param resource
     *            The WSDL resource.
     */
    public WsdlWorkingCopy(IResource resource) {
        super(Collections.singletonList(resource), WSDLPackage.eINSTANCE);
    }

    /**
     * Get a checksum for this resource.
     * 
     * @return checksum if it can be calculated from the resource,
     *         <code>null</code> otherwise.
     * @throws IOException
     * @since 3.5
     */
    public String getCheckSum() throws IOException {

        IResource resource = getFirstResource();
        if (resource instanceof IFile) {
            long modStamp = resource.getModificationStamp();
            if (checkSum == null || lastCheckSumModificationStamp != modStamp) {

                IPath location = resource.getLocation();
                if (location != null) {
                    File file = location.toFile();
                    if (file != null && file.exists()) {
                        checkSum = CheckSumUtil.getCheckSum(file);
                        lastCheckSumModificationStamp = modStamp;
                    }
                }
            }
        }

        return checkSum;
    }

    @Override
    protected void cleanup() {
        lastValidationModificationStamp = 0;
        validationResult = null;
        lastCheckSumModificationStamp = 0;
        checkSum = null;

        super.cleanup();
    }

    /**
     * Validate the WSDL. If validation has already been run on this WSDL then
     * it will just return the result, otherwise if the validation is not
     * current (file has changed since last validation), or validation hasn't
     * run at all then it will be run immediately.
     * <p>
     * This validation is set to ignore WSI compliance issues.
     * </p>
     * <p>
     * Use {@link #forceValidate()} to force the validation, ignoring the cached
     * result. Note that the cache only considers if this WSDL has changed, if
     * an imported WSDL or XSD has changed then the cache will not be cleared
     * and so you may not get an accurate validation result subsequently.
     * </p>
     * 
     * <p>
     * <strong>This is EXPERIMENTAL and may change. It is STRONGLY recommended
     * that this is NOT called from the UI thread as it may cause a
     * deadlock.</strong>
     * </p>
     * 
     * @see WsdlValidationStatus
     * @see #forceValidate()
     * 
     * @return If validation reports errors or warnings then a
     *         {@link MultiStatus} will be returned containing
     *         {@link WsdlValidationStatus} objects with details of each
     *         error/warning as reported by the WST validator, otherwise OK
     *         {@link Status} will be returned.
     * 
     * @since 3.5
     */
    public synchronized IStatus validate() {
        IResource res = getFirstResource();
        if (res != null) {
            final long modStamp = res.getModificationStamp();
            if (validationResult == null
                    || lastValidationModificationStamp != modStamp) {
                validationResult = forceValidate();
                lastValidationModificationStamp = modStamp;
            }
        }
        return validationResult;
    }

    /**
     * Run the WSDL validation. This method will always run the validation
     * without any caching (unlike {@link #validate()}).
     * <p>
     * <strong>This is EXPERIMENTAL and may change. It is STRONGLY recommended
     * that this is NOT called from the UI thread as it may cause a
     * deadlock.</strong>
     * </p>
     * 
     * @see #validate()
     * @see WsdlValidationStatus
     * 
     * @return If validation reports errors or warnings then a
     *         {@link MultiStatus} will be returned containing
     *         {@link WsdlValidationStatus} objects with details of each
     *         error/warning as reported by the WST validator, otherwise OK
     *         {@link Status} will be returned.
     * 
     * @since 3.5.10
     */
    public synchronized IStatus forceValidate() {
        IResource res = getFirstResource();
        IStatus status = Status.OK_STATUS;
        if (res != null) {
            try {
                URI uri = res.getLocationURI();
                if (uri != null) {
                    URL url = uri.toURL();

                    // this here to avoid calls to www.ws-i.org
                    WSDLValidationConfiguration conf =
                            new WSDLValidationConfiguration();
                    /*
                     * JA: I don't think it really has any impact. It looks like
                     * the property is lost somewhere on the way when it's
                     * copied. See/debug:
                     * org.eclipse.wst.wsi.internal.validate.wsdl.WSDLValidator
                     * .validate(Document, IValidationInfo) for more details.
                     */
                    conf.setProperty("http://ws-i.org/profiles/BasicWithAttachments/ComplianceLevel", //$NON-NLS-1$
                            "IGNORE"); //$NON-NLS-1$

                    IValidationReport retValue =
                            wsdlValidator.validate(url.toString(), null, conf);

                    if (retValue.getValidationMessages() != null
                            && retValue.getValidationMessages().length > 0) {
                        // There are errors/problems
                        List<IStatus> st = new ArrayList<IStatus>();
                        for (IValidationMessage message : retValue
                                .getValidationMessages()) {
                            st.add(createStatus(message));
                        }

                        status =
                                new MultiStatus(
                                        WsdlUIPlugin.PLUGIN_ID,
                                        0,
                                        st.toArray(new IStatus[st.size()]),
                                        "WST validation has reported problems with the WSDL.",
                                        null);
                    }
                }

            } catch (Exception e) {
                WsdlUIPlugin.getDefault().getLogger()
                        .error(e, "Exception thrown by WSDLValidator"); //$NON-NLS-1$
                status =
                        new Status(IStatus.ERROR, WsdlUIPlugin.PLUGIN_ID,
                                "Exception thrown by WSDLValidator", e); //$NON-NLS-1$
            }
        }
        return status;
    }

    /**
     * Create an {@link WsdlValidationStatus} object for the given message.
     * 
     * @param message
     * @return
     */
    private WsdlValidationStatus createStatus(IValidationMessage message) {
        return new WsdlValidationStatus(
                message.getSeverity() == IValidationMessage.SEV_ERROR ? IStatus.ERROR
                        : IStatus.WARNING, WsdlUIPlugin.PLUGIN_ID,
                message.getMessage(), message.getURI(), message.getColumn(),
                message.getLine(), message.getNestedMessages());
    }

    /**
     * URI resolver for the WSDL validator that will set the physical location
     * of schemas with namespaces that should be ignored to blank so that they
     * don't get validated / accessed on the net.
     */
    private static class Resolver implements IExtensibleURIResolver,
            IPropertyChangeListener {

        private final List<String> namespaces;

        public Resolver() {
            namespaces = Collections.synchronizedList(new ArrayList<String>());
            WsdlUIPlugin.getDefault().getPreferenceStore()
                    .addPropertyChangeListener(this);

            loadNamespaces(namespaces);
        }

        @Override
        public void resolve(String baseLocation, String publicId,
                String systemId, IURIResolutionResult result) {
            if (result != null) {
                String location = result.getPhysicalLocation();
                if (location != null && location.length() > 0) {
                    for (String namespace : namespaces) {
                        if (location.startsWith(namespace)) {
                            // Ignore this namespace
                            result.setPhysicalLocation(""); //$NON-NLS-1$
                            location = ""; //$NON-NLS-1$
                            break;
                        }
                    }
                }
            }
        }

        @Override
        public void propertyChange(PropertyChangeEvent event) {
            if (WsdlUIPlugin.WSDL_VALIDATION_NAMESPACE_IGNORE_PREF.equals(event
                    .getProperty())) {
                loadNamespaces(namespaces);
            }
        }

        /**
         * Load the namespaces from the preferences.
         * 
         * @param namespaces
         */
        private void loadNamespaces(List<String> namespaces) {
            synchronized (namespaces) {
                namespaces.clear();

                List<String> values =
                        WSDLSchemaValidationPreferencePage.getNamespaces();
                if (values != null && !values.isEmpty()) {
                    namespaces.addAll(values);
                }
            }
        }
    }
}
