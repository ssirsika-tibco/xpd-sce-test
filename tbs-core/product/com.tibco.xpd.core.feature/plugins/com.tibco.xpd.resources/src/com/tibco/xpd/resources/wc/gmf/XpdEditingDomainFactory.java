/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.wc.gmf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.ExceptionHandler;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.ReadWriteValidatorImpl;
import org.eclipse.emf.transaction.impl.TransactionValidator;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl.ValidatorFactoryImpl;
import org.eclipse.emf.workspace.impl.WorkspaceCommandStackImpl;
import org.eclipse.gmf.runtime.diagram.core.DiagramEditingDomainFactory;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.resources.XpdConfigOption;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Factory to create the shared transaction editing domain. When the editing
 * domain is created any resources registered to be pre-added will be created in
 * the resource set. See extension point
 * <code>com.tibco.xpd.resources.resourcePreLoader</code>.
 * 
 * @author njpatel
 * 
 */
public class XpdEditingDomainFactory extends DiagramEditingDomainFactory {

    // Extension point id of the resource preloader
    private static final String RESOURCE_PRELOADER_ID = "resourcePreLoader"; //$NON-NLS-1$

    private static final String ATTR_URI = "uri"; //$NON-NLS-1$

    private static final String ITEM_ADAPTER_FACTORY_OVERRIDE_ID =
            "itemProviderAdapterOverride"; //$NON-NLS-1$

    private static final String ATTR_FACTORY = "factory"; //$NON-NLS-1$

    private TransactionalEditingDomain editingDomain;

    @Override
    public TransactionalEditingDomain createEditingDomain(
            IOperationHistory history) {

        XpdResourceSet rSet = new XpdResourceSet();
        TransactionalEditingDomain editingDomain =
                createEditingDomain(rSet, history);
        rSet.setEditingDomain(editingDomain);

        return editingDomain;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionalEditingDomain createEditingDomain(ResourceSet rset,
            IOperationHistory history) {
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            editingDomain = super.createEditingDomain(rset, history);
        } else {
            // In headless mode the standard implementation of transactional
            // editing domain is created instead of DiagramEditingDomain which
            // would require UI thread access.
            WorkspaceCommandStackImpl stack =
                    new WorkspaceCommandStackImpl(history);

            editingDomain = new TransactionalEditingDomainImpl(
                    new ComposedAdapterFactory(
                            ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
                    stack, rset);

            mapResourceSet(editingDomain);
            configure(editingDomain);
        }

        /*
         * AdapterFactoryEditingDomain.isReadOnly() method is backed by a
         * Resource->BoolenReadOnlyStatus HashMap that is assigned-on-demand
         * ONLY ONCE.
         * 
         * This is kind of understandable because Eclipse does not send any
         * notifications on read-only status changes.
         * 
         * However, that does not help our users when they are not permitted to
         * change an initially read-only file that is subsequently changed to
         * not-read-only (especially when they are using a source control system
         * that uses read-only status to control changeability files etc).
         * 
         * So we replace the resource-to-read-only hashmap in the editing domain
         * with our own that frequently re-checks the resource-read-only status
         * direct on the file sytem for local files (which is the same as
         * standard editing domain except that standard caches the result only
         * once ever.
         */
        if (editingDomain instanceof AdapterFactoryEditingDomain) {
            ((AdapterFactoryEditingDomain) editingDomain)
                    .setResourceToReadOnlyMap(
                            new CachedResourceToReadOnlyMap());
        }
        return editingDomain;
    }

    @Override
    protected void configure(TransactionalEditingDomain domain) {

        if (XpdConfigOption.LAZY_CROSS_REFERENCE_ADAPTER.isOn()) {
            final ResourceSet rset = domain.getResourceSet();
            // Create a CrossReferenceAdapter with resolve == false.
            // This ensures that cross references are resolved lazy (on demand)
            // and not by the CrossReferenceAdapter itself. Resolving them
            // eagerly is bad for BW performance.
            // Usually, super.configure() sets the Adapter.
            // However if we do it before super.() does it, it won't be set
            // again.
            if (CrossReferenceAdapter
                    .getExistingCrossReferenceAdapter(rset) == null) {
                rset.eAdapters().add(new CrossReferenceAdapter(false));
            }
        }

        super.configure(domain);

        // Override the default validator factory so that the EMF validation
        // error message dialog can be suppressed
        if (domain instanceof TransactionalEditingDomainImpl) {
            ((TransactionalEditingDomainImpl) domain)
                    .setValidatorFactory(XpdValidationFactory.INSTANCE);
        }

        // insert adapter factories defined in "itemProviderAdapterOverride"
        // extension point
        insertAdapterFactories(domain);

        ResourceSet resourceSet = domain.getResourceSet();
        URI[] uris = getPreLoadResourceUris();

        for (URI uri : uris) {
            // Load the resource
            Resource resource = resourceSet.getResource(uri, true);

            if (resource == null) {
                XpdResourcesPlugin.getDefault().getLogger().error(String.format(
                        Messages.XpdEditingDomainFactory_failedToPreAddResource_message,
                        uri));
            }
        }

        // Register exception handler to display rollback exception errors to
        // user
        WorkspaceCommandStackImpl stack =
                (WorkspaceCommandStackImpl) domain.getCommandStack();
        stack.setExceptionHandler(new ExceptionHandler() {

            @Override
            public void handleException(Exception e) {

                if (e instanceof RollbackException) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                    if (!XpdResourcesPlugin.isInHeadlessMode()) {
                        Shell shell = XpdResourcesPlugin.getStandardDisplay()
                                .getActiveShell();

                        ErrorDialog.openError(shell,
                                Messages.XpdEditingDomainFactory_commandStackException_errorDlg_title,
                                Messages.XpdEditingDomainFactory_commandStackException_errorDlg_message,
                                ((RollbackException) e).getStatus());
                    }
                }
            }
        });
    }

    private void insertAdapterFactories(TransactionalEditingDomain domain) {

        List<AdapterFactory> factories = new ArrayList<AdapterFactory>();

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                        ITEM_ADAPTER_FACTORY_OVERRIDE_ID);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] elements =
                            ext.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            Object factory = null;
                            try {
                                factory = elem.createExecutableExtension(
                                        ATTR_FACTORY);
                            } catch (CoreException e) {
                                // ignore, will be reported in next if statement
                            }
                            if (factory instanceof AdapterFactory) {
                                factories.add((AdapterFactory) factory);
                            } else {
                                // report to eclipse log
                                reportInvalidExtensionPoint(elem);
                            }
                        }
                    }
                }
            }
        }

        if (!factories.isEmpty()) {
            AdapterFactoryEditingDomain ed =
                    (AdapterFactoryEditingDomain) domain;
            ComposedAdapterFactory caf =
                    (ComposedAdapterFactory) ed.getAdapterFactory();

            for (AdapterFactory af : factories) {
                caf.insertAdapterFactory(af);
            }
        }
    }

    private void reportInvalidExtensionPoint(IConfigurationElement elem) {
        XpdResourcesPlugin.getDefault().getLogger().error(String.format(
                Messages.XpdEditingDomainFactory_invalidAdapterOverrideExtensionError_message,
                elem.getContributor().getName()));
    }

    /**
     * Get registered pre-add resource URIs.
     * 
     * @return array of URIs of resources to add, empty list if none.
     */
    protected URI[] getPreLoadResourceUris() {
        Set<URI> uris = new HashSet<URI>();

        IExtensionPoint point = Platform.getExtensionRegistry()
                .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                        RESOURCE_PRELOADER_ID);

        if (point != null) {
            IExtension[] extensions = point.getExtensions();

            if (extensions != null) {
                for (IExtension ext : extensions) {
                    IConfigurationElement[] elements =
                            ext.getConfigurationElements();

                    if (elements != null) {
                        for (IConfigurationElement elem : elements) {
                            String uriValue = elem.getAttribute(ATTR_URI);

                            if (uriValue != null) {
                                uris.add(URI.createURI(uriValue));
                            }
                        }
                    }
                }
            }
        }

        return uris.toArray(new URI[uris.size()]);
    }

    /**
     * Validator factory implementation to suppress the EMF validation as the
     * Studio validation engine will control the EMF validation result
     * reporting.
     * 
     * @author njpatel
     * 
     * @since 3.1
     * 
     */
    private static class XpdValidationFactory extends ValidatorFactoryImpl {

        public static final XpdValidationFactory INSTANCE =
                new XpdValidationFactory();

        /*
         * (non-Javadoc)
         * 
         * @seeorg.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl.
         * ValidatorFactoryImpl#createReadWriteValidator()
         */
        @Override
        public TransactionValidator createReadWriteValidator() {
            return new ReadWriteValidatorImpl() {

                @Override
                public IStatus validate(Transaction tx) {
                    return Status.OK_STATUS;
                }
            };
        }

    }

    /**
     * XPD resource set that will load resources using a read-write transaction.
     * 
     * @author njpatel
     * 
     */
    protected class XpdResourceSet extends ResourceSetImpl
            implements IEditingDomainProvider {

        private EditingDomain editingDomain;

        /**
         * Set the parent editing domain of this resource set.
         * 
         * @param editingDomain
         */
        public void setEditingDomain(EditingDomain editingDomain) {
            this.editingDomain = editingDomain;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
         */
        @Override
        public EditingDomain getEditingDomain() {
            return editingDomain;
        }

        /**
         * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#createResource(org.eclipse.emf.common.util.URI,
         *      java.lang.String)
         * 
         * @param uri
         * @param contentType
         * @return
         */
        @Override
        public Resource createResource(URI uri, String contentType) {
            Resource resource = super.createResource(uri, contentType);

            /*
             * SCF-63: Unregister the CrossReferenceAdapter from XSD and WSDL
             * resources as this causes large XSD/WSDLs to take a very long time
             * to load.
             */
            /*
             * JA: ACE-120: We should not use XSD and WSDL resources anymore.
             * Dependency removed.
             * 
             * if (resource instanceof XSDResourceImpl || resource instanceof
             * WSDLResourceImpl) { if (resource.eAdapters() != null) {
             * CrossReferenceAdapter refAdapter = null; for (Adapter adapter :
             * resource.eAdapters()) { if (adapter instanceof
             * CrossReferenceAdapter) { refAdapter = (CrossReferenceAdapter)
             * adapter; break; } }
             * 
             * if (refAdapter != null) {
             * resource.eAdapters().remove(refAdapter); } } }
             */

            return resource;
        }

        @Override
        protected void demandLoad(Resource resource) throws IOException {
            EditingDomain ed = getEditingDomain();

            if (ed instanceof InternalTransactionalEditingDomain) {
                InternalTransactionalEditingDomain tED =
                        (InternalTransactionalEditingDomain) ed;
                Transaction transaction = tED.getActiveTransaction();

                if (transaction != null && !transaction.isReadOnly()) {
                    // This is already in a write-transaction so just load the
                    // model
                    super.demandLoad(resource);
                } else {
                    /*
                     * There isn't an active transaction or the transaction is
                     * read-only so start an unprotected transaction that will
                     * allow read-write access to the model.
                     */
                    try {
                        transaction = startTransaction(tED);
                        super.demandLoad(resource);

                    } catch (InterruptedException e) {
                        XpdResourcesPlugin.getDefault().getLog()
                                .log(new Status(IStatus.ERROR,
                                        XpdResourcesPlugin.ID_PLUGIN,
                                        e.getMessage(), e));
                    } finally {
                        // Commit the transaction
                        try {
                            transaction.commit();
                        } catch (RollbackException e) {
                            XpdResourcesPlugin.getDefault().getLog()
                                    .log(new Status(IStatus.ERROR,
                                            XpdResourcesPlugin.ID_PLUGIN,
                                            e.getMessage(), e));
                        }
                    }
                }

            } else {
                super.demandLoad(resource);
            }
        }

        @Override
        protected void handleDemandLoadException(Resource resource,
                IOException exception) throws RuntimeException {
            /*
             * MR 40352 - Note that if we do not catch Exception here it can
             * mess things up for owners (like
             * AbstractTransactionalWorkingCopy).
             * 
             * This is because if the file exists but fails to load for some
             * reason (such as invalid XML) then ResourceSetImpl throws up an
             * exception AFTER ADDING the resource to it's list.
             * 
             * This causes the editingDomain.loadResource() to return null
             * rather than returning a resource with errors set on it.
             * 
             * But the NEXT time you call ed.loadResource() ResourceSetImpl
             * picks it up out of the cached list and actually returns the
             * resource it had all along.
             * 
             * Therefore we can give a more consistent return resource but with
             * errors set on it approach and the caller can decide whether to
             * unload it or not.
             * 
             * This is achieved by catching the exception normally thrown during
             * demandLoad and returning the resource up instead. Which means
             * that AbstractTransactionalWorkingCopy gets a more consistent
             * resource+errors rather than sometimes getting resource+errors and
             * sometimes getting resource=null
             */
            try {
                super.handleDemandLoadException(resource, exception);
            } catch (Exception e) {
                /*
                 * Catch error but don't log it as EMF will sometimes try to
                 * resolve references and if the reference is to a project that
                 * is no longer referenced from the host project then a lot of
                 * FileNotFound exceptions are thrown (which we expect but do
                 * not want to log).
                 */
            }
        }

        /**
         * Start an unprotected transaction.
         * 
         * @param domain
         *            editing doman
         * @return {@link Transaction}.
         * @throws InterruptedException
         */
        private Transaction startTransaction(
                InternalTransactionalEditingDomain editingDomain)
                throws InterruptedException {
            Map<String, Object> txOptions = new HashMap<String, Object>();
            txOptions.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);
            return editingDomain.startTransaction(false, txOptions);
        }

    }

    /*
     * HANDLE READ-ONLY STATUS CHANGE STUFF
     */
    /**
     * Maximum frequency of going back to JavaIO to check read-only status.
     */
    private long RESULT_CACHE_PERIOD = 1000;

    /*
     * For checking that we're not thrashing things to death going back to
     * JavaIO too much.
     */
    private static int numGets = 0, numRedirectedGets = 0;

    /**
     * Small class to for a map to cache the the last read-only status from
     * JavaIO and when that status was accessed.
     * 
     * @author aallway
     * @since 26 Aug 2011
     */
    private static class TimedReadOnlyStatusResult {
        long timeResultGot;

        Boolean isReadOnly;
    }

    /**
     * AdapterFactoryEditingDomain.isReadOnly() method is backed by a
     * Resource->BoolenReadOnlyStatus HashMap that is assigned-on-demand ONLY
     * ONCE.
     * <p>
     * So we replace the resource-to-read-only hashmap in the editing domain
     * with our own that frequently re-checks the resource-read-only status
     * direct on the file sytem for local files (which is the same as standard
     * editing domain except that standard caches the result only once ever.
     * <p>
     * So when the {@link AdapterFactoryEditingDomain#isReadOnly(Resource)} is
     * called it will call our {@link CachedResourceToReadOnlyMap#get(Object)}
     * which will always get from the actual file system status (at a maximum
     * frequency of RESULT_CACHE_PERIOD).
     * <p>
     * So to {@link AdapterFactoryEditingDomain} it will always look like the
     * resouce is already cached for local file resources because our get()
     * method will alays return a result for those.
     * 
     * @author aallway
     * @since 26 Aug 2011
     */
    private class CachedResourceToReadOnlyMap
            extends HashMap<Resource, Boolean> {

        /**
         * Map of resource to the last result for that resource and the time at
         * which it was checked.
         */
        private Map<Resource, TimedReadOnlyStatusResult> timedPreviousResultCache =
                new HashMap<Resource, XpdEditingDomainFactory.TimedReadOnlyStatusResult>();

        /**
         * @see java.util.HashMap#get(java.lang.Object)
         * 
         * @param key
         * @return
         */
        @Override
        public Boolean get(Object res) {
            Boolean ret = null;
            numGets++;

            if (res instanceof Resource) {
                Resource resource = (Resource) res;

                /*
                 * Check if we recently already got the read-only status from
                 * JavaIO
                 */
                TimedReadOnlyStatusResult timeAndResult =
                        timedPreviousResultCache.get(resource);

                if (timeAndResult != null) {
                    if ((timeAndResult.timeResultGot
                            + RESULT_CACHE_PERIOD) > System
                                    .currentTimeMillis()) {
                        /*
                         * We only just got the status of this local file system
                         * file.
                         */
                        return timeAndResult.isReadOnly;
                    }
                }

                // TODO ADD ABILITY TO ADD READ-ONLY STATUS CHANGE LISTENERS
                // SO that things like process diagram can update.

                /*
                 * See if this is a local file system file and if so, then get
                 * the status from JavaIO (i.e. the file-system).
                 */
                URI uri = (resource.getResourceSet() == null
                        ? editingDomain.getResourceSet()
                        : resource.getResourceSet()).getURIConverter()
                                .normalize(resource.getURI());

                URI localURI = CommonPlugin.asLocalURI(uri);

                if (localURI.isFile() && !localURI.isRelative()) {
                    numRedirectedGets++;

                    File file = new File(localURI.toFileString());
                    if (file.exists() && !file.canWrite()) {
                        ret = Boolean.TRUE;
                    } else {
                        ret = Boolean.FALSE;
                    }

                    /*
                     * Cache this result for a short period (because is ReadOnly
                     * is calle by lots of things especially the prepare() of
                     * most commands!
                     * 
                     * We only do this for local file system files (other files
                     * we resort to the standard cache-once only (i.e. eiditng
                     * domain will call this get() method 1st time, we will
                     * super.get() which will be null so it will calc the
                     * read-only status and do a put(), next time thru we will
                     * fall back to super.get() and it will return the last
                     * result put in by the ed dom.
                     */
                    if (timeAndResult == null) {
                        /*
                         * 1st time add the cache entry. Subsequent times we
                         * just result the values in the existing one.
                         */
                        timeAndResult = new TimedReadOnlyStatusResult();
                        timedPreviousResultCache.put(resource, timeAndResult);
                    }

                    timeAndResult.timeResultGot = System.currentTimeMillis();
                    timeAndResult.isReadOnly = ret;
                }
            }

            if (ret == null) {
                ret = super.get(res);
            }

            boolean log = false;
            if (log) {
                System.out.println("ResourceToReadOnlyMap(" + hashCode() //$NON-NLS-1$
                        + "): numGets(" + numGets + ")  numRedirectedGets(" //$NON-NLS-1$ //$NON-NLS-2$
                        + numRedirectedGets + ")"); //$NON-NLS-1$
            }
            return ret;
        }

        /**
         * @see java.util.HashMap#remove(java.lang.Object)
         * 
         * @param key
         * @return
         */
        @Override
        public Boolean remove(Object key) {
            timedPreviousResultCache.remove(key);
            return super.remove(key);
        }
    }

}