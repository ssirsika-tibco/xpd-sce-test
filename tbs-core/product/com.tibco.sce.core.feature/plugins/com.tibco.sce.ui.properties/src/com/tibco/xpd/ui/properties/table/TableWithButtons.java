/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.components.AbstractViewerWithButtons;

/**
 * <b>Table viewer with optional buttons</b>
 * <p>
 * This class provides a wrapper around a table view with the potential to add
 * standard action buttons (add row, delete row(s), move row up / down). You can
 * also add new actions of your own.
 * <p>
 * <b>Example...</b>
 * <p>
 * 
 * <pre>
 * // Create the table with buttons wrapper.
 * TableWithButtons tableWithBtns = new TableWithButtons(xpdFormToolkit, parentComposite, 
 *       SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
 * 
 * // Force creation of wrapped table viewer  
 * Composite parentOfTableAndButtons = tableWithButtons.createControl();
 * 
 * // You can now access the wrapped table viweer 
 * TableViewer = tableWithButtons.getViewer();
 * 
 * // Now you can set cell editors / content and label providers on viweer as normal.
 * 
 * &lt;b&gt;// You can also add actions to the actions manager.
 * // There are 4 built in abstract action providers that you can simply override
 * // to provide the manipulation to the list data required for each action...
 * // &lt;/b&gt;
 * // TableWithButtonsNewRowAction.java
 * // TableWithButtonsDeleteRowAction.java
 * // TableWithButtonsMoveRowUpAction.java
 * // TableWithButtonsMoveRowDownAction.java
 * //
 * // Simply subclass any of these and add them to the actions manager...
 * tableWithButtons.getActionsManager().add(new MYAddNewDataRow(tableViewer));
 * tableWithButtons.getActionsManager().add(new MYAddDeleteDataRow(tableViewer));
 * 
 * // When you add the above action types to the actions manager you will also
 * // enable the key-stroke equivalent for those actions.
 * // 
 * // You can also add your own actions to the actions manager.
 * 
 * // Note that If you wish to contribute a TableWithButtonsNewRowAction action BUT
 * // DO NOT want automatic creation of a new row when user cursors of end of last row 
 * // (i.e. the last row [...] facility) then you should not provide a CellEditor for 
 * // the first column. This will mean that the AddNewRow action will only be called
 * // by the user pressing [+] or selecting context menu-&gt;New
 * </pre>
 * 
 * <p>
 * 
 * @author Sid
 */
public final class TableWithButtons extends AbstractViewerWithButtons {

    private InternalCustomTableViewer internalTableViewer = null;

    /**
     * Constructor.
     * 
     * @param toolkit
     * @param parent
     * @param style
     */
    public TableWithButtons(XpdFormToolkit toolkit, Composite parent, int style) {
        super(toolkit, parent, style);

    }

    @Override
    protected StructuredViewer createViewer(XpdFormToolkit toolkit,
            Composite parent, int viewerStyle) {
        internalTableViewer =
                new InternalCustomTableViewer(toolkit, parent,
                        new MapActionsToActionsListener(), viewerStyle);

        return internalTableViewer;
    }

    /**
     * Get the tabel viewer.
     */
    public TableViewer getViewer() {
        return (TableViewer) super.getViewer();
    }

    /**
     * This class maps the std add/del/move up/move down actions that have been
     * added to the TableWithButtons onto the InternalCustomTableViewer's
     * actions.
     * 
     * @author aallway
     * 
     */
    private class MapActionsToActionsListener implements
            InternalTableActionsListener {

        /**
         * Find given action class in list of action contributors.
         * 
         * @param actionClass
         * @return
         */
        private Object findAction(Class<?> actionClass) {
            IContributionManager actionsManager = getActionsManager();

            if (actionsManager != null) {
                IContributionItem[] actions = actionsManager.getItems();

                if (actions != null) {
                    for (int i = 0; i < actions.length; i++) {
                        if (actions[i] instanceof ActionContributionItem) {
                            IAction action =
                                    ((ActionContributionItem) actions[i])
                                            .getAction();

                            if (actionClass.isInstance(action)) {
                                return action;
                            }
                        }

                    }
                }
            }

            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.properties.table.InternalTableActionsListener#
         * createDefaultId()
         */
        public String createDefaultId() {
            TableWithButtonsNewRowAction action =
                    (TableWithButtonsNewRowAction) findAction(TableWithButtonsNewRowAction.class);
            if (action != null) {
                return action.getNewRowFirstCellVal();
            }
            throw new NullPointerException("Action is null."); //$NON-NLS-1$
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.properties.table.InternalTableActionsListener#
         * createWithDefaults(java.lang.String)
         */
        public Object createWithDefaults(String id) {
            // When asked to create new row by viewer then use the raw create
            // data (the viewer can then adjust the selection etc)
            TableWithButtonsNewRowAction action =
                    (TableWithButtonsNewRowAction) findAction(TableWithButtonsNewRowAction.class);
            if (action != null) {
                Object newRowData = action.createNewRow(id);

                return newRowData;
            }
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.properties.table.InternalTableActionsListener#
         * canCreateNewRows()
         */
        public boolean canCreateNewRows() {
            // Can create new rows if the owner has added new row action.
            TableWithButtonsNewRowAction action =
                    (TableWithButtonsNewRowAction) findAction(TableWithButtonsNewRowAction.class);
            if (action != null) {
                return true;
            }
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.ui.properties.table.InternalTableActionsListener#deleteRows
         * (org.eclipse.jface.viewers.IStructuredSelection)
         */
        public void deleteRows(IStructuredSelection selection) {
            // When asked to create delete rows by viewer then use the raw
            // delete data (the viewer can then adjust the selection etc)
            TableWithButtonsDeleteRowAction action =
                    (TableWithButtonsDeleteRowAction) findAction(TableWithButtonsDeleteRowAction.class);
            if (action != null && canDeleteRows()) {
                action.deleteRows(selection);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.properties.table.InternalTableActionsListener#
         * canDeleteRows()
         */
        public boolean canDeleteRows() {
            // Can create new rows if the owner has added new row action.
            TableWithButtonsDeleteRowAction action =
                    (TableWithButtonsDeleteRowAction) findAction(TableWithButtonsDeleteRowAction.class);
            if (action != null && action.isEnabled()) {
                return true;
            }
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.ui.properties.table.InternalTableActionsListener#setInput
         * (org.eclipse.emf.ecore.EObject)
         */
        public void setInput(EObject inputEObject) {
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.properties.table.InternalTableActionsListener#
         * canMoveRowUp()
         */
        public boolean canMoveRowUp() {
            // Can create new rows if the owner has added new row action.
            TableWithButtonsMoveRowUpAction action =
                    (TableWithButtonsMoveRowUpAction) findAction(TableWithButtonsMoveRowUpAction.class);
            if (action != null) {
                return true;
            }
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * com.tibco.xpd.ui.properties.table.InternalTableActionsListener#moveRowUp
         * (java.lang.Object)
         */
        public void moveRowUp(Object rowData) {
            // When asked to move row by viewer then use the raw
            // move data (the viewer can then adjust the selection etc)
            TableWithButtonsMoveRowUpAction action =
                    (TableWithButtonsMoveRowUpAction) findAction(TableWithButtonsMoveRowUpAction.class);
            if (action != null) {
                action.moveRowUp(rowData);
            }

            return;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.properties.table.InternalTableActionsListener#
         * canMoveRowDown()
         */
        public boolean canMoveRowDown() {
            // Can create new rows if the owner has added new row action.
            TableWithButtonsMoveRowDownAction action =
                    (TableWithButtonsMoveRowDownAction) findAction(TableWithButtonsMoveRowDownAction.class);
            if (action != null) {
                return true;
            }
            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @seecom.tibco.xpd.ui.properties.table.InternalTableActionsListener#
         * moveRowDown(java.lang.Object)
         */
        public void moveRowDown(Object rowData) {
            // When asked to move row by viewer then use the raw
            // move data (the viewer can then adjust the selection etc)
            TableWithButtonsMoveRowDownAction action =
                    (TableWithButtonsMoveRowDownAction) findAction(TableWithButtonsMoveRowDownAction.class);
            if (action != null) {
                action.moveRowDown(rowData);
            }

            return;
        }

    }

}
