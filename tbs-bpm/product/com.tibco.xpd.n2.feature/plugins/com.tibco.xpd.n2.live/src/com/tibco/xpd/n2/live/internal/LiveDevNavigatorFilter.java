package com.tibco.xpd.n2.live.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.ExpressionTagNames;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * This is a ViewerFilter that is applied to the project explorer to support
 * live dev filtering. If the live dev perpective is active it will filter out
 * all items unless they are specifically included by a live dev extension
 * point.
 * 
 * @author nwilson
 * @since 2 Sep 2014
 */
public class LiveDevNavigatorFilter extends ViewerFilter {

    /**
     * The live dev extension point ID.
     */
    private static final String EXT_POINT_ID = "LiveDevelopment"; //$NON-NLS-1$

    /**
     * Inclusion expressions derived from any extension point contributions.
     */
    private Collection<Expression> explorerIncludes;

    /**
     * Apply the contributed expressions to a viewer element to determine if it
     * should be included. If any expression returns true then the item will be
     * included.
     * 
     * @see org.eclipse.jface.viewers.ViewerFilter
     *      #select(org.eclipse.jface.viewers.Viewer, java.lang.Object,
     *      java.lang.Object)
     * 
     * @param viewer
     *            The viewer to which the filter applies.
     * @param parentElement
     *            The parent of the element being considered.
     * @param element
     *            The element to consider.
     * @return true to include the element, false to exclude.
     */
    @Override
    public boolean select(Viewer viewer, Object parentElement, Object element) {
        boolean include = true;
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null) {
            IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
            if (window != null) {
                if (Activator.getDefault().isLiveDevFilterOn(window)) {
                    include = false;
                    if (explorerIncludes == null) {
                        init();
                    }
                    EvaluationContext ctx =
                            new EvaluationContext(null, element);
                    for (Expression explorerInclude : explorerIncludes) {
                        try {
                            EvaluationResult result =
                                    explorerInclude.evaluate(ctx);
                            if (EvaluationResult.TRUE.equals(result)) {
                                include = true;
                                break;
                            }
                        } catch (CoreException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return include;
    }

    /**
     * One-time initialisation of the explorerIncludes collection. This method
     * reads any extension point contributions and converts them to expressions
     * for use by the filter.
     */
    private void init() {
        explorerIncludes = new ArrayList<>();
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Activator.PLUGIN_ID, EXT_POINT_ID);
        if (point != null) {
            IConfigurationElement[] contributions =
                    point.getConfigurationElements();

            for (IConfigurationElement contribution : contributions) {
                if ("explorerInclude".equals(contribution.getName())) { //$NON-NLS-1$
                    IConfigurationElement[] enablements =
                            contribution
                                    .getChildren(ExpressionTagNames.ENABLEMENT);
                    if (enablements != null) {
                        for (IConfigurationElement enablement : enablements) {

                            try {
                                Expression expression =
                                        ExpressionConverter.getDefault()
                                                .perform(enablement);
                                explorerIncludes.add(expression);
                            } catch (CoreException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        }
    }
}
