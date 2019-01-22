/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import com.tibco.xpd.rcp.RCPActivator;
import com.tibco.xpd.rcp.ribbon.AbstractRibbonGroup;
import com.tibco.xpd.resources.util.DependencySorter;
import com.tibco.xpd.resources.util.DependencySorter.Arc;

/**
 * Ribbon extension helper class. This provides functions to get all the defined
 * tabs and groups in the extension.
 * 
 * @author njpatel
 * 
 */
public final class RibbonExtHelper {

    private static final String EXT_ID = "ribbon"; //$NON-NLS-1$

    private static final String TAB = "tab"; //$NON-NLS-1$

    private static final String GROUP = "group"; //$NON-NLS-1$

    private final List<Tab> tabs;

    private final Map<String /* tab id */, List<TabGroup>> groups;

    public RibbonExtHelper() {
        tabs = new ArrayList<Tab>();
        groups = new HashMap<String, List<TabGroup>>();

        loadExtension();
    }

    /**
     * Get all registered tabs.
     * 
     * @return
     */
    public Collection<Tab> getTabs() {
        return tabs;
    }

    /**
     * Get groups registered for a given tab.
     * 
     * @param tabId
     * @return
     */
    public Collection<TabGroup> getGroups(String tabId) {
        return groups.get(tabId);
    }

    /**
     * Load the extension point.
     */
    private void loadExtension() {
        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(RCPActivator.PLUGIN_ID, EXT_ID);
        if (point != null) {
            IConfigurationElement[] elements = point.getConfigurationElements();

            for (IConfigurationElement element : elements) {
                if (TAB.equals(element.getName())) {
                    tabs.add(new Tab(element));
                } else if (GROUP.equals(element.getName())) {
                    TabGroup group = new TabGroup(element);
                    if (group.getTabId() != null) {
                        List<TabGroup> gr = null;

                        if (groups.containsKey(group.getTabId())) {
                            gr = groups.get(group.getTabId());
                        } else {
                            gr = new ArrayList<TabGroup>();
                            groups.put(group.getTabId(), gr);
                        }
                        gr.add(group);
                    }
                }
            }

            sortTabs(tabs);
            sortGroups(groups);
        }
    }

    /**
     * @param tabs
     */
    private void sortTabs(List<Tab> tabs) {
        List<Arc<Tab>> arcs = new ArrayList<Arc<Tab>>();
        for (Tab tab : tabs) {
            if (tab.getAfterTabId() != null) {
                Tab toTab = getTab(tabs, tab.getAfterTabId());
                if (toTab != null) {
                    arcs.add(new Arc<Tab>(tab, toTab));
                }
            }
        }

        DependencySorter<Tab> sorter = new DependencySorter<Tab>(arcs, tabs);
        List<Tab> orderedList = sorter.getOrderedList();

        tabs.clear();
        tabs.addAll(orderedList);
    }

    /**
     * @param groups
     */
    private void sortGroups(Map<String, List<TabGroup>> groups) {
        for (String tabId : groups.keySet()) {
            List<TabGroup> grs = groups.get(tabId);
            if (grs != null && !grs.isEmpty()) {
                List<Arc<TabGroup>> arcs = new ArrayList<Arc<TabGroup>>();

                for (TabGroup gr : grs) {
                    if (gr.getAfterGroupId() != null) {
                        TabGroup toGr = getGroup(grs, gr.getAfterGroupId());
                        if (toGr != null) {
                            arcs.add(new Arc<TabGroup>(gr, toGr));
                        }
                    }
                }

                DependencySorter<TabGroup> sorter =
                        new DependencySorter<TabGroup>(arcs, grs);
                List<TabGroup> orderedList = sorter.getOrderedList();
                groups.put(tabId, orderedList);
            }
        }
    }

    /**
     * @param tabs
     * @param id
     * @return
     */
    private Tab getTab(List<Tab> tabs, String id) {
        for (Tab tab : tabs) {
            if (tab.getId().equals(id)) {
                return tab;
            }
        }
        return null;
    }

    /**
     * @param groups
     * @param id
     * @return
     */
    private TabGroup getGroup(List<TabGroup> groups, String id) {
        for (TabGroup group : groups) {
            if (group.getId().equals(id)) {
                return group;
            }
        }
        return null;
    }

    /**
     * Represents the Tab in the ribbon.
     * 
     * @author njpatel
     * 
     */
    public class Tab {

        private final IConfigurationElement element;

        private String id;

        private String label;

        private String afterTab;

        private Tab(IConfigurationElement element) {
            this.element = element;
        }

        public String getId() {
            if (id == null) {
                id = element.getAttribute("id"); //$NON-NLS-1$
            }
            return id;
        }

        public String getLabel() {
            if (label == null) {
                label = element.getAttribute("label"); //$NON-NLS-1$
            }
            return label;
        }

        public String getAfterTabId() {
            if (afterTab == null) {
                afterTab = element.getAttribute("afterTab"); //$NON-NLS-1$
            }
            return afterTab;
        }

        @Override
        public String toString() {
            return getId();
        }
    }

    /**
     * Represents a Group in a Tab.
     * 
     * @author njpatel
     * 
     */
    public class TabGroup {

        private final IConfigurationElement element;

        private String id;

        private AbstractRibbonGroup clazz;

        private String afterGroup;

        private String tabId;

        private String label;

        private TabGroup(IConfigurationElement element) {
            this.element = element;
        }

        public String getId() {
            if (id == null) {
                id = element.getAttribute("id"); //$NON-NLS-1$
            }
            return id;
        }

        public String getLabel() {
            if (label == null) {
                label = element.getAttribute("label"); //$NON-NLS-1$
            }
            return label;
        }

        public String getTabId() {
            if (tabId == null) {
                tabId = element.getAttribute("tab"); //$NON-NLS-1$
            }
            return tabId;
        }

        public AbstractRibbonGroup getRibbonGroupClass() throws CoreException {
            if (clazz == null) {
                clazz =
                        (AbstractRibbonGroup) element
                                .createExecutableExtension("class"); //$NON-NLS-1$
            }
            return clazz;
        }

        public String getAfterGroupId() {
            if (afterGroup == null) {
                afterGroup = element.getAttribute("afterGroup"); //$NON-NLS-1$
            }
            return afterGroup;
        }

        @Override
        public String toString() {
            return getId();
        }
    }
}
