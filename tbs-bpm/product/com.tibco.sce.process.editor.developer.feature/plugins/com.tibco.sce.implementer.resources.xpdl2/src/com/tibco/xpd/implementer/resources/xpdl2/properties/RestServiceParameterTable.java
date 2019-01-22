/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.ui.components.BaseTableControl;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.resources.ui.components.actions.ViewerDeleteAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerEditAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveDownAction;
import com.tibco.xpd.resources.ui.components.actions.ViewerMoveUpAction;

/**
 * Table for displaying REST service parameters
 * 
 * @author agondal
 * @since 25 Jan 2013
 */
public class RestServiceParameterTable extends BaseTableControl {

    private final EditingDomain editingDomain;

    private IContentProvider contentProvider;

    /**
     * @param parent
     * @param toolkit
     */
    public RestServiceParameterTable(Composite parent, XpdToolkit toolkit,
            EditingDomain editingDomain) {
        super(parent, toolkit, null, false);
        this.editingDomain = editingDomain;
        createContents(parent, toolkit, null);
        setEnableActions(false);

    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#addColumns(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     */
    @Override
    protected void addColumns(ColumnViewer viewer) {
        new ParameterNameColumn(editingDomain, viewer);
        new ParameterTypeColumn(editingDomain, viewer);

        setColumnProportions(new float[] { 0.25f, 0.75f });

    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createDeleteAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerDeleteAction createDeleteAction(ColumnViewer viewer) {
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createEditAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerEditAction createEditAction(ColumnViewer viewer) {
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveUpAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerMoveUpAction createMoveUpAction(ColumnViewer viewer) {
        return null;
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.BaseColumnViewerControl#createMoveDownAction(org.eclipse.jface.viewers.ColumnViewer)
     * 
     * @param viewer
     * @return
     */
    @Override
    protected ViewerMoveDownAction createMoveDownAction(ColumnViewer viewer) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.resources.ui.components.BaseColumnViewerControl#
     * getViewerContentProvider()
     */
    @Override
    protected IContentProvider getViewerContentProvider() {
        if (contentProvider == null) {

            return new RestServiceParamContentProvider();
        }
        return contentProvider;
    }

    private class ParameterNameColumn extends AbstractColumn {
        private final TextCellEditor editor;

        /**
         * @param editingDomain
         * @param viewer
         */
        public ParameterNameColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.RestParamHandler_ParamNameHeader_label, 200);
            editor =
                    new TextCellEditor((Composite) viewer.getControl(),
                            SWT.READ_ONLY);

        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getForeground
         * (java.lang.Object)
         */
        @Override
        protected Color getForeground(Object element) {

            return Display.getDefault()
                    .getSystemColor(SWT.COLOR_LIST_FOREGROUND);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         */
        @Override
        protected boolean getShowImage() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java
         * .lang.Object)
         */
        @Override
        protected Image getImage(Object element) {
            if (element != null) {

                return Xpdl2ResourcesPlugin.getDefault().getImageRegistry()
                        .get(Xpdl2ResourcesConsts.ICON_FORMALPARAMETER);
            }

            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            if (element instanceof ParamsData) {
                return editor;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {
            if (element instanceof ParamsData) {

                return ((ParamsData) element).paramName;

            }
            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getToolTipText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getToolTipText(Object element) {

            return super.getToolTipText(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    private class ParameterTypeColumn extends AbstractColumn {

        /**
         * @param editingDomain
         * @param viewer
         */
        public ParameterTypeColumn(EditingDomain editingDomain,
                ColumnViewer viewer) {
            super(editingDomain, viewer, SWT.NONE,
                    Messages.RestParamHandler_ParamTypeHeader_label, 200);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getForeground
         * (java.lang.Object)
         */
        @Override
        protected Color getForeground(Object element) {

            return Display.getDefault()
                    .getSystemColor(SWT.COLOR_LIST_FOREGROUND);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getShowImage()
         */
        @Override
        protected boolean getShowImage() {
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java
         * .lang.Object)
         */
        @Override
        protected Image getImage(Object element) {

            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor
         * (java.lang.Object)
         */
        @Override
        protected CellEditor getCellEditor(Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getSetValueCommand
         * (java.lang.Object, java.lang.Object)
         */
        @Override
        protected Command getSetValueCommand(Object element, Object value) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java
         * .lang.Object)
         */
        @Override
        protected String getText(Object element) {

            if (element instanceof ParamsData) {

                return ((ParamsData) element).paramType;

            }

            return null;
        }

        /**
         * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getToolTipText(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        protected String getToolTipText(Object element) {

            return super.getToolTipText(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.resources.ui.components.AbstractColumn#getValueForEditor
         * (java.lang.Object)
         */
        @Override
        protected Object getValueForEditor(Object element) {
            return getText(element);
        }

    }

    /**
     * Content provider for the viewer displayed in the property section showing
     * the rest service parameters.
     * 
     */
    public class RestServiceParamContentProvider implements
            IStructuredContentProvider {

        /**
         * @param inputElement
         *            The input element to get the content for.
         * @return An array of content objects.
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#
         *      getElements(java.lang.Object)
         */
        @Override
        @SuppressWarnings("unchecked")
        public Object[] getElements(Object input) {

            Collection<ParamsData> paramsList =
                    new ArrayList<RestServiceParameterTable.ParamsData>();

            if (input != null) {

                paramsList = (Collection<ParamsData>) input;
            }

            return paramsList.toArray();
        }

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

        }

    }

    /**
     * Data class for transporting info about rest parameter name and type
     * 
     * 
     * @author agondal
     * @since 28 Jan 2013
     */
    public static class ParamsData {

        String paramName;

        String paramType;

    }
}
