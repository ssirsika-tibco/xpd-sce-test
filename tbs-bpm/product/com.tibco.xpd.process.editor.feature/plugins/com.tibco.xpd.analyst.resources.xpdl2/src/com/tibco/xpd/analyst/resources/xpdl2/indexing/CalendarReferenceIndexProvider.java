/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.indexing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.indexer.IndexerItemImpl;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.xpdExtension.CalendarReference;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Indexer provider to index the Calendar References.
 * 
 */
public class CalendarReferenceIndexProvider extends
        AbstractXpdl2ResourceIndexProvider {

    private static final String XPDL_INDEXER_ID =
            "com.tibco.xpd.analyst.resources.xpdl2.indexing.calendarReference"; //$NON-NLS-1$

    private static final String ATT_ALIAS = "alias"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.indexing.AbstractXpdl2ResourceIndexProvider#addIndexedItemsForPackage(java.util.ArrayList,
     *      java.lang.String, java.lang.String, com.tibco.xpd.xpdl2.Package)
     * 
     * @param indexedItems
     * @param projectName
     * @param xpdlPath
     * @param pkg
     */
    @Override
    protected void addIndexedItemsForPackage(
            ArrayList<IndexerItem> indexedItems, String projectName,
            String xpdlPath, Package pkg) {

        for (Process process : pkg.getProcesses()) {
            // Check if Process has Calendar Reference
            CalendarReference reference = getCalendarReference(process);
            if (reference != null && reference.getAlias() != null
                    && !reference.getAlias().isEmpty()) {
                indexedItems.add(createIndexerItem(process, reference));
            }

            // Check if Trigger Timer events have a Calendar Reference
            for (Activity activity : Xpdl2ModelUtil
                    .getAllActivitiesInProc(process)) {
                TriggerTimer triggerTimer = getTriggerTimer(activity);
                if (triggerTimer != null) {
                    reference = getCalendarReference(triggerTimer);

                    if (reference != null && reference.getAlias() != null
                            && !reference.getAlias().isEmpty()) {
                        indexedItems
                                .add(createIndexerItem(activity, reference));
                    }
                }
            }
        }
    }

    /**
     * @see com.tibco.xpd.analyst.resources.xpdl2.indexing.AbstractXpdl2ResourceIndexProvider#shouldReIndexForObject(java.lang.Object,
     *      org.eclipse.emf.common.notify.Notification)
     * 
     * @param obj
     * @param notification
     * @return
     */
    @Override
    protected boolean shouldReIndexForObject(Object obj,
            Notification notification) {

        if (obj instanceof CalendarReference) {
            return true;
        } else if (obj instanceof Activity) {
            if (getTriggerTimer((Activity) obj) != null) {
                return true;
            }
        } else if (obj instanceof IntermediateEvent) {
            return true;
        }
        return false;
    }

    /**
     * Get the {@link TriggerTimer} implementation of this Activity.
     * 
     * @param eventAct
     * @return TriggerTimer if this is TriggerTimer activity, <code>null</code>
     *         otherwise.
     */
    private TriggerTimer getTriggerTimer(Activity eventAct) {
        Event event = eventAct.getEvent();
        TriggerTimer triggerTimer = null;
        if (event instanceof StartEvent) {
            triggerTimer = ((StartEvent) event).getTriggerTimer();
        } else if (event instanceof IntermediateEvent) {
            triggerTimer = ((IntermediateEvent) event).getTriggerTimer();
        }
        return triggerTimer;
    }

    /**
     * Get the CalendarReference from the given element.
     * 
     * @param container
     * @return {@link CalendarReference} or <code>null</code> if one is not
     *         defined.
     */
    private CalendarReference getCalendarReference(
            OtherElementsContainer container) {
        Object element =
                Xpdl2ModelUtil.getOtherElement(container,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CalendarReference());

        return (CalendarReference) (element instanceof CalendarReference ? element
                : null);
    }

    /**
     * Create an indexer item, to add to the indexer, for the given calendar
     * reference.
     * 
     * @param container
     * @param calendarRef
     * @return
     */
    private IndexerItem createIndexerItem(NamedElement container,
            CalendarReference calendarRef) {
        Map<String, String> info = new HashMap<String, String>();

        info.put(ATT_ALIAS, calendarRef.getAlias());

        return new IndexerItemImpl(container.getName(), container.eClass()
                .getName(), EcoreUtil.getURI(container).toString(), info);
    }

    /**
     * Get the calendar reference index items with the given alias.
     * 
     * @param alias
     *            alias to search for, <code>null</code> to return all indexed
     *            items
     * @return list of calendar reference index items, empty list if none found.
     */
    public static List<CalendarReferenceIndex> getIndexerItems(String alias) {
        List<CalendarReferenceIndex> items =
                new ArrayList<CalendarReferenceIndex>();

        IndexerService indexerService =
                XpdResourcesPlugin.getDefault().getIndexerService();
        IndexerItemImpl criteria = new IndexerItemImpl();

        if (alias != null) {
            criteria.set(ATT_ALIAS, alias);
        }

        Collection<IndexerItem> result =
                indexerService.query(XPDL_INDEXER_ID, criteria);

        for (IndexerItem item : result) {
            items.add(new CalendarReferenceIndex(item));
        }

        return items;
    }

    /**
     * Calendar reference wrapper of an Indexer Item.
     */
    public static class CalendarReferenceIndex {
        private String alias;

        private CalendarReferenceIndex(IndexerItem item) {
            alias = item.get(ATT_ALIAS);
        }

        /**
         * @return the alias
         */
        public String getAlias() {
            return alias;
        }

        /**
         * @see java.lang.Object#toString()
         * 
         * @return
         */
        @Override
        public String toString() {
            return String.format("Alias: %s", //$NON-NLS-1$
                    alias);
        }
    }
}
