/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.ui.properties.AbstractXpdSection;

/**
 * @author nwilson
 */
public class SashContributionManager {

    private ArrayList<SashContribution> sections;

    public SashContributionManager() {
        sections = new ArrayList<SashContribution>();
        IExtensionPoint extensionPoint =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                "sashPropertySection"); //$NON-NLS-1$
        IConfigurationElement[] configs =
                extensionPoint.getConfigurationElements();
        ArrayList<SashContribution> noAfter = new ArrayList<SashContribution>();
        ArrayList<SashContribution> after = new ArrayList<SashContribution>();
        for (IConfigurationElement config : configs) {
            String id = config.getAttribute("id"); //$NON-NLS-1$
            String className = config.getAttribute("class"); //$NON-NLS-1$
            String filterName = config.getAttribute("filter"); //$NON-NLS-1$
            String sashContributionSection =
                    config.getAttribute("sashContributionSection"); //$NON-NLS-1$
            String afterId = config.getAttribute("afterSection"); //$NON-NLS-1$
            SashContributionLocation location =
                    SashContributionLocation.valueOf(config
                            .getAttribute("location")); //$NON-NLS-1$
            boolean initExpand =
                    "true".equals(config.getAttribute("expandFirstTimeEver")); //$NON-NLS-1$//$NON-NLS-2$
            boolean grabVertical =
                    "true".equals(config.getAttribute("grabExtraVertical")); //$NON-NLS-1$//$NON-NLS-2$
            String expandHeaderLabel = config.getAttribute("expandHeaderLabel"); //$NON-NLS-1$
            if (expandHeaderLabel == null) {
                expandHeaderLabel = "???"; //$NON-NLS-1$
            }

            String minHeightStr =
                    config.getAttribute("minimumHeightOrMinus1ForDefault"); //$NON-NLS-1$
            int minHeight = SWT.DEFAULT;
            if (minHeightStr != null) {
                try {
                    int i = Integer.parseInt(minHeightStr);
                    minHeight = i;
                } catch (NumberFormatException e) {
                }
            }

            try {
                Object sectionObject =
                        config.createExecutableExtension("class"); //$NON-NLS-1$
                if (sectionObject instanceof AbstractXpdSection) {
                    AbstractXpdSection section =
                            (AbstractXpdSection) sectionObject;
                    IFilter filter = null;
                    if (className != null && className.length() != 0) {
                        if (className.equals(filterName)
                                && sectionObject instanceof IFilter) {
                            filter = (IFilter) sectionObject;
                        } else if (filterName != null
                                && filterName.length() != 0) {
                            Object filterObject =
                                    config.createExecutableExtension("filter"); //$NON-NLS-1$
                            if (filterObject instanceof IFilter) {
                                filter = (IFilter) filterObject;
                            }
                        }
                    }
                    SashContribution contribution =
                            new SashContribution(id, section, filter,
                                    sashContributionSection, afterId, location,
                                    initExpand, grabVertical,
                                    expandHeaderLabel, minHeight);
                    if (afterId == null || afterId.length() == 0) {
                        noAfter.add(contribution);
                    } else {
                        after.add(contribution);
                    }
                }
            } catch (CoreException e) {
                Xpdl2ProcessEditorPlugin.getDefault().getLogger().error(e);
            }
        }
        while (after.size() != 0) {
            ArrayList<SashContribution> toRemove =
                    new ArrayList<SashContribution>();
            for (SashContribution contribution : after) {
                String id = contribution.getAfterId();
                SashContribution before = null;
                for (SashContribution current : sections) {
                    if (id.equals(current.getId())) {
                        before = current;
                        break;
                    }
                }
                if (before != null) {
                    sections.add(sections.indexOf(before) + 1, contribution);
                    toRemove.add(contribution);
                } else {
                    for (SashContribution current : noAfter) {
                        if (id.equals(current.getId())) {
                            before = current;
                            break;
                        }
                    }
                    if (before != null) {
                        noAfter.remove(before);
                        sections.add(before);
                        sections.add(contribution);
                        toRemove.add(contribution);
                    }
                }
            }
            if (toRemove.size() == 0) {
                for (SashContribution contribution : after) {
                    sections.add(contribution);
                }
                break;
            }
            for (SashContribution contribution : toRemove) {
                after.remove(contribution);
            }
        }
        for (SashContribution contribution : noAfter) {
            sections.add(contribution);
        }
    }

    /**
     * @param sectionId
     * @param details
     * @param input
     */
    public List<SashContribution> getSections(String sectionId,
            SashContributionLocation details, EObject input) {
        List<SashContribution> filtered = new ArrayList<SashContribution>();
        if (sectionId != null && details != null) {
            for (SashContribution section : sections) {
                if (sectionId.equals(section.getSashContributionSection())
                        && details.equals(section.getLocation())) {
                    IFilter filter = section.getFilter();
                    if (filter == null) {
                        filtered.add(section);
                    } else {
                        boolean select = filter.select(input);
                        if (select) {
                            filtered.add(section);
                        }
                    }
                }
            }
        }
        return filtered;
    }

    public class SashContribution {

        private String id;

        private AbstractXpdSection section;

        private IFilter filter;

        private String sashContributionSection;

        private String afterId;

        private SashContributionLocation location;

        private boolean initExpand = false;

        private boolean grabVertical = false;

        private String expandHeaderLabel;

        private int minimumHeight;

        /**
         * @param id
         * @param section
         * @param filter
         * @param sashContributionSection
         * @param afterId
         * @param location
         */
        public SashContribution(String id, AbstractXpdSection section,
                IFilter filter, String sashContributionSection, String afterId,
                SashContributionLocation location, boolean initExpand,
                boolean grabVertical, String expandHeaderLabel,
                int minimumHeight) {
            this.id = id;
            this.section = section;
            this.filter = filter;
            this.sashContributionSection = sashContributionSection;
            this.afterId = afterId;
            this.location = location;
            this.initExpand = initExpand;
            this.grabVertical = grabVertical;
            this.expandHeaderLabel = expandHeaderLabel;
            this.minimumHeight = minimumHeight;
        }

        public String getId() {
            return id;
        }

        public AbstractXpdSection getSection() {
            return section;
        }

        public IFilter getFilter() {
            return filter;
        }

        public String getSashContributionSection() {
            return sashContributionSection;
        }

        public String getAfterId() {
            return afterId;
        }

        public SashContributionLocation getLocation() {
            return location;
        }

        /**
         * @return the initExpand
         */
        public boolean isInitExpand() {
            return initExpand;
        }

        /**
         * @return the grabVertical
         */
        public boolean isGrabVertical() {
            return grabVertical;
        }

        /**
         * @return the expandHeaderLabel
         */
        public String getExpandHeaderLabel() {
            return expandHeaderLabel;
        }

        /**
         * @return the minimumHeight
         */
        public int getMinimumHeight() {
            return minimumHeight;
        }

        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj instanceof SashContribution) {
                SashContribution other = (SashContribution) obj;
                if (id.equals(other.id)) {
                    equal = true;
                }
            }
            return equal;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

    }

    /**
     * Disposes of any sections that this manager has created. This should be
     * called when the manager is no longer required to ensure that any
     * listeners registered by the sections are cleaned up.
     */
    public void dispose() {
        for (SashContribution contribution : sections) {
            AbstractXpdSection section = contribution.getSection();
            if (section != null) {
                section.dispose();
            }
        }
    }
}
