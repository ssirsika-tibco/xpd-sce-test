/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.properties.general;

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

import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.n2.globalsignal.resource.ui.PayloadDataGroup;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Payload data section (displayed when a Payload Data Group is selected).
 * 
 * @author sajain
 * @since Feb 23, 2015
 */
public class PayloadDataSection extends AbstractTransactionalSection {

    /**
     * Table to show all payload datafields in a global signal.
     */
    PayloadDataTable pdfTable;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        /*
         * Create root.
         */
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());

        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false);
        data.horizontalIndent = 5;

        /*
         * Create pdf table.
         */
        pdfTable = new PayloadDataTable(root, toolkit, getEditingDomain());
        pdfTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

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

        /*
         * Refresh table.
         */
        if (pdfTable != null && pdfTable.getViewer() != null) {

            pdfTable.getViewer().cancelEditing();
            pdfTable.getViewer().setInput(input);
            pdfTable.reloadDeclaredTypes();
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

            if (obj instanceof PayloadDataGroup) {

                PayloadDataGroup correlationDataGroup = (PayloadDataGroup) obj;

                if (correlationDataGroup.getParent() instanceof EObject) {

                    inputList.add((EObject) correlationDataGroup.getParent());

                }

            } else if (obj instanceof GlobalSignal) {

                inputList.add((GlobalSignal) obj);
            }
        }
        super.setInput(inputList);
    }

    public static class PayloadDataContainerFilter implements IFilter {

        @Override
        public boolean select(Object toTest) {

            if (toTest instanceof PayloadDataGroup) {

                PayloadDataGroup fieldGroup = (PayloadDataGroup) toTest;

                if (fieldGroup.getParent() instanceof GlobalSignal) {

                    return true;
                }
            }
            return false;
        }

    }

}
