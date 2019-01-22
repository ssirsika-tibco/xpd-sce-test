/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.editor;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.wc.AbstractWorkingCopy;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.XpdPropertiesFormToolkit;

/**
 * Form page for Global Signal Definition file.
 * 
 * @author sajain
 * @since Feb 10, 2015
 */
public class GsdFormPage extends FormPage {

    /**
     * GSD file section instance.
     */
    private GsdEObjectSection gsdEObjectSection;

    /**
     * GSD's root element.
     */
    private GlobalSignalDefinitions gsds;

    /**
     * Form toolkit.
     */
    private FormToolkit formToolKit;

    /**
     * Xpd form toolkit.
     */
    private XpdFormToolkit xpdFormToolkit;

    /**
     * Form instance.
     */
    private Form form;

    /**
     * Form page for Global Signal Definition file.
     * 
     * @param editor
     * @param id
     * @param title
     */
    public GsdFormPage(FormEditor editor, String id,
            String title) {
        super(editor, id, title);
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {

        /*
         * Design scrolled form.
         */
        ScrolledForm scrolledForm = managedForm.getForm();
        formToolKit = new FormToolkit(scrolledForm.getDisplay());
        GridLayout gridLayout = new GridLayout();
        scrolledForm.getBody().setLayout(gridLayout);

        /*
         * Create a form out of the scrolled form.
         */
        form = formToolKit.createForm(scrolledForm.getBody());
        form.setText(getEditor().getPartName());
        form.setImage(GsdResourcePlugin.getDefault()
                .getImageRegistry().get("icons/obj16/gsdFile.png"));

        /*
         * Get gsd file.
         */
        IFile file = ((IFileEditorInput) getEditorInput()).getFile();

        /*
         * Get it's working copy.
         */
        WorkingCopy workingCopyObj =
                XpdResourcesPlugin.getDefault().getWorkingCopy(file);

        if (workingCopyObj instanceof AbstractWorkingCopy) {

            WorkingCopy workingCopy = workingCopyObj;

            /*
             * Check if working copy's root element is correct and then only
             * proceed.
             */
            if (workingCopy != null
                    && workingCopy.getRootElement() instanceof GlobalSignalDefinitions) {

                /*
                 * Fetch the root element.
                 */
                gsds = (GlobalSignalDefinitions) workingCopy.getRootElement();

                /*
                 * Set layout and layout data on the form.
                 */
                form.setLayoutData(new GridData(GridData.FILL_BOTH));
                gridLayout = new GridLayout();
                form.getBody().setLayout(new GridLayout());
                formToolKit.decorateFormHeading(form);

                xpdFormToolkit =
                        new XpdPropertiesFormToolkit(
                                new TabbedPropertySheetWidgetFactory());

                /*
                 * Create the gsd EObject section on the editor.
                 */
                gsdEObjectSection =
                        new GsdEObjectSection(getEditor());

                gsdEObjectSection.setInput(Collections.singleton(gsds));

                gsdEObjectSection
                        .createControls(form.getBody(), xpdFormToolkit);
            }
        }

    }

    /**
     * @return the form
     */
    public Form getForm() {
        return form;
    }

    @Override
    public void dispose() {
        formToolKit.dispose();
        xpdFormToolkit.dispose();
        super.dispose();
    }

    /**
     * @see org.eclipse.ui.forms.editor.FormPage#setFocus()
     * 
     */
    @Override
    public void setFocus() {
        if (form != null) {
            form.setFocus();
        }
    }
}