/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.validation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.util.GlobalSignalUtil;
import com.tibco.xpd.n2.globalsignal.resource.util.GSDModelUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;

/**
 * Rules specific to global signals.
 * 
 * @author sajain
 * @since Feb 25, 2015
 */
public class GlobalSignalRule implements IValidationRule {

    /**
     * Global Signal should have a Timeout value when 'Timeout Signal After'
     * option is selected.
     */
    private static final String GLOBAL_SIGNAL_TIMEOUT_ISSUE =
            "gsd.globalSignalMustHaveTimeOutValueWhenTSA"; //$NON-NLS-1$

    /**
     * Global Signal name cannot be empty.
     */
    private static final String GLOBAL_SIGNAL_NAME_ISSUE =
            "gsd.globalSignalNameCannotBeEmpty";

    /**
     * Signals should be unique in a Global Signal Definition file.
     */
    private static final String DUPLICATE_GLOBAL_SIGNAL_ISSUE =
            "gsd.globalSignalsMustBeUnique"; //$NON-NLS-1$

    /**
     * Global Signal must have at least one Correlation Field.
     */
    private static final String GLOBAL_SIGNAL_MUST_HAVE_CORRELATION_FIELD_ISSUE =
            "gsd.globalSignalMustHaveCorrelationField"; //$NON-NLS-1$    

    /**
     * The qualified Global Signal name '%1$s' should be less than 255
     * characters.
     */
    public static final String ISSUE_QUALIFIED_SIGNAL_NAME_LESS_THAN_255_CHARACTERS =
            "gsd.globalSignalNameLengthMustBeLessThan255";//$NON-NLS-1$

    /**
     * Global signal correlation timeout must be less than 24 hours (86400
     * seconds).
     */
    public static final String ISSUE_GLOBAL_SIGNAL_TIMEOUT_MUST_BE_LESS_THAN_24_HOURS =
            "gsd.globalSignalTimeoutMustBeLessThan24Hrs"; //$NON-NLS-1$

    /**
     * Name '%1$s' can contain only alpha-numeric and underscore characters and
     * cannot have leading numerics.
     */
    private static final String INVALID_GLOBAL_SIGNAL_NAME =
            "gsd.invalidNameError"; //$NON-NLS-1$

    @Override
    public Class<?> getTargetClass() {
        return GlobalSignal.class;
    }

    /**
     * Default constructor.
     */
    public GlobalSignalRule() {
    }

    @Override
    public void validate(IValidationScope scope, Object o) {

        if (o instanceof GlobalSignal) {

            GlobalSignal gs = (GlobalSignal) o;

            /*
             * Global Signal name cannot be empty.
             */
            if (null == gs.getName() || gs.getName().isEmpty()) {

                addIssue(GLOBAL_SIGNAL_NAME_ISSUE, scope, gs, null);

            } else {

                /*
                 * Name '%1$s' can contain only alpha-numeric and underscore
                 * characters and cannot have leading numerics.
                 */

                String name = gs.getName();

                if (!NameUtil.isValidName(name, false)) {

                    List<String> messages = new ArrayList<String>();

                    messages.add(name);

                    addIssue(INVALID_GLOBAL_SIGNAL_NAME,
                            gs,
                            messages,
                            null,
                            scope);
                }

            }

            /*
             * Global Signal should have a Timeout value when 'Timeout Signal
             * After' option is selected.
             */
            validateSignalForTimeout(scope, gs);

            /*
             * Signals should be unique in a Global Signal Definition file.
             */
            validateAgainstDuplicateSignals(scope, gs);

            /*
             * check if the qualified signal name is less than 255 characters
             * else raise an issue.
             */
            checkQualifiedSignalNameIsLessThan255Chars(scope, gs);

            /*
             * Global Signal must have at least one Correlation Field.
             */
            checkForCorrelationFieldInSignal(scope, gs);
        }
    }

    /**
     * Checks if the qualified signal name is less than 255 characters, if not
     * then raises an issue.
     * 
     * @param scope
     * @param globalSignal
     */
    private void checkQualifiedSignalNameIsLessThan255Chars(
            IValidationScope scope, GlobalSignal globalSignal) {
        /*
         * get the qualified signal name.
         */
        String globalSignalQualifiedName =
                GlobalSignalUtil.getGlobalSignalQualifiedName(globalSignal);

        if (globalSignalQualifiedName != null
                && !globalSignalQualifiedName.isEmpty()) {
            /*
             * get the index of first slash.
             */
            int indexOfFirstSlash = globalSignalQualifiedName.indexOf('/');

            if (indexOfFirstSlash != -1) {
                /*
                 * The qualified Signal Name will have the project ID, at
                 * runtime everything following the project ID would be the
                 * qualified name and should be less than 255 characters.
                 */
                String qualifiedSignalNameWithoutProjectId =
                        globalSignalQualifiedName
                                .substring(indexOfFirstSlash + 1,
                                        globalSignalQualifiedName.length());

                if (!(qualifiedSignalNameWithoutProjectId.length() < 255)) {
                    addIssue(ISSUE_QUALIFIED_SIGNAL_NAME_LESS_THAN_255_CHARACTERS,
                            scope,
                            globalSignal,
                            qualifiedSignalNameWithoutProjectId);
                }
            }
        }
    }

    /**
     * Global Signal must have at least one Correlation Field.
     * 
     * @param scope
     * @param gs
     */
    private void checkForCorrelationFieldInSignal(IValidationScope scope,
            GlobalSignal gs) {

        if (gs.getPayloadDataFields() == null) {

            addIssue(GLOBAL_SIGNAL_MUST_HAVE_CORRELATION_FIELD_ISSUE,
                    scope,
                    gs,
                    null);

        } else if (gs.getPayloadDataFields().isEmpty()) {

            addIssue(GLOBAL_SIGNAL_MUST_HAVE_CORRELATION_FIELD_ISSUE,
                    scope,
                    gs,
                    null);

        } else {

            boolean hasCorrelationField = false;

            for (PayloadDataField eachPdf : gs.getPayloadDataFields()) {

                if (eachPdf.isCorrelation()) {
                    hasCorrelationField = true;
                    break;

                } else {
                    hasCorrelationField = false;
                    continue;
                }
            }

            if (!hasCorrelationField) {

                addIssue(GLOBAL_SIGNAL_MUST_HAVE_CORRELATION_FIELD_ISSUE,
                        scope,
                        gs,
                        null);
            }

        }
    }

    /**
     * Signals should be unique in a Global Signal Definition file.
     * 
     * @param scope
     * @param gs
     */
    private void validateAgainstDuplicateSignals(IValidationScope scope,
            GlobalSignal gs) {

        if (gs.eContainer() instanceof GlobalSignalDefinitions) {

            GlobalSignalDefinitions gsds =
                    (GlobalSignalDefinitions) (gs.eContainer());

            for (GlobalSignal eachGS : gsds.getGlobalSignals()) {

                if (!eachGS.equals(gs) && eachGS.getName().equals(gs.getName())) {

                    addIssue(DUPLICATE_GLOBAL_SIGNAL_ISSUE, scope, gs, null);
                    break;
                }

            }
        }
    }

    /**
     * Global Signal should have a Timeout value when 'Timeout Signal After'
     * option is selected.
     * 
     * @param scope
     * @param gs
     */
    private void validateSignalForTimeout(IValidationScope scope,
            GlobalSignal gs) {

        if (gs.getCorrelationTimeout() != null) {

            if (BigInteger.ZERO.equals(gs.getCorrelationTimeout())) {

                addIssue(GLOBAL_SIGNAL_TIMEOUT_ISSUE, scope, gs, null);

            } else if (gs.getCorrelationTimeout().intValue() > GSDModelUtil.MAX_CORRELATION_TIMEOUT_VALUE) {

                addIssue(ISSUE_GLOBAL_SIGNAL_TIMEOUT_MUST_BE_LESS_THAN_24_HOURS,
                        scope,
                        gs,
                        null);
            }
        }
    }

    /**
     * Creates Issue of the given Id for the {@link GlobalSignal}.
     * 
     * @param issueId
     * @param scope
     * @param gs
     * @param label
     */
    private void addIssue(String issueId, IValidationScope scope,
            GlobalSignal gs, String label) {

        Map<String, String> additionalInfo = new HashMap<String, String>();

        additionalInfo.put(issueId, label);

        scope.createIssue(issueId,
                label,
                gs.eResource().getURIFragment(gs),
                Collections.singletonList(label),
                additionalInfo);
    }

    /**
     * @param id
     *            The ID of the issue.
     * @param o
     *            The object on which the issue occurred.
     * @param messages
     *            Messages to use in formatting error message text.
     * @param additionalInfo
     *            Additional info map.
     */
    protected void addIssue(String id, EObject o, List<String> messages,
            Map<String, String> additionalInfo, IValidationScope scope) {

        Resource resource = o.eResource();

        if (resource != null) {

            String uri = resource.getURIFragment(o);

            scope.createIssue(id, null, uri, messages, additionalInfo);

        }
    }

}