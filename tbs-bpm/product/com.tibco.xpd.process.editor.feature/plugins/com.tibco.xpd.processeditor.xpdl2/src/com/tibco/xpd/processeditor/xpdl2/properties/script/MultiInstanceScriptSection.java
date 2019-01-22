/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * 
 * <p>
 * <i>Created: 4 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public class MultiInstanceScriptSection extends
        AbstractFilteredTransactionalSection {
    private CTabFolder tabFolder;

    private Set<BaseScriptSection> subSections =
            new HashSet<BaseScriptSection>();

    // loop section
    private MILoopExpressionSection miLoopExprSection;

    // complex section
    private MIComplexExpressionSection miComplexExprSection;

    // additional instances section
    private MIAdditionalInstancesSection miAdditionalSection;

    private SelectionListener tabSelectionListener;

    public MultiInstanceScriptSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        miLoopExprSection = new MILoopExpressionSection();
        subSections.add(miLoopExprSection);
        miComplexExprSection = new MIComplexExpressionSection();
        subSections.add(miComplexExprSection);
        miAdditionalSection = new MIAdditionalInstancesSection();
        subSections.add(miAdditionalSection);
        tabSelectionListener = new TabSelectionListener();
        activityListener = new IActivityManagerListener() {
            public void activityManagerChanged(
                    ActivityManagerEvent activityManagerEvent) {
                if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
                    doRefresh();
                }

            }
        };
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout());
        Label createLabel =
                toolkit
                        .createLabel(root,
                                Messages.MultiInstanceScriptSection_MultiInstancesScript);
        createLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // Create tabs
        tabFolder = toolkit.createTabFolder(root, SWT.FLAT | SWT.MULTI);
        tabFolder.setBorderVisible(true);
        GridData gData = new GridData(GridData.FILL_BOTH);
        gData.widthHint = 10;
        gData.heightHint = 10;
        tabFolder.setLayoutData(gData);
        // Init Script tab
        CTabItem tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.MultiInstanceScriptSection_LoopExpressionTab);
        miLoopExprSection.setPropertySheetPage(getPropertySheetPage());
        tabItem
                .setControl(miLoopExprSection
                        .createControls(tabFolder, toolkit));
        tabItem.setData(DATA_SECTION, miLoopExprSection);
        tabFolder.setSelection(tabItem);
        // Complex Exit Expression
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem.setText(Messages.MultiInstanceScriptSection_ComplexExitExprTab);
        miComplexExprSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(miComplexExprSection.createControls(tabFolder,
                toolkit));
        tabItem.setData(DATA_SECTION, miComplexExprSection);
        // Additional Instances Expression
        tabItem = toolkit.createTabItem(tabFolder, SWT.NONE);
        tabItem
                .setText(Messages.MultiInstanceScriptSection_AdditionalInstancesTab);
        miAdditionalSection.setPropertySheetPage(getPropertySheetPage());
        tabItem.setControl(miAdditionalSection.createControls(tabFolder,
                toolkit));
        tabItem.setData(DATA_SECTION, miAdditionalSection);
        tabFolder.addSelectionListener(tabSelectionListener);
        return root;
    }

    @Override
    public void setInput(Collection items) {
        super.setInput(items);
        // Update all subsections
        if (subSections != null) {
            for (BaseScriptSection section : subSections) {
                section.setInput(items);
            }
        }
    }

    @Override
    protected Command doGetCommand(Object obj) {
        return null;
    }

    @Override
    protected void doRefresh() {
        if (subSections != null) {
            CTabItem currSelection = tabFolder.getSelection();
            BaseScriptSection section =
                    (BaseScriptSection) currSelection.getData(DATA_SECTION);
            for (BaseScriptSection tempSection : subSections) {
                if (section.equals(tempSection))
                    tempSection.refresh();
            }
        }

        /*
         * XPD-1491: Noticed that we weren't showing script tab header icons for
         * "defined/undefined" script for task MI loop scripts.
         */
        if (!tabFolder.isDisposed()) {
            CTabItem[] tabItems = tabFolder.getItems();
            if (tabItems != null) {
                for (CTabItem tabItem : tabItems) {
                    BaseScriptSection section =
                            (BaseScriptSection) tabItem.getData(DATA_SECTION);

                    if (section instanceof BaseProcessScriptSection) {
                        Image reqdImg = null;

                        if (ProcessScriptUtil
                                .isScriptDefined((BaseProcessScriptSection) section)) {
                            reqdImg =
                                    Xpdl2ProcessEditorPlugin
                                            .getDefault()
                                            .getImageRegistry()
                                            .get(ProcessEditorConstants.ICON_TAB_HEADER_ON);

                        } else {
                            reqdImg =
                                    Xpdl2ProcessEditorPlugin
                                            .getDefault()
                                            .getImageRegistry()
                                            .get(ProcessEditorConstants.ICON_TAB_HEADER_OFF);
                        }

                        if (tabItem.getImage() != reqdImg) {
                            tabItem.setImage(reqdImg);
                        }
                    }
                }
            }
        }
        return;
    }

    private static final String DATA_SECTION = "section"; //$NON-NLS-1$

    public static class TabSelectionListener implements SelectionListener {

        public void widgetDefaultSelected(SelectionEvent e) {
            refreshSection(e);
        }

        public void widgetSelected(SelectionEvent e) {
            refreshSection(e);
        }

        private void refreshSection(SelectionEvent e) {
            Object source = e.getSource();
            if (source instanceof CTabFolder) {
                CTabFolder folder = (CTabFolder) source;
                CTabItem currSelection = folder.getSelection();
                BaseScriptSection section =
                        (BaseScriptSection) currSelection.getData(DATA_SECTION);
                section.refresh();
            }
        }
    }

    private final IActivityManagerListener activityListener;

    @Override
    public void aboutToBeHidden() {
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .removeActivityManagerListener(activityListener);
        super.aboutToBeHidden();
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processwidget.properties.general.SashDividedSection#
     * aboutToBeShown()
     */
    @Override
    public void aboutToBeShown() {
        super.aboutToBeShown();
        PlatformUI.getWorkbench().getActivitySupport().getActivityManager()
                .addActivityManagerListener(activityListener);
    }

}
