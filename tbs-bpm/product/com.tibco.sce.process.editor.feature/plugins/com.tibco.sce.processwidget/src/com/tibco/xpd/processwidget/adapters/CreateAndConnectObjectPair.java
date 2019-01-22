package com.tibco.xpd.processwidget.adapters;

/**
 * CreateAndConnectObjectPair
 * <p>
 * Simple data class for pairing up an object type with a connection type
 * 
 * @author aallway
 * @since 3.3 (19 Aug 2009)
 */
public class CreateAndConnectObjectPair {

    private CreateAndConnectObjectPair.CreateAndConnectObjectType objectType;

    private CreateAndConnectObjectPair.CreateAndConnectConnectionType connectionType;

    /**
     * @param objectType
     * @param connectionType
     */
    public CreateAndConnectObjectPair(
            CreateAndConnectObjectPair.CreateAndConnectObjectType objectType,
            CreateAndConnectObjectPair.CreateAndConnectConnectionType connectionType) {
        super();

        this.objectType = objectType;
        this.connectionType = connectionType;
    }

    /**
     * @return the objectType
     */
    public CreateAndConnectObjectPair.CreateAndConnectObjectType getObjectType() {
        return objectType;
    }

    /**
     * @return the connectionType
     */
    public CreateAndConnectObjectPair.CreateAndConnectConnectionType getConnectionType() {
        return connectionType;
    }

    /**
     * CreateAndConnectObjectType
     * <p>
     * Enumeration of
     * 
     * @author aallway
     * @since 3.3 (19 Aug 2009)
     */
    public static enum CreateAndConnectObjectType {
        // Note that separator is a 'special' that allows control of the
        // popup menu content.
        TASK, GATEWAY, START_EVENT, INTERMEDIATE_EVENT, END_EVENT, ANNOTATION, DATAOBJECT, SEPARATOR
    }

    /**
     * CreateAndConnectObjectType
     * <p>
     * Enumeration of
     * 
     * @author aallway
     * @since 3.3 (19 Aug 2009)
     */
    public static enum CreateAndConnectConnectionType {
        SEQUENCE_FLOW, MESSAGE_FLOW, ASSOCIATION
    }

}