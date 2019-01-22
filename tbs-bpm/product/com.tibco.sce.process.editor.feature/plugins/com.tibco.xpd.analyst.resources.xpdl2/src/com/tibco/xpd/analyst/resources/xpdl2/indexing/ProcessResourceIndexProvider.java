package com.tibco.xpd.analyst.resources.xpdl2.indexing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.BasicFeatureMapEntry;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class ProcessResourceIndexProvider extends
        AbstractXpdl2ResourceIndexProvider {

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.analyst.resources.xpdl2.indexing.
     * AbstractXpdl2ResourceIndexProvider
     * #addIndexedItemsForPackage(java.util.ArrayList, java.lang.String,
     * java.lang.String, com.tibco.xpd.xpdl2.Package)
     */
    @Override
    protected void addIndexedItemsForPackage(
            ArrayList<IndexerItem> indexedItems, String projectName,
            String xpdlPath, Package processPackage) {

        /*
         * Index the package itself.
         */
        String id = processPackage.getId();

        String name = processPackage.getName();

        String displayName =
                (String) Xpdl2ModelUtil.getOtherAttribute(processPackage,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName());

        String uri = ProcessUIUtil.getURIString(processPackage, true);

        URI imageURI =
                URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID + "/" //$NON-NLS-1$
                        + Xpdl2UiPlugin.IMG_PROCESSPACKAGE, true);

        createResourceItem(indexedItems,
                projectName,
                xpdlPath,
                name,
                uri,
                ProcessResourceItemType.PROCESSPACKAGE.toString(),
                imageURI.toString(),
                id,
                displayName);

        /*
         * Index the processes
         */
        for (Process process : processPackage.getProcesses()) {
            indexProcess(process, projectName, xpdlPath, indexedItems);
        }

        /*
         * Index the process interfaces.
         */
        ProcessInterfaces processInterfaces =
                ProcessInterfaceUtil.getProcessInterfaces(processPackage);
        if (processInterfaces != null) {
            for (ProcessInterface processInterface : processInterfaces
                    .getProcessInterface()) {
                indexProcessInterface(processInterface,
                        projectName,
                        xpdlPath,
                        indexedItems);
            }
        }

        return;
    }

    /**
     * Index the given process.
     * 
     * @param process
     * @param projectName
     * @param xpdlPath
     * @param uri
     * @param indexedItems
     */
    private void indexProcess(Process process, String projectName,
            String xpdlPath, Collection<IndexerItem> indexedItems) {
        String id = process.getId();

        String name = ProcessUIUtil.getProcessQualifiedName(process);

        String display_name =
                (String) Xpdl2ModelUtil.getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName());

        String uri = ProcessUIUtil.getURIString(process, true);
        URI imageURI = null;
        if (Xpdl2ModelUtil.isCaseService(process)) {

            imageURI =
                    URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID + "/" //$NON-NLS-1$
                            + Xpdl2UiPlugin.IMG_CASE_SERVICE_PAGEFLOW_PROCESS,
                            true);
        } else if (Xpdl2ModelUtil.isPageflowBusinessService(process)) {
            imageURI =
                    URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID
                            + "/" //$NON-NLS-1$
                            + Xpdl2UiPlugin.IMG_BUSINESS_SERVICE_PAGEFLOW_PROCESS,
                            true);
        } else if (Xpdl2ModelUtil.isPageflow(process)) {
            imageURI =
                    URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID + "/" //$NON-NLS-1$
                            + Xpdl2UiPlugin.IMG_PAGEFLOW_PROCESS, true);
        }
        if (Xpdl2ModelUtil.isPageflow(process)
                || (Xpdl2ModelUtil.isPageflowBusinessService(process))) {

            String category =
                    (String) Xpdl2ModelUtil.getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BusinessServiceCategory());

            createResourceItem(indexedItems,
                    projectName,
                    xpdlPath,
                    name,
                    uri,
                    ProcessResourceItemType.PAGEFLOW.toString(),
                    imageURI.toString(),
                    id,
                    display_name,
                    category);
        } else if (Xpdl2ModelUtil.isServiceProcess(process)) {

            imageURI =
                    URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID + "/" //$NON-NLS-1$
                            + Xpdl2UiPlugin.IMG_SERVICE_PROCESS, true);
            createResourceItem(indexedItems,
                    projectName,
                    xpdlPath,
                    name,
                    uri,
                    ProcessResourceItemType.SERVICEPROCESS.toString(),
                    imageURI.toString(),
                    id,
                    display_name);
        } else {

            imageURI =
                    URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID + "/" //$NON-NLS-1$
                            + Xpdl2UiPlugin.IMG_BUSINESS_PROCESS, true);

            createResourceItem(indexedItems,
                    projectName,
                    xpdlPath,
                    name,
                    uri,
                    ProcessResourceItemType.PROCESS.toString(),
                    imageURI.toString(),
                    id,
                    display_name);
        }

        return;
    }

    /**
     * Index the given process interface.
     * 
     * @param processInterface
     * @param projectName
     * @param xpdlPath
     * @param uri
     * @param indexItems
     */
    private void indexProcessInterface(ProcessInterface processInterface,
            String projectName, String xpdlPath,
            Collection<IndexerItem> indexItems) {

        String id = processInterface.getId();

        String displayName =
                (String) Xpdl2ModelUtil.getOtherAttribute(processInterface,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName());
        String name =
                ProcessUIUtil
                        .getProcessInterfaceQualifiedName(processInterface);

        String uri = ProcessUIUtil.getURIString(processInterface, true);

        URI imageURI = null;

        if (Xpdl2ModelUtil.isServiceProcessInterface(processInterface)) {

            imageURI =
                    URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID + "/" //$NON-NLS-1$
                            + Xpdl2UiPlugin.IMG_SERVICEPROCESSINTERFACE, true);

            createResourceItem(indexItems,
                    projectName,
                    xpdlPath,
                    name,
                    uri,
                    ProcessResourceItemType.SERVICEPROCESSINTERFACE.toString(),
                    imageURI.toString(),
                    id,
                    displayName);
        } else {

            imageURI =
                    URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID + "/" //$NON-NLS-1$
                            + Xpdl2UiPlugin.IMG_PROCESSINTERFACE, true);
            createResourceItem(indexItems,
                    projectName,
                    xpdlPath,
                    name,
                    uri,
                    ProcessResourceItemType.PROCESSINTERFACE.toString(),
                    imageURI.toString(),
                    id,
                    displayName);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.analyst.resources.xpdl2.indexing.
     * AbstractXpdl2ResourceIndexProvider
     * #shouldReIndexForObject(java.lang.Object,
     * org.eclipse.emf.common.notify.Notification)
     */
    @Override
    protected boolean shouldReIndexForObject(Object o, Notification notification) {
        boolean shouldReIndex = false;
        if (o instanceof Process || o instanceof ProcessInterface
                || o instanceof Package || o instanceof ProcessInterfaces) {
            shouldReIndex = true;
        }

        if (o == Process.class || o == ProcessInterface.class
                || o == Package.class || o == ProcessInterfaces.class) {
            shouldReIndex = true;
        }

        if (shouldReIndex) {
            /*
             * For set's Only bother re-indexing if it's a chnage to something
             * we index!
             */
            if (Notification.SET == notification.getEventType()) {
                shouldReIndex = false;

                Object feature = notification.getFeature();
                if (feature instanceof EAttribute) {
                    EAttribute att = (EAttribute) feature;
                    if (att == Xpdl2Package.eINSTANCE.getNamedElement_Name()
                            || att == XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName()
                            || att == Xpdl2Package.eINSTANCE
                                    .getUniqueIdElement_Id()
                            || att == XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_BusinessServiceCategory()) {
                        shouldReIndex = true;
                    }
                }
            }
        }

        /*
         * Handle extension attributes add and remove (in this case the
         * feateuremapentry is the target rather than the owner process.
         */
        if (!shouldReIndex) {
            if (o instanceof BasicFeatureMapEntry) {
                EStructuralFeature feature =
                        ((BasicFeatureMapEntry) o).getEStructuralFeature();

                if (XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_XpdModelType().equals(feature)) {
                    /*
                     * If the type of process changes (by being added, removed
                     * or set) then we should always reindex
                     */
                    shouldReIndex = true;
                }
            }
        }

        return shouldReIndex;
    }

    /**
     * create an resource item entry
     * 
     * @param name
     * @param uri
     * @param type
     */
    private void createResourceItem(Collection<IndexerItem> list,
            String projectName, String path, String name, String uri,
            String type, String imageURI, String item_id, String display_name) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(IndexerServiceImpl.ATTRIBUTE_PROJECT, projectName);
        map.put(IndexerServiceImpl.ATTRIBUTE_PATH, path);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_IMAGE_URI, imageURI);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, item_id);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME, display_name);

        IndexerItem item = new IndexerItemImpl(name, type, uri, map);
        list.add(item);

        return;
    }

    /**
     * create an resource item entry
     * 
     * @param name
     * @param uri
     * @param type
     */
    private void createResourceItem(Collection<IndexerItem> list,
            String projectName, String path, String name, String uri,
            String type, String imageURI, String item_id, String display_name,
            String category) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(IndexerServiceImpl.ATTRIBUTE_PROJECT, projectName);
        map.put(IndexerServiceImpl.ATTRIBUTE_PATH, path);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_IMAGE_URI, imageURI);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, item_id);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME, display_name);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_CATEGORY, category);

        IndexerItem item = new IndexerItemImpl(name, type, uri, map);
        list.add(item);
    }

}
