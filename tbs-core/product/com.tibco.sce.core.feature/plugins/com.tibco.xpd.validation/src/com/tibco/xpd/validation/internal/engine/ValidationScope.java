/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.internal.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.preferences.util.ValidationPreferenceUtil;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IPreProcessorFactory;
import com.tibco.xpd.validation.provider.IValidationScope;

/**
 * Internal implementation of the IValidationScope interface.
 * 
 * @author nwilson
 */
public class ValidationScope implements IValidationScope {

    /** A collection of issues for this validation run. */
    private Collection<IIssue> issues;

    /** A collection of affected object URIs. */
    private Collection<String> uris;

    /** The map of ids to pre-processors. */
    private Map<Class<? extends IPreProcessor>, Map<Object, IPreProcessor>> processors;

    /** The current destination environment. */
    private Destination destination;

    /** The marker type. */
    private String markerType;

    /** The current uri. */
    private String affectedUri;

    /** The live validation flag */
    private boolean isLiveValidation;

    /** Validate on save only rule flag. */
    private boolean isValidateOnSaveOnlyRule;

    /** The current validation provider id. */
    private String currentProviderId;

    /** The current rule id. */
    private String currentRuleId;

    /**
     * Map to store validation rules being run for each provider in each
     * destination (i.e., destId -> providerId -> ruleId)
     */
    private Map<String /* dest id */, Map<String /* provider Id */, Set<String> /* ruleIds */>> destProviderRulesMap;

    /**
     * Constructor.
     */
    public ValidationScope() {
        issues = new ArrayList<IIssue>();
        uris = new HashSet<String>();
        processors =
                new HashMap<Class<? extends IPreProcessor>, Map<Object, IPreProcessor>>();
        destProviderRulesMap = new HashMap<String, Map<String, Set<String>>>();
    }

    /**
     * @param id
     *            The issue id.
     * @param location
     *            The location of the issue.
     * @param uri
     *            The internal location of the issue.
     */
    public void createIssue(String id, String location, String uri) {
        createIssue(id, location, uri, new ArrayList<String>());
    }

    /**
     * @param id
     *            The issue id.
     * @param location
     *            The location of the issue.
     * @param uri
     *            The internal location of the issue.
     * @param messages
     *            Messages to use for marmatting error text.
     */
    public void createIssue(String id, String location, String uri,
            Collection<String> messages) {
        _createIssue(id, location, uri, messages);
    }

    /**
     * @param id
     *            The issue id.
     * @param location
     *            The location of the issue.
     * @param uri
     *            The internal location of the issue.
     * @param messages
     *            Messages to use for marmatting error text.
     * @param additionalInfoMap
     *            additional info map.
     */
    public void createIssue(String id, String location, String uri,
            Collection<String> messages, Map<String, String> additionalInfoMap) {
        Issue issue = _createIssue(id, location, uri, messages);
        if (issue != null) {
            issue.setAdditionalInfo(additionalInfoMap);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.validation.provider.IValidationScope#createIssue(java.
     * lang.String, java.lang.String, java.lang.String, java.util.Collection,
     * java.util.Map, java.util.Map)
     */
    public void createIssue(String id, String location, String uri,
            Collection<String> messages, Map<String, String> additionalInfoMap,
            Map<String, Object> markerAttributes) {

        Issue issue = _createIssue(id, location, uri, messages);

        if (issue != null) {
            issue.setAdditionalInfo(additionalInfoMap);
            issue.setMarkerAttributes(markerAttributes);
        }
    }

    /**
     * @return The collected issues.
     * @see com.tibco.xpd.validation.provider.IValidationScope#getIssues()
     */
    public Collection<IIssue> getIssues() {
        return issues;
    }

    /**
     * @return The current destination environment.
     */
    public Destination getCurrentDestination() {
        return destination;
    }

    /**
     * @param destination
     *            The current destination environment.
     */
    public void setCurrentDestination(Destination destination) {
        this.destination = destination;
    }

    /**
     * @param <T>
     *            The tool type.
     * @param clss
     *            The tool class.
     * @param input
     *            The tool input object.
     * @return The tool.
     */
    public <T extends IPreProcessor> T getTool(Class<T> clss, Object input) {
        Map<Object, IPreProcessor> byObject = processors.get(clss);
        if (byObject == null) {
            byObject = new HashMap<Object, IPreProcessor>();
            processors.put(clss, byObject);
        }
        IPreProcessor cached = byObject.get(input);
        T processor = clss.cast(cached);
        if (processor == null) {
            PreProcessor preProcessor =
                    ValidationManager.getInstance().getPreProcessor(clss);
            IPreProcessorFactory factory = preProcessor.getFactory();
            processor = clss.cast(factory.createPreProcessor(this, input));
            if (processor != null) {
                byObject.put(input, processor);
            }
        }
        return processor;
    }

    /**
     * @param markerType
     *            The marker type.
     */
    public void setCurrentMarkerType(String markerType) {
        this.markerType = markerType;
    }

    /**
     * @param uri
     *            The URI to add.
     * @see com.tibco.xpd.validation.provider.IValidationScope#setAffectedUri(java.lang.String)
     */
    public void setAffectedUri(String uri) {
        affectedUri = uri;
        uris.add(uri);
    }

    /**
     * @return The affected URIs.
     */
    public Collection<String> getAffectedUris() {
        return uris;
    }

    /**
     * Create an <code>Issue</code> with the given information.
     * 
     * @param id
     *            issue id
     * @param location
     *            problem location
     * @param uri
     *            object uri
     * @param messages
     *            message strings
     */
    private Issue _createIssue(String id, String location, String uri,
            Collection<String> messages) {
        int value = ValidationPreferenceUtil.getPreferenceValue(id);
        Issue issue = null;
        // If issue severity is set to ignore then don't create issue
        if (value >= 0) {
            issue =
                    new Issue(id, destination, location, uri, affectedUri,
                            markerType, value, messages,
                            isValidateOnSaveOnlyRule(), getCurrentProviderId(),
                            getCurrentRuleId());
            issues.add(issue);
        }

        return issue;
    }

    /**
     * @return the isLiveValidation
     */
    public boolean isLiveValidation() {
        return isLiveValidation;
    }

    /**
     * @param isLiveValidation
     *            the isLiveValidation to set
     */
    public void setIsLiveValidation(boolean isLiveValidation) {
        this.isLiveValidation = isLiveValidation;
    }

    /**
     * @return the isValidateOnSaveOnlyRule
     */
    public boolean isValidateOnSaveOnlyRule() {
        return isValidateOnSaveOnlyRule;
    }

    /**
     * @param isValidateOnSaveOnlyRule
     *            the isValidateOnSaveOnlyRule to set
     */
    public void setValidateOnSaveOnlyRule(boolean isValidateOnSaveOnlyRule) {
        this.isValidateOnSaveOnlyRule = isValidateOnSaveOnlyRule;
    }

    /**
     * @return the currentProviderId
     */
    public String getCurrentProviderId() {
        return currentProviderId;
    }

    /**
     * @param currentProviderId
     *            the currentProviderId to set
     */
    public void setCurrentProviderId(String currentProviderId) {
        this.currentProviderId = currentProviderId;
    }

    /**
     * @return the currentRuleId
     */
    public String getCurrentRuleId() {
        return currentRuleId;
    }

    /**
     * @param currentRuleId
     *            the currentRuleId to set
     */
    public void setCurrentRuleId(String currentRuleId) {
        this.currentRuleId = currentRuleId;
        // Add the rule id to the list of rules being validated
        addToExecutedRulesList(currentRuleId);
    }

    /**
     * @see com.tibco.xpd.validation.provider.IValidationScope#wasRuleExecutedForTheIssue(com.tibco.xpd.validation.provider.IIssue)
     * 
     * @param issue
     * @return
     */
    public boolean wasRuleExecutedForTheIssue(IIssue issue) {
        Map<String, Set<String>> providerRulesMap =
                destProviderRulesMap.get(issue.getDestinationId());
        if (providerRulesMap != null) {
            Collection<String> rulesList =
                    providerRulesMap.get(issue.getProviderId());
            if (rulesList != null) {
                if (rulesList.contains(issue.getRuleId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds the given rule id to the list of executed rules for a validation run
     * so that we can uniquely identify whether a particular rule was re-run in
     * a particular destination and validation provider. This would be useful
     * when updating the problem markers so that we do not delete any markers
     * whose rule has not re-run during a validation run.
     * 
     * @param ruleId
     */
    private void addToExecutedRulesList(String ruleId) {

        Map<String, Set<String>> providerRulesMap =
                destProviderRulesMap.get(getCurrentDestination().getId());

        if (providerRulesMap == null) {
            providerRulesMap = new HashMap<String, Set<String>>();
            destProviderRulesMap.put(getCurrentDestination().getId(),
                    providerRulesMap);

        }

        Set<String> rulesList = providerRulesMap.get(getCurrentProviderId());
        if (rulesList == null) {
            rulesList = new HashSet<String>();
            providerRulesMap.put(getCurrentProviderId(), rulesList);
        }

        rulesList.add(ruleId);
    }
}
