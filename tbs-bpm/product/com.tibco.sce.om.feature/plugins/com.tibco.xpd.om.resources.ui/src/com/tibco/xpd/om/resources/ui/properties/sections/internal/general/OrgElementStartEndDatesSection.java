/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.om.resources.ui.properties.sections.internal.general;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.DateControl;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.DateControl.DateControlHandler;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section for non-hierarchical instances of
 * {@link OrgUnitRelationship}.
 * 
 * @author patkinso
 * @since 18 Oct 2012
 */
public class OrgElementStartEndDatesSection extends AbstractGeneralSection
        implements IFilter {

    private DateControl startDate;

    private DateControl endDate;

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof OrgElement) {
            OrgElement elem = (OrgElement) input;
            startDate.setValue(elem.getStartDate());
            endDate.setValue(elem.getEndDate());
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // dates are set by the picker listener

        return null;
    }

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        EObject eo = resollveInput(toTest);
        return (eo instanceof OrgUnitRelationship) ? !((OrgUnitRelationship) eo)
                .isIsHierarchical() : false;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.OrgElementSection_dates_label);
        Composite dateControls = toolkit.createComposite(root);
        dateControls.setLayout(new GridLayout(4, false));

        // start date
        Label lbl =
                toolkit.createLabel(dateControls,
                        Messages.OrgElementSection_startDate_label);
        GridData data = new GridData();

        lbl.setLayoutData(data);
        startDate =
                new DateControl(dateControls, SWT.NONE, toolkit,
                        getEditingDomain(), new PickerDateHandler(
                                OMPackage.eINSTANCE.getOrgElement_StartDate()));
        int dateWidth = 120;
        startDate.setMinimumTextWidth(dateWidth);
        // end date
        lbl =
                toolkit.createLabel(dateControls,
                        Messages.OrgElementSection_endDate_label);
        data = new GridData();
        data.horizontalIndent = 30;
        lbl.setLayoutData(data);
        endDate =
                new DateControl(dateControls, SWT.NONE, toolkit,
                        getEditingDomain(), new PickerDateHandler(
                                OMPackage.eINSTANCE.getOrgElement_EndDate()));
        endDate.setMinimumTextWidth(dateWidth);

        return root;
    }

    /**
     * Start and end date setting handler.
     * 
     * @author njpatel
     * 
     */
    private class PickerDateHandler implements DateControlHandler {
        private final EAttribute attribute;

        public PickerDateHandler(EAttribute attribute) {
            this.attribute = attribute;
        }

        @Override
        public Command getClearDateCommand(EditingDomain ed, Date date) {
            EObject input = getInput();
            if (input != null) {
                return SetCommand.create(ed,
                        input,
                        attribute,
                        SetCommand.UNSET_VALUE);
            }
            return null;
        }

        @Override
        public Command getSetDateCommand(EditingDomain ed, Date date) {
            EObject input = getInput();
            if (input != null) {
                // Reset the time to midnight
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                return SetCommand.create(ed,
                        input,
                        attribute,
                        calendar.getTime());
            }
            return null;
        }
    }

}
