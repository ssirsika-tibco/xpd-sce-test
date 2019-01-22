/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.ActivityManagerEvent;
import org.eclipse.ui.activities.IActivityManagerListener;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ProcessScriptUtil;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker;
import com.tibco.xpd.ui.properties.ExpandableSectionStacker.ISectionContentCreator;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author Miguel Torres
 */
public abstract class AbstractAuditScriptSection extends
        AbstractFilteredTransactionalSection implements ISectionContentCreator {

    private ExpandableSectionStacker expandableHeaderController;

    private Control expandablesContainer;

    private ScrolledComposite scrolledContainer;

    public final static String CLIENT_SCRIPT_SECTION = "clientScriptSection"; //$NON-NLS-1$

    public final static String ENGINE_SCRIPT_SECTION = "engineScriptSection"; //$NON-NLS-1$

    private CTabFolder engineTabFolder;

    private CTabFolder clientTabFolder;

    private Set<BaseScriptSection> subSections =
            new HashSet<BaseScriptSection>();

    private SelectionListener tabSelectionListener;

    public AbstractAuditScriptSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        setShowInWizard(false);
        initializeSections(subSections);
        tabSelectionListener = new TabSelectionListener();
        activityListener = new IActivityManagerListener() {
            @Override
            public void activityManagerChanged(
                    ActivityManagerEvent activityManagerEvent) {
                if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
                    doRefresh();
                }

            }
        };
    }

    protected abstract void initializeSections(
            Set<BaseScriptSection> subSections);

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        scrolledContainer =
                toolkit.createScrolledComposite(parent, SWT.V_SCROLL
                        | SWT.H_SCROLL);

        scrolledContainer.setExpandHorizontal(true);
        scrolledContainer.setExpandVertical(true);

        Composite root = toolkit.createComposite(scrolledContainer);
        GridLayout gl = new GridLayout(1, false);
        gl.marginLeft = 4;
        gl.marginWidth = 0;
        root.setLayout(gl);

        String sectPrefId = getSectionContainerType().toString();

        expandableHeaderController = new ExpandableSectionStacker(sectPrefId);

        if (isEligibleScriptSection(ENGINE_SCRIPT_SECTION)) {
            expandableHeaderController.addSection(ENGINE_SCRIPT_SECTION,
                    Messages.AbstractAuditScriptSection_EngineScriptsTitle,
                    SWT.DEFAULT,
                    true,
                    true);
        }

        if (isEligibleScriptSection(CLIENT_SCRIPT_SECTION)) {
            expandableHeaderController.addSection(CLIENT_SCRIPT_SECTION,
                    Messages.AbstractAuditScriptSection_ClientScriptsTitle,
                    SWT.DEFAULT,
                    true,
                    true);
        }

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

    protected abstract void doCreateMoreEngineControls(CTabFolder tabFolder,
            XpdFormToolkit toolkit, boolean setSelection);

    protected abstract void doCreateMoreClientControls(CTabFolder tabFolder,
            XpdFormToolkit toolkit, boolean setSelection);

    protected boolean isUserTask(Activity act) {
        if (act != null) {
            Implementation implementation = act.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskUser taskUser = task.getTaskUser();
                if (taskUser != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setInput(Collection<?> items) {
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
            if (engineTabFolder != null) {
                CTabItem currEngineSelection = engineTabFolder.getSelection();
                if (currEngineSelection != null) {
                    BaseScriptSection section =
                            (BaseScriptSection) currEngineSelection
                                    .getData(DATA_SECTION);
                    for (BaseScriptSection tempSection : subSections) {
                        if (section.equals(tempSection)) {
                            tempSection.refresh();
                        }
                    }
                }
            }
            if (clientTabFolder != null) {
                CTabItem currClientSelection = clientTabFolder.getSelection();
                if (currClientSelection != null) {
                    BaseScriptSection section =
                            (BaseScriptSection) currClientSelection
                                    .getData(DATA_SECTION);
                    for (BaseScriptSection tempSection : subSections) {
                        if (section.equals(tempSection)) {
                            tempSection.refresh();
                        }
                    }
                }
            }
            updateAllTabsText();
        }
    }

    private void updateAllTabsText() {
        if (engineTabFolder != null) {
            CTabItem[] engineTabItems = engineTabFolder.getItems();
            for (CTabItem engineTabItem : engineTabItems) {
                if (engineTabItem != null) {
                    BaseScriptSection section =
                            (BaseScriptSection) engineTabItem
                                    .getData(DATA_SECTION);
                    if (section instanceof BaseProcessScriptSection) {
                        updateTabText((BaseProcessScriptSection) section,
                                engineTabItem);
                    }
                }
            }
        }
        if (clientTabFolder != null) {
            CTabItem[] clientTabItems = clientTabFolder.getItems();
            for (CTabItem clientTabItem : clientTabItems) {
                if (clientTabItem != null) {
                    BaseScriptSection section =
                            (BaseScriptSection) clientTabItem
                                    .getData(DATA_SECTION);
                    if (section instanceof BaseProcessScriptSection) {
                        updateTabText((BaseProcessScriptSection) section,
                                clientTabItem);
                    }
                }
            }
        }
    }

    private void updateTabText(BaseProcessScriptSection section,
            CTabItem tabItem) {

        Image reqdImg = null;

        if (ProcessScriptUtil.isScriptDefined(section)) {
            reqdImg =
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.ICON_TAB_HEADER_ON);

        } else {
            reqdImg =
                    Xpdl2ProcessEditorPlugin.getDefault().getImageRegistry()
                            .get(ProcessEditorConstants.ICON_TAB_HEADER_OFF);
        }

        if (tabItem.getImage() != reqdImg) {
            tabItem.setImage(reqdImg);
        }
    }

    protected static final String DATA_SECTION = "section"; //$NON-NLS-1$

    public static class TabSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
            refreshSection(e);
        }

        @Override
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
                section.setScriptSelectionChanged(true);
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

    @Override
    public Control createExpandableSectionContent(String sectionId,
            Composite container, XpdFormToolkit toolkit) {
        Section expandableSection =
                expandableHeaderController.getExpandableSection(sectionId);
        if (ENGINE_SCRIPT_SECTION.equals(sectionId)) {
            // Create engine tabs
            engineTabFolder =
                    toolkit.createTabFolder(container, SWT.FLAT | SWT.MULTI);
            engineTabFolder.setBorderVisible(true);
            GridData gData =
                    new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                            | GridData.GRAB_HORIZONTAL);
            gData.heightHint = 150;
            engineTabFolder.setLayoutData(gData);

            doCreateMoreEngineControls(engineTabFolder, toolkit, true);
            engineTabFolder.addSelectionListener(tabSelectionListener);
            if (engineTabFolder != null
                    && (engineTabFolder.getItems() == null || engineTabFolder
                            .getItems().length == 0)) {
                expandableSection.setExpanded(false);
                expandableSection.setEnabled(false);
            }
        } else if (CLIENT_SCRIPT_SECTION.equals(sectionId)) {
            // Create client tabs
            clientTabFolder =
                    toolkit.createTabFolder(container, SWT.FLAT | SWT.MULTI);
            clientTabFolder.setBorderVisible(true);
            GridData gData =
                    new GridData(GridData.FILL_BOTH | GridData.GRAB_VERTICAL
                            | GridData.GRAB_HORIZONTAL);
            gData.heightHint = 150;
            clientTabFolder.setLayoutData(gData);

            doCreateMoreClientControls(clientTabFolder, toolkit, true);

            clientTabFolder.addSelectionListener(tabSelectionListener);
        }

        return container;
    }

    /**
     * @param id
     *            section id
     * @return boolean to indicate whether the specified script section should
     *         exist
     */
    protected boolean isEligibleScriptSection(String id) {
        return ENGINE_SCRIPT_SECTION.equals(id);
    }

    @Override
    public void expandableSectionStateChanged(String sectionId) {
        // TODO Auto-generated method stub
    }

}
