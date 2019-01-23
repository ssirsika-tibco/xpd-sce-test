/*
 ** 
 **  MODULE:             $RCSfile: DeleteObjectActionHandler.java $ 
 **                      $Revision: 1.5 $ 
 **                      $Date: 2005/04/22 14:01:41Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 */
package com.tibco.xpd.processwidget.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.xpd.processwidget.popup.actions.DeleteObject;

/**
 * Handler for DeleteObjects action
 * 
 * @author WojciechZ
 */
public class DeleteObjectActionHandler extends Action implements
        ISelectionChangedListener {

    private DeleteObject deleteDelegate;

    private final ISelectionProvider provider;

    /**
     * @param graphicalViewer
     */
    public DeleteObjectActionHandler(ISelectionProvider provider) {
        this.provider = provider;
        provider.addSelectionChangedListener(this);
        deleteDelegate = new DeleteObject();
        setId(ActionFactory.DELETE.getId());
        /*
         * Set this action as disabled by default, otherwise the delete action
         * is set as enabled when the process editor is opened but there is
         * nothing that can be deleted (more apparent in the RCP due to the
         * presence of the delete action in the ribbon).
         */
        setEnabled(false);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
     */
    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        deleteDelegate.selectionChanged(this, event.getSelection());
    }

    /**
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        deleteDelegate.run(this);
    }

    /**
     * Stop listening of the model
     */
    public void dispose() {
        provider.removeSelectionChangedListener(this);
    }
}