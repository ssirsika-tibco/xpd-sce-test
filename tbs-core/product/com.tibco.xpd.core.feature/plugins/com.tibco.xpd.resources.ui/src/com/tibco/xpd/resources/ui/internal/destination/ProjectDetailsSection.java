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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.osgi.framework.Version;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.components.FocusCellAndRowHighlighter;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.GovernanceStateService;
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

    private Version version;

    private CLabel statusTxt;

    private Text versionTxt;

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

    /* Sid ACE-441 Allow hide of just the version part of lifecycle. */
    private boolean showProjectVersion = true;

    /* Sid ACE-2980 Allow hide of project status. */
    private boolean showProjectStatus = true;

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
     * Set whether the destination environment selection should be displayed on
     * this page.
     * 
     * @param show
     */
    public void setShowProjectVersion(boolean show) {
        this.showProjectVersion = show;
    }

    /**
     * Set whether the project status selection should be displayed on this page.
     * 
     * @param show
     */
    public void setShowProjectStatus(boolean show) {
        this.showProjectStatus = show;
    }

    /**
     * Add a listener that will be notified of any modification to the input in this section.
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
        return version.toString();
    }

    /**
     * Set the version in this section.
     * 
     * @param aValue
     */
    public void setVersion(String aValue) {
        listenToChanges = false;
        try {
            version = new Version(aValue);
            if (versionTxt != null && !versionTxt.isDisposed()) {
                aValue = "" + version.getMajor() + "." + version.getMinor(); //$NON-NLS-1$ //$NON-NLS-2$
                versionTxt.setText(aValue);
            }
        } finally {
            listenToChanges = true;
        }
    }

    /**
     * Update the status in this section.
     * 
     * @param status
     */
    public void updateStatus(IProject project) {
        if (project != null && statusTxt != null && !statusTxt.isDisposed()) {
            try {
                if (new GovernanceStateService().isLockedForProduction(project)) {
                    statusTxt.setText(Messages.ProjectDetailsSection_LockedForProduction_Status);
                    statusTxt.setImage(XpdResourcesUIActivator.getDefault().getImageRegistry()
                            .get(XpdResourcesUIConstants.ICON_LOCKED));

                } else {
                    statusTxt.setText(Messages.ProjectDetailsSection_Draft_status);
                    statusTxt.setImage(XpdResourcesUIActivator.getDefault().getImageRegistry()
                            .get(XpdResourcesUIConstants.ICON_UNLOCKED));

                }
            } catch (CoreException e) {
                XpdResourcesUIActivator.getDefault().getLogger().error(e);
            }

        } else {
            statusTxt.setText("");
            statusTxt.setImage(null);
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
        root.setLayout(new GridLayout(2, false));

        Label idLbl =
                createLabel(root, Messages.ProjectDetailsSection_id_label);
        idTxt = createText(root, id, STANDARD_TEXT_WIDTH, (ModifyEvent e) -> {
            id = idTxt.getText();
            fireModifyEvent(idTxt);
        });

        // If not in developer capability then hide the id entry controls
        if (!showId) {
            GridData data = new GridData(0, 0);

            idLbl.setVisible(false);
            idLbl.setLayoutData(data);
            idTxt.setVisible(false);
            idTxt.setLayoutData(data);
        }

        /* Sid ACE-441 Only show project version if required. */
        if (this.showProjectVersion) {
            GridData layoutData = new GridData();
            layoutData.widthHint = STANDARD_LABEL_WIDTH;
            /* Slight change to align with vertical centre of edit box. */
            layoutData.verticalIndent = 4;
            layoutData.verticalAlignment = SWT.TOP;

            createLabel(root,
                    Messages.ProjectDetailsSection_version_label,
                    layoutData);

            createVersionControl(root);
        }

        /* Sid ACE-2980 Only show project status if necessary. */
        if (this.showProjectStatus) {
            createLabel(root, Messages.ProjectDetailsSection_status_label);

            /* Sid ACE-2980 Status is now just a Draft / Locked for production label. */
            GridData gd = new GridData(GridData.FILL_HORIZONTAL);
            statusTxt = createCLabel(root, "", gd); //$NON-NLS-1$

        }

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

    /**
     * Creates a collection of controls (adding them to the given composite)
     * that displays the current version and allows the user to increment the
     * major version. Incrementing the major version will automatically set the
     * minor and micro version to 0 whilst maintaining the qualifier.
     * 
     * @param aParent
     *            the container into which the components are to be added.
     */
    private void createVersionControl(Composite aParent) {
        Composite container = new Composite(aParent, SWT.NONE);

        /*
         * When creating a wrapping composite that you do not want to affect
         * positioning, then set the layout margins to 0.
         */
        GridLayout containerLayout = new GridLayout(2, false);
        containerLayout.marginHeight = containerLayout.marginWidth = 0;

        container.setLayout(containerLayout);

        container.setLayoutData(new GridData(SWT.TOP, SWT.LEFT, false, false));

        String value = ""; //$NON-NLS-1$
        if (version != null) {
            value += version.getMajor() + "." + version.getMinor(); //$NON-NLS-1$
        }
        versionTxt = createText(container, value, 30, null);
        versionTxt.setEditable(false);
        versionTxt.setCapture(false);

        SelectionListener buttonClick = new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                // increment the major version (reset minor and micro)
                version = new Version(version.getMajor() + 1, 0, 0,
                        version.getQualifier());

                // display the new value and notify listeners
                versionTxt.setText("" + version.getMajor() + ".0"); //$NON-NLS-1$//$NON-NLS-2$
                fireModifyEvent(versionTxt);

                // prevent double increments
                ((Button) e.getSource()).setEnabled(false);
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        };
        createButton(container,
                Messages.ProjectDetailsSection_increment_version_btn,
                SWT.PUSH,
                buttonClick).setToolTipText(
                        Messages.ProjectDetailsSection_increment_version_tooltip);

        GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        layoutData.widthHint = STANDARD_TEXT_WIDTH;
        new Composite(container, SWT.NONE);
        createLabel(container,
                Messages.ProjectDetailsSection_Incr_version_hint1,
                layoutData);

        /*
         * Should not re-use layout-data instances in multiple controls (there
         * can be private cached information that is set by the layout for each
         * individual control's layout data)
         */
        layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        layoutData.widthHint = STANDARD_TEXT_WIDTH;
        new Composite(container, SWT.NONE);

        createLabel(container,
                Messages.ProjectDetailsSection_Incr_version_hint2,
                layoutData);
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
     * @return the newly created label.
     */
    private Label createLabel(Composite parent, String label) {
        GridData layout = new GridData();
        layout.widthHint = STANDARD_LABEL_WIDTH;
        return createLabel(parent, label, layout);
    }

    /**
     * Creates a label and adds it to the given parent composite.
     * 
     * @param aParent
     *            the parent composite to which the label is added.
     * @param aText
     *            the text to be displayed within the label.
     * @param aLayout
     *            the layout data to be assigned to the label.
     * @return the newly created label.
     */
    private Label createLabel(Composite aParent, String aText, Object aLayout) {
        Label result = new Label(aParent, SWT.WRAP);
        result.setLayoutData(aLayout);

        if (aText != null) {
            result.setText(aText);
        }
        return result;
    }

    /**
     * Creates a label and adds it to the given parent composite.
     * 
     * @param aParent
     *            the parent composite to which the label is added.
     * @param aText
     *            the text to be displayed within the label.
     * @param aLayout
     *            the layout data to be assigned to the label.
     * @return the newly created label.
     */
    private CLabel createCLabel(Composite aParent, String aText, Object aLayout) {
        CLabel result = new CLabel(aParent, SWT.WRAP);
        result.setLayoutData(aLayout);

        if (aText != null) {
            result.setText(aText);
        }
        return result;
    }

    /**
     * Creates a button and adds it to the given parent composite.
     * 
     * @param aParent
     *            the parent composite to which the button is added.
     * @param aLabel
     *            the label to be displayed within the button.
     * @param aStyle
     *            the button style.
     * @param aListener
     *            an optional listener implementation.
     * @return the newly created button.
     */
    private Button createButton(Composite aParent, String aLabel, int aStyle,
            SelectionListener aListener) {
        Button result = new Button(aParent, aStyle);

        if (aLabel != null) {
            result.setText(aLabel);
        }

        if (aListener != null) {
            result.addSelectionListener(aListener);
        }

        return result;
    }

    /**
     * Create a {@link Text} control with a default width hint.
     * 
     * @param aParent
     *            parent container
     * @param aText
     *            text to set in the control, <code>null</code> if no text
     *            should be set
     * @return the newly created text field.
     */
    private Text createText(Composite aParent, String aText, int aWidth,
            ModifyListener aListener) {
        Text result = new Text(aParent, SWT.BORDER);

        GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
        data.widthHint = aWidth;
        result.setLayoutData(data);

        if (aText != null) {
            result.setText(aText);
        }

        if (aListener != null) {
            result.addModifyListener(aListener);
        }

        return result;
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
            return Boolean.valueOf(selectedDestinations.contains(element));
        }

        @Override
        protected void setValue(Object element, Object value) {
            if (value instanceof Boolean && element instanceof String) {
                if (((Boolean) value).booleanValue()) {
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
