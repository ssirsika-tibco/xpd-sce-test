/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.DataFilterPicker.DataPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.processeditor.xpdl2.fields.DataFieldContributor;
import com.tibco.xpd.processeditor.xpdl2.pickers.DBDataFilterPicker;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * A data field cell editor. Provides with a browse button that will launch the
 * data field picker.
 * 
 * @author njpatel
 * 
 */
public class DataFieldCellEditor extends DialogCellEditor {

    private final IDFProvider inputProvider;

    /**
     * Data field cell editor that provides a data field picker
     * 
     * @param parent
     * @param inputProvider
     */
    public DataFieldCellEditor(Composite parent, IDFProvider inputProvider) {
        super(parent);
        this.inputProvider = inputProvider;

        if (inputProvider == null) {
            throw new NullPointerException(
                    "Data field cell editor input provider in null."); //$NON-NLS-1$
        }

    }

    @Override
    protected Object openDialogBox(Control cellEditorWindow) {
        DataFilterPicker picker = getPicker(cellEditorWindow.getShell());

        // TODO: Add the clear button
        ProcessRelevantData initSel = null;
        Object specialFieldSelection = null;

        if (inputProvider != null) {
            Activity activity = inputProvider.getActivity();
            Process proc = activity.getProcess();
            Object v = getValue();
            if (proc != null && v instanceof ConceptPath) {
                String modelDataName = ((ConceptPath) v).getName();

                for (Iterator iter =
                        proc.getPackage().getDataFields().iterator(); iter
                        .hasNext();) {
                    DataField df = (DataField) iter.next();
                    if (modelDataName.equals(df.getName())) {
                        initSel = df;
                        break;
                    }
                }

                if (initSel == null) {
                    List formalParams =
                            ProcessInterfaceUtil.getAllFormalParameters(proc);

                    for (Iterator iter = formalParams.iterator(); iter
                            .hasNext();) {
                        FormalParameter df = (FormalParameter) iter.next();
                        if (modelDataName.equals(df.getName())) {
                            initSel = df;
                            break;
                        }
                    }

                    if (initSel == null) {
                        for (Iterator iter = proc.getDataFields().iterator(); iter
                                .hasNext();) {
                            DataField df = (DataField) iter.next();
                            if (modelDataName.equals(df.getName())) {
                                initSel = df;
                                break;
                            }
                        }
                    }

                    if (initSel == null) {
                        Collection<ConceptPath> parameters =
                                ConceptUtil
                                        .getContributedFields(proc,
                                                DataFieldContributor.CONTEXT_DATABASE_SERVICE_TASK);
                        for (ConceptPath conceptPath : parameters) {
                            if (modelDataName.equals(conceptPath.getName())) {
                                specialFieldSelection =
                                        DataFieldValueUtil
                                                .getLabelForConceptPath(conceptPath);
                                break;
                            }
                        }
                    }
                }

            }
        }

        if (initSel != null) {
            picker.setInitialElementSelections(Collections
                    .singletonList(initSel));
        } else {
            if (specialFieldSelection != null) {
                picker
                        .setInitialSelections(new Object[] { specialFieldSelection });
            } else {
                picker.setInitialSelections(new Object[0]);
            }
        }

        Object result = null;

        if (picker != null) {
            int ret = picker.open();

            if (ret == DataFilterPicker.OK) {
                result = picker.getFirstResult();

                if ((result instanceof ProcessRelevantData)) {
                    result =
                            new ConceptPath(
                                    result,
                                    ConceptUtil
                                            .getConceptClass((ProcessRelevantData) result));
                }
            }
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.DialogCellEditor#doGetValue()
     */
    @Override
    protected Object doGetValue() {
        return super.doGetValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.DialogCellEditor#doSetValue(java.lang.Object)
     */
    @Override
    protected void doSetValue(Object value) {
        // TODO Auto-generated method stub
        super.doSetValue(value);
    }

    @Override
    protected void updateContents(Object value) {
        if (value instanceof ConceptPath) {
            super.updateContents(DataFieldValueUtil
                    .getLabelForConceptPath((ConceptPath) value));
        } else {
            super.updateContents(value);
        }
    }

    /**
     * Get the data picker
     * 
     * @param shell
     * @return
     */
    private DBDataFilterPicker getPicker(Shell shell) {
        DBDataFilterPicker dataPicker =
                new DBDataFilterResponsePicker(shell,
                        DataPickerType.DATAFIELD_FORMALPARAMETER, false);

        /*
         * If we have the activity then set it as input, so that activity local
         * data and process data are available in scope
         */
        if (inputProvider != null) {
            dataPicker.setScope(inputProvider.getActivity());
        }

        return dataPicker;
    }

}
