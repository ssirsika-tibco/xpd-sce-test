/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.xpd.processeditor.xpdl2.properties.SashContributionManager.SashContribution;
import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.AbstractXpdSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Property section that provides a left and right side sash-divided section
 * whose left and right content is contributed via the
 * <code>com.tibco.xpd.processeditor.xpdl2.sashPropertySection</code> extension
 * point.
 * <p>
 * Each contribution defines a section that is encapsulated within an expandable
 * section buy this class.
 * <p>
 * Each contribution identifies the {@link SashContributionSection} via the
 * sectionId that is passed to
 * {@link SashContributionSection#SashContributionSection(EClass, String)}
 * constructor by the sub-class.
 * 
 * @author aallway
 * @since 25 Jun 2012
 */
public abstract class SashContributionSection extends
        SashDividedFilteredTransactionalSection implements IFilter,
        ISectionContentCreator {

    private String sectionId;

    private Composite generalContainer;

    private ExpandableSectionStacker generalExpandStacker;

    private Composite detailsContainer;

    private ExpandableSectionStacker detailsExpandStacker;

    private XpdFormToolkit generalToolkit;

    private XpdFormToolkit detailsToolkit;

    private List<SashContribution> currentGeneralSections =
            Collections.EMPTY_LIST;

    private List<SashContribution> currentDetailSections =
            Collections.EMPTY_LIST;

    private ScrolledComposite scrolledDetailsContainer;

    private ScrolledComposite scrolledGeneralContainer;

    private SashContributionManager sashContributionManager =
            new SashContributionManager();

    /**
     * @param selectionFilterClass
     * @param sectionId
     */
    public SashContributionSection(EClass selectionFilterClass, String sectionId) {
        super(selectionFilterClass, sectionId);
        this.sectionId = sectionId;
    }

    @Override
    public boolean select(Object toTest) {
        boolean select = false;
        EObject eo = getBaseSelectObject(toTest);
        if (eo != null) {
            List<SashContribution> generalSections =
                    sashContributionManager.getSections(sectionId,
                            SashContributionLocation.GENERAL,
                            eo);
            List<SashContribution> detailSections =
                    sashContributionManager.getSections(sectionId,
                            SashContributionLocation.DETAILS,
                            eo);
            if (generalSections.size() > 0 || detailSections.size() > 0) {
                select = true;
            }
        }
        return select;
    }

    /**
     * @param parent
     * @param toolkit
     * @return
     * @see com.tibco.xpd.ui.properties.SashDividedEObjectSection#createGeneralSection(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Composite createGeneralSection(Composite parent,
            XpdFormToolkit toolkit) {
        scrolledGeneralContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledGeneralContainer.setExpandHorizontal(true);
        scrolledGeneralContainer.setExpandVertical(true);

        generalContainer = toolkit.createComposite(scrolledGeneralContainer);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        generalContainer.setLayout(layout);
        generalToolkit = toolkit;

        scrolledGeneralContainer.setContent(generalContainer);

        return scrolledGeneralContainer;
    }

    /**
     * @param parent
     * @param toolkit
     * @return
     * @see com.tibco.xpd.ui.properties.SashDividedEObjectSection#createDetailsSection(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Composite createDetailsSection(Composite parent,
            XpdFormToolkit toolkit) {
        scrolledDetailsContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledDetailsContainer.setExpandHorizontal(true);
        scrolledDetailsContainer.setExpandVertical(true);

        detailsContainer = toolkit.createComposite(scrolledDetailsContainer);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        detailsContainer.setLayout(layout);
        detailsToolkit = toolkit;
        scrolledDetailsContainer.setContent(detailsContainer);

        return scrolledDetailsContainer;
    }

    protected void refreshSections() {
        /*
         * Sid : Noticed that execution of 'Remove Ad-hoc status and
         * configuration' quick fix for multi instance ad-hoc got an SWTExeption
         * widget disposed. Because property tab removed just as we get
         * refresh..
         */
        if (scrolledGeneralContainer == null
                || scrolledGeneralContainer.isDisposed() || getSash() == null
                || getSash().isDisposed()) {
            return;
        }

        List<SashContribution> newGeneralSections =
                sashContributionManager.getSections(sectionId,
                        SashContributionLocation.GENERAL,
                        getInput());

        boolean forceLayout = false;

        if (!compareLists(newGeneralSections, currentGeneralSections)) {

            disposeSections(generalContainer);

            generalExpandStacker =
                    new ExpandableSectionStacker(sectionId + ".general"); //$NON-NLS-1$

            for (SashContribution contribution : newGeneralSections) {
                generalExpandStacker.addSection(contribution.getId(),
                        contribution.getExpandHeaderLabel(),
                        contribution.getMinimumHeight(),
                        contribution.isInitExpand(),
                        contribution.isGrabVertical());
            }

            currentGeneralSections = newGeneralSections;

            Control genExpCont =
                    generalExpandStacker
                            .createExpandableSections(generalContainer,
                                    generalToolkit,
                                    this);
            genExpCont.setLayoutData(new GridData(GridData.FILL_BOTH
                    | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));

            Point prefSize =
                    generalContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            scrolledGeneralContainer.setMinSize(prefSize);

            forceLayout = true;

        } else {
            refreshCurrentSections(currentGeneralSections);
        }

        /*
         * Sid : Noticed that execution of 'Remove Ad-hoc status and
         * configuration' quick fix for multi instance ad-hoc got an SWTExeption
         * widget disposed. Because property tab removed just as we get
         * refresh..
         */
        if (scrolledGeneralContainer == null
                || scrolledGeneralContainer.isDisposed() || getSash() == null
                || getSash().isDisposed()) {
            return;
        }

        //
        // Now details sections.
        //
        List<SashContribution> newDetailSections =
                sashContributionManager.getSections(sectionId,
                        SashContributionLocation.DETAILS,
                        getInput());

        if (!compareLists(newDetailSections, currentDetailSections)) {
            disposeSections(detailsContainer);

            detailsExpandStacker =
                    new ExpandableSectionStacker(sectionId + ".details"); //$NON-NLS-1$

            for (SashContribution contribution : newDetailSections) {
                detailsExpandStacker.addSection(contribution.getId(),
                        contribution.getExpandHeaderLabel(),
                        contribution.getMinimumHeight(),
                        contribution.isInitExpand(),
                        contribution.isGrabVertical());
            }

            currentDetailSections = newDetailSections;

            Control detExpCont =
                    detailsExpandStacker
                            .createExpandableSections(detailsContainer,
                                    detailsToolkit,
                                    this);
            detExpCont.setLayoutData(new GridData(GridData.FILL_BOTH
                    | GridData.GRAB_VERTICAL | GridData.GRAB_HORIZONTAL));

            Point prefSize =
                    detailsContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            scrolledDetailsContainer.setMinSize(prefSize);

            forceLayout = true;

        } else {
            refreshCurrentSections(currentDetailSections);
        }

        /*
         * Sid : Noticed that execution of 'Remove Ad-hoc status and
         * configuration' quick fix for multi instance ad-hoc got an SWTExeption
         * widget disposed. Because property tab removed just as we get
         * refresh..
         */
        if (scrolledGeneralContainer == null
                || scrolledGeneralContainer.isDisposed() || getSash() == null
                || getSash().isDisposed()) {
            return;
        }
        /*
         * XPD-5427 If we don't have sections on both sides of sash then
         * maximise the other side
         */
        boolean sashVisible = true;

        int numGeneralSections = currentGeneralSections.size();
        int numDetailsSections = currentDetailSections.size();

        if (numGeneralSections > 0 && numDetailsSections > 0) {
            setSashPercentToLastUserSelected();

        } else if (numGeneralSections == 0 && numDetailsSections == 0) {
            setSashPercentToLastUserSelected();
            sashVisible = false;

        } else if (numGeneralSections > 0) {
            setSashPercent(1); /* 100% LHS */
            sashVisible = false;

        } else {
            setSashPercent(MIN_SIDE_SIZE); /* 100% RHS */
            sashVisible = false;
        }

        if (sashVisible != getSash().getVisible()) {
            getSash().setVisible(sashVisible);
            forceLayout = true;
        }

        if (forceLayout) {
            forceLayout();
        }

    }

    /**
     * @param parentComposite
     */
    private void disposeSections(Composite parentComposite) {
        for (Control control : parentComposite.getChildren()) {
            control.dispose();
        }
    }

    /**
     * @param currentGeneralSections2
     */
    private void refreshCurrentSections(
            List<SashContribution> sectionContributions) {
        for (SashContribution contribution : sectionContributions) {
            Object input = getInput();
            if (input == null) {
                input = ""; //$NON-NLS-1$
            }

            Object sectInput = null;
            AbstractXpdSection section = contribution.getSection();
            if (section instanceof AbstractTransactionalSection) {
                sectInput = ((AbstractTransactionalSection) section).getInput();
            }

            if (!input.equals(sectInput)) {
                section.setInput(getPart(), getSelection());
            }

            section.refresh();
        }
    }

    /**
     * @param list1
     * @param list2
     */
    private boolean compareLists(List<SashContribution> list1,
            List<SashContribution> list2) {
        boolean different = false;

        if (list1.size() != list2.size()) {
            different = true;
        } else {
            // Same number of sections - check they're all the same.
            int sz = list1.size();
            for (int i = 0; i < sz; i++) {
                if (!list1.get(i).equals(list2.get(i))) {
                    different = true;
                    break;
                }
            }
        }

        return !different;
    }

    /**
     * @param obj
     * @return
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();

        refreshSections();
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean refresh = false;
        List<SashContribution> generalSections =
                sashContributionManager.getSections(sectionId,
                        SashContributionLocation.GENERAL,
                        getInput());
        for (SashContribution contribution : generalSections) {
            if (contribution.getSection() instanceof SashSection) {
                refresh =
                        ((SashSection) contribution.getSection())
                                .shouldSashSectionRefresh(notifications);
                if (refresh) {
                    break;
                }
            }
        }
        if (!refresh) {
            List<SashContribution> detailSections =
                    sashContributionManager.getSections(sectionId,
                            SashContributionLocation.DETAILS,
                            getInput());

            for (SashContribution contribution : detailSections) {
                if (contribution.getSection() instanceof SashSection) {
                    refresh =
                            ((SashSection) contribution.getSection())
                                    .shouldSashSectionRefresh(notifications);
                    if (refresh) {
                        break;
                    }
                }
            }
        }
        return refresh;
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
        SashContribution sectContribution = null;

        for (SashContribution contr : currentGeneralSections) {
            if (sectionId.equals(contr.getId())) {
                sectContribution = contr;
                break;
            }
        }

        if (sectContribution == null) {
            for (SashContribution contr : currentDetailSections) {
                if (sectionId.equals(contr.getId())) {
                    sectContribution = contr;
                    break;
                }
            }
        }

        if (sectContribution != null) {
            Composite wrapper = toolkit.createComposite(container);
            wrapper.setLayout(new FillLayout());

            AbstractXpdSection section = sectContribution.getSection();
            section.createControls(wrapper, getPropertySheetPage());
            section.setInput(getPart(), getSelection());
            section.refresh();

            return wrapper;
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
        return;
    }

    /**
     * @see com.tibco.xpd.ui.properties.SashDividedTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        sashContributionManager.dispose();
        super.dispose();
    }

    /**
     * 
     * @param id
     *            the Id of the {@link Section} to fetch
     * @return the {@link Section} for the passed id , else return
     *         <code>null</code> if no Section with the specified id found.
     */
    protected Section getSection(String id) {

        Section section = null;

        /*
         * First look through the general sections
         */
        if (generalExpandStacker != null) {

            section = generalExpandStacker.getExpandableSection(id);

        }

        /*
         * look through the details section.
         */
        if (section == null && detailsExpandStacker != null) {

            section = detailsExpandStacker.getExpandableSection(id);
        }

        return section;
    }
}
