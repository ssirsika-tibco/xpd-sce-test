/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.contributions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.properties.general.AbstractFilteredPropertyTabProvider;

/**
 * Allows filtering of Process editor property tab visibility based on
 * contributions to the
 * <code>com.tibco.xpd.analyst.resources.xpdl2.processEditorConfiguration</code>
 * extenion point.
 * <p>
 * See {@link AbstractFilteredPropertyTabProvider} for more information.
 * 
 * @author aallway
 * @since 25 Nov 2011
 */
public class ProcessEditorPropertyTabProvider extends
        AbstractFilteredPropertyTabProvider {

    /**
     * Temporarily allocated between
     * {@link #prepareFilter(IWorkbenchPart, ISelection, String)} and
     * {@link #disposeFilter()} for use in
     * {@link #excludeTab(ITabDescriptor, IWorkbenchPart, ISelection, String)}
     */
    private Set<String> currentExcludedPropertyTabIds = null;

    /**
     * @param partSiteToPropertyContributerMaps
     */
    public ProcessEditorPropertyTabProvider() {
        super("com.tibco.xpd.processeditor.xpdl2.propertyContributor"); //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.AbstractFilteredPropertyTabProvider#excludeTab(org.eclipse.ui.views.properties.tabbed.ITabDescriptor,
     *      org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection,
     *      java.lang.String)
     * 
     * @param tabDescriptor
     * @param part
     * @param selection
     * @param propertyContributorId
     * @return
     */
    @Override
    protected boolean excludeTab(ITabDescriptor tabDescriptor,
            IWorkbenchPart part, ISelection selection,
            String propertyContributorId) {

        if (currentExcludedPropertyTabIds != null) {
            return currentExcludedPropertyTabIds
                    .contains(tabDescriptor.getId());
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.AbstractFilteredPropertyTabProvider#prepareFilter(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection, java.lang.String)
     * 
     * @param part
     * @param selection
     * @param propertyContributorId
     */
    @Override
    protected void prepareFilter(IWorkbenchPart part, ISelection selection,
            String propertyContributorId) {
        /*
         * Create set of excluded propertyTab id's for the selected objects.
         */
        currentExcludedPropertyTabIds = new HashSet<String>();

        if (selection instanceof IStructuredSelection) {
            IStructuredSelection ss = (IStructuredSelection) selection;

            for (Iterator iterator = ss.iterator(); iterator.hasNext();) {
                Object object = iterator.next();

                Set<String> excludedPropertyTabIds =
                        ProcessEditorConfigurationUtil
                                .getExcludedPropertyTabIds(object);
                if (excludedPropertyTabIds != null
                        && excludedPropertyTabIds.size() > 0) {
                    currentExcludedPropertyTabIds
                            .addAll(excludedPropertyTabIds);
                }
            }
        }

    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.AbstractFilteredPropertyTabProvider#disposeFilter()
     * 
     */
    @Override
    protected void disposeFilter() {
        currentExcludedPropertyTabIds = null;
    }
}
