/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.parameters;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IAdaptable;
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
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.FormalParameterGroup;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ParameterSection
 * 
 * 
 * @author bharge
 * @since 3.3 (22 Oct 2009)
 */
public class ParameterSection extends AbstractTransactionalSection {
    ParameterTable parameterTable;

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

        parameterTable = new ParameterTable(root, toolkit, getEditingDomain());
        parameterTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true));

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

    @Override
    public void setInput(Collection<?> items) {
        EObject input = null;
        if (items.size() == 1) {

            Object obj = items.iterator().next();

            if (!(obj instanceof EObject)) {
                if (obj instanceof FormalParameterGroup) {
                    obj = ((FormalParameterGroup) obj).getParent();
                } else if (obj instanceof IAdaptable) {
                    obj = ((IAdaptable) obj).getAdapter(EObject.class);
                }
            }

            if (obj instanceof Process) {
                input = (Process) obj;
            } else if (obj instanceof ProcessInterface) {
                input = (ProcessInterface) obj;
            }

        }

        if (input == null) {
            super.setInput(Collections.emptyList());
        } else {
            super.setInput(Collections.singletonList(input));
        }
        return;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();
        if (parameterTable != null && parameterTable.getViewer() != null) {
            parameterTable.getViewer().cancelEditing();
            parameterTable.getViewer().setInput(input);
            parameterTable.reloadDeclaredTypes();
        }
    }

    /**
     * Formal Parameter section visibility filter.
     * <p>
     * Visible when Project Explorer formal parameter group, Process or anything
     * adaptable to process is selected.
     * 
     * 
     * @author aallway
     * @since 3.4.2 (26 Jul 2010)
     */
    public static class ParameterSectionFilter implements IFilter {

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         */
        @Override
        public boolean select(Object toTest) {
            EObject eo = null;

            if (toTest instanceof FormalParameterGroup) {
                FormalParameterGroup paramGroup = (FormalParameterGroup) toTest;
                toTest = paramGroup.getParent();
            }

            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null) {
                if (eo instanceof Process) {
                    Process process = (Process) eo;

                    if (!Xpdl2ModelUtil.isTaskLibrary(process)) {
                        /*
                         * DOn't show for wizard (in which case object won't
                         * have been added to resoruce yet.
                         */
                        /*
                         * XPD-1140: File may not be there in case of remote
                         * repository. eResource will be but won't be for
                         * wizard.
                         */
                        if (process.eResource() != null) {
                            /*
                             * Sid XPD-2516: Don't show fields table section if
                             * processEditorConfiguration section has an active
                             * exclusion for participants.
                             */
                            if (!ProcessEditorConfigurationUtil
                                    .getExcludedElementTypes(process)
                                    .contains(ProcessEditorElementType.formal_parameter)) {
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
