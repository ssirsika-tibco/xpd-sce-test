/**
 * SearchPatternKeyListener.java
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
 * SearchPatternKeyListener
 * 
 * Keyboard handler for the Search pattern text control.
 */
class SearchPatternKeyListener implements KeyListener {

    /**
     * 
     */
    private final QuickSearchPopup quickSearchPopup;

    /**
     * @param quickSearchPopup
     */
    SearchPatternKeyListener(QuickSearchPopup quickSearchPopup) {
        this.quickSearchPopup = quickSearchPopup;
    }

    public void keyPressed(KeyEvent e) {
        if (e.keyCode == SWT.ARROW_DOWN) {
            this.quickSearchPopup.selectNextMatchingElement();

            // Prevent normal 'cursor right' behaviour for arrow down on
            // single line text ctrl.
            e.doit = false;

        } else if (e.keyCode == SWT.ARROW_UP) {
            this.quickSearchPopup.selectPreviousMatchingElement();

            // Prevent normal 'cursor left' behaviour for arrow up on
            // single line text ctrl.
            e.doit = false;
            
        } else if (e.keyCode == SWT.PAGE_DOWN) {
            quickSearchPopup.pageDownMatchingElements();
            
            e.doit = false;
            
        } else if (e.keyCode == SWT.PAGE_UP) {
            quickSearchPopup.pageUpMatchingElements();
            
            e.doit = false;
            
        }

        return;
    }

    public void keyReleased(KeyEvent e) {
        if (e.keyCode == SWT.CR) {
            // Ask associated view to select element currently selected in
            // matching items list and close the popup.
            this.quickSearchPopup.gotoSelectedSearchElement(true);

            e.doit = false;

        } else if (e.keyCode == SWT.ESC) {
            quickSearchPopup.closePopup();
        }

        return;
    }

}