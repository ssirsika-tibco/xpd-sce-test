/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;

import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * <p>
 * <i>Created: Nov 17, 2006</i>.
 * 
 * @author mmaciuki
 */
public class TibcoEMFAdapterFactory implements IAdapterFactory {

    /**
     * TODO comment me.
     */
    protected static final Class IFILE_CLASS = IFile.class;

    /**
     * TODO comment me.
     */
    protected static final Class IRESOURCE_CLASS = IResource.class;

    /**
     * TODO comment me.
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     *      java.lang.Class)

     * @param adaptableObject
     *            param
     * @param adapterType
     *            param
     * @return Object
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        IResource result;
        if (adaptableObject instanceof EObject) {
            if (adapterType == IFILE_CLASS || adapterType == IRESOURCE_CLASS) {
                result = Helper.getFile((EObject) adaptableObject);
            } else {
                // get resource from working copy
                WorkingCopy workingCopy = WorkingCopyUtil
                        .getWorkingCopyFor((EObject) adaptableObject);
                result = workingCopy.getEclipseResources().get(0);
            }
        } else {
            result = null;
        }

        return result;
    }

    /**
     * TODO comment me.
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     * @return Class[]
     */
    public Class[] getAdapterList() {
        return new Class[] { IRESOURCE_CLASS, IFILE_CLASS };
    }

    /**
     * <p>
     * <i>Created: Nov 17, 2006</i>.
     * 
     * @see WorkbenchResourceHelper
     * @see WorkbenchResourceHelperBase
     * @author mmaciuki
     */
    static final class Helper {

        /**
         * TODO comment me.
         */
        private static final String PLATFORM_RESOURCE = "resource"; //$NON-NLS-1$

        /**
         * TODO comment me.
         */
        private static final Object PLATFORM_PROTOCOL = "platform"; //$NON-NLS-1$

        /**
         * TODO comment me.
         */
        private Helper() {
        }

        /**
         * TODO comment me.
         * 
         * @param obj
         *            param
         * @return IFile
         */
        public static IFile getFile(EObject obj) {
            if (obj == null) {
                return null;
            }

            Resource mofResource = obj.eResource();
            if (mofResource == null) {
                return null;
            }
            return getFile(mofResource);
        }

        /**
         * TODO comment me.
         * 
         * @param aResource
         *            param
         * @return IFile
         */
        public static IFile getFile(Resource aResource) {
            if (aResource != null) {
                // if (isReferencedResource(aResource))
                // return getFile((ReferencedResource) aResource);
                return getFile(aResource.getResourceSet(), aResource.getURI());
            }
            return null;
        }

        /**
         * TODO comment me.
         * 
         * @param set
         *            param
         * @param uri
         *            param
         * @return IFile
         */
        protected static IFile getFile(ResourceSet set, URI uri) {
            IFile file = getPlatformFile(uri);
            if (file == null) {
                if (set != null) {
                    URIConverter converter = set.getURIConverter();
                    URI convertedUri = converter.normalize(uri);
                    if (!uri.equals(convertedUri)) {
                        return getPlatformFile(convertedUri);
                    }
                }
            }
            return file;
        }

        /**
         * TODO comment me.
         * 
         * @param uri
         *            param
         * @return IFile
         */
        public static IFile getPlatformFile(URI uri) {
            if (isPlatformResourceURI(uri)) {
                String fileString = URI.decode(uri.path());
                fileString = fileString
                        .substring(PLATFORM_RESOURCE.length() + 1);
                return ResourcesPlugin.getWorkspace().getRoot().getFile(
                        new Path(fileString));
            }
            return null;
        }

        /**
         * TODO comment me.
         * 
         * @param uri
         *            param
         * @return bolean
         */
        public static boolean isPlatformResourceURI(URI uri) {
            return PLATFORM_PROTOCOL.equals(uri.scheme())
                    && PLATFORM_RESOURCE.equals(uri.segment(0));
        }
    }
}
