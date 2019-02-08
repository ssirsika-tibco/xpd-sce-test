package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventType;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.EventViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.ImplementationType;

/**
 * Static class to use as filter. Delegate to {@link ActivityMessageProvider} to
 * determine filter condition.
 *
 * @author scrossle
 */
public class MappingOutServiceFilter implements IFilter, IPluginContribution {

    private IFilter filter;

    public MappingOutServiceFilter() {
        /*
         * "Map to service" filter will accept: - receive task implemented by
         * webservice - end event implemented by web service
         */
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;
        TaskViewerFilter taskFilterBuilder =
                viewerFilterBuilder.getTaskFilter();
        IFilter wsTaskFilter = taskFilterBuilder
                .isImplementedBy(ImplementationType.WEB_SERVICE_LITERAL);
        IFilter typeFilter =
                taskFilterBuilder.hasType(TaskType.RECEIVE_LITERAL);
        IFilter taskFilter =
                viewerFilterBuilder.getComposite(wsTaskFilter, typeFilter);

        EventViewerFilter eventViewerFilter =
                viewerFilterBuilder.getEventFilter();
        IFilter wsEventFilter = eventViewerFilter
                .isImplementedBy(ImplementationType.WEB_SERVICE_LITERAL);
        IFilter eventTypeFilter = eventViewerFilter.hasType(EventType.START,
                EventType.INTERMEDIATE);
        IFilter triggerResultMessageFilter =
                eventViewerFilter.hasTriggerResultMessage();
        IFilter eventFilter =
                viewerFilterBuilder.getComposite(triggerResultMessageFilter,
                        wsEventFilter,
                        eventTypeFilter);

        filter = viewerFilterBuilder
                .getComposite(ViewerFilterBuilder.OR, taskFilter, eventFilter);
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
        if (pluginContributon != null
                && WorkbenchActivityHelper.filterItem(pluginContributon)) {
            return false;
        }

        boolean result = false;// filter.select(toTest);
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