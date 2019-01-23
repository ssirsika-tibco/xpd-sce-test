/**
 * QuickSearchCategoriesPopup.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch.popup;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.quickSearch.QuickSearchCategoryAndCheckstate;

/**
 * QuickSearchCategoriesPopup
 * <p>
 * The quick search popup's search category selection popup (provides a tree of
 * checkbox selectable search categories contributed to the quick search popup.
 */
class QuickSearchCategoriesPopup implements ITreeContentProvider,
        ILabelProvider {

    private QuickSearchPopup quickSearchPopup;

    private Shell shell;

    private Composite rootContainer;

    private CheckboxTreeViewer categoryViewer;

    private Tree categoryTree;


    /**
     * @param partShell
     * @param helper
     * @param cursorLocation
     */
    QuickSearchCategoriesPopup(Shell partShell,
            QuickSearchPopup quickSearchPopup, Point cursorLocation) {
        this.quickSearchPopup = quickSearchPopup;

        shell = new Shell(shell, SWT.NO_TRIM | SWT.ON_TOP);
        shell.setLayout(new FillLayout());

        shell.setSize(10, 100);
        createContent(shell);

        Point sz = categoryViewer.getTree().computeSize(SWT.DEFAULT, 150, true);
        sz.x += 50;
        
        if (sz.x < 100) {
            sz.x = 100;
        }
        if (sz.y < 100) {
            sz.y = 100;
        }
        shell.setSize(sz);
        Rectangle screenBnds = Display.getDefault().getClientArea();
        Point loc = new Point(cursorLocation.x, cursorLocation.y);

        if ((loc.x + sz.x) > screenBnds.width) {
            loc.x = screenBnds.width - sz.x;
        }
        if ((loc.y + sz.y) > screenBnds.height) {
            loc.y = screenBnds.height - sz.y;
        }
        shell.setLocation(loc);
        shell.layout(true);
    }

    /**
     * @return the shell
     */
    public Shell getShell() {
        return shell;
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

        // Create the tree view of checkable search categories
        categoryViewer = new CheckboxTreeViewer(rootContainer, SWT.NONE);
        categoryTree = categoryViewer.getTree();

        categoryTree.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.FILL_VERTICAL));

        categoryViewer.setContentProvider(this);
        categoryViewer.setLabelProvider(this);
        categoryViewer.setInput(this);
        categoryViewer.expandAll();

        categoryViewer.addCheckStateListener(new ICheckStateListener() {

            public void checkStateChanged(CheckStateChangedEvent event) {
                // reperform the existing search.
                if (event.getElement() instanceof QuickSearchCategoryAndCheckstate) {
                    // Set check state of this category and all it's children.
                    QuickSearchCategoryAndCheckstate category =
                            (QuickSearchCategoryAndCheckstate) event.getElement();

                    setCategoryCheckState(category, event.getChecked());

                    synchroniseCheckStates(quickSearchPopup.getSearchHelper()
                            .getSearchCategories());

                    quickSearchPopup.getSearchHelper()
                            .cacheContributedElements();

                    quickSearchPopup.setSearchPattern(quickSearchPopup
                            .getSearchHelper().getSearchString());
                }
            }
        });

        synchroniseCheckStates(quickSearchPopup.getSearchHelper()
                .getSearchCategories());

        categoryTree.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                if (e.keyCode == SWT.ESC) {
                    quickSearchPopup.closeCategoriesPopup();
                    quickSearchPopup.getQuickSearchShell().setActive();
                }

            }
        });

        categoryTree.setForeground(rootContainer.getForeground());
        categoryTree.setBackground(rootContainer.getBackground());

        return;
    }

    /**
     * Synchronise the check state in tree control with those given by the
     * configuration items.
     * 
     * @param items
     * @return Bit flags 0x01 = At least one is unchecked, 0x02 = At least one
     *         is checked
     */
    private int synchroniseCheckStates(List<QuickSearchCategoryAndCheckstate> items) {

        int checkState = 0;

        for (QuickSearchCategoryAndCheckstate item : items) {
            // Recurs to set children states.
            List<QuickSearchCategoryAndCheckstate> children = item.getChildren();

            if (children != null && children.size() > 0) {
                // This is a parent item in tree.
                int childState = synchroniseCheckStates(children);

                // Synchronise parent config item with state of children.
                if ((childState & 0x01) != 0 && (childState & 0x02) != 0) {
                    // There is a mixture of check states in child. Set this
                    // parent config item to unchecked (so owner doesn't
                    // think that all underneath are checked)
                    item.setChecked(false);
                    categoryViewer.setGrayChecked(item, true);

                } else if ((childState & 0x02) != 0) {
                    // Some were checked and none were unchecked (all were
                    // checked)
                    item.setChecked(true);
                    categoryViewer.setGrayed(item, false);
                    categoryViewer.setChecked(item, true);

                } else {
                    // All were unchecked
                    item.setChecked(false);
                    categoryViewer.setGrayed(item, false);
                    categoryViewer.setChecked(item, false);

                }

                // Add the results to our return state.
                checkState |= childState;

            } else {
                // Not a parent item, set state according to config item.
                boolean isChecked = item.isChecked();

                categoryViewer.setChecked(item, isChecked);

                if (isChecked) {
                    checkState |= 0x02;
                } else {
                    checkState |= 0x01;
                }
            }
        }

        return checkState;
    }

    /**
     * Set the configuration item check state.
     * 
     * @param category
     * @param checked
     * @param recursing
     */
    private void setCategoryCheckState(QuickSearchCategoryAndCheckstate category,
            boolean checked) {
        // Set the check state of this item and all children to the given
        // state

        category.setChecked(checked);

        // Recursively.
        List<QuickSearchCategoryAndCheckstate> children = category.getChildren();

        if (children != null && children.size() > 0) {
            for (QuickSearchCategoryAndCheckstate child : children) {
                setCategoryCheckState(child, checked);
            }
        }

        return;
    }

    public void dispose() {
    }

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    public void addListener(ILabelProviderListener listener) {
    }

    public boolean isLabelProperty(Object element, String property) {
        return false;
    }

    public void removeListener(ILabelProviderListener listener) {
    }

    public Image getImage(Object element) {
        return null;
    }

    public String getText(Object element) {
        if (element instanceof QuickSearchCategoryAndCheckstate) {
            return ((QuickSearchCategoryAndCheckstate) element).getCategory().getLabel();
        }
        return ""; //$NON-NLS-1$
    }

    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof QuickSearchCategoryAndCheckstate) {
            List<QuickSearchCategoryAndCheckstate> children =
                    ((QuickSearchCategoryAndCheckstate) parentElement).getChildren();
            if (children != null && !children.isEmpty()) {
                return children.toArray();
            }
        }

        return new Object[0];
    }

    public Object getParent(Object element) {
        if (element instanceof QuickSearchCategoryAndCheckstate) {
            return ((QuickSearchCategoryAndCheckstate) element).getParent();
        }
        return null;
    }

    public boolean hasChildren(Object element) {
        if (element instanceof QuickSearchCategoryAndCheckstate) {
            List<QuickSearchCategoryAndCheckstate> children =
                    ((QuickSearchCategoryAndCheckstate) element).getChildren();
            if (children != null && !children.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public Object[] getElements(Object inputElement) {
        List<QuickSearchCategoryAndCheckstate> cats =
                quickSearchPopup.getSearchHelper().getSearchCategories();
        if (cats != null && !cats.isEmpty()) {
            return cats.toArray();
        }

        return new Object[0];
    }

}
