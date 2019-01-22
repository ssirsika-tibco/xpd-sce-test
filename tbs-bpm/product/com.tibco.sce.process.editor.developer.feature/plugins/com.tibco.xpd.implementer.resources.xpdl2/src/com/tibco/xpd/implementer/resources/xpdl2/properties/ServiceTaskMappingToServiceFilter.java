package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter;
import com.tibco.xpd.implementer.resources.xpdl2.properties.filter.ViewerFilterBuilder;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ImplementationType;

/**
 * Filter for "Map To Service". "Map to Service" - data fields in left hand
 * column.
 * 
 * @author scrossle, mmaciuki
 */
public class ServiceTaskMappingToServiceFilter implements IFilter,
        IPluginContribution {

    private IFilter filter;

    public ServiceTaskMappingToServiceFilter() {
        /*
         * "Map to service" filter will accept: - service task implemented by
         * webservice
         */
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;
        TaskViewerFilter taskFilter = viewerFilterBuilder.getTaskFilter();
        IFilter implFilter =
                taskFilter
                        .isImplementedBy(ImplementationType.WEB_SERVICE_LITERAL);
        IFilter typeFilter = taskFilter.hasType(TaskType.SERVICE_LITERAL);

        IFilter grammerCheckFilter = createGrammarFilter(taskFilter);
        filter =
                viewerFilterBuilder.getComposite(implFilter,
                        typeFilter,
                        grammerCheckFilter);
    }

    /**
     * Return {@link IFilter} to check applicable script grammar types.
     * 
     * @param taskFilter
     * @return {@link IFilter}
     */
    protected IFilter createGrammarFilter(TaskViewerFilter taskFilter) {
        IFilter grammerCheckFilter =
                taskFilter.hasScriptGrammar(DirectionType.IN_LITERAL,
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
        return "developer.activityMessageMapperTo"; //$NON-NLS-1$
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