/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFOperationCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.contentassist.ContentAssistantHelper;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.jface.contentassist.IContentAssistSubjectControl;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.ui.providers.OperationItemProvider.OperationHintIDs;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.properties.CommandProvider;
import com.tibco.xpd.ui.properties.CommandTextFieldHandler;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * BOM property section for {@link Operation}s.
 * 
 * @author njpatel
 */
@SuppressWarnings("deprecation")
public class OperationSection extends AbstractGeneralSection {

    private Text multiplicity;

    private IParser multiplicityParser;

    private ProcessorWrapper multiplicityDelegate;

    private TypePickerControl typeCtrl;

    private Label multiplicityLabel;

    private OperationArgumentsTable argTable;

    private String currentValue;

    /**
     * {@link Operation} table section.
     */
    public OperationSection() {
        super();
        setShouldUseExtraSpace(true);
    }

    @Override
    protected boolean shouldDisplay(EObject eo) {
        return eo instanceof Operation;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root, toolkit, Messages.OperationSection_returnType_label);
        typeCtrl = new TypePickerControl(root, toolkit, getEditingDomain());
        typeCtrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        multiplicityLabel =
                createLabel(root,
                        toolkit,
                        Messages.OperationSection_multiplicity_label);
        multiplicity = toolkit.createText(root, ""); //$NON-NLS-1$
        setLayoutData(multiplicity);
        multiplicityDelegate = new ProcessorWrapper();
        manageControl(multiplicity, OperationHintIDs.MULTIPLICITY);
        ContentAssistantHelper.createTextContentAssistant(multiplicity,
                multiplicityDelegate);

        Label lbl =
                createLabel(root,
                        toolkit,
                        Messages.OperationSection_arguments_label);
        GridData gData = new GridData(SWT.LEFT, SWT.TOP, false, false);
        gData.widthHint = STANDARD_LABEL_WIDTH;
        lbl.setLayoutData(gData);
        argTable =
                new OperationArgumentsTable(root, toolkit, getEditingDomain());
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 40;
        argTable.setLayoutData(data);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void doRefresh() {
        EObject input = getInput();
        currentValue = ""; //$NON-NLS-1$
        if (typeCtrl != null && !typeCtrl.isDisposed()) {
            Type type = null;
            if (input instanceof Operation) {
                Parameter parameter = getReturnParameter((Operation) input);

                if (parameter != null) {
                    ParserService ps = ParserService.getInstance();
                    multiplicityParser =
                            ps.getParser(OperationHintIDs.MULTIPLICITY
                                    .hint(parameter));
                    multiplicityDelegate
                            .setDelegate((ISubjectControlContentAssistProcessor) multiplicityParser
                                    .getCompletionProcessor(OperationHintIDs.MULTIPLICITY
                                            .hint(parameter)));

                    currentValue =
                            multiplicityParser
                                    .getEditString(new EObjectAdapter(parameter),
                                            0);
                    updateText(multiplicity, currentValue);
                    enableMultiplicity(true);
                } else {
                    enableMultiplicity(false);
                }

                type = ((Operation) input).getType();

                // Update the arguments
                if (argTable != null && argTable.getViewer() != null) {
                    argTable.getViewer().setInput(input);
                }
            }
            typeCtrl.setValue(type);
        }
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        EObject input = getInput();

        Parameter param = null;
        if (input instanceof Operation) {
            param = getReturnParameter((Operation) input);
        }
        if (param != null) {
            ParserService ps = ParserService.getInstance();
            // Get this multiplicity for this param
            multiplicityParser =
                    ps.getParser(OperationHintIDs.MULTIPLICITY.hint(param));
            multiplicityDelegate
                    .setDelegate((ISubjectControlContentAssistProcessor) multiplicityParser
                            .getCompletionProcessor(OperationHintIDs.MULTIPLICITY
                                    .hint(param)));
        } else {
            multiplicityParser = null;
            multiplicityDelegate.setDelegate(null);
        }
    }

    /**
     * @param multiplicity
     * @param parser
     */
    private void manageControl(Text text, OperationHintIDs id) {
        new ParserTextFieldHandler(text, id);
    }

    /**
     * Get the return parameter of the operation.
     * 
     * @param operation
     * @return
     */
    private Parameter getReturnParameter(Operation operation) {
        EList<Parameter> ownedParams = operation.getOwnedParameters();

        // Look for the return type
        for (Parameter parameter : ownedParams) {
            if (parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
                return parameter;
            }
        }
        return null;
    }

    /**
     * Enable/disable the multiplicity option.
     * 
     * @param enabled
     */
    private void enableMultiplicity(boolean enabled) {
        if (multiplicity != null && !multiplicity.isDisposed()) {
            multiplicityLabel.setEnabled(enabled);
            multiplicity.setEnabled(enabled);
            if (!enabled) {
                updateText(multiplicity, null);
            }
        }
    }

    private class ParserTextFieldHandler extends CommandTextFieldHandler {
        public ParserTextFieldHandler(Text text, OperationHintIDs id) {
            super(text, new ParserCommandProvider(id));
            setModifyOnDeactivateOnly(true);
        }
    }

    private class ParserCommandProvider implements CommandProvider {

        private final OperationHintIDs id;

        public ParserCommandProvider(OperationHintIDs id) {
            this.id = id;
        }

        @Override
        public Command getCommand(Object obj) {
            Text text = (Text) obj;

            ParserService ps = ParserService.getInstance();

            EObject input = getInput();

            if (input instanceof Operation) {
                Operation op = (Operation) input;

                Parameter param = getReturnParameter(op);

                if (param != null) {
                    IParser parser = ps.getParser(id.hint(param));

                    ICommand cmd =
                            parser.getParseCommand(new EObjectAdapter(param),
                                    text.getText(),
                                    0);
                    if (cmd.canExecute()) {
                        return new EMFOperationCommand(
                                (TransactionalEditingDomain) getEditingDomain(),
                                cmd);
                    } else {
                        if (currentValue != null) {
                            // Reset the old value
                            updateText(multiplicity, currentValue);
                        }
                    }

                }
            }

            return null;

        }

        @Override
        public void refresh() {
            // String value = parser.getEditString(new
            // EObjectAdapter(getInput()), 0);

        }

        @Override
        public EditingDomain getEditingDomain() {
            return XpdResourcesPlugin.getDefault().getEditingDomain();
        }
    }

    /**
     * Type picker control.
     * 
     * @author njpatel
     * 
     */
    private class TypePickerControl extends BOMPickerControl<Type> {

        private Type value;

        public TypePickerControl(Composite parent, XpdFormToolkit toolkit,
                EditingDomain editingDomain) {
            super(parent, SWT.NONE, toolkit, editingDomain);
            setToolTipText(Messages.OperationSection_selectType_button_tooltip);
            setClearTooltip(Messages.OperationSection_clearType_button_tooltip);
            setLabelProvider(new PickerControlLabelProvider());
            setHyperlinkActive(true);
        }

        @Override
        protected Type doBrowse(Control control) {
            EObject input = getInput();
            if (input instanceof EObject) {
                return BomUIUtil.getTypeFromPicker(control.getShell(), input);
            }
            return null;
        }

        @Override
        public void setValue(Type value) {
            this.value = value;
            // If the value is a proxy then set to null
            super.setValue(value != null && value.eIsProxy() ? null : value);

            /*
             * If this value is from a resource in the workspace then enable the
             * hyperlink, otherwise don't as user won't be able to 'go to' the
             * resource
             */
            setHyperlinkActive(value != null && !value.eIsProxy()
                    && value.eResource() != null
                    && value.eResource().getURI().isPlatformResource());
        }

        @Override
        protected String getClearText() {
            if (value != null && value.eIsProxy()) {
                return UNRESOLVED_REFERENCE;
            }
            return super.getClearText();
        }

        @Override
        protected Command getClearValueCommand(EditingDomain editingDomain,
                Type value) {
            Command cmd = null;
            EObject input = getInput();
            if (input instanceof Operation) {
                Parameter param = getReturnParameter((Operation) input);

                if (param != null) {
                    cmd =
                            RemoveCommand
                                    .create(editingDomain,
                                            input,
                                            UMLPackage.eINSTANCE
                                                    .getBehavioralFeature_OwnedParameter(),
                                            param);
                }
            }
            return cmd;
        }

        @Override
        protected Command getSetValueCommand(EditingDomain editingDomain,
                Type value) {
            Command cmd = null;
            EObject input = getInput();
            if (input instanceof Operation) {
                cmd =
                        SetCommand.create(editingDomain,
                                input,
                                UMLPackage.eINSTANCE.getOperation_Type(),
                                value);
            }
            return cmd;
        }
    }

    /**
     * Required because of inconsistency between live-cycle of the controls and
     * the input. The controls can be reused across many inputs, while each
     * input can have its own parser.
     * 
     * @author wzurek
     * 
     */
    private class ProcessorWrapper implements
            ISubjectControlContentAssistProcessor {

        private ISubjectControlContentAssistProcessor processor;

        public void setDelegate(ISubjectControlContentAssistProcessor processor) {
            this.processor = processor;
        }

        @Override
        public ICompletionProposal[] computeCompletionProposals(
                ITextViewer viewer, int offset) {
            if (processor == null) {
                return new ICompletionProposal[0];
            }
            return processor.computeCompletionProposals(viewer, offset);
        }

        @Override
        public IContextInformation[] computeContextInformation(
                ITextViewer viewer, int offset) {
            if (processor == null) {
                return new IContextInformation[0];
            }
            return processor.computeContextInformation(viewer, offset);
        }

        @Override
        public char[] getCompletionProposalAutoActivationCharacters() {
            if (processor == null) {
                return new char[0];
            }
            return processor.getCompletionProposalAutoActivationCharacters();
        }

        @Override
        public char[] getContextInformationAutoActivationCharacters() {
            if (processor == null) {
                return new char[0];
            }
            return processor.getContextInformationAutoActivationCharacters();
        }

        @Override
        public IContextInformationValidator getContextInformationValidator() {
            if (processor == null) {
                return null;
            }
            return processor.getContextInformationValidator();
        }

        @Override
        public String getErrorMessage() {
            if (processor == null) {
                return ""; //$NON-NLS-1$
            }
            return processor.getErrorMessage();
        }

        @Override
        public ICompletionProposal[] computeCompletionProposals(
                IContentAssistSubjectControl contentAssistSubjectControl,
                int documentOffset) {
            if (processor == null) {
                return new ICompletionProposal[0];
            }
            return processor
                    .computeCompletionProposals(contentAssistSubjectControl,
                            documentOffset);
        }

        @Override
        public IContextInformation[] computeContextInformation(
                IContentAssistSubjectControl contentAssistSubjectControl,
                int documentOffset) {
            if (processor == null) {
                return new IContextInformation[0];
            }
            return processor
                    .computeContextInformation(contentAssistSubjectControl,
                            documentOffset);
        }
    }
}
