/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.internal.propertysection.general;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.modeler.custom.Activator;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.custom.internal.propertysection.AbstractGeneralSection;
import com.tibco.xpd.bom.resources.ui.providers.PropertyItemProvider.IDs;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * BOM property section for {@link MultiplicityElement}. This will allow the
 * setting of multiplicity.
 * 
 * @author njpatel
 * 
 */
@SuppressWarnings("deprecation")
public class MultiplicityElementSection extends AbstractGeneralSection {

    private Text multiplicity;

    private IParser multiplicityParser;

    private ProcessorWrapper multiplicityDelegate;

    private String currentValue;

    private String previousValue = ""; //$NON-NLS-1$

    @Override
    protected boolean shouldDisplay(EObject eo) {
        boolean isACaseId = eo instanceof Property
                && BOMGlobalDataUtils.isCID((Property) eo);
        // Hide for all caseIds...
        if (isACaseId) {
            // Don't display this section even if the caseId has not not a
            // multiplicity of 1.
            // This must be fixed by the validation quick-fix.

            // Property prop = (Property) eo;
            // boolean hasOneMultiplicity =
            // prop.getUpper() == 1 && prop.getLower() == 1;
            // // ...unless the multiplicity is not 1.
            // if (!hasOneMultiplicity) {
            // return true;
            // }
            return false;
        }
        return eo instanceof MultiplicityElement;
    }

    @Override
    protected Control doCreateControls(Composite parent,
            XpdFormToolkit toolkit) {
        Composite root = (Composite) super.doCreateControls(parent, toolkit);

        createLabel(root,
                toolkit,
                Messages.MultiplicityElementSection_multiplicity_label);
        multiplicity = toolkit.createText(root, ""); //$NON-NLS-1$
        setLayoutData(multiplicity);
        multiplicityDelegate = new ProcessorWrapper();

        manageControlUpdateOnDeactivate(multiplicity);

        ContentAssistantHelper.createTextContentAssistant(multiplicity,
                multiplicityDelegate);

        return root;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        ParserService ps = ParserService.getInstance();
        IParser parser = ps.getParser(IDs.MULTIPLICITY.hint(getInput()));

        final ICommand cmd =
                parser.getParseCommand(new EObjectAdapter(getInput()),
                        multiplicity.getText(),
                        0);

        if (cmd.canExecute()) {
            return new RecordingCommand(
                    (TransactionalEditingDomain) getEditingDomain()) {
                @Override
                protected void doExecute() {
                    IStatus status;
                    try {
                        status = cmd.execute(new NullProgressMonitor(), null);
                    } catch (ExecutionException e) {
                        throw new WrappedException(e);
                    }
                    if (status.getSeverity() >= IStatus.ERROR) {
                        if (status.getSeverity() == IStatus.ERROR) {
                            Activator.getDefault().getLogger().log(status);
                        }
                        throw new OperationCanceledException();
                    }
                }
            };
        } else {
            if (currentValue != null) {
                // Reset the old value
                updateText(multiplicity, currentValue);
            }
        }

        return null;
    }

    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);

        EObject input = getInput();
        if (input instanceof MultiplicityElement) {
            EObjectAdapter hint = new EObjectAdapter(getInput());
            ParserService ps = ParserService.getInstance();
            multiplicityParser = ps.getParser(IDs.MULTIPLICITY.hint(input));
            multiplicityDelegate.setDelegate(
                    (ISubjectControlContentAssistProcessor) multiplicityParser
                            .getCompletionProcessor(hint));
        } else {
            multiplicityParser = null;
            multiplicityDelegate.setDelegate(null);
        }
    }

    @Override
    protected void doRefresh() {
        currentValue = ""; //$NON-NLS-1$
        if (multiplicityParser != null) {
            EObjectAdapter hint = new EObjectAdapter(getInput());
            currentValue = multiplicityParser.getEditString(hint, 0);
            updateText(multiplicity, multiplicityParser.getEditString(hint, 0));
            Object input = getInput();
            if (input instanceof Property) {
                Property prop = (Property) input;

                boolean makeReadOnly = false;

                // Make read-only if already the correct multiplicity
                if (GlobalDataProfileManager.getInstance().isAutoCaseIdentifier(
                        prop) && ("0..1".compareTo(currentValue) == 0)) {
                    makeReadOnly = true;
                } else if (("1".compareTo(currentValue) == 0)
                        && (GlobalDataProfileManager.getInstance().isCID(prop)
                                || GlobalDataProfileManager.getInstance()
                                        .isCompositeCaseIdentifier(prop))) {
                    makeReadOnly = true;
                }

                if (makeReadOnly) {
                    // Multiplicity should not be editable for any type of
                    // Case Identifiers
                    if (multiplicity.isEnabled()) {
                        multiplicity.setEnabled(false);
                        multiplicity.getParent().layout(true);
                    }
                } else {
                    multiplicity.setEnabled(true);
                    multiplicity.getParent().layout(true);
                }
            }
        } else {
            multiplicity.setText(""); //$NON-NLS-1$
            multiplicity.setEditable(false);
        }

        // Need to support things like the fetching section
        // changing based off of the multiplicity changing
        if (previousValue.compareTo(currentValue) != 0) {
            previousValue = currentValue;

            /*
             * Delay refreshTabs() because we may be refresh during deactivation
             * of multiplicity control and on that deactivation (unset focus)
             * the command to update model is execyuted so we get a refresh
             * halfway through unset focus and refreshTabs throws old controls
             * away and then KAPPOOOWWWEEEE
             */
            if (Display.getCurrent() != null
                    && !Display.getCurrent().isDisposed()) {

                Display.getCurrent().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        if (multiplicity != null
                                && !multiplicity.isDisposed()) {
                            refreshTabs();
                        }
                    }
                });
            }
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
    private class ProcessorWrapper
            implements ISubjectControlContentAssistProcessor {

        private ISubjectControlContentAssistProcessor processor;

        public void setDelegate(
                ISubjectControlContentAssistProcessor processor) {
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
            return processor.computeCompletionProposals(
                    contentAssistSubjectControl,
                    documentOffset);
        }

        @Override
        public IContextInformation[] computeContextInformation(
                IContentAssistSubjectControl contentAssistSubjectControl,
                int documentOffset) {
            if (processor == null) {
                return new IContextInformation[0];
            }
            return processor.computeContextInformation(
                    contentAssistSubjectControl,
                    documentOffset);
        }
    }

}
