package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.CatchThrowFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventType;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;

/**
 * Filter for Intermediate Throw events.
 * 
 * @author scrossle, mmaciuki, nwilson
 */
public class IntermediateThrowMappingOneWayFilter implements IFilter,
        IPluginContribution {

    private IFilter filter;

    public IntermediateThrowMappingOneWayFilter() {
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;
        CatchThrowFilter catchThrowFilter =
                viewerFilterBuilder.getCatchThrowFilter();
        IFilter cTFilter = catchThrowFilter.hasCatchThrowType(CatchThrow.THROW);

        // Intermediate Throw message event
        EventViewerFilter eventViewerFilter =
                viewerFilterBuilder.getEventFilter();
        IFilter wsEventFilter =
                eventViewerFilter
                        .isImplementedBy(ImplementationType.WEB_SERVICE_LITERAL);
        IFilter eventTypeFilter =
                eventViewerFilter.hasType(EventType.INTERMEDIATE);
        IFilter triggerResultMessageFilter =
                eventViewerFilter.hasTriggerResultMessage();
        IFilter eventFilter =
                viewerFilterBuilder.getComposite(triggerResultMessageFilter,
                        wsEventFilter,
                        eventTypeFilter);
        IFilter requestFilter = new IFilter() {

            @Override
            public boolean select(Object toTest) {
                boolean result = false;

                EObject eo = null;
                if (toTest instanceof EObject) {
                    eo = (EObject) toTest;
                } else if (toTest instanceof IAdaptable) {
                    eo =
                            (EObject) ((IAdaptable) toTest)
                                    .getAdapter(EObject.class);
                }

                if (eo instanceof Activity) {
                    Activity a = (Activity) eo;
                    result = !ReplyActivityUtil.isReplyActivity(a);
                }
                return result;
            }

        };

        IFilter grammerCheckFilter = createGrammarFilter(eventViewerFilter);

        IFilter eventCatchFilter =
                viewerFilterBuilder.getComposite(ViewerFilterBuilder.AND,
                        eventFilter,
                        cTFilter,
                        requestFilter,
                        grammerCheckFilter);

        filter = eventCatchFilter;
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

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
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
        return "developer.activityMessageMapperIn"; //$NON-NLS-1$
    }

    /**
     * Contributing plug-in identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     */
    @Override
    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }

}