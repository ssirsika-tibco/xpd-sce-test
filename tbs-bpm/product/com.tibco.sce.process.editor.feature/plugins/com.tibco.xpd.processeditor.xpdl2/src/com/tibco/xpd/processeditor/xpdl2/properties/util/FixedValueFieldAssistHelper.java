/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.IControlCreator;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.keys.IBindingService;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * FixedValueFieldAssistHelper
 * <p>
 * Helper class to create a content assisted text control via a given form
 * toolkit.
 * </p>
 * <p>
 * This is designed for cases where content assist is required but the value
 * MUST be one of those values in the proposals list. The list of proposals is
 * provided by the caller as an array of any objects. The object's toString()
 * method is used for the proposal text.
 * </p>
 * <p>
 * You can switch off the fixed predefined value only behaviour using
 * setFixedValueOnly() - this causes behaviour to revert to the standard content
 * assist + the other non-fixed value related behaviours listed below (like
 * click on lightbulb).
 * </p>
 * <p>
 * On construction, this class adds a DecoratedField to the given parent and
 * sets this up as a text control input. This class then handles various
 * standard features that are not provided inherently by content assist...
 * <li>Any character typed will cause the proposal list to pop-up (as well as
 * the standard <Ctrl+Space>)</li>
 * <li>This class informs the caller of changes to the value represented by the
 * text control when the text control looses focus OR a specific proposal is
 * chosen by the user.</li>
 * <li>The caller's change listener is passed the original object passed as a
 * proposal.</li>
 * <li>Therefore the caller is guaranteed to be passed a valid proposal value in
 * all circumstances.
 * <li>The list of proposals is sorted and filtered by this class.</li>
 * <li>The entire current contents of the text control is used to filter the
 * proposal list</li>
 * <li></li>
 * </p>
 * 
 * @author aallway
 */
public class FixedValueFieldAssistHelper implements IContentProposalListener {

    public static final String IS_FIXEDVALUE_CONTENTASSIST_FIELD =
            "IS_FIXEDVALUE_CONTENTASSIST_FIELD"; //$NON-NLS-1$

    public static final String FIXEDVALUE_CONTENTASSIST_FIELD_HELPER =
            "FIXEDVALUE_CONTENTASSIST_FIELD_HELPER"; //$NON-NLS-1$

    // whether fixed pre-defined value behaviour is on or not.
    private boolean isFixedFieldBehaviour = true;

    /**
     * OpenableContentProposalAdapter
     * 
     */
    private final class OpenableContentProposalAdapter extends
            ContentProposalAdapter {

        /**
         * @param control
         * @param adapter
         * @param provider
         * @param stroke
         * @param characters
         */
        private OpenableContentProposalAdapter(Control control,
                IControlContentAdapter adapter,
                IContentProposalProvider provider, KeyStroke stroke,
                char[] characters) {
            super(control, adapter, provider, stroke, characters);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.fieldassist.ContentProposalAdapter#openProposalPopup
         * ()
         */
        @Override
        public void openProposalPopup() {
            // Override simply to make open popup visible.
            super.openProposalPopup();
        }

        @Override
        public void closeProposalPopup() {
            // There is no close popup but setEnabled(false) closes it.
            setEnabled(false);
            setEnabled(true); // set enebaled does not re-open it so there you
            // go!
        }

    }

    /**
     * FixedValueProposalProvider Interface to provide content assist proposals
     * for this class. The objects returned can be of any type, their toString()
     * method is used for content assist label and filtering.
     */
    public interface FixedValueFieldProposalProvider {
        /**
         * Return list of proposal objects. Each object's toString() method is
         * used to provide proposal label and proposal filtering.
         * 
         * @return
         */
        Object[] getProposals();
    }

    /**
     * FixedValueProposalProvider Interface to provide content assist proposals
     * for this class. The objects returned can be of any type, their toString()
     * method is used for content assist label and filtering.
     */
    public interface PositionalFieldProposalProvider {
        /**
         * Return list of proposal objects. Each object's toString() method is
         * used to provide proposal label and proposal filtering.
         * 
         * @return
         */
        Object[] getProposals(String contents, int position);
    }

    /**
     * FixedValueFieldChangedListener Listener for change of value in fixed
     * value content assisted text control.
     */
    public interface FixedValueFieldChangedListener {
        /**
         * Called when the value represented by this fixed value content
         * assisted field changes either by direct selection of a proposal or by
         * the control loosing focus (user exits field).
         * 
         * @param newValue
         *            Guaranteed to be one of the proposal objects provided by
         *            the FixedValueProposalProvider given on construction.
         *            NOTE: THIS MAY BE NULL - if no proposals start with any
         *            text typed by user.
         */
        void fixedValueFieldChanged(Object newValue);
    }

    /**
     * FixedValueTextControlCreator Text control creator for DecoratedField.
     */
    private class FixedValueTextControlCreator implements IControlCreator {
        XpdFormToolkit formToolkit;

        public FixedValueTextControlCreator(XpdFormToolkit formToolkit) {
            this.formToolkit = formToolkit;
        }

        @Override
        public Control createControl(Composite parent, int style) {
            // Looks weird. Control IS only single line BUT there's a bug in
            // Win32 that when EM_SETSEL with Selection End Char BEFORE
            // selection Start (which is what we need to do for filling in the
            // end of the auto complete) it doesn't do what it's meant to do
            // (i.e. it is MEANT to place the caret at the END pos EVEN WHEN the
            // end pos is before the start) - It doesn't on windows (not caused
            // by SWT this is a WINDOWS problem).
            //
            // Easy fix for this is to set the SWT.MULTI style on the edit box -
            // multi-line edit boxes do not have same bug as single line.
            // User will never e able to type in a new-line anyhow because (a)
            // it is grabbed by the auto when it's open and (b) we verify
            // againsty it to.
            FixedValueFieldAssistHelper.this.parent = parent;

            Text txt = formToolkit.createText(parent, "", (style | SWT.MULTI) //$NON-NLS-1$
                    & ~SWT.SINGLE);

            // Add property that ContentAssist command handler
            // FixedValFieldContentAssistCommand handler can check to ensure
            // that this is a content assist field.
            txt.setData(IS_FIXEDVALUE_CONTENTASSIST_FIELD, new Boolean(true));

            return txt;
        }
    }

    /**
     * FixedValueContentProposal Class representing an individual proposal for
     * given content assist list.
     * 
     * Wraps the caller's proposal object and uses toString() to provide text
     * content.
     */
    private class FixedValueFieldContentProposal implements IContentProposal,
            Comparable {
        private Object proposalObject;

        /**
         * @return the proposalObject
         */
        public Object getProposalObject() {
            return proposalObject;
        }

        /**
		 * 
		 */
        public FixedValueFieldContentProposal(Object proposalObject) {
            this.proposalObject = proposalObject;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.fieldassist.IContentProposal#getContent()
         */
        @Override
        public String getContent() {
            return proposalObject.toString();
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(Object o) {
            FixedValueFieldContentProposal cmp2 =
                    (FixedValueFieldContentProposal) o;
            int cmp = getContent().compareToIgnoreCase(cmp2.getContent());

            // Normally map will trhow out duplicates (where compareTo returns
            // 0) we don't want ignore any.
            if (cmp == 0) {
                cmp = this.hashCode() - cmp2.hashCode();
            }

            return cmp;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.fieldassist.IContentProposal#getCursorPosition()
         */
        @Override
        public int getCursorPosition() {
            return getContent().length();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.fieldassist.IContentProposal#getDescription()
         */
        @Override
        public String getDescription() {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.fieldassist.IContentProposal#getLabel()
         */
        @Override
        public String getLabel() {
            return null;
        }

    }

    /**
     * InternalFixValProposalProvider Our content assist proposal provider that
     * wraps, sorts and filters caller's proposal objects.
     * 
     */
    private class InternalFixValProposalProvider implements
            IContentProposalProvider {
        /**
         * The caller's proposal provider.
         */
        private PositionalFieldProposalProvider proposalProvider;

        /**
         * Constructs this wrapper of proposal objects.
         */
        public InternalFixValProposalProvider(
                PositionalFieldProposalProvider proposalProvider) {
            this.proposalProvider = proposalProvider;
        }

        @Override
        public IContentProposal[] getProposals(String contents, int position) {
            return getProposals(contents, position, true);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.fieldassist.IContentProposalProvider#getProposals
         * (java.lang.String, int)
         */
        public IContentProposal[] getProposals(String contents, int position,
                boolean backwardsMatch) {

            Object[] proposalObjects =
                    proposalProvider.getProposals(contents, position);

            if (proposalObjects != null && proposalObjects.length > 0) {

                TreeSet<FixedValueFieldContentProposal> sortedFilteredProposals =
                        new TreeSet<FixedValueFieldContentProposal>();

                // If we are not backwards matching, only return proposals if we
                // have a beginning string to look for (i.e. don't return ALL
                // when no value to search for.
                if (backwardsMatch || position > 0) {
                    // Special proposal filter that checks for proposals that
                    // are longer than the current content BUT if there are
                    // none, re-checks for any that are same as contents MINUS
                    // one character and so on until no proposals match at which
                    // time everything will match.
                    int contentCheckLength = position;

                    do {
                        for (int i = 0; i < proposalObjects.length; i++) {

                            String proposalName = proposalObjects[i].toString();
                            if (proposalName.length() >= contentCheckLength
                                    && proposalName
                                            .substring(0, contentCheckLength)
                                            .equalsIgnoreCase(contents
                                                    .substring(0,
                                                            contentCheckLength))) {

                                sortedFilteredProposals
                                        .add(new FixedValueFieldContentProposal(
                                                proposalObjects[i]));

                            }
                        }

                        if (!backwardsMatch) {
                            break;
                        }
                    } while (sortedFilteredProposals.size() == 0
                            && (--contentCheckLength) >= 0);
                }

                //
                // If there is an exact match for the current content then make
                // sure it's entry appear at the top of the list. when the
                // proposal popup appears, the first item is always selected and
                // if that's not the same as current content then pressing
                // return wiithout actually changing anything will replace with
                // top proposal!
                FixedValueFieldContentProposal exactProposal = null;
                for (Iterator iter = sortedFilteredProposals.iterator(); iter
                        .hasNext();) {
                    FixedValueFieldContentProposal proposal =
                            (FixedValueFieldContentProposal) iter.next();

                    if (proposal.getContent().equalsIgnoreCase(contents)) {
                        // Remember it, and remove it.
                        exactProposal = proposal;
                        iter.remove();
                        break;
                    }
                }

                //
                // Finally create the list of proposals required by proposal
                // adapter interface.
                IContentProposal[] contentProposals = null;
                int numProposals = sortedFilteredProposals.size();
                if (exactProposal != null) {
                    numProposals++;

                }
                contentProposals = new IContentProposal[numProposals];

                int i = 0;

                // If we have exact proposal then add that at the top.
                if (exactProposal != null) {
                    contentProposals[i] = exactProposal;
                    i++;
                }

                for (Iterator iter = sortedFilteredProposals.iterator(); iter
                        .hasNext();) {
                    contentProposals[i++] = (IContentProposal) iter.next();
                }

                return contentProposals;
            }

            return new IContentProposal[0];
        }

    }

    private class IgnoreableTextContentAdapter extends TextContentAdapter {
        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.fieldassist.TextContentAdapter#setControlContents
         * (org.eclipse.swt.widgets.Control, java.lang.String, int)
         */
        @Override
        public void setControlContents(Control control, String text,
                int cursorPosition) {
            //
            // Make sure we switch of the usual select-closest from given
            // position behaviour.
            ignoreModify = true;
            super.setControlContents(control, text, cursorPosition);
            ignoreModify = false;
        }
    }

    /**
     * The decorated field containing the content assisted text control.
     */
    private DecoratedField decoratedField;

    /**
     * Original parent of text control
     */
    Composite parent;

    /**
     * List of content changed listeners.
     */
    private ArrayList<FixedValueFieldChangedListener> valueChangeListeners;

    /**
     * Content proposal provider wrapper for text control in decorated field.
     */
    private InternalFixValProposalProvider internalFixValProposalProvider;

    /**
     * Content assist proposal adapter.
     */
    private OpenableContentProposalAdapter proposalAdapter;

    /**
     * Set to temporarily ignore modifications...
     */
    boolean ignoreModify = false;

    boolean alreadyUpdatingFromChange = false;

    public FixedValueFieldAssistHelper(XpdFormToolkit formToolKit,
            Composite parent,
            final FixedValueFieldProposalProvider proposalProvider,
            boolean isFixedFieldBehaviour) {
        this(formToolKit, parent, new PositionalFieldProposalProvider() {

            @Override
            public Object[] getProposals(String contents, int position) {
                return proposalProvider.getProposals();
            }

        }, isFixedFieldBehaviour);
    }

    /**
     * Add a fixed-value content assisted text control to the given parent
     * composite (via the given FormToolkit).
     */
    public FixedValueFieldAssistHelper(XpdFormToolkit formToolKit,
            Composite parent, PositionalFieldProposalProvider proposalProvider,
            boolean isFixedFieldBehaviour) {

        this.valueChangeListeners =
                new ArrayList<FixedValueFieldChangedListener>();
        this.isFixedFieldBehaviour = isFixedFieldBehaviour;

        //
        // Add the decorated field to parent control.
        decoratedField =
                new DecoratedField(parent, SWT.NONE,
                        new FixedValueTextControlCreator(formToolKit));
        decoratedField.getLayoutControl().setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL));

        Text txt = (Text) decoratedField.getControl();

        // OK - this is gross... but expedient :o)
        // By default DecoratedField covers the layout composite with the text
        // control so when form tool kit trys to draw borders in the parent
        // control it does so above the top of the parent and below the bottom
        // of the parent because there is no margin between child and parent. So
        // we'll overwrite the std decorated field form data with our own that
        // has a border (for safety's sake we'll only do it if decorated field
        // is still using FormData layoutr - in case it changes in future).
        Object ldata = txt.getLayoutData();
        if (ldata instanceof FormData) {
            FormData data = new FormData();
            data.left = new FormAttachment(0, 2);
            data.top = new FormAttachment(0, 2);
            data.right = new FormAttachment(100, -2);
            data.bottom = new FormAttachment(100, -2);
            txt.setLayoutData(data);
        }

        txt.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
        formToolKit.paintBordersFor((Composite) decoratedField
                .getLayoutControl());

        FieldDecoration requiredFieldIndicator =
                FieldDecorationRegistry
                        .getDefault()
                        .getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL);

        /*
         * XPD-5842: use preference value for content assist key rather than
         * hard coded 'Ctrl+Space'
         */
        IBindingService bindingService =
                (IBindingService) PlatformUI.getWorkbench()
                        .getService(IBindingService.class);
        TriggerSequence triggerSequence =
                bindingService
                        .getBestActiveBindingFor(IWorkbenchCommandConstants.EDIT_CONTENT_ASSIST);
        String keyString;
        if (triggerSequence != null) {
            keyString = triggerSequence.toString();
        } else {
            keyString = "Ctrl+Space"; //$NON-NLS-1$
        }

        requiredFieldIndicator
                .setDescription(String
                        .format(Messages.FixedValueFieldAssistHelper_AssistAvailable_tooltip2,
                                keyString));

        decoratedField.addFieldDecoration(requiredFieldIndicator, SWT.TOP
                | SWT.LEFT, false);

        //
        // Set up the content assist provider...
        // Activate on (almost) any character typed
        try {

            KeyStroke keyStroke = KeyStroke.getInstance(keyString); //$NON-NLS-1$

            // Create our internal proposal provider that wraps the caller's
            // object provider.
            internalFixValProposalProvider =
                    new InternalFixValProposalProvider(proposalProvider);

            Text textControl = (Text) decoratedField.getControl();

            textControl.setData(FIXEDVALUE_CONTENTASSIST_FIELD_HELPER, this);

            // assume that myTextControl has already been created in some way
            proposalAdapter =
                    new OpenableContentProposalAdapter(textControl,
                            new IgnoreableTextContentAdapter(),
                            internalFixValProposalProvider, keyStroke, null);

            // Always replace entire contents on selection of proposal by user
            proposalAdapter
                    .setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);

            // We always handle filtering.
            proposalAdapter.setFilterStyle(ContentProposalAdapter.FILTER_NONE);

            // Listen for proposal selection by user
            proposalAdapter.addContentProposalListener(this);

            setupFocusListener(textControl);

            setupKeyListener(textControl);

            setupKeyVerifyListener(textControl);

            setUpTextModifyListener(textControl);

            setupClickPopupListener();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Add 'click content assist light-buld icon to popup proposals'.
     */
    private void setupClickPopupListener() {
        Control[] children =
                ((Composite) decoratedField.getLayoutControl()).getChildren();

        for (int i = 0; i < children.length; i++) {
            if (children[i] instanceof Label) {
                Label decoration = (Label) children[i];
                decoration.setData("name", "lightbulb"); //$NON-NLS-1$ //$NON-NLS-2$

                decoration.addMouseListener(new MouseAdapter() {
                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * org.eclipse.swt.events.MouseAdapter#mouseUp(org.eclipse
                     * .swt.events.MouseEvent)
                     */
                    @Override
                    public void mouseUp(MouseEvent e) {
                        super.mouseUp(e);
                        if (decoratedField.getControl().isEnabled()) {
                            proposalAdapter.openProposalPopup();
                            decoratedField.getControl().forceFocus();
                        }
                    }

                });
                break;
            }
        }
    }

    /**
     * Open the proposal popup.
     * 
     * @return
     */
    public void openProposalPopup() {
        if (proposalAdapter != null) {
            proposalAdapter.openProposalPopup();
        }
    }

    /**
     * When user modifies text, update copntrol to reflect closest proposal.
     * 
     * @param textControl
     */
    private void setUpTextModifyListener(Text textControl) {
        textControl.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                updateTextFromChange();
            }
        });
    }

    /**
     * Add keystroke verify listener for various factilities...
     * 
     * @param textControl
     */
    private void setupKeyVerifyListener(Text textControl) {
        textControl.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                // We have to use a multi line edit control rather than a
                // single because this is the only way to fix a Win32 bug
                // with EM_SETSEL (doesn't allow reverse selection on single
                // line edit controls).
                // So we fake single-line by disallowing newline here.
                if (e.keyCode == SWT.CR) {
                    e.doit = false;
                } else if (e.keyCode == SWT.TAB) {
                    // Because we are forced to use MULTI line text control,
                    // it ignores tabs
                    // (requires Ctrl+tab) so we'll traves to next control
                    // ourselves!
                    e.doit = false;

                    ((Text) e.widget).clearSelection();
                    ((Text) e.widget).traverse(SWT.TRAVERSE_TAB_NEXT);

                } else if (e.keyCode == SWT.BS) {
                    if (isFixedFieldBehaviour) {
                        // We keep the text that user hasn't typed yet in
                        // control and selected at end of what has been typed.
                        // Nominally back-space deletes the current selection
                        // only. We need it to do that and one more char
                        // backwards to work sensibly.
                        Text text = (Text) e.widget;

                        Point sel = text.getSelection();
                        if (sel.x != sel.y && sel.y >= 0) {
                            sel.x--;
                            text.setSelection(sel);
                        }
                    }
                }
            }
        });
    }

    /**
     * Add key-stroke listener to text control to do auto-popup of proposals
     * when key pressed
     * 
     * @param textControl
     */
    private void setupKeyListener(Text textControl) {
        textControl.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // The code below used to handle Ctrl+Space. This stopped
                // working at some point in eclipse 3.2 because it had a
                // Ky-bouind command for content assist. This meant thatr we no
                // longer received Ctrl+Space (trapped by command key-binding
                // before it got here.
                // This has been fixed by adding a proper content assist command
                // handler for ourt content assisted fields.
                //
                // So we don't need to check explicitly, but I want to leave
                // code here until we're totally sure that command handler
                // always works.
                if (true) {
                    return;
                }

                if (!isFixedFieldBehaviour) {
                    // If not fixed field behaviour then only display popup list
                    // when ctrl+space pressed.
                    if ((e.stateMask & SWT.CTRL) != 0) {

                        if ((e.stateMask & SWT.ALT) == 0) {
                            if (e.keyCode == ' ') {
                                System.out
                                        .println("CTRL+SPACE via key listener"); //$NON-NLS-1$
                                proposalAdapter.openProposalPopup();
                            }
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                if ((e.stateMask & SWT.CTRL) == 0
                        && (e.stateMask & SWT.ALT) == 0) {
                    switch (e.keyCode) {
                    case SWT.TAB:
                    case SWT.CR:
                    case SWT.ESC:
                    case SWT.CTRL:
                    case SWT.ALT:
                    case SWT.SHIFT:
                        break;

                    case SWT.ARROW_LEFT:
                    case SWT.ARROW_RIGHT:
                    case SWT.HOME:
                    case SWT.END:
                        // Update selection - basically keep end-part
                        // selection in-synch with cursor position
                        updateTextFromChange();
                    default: {
                        // Don't auto popup content assist list unless fixed val
                        // behaviour is on.
                        if (isFixedFieldBehaviour) {
                            proposalAdapter.openProposalPopup();
                        }
                        break;
                    }
                    }
                }
            }
        });
    }

    /**
     * Set up a focus listener on text control. Then if user has paritally typed
     * a proposal we can select the first that matches and inform change
     * listeners. This means that the caller is ALWAYS informed of change and
     * the new value is always one of the proposals.
     * 
     * @param textControl
     */
    private void setupFocusListener(Text textControl) {
        //
        textControl.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {

                // TODO - Need to do something different for non-fixed value
                // behaviour
                // I think that on loose focus we have to switch on whether the
                // popup list is currently displayed.
                // If it isn't displayed then we take current value, otherwise
                // we take the selected value from popup.

                IContentProposal selProposal = null;

                if (isFixedFieldBehaviour) {
                    // Get the list of proposals available for the current value
                    // of
                    // text field and fire value change for first proposal that
                    // matches.

                    String content = ((Text) e.widget).getText();

                    if (content.length() > 0) {
                        IContentProposal[] proposals =
                                internalFixValProposalProvider
                                        .getProposals(content, content.length());

                        if (proposals.length > 0) {
                            selProposal = proposals[0];
                        }
                    }
                }
                proposalAccepted(selProposal);

            }
        });
    }

    /**
     * Returns the decorated field added by the constructor.
     * 
     * @return the decoratedField
     */
    public DecoratedField getDecoratedField() {
        return decoratedField;
    }

    /**
     * Add a value change listener to this content assisted field (value change
     * is only notified when proposal accepted or text control looses focus (NOT
     * for each character typed in text control)).
     * 
     * @param listener
     */
    public void addValueChangedListener(FixedValueFieldChangedListener listener) {
        if (!valueChangeListeners.contains(listener)) {
            valueChangeListeners.add(listener);
        }
    }

    /**
     * Remove given value change listener.
     * 
     * @param listener
     */
    public void removeValueChangedListener(
            FixedValueFieldChangedListener listener) {
        valueChangeListeners.remove(listener);
    }

    /**
     * Notify all listeners that the value has changed.
     * 
     */
    private void fireValueChanged(Object newValue) {
        for (FixedValueFieldChangedListener listener : valueChangeListeners) {
            listener.fixedValueFieldChanged(newValue);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.fieldassist.IContentProposalListener#proposalAccepted
     * (org.eclipse.jface.fieldassist.IContentProposal)
     */
    @Override
    public void proposalAccepted(IContentProposal proposal) {
        Object newValue = null;
        if (proposal != null) {
            FixedValueFieldContentProposal valueProposal =
                    (FixedValueFieldContentProposal) proposal;

            /**
             * When proposal selected by user inform the listeners of a change
             * (into the wrapped proposal object).
             */
            newValue = valueProposal.getProposalObject();
        }

        ignoreModify = true;

        // If we are not fixed value editing and there is no proposal that
        // matches current edit text then fire change with text in edit box.
        if (newValue == null && !isFixedFieldBehaviour) {
            Text text = ((Text) decoratedField.getControl());
            newValue = text.getText();
        }

        // System.out.println("Proposal accepted: "+newValue);
        fireValueChanged(newValue);
        ignoreModify = false;
    }

    /**
     * When the user types something, re-evaluate against proposals and setup
     * text control text and selection appropriately.
     */
    private void updateTextFromChange() {
        if (!ignoreModify && !alreadyUpdatingFromChange
                && decoratedField.getControl().isFocusControl()) {
            alreadyUpdatingFromChange = true;

            try {

                Text text = ((Text) decoratedField.getControl());
                int pos = text.getCaretPosition();

                String val = text.getText();

                if (val.length() > 0) {
                    boolean haveExactMatch = false;
                    IContentProposal[] proposals =
                            internalFixValProposalProvider.getProposals(text
                                    .getText(), val.length());

                    if (proposals != null) {
                        for (int i = 0; i < proposals.length; i++) {
                            if (proposals[i].getContent().equalsIgnoreCase(val)) {
                                haveExactMatch = true;
                                break;
                            }
                        }
                    }

                    if (!haveExactMatch) {
                        //
                        // Don't have exact match so see what matches up to the
                        // current cursor position.
                        if (isFixedFieldBehaviour) {
                            proposals =
                                    internalFixValProposalProvider
                                            .getProposals(text.getText(), pos);
                        } else {
                            proposals =
                                    internalFixValProposalProvider
                                            .getProposals(text.getText(),
                                                    pos,
                                                    false);
                        }

                        if (isFixedFieldBehaviour
                                && (proposals == null || proposals.length < 1
                                        && text.getText().length() > 0)) {
                            // text up to cursor does not match anything, if
                            // it's fixed value selection only then wipe it
                            // out.
                            text.setText(""); //$NON-NLS-1$
                            text.setSelection(0);

                        } else if (!isFixedFieldBehaviour
                                && (proposals == null || proposals.length < 1)) {
                            // No valid value matching, close the popup
                            proposalAdapter.closeProposalPopup();
                        } else {

                            if (isFixedFieldBehaviour) {
                                // Don't add proposed content unless doing fixed
                                // value behaviour.
                                FixedValueFieldContentProposal proposal =
                                        (FixedValueFieldContentProposal) proposals[0];

                                if (!text.getText()
                                        .equals(proposal.getContent())) {
                                    // Set the control to first selection that
                                    // matches and set the selection to the
                                    // portion after current content that does
                                    // match.
                                    String content = proposals[0].getContent();

                                    int matchLen;
                                    for (matchLen = 0; matchLen <= val.length()
                                            && matchLen <= content.length(); matchLen++) {
                                        if (!content
                                                .substring(0, matchLen)
                                                .equalsIgnoreCase(val
                                                        .substring(0, matchLen))) {
                                            break;
                                        }
                                    }

                                    // text.setText(proposals[0].getContent()
                                    // .substring(0, matchLen - 1));
                                    // text.setSelection(pos);
                                    text.setText(content);
                                    text.setSelection(content.length(),
                                            Math.min(matchLen - 1, pos));

                                }
                            }
                        }
                    } else if (isFixedFieldBehaviour) {
                        // Current content exactly matches a proposal so just
                        // set selection
                        // from caret pos to end.
                        text.setSelection(val.length(), pos);
                    }
                }
            } catch (Exception e) {
            }

            alreadyUpdatingFromChange = false;

        }
    }

    /**
     * Set whether 'select from fixed pre-defined list of values' behaviour is
     * on or off.
     * 
     * @return boolean
     */
    public boolean isFixedFieldBehaviour() {
        return isFixedFieldBehaviour;
    }

}
