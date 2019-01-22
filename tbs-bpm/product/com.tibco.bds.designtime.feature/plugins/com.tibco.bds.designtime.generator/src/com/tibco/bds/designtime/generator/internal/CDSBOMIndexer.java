/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.bds.designtime.generator.internal;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.bds.designtime.generator.BDSUtils;
import com.tibco.bds.designtime.generator.CDSBOMIndexerService;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.bom.types.api.BomTypesUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;
import com.tibco.xpd.resources.wc.NotificationPropertyChangeEvent;

/**
 * Indexer provider for CDS-specific information to be indexed for Business
 * Object Models.
 * 
 * @author njpatel
 * 
 */
public class CDSBOMIndexer implements WorkingCopyIndexProvider {

    public CDSBOMIndexer() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider#getIndexItems
     * (com.tibco.xpd.resources.WorkingCopy)
     */
    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        List<IndexerItem> items = new ArrayList<IndexerItem>();

        // Only index if the bom file is in the BOM special folder
        if (isInSpecialFolder(wc)) {
            EObject root = wc != null ? wc.getRootElement() : null;
            if (root instanceof Model) {
                IFile bomFile = (IFile) wc.getEclipseResources().get(0);
                boolean shouldGenerateBDSPlugin = willGenerateCDS(bomFile);
                if (shouldGenerateBDSPlugin) {
                    update(root, items);
                }
            }
        }
        return items;
    }

    /**
     * @param bomFile
     * @return <code>true</code>if there is anything in the given BOM File that
     *         would cause it to generate CDS content (factories etc)
     */
    public static boolean willGenerateCDS(IFile bomFile) {
        List<IFile> bomFileList = new ArrayList<IFile>();
        bomFileList.add(bomFile);

        boolean shouldGenerateBDSPlugin =
                BDSUtils.shouldGenerateBDSPlugin(bomFileList);

        return shouldGenerateBDSPlugin;
    }

    /**
     * Update the indexer with the given object. If this object is a
     * {@link Package} then this method will be called recursively for its
     * children.
     * 
     * @param eo
     * @param items
     */
    private void update(EObject eo, List<IndexerItem> items) {
        if (eo != null) {
            if (isObjectOfInterest(eo)) {
                IndexerItem item = getIndexerItem((NamedElement) eo);
                if (item != null) {
                    items.add(item);
                }

                // Index all (association) properties
                if (eo instanceof Class) {
                    updateProperties((Class) eo, items);
                }
            }

            // Only interested in children of Packages
            if (eo instanceof Package) {
                for (EObject content : eo.eContents()) {
                    update(content, items);
                }
            }
        }
    }

    /**
     * Store information on associations from the given class.
     * 
     * @param cl
     * @param items
     */
    private void updateProperties(Class cl, List<IndexerItem> items) {
        String parentUri = EcoreUtil.getURI(cl).toString();
        for (Property prop : cl.getOwnedAttributes()) {
            if (prop.getType() instanceof Class) {
                IndexerItemImpl item =
                        (IndexerItemImpl) getIndexerItem(prop.getType());
                // Store the class URI as the reference to the class
                if (item != null) {
                    item.set(CDSBOMIndexerService.ASSOCIATION, item.getURI());
                    item.set(CDSBOMIndexerService.PARENT, parentUri);
                    item.setUri(EcoreUtil.getURI(prop).toString());
                    item.setName(prop.getName());
                    item.setType("Property"); //$NON-NLS-1$
                    items.add(item);
                }
            } else if (BomTypesUtil.isPropertyXsdAnyType(prop)) {
                IndexerItemImpl item = (IndexerItemImpl) getIndexerItem(prop);
                if (item != null) {
                    item.set(CDSBOMIndexerService.PARENT, parentUri);
                    item.setUri(EcoreUtil.getURI(prop).toString());
                    item.setName(prop.getName());
                    item.setType("Property_XsdAnyType"); //$NON-NLS-1$
                    items.add(item);
                }
            }
        }
    }

    /**
     * Generate a factory name for the given package.
     * 
     * @param pkg
     * @return
     */
    private String getFactoryName(Package pkg) {
        if (pkg != null) {
            String name = pkg.getName();
            if (pkg instanceof Model && name.indexOf('.') > 0) {
                // Get the last segment from the qualified name
                name = name.substring(name.lastIndexOf('.') + 1);
            }
            return capitalize(name) + "Factory"; //$NON-NLS-1$
        }
        return null;
    }

    /**
     * Get the indexer item for the given {@link NamedElement}.
     * 
     * @param element
     * @return
     */
    private IndexerItem getIndexerItem(NamedElement element) {
        IndexerItemImpl item = null;

        String uri = EcoreUtil.getURI(element).toString();
        Map<String, String> info = new HashMap<String, String>();
        Package pkg = element.getNearestPackage();
        if (pkg != null) {
            String qualifiedName = pkg.getQualifiedName();
            if (qualifiedName != null) {
                qualifiedName = BOMWorkingCopy.getQualifiedName(qualifiedName);
                info.put(CDSBOMIndexerService.PACKAGE_NAME, qualifiedName);
                info.put(CDSBOMIndexerService.FACTORY_NAME, getFactoryName(pkg));
                String typeName = null;
                if (element instanceof Package) {
                    String namespaceUri = getNamespaceUri((Package) element);
                    if (namespaceUri != null) {
                        info.put(CDSBOMIndexerService.INDEXER_ATTR_NAMESPACE_URI,
                                namespaceUri);
                    }
                    // hardcoding model name to be package name
                    typeName = CDSBOMIndexerService.PACKAGE_LITERAL;
                    String fullyQualifiedName =
                            BOMWorkingCopy
                                    .getQualifiedPackageName((Package) element);
                    // storing fully qualified package name
                    info.put(CDSBOMIndexerService.QUALIFIED_NAME,
                            fullyQualifiedName);
                } else if (element instanceof Enumeration) {
                    Enumeration en = (Enumeration) element;
                    String enumFullyQualifiedName =
                            BOMWorkingCopy.getQualifiedName(en
                                    .getQualifiedName());
                    info.put(CDSBOMIndexerService.QUALIFIED_NAME,
                            enumFullyQualifiedName);
                }
                if (typeName == null) {
                    typeName = element.eClass().getName();
                }

                // storing alias for CDS factory for the package
                info.put(CDSBOMIndexerService.CDS_FACTORY_NAME,
                        getQualifiedFactoryName(pkg));

                item =
                        new IndexerItemImpl(element.getName(), typeName, uri,
                                info);
            }

        }
        return item;
    }

    /**
     * Get the CDS factory name that <i>would</i> be generated for the given
     * package name <i>if</i> that package were to have CDS factory generated.
     * <p>
     * Unlike {@link #getCDSFactoryForPackage(Package)} it <b>does not make any
     * judgement as to whether the package will generate a CDS factory or
     * not.</b>
     * </p>
     * 
     * @param packageName
     * @return The CDS factory name.
     */
    public static String getQualifiedFactoryName(Package pkg) {
        String fullyQualifiedName = BOMWorkingCopy.getQualifiedPackageName(pkg);
        return getCDSFactoryName(fullyQualifiedName);
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

        if (event instanceof NotificationPropertyChangeEvent) {
            Collection<Notification> notifications =
                    ((NotificationPropertyChangeEvent) event)
                            .getNotifications();

            /*
             * Interested in Package, Class and Enumeration. Only interested in
             * name change on either of these objects or add/remove of these
             * objects
             */
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    switch (notification.getEventType()) {
                    case Notification.ADD:
                        if (notification.getFeature() == UMLPackage.eINSTANCE
                                .getPackage_PackagedElement()) {
                            if (notification.getNewValue() instanceof Association
                                    || isObjectOfInterest(notification
                                            .getNewValue())) {
                                return true;
                            }
                        }
                        break;
                    case Notification.REMOVE:
                        if (notification.getFeature() == UMLPackage.eINSTANCE
                                .getPackage_PackagedElement()) {
                            if (notification.getOldValue() instanceof Association
                                    || isObjectOfInterest(notification
                                            .getOldValue())) {
                                return true;
                            }
                        } else if (notification.getFeature() == UMLPackage.eINSTANCE
                                .getStructuredClassifier_OwnedAttribute()
                                && notification.getOldValue() instanceof Property) {
                            if (((Property) notification.getOldValue())
                                    .getType() instanceof Class) {
                                // Removed an attribute that has a class as the
                                // type
                                return true;
                            }
                        }
                        break;

                    case Notification.SET:
                        if (notification.getFeature() == UMLPackage.eINSTANCE
                                .getNamedElement_Name()) {
                            if (isObjectOfInterest(notification.getNotifier())) {
                                return true;
                            }
                        } else if (notification.getFeature() == UMLPackage.eINSTANCE
                                .getTypedElement_Type()) {
                            /*
                             * If the type of a property has changed to/from a
                             * Class then re-index
                             */
                            if (notification.getNewValue() instanceof Class
                                    || notification.getOldValue() instanceof Class) {
                                return true;
                            }
                        } else if (notification.getFeature() == UMLPackage.eINSTANCE
                                .getProperty_OwningAssociation()) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if this object should be added to the indexer.
     * 
     * @param obj
     * @return <code>true</code> if the object is a Package (that contains a
     *         Class, PrimitiveType or Enumeration), Class or Enumeration.
     */
    private boolean isObjectOfInterest(Object obj) {

        if (obj instanceof Package) {
            return hasCDSContent((Package) obj);
        }

        return obj instanceof Class || obj instanceof Enumeration;
    }

    /**
     * If the Package contains either a Class, Primitive Type or Enumeration
     * then CDS factories etc will exist for it.
     * 
     * @param pkg
     * 
     * @return <code>true</code> if the given package has CDS content.
     */
    public static boolean hasCDSContent(Package pkg) {
        /*
         * If the object is a Package then check if this Package contains either
         * a Class, Primitive Type or Enumeration, otherwise we don't need to
         * store the package information in the indexer.
         */
        for (EObject child : pkg.getPackagedElements()) {
            if (child instanceof Class || child instanceof Enumeration
                    || child instanceof PrimitiveType) {
                return true;
            }
        }

        /* Package has nothing of interest so don't index it */
        return false;
    }

    /**
     * Capitalize the given string.
     * 
     * @param name
     * @return
     */
    private String capitalize(String name) {
        if (name != null && name.length() > 0) {
            name = name.substring(0, 1).toUpperCase().concat(name.substring(1));
        }
        return name;
    }

    /**
     * Check if the given WorkingCopy comes from a resource in the BOM special
     * folder.
     * 
     * @param wc
     * @return
     */
    private boolean isInSpecialFolder(WorkingCopy wc) {
        if (wc != null) {
            IResource res = wc.getEclipseResources().get(0);
            if (res != null) {
                ProjectConfig projectConfig =
                        XpdResourcesPlugin.getDefault()
                                .getProjectConfig(res.getProject());

                if (projectConfig != null) {
                    SpecialFolders specialFolders =
                            projectConfig.getSpecialFolders();
                    SpecialFolder sf = specialFolders.getFolderContainer(res);
                    return sf != null
                            && BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND
                                    .equals(sf.getKind());
                }
            }
        }
        return false;
    }

    /**
     * Get the namespace URI to store for the given package.
     * 
     * @param pkg
     * @return
     */
    public static String getNamespaceUri(Package pkg) {
        return BOMUtils.getNamespace(pkg, true);
    }

    /**
     * Get the CDS factory name that <i>would</i> be generated for the given
     * package name <i>if</i> that package were to have CDS factory generated.
     * <p>
     * Unlike {@link #getCDSFactoryForPackage(Package)} it <b>does not make any
     * judgement as to whether the package will generate a CDS factory or
     * not.</b>
     * </p>
     * 
     * @param packageName
     * @return The CDS factory name.
     */
    public static String getCDSFactoryName(String packageName) {
        String factoryName = null;

        if (packageName != null) {
            factoryName = packageName.replaceAll("\\.", "_").concat("_Factory"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        return factoryName;
    }

}
