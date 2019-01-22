/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.properties.table;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * <i>Created: Nov 10, 2006</i>.
 * 
 * @author mmaciuki
 */
interface InternalTableActionsListener {

    /**
     * Return true if the class wants auto-crfeate new row behaviour.
     * 
     * @return
     */
    boolean canCreateNewRows();

    /**
     * Return the default value for first cell of potential new rows (Only used
     * if new row behaviour is on).
     * 
     * @return String
     */
    String createDefaultId();

    /**
     * Add the data for a new row at the end of the current rows.
     * 
     * @param id
     *            param
     * 
     * @return Data object for new row
     */
    Object createWithDefaults(String id);

    /**
     * Class is capable of deleting items.
     * 
     * @return
     */
    boolean canDeleteRows();

    /**
     * Delete the data for the selected rows..
     * 
     * @param selection
     *            param
     */
    void deleteRows(IStructuredSelection selection);

    /**
     * Class is capable of moving a rows up
     * 
     * @return
     */
    boolean canMoveRowUp();

    /**
     * Move a row up 
     * 
     * @param rowData
     */
    void moveRowUp(Object rowData);

    /**
     * Class is capable of moving rows down.
     * 
     * @return
     */
    boolean canMoveRowDown();

    /**
     * Move a row down
     * 
     * @param rowData
     */
    void moveRowDown(Object rowData);

    /**
     * The input for table viewer has changed.
     * 
     * @param inputEObject
     *            param
     */
    void setInput(EObject inputEObject);

}
