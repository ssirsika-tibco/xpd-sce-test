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
 * Wrapper for a process editor ElementTypeExclusions contribution.
 * 
 * @author aallway
 * @since 12 Dec 2011
 */
class ElementTypeExclusionDescriptor {

    static final String ELEMENT_TYPE_EXCLUSIONS_ELEMENT =
            "ElementTypeExclusions"; //$NON-NLS-1$

    private static final String ELEMENT_TYPE_ELEMENT = "ElementType"; //$NON-NLS-1$

    private static final String TYPE_ATTRIBUTE = "type"; //$NON-NLS-1$

    private Set<ProcessEditorElementType> excludedElementTypes =
            new HashSet<ProcessEditorElementType>();

    private Expression enablementExpression;

    private String contributorName;

    /**
     * 
     * @param elementTypeExclusionElement
     * @throws CoreException
     */
    public ElementTypeExclusionDescriptor(
            IConfigurationElement elementTypeExclusionElement)
            throws CoreException {
        contributorName =
                elementTypeExclusionElement.getContributor().getName();

        IConfigurationElement[] enablements =
                elementTypeExclusionElement
                        .getChildren(ExpressionTagNames.ENABLEMENT);

        if (enablements == null || enablements.length != 1) {
            throw new IllegalStateException(
                    String.format("processEditorConfiguration/ElementTypeExclusions contribution must have exactly 1 enablement element (contributor:%s)", //$NON-NLS-1$
                            contributorName));
        }

        enablementExpression =
                ExpressionConverter.getDefault().perform(enablements[0]);

        excludedElementTypes = new HashSet<ProcessEditorElementType>();

        loadElementTypeElements(elementTypeExclusionElement,
                excludedElementTypes);
    }

    /**
     * @param elementTypeExclusionElement
     * @param excludedElementTypes
     */
    private void loadElementTypeElements(
            IConfigurationElement elementTypeExclusionElement,
            Set<ProcessEditorElementType> excludedElementTypes) {
        /*
         * Gather all the excluded elements types from this contribution
         */
        IConfigurationElement[] objectTypes =
                elementTypeExclusionElement.getChildren(ELEMENT_TYPE_ELEMENT);

        if (objectTypes != null) {
            for (IConfigurationElement objectType : objectTypes) {
                String type = objectType.getAttribute(TYPE_ATTRIBUTE);

                if (type != null && type.length() > 0) {
                    ProcessEditorElementType typeEnum = null;
                    try {
                        typeEnum = ProcessEditorElementType.valueOf(type);
                    } catch (Exception e) {
                    }

                    if (typeEnum != null) {
                        excludedElementTypes.add(typeEnum);

                    } else {
                        Xpdl2ResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(String.format("Unrecognised ElementType %1$s' specified for exclusion by contributor: %2$s", //$NON-NLS-1$
                                        type,
                                        contributorName));
                    }
                }
            }
        }

    }

    /**
     * @return the excludedElementTypes
     */
    public Set<ProcessEditorElementType> getExcludedElementTypes() {
        return excludedElementTypes;
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