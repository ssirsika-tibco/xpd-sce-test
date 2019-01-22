/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.implementer.nativeservices.databaseservice.properties.table;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.tibco.xpd.implementer.nativeservices.NativeServicesActivator;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.logger.LoggerFactory;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * @author NWilson
 * 
 */
public class ExternalReferenceLinkCellEditor extends CellEditor {

    private TableViewer tableViewer;

    public ExternalReferenceLinkCellEditor(TableViewer tableViewer) {
        this.tableViewer = tableViewer;
    }

    /**
     * @see org.eclipse.jface.viewers.CellEditor#createControl(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    protected Control createControl(Composite parent) {
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.CellEditor#doGetValue()
     * 
     * @return
     */
    @Override
    protected Object doGetValue() {
        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.CellEditor#doSetFocus()
     * 
     */
    @Override
    protected void doSetFocus() {
    }

    /**
     * @see org.eclipse.jface.viewers.CellEditor#doSetValue(java.lang.Object)
     * 
     * @param value
     */
    @Override
    protected void doSetValue(Object value) {
        if (tableViewer != null) {
            ISelection selection = tableViewer.getSelection();
            if (selection instanceof IStructuredSelection) {
                IStructuredSelection structured =
                        (IStructuredSelection) selection;
                if (structured.size() == 1) {
                    Object item = structured.getFirstElement();
                    if (item instanceof DataMapping) {
                        DataMapping mapping = (DataMapping) item;
                        ConceptPath path =
                                DataFieldValueUtil
                                        .getConceptPathForDataMapping(mapping);
                        if (path != null) {
                            Object concept = path.getItem();
                            if (concept instanceof ProcessRelevantData) {
                                ProcessRelevantData prd =
                                        (ProcessRelevantData) concept;
                                if (prd.getDataType() instanceof ExternalReference) {
                                    ExternalReference ref =
                                            (ExternalReference) prd
                                                    .getDataType();
                                    IProject project =
                                            WorkingCopyUtil
                                                    .getProjectFor(mapping);
                                    IFile file =
                                            SpecialFolderUtil
                                                    .resolveSpecialFolderRelativePath(project,
                                                            ConceptUtil.BOM_SPECIAL_FOLDER_KIND,
                                                            ref.getLocation());
                                    if (file != null && file.exists()) {
                                        try {
                                            IDE.openEditor(PlatformUI
                                                    .getWorkbench()
                                                    .getActiveWorkbenchWindow()
                                                    .getActivePage(), file);
                                        } catch (PartInitException e) {
                                            Logger log =
                                                    LoggerFactory
                                                            .createLogger(NativeServicesActivator.PLUGIN_ID);
                                            log.error(e);
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
