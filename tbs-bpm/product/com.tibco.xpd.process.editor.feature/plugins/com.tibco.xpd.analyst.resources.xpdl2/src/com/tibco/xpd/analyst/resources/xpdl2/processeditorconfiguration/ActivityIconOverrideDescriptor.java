package com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.ExpressionTagNames;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.xpdl2.resources.IActivityIconProvider;

/**
 * Wrapper for the ActivityIconOverrides Extension contribution.
 * <p>
 * This allows us to keep all contributions cached so that (when enabled) the
 * same ActivityIconPoviderWrapper classes are returned therefore, on model
 * change, the process editor will be able to compare the previous set of
 * providers with the set of providers according to the new model state and
 * decide whether a complete refresh is required.
 * <p>
 * It acts as a place holder for the list of activity icon provider wrappers and
 * the associated enable for those.
 * 
 * @author aallway
 * @since 8 Dec 2011
 */
class ActivityIconOverrideDescriptor {

    static final String ACTIVITY_ICON_OVERRIDES_ELEMENT =
            "ActivityIconOverrides"; //$NON-NLS-1$

    static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$

    static final String ACTIVITY_ICON_PROVIDER_ELEMENT = "ActivityIconProvider"; //$NON-NLS-1$

    private List<ActivityIconProviderDescriptor> iconProviderDescriptors =
            new ArrayList<ActivityIconProviderDescriptor>();

    private Expression enablementExpression;

    private String contributorName;

    /**
     * @param activityOverrideElement
     * 
     * @throws CoreException
     */
    ActivityIconOverrideDescriptor(IConfigurationElement activityOverrideElement)
            throws CoreException {

        contributorName = activityOverrideElement.getContributor().getName();

        IConfigurationElement[] enablements =
                activityOverrideElement
                        .getChildren(ExpressionTagNames.ENABLEMENT);

        if (enablements == null || enablements.length != 1) {
            throw new IllegalStateException(
                    String.format("processEditorConfiguration/ActivityIconOverrides contribution must have exactly 1 enablement element (contributor:%s)", //$NON-NLS-1$
                            contributorName));
        }

        enablementExpression =
                ExpressionConverter.getDefault().perform(enablements[0]);

        iconProviderDescriptors =
                new ArrayList<ActivityIconProviderDescriptor>();

        loadActivityIconProviderElements(activityOverrideElement,
                iconProviderDescriptors);
    }

    /**
     * @return the iconProviderDescriptors
     */
    List<ActivityIconProviderDescriptor> getIconProviderDescriptors() {
        return iconProviderDescriptors;
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

    /**
     * @param configElement
     * @param iconProviderWrappers
     */
    private static void loadActivityIconProviderElements(
            IConfigurationElement configElement,
            List<ActivityIconProviderDescriptor> iconProviderWrappers)
            throws IllegalStateException {

        /*
         * Gather all the ActivityIconProviders
         */
        IConfigurationElement[] iconProviderElements =
                configElement.getChildren(ACTIVITY_ICON_PROVIDER_ELEMENT);

        if (iconProviderElements != null) {
            for (IConfigurationElement iconProviderElement : iconProviderElements) {
                try {
                    Object iconProvider =
                            iconProviderElement
                                    .createExecutableExtension(CLASS_ATTRIBUTE);
                    if ((iconProvider instanceof IActivityIconProvider)) {
                        /*
                         * create a wrapper to hold the contributed icon
                         * provider and list of obejct types it applies to.
                         */
                        ActivityIconProviderDescriptor iconProviderWrapper =
                                new ActivityIconProviderDescriptor(
                                        (IActivityIconProvider) iconProvider);

                        ProcessEditorConfigurationUtil
                                .loadObjectTypeElements(iconProviderElement,
                                        iconProviderWrapper.objectTypes);

                        iconProviderWrappers.add(iconProviderWrapper);

                    } else {
                        throw new IllegalStateException(
                                String.format("Invalid IActivityIconProvider class from contributor: %s", //$NON-NLS-1$
                                        iconProviderElement.getContributor()
                                                .getName()));

                    }

                } catch (CoreException e) {
                    throw new IllegalStateException(
                            String.format("Can't load IActivityIconProvider from contributor: %s", //$NON-NLS-1$
                                    iconProviderElement.getContributor()
                                            .getName()));
                }
            } /* Next icon provider element */
        }
    }
}