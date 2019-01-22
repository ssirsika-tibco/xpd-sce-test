/**
 * ProcessElementsQuickSearchContentProvider.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.quickSearchContribution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.ui.IWorkbenchPartReference;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ProcessResourceItemType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.ui.api.quicksearch.AbstractQuickSearchContentProvider;
import com.tibco.xpd.resources.ui.api.quicksearch.QuickSearchPopupCategory;

/**
 * ProcessElementsQuickSearchContentProvider
 * 
 */
public class ProcessElementsQuickSearchContentProvider extends
        AbstractQuickSearchContentProvider {

    private static final String PROCESS_ELEMENTS_QS_CATEGORY_ID =
            "projectExplorer.processelements.category"; //$NON-NLS-1$

    private static final String PACKAGES_QS_CATEGORY_ID =
            "projectExplorer.packagesbyname.category"; //$NON-NLS-1$

    private static final String PROCESSES_QS_CATEGORY_ID =
            "projectExplorer.processes.category"; //$NON-NLS-1$

    private static final String PROCESSINTEFACES_QS_CATEGORY_ID =
            "projectExplorer.processinterfaces.category"; //$NON-NLS-1$

    private static final String PAGEFLOW_QS_CATEGORY_ID =
            "projectExplorer.pageflows.category"; //$NON-NLS-1$

    private Collection<IndexerItem> availableElements;

    /**
     * @param partRef
     */
    public ProcessElementsQuickSearchContentProvider(
            IWorkbenchPartReference partRef) {
        super(partRef);

        // Cache all the currently available elements now because (a) this
        // looks providers up from extension point and (b) we can get away
        // with it because the content cannot change without search popup
        // being disabled and therefore we will get thrown away when that
        // happens.

        // long start = System.currentTimeMillis();

        availableElements = ProcessUIUtil.getAllProcessIndexerItems();

        /*
         * System.out .println("ProcessElementsQuickSearchContentProvider took "
         * + (System.currentTimeMillis() - start) + " ms to get indexed items");
         * //$NON-NLS-1$ //$NON-NLS-2$
         */
    }

    @Override
    public void dispose() {
    }

    @Override
    public Collection<QuickSearchPopupCategory> getCategories() {
        List<QuickSearchPopupCategory> cats =
                new ArrayList<QuickSearchPopupCategory>();

        QuickSearchPopupCategory procElementsCat =
                new QuickSearchPopupCategory(
                        PROCESS_ELEMENTS_QS_CATEGORY_ID,
                        Messages.ProcessElementsQuickSearchContentProvider_ProcRelatedElements_label);
        cats.add(procElementsCat);

        procElementsCat
                .addChild(new QuickSearchPopupCategory(
                        PACKAGES_QS_CATEGORY_ID,
                        Messages.ProcessElementsQuickSearchContentProvider_PackagesByName_label));

        procElementsCat
                .addChild(new QuickSearchPopupCategory(
                        PROCESSES_QS_CATEGORY_ID,
                        Messages.ProcessElementsQuickSearchContentProvider_Processes_label));
        procElementsCat
                .addChild(new QuickSearchPopupCategory(
                        PROCESSINTEFACES_QS_CATEGORY_ID,
                        Messages.ProcessElementsQuickSearchContentProvider_ProcInterfaces_label));

        procElementsCat
                .addChild(new QuickSearchPopupCategory(
                        PAGEFLOW_QS_CATEGORY_ID,
                        Messages.ProcessElementsQuickSearchContentProvider_ProcessElementsQuickSearchContentProvider_Pageflows_label));

        return cats;
    }

    @Override
    public Collection<?> getElements() {
        return availableElements;
    }

    @Override
    public Collection<?> getElements(
            Collection<QuickSearchPopupCategory> categories) {

        boolean wantProcesses = false;
        boolean wantInterfaces = false;
        boolean wantPackages = false;
        boolean wantPageflows = false;

        for (QuickSearchPopupCategory cat : categories) {
            if (PROCESSES_QS_CATEGORY_ID.equals(cat.getId())) {
                wantProcesses = true;
            } else if (PROCESSINTEFACES_QS_CATEGORY_ID.equals(cat.getId())) {
                wantInterfaces = true;
            } else if (PACKAGES_QS_CATEGORY_ID.equals(cat.getId())) {
                wantPackages = true;
            } else if (PAGEFLOW_QS_CATEGORY_ID.equals(cat.getId())) {
                wantPageflows = true;
            }
        }

        List<IndexerItem> retElements = new ArrayList<IndexerItem>();

        for (IndexerItem item : availableElements) {
            String type = item.getType();

            if (wantProcesses
                    && ProcessResourceItemType.PROCESS.toString().equals(type)) {
                retElements.add(item);
            } else if (wantInterfaces
                    && ProcessResourceItemType.PROCESSINTERFACE.toString()
                            .equals(type)) {
                retElements.add(item);
            } else if (wantPackages
                    && ProcessResourceItemType.PROCESSPACKAGE.toString()
                            .equals(type)) {
                retElements.add(item);
            } else if (wantPageflows
                    && ProcessResourceItemType.PAGEFLOW.toString().equals(type)) {
                retElements.add(item);
            }

        }

        return retElements;
    }

}
