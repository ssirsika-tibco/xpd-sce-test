/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.Allocable;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for the {@link Allocable} object.
 * 
 * @author njpatel
 * 
 */
public class AllocableSection extends AbstractGeneralSection implements IFilter {

    /**
     * Allocation method.
     */
    private enum AllocationMethod {
        ANY(Messages.AllocableSection_allocationMethod_random_name), NEXT(
                Messages.AllocableSection_allocationMethod_roundRobin_name);

        private final String label;

        AllocationMethod(String label) {
            this.label = label;
        }

        /**
         * Get the label to be used for this allocation method.
         * 
         * @return label
         */
        public String getLabel() {
            return label;
        }
    }

    private final Map<AllocationMethod, Button> buttons;

    /**
     * {@link Allocable} object general section.
     */
    public AllocableSection() {
        buttons = new HashMap<AllocationMethod, Button>();
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root,
                toolkit,
                Messages.AllocableSection_allocationMethod_label);

        Composite buttonsContainer = toolkit.createComposite(root);
        RowLayout layout = new RowLayout(SWT.HORIZONTAL);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.spacing = 20;
        buttonsContainer.setLayout(layout);

        for (AllocationMethod method : AllocationMethod.values()) {
            Button btn =
                    toolkit.createButton(buttonsContainer,
                            method.getLabel(),
                            SWT.RADIO,
                            "allocation-method-" //$NON-NLS-1$
                                    + method.name() + "-radio"); //$NON-NLS-1$
            btn.setData(method);
            manageControl(btn);
            buttons.put(method, btn);
        }

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        if (obj instanceof Button) {
            EObject input = getInput();
            Button btn = (Button) obj;
            Object data = btn.getData();
            if (input != null && btn.getSelection()
                    && data instanceof AllocationMethod) {
                return SetCommand.create(getEditingDomain(),
                        input,
                        OMPackage.eINSTANCE.getAllocable_AllocationMethod(),
                        ((AllocationMethod) data).name());
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Allocable) {
            String method = ((Allocable) input).getAllocationMethod();

            if (method != null) {
                AllocationMethod allocMethod = AllocationMethod.valueOf(method);
                for (Entry<AllocationMethod, Button> entry : buttons.entrySet()) {
                    Button btn = entry.getValue();
                    if (btn != null && !btn.isDisposed()) {
                        btn.setSelection(allocMethod == entry.getKey());
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject input = resollveInput(toTest);

        /*
         * XPD-5300: Don't show for Dynamic OrgUnit
         */
        if (input instanceof DynamicOrgUnit) {
            return false;
        } else if (input instanceof Organization) {
            /*
             * XPD-5772: Saket: Don't show for Dynamic Organization templates.
             */
            Organization org = (Organization) input;
            if (org.isDynamic()) {
                return false;
            }
        }

        return input instanceof Allocable;
    }
}
