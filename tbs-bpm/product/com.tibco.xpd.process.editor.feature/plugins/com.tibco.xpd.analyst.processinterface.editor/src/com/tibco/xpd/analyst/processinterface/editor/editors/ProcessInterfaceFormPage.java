/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.analyst.processinterface.editor.editors;

import java.util.Collections;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.analyst.processinterface.editor.ProcessInterfaceEditorConstants;
import com.tibco.xpd.analyst.processinterface.editor.ProcessInterfaceEditorPlugin;
import com.tibco.xpd.analyst.processinterface.editor.inputs.ProcessInterfaceEditorInput;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.properties.XpdPropertiesFormToolkit;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Form page added to a form editor. Contains an AbstractEObjectSection which
 * assists in managing controls on the form.
 * 
 * @author rsomayaj
 * 
 * 
 */
public class ProcessInterfaceFormPage extends FormPage {

    private ProcessInterfaceEObjectSection interfaceEObjectSection;

    private ProcessInterface processInterface;

    private FormToolkit formToolKit;

    private XpdFormToolkit xpdFormToolkit;

    private Form form;

    public ProcessInterfaceFormPage(FormEditor editor, String id, String title) {
        super(editor, id, title);
    }

    @Override
    protected void createFormContent(IManagedForm managedForm) {
        ScrolledForm scrolledForm = managedForm.getForm();
        formToolKit = new FormToolkit(scrolledForm.getDisplay());
        GridLayout gridLayout = new GridLayout();
        scrolledForm.getBody().setLayout(gridLayout);

        form = formToolKit.createForm(scrolledForm.getBody());
        form.setText(getEditor().getPartName());

        processInterface =
                ((ProcessInterfaceEditorInput) getEditorInput())
                        .getProcessInterface();

        if (Xpdl2ModelUtil.isServiceProcessInterface(processInterface)) {

            form.setImage(ProcessInterfaceEditorPlugin
                    .getDefault()
                    .getImageRegistry()
                    .get(ProcessInterfaceEditorConstants.IMG_SERVICE_PROCESS_INTERFACE));
        } else {

            form.setImage(ProcessInterfaceEditorPlugin.getDefault()
                    .getImageRegistry()
                    .get(ProcessInterfaceEditorConstants.IMG_PROCESS_INTERFACE));
        }

        form.setLayoutData(new GridData(GridData.FILL_BOTH));
        gridLayout = new GridLayout();
        form.getBody().setLayout(new GridLayout());
        formToolKit.decorateFormHeading(form);
        xpdFormToolkit =
                new XpdPropertiesFormToolkit(
                        new TabbedPropertySheetWidgetFactory());
        interfaceEObjectSection =
                new ProcessInterfaceEObjectSection(getEditor());
        interfaceEObjectSection.setInput(Collections
                .singleton(processInterface));
        interfaceEObjectSection.createControls(form.getBody(), xpdFormToolkit);
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
