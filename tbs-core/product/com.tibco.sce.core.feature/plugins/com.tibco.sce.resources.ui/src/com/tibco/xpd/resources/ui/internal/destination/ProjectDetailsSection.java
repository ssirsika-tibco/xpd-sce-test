/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.destination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.osgi.framework.Version;

import com.tibco.xpd.resources.projectconfig.ProjectStatus;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.components.FocusCellAndRowHighlighter;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * {@link DialogPage} section that provides the controls to set the project
 * version details.
 * 
 * @author njpatel
 * 
 */
public class ProjectDetailsSection extends DialogPage {
    public static final int STANDARD_TEXT_WIDTH = 250;

    public static final int STANDARD_LABEL_WIDTH = 90;

    private String id;

    private Text idTxt;

    private String version;

    private Text versionTxt;

    private ProjectStatus status;

    private ComboViewer statusViewer;

    private TableViewer destinationsList;

    private List<String> globalDestinations;

    private final List<String> selectedDestinations;

    private final Image tickedImg;

    private final Image untickedImg;

    private Composite root;

    private final Set<ModifyListener> listeners;

    private static final Pattern idPattern =
            Pattern.compile("(^[a-zA-Z])|(^[a-zA-Z][\\w\\._-]*[\\w&&[^._-]])"); //$NON-NLS-1$

    private boolean listenToChanges = true;

    private Label noDestinationMessage;

    private final boolean showId;

    /**
     * Show the destination environment selection list when set to
     * <code>true</code>.
     */
    private boolean showDestinationEnv = true;

    /**
     * Project version details section.
     */
    public ProjectDetailsSection() {
        selectedDestinations = new ArrayList<String>();
        listeners = new HashSet<ModifyListener>();
        tickedImg = XpdResourcesUIActivator
                .getImageDescriptor("/icons/ticked.png").createImage(); //$NON-NLS-1$
        untickedImg = XpdResourcesUIActivator
                .getImageDescriptor("/icons/unticked.png").createImage(); //$NON-NLS-1$

        // Show the id entry if the developer capability is enabled
        showId = CapabilityUtil.isDeveloperActivityEnabled();
    }

    /**
     * Set whether the destination environment selection should be displayed on
     * this page.
     * 
     * @param show
     */
    public void setShowDestinationEnvironment(boolean show) {
        this.showDestinationEnv = show;
    }

    /**
     * Add a listener that will be notified of any modification to the input in
     * this section.
     * 
     * @param listener
     */
    public void addModifyListener(ModifyListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * Remove modification listeners
     * 
     * @param listener
     */
    public void removeModifyListener(ModifyListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    @Override
    public void dispose() {
        tickedImg.dispose();
        untickedImg.dispose();
        super.dispose();
    }

    /**
     * Get the id set in this section.
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id in this section.
     * 
     * @param id
     */
    public void setId(String id) {
        listenToChanges = false;
        try {
            this.id = id;
            if (idTxt != null && !idTxt.isDisposed()) {
                idTxt.setText(id != null ? id : ""); //$NON-NLS-1$
            }
        } finally {
            listenToChanges = true;
        }
    }

    /**
     * Get the version set in this section.
     * 
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version in this section.
     * 
     * @param version
     */
    public void setVersion(String version) {
        listenToChanges = false;
        try {
            this.version = version;
            if (versionTxt != null && !versionTxt.isDisposed()) {
                versionTxt.setText(version != null ? version : ""); //$NON-NLS-1$
            }
        } finally {
            listenToChanges = true;
        }
    }

    /**
     * Get the status set in this section.
     * 
     * @return
     */
    public ProjectStatus getStatus() {
        return status;
    }

    /**
     * Set the status in this section.
     * 
     * @param status
     */
    public void setStatus(ProjectStatus status) {
        listenToChanges = false;
        try {
            this.status = status;
            if (statusViewer != null
                    && !statusViewer.getControl().isDisposed()) {
                statusViewer.setSelection(
                        status != null ? new StructuredSelection(status)
                                : StructuredSelection.EMPTY);
            }
        } finally {
            listenToChanges = true;
        }
    }

    /**
     * Validate the input of this section.
     * 
     * @return {@link IStatus}
     */
    public IStatus validate() {
        /*
         * Validate id, if not hidden
         */
        if (showId) {
            String id = getId();
            if (id != null && id.length() > 0) {
                /*
                 * SCF-137 - Kapil: The special character '-' has been added to
                 * Project-id as it is supported by runtime. So now the
                 * Project-id can be something like : com.example.project-myproj
                 */
                Matcher matcher = idPattern.matcher(id);
                if (!matcher.matches()) {
                    return new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                            Messages.ProjectDetailsSection_invalidId_error_shortdesc);
                }
            } else {
                return new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                        Messages.ProjectDetailsSection_EmptyId_error_shortdesc);
            }
        }
        /*
         * Validate version
         */
        String version = getVersion();
        if (version != null && version.length() > 0) {
            /*
             * XPD-8334: Discourage use of leading zeroes in project version
             * numbers.
             */
            String[] versionParts = version.split("\\.");
            for (int i = 0; i < versionParts.length; i++) {
                /*
                 * Need to validate only the first 3 parts here.
                 */
                if (i == 3) {
                    break;
                }
                /*
                 * Validate against use of leading zeroes in project version
                 * parts.
                 */
                if (versionParts[i].length() > 1
                        && versionParts[i].startsWith("0")) {
                    return new Status(IStatus.WARNING,
                            XpdResourcesUIActivator.ID,
                            Messages.ProjectDetailsSection_VersionPartLeadingZero_warning_shortdesc);
                }
            }

            try {
                Version parsedVersion = Version.parseVersion(version);

                /*
                 * Project version qualifier must not contain more than 36
                 * characters.
                 */
                if (parsedVersion.getQualifier().toString().length() > 36) {

                    return new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                            Messages.ProjectDetailsSection_VersionQualifierCantExceed36Chars_error_shortdesc);
                }

            } catch (IllegalArgumentException e) {
                return new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                        Messages.ProjectDetailsSection_InvalidVersionFormat_error_shortdesc);
            }
        } else {
            return new Status(IStatus.ERROR, XpdResourcesUIActivator.ID,
                    Messages.ProjectDetailsSection_emptyVersion_error_shortdesc);
        }

        return Status.OK_STATUS;
    }

    /**
     * Get the default id to use for the given project name.
     * 
     * @param projectName
     * @return id or <code>null</code> if the project name is <code>null</code>.
     */
    public static String getDefaultId(String projectName) {
        return ProjectUtil.getDefaultProjectLifecycleId(projectName);
    }

    /**
     * Get the destination ids selected in this section.
     * 
     * @return
     */
    public List<String> getDestinationIds() {
        return selectedDestinations;
    }

    /**
     * Set the selected destinations in this section.
     * 
     * @param destinationIds
     */
    public void setDestinationIds(List<String> destinationIds) {
        listenToChanges = false;
        try {
            selectedDestinations.clear();
            if (destinationIds != null) {
                selectedDestinations.addAll(destinationIds);
            }
            if (destinationsList != null
                    && !destinationsList.getControl().isDisposed()) {
                destinationsList.refresh();
            }
        } finally {
            listenToChanges = true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.
     * widgets .Composite)
     */
    @Override
    public void createControl(Composite parent) {
        root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        root.setLayout(layout);

        Label idLbl =
                createLabel(root, Messages.ProjectDetailsSection_id_label);
        idTxt = createText(root, id);
        idTxt.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                id = idTxt.getText();
                fireModifyEvent(idTxt);
            }
        });

        // If not in developer capability then hide the id entry controls
        if (!showId) {
            GridData data = new GridData();
            data.widthHint = 0;
            data.heightHint = 0;

            idLbl.setVisible(false);
            idLbl.setLayoutData(data);
            idTxt.setVisible(false);
            idTxt.setLayoutData(data);
        }

        createLabel(root, Messages.ProjectDetailsSection_version_label);
        versionTxt = createText(root, version);
        versionTxt.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                version = versionTxt.getText();
                fireModifyEvent(versionTxt);
            }
        });
        createLabel(root, Messages.ProjectDetailsSection_status_label);
        statusViewer = new ComboViewer(root);
        statusViewer.setContentProvider(new ArrayContentProvider());
        statusViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element instanceof ProjectStatus) {
                    return ((ProjectStatus) element).getLabel();
                }
                return super.getText(element);
            }
        });
        statusViewer.setInput(ProjectStatus.values());
        if (status != null) {
            statusViewer.setSelection(new StructuredSelection(status));
        }
        statusViewer.addPostSelectionChangedListener(
                new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        ISelection selection = event.getSelection();
                        if (selection instanceof IStructuredSelection) {
                            status = (ProjectStatus) ((IStructuredSelection) selection)
                                    .getFirstElement();
                            fireModifyEvent(statusViewer.getCombo());
                        }
                    }
                });
        if (showDestinationEnv) {
            createLabel(root,
                    Messages.ProjectDetailsSection_destinationEnvironment_label);
            destinationsList = createDestinationList(root);

            createLabel(root, ""); //$NON-NLS-1$
            noDestinationMessage = new Label(root, SWT.WRAP);
            noDestinationMessage.setForeground(ColorConstants.red);
            showNoDestinationWarning(false);
            noDestinationMessage.setText("* " //$NON-NLS-1$
                    + Messages.ProjectDetailsSection_DestinationEnvDoesNotExist_longdesc);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.DialogPage#getControl()
     */
    @Override
    public Control getControl() {
        return root;
    }

    /**
     * Notify all registered listeners of the modification in this section.
     * 
     * @param w
     *            the widget that was modified
     */
    private void fireModifyEvent(Widget w) {
        if (listenToChanges) {
            Event e = new Event();
            e.display = getShell().getDisplay();
            e.widget = w;
            ModifyEvent ev = new ModifyEvent(e);
            for (ModifyListener listener : listeners) {
                listener.modifyText(ev);
            }
        }
    }

    /**
     * Show/hide the no destination warning printed at the foot of the
     * destination selection list.
     * 
     * @param show
     */
    private void showNoDestinationWarning(boolean show) {
        if (root != null && !root.isDisposed() && noDestinationMessage != null
                && !noDestinationMessage.isDisposed()) {
            GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
            if (show) {
                data.widthHint = 50;
                data.heightHint = 50;
                noDestinationMessage.setVisible(true);
            } else {
                data.widthHint = 0;
                data.heightHint = 0;
                noDestinationMessage.setVisible(false);
            }
            noDestinationMessage.setLayoutData(data);
            root.layout();
        }
    }

    /**
     * Create a {@link Label} - this will set the default width hint.
     * 
     * @param parent
     *            parent container
     * @param label
     *            label text
     * @return
     */
    private Label createLabel(Composite parent, String label) {
        Label lbl = new Label(parent, SWT.WRAP);
        lbl.setText(label);
        GridData data = new GridData();
        data.widthHint = STANDARD_LABEL_WIDTH;
        lbl.setLayoutData(data);
        return lbl;
    }

    /**
     * Create a {@link Text} control with a default width hint.
     * 
     * @param parent
     *            parent container
     * @param text
     *            text to set in the control, <code>null</code> if no text
     *            should be set
     * @return
     */
    private Text createText(Composite parent, String text) {
        Text txt = new Text(parent, SWT.BORDER);
        GridData data = new GridData(SWT.FILL, SWT.LEFT, true, false);
        data.widthHint = STANDARD_TEXT_WIDTH;
        txt.setLayoutData(data);
        if (text != null) {
            txt.setText(text);
        }
        return txt;
    }

    /**
     * Create the table viewer to display the available global destinations.
     * 
     * @param parent
     *            parent container
     * @return
     */
    private TableViewer createDestinationList(Composite parent) {
        final TableViewer viewer = new TableViewer(parent);

        GridData data = new GridData(SWT.FILL, SWT.LEFT, true, false);
        data.widthHint = 200;
        data.heightHint = 100;
        viewer.getControl().setLayoutData(data);
        viewer.setContentProvider(new DestinationContentProvider());

        TableViewerColumn selectCol = new TableViewerColumn(viewer, SWT.LEFT);
        selectCol.getColumn().setWidth(20);
        selectCol.setEditingSupport(new SelectColumnEditingSupport(viewer));

        final TableViewerFocusCellManager mgr = new TableViewerFocusCellManager(
                viewer, new FocusCellAndRowHighlighter(viewer));

        viewer.getTable().addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                ViewerCell cell = mgr.getFocusCell();
                if (cell != null) {
                    viewer.getTable().setSelection((TableItem) cell.getItem());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }

        });

        selectCol.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public Image getImage(Object element) {
                if (selectedDestinations.contains(element)) {
                    return tickedImg;
                }
                return untickedImg;
            }

            @Override
            public String getText(Object element) {
                return null;
            }
        });

        TableViewerColumn nameCol = new TableViewerColumn(viewer, SWT.LEFT);
        nameCol.getColumn()
                .setText(Messages.ProjectDetailsSection_name_column_title);
        nameCol.getColumn().setWidth(200);
        nameCol.setLabelProvider(new DestinationLabelProvider());
        List<String> destinations = getGlobalDestinations();
        viewer.setInput(destinations);

        viewer.getTable().addKeyListener(new KeyListener() {

            final int ASCII_SPACE = 32;

            @Override
            public void keyPressed(KeyEvent e) {

                if (ASCII_SPACE == e.keyCode) {
                    // permit typical user experience of toggling on/off
                    // checkboxes
                    IStructuredSelection sel =
                            (IStructuredSelection) viewer.getSelection();

                    for (Object obj : sel.toList()) {
                        if (obj instanceof String) {
                            String itemID = (String) obj;
                            if (selectedDestinations.contains(itemID)) {
                                selectedDestinations.remove(itemID);
                            } else {
                                selectedDestinations.add(itemID);
                            }
                        }

                    }
                    viewer.refresh();
                }
            }

            @Override
            public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
            }

        });

        return viewer;
    }

    /**
     * Get all registered global destinations.
     * 
     * @return list of global destinations, empty list if none found.
     */
    private List<String> getGlobalDestinations() {
        if (globalDestinations == null) {
            globalDestinations = GlobalDestinationUtil.getGlobalDestinations();
        }

        return globalDestinations;
    }

    /**
     * Editing support for the selection (first) column of the global
     * destination selection table.
     * 
     * @author njpatel
     * 
     */
    private class SelectColumnEditingSupport extends EditingSupport {

        private final CheckboxCellEditor editor;

        public SelectColumnEditingSupport(ColumnViewer viewer) {
            super(viewer);
            editor = new CheckboxCellEditor((Composite) viewer.getControl());
        }

        @Override
        protected boolean canEdit(Object element) {
            return true;
        }

        @Override
        protected CellEditor getCellEditor(Object element) {
            return editor;
        }

        @Override
        protected Object getValue(Object element) {
            return selectedDestinations.contains(element);
        }

        @Override
        protected void setValue(Object element, Object value) {
            if (value instanceof Boolean && element instanceof String) {
                if ((Boolean) value) {
                    if (!selectedDestinations.contains(element)) {
                        selectedDestinations.add((String) element);
                    }
                } else {
                    selectedDestinations.remove(element);
                }
                destinationsList.refresh();
                fireModifyEvent(getViewer().getControl());
            }
        }
    }

    private class DestinationLabelProvider extends ColumnLabelProvider {
        private final Color RED = ColorConstants.red;

        @Override
        public Color getForeground(Object element) {
            if (!isDestinationAvailable(element)) {
                return RED;
            }
            return super.getForeground(element);
        }

        @Override
        public String getText(Object element) {
            String text = super.getText(element);

            if (!isDestinationAvailable(element)) {
                text += "*"; //$NON-NLS-1$
            }

            return text;
        }

        private boolean isDestinationAvailable(Object element) {
            List<String> destinations = getGlobalDestinations();
            return destinations.contains(element);
        }
    }

    private class DestinationContentProvider
            implements IStructuredContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            List<Object> elements = new ArrayList<Object>();
            if (inputElement instanceof Collection<?>) {
                for (Object obj : ((Collection<?>) inputElement)) {
                    elements.add(obj);
                }
            }

            boolean showNoDestinationMessage = false;
            if (!selectedDestinations.isEmpty()) {
                for (String dest : selectedDestinations) {
                    if (!elements.contains(dest)) {
                        elements.add(dest);
                        showNoDestinationMessage = true;
                    }
                }
            }
            // Update the visibility of the destination not available message
            showNoDestinationWarning(showNoDestinationMessage);
            return elements.toArray();
        }

        @Override
        public void dispose() {

        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput,
                Object newInput) {

        }

    }
}
