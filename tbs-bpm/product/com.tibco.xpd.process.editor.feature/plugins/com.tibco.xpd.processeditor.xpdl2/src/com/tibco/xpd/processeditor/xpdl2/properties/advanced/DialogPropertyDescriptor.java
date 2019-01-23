/**
 * DialogPropertyDescriptor.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2008
 */
package com.tibco.xpd.processeditor.xpdl2.properties.advanced;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;

/**
 * DialogPropertyDescriptor
 * 
 */
public abstract class DialogPropertyDescriptor extends PropertyDescriptor {

    /**
     * XpdDialogCellEditor
     * <p>
     * Special dialog cell editor that includes a clear button and uses a
     * PropertyValueAndContext as expected value so that when finally created
     * the sub-class's openDialogBox implementation knows the context in which
     * the dialog is being opened.
     * <p>
     * When the clear button is pressed a doSetValue(null) is performed.
     */
    public abstract class XpdDialogCellEditor extends DialogCellEditor {
        private Button clearButton;

        private Control labelContents;

        private Control launchButton;

        /**
         * Internal class for laying out the dialog.
         */
        private class XpdDialogCellLayout extends Layout {
            public void layout(Composite editor, boolean force) {
                Rectangle bounds = editor.getClientArea();
                Point launchBtnSize =
                        launchButton.computeSize(SWT.DEFAULT,
                                SWT.DEFAULT,
                                force);
                Point clearBtnSize =
                        clearButton
                                .computeSize(SWT.DEFAULT, SWT.DEFAULT, force);

                if (labelContents != null) {
                    labelContents
                            .setBounds(0,
                                    0,
                                    bounds.width
                                            - (launchBtnSize.x + clearBtnSize.x),
                                    bounds.height);
                }

                launchButton.setBounds(bounds.width
                        - (launchBtnSize.x + clearBtnSize.x),
                        0,
                        launchBtnSize.x,
                        bounds.height);
                clearButton.setBounds(bounds.width - clearBtnSize.x,
                        0,
                        clearBtnSize.x,
                        bounds.height);
            }

            public Point computeSize(Composite editor, int wHint, int hHint,
                    boolean force) {
                if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
                    return new Point(wHint, hHint);
                }

                Point contentsSize =
                        labelContents.computeSize(SWT.DEFAULT,
                                SWT.DEFAULT,
                                force);
                Point buttonSize =
                        launchButton.computeSize(SWT.DEFAULT,
                                SWT.DEFAULT,
                                force);
                Point clearSize =
                        clearButton
                                .computeSize(SWT.DEFAULT, SWT.DEFAULT, force);

                // Just return the button width to ensure the button is not
                // clipped
                // if the label is long.
                // The label will just use whatever extra width there is
                Point result =
                        new Point((buttonSize.x + clearSize.x), Math
                                .max(contentsSize.y, buttonSize.y));
                return result;
            }
        }

        /**
         * @param parent
         */
        private XpdDialogCellEditor(Composite parent) {
            super(parent);
        }

        protected abstract boolean getClearEnabled();

        @Override
        protected Object openDialogBox(Control cellEditorWindow) {
            PropertyValueAndContext valueAndContext =
                    getPropertyValueAndContext(doGetValue());
            if (valueAndContext != null) {
                return DialogPropertyDescriptor.this
                        .openDialogBox(cellEditorWindow, valueAndContext);
            }

            return null;
        }

        @Override
        protected void updateContents(Object value) {
            PropertyValueAndContext valueAndContext =
                    getPropertyValueAndContext(doGetValue());

            super.updateContents(getLabelProvider().getText(valueAndContext));

        }

        @Override
        protected Control createControl(Composite parent) {
            Control editor = super.createControl(parent);
            if (getClearEnabled()) {
                if (editor instanceof Composite) {
                    Composite editorCmp = (Composite) editor;
                    // 
                    // Add our own clear button to the editor
                    // 
                    clearButton = createClearButton(editorCmp);

                    // 
                    // And set a different layout on editor.
                    //
                    Control[] children = editorCmp.getChildren();
                    int numChildren = children.length;

                    editorCmp.setLayout(new XpdDialogCellLayout());

                    labelContents = null;

                    for (int i = 0; i < children.length; i++) {
                        Control child = children[i];

                        if (!(child instanceof Button)) {
                            labelContents = child;
                        } else {
                            if (child != clearButton) {
                                launchButton = (Button) child;
                            }
                        }
                    }

                } else {
                    throw new RuntimeException(
                            "XpdDialogCellEditor: can't add clear button, jface returned unexpected cell editor type."); //$NON-NLS-1$
                }
            }

            return editor;
        }

        private Button createClearButton(Composite editorCmp) {
            Button btn = new Button(editorCmp, SWT.DOWN);
            btn.setText(Messages.DialogPropertyDescriptor_Clear_button);

            // Add an escape listener.
            btn.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    if (e.character == '\u001b') { // Escape
                        fireCancelEditor();
                    }
                }
            });

            btn.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent e) {
                    PropertyValueAndContext value =
                            getPropertyValueAndContext(getValue());
                    if (value != null) {
                        value =
                                new PropertyValueAndContext(value.getContext(),
                                        null);
                    }
                    markDirty();
                    doSetValue(value);
                    fireApplyEditorValue();
                }
            });

            return btn;
        }
    }

    /**
     * PropertyValueAndContext
     * <p>
     * Class containing the value of a given property AND the context in which
     * the property 'lives'.
     * </p>
     * <p>
     * Things like the advanced property tab get the 'current value' of a
     * property and pass that as the value on the property descriptor (and hence
     * onto the cell editor). For dialog cell editor's it is usually important
     * to have more that just the current value of the property, it is important
     * to have the context in which the value is set (i.e. the current input).
     * <p>
     * Therefore anything that will uses this property descriptor <b>MUST</b>
     * provide an object of this class as the 'value' of the property.
     * </p>
     * <p>
     * The owner of the DialogPropertyDescriptor must also be aware that when a
     * set value is performed, the value object will be a new
     * PropertyValueAndContext object.
     */
    public static class PropertyValueAndContext {
        Object context;

        Object value;

        public PropertyValueAndContext(Object context, Object value) {
            this.context = context;
            this.value = value;

        }

        public Object getContext() {
            return context;
        }

        public Object getValue() {
            return value;
        }
    }

    /** Main Property descriptor's 'clear button enabled flag. */
    private boolean clearEnabled = false;

    /**
     * Create a DialogPropertyDescriptor.
     * <p>
     * When using this class, the sub-class must ensure that when setting a
     * value for the property it is a {@link PropertyValueAndContext} object
     * that wraps the actual value of the property and a context in which the
     * property exists.
     * <p>
     * 
     * @param id
     * @param displayName
     * @param clearEnabled
     *            Whether to show a [Clear] button next to the normal [...]
     *            dialog-open button. On [Clear] a doSetValue() is performed
     *            with the value in {@link PropertyValueAndContext} set to null;
     */
    public DialogPropertyDescriptor(Object id, String displayName,
            boolean clearEnabled) {
        super(id, displayName);

        this.clearEnabled = clearEnabled;

        setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                if (element != null) {
                    PropertyValueAndContext valueAndContext =
                            getPropertyValueAndContext(element);

                    if (valueAndContext != null
                            && valueAndContext.getValue() != null) {
                        return valueAndContext.getValue().toString();
                    }
                }
                return ""; //$NON-NLS-1$

            }
        });
    }

    /**
     * The [...] button has been pressed, open the dialog.
     * 
     * @param cellEditorWindow
     * @param propertyValueAndContext
     * @return
     */
    protected abstract PropertyValueAndContext openDialogBox(
            Control cellEditorWindow,
            PropertyValueAndContext propertyValueAndContext);

    @Override
    public CellEditor createPropertyEditor(Composite parent) {
        return new XpdDialogCellEditor(parent) {
            @Override
            protected boolean getClearEnabled() {
                return clearEnabled;
            }
        };
    }

    /**
     * Get and ensure that the cell property value is a
     * {@link PropertyValueAndContext}.
     * 
     * @param value
     * @return The value as a {@link PropertyValueAndContext} or null if no
     *         value set (will throw exception if value is anything else).
     */
    private PropertyValueAndContext getPropertyValueAndContext(Object value) {
        if (value == null) {
            return null;
        }

        if (!(value instanceof PropertyValueAndContext)) {
            throw new RuntimeException(
                    "Property Contribution must provide DialogPropertyDescriptor.PropertyValueAndContext as value for DialogPropertyDescriptor."); //$NON-NLS-1$
        }
        return (PropertyValueAndContext) value;
    }

}
