package com.tibco.xpd.wm.tasklibrary.editor.indexing;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorContstants;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.wm.tasklibrary.editor.resources.TaskLibraryFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.provider.Xpdl2EditPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Indexer for task libraries.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryResourceIndexProvider implements
        WorkingCopyIndexProvider {

    private static final String TASK_LIBRARIES_INDEX_CONTAINERFOLDER =
            "taskLibraries"; //$NON-NLS-1$

    private static final String TASKS_INDEX_CONTAINERFOLDER = "tasks"; //$NON-NLS-1$

    private static final String TASKSETS_INDEX_CONTAINERFOLDER = "taskSets"; //$NON-NLS-1$

    /**
     * @param wc
     * @param resource
     */
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        ArrayList<IndexerItem> items = new ArrayList<IndexerItem>();

        String path = ProcessUIUtil.createPath(wc);

        IResource resource = wc.getEclipseResources().get(0);

        Process taskLibrary = TaskLibraryFactory.INSTANCE.getTaskLibrary(wc);

        updateIndex_2(items, resource.getProject().getName(), path, taskLibrary);

        return items;
    }

    /**
     * 
     */
    private void updateIndex_2(Collection<IndexerItem> items,
            String projectName, String path, EObject element) {
        if (element == null) {
            return;
        }

        List interestingChildren = null;

        String uri = ProcessUIUtil.getURIString(element, true);

        if (element instanceof Process) {
            //
            // Add index for Task Library.
            Process taskLibrary = (Process) element;
            String id = taskLibrary.getId();
            String name =
                    ProcessUIUtil.getQualifiedName(taskLibrary,
                            TASK_LIBRARIES_INDEX_CONTAINERFOLDER);

            String display_name =
                    Xpdl2ModelUtil.getDisplayNameOrName(taskLibrary);
            URI imageURI =
                    URI
                            .createPlatformPluginURI(TaskLibraryEditorPlugin.PLUGIN_ID
                                    + "/" //$NON-NLS-1$
                                    + TaskLibraryEditorContstants.ICON_TASKLIBRARY,
                                    true);
            createResourceItem(items,
                    projectName,
                    path,
                    name,
                    uri,
                    Xpdl2ResourcesPlugin.TASK_LIBRARY_INDEX_TYPE,
                    imageURI.toString(),
                    id,
                    display_name);

            // Recurs to do lanes (task sets) and activities.
            interestingChildren = new ArrayList<Object>();
            interestingChildren.addAll(Xpdl2ModelUtil
                    .getProcessLanes(taskLibrary));
            interestingChildren.addAll(Xpdl2ModelUtil
                    .getAllActivitiesInProc(taskLibrary));

        } else if (element instanceof Lane) {
            //
            // Index Task Sets.
            Lane taskSet = (Lane) element;

            String id = taskSet.getId();
            String name =
                    ProcessUIUtil.getQualifiedName(taskSet,
                            TASKSETS_INDEX_CONTAINERFOLDER);

            String display_name = Xpdl2ModelUtil.getDisplayNameOrName(taskSet);
            URI imageURI =
                    URI
                            .createPlatformPluginURI(TaskLibraryEditorPlugin.PLUGIN_ID
                                    + "/" //$NON-NLS-1$
                                    + TaskLibraryEditorContstants.IMG_TASKSET,
                                    true);
            createResourceItem(items,
                    projectName,
                    path,
                    name,
                    uri,
                    Xpdl2ResourcesPlugin.TASK_LIBRARY_TASKSET_INDEX_TYPE,
                    imageURI.toString(),
                    id,
                    display_name);

        } else if (element instanceof Activity) {

            // 
            // Index tasks in library.
            Activity activity = (Activity) element;

            String id = activity.getId();
            String name =
                    ProcessUIUtil.getQualifiedName(activity,
                            TASKS_INDEX_CONTAINERFOLDER);

            String display_name = Xpdl2ModelUtil.getDisplayNameOrName(activity);
            URI imageURI =
                    URI.createPlatformPluginURI(Xpdl2EditPlugin.PLUGIN_ID
                            + "/icons/" //$NON-NLS-1$
                            + Xpdl2ModelUtil.getActivityImagePath(activity),
                            true);
            createResourceItem(items,
                    projectName,
                    path,
                    name,
                    uri,
                    Xpdl2ResourcesPlugin.TASK_LIBRARY_TASK_INDEX_TYPE,
                    imageURI.toString(),
                    id,
                    display_name);

        } else if (element instanceof Package) {
            // Only interested in stuff under package
            interestingChildren = ((Package) element).getProcesses();
        }

        //
        // Recurs thru the object's children.
        if (interestingChildren != null) {
            for (Iterator iterator = interestingChildren.iterator(); iterator
                    .hasNext();) {
                EObject eo = (EObject) iterator.next();

                updateIndex_2(items, projectName, path, eo);
            }
        }
    }

    /**
     * create an resource item entry
     * 
     * @param name
     * @param uri
     * @param type
     */
    void createResourceItem(Collection<IndexerItem> list, String projectName,
            String path, String name, String uri, String type, String imageURI,
            String item_id, String display_name) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(IndexerServiceImpl.ATTRIBUTE_PROJECT, projectName);
        map.put(IndexerServiceImpl.ATTRIBUTE_PATH, path);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_IMAGE_URI, imageURI);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, item_id);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME, display_name);

        IndexerItem item = new IndexerItemImpl(name, type, uri, map);
        list.add(item);
    }

    public boolean isAffectingEvent(PropertyChangeEvent event) {
        boolean isAffectingEvent = false;

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

                    boolean isNameOrId = false;
                    boolean isSet = false;

                    if (feature instanceof EAttribute) {
                        EAttribute att = (EAttribute) feature;
                        if (att == Xpdl2Package.eINSTANCE
                                .getNamedElement_Name()
                                || att == XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName()
                                || att == Xpdl2Package.eINSTANCE
                                        .getUniqueIdElement_Id()
                                || att == XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_BusinessServiceCategory()) {
                            isNameOrId = true;
                        }
                    }

                    switch (eventType) {
                    case Notification.ADD:
                        if (isInterestingObject(newValue)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.ADD_MANY:
                        if (isInterestingObject(newValue)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.MOVE:
                        if (isInterestingObject(newValue)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.REMOVE:
                        if (isInterestingObject(container)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.SET:
                        if (isNameOrId && isInterestingObject(notifier)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.UNSET:
                        if (isNameOrId && isInterestingObject(notifier)) {
                            isAffectingEvent = true;
                        }
                        break;
                    }

                }

                if (isAffectingEvent) {
                    break;
                }
            }
        }

        return isAffectingEvent;
    }

    /**
     * @param newValue
     * @return
     */
    private boolean isInterestingObject(Object o) {
        if (o instanceof Collection) {
            for (Object subO : ((Collection) o)) {
                if (isInterestingObject(subO)) {
                    return true;
                }
            }
        }

        if (o instanceof Process || o instanceof Package
                || o instanceof Activity || o instanceof Lane) {
            return true;
        }

        if (o == Process.class || o == Package.class || o == Activity.class
                || o == FlowContainer.class || o == Lane.class) {
            return true;
        }

        return false;
    }
}
