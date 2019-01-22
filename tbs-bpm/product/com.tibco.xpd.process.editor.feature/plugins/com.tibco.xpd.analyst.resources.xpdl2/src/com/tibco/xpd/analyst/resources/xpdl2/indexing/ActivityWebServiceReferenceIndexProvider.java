/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.indexing;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityWebServiceReference;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Indexes references to Web Services from tasks and events in processes.
 * 
 * @author aallway
 * @since 3.3 (19 Feb 2010)
 */
public class ActivityWebServiceReferenceIndexProvider extends
        AbstractXpdl2ResourceIndexProvider {

    public static final String ACTIVITY_WEBSERVICE_REF_INDEX_TYPE =
            "ACTIVITY_WEBSERVICE_REFERENCE"; //$NON-NLS-1$

    public static final String ACTIVITY_WEBSERVICE_REF_INDEX_ID =
            "com.tibco.xpd.analyst.resources.xpdl2.indexing.webServiceReference"; //$NON-NLS-1$

    public static final String COLUMN_ACTIVITY_ID = "activity_id"; //$NON-NLS-1$

    public static final String COLUMN_WSDL_NAMESPACE = "wsdl_namespace"; //$NON-NLS-1$

    public static final String COLUMN_PORT_TYPE_NAMESPACE =
            "port_type_namespace"; //$NON-NLS-1$

    public static final String COLUMN_PORT_TYPE_NAME = "port_type_name"; //$NON-NLS-1$

    public static final String COLUMN_PORT_NAME = "port_name"; //$NON-NLS-1$

    public static final String COLUMN_OPERATION_NAME = "operation_name"; //$NON-NLS-1$

    public static final String COLUMN_WSDLFILE_LOCATION = "wsdlfile_location"; //$NON-NLS-1$

    public static final String COLUMN_TRANSPORT_TYPE = "transport_type"; //$NON-NLS-1$

    public static final String COLUMN_ENDPOINT_PARTICIPANT_ID =
            "endpoint_participant_id"; //$NON-NLS-1$

    public static final String COLUMN_ENDPOINT_PARTICIPANT_NAME =
            "endpoint_participant_name"; //$NON-NLS-1$

    public static final String COLUMN_IS_API_ACTIVITY = "is_api_activity"; //$NON-NLS-1$

    public static final String COLUMN_IS_BIZ_SERVICE_INVOKE =
            "is_biz_service_invoke"; //$NON-NLS-1$

    public ActivityWebServiceReferenceIndexProvider() {
        super();
    }

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
            String xpdlPath, Package pkg) {

        for (Process process : pkg.getProcesses()) {
            for (Activity activity : process.getActivities()) {
                IndexerItem item =
                        indexActivity(activity, projectName, xpdlPath);
                if (item != null) {
                    indexedItems.add(item);
                }
            }

            for (ActivitySet activitySet : process.getActivitySets()) {
                for (Activity activity : activitySet.getActivities()) {
                    IndexerItem item =
                            indexActivity(activity, projectName, xpdlPath);
                    if (item != null) {
                        indexedItems.add(item);
                    }
                }
            }
        }

        return;
    }

    /**
     * Create and add indexer
     * 
     * @param activity
     * @param projectName
     * @param xpdlPath
     * 
     * @return Index item for activity or null if none required.
     */
    private IndexerItem indexActivity(Activity activity, String projectName,
            String xpdlPath) {
        if (isWebServiceActivity(activity)) {
            return createActivityWebServiceReferenceIndexItem(activity);
        }
        return null;
    }

    /**
     * @param activity
     * @return true if the activity references a web-service operation.
     */
    private boolean isWebServiceActivity(Activity activity) {
        /*
         * If we can access the operation set in port type then it's a
         * web-related activity.
         */
        PortTypeOperation pto = Xpdl2ModelUtil.getPortTypeOperation(activity);
        if (pto != null) {
            String operation = pto.getOperationName();
            if (operation != null && operation.length() > 0) {
                return true;
            }
        }
        return false;
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
        // setLogNotifications(true);
        if (o instanceof Activity || o == Activity.class) {
            Activity activity = (Activity) o;

            /*
             * Only interested in whole Activity if it is being added or removed
             * AND it is setup as web-service related activity.
             */
            int notificationType = notification.getEventType();
            if (Notification.ADD == notificationType
                    || Notification.ADD_MANY == notificationType
                    || Notification.REMOVE == notificationType
                    || Notification.REMOVE_MANY == notificationType) {
                WebServiceOperation wso =
                        Xpdl2ModelUtil.getWebServiceOperation(activity);
                if (wso != null) {
                    return true;
                }

            } else if (Notification.SET == notificationType
                    && notification.getFeature() == XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_DisplayName()) {
                /* Activity name change. */
                return true;
            }

        } else if (isParticipantRelatedElement(notification.getNotifier())) {
            /*
             * Rebuild index if participant name changes as this is cached.
             */
            return true;

        } else if (isActivityWebServiceRelatedElement(notification
                .getNotifier())
                || isActivityWebServiceRelatedElement(notification
                        .getOldValue())
                || isActivityWebServiceRelatedElement(notification
                        .getNewValue())) {
            /*
             * If setting / adding or removing WebsServiceOperation or
             * PortTypeOperation (or any child thereof) then will need to do a
             * rebuild.
             */
            return true;
        }

        return false;
    }

    /**
     * @param element
     * 
     * @return true if the value is xpdl2:WebServiceOperation or
     *         xpdExt:PortTypeOperation or a child thereof.
     */
    private boolean isActivityWebServiceRelatedElement(Object element) {
        if (element instanceof EObject) {
            if (Xpdl2ModelUtil
                    .getAncestor((EObject) element, TaskService.class) != null) {
                return true;
            } else if (Xpdl2ModelUtil.getAncestor((EObject) element,
                    TaskSend.class) != null) {
                return true;
            } else if (Xpdl2ModelUtil.getAncestor((EObject) element,
                    TaskReceive.class) != null) {
                return true;
            } else if (Xpdl2ModelUtil.getAncestor((EObject) element,
                    TriggerResultMessage.class) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param element
     * 
     * @return true if the value is xpdl2:WebServiceOperation or
     *         xpdExt:PortTypeOperation or a child thereof.
     */
    private boolean isParticipantRelatedElement(Object element) {
        if (element instanceof EObject) {
            if (Xpdl2ModelUtil
                    .getAncestor((EObject) element, Participant.class) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create the indexer item for a activity reference to a wsdl operation.
     * 
     * @param activity
     * @param projectName
     * @param xpdlPath
     * 
     * @return the indexer item for a activity ref to wsdl operation.
     */
    private IndexerItem createActivityWebServiceReferenceIndexItem(
            Activity activity) {

        ActivityWebServiceReference webServiceRef =
                ActivityWebServiceReference
                        .createActivityWebServiceReference(activity);

        if (webServiceRef != null) {

            HashMap<String, String> map = new HashMap<String, String>();

            map.put(COLUMN_ACTIVITY_ID, webServiceRef.getActivityId());

            map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME,
                    webServiceRef.getDisplayName());

            map.put(COLUMN_WSDL_NAMESPACE, webServiceRef.getWsdlNamespace());

            map.put(COLUMN_PORT_TYPE_NAMESPACE,
                    webServiceRef.getPortTypeNamespace());

            map.put(COLUMN_PORT_TYPE_NAME, webServiceRef.getPortTypeName());

            map.put(COLUMN_PORT_NAME, webServiceRef.getPortName());

            map.put(COLUMN_OPERATION_NAME, webServiceRef.getOperation());

            map.put(COLUMN_TRANSPORT_TYPE, webServiceRef.getTransportType());

            map.put(COLUMN_WSDLFILE_LOCATION,
                    webServiceRef.getWsdlFileLocation());

            map.put(COLUMN_ENDPOINT_PARTICIPANT_ID,
                    webServiceRef.getParticipantId());

            map.put(COLUMN_ENDPOINT_PARTICIPANT_NAME,
                    webServiceRef.getParticipantName());

            map.put(COLUMN_IS_API_ACTIVITY, webServiceRef.getIsApiActivity()
                    .toString());

            map.put(COLUMN_IS_BIZ_SERVICE_INVOKE, webServiceRef
                    .getIsBusinessServiceInvoke().toString());

            IndexerItem item =
                    new IndexerItemImpl(
                            ProcessUIUtil.getActivityQualifiedName(activity),
                            ACTIVITY_WEBSERVICE_REF_INDEX_TYPE,
                            webServiceRef.getActivityUri(), map);

            return item;
        }

        return null;
    }
}
