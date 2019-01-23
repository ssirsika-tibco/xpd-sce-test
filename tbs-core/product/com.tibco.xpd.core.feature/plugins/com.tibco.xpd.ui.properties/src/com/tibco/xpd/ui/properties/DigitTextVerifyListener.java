/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.ui.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

/**
 * DigitTextVerifyListener
 * <p>
 * This class helps to fulfil the purpose to allow only digits to be entered in
 * the numeric text fields and ignore the rest of the inputs (e.g. text).
 * <p>
 * This class implements the VerifyListener interface. Generally, classes which
 * implement this interface provide a method that deals with the events that are
 * generated when text is about to be modified. In order to use this interface
 * for our intended purpose, DigitTextVerifyListener overrides it's verifyText
 * method.
 * <p>
 * <b>How to use this class...</b>
 * <li>Create an instance of the class.</li>
 * <li>Simply add it to the control you wish to use it on (by using the
 * addVerifyListener method)</li>
 * 
 * @author sajain
 * @since Sep 27, 2012
 */
public class DigitTextVerifyListener implements VerifyListener {

    /**
     * @see org.eclipse.swt.events.VerifyListener#verifyText(org.eclipse.swt.events.VerifyEvent)
     * 
     * @param e
     */
    public void verifyText(VerifyEvent e) {
        // TODO Auto-generated method stub
        if (Character.isDigit(e.character) || e.keyCode == SWT.DEL
                || e.keyCode == SWT.BS || e.character == 0) {
            e.doit = true;
        } else {
            e.doit = false;
        }

    }
}
