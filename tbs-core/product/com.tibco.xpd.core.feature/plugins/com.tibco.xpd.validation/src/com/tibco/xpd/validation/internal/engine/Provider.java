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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.ValidationRule;
import com.tibco.xpd.validation.provider.IScopeProvider;
import com.tibco.xpd.validation.provider.IScopeProvider2;
import com.tibco.xpd.validation.provider.IValidationItem;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.validation.utils.ValidationStats;

/**
 * Validation provider.
 * 
 * @author nwilson
 */
public class Provider {

    /** The provider ID. */
    private String id;

    /** The provider name. */
    private String name;

    /** The scope provider. */
    private IScopeProvider scopeProvider;

    /** The marker type. */
    private String markerType;

    /** The rules grouped by target class name. */
    private Map<Class, Collection<ValidationRule>> groups;

    private final RulesExceptionHandler exceptionHandler;

    /**
     * @param id
     *            The provider ID.
     * @param name
     *            The provider name.
     * @param scopeProvider
     *            The scope provider.
     * @param markerType
     *            The marker type.
     */
    public Provider(String id, String name, IScopeProvider scopeProvider,
            String markerType) {
        this.id = id;
        this.name = name;
        this.scopeProvider = scopeProvider;
        this.markerType = markerType;
        groups = new HashMap<Class, Collection<ValidationRule>>();

        this.exceptionHandler = new RulesExceptionHandler();
    }

    /**
     * @return The provider ID.
     */
    public String getId() {
        return id;
    }

    /**
     * @return The provider name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param rule
     *            The rule.
     */
    public void addRule(ValidationRule rule) {
        Class targetClass = rule.getValidationRule().getTargetClass();
        if (targetClass != null) {
            Collection<ValidationRule> rules = groups.get(targetClass);
            if (rules == null) {
                rules = new ArrayList<ValidationRule>();
                groups.put(targetClass, rules);
            }
            rules.add(rule);
        }
    }

    /**
     * @param destination
     *            The destination environment.
     * @param scope
     *            The validation scope.
     * @param item
     *            The item to validate.
     */
    public void validate(Destination destination, IValidationScope scope,
            IValidationItem item) {
        Collection<EObject> objects;
        if (scopeProvider instanceof IScopeProvider2) {
            objects =
                    ((IScopeProvider2) scopeProvider)
                            .getAffectedObjects(destination, id, scope, item);
        } else {
            objects = scopeProvider.getAffectedObjects(destination, id, item);
        }
        exceptionHandler.initialize(scope);

        scope.setCurrentMarkerType(markerType);
        for (EObject o : objects) {
            if (o != null && o.eResource() != null) {
                Resource resource = o.eResource();
                String uri = resource.getURIFragment(o);
                scope.setAffectedUri(uri);
                for (Class groupClass : groups.keySet()) {
                    if (groupClass.isInstance(o)) {
                        Collection<ValidationRule> rules =
                                groups.get(groupClass);
                        for (ValidationRule validationRule : rules) {
                            IValidationRule rule =
                                    validationRule.getValidationRule();
                            /*
                             * if this rule is configured to be validated only
                             * on save and currently we are in live validation
                             * mode, then don't run this rule.
                             */
                            if (scope.isLiveValidation()
                                    && validationRule.isValidateOnSaveOnly()) {
                                continue;
                            }
                            if (!exceptionHandler.isRuleBarred(rule)) {
                                try {
                                    scope.setCurrentRuleId(rule.getClass()
                                            .getName());
                                    scope.setValidateOnSaveOnlyRule(validationRule
                                            .isValidateOnSaveOnly());
                                    /*
                                     * If validation stats collection flag is
                                     * set then store validation rule data
                                     */
                                    if (ValidationActivator.getDefault()
                                            .isCollectValidationStats()) {
                                        long startTime =
                                                System.currentTimeMillis();
                                        rule.validate(scope, o);
                                        long endTime =
                                                System.currentTimeMillis();
                                        long totalTime = endTime - startTime;

                                        ValidationStats.getInstance()
                                                .addRuleData(rule.getClass(),
                                                        totalTime);
                                    } else {
                                        rule.validate(scope, o);
                                    }

                                } catch (Throwable t) {
                                    /*
                                     * Catch any exceptions and log, then
                                     * continue with next rule
                                     */
                                    exceptionHandler.handleException(rule, t);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Rules exception handler. This will track all rules that throw an
     * exception and report it in the log. Use
     * {@link #isRuleBarred(IValidationRule) isRulesBarred} to check if a rule
     * has previously thrown an exception - this can be used to stop running the
     * rule again for this validation run.
     * 
     * @author njpatel
     */
    private class RulesExceptionHandler {
        private IValidationScope scope;

        private final Set<IValidationRule> rules;

        /**
         * Rules exception handler.
         */
        public RulesExceptionHandler() {
            rules = new HashSet<IValidationRule>();
        }

        /**
         * Initialize the handler. If the validation scope has changed then it
         * will reset this handler.
         * 
         * @param scope
         *            current validation scope.
         */
        public void initialize(IValidationScope scope) {
            if (scope != this.scope) {
                this.scope = scope;
                rules.clear();
            }
        }

        /**
         * Handle the exception thrown by the rule. This will register the rule
         * as bad and log the exception.
         * 
         * @param rule
         *            rule that threw the exception
         * @param t
         *            exception
         */
        public void handleException(IValidationRule rule, Throwable t) {
            rules.add(rule);

            ValidationActivator
                    .getDefault()
                    .getLogger()
                    .error(t,
                            String.format(Messages.Provider_ruleException_error_message,
                                    rule.getClass().getName()));
        }

        /**
         * Check if this rule threw an exception previously (in this validation
         * run).
         * 
         * @param rule
         *            validation rule
         * @return <code>true</code> if this rule threw an exception previously,
         *         <code>false</code> otherwise.
         */
        public boolean isRuleBarred(IValidationRule rule) {
            return rules.contains(rule);
        }
    }

}
