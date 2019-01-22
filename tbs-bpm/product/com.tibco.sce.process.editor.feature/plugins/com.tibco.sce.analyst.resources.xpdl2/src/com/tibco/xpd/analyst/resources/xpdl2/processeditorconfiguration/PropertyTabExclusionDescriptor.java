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
 * Wrapper for a process editor PropertyTabExclusion contribution.
 * 
 * @author aallway
 * @since 12 Dec 2011
 */
class PropertyTabExclusionDescriptor {

    static final String PROPERTY_TAB_EXCLUSIONS_ELEMENT =
            "PropertyTabExclusions"; //$NON-NLS-1$

    private static final String PROPERTY_TAB_ELEMENT = "PropertyTab"; //$NON-NLS-1$

    private static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    private Set<String> excludedPropertyTabIds = new HashSet<String>();

    private Expression enablementExpression;

    private String contributorName;

    /**
     * 
     * @param propertyTabExclusionElement
     * @throws CoreException
     */
    public PropertyTabExclusionDescriptor(
            IConfigurationElement propertyTabExclusionElement)
            throws CoreException {
        contributorName =
                propertyTabExclusionElement.getContributor().getName();

        IConfigurationElement[] enablements =
                propertyTabExclusionElement
                        .getChildren(ExpressionTagNames.ENABLEMENT);

        if (enablements == null || enablements.length != 1) {
            throw new IllegalStateException(
                    String.format("processEditorConfiguration/PropertyTabExclusions contribution must have exactly 1 enablement element (contributor:%s)", //$NON-NLS-1$
                            contributorName));
        }

        enablementExpression =
                ExpressionConverter.getDefault().perform(enablements[0]);

        excludedPropertyTabIds = new HashSet<String>();
        loadExcludedPropertyTabElements(propertyTabExclusionElement,
                excludedPropertyTabIds);

    }

    /**
     * @param configElement
     * @param excludedPropertyTabIds
     */
    private static void loadExcludedPropertyTabElements(
            IConfigurationElement configElement,
            Set<String> excludedPropertyTabIds) {
        /*
         * Gather all the excluded property tab ids from this contribution
         */
        IConfigurationElement[] objectTypes =
                configElement.getChildren(PROPERTY_TAB_ELEMENT);

        if (objectTypes != null) {
            for (IConfigurationElement objectType : objectTypes) {
                String propertyTabId = objectType.getAttribute(ID_ATTRIBUTE);

                if (propertyTabId != null && propertyTabId.length() > 0) {
                    excludedPropertyTabIds.add(propertyTabId);
                }
            }
        }
    }

    /**
     * @return the excludedPropertyTabIds
     */
    Set<String> getExcludedPropertyTabIds() {
        return excludedPropertyTabIds;
    }

    /**
     * @param evaluationContext
     * 
     * @return true if this ActivityIconOverride contribution is enabled.
     */
    boolean isEnabled(EvaluationContext evaluationContext) {
        EvaluationResult result;
        try {
            result = enablementExpression.evaluate(evaluationContext);
            if (EvaluationResult.TRUE.equals(result)) {
                return true;
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