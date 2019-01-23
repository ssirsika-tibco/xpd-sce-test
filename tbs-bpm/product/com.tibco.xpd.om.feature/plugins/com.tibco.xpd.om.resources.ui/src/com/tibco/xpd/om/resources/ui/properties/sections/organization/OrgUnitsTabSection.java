/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.organization;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.general.AbstractGeneralSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section for the organization units tab (for {@link Organization} and
 * {@link OrgUnit}).
 * 
 * @author njpatel
 * 
 */
public class OrgUnitsTabSection extends AbstractGeneralSection implements
        IFilter, ISectionContentCreator {

    private final String ORGUNIT_SECTION = "orgunit_section"; //$NON-NLS-1$

    private final String DYN_ORGUNIT_SECTION = "dyn_orgunit_section"; //$NON-NLS-1$

    private ExpandableSectionStacker expandableHeaderController;

    private Control expandablesContainer;

    private OrgUnitsTabSubSection orgUnitSection;

    private DynamicOrgUnitsTabSubSection dynOrgUnitSection;

    private ScrolledComposite scrolledContainer;

    /**
     * @see com.tibco.xpd.om.resources.ui.properties.sections.organization.LocatableGroupSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        Composite root = toolkit.createComposite(scrolledContainer);
        GridLayout gl = new GridLayout(1, false);
        gl.marginLeft = 0;
        gl.marginWidth = 0;
        root.setLayout(gl);

        String sectPrefId = getSectionContainerType().toString();
        expandableHeaderController = new ExpandableSectionStacker(sectPrefId);

        expandableHeaderController.addSection(ORGUNIT_SECTION,
                Messages.OrgUnitsTabSection_orgUnitSection_title,
                200,
                true,
                true);

        expandableHeaderController.addSection(DYN_ORGUNIT_SECTION,
                Messages.OrgUnitsTabSection_dynamicOrgUnits_title,
                200,
                false,
                false);

        expandablesContainer =
                expandableHeaderController.createExpandableSections(root,
                        toolkit,
                        this);

        GridData gd =
                new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                        | GridData.GRAB_HORIZONTAL);
        expandablesContainer.setLayoutData(gd);

        // set contents of sections on to the scrolled composite
        scrolledContainer.setContent(root);

        Point prefSize = root.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        setMinimumHeight(prefSize.y);

        return scrolledContainer;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);

        /*
         * XPD-5300: Don't show for a Dynamic OrgUnit
         */
        if (eo instanceof DynamicOrgUnit) {
            return false;
        }

        return eo instanceof Organization || eo instanceof OrgUnit;
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean shouldRefresh = super.shouldRefresh(notifications);
        /*
         * If a sub-unit has been refreshed then this section needs to be
         * updated (sub-units are not contained in this OrgUnit)
         */
        if (!shouldRefresh && notifications != null) {
            // Also refresh if any of the sub-units have been updated
            for (Notification notification : notifications) {
                if (notification.getNotifier() instanceof OrgUnit) {
                    OrgUnitRelationship relationship =
                            ((OrgUnit) notification.getNotifier())
                                    .getIncomingHierachicalRelationship();
                    if (relationship != null && relationship.getFrom() != null) {
                        return (relationship.getFrom().equals(getInput()));
                    }
                }
            }
        }

        return shouldRefresh;
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#createExpandableSectionContent(java.lang.String,
     *      org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param sectionId
     * @param container
     * @param toolkit
     * @return
     */
    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        if (ORGUNIT_SECTION.equals(sectionId)) {
            orgUnitSection = new OrgUnitsTabSubSection();
            return orgUnitSection.createControls(container, toolkit);
        } else if (DYN_ORGUNIT_SECTION.equals(sectionId)) {
            dynOrgUnitSection = new DynamicOrgUnitsTabSubSection();
            return dynOrgUnitSection.createControls(container, toolkit);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator#expandableSectionStateChanged(java.lang.String)
     * 
     * @param sectionId
     */
    @Override
    public void expandableSectionStateChanged(String sectionId) {
        Point prefSize =
                scrolledContainer.getContent().computeSize(SWT.DEFAULT,
                        SWT.DEFAULT);
        scrolledContainer.setMinSize(prefSize);

        setMinimumHeight(prefSize.y);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        if (orgUnitSection != null) {
            orgUnitSection.refresh();
        }
        if (dynOrgUnitSection != null) {
            dynOrgUnitSection.refresh();
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        if (orgUnitSection != null) {
            orgUnitSection.setInput(items);
        }

        EObject input = getInput();
        if (!isInDynamicOrganization(input)) {
            // Hide the dynamic org unit section
            expandableHeaderController.setSectionVisible(DYN_ORGUNIT_SECTION,
                    true);
            if (dynOrgUnitSection != null) {
                dynOrgUnitSection.setInput(items);
            }
        } else {
            // Hide the dynamic org unit section
            expandableHeaderController.setSectionVisible(DYN_ORGUNIT_SECTION,
                    false);
        }
    }

    /**
     * Check if the input of this section is from a Dynamic Organization.
     * 
     * @param input
     * @return
     */
    private boolean isInDynamicOrganization(EObject input) {
        Organization org = null;
        if (input instanceof Organization) {
            org = ((Organization) input);
        } else if (input instanceof OrgUnit) {
            org = ((OrgUnit) input).getOrganization();
        }
        return org != null && org.isDynamic();
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        // TODO Auto-generated method stub
        return null;
    }
}
