package com.tibco.xpd.rest.schema.internal;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.SpecialFolders;

/**
 * Index provider for JSON Schema ("jsd") files. This caches the UML Class names
 * of all classes defined in the schema file.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class JsonSchemaIndexProvider implements WorkingCopyIndexProvider {

    /**
     * Index field to indicate if the class is a root class.
     */
    public static final String IS_ROOT = "isRoot"; //$NON-NLS-1$

    /**
     * Index field to contain the type ID.
     */
    public static final String TYPE_ID = "typeId"; //$NON-NLS-1$

    @Override
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        Collection<IndexerItem> items = new ArrayList<IndexerItem>();
        if (isInSpecialFolder(wc)) {
            EObject root = wc.getRootElement();
            if (root instanceof Package) {
                updateIndex(wc, items, (Package) root);
            }
        }
        return items;
    }

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
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return true if we are interested in the object that changed.
     */
    boolean isInterestingObject(Object object) {
        if (object == Package.class) {
            return true;
        }
        if (object instanceof Class || object instanceof Package
                || object instanceof PrimitiveType) {

            return true;
        }
        return false;
    }

    /**
     * @param wc
     *            The working copy.
     * @return true if is contained in a REST Special Folder.
     */
    private boolean isInSpecialFolder(WorkingCopy wc) {
        IResource eclipseResource = wc.getEclipseResources().get(0);
        ProjectConfig projectConfig =
                XpdResourcesPlugin.getDefault()
                        .getProjectConfig(eclipseResource.getProject());

        if (projectConfig != null) {
            SpecialFolders specialFolders = projectConfig.getSpecialFolders();
            SpecialFolder sf =
                    specialFolders.getFolderContainer(eclipseResource);
            return sf != null && "rest".equals(sf.getKind()); //$NON-NLS-1$
        }
        return false;
    }

    /**
     * Updates the REST class index to reflect changes.
     * 
     * @param wc
     *            The working copy.
     * @param items
     *            The index items to modify.
     * @param pkg
     *            The sourc epackage to update from.
     */
    private void updateIndex(WorkingCopy wc, Collection<IndexerItem> items,
            Package pkg) {
        EList<PackageableElement> elements = pkg.getPackagedElements();
        for (PackageableElement element : elements) {
            if (element instanceof Class) {
                Class cls = (Class) element;
                String name = element.getName();
                String type = Class.class.getName();
                String uri = getURI(element);
                String isRoot =
                        Boolean.toString(cls.getEAnnotation("root") != null); //$NON-NLS-1$
                Map<String, String> additionalAttributes = new HashMap<>();
                additionalAttributes.put(IS_ROOT, isRoot);
                additionalAttributes.put(TYPE_ID, element.eResource()
                        .getURIFragment(element));
                items.add(new IndexerItemImpl(name, type, uri,
                        additionalAttributes));
            }
        }
    }

    /**
     * @param modelElement
     *            The EMF model element.
     * @return The URI of the resource containing the element.
     */
    private String getURI(EObject modelElement) {
        Resource modelElementResource = modelElement.eResource();
        URI uri =
                modelElementResource
                        .getURI()
                        .appendFragment(modelElementResource.getURIFragment(modelElement));
        String uriToString = uri.toString();
        return uriToString;

    }
}
