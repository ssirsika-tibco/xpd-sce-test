/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.extensions;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Wrapper for {@link PreferencePage} that doubles up to allow contribution to
 * the extension point
 * <code>com.tibco.xpd.processeditor.xpdl2.processEditorPreferenceContribution</code>
 * <p>
 * The class is loaded and its normal methods are delegated to from the main
 * process modeler pref page BUT can be loaded directly into a preference dialog
 * (for things such as quick fixes etc.
 * <p>
 * This allows proc modeler pref page to call createContents() etc as we can
 * raisew the visibility of those methods
 * <p>
 * It also allows us to override {@link #setErrorMessage(String)} so that we set
 * it on process modeler page of we are embeded there or in our own page if not.
 * 
 * @author aallway
 * @since 4 Jul 2014
 */
public abstract class AbstractPreferencePageContributor extends PreferencePage {

    private PreferencePage processModelerPrefPage = null;

    /**
     * Method is called when this pref page is loaded into process modler pref
     * page.
     * 
     * @param processModelerPrefPage
     */
    public void initFromProcessModelerPage(PreferencePage processModelerPrefPage) {
        this.processModelerPrefPage = processModelerPrefPage;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#setErrorMessage(java.lang.String)
     * 
     * @param newMessage
     */
    @Override
    public void setErrorMessage(String newMessage) {
        /*
         * IF we are embdded in proc modeler page then we redirect error message
         * there.
         */
        if (processModelerPrefPage != null) {
            processModelerPrefPage.setErrorMessage(newMessage);
        } else {
            super.setErrorMessage(newMessage);
        }
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    public abstract Control createContents(Composite parent);

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
     * 
     */
    @Override
    public void performDefaults() {
        super.performDefaults();
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#performApply()
     * 
     */
    @Override
    public void performApply() {
        super.performApply();
    }

}
