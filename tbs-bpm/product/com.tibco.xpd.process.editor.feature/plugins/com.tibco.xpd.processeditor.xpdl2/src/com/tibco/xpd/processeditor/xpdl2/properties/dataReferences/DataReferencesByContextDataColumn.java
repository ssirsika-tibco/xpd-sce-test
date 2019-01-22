/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataReferences;

import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.resources.ui.components.AbstractColumn;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdWizardToolkit;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.resolvers.ProcessDataReferenceAndContexts;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Column for process data list
 * 
 * @author aallway
 * @since 25 Jun 2012
 */
class DataReferencesByContextDataColumn extends AbstractColumn {

    private DialogCellEditor cellEditor;

    public DataReferencesByContextDataColumn(EditingDomain editingDomain,
            ColumnViewer viewer, String header, int width) {
        super(editingDomain, viewer, header, width);

        cellEditor = new DialogCellEditor((Composite) viewer.getControl()) {

            @SuppressWarnings("unchecked")
            @Override
            protected Object openDialogBox(Control cellEditorWindow) {
                Entry<DataReferenceContext, List<ProcessRelevantData>> contextAndDataList =
                        (Entry<DataReferenceContext, List<ProcessRelevantData>>) this
                                .getValue();

                final GroupReferencesByContextDataListDialog dialog =
                        new GroupReferencesByContextDataListDialog(getViewer()
                                .getControl().getShell(), contextAndDataList);

                dialog.open();

                return null;
            }

            /**
             * @see org.eclipse.jface.viewers.DialogCellEditor#updateContents(java.lang.Object)
             * 
             * @param value
             */
            @Override
            protected void updateContents(Object value) {
                super.updateContents(getDataListLabel(value));
            }

        };
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getText(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected String getText(Object element) {
        return getDataListLabel(element);
    }

    /**
     * @param element
     * @return Comma separate list of data labels for the given cell input
     *         element.
     */
    public String getDataListLabel(Object element) {
        if (element instanceof Entry) {
            @SuppressWarnings("unchecked")
            List<ProcessRelevantData> dataList =
                    (List<ProcessRelevantData>) ((Entry) element).getValue();

            StringBuilder sb = new StringBuilder();

            for (ProcessRelevantData data : dataList) {
                if (sb.length() > 0) {
                    sb.append(", "); //$NON-NLS-1$
                }
                sb.append(Xpdl2ModelUtil.getDisplayNameOrName(data));
            }

            return sb.toString();
        }
        return "??"; //$NON-NLS-1$
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getImage(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Image getImage(Object element) {
        if (element instanceof ProcessDataReferenceAndContexts) {
            return WorkingCopyUtil
                    .getImage(((ProcessDataReferenceAndContexts) element)
                            .getReferencedData());
        }

        return super.getImage(element);
    }

    /**
     * @see com.tibco.xpd.resources.ui.components.AbstractColumn#getCellEditor(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected CellEditor getCellEditor(Object element) {
        if (element instanceof Entry) {
            return cellEditor;
        }
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
        return element;
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

    /**
     * List of data referenced in given context.
     * 
     * @author aallway
     * @since 25 Jun 2012
     */
    private class GroupReferencesByContextDataListDialog extends Dialog {

        private Entry<DataReferenceContext, List<ProcessRelevantData>> contextToDataList;

        protected GroupReferencesByContextDataListDialog(
                Shell parentShell,
                Entry<DataReferenceContext, List<ProcessRelevantData>> contextToDataList) {
            super(parentShell);
            this.contextToDataList = contextToDataList;
        }

        /**
         * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
         * 
         * @param newShell
         */
        @Override
        protected void configureShell(Shell newShell) {
            super.configureShell(newShell);

            newShell.setText(Messages.ReferenceContextsColumn_DataReferences_title);
        }

        /**
         * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         * @return
         */
        @Override
        protected Control createDialogArea(Composite parent) {

            Composite mainContainer =
                    (Composite) super.createDialogArea(parent);

            XpdWizardToolkit toolkit = new XpdWizardToolkit(mainContainer);

            Composite container =
                    toolkit.createComposite(mainContainer, SWT.NONE);
            container.setLayoutData(new GridData(GridData.FILL_BOTH));
            GridLayout gl = new GridLayout(1, false);
            gl.marginHeight = 0;
            gl.marginWidth = 0;
            container.setLayout(gl);

            Label label =
                    toolkit.createLabel(container,
                            String.format(Messages.GroupReferencesByContextDataColumn_DataReferenceInContext_description,
                                    contextToDataList.getKey().getLabel()));
            label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            org.eclipse.swt.widgets.List list =
                    toolkit.createList(container, SWT.V_SCROLL | SWT.BORDER);

            for (ProcessRelevantData data : contextToDataList.getValue()) {
                list.add(WorkingCopyUtil.getText(data));
            }

            GridData gd = new GridData(GridData.FILL_BOTH);
            gd.heightHint = 150;
            gd.widthHint = 350;

            list.setLayoutData(gd);

            return mainContainer;
        }

        /**
         * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            createButton(parent,
                    IDialogConstants.OK_ID,
                    IDialogConstants.OK_LABEL,
                    true);
        }
    }

}