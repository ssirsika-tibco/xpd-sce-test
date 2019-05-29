/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.indexing;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Indexer for process package / process participants.
 * 
 * @author aallway
 * @since 3.3 (18 Jan 2010)
 */
public class ProcessParticipantResourceIndexProvider extends
        AbstractXpdl2ResourceIndexProvider {

    public static final String PROCESS_PARTICIPANT_INDEX_TYPE =
            "PROCESS_PARTICIPANT"; //$NON-NLS-1$

    public static final String PROCESS_PARTICIPANT_INDEXER_ID =
            "com.tibco.xpd.analyst.resources.xpdl2.indexing.participantIndexer"; //$NON-NLS-1$

    /**
     * Attribute to store shared resource type of the participant.
     */
    public static final String ATTRIBUTE_SHARED_RESOURCE_TYPE =
            "sharedResourceType"; //$NON-NLS-1$

    /**
     * Attribute to store shared resource end point name of the participant.
     */
    public static final String ATTRIBUTE_ENDPOINT_NAME = "endPointName"; //$NON-NLS-1$

    /**
     * Attribute to store shared resource end point ID of the participant.
     */
    public static final String ATTRIBUTE_ENDPOINT_ID = "endPointId"; //$NON-NLS-1$

    public ProcessParticipantResourceIndexProvider() {
        super();
    }

    public enum ResourceType {
        EMAIL(Messages.SharedResourcesSection_EmailEnum_button,
                XpdExtensionPackage.eINSTANCE
                        .getParticipantSharedResource_Email(),
                ""), // //$NON-NLS-1$

        REST_SERVICE(Messages.SharedResourcesSection_RestServiceEnum_button,
                XpdExtensionPackage.eINSTANCE
                        .getParticipantSharedResource_RestService(),
                "REST Service invocation shared resource."), //$NON-NLS-1$

        JDBC(Messages.SharedResourcesSection_JdbcEnum_button,
                XpdExtensionPackage.eINSTANCE
                        .getParticipantSharedResource_Jdbc(),
                ""); // //$NON-NLS-1$

        private final String label;

        private final EStructuralFeature feature;

        private final String description;

        private ResourceType(String label, EStructuralFeature feature,
                String description) {
            this.label = label;
            this.feature = feature;
            this.description = description;
        }

        /**
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        public EStructuralFeature getFeature() {
            return feature;
        }

        @Override
        public String toString() {
            return label;
        }

        public static ResourceType getByFeature(EStructuralFeature feature) {
            for (ResourceType r : ResourceType.values()) {
                if (r.feature == feature) {
                    return r;
                }
            }
            throw new IllegalArgumentException("Incorrect feature: " + feature); //$NON-NLS-1$
        }
    };

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

        /* Add package participants. */
        for (Participant participant : pkg.getParticipants()) {
            indexedItems.add(createParticipantIndexItem(participant,
                    projectName,
                    xpdlPath));
        }

        /* Add process participants. */
        for (Process process : pkg.getProcesses()) {
            for (Participant participant : process.getParticipants()) {
                indexedItems.add(createParticipantIndexItem(participant,
                        projectName,
                        xpdlPath));
            }
        }

        return;
    }

    /**
     * Create the indexer item for a participant.
     * 
     * @param participant
     * @param projectName
     * @param xpdlPath
     * 
     * @return the indexer item for a participant.
     */
    private IndexerItem createParticipantIndexItem(Participant participant,
            String projectName, String xpdlPath) {

        String id = participant.getId();

        String name = ProcessUIUtil.getParticipantQualifiedName(participant);

        String display_name =
                (String) Xpdl2ModelUtil.getOtherAttribute(participant,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_DisplayName());

        String uri = ProcessUIUtil.getURIString(participant, true);

        URI imageURI =
                URI.createPlatformPluginURI(Xpdl2UiPlugin.PLUGIN_ID + "/" //$NON-NLS-1$
                        + Xpdl2UiPlugin.IMG_PARTICIPANT, true);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(IndexerServiceImpl.ATTRIBUTE_PROJECT, projectName);
        map.put(IndexerServiceImpl.ATTRIBUTE_PATH, xpdlPath);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_IMAGE_URI, imageURI.toString());
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_ITEM_ID, id);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME, display_name);
        map.put(Xpdl2ResourcesPlugin.ATTRIBUTE_NAME, participant.getName());

        Object psrObj = Xpdl2ModelUtil.getOtherElement(participant,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ParticipantSharedResource());

        if (psrObj instanceof ParticipantSharedResource) {
            ParticipantSharedResource psr = (ParticipantSharedResource) psrObj;

            if (null != psr.getRestService()) {
                map.put(ATTRIBUTE_SHARED_RESOURCE_TYPE,
                        ResourceType.REST_SERVICE.toString());

                map.put(ATTRIBUTE_ENDPOINT_NAME,
                        psr.getRestService().getResourceName());
            }
        }
        
        //type and additional info (endpoint id name), extension point contribution for the indexer.

        IndexerItem item =
                new IndexerItemImpl(name, PROCESS_PARTICIPANT_INDEX_TYPE, uri,
                        map);

        return item;
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
        if (o instanceof Participant) {
            shouldReIndex = true;
        }

        if (o == Participant.class) {
            shouldReIndex = true;
        }

        if (shouldReIndex) {
            /*
             * For set's Only bother re-indexing if it's a chnage to something
             * we store in index!
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
                                    .getUniqueIdElement_Id()) {
                        shouldReIndex = true;
                    }
                }
            }
        }

        return shouldReIndex;
    }

}
