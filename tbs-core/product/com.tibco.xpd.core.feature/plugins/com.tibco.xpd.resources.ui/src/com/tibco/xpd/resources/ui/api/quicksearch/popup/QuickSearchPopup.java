/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch.popup;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.ui.internal.quickSearch.QuickSearchContributionHelper;
import com.tibco.xpd.resources.ui.internal.quickSearch.QuickSearchElementAndContributor;

/**
 * QuickSearchPopup
 * <p>
 * The quick search popup can be contributed to any workbench view part in the
 * normal way (to an accelerator or menu or toolbar button etc) (using
 * {@link QuickSearchPopupAction}.
 * <p>
 * It is intended to allow the user to perform a 'local' search of items in the
 * current active workbench view part.
 * <p>
 * The searchable elements are contributed for a workbench part via the
 * <code>com.tibco.xpd.resources.ui.quickSearchContribution</code> extension
 * point. This allows searchable content to be contributed to a given view part
 * id.
 * </p>
 * <p>
 * The QuickSearchPopup UI uses these contributions to gather together all
 * searchable content for a view and then allows the user to filter thru this
 * content in a standard way.
 * </p>
 * 
 * @author aallway
 * @since 3.1
 */
public final class QuickSearchPopup {

    /**
     * 
     */
    private static final int APPROX_MAX_WIDTH = 350;

    private int minPopupWidth = 120;

    private QuickSearchContributionHelper searchHelper;

    private Shell quickSearchShell;

    private Text searchTextCtrl;

    private ToolBarManager prevNextToolbar;

    private Action gotoPrevAction;

    private Action gotoNextAction;

    private ToolBarManager selectCategoriesToolbar;

    private Action selectCategoriesAction;

    private QuickSearchCategoriesPopup searchCategoriesPopup;

    private TableViewer searchTableViewer;

    private Table searchTable;

    private TableColumn searchTableColumn;

    private CLabel searchPathCtrl;

    private boolean ignoreSearchTextEvents = false;

    private Color origTableForegroundColor = null;

    private Composite rootContainer;

    private boolean selectionChangedSinceLastSetPattern = false;

    private KillQuickSearchOnDeactivateListener quickSearchPopupDeactivateListener;

    private KillCategoriesOnDeactivateListener categoriesPopupDeactivateListener;

    private String searchDescription = ""; //$NON-NLS-1$

    private CLabel windowIcon;

    private WindowIconDragMoveHandler windowIconDragMoveHandler;

    private Composite searchPathContainer;

    /**
     * @param partShell
     * @param searchHelper
     * @param cursorLocation
     * @param searchDescription
     */
    public QuickSearchPopup(Shell partShell,
            QuickSearchContributionHelper searchHelper, Point cursorLocation,
            String searchDescription) {
        this.searchHelper = searchHelper;
        this.searchDescription = searchDescription;

        quickSearchShell =
                new Shell((Display) null, SWT.NO_TRIM | SWT.ON_TOP | SWT.TOOL);
        quickSearchShell.setLayout(new FillLayout());

        quickSearchShell.setSize(10, 10);

        quickSearchShell.setLocation(cursorLocation);
        createContent(quickSearchShell);

        quickSearchPopupDeactivateListener =
                new KillQuickSearchOnDeactivateListener();
        categoriesPopupDeactivateListener =
                new KillCategoriesOnDeactivateListener();

        quickSearchShell.addShellListener(quickSearchPopupDeactivateListener);
    }

    /**
     * @return the shell
     */
    public Shell getQuickSearchShell() {
        return quickSearchShell;
    }

    /**
     * @param quickSearchPopup
     */
    private void createContent(Composite shell) {
        ImageRegistry imageRegistry =
                XpdResourcesUIActivator.getDefault().getImageRegistry();

        rootContainer = new Composite(shell, SWT.BORDER);
        GridLayout popupLayout = new GridLayout(1, false);
        popupLayout.verticalSpacing = 0;
        popupLayout.horizontalSpacing = 0;
        popupLayout.marginHeight = 0;
        popupLayout.marginWidth = 0;
        rootContainer.setLayout(popupLayout);

        rootContainer.setBackground(ColorConstants.tooltipBackground);
        rootContainer.setForeground(ColorConstants.tooltipForeground);

        //
        // Create the search pattern edit controls
        //
        Composite textAreaContainer = new Composite(rootContainer, SWT.NONE);
        textAreaContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        textAreaContainer.setBackground(rootContainer.getBackground());
        textAreaContainer.setForeground(rootContainer.getForeground());

        GridLayout textLayout = new GridLayout(3, false);
        textLayout.marginHeight = 0;
        textLayout.marginWidth = 0;
        textLayout.horizontalSpacing = 0;
        textAreaContainer.setLayout(textLayout);

        windowIcon = new CLabel(textAreaContainer, SWT.NONE);
        windowIcon.setImage(imageRegistry
                .get(XpdResourcesUIConstants.IMG_SEARCH_ICON));
        windowIcon.setLayoutData(new GridData());
        if (searchDescription != null && searchDescription.length() > 0) {
            windowIcon.setToolTipText(searchDescription);
        } else {
            windowIcon
                    .setToolTipText(Messages.QuickSearchPopup_QuickSearch_tooltip);
        }

        windowIcon.setBackground(rootContainer.getBackground());
        windowIcon.setForeground(rootContainer.getForeground());

        windowIconDragMoveHandler = new WindowIconDragMoveHandler();
        windowIcon.addMouseListener(windowIconDragMoveHandler);
        windowIcon.addMouseMoveListener(windowIconDragMoveHandler);
        windowIcon.setCursor(Cursors.SIZEALL);

        searchTextCtrl = new Text(textAreaContainer, SWT.SINGLE);
        GridData gridData =
                new GridData(GridData.VERTICAL_ALIGN_CENTER
                        | GridData.FILL_HORIZONTAL);
        searchTextCtrl.setLayoutData(gridData);
        searchTextCtrl
                .setToolTipText(Messages.QuickSearchPopup_QuickSearchPatternDesc_tooltip);

        searchTextCtrl.setBackground(rootContainer.getBackground());
        searchTextCtrl.setForeground(rootContainer.getForeground());

        searchTextCtrl.setText(searchDescription);
        searchTextCtrl.selectAll();

        searchTextCtrl.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                if (searchTextCtrl != null && !searchTableColumn.isDisposed()) {
                    setSearchPattern(searchTextCtrl.getText());
                }
            }
        });

        searchTextCtrl.addKeyListener(new SearchPatternKeyListener(this));

        //
        // And the goto next/previous buttons
        //
        prevNextToolbar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
        prevNextToolbar.createControl(textAreaContainer);

        ToolBar tbCtrl = prevNextToolbar.getControl();
        GridData tbGridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        tbCtrl.setLayoutData(tbGridData);
        tbCtrl.setBackground(rootContainer.getBackground());
        tbCtrl.setForeground(rootContainer.getForeground());

        gotoPrevAction = new GotoPrevAction();
        gotoPrevAction.setEnabled(false);

        gotoNextAction = new GotoNextAction();
        gotoNextAction.setEnabled(false);

        prevNextToolbar.add(gotoPrevAction);
        prevNextToolbar.add(gotoNextAction);

        prevNextToolbar.update(true);

        //
        // Create the matching items list
        //
        Label separator =
                new Label(rootContainer, SWT.SEPARATOR | SWT.HORIZONTAL);
        separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        separator.setBackground(rootContainer.getBackground());
        separator.setForeground(rootContainer.getForeground());

        searchTableViewer =
                new TableViewer(rootContainer, SWT.SINGLE | SWT.V_SCROLL);
        searchTableViewer
                .setContentProvider(new QuickSearchPopupListContentProvider());
        searchTableViewer
                .setLabelProvider(new QuickSearchPopupListLabelProvider());
        searchTableViewer.setInput(searchHelper);
        searchTableViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    // Selection changed listener to update element path text at
                    // bottom of popup.
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        if (event.getSelectionProvider() == getSearchTableViewer()) {
                            refreshSearchPath();
                        }
                    }
                });

        searchTable = searchTableViewer.getTable();
        searchTable.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL,
                GridData.VERTICAL_ALIGN_FILL, true, true));

        origTableForegroundColor = searchTable.getForeground();
        searchTable.setBackground(rootContainer.getBackground());
        // searchTable.setBackground(ColorConstants.listBackground);
        searchTable.setForeground(rootContainer.getForeground());

        searchTable.addKeyListener(new MatchingElementsListKeyListener(this));
        searchTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                gotoSelectedSearchElement(true);
            }
        });

        searchTableColumn = new TableColumn(searchTable, SWT.LEFT);
        searchTableColumn.setResizable(false);

        //
        // Create the selected matching item path ctrls.
        //
        separator = new Label(rootContainer, SWT.SEPARATOR | SWT.HORIZONTAL);
        separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        separator.setBackground(rootContainer.getBackground());
        separator.setForeground(rootContainer.getForeground());

        searchPathContainer = new Composite(rootContainer, SWT.NONE);
        searchPathContainer
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        searchPathContainer.setBackground(rootContainer.getBackground());
        searchPathContainer.setForeground(rootContainer.getForeground());

        GridLayout gl = new GridLayout(2, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        gl.horizontalSpacing = 0;
        searchPathContainer.setLayout(gl);

        searchPathCtrl = new CLabel(searchPathContainer, SWT.NONE);
        searchPathCtrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        searchPathCtrl.setBackground(rootContainer.getBackground());
        searchPathCtrl.setForeground(rootContainer.getForeground());
        searchPathCtrl.addMouseListener(windowIconDragMoveHandler);
        searchPathCtrl.addMouseMoveListener(windowIconDragMoveHandler);
        searchPathCtrl.setCursor(Cursors.SIZEALL);

        selectCategoriesToolbar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
        selectCategoriesToolbar.createControl(searchPathContainer);

        ToolBar scCtrl = selectCategoriesToolbar.getControl();
        GridData scGridData = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        scCtrl.setLayoutData(scGridData);
        scCtrl.setBackground(rootContainer.getBackground());
        scCtrl.setForeground(rootContainer.getForeground());

        selectCategoriesAction = new SelectCategoriesAction();
        selectCategoriesAction.setEnabled(true);
        selectCategoriesToolbar.add(selectCategoriesAction);
        selectCategoriesToolbar.update(true);

        Point sz = searchTextCtrl.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        if (sz.x > minPopupWidth) {
            minPopupWidth = sz.x;
        }

        minPopupWidth +=
                windowIcon.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x;
        minPopupWidth +=
                prevNextToolbar.getControl().computeSize(SWT.DEFAULT,
                        SWT.DEFAULT,
                        true).x;
        minPopupWidth += 20;

        setSearchPattern(""); //$NON-NLS-1$
        return;
    }

    /**
     * Reset the search categories control from the latest workbench part quick
     * search contributions.
     */
    void setSearchPattern(String searchString) {

        searchHelper.setSearchString(searchString);

        if (searchTextCtrl != null && !searchTextCtrl.isDisposed()
                && searchTableViewer != null && searchTable != null
                && !searchTable.isDisposed()) {

            searchTextCtrl.setRedraw(false);
            searchTable.setRedraw(false);

            searchTable.setForeground(ColorConstants.gray);

            boolean prevNextEnabled = false;

            ignoreSearchTextEvents = true;

            try {
                List<QuickSearchElementAndContributor> matchingElements =
                        searchHelper.getCurrentMatchingElements();

                QuickSearchElementAndContributor currSel =
                        getSelectedMatchingItem();

                searchTableViewer.refresh();

                if (!matchingElements.isEmpty()) {
                    QuickSearchElementAndContributor element =
                            matchingElements.get(0);
                    searchTableViewer.setSelection(new StructuredSelection(
                            element));
                    searchTable.setTopIndex(0);

                    searchTableViewer.reveal(element);

                    prevNextEnabled = true;

                } else {
                    searchTableViewer.setSelection(StructuredSelection.EMPTY);
                    searchTable.setTopIndex(0);
                }

                selectionChangedSinceLastSetPattern = false;

                if (!matchingElements.isEmpty()) {
                    searchTable.setForeground(origTableForegroundColor);
                }

            } finally {

                if (prevNextEnabled != gotoNextAction.isEnabled()) {
                    gotoNextAction.setEnabled(prevNextEnabled);
                    gotoPrevAction.setEnabled(prevNextEnabled);
                }

                if (!searchTextCtrl.isDisposed()) {
                    searchTextCtrl.setRedraw(true);
                }

                if (searchTable != null && !searchTable.isDisposed()) {
                    searchTable.setRedraw(true);
                }
                ignoreSearchTextEvents = false;
            }

            layoutPopupControl();

        }

        return;
    }

    /**
     * Get the currently selected item from list of search matching items.
     * 
     * @return Selected item or null if none selected.
     */
    QuickSearchElementAndContributor getSelectedMatchingItem() {
        if (searchTableViewer != null && searchTable != null
                && !searchTable.isDisposed()) {
            ISelection sel = searchTableViewer.getSelection();
            if (sel instanceof IStructuredSelection) {
                Object element = ((IStructuredSelection) sel).getFirstElement();

                if (element instanceof QuickSearchElementAndContributor) {
                    return (QuickSearchElementAndContributor) element;
                }
            }
        }

        return null;
    }

    /**
     * Layout the popup search control.
     * 
     */
    void layoutPopupControl() {

        if (searchTable != null && !searchTable.isDisposed()) {

            List<QuickSearchElementAndContributor> matchingItems =
                    searchHelper.getCurrentMatchingElements();

            Rectangle textCtrlBnds = searchTextCtrl.getBounds();

            // Find the widest list item
            TableItem[] items = searchTable.getItems();

            // Set the column width to something silly, so we get the actual
            // text length rather than column width for things that are wider
            // than column.
            // Effectively this is a width limit on the table.
            searchTableColumn.setWidth(APPROX_MAX_WIDTH);

            int widest = 0;

            for (int itemIdx = 0; itemIdx < items.length; itemIdx++) {
                TableItem tableItem = items[itemIdx];

                Rectangle b = tableItem.getBounds();

                if (b.width > widest) {
                    widest = b.width;
                }
            }

            // add a bit for good luck!
            widest += 40;

            // If search path label control wants to be bigger than tanb;e items
            // then let it.
            Point spPrefSize =
                    searchPathContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT);
            spPrefSize.x = Math.min(APPROX_MAX_WIDTH, spPrefSize.x);

            if (widest < spPrefSize.x) {
                widest = spPrefSize.x;
            }

            searchTableColumn.setWidth(widest);

            // TODO - this is to ensure that parent of table is wide enough to
            // contain vertical scroll - should be changed to do it properly and
            // get scroll bar width etc.
            widest += 130;

            if (widest < (textCtrlBnds.width + 20)) {
                widest = textCtrlBnds.width + 20;
            }

            GridData tableContainerGridData =
                    (GridData) searchTable.getLayoutData();

            tableContainerGridData.widthHint = widest;

            // Setting min width causes vertical scroll bar not to appear!
            // tableContainerGridData.minimumWidth = widest;

            //
            // Reset the height hint according to num items.
            // (Max 5).
            int itemHeight = searchTable.getItemHeight();

            int origHeightHint = tableContainerGridData.heightHint;
            int newHeightHint = origHeightHint;

            if (matchingItems != null && !matchingItems.isEmpty()) {
                newHeightHint =
                        itemHeight * (Math.min(matchingItems.size(), 10));
            } else {
                newHeightHint = 0;
            }

            if (newHeightHint > tableContainerGridData.heightHint) {
                tableContainerGridData.heightHint = newHeightHint;
            }

            // Position and size according to preferred size.
            Point curSize = quickSearchShell.getSize();

            // This may look a bit wierd but it works!
            Point newSize = new Point(0, 0);
            newSize.x =
                    searchTable.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x;
            newSize.y =
                    searchTable.getParent().computeSize(SWT.DEFAULT,
                            SWT.DEFAULT,
                            true).y;

            if (newSize.x < minPopupWidth) {
                newSize.x = minPopupWidth;
            }

            boolean changed = false;

            // Only allow GROW not shrink...
            if (newSize.x <= curSize.x) {
                newSize.x = curSize.x;
            }

            if (newSize.x != curSize.x || newSize.y != curSize.y) {
                quickSearchShell.setSize(newSize.x, newSize.y);
                changed = true;
            }

            Rectangle screenBnds = Display.getDefault().getClientArea();
            Point loc = quickSearchShell.getLocation();
            Point newLoc = new Point(loc.x, loc.y);

            if ((loc.x + newSize.x) > screenBnds.width) {
                newLoc.x = screenBnds.width - newSize.x;
                changed = true;
            }

            if ((loc.y + newSize.y) > screenBnds.height) {
                newLoc.y = screenBnds.height - newSize.y;
                changed = true;
            }

            if (newLoc.x != loc.x || newLoc.y != loc.y) {
                quickSearchShell.setLocation(newLoc);
                changed = true;
            }

            if (changed) {
                quickSearchShell.layout(true, true);
            }

            if (searchCategoriesPopup != null
                    && searchCategoriesPopup.getShell() != null
                    && !searchCategoriesPopup.getShell().isDisposed()) {
                Rectangle b = selectCategoriesToolbar.getControl().getBounds();

                Point catPopLoc =
                        selectCategoriesToolbar.getControl().getParent()
                                .toDisplay(b.x, b.y);
                Point catPopSize = searchCategoriesPopup.getShell().getSize();
                if ((catPopLoc.x + catPopSize.x) > screenBnds.width) {
                    catPopLoc.x = screenBnds.width - catPopSize.x;
                }
                if ((catPopLoc.y + catPopSize.y) > screenBnds.height) {
                    catPopLoc.y = screenBnds.height - catPopSize.y;
                }

                b = searchCategoriesPopup.getShell().getBounds();
                if (b.x != catPopLoc.x || b.y != catPopLoc.y) {

                    searchCategoriesPopup.getShell().setLocation(catPopLoc);
                }
            }

        }

        return;
    }

    /**
     * @return the ignoreSearchTextEvents
     */
    public boolean isIgnoreSearchTextEvents() {
        return ignoreSearchTextEvents;
    }

    /**
     * @return the searchTableViewer
     */
    public TableViewer getSearchTableViewer() {
        return searchTableViewer;
    }

    /**
     * @return the searchHelper
     */
    public QuickSearchContributionHelper getSearchHelper() {
        return searchHelper;
    }

    /**
     * Ask associated view to select element currently selected in matching
     * items list and close the popup.
     * 
     * @param closePopup
     *            Whether to close the popup before selecting current element in
     *            associated view.
     */
    public void gotoSelectedSearchElement(boolean closePopup) {
        if (quickSearchShell != null && !quickSearchShell.isDisposed()
                && searchTable != null && !searchTable.isDisposed()) {
            QuickSearchElementAndContributor searchElement = null;

            ISelection sel = searchTableViewer.getSelection();
            if (sel instanceof StructuredSelection) {
                Object o = ((StructuredSelection) sel).getFirstElement();

                if (o instanceof QuickSearchElementAndContributor) {
                    searchElement = (QuickSearchElementAndContributor) o;
                }
            }

            if (closePopup) {
                closePopup();
            }

            if (searchElement != null) {
                Control ctrl = Display.getCurrent().getCursorControl();
                Cursor currCursor = null;
                if (ctrl != null && !ctrl.isDisposed()) {
                    ctrl.setCapture(true);
                    currCursor = ctrl.getCursor();
                    ctrl.setCursor(Display.getDefault()
                            .getSystemCursor(SWT.CURSOR_WAIT));
                }

                try {
                    Rectangle revealRect =
                            searchHelper.selectSearchElement(searchElement,
                                    closePopup);
                    if (revealRect != null && !closePopup) {
                        // Make sure we're not overlapping the selection.
                        // If we are then move the popup.
                        Rectangle popupRect = quickSearchShell.getBounds();

                        if (popupRect.intersects(revealRect)) {
                            Point origLoc = new Point(popupRect.x, popupRect.y);
                            Point newLoc = new Point(popupRect.x, popupRect.y);

                            Rectangle screenBnds =
                                    Display.getDefault().getClientArea();

                            //
                            // If the reveal object is larger than the popup
                            // then just make sure we're not covering the
                            // centre.
                            if (revealRect.height > popupRect.height
                                    || revealRect.width > popupRect.width) {
                                // Just reset the reveal rectabgle to a small
                                // area around the centre.
                                revealRect.x =
                                        (revealRect.x + (revealRect.width / 2)) - 32;
                                revealRect.width = 64;
                                revealRect.y =
                                        (revealRect.y + (revealRect.height / 2)) - 32;
                                revealRect.height = 64;
                            }

                            // Make sure it still intersects after possible
                            // adjustment.
                            if (popupRect.intersects(revealRect)) {
                                int revealRight =
                                        revealRect.x + revealRect.width;
                                int revealBottom =
                                        revealRect.y + revealRect.height;
                                int screenRight =
                                        screenBnds.x + screenBnds.width;
                                int screenBottom =
                                        screenBnds.y + screenBnds.height;

                                if ((revealRight + popupRect.width) < screenRight) {
                                    // nuff room to right of reveal - move
                                    // right.
                                    newLoc.x = revealRight;
                                } else if ((revealRect.x - popupRect.width) >= 0) {
                                    // nuff room to left of reveal - move right.
                                    newLoc.x = revealRect.x - popupRect.width;
                                } else {
                                    // If could not move out of the way
                                    // horizontally try vertically.

                                    if ((revealBottom + popupRect.height) < screenBottom) {
                                        // nuff room under reveal - move down.
                                        newLoc.y = revealBottom;
                                    } else if ((revealRect.y - popupRect.height) >= 0) {
                                        // nuff room above reveal - move up.
                                        newLoc.y =
                                                revealRect.y - popupRect.height;
                                    }
                                }

                                // If we needed to move then do it.
                                if (!newLoc.equals(origLoc)) {
                                    quickSearchShell.setLocation(newLoc);

                                    Point cl =
                                            quickSearchShell.getDisplay()
                                                    .getCursorLocation();
                                    quickSearchShell
                                            .getDisplay()
                                            .setCursorLocation(cl.x
                                                    + (newLoc.x - origLoc.x),
                                                    cl.y
                                                            + (newLoc.y - origLoc.y));
                                }
                            }
                        }

                    }

                } finally {
                    if (ctrl != null && !ctrl.isDisposed()) {
                        ctrl.setCursor(currCursor);
                        ctrl.setCapture(false);
                    }
                }
            }
        }

        return;
    }

    /**
     * Close the quick search popup.
     */
    public void closePopup() {
        if (windowIconDragMoveHandler.dragStarted) {
            if (windowIcon != null && !windowIcon.isDisposed()) {
                windowIcon.setCapture(true);
            }
        }

        if (quickSearchShell != null && !quickSearchShell.isDisposed()) {
            quickSearchShell
                    .removeShellListener(quickSearchPopupDeactivateListener);
            quickSearchShell.close();
        }

        // Just in case, kill the categories popup too.
        closeCategoriesPopup();

        return;
    }

    /**
     * Close the search categories popup if it is open.
     */
    public void closeCategoriesPopup() {
        if (searchCategoriesPopup != null
                && searchCategoriesPopup.getShell() != null
                && !searchCategoriesPopup.getShell().isDisposed()) {

            searchCategoriesPopup.getShell()
                    .removeShellListener(categoriesPopupDeactivateListener);
            searchCategoriesPopup.getShell().close();
            searchCategoriesPopup = null;
        }

    }

    /**
     * Select next matching item in list (and wrap at end of list)
     * 
     */
    public void selectNextMatchingElement() {
        if (searchTable != null && !searchTable.isDisposed()) {
            List<QuickSearchElementAndContributor> matchingElements =
                    searchHelper.getCurrentMatchingElements();

            if (!matchingElements.isEmpty()) {
                int idx;

                idx = searchTable.getSelectionIndex();

                if (idx >= (searchTable.getItemCount() - 1)) {
                    idx = 0;
                } else {
                    idx++;
                }

                QuickSearchElementAndContributor element =
                        matchingElements.get(idx);
                searchTableViewer
                        .setSelection(new StructuredSelection(element));
                searchTableViewer.reveal(element);

                selectionChangedSinceLastSetPattern = true;

            } else {
                searchTableViewer.setSelection(StructuredSelection.EMPTY);
            }
        }

        return;
    }

    /**
     * Select previous matching item in list (and wrap at start of list)
     * 
     */
    public void selectPreviousMatchingElement() {
        if (searchTable != null && !searchTable.isDisposed()) {
            List<QuickSearchElementAndContributor> matchingElements =
                    searchHelper.getCurrentMatchingElements();

            if (!matchingElements.isEmpty()) {
                int idx = searchTable.getSelectionIndex();

                if (idx <= 0) {
                    idx = searchTable.getItemCount() - 1;
                } else {
                    idx--;
                }

                QuickSearchElementAndContributor element =
                        matchingElements.get(idx);
                searchTableViewer
                        .setSelection(new StructuredSelection(element));
                searchTableViewer.reveal(element);

                selectionChangedSinceLastSetPattern = true;

            } else {
                searchTableViewer.setSelection(StructuredSelection.EMPTY);
            }
        }
        return;
    }

    /**
     * Page down the matching elements list.
     */
    public void pageDownMatchingElements() {
        if (searchTable != null && !searchTable.isDisposed()) {
            List<QuickSearchElementAndContributor> matchingElements =
                    searchHelper.getCurrentMatchingElements();

            if (!matchingElements.isEmpty()) {
                int idx = searchTable.getSelectionIndex();

                Rectangle bnds = searchTable.getClientArea();

                int itemHeight = searchTable.getItemHeight();
                if (itemHeight > 0) {
                    int itemsPerPage = bnds.height / itemHeight;

                    if (itemsPerPage > 0) {
                        int topIndex = searchTable.getTopIndex();
                        int numItems = searchTable.getItemCount();
                        int currIdx = searchTable.getSelectionIndex();

                        if ((topIndex + itemsPerPage) < numItems) {
                            searchTable.setTopIndex(topIndex + itemsPerPage);
                        }

                        if ((currIdx + itemsPerPage) < numItems) {
                            QuickSearchElementAndContributor element =
                                    matchingElements
                                            .get(currIdx + itemsPerPage);
                            searchTableViewer
                                    .setSelection(new StructuredSelection(
                                            element));
                            searchTableViewer.reveal(element);

                            selectionChangedSinceLastSetPattern = true;

                        } else if (numItems > 0) {
                            QuickSearchElementAndContributor element =
                                    matchingElements.get(numItems - 1);
                            searchTableViewer
                                    .setSelection(new StructuredSelection(
                                            element));
                            searchTableViewer.reveal(element);

                        }
                    }
                }

            } else {
                searchTableViewer.setSelection(StructuredSelection.EMPTY);
            }
        }

        return;
    }

    /**
     * Page down the matching elements list.
     */
    public void pageUpMatchingElements() {
        if (searchTable != null && !searchTable.isDisposed()) {
            List<QuickSearchElementAndContributor> matchingElements =
                    searchHelper.getCurrentMatchingElements();

            if (!matchingElements.isEmpty()) {
                int idx = searchTable.getSelectionIndex();

                Rectangle bnds = searchTable.getClientArea();

                int itemHeight = searchTable.getItemHeight();
                if (itemHeight > 0) {
                    int itemsPerPage = bnds.height / itemHeight;

                    if (itemsPerPage > 0) {
                        int topIndex = searchTable.getTopIndex();
                        int numItems = searchTable.getItemCount();
                        int currIdx = searchTable.getSelectionIndex();

                        if ((topIndex - itemsPerPage) > 0) {
                            searchTable.setTopIndex(topIndex - itemsPerPage);
                        } else {
                            searchTable.setTopIndex(0);
                        }

                        if ((currIdx - itemsPerPage) > 0) {
                            QuickSearchElementAndContributor element =
                                    matchingElements
                                            .get(currIdx - itemsPerPage);
                            searchTableViewer
                                    .setSelection(new StructuredSelection(
                                            element));
                            searchTableViewer.reveal(element);

                            selectionChangedSinceLastSetPattern = true;

                        } else if (numItems > 0) {
                            QuickSearchElementAndContributor element =
                                    matchingElements.get(0);
                            searchTableViewer
                                    .setSelection(new StructuredSelection(
                                            element));
                            searchTableViewer.reveal(element);

                        }
                    }
                }

            } else {
                searchTableViewer.setSelection(StructuredSelection.EMPTY);
            }
        }

        return;
    }

    /**
     * Update the search path control.
     */
    public void refreshSearchPath() {
        if (searchPathCtrl != null && !searchPathCtrl.isDisposed()
                && searchTable != null && !searchTable.isDisposed()) {
            String searchPath = ""; //$NON-NLS-1$

            ISelection sel = searchTableViewer.getSelection();

            if (sel instanceof IStructuredSelection) {
                IStructuredSelection ssel = (IStructuredSelection) sel;

                if (ssel.size() == 1) {
                    if (ssel.getFirstElement() instanceof QuickSearchElementAndContributor) {
                        QuickSearchElementAndContributor element =
                                (QuickSearchElementAndContributor) ssel
                                        .getFirstElement();

                        searchPath =
                                element.getElementTypeName()
                                        + " - " + element.getElementPath(); //$NON-NLS-1$
                    }
                }
            }

            searchPathCtrl.setText(searchPath);
        }

        return;
    }

    /**
     * Open the search categories popup.
     */
    private void selectSearchCategories() {
        if (searchCategoriesPopup != null) {
            closeCategoriesPopup();
        }

        Rectangle b = selectCategoriesToolbar.getControl().getBounds();

        Point loc =
                selectCategoriesToolbar.getControl().getParent()
                        .toDisplay(b.x, b.y);

        searchCategoriesPopup =
                new QuickSearchCategoriesPopup(quickSearchShell, this,
                        new Point(loc.x, loc.y));

        searchCategoriesPopup.getShell()
                .addShellListener(categoriesPopupDeactivateListener);

        searchCategoriesPopup.getShell().open();

        return;
    }

    /**
     * GotoPrevAction
     * <p>
     * The select previous matching element in associated view action.
     */
    private class GotoPrevAction extends Action {
        public GotoPrevAction() {
            super("", Action.AS_PUSH_BUTTON); //$NON-NLS-1$

            this.setId("com.tibco.xpd.resources.ui.quicksearch.popup.gotoPrevAction"); //$NON-NLS-1$
            this.setImageDescriptor(XpdResourcesUIActivator
                    .getImageDescriptor(XpdResourcesUIConstants.IMG_PREVIOUS_ICON));
            this.setDisabledImageDescriptor(XpdResourcesUIActivator
                    .getImageDescriptor(XpdResourcesUIConstants.IMG_PREVIOUSDISABLED_ICON));

            this.setToolTipText(Messages.QuickSearchPopup_GotoPrevious_tooltip);
        }

        @Override
        public void run() {
            selectPreviousMatchingElement();
            gotoSelectedSearchElement(false);
        }
    }

    /**
     * GotoNextAction
     * <p>
     * The select next matching element in associated view action.
     */
    private class GotoNextAction extends Action {
        public GotoNextAction() {
            super("", Action.AS_PUSH_BUTTON); //$NON-NLS-1$
            this.setId("com.tibco.xpd.resources.ui.quicksearch.popup.gotoNextAction"); //$NON-NLS-1$
            this.setImageDescriptor(XpdResourcesUIActivator
                    .getImageDescriptor(XpdResourcesUIConstants.IMG_NEXT_ICON));
            this.setDisabledImageDescriptor(XpdResourcesUIActivator
                    .getImageDescriptor(XpdResourcesUIConstants.IMG_NEXTDISABLED_ICON));

            this.setToolTipText(Messages.QuickSearchPopup_GotoNext_tooltip);
        }

        @Override
        public void run() {
            if (selectionChangedSinceLastSetPattern) {
                selectNextMatchingElement();
            } else {
                selectionChangedSinceLastSetPattern = true;
            }
            gotoSelectedSearchElement(false);
        }
    }

    /**
     * SelectCategoriesAction
     * <p>
     * Popup the search category selection popup.
     */
    private class SelectCategoriesAction extends Action {
        public SelectCategoriesAction() {
            super("", Action.AS_PUSH_BUTTON); //$NON-NLS-1$
            this.setId("com.tibco.xpd.resources.ui.quicksearch.popup.selectCategoriesAction"); //$NON-NLS-1$
            this.setImageDescriptor(XpdResourcesUIActivator
                    .getImageDescriptor(XpdResourcesUIConstants.IMG_CATEGORIES_ICON));

            this.setToolTipText(Messages.QuickSearchPopup_SelectQuickSearchCategories_tooltip);
        }

        @Override
        public void run() {
            selectSearchCategories();
        }

    }

    /**
     * KillQuickSearchOnDeactivateListener
     * <p>
     * Popup Deactivation listener for quick search popup
     * <p>
     * The behaviour we want is that we close the quick search popup on
     * deactivation UNLESS we are passing activation to the quick search
     * categories popup.
     */
    private class KillQuickSearchOnDeactivateListener extends ShellAdapter {

        @Override
        public void shellDeactivated(ShellEvent e) {
            // Quick search popup has been deactivated BUT only kill it if it's
            // not because categories popup is gaining the activation.

            // In order to do this, use asnych exec to allow the categories
            // popup to be activated.
            getQuickSearchShell().getDisplay().asyncExec(new Runnable() {

                @Override
                public void run() {
                    boolean closeShell = true;

                    if (searchCategoriesPopup != null
                            && searchCategoriesPopup.getShell() != null) {
                        // We do have a categories popup - is it the active
                        // shell?
                        if (getQuickSearchShell().getDisplay().getActiveShell() == searchCategoriesPopup
                                .getShell()) {
                            closeShell = false;
                        }
                    }

                    if (closeShell && !getQuickSearchShell().isDisposed()) {
                        closePopup();

                    }
                }
            });
        }
    }

    /**
     * KillCategoriesOnDeactivateListener
     * <p>
     * When categories popup looses activation then close it. If the main quick
     * search popup is not the popup receiving activation then kill that too.
     */
    private class KillCategoriesOnDeactivateListener extends ShellAdapter {

        @Override
        public void shellDeactivated(ShellEvent e) {

            // Allow system to activate whatever control is going to be.
            Display.getCurrent().asyncExec(new Runnable() {
                @Override
                public void run() {
                    Shell activeShell = Display.getCurrent().getActiveShell();

                    closeCategoriesPopup();

                    // If the active shell is the quick search popup then do
                    // nothing.
                    if (activeShell != getQuickSearchShell()) {
                        if (!getQuickSearchShell().isDisposed()) {
                            // Loosing activation to entirely different control,
                            // close the main control too.
                            closePopup();
                        }
                    }
                }
            });

        }
    }

    private class WindowIconDragMoveHandler implements MouseListener,
            MouseMoveListener {
        private boolean dragStarted = false;

        private Point startDragOffset = null;

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            if (e.widget instanceof Control) {
                ((Control) e.widget).setCapture(true);
                dragStarted = true;

                Point popupLoc = getQuickSearchShell().getLocation();

                Point mouseLoc = ((Control) e.widget).toDisplay(e.x, e.y);

                startDragOffset =
                        new Point(mouseLoc.x - popupLoc.x, mouseLoc.y
                                - popupLoc.y);
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
            if (dragStarted) {
                dragStarted = false;

                if (e.widget instanceof Control) {
                    ((Control) e.widget).setCapture(false);
                }
            }
        }

        @Override
        public void mouseMove(MouseEvent e) {
            if (dragStarted) {
                if (e.widget instanceof Control) {
                    Point mouseLoc = ((Control) e.widget).toDisplay(e.x, e.y);

                    Point popupLoc =
                            new Point(mouseLoc.x - startDragOffset.x,
                                    mouseLoc.y - startDragOffset.y);
                    getQuickSearchShell().setLocation(popupLoc);
                }
            }
        }

    }
}
