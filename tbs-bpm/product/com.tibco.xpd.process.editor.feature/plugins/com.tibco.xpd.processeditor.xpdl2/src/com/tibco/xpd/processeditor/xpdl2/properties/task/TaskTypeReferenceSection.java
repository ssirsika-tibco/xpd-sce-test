/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.LocalTaskReferenceActivityPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.TaskLibraryActivityPicker;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ReferenceTaskUtil;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.ui.Xpdl2UiPlugin;
import com.tibco.xpd.xpdl2.edit.util.RevealProcessDiagramEObject;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Task type reference section
 * 
 * @author aallway
 */
public class TaskTypeReferenceSection extends
        AbstractFilteredTransactionalSection implements IPluginContribution {

    private String instrumentationPrefixName;

    //
    // Controls for local task selection.
    private Button localTaskRadio;

    private Composite localTaskControls;

    private CLabel localTaskLabel;

    private CLabel localTaskPath;

    private Button browseLocalTask;

    private Button gotoLocalTask;

    //
    // Controls for library task selection.
    private Button libraryTaskRadio;

    private Composite libraryTaskControls;

    private CLabel libraryTaskLabel;

    private CLabel libraryTaskPath;

    private Button browseLibraryTask;

    private Button gotoLibraryTask;

    private Color origLabelColour;

    private static final int SUBSECTION_INSET = 16;

    // Horrible kludge to get round a layout problem that seem sto happen when a
    // force layout is performed after first created.
    private boolean firstRefreshEver = true;

    public TaskTypeReferenceSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public TaskTypeReferenceSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite refTaskPage = toolkit.createComposite(parent);

        GridLayout pageLayout = new GridLayout(1, false);
        pageLayout.marginTop = 8;
        refTaskPage.setLayout(pageLayout);

        //
        // Library Task Reference Controls.
        if (Xpdl2ResourcesPlugin.isTaskLibraryPluginAvailable()) {
            libraryTaskRadio =
                    toolkit
                            .createButton(refTaskPage,
                                    Messages.TaskTypeReferenceSection_ReferenceLibraryTask_label,
                                    SWT.RADIO);
            libraryTaskRadio.setLayoutData(new GridData());
            manageControl(libraryTaskRadio);

            libraryTaskControls =
                    createLibraryTaskSelectionControls(refTaskPage, toolkit);
            libraryTaskControls.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));
        }

        //
        // Local task reference controls.
        localTaskRadio =
                toolkit
                        .createButton(refTaskPage,
                                Messages.TaskTypeReferenceSection_ReferenceLocalTask_label,
                                SWT.RADIO);
        localTaskRadio.setLayoutData(new GridData());
        manageControl(localTaskRadio);

        localTaskControls =
                createLocalTaskSelectionControls(refTaskPage, toolkit);
        localTaskControls.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        adjustLabelAlignment();

        //
        // Add a filler to bunch everything else above up.
        Composite filler = toolkit.createComposite(refTaskPage);
        filler.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_VERTICAL));

        return refTaskPage;
    }

    /**
     * Try and match up the largest of the 2 labels (cos there in separate
     * container's their grid layouts won't match them up.
     */
    private void adjustLabelAlignment() {
        // 
        // Try and match up the largest of the 2 labels (cos there in separate
        // container's their grid layouts won't match them up.
        if (Xpdl2ResourcesPlugin.isTaskLibraryPluginAvailable()) {
            List<Control> labels = new ArrayList<Control>();
            labels.add(localTaskLabel);
            labels.add(libraryTaskLabel);

            Map<Control, Integer> adjustments = new HashMap<Control, Integer>();

            setSameGridDataWidth(labels, adjustments);
        }
    }

    /**
     * Create the composite containing the controls for library task selection.
     * 
     * @param parent
     * @param toolkit
     * @return the composite containing the controls for library task selection.
     */
    private Composite createLibraryTaskSelectionControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(4, false);
        layout.marginLeft = SUBSECTION_INSET;
        layout.marginBottom = 15;
        layout.marginTop = 2;
        layout.marginHeight = 0;
        container.setLayout(layout);

        toolkit.paintBordersFor(container);

        libraryTaskLabel =
                toolkit.createCLabel(container,
                        Messages.TaskTypeReferenceSection_LibraryTask_label);
        libraryTaskLabel.setLayoutData(new GridData());

        origLabelColour = libraryTaskLabel.getForeground();

        libraryTaskPath = toolkit.createCLabel(container, ""); //$NON-NLS-1$
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.widthHint = 100;
        libraryTaskPath.setLayoutData(gd);
        libraryTaskPath.setData(FormToolkit.KEY_DRAW_BORDER,
                FormToolkit.TEXT_BORDER);

        browseLibraryTask = toolkit.createButton(container, "...", SWT.PUSH); //$NON-NLS-1$
        browseLibraryTask.setLayoutData(new GridData());
        manageControl(browseLibraryTask);

        gotoLibraryTask =
                toolkit.createButton(container,
                        Messages.GotoButton_Label,
                        SWT.PUSH);
        gotoLibraryTask.setLayoutData(new GridData());
        manageControl(gotoLibraryTask);

        return container;
    }

    /**
     * Create the composite containing the controls for local task selection.
     * 
     * @param parent
     * @param toolkit
     * @return the composite containing the controls for local task selection.
     */
    private Composite createLocalTaskSelectionControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite container = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(4, false);
        layout.marginLeft = SUBSECTION_INSET;
        layout.marginBottom = 15;
        layout.marginTop = 2;
        layout.marginHeight = 0;
        container.setLayout(layout);

        toolkit.paintBordersFor(container);

        localTaskLabel =
                toolkit.createCLabel(container,
                        Messages.TaskTypeReferenceSection_RefdTask_label);
        localTaskLabel.setLayoutData(new GridData());

        origLabelColour = localTaskLabel.getForeground();

        localTaskPath = toolkit.createCLabel(container, ""); //$NON-NLS-1$
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.widthHint = 100;
        localTaskPath.setLayoutData(gd);
        localTaskPath.setData(FormToolkit.KEY_DRAW_BORDER,
                FormToolkit.TEXT_BORDER);

        browseLocalTask = toolkit.createButton(container, "...", SWT.PUSH); //$NON-NLS-1$
        browseLocalTask.setLayoutData(new GridData());
        manageControl(browseLocalTask);

        gotoLocalTask =
                toolkit.createButton(container,
                        Messages.GotoButton_Label,
                        SWT.PUSH);
        gotoLocalTask.setLayoutData(new GridData());
        manageControl(gotoLocalTask);

        return container;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        Activity act = getActivity();

        if (act != null) {
            if (obj == localTaskRadio) {
                // Switch to local task reference (If it is currently set to
                // task library task - otherwise ignore.
                if (ReferenceTaskUtil.getTaskLibraryReference(act) != null) {
                    // Simply recreate the Reference element
                    cmd =
                            ReferenceTaskUtil
                                    .getSetReferencedLocalTaskCommand(getEditingDomain(),
                                            act,
                                            null);
                }

            } else if (obj == browseLocalTask) {
                cmd = selectTaskFromLocal();

            } else if (obj == libraryTaskRadio) {
                // Switch to library task reference (If it is currently set to
                // local task reference - otherwise ignore.
                if (ReferenceTaskUtil.getTaskLibraryReference(act) == null) {
                    // Simply recreate the Reference element
                    cmd =
                            ReferenceTaskUtil
                                    .getSetReferencedLibraryTaskCommand(getEditingDomain(),
                                            act,
                                            act.getProcess(),
                                            null,
                                            null);
                }

            } else if (obj == browseLibraryTask) {
                cmd = selectTaskFromLibrary();

            } else if (obj == gotoLibraryTask || obj == gotoLocalTask) {
                Activity refdLibraryAct =
                        ReferenceTaskUtil.getReferencedTask(act);
                if (refdLibraryAct != null) {
                    RevealProcessDiagramEObject.revealEObject(getSite(),
                            refdLibraryAct,
                            true);
                }
            }
        }

        return cmd;
    }

    private Command selectTaskFromLibrary() {

        TaskLibraryActivityPicker picker =
                new TaskLibraryActivityPicker(getSite().getShell(), false);
        if (picker.open() == picker.OK) {
            Object[] result = picker.getResult();

            if (result.length == 1 && result[0] instanceof Activity) {
                Activity libraryTask = (Activity) result[0];
                Process taskLibrary = libraryTask.getProcess();

                return ReferenceTaskUtil
                        .getSetReferencedLibraryTaskCommand(getEditingDomain(),
                                getActivity(),
                                getActivity().getProcess(),
                                libraryTask.getId(),
                                taskLibrary);
            }
        }

        return null;
    }

    private Command selectTaskFromLocal() {
        LocalTaskReferenceActivityPicker picker =
                new LocalTaskReferenceActivityPicker(getSite().getShell(),
                        getActivity().getProcess());
        if (picker.open() == picker.OK) {
            Object[] result = picker.getResult();

            if (result.length == 1 && result[0] instanceof Activity) {
                Activity referencedTask = (Activity) result[0];

                return ReferenceTaskUtil
                        .getSetReferencedLocalTaskCommand(getEditingDomain(),
                                getActivity(),
                                referencedTask.getId());
            }
        }

        return null;
    }

    @Override
    protected void doRefresh() {
        Activity activity = getActivity();

        if (activity != null) {
            boolean needsLayout = false;

            boolean hasLibraryTaskReference =
                    ReferenceTaskUtil.isTaskLibraryTaskReference(activity);

            if (doRefreshLibraryTaskControls(activity, hasLibraryTaskReference)) {
                needsLayout = true;
            }

            if (doRefreshLocalTaskControls(activity, hasLibraryTaskReference)) {
                needsLayout = true;
            }

            adjustLabelAlignment();

            if (needsLayout && !firstRefreshEver) {
                forceLayout();
            }
        }

        firstRefreshEver = false;
        return;
    }

    /**
     * Refresh the controls for specifying library task reference.
     * 
     * @param activity
     * @param hasLibraryTaskReference
     * @param extActRef
     * 
     * @return Whether a layout should be performed.
     */
    private boolean doRefreshLibraryTaskControls(Activity activity,
            boolean hasLibraryTaskReference) {
        boolean needsLayout = false;

        if (Xpdl2ResourcesPlugin.isTaskLibraryPluginAvailable()) {
            boolean isValid = false;
            boolean isDefined = false;

            String tooltip =
                    Messages.TaskTypeReferenceSection_TheCurrentLibraryTask_tooltip;

            String libraryTaskName = ""; //$NON-NLS-1$
            Image libraryTaskIcon = null;

            if (!hasLibraryTaskReference) {
                //
                // Is not a library task reference so disable all the controls.
                //
                setEnabled(libraryTaskControls, false);

                libraryTaskRadio.setSelection(false);

                isValid = true;

            } else {
                //
                // Is a library task reference.
                //
                setEnabled(libraryTaskControls, true);

                libraryTaskRadio.setSelection(true);

                String referencedTaskId =
                        ReferenceTaskUtil.getReferencedTaskId(activity);
                String taskLibraryId =
                        ReferenceTaskUtil.getTaskLibraryReference(activity)
                                .getTaskLibraryId();

                if (referencedTaskId == null || referencedTaskId.length() == 0
                        || taskLibraryId == null || taskLibraryId.length() == 0) {
                    // No task library reference set yet.
                    libraryTaskPath.setForeground(ColorConstants.lightGray);

                    tooltip =
                            Messages.TaskTypeReferenceSection_YouMustSelectLibraryTask_tooltip;

                    libraryTaskName = Messages.TaskGeneralSection_NOT_SET;

                } else {
                    //
                    // Look for index task library task.
                    IndexerItem idxTaskItem =
                            ReferenceTaskUtil
                                    .getReferencedLibraryTaskIndexerItem(activity);
                    if (idxTaskItem == null) {
                        libraryTaskPath.setForeground(ColorConstants.lightGray);

                        IndexerItem idxTaskLibraryItem =
                                ReferenceTaskUtil
                                        .getReferencedTaskLibraryIndexerItem(activity);
                        if (idxTaskLibraryItem == null) {
                            tooltip =
                                    Messages.TaskTypeReferenceSection_UnableToLocateTaskLibrary_tooltip;
                            libraryTaskName =
                                    "<" //$NON-NLS-1$
                                            + Messages.TaskTypeReferenceSection_UnableToLocateTaskLibrary_tooltip
                                            + ">"; //$NON-NLS-1$
                        } else {
                            tooltip =
                                    String
                                            .format(Messages.TaskTypeReferenceSection_UnableToLocateTaskInLibrary_tooltip,
                                                    idxTaskLibraryItem
                                                            .get(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME));
                            libraryTaskName = "<" //$NON-NLS-1$
                                    + tooltip + ">"; //$NON-NLS-1$
                        }
                    } else {
                        //
                        // Found library task
                        isValid = true;
                        isDefined = true;

                        libraryTaskName =
                                idxTaskItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME);
                        String imgUriStr =
                                idxTaskItem
                                        .get(Xpdl2ResourcesPlugin.ATTRIBUTE_IMAGE_URI);
                        if (imgUriStr != null) {
                            URI imageUri = URI.createURI(imgUriStr);

                            libraryTaskIcon =
                                    ExtendedImageRegistry.getInstance()
                                            .getImage(imageUri);
                        }

                        // prefix with library name.
                        IndexerItem idxLibraryItem =
                                ProcessUIUtil
                                        .getTaskLibraryIndexItem(taskLibraryId);
                        if (idxLibraryItem != null) {
                            String libraryName =
                                    idxLibraryItem
                                            .get(Xpdl2ResourcesPlugin.ATTRIBUTE_DISPLAY_NAME);
                            if (libraryName != null && libraryName.length() > 0) {
                                libraryTaskName =
                                        libraryName + "/" + libraryTaskName; //$NON-NLS-1$
                            }
                        }
                    }
                }
            }

            //
            // Update the controls.
            libraryTaskLabel.setToolTipText(tooltip);
            libraryTaskPath.setToolTipText(tooltip);
            libraryTaskPath.setText(libraryTaskName);

            if (isValid) {
                // Either it's a local reference OR it's a valid library
                // reference
                if (libraryTaskLabel.getImage() != null) {
                    libraryTaskLabel.setImage(null);
                    needsLayout = true;
                }

                if (libraryTaskPath.getImage() != libraryTaskIcon) {
                    libraryTaskPath.setImage(libraryTaskIcon);
                    needsLayout = true;
                }

            } else {
                // Invalid task library reference.
                if (libraryTaskLabel.getImage() == null) {
                    libraryTaskLabel.setImage(Xpdl2UiPlugin.getDefault()
                            .getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));
                    needsLayout = true;
                }

                if (libraryTaskPath.getImage() != null) {
                    libraryTaskPath.setImage(null);
                    needsLayout = true;
                }

            }

            gotoLibraryTask.setEnabled(isDefined);
        }
        return needsLayout;
    }

    /**
     * Refresh the controls for specifying local task reference.
     * 
     * @param activity
     * @param hasLibraryTaskReference
     * 
     * @return Whether a layout should be performed.
     */
    private boolean doRefreshLocalTaskControls(Activity activity,
            boolean hasLibraryTaskReference) {
        boolean isValid = false;

        String tooltip =
                Messages.TaskTypeReferenceSection_TheCurrentReferenceLocalTask_tooltip;

        Image localTaskIcon = null;

        if (hasLibraryTaskReference) {
            //
            // Is not a local task reference so disable all the controls.
            //
            setEnabled(localTaskControls, false);

            localTaskRadio.setSelection(false);

            localTaskPath.setText(""); //$NON-NLS-1$

            isValid = true;

        } else {
            //
            // Is a local task reference.
            //
            setEnabled(localTaskControls, true);

            localTaskRadio.setSelection(true);

            Activity referencedTask =
                    ReferenceTaskUtil.getReferencedLocalActivity(getActivity());
            if (referencedTask != null) {
                localTaskPath.setText(Xpdl2ModelUtil
                        .getDisplayNameOrName(referencedTask));
                isValid = true;
                localTaskIcon = WorkingCopyUtil.getImage(referencedTask);
            } else {
                localTaskPath.setForeground(ColorConstants.lightGray);

                String refTaskId =
                        ReferenceTaskUtil.getReferencedTaskId(getActivity());
                if (refTaskId == null || refTaskId.length() == 0
                        || "-unknown-".equals(refTaskId)) { //$NON-NLS-1$
                    tooltip =
                            Messages.TaskTypeReferenceSection_YouMustSelectLocalTask_tooltip;
                    localTaskPath.setText(Messages.TaskGeneralSection_NOT_SET);
                } else {
                    tooltip =
                            Messages.TaskTypeReferenceSection_UnableToLocateLocalTask_tooltip;
                    localTaskPath.setText("<" + tooltip + ">"); //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
        }

        boolean needsLayout = false;

        if (isValid) {
            if (localTaskLabel.getImage() != null) {
                localTaskLabel.setImage(null);
                needsLayout = true;
            }

        } else {
            if (localTaskLabel.getImage() == null) {
                localTaskLabel.setImage(Xpdl2UiPlugin.getDefault()
                        .getImageRegistry().get(Xpdl2UiPlugin.IMG_ERROR));
                needsLayout = true;
            }

        }

        if (localTaskPath.getImage() != localTaskIcon) {
            localTaskPath.setImage(localTaskIcon);
            needsLayout = true;
        }

        localTaskPath.setToolTipText(tooltip);
        localTaskLabel.setToolTipText(tooltip);

        gotoLocalTask.setEnabled(isValid && !hasLibraryTaskReference);

        return needsLayout;
    }

    /**
     * Get xpdl2 Activity from the input
     * 
     * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

    public String getLocalId() {
        return "analyst.referenceSection"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

    /**
     * Content assist list entry for local task selection.
     */
    private class ReferenceTaskListEntry {
        private Activity activity;

        public ReferenceTaskListEntry(Activity activity) {
            this.activity = activity;
        }

        public String toString() {
            return getName();
        }

        public String getName() {
            String name = Xpdl2ModelUtil.getDisplayNameOrName(activity);

            if (name == null || name.length() == 0) {
                name = Messages.TaskTypeReferenceSection_UnnamedTask_label;
            }
            return name;
        }

        public String getId() {
            return activity.getId();
        }

        public Activity getActivity() {
            return activity;
        }
    }

}
