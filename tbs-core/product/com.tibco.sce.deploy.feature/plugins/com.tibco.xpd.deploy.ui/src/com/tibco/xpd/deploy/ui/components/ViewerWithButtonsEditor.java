/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.components;

import java.beans.PropertyChangeListener;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ContributionManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

/**
 * Editor composed of structured viewer and associated actions rendered as
 * buttons on the right side of the viewer.
 * 
 * Here is an example of usage:
 * 
 * <pre>
 * IContributionManager buttonsManager = viewerEditor.getButtonsManager();
 * 
 * Action newAction = new NewViewerElementAction(paramsViewer, &quot;&amp;New...&quot;);
 * Action editAction = new EditViewerElementAction(paramsViewer, &quot;&amp;Edit..&quot;);
 * Action removeAction = new RemoveViewerElementAction(paramsViewer, &quot;&amp;Remove&quot;);
 * 
 * buttonsManager.add(newAction);
 * buttonsManager.add(editAction);
 * buttonsManager.add(removeAction);
 * buttonsManager.update(true);
 * viewerEditor.selectionChanged((IStructuredSelection) paramsViewer
 *         .getSelection());
 * </pre>
 * 
 * <p>
 * <i>Created: 4 December 2006</i>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class ViewerWithButtonsEditor {

    private final Composite parent;

    private final StructuredViewer viewer;

    private final ViewsButtonsContributionManager buttonsManager;

    /**
     * The constructor.
     */
    public ViewerWithButtonsEditor(Composite parent, StructuredViewer viewer) {
        this.parent = parent;
        Layout runtimeControlLayout = new GridLayout(2, false);
        this.parent.setLayout(runtimeControlLayout);

        this.viewer = viewer;
        GridData viewerGridData = new GridData(GridData.FILL_BOTH);
        this.viewer.getControl().setLayoutData(viewerGridData);
        this.viewer
                .addSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        ViewerWithButtonsEditor.this.buttonsManager
                                .selectionChanged((IStructuredSelection) event
                                        .getSelection());
                    }
                });

        // buttons
        Composite buttonsComposite = new Composite(parent, SWT.NONE);
        GridData layoutData = new GridData(GridData.BEGINNING,
                GridData.BEGINNING, false, false);
        buttonsComposite.setLayoutData(layoutData);
        GridLayout buttonsCompositeLayout = new GridLayout(1, false);
        buttonsCompositeLayout.marginHeight = 2;
        buttonsCompositeLayout.marginWidth = 0;
        buttonsComposite.setLayout(buttonsCompositeLayout);
        buttonsManager = new ViewsButtonsContributionManager(buttonsComposite);
    }

    public IContributionManager getButtonsManager() {
        return buttonsManager;
    }

    public void selectionChanged(IStructuredSelection selection) {
        if (buttonsManager != null) {
            buttonsManager.selectionChanged(selection);
        }
    }

    private static class ViewsButtonsContributionManager extends
            ContributionManager {

        private static final String ACTION_LISTENER = "ActionListener"; //$NON-NLS-1$

        private static final String ACTION = "Action"; //$NON-NLS-1$

        private final Composite parent;

        private final ButtonSelectionListener actionSelectionListener = new ButtonSelectionListener();

        public ViewsButtonsContributionManager(Composite parent) {
            this.parent = parent;
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
                        Button button = new Button(parent, SWT.PUSH);
                        GridData layoutData = new GridData(
                                GridData.FILL_HORIZONTAL);
                        layoutData.widthHint = 80;
                        button.setLayoutData(layoutData);
                        button.setData(ACTION, action);
                        button.addSelectionListener(actionSelectionListener);
                        button.setText(action.getText());
                        button.setToolTipText(action.getToolTipText());
                        IPropertyChangeListener listener = new ActionPropertyChangeListener(
                                button);
                        action.addPropertyChangeListener(listener);
                        button.setData(ACTION_LISTENER, listener);
                    }
                }
                parent.layout();
            }

        }

        private static class ButtonSelectionListener extends SelectionAdapter {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Object data = e.widget.getData(ACTION);
                if (data instanceof IAction) {
                    ((IAction) data).run();
                }
            }
        }

        public static class ActionPropertyChangeListener implements
                IPropertyChangeListener {
            private final Button button;

            public ActionPropertyChangeListener(Button aButton) {
                button = aButton;

            }

            public void propertyChange(PropertyChangeEvent event) {
                Object source = event.getSource();
                if (source instanceof IAction) {
                    String property = event.getProperty();
                    if (IAction.TEXT.equals(property)) {
                        button.setText((String) event.getNewValue());
                    } else if (IAction.TOOL_TIP_TEXT.equals(property)) {
                        button.setToolTipText((String) event.getNewValue());
                    } else if (IAction.ENABLED.equals(property)) {
                        button.setEnabled(((Boolean) event.getNewValue())
                                .booleanValue());
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
