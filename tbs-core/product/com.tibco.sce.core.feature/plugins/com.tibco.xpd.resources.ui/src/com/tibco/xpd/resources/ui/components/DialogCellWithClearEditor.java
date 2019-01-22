/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.components;

import java.text.MessageFormat;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;

import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * An abstract cell editor that uses a dialog. This dialog cell editor has a
 * label control on the left, a browse button and a clear button. Pressing the
 * browse button opens a dialog window (for example, a color dialog or a file
 * dialog) to change the cell editor's value. The cell editor's value is the
 * value of the dialog. The clear button will clear the value.
 * <p>
 * Subclasses may override the following methods:
 * <ul>
 * <li><code>createContents</code>: creates the cell editor's 'display value'
 * control</li>
 * <li><code>updateContents</code>: updates the cell editor's 'display value'
 * control after its value has changed</li>
 * <li><code>openDialogBox</code>: opens the dialog box when the end user
 * presses the button</li>
 * <li><code>getClearValue</code>: clear the value, by default the default
 * implementation returns <code>null</code>, subclasses may override if a
 * difference 'clear' (default) value needs to be set.
 * </ul>
 * </p>
 */
public abstract class DialogCellWithClearEditor extends CellEditor {

    /**
     * The editor control.
     */
    private Composite editor;

    /**
     * The current contents.
     */
    private Control contents;

    /**
     * The label that gets reused by <code>updateLabel</code>.
     */
    private Label defaultLabel;

    /**
     * The browse button.
     */
    private Button browseBtn;

    /**
     * The clear button.
     */
    private Button clearBtn;

    /**
     * Listens for 'focusLost' events and fires the 'apply' event as long as the
     * focus wasn't lost because the dialog was opened.
     */
    private FocusListener buttonFocusListener;

    /**
     * The value of this cell editor; initially <code>null</code>.
     */
    private Object value = null;

    private SelectionListener selectionListener;

    /**
     * Default DialogCellEditor style
     */
    private static final int defaultStyle = SWT.NONE;

    /** Set to <code>true</code> if the Clear button should be hidden */
    private boolean hideClear;

    /**
     * Internal class for laying out the dialog.
     */
    private class DialogCellLayout extends Layout {
        private static final int SPACING = 5;

        public void layout(Composite editor, boolean force) {
            Rectangle bounds = editor.getClientArea();
            Point browseBtnSize =
                    browseBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
            Point clearBtnSize =
                    !hideClear ? clearBtn.computeSize(SWT.DEFAULT,
                            SWT.DEFAULT,
                            force) : new Point(0, 0);
            if (contents != null) {
                contents.setBounds(0, 0, bounds.width - browseBtnSize.x
                        - clearBtnSize.x - SPACING, bounds.height);
            }
            browseBtn.setBounds(bounds.width
                    - (browseBtnSize.x + SPACING + clearBtnSize.x),
                    0,
                    browseBtnSize.x,
                    bounds.height);
            if (!hideClear) {
                clearBtn.setBounds(bounds.width - clearBtnSize.x - SPACING,
                        0,
                        clearBtnSize.x,
                        bounds.height);
            }
        }

        public Point computeSize(Composite editor, int wHint, int hHint,
                boolean force) {
            if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
                return new Point(wHint, hHint);
            }
            Point contentsSize =
                    contents.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
            Point browseBtnSize =
                    browseBtn.computeSize(SWT.DEFAULT, SWT.DEFAULT, force);
            Point clearBtnSize =
                    !hideClear ? clearBtn.computeSize(SWT.DEFAULT,
                            SWT.DEFAULT,
                            force) : new Point(0, 0);
            // Just return the button width to ensure the button is not clipped
            // if the label is long.
            // The label will just use whatever extra width there is
            Point result =
                    new Point(browseBtnSize.x + SPACING + clearBtnSize.x, Math
                            .max(contentsSize.y, browseBtnSize.y));
            return result;
        }
    }

    /**
     * Creates a new dialog cell editor with no control
     */
    public DialogCellWithClearEditor() {
        setStyle(defaultStyle);
    }

    /**
     * Creates a new dialog cell editor parented under the given control. The
     * cell editor value is <code>null</code> initially, and has no validator.
     * 
     * @param parent
     *            the parent control
     */
    public DialogCellWithClearEditor(Composite parent) {
        this(parent, defaultStyle);
    }

    /**
     * Creates a new dialog cell editor parented under the given control. The
     * cell editor value is <code>null</code> initially, and has no validator.
     * 
     * @param parent
     *            the parent control
     * @param style
     *            the style bits
     */
    public DialogCellWithClearEditor(Composite parent, int style) {
        super(parent, style);
    }

    /**
     * Creates a new dialog cell editor parented under the given control. The
     * cell editor value is <code>null</code> initially, and has no validator.
     * 
     * @param parent
     *            the parent control
     * @param style
     *            the style bits
     * @param showClear
     *            <code>true</code> if the Clear button should be shown and
     *            <code>false</code> if the behaviour of
     *            {@link DialogCellEditor} is required.
     */
    public DialogCellWithClearEditor(Composite parent, int style,
            boolean showClear) {
        setStyle(style);
        this.hideClear = !showClear;
        create(parent);
    }

    /**
     * Creates the button for this cell editor under the given parent control.
     * <p>
     * The default implementation of this framework method creates the button
     * display on the right hand side of the dialog cell editor. Subclasses may
     * extend or reimplement.
     * </p>
     * 
     * @param parent
     *            the parent control
     * @param label
     *            the button label
     * @return the new button control
     */
    private Button createButton(Composite parent, String label) {
        Button result = new Button(parent, SWT.DOWN);
        result.setText(label);
        return result;
    }

    /**
     * Creates the controls used to show the value of this cell editor.
     * <p>
     * The default implementation of this framework method creates a label
     * widget, using the same font and background color as the parent control.
     * </p>
     * <p>
     * Subclasses may reimplement. If you reimplement this method, you should
     * also reimplement <code>updateContents</code>.
     * </p>
     * 
     * @param cell
     *            the control for this cell editor
     * @return the underlying control
     */
    protected Control createContents(Composite cell) {
        defaultLabel = new Label(cell, SWT.LEFT);
        defaultLabel.setFont(cell.getFont());
        defaultLabel.setBackground(cell.getBackground());
        return defaultLabel;
    }

    /*
     * (non-Javadoc) Method declared on CellEditor.
     */
    protected Control createControl(Composite parent) {

        Font font = parent.getFont();
        Color bg = parent.getBackground();

        editor = new Composite(parent, getStyle());
        editor.setFont(font);
        editor.setBackground(bg);
        editor.setLayout(new DialogCellLayout());

        contents = createContents(editor);
        updateContents(value);

        browseBtn = createButton(editor, "..."); //$NON-NLS-1$
        browseBtn.setFont(font);

        browseBtn.addKeyListener(new KeyAdapter() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.swt.events.KeyListener#keyReleased(org.eclipse.swt
             * .events.KeyEvent)
             */
            public void keyReleased(KeyEvent e) {
                if (e.character == '\u001b') { // Escape
                    fireCancelEditor();
                }
            }
        });

        browseBtn.addFocusListener(getButtonFocusListener());

        browseBtn.addSelectionListener(getSelectionListener());

        if (!hideClear) {
            clearBtn =
                    createButton(editor,
                            Messages.DialogCellWithClearEditor_clear_button);
            clearBtn.setToolTipText(getClearButtonToolTip());
            clearBtn.setFont(font);
            clearBtn.addSelectionListener(getSelectionListener());
        }

        setValueValid(true);

        return editor;
    }

    /**
     * Get the clear button's tooltip.
     * 
     * @return
     */
    protected String getClearButtonToolTip() {
        return Messages.DialogCellWithClearEditor_clear_button_tooltip;
    }

    @Override
    protected void focusLost() {
        // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * Override in order to remove the button's focus listener if the celleditor
     * is deactivating.
     * 
     * @see org.eclipse.jface.viewers.CellEditor#deactivate()
     */
    public void deactivate() {
        if (browseBtn != null && !browseBtn.isDisposed()) {
            browseBtn.removeFocusListener(getButtonFocusListener());
        }

        super.deactivate();
    }

    /*
     * (non-Javadoc) Method declared on CellEditor.
     */
    protected Object doGetValue() {
        return value;
    }

    /*
     * (non-Javadoc) Method declared on CellEditor. The focus is set to the cell
     * editor's button.
     */
    protected void doSetFocus() {
        browseBtn.setFocus();

        // add a FocusListener to the button
        browseBtn.addFocusListener(getButtonFocusListener());
    }

    /**
     * Return a listener for button focus.
     * 
     * @return FocusListener
     */
    private FocusListener getButtonFocusListener() {
        if (buttonFocusListener == null) {
            buttonFocusListener = new FocusListener() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * org.eclipse.swt.events.FocusListener#focusGained(org.eclipse
                 * .swt.events.FocusEvent)
                 */
                public void focusGained(FocusEvent e) {
                    // Do nothing
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * org.eclipse.swt.events.FocusListener#focusLost(org.eclipse
                 * .swt.events.FocusEvent)
                 */
                public void focusLost(FocusEvent e) {
                    DialogCellWithClearEditor.this.focusLost();
                }
            };
        }

        return buttonFocusListener;
    }

    /**
     * Get the selection listener.
     * 
     * @return
     */
    private SelectionListener getSelectionListener() {
        if (selectionListener == null) {
            selectionListener = new SelectionAdapter() {
                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * org.eclipse.swt.events.SelectionListener#widgetSelected(org
                 * .eclipse .swt.events.SelectionEvent)
                 */
                public void widgetSelected(SelectionEvent event) {
                    // Remove the button's focus listener since it's guaranteed
                    // to lose focus when the dialog opens
                    if (event.widget == browseBtn) {
                        browseBtn.removeFocusListener(getButtonFocusListener());

                        Object newValue = openDialogBox(editor);

                        // Re-add the listener once the dialog closes
                        browseBtn.addFocusListener(getButtonFocusListener());

                        if (newValue != null) {
                            boolean newValidState = isCorrect(newValue);
                            if (newValidState) {
                                markDirty();
                                doSetValue(newValue);
                            } else {
                                // try to insert the current value into the
                                // error message.
                                setErrorMessage(MessageFormat
                                        .format(getErrorMessage(),
                                                new Object[] { newValue
                                                        .toString() }));
                            }
                            fireApplyEditorValue();
                        }
                    } else if (!hideClear && event.widget == clearBtn) {
                        doClearValue();
                    }
                }
            };
        }
        return selectionListener;
    }

    /**
     * Clear the value of this cell.
     */
    private void doClearValue() {
        Object clearValue = getClearValue();
        markDirty();
        doSetValue(clearValue);
        fireApplyEditorValue();
    }

    /*
     * (non-Javadoc) Method declared on CellEditor.
     */
    protected void doSetValue(Object value) {
        this.value = value;
        updateContents(value);
    }

    /**
     * Returns the default label widget created by <code>createContents</code>.
     * 
     * @return the default label widget
     */
    protected Label getDefaultLabel() {
        return defaultLabel;
    }

    /**
     * Opens a dialog box under the given parent control and returns the
     * dialog's value when it closes, or <code>null</code> if the dialog was
     * canceled or no selection was made in the dialog.
     * <p>
     * This framework method must be implemented by concrete subclasses. It is
     * called when the user has pressed the button and the dialog box must pop
     * up.
     * </p>
     * 
     * @param cellEditorWindow
     *            the parent control cell editor's window so that a subclass can
     *            adjust the dialog box accordingly
     * @return the selected value, or <code>null</code> if the dialog was
     *         canceled or no selection was made in the dialog
     */
    protected abstract Object openDialogBox(Control cellEditorWindow);

    /**
     * Clear the value in this cell. Default implementation returns
     * <code>null</code>, subclasses may override if they wish to set a
     * different value on clear.
     * 
     * @return clear value, <code>null</code> returned by default.
     */
    protected Object getClearValue() {
        return null;
    }

    /**
     * Updates the controls showing the value of this cell editor.
     * <p>
     * The default implementation of this framework method just converts the
     * passed object to a string using <code>toString</code> and sets this as
     * the text of the label widget.
     * </p>
     * <p>
     * Subclasses may reimplement. If you reimplement this method, you should
     * also reimplement <code>createContents</code>.
     * </p>
     * 
     * @param value
     *            the new value of this cell editor
     */
    protected void updateContents(Object value) {
        if (defaultLabel == null) {
            return;
        }

        String text = "";//$NON-NLS-1$
        if (value != null) {
            text = value.toString();
        }
        defaultLabel.setText(text);
    }
}
