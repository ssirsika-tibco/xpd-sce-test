/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.nimbus.xsltutil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tibco.xpd.nimbus.XpdNimbusPlugin;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * Util class to be instantiated by NimbusProcessMap_2_BSxpdl.xslt
 * 
 * @author aallway
 * @since 16 Oct 2012
 */
public class ImportNimbusProcessMapXsltUtil {

    /** Load on demand cache of nimbus activity id to xpdl2 activity id. */
    private Map<String, String> nimbusActivityIdMap =
            new HashMap<String, String>();

    /** Load on demand cache of nimbus resource id to xpdl2 participant id. */
    private Map<String, String> nimbusResourceIdMap =
            new HashMap<String, String>();

    /**
     * @param label
     * @return The internal token name for the given label.
     */
    public String getTokenName(String label) {
        if (label == null || label.length() == 0) {
            return ""; //$NON-NLS-1$
        }

        return NameUtil.getInternalName(label, false);
    }

    /**
     * @return Current date as a string.
     */
    public String getCurrentDate() {
        Date date = new Date();
        return date.toLocaleString();
    }

    /**
     * Reduce the given text element set down to the unique set
     * <p>
     * This is done CaSe insensitively.
     * <p>
     * Elements with empty names are not returned.
     * 
     * @param textElements
     *            Elements whose text is to be used to return the nodes.
     * 
     * @return Set of elements with different non empty text values.
     */
    public NodeList getUniqueTextElementSet(NodeList textElements) {
        Map<String, Node> textValueToNodeMap =
                new LinkedHashMap<String, Node>();

        for (int i = 0; i < textElements.getLength(); i++) {
            Node textElement = textElements.item(i);

            Node textNode = textElement.getFirstChild();

            if (textNode != null) {
                String textValue = textNode.getTextContent();

                if (textValue != null && textValue.length() > 0) {

                    boolean alreadyContains = false;
                    for (Entry<String, Node> entry : textValueToNodeMap
                            .entrySet()) {
                        if (isEquivalentDataName(textValue, entry.getKey())) {
                            alreadyContains = true;
                            break;
                        }
                    }

                    if (!alreadyContains) {
                        textValueToNodeMap.put(normaliseSpace(textValue),
                                textElement);
                    }
                }
            }
        }

        ArrayNodeList returnList = new ArrayNodeList();

        if (!textValueToNodeMap.isEmpty()) {
            for (Node resourceElement : textValueToNodeMap.values()) {
                returnList.add(resourceElement);
            }
        }

        return returnList;
    }

    /**
     * Check if the two strings are the the equivalent data names.
     * <p>
     * During import, connection labels are used to define incoming / outgoing
     * data. However, we allow for the mixed-case and whitespace-ignored
     * equivalence so it's not so easy for the user to slightly mis-type a name
     * they meant to be same AND prevents us from getting multiple data fields
     * with same name once it becomes tokenised.
     * 
     * @param s1
     * @param s2
     * @return <code>true</code> if strings would generate the equivalent data
     *         name.
     */
    public boolean isEquivalentDataName(String s1, String s2) {
        boolean res = getTokenName(s1).equalsIgnoreCase(getTokenName(s2));
        return res;
    }

    /**
     * Perform a standard normalisation of the given string (trimmed and all
     * white space sequences turned to single space.
     * 
     * @param s1
     * @return normalised string.
     */
    public String normaliseSpace(String s1) {
        if (s1 == null) {
            return ""; //$NON-NLS-1$
        }

        s1 = s1.trim();
        s1 = s1.replaceAll("[\\r\\n\\t\\s]+", " "); //$NON-NLS-1$ //$NON-NLS-2$
        return s1;
    }

    /**
     * @return Create a unique identifier.
     */
    public String createUniqueId() {
        return EcoreUtil.generateUUID();
    }

    /**
     * Get the xpdl2 activity id for the nimbus diagram activity id.
     * 
     * @param nimbusDiagramId
     * @param nimbusActivityId
     * 
     * @return xpdl activity id.
     */
    public String getActivityId(String nimbusDiagramId, String nimbusActivityId) {
        String searchId = nimbusDiagramId + "_Act_" + nimbusActivityId; //$NON-NLS-1$

        String xpdl2ActivityId = nimbusActivityIdMap.get(searchId);

        if (xpdl2ActivityId == null || xpdl2ActivityId.length() == 0) {
            xpdl2ActivityId = createUniqueId();

            nimbusActivityIdMap.put(searchId, xpdl2ActivityId);
        }

        return xpdl2ActivityId;
    }

    /**
     * Get the xpdl2 participant id for the nimbus diagram resource id.
     * 
     * @param resourceName
     * 
     * @return xpdl participant id.
     */
    public String getParticipantId(String resourceName) {
        String searchId = resourceName;

        String xpdl2ParticipantId = nimbusResourceIdMap.get(searchId);

        if (xpdl2ParticipantId == null || xpdl2ParticipantId.length() == 0) {
            xpdl2ParticipantId = createUniqueId();

            nimbusResourceIdMap.put(searchId, xpdl2ParticipantId);
        }

        return xpdl2ParticipantId;
    }

    /**
     * Output debug message to eclipse log.
     * 
     * @param message
     */
    public void debug(String message) {
        XpdNimbusPlugin.getDefault().getLogger().warn(message);
    }

    public String inspectObject(Object object) {
        if (object != null) {
            return object.toString();
        }
        return null;
    }

    /**
     * Array implementation of {@link NodeList}
     * 
     * 
     * @author aallway
     * @since 16 Oct 2012
     */
    @SuppressWarnings({ "serial", "unused" })
    private static class ArrayNodeList extends ArrayList<Node> implements
            NodeList {

        /**
         * @see org.w3c.dom.NodeList#item(int)
         * 
         * @param index
         * @return
         */
        @Override
        public Node item(int index) {
            return get(index);
        }

        /**
         * @see org.w3c.dom.NodeList#getLength()
         * 
         * @return
         */
        @Override
        public int getLength() {
            return size();
        }

    }
}
