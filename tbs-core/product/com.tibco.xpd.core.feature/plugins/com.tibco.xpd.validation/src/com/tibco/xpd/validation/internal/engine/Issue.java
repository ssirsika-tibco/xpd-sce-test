/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.preferences.util.ValidationPreferenceUtil;
import com.tibco.xpd.validation.provider.IIssue;

/**
 * Internal class to contain issue information.
 * 
 * @author nwilson
 */
public class Issue implements IIssue {

    /** The issue id. */
    private String id;

    /** The destination environment ID that generated this issue. */
    private String destinationId;

    /** The destination environment that generated this issue. */
    private String destinationLabel;

    /**
     * The descriptive location of this issue. This is added to the message of
     * the marker.
     */
    private String location;

    /**
     * The URI of the object containing the error. This will be used to navigate
     * to the location of the issue.
     */
    private String uri;

    /** The formatted error message. */
    private String message;

    /** The marker id. */
    private String markerType;

    /** The URI of the object that was validated. */
    private String affectedUri;

    /** The Eclipse marker. */
    private IMarker marker;

    /** The <code>{@link IMarker}</code> severity, or -1 for ignore */
    private Integer severity;

    /** Messages to use for formatting error text. */
    private Collection<String> messages;

    /** Map for storing the additional info on the marker. */
    private Map<String, String> additionalInfoMap;

    /** Map for storing the discrete marker attributes. */
    private Map<String, Object> markerAttributes;

    /** To store whether the issue was created by validate-on-save-only rule. */
    private boolean validateOnSaveOnly;

    /** the validation provider Id **/
    private String providerId;

    /** the validation rule Id **/
    private String ruleId;

    // Message pattern to determine whether MessageFormat or String.format
    // messages are used for this issue
    private static final Pattern messagePattern = Pattern
            .compile(".*\\{\\d+(,\\s*.+)*\\}.*"); //$NON-NLS-1$

    /**
     * @param id
     *            The issue id.
     * @param destination
     *            The destination environment that generated this issue.
     * @param location
     *            The location of the issue.
     * @param uri
     *            The URI of the object containing the error.
     * @param affectedUri
     *            The URI of the object that was validated.
     * @param markerType
     *            The marker type.
     * @param severity
     *            The severity of the marker. This should be the
     *            <code>{@link IMarker}</code> severity value, or -1 for ignore.
     * @param messages
     *            Messages to use for formatting error text.
     * 
     * @param validateOnSaveOnlyRule
     *            whether its validate-on-save-only validation rule.
     * @param providerId
     *            validation provider for the current rule
     * @param ruleId
     *            the rule raising this issue
     */
    public Issue(String id, Destination destination, String location,
            String uri, String affectedUri, String markerType, int severity,
            Collection<String> messages, boolean validateOnSaveOnlyRule,
            String providerId, String ruleId) {
        this.id = id;
        if (destination != null) {
            destinationId = destination.getId();

            destinationLabel = destination.getName();
            if (destination.getVersion() != null) {
                destinationLabel += " " + destination.getVersion(); //$NON-NLS-1$
            }
        }
        this.location = location;
        this.uri = uri;
        this.affectedUri = affectedUri;
        this.markerType = markerType;
        this.severity = new Integer(severity);
        this.messages = messages;
        this.validateOnSaveOnly = validateOnSaveOnlyRule;
        this.providerId = providerId;
        this.ruleId = ruleId;
    }

    /**
     * @param marker
     *            The Eclipse marker.
     */
    public Issue(IMarker marker) {
        this.marker = marker;
        try {
            uri = (String) marker.getAttribute(IMarker.LOCATION);
            message = (String) marker.getAttribute(IMarker.MESSAGE);
            affectedUri = (String) marker.getAttribute(AFFECTED_URI);
            id = (String) marker.getAttribute(ID);
            destinationId = (String) marker.getAttribute(DESTINATION_ID);
            markerType = marker.getType();

            Boolean valOnSave =
                    (Boolean) marker.getAttribute(VALIDATE_ON_SAVE_ONLY);
            if (valOnSave != null) {
                validateOnSaveOnly = valOnSave.booleanValue();
            } else {
                validateOnSaveOnly = false;
            }

            providerId = (String) marker.getAttribute(PROVIDER_ID);
            ruleId = (String) marker.getAttribute(RULE_ID);
            constructAdditionalInfoMap();
        } catch (CoreException e) {
            ValidationActivator.getDefault().getLogger().error(e);
        }

        this.severity =
                new Integer(marker.getAttribute(IMarker.SEVERITY,
                        ValidationPreferenceUtil.getDefaultPreferenceValue(id)));
    }

    /**
     * @return The issue id.
     */
    public String getId() {
        return id;
    }

    /**
     * Get the descriptive location of the issue (this value is added to the
     * message of the resource marker).
     * 
     * @return The descriptive location of this issue.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return The destination environment that generated this issue.
     */
    public String getDestination() {
        return destinationLabel;
    }

    /**
     * @return The destination environment ID that generated this issue.
     */
    public String getDestinationId() {
        return destinationId;
    }

    /**
     * @return The URI of the object containing the error.
     */
    public String getUri() {
        return uri;
    }

    /**
     * The URI of the location where this problem has occurred (this will be
     * added to the location attribute of the resource marker).
     * 
     * @return The URI of the object that was validated.
     */
    public String getAffectedUri() {
        return affectedUri;
    }

    /**
     * @return The marker type.
     */
    public String getMarkerType() {
        return markerType;
    }

    /**
     * @return Messages to use for formatting error text.
     */
    public Collection<String> getMessages() {
        return messages;
    }

    /**
     * @return The Eclipse marker.
     */
    public IMarker getMarker() {
        return marker;
    }

    /**
     * @param obj
     *            The object to test for equality.
     * @return true if the objects match
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof Issue) {
            Issue other = (Issue) obj;

            if (isEqual(id, other.id) && isEqual(uri, other.uri)
                    && isEqual(destinationId, other.destinationId)
                    && isEqual(message, other.message)
                    && isEqual(severity, other.severity)
                    && compareAdditionalInfo(this, other)) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * @param issue1
     *            The first issue.
     * @param issue2
     *            The second issue.
     * @return true if the additional information on the two issues matches.
     */
    private boolean compareAdditionalInfo(Issue issue1, Issue issue2) {
        String additionalInfoString1 = issue1.getAdditionalInfoString();
        String additionalInfoString2 = issue2.getAdditionalInfoString();
        if (additionalInfoString1 == null && additionalInfoString2 == null) {
            return true;
        }
        if (additionalInfoString1 == null) {
            if (additionalInfoString2 != null) {
                return false;
            }
        }
        if (additionalInfoString2 == null) {
            if (additionalInfoString1 != null) {
                return false;
            }
        }
        return additionalInfoString1.equals(additionalInfoString2);
    }

    /**
     * @param o1
     *            Object 1.
     * @param o2
     *            Object 2.
     * @return true if the objects are equal.
     */
    private boolean isEqual(Object o1, Object o2) {
        return (o1 == null && o2 == null) || (o1 != null && o1.equals(o2));
    }

    /**
     * @return The issue hashcode.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int code = 0;
        code += id == null ? 0 : id.hashCode();
        code += uri == null ? 0 : uri.hashCode();
        code += destinationId == null ? 0 : destinationId.hashCode();
        return code;
    }

    /**
     * @return String representation of the additional info
     */
    public String getAdditionalInfoString() {
        if (additionalInfoMap != null) {
            try {
                Properties props = new Properties();
                Set<Entry<String, String>> additionalEntry =
                        additionalInfoMap.entrySet();
                for (Entry<String, String> entry : additionalEntry) {
                    props.setProperty(entry.getKey(), entry.getValue());
                }
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                props.store(out, ""); //$NON-NLS-1$
                String cache = new String(out.toByteArray());
                return cache;
            } catch (Exception ex) {
                // Ignore
            }
        }
        return null;
    }

    /**
     * 
     * 
     */
    @SuppressWarnings("unchecked")
    private void constructAdditionalInfoMap() {
        try {
            Object object =
                    marker.getAttribute(XpdConsts.VALIDATION_MARKER_ADDITIONAL_INFO);

            if (object != null) {
                String additionalInfo = String.valueOf(object);
                if (additionalInfo != null) {
                    ByteArrayInputStream stream =
                            new ByteArrayInputStream(additionalInfo.getBytes());
                    try {
                        Properties props = new Properties();
                        props.load(stream);
                        additionalInfoMap = new HashMap<String, String>();
                        additionalInfoMap.putAll((Map) props);
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }
        } catch (CoreException e1) {
            ValidationActivator.getDefault().getLogger().error(e1);
        }

    }

    /**
     * Creates the issue message from the given template.
     * 
     * @param template
     *            The message template.
     * @see com.tibco.xpd.validation.provider.IIssue#createMessage(java.lang.String)
     */
    public void createMessage(String template) {

        if (template != null) {
            try {
                // Select MessageFormat or String.format depending on message
                // string
                String text;
                Matcher matcher = messagePattern.matcher(template);
                if (matcher.matches()) {
                    text = MessageFormat.format(template, messages.toArray());
                } else {
                    text = String.format(template, messages.toArray());
                }
                if (destinationLabel != null) {
                    String template2 =
                            Messages.ValidationEngine_DestinationColonMessage;
                    message = String.format(template2, destinationLabel, text);
                } else {
                    message = text;
                }
                if (location != null) {
                    template = Messages.ValidationEngine_MessageInBrackets;
                    message = message + String.format(template, location);
                }
            } catch (IllegalFormatException ex) {
                String s1 = "Cannot create validation message ["; //$NON-NLS-1$
                String s2 = "]"; //$NON-NLS-1$
                LoggerFactory.createLogger(ValidationActivator.PLUGIN_ID)
                        .error(ex, s1 + template + s2);
                return;
            }
        }
    }

    /**
     * @return The issue message.
     * @see com.tibco.xpd.validation.provider.IIssue#getMessage()
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param additionalInfoMap
     *            The additional info.
     */
    public void setAdditionalInfo(Map<String, String> additionalInfoMap) {
        this.additionalInfoMap = additionalInfoMap;
    }

    /**
     * Get the additional info map. Added at package level to allow the
     * ValidationEngine to access this directly.
     * 
     * @return
     * 
     * @since 3.5
     */
    /* package level */Map<String, String> getAdditionalInfo() {
        return additionalInfoMap;
    }

    /**
     * Set the marker attributes. These attributes will be added to the
     * resulting {@link IMarker marker} as discrete attributes.
     * 
     * @param attributes
     */
    public void setMarkerAttributes(Map<String, Object> attributes) {
        this.markerAttributes = attributes;
    }

    /**
     * Get the marker attributes.
     * 
     * @return map of marker attributes if available, <code>null</code>
     *         otherwise.
     */
    public Map<String, Object> getMarkerAttributes() {
        return markerAttributes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.provider.IIssue#getSeverity()
     */
    public Integer getSeverity() {
        return severity;
    }

    /**
     * @return the validateOnSaveOnly
     */
    public boolean isValidateOnSaveOnly() {
        return validateOnSaveOnly;
    }

    /**
     * @return the validation provider ID contributing the rule
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @return the validation rule ID raising the issue
     */
    public String getRuleId() {
        return ruleId;
    }

}
