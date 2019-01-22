/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataReferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property section for listing data reference contexts and the data that's
 * reference in that context - grouped by context.
 * 
 * @author aallway
 * @since 25 Jun 2012
 */
public class DataReferencesByContextSection extends
        AbstractTransactionalSection {

    private GroupReferencesByContextTable table;

    private ReferencedContextContentProvider contentProvider;

    /**
     * @param dataReferencesSection
     */
    public DataReferencesByContextSection() {
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        /*
         * Do nothing on doRefresh() the owner DataReferencesSection will call
         * refreshReferences() after it caches the data references.
         */
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);

        GridLayout gl = new GridLayout(1, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        gl.marginBottom = 4;
        root.setLayout(gl);

        table = new GroupReferencesByContextTable(root, toolkit);

        contentProvider = new ReferencedContextContentProvider();

        table.getViewer().setContentProvider(contentProvider);

        table.setLayoutData(new GridData(GridData.FILL_BOTH));

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /**
     * @param cachedDataReferences
     */
    public void refreshReferences(
            Collection<ProcessDataReferenceAndContexts> cachedDataReferences) {

        if (!table.isDisposed()) {
            table.getViewer().setInput(cachedDataReferences);
        }
    }

    /**
     * Table to view referenced data contextx and the process datathat is
     * referenced in each context.
     * 
     * @author aallway
     * @since 25 Jun 2012
     */
    private class GroupReferencesByContextTable extends BaseTableControl {

        /**
         * @param parent
         * @param toolkit
         * @param viewerInput
         * @param createContent
         */
        protected GroupReferencesByContextTable(Composite parent,
                XpdToolkit toolkit) {
            super(parent, toolkit);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void addColumns(ColumnViewer viewer) {
            new DataReferencesByContextNameColumn(
                    getEditingDomain(),
                    viewer,
                    Messages.DataReferencesByContextSection_ReferenceContext_columntitle,
                    -1);
            new DataReferencesByContextDataColumn(
                    getEditingDomain(),
                    viewer,
                    Messages.DataReferencesByContextSection_DataReferenced_columntitle,
                    -1);

            this.setColumnProportions(new float[] { 0.3f, 0.7f });
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createActions(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void createActions(ColumnViewer viewer) {
            /* Hide all actions by not creating them. */
        }

    }

    /**
     * Context provider for simple (preset) list of
     * {@link ProcessDataReferenceAndContexts} (sorted by data reference
     * context).
     * <p>
     * Each element is an {@link Entry} with reference context as key and sorted
     * list of data fields referenced in that context.
     * 
     * @author aallway
     * @since 25 Jun 2012
     */
    private static class ReferencedContextContentProvider implements
            IStructuredContentProvider {

        private List<Entry<DataReferenceContext, List<ProcessRelevantData>>> refContextToDataListEntries =
                Collections.emptyList();

        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {

            return refContextToDataListEntries.toArray();
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         * 
         */
        @Override
        public void dispose() {
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param oldInput
         * @param newInput
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            Collection<ProcessDataReferenceAndContexts> dataReferences =
                    (Collection<ProcessDataReferenceAndContexts>) newInput;

            if (dataReferences == null) {
                refContextToDataListEntries = Collections.emptyList();

            } else {
                /*
                 * Create a may whose EntrySet = Context->Sort list of dat ain
                 * that context.
                 */
                HashMap<DataReferenceContext, List<ProcessRelevantData>> tmpMap =
                        new HashMap<DataReferenceContext, List<ProcessRelevantData>>();

                for (ProcessDataReferenceAndContexts dataRef : dataReferences) {
                    /*
                     * Sid XPD-7078: Display all contexts with unique id+label
                     * (rather than missing contexts that have same id but
                     * different label.
                     */
                    for (DataReferenceContext context : dataRef
                            .getUniquelyLabelledContexts()) {

                        List<ProcessRelevantData> data = tmpMap.get(context);

                        if (data == null) {
                            /* Add entry for context when not present yet. */
                            data = new ArrayList<ProcessRelevantData>();
                            tmpMap.put(context, data);
                        }

                        data.add(dataRef.getReferencedData());
                    }
                }

                /* Then sort each list of data items. */
                refContextToDataListEntries =
                        new ArrayList<Entry<DataReferenceContext, List<ProcessRelevantData>>>();

                Set<Entry<DataReferenceContext, List<ProcessRelevantData>>> entries =
                        tmpMap.entrySet();

                for (Entry<DataReferenceContext, List<ProcessRelevantData>> entry : entries) {
                    /*
                     * Add entry to list and sort the list of data entries in
                     * it.
                     */
                    refContextToDataListEntries.add(entry);

                    Collections.sort(entry.getValue(),
                            new Comparator<ProcessRelevantData>() {

                                @Override
                                public int compare(ProcessRelevantData o1,
                                        ProcessRelevantData o2) {
                                    String o1name =
                                            Xpdl2ModelUtil
                                                    .getDisplayNameOrName(o1);
                                    String o2name =
                                            Xpdl2ModelUtil
                                                    .getDisplayNameOrName(o2);
                                    return o1name.compareToIgnoreCase(o2name);
                                }
                            });
                }

                /* Then finally, sort the main list by context sort key. */
                Collections
                        .sort(refContextToDataListEntries,
                                new Comparator<Entry<DataReferenceContext, List<ProcessRelevantData>>>() {

                                    @Override
                                    public int compare(
                                            Entry<DataReferenceContext, List<ProcessRelevantData>> o1,
                                            Entry<DataReferenceContext, List<ProcessRelevantData>> o2) {
                                        return o1
                                                .getKey()
                                                .getSortingKey()
                                                .compareTo(o2.getKey()
                                                        .getSortingKey());
                                    }
                                });
            }
        }
    }

}
