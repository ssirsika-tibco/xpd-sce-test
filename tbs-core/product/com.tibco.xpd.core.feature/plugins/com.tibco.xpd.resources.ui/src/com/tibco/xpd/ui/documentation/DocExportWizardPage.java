/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.documentation;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;

import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage;

/**
 * 
 * @author mtorres
 * 
 * @since 3.5
 * 
 */
public class DocExportWizardPage extends ExportWizardPage {

    public DocExportWizardPage(IStructuredSelection selection, Object input,
            ViewerSorter sorter, ViewerFilter[] filters,
            IExportDataProvider provider) {
        super(selection, input, sorter, filters, provider);
    }

    @Override
    protected Control createUserControl(Composite container) {
        FormText docPreferencesForm = new FormText(container, SWT.WRAP);
        docPreferencesForm
                .setText(Messages.DocExportWizardPage_ConfigureDocExportPrefs,
                        true,
                        false);
        docPreferencesForm.addHyperlinkListener(new DocPrefsHyperlinkListener());
        return docPreferencesForm;
    }
    
    class DocPrefsHyperlinkListener implements IHyperlinkListener {

        @Override
        public void linkActivated(HyperlinkEvent e) {
            if (e.getHref() != null && e.getHref().equals("configureDocPrefs")) {//$NON-NLS-1$
                PreferenceDialog pref =
                        PreferencesUtil
                                .createPreferenceDialogOn(getShell(),
                                        "com.tibco.xpd.ui.preferences.documentation.DocumentationGeneralPreferencePage", null, null);//$NON-NLS-1$
                if (pref != null) {
                    pref.open();
                }
            }
        }

        @Override
        public void linkEntered(HyperlinkEvent e) {
            // TODO Auto-generated method stub            
        }

        @Override
        public void linkExited(HyperlinkEvent e) {
            // TODO Auto-generated method stub            
        }


        
    }

}
