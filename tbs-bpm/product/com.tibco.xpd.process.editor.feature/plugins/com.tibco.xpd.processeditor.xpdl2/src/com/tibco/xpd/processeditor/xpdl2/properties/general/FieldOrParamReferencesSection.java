/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.general;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchSite;

import com.tibco.xpd.processeditor.xpdl2.properties.dataReferences.DataReferencesByDataContextsColumn;
import com.tibco.xpd.processeditor.xpdl2.properties.general.DiagramObjectDataReferenceAndContexts.GotoItemOpenListener;
import com.tibco.xpd.processeditor.xpdl2.properties.general.DiagramObjectDataReferenceAndContexts.ViewerLabelProvider;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTreeControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * References to given data field / parameter property section.
 * 
 * @author aallway
 * @since 5 Jul 2012
 */
public class FieldOrParamReferencesSection extends AbstractTransactionalSection {

    private FieldOrParamReferencesTable table;

    private IWorkbenchSite site;

    /**
     * @param site
     */
    public FieldOrParamReferencesSection(IWorkbenchSite site) {
        super();
        this.site = site;
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

        table = new FieldOrParamReferencesTable(root, toolkit);

        table.setLayoutData(new GridData(GridData.FILL_BOTH));

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        /*
         * We do all of our stuff on setInput only don't want to keep refreshing
         * just because data field itself is changed - that won't change
         * references.
         */
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        EObject input = getInput();

        if (table != null && table.getViewer() != null) {
            table.getViewer().setInput(input);

            table.getGotoAction().setWorkbenchSite(site);

        }
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
     * Table for each diagram object that references the input data field and
     * the contexts in which that is referenced.
     * 
     * @author aallway
     * @since 5 Jul 2012
     */
    private class FieldOrParamReferencesTable extends BaseTreeControl {

        private ILabelProvider viewerLabelProvider = null;

        private GotoItemOpenListener gotoAction;

        /**
         * @param parent
         * @param toolkit
         */
        public FieldOrParamReferencesTable(Composite parent, XpdToolkit toolkit) {
            super(parent, toolkit);
            getViewer()
                    .setContentProvider(new DiagramObjectDataReferenceAndContexts.ViewerContentProvider());
            getViewer().setSorter(new ViewerSorter());

            getTreeViewer().setAutoExpandLevel(TreeViewer.ALL_LEVELS);
            getTreeViewer().getTree().setLinesVisible(true);
            getTreeViewer().getTree().setHeaderVisible(true);

            gotoAction =
                    new DiagramObjectDataReferenceAndContexts.GotoItemOpenListener();
            getTreeViewer().addOpenListener(gotoAction);
        }

        /**
         * @return the gotoAction
         */
        public GotoItemOpenListener getGotoAction() {
            return gotoAction;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createActions(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void createActions(ColumnViewer viewer) {
            /* No actions required. */
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#getViewerLabelProvider()
         * 
         * @return
         */
        @Override
        protected ILabelProvider getViewerLabelProvider() {
            if (viewerLabelProvider == null) {
                viewerLabelProvider =
                        new DiagramObjectDataReferenceAndContexts.ViewerLabelProvider();
            }

            return viewerLabelProvider;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.BaseTreeControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
         * 
         * @param viewer
         */
        @Override
        protected void addColumns(ColumnViewer viewer) {
            new DiagramObjectColumn(
                    getEditingDomain(),
                    viewer,
                    Messages.FieldOrParamReferencesSection_ReferencedBy_columntitle,
                    -1);
            new DataReferencesByDataContextsColumn(
                    getEditingDomain(),
                    viewer,
                    Messages.DataReferencesByDataSection_ReferenceContext_column_title,
                    -1);

            this.setColumnProportions(new float[] { 0.3f, 0.7f });
        }
    }

    /**
     * Column for the referncing diagram object.
     * 
     * 
     * @author aallway
     * @since 5 Jul 2012
     */
    private static class DiagramObjectColumn extends AbstractColumn {

        private ViewerLabelProvider labelProvider;

        /**
         * @param editingDomain
         * @param viewer
         * @param header
         * @param width
         */
        public DiagramObjectColumn(EditingDomain editingDomain,
                ColumnViewer viewer, String header, int width) {
            super(editingDomain, viewer, header, width);

            labelProvider =
                    new DiagramObjectDataReferenceAndContexts.ViewerLabelProvider();

            setShowImage(true);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getText(Object element) {
            return labelProvider.getText(element);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected Image getImage(Object element) {
            return labelProvider.getImage(element);
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand(java.lang.Object,
         *      java.lang.Object)
         * 
         * @param element
         * @param value
         * @return
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            return null;
        }

    }

}
