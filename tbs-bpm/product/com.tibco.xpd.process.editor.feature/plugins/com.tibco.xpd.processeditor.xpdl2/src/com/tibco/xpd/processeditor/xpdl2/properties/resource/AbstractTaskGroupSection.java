/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.osgi.framework.Bundle;

import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorConstants;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.validation.IValidationListener;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.ValidationEvent;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public abstract class AbstractTaskGroupSection extends
        AbstractFilteredTransactionalSection implements IValidationListener {

    public TreeViewer resourcePatternsTreeViewer;

    private Button remove;

    private Button addTaskToGroup;

    private Button newTaskGroup;

    private Hyperlink linkToProcessTaskGroups;

    private long lastChangeSelectionTime = 0;

    private Object lastSelection;

    private Composite composite;

    /**
     * this is the name of the tab as specified in the plugin.xml file
     */
    public static final String PROCESS_TASK_GROUP_NAME =
            "%property_tab_taskGroups_1"; //$NON-NLS-1$

    public AbstractTaskGroupSection(EClass activityOrProcessClass) {
        super(activityOrProcessClass);
        setShowInWizard(false);
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        lastSelection = null;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        composite = toolkit.createComposite(parent);

        /**
         * creating the description for the resource patterns group
         */
        final Composite descComposite = toolkit.createComposite(composite);
        descComposite.setLayout(new GridLayout());
        Label label =
                toolkit.createLabel(descComposite, getDescText(), SWT.WRAP);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        final Composite controls = toolkit.createComposite(descComposite);
        controls.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        controls.setLayout(new GridLayout(2, false));

        Tree tree =
                toolkit.createTree(controls, SWT.MULTI, "resourcePatternTree"); //$NON-NLS-1$
        resourcePatternsTreeViewer = new TreeViewer(tree);
        IContentProvider contentProvider = getContentProvider();
        resourcePatternsTreeViewer.setContentProvider(contentProvider);
        ILabelProvider labelProvider = getLabelProvider();
        if (labelProvider instanceof ILabelDecorator) {
            resourcePatternsTreeViewer
                    .setLabelProvider(new DecoratingLabelProvider(
                            labelProvider, (ILabelDecorator) labelProvider));

        } else {
            resourcePatternsTreeViewer.setLabelProvider(labelProvider);
        }

        ValidationActivator.getDefault().addValidationListener(this);

        GridData gd1 = new GridData(SWT.FILL, SWT.FILL, true, true);
        tree.setLayoutData(gd1);

        resourcePatternsTreeViewer
                .setColumnProperties(new String[] { "resourcePatternTreeColumn" }); //$NON-NLS-1$
        resourcePatternsTreeViewer
                .setCellEditors(new CellEditor[] { new TextCellEditor(tree,
                        SWT.BORDER) });
        resourcePatternsTreeViewer.setCellModifier(new ICellModifier() {

            @Override
            public boolean canModify(Object element, String property) {
                boolean canEdit = false;
                long sinceLast =
                        System.currentTimeMillis() - lastChangeSelectionTime;

                if (lastSelection == element && sinceLast > 500
                        && !(element instanceof ActivityRef)) {
                    canEdit = true;
                }
                return canEdit;
            }

            @Override
            public Object getValue(Object element, String property) {
                String value = ""; //$NON-NLS-1$
                if (element instanceof NamedElement) {
                    NamedElement namedElement = (NamedElement) element;
                    value = namedElement.getName();
                }
                return value;
            }

            @Override
            public void modify(Object element, String property, Object value) {
                if (element instanceof TreeItem) {
                    element = ((TreeItem) element).getData();
                }
                if (element instanceof NamedElement && value instanceof String) {
                    NamedElement namedElement = (NamedElement) element;
                    String newValue = (String) value;
                    if (!newValue.equals(namedElement.getName())) {
                        CompoundCommand cmd =
                                new CompoundCommand(
                                        Messages.SeparationOfDutiesSashSection_SetSeparationGroupNameCommand);
                        EditingDomain ed =
                                WorkingCopyUtil.getEditingDomain(namedElement);
                        /**
                         * restricting the length of the group name to 200
                         * characters
                         * */
                        if (newValue.length() > 200) {
                            newValue = newValue.substring(0, 200);
                        }
                        if (ed != null) {
                            cmd.append(SetCommand.create(ed,
                                    namedElement,
                                    Xpdl2Package.eINSTANCE
                                            .getNamedElement_Name(),
                                    newValue));
                            if (cmd.canExecute()) {
                                ed.getCommandStack().execute(cmd);
                            }
                        }
                    }
                }
            }

        });
        Composite buttonComposite = toolkit.createComposite(controls);
        buttonComposite.setLayoutData(new GridData(
                GridData.VERTICAL_ALIGN_BEGINNING));
        buttonComposite.setLayout(new GridLayout());

        /**
         * creating the buttons
         */
        newTaskGroup =
                toolkit.createButton(buttonComposite,
                        "", SWT.PUSH, "newTaskGroup"); //$NON-NLS-1$//$NON-NLS-2$
        newTaskGroup.setImage(Xpdl2ProcessEditorPlugin.getDefault()
                .getImageRegistry()
                .get(ProcessEditorConstants.IMG_NEW_TASK_TO_GROUP));
        newTaskGroup
                .setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        newTaskGroup
                .setToolTipText(Messages.ResourcePatternSection_CreateNewTaskGroup_TooltipText);

        addTaskToGroup =
                toolkit.createButton(buttonComposite,
                        "", SWT.PUSH, "addTaskToGroup"); //$NON-NLS-1$//$NON-NLS-2$
        addTaskToGroup.setImage(Xpdl2ProcessEditorPlugin.getDefault()
                .getImageRegistry()
                .get(ProcessEditorConstants.IMG_ADD_TASK_TO_GROUP));
        addTaskToGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false,
                false));
        addTaskToGroup
                .setToolTipText(Messages.ResourcePatternSection_SelectTaskGroupMembers_TooltipText);

        remove = toolkit.createButton(buttonComposite, "", SWT.PUSH, //$NON-NLS-1$
                "removeSeparation"); //$NON-NLS-1$
        remove.setImage(Xpdl2ProcessEditorPlugin.getDefault()
                .getImageRegistry().get(ProcessEditorConstants.IMG_DELETE));
        remove.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
        remove.setToolTipText(Messages.ResourcePatternSection_DeleteGroupOrMember_TooltipText);

        composite.setLayout(new Layout() {

            @Override
            protected Point computeSize(Composite composite, int wHint,
                    int hHint, boolean flushCache) {
                return new Point(wHint, 200);
            }

            @Override
            protected void layout(Composite composite, boolean flushCache) {
                descComposite.setBounds(composite.getClientArea());
            }

        });

        resourcePatternsTreeViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        TreeItem[] selection =
                                resourcePatternsTreeViewer.getTree()
                                        .getSelection();

                        if (selection != null && selection.length > 0
                                && !selection[0].equals(lastSelection)) {
                            lastChangeSelectionTime =
                                    System.currentTimeMillis();
                            lastSelection = selection[0].getData();
                        }
                        updateButtonState();
                    }

                });

        resourcePatternsTreeViewer.getControl()
                .addKeyListener(new KeyListener() {

                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (e.character == SWT.DEL) {
                            if (!resourcePatternsTreeViewer.getSelection()
                                    .isEmpty() && remove.isEnabled()) {
                                Command cmd =
                                        doDeleteTaskGroupOrTaskFromGroup(resourcePatternsTreeViewer.getSelection());
                                if (cmd.canExecute()) {
                                    getEditingDomain().getCommandStack()
                                            .execute(cmd);
                                }
                            }
                        } else if (e.character == SWT.CR) {
                            if (!resourcePatternsTreeViewer.getSelection()
                                    .isEmpty() && addTaskToGroup.isEnabled()) {
                                Command cmd =
                                        doEditGroup(resourcePatternsTreeViewer
                                                .getSelection());
                                if (cmd.canExecute()) {
                                    getEditingDomain().getCommandStack()
                                            .execute(cmd);
                                }
                            }

                        } else if (e.keyCode == SWT.INSERT) {
                            if (newTaskGroup.isEnabled()) {
                                Command cmd = doCreateNewTaskGroup();
                                if (cmd.canExecute()) {
                                    getEditingDomain().getCommandStack()
                                            .execute(cmd);
                                }
                            }

                        }
                        return;
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }
                });

        /**
         * creating the hyperlink to Process Task Groups
         */

        linkToProcessTaskGroups =
                toolkit.createHyperlink(descComposite,
                        Messages.ActivityTaskGroupSection_ProcessTaskGroupLink,
                        SWT.NONE,
                        null);

        linkToProcessTaskGroups.addHyperlinkListener(new IHyperlinkListener() {

            @Override
            public void linkActivated(HyperlinkEvent e) {
                Bundle bundle =
                        Xpdl2ProcessEditorPlugin.getDefault().getBundle();

                IEditorPart activeEditor =
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage().getActiveEditor();
                if (activeEditor instanceof AbstractProcessDiagramEditor) {
                    AbstractProcessDiagramEditor processDiagramEditor =
                            (AbstractProcessDiagramEditor) activeEditor;
                    IEditorInput editorInput =
                            processDiagramEditor.getEditorInput();
                    if (editorInput instanceof ProcessEditorInput) {
                        ProcessEditorInput processEditorInput =
                                (ProcessEditorInput) editorInput;
                        Process process = processEditorInput.getProcess();
                        processDiagramEditor.navigateToObjects(Collections
                                .singletonList((EObject) process),
                                true,
                                false,
                                false,
                                false);
                        getPropertySheetPage()
                                .selectionChanged(processDiagramEditor,
                                        new StructuredSelection(process));
                    }
                }
                String tabName =
                        Platform.getResourceString(bundle,
                                PROCESS_TASK_GROUP_NAME);
                showTab(tabName);
            }

            @Override
            public void linkEntered(HyperlinkEvent e) {
                // do nothing
            }

            @Override
            public void linkExited(HyperlinkEvent e) {
                // do nothing
            }

        });

        manageControl(remove);
        manageControl(newTaskGroup);
        manageControl(addTaskToGroup);
        updateButtonState();

        return composite;
    }

    /*
     * 
     * */
    public abstract IContentProvider getContentProvider();

    public abstract ILabelProvider getLabelProvider();

    public abstract String getDescText();

    public abstract String getRemoveMessage();

    public abstract String getCreateNewTaskGroupMessage();

    public abstract String getSelectTaskGroupMessage();

    public abstract String getStringFormat();

    /**
     * 
     */
    private void updateButtonState() {
        boolean enableRemove = true;
        boolean enableAddTaskGroup = false;

        ISelection selection = resourcePatternsTreeViewer.getSelection();
        if (selection instanceof StructuredSelection) {
            StructuredSelection structured = (StructuredSelection) selection;
            if (structured.size() > 0) {
                Object firstElement = structured.getFirstElement();
                if (firstElement instanceof ActivityRef) {
                    ActivityRef activityRef = (ActivityRef) firstElement;
                    EObject object = getInput();
                    if (object instanceof Activity) {
                        Activity activity = (Activity) object;
                        if (activity.getId().equals(activityRef.getIdRef())) {
                            enableRemove = false;
                        }
                    }
                }
                enableAddTaskGroup = true;
            } else {
                enableRemove = false;
            }
        }
        remove.setEnabled(enableRemove);
        addTaskToGroup.setEnabled(enableAddTaskGroup);
    }

    public boolean shouldSashSectionRefresh(List<Notification> notifications) {
        return shouldRefresh(notifications);
    }

    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean refresh = false;
        EObject input = getInput();
        Process process = null;

        if (input != null && notifications != null && !notifications.isEmpty()) {
            if (input instanceof Activity) {
                process = ((Activity) input).getProcess();
            } else if (input instanceof Process) {
                process = (Process) input;
            }
            for (Notification notification : notifications) {
                if (!notification.isTouch()) {
                    Object notifier = notification.getNotifier();

                    if (notifier instanceof EObject) {
                        if (notifier == input
                                || EcoreUtil.isAncestor(process,
                                        (EObject) notifier)) {
                            refresh = true;
                            break;
                        }
                    }
                }
            }
        }

        return refresh;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        /** The selection. */
        ISelection selection = resourcePatternsTreeViewer.getSelection();

        if (obj == newTaskGroup) {
            cmd = doCreateNewTaskGroup();

        } else if (obj == addTaskToGroup) {
            cmd = doEditGroup(selection);

        } else if (obj == remove) {
            cmd = doDeleteTaskGroupOrTaskFromGroup(selection);

        }

        return cmd;
    }

    /**
     * @return Command to add new task group.
     */
    private Command doCreateNewTaskGroup() {
        Command cmd;
        Object input = getInput();

        EditingDomain ed = null;
        ProcessResourcePatterns resourcePatterns = null;

        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            Process process = activity.getProcess();
            resourcePatterns =
                    (ProcessResourcePatterns) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessResourcePatterns());
            ed = WorkingCopyUtil.getEditingDomain(activity);
        } else if (input instanceof Process) {
            Process process = (Process) input;
            resourcePatterns =
                    (ProcessResourcePatterns) Xpdl2ModelUtil
                            .getOtherElement(process,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ProcessResourcePatterns());
            ed = WorkingCopyUtil.getEditingDomain(process);
        }

        cmd = createTaskGroup(input, resourcePatterns, ed);
        return cmd;
    }

    protected abstract Command doEditGroup(ISelection selection);

    protected abstract Command doDeleteTaskGroupOrTaskFromGroup(
            ISelection selection);

    public abstract Command createTaskGroup(Object input,
            ProcessResourcePatterns resourcePatterns, EditingDomain ed);

    /**
     * As the tasks are created to form a group, the group name is created
     * accordingly to show the task(s) name(s) in it. (The user can rename the
     * group using context menu)
     */

    protected String getGroupName(Collection<Activity> activities) {
        StringBuilder names = new StringBuilder();
        /**
         * this was required because we were modifying the name of the group as
         * per the tasks selected after the group was created. but it would
         * happen even if the group is renamed using rename option which is
         * undesirable. hence commented it out
         */
        if (null != activities) {
            for (Activity activity : activities) {
                if (names.length() != 0) {
                    names.append(" & "); //$NON-NLS-1$
                }
                String label = Xpdl2ModelUtil.getDisplayNameOrName(activity);
                names.append(label);
            }
        }
        return String.format(getStringFormat(), names.toString());
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        EObject input = getInput();

        if (input instanceof Activity) {
            Activity activity = (Activity) input;
            linkToProcessTaskGroups.setVisible(true);
            if (activity != resourcePatternsTreeViewer.getInput()) {
                resourcePatternsTreeViewer.setInput(activity);
            } else {
                resourcePatternsTreeViewer.refresh();
            }
        } else if (input instanceof Process) {
            Process process = (Process) input;
            linkToProcessTaskGroups.setVisible(false);
            /**
             * do refresh the tree viewer if the input is the same, else set
             * input
             */
            if (process != resourcePatternsTreeViewer.getInput()) {
                resourcePatternsTreeViewer.setInput(input);
            } else {
                resourcePatternsTreeViewer.refresh();
            }
        }
    }

    /**
     * @see com.tibco.xpd.validation.IValidationListener#validationEvent(com.tibco.xpd.validation.ValidationEvent)
     * 
     * @param event
     */
    @Override
    public void validationEvent(ValidationEvent event) {
        getSite().getShell().getDisplay().asyncExec(new Runnable() {

            @Override
            public void run() {
                if (getInput() != null && resourcePatternsTreeViewer != null
                        && resourcePatternsTreeViewer.getTree() != null
                        && !resourcePatternsTreeViewer.getTree().isDisposed()) {
                    resourcePatternsTreeViewer.refresh();
                }
            }
        });
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        ValidationActivator.getDefault().removeValidationListener(this);
        super.dispose();
    }
}
