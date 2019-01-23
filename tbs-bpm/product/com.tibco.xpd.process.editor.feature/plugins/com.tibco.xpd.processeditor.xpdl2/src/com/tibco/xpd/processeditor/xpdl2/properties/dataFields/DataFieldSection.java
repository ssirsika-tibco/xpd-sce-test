/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.dataFields;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorConfigurationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.processeditorconfiguration.ProcessEditorElementType;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.DataFieldGroup;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;

/**
 * DataFieldSection
 * 
 * 
 * @author bharge
 * @since 3.3 (20 Oct 2009)
 */
public class DataFieldSection extends AbstractTransactionalSection {
    DataFieldTable dataFieldTable;

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
        root.setLayout(new FillLayout());

        dataFieldTable = new DataFieldTable(root, toolkit, getEditingDomain());

        return root;

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
            if (obj instanceof DataFieldGroup) {
                DataFieldGroup dataFieldGroup = (DataFieldGroup) obj;
                if (dataFieldGroup.getParent() instanceof EObject) {
                    inputList.add((EObject) dataFieldGroup.getParent());
                }
            } else {
                /** Add all activities except gateways to the list of inputs */
                EObject eo = null;

                if (obj instanceof EObject) {
                    eo = (EObject) obj;
                } else if (obj instanceof IAdaptable) {
                    eo = (EObject) ((IAdaptable) obj).getAdapter(EObject.class);
                }

                if (eo instanceof Activity) {
                    Activity activity = (Activity) eo;
                    /** data fields tab must not be shown for gateways */
                    if (activity.getRoute() == null) {
                        inputList.add(eo);
                    }
                } else if (eo instanceof Process) {
                    inputList.add(eo);
                }
            }
        }
        super.setInput(inputList);
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
        if (dataFieldTable != null && dataFieldTable.getViewer() != null) {
            dataFieldTable.getViewer().cancelEditing();
            dataFieldTable.getViewer().setInput(input);
            dataFieldTable.reloadDeclaredTypes();
        }
    }

    public static class DataFieldContainerFilter implements IFilter {

        @Override
        public boolean select(Object toTest) {

            EObject eo = null;

            if (toTest instanceof DataFieldGroup) {
                DataFieldGroup fieldGroup = (DataFieldGroup) toTest;
                toTest = fieldGroup.getParent();
            }

            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null) {
                /**
                 * DOn't show for wizard (in which case object won't have been
                 * added to resoruce yet.
                 */
                /*
                 * XPD-1140: File may not be there in case of remote repository.
                 * eResource will be but won't be for wizard.
                 */

                if (eo != null && eo.eResource() != null) {
                    if (eo instanceof Activity) {
                        Activity activity = (Activity) eo;
                        /**
                         * data fields tab must not be shown for gateways and
                         * data fields tab must not be shown for activities in
                         * decision flows
                         */
                        if (activity.getRoute() == null
                                && !DecisionFlowUtil.isDecisionFlow(activity
                                        .getProcess())) {
                            /*
                             * Sid XPD-2516: Don't show fields table section if
                             * processEditorConfiguration section has an active
                             * exclusion for participants.
                             */
                            if (!ProcessEditorConfigurationUtil
                                    .getExcludedElementTypes(activity.getProcess())
                                    .contains(ProcessEditorElementType.datafield)) {
                                return true;
                            }
                        }

                    } else if (eo instanceof Process) {
                        Process process = (Process) eo;
                        if (!DecisionFlowUtil.isDecisionFlow(process)) {
                            /*
                             * Sid XPD-2516: Don't show fields table section if
                             * processEditorConfiguration section has an active
                             * exclusion for participants.
                             */
                            if (!ProcessEditorConfigurationUtil
                                    .getExcludedElementTypes(process)
                                    .contains(ProcessEditorElementType.datafield)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

    }

}
