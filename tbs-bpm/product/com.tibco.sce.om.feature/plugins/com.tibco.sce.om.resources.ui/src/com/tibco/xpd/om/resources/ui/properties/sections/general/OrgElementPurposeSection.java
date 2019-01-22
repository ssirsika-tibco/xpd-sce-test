/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for the {@link OrgElement} object.
 * 
 * @author njpatel
 * 
 */
public class OrgElementPurposeSection extends AbstractGeneralSection implements
        IFilter {

    private Text purposeTxt;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.OrgElementSection_purpose_label);
        purposeTxt =
                toolkit.createText(root,
                        getInput(),
                        OMPackage.eINSTANCE.getOrgElement_Purpose(),
                        "orgElement-purpose"); //$NON-NLS-1$
        setLayoutData(purposeTxt);
        manageControlUpdateOnDeactivate(purposeTxt);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        if (obj == purposeTxt) {
            EObject input = getInput();
            Object attr = purposeTxt.getData(XpdFormToolkit.FEATURE_DATA);
            if (input != null && attr != null) {
                String text = purposeTxt.getText();
                return SetCommand.create(getEditingDomain(),
                        input,
                        attr,
                        text.length() > 0 ? text : SetCommand.UNSET_VALUE);
            }
        }

        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof OrgElement && !purposeTxt.isDisposed()) {
            OrgElement elem = (OrgElement) input;
            updateText(purposeTxt, elem.getPurpose());
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
        }

        return input instanceof OrgElement;
    }

}
