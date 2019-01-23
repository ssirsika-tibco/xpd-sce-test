/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.wizard;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * This wizard page can be used in a new creation wizard to allow the user to
 * select a fragment (template) to apply to a model being created. The default
 * implementation shows a check box to select if the fragment should be applied
 * to the model being created and also a list of fragments to select from (based
 * on the fragment provider ids provided).
 * <p>
 * This class can be extended.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class TemplatesWizardPage extends AbstractXpdWizardPage {

    private IFragment fragment;
    private final String[] providerIds;
    private boolean applyTemplate = true;
    private static final String DEFAULT_CHECK_MSG = Messages.TemplatesWizardPage_applyTemplate_button;
    private String selectTemplateMessage = DEFAULT_CHECK_MSG;
    private Button selectTemplateCheck;
    private TreeWithImagePreviewComposite preview;

    /**
     * Fragment wizard page. This will display a list of fragments from the
     * given provider ids.
     * <p>
     * Use {@link #setSelectTemplateCheck(boolean)} to change the default check
     * state of the select fragment check box - by default set to checked.<br/>
     * Use {@link #setSelectTemplateMessage(String)} to change the check box
     * message.
     * </p>
     * 
     * @param pageName
     *            wizard page name
     * @param title
     *            page title
     * @param titleImage
     *            title image, <code>null</code> if no image is required
     * @param providerIds
     *            the fragments supplied by this providers will be included in
     *            the selection
     */
    public TemplatesWizardPage(String pageName, String title,
            ImageDescriptor titleImage, String[] providerIds) {
        super(pageName, title, titleImage);
        Assert.isTrue(providerIds != null && providerIds.length > 0,
                "Fragment provider ids"); //$NON-NLS-1$
        this.providerIds = providerIds;
    }

    /**
     * Check if user selected to apply the template.
     * 
     * @return <code>true</code> if a template should be applied.
     */
    public boolean applyTemplate() {
        return applyTemplate;
    }

    /**
     * Get the selected template to apply.
     * 
     * @return fragment.
     */
    public IFragment getTemplate() {
        return fragment;
    }

    /**
     * Set the default state of the select template checkbox.
     * 
     * @param check
     *            <code>true</code> if it should be checked by default,
     *            <code>false</code> otherwise.
     */
    public void setSelectTemplateCheck(boolean check) {
        applyTemplate = check;
    }

    /**
     * Replace the default select template checkbox text.
     * 
     * @param text
     *            checkbox text, <code>null</code> to use the default message
     */
    public void setSelectTemplateMessage(String text) {
        selectTemplateMessage = text != null ? text : DEFAULT_CHECK_MSG;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        root.setLayout(layout);

        // Create the header section
        Composite section = createHeaderSection(root);
        if (section != null) {
            section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
        }

        // Create the main fragment section
        section = createFragmentSection(root);
        if (section != null) {
            GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
            layoutData.widthHint = 700;
            section.setLayoutData(layoutData);
        }

        // Create the footer section
        section = createFooterSection(root);
        if (section != null) {
            section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                    false));
        }

        setPageComplete(isPageValid());
        setControl(root);
    }

    /**
     * Create the header section of this page. The default implementation adds a
     * checkbox to select whether a fragment should be selected. Subclasses can
     * override to change/add to the header section. (Composite returned by the
     * default implementatation uses {@link GridLayout}).
     * 
     * @param parent
     *            parent <code>Composite</code>
     * @return header <code>Composite</code>.
     */
    protected Composite createHeaderSection(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        root.setLayout(new GridLayout());

        selectTemplateCheck = new Button(root, SWT.CHECK);
        selectTemplateCheck.setSelection(applyTemplate);
        selectTemplateCheck.setText(selectTemplateMessage);
        selectTemplateCheck.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                applyTemplate = selectTemplateCheck.getSelection();

                if (preview != null && !preview.isDisposed()) {
                    preview.setEnabled(applyTemplate);
                }

                setPageComplete(isPageValid());

            }
        });

        return root;
    }

    /**
     * Create the main fragment selection section. This displays the sash
     * section with the tree view and the fragment preview.
     * 
     * @param parent
     *            parent <code>Composite</code>
     * @return main <code>Composite</code>
     */
    protected Composite createFragmentSection(Composite parent) {
        preview = new TreeWithImagePreviewComposite(parent, providerIds,
                SWT.BORDER);
        preview.addSelectionChangedListener(new ISelectionChangedListener() {

            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event
                        .getSelection();

                if (selection != null
                        && selection.getFirstElement() instanceof IFragment) {
                    fragment = (IFragment) selection.getFirstElement();
                } else {
                    fragment = null;
                }
                setPageComplete(isPageValid());
            }

        });

        preview.setEnabled(applyTemplate());

        return preview;
    }

    /**
     * Create footer section of this page. The default implementation returns
     * <code>null</code>. Subclasses can override this to add a footer section.
     * 
     * @param parent
     *            parent <code>Composite</code>
     * @return footer <code>Composite</code>, <code>null</code> if none
     *         required.
     */
    protected Composite createFooterSection(Composite parent) {
        return null;
    }

    /**
     * Check if the page is valid.
     * 
     * @return <code>true</code> if page is valid and can continue,
     *         <code>false</code> otherwise.
     */
    protected boolean isPageValid() {
        return !selectTemplateCheck.getSelection()
                || (selectTemplateCheck.getSelection() && fragment != null);
    }
}
