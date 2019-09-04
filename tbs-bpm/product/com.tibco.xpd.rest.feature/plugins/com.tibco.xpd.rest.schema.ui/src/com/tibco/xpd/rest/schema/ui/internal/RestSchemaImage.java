/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.schema.ui.internal;

/**
 * List of images registered with Image Registry.
 * 
 * @author nwilson
 * @since 12 Jan 2015
 */
public enum RestSchemaImage {
    ADD_JSON_CLASS("icons/AddJSONClass.png"), //$NON-NLS-1$
    ADD_JSON_PROPERTY("icons/AddJSONProperty.png"), //$NON-NLS-1$
    JSON_BASE_PROPERTY("icons/JSONBaseProperty.png"), //$NON-NLS-1$
    JSON_BOOLEAN_ARRAY_PROPERTY("icons/JSONBooleanArrayProperty.png"), //$NON-NLS-1$
    JSON_BOOLEAN_PROPERTY("icons/JSONBooleanProperty.png"), //$NON-NLS-1$
    JSON_CLASS_ARRAY_PROPERTY("icons/JSONClassArrayProperty.png"), //$NON-NLS-1$
    JSON_CLASS_OBJECT("icons/JSONClassObject.png"), //$NON-NLS-1$
    JSON_CLASS_PROPERTY("icons/JSONClassProperty.png"), //$NON-NLS-1$
    JSON_DATE_TIME_ARRAY_PROPERTY("icons/JSONDateTimeArrayProperty.png"), //$NON-NLS-1$
    JSON_DATE_TIME_PROPERTY("icons/JSONDateTimeProperty.png"), //$NON-NLS-1$
    JSON_INTEGER_ARRAY_PROPERTY("icons/JSONIntegerArrayProperty.png"), //$NON-NLS-1$
    JSON_INTEGER_PROPERTY("icons/JSONIntegerProperty.png"), //$NON-NLS-1$
    JSON_NUMBER_ARRAY_PROPERTY("icons/JSONNumberArrayProperty.png"), //$NON-NLS-1$
    JSON_NUMBER_PROPERTY("icons/JSONNumberProperty.png"), //$NON-NLS-1$
    JSON_ROOT_CLASS_OBJECT("icons/JSONRootClassObject.png"), //$NON-NLS-1$
    JSON_STRING_ARRAY_PROPERTY("icons/JSONStringArrayProperty.png"), //$NON-NLS-1$
    JSON_STRING_PROPERTY("icons/JSONStringProperty.png"), //$NON-NLS-1$
    JSON_SWITCH_ROOT("icons/SwitchRoot.png"), //$NON-NLS-1$
    IMPORT_JSON_WIZARD("icons/ImportJSONWizard.png"), //$NON-NLS-1$
    NEW_JSON_FILE_WIZARD("icons/NewJSONFileWizard.png"), //$NON-NLS-1$
    IMG_ERROR("icons/error.png"), //$NON-NLS-1$
    IMG_UNPROCESSED_TEXT_TYPE("icons/UnprocessedText.png"); //$NON-NLS-1$

    private String id;

    RestSchemaImage(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
