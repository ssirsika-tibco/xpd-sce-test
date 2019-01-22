/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.transform.properties;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

import com.tibco.xpd.implementer.script.Xpdl2WsdlUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.sourceviewer.client.CommandProvider;
import com.tibco.xpd.script.sourceviewer.client.ISiteProvider;
import com.tibco.xpd.script.sourceviewer.client.ScriptEditor;
import com.tibco.xpd.script.sourceviewer.viewer.ScriptSourceViewer;
import com.tibco.xpd.script.transform.Activator;
import com.tibco.xpd.script.transform.document.TransformDirection;
import com.tibco.xpd.script.transform.internal.Messages;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.script.ui.internal.JScriptResourceMarkerAnnotationModel;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Factory;

/**
 * @author mtorres
 */
public class XSLTTransformEditorSection extends AbstractScriptEditorSection {

    public static final String SCRIPT_GRAMMAR = "XSLT_Transform"; //$NON-NLS-1$

    private Button xslt;

    private Button url;

    private PageBook book;

    private Composite urlComposite;

    private Text urlEditor;

    private ScriptEditor scriptViewer;

    private ExpandableComposite inputDomComposite;

    private DomSection inputDomSection;

    private ExpandableComposite outputDomComposite;

    private DomSection outputDomSection;

    /**
     * @param class1
     */
    public XSLTTransformEditorSection(EClass class1) {
        super(class1);
    }

    /**
     * @param class1
     */
    public XSLTTransformEditorSection() {
        super(null);
        inputDomSection = new DomSection(TransformDirection.INPUT);
        outputDomSection = new DomSection(TransformDirection.OUTPUT);
    }

    /**
     * @param parent
     * @param toolkit
     * @return
     * @see com.tibco.xpd.script.ui.api.general.AbstractScriptEditorSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite control = toolkit.createComposite(parent);
        toolkit.paintBordersFor(control);

        GridLayout layout = new GridLayout();
        layout.numColumns = 4;
        control.setLayout(layout);

        GridData data;

        xslt = toolkit.createButton(control, null, SWT.RADIO, "staticXslt"); //$NON-NLS-1$
        data = new GridData(SWT.FILL, SWT.FILL, false, false);
        xslt.setLayoutData(data);

        Label xsltLabel =
                toolkit.createLabel(control,
                        Messages.XSLTTransformEditorSection_Edit_xslt);
        data = new GridData(SWT.LEAD, SWT.FILL, true, false);
        xsltLabel.setLayoutData(data);

        url = toolkit.createButton(control, null, SWT.RADIO, "dynamicXslt"); //$NON-NLS-1$
        data = new GridData(SWT.FILL, SWT.FILL, false, false);
        url.setLayoutData(data);

        Label urlLabel =
                toolkit.createLabel(control,
                        Messages.XSLTTransformEditorSection_Import_XsltFromURL);
        data = new GridData(SWT.LEAD, SWT.FILL, true, false);
        urlLabel.setLayoutData(data);

        book = new PageBook(control, SWT.NONE);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.horizontalSpan = 4;
        book.setLayoutData(data);

        int styles = SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL;
        scriptViewer = new ScriptSourceViewer(book, styles, SCRIPT_GRAMMAR);
        scriptViewer.installSiteProvider(new SiteProvider());
        scriptViewer.configure();
        scriptViewer.getControl()
                .setLayoutData(new GridData(GridData.FILL_BOTH));
        scriptViewer.getControl().setData(FormToolkit.KEY_DRAW_BORDER,
                FormToolkit.TREE_BORDER);
        scriptViewer.installCommandProvider(new SaveCommandProvider());

        urlComposite = toolkit.createComposite(book);
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        urlComposite.setLayoutData(data);
        GridLayout layout2 = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        urlComposite.setLayout(layout2);
        urlEditor =
                toolkit.createText(urlComposite,
                        "", SWT.BORDER | SWT.FLAT, "xsltEditor"); //$NON-NLS-1$ //$NON-NLS-2$
        data = new GridData(SWT.FILL, SWT.TOP, true, false);
        urlEditor.setLayoutData(data);

        doCreateDomControls(control, toolkit);

        manageControl(xslt);
        manageControl(url);
        manageControl(urlEditor);

        return control;
    }

    private void doCreateDomControls(final Composite container,
            XpdFormToolkit toolkit) {

        Composite domsContainer = toolkit.createComposite(container);

        GridLayout layout = new GridLayout(2, true);
        layout.marginLeft = 0;
        layout.marginRight = 0;
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        domsContainer.setLayout(layout);

        GridData gData = new GridData(SWT.FILL, SWT.FILL, true, false);
        gData.horizontalSpan = 4;
        domsContainer.setLayoutData(gData);
        inputDomComposite =
                toolkit.createSection(domsContainer,
                        ExpandableComposite.TWISTIE
                                | ExpandableComposite.TITLE_BAR);
        inputDomComposite.setText(Messages.XSLTTransformEditorSection_InputDom);
        inputDomComposite.marginWidth = 0;
        inputDomComposite.setLayout(new GridLayout());
        inputDomComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
                false));
        inputDomComposite.addExpansionListener(new IExpansionListener() {
            public void expansionStateChanged(ExpansionEvent e) {
                container.layout();
                if (e.getState()) {
                    if (inputDomSection != null) {
                        inputDomSection.refresh();
                    }
                }
            }

            public void expansionStateChanging(ExpansionEvent e) {

            }
        });

        if (inputDomSection != null) {
            inputDomSection.createControls(inputDomComposite,
                    getPropertySheetPage());
            inputDomComposite.setClient(inputDomSection.getControl());
        }

        outputDomComposite =
                toolkit.createSection(domsContainer,
                        ExpandableComposite.TWISTIE
                                | ExpandableComposite.TITLE_BAR);
        outputDomComposite
                .setText(Messages.XSLTTransformEditorSection_OutputDom);
        outputDomComposite.marginWidth = 0;
        outputDomComposite.setLayout(new GridLayout());
        outputDomComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        outputDomComposite.addExpansionListener(new IExpansionListener() {
            public void expansionStateChanged(ExpansionEvent e) {
                container.layout();
                if (e.getState()) {
                    if (outputDomSection != null) {
                        outputDomSection.refresh();
                    }
                }
            }

            public void expansionStateChanging(ExpansionEvent e) {

            }
        });

        if (outputDomSection != null) {
            outputDomSection.createControls(outputDomComposite,
                    getPropertySheetPage());
            outputDomComposite.setClient(outputDomSection.getControl());
        }

    }

    /**
     * @param obj
     * @return
     * @see com.tibco.xpd.script.ui.api.general.AbstractScriptEditorSection#doGetCommand(java.lang.Object)
     */
    @Override
    public Command doGetCommand(Object obj) {
        CompoundCommand cmd = new CompoundCommand();
        if (obj == xslt || obj == url) {
            if (obj == xslt) {
                cmd.setLabel(Messages.XSLTTransformEditorSection_SetXsltEdit);
            } else {
                cmd.setLabel(Messages.XSLTTransformEditorSection_SetXsltImport);
            }
            EObject input = getInput();
            if (input instanceof ScriptInformation) {
                ScriptInformation information = (ScriptInformation) input;
                EditingDomain ed =
                        WorkingCopyUtil.getEditingDomain(information);
                Boolean isReference = new Boolean(url.getSelection());
                cmd.append(SetCommand.create(ed,
                        information,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptInformation_Reference(),
                        isReference));
                Expression expression =
                        Xpdl2Factory.eINSTANCE.createExpression();
                expression.setScriptGrammar(SCRIPT_GRAMMAR);
                cmd.append(SetCommand.create(ed,
                        information,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptInformation_Expression(),
                        expression));
            }
        } else {
            cmd.setLabel(Messages.XSLTTransformEditorSection_EditScript);
            EObject input = getInput();
            if (input instanceof ScriptInformation) {
                ScriptInformation information = (ScriptInformation) input;
                EditingDomain ed =
                        WorkingCopyUtil.getEditingDomain(information);
                Expression expression =
                        Xpdl2Factory.eINSTANCE.createExpression(urlEditor
                                .getText());
                expression.setScriptGrammar(SCRIPT_GRAMMAR);
                cmd.append(SetCommand.create(ed,
                        information,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptInformation_Expression(),
                        expression));
            }
        }
        return cmd;
    }

    /**
     * 
     * @see com.tibco.xpd.script.ui.api.general.AbstractScriptEditorSection#doRefresh()
     */
    @Override
    public void doRefresh() {
        EObject input = getInput();
        String text = null;
        boolean isReference = false;
        if (input instanceof ScriptInformation) {
            ScriptInformation information = (ScriptInformation) input;
            text = Xpdl2WsdlUtil.getWebServiceTaskScript(information);
            isReference = information.isReference();
        }
        if (text == null) {
            text = ""; //$NON-NLS-1$
        }
        if (isReference) {
            book.showPage(urlComposite);
            if (!text.equals(urlEditor.getText())) {
                updateText(urlEditor, text);
            }
            url.setSelection(true);
            xslt.setSelection(false);
        } else {
            book.showPage(scriptViewer.getControl());
            ResourceMarkerAnnotationModel annotationModel = null;
            if (input instanceof ScriptInformation) {
                IFile file = WorkingCopyUtil.getFile(input);
                String scriptContainerId = ((ScriptInformation) input).getId();
                String scriptContext = getScriptContext();
                if (file != null) {
                    annotationModel =
                            new JScriptResourceMarkerAnnotationModel(file,
                                    scriptContainerId, scriptContext);
                }
            }
            scriptViewer.installResourceMarkerAnnotationModel(annotationModel);
            scriptViewer.setInputString(text);
            scriptViewer.doRefresh();
            xslt.setSelection(true);
            url.setSelection(false);
        }
    }

    /**
     * @param editingDomain
     * @param object
     * @return
     * @see com.tibco.xpd.script.ui.api.general.AbstractScriptEditorSection#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject)
     */
    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject object) {
        Command cmd = new CompoundCommand();
        return cmd;
    }

    class SiteProvider implements ISiteProvider {

        public IWorkbenchPartSite getWorkbenchPartSite() {
            return getViewSite();
        }
    }

    class SaveCommandProvider implements CommandProvider {

        public void executeSaveCommand(IDocument document) {
            EObject eObject = getInput();
            if (eObject instanceof ScriptInformation) {
                ScriptInformation information = (ScriptInformation) eObject;
                String script = document.get();
                String current =
                        Xpdl2WsdlUtil.getWebServiceTaskScript(information);
                if (!script.equals(current)) {
                    if (information.eContainer() == null) {
                        Activity activity = information.getActivity();
                        if (activity != null) {
                            EditingDomain ed =
                                    WorkingCopyUtil.getEditingDomain(activity);
                            Command createTaskScriptCommand =
                                    Xpdl2WsdlUtil
                                            .getSetWebServiceTaskScriptCommand(ed,
                                                    script,
                                                    information,
                                                    SCRIPT_GRAMMAR);
                            if (createTaskScriptCommand != null
                                    && createTaskScriptCommand.canExecute()) {
                                ed.getCommandStack()
                                        .execute(createTaskScriptCommand);
                            }
                        }
                    } else {
                        EditingDomain ed =
                                WorkingCopyUtil.getEditingDomain(information);
                        Command setTaskScriptCommand =
                                Xpdl2WsdlUtil
                                        .getSetWebServiceTaskScriptCommand(ed,
                                                script,
                                                information,
                                                SCRIPT_GRAMMAR);
                        if (setTaskScriptCommand != null
                                && setTaskScriptCommand.canExecute()) {
                            ed.getCommandStack().execute(setTaskScriptCommand);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        if (inputDomSection != null) {
            inputDomSection.setInput(items);
        }
        if (outputDomSection != null) {
            outputDomSection.setInput(items);
        }
    }

    /**
     * @return
     * @see com.tibco.xpd.script.ui.api.com.tibco.xpd.script.ui.internal.AbstractScriptEditorSection#getScriptGrammar()
     */
    @Override
    protected String getScriptGrammar() {
        return SCRIPT_GRAMMAR;
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "developer.editor"; //$NON-NLS-1$
    }
}
