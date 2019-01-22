/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.provider;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IMarker;

import com.tibco.xpd.validation.destinations.Destination;

/**
 * An instance of this interface is created by the validation engine for every
 * validation run and is made available to the rules. It provides a means for
 * the rules to create issues and also gives access to the IPreProcessor tools.
 * 
 * @author nwilson
 */
public interface IValidationScope {

    /**
     * @param id
     *            The issue id.
     * @param location
     *            The location of the issue.
     * @param uri
     *            The internal location of the issue.
     */
    void createIssue(String id, String location, String uri);

    /**
     * @param id
     *            The issue id.
     * @param location
     *            The location of the issue.
     * @param uri
     *            The internal location of the issue.
     * @param message
     *            Additional message text.
     */
    void createIssue(String id, String location, String uri,
            Collection<String> message);

    /**
     * @param id
     *            The issue id.
     * @param location
     *            The location of the issue.
     * @param uri
     *            The internal location of the issue.
     * @param message
     *            Additional message text.
     * @param additionalInfo
     *            Map of additional information items for this issue.
     * 
     */
    void createIssue(String id, String location, String uri,
            Collection<String> message, Map<String, String> additionalInfo);

    /**
     * Create an issue.
     * <p>
     * The marker attributes will be added as discreet attributes to the
     * resulting marker as opposed to the additionalInfo map that gets added as
     * a single attribute.
     * </p>
     * <p>
     * <b>NOTE:</b> Only user attributes should be set. Any system marker
     * attributes set will be overridden.
     * </p>
     * 
     * @param id
     *            The issue id.
     * @param location
     *            The location of the issue.
     * @param uri
     *            The internal location of the issue.
     * @param message
     *            Additional message text.
     * @param additionalInfo
     *            Map of additional information items for this issue.
     * @param markerAttributes
     *            Map of marker attributes that will be added to the created
     *            marker. These can then be accessed using
     *            {@link IMarker#getAttribute(String)}.
     * 
     * @since 3.1
     */
    void createIssue(String id, String location, String uri,
            Collection<String> message, Map<String, String> additionalInfo,
            Map<String, Object> markerAttributes);

    /**
     * @return The collected issues.
     */
    Collection<IIssue> getIssues();

    /**
     * @param destination
     *            The current destination environment.
     */
    void setCurrentDestination(Destination destination);

    /**
     * @return The current destination environment.
     */
    Destination getCurrentDestination();

    /**
     * @param <T>
     *            The tool type.
     * @param clss
     *            The tool class.
     * @param input
     *            The tool input object.
     * @return The tool.
     */
    <T extends IPreProcessor> T getTool(Class<T> clss, Object input);

    /**
     * @param markerType
     *            The marker type.
     */
    void setCurrentMarkerType(String markerType);

    /**
     * @param uri
     *            The uri affected.
     */
    void setAffectedUri(String uri);

    /**
     * @return The affected URIs.
     */
    Collection<String> getAffectedUris();

    /**
     * @return the isLiveValidation
     */
    public boolean isLiveValidation();

    /**
     * @param isLiveValidation
     *            the isLiveValidation to set
     */
    public void setIsLiveValidation(boolean isLiveValidation);

    /**
     * @return the isValidateOnSaveOnlyRule
     */
    public boolean isValidateOnSaveOnlyRule();

    /**
     * @param isValidateOnSaveOnlyRule
     *            the isValidateOnSaveOnlyRule to set
     */
    public void setValidateOnSaveOnlyRule(boolean isValidateOnSaveOnlyRule);

    /**
     * @return the currentProviderId
     */
    public String getCurrentProviderId();

    /**
     * @param currentProviderId
     *            the currentProviderId to set
     */
    public void setCurrentProviderId(String currentProviderId);

    /**
     * @return the currentRuleId
     */
    public String getCurrentRuleId();

    /**
     * @param currentRuleId
     *            the currentRuleId to set
     */
    public void setCurrentRuleId(String currentRuleId);

    /**
     * This method checks whether the rule that raise the given issue was
     * executed or not in a validation run.
     * 
     * <p>
     * On setting current rule id on the scope using <code>
     * setCurrentRuleId(String currentRuleId)</code> and provided the
     * destination and provider ids are set as well, then a list of rules being
     * run is created for each destination and provider.
     * </P>
     * 
     * @param issue
     * @return <code>true</code> if the rule that raised this issue is in the
     *         list of validated rules for the validation run,
     *         <code>false</code> otherwise.
     */
    public boolean wasRuleExecutedForTheIssue(IIssue issue);

}
