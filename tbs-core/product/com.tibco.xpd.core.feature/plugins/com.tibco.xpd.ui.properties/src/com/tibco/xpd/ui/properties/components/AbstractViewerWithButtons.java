/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.properties.components;

import java.beans.PropertyChangeListener;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;

import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * <p>
 * AbstractViewerWithButtons
 * <p>
 * This class allows creation of a structured viewer with the ability to add
 * action buttons to it.
 * <p>
 * In other words, you subclass this class and provide whatever viewer you wish
 * via the createViewer() method. And then you can add action buttons to it.
 * <p>
 * Currently these buttons are always created on the right hand side.
 * <p>
 * TODO: Later we Create 4 sets of buttons positioned top, left, right and below
 * viewer.
 * 
 * @author Sid
 * 
 */
public abstract class AbstractViewerWithButtons {

    private Composite parent = null;

    private Composite root = null;

    private StructuredViewer viewer = null; // This will be created by

    // sub-class.

    private int viewerStyle = 0;

    private XpdFormToolkit toolkit = null;

    private ViewsButtonsContributionManager actionsManager = null;

    private boolean enabled = true;

    private Composite buttonsComposite;

    /**
     * The constructor.
     */
    public AbstractViewerWithButtons(XpdFormToolkit toolkit, Composite parent,
            int viewerStyle) {
        this.parent = parent;
        this.viewerStyle = viewerStyle;
        this.toolkit = toolkit;

    }

    /**
     * Set the background color of this control.
     * 
     * @param color
     * @since 3.2
     */
    public void setBackground(Color color) {
        if (color != null) {
            if (root != null && !root.isDisposed())
                root.setBackground(color);
            if (buttonsComposite != null && !buttonsComposite.isDisposed())
                buttonsComposite.setBackground(color);
        }
    }

    /**
     * Create the control. This should be done directly after construction.
     * 
     */
    public Composite createControl() {
        // Create the root parent of the viewer and the button stack.
        root = toolkit.createComposite(parent);

        GridLayout runtimeControlLayout = new GridLayout(2, false);
        runtimeControlLayout.marginHeight = 1;
        runtimeControlLayout.marginWidth = 1;
        root.setLayout(runtimeControlLayout);

        viewer = createViewer(toolkit, root, viewerStyle);

        GridData viewerGridData = new GridData(GridData.FILL_BOTH);

        viewer.getControl().setLayoutData(viewerGridData);

        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                AbstractViewerWithButtons.this
                        .selectionChanged((IStructuredSelection) event
                                .getSelection());
            }
        });

        buttonsComposite = toolkit.createComposite(root, SWT.NONE);
        GridLayout buttonsCompositeLayout = new GridLayout(1, false);
        buttonsCompositeLayout.marginHeight = 2;
        buttonsCompositeLayout.marginWidth = 0;
        buttonsComposite.setLayout(buttonsCompositeLayout);

        Table table = ((TableViewer) viewer).getTable();
        actionsManager = new ViewsButtonsContributionManager(toolkit,
                buttonsComposite, table);

        selectionChanged((IStructuredSelection) getViewer().getSelection());

        return root;
    }

    /**
     * Subclass should override this method to create the viewer it wants.
     * 
     * @param toolkit
     * @param parent
     * @return
     */
    protected abstract StructuredViewer createViewer(XpdFormToolkit toolkit,
            Composite parent, int viewerStyle);

    /**
     * 
     * @return The viewer that forms the main part of the control.
     */
    public StructuredViewer getViewer() {
        return viewer;
    }

    /**
     * Return the buttons manager - new actions can be added to this and will
     * appear as buttons to the right of the viewer.
     * 
     * @return button manager.
     */
    public IContributionManager getActionsManager() {
        return actionsManager;
    }

    /**
     * Notify this control that the selection in the viewer has changed.
     * 
     * @param selection
     */
    public void selectionChanged(IStructuredSelection selection) {
        if (actionsManager != null) {
            actionsManager.selectionChanged(selection);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        actionsManager.setEnabled(enabled);
    }

    /**
     * Manage the button action contributions.
     * 
     */
    private static class ViewsButtonsContributionManager extends
            ContributionManager {

        private static final String ACTION_LISTENER = "ActionListener"; //$NON-NLS-1$

        private static final String ACTION = "Action"; //$NON-NLS-1$

        private final Composite parent;

        private final XpdFormToolkit toolkit;

        private final ButtonSelectionListener actionSelectionListener = new ButtonSelectionListener();

        private final Table table;

        private boolean enabled = true;

        public ViewsButtonsContributionManager(XpdFormToolkit toolkit,
                Composite parent, Table table) {
            this.parent = parent;
            this.toolkit = toolkit;
            this.table = table;

            // context menu
            MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
            menuManager.setRemoveAllWhenShown(true);
            menuManager.addMenuListener(new IMenuListener() {
                public void menuAboutToShow(IMenuManager manager) {
                    IContributionItem[] items = getItems();
                    for (IContributionItem item : items) {
                        if (item instanceof ActionContributionItem) {
                            manager.add(item);
                        }
                    }
                }
            });
            Menu menu = menuManager.createContextMenu(table);
            table.setMenu(menu);
        }

        public void setEnabled(boolean enabled) {
            if (enabled != this.enabled) {
                this.enabled = enabled;
                update(true);
            }
        }

        public void update(boolean force) {
            if (isDirty() || force) {
                for (Control control : parent.getChildren()) {
                    if (control.getData(ACTION) instanceof IAction) {
                        IAction action = (IAction) control.getData(ACTION);
                        Object listener = control.getData(ACTION_LISTENER);
                        if (listener instanceof PropertyChangeListener) {
                            action
                                    .removePropertyChangeListener((IPropertyChangeListener) listener);
                        }
                        control.dispose();
                    }
                }
                IContributionItem[] items = getItems();
                for (IContributionItem item : items) {
                    if (item instanceof ActionContributionItem) {
                        IAction action = ((ActionContributionItem) item)
                                .getAction();
                        ImageDescriptor id = action.getImageDescriptor();
                        Button button = toolkit.createButton(parent, id == null
                                && action.getText() != null ? action.getText()
                                : "", SWT.PUSH, action.getId()); //$NON-NLS-1$
                        GridData layoutData = new GridData(
                                GridData.FILL_HORIZONTAL);
                        button.setLayoutData(layoutData);
                        button.setData(ACTION, action);
                        button.addSelectionListener(actionSelectionListener);
                        button.setToolTipText(action.getToolTipText());

                        button.setEnabled(action.isEnabled() && enabled);

                        if (id != null) {
                            button.setImage(id.createImage());
                            button.addDisposeListener(new DisposeListener() {

                                public void widgetDisposed(DisposeEvent e) {
                                    Button btn = (Button) e.widget;
                                    Image img = btn.getImage();
                                    if (img != null && !img.isDisposed()) {
                                        img.dispose();
                                    }

                                }
                            });
                        }

                        IPropertyChangeListener listener = new ActionPropertyChangeListener(
                                button);
                        action.addPropertyChangeListener(listener);
                        button.setData(ACTION_LISTENER, listener);
                    }
                }

                if (getItems().length > 0) {
                    GridData layoutData = new GridData(GridData.BEGINNING,
                            GridData.BEGINNING, false, false);
                    parent.setLayoutData(layoutData);
                } else {
                    GridData layoutData = new GridData(GridData.BEGINNING,
                            GridData.BEGINNING, false, false);
                    layoutData.widthHint = 0;
                    layoutData.heightHint = 0;
                    parent.setLayoutData(layoutData);

                }

                parent.layout();
            }

        }

        private class ButtonSelectionListener extends SelectionAdapter {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Object data = e.widget.getData(ACTION);
                if (data instanceof IAction) {
                    ((IAction) data).run();
                }
            }
        }

        public class ActionPropertyChangeListener implements
                IPropertyChangeListener {
            private final Button button;

            public ActionPropertyChangeListener(Button aButton) {
                button = aButton;

            }

            public void propertyChange(PropertyChangeEvent event) {
                Object source = event.getSource();
                if (source instanceof IAction && !button.isDisposed()) {
                    String property = event.getProperty();
                    if (IAction.TEXT.equals(property)) {
                        button.setText((String) event.getNewValue());
                    } else if (IAction.TOOL_TIP_TEXT.equals(property)) {
                        button.setToolTipText((String) event.getNewValue());
                    } else if (IAction.ENABLED.equals(property)) {
                        button.setEnabled(((Boolean) event.getNewValue())
                                .booleanValue()
                                && enabled);
                    } else if (IAction.DESCRIPTION.equals(property)) {
                        // do nothing
                    } else if (IAction.CHECKED.equals(property)) {
                        button.setSelection(((Boolean) event.getNewValue())
                                .booleanValue());
                    } else if (IAction.IMAGE.equals(property)) {
                        // do nothing
                    } else {
                        // do nothing for other not supported properties.
                    }
                }

            }
        }

        public void selectionChanged(IStructuredSelection selection) {
            IContributionItem[] items = getItems();
            for (IContributionItem item : items) {
                if (item instanceof ActionContributionItem) {
                    IAction action = ((ActionContributionItem) item)
                            .getAction();
                    if (action instanceof ViewerAction) {
                        ViewerAction viewerAction = (ViewerAction) action;
                        viewerAction.selectionChanged(selection);
                    }
                }
            }
        }
    }

}
