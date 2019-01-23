/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.properties.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyRegistry;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptorProvider;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * This class allows Studio to filter Property Tabs given editors/views via the
 * <code>org.eclipse.ui.views.properties.tabbed.propertyContributor / tabDescriptorProvider</code>
 * extension point ability.
 * <p>
 * The problem was that there is no direct filtering on tabs in eclipse property
 * view/tab framework. Instead it filters out tabs that do not have any section
 * contributed after those section's filters have been executed. So each of the
 * propertySection filters are executed for selection and the list of sections
 * for each tab is built up - if that list is empty then the tab is not
 * displayed.
 * <p>
 * For the purpose of allowing other dev team's to switch tab's this is much too
 * fine grained and would cause problems if new sections were added to property
 * tabs by process dev team.
 * <p>
 * So a subclass of this class can be contributed as a tabDescriptorProvider.
 * This class is then used by the {@link TabbedPropertySheetPage} via the
 * {@link TabbedPropertyRegistry} which detects the presence of this
 * tabDescriptor are asks it to provide the list of tabs (bypassing the normal
 * mechanism above). <b>In order not re re-invent or re-write the entire
 * contribution mechanism</b> this {@link AbstractFilteredPropertyTabProvider}
 * class delegates provision of the list of tabs to <b>our own subclass of
 * {@link TabbedPropertyRegistry} ({@link FilteredTabbedPropertyRegistry})</b>.
 * This is the only way which we can guarantee accessing of the contributed tabs
 * and sections will be the same as eclipse's own mechanism.
 * <p>
 * Obviously we do not want our subclass {@link FilteredTabbedPropertyRegistry}
 * to completely use the standard mechanism (as that would load this
 * tabDecriptorProvider class again and we'd recurs infinitely. However having
 * sub-classed TabbedPropertyRegistry we have access to it's
 * {@link TabbedPropertyRegistry#getAllTabDescriptors} which will bypass loading
 * of the tabDescriptor provider. So...
 * 
 * <pre>
 * TabbedPropertySheetPage:
 *    Creates TabbedPropertyRegistry for propertyContributorId of the View/EditorPart:
 *      TabberPropertyRegistry loads this AbstractFilteredPropertyTabProvider
 *                       (because its set in propertyContributer extension):
 *          This AbstractFilteredPropertyTabProvider uses the given propertyContributorId
 *              to Construct our own {@link FilteredTabbedPropertyRegistry} class which
 *              has access to the property tab/section contributions as usual and allows
 *              this AbstractFilteredPropertyTabProvider access to all the contribtued
 * tabs/sections.
 * 
 * <pre>
 * 
 * 
 * <p>
 * Please note that although in theory it is works to do this sort of thing for
 * the project explorer view, we do not control it's propertyContributor
 * extension point contribution and therefore although we can make our own
 * propertyContributor contribution giving the same contributor id as
 * projectExplorer and eclipse will load our contribution and project
 * explorer's, it is indeterminate whose tabDescriptorProvider will be used - so
 * if eclipse team adds one to their project explorer propertyContributor
 * extension then it could scupper ours!
 * 
 * @author aallway
 * @since 24 Nov 2011
 */
@SuppressWarnings("restriction")
public abstract class AbstractFilteredPropertyTabProvider implements
        ITabDescriptorProvider {

    /**
     * Cache of our delegate filtered tabbed property registries for each
     * property contributor id.
     */
    private static Map<String, FilteredTabbedPropertyRegistry> tabRegistryCache =
            new HashMap<String, AbstractFilteredPropertyTabProvider.FilteredTabbedPropertyRegistry>();

    private String propertyContributorId;

    /**
     * @param propertyContributorId
     */
    public AbstractFilteredPropertyTabProvider(String propertyContributorId) {
        super();
        this.propertyContributorId = propertyContributorId;
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabDescriptorProvider#getTabDescriptors(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     * @return
     */
    @Override
    public ITabDescriptor[] getTabDescriptors(IWorkbenchPart part,
            ISelection selection) {

        /*
         * See if we have tab registry cached for this contributor id.
         */
        FilteredTabbedPropertyRegistry tabbedPropertyRegistry =
                tabRegistryCache.get(propertyContributorId);
        if (tabbedPropertyRegistry == null) {
            tabbedPropertyRegistry =
                    new FilteredTabbedPropertyRegistry(propertyContributorId);

            tabRegistryCache.put(propertyContributorId, tabbedPropertyRegistry);
        }

        /*
         * Return the list of all tab descriptors according to the standard
         * criteria of the
         * org.eclipse.ui.views.properties.tabbed.propertyContributor extension
         * point BUT filtered according to the sub-class's requirements.
         */
        return tabbedPropertyRegistry.getAllTabDescriptors(part,
                selection,
                propertyContributorId);
    }

    /**
     * Called once before set of tab descriptor filtering (i.e. an object
     * selection).
     * <p>
     * Allows caching of filtering info that can be reused for each call to
     * {@link #excludeTab(ITabDescriptor, IWorkbenchPart, ISelection, String)}
     * 
     * @param part
     * @param selection
     * @param propertyContributorId
     */
    protected abstract void prepareFilter(IWorkbenchPart part,
            ISelection selection, String propertyContributorId);

    /**
     * Called once afterset of tab descriptor filtering (i.e. an object
     * selection).
     * 
     */
    protected abstract void disposeFilter();

    /**
     * @param tabDescriptor
     * @param propertyContributorId
     * @param selection
     * @param part
     * @return <code>true</code> to exclude tab
     */
    protected abstract boolean excludeTab(ITabDescriptor tabDescriptor,
            IWorkbenchPart part, ISelection selection,
            String propertyContributorId);

    /**
     * Our own tabbed property registry - we extend
     * {@link TabbedPropertyRegistry} <b>only</b> so that we can access the
     * {@link TabbedPropertyRegistry#getAllTabDescriptors()} method.
     * 
     * 
     * @author aallway
     * @since 25 Nov 2011
     */
    private class FilteredTabbedPropertyRegistry extends TabbedPropertyRegistry {

        /**
         * @param id
         */
        @SuppressWarnings("restriction")
        protected FilteredTabbedPropertyRegistry(String id) {
            super(id);
        }

        /**
         * 
         * @param part
         * @param selection
         * @param propertyContributorId
         * 
         * @return Filtered list of tab descriptors (based on parent class'
         *         includeTab() method.
         */
        @SuppressWarnings("restriction")
        private ITabDescriptor[] getAllTabDescriptors(IWorkbenchPart part,
                ISelection selection, String propertyContributorId) {

            ITabDescriptor[] tabDescs = getAllTabDescriptors();

            if (tabDescs != null) {
                List<ITabDescriptor> filteredTabDescs =
                        new ArrayList<ITabDescriptor>();

                prepareFilter(part, selection, propertyContributorId);

                for (ITabDescriptor tabDescriptor : tabDescs) {

                    if (!excludeTab(tabDescriptor,
                            part,
                            selection,
                            propertyContributorId)) {
                        filteredTabDescs.add(tabDescriptor);
                    }
                }

                disposeFilter();

                tabDescs = filteredTabDescs.toArray(new ITabDescriptor[0]);
            }

            return tabDescs;
        }
    }

}
