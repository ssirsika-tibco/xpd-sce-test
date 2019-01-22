/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.internal.wc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.tibco.xpd.resources.INonXPDProjectWCFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;

/**
 * Factory for resources in XPD project. Factory is listening to the changes in
 * the workspace and unloads loaded resources when the base resource is changed.
 * 
 * @author jarciuch
 */
public class XpdProjectResourceFactoryImpl implements
        XpdProjectResourceFactory, PropertyChangeListener {

    /** base project. */
    private final IProject project;

    /** Resource to working copy map - this is a synchronized map. */
    private final Map<IResource, WorkingCopy> resources = Collections
            .synchronizedMap(new HashMap<IResource, WorkingCopy>());

    /** Working copy factories */
    private List<WorkingCopyFactoryExtension> wcFactoryExtensions;

    /**
     * Creates instance of XPDProjectResourceFactoryImpl.
     * 
     * @param project
     *            project with the resources
     */
    public XpdProjectResourceFactoryImpl(IProject project) {
        this.project = project;
    }

    /**
     * Search for eclipse resource for given path inside ProcessRoot folder.
     * 
     * @param source
     * @param reference
     * @param referenceType
     * @return <code>IResource</code> if reference resolved, <code>null</code>
     *         otherwise.
     */
    @Override
    public IResource resolveResourceReference(IResource source,
            String reference, String referenceType) {
        ProjectConfig config =
                XpdResourcesPlugin.getDefault().getProjectConfig(project);

        if (config != null && config.getSpecialFolders() != null) {
            EList<SpecialFolder> folders =
                    config.getSpecialFolders().getFoldersOfKind(referenceType);

            for (SpecialFolder folder : folders) {
                if (folder != null) {
                    IFolder folderRes = folder.getFolder();
                    if (folderRes != null) {
                        IResource res = folderRes.findMember(reference);
                        if (res != null) {
                            return res;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Dispose all resources. This method should only be called by the Project
     * Resource Factory framework.
     */
    public void dispose() {
        // Dispose all working copies
        Set<IResource> keySet = resources.keySet();
        // Run in sync as it's a synchronizedMap
        synchronized (resources) {
            for (Iterator<IResource> resourcesIter = keySet.iterator(); resourcesIter
                    .hasNext();) {
                WorkingCopy wc = resources.get(resourcesIter.next());
                resourcesIter.remove();
                if (wc instanceof AbstractWorkingCopy) {
                    ((AbstractWorkingCopy) wc).dispose();
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.XpdProjectResourceFactory#factoryResourceChanged
     * (org.eclipse.core.resources.IResourceDelta)
     */
    @Override
    public void factoryResourceChanged(IResourceDelta delta) {
        /*
         * No implementation - this method has been @deprecated. Method
         * dispose() has been introduced instead to clean the factory
         */
    }

    /**
     * Used by {@see
     * XpdProjectResourceFactoryImpl#retrieveNamespaceURI(IResource)} to stop
     * SAX parsing after first element.
     * 
     * @author wzurek
     */
    private class StopException extends Exception {
        /** serial id. */
        private static final long serialVersionUID = 5872865790296051123L;

        /** return value. */
        public String uri;
    }

    /**
     * This method iterates thru map of working copies of the resources and
     * returns the ones who are dirty.
     * 
     * @return all resourcess that are marked as dirty
     */
    @Override
    public Map<IResource, WorkingCopy> getDirtyResources() {
        Map<IResource, WorkingCopy> dirtyResources = //
                new HashMap<IResource, WorkingCopy>();
        if (resources != null) {
            Set<Entry<IResource, WorkingCopy>> set = resources.entrySet();
            // Run in sync as it's a synchronizedMap
            synchronized (resources) {
                for (Entry<IResource, WorkingCopy> element : set) {
                    IResource key = element.getKey();
                    WorkingCopy value = element.getValue();
                    if (value != null && value.isWorkingCopyDirty()) {
                        dirtyResources.put(key, value);
                    }
                }
            }
        }
        return dirtyResources;
    }

    /**
     * @param resource
     *            resource that is contained by working copy
     * @return working copy
     */
    @Override
    public WorkingCopy getWorkingCopy(IResource resource) {
        WorkingCopy result;
        if (resource == null) {
            throw new NullPointerException(
                    Messages.XpdProjectResourceFactoryImpl_resourceCannotBeNull_longdesc);
        }
        // if the file doesn't exist, return null
        if (!resource.exists()) {
            return null;
        }
        if (!resource.getProject().equals(project)) {
            throw new IllegalArgumentException(
                    Messages.XpdProjectResourceFactoryImpl_resourceMustBeInSameProject_longdesc);
        }
        result = getWCFromExtension(resource);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.XpdProjectResourceFactory#getWorkingCopies()
     */
    @Override
    public WorkingCopy[] getWorkingCopies() {
        return resources.values().toArray(new WorkingCopy[resources.values()
                .size()]);
    }

    /**
     * Returns working copy from cache or create new from extension (or returns
     * null).
     * 
     * @param resource
     *            resource to check
     * @return working copy from cache or create new from extension (or returns
     *         null)
     */
    private WorkingCopy getWCFromExtension(IResource resource) {
        WorkingCopy result;

        if (resources.containsKey(resource)) {
            // if there was already created working copy return it...
            result = resources.get(resource);
        } else {
            // ... otherwise create new one
            boolean wcCreated = false;
            synchronized (this) {
                try {
                    /*
                     * Check if the working copy has already been created by a
                     * thread that may already have been in this sync block
                     * while this thread was waiting...
                     */
                    result = resources.get(resource);

                    if (result == null) {
                        WorkingCopyFactory wcFactory =
                                getWorkingCopyFactory(resource);
                        if (wcFactory != null) {
                            result = wcFactory.getWorkingCopy(resource);
                            if (result != null) {
                                // Register listener with the working copy
                                result.addListener(this);
                                this.resources.put(resource, result);
                                wcCreated = true;
                            }
                        } else {
                            result = null;
                        }
                    }
                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                    result = null;
                }
            }

            if (wcCreated && result != null) {
                // Fire a working copy created event
                WorkingCopyCreationListenersManager.getInstance()
                        .fireWorkingCopyCreated(result);
            }
        }
        return result;
    }

    private boolean isXPDNatureProject() {
        try {
            return project.hasNature(XpdConsts.PROJECT_NATURE_ID);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        return false;
    }

    /**
     * Start parsing the file and return URI of the namespace of root element,
     * or null.
     * 
     * @param resource
     *            resource to check
     * @return namespace or null if not determined
     */
    private String retrieveNamespaceURI(IResource resource) {

        String uri = null;
        XMLReader xmlReader = getXMLReader();

        if (xmlReader != null) {
            try {
                DefaultHandler handler = new DefaultHandler() {
                    @Override
                    public void startElement(String uri, String localName,
                            String qName, Attributes attributes)
                            throws SAXException {

                        StopException ex = new StopException();
                        ex.uri = uri;
                        throw new SAXException(ex);
                    }
                };
                xmlReader.setContentHandler(handler);
                xmlReader.parse(new InputSource(new FileInputStream(resource
                        .getLocation().toFile())));
            } catch (SAXException e) {
                // A parsing error occurred; the xml input is not valid
                if (e.getException() instanceof StopException) {
                    uri = ((StopException) e.getException()).uri;
                }
            } catch (IOException e) {
                // ignore, will return null
            }
        }

        return "".equals(uri) ? null : uri; //$NON-NLS-1$
    }

    /**
     * @return XML reader
     */
    private XMLReader getXMLReader() {
        XMLReader xmlReader = null;
        try {
            xmlReader = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            // If unable to create an instance, let's try to use
            // the XMLReader from JAXP
            if (xmlReader == null) {
                SAXParserFactory spf = SAXParserFactory.newInstance();
                spf.setNamespaceAware(true);
                try {
                    xmlReader = spf.newSAXParser().getXMLReader();
                } catch (SAXException e1) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                } catch (ParserConfigurationException e1) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
        }
        return xmlReader;
    }

    /**
     * Retrieve working copy factory for given resource from extension point or
     * from the cache.
     * 
     * @param resource
     *            resource to check
     * @return working copy factory for given resource or null if not found.
     * @throws CoreException
     *             when something went wrong
     */
    private WorkingCopyFactory getWorkingCopyFactory(IResource resource)
            throws CoreException {
        WorkingCopyFactory result = null;
        boolean isXPDProject = isXPDNatureProject();

        // Lazy-load the working copy factory extensions
        if (wcFactoryExtensions == null) {
            IExtensionPoint extensionPoint =
                    Platform.getExtensionRegistry()
                            .getExtensionPoint(XpdResourcesPlugin.ID_PLUGIN,
                                    "workingCopyFactory"); //$NON-NLS-1$
            if (extensionPoint == null) {
                // No plug-ins contributed to extension
                wcFactoryExtensions = Collections.emptyList();
            } else {
                wcFactoryExtensions =
                        new ArrayList<WorkingCopyFactoryExtension>();
                for (IConfigurationElement element : extensionPoint
                        .getConfigurationElements()) {
                    wcFactoryExtensions.add(new WorkingCopyFactoryExtension(
                            element));
                }
            }
        }

        for (WorkingCopyFactoryExtension ext : wcFactoryExtensions) {
            // using extension to determine if this working copy factory
            // is applicable for resource
            boolean isFactoryFor = true;
            try {
                Pattern pattern = ext.getFilePattern();
                if (pattern != null) {
                    String fileName = resource.getName();
                    Matcher matcher = pattern.matcher(fileName);
                    if (!matcher.matches()) {
                        isFactoryFor = false;
                        continue;
                    }
                }
            } catch (PatternSyntaxException ex) {
                XpdResourcesPlugin.getDefault().getLogger().error(ex);
            }

            // (optional check) check if xml namespace matches
            String xmlNamespace = ext.getXmlNamespace();
            if (xmlNamespace != null) {
                String uri = retrieveNamespaceURI(resource);
                if (!xmlNamespace.equals(uri)) {
                    isFactoryFor = false;
                    continue;
                }
            }

            if (isFactoryFor) {
                // only if configuration element points to proper factory
                WorkingCopyFactory wcFactory = ext.getWcFactory();
                if (wcFactory != null
                        && (isXPDProject || wcFactory instanceof INonXPDProjectWCFactory)) {
                    // ask factory if it is applicable for this resource
                    boolean isApplicable = wcFactory.isFactoryFor(resource);
                    // (required check) check if factory is applicable for
                    // resource
                    if (isApplicable) {
                        // this if factory that we are looking for
                        result = wcFactory;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @seejava.beans.PropertyChangeListener#propertyChange(java.beans.
     * PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(WorkingCopy.PROP_REMOVED)) {
            Object wcToRemove = evt.getSource();

            // Remove the working copy from local cache
            if (wcToRemove != null) {
                Set<Entry<IResource, WorkingCopy>> entrySet =
                        resources.entrySet();

                // Run in sync as it's a synchronizedMap
                synchronized (resources) {
                    for (Iterator<Entry<IResource, WorkingCopy>> iter =
                            entrySet.iterator(); iter.hasNext();) {
                        Entry<IResource, WorkingCopy> next = iter.next();

                        if (next.getValue().equals(wcToRemove)) {
                            iter.remove();
                        }
                    }
                }
            }
        }
    }

    /**
     * Add working copies for all resources in the project.
     */
    /* default */void addAllWorkingCopies() {
        try {
            project.accept(new IResourceVisitor() {
                @Override
                public boolean visit(IResource resource) throws CoreException {
                    if (resource instanceof IFile) {
                        getWCFromExtension(resource);
                        return false;
                    }
                    return true;
                }
            });
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }

        XpdResourcesPlugin.getDefault().getLogger()
                .debug("Created working copies: " + resources); //$NON-NLS-1$
    }

    /**
     * Representation of the WorkingCopy factory extension.
     * 
     * @author njpatel
     */
    private class WorkingCopyFactoryExtension {

        private final IConfigurationElement element;

        private Pattern filePattern;

        private String xmlNamespace;

        private WorkingCopyFactory wcFactory;

        private WorkingCopyFactoryExtension(IConfigurationElement element) {
            this.element = element;
        }

        /**
         * Get the file pattern specified in the extension.
         * 
         * @return Pattern if provided, <code>null</code> otherwise.
         */
        public Pattern getFilePattern() {
            if (filePattern == null) {
                String pattern = element.getAttribute("filePattern"); //$NON-NLS-1$
                if (pattern != null && pattern.length() > 0) {
                    filePattern = Pattern.compile(pattern);
                }
            }
            return filePattern;
        }

        /**
         * Get the XML namespace provided in the extension.
         * 
         * @return namespace or <code>null</code> if not specified.
         */
        public String getXmlNamespace() {
            if (xmlNamespace == null) {
                xmlNamespace = element.getAttribute("xmlNamespace"); //$NON-NLS-1$
            }
            return xmlNamespace;
        }

        /**
         * Get the working copy factory.
         * 
         * @return
         */
        public WorkingCopyFactory getWcFactory() {
            if (wcFactory == null) {
                Object clazz;
                try {
                    clazz = element.createExecutableExtension("class");//$NON-NLS-1$
                    if (clazz instanceof WorkingCopyFactory) {
                        wcFactory = (WorkingCopyFactory) clazz;
                    }
                } catch (CoreException e) {
                    XpdResourcesPlugin.getDefault().getLogger().error(e);
                }
            }
            return wcFactory;
        }
    }
}
