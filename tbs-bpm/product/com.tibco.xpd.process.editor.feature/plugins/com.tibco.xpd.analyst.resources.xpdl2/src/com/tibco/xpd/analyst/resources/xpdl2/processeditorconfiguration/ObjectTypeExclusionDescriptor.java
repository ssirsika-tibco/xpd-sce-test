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
 * Wrapper for a process editor objectTypeExclusions contribution.
 * 
 * @author aallway
 * @since 12 Dec 2011
 */
class ObjectTypeExclusionDescriptor {

    static final String OBJECT_TYPE_EXCLUSIONS_ELEMENT = "ObjectTypeExclusions"; //$NON-NLS-1$

    private Set<ProcessEditorObjectType> excludedObjectTypes =
            new HashSet<ProcessEditorObjectType>();

    private Expression enablementExpression;

    private String contributorName;

    /**
     * 
     * @param objectTypeExclusionElement
     * @throws CoreException
     */
    public ObjectTypeExclusionDescriptor(
            IConfigurationElement objectTypeExclusionElement)
            throws CoreException {
        contributorName = objectTypeExclusionElement.getContributor().getName();

        IConfigurationElement[] enablements =
                objectTypeExclusionElement
                        .getChildren(ExpressionTagNames.ENABLEMENT);

        if (enablements == null || enablements.length != 1) {
            throw new IllegalStateException(
                    String.format("processEditorConfiguration/ObjectTypeExclusions contribution must have exactly 1 enablement element (contributor:%s)", //$NON-NLS-1$
                            contributorName));
        }

        enablementExpression =
                ExpressionConverter.getDefault().perform(enablements[0]);

        excludedObjectTypes = new HashSet<ProcessEditorObjectType>();

        ProcessEditorConfigurationUtil
                .loadObjectTypeElements(objectTypeExclusionElement,
                        excludedObjectTypes);
    }

    /**
     * @return the excludedObjectTypes
     */
    Set<ProcessEditorObjectType> getExcludedObjectTypes() {
        return excludedObjectTypes;
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