/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.Section;

/**
 * ExpandableSectionStacker
 * <p>
 * This class is a helper for property pages containing multiple expandable
 * sections.
 * <p>
 * It creates and controls the the expandable sections, takes away some of the
 * layout complexity, and provides 'best-fit' behaviour.
 * <p>
 * By 'best fit' we mean that when the user expands a collapsed section, this
 * class will work out if there is enough room in the parent for it to expand
 * into. If not then other section(s) will be closed to make room for it.
 * <p>
 * THis is done on the following basis...
 * <li>If possible, the smallest SINGLE currently expanded section that would
 * make enough room for the newly expanded section is collapsed.</li>
 * <li>If this is not possible, then multiple sections are collapsed until there
 * is room for the newly expanded section (in order from biggest to smallest.</li>
 * 
 * <p>
 * The idea is that you can make the best use of a small property sheet area
 * that needs to contain many controls (without resorting to extra property
 * tabs.
 * 
 * <p>
 * <b>How to use this class...</b>
 * <li>Create an instance of the class.</li>
 * <li>Use addSection() to describe and each expandable section (the section is
 * not created yet</li>
 * <li>After ALL sections have been added call createExpandableSections(). The
 * passed ISectionContentCreator is called for each section passing the id for
 * that section. Create the controls for each section (adding them to the given
 * container).</li>
 * 
 * @author aallway
 * @since 3.1
 */
public class ExpandableSectionStacker implements IExpansionListener {

    /**
     * ISectionContentCreator
     * <p>
     * Provides the 'call-back' method that allows the creator to add it's
     * controls to the given section (note: is not necessarily the expandable
     * Section comnposite!).
     */
    public interface ISectionContentCreator {
        /**
         * Asks the creator to add the controls to the given section.
         * <p>
         * This method is called for each section added via the addSection()
         * method <i>when</i> the creator calls the createExpandableSections()
         * method.
         * 
         * @param sectionId
         *            The section id.
         * @param container
         *            The composite to add your controls to.
         * @param toolkit
         *            The toolkit to use when creatign the controls.
         * 
         * @return The main control that you have added to the section
         *         (nominally, this method will create it's own composite and
         *         add all it's controls to that).
         */
        public Control createExpandableSectionContent(String sectionId,
                Composite container, XpdFormToolkit toolkit);

        /**
         * This method is provided so that the creator can react to a section
         * being expanded or collapsed.
         * <p>
         * You may not need to perform anything special, but it should be noted
         * that some section layout's (such as placing the expandable sections
         * within a scrolled composite) may require to force a complete
         * re-layout of the section in order to ensure that the scrollbars are
         * updated etc.
         * 
         * @param sectionId
         */
        public void expandableSectionStateChanged(String sectionId);

    }

    /**
     * List of info for the added sections.
     */
    private List<ExpandableSectionInfo> sections =
            new ArrayList<ExpandableSectionInfo>();

    /**
     * The container of the expandable sections.
     */
    private Composite expandablesContainer;

    private ISectionContentCreator sectionContentCreator = null;

    /**
     * This is the nominal size of just the headers.
     */
    private int collapsedHeadersHeight = 0;

    /**
     * A unique Id for this group of expandable sections.
     */
    private String sectionGroupId;

    /**
     * Create an expandable section stacker.
     * 
     * @param sectionGroupId
     *            Used for storing the last expansion state of the sections in
     *            preferences
     */
    public ExpandableSectionStacker(String sectionGroupId) {
        this.sectionGroupId = sectionGroupId;
    }

    /**
     * Add and describe an expandable section.
     * <p>
     * <i>If <code>extraHeightGrabber</code> is set to 'false' then it is highly
     * recommended to use SWT.DEFAULT (-1) for <code>minimumHeight</code>
     * parameter so the section uses its child's size.</i>
     * </p>
     * 
     * @param sectionId
     *            This will be passed back to the ISectionContentCreator during
     *            the call to the createExpandableSections() method.
     * @param label
     *            The label for the expansion header bar.
     * @param minimumHeight
     *            Fixed height hint for the section (you can use SWT.DEFAULT
     *            <i>but</i> for many layout schemes this would allow the
     *            section to be collapsed to nothing without activating
     *            scrollbars in the main section.).
     * @param initExpanded
     *            true if the section is to be initially expanded.
     * @param extraHeightGrabber
     *            This section should grab extra available height from other
     *            sections if there is any spare.
     */
    public void addSection(String sectionId, String label, int minimumHeight,
            boolean initExpanded, boolean extraHeightGrabber) {
        ExpandableSectionInfo section =
                new ExpandableSectionInfo(sectionId, label, minimumHeight,
                        initExpanded, extraHeightGrabber);

        sections.add(section);

        // Override the initial expansion state if we have one store in prefs.
        section.initExpanded = getStoredExpansionState(section.sectionId);

        return;
    }

    /**
     * @param sectionId
     *            Section Id (as previously passed to addSection()
     * 
     * @return Returns the expandable section for the given section id or null
     *         on error.
     */
    public Section getExpandableSection(String sectionId) {
        for (ExpandableSectionInfo section : sections) {
            if (sectionId.equals(section.sectionId)) {
                return section.expandableSection;
            }
        }

        return null;
    }

    /**
     * 
     * @return Returns all the expandable sections
     * 
     */
    public List<ExpandableSectionInfo> getExpandableSections() {
        return sections;
    }

    /**
     * After adding ALL the required expandable sections (using addSection())
     * call this method to create the sections.
     * <p>
     * This method will use the given sectionCreator to 'call back' to add the
     * content for each section in turn.
     * 
     * @param parent
     *            Container of all expandable headers.
     * @param toolkit
     *            Toolkit to use to create controls.
     * @param sectionCreator
     *            Section content creator.
     * 
     * @return The container (which holds the expandable sections) that was
     *         added to the parent, the caller is then free to set the layout
     *         data to this (according to it's layout scheme requirements).
     */
    public Control createExpandableSections(Composite parent,
            XpdFormToolkit toolkit, ISectionContentCreator sectionCreator) {

        sectionContentCreator = sectionCreator;

        expandablesContainer = toolkit.createComposite(parent);

        GridLayout gl = new GridLayout(1, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        expandablesContainer.setLayout(gl);

        //
        // Create the expandable headers...
        //
        for (ExpandableSectionInfo section : sections) {
            if (section.expandableSection == null) {
                //
                // First the expansion widget
                //
                section.expandableSection =
                        toolkit.createSection(expandablesContainer,
                                Section.TWISTIE
                                        | Section.CLIENT_INDENT
                                        | Section.TITLE_BAR
                                        | (section.initExpanded ? Section.EXPANDED
                                                : 0));

                collapsedHeadersHeight +=
                        section.expandableSection.computeSize(SWT.DEFAULT,
                                SWT.DEFAULT).y;

                section.expandableSection.setText(section.sectionName);

                int gdStyle = getExpandableSectionGridDataStyle(section);

                GridData gd = new GridData(gdStyle);
                section.expandableSection.setLayoutData(gd);

                section.expandableSection.addExpansionListener(this);

                //
                // Then a composite to contain the owner's controls for the
                // section.
                section.contentContainer =
                        toolkit.createComposite(section.expandableSection);

                GridData contentContainerGD =
                        new GridData(
                                GridData.FILL_BOTH
                                        | (section.extraHeightGrabber ? GridData.GRAB_VERTICAL
                                                : 0));
                section.contentContainer.setLayoutData(contentContainerGD);

                GridLayout contentGL = new GridLayout(1, false);
                contentGL.marginHeight = 0;
                contentGL.marginWidth = 0;
                section.contentContainer.setLayout(gl);

                section.expandableSection.setClient(section.contentContainer);
                
                /*
                 * XPD-8380: Saket: Need to make the background color the same as the expandables container 
                 * to make the layout look uniform (to fix the issues introduced after the adoption of Eclipse oxygen).
                 */
                section.contentContainer.getParent().setBackground(expandablesContainer.getBackground());

                section.content =
                        sectionCreator
                                .createExpandableSectionContent(section.sectionId,
                                        section.contentContainer,
                                        toolkit);
                if (section.content != null) {
                    GridData contentGD = new GridData(GridData.FILL_BOTH);
                    contentGD.heightHint = section.minimumHeight;
                    contentGD.minimumHeight = section.minimumHeight;
                    section.content.setLayoutData(contentGD);

                } else {
                    throw new RuntimeException(
                            "Owner failed to return content for section: " + section.sectionId); //$NON-NLS-1$
                }

            } else {
                throw new RuntimeException(
                        "ExpandableSectioController can only initialise once!"); //$NON-NLS-1$
            }
        }

        for (ExpandableSectionInfo section : sections) {
            if (section.expandableSection != null) {
                internalSetExpansionState(section, section.initExpanded);
            }
        }

        return expandablesContainer;
    }

    /**
     * Sets Visibility of the Section.
     * 
     * @param sectionId
     * @param isVisible
     */
    public void setSectionVisible(String sectionId, boolean isVisible) {
        for (ExpandableSectionInfo section : sections) {
            if (sectionId.equals(section.sectionId)) {

                if (section.expandableSection.getVisible() != isVisible) {
                    if (isVisible) {
                        int gdStyle =
                                getExpandableSectionGridDataStyle(section);

                        GridData gd = new GridData(gdStyle);
                        section.expandableSection.setLayoutData(gd);

                        section.expandableSection.setVisible(true);

                        expandablesContainer.layout();
                    } else {
                        GridData gd = new GridData();
                        gd.heightHint = 0;
                        gd.widthHint = 0;

                        section.expandableSection.setLayoutData(gd);
                        section.expandableSection.setVisible(false);

                        expandablesContainer.layout();
                    }
                }
            }
        }
    }

    /**
     * @param section
     * @return
     */
    private int getExpandableSectionGridDataStyle(ExpandableSectionInfo section) {
        int gdStyle;
        if (section.extraHeightGrabber) {
            gdStyle =
                    GridData.FILL_BOTH | GridData.HORIZONTAL_ALIGN_BEGINNING
                            | GridData.VERTICAL_ALIGN_BEGINNING;

        } else {
            gdStyle =
                    GridData.FILL_HORIZONTAL
                            | GridData.HORIZONTAL_ALIGN_BEGINNING
                            | GridData.VERTICAL_ALIGN_BEGINNING;

        }
        return gdStyle;
    }

    /**
     * Returns the nominal height of all the expandable headers (just the
     * headers in collapsed mode).
     * <p>
     * <b>Note</b> only available after createExpandable Headers is called.
     * 
     * @return the collapsedHeadersHeight
     */
    public int getCollapsedHeadersHeight() {
        return collapsedHeadersHeight;
    }

    /**
     * React to a section being expanded / collapsed.
     */
    public void expansionStateChanged(ExpansionEvent e) {

        ExpandableSectionInfo changedSection = getSectionInfo(e);
        boolean expanded = e.getState();

        setExpansionState(changedSection.sectionId, expanded, true);

        return;
    }

    /**
     * @param sectionid
     * @return <code>true</code> if the given section is expanded.
     */
    public boolean isExpanded(String sectionId) {
        Section section = getExpandableSection(sectionId);
        if (section != null) {
            return section.isExpanded();
        }
        return true;
    }

    /**
     * Set the expansion state of the given section.
     * <p>
     * <b>This method should be used in preference to calling
     * section.setExpand() directly.</b>
     * <p>
     * Unlike the individual section.setExpand() this methd behaves exactly as
     * if the user had selected the expansion state (i.e. will rearrange other
     * sections expansion state to make room and so on).
     * 
     * @param sectionId
     * @param expanded
     */
    public void setExpansionState(String sectionId, boolean expanded,
            boolean storeState) {

        ExpandableSectionInfo sectionInfo = null;
        for (ExpandableSectionInfo s : sections) {
            if (s.sectionId.equals(sectionId)) {
                sectionInfo = s;
                break;
            }
        }

        if (sectionInfo != null && expandablesContainer != null
                && !expandablesContainer.isDisposed()) {

            if (expanded != sectionInfo.expandableSection.isExpanded()) {
                sectionInfo.expandableSection.setExpanded(expanded);
            }

            //
            // If section is being expanded, close enough of the others to
            // make space for the new one opening.
            //

            if (expanded) {
                //
                // Ok - all headers are in the state the user has requested
                // (by pressing open/close).
                // Work out the nominal height we have available to us and
                // then how much space is nominally required by each
                // section.
                //
                int availableHeight =
                        expandablesContainer.getParent().getSize().y;
                int requiredHeight = 0;

                int verticalSpacing =
                        ((GridLayout) expandablesContainer.getLayout()).verticalSpacing;

                for (ExpandableSectionInfo section : sections) {

                    section.temporaryNominalHeight =
                            section.expandableSection.computeSize(SWT.DEFAULT,
                                    SWT.DEFAULT).y;
                    requiredHeight += section.temporaryNominalHeight;
                }

                requiredHeight += (sections.size()) * verticalSpacing;

                //
                // If there is not enough room in the container's height
                // then collapse sections to make room.
                //
                if (requiredHeight > availableHeight) {
                    int extraSpaceNeeded = requiredHeight - availableHeight;
                    ExpandableSectionInfo singleSectionToCollapse = null;

                    //
                    // See if there is a single expanded section that can be
                    // closed to make enough space.
                    // (Selecting the smallest section required to make
                    // enough space).
                    for (ExpandableSectionInfo section : sections) {
                        if (section != sectionInfo
                                && section.expandableSection.isExpanded()) {
                            if (section.temporaryNominalHeight >= extraSpaceNeeded) {
                                if (singleSectionToCollapse == null) {
                                    singleSectionToCollapse = section;

                                } else {
                                    // We already found one section that is
                                    // larger than the space required. If
                                    // this one is larger than the space
                                    // needed BUT smaller than the current
                                    // one selected then collapse this one
                                    // instead.
                                    if (section.temporaryNominalHeight < singleSectionToCollapse.temporaryNominalHeight) {
                                        singleSectionToCollapse = section;
                                    }
                                }
                            }
                        }
                    }

                    if (singleSectionToCollapse != null) {
                        //
                        // It is enough to collapse one single section to
                        // make room.
                        singleSectionToCollapse.expandableSection
                                .setExpanded(false);

                        // setExpansionState(singleSectionToCollapse,
                        // false);

                    } else {
                        //
                        // There was not a single section that could be
                        // closed to make room. Collapse as many as required
                        // to make space.
                        List<ExpandableSectionInfo> bySize =
                                new ArrayList<ExpandableSectionInfo>();
                        bySize.addAll(sections);

                        Collections.sort(bySize,
                                new Comparator<ExpandableSectionInfo>() {
                                    public int compare(
                                            ExpandableSectionInfo o1,
                                            ExpandableSectionInfo o2) {
                                        return o2.temporaryNominalHeight
                                                - o1.temporaryNominalHeight;
                                    }
                                });

                        int spaceMadeAvailable = 0;

                        for (ExpandableSectionInfo section : bySize) {
                            if (section != sectionInfo
                                    && section.expandableSection.isExpanded()) {

                                section.expandableSection.setExpanded(false);

                                spaceMadeAvailable +=
                                        section.temporaryNominalHeight;
                                if (spaceMadeAvailable >= extraSpaceNeeded) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            //
            // Finally reset the layout data for all sections according to
            // their current expansion state.
            //
            for (ExpandableSectionInfo section : sections) {
                boolean isExpanded = section.expandableSection.isExpanded();

                if (section.expandableSection.isVisible()) {
                    internalSetExpansionState(section, isExpanded);

                    // Store the expansion state in prefs.
                    if (storeState) {
                        /*
                         * Only ever store expansion state for enabled sections
                         * - nominally things like paritcipant section want to
                         * keep collapsed the sections that are disabled - so
                         * when expanding or contracting other sections then we
                         * should ignore disabled sections.
                         */
                        if (section.expandableSection.isEnabled()) {
                            storeNewExpansionState(section, isExpanded);
                        }
                    }
                }
            }

            expandablesContainer.layout();

            sectionContentCreator
                    .expandableSectionStateChanged(sectionInfo.sectionId);

        }

        return;
    }

    /**
     * Store the expansion state of the given section.
     * 
     * @param section
     * @param expanded
     */
    private void storeNewExpansionState(ExpandableSectionInfo section,
            boolean expanded) {
        IPreferenceStore prefs =
                PropertiesPlugin.getDefault().getPreferenceStore();

        String prefId = getExpansionStatePreferenceId(section);

        //
        // Have to use int because for boolean, the pref gets removed if
        // returned to it's default value (false) so we can't tell whether
        // it's false or never been set.
        //
        // 1 == Collapsed
        // 2 == Expanded

        prefs.setValue(prefId, expanded ? 2 : 1);

        return;
    }

    /**
     * Get the previously preference-stored expansion state of the given
     * section.
     * 
     * @param sectionId
     * 
     * @return pref-stored state or defaultState if not in prefs.
     */
    public boolean getStoredExpansionState(String sectionId) {

        ExpandableSectionInfo section = null;
        for (ExpandableSectionInfo s : sections) {
            if (s.sectionId.equals(sectionId)) {
                section = s;
                break;
            }
        }

        boolean defaultState = false;

        if (section != null) {
            defaultState = section.initExpanded;

            IPreferenceStore prefs =
                    PropertiesPlugin.getDefault().getPreferenceStore();

            String prefId = getExpansionStatePreferenceId(section);

            if (prefs.contains(prefId)) {
                //
                // Have to use int because for boolean, the pref gets removed if
                // returned to it's default value (false) so we can't tell
                // whether
                // it's false or never been set.
                //
                // 1 == Collapsed
                // 2 == Expanded
                int i = prefs.getInt(prefId);

                if (i == 1) {
                    return false;
                } else if (i == 2) {
                    return true;
                }

            }
        }
        return defaultState;
    }

    /**
     * @param section
     * @return
     */
    private String getExpansionStatePreferenceId(ExpandableSectionInfo section) {
        String prefId =
                sectionGroupId + "." + section.sectionId
                        + ".sectionExpandState";
        return prefId;
    }

    /**
     * Set the individual expansion state of the given section.
     * <p>
     * This is the raw set state and size of a given expandable section (it does
     * not resize other sections etc to make room).
     * 
     * @param section
     * @param expanded
     */
    private void internalSetExpansionState(ExpandableSectionInfo section,
            boolean expanded) {
        if (section.expandableSection == null
                || section.expandableSection.isDisposed()
                || section.contentContainer == null
                || section.contentContainer.isDisposed()) {
            return;
        }

        GridData gd;

        if (expanded) {
            int gdStyle = getExpandableSectionGridDataStyle(section);

            gd = new GridData(gdStyle);
            gd.heightHint = section.minimumHeight;
            gd.minimumHeight = section.minimumHeight;
            section.expandableSection.setLayoutData(gd);

            GridData contentContainerGD =
                    new GridData(
                            GridData.FILL_BOTH
                                    | (section.extraHeightGrabber ? GridData.GRAB_VERTICAL
                                            : 0));
            section.contentContainer.setLayoutData(contentContainerGD);

            GridData contentGD = new GridData(GridData.FILL_BOTH);
            contentGD.heightHint = section.minimumHeight;
            section.content.setLayoutData(contentGD);

        } else {
            gd =
                    new GridData(GridData.FILL_HORIZONTAL
                            | GridData.HORIZONTAL_ALIGN_BEGINNING
                            | GridData.VERTICAL_ALIGN_BEGINNING);
            section.expandableSection.setLayoutData(gd);

            GridData contentContainerGD =
                    new GridData(
                            GridData.FILL_BOTH
                                    | (section.extraHeightGrabber ? GridData.GRAB_VERTICAL
                                            : 0));
            section.contentContainer.setLayoutData(contentContainerGD);

            GridData contentGD = new GridData(GridData.FILL_BOTH);
            contentGD.heightHint = section.minimumHeight;
            section.content.setLayoutData(contentGD);

        }

        section.expandableSection.layout();

        return;
    }

    public void expansionStateChanging(ExpansionEvent e) {
    }

    /**
     * Find the section info for the given expansion event
     * 
     * @param expansionEvent
     *            The expansion event
     * 
     * @return The section info or null if not found.
     */
    private ExpandableSectionInfo getSectionInfo(ExpansionEvent expansionEvent) {
        if (expansionEvent.getSource() instanceof Control) {
            Control ctrl = (Control) expansionEvent.getSource();

            for (ExpandableSectionInfo section : sections) {
                if (ctrl == section.expandableSection) {
                    return section;
                }
            }
        }
        return null;
    }

    /**
     * ExpandableSectionInfo
     * <p>
     * Little data class to store info regarding each section.
     * 
     */
    private class ExpandableSectionInfo {
        String sectionId;

        String sectionName;

        int minimumHeight;

        int temporaryNominalHeight;

        Section expandableSection = null;

        Composite contentContainer = null;

        Control content = null;

        private boolean initExpanded = false;

        private boolean extraHeightGrabber;

        public ExpandableSectionInfo(String sectionId, String sectionName,
                int heightHint, boolean initExpanded, boolean extraHeightGrabber) {
            super();
            this.sectionId = sectionId;
            this.sectionName = sectionName;
            this.minimumHeight = heightHint;
            this.initExpanded = initExpanded;
            this.extraHeightGrabber = extraHeightGrabber;
        }

    }

}
