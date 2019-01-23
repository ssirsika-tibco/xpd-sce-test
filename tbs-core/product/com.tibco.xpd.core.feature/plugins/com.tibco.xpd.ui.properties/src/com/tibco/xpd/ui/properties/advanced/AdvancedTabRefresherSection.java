/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.ui.properties.advanced;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.ui.properties.AbstractTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.internal.util.XpdPropertiesUtil;

/**
 * This section is designed to be contributed to an Advanced properties tab in
 * order to allow triggering a refresh when the model changes.
 * <p>
 * This model change listening is not normally available when the project
 * explorer is the selection provider (and hence contributes it's own
 * AdvancedPropertySection to it's own Advanced tab.
 * <p>
 * <b>How we achieve refresh on model change...</b>
 * <li>This property section should be contributed to any Advanced tab that you
 * wish to force a refresh on model change. This is done using the
 * propertySections extension in plugin.xml...</li>
 * 
 * <pre>
 *  &lt;extension 
 *        point=&quot;org.eclipse.ui.views.properties.tabbed.propertySections&quot;&gt;
 *    &lt;propertySections 
 *        contributorId=&quot;org.eclipse.ui.navigator.ProjectExplorer&quot;&gt;
 *      &lt;propertySection
 *          afterSection=&quot;CommonNavigator.section.Advanced&quot;
 *          class=&quot;com.tibco.xpd.analyst.resources.xpdl2.properties.advanced.InputListeningAdvancedPropSection&quot;
 *          enablesFor=&quot;1&quot;
 *          id=&quot;com.tibco.xpd.resources.xpdl2.inputListeningAdvancedPropSection&quot;
 *          tab=&quot;CommonNavigator.tab.Resource&quot;&gt;
 *        &lt;input
 *            type=&quot;org.eclipse.emf.ecore.EObject&quot;&gt;
 *        &lt;/input&gt;
 *      &lt;/propertySection&gt;
 *    &lt;/propertySections&gt;
 *  &lt;/extension&gt;
 * </pre>
 * 
 * <li>In the example extension above, we add this section to the project
 * explorer-contributed "CommonNavigator.tab.Advanced" tab after the standard
 * "CommonNavigator.section.Advanced" section.</li>
 * <li>You can also contribute it to your own input-selection view that
 * contributer's its own Advanced tab and section (for example GMF/GEF editor).</li>
 * </p>
 * 
 * <pre></pre>
 * 
 * <p>
 * <li>This section is then <i>guaranteed</i> to get a setInput() every time the
 * AdvancedPropertySection gets a setInput().</li>
 * <li>When setInput() is called we add a WorkingCopy property changed listener
 * to listen for commands being run on the model.</li>
 * <li>When the listener fires we get the currently selected property tab from
 * the property sheet page and then search thru the sections looking for an
 * AdvancedPropertySection.</li>
 * <li>When we find one we simply refresh it.</li>
 * <li>This in turn will cause AdvancedPropertySection to ask all the
 * PropertySourceProvider for the latest values for all the properties.</li>
 * </p>
 * </pre> <b>Note 1: In version 3.6 the section id was changed from:
 * com.tibco.xpd.analyst.resources.xpdl2.inputListeningAdvancedPropSection</b>
 * <p/>
 * <b>Note 2: In version 3.6 CommonNavigator no longer contribute:
 * CommonNavigator.tab.Resource instead: CommonNavigator.tab.Advanced tab.</b>
 * </pre>
 * 
 * @author aallway
 */
public class AdvancedTabRefresherSection extends AbstractTransactionalSection {

    private boolean alreadyRefreshing = false;

    public AdvancedTabRefresherSection() {
        super();
        setMinimumHeight(SWT.DEFAULT);
        // Don't include this section in the creation wizards
        setShowInWizard(false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractTransactionalSection#resollveInput
     * (java.lang.Object)
     */
    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EObject) {
            return (EObject) object;
        }
        if (object instanceof IAdaptable) {
            return (EObject) ((IAdaptable) object).getAdapter(EObject.class);
        }
        return null;
    }

    /*
     * If there's and AdvancedPropertiesSection in the current tab then refresh
     * it.
     */
    private void refreshAdvancedSection() {
        if (alreadyRefreshing) {
            return;
        }

        alreadyRefreshing = true;

        try {
            TabbedPropertySheetPage tabbedPropertySheetPage =
                    getPropertySheetPage();
            if (tabbedPropertySheetPage != null) {
                ISection[] sections =
                        XpdPropertiesUtil.getSections(tabbedPropertySheetPage);

                if (sections != null) {
                    for (int i = 0; i < sections.length; i++) {
                        ISection section = sections[i];

                        if (section instanceof AdvancedPropertySection) {
                            // System.out.println("REFRESHING ADVANCED TAB
                            // SECTION");
                            section.refresh();
                        }
                    }
                }
            }
        } finally {
            alreadyRefreshing = false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        // Set up a layout that returns the smallest valid size that section
        // layout will allow.
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new Layout() {

            @Override
            protected Point computeSize(Composite composite, int wHint,
                    int hHint, boolean flushCache) {

                return new Point(wHint, 1);
            }

            @Override
            protected void layout(Composite composite, boolean flushCache) {
            }
        });

        return root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#shouldUseExtraSpace()
     */
    @Override
    public boolean shouldUseExtraSpace() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        refreshAdvancedSection();

    }

}
