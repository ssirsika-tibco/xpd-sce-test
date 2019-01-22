package com.tibco.bx.validation.preferences;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;

public class BpmGeneralPreferencePage extends PreferencePage implements
        IWorkbenchPreferencePage, IWorkbenchPropertyPage {

    /**
     * 
     */
    public BpmGeneralPreferencePage() {
        noDefaultAndApplyButton();
    }

    /**
     * @param title
     * @param image
     */
    public BpmGeneralPreferencePage(String title, ImageDescriptor image) {
        super(title, image);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param title
     */
    public BpmGeneralPreferencePage(String title) {
        super(title);
        // TODO Auto-generated constructor stub
    }

    private IAdaptable element;

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     * 
     * @param parent
     * @return
     */
    @Override
    protected Control createContents(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        root.setLayout(layout);

        Label lbl = new Label(root, SWT.NONE);
        lbl.setText("Expand this category to set specific BPM properties"); //$NON-NLS-1$

        return root;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
     * 
     * @return
     */
    public IAdaptable getElement() {
        return element;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
     * 
     * @param element
     */
    public void setElement(IAdaptable element) {
        this.element = element;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     * 
     * @param workbench
     */
    public void init(IWorkbench workbench) {
        // TODO Auto-generated method stub

    }

}
