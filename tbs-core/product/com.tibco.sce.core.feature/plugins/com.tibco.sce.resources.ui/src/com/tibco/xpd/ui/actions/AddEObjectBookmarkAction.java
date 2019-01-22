/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.AddBookmarkAction;
import org.eclipse.ui.ide.IDEActionFactory;

/**
 * Action To Bookmark a selected EObject element (or one that is
 * IAdaptable to EObject) to the eclipse standard Bookmarks view.
 * 
 * @author aallway
 * @since 3.2
 */
public class AddEObjectBookmarkAction extends Action implements ISelectionChangedListener {

    private IActionDelegate actionDelegate;
    
    private final ISelectionProvider selectionProvider;
    
    public AddEObjectBookmarkAction(ISelectionProvider provider) {
        selectionProvider = provider;
        selectionProvider.addSelectionChangedListener(this);
        actionDelegate = new AddEObjectBookmarkActionDelegate();
        
        setId(IDEActionFactory.BOOKMARK.getId()); //$NON-NLS-1$
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return super.getId();
    }
    public void selectionChanged(SelectionChangedEvent event) {
        actionDelegate.selectionChanged(this, event.getSelection());
    }
    
    @Override
    public void run() {
        actionDelegate.run(this);
    }
    /**
     * Stop listening of the model
     */
    public void dispose() {
        selectionProvider.removeSelectionChangedListener(this);
    }
    
}
