/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.indexing;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.bom.resources.ui.internal.indexing.BOMResourceIndexProvider;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.bom.resources.utils.ResourceItemType;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.WorkingCopyIndexProvider;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.complexdatatype.ComplexDataTypeReference;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process To BOM indexer provider.
 * 
 * @author mtorres
 * 
 */
public class ProcessToBOMIndexerProvider implements WorkingCopyIndexProvider {

    /**
     * @param wc
     * @param resource
     */
    public Collection<IndexerItem> getIndexItems(WorkingCopy wc) {
        //        System.out.println(getClass().getSimpleName() + "getIndexItems(): " //$NON-NLS-1$
        // + wc.getEclipseResources().get(0).getFullPath());

        ArrayList<IndexerItem> items = new ArrayList<IndexerItem>();
        String path = ProcessUIUtil.createPath(wc);
        IResource resource = wc.getEclipseResources().get(0);
        String projectName = null;
        if (resource != null && resource.getProject() != null) {
            projectName = resource.getProject().getName();
        }
        if (wc instanceof Xpdl2WorkingCopyImpl) {
            EObject root = wc.getRootElement();
            updateProcessIndex_2(items, projectName, path, root);
        } else if (wc instanceof BOMWorkingCopy) {
            if (BOMResourceIndexProvider.isInSpecialFolder(wc)) {
                EObject root = wc.getRootElement();
                updateBOMIndex_2(items, projectName, path, root);
            }
        }

        return items;
    }

    public enum ProcessToBomType {
        PROCESS, BOM, UNKNOWN
    }

    /**
     * 
     */
    private void updateProcessIndex_2(Collection<IndexerItem> items,
            String projectName, String path, EObject element) {
        if (element == null || element.eResource() == null) {
            return;
        }
        EList<EObject> list = element.eContents();
        Iterator<EObject> it = list.iterator();

        String processUriStr = ProcessUIUtil.getURIString(element, true);
        String processLocation = ProcessUIUtil.getURIString(element, false);
        if (element instanceof Process) {
            Process process = (Process) element;
            String id = process.getId();
            String name = ProcessUIUtil.getProcessQualifiedName(process);
            String display_name =
                    (String) Xpdl2ModelUtil.getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
            List<ExternalReference> externalReferences =
                    ProcessUIUtil.getBOMExternalReferences(element);
            if (externalReferences != null && !externalReferences.isEmpty()) {
                for (ExternalReference externalReference : externalReferences) {
                    if (externalReference != null) {
                        String uri =
                                ProcessUIUtil.getProcessToBOMURIString(element,
                                        externalReference);
                        if (Xpdl2ModelUtil.isPageflow(process)) {
                            String category =
                                    (String) Xpdl2ModelUtil
                                            .getOtherAttribute(process,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_BusinessServiceCategory());
                            createResourceItem(items,
                                    projectName,
                                    path,
                                    name,
                                    uri,
                                    ProcessResourceItemType.PAGEFLOW.toString(),
                                    id,
                                    ProcessToBomType.PROCESS.name(),
                                    display_name,
                                    category,
                                    processUriStr,
                                    processLocation,
                                    externalReference.getXref(),
                                    externalReference.getLocation(),
                                    externalReference.getNamespace());
                        } else {
                            createResourceItem(items,
                                    projectName,
                                    path,
                                    name,
                                    uri,
                                    ProcessResourceItemType.PROCESS.toString(),
                                    id,
                                    ProcessToBomType.PROCESS.name(),
                                    display_name,
                                    processUriStr,
                                    processLocation,
                                    externalReference.getXref(),
                                    externalReference.getLocation(),
                                    externalReference.getNamespace());
                        }
                    }
                }
            } else {
                createResourceItem(items,
                        null,
                        null,
                        null,
                        ProcessUIUtil.getURIString(element, true),
                        ProcessResourceItemType.PROCESS.toString(),
                        null,
                        ProcessToBomType.UNKNOWN.name(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
            }
        } else if (element instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) element;
            String id = processInterface.getId();
            String displayName =
                    (String) Xpdl2ModelUtil.getOtherAttribute(processInterface,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
            String name =
                    ProcessUIUtil
                            .getProcessInterfaceQualifiedName(processInterface);

            List<ExternalReference> externalReferences =
                    ProcessUIUtil.getBOMExternalReferences(element);
            if (externalReferences != null && !externalReferences.isEmpty()) {
                for (ExternalReference externalReference : externalReferences) {
                    if (externalReference != null) {
                        String uri =
                                ProcessUIUtil.getProcessToBOMURIString(element,
                                        externalReference);
                        createResourceItem(items,
                                projectName,
                                path,
                                name,
                                uri,
                                ProcessResourceItemType.PROCESSINTERFACE
                                        .toString(),
                                id,
                                ProcessToBomType.PROCESS.name(),
                                displayName,
                                processUriStr,
                                processLocation,
                                externalReference.getXref(),
                                externalReference.getLocation(),
                                externalReference.getNamespace());
                    }
                }
            } else {
                createResourceItem(items,
                        null,
                        null,
                        null,
                        ProcessUIUtil.getURIString(element, true),
                        ProcessResourceItemType.PROCESSINTERFACE.toString(),
                        null,
                        ProcessToBomType.UNKNOWN.name(),
                        null,
                        null,
                        processLocation,
                        null,
                        null,
                        null);
            }
        } else if (element instanceof Package) {
            Package processPackage = (Package) element;
            String id = processPackage.getId();
            String name = processPackage.getName();
            String displayName =
                    (String) Xpdl2ModelUtil.getOtherAttribute(processPackage,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
            List<ExternalReference> externalReferences =
                    ProcessUIUtil.getBOMExternalReferences(element);
            if (externalReferences != null && !externalReferences.isEmpty()) {
                for (ExternalReference externalReference : externalReferences) {
                    if (externalReference != null) {
                        String uri =
                                ProcessUIUtil.getProcessToBOMURIString(element,
                                        externalReference);
                        createResourceItem(items,
                                projectName,
                                path,
                                name,
                                uri,
                                ProcessResourceItemType.PROCESSPACKAGE
                                        .toString(),
                                id,
                                ProcessToBomType.PROCESS.name(),
                                displayName,
                                processUriStr,
                                processLocation,
                                externalReference.getXref(),
                                externalReference.getLocation(),
                                externalReference.getNamespace());
                    }
                }
            } else {
                createResourceItem(items,
                        null,
                        null,
                        null,
                        ProcessUIUtil.getURIString(element, true),
                        ProcessResourceItemType.PROCESSPACKAGE.toString(),
                        null,
                        ProcessToBomType.UNKNOWN.name(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
            }
        }

        while (it.hasNext()) {
            updateProcessIndex_2(items, projectName, path, it.next());
        }
    }

    /**
     * internally used method to parse the EObject model
     */
    private void updateBOMIndex_2(Collection<IndexerItem> items,
            String projectName, String path, EObject element) {
        if (element == null) {
            return;
        }
        EList<EObject> list = element.eContents();
        Iterator<EObject> it = list.iterator();
        String uri = BomUIUtil.getURI(element);
        if (element instanceof Class) {

            ComplexDataTypeReference complexDataTypeReferenceProcess =
                    ProcessUIUtil.resolveComplexDataTypeReference(element,
                            WorkingCopyUtil.getProjectFor(element));
            if (complexDataTypeReferenceProcess != null) {
                String name =
                        BOMWorkingCopy.getQualifiedClassName((Class) element);
                String type = ResourceItemType.CLASS.toString();
                String processToBomType = ProcessToBomType.BOM.name();
                String item_id = complexDataTypeReferenceProcess.getXRef();
                String bomRefId = complexDataTypeReferenceProcess.getXRef();
                String bomLocation =
                        complexDataTypeReferenceProcess.getLocation();
                String bomNamespace =
                        complexDataTypeReferenceProcess.getNameSpace();
                createResourceItem(items,
                        projectName,
                        path,
                        name,
                        uri,
                        type,
                        item_id,
                        processToBomType,
                        null,
                        null,
                        null,
                        bomRefId,
                        bomLocation,
                        bomNamespace);
            }
        }
        if (element instanceof PrimitiveType) {

            ComplexDataTypeReference complexDataTypeReferenceProcess =
                    ProcessUIUtil.resolveComplexDataTypeReference(element,
                            WorkingCopyUtil.getProjectFor(element));
            if (complexDataTypeReferenceProcess != null) {
                String name =
                        BOMWorkingCopy
                                .getQualifiedClassName((PrimitiveType) element);
                String type = ResourceItemType.PRIMITIVE_TYPE.toString();
                String processToBomType = ProcessToBomType.BOM.name();
                String item_id = complexDataTypeReferenceProcess.getXRef();
                String bomRefId = complexDataTypeReferenceProcess.getXRef();
                String bomLocation =
                        complexDataTypeReferenceProcess.getLocation();
                String bomNamespace =
                        complexDataTypeReferenceProcess.getNameSpace();
                createResourceItem(items,
                        projectName,
                        path,
                        name,
                        uri,
                        type,
                        item_id,
                        processToBomType,
                        null,
                        null,
                        null,
                        bomRefId,
                        bomLocation,
                        bomNamespace);
            }
        } else if (element instanceof Enumeration) {	// XPD=715
            ComplexDataTypeReference complexDataTypeReference =
                    ProcessUIUtil.resolveComplexDataTypeReference(element,
                            WorkingCopyUtil.getProjectFor(element));
            if (null != complexDataTypeReference) {
                String name =
                        BOMWorkingCopy
                                .getQualifiedClassName((Enumeration) element);
                String type = ResourceItemType.ENUMERATION.toString();
                String processToBomType = ProcessToBomType.BOM.name();
                String item_id = complexDataTypeReference.getXRef();
                String bomRefId = complexDataTypeReference.getXRef();
                String bomLocation = complexDataTypeReference.getLocation();
                String bomNamespace = complexDataTypeReference.getNameSpace();
                createResourceItem(items,
                        projectName,
                        path,
                        name,
                        uri,
                        type,
                        item_id,
                        processToBomType,
                        null,
                        null,
                        null,
                        bomRefId,
                        bomLocation,
                        bomNamespace);
            }
        }

        while (it.hasNext()) {
            updateBOMIndex_2(items, projectName, path, it.next());
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
            String path, String name, String uri, String type, String item_id,
            String processToBomType, String display_name, String processUriStr,
            String processLocation, String bomRefId, String bomLocation,
            String bomNamespace) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(IndexerServiceImpl.ATTRIBUTE_PROJECT, projectName);
        map.put(IndexerServiceImpl.ATTRIBUTE_PATH, path);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, item_id);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                processToBomType);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME, display_name);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_URI, processUriStr);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION,
                processLocation);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID, bomRefId);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION, bomLocation);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE, bomNamespace);

        IndexerItem item = new IndexerItemImpl(name, type, uri, map);
        list.add(item);
    }

    /**
     * create an resource item entry
     * 
     * @param name
     * @param uri
     * @param type
     */
    void createResourceItem(Collection<IndexerItem> list, String projectName,
            String path, String name, String uri, String type, String item_id,
            String processToBomType, String display_name, String category,
            String processUriStr, String processLocation, String bomRefId,
            String bomLocation, String bomNamespace) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(IndexerServiceImpl.ATTRIBUTE_PROJECT, projectName);
        map.put(IndexerServiceImpl.ATTRIBUTE_PATH, path);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, item_id);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_TO_BOM_TYPE,
                processToBomType);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME, display_name);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_CATEGORY, category);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_URI, processUriStr);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_PROCESS_LOCATION,
                processLocation);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_REFID, bomRefId);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_LOCATION, bomLocation);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_BOM_NAMESPACE, bomNamespace);

        IndexerItem item = new IndexerItemImpl(name, type, uri, map);
        list.add(item);
    }

    /**
     * @param projectName
     * @param path
     * @return
     */
    public static String createPath(String projectName, String path) {
        return projectName + "_" + path; //$NON-NLS-1$
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

                    boolean isInterestingSetOrUnset = true;
                    boolean isSet = false;
                    // Check if we are setting or unsetting the property we are
                    // interested in
                    // if(feature instanceof EReference){
                    // EReference ref = (EReference)feature;
                    // TODO: We should filter here the set and unset activities
                    // }

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
                        if (isInterestingSetOrUnset
                                && isInterestingObject(notifier)) {
                            isAffectingEvent = true;
                        }
                        break;
                    case Notification.UNSET:
                        if (isInterestingSetOrUnset
                                && isInterestingObject(notifier)) {
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
     * Filter the objects that we are interested in
     * 
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

        if (o instanceof Process || o instanceof ProcessInterface
                || o instanceof Package) {
            return true;
        }

        if (o == Process.class || o == ProcessInterface.class
                || o == Package.class) {
            return true;
        }

        if (o instanceof ProcessRelevantData) {
            return true;
        }

        if (o == ProcessRelevantData.class) {
            return true;
        }

        if (o instanceof Class) {
            return true;
        }

        if (o == Class.class) {
            return true;
        }

        if (o instanceof PrimitiveType) {
            return true;
        }

        if (o == PrimitiveType.class) {
            return true;
        }

        if (o instanceof org.eclipse.uml2.uml.Package) {
            return true;
        }

        if (o == org.eclipse.uml2.uml.Package.class) {
            return true;
        }

        return false;
    }

}
