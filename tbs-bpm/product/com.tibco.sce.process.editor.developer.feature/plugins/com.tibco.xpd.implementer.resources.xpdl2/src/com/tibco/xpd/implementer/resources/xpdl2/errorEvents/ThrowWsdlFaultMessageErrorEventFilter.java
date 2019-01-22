/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.errorEvents;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;

/**
 * Filter for catch error intermediate events for throw fault message end Error
 * Events that throw faults of operation of associated request activity.
 * 
 * @author aallway
 * @since 3.3
 */
public class ThrowWsdlFaultMessageErrorEventFilter implements IFilter,
        IPluginContribution {
    private IFilter filter;

    /**
     * 
     */
    public ThrowWsdlFaultMessageErrorEventFilter() {
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;

        EventViewerFilter eventViewerFilter =
                viewerFilterBuilder.getEventFilter();

        IFilter throwFaultErrorEventCheckFilter = new IFilter() {

            @Override
            public boolean select(Object toTest) {
                if (!CapabilityUtil.isDeveloperActivityEnabled()) {
                    return false;
                }

                EObject eo = null;
                boolean result = false;
                if (toTest instanceof EObject) {
                    eo = (EObject) toTest;
                } else if (toTest instanceof IAdaptable) {
                    eo =
                            (EObject) ((IAdaptable) toTest)
                                    .getAdapter(EObject.class);
                }

                // Check it's a catch Error intermediate event.
                if (eo != null && eo instanceof Activity) {
                    Activity activity = (Activity) eo;

                    if (ThrowErrorEventUtil
                            .isThrowFaultMessageErrorEvent(activity)) {
                        result = true;
                    }
                }

                return result;
            }
        };

        IFilter grammerCheckFilter = createGrammarFilter(eventViewerFilter);

        IFilter eventFilter =
                viewerFilterBuilder
                        .getComposite(throwFaultErrorEventCheckFilter,
                                grammerCheckFilter);

        // Filter
        filter = eventFilter;
    }

    /**
     * Return {@link IFilter} to check applicable script grammar types.
     * 
     * @param taskFilter
     * @return {@link IFilter}
     */
    protected IFilter createGrammarFilter(EventViewerFilter eventViewerFilter) {
        IFilter grammerCheckFilter =
                eventViewerFilter.hasScriptGrammar(DirectionType.IN_LITERAL,
                        ScriptGrammarFactory.JAVASCRIPT,
                        ScriptGrammarFactory.XPATH);
        return grammerCheckFilter;
    }

    @Override
    public boolean select(Object toTest) {
        // Activities based filtering
        IPluginContribution pluginContributon = this;
        if (PlatformUI.isWorkbenchRunning() && pluginContributon != null
                && WorkbenchActivityHelper.filterItem(pluginContributon)) {
            return false;
        }

        boolean result = filter.select(toTest);
        return result;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    @Override
    public String getLocalId() {
        return "developer.throwWsdlErrorMapperOut"; //$NON-NLS-1$
    }

    /**
     * Contributing plug-in identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     */
    @Override
    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

}
