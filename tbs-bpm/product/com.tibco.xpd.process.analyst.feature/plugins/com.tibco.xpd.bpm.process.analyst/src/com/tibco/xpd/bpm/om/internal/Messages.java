/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.bpm.om.internal;

import org.eclipse.osgi.util.NLS;

/**
 * 
 * 
 * @author aallway
 * @since 6 Jul 2012
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.bpm.om.internal.messages"; //$NON-NLS-1$

    public static String BaseTypeSection_basicTypeCmb_label;

    public static String BaseTypeSection_externalRefCmb_label;

    public static String BaseTypeSection_setReferenceDomain_Participant_label;

    public static String BaseTypeSection_UnresolvedReference;

    public static String ParticipantPropertySection_CannotResolveExternalRef_tooltip;

    public static String ParticipantPropertySection_definedExternal;

    public static String ParticipantPropertySection_ParticipantName_value;

    public static String ParticipantPropertySection_externalReferenceNotSet_tooltip;

    public static String ParticipantPropertySection_FieldTypeSectionHeader_label;

    public static String ParticipantPropertySection_noReferenceSet_shortdesc;

    public static String ParticipantPropertySection_OrgModelQueryScriptSectionHeader_label;

    public static String ParticipantPropertySection_ReferenceSectionHeader_label;

    public static String ParticipantPropertySection_selectReference_browse_tooltip;

    public static String ParticipantPropertySection_SetType_menu;

    public static String ParticipantPropertySection_SetExternalReference_menu;

    public static String ParticipantPropertySection_type_label;

    public static String ParticipantPropertySection_SharedResourceSection_label;

    public static String DataFieldsSection_externalReferenceColumn_label;

    public static String ParticipantsSection_SetExternalReference_menu;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
