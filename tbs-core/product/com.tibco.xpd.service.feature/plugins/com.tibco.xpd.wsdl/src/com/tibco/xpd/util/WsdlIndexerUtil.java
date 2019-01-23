/*
 * Copyright (c) TIBCO Software Inc. 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.util;

import static com.tibco.xpd.wsdl.Activator.TIBEX;
import static com.tibco.xpd.wsdl.Activator.TIBEX_URI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.wsdl.Fault;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.PortType;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.WSDLElement;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.WsdlServiceKey;

/**
 * WSDL Indexer utility that allows access to the WSDL indexer.
 * 
 * @author njpatel
 * 
 */
public final class WsdlIndexerUtil {

    /** The wsdl extension attribute for the source URI of the wsdl */
    public static final String SRC_EXT_ATTR = Activator.TIBEX + ":src"; //$NON-NLS-1$

    /**
     * WSDL indexer id.
     */
    public static final String INDEXER_ID = "com.tibco.xpd.wsdl.wsdlIndexer"; //$NON-NLS-1$

    /** WSDL Indexer qualified name delimiter */
    private static final String QNAME_DELIM = "."; //$NON-NLS-1$

    /**
     * Type of indexer element.
     */
    public enum WsdlElementType {
        PORT_TYPE, SERVICE_OPERATION, PORTTYPE_OPERATION, WSDL, PORT, MESSAGE, SERVICE;

        public static WsdlElementType getType(IndexerItem item) {
            if (item != null) {
                return valueOf(item.getType());
            }
            return null;
        }
    }

    /**
     * WSDL indexer additional attributes.
     * <p>
     * Use the {@link #toString()} method to get the String representation of
     * this attribute (to be used in indexer criteria for example).
     * </p>
     */
    public enum WsdlIndexerAttributes {
        PROJECT("internal_project"), //$NON-NLS-1$
        PATH("internal_path"), //$NON-NLS-1$
        CONTAINER("container"), //$NON-NLS-1$ 
        QUALIFICATION("qualification"), //$NON-NLS-1$
        REFERED_PORTTYPE("referedPortType"), //$NON-NLS-1$
        INSPECIALFOLDER("inSpecialFolder"), //$NON-NLS-1$
        ISDERIVED("isDerived"), //$NON-NLS-1$
        TRANSPORT_URI("transportUri"), //$NON-NLS-1$
        SRC_URI("srcUri"), //$NON-NLS-1$
        TARGET_NAMESPACE("targetNamespace"); //$NON-NLS-1$

        private final String identifier;

        WsdlIndexerAttributes(String identifier) {
            this.identifier = identifier;
        }

        @Override
        public String toString() {
            return identifier;
        }
    }

    private static final EditingDomain editingDomain = XpdResourcesPlugin
            .getDefault().getEditingDomain();

    private static final IndexerService service = XpdResourcesPlugin
            .getDefault().getIndexerService();

    /**
     * Get all indexer items with the given criteria.
     * 
     * @param name
     *            name of item to find, can be <code>null</code>
     * @param type
     *            type of item to find, can be <code>null</code>
     * @param uri
     *            uri of item to find, can be <code>null</code>
     * @param additionalAttrs
     *            additional attributes to find, can be <code>null</code>
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return indexed items matching the criteria. If all parameters are
     *         <code>null</code> then all indexed items will be returned.
     */
    public static Collection<IndexerItem> getIndexedItems(String name,
            WsdlElementType type, String uri,
            Map<WsdlIndexerAttributes, String> additionalAttrs,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        IndexerItemImpl criteria =
                new IndexerItemImpl(name, type != null ? type.name() : null,
                        uri, null);
        if (additionalAttrs != null) {
            for (Entry<WsdlIndexerAttributes, String> entry : additionalAttrs
                    .entrySet()) {
                criteria.set(entry.getKey().toString(), entry.getValue());
            }
        }

        if (inSpecialFolderOnly) {
            criteria.set(WsdlIndexerAttributes.INSPECIALFOLDER.toString(), "y"); //$NON-NLS-1$
        }

        if (!includeDerivedResources) {
            // Don't include derived resources
            criteria.set(WsdlIndexerAttributes.ISDERIVED.toString(), "n"); //$NON-NLS-1$
        }

        return service.query(INDEXER_ID, criteria);
    }

    /**
     * Get the name of the project this indexed item belongs to.
     * 
     * @param item
     * @return
     */
    public static String getProjectName(IndexerItem item) {
        return item != null ? item
                .get(WsdlIndexerAttributes.PROJECT.toString()) : null;
    }

    /**
     * Get the path of the resource this indexed item belongs to. This will be
     * the full workspace-relative path.
     * 
     * @param item
     * @return
     */
    public static String getPath(IndexerItem item) {
        return item != null ? item.get(WsdlIndexerAttributes.PATH.toString())
                : null;
    }

    /**
     * Get the WSDL file that contains the given indexed item.
     * 
     * @param item
     * @return {@link IFile} if found, <code>null</code> otherwise.
     */
    public static IFile getWsdlFile(IndexerItem item) {
        IFile file = null;
        if (item != null) {
            String path = getPath(item);
            if (path != null) {
                IResource member =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .findMember(path);
                if (member instanceof IFile) {
                    file = (IFile) member;
                }
            }
        }
        return file;
    }

    /**
     * Check if the given indexed item belongs to a WSDL in a services special
     * folder.
     * 
     * @param item
     * @return <code>true</code> if in Services special folder,
     *         <code>false</code> otherwise.
     */
    public static boolean isInServicesSpecialFolder(IndexerItem item) {
        if (item != null) {
            return "y".equals(item.get(WsdlIndexerAttributes.INSPECIALFOLDER //$NON-NLS-1$
                    .toString()));
        }
        return false;
    }

    /**
     * Check if the given indexed item belongs to a WSDL resource that is marked
     * as derived.
     * 
     * @param item
     * @return <code>true</code> if it comes from a derived resource,
     *         <code>false</code> otherwise.
     */
    public static boolean isInDerivedResource(IndexerItem item) {
        if (item != null) {
            return "y".equals(item.get(WsdlIndexerAttributes.ISDERIVED //$NON-NLS-1$
                    .toString()));
        }
        return false;
    }

    /**
     * Get the container {@link IndexerItem} of the given item
     * 
     * @param item
     * @return Container indexer item if there is one, <code>null</code>
     *         otherwise.
     */
    public static IndexerItem getContainer(IndexerItem item) {
        IndexerItem container = null;
        if (item != null) {
            String containerUri =
                    item.get(WsdlIndexerAttributes.CONTAINER.toString());
            if (containerUri != null) {
                IndexerItemImpl criteria =
                        new IndexerItemImpl(null, null, containerUri, null);
                Collection<IndexerItem> query =
                        service.query(INDEXER_ID, criteria);
                if (query != null && !query.isEmpty()) {
                    container = query.iterator().next();
                }
            }
        }
        return container;
    }

    /**
     * Finds all ports for given port type. The ports may belong to different
     * WSDL files then port type.
     * 
     * @param portType
     * @param inSpecialFolderOnly
     *            <code>true</code> to only consider WSDLs from the Services
     *            special folder, <code>false</code> to include all WSDLs in the
     *            workspace.
     * @param includeDerivedResources
     *            <code>true</code> to include derived resources in the search,
     *            <code>false</code> otherwise.
     * @return
     */
    public static List<Port> getPorts(org.eclipse.wst.wsdl.PortType portType,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        List<Port> ports = new ArrayList<Port>();
        Collection<IndexerItem> items =
                getIndexedItems(null,
                        WsdlElementType.PORT,
                        null,
                        singletonMap(WsdlIndexerAttributes.REFERED_PORTTYPE,
                                EcoreUtil.getURI(portType).toString()),
                        inSpecialFolderOnly,
                        includeDerivedResources);

        for (IndexerItem item : items) {
            EObject eo = resolve(item);
            if (eo instanceof Port) {
                ports.add((Port) eo);
            }
        }
        return ports;
    }

    /**
     * Get the remote (source) URL of the given Operation.
     * 
     * @param project
     *            project to search
     * @param key
     *            the Operation information
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return the source url, <code>null</code> if it cannot be determined
     */
    public static String getWsdlUrl(IProject project, WsdlServiceKey key,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        if (key != null) {
            IndexerItem item =
                    getOperationItem(project,
                            key,
                            inSpecialFolderOnly,
                            includeDerivedResources);
            if (item != null) {
                return getWsdlUrl(item, inSpecialFolderOnly);
            }
        }
        return null;
    }

    /**
     * Get the remote (source) URL of the WSDL that this indexer item belongs
     * to.
     * 
     * @param item
     * @param needSpecialFolderRelativePath
     *            <code>true</code> if Services special folder relative path is
     *            required when returning the path to the local wsdl file.
     * @return the source url. If no source url is found then this will return
     *         the Services special folder relative path of the local wsdl file
     *         if <i>needSpecialFolderRelativPath</i> is set to
     *         <code>true</code> and project relative path if it is set to
     *         <code>false</code>.
     */
    public static String getWsdlUrl(IndexerItem item,
            boolean needSpecialFolderRelativePath) {
        String srcUri = null;
        if (item != null) {
            String path = getPath(item);
            if (path != null) {
                /*
                 * First check if the source url has been indexed
                 */
                IndexerItemImpl criteria = new IndexerItemImpl();
                criteria.setType(WsdlElementType.WSDL.toString());
                criteria.set(WsdlIndexerAttributes.PATH.toString(), path);
                Collection<IndexerItem> items =
                        service.query(INDEXER_ID, criteria);
                if (items != null && !items.isEmpty()) {
                    IndexerItem def = items.iterator().next();
                    srcUri = def.get(WsdlIndexerAttributes.SRC_URI.toString());
                }

                // If no source URL is found then return the local resource's
                // special folder relative path
                if (srcUri == null || srcUri.length() == 0) {
                    IPath p =
                            getRelativePath(item, needSpecialFolderRelativePath);
                    if (p != null) {
                        srcUri = p.toString();
                    }
                }
            }
        }
        return srcUri;
    }

    /**
     * Get the local WSDL file that corresponds to the given source URI.
     * 
     * @param project
     *            the project to search for the local wsdl file (this will also
     *            check all referenced projects)
     * @param sourceUri
     *            the source URI of the wsdl
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * 
     * @return wsdl file, <code>null</code> if one is not found.
     */
    public static IFile getWsdlLocalFile(IProject project, String sourceUri,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        IFile file = null;
        if (project != null && sourceUri != null) {
            // Search the index for the WSDL with the given Source URI
            Collection<IndexerItem> items =
                    getIndexedItems(null,
                            WsdlElementType.WSDL,
                            null,
                            singletonMap(WsdlIndexerAttributes.SRC_URI,
                                    sourceUri),
                            inSpecialFolderOnly,
                            includeDerivedResources);
            if (items != null && !items.isEmpty()) {
                // Find the item from the given project (or referenced project)
                IndexerItem item = findItemFromProject(project, items);
                if (item != null) {
                    String path = getPath(item);
                    if (path != null) {
                        IResource member =
                                project.getWorkspace().getRoot()
                                        .findMember(path);
                        if (member instanceof IFile) {
                            file = (IFile) member;
                        }
                    }
                }
            }
        }
        return file;
    }

    /**
     * Add the source URL to the Definition element in the given resource. This
     * uses a TIBCO extension to add the source attribute.
     * 
     * @param wsdlResource
     * @param wsdlSourceUri
     */
    public static void addSourceUri(Resource wsdlResource, String wsdlSourceUri) {

        if (wsdlResource != null && wsdlSourceUri != null) {

            Definition def = null;

            for (EObject eo : wsdlResource.getContents()) {
                if (eo instanceof Definition) {
                    def = (Definition) eo;
                    break;
                }
            }
            if (def != null) {
                addSourceUri(def, def, wsdlSourceUri);
            }
        }
    }

    /***
     * Add the source URL to the given element. This uses a TIBCO extension to
     * add the source attribute.
     * 
     * @param definition
     * @param element
     * @param srcUri
     */
    public static void addSourceUri(Definition definition, WSDLElement element,
            String srcUri) {

        if (null != definition && null != element
                && null != element.getElement()) {

            String namespace = definition.getNamespace(Activator.TIBEX);

            if (namespace == null) {
                // Need to add the extension namespace
                definition.addNamespace(TIBEX, TIBEX_URI);
            }

            element.getElement().setAttribute(WsdlIndexerUtil.SRC_EXT_ATTR,
                    srcUri);
        }
    }

    /**
     * Get the source URI from the given WSDL resource. This will read the
     * source extension of the Definition for the source uri.
     * 
     * @param wsdlResource
     * @return source URI if it exists, <code>null</code> otherwise.
     */
    public static String getSourceUri(Resource wsdlResource) {
        if (wsdlResource != null) {
            Definition def = null;
            for (EObject eo : wsdlResource.getContents()) {
                if (eo instanceof Definition) {
                    def = (Definition) eo;
                    break;
                }
            }
            if (def != null) {
                return def.getElement()
                        .getAttribute(WsdlIndexerUtil.SRC_EXT_ATTR);
            }

        }
        return null;
    }

    /**
     * Get the relative path of the local WSDL file that contains the
     * information provided in the WsdlServiceKey.
     * <p>
     * If <i>inSpecialFolderOnly</i> is set to:
     * <ul>
     * <li><code>true</code> - then this will return the special folder relative
     * path if the file is found (in the special folder), otherwise
     * <code>null</code> will be returned.</li>
     * <li><code>false</code> - then this will return the project relative path
     * if the file is found, otherwise <code>null</code> will be returned.</li>
     * </ul>
     * </p>
     * 
     * @param project
     * @param key
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @return path or <code>null</code> if the WSDL file is not found
     */
    public static IPath getRelativePath(IProject project, WsdlServiceKey key,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        IPath path = null;
        if (project != null && key != null) {
            String filePath = key.getFilePath();

            if (filePath != null && filePath.length() > 0) {
                /*
                 * Resolve this file path - if it is a remote URI then this will
                 * try to locate the local wsdl file in the project
                 */
                IFile file =
                        resolveFile(project,
                                filePath,
                                inSpecialFolderOnly,
                                includeDerivedResources);
                if (file != null) {
                    // Return the relative path
                    path =
                            inSpecialFolderOnly ? SpecialFolderUtil
                                    .getSpecialFolderRelativePath(file,
                                            Activator.WSDL_SPECIALFOLDER_KIND)
                                    : file.getProjectRelativePath();
                }
            }

            /*
             * If the file was not found then use the Operation information to
             * best match a wsdl in the project and return this.
             */
            if (path == null) {
                IndexerItem opItem =
                        getOperationItem(project,
                                key,
                                true,
                                includeDerivedResources);
                if (opItem != null) {
                    path = getRelativePath(opItem, inSpecialFolderOnly);
                }
            }
        }

        return path;
    }

    /**
     * Get the relative path of the resource that is the source of the given
     * indexer item.
     * 
     * @param item
     * @param needSpecialFolderRelativePath
     *            <code>true</code> if a Services special folder path should be
     *            returned, <code>false</code> if a project relative path should
     *            be returned.
     * @return if needSpecialFolderRelativePath is set to:
     *         <ul>
     *         <li><code>true</code> - the special folder relative path if the
     *         resource is in a special folder, <code>null</code> otherwise,</li>
     *         <li><code>false</code> - the project relative path if the
     *         resource is found, <code>null</code> otherwise.</li>
     *         </ul>
     */
    public static IPath getRelativePath(IndexerItem item,
            boolean needSpecialFolderRelativePath) {
        IPath path = null;

        if (item != null) {
            String filePath = getPath(item);
            if (filePath != null) {
                IResource member =
                        ResourcesPlugin.getWorkspace().getRoot()
                                .findMember(filePath);
                if (member != null) {
                    if (isInServicesSpecialFolder(item)) {
                        path =
                                SpecialFolderUtil
                                        .getSpecialFolderRelativePath(member,
                                                Activator.WSDL_SPECIALFOLDER_KIND);
                    } else {
                        path = member.getProjectRelativePath();
                    }
                }
            }
        }
        return path;
    }

    /**
     * Resolve the given {@link IndexerItem} to an EObject.
     * 
     * @param editingDomain
     * @param item
     * @return EObject if the indexer item can be resolved, <code>null</code>
     *         otherwise.
     */
    public static EObject resolve(final IndexerItem item) {
        if (editingDomain != null && editingDomain.getResourceSet() != null
                && item != null && item.getURI() != null) {

            try {
                return TransactionUtil
                        .runExclusive((TransactionalEditingDomain) editingDomain,
                                new RunnableWithResult.Impl<EObject>() {
                                    public void run() {
                                        setResult(

                                        editingDomain.getResourceSet()
                                                .getEObject(URI.createURI(item
                                                        .getURI()),
                                                        true)

                                        );
                                    }
                                });
            } catch (InterruptedException e) {
                String m =
                        String.format("Interupted while trying to resolve indexer item: %1$s", //$NON-NLS-1$
                                item);
                Activator.getDefault().getLogger().error(e, m);
            }
        }
        return null;
    }

    /**
     * Get the {@link Operation} with the given details.
     * 
     * @param project
     * @param key
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return
     */
    public static Operation getOperation(IProject project, WsdlServiceKey key,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        IndexerItem item =
                getOperationItem(project,
                        key,
                        inSpecialFolderOnly,
                        includeDerivedResources);
        if (item != null) {
            EObject eo = resolve(item);
            if (eo instanceof Operation) {
                return (Operation) eo;
            } else if (eo instanceof BindingOperation) {
                return ((BindingOperation) eo).getOperation();
            }
        }
        return null;
    }

    /**
     * Get the Operation indexer item with the given details.
     * 
     * @param project
     *            project to search for the operation (will include referenced
     *            projects)
     * @param key
     *            operation information
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return
     */
    public static IndexerItem getOperationItem(IProject project,
            WsdlServiceKey key, boolean inSpecialFolderOnly,
            boolean includeDerivedResources) {
        if (key != null) {
            if (key.getService() != null && key.getService().length() > 0) {
                return getOperationItem(project,
                        key.getService(),
                        key.getPort(),
                        key.getOperation(),
                        key.getFilePath(),
                        inSpecialFolderOnly,
                        includeDerivedResources);
            } else {
                return getOperationItem(project,
                        key.getPortType(),
                        key.getPortTypeOperation(),
                        key.getFilePath(),
                        inSpecialFolderOnly,
                        includeDerivedResources);
            }
        }
        return null;
    }

    /**
     * Get the {@link IndexerItem} that represents the given port (binding)
     * Operation from the indexer. Use <code>resolve</code> to resolve the
     * indexer item to an {@link EObject}.
     * 
     * @param project
     * @param serviceName
     * @param portName
     * @param operationName
     * @param fileLocation
     *            Services special folder relative path of the wsdl file that
     *            will contain the Operation, <code>null</code> to search in all
     *            wsdls in the project (and referenced projects).
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return
     */
    private static IndexerItem getOperationItem(IProject project,
            String serviceName, String portName, String operationName,
            String fileLocation, boolean inSpecialFolderOnly,
            boolean includeDerivedResources) {
        if (project != null && serviceName != null && serviceName.length() > 0
                && portName != null && portName.length() > 0
                && operationName != null && operationName.length() > 0) {
            Map<WsdlIndexerAttributes, String> opts =
                    new HashMap<WsdlIndexerAttributes, String>();
            opts.put(WsdlIndexerAttributes.QUALIFICATION,
                    createQualification(serviceName, portName));
            IFile resolvedFile = null;
            if (fileLocation != null) {
                resolvedFile =
                        resolveFile(project,
                                fileLocation,
                                inSpecialFolderOnly,
                                includeDerivedResources);
                /*
                 * If we have a local file then search the indexer for the
                 * content of this file only, otherwise search for a match from
                 * any wsdl file in this project (or referenced projects)
                 */
                if (resolvedFile != null) {
                    opts.put(WsdlIndexerAttributes.PATH, resolvedFile
                            .getFullPath().toPortableString());
                }
            }
            /*
             * Get all Service Operations from the indexer that match the given
             * name and the qualification built up from the service and port
             * name given.
             */
            Collection<IndexerItem> items =
                    getIndexedItems(operationName,
                            WsdlElementType.SERVICE_OPERATION,
                            null,
                            opts,
                            inSpecialFolderOnly,
                            includeDerivedResources);

            if (items != null && !items.isEmpty()) {
                return resolvedFile != null ? items.iterator().next()
                        : findItemFromProject(project, items);
            }
        }
        return null;
    }

    /**
     * Get the {@link IndexerItem} that represents the given port type Operation
     * from the indexer. Use <code>resolve</code> to resolve the indexer item to
     * an {@link EObject}.
     * 
     * @see #resolve(EditingDomain, IndexerItem)
     * 
     * @param project
     * @param portTypeName
     * @param operationName
     * @param fileLocation
     *            Services special folder relative path of the wsdl file that
     *            will contain the Operation, <code>null</code> to search in all
     *            wsdls in the project (and referenced projects).
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return IndexerItem that represents the given operation,
     *         <code>null</code> if one cannot be found (in the given project or
     *         any of its referenced projects).
     */
    private static IndexerItem getOperationItem(IProject project,
            String portTypeName, String operationName, String fileLocation,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        if (project != null && portTypeName != null
                && portTypeName.length() > 0 && operationName != null
                && operationName.length() > 0) {
            Map<WsdlIndexerAttributes, String> opts =
                    new HashMap<WsdlIndexerAttributes, String>();
            opts.put(WsdlIndexerAttributes.QUALIFICATION, portTypeName);
            IFile resolvedFile = null;
            if (fileLocation != null) {
                /*
                 * If we have a local file then search the indexer for the
                 * content of this file only, otherwise search for a match from
                 * any wsdl file in this project (or referenced projects)
                 */
                resolvedFile =
                        resolveFile(project,
                                fileLocation,
                                inSpecialFolderOnly,
                                includeDerivedResources);
                if (resolvedFile != null) {
                    opts.put(WsdlIndexerAttributes.PATH, resolvedFile
                            .getFullPath().toPortableString());
                }
            }
            /*
             * Find all Operations in the indexer that have the matching name
             * and qualification that matches the port type name
             */
            Collection<IndexerItem> items =
                    getIndexedItems(operationName,
                            WsdlElementType.PORTTYPE_OPERATION,
                            null,
                            opts,
                            inSpecialFolderOnly,
                            includeDerivedResources);

            if (items != null && !items.isEmpty()) {
                return resolvedFile != null ? items.iterator().next()
                        : findItemFromProject(project, items);
            }
        }
        return null;
    }

    /**
     * Resolve the given path into an {@link IFile}.
     * 
     * @param project
     *            project (and referenced projects) to search for the file
     * @param path
     *            if <i>inSpecialFolderOnly</i> is set to <code>true</code> then
     *            this is expected to be a Services special folder relative
     *            path, if <code>false</code> then this is expected to be a
     *            project relative path.
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return resolved file if found, <code>null</code> otherwise.
     */
    private static IFile resolveFile(IProject project, String path,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        IFile file = null;

        if (project != null && path != null) {
            if (inSpecialFolderOnly) {
                // Resolve in the special folder
                file =
                        SpecialFolderUtil
                                .resolveSpecialFolderRelativePath(project,
                                        Activator.WSDL_SPECIALFOLDER_KIND,
                                        path,
                                        true);
            } else {
                // Resolve against the project and referenced projects
                Set<IProject> projects = new LinkedHashSet<IProject>();
                projects.add(project);
                ProjectUtil.getReferencedProjectsHierarchy(project, projects);

                for (IProject p : projects) {
                    IResource member = p.findMember(path);
                    if (member instanceof IFile) {
                        file = (IFile) member;
                        break;
                    }
                }
            }

            if (file == null) {
                // This may be a remote URI so match that
                file =
                        getWsdlLocalFile(project,
                                path,
                                inSpecialFolderOnly,
                                includeDerivedResources);
            }
        }

        return file;
    }

    /**
     * Find an item from the collection that comes from the given project. If
     * not found then all (direct and indirect) referenced projects will be
     * checked.
     * 
     * @param project
     * @param items
     * @return IndexerItem if match found, <code>null</code> otherwise.
     */
    private static IndexerItem findItemFromProject(IProject project,
            Collection<IndexerItem> items) {
        if (project != null && items != null) {
            // First check if there is an item that belongs to the given project
            String projectName = project.getName();
            for (IndexerItem item : items) {
                if (projectName.equals(getProjectName(item))) {
                    return item;
                }
            }

            // Now search for item in any referenced (both direct and
            // indirect) projects
            Set<IProject> projects =
                    ProjectUtil.getReferencedProjectsHierarchy(project, null);

            for (IProject ref : projects) {
                projectName = ref.getName();
                for (IndexerItem item : items) {
                    if (projectName.equals(getProjectName(item))) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the {@link Port} with the given details.
     * 
     * @param project
     * @param serviceName
     * @param portName
     * @param fileLocation
     *            relative path of the wsdl file that will contain the
     *            Operation, <code>null</code> to search in all wsdls in the
     *            project (and referenced projects). if
     *            <i>inSpecialFolderOnly</i> is set to <code>true</code> then
     *            this is expected to be the Services special folder, otherwise
     *            this is expected to be a project relative path.
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return
     */
    public static Port getPort(IProject project, String serviceName,
            String portName, String fileLocation, boolean inSpecialFolderOnly,
            boolean includeDerivedResources) {
        IndexerItem item =
                getPortItem(project,
                        serviceName,
                        portName,
                        fileLocation,
                        inSpecialFolderOnly,
                        includeDerivedResources);
        if (item != null) {
            EObject eo = resolve(item);
            if (eo instanceof Port) {
                return (Port) eo;
            }
        }
        return null;
    }

    /**
     * Get the indexed port item that matches the criteria.
     * 
     * @param project
     * @param serviceName
     * @param portName
     * @param fileLocation
     *            relative path of the wsdl file that will contain the
     *            Operation, <code>null</code> to search in all wsdls in the
     *            project (and referenced projects). if
     *            <i>inSpecialFolderOnly</i> is set to <code>true</code> then
     *            this is expected to be the Services special folder, otherwise
     *            this is expected to be a project relative path.
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return
     */
    public static IndexerItem getPortItem(IProject project, String serviceName,
            String portName, String fileLocation, boolean inSpecialFolderOnly,
            boolean includeDerivedResources) {
        if (project != null && serviceName != null && serviceName.length() > 0
                && portName != null && portName.length() > 0) {
            Map<WsdlIndexerAttributes, String> opts =
                    new HashMap<WsdlIndexerAttributes, String>();
            opts.put(WsdlIndexerAttributes.QUALIFICATION, serviceName);

            IFile resolvedFile = null;
            if (fileLocation != null) {
                /*
                 * If we have a local file then search the indexer for the
                 * content of this file only (if this is a source URL then the
                 * local copy of the wsdl will be used), otherwise search for a
                 * match from any wsdl file in this project (or referenced
                 * projects)
                 */
                resolvedFile =
                        resolveFile(project,
                                fileLocation,
                                inSpecialFolderOnly,
                                includeDerivedResources);
                if (resolvedFile != null) {
                    opts.put(WsdlIndexerAttributes.PATH, resolvedFile
                            .getFullPath().toPortableString());
                }
            }
            /*
             * Find all Ports in the indexer that have the matching name and
             * qualification that matches the service name
             */
            Collection<IndexerItem> items =
                    getIndexedItems(portName,
                            WsdlElementType.PORT,
                            null,
                            opts,
                            inSpecialFolderOnly,
                            includeDerivedResources);
            if (items != null && !items.isEmpty()) {
                return resolvedFile != null ? items.iterator().next()
                        : findItemFromProject(project, items);
            }
        }
        return null;
    }

    /**
     * Get the {@link Port} that matches the given key.
     * 
     * @param project
     * @param key
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return Port if the given key contains the port (binding) operation
     *         information, <code>null</code> otherwise.
     */
    public static Port getPort(IProject project, WsdlServiceKey key,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        if (project != null && key != null) {
            // First get the Operation item
            IndexerItem opItem =
                    getOperationItem(project,
                            key,
                            inSpecialFolderOnly,
                            includeDerivedResources);
            if (opItem != null
                    && WsdlElementType.valueOf(opItem.getType()) == WsdlElementType.SERVICE_OPERATION) {
                // Get the containing Port of this operation
                IndexerItem portItem = getContainer(opItem);
                if (portItem != null) {
                    EObject eo = resolve(portItem);
                    if (eo instanceof Port) {
                        return (Port) eo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the transport URI of the {@link Port} that matches the given key.
     * 
     * @param project
     * @param key
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return transport URI if the key contains the Port Operation information.
     */
    public static String getTransportUri(IProject project, WsdlServiceKey key,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        if (project != null && key != null) {
            // First get the Operation item
            IndexerItem opItem =
                    getOperationItem(project,
                            key,
                            inSpecialFolderOnly,
                            includeDerivedResources);
            if (opItem != null
                    && WsdlElementType.valueOf(opItem.getType()) == WsdlElementType.SERVICE_OPERATION) {
                // Get the containing Port of this operation
                IndexerItem portItem = getContainer(opItem);
                if (portItem != null) {
                    return portItem.get(WsdlIndexerAttributes.TRANSPORT_URI
                            .toString());
                }
            }
        }
        return null;
    }

    /**
     * Get the {@link PortType} that matches the given key. If this key contains
     * the <code>PortType</code> {@link Operation} information then this will be
     * used to get the PortType. If not and it contains the Port (binding)
     * Operation information then the PortType will be derived from this.
     * 
     * @param project
     * @param key
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return <code>PortType</code> if it can be determined, <code>null</code>
     *         otherwise.
     */
    public static PortType getPortType(IProject project, WsdlServiceKey key,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        if (project != null && key != null) {
            // If we have port type information then get the type
            IndexerItem portTypeItem = null;

            if (key.getPortType() != null && key.getPortType().length() > 0) {
                // Get operation so that we know that we have the right port
                // type
                IndexerItem opItem =
                        getOperationItem(project,
                                key.getPortType(),
                                key.getPortTypeOperation(),
                                key.getFilePath(),
                                inSpecialFolderOnly,
                                includeDerivedResources);
                if (opItem != null) {
                    // Get the container - the port type
                    portTypeItem = getContainer(opItem);
                }
            }

            // If we did not get the port type and we have the service
            // information then use that to get the port type
            if (portTypeItem == null && key.getService() != null
                    && key.getService().length() > 0) {
                IndexerItem opItem =
                        getOperationItem(project,
                                key.getService(),
                                key.getPort(),
                                key.getOperation(),
                                key.getFilePath(),
                                inSpecialFolderOnly,
                                includeDerivedResources);
                if (opItem != null) {
                    // Get the referenced port type
                    String portTypeUri =
                            opItem.get(WsdlIndexerAttributes.REFERED_PORTTYPE
                                    .toString());
                    if (portTypeUri != null) {
                        Collection<IndexerItem> items =
                                getIndexedItems(null,
                                        WsdlElementType.PORT_TYPE,
                                        portTypeUri,
                                        null,
                                        inSpecialFolderOnly,
                                        includeDerivedResources);
                        if (!items.isEmpty()) {
                            portTypeItem = items.iterator().next();
                        }
                    }
                }
            }

            if (portTypeItem != null
                    && WsdlElementType.valueOf(portTypeItem.getType()) == WsdlElementType.PORT_TYPE) {
                EObject eo = resolve(portTypeItem);
                if (eo instanceof PortType) {
                    return (PortType) eo;
                }
            }
        }
        return null;
    }

    /**
     * Get the {@link Binding} that matches the given Port (binding) Operation
     * key.
     * 
     * @param project
     * @param key
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return <code>Binding</code> if it can be determined from the key,
     *         <code>null</code> otherwise.
     */
    public static Binding getBinding(IProject project, WsdlServiceKey key,
            boolean inSpecialFolderOnly, boolean includeDerivedResources) {
        if (project != null && key != null) {
            // Get the port
            Port port =
                    getPort(project,
                            key,
                            inSpecialFolderOnly,
                            includeDerivedResources);
            if (port != null) {
                return port.getBinding();
            }
        }
        return null;
    }

    /**
     * Get the input message parts of the given Operation.
     * 
     * @param project
     * @param key
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return
     */
    public static Collection<Part> getInputParts(IProject project,
            WsdlServiceKey key, boolean inSpecialFolderOnly,
            boolean includeDerivedResources) {
        List<Part> parts = new ArrayList<Part>();

        Operation op =
                getOperation(project,
                        key,
                        inSpecialFolderOnly,
                        includeDerivedResources);
        if (op != null) {
            Input input = op.getInput();
            if (input != null) {
                Message message = input.getMessage();
                if (message != null) {
                    List<?> orderedParts = message.getOrderedParts(null);
                    for (Object next : orderedParts) {
                        if (next instanceof Part) {
                            parts.add((Part) next);
                        }
                    }
                }
            }
        }
        return parts;
    }

    /**
     * Get the output message parts of the given Operation.
     * 
     * @param project
     * @param key
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return
     */
    public static Collection<Part> getOutputParts(IProject project,
            WsdlServiceKey key, boolean inSpecialFolderOnly,
            boolean includeDerivedResources) {
        List<Part> parts = new ArrayList<Part>();

        Operation op =
                getOperation(project,
                        key,
                        inSpecialFolderOnly,
                        includeDerivedResources);
        if (op != null) {
            Output out = op.getOutput();
            if (out != null) {
                Message message = out.getMessage();
                if (message != null) {
                    List<?> orderedParts = message.getOrderedParts(null);
                    for (Object next : orderedParts) {
                        if (next instanceof Part) {
                            parts.add((Part) next);
                        }
                    }
                }
            }
        }
        return parts;
    }

    /**
     * Get the fault message parts of the given Operation.
     * 
     * @param project
     * @param key
     * @param faultname
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * 
     * @return collection of fault message parts.
     */
    public static Collection<Part> getFaultParts(IProject project,
            WsdlServiceKey key, String faultName, boolean inSpecialFolderOnly,
            boolean includeDerivedResources) {
        List<Part> parts = new ArrayList<Part>();

        Operation op =
                getOperation(project,
                        key,
                        inSpecialFolderOnly,
                        includeDerivedResources);

        if (op != null) {

            Map faults = op.getFaults();

            if (faults != null && faults.size() > 0) {
                org.eclipse.wst.wsdl.Fault wsdlFault = null;

                for (Iterator iterator = faults.entrySet().iterator(); iterator
                        .hasNext();) {
                    Entry entry = (Entry) iterator.next();

                    if (entry.getValue() instanceof Fault
                            && entry.getValue() instanceof org.eclipse.wst.wsdl.Fault) {
                        org.eclipse.wst.wsdl.Fault fault =
                                (org.eclipse.wst.wsdl.Fault) entry.getValue();

                        if (faultName.equals(fault.getName())) {
                            wsdlFault = fault;
                            break;
                        }
                    }
                }

                if (wsdlFault != null) {
                    Message message = wsdlFault.getMessage();
                    if (message != null) {
                        List<?> orderedParts = message.getOrderedParts(null);
                        for (Object next : orderedParts) {
                            if (next instanceof Part) {
                                parts.add((Part) next);
                            }
                        }
                    }
                }
            }
        }

        return parts;
    }

    /**
     * Create a qualification with the given parts. This method will concatenate
     * the given parts using '.' as the delimiter.
     * 
     * @param parts
     *            parts of the qualification
     * @return the qualification string
     */
    public static String createQualification(String... parts) {
        String qualification = null;

        if (parts != null) {
            qualification = ""; //$NON-NLS-1$
            for (String part : parts) {
                if (qualification.length() > 0) {
                    qualification += QNAME_DELIM;
                }
                qualification += part;
            }
        }

        return qualification;
    }

    /**
     * Check if the given item comes from a BW web service.
     * <p>
     * If the given item is a PORT then its transport URI will be checked - if
     * SOAP over JMS then this is a BW service. If this is not a PORT item then
     * a PORT item in the same resource as the given item will be searched and
     * the transport URI test carried out.
     * </p>
     * 
     * @param item
     * @return <code>true</code> if from BW web service, <code>false</code>
     *         otherwise.
     */
    public static boolean isBW(IndexerItem item) {
        if (item != null) {
            IndexerItem portItem = null;
            if (WsdlElementType.PORT.toString().equals(item.getType())) {
                portItem = item;
            } else {
                // Search for a PORT in the same resource
                String path = getPath(item);
                if (path != null) {
                    IndexerItemImpl criteria = new IndexerItemImpl();
                    criteria.setType(WsdlElementType.PORT.toString());
                    criteria.set(WsdlIndexerAttributes.PATH.toString(), path);
                    Collection<IndexerItem> items =
                            service.query(INDEXER_ID, criteria);
                    if (items != null && !items.isEmpty()) {
                        IndexerItem i = items.iterator().next();
                        if (WsdlElementType.PORT.toString().equals(i.getType())) {
                            portItem = i;
                        }
                    }
                }
            }

            if (portItem != null) {
                IFile wsdlFile = getWsdlFile(portItem);
                String targetNamespace =
                        portItem.get(WsdlIndexerAttributes.TARGET_NAMESPACE
                                .toString());

                /*
                 * Sid SIA-36: The quesiton of whether a WSDL is a BW service
                 * for iprocess should be based on the target namespace being
                 * something like
                 * "http://www.tibco.com/namespaces/2005/bw/staffware-serviceagent/ ..."
                 * NOT on whether the transport simply ends in the letters "jms"
                 * (I've coded this to omitt checking for the 'year' component
                 * just in case this URI has changed over the years).
                 * 
                 * The WSDL import "Descriptor for XML OVer JMS" unfortunately
                 * specified the same transport namespace and that then caused
                 * confusion between those WSDLs and real iprocess BW wsdls.
                 */
                if (targetNamespace.contains("http://www.tibco.com/") //$NON-NLS-1$
                        && targetNamespace.contains("/bw/") //$NON-NLS-1$
                        && targetNamespace.contains("/staffware-serviceagent/")) { //$NON-NLS-1$
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * for a given indexer item return true if the transport is soap over jms
     * 
     * @param item
     * @return
     */
    public static boolean isSoapJms(IndexerItem item) {

        if (null != item) {
            IndexerItem portItem = null;
            if (WsdlElementType.PORT.toString().equals(item.getType())) {
                portItem = item;
            } else {
                // Search for a PORT in the same resource
                String path = getPath(item);
                if (path != null) {
                    IndexerItemImpl criteria = new IndexerItemImpl();
                    criteria.setType(WsdlElementType.PORT.toString());
                    criteria.set(WsdlIndexerAttributes.PATH.toString(), path);
                    Collection<IndexerItem> items =
                            service.query(INDEXER_ID, criteria);
                    if (items != null && !items.isEmpty()) {
                        IndexerItem i = items.iterator().next();
                        if (WsdlElementType.PORT.toString().equals(i.getType())) {
                            portItem = i;
                        }
                    }
                }
            }

            if (null != portItem) {
                String uri =
                        portItem.get(WsdlIndexerAttributes.TRANSPORT_URI
                                .toString());
                return uri != null && uri.toLowerCase().endsWith("jms"); //$NON-NLS-1$
            }
        }

        return false;
    }

    /**
     * for a given indexer item return true if the transport is soap over http
     * 
     * @param item
     * @return
     */
    public static boolean isSoapHttp(IndexerItem item) {

        if (null != item) {
            IndexerItem portItem = null;
            if (WsdlElementType.PORT.toString().equals(item.getType())) {
                portItem = item;
            } else {
                // Search for a PORT in the same resource
                String path = getPath(item);
                if (path != null) {
                    IndexerItemImpl criteria = new IndexerItemImpl();
                    criteria.setType(WsdlElementType.PORT.toString());
                    criteria.set(WsdlIndexerAttributes.PATH.toString(), path);
                    Collection<IndexerItem> items =
                            service.query(INDEXER_ID, criteria);
                    if (items != null && !items.isEmpty()) {
                        IndexerItem i = items.iterator().next();
                        if (WsdlElementType.PORT.toString().equals(i.getType())) {
                            portItem = i;
                        }
                    }
                }
            }

            if (null != portItem) {
                String uri =
                        portItem.get(WsdlIndexerAttributes.TRANSPORT_URI
                                .toString());
                return uri != null && uri.toLowerCase().endsWith("http"); //$NON-NLS-1$
            }
        }

        return false;
    }

    /**
     * Create a map with a single element.
     * 
     * @param key
     * @param value
     * @return
     */
    private static Map<WsdlIndexerAttributes, String> singletonMap(
            WsdlIndexerAttributes key, String value) {
        Map<WsdlIndexerAttributes, String> map =
                new HashMap<WsdlIndexerAttributes, String>(1);
        map.put(key, value);
        return map;
    }

    /**
     * @param wsdlFile
     *            The WSDL file.
     * @return The Definition.
     * @since 3.3
     */
    public static Definition getDefinition(IFile wsdlFile) {
        Definition definition = null;
        if (wsdlFile != null) {
            WorkingCopy wc =
                    XpdResourcesPlugin.getDefault().getWorkingCopy(wsdlFile);
            if (wc != null) {
                EObject eo = wc.getRootElement();
                if (eo instanceof Definition) {
                    definition = (Definition) eo;
                }
            }
        }
        return definition;
    }

    /**
     * Get the {@link BindingOperation} with the given details.
     * 
     * @param project
     * @param key
     * @param inSpecialFolderOnly
     *            <code>true</code> to include elements from WSDLs in the
     *            Services special folder only, <code>false</code> if all WSDLs
     *            should be included.
     * @param includeDerivedResources
     *            <code>true</code> if elements from derived resources should be
     *            included, <code>false</code> to exclude.
     * @return
     */
    public static BindingOperation getBindingOperation(IProject project,
            WsdlServiceKey key, boolean inSpecialFolderOnly,
            boolean includeDerivedResources) {
        IndexerItem item =
                getOperationItem(project,
                        key,
                        inSpecialFolderOnly,
                        includeDerivedResources);
        if (item != null) {
            EObject eo = resolve(item);
            if (eo instanceof BindingOperation) {
                return ((BindingOperation) eo);
            }
        }
        return null;
    }
}
