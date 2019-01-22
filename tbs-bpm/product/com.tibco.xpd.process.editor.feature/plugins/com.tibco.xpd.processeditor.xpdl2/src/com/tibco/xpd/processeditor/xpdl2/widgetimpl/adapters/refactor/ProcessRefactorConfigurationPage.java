package com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.refactor;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.ScrolledFormText;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;

import com.tibco.xpd.processeditor.xpdl2.dialogs.XPDZoomImageControl;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * ProcessRefactorConfigurationPage
 * <p>
 * Configuration / Problems page for the process refactor wizard. This takes the
 * configuration items passed on constructions add displays a checklist tree
 * control, description of currently selected item and (if the current
 * configuration item supports is) a preview image of the problem /
 * configuration.
 * </p>
 */
public class ProcessRefactorConfigurationPage extends AbstractXpdWizardPage implements
        ICheckStateListener {

    public static final String DETAIL_TEXT_ORANGE_KEY = "orange"; //$NON-NLS-1$

    public static final String DETAIL_TEXT_GREEN_KEY = "green"; //$NON-NLS-1$

    public static final String DETAIL_TEXT_BLUE_KEY = "blue"; //$NON-NLS-1$

    public static final String DETAIL_TEXT_RED_KEY = "red"; //$NON-NLS-1$

    public static final String DETAIL_TEXT_YELLOW_KEY = "yellow"; //$NON-NLS-1$

    public static final String DETAIL_TEXT_GREY_KEY = "grey"; //$NON-NLS-1$

    public static final String DETAIL_TEXT_BLACK_KEY = "black"; //$NON-NLS-1$

    public static final String DETAIL_TEXT_WHITE_KEY = "white"; //$NON-NLS-1$

    /**
     * Page in config page pagebook for displaying rich text detailed cause of
     * config problem/info.
     * 
     * @author aallway
     * 
     */
    private class ConfigTextDetailsPage extends Page {

        public FormText textDetails = null;

        ScrolledFormText scrollText;

        @Override
        public void createControl(Composite parent) {
            scrollText =
                    new ScrolledFormText(parent, SWT.BORDER | SWT.V_SCROLL,
                            true);
            scrollText.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_LIST_BACKGROUND));
            textDetails = scrollText.getFormText();
            textDetails.setBackground(Display.getCurrent()
                    .getSystemColor(SWT.COLOR_LIST_BACKGROUND));

            textDetails.setColor(DETAIL_TEXT_GREEN_KEY,
                    ColorConstants.darkGreen);
            textDetails.setColor(DETAIL_TEXT_ORANGE_KEY, new Color(null, 220,
                    150, 50));
            textDetails.setColor(DETAIL_TEXT_BLUE_KEY, ColorConstants.blue);
            textDetails.setColor(DETAIL_TEXT_RED_KEY, ColorConstants.red);
            textDetails.setColor(DETAIL_TEXT_YELLOW_KEY, ColorConstants.yellow);
            textDetails.setColor(DETAIL_TEXT_GREY_KEY, ColorConstants.gray);
            textDetails.setColor(DETAIL_TEXT_BLACK_KEY, ColorConstants.black);
            textDetails.setColor(DETAIL_TEXT_WHITE_KEY, ColorConstants.white);
        }

        @Override
        public Control getControl() {
            return scrollText;
        }

        @Override
        public void setFocus() {
        }

    }

    /**
     * Page in config page pagebook for displaying zoomable image displaying
     * cause of config problem/info.
     * 
     * @author aallway
     * 
     */
    private class ConfigImagePage extends Page {

        public XPDZoomImageControl imageControlCmp = null;

        @Override
        public void createControl(Composite parent) {
            //
            // Create the zoomable image control.
            imageControlCmp =
                    new XPDZoomImageControl(parent, SWT.NONE, true,
                            new Dimension(SWT.DEFAULT, 120)) {
                        @Override
                        protected Image createImage(Dimension sizeHint) {
                            return getCurrentConfigItemImage(sizeHint);
                        }
                    };
        }

        @Override
        public Control getControl() {
            return imageControlCmp;
        }

        @Override
        public void setFocus() {
        }

    }

    /**
     * 
     */
    private final ProcessRefactorWizard processRefactorWizard;

    private Composite rootControl = null;

    private CheckboxTreeViewer configTree = null;

    private PageBook imageOrTextDetailsBook = null;

    private ConfigImagePage imageDetailsPage = null;

    private ConfigTextDetailsPage textDetailsPage = null;

    private List<ProcessRefactorConfigurationItem> configItems;

    private Label configDescription = null;

    private boolean alwaysRefreshOnCheckStateChange = false;

    /**
     * Create a refactor configuration page that handles the given refactor
     * items.
     */
    public ProcessRefactorConfigurationPage(ProcessRefactorWizard wizard,
            List<ProcessRefactorConfigurationItem> configItems) {
        super(
                Messages.ProcessRefactorConfigurationPage_RefactorAsSubprocConfig_title);
        processRefactorWizard = wizard;

        this.configItems = configItems;

        setDescription(Messages.ProcessRefactorConfigurationPage_RefactorAsSubprocConfig_longdesc);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {

        rootControl = new Composite(parent, SWT.NONE);

        setControl(rootControl);

        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.verticalSpacing = 10;
        rootControl.setLayout(gridLayout);

        //
        // Create the configuration checkbox tree.
        configTree = new CheckboxTreeViewer(rootControl, SWT.BORDER);

        configTree
                .setLabelProvider(new ProcessRefactorConfigLabelProvider(this));
        configTree.setContentProvider(new ProcessRefactorConfigContentProvider(
                this));
        configTree.setInput(this);

        configTree.expandToLevel(2);

        GridData treeData = new GridData(SWT.FILL, SWT.FILL, true, true);
        treeData.heightHint = 150;
        treeData.minimumHeight = 70;

        configTree.getControl().setLayoutData(treeData);

        synchroniseCheckStates(configItems);

        configTree.addCheckStateListener(this);

        configTree.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                if (event.getSelection() instanceof TreeSelection) {
                    TreeSelection sel = (TreeSelection) event.getSelection();

                    Object item = sel.getFirstElement();
                    if (item instanceof ProcessRefactorConfigurationItem) {
                        updateControls((ProcessRefactorConfigurationItem) item);
                    }
                }
            }
        });

        //
        // Create the configuration description control.
        Composite descContainer = new Composite(rootControl, SWT.NONE);
        GridData descContData = new GridData(SWT.FILL, SWT.FILL, true, false);
        descContData.widthHint = 500;
        descContainer.setLayoutData(descContData);

        GridLayout descContLayout = new GridLayout(1, false);
        descContainer.setLayout(descContLayout);

        configDescription = new Label(descContainer, SWT.WRAP);
        GridData descData = new GridData(SWT.FILL, SWT.FILL, true, false);
        descData.widthHint = 500;
        configDescription.setLayoutData(descData);

        //
        // Each configuration item can choose whether to use an image to show
        // image about a given configuration items or a FormText textual
        // details.
        imageOrTextDetailsBook = new PageBook(rootControl, SWT.NONE);
        GridData bookLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
        bookLayoutData.heightHint = 200;
        bookLayoutData.minimumHeight = 100;
        imageOrTextDetailsBook.setLayoutData(bookLayoutData);

        imageDetailsPage = new ConfigImagePage();
        imageDetailsPage.createControl(imageOrTextDetailsBook);

        textDetailsPage = new ConfigTextDetailsPage();
        textDetailsPage.createControl(imageOrTextDetailsBook);

        imageOrTextDetailsBook.showPage(textDetailsPage.getControl());

        // 
        // Expand parts of the tree that have problem children.
        expandProblemChildren(configItems);

        // 
        // Set the selected item to the first unchecked problem if not
        // checked item.
        if (!setInitialSelectedItem(configItems)) {
            setSelectedItem(configItems.get(0));
        }

        //
        // Set page complete / not complete depending on whether sub-class
        // thinks config is complete.
        setPageComplete(processRefactorWizard
                .isConfigurationComplete(processRefactorWizard.inputObject,
                        configItems));
    }

    /**
     * Find and select the first problem if unchacked item.
     * 
     * @param items
     * @return Whether unchekced prblem child found and selected.
     */
    private boolean setInitialSelectedItem(
            List<ProcessRefactorConfigurationItem> items) {
        for (ProcessRefactorConfigurationItem ci : items) {
            if (ci.isProblemIfUnchecked() && !ci.isChecked()) {
                setSelectedItem(ci);
                return true;
            } else {
                if (ci.getChildItems() != null) {
                    if (setInitialSelectedItem(ci.getChildItems())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void expandProblemChildren(
            List<ProcessRefactorConfigurationItem> items) {
        for (ProcessRefactorConfigurationItem ci : items) {
            if (ci.isProblemIfUnchecked() && !ci.isChecked()) {

                configTree.expandToLevel(ci, CheckboxTreeViewer.ALL_LEVELS);

                ProcessRefactorConfigurationItem parent = ci.getParent();
                while (parent != null) {
                    configTree.expandToLevel(parent,
                            CheckboxTreeViewer.ALL_LEVELS);
                    parent = parent.getParent();
                }

            }

            if (ci.getChildItems() != null) {
                expandProblemChildren(ci.getChildItems());
            }
        }
        return;
    }

    /**
     * Get the image for the currently selected configuation item.
     * 
     * @param sizeHint
     * @return
     */
    protected Image getCurrentConfigItemImage(Dimension sizeHint) {
        if (configTree.getSelection() instanceof TreeSelection) {
            TreeSelection sel = (TreeSelection) configTree.getSelection();

            if (sel.getFirstElement() instanceof ProcessRefactorConfigurationItem) {
                ProcessRefactorConfigurationItem ci =
                        (ProcessRefactorConfigurationItem) sel
                                .getFirstElement();

                return ci.getConfigItemPreviewImage(sizeHint);
            }

        }
        return null;
    }

    /**
     * Get the text details description for the currently selected configuation
     * item.
     * 
     * @return
     */
    protected String getCurrentConfigTextDetails() {
        if (configTree.getSelection() instanceof TreeSelection) {
            TreeSelection sel = (TreeSelection) configTree.getSelection();

            if (sel.getFirstElement() instanceof ProcessRefactorConfigurationItem) {
                ProcessRefactorConfigurationItem ci =
                        (ProcessRefactorConfigurationItem) sel
                                .getFirstElement();

                return ci.getConfigItemTextDetails();
            }

        }
        return null;
    }

    /**
     * Make sure we dispose the zoomable image control so that images get
     * disposed.
     */
    @Override
    public void dispose() {
        super.dispose();

        if (imageDetailsPage.imageControlCmp != null) {
            if (!imageDetailsPage.imageControlCmp.isDisposed()) {
                imageDetailsPage.imageControlCmp.dispose();
            }
        }
    }

    /**
     * @param item
     */
    private void setSelectedItem(ProcessRefactorConfigurationItem item) {
        Object[] sel = new Object[1];
        sel[0] = item;
        TreePath tp = new TreePath(sel);
        configTree.setSelection(new TreeSelection(tp), true);
    }

    private void updateControls(ProcessRefactorConfigurationItem item) {
        configDescription.setText(item.getItemDesc());

        rootControl.layout(true);

        imageDetailsPage.imageControlCmp.refresh(true);

        if (imageDetailsPage.imageControlCmp.getImage() != null) {
            imageDetailsPage.imageControlCmp.setEnabled(true);

            imageOrTextDetailsBook.showPage(imageDetailsPage.getControl());

        } else {
            // If we don't have an image for config item then see it there is
            // detailed text.

            String formTextDetails = getCurrentConfigTextDetails();
            if (formTextDetails != null && formTextDetails.length() > 0) {
                String txt;

                if (!formTextDetails.startsWith("<form>") //$NON-NLS-1$
                        || !formTextDetails.endsWith("</form>")) { //$NON-NLS-1$
                    txt = "<form>" + formTextDetails + "</form>"; //$NON-NLS-1$ //$NON-NLS-2$
                } else {
                    txt = formTextDetails;
                }

                textDetailsPage.scrollText.setText(txt);

            } else {
                // We have neither image or detail text, leave current page
                // showing and empty text details content.
                textDetailsPage.scrollText.setText("<form></form>"); //$NON-NLS-1$

            }
            imageOrTextDetailsBook.showPage(textDetailsPage.getControl());
        }
    }

    /**
     * Synchronise the check state in tree control with those given by the
     * configuration items.
     * 
     * @param items
     * @return Bit flags 0x01 = At least one is unchecked, 0x02 = At least one
     *         is checked
     */
    private int synchroniseCheckStates(
            List<ProcessRefactorConfigurationItem> items) {
        int checkState = 0;

        for (ProcessRefactorConfigurationItem item : items) {
            // Recurs to set children states.
            List<ProcessRefactorConfigurationItem> children =
                    item.getChildItems();

            if (children != null && children.size() > 0) {
                // This is a parent item in tree.
                int childState = synchroniseCheckStates(children);

                // Synchronise parent config item with state of children.
                if ((childState & 0x01) != 0 && (childState & 0x02) != 0) {
                    // There is a mixture of check states in child. Set this
                    // parent config item to unchecked (so owner doesn't
                    // think that all underneath are checked)
                    item.setChecked(false);
                    configTree.setGrayChecked(item, true);

                } else if ((childState & 0x02) != 0) {
                    // Some were checked and none were unchecked (all were
                    // checked)
                    item.setChecked(true);
                    configTree.setGrayed(item, false);
                    configTree.setChecked(item, true);

                } else {
                    // All were unchecked
                    item.setChecked(false);
                    configTree.setGrayed(item, false);
                    configTree.setChecked(item, false);

                }

                // Add the results to our return state.
                checkState |= childState;

            } else {
                // Not a parent item, set state according to config item.
                boolean isChecked = item.isChecked();

                configTree.setChecked(item, isChecked);

                if (isChecked) {
                    checkState |= 0x02;
                } else {
                    checkState |= 0x01;
                }

            }

            // 
            // If config item checkbox is disabled then paint it gray.
            // (currently there is no way of ACTUALLY disabling individual tree
            // items in SWT/jface.
            TreeItem treeItem =
                    findConfigTreeItem(configTree.getTree().getItems(), item);
            if (treeItem != null) {
                if (!item.isCheckIsEnabled()) {
                    treeItem.setForeground(ColorConstants.gray);
                } else {
                    treeItem.setForeground(ColorConstants.listForeground);
                }
            }
        }

        return checkState;
    }

    private TreeItem findConfigTreeItem(TreeItem[] treeItems, Object item) {
        if (treeItems != null) {
            for (int i = 0; i < treeItems.length; i++) {
                TreeItem treeItem = treeItems[i];

                if (treeItem.getData() == item) {
                    return treeItem;
                } else {
                    TreeItem subItem =
                            findConfigTreeItem(treeItem.getItems(), item);
                    if (subItem != null) {
                        return subItem;
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return the configItems
     */
    List<ProcessRefactorConfigurationItem> getConfigItems() {
        return configItems;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse
     * .jface.viewers.CheckStateChangedEvent)
     */
    public void checkStateChanged(CheckStateChangedEvent event) {
        if (event.getElement() instanceof ProcessRefactorConfigurationItem) {
            ProcessRefactorConfigurationItem ci =
                    (ProcessRefactorConfigurationItem) event.getElement();

            // Set the config item check state and state it's children
            setConfigItemState(ci, event.getChecked(), false);

            // Synchronise the control with the config item check states.
            synchroniseCheckStates(configItems);

            // If this item has children then expand it.
            List<ProcessRefactorConfigurationItem> children =
                    ci.getChildItems();

            if (children != null && children.size() > 0) {
                configTree.setExpandedState(ci, true);
            }

            //
            // Have to refresh tree when check state changes on item that is...
            // - A problem if it is unchecked (so that state change causes reget
            // of label/icon.
            // - Caller has specified that we should always do so.
            // - This is a disabled checkbox (SWT does not really allow tree
            // item checkbox disabling, so we fake it by always setting it back
            // to original.
            if (ci.isProblemIfUnchecked()
                    || alwaysRefreshOnCheckStateChange
                    || !ci.isCheckIsEnabled()
                    || (ci.getParent() instanceof SingleChildSelectionRefactorConfigItem && ci
                            .getParent().isProblemIfUnchecked())) {
                if (event.getSource() instanceof CheckboxTreeViewer) {
                    CheckboxTreeViewer tree =
                            (CheckboxTreeViewer) event.getSource();

                    tree.refresh(true);
                }
            }

            //
            // Set page complete / not complete depending on whether
            // sub-class thinks config is complete.
            setPageComplete(processRefactorWizard
                    .isConfigurationComplete(processRefactorWizard.inputObject,
                            configItems));

            // Set the selection on the checked item.
            setSelectedItem(ci);

        }

    }

    /**
     * Set the configuration item check state.
     * 
     * @param ci
     * @param checked
     * @param recursing
     */
    private void setConfigItemState(ProcessRefactorConfigurationItem ci,
            boolean checked, boolean recursing) {
        // Only set the check state if it is enabled, unless doing children of
        // the clicked on item (in which case we always we want to set children
        // to parent's check state when parent is selected).
        if (ci.isCheckIsEnabled() || recursing) {
            // Set the check state of this item and all children to the given
            // state

            ci.setChecked(checked);

            // Recursively. (unless Single mutually exclusive chiold folder is
            // selected - these look after themselves BUT allow uncheck all.
            if (!(ci instanceof SingleChildSelectionRefactorConfigItem)
                    || !checked) {
                List<ProcessRefactorConfigurationItem> children =
                        ci.getChildItems();

                if (children != null && children.size() > 0) {
                    for (ProcessRefactorConfigurationItem child : children) {
                        setConfigItemState(child, checked, true);
                    }
                }

            }
        }
    }

    /**
     * @return the alwaysRefreshOnCheckStateChange
     */
    public boolean isAlwaysRefreshOnCheckStateChange() {
        return alwaysRefreshOnCheckStateChange;
    }

    /**
     * @param alwaysRefreshOnCheckStateChange
     *            the alwaysRefreshOnCheckStateChange to set
     */
    public void setAlwaysRefreshOnCheckStateChange(
            boolean alwaysRefreshOnCheckStateChange) {
        this.alwaysRefreshOnCheckStateChange = alwaysRefreshOnCheckStateChange;
    }

}