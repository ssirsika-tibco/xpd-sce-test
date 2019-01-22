/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.contentassist.IContentAssistSubjectControl;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.internal.Messages;

/**
 * @author wzurek
 * 
 */
@SuppressWarnings("deprecation")
public class MultiplicityParser implements ISemanticParser {
    private static final String STAR = "*"; //$NON-NLS-1$

    /**
     * @author wzurek
     * 
     */
    private final class FixedListContentAssistProcessor implements
            ISubjectControlContentAssistProcessor {

        private final String[] values;

        private final String[] displayValues;

        private final String[] additionalInfos;

        /**
         * The Constructor.
         */
        public FixedListContentAssistProcessor(String[] values,
                String[] displayValues, String[] additionalInfos) {
            this.values = values;
            this.displayValues = displayValues;
            this.additionalInfos = additionalInfos;
        }

        public ICompletionProposal[] computeCompletionProposals(
                ITextViewer viewer, int offset) {
            try {
                String prefix = viewer.getDocument().get(0, offset);
                return computeCompletionProposals(prefix, offset);
            } catch (BadLocationException e) {
                // ignored
            }
            return null;
        }

        public ICompletionProposal[] computeCompletionProposals(
                IContentAssistSubjectControl control, int offset) {
            try {
                String prefix = control.getDocument().get(0, offset);
                return computeCompletionProposals(prefix, offset);
            } catch (BadLocationException e) {
                // ignored
            }
            return null;
        }

        public ICompletionProposal[] computeCompletionProposals(String prefix,
                int size) {
            List<ICompletionProposal> result =
                    new ArrayList<ICompletionProposal>(values.length);
            for (int i = 0; i < values.length; i++) {
                if (values[i].startsWith(prefix)) {
                    CompletionProposal completionProposal =
                            new CompletionProposal(values[i], 0, size,
                                    values[i].length(), null,
                                    displayValues == null ? null
                                            : displayValues[i], null,
                                    additionalInfos == null ? null
                                            : additionalInfos[i]);
                    result.add(completionProposal);
                }
            }
            return result.toArray(new ICompletionProposal[result.size()]);
        }

        public IContextInformation[] computeContextInformation(
                ITextViewer viewer, int offset) {
            return null;
        }

        public char[] getCompletionProposalAutoActivationCharacters() {

            return new char[] { '1', '*', '0' };
        }

        public char[] getContextInformationAutoActivationCharacters() {
            return new char[] {};
        }

        public IContextInformationValidator getContextInformationValidator() {
            return null;
        }

        public String getErrorMessage() {
            return ""; //$NON-NLS-1$
        }

        public IContextInformation[] computeContextInformation(
                IContentAssistSubjectControl contentAssistSubjectControl,
                int documentOffset) {
            return computeContextInformation((ITextViewer) null, documentOffset);
        }
    }

    static private Pattern singleNumber = Pattern.compile("\\*|\\d+"); //$NON-NLS-1$

    static private Pattern twoNumbers = Pattern
            .compile("(\\d+)\\.\\.(\\*|\\d+)"); //$NON-NLS-1$

    /**
     * The Constructor
     * 
     * @param index
     *            index of the association end to display/edit
     */
    public MultiplicityParser() {
    }

    public String getEditString(IAdaptable element, int flags) {

        EObject eo = getEObject(element);

        if (eo instanceof Association) {

            Property prop = getProperty(element);

            if (prop == null
                    || (prop.getLowerValue() == null && prop.getUpperValue() == null)) {
                return ""; //$NON-NLS-1$
            }

            int lower = prop.getLower();
            int upper = prop.getUpper();

            return lower == 0 && upper < 0 ? STAR : lower == upper ? String
                    .valueOf(lower) : lower + ".." + (upper < 0 ? STAR : upper); //$NON-NLS-1$

        }

        if (eo instanceof Property) {
            Property prop = (Property) eo;

            if (prop == null
                    || (prop.getLowerValue() == null && prop.getUpperValue() == null)) {
                return ""; //$NON-NLS-1$
            }

            int lower = prop.getLower();
            int upper = prop.getUpper();

            return lower == 0 && upper < 0 ? STAR : lower == upper ? String
                    .valueOf(lower) : lower + ".." + (upper < 0 ? STAR : upper); //$NON-NLS-1$

        }

        if (eo instanceof Parameter) {
            Parameter param = (Parameter) eo;

            if (param == null
                    || (param.getLowerValue() == null && param.getUpperValue() == null)) {
                return ""; //$NON-NLS-1$
            }

            int lower = param.getLower();
            int upper = param.getUpper();

            return lower == 0 && upper < 0 ? STAR : lower == upper ? String
                    .valueOf(lower) : lower + ".." + (upper < 0 ? STAR : upper); //$NON-NLS-1$

        }

        return ""; //$NON-NLS-1$

    }

    protected Property getProperty(IAdaptable element) {
        return (Property) element.getAdapter(Property.class);
    }

    protected EObject getEObject(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {
        newString = newString.trim();

        EObject eo = getEObject(element);
        if (eo == null) {
            return UnexecutableCommand.INSTANCE;
        }

        String cmdMessage = ""; //$NON-NLS-1$

        if (eo instanceof Parameter) {
            cmdMessage = Messages.OperationMultiplicityParser_command_message;
        } else {
            cmdMessage = Messages.AssociationMultiplicityParser_command_message;
        }

        TransactionalEditingDomain ed = TransactionUtil.getEditingDomain(eo);
        if (ed == null) {
            return UnexecutableCommand.INSTANCE;
        }

        if (newString.length() == 0) {

            Command cmd1 =
                    SetCommand
                            .create(ed,
                                    eo,
                                    UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE,
                                    null);
            Command cmd2 =
                    SetCommand
                            .create(ed,
                                    eo,
                                    UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE,
                                    null);

            CompositeCommand result = new CompositeCommand(cmdMessage);
            result.add(new EMFCommandOperation(ed, cmd1));
            result.add(new EMFCommandOperation(ed, cmd2));
            return result;
        }

        int upper;
        int lower;
        try {
            Matcher matcher = singleNumber.matcher(newString);
            if (matcher.matches()) {
                if (STAR.equals(newString)) {
                    lower = 0;
                    upper = -1;
                } else {
                    lower = Integer.parseInt(newString);
                    upper = lower;
                }
            } else {
                matcher = twoNumbers.matcher(newString);
                if (matcher.matches()) {
                    lower = Integer.parseInt(matcher.group(1));

                    String up = matcher.group(2);
                    if (STAR.equals(up)) {
                        upper = -1;
                    } else {
                        upper = Integer.parseInt(up);
                    }
                } else {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {
            return UnexecutableCommand.INSTANCE;
        }

        Command cmd1 =
                SetCommand.create(ed,
                        eo,
                        UMLPackage.eINSTANCE.getMultiplicityElement_Lower(),
                        lower);
        Command cmd2 =
                SetCommand.create(ed,
                        eo,
                        UMLPackage.eINSTANCE.getMultiplicityElement_Upper(),
                        upper);

        CompositeCommand result = new CompositeCommand(cmdMessage);
        result.add(new EMFCommandOperation(ed, cmd1));
        result.add(new EMFCommandOperation(ed, cmd2));
        return result;
    }

    /**
     * 
     */
    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;
            EAttribute f1 =
                    UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE;
            EAttribute f2 = UMLPackage.Literals.LITERAL_INTEGER__VALUE;
            if (f1.equals(n.getFeature()) || f2.equals(n.getFeature())) {
                return true;
            }

            // Need to also check for changes to the Upper and Lower Values
            EReference f3 =
                    UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE;
            EReference f4 =
                    UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE;
            if (f3.equals(n.getFeature()) || f4.equals(n.getFeature())) {
                return true;
            }
        }
        return false;
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        String[] values = new String[] { "1", "0..1", STAR, "1.." + STAR }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        String[] displayValues =
                new String[] {
                        Messages.AssociationMultiplicityParser_one_message,
                        Messages.AssociationMultiplicityParser_zero_one_message,
                        Messages.AssociationMultiplicityParser_zero_more_message,
                        Messages.AssociationMultiplicityParser_one_more_message };
        return new FixedListContentAssistProcessor(values, displayValues, null);
    }

    public String getPrintString(IAdaptable element, int flags) {
        return getEditString(element, flags);
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        boolean valid = getParseCommand(element, editString, 0).canExecute();
        return valid ? ParserEditStatus.EDITABLE_STATUS
                : ParserEditStatus.UNEDITABLE_STATUS;
    }

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {
        return false;
    }

    public List<?> getSemanticElementsBeingParsed(EObject element) {
        List<EObject> lst = null;
        if (element instanceof Property) {

            Property prop = (Property) element;

            lst = new ArrayList<EObject>();
            lst.add(element);

            lst.add(prop.getUpperValue());
            lst.add(prop.getLowerValue());
        }

        return lst == null ? Collections.EMPTY_LIST : lst;
    }
}
