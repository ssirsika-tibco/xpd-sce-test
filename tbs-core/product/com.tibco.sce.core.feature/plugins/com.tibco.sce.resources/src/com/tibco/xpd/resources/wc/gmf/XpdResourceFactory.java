/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.wc.gmf;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;
import org.eclipse.gmf.runtime.emf.core.resources.GMFHelper;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResource;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory;

import com.tibco.xpd.resources.SpecialFolderRelativeURIHandler;
import com.tibco.xpd.resources.util.URIUtil;

/**
 * An extension of the {@link GMFResourceFactory} that will register custom
 * {@link URIHandler URIHandlers} to all resources. This handler will
 * resolve/deresolve URIs stored in the model relative to a special folder.
 * <p>
 * An extension of <code>GMFResource</code> is used for the resource.
 * </p>
 * <p>
 * This class can either be subclassed or used directly in the
 * <code>org.eclipse.emf.ecore.extension_parser</code> extension.
 * </p>
 * 
 * @author njpatel
 * 
 * @since 3.0
 * 
 */
public class XpdResourceFactory extends GMFResourceFactory implements
        URIFragmentQueryProvider {

    private final static String UTF8_XMI_ENCODING = "UTF-8"; //$NON-NLS-1$

    @SuppressWarnings("unchecked")
    @Override
    public Resource createResource(URI uri) {
        /*
         * Overridden this method to create our own resource. See XpdResource
         * for more details.
         */
        XMIResource resource = new XpdResource(uri);

        resource.getDefaultLoadOptions().putAll(getDefaultLoadOptions());
        resource.getDefaultSaveOptions().putAll(getDefaultSaveOptions());

        if (!resource.getEncoding().equals(UTF8_XMI_ENCODING))
            resource.setEncoding(UTF8_XMI_ENCODING);

        // Add the handler to the load and save options
        SpecialFolderRelativeURIHandler handler =
                new SpecialFolderRelativeURIHandler();
        /*
         *  XPD-8306 (Nick) The following line has been added to fix a NullPointer issue
         *  when creating BOM/OM model files. This wasn't previously needed, but with the
         *  latest Eclipse/EMF libraries it seems to clear the base URI after saving the
         *  file, which causes subsequent code to fail.
         *  Setting the base URI here resolves the issue, but it's unclear why we were previously
         *  leaving the setting of the base URI until the Save.
         */
        handler.setBaseURI(uri);
        
        resource.getDefaultLoadOptions().put(XMIResource.OPTION_URI_HANDLER,
                handler);
        resource.getDefaultSaveOptions().put(XMIResource.OPTION_URI_HANDLER,
                handler);

        // Add the query provider
        resource.getDefaultLoadOptions().put(OPTION_FRAGMENT_QUERY_PROVIDER,
                this);

        return resource;
    }

    /**
     * Subclasses may override. Default implementation returns <code>null</code>
     * .
     * 
     * @see URIFragmentQueryProvider#getURIFragmentQuery(EObject)
     */
    public String getURIFragmentQuery(EObject eo) {
        return null;
    }

    /**
     * Extends the standard <code>GMFResource</code> to override
     * {@link GMFResource#createXMLHelper() createXMLHelper}.
     * <p>
     * GMFResource overrides {@link XMIHelperImpl} with {@link GMFHelper}.
     * Unfortunately, {@link GMFResource} does not allow URI resolution of
     * inter-project references. Therefore, <code>xpdResource</code> has been
     * created to revert to using {@link XMIHelperImpl}.
     * </p>
     * 
     * @author njpatel
     * 
     */
    private class XpdResource extends GMFResource {

        public XpdResource(URI uri) {
            super(uri);
        }

        @Override
        protected void unloaded(InternalEObject internalEObject) {
            /*
             * Override the unload method so that the fragment query can be
             * inserted in the URI when the proxy object is being created.
             */
            String fragment = getURIFragment(internalEObject);

            if (fragment != null) {
                // Append query to the fragment
                String query = getURIFragmentQuery(internalEObject);

                if (query != null) {
                    fragment += "?" + URIUtil.encodeQuery(query) + "?"; //$NON-NLS-1$ //$NON-NLS-2$
                }

                internalEObject.eSetProxyURI(uri.appendFragment(fragment));
                internalEObject.eAdapters().clear();

                return;
            }

            super.unloaded(internalEObject);
        }

        @Override
        protected XMLHelper createXMLHelper() {
            /*
             * Override the XMIHelper so that additional queries can be attached
             * to the fragment of each uri - this will be used to hold extra
             * information to identify the object referenced in case the
             * reference is broken.
             */
            return new XMIHelperImpl(this) {

                @Override
                protected String getURIFragmentQuery(
                        Resource containingResource, EObject object) {
                    String query =
                            XpdResourceFactory.this.getURIFragmentQuery(object);
                    return URIUtil.encodeQuery(query);
                }
            };
        }

    }
}
