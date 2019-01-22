/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataFields;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.CorrelationDataGroup;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * CorrelationDataSection
 * 
 * 
 * @author bharge
 * @since 3.3 (22 Oct 2009)
 */
public class CorrelationDataSection extends AbstractTransactionalSection {
    CorrelationDataTable correlationDataTable;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;

        correlationDataTable =
                new CorrelationDataTable(root, toolkit, getEditingDomain());
        correlationDataTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
                true, true));

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (correlationDataTable != null
                && correlationDataTable.getViewer() != null) {
            correlationDataTable.getViewer().cancelEditing();
            correlationDataTable.getViewer().setInput(input);
            correlationDataTable.reloadDeclaredTypes();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java
     * .util.Collection)
     */
    @Override
    public void setInput(Collection<?> items) {
        List<EObject> inputList = new ArrayList<EObject>();
        for (Object obj : items) {
            if (obj instanceof CorrelationDataGroup) {
                CorrelationDataGroup correlationDataGroup =
                        (CorrelationDataGroup) obj;
                if (correlationDataGroup.getParent() instanceof EObject) {
                    inputList.add((EObject) correlationDataGroup.getParent());
                }
            } else if (obj instanceof Process) {
                inputList.add((Process) obj);
            }
        }
        super.setInput(inputList);
    }

    public static class CorrelationDataFieldContainerFilter implements IFilter {

        @Override
        public boolean select(Object toTest) {
            Process process = null;

            if (toTest instanceof CorrelationDataGroup) {
                CorrelationDataGroup fieldGroup = (CorrelationDataGroup) toTest;

                if (fieldGroup.getParent() instanceof Process) {
                    process = (Process) fieldGroup.getParent();
                }

            }

            if (process != null && process.eResource() != null) {

                if (!DecisionFlowUtil.isDecisionFlow(process)) {
                    /*
                     * Sid XPD-2516: Don't show fields table section if
                     * processEditorConfiguration section has an active
                     * exclusion for participants.
                     */
                    if (!ProcessEditorConfigurationUtil
                            .getExcludedElementTypes(process)
                            .contains(ProcessEditorElementType.correlation_data)) {
                        return true;
                    }
                }
            }
            return false;
        }

    }

}
