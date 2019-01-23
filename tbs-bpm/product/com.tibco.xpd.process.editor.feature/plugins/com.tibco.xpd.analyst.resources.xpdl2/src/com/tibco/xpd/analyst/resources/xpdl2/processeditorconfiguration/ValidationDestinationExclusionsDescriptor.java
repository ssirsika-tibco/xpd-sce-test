package com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.ExpressionTagNames;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;

/**
 * Wrapper for a process editor ValidationDestinationExclusions contribution.
 * 
 * @author aallway
 * @since 12 Dec 2011
 */
class ValidationDestinationExclusionsDescriptor {

    static final String VALIDATION_DESTINATION_EXCLUSIONS_ELEMENT =
            "ValidationDestinationExclusions"; //$NON-NLS-1$

    private static final String VALIDATION_DEST_EXCLUSION_ELEMENT =
            "ValidationDestinationExclusion"; //$NON-NLS-1$

    private static final String VALIDATION_DEST_ID_ATTRIBUTE =
            "validationDestinationId"; //$NON-NLS-1$

    private static final String SPECIFIC_VALIDATION_PROVIDER_ID_ATTRIBUTE =
            "specificValidationProviderId"; //$NON-NLS-1$

    private static final String ALL_PROVIDERS_ID = "$$_ALL_PROVIDERS_$$"; //$NON-NLS-1$

    /**
     * The set of excluded validation destinations and providers. Each string in
     * the set is compiled from the contribution's specified validation
     * destination id and optional validation-provider id...
     * <p>
     * <validation-destination-id>::<validation-provider-id |
     * $$_ALL_PROVIDERS_$$>
     * 
     * */
    private Set<String> excludedValidationDestinations = new HashSet<String>();

    private Expression enablementExpression;

    private String contributorName;

    /**
     * 
     * @param elementTypeExclusionsElement
     * @throws CoreException
     */
    public ValidationDestinationExclusionsDescriptor(
            IConfigurationElement elementTypeExclusionsElement)
            throws CoreException {
        contributorName =
                elementTypeExclusionsElement.getContributor().getName();

        IConfigurationElement[] enablements =
                elementTypeExclusionsElement
                        .getChildren(ExpressionTagNames.ENABLEMENT);

        if (enablements == null || enablements.length != 1) {
            throw new IllegalStateException(
                    String.format("processEditorConfiguration/ValidationDestinationExclusions contribution must have exactly 1 enablement element (contributor:%s)", //$NON-NLS-1$
                            contributorName));
        }

        enablementExpression =
                ExpressionConverter.getDefault().perform(enablements[0]);

        loadValidationExclusionElements(elementTypeExclusionsElement);
    }

    /**
     * @param elementTypeExclusionElement
     */
    private void loadValidationExclusionElements(
            IConfigurationElement elementTypeExclusionElement) {

        excludedValidationDestinations = new HashSet<String>();

        /*
         * Gather all the excluded elements types from this contribution
         */
        IConfigurationElement[] validationExclusions =
                elementTypeExclusionElement
                        .getChildren(VALIDATION_DEST_EXCLUSION_ELEMENT);

        if (validationExclusions != null) {
            for (IConfigurationElement objectType : validationExclusions) {
                String validationDestinationId =
                        objectType.getAttribute(VALIDATION_DEST_ID_ATTRIBUTE);

                if (validationDestinationId != null
                        && validationDestinationId.length() > 0) {

                    String validationProviderId =
                            objectType
                                    .getAttribute(SPECIFIC_VALIDATION_PROVIDER_ID_ATTRIBUTE);

                    excludedValidationDestinations
                            .add(buildExcludedValidationId(validationDestinationId,
                                    validationProviderId));
                }
            }
        }
    }

    private String buildExcludedValidationId(String validationDestinationId,
            String validationProviderId) {
        if (validationProviderId == null
                || validationProviderId.trim().length() == 0) {
            validationProviderId = ALL_PROVIDERS_ID;
        }
        return validationDestinationId + "::" + validationProviderId; //$NON-NLS-1$
    }

    /**
     * @param evaluationContext
     * @param validationDestinationId
     * @param validationProviderId
     * 
     * @return <code>true</code> if this contribution is enabled and the given
     *         validation destination / provider is excluded by it.
     */
    boolean isExcludedValidationDestinationAndProvider(
            EvaluationContext evaluationContext,
            String validationDestinationId, String validationProviderId) {
        EvaluationResult result;
        try {
            result = enablementExpression.evaluate(evaluationContext);
            if (EvaluationResult.TRUE.equals(result)) {

                /*
                 * This contribution is enabled. First of all check to see if
                 * there are any contributions for ALL providers of the given
                 * destination.
                 */
                String allProvidersKey =
                        buildExcludedValidationId(validationDestinationId, null);

                if (excludedValidationDestinations.contains(allProvidersKey)) {
                    /*
                     * don't care about the specific validaiton provider,
                     * someone has disabled whole destination.
                     */
                    return true;
                }

                /*
                 * Then if not all providers are excluded, then check for this
                 * specific one.
                 */
                String specificProviderKey =
                        buildExcludedValidationId(validationDestinationId,
                                validationProviderId);
                if (excludedValidationDestinations
                        .contains(specificProviderKey)) {
                    return true;
                }
            }

        } catch (CoreException e) {
            Xpdl2ResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            this.getClass().getSimpleName()
                                    + ": Error evaluating enablement element from contribution from: " //$NON-NLS-1$
                                    + contributorName);
        }

        return false;
    }

}