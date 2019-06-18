/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.general;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.om.core.om.BaseOrgModel;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgMetaModel;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for the {@link BaseOrgModel} object.
 * 
 * @author njpatel
 * 
 */
public class BaseOrgModelSection extends AbstractGeneralSection implements
        IFilter {

    private Text authorTxt;
    private Text dateCreatedTxt;

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.BaseOrgModelSection_author_label);
        authorTxt = toolkit.createText(root, getInput(), OMPackage.eINSTANCE
                .getBaseOrgModel_Author(), "baseOrgModel-author"); //$NON-NLS-1$
        setLayoutData(authorTxt);
        manageControlUpdateOnDeactivate(authorTxt);

        createLabel(root, toolkit,
                Messages.BaseOrgModelSection_dateCreated_label);

        long date = getInput() != null ? ((BaseOrgModel) getInput())
                .getDateCreated() : 0;

        dateCreatedTxt = toolkit.createText(root, getDate(date), SWT.NONE,
                "baseOrgModel-dateCreated"); //$NON-NLS-1$
        dateCreatedTxt.setEnabled(false);
        setLayoutData(dateCreatedTxt);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        if (obj instanceof Text) {
            Text text = (Text) obj;
            Object data = text.getData(XpdFormToolkit.FEATURE_DATA);

            if (data != null) {
                String value = text.getText();
                cmd = SetCommand.create(getEditingDomain(), getInput(), data,
                        value != null ? value : SetCommand.UNSET_VALUE);
            }
        }

        return cmd;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof BaseOrgModel && !dateCreatedTxt.isDisposed()) {
            updateText(authorTxt, (String) input
                    .eGet((EStructuralFeature) authorTxt
                            .getData(XpdFormToolkit.FEATURE_DATA)));

            long created = ((BaseOrgModel) input).getDateCreated();
            updateText(dateCreatedTxt, getDate(created));
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

        // Don't show this section for the schema model
        return input instanceof BaseOrgModel
                && !(input instanceof OrgMetaModel);
    }

    /**
     * Get the date value from the time stamp.
     * 
     * @param dateValue
     * @return
     */
    private String getDate(long dateValue) {
        String date = ""; //$NON-NLS-1$

        if (dateValue != 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            date = dateFormat.format(new Date(dateValue));
        }

        return date;
    }
}
