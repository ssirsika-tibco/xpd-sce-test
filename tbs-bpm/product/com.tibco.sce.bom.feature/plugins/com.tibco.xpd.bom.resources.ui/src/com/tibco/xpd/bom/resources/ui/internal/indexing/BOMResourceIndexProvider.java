/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.internal.indexing;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.BOMIndexerService;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;

/**
 * This class provides information about a working copy. This information is
 * then used by the IndexerService to store it in the resource database. All
 * ResourceIndexerProvider implementations mus extends the abstract class
 * AbstractWorkingCopyIndexProvider and implement the interface
 * WorkingCopyIndexProvider.
 * 
 * @author rassisi
 * 
 */
public class BOMResourceIndexProvider implements WorkingCopyIndexProvider {

    /**
     * This method parses all EObjects in the working copy and returns a
     * collection of descriptors (IndexerItem) about them.
     * 
     * @param wc
     * @param resource
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        // System.out.println(getClass().getSimpleName() + "getIndexItems(): "
        // + wc.getEclipseResources().get(0).getFullPath());

        Collection<IndexerItem> items = new ArrayList<IndexerItem>();
        if (isInSpecialFolder(wc)) {
            EObject root = wc.getRootElement();
            updateIndex_2(items, root);
        }
        return items;
    }

    /**
     * internally used method to parse the EObject model
     */
    private void updateIndex_2(Collection<IndexerItem> items, EObject element) {
        if (element == null) {
            return;
        }
        String uri = BomUIUtil.getURI(element);

        if (element instanceof Package) {
            Package pkg = (Package) element;
            String name = BOMWorkingCopy.getQualifiedPackageName(pkg);

            URI imageURI =
                    URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/" //$NON-NLS-1$
                            + BOMImages.PACKAGE, true);
            createResourceItem(items,
                    name,
                    getDisplayLabel(pkg),
                    uri,
                    ResourceItemType.PACKAGE.name(),
                    null,
                    imageURI.toString(),
                    getCDSFactoryName(name),
                    getNamespaceUri(pkg),
                    (name == null) ? "" : name.toLowerCase()); //$NON-NLS-1$

        } else if (element instanceof Class) {
            Class clazz = (Class) element;
            String name = BOMWorkingCopy.getQualifiedClassName(clazz);
            String caseOrGlobalType = null;

            String type = ResourceItemType.CLASS.toString();
            String imageURI =
                    URI.createPlatformPluginURI(Activator.PLUGIN_ID
                            + "/" + BOMImages.CLASS, true) //$NON-NLS-1$
                            .toString();

            if (BOMGlobalDataUtils.isCaseClass(clazz)) {
                caseOrGlobalType = ResourceItemType.CASE_CLASS.toString();
                imageURI =
                        URI.createPlatformPluginURI(Activator.PLUGIN_ID
                                + "/" + BOMImages.CASE_CLASS, true) //$NON-NLS-1$
                                .toString();
            } else if (BOMGlobalDataUtils.isGlobalClass(clazz)) {
                caseOrGlobalType = ResourceItemType.GLOBAL_CLASS.toString();
                imageURI =
                        URI.createPlatformPluginURI(Activator.PLUGIN_ID
                                + "/" + BOMImages.GLOBAL_CLASS, true) //$NON-NLS-1$
                                .toString();
            }

            createResourceItem(items,
                    name,
                    getDisplayLabel(clazz),
                    uri,
                    type,
                    caseOrGlobalType,
                    imageURI);

        } else if (element instanceof PrimitiveType) {
            PrimitiveType primitiveType = (PrimitiveType) element;
            String name =
                    BOMWorkingCopy.getQualifiedName(primitiveType
                            .getQualifiedName());
            createResourceItem(items,
                    name,
                    getDisplayLabel(primitiveType),
                    uri,
                    ResourceItemType.PRIMITIVE_TYPE.toString(),
                    null,
                    URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/" //$NON-NLS-1$
                            + BOMImages.PRIMITIVE_TYPE, true).toString());
        } else if (element instanceof Enumeration) {
            Enumeration en = (Enumeration) element;
            String name =
                    BOMWorkingCopy.getQualifiedName(en.getQualifiedName());
            createResourceItem(items,
                    name,
                    getDisplayLabel(en),
                    uri,
                    ResourceItemType.ENUMERATION.toString(),
                    null,
                    URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/" //$NON-NLS-1$
                            + BOMImages.ENUMERATION, true).toString());
        }

        // Only interested in contents of Packages
        if (element instanceof Package) {
            for (EObject eo : element.eContents()) {
                updateIndex_2(items, eo);
            }
        }
    }

    private String getDisplayLabel(NamedElement elem) {
        return PrimitivesUtil.getDisplayLabel(elem);
    }

    /**
     * Create the CDS factory name from the given package name.
     * 
     * @param packageName
     * @return
     */
    private String getCDSFactoryName(String packageName) {
        String factoryName = null;

        if (packageName != null) {
            factoryName = packageName.replaceAll("\\.", "_").concat("_Factory"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        return factoryName;
    }

    /**
     * Get the namespace URI to store for the given package.
     * 
     * @param pkg
     * @return
     */
    private String getNamespaceUri(Package pkg) {
        return BOMUtils.getNamespace(pkg, true);
    }

    /**
     * @param wc
     * @return
     */
    public static boolean isInSpecialFolder(WorkingCopy wc) {
        IResource eclipseResource = wc.getEclipseResources().get(0);
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault()
                        .getProjectConfig(eclipseResource.getProject());

        if (projectConfig != null) {
            SpecialFolders specialFolders = projectConfig.getSpecialFolders();
            SpecialFolder sf =
                    specialFolders.getFolderContainer(eclipseResource);
            return sf != null
                    && BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND.equals(sf
                            .getKind());
        }
        return false;
    }

    /**
     * create an resource item entry
     * 
     * @param name
     * @param uri
     * @param type
     * @param caseOrGlobalType
     * @param imageURI
     */
    private void createResourceItem(Collection<IndexerItem> list, String name,
            String displayLabel, String uri, String type,
            String caseOrGlobalType, String imageURI) {
        createResourceItem(list,
                name,
                displayLabel,
                uri,
                type,
                caseOrGlobalType,
                imageURI,
                null,
                null,
                null);
    }

    /**
     * create an resource item entry
     * 
     * @param name
     * @param uri
     * @param type
     * @param caseOrGlobalType
     *            will have 'Case Class' or 'Global Class' if it is a case or
     *            global class null otherwise
     * @param cdsFactoryName
     * @param namespaceUri
     */
    private void createResourceItem(Collection<IndexerItem> list, String name,
            String displayLabel, String uri, String type,
            String caseOrGlobalType, String imageURI, String cdsFactoryName,
            String namespaceUri, String packageNameInLowerCase) {

        Map<String, String> map = new HashMap<String, String>();
        if (null != caseOrGlobalType) {
            map.put(BOMResourcesPlugin.INDEXER_ATTRIBUTE_CASE_OR_GLOBAL_TYPE,
                    caseOrGlobalType);
        }
        map.put(BOMResourcesPlugin.INDEXER_ATTRIBUTE_IMAGE_URI, imageURI);

        if (displayLabel != null) {
            map.put(BOMIndexerService.INDEXER_ATTR_DISPLAY_LABEL, displayLabel);
        }
        if (cdsFactoryName != null) {
            map.put(BOMIndexerService.INDEXER_ATTR_CDS_FACTORY, cdsFactoryName);
        }
        if (namespaceUri != null) {
            map.put(BOMIndexerService.INDEXER_ATTR_NAMESPACE_URI, namespaceUri);
        }
        if (packageNameInLowerCase != null) {
            map.put(BOMIndexerService.INDEXER_ATTR_PACKAGE_LOWERCASE,
                    packageNameInLowerCase);
        }
        IndexerItem item = new IndexerItemImpl(name, type, uri, map);
        list.add(item);
    }

    /**
     * @param resource
     * @return
     */
    protected WorkingCopy getWorkingCopy(IResource resource) {
        return XpdResourcesPlugin.getDefault().getWorkingCopy(resource);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#isAffectingEvent
     * (java.beans.PropertyChangeEvent)
     */
    @Override
    public boolean isAffectingEvent(PropertyChangeEvent event) {
        if (event != null
                && event.getNewValue() instanceof ResourceSetChangeEvent) {
            ResourceSetChangeEvent changeEvent =
                    (ResourceSetChangeEvent) event.getNewValue();
            List<?> notifications = changeEvent.getNotifications();
            for (Object object : notifications) {
                if (object instanceof Notification) {
                    Notification notification = (Notification) object;
                    int eventType = notification.getEventType();
                    Object feature = notification.getFeature();

                    Object container = null;
                    if (feature instanceof EReference) {
                        container = ((EReference) feature).getContainerClass();
                    }
                    Object notifier = notification.getNotifier();
                    Object newValue = notification.getNewValue();
                    boolean isName = false;
                    boolean isSet = false;

                    if (feature instanceof EAttribute) {
                        EAttribute att = (EAttribute) feature;
                        isName = att == UMLPackage.Literals.NAMED_ELEMENT__NAME;
                    }

                    switch (eventType) {
                    case Notification.ADD:
                        if (isInterestingObject(newValue)) {
                            return true;
                        }
                        break;
                    case Notification.ADD_MANY:
                        if (isInterestingObject(newValue)) {
                            return true;
                        }
                        break;
                    case Notification.MOVE:
                        if (isInterestingObject(newValue)) {
                            return true;
                        }
                        break;
                    case Notification.REMOVE:
                        if (isInterestingObject(container)) {
                            return true;
                        }
                        break;
                    case Notification.SET:
                        isSet = true;
                        break;
                    case Notification.UNSET:
                        isSet = true;
                        break;
                    }

                    if (notifier instanceof Class) {
                        if (isSet && isName) {
                            return true;
                        }
                    } else if (notifier instanceof Package) {
                        if (isSet && isName) {
                            return true;
                        }
                    } else if (notifier instanceof PrimitiveType) {
                        if (isSet && isName) {
                            return true;
                        }
                    } else if (notifier instanceof Enumeration) {
                        if (isSet && isName) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    /**
     * @return
     */
    boolean isInterestingObject(Object object) {
        if (object == Package.class) {
            return true;
        }
        if (object instanceof Class || object instanceof Package
                || object instanceof PrimitiveType
                || object instanceof Enumeration) {

            return true;
        }
        return false;
    }
}
