/**
 * MatchingElementsListKeyListener.java
 *
 * 
 *
 * @author aallway
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.resources.ui.api.quicksearch.popup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

/**
 * SearchTableCtrlKeyListener
 * 
 * Keyboard listener for the matching results table list control.
 */
class MatchingElementsListKeyListener implements KeyListener {

    private final QuickSearchPopup quickSearchPopup;

    /**
     * @param quickSearchPopup
     */
    MatchingElementsListKeyListener(QuickSearchPopup quickSearchPopup) {
        this.quickSearchPopup = quickSearchPopup;
    }

    public void keyPressed(KeyEvent e) {
        return;
    }

    public void keyReleased(KeyEvent e) {
        if (e.keyCode == SWT.CR) {
            // Ask associated view to select element currently selected in
            // matching items list and close the popup.
            quickSearchPopup.gotoSelectedSearchElement(true);

            e.doit = false;
            
        } else if (e.keyCode == SWT.ESC) {
            quickSearchPopup.closePopup();
        }   

        return;
    }

}