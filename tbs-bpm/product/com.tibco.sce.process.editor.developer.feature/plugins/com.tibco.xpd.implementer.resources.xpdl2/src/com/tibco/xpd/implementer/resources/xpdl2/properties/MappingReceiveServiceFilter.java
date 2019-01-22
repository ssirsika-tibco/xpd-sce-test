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

public class MappingReceiveServiceFilter implements IFilter,
        IPluginContribution {

    protected IFilter filter;

    public MappingReceiveServiceFilter() {
        /*
         * Filter will accept: - receive task implemented by webservice - start
         * message event implemented by webservice - intermediate message event
         * implemented by webservice
         */

        // Receive Task
        ViewerFilterBuilder viewerFilterBuilder = ViewerFilterBuilder.INSTANCE;
        TaskViewerFilter taskFilterBuilder =
                viewerFilterBuilder.getTaskFilter();
        IFilter wsTaskFilter =
                taskFilterBuilder
                        .isImplementedBy(ImplementationType.WEB_SERVICE_LITERAL);
        IFilter typeFilter =
                taskFilterBuilder.hasType(TaskType.RECEIVE_LITERAL);

        IFilter grammerCheckFilter = createGrammarFilter(taskFilterBuilder);

        IFilter taskFilter =
                viewerFilterBuilder.getComposite(wsTaskFilter,
                        typeFilter,
                        grammerCheckFilter);

        // Filter
        filter = taskFilter;
    }

    /**
     * Return {@link IFilter} to check applicable script grammar types.
     * 
     * @param taskFilter
     * @return {@link IFilter}
     */
    protected IFilter createGrammarFilter(TaskViewerFilter taskFilterBuilder) {
        IFilter grammerCheckFilter =
                taskFilterBuilder.hasScriptGrammar(DirectionType.OUT_LITERAL,
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
