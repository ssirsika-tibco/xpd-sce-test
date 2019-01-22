/*
 * Copyright (c) TIBCO Software Inc. 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabItem;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.ui.properties.internal.Messages;
import com.tibco.xpd.ui.properties.internal.util.XpdPropertiesUtil;

/**
 * @author wzurek
 */
public class EmptySection implements ISection, ITypeMapper,
        ITabbedPropertySheetPageContributor {

    private static boolean ignoreMapping = false;

    public class NoEmptySection {
    }

    public class HasEmptySection {
    }

    public void createControls(Composite parent,
            TabbedPropertySheetPage sheetPage) {
        TabbedPropertySheetWidgetFactory f = sheetPage.getWidgetFactory();
        Composite root = f.createComposite(parent);
        root.setLayout(new GridLayout());
        f.createLabel(root, Messages.EmptySection_label);
    }

    public void setInput(IWorkbenchPart part, ISelection selection) {
        // do nothing
    }

    public void aboutToBeShown() {
        // do nothing
    }

    public void aboutToBeHidden() {
        // do nothing
    }

    public void dispose() {
        // do nothing
    }

    public int getMinimumHeight() {
        return 20;
    }

    public boolean shouldUseExtraSpace() {
        return false;
    }

    public void refresh() {
        // do nothing
    }

    public Class<?> mapType(Object object) {
        if (EmptySection.ignoreMapping) {
            return NoEmptySection.class;
        }
        EmptySection.ignoreMapping = true;

        try {
            ITabItem[] tabs = XpdPropertiesUtil.getTabDescriptors(this, new StructuredSelection(object));
            if (tabs.length > 0) {
                return NoEmptySection.class;
            }
        } finally {
            EmptySection.ignoreMapping = false;
        }
        return HasEmptySection.class;
    }

    public String getContributorId() {
        return PropertiesPluginConstants.XPD_PROPERTY_CONTRIBUTOR_ID;
    }

}
