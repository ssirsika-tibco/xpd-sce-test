/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.pages;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.xpd.fragments.IFragmentElement;
import com.tibco.xpd.fragments.internal.FragmentsViewPart;
import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.thumbnail.FragmentThumbnailList;

/**
 * Details page of the fragments master details block. This is the thumbnails
 * section in the fragments view.
 * 
 * @author njpatel
 * 
 */
public class FragmentElementPage implements IDetailsPage {

    private FormToolkit toolkit;
    private Section section;
    private FragmentThumbnailList thumbnailList;
    // Current selection
    private IFragmentElement input;

    private Color descColor;
    private final Color grey;
    private final FragmentsViewPart viewPart;
    private final MenuManager menuManager;

    /**
     * Thumbnails section in the fragment view.
     * 
     * @param viewPart
     */
    public FragmentElementPage(FragmentsViewPart viewPart) {
        this.viewPart = viewPart;
        grey = new Color(viewPart.getSite().getShell().getDisplay(), 185, 185,
                185);
        menuManager = FragmentsViewPage.createMenuManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.IDetailsPage#createContents(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createContents(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        parent.setLayout(layout);
        section = toolkit.createSection(parent, Section.DESCRIPTION);
        section.setLayout(new FillLayout());
        section.setText(Messages.FragmentElementPage_section_title);
        section.marginWidth = 10;
        section.marginHeight = 5;
        toolkit.createCompositeSeparator(section);
        section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Composite client = createClientContent(toolkit, section);

        if (client != null) {
            section.setClient(client);

        }
    }

    /**
     * Create the client content.
     * 
     * @param toolkit
     *            form toolkit
     * @param parent
     *            parent composite
     * @return client composite.
     */
    protected Composite createClientContent(FormToolkit toolkit,
            Composite parent) {
        thumbnailList = new FragmentThumbnailList(parent, SWT.NONE, viewPart);

        if (menuManager != null) {
            Menu menu = menuManager.createContextMenu(parent);
            parent.setMenu(menu);
            viewPart.getSite().registerContextMenu(menuManager, thumbnailList);
        }

        toolkit.adapt(thumbnailList);

        viewPart.addSelectionProvider(thumbnailList);

        parent.addControlListener(new ControlAdapter() {
            @Override
            public void controlResized(ControlEvent e) {
                if (thumbnailList != null && !thumbnailList.isDisposed()) {
                    thumbnailList.layout();
                }
            }
        });

        return thumbnailList;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.IFormPart#commit(boolean)
     */
    public void commit(boolean onSave) {
        // Nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.IFormPart#dispose()
     */
    public void dispose() {
        if (thumbnailList != null) {
            viewPart.removeSelectionProvider(thumbnailList);
            thumbnailList.dispose();
            thumbnailList = null;
        }

        if (menuManager != null) {
            menuManager.dispose();
        }

        grey.dispose();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.IFormPart#initialize(org.eclipse.ui.forms.IManagedForm
     * )
     */
    public void initialize(IManagedForm form) {
        toolkit = form.getToolkit();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.IFormPart#isDirty()
     */
    public boolean isDirty() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.IFormPart#isStale()
     */
    public boolean isStale() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.IFormPart#refresh()
     */
    public void refresh() {
        // Force update
        setInput(input);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.IFormPart#setFocus()
     */
    public void setFocus() {
        // Nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.forms.IFormPart#setFormInput(java.lang.Object)
     */
    public boolean setFormInput(Object input) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.forms.IPartSelectionListener#selectionChanged(org.eclipse
     * .ui.forms.IFormPart, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IFormPart part, ISelection selection) {
        if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
            Object element = ((IStructuredSelection) selection)
                    .getFirstElement();

            if (element instanceof IFragmentElement
                    && !element.equals(getInput())) {
                setInput((IFragmentElement) element);
            }
        }
    }

    /**
     * Set the current selection of this page.
     * 
     * @param input
     */
    protected void setInput(IFragmentElement input) {
        this.input = input;

        if (section != null && !section.isDisposed()) {
            if (descColor == null) {
                descColor = section.getDescriptionControl().getForeground();
            }

            // Need to escape ampersands as the text is set in a label and
            // single '&' denote a mnemonic
            section.setText(escapeName(input.getNameLabel()));
            String desc = input.getDescriptionLabel();
            if (desc != null && desc.length() > 0) {
                section.getDescriptionControl().setForeground(descColor);
                section.setDescription(desc);
            } else {
                section.getDescriptionControl().setForeground(grey);
                section
                        .setDescription(Messages.FragmentElementPage_noDescription_label);
            }

            section.layout();
        }
        if (thumbnailList != null && !thumbnailList.isDisposed()) {
            thumbnailList.setDataContent(input);
        }
    }

    /**
     * Escape the name before it gets set as the section title. '&' characters
     * denote mnemonics as the text will be set in a label so any '&' in the
     * name should be replaced with double '&'.
     * 
     * @param name
     *            name to escape
     * @return escaped name
     */
    private String escapeName(String name) {

        if (name != null) {
            name = name.replaceAll("&", "&&"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        return name != null ? name : ""; //$NON-NLS-1$
    }

    /**
     * Current selection of this page.
     * 
     * @return
     */
    protected IFragmentElement getInput() {
        return input;
    }
}
