/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataReferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property section for listing data references and the contexts they're
 * referenced in - grouped by data.
 * 
 * @author aallway
 * @since 25 Jun 2012
 */
public class DataReferencesByDataSection extends AbstractTransactionalSection {

    private GroupReferencesByDataTable table;

    private ReferencedDataContentProvider contentProvider;

    /**
     * @param dataReferencesSection
     */
    public DataReferencesByDataSection() {
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

        table = new GroupReferencesByDataTable(root, toolkit);

        contentProvider = new ReferencedDataContentProvider();

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
     * Table to view referenced process data and the context(s) that they are
     * referenced in.
     * 
     * @author aallway
     * @since 25 Jun 2012
     */
    private class GroupReferencesByDataTable extends BaseTableControl {

        /**
         * @param parent
         * @param toolkit
         * @param viewerInput
         * @param createContent
         */
        protected GroupReferencesByDataTable(Composite parent,
                XpdToolkit toolkit) {
            super(parent, toolkit);
            enableCellSpecificTooltips();
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void addColumns(ColumnViewer viewer) {
            new DataReferencesByDataNameColumn(getEditingDomain(), viewer,
                    Messages.DataReferencesByDataSection_Data_Column_title, -1);
            new DataReferencesByDataContextsColumn(
                    getEditingDomain(),
                    viewer,
                    Messages.DataReferencesByDataSection_ReferenceContext_column_title,
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
     * {@link ProcessDataReferenceAndContexts} (sorted by referenced process
     * data label.
     * 
     * @author aallway
     * @since 25 Jun 2012
     */
    private static class ReferencedDataContentProvider implements
            IStructuredContentProvider {

        private List<ProcessDataReferenceAndContexts> dataReferences =
                Collections.emptyList();

        /**
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {
            return dataReferences.toArray();
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
            Collection<ProcessDataReferenceAndContexts> inputDataReferences =
                    (Collection<ProcessDataReferenceAndContexts>) newInput;
            if (inputDataReferences == null) {
                inputDataReferences = Collections.emptyList();
            }

            /* Sort the list by process data name. */
            dataReferences =
                    new ArrayList<ProcessDataReferenceAndContexts>(
                            inputDataReferences);

            Collections.sort(dataReferences,
                    new Comparator<ProcessDataReferenceAndContexts>() {

                        @Override
                        public int compare(ProcessDataReferenceAndContexts o1,
                                ProcessDataReferenceAndContexts o2) {
                            String o1name =
                                    Xpdl2ModelUtil.getDisplayNameOrName(o1
                                            .getReferencedData());
                            String o2name =
                                    Xpdl2ModelUtil.getDisplayNameOrName(o2
                                            .getReferencedData());
                            return o1name.compareToIgnoreCase(o2name);
                        }
                    });
        }
    }
}
