/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.Map;

import org.eclipse.jface.fieldassist.DecoratedField;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.TextControlCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

/**
 * @author nwilson
 */
public class JavaScriptField extends Composite implements VerifyListener {

    /** The text control. */
    private Text field;

    /** The decorated field. */
    private DecoratedField df;

    /** The field error decoration. */
    private FieldDecoration decoration;

    /** Flag for expression validity. */
    private boolean valid = true;

    /** Map of input field names to values. */
    private Map<String, Object> fields;

    /** Prefix to use when validating script. */
    private String prefix;

    /** The script transformer to use before verification. */
    private IScriptTransformer preVerifyScriptTransformer;

    /**
     * @param parent
     *            The parent composite.
     * @param style
     *            The style for the field.
     */
    public JavaScriptField(Composite parent, int style) {
        super(parent, style);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        setLayout(layout);
        TextControlCreator creator = new TextControlCreator();
        df = new DecoratedField(this, style, creator);
        df.setUseMaximumDecorationWidth(false);
        df.getLayoutControl().setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true));
        field = (Text) df.getControl();

        ImageCache cache = Activator.getDefault().getImageCache();
        Image image = cache.getImage(ImageCache.ERROR);
        decoration = new FieldDecoration(image, ""); //$NON-NLS-1$
        df.addFieldDecoration(decoration, SWT.RIGHT | SWT.BOTTOM, false);

        field.addVerifyListener(this);
    }

    /**
     * @param fields
     *            The fields to use for validation.
     */
    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    /**
     * @param e
     *            The verification event.
     * @see org.eclipse.swt.events.VerifyListener#verifyText(
     *      org.eclipse.swt.events.VerifyEvent)
     */
    public void verifyText(VerifyEvent e) {
        String text = field.getText(0, e.start - 1)
                + e.text
                + field
                        .getText(e.end, field.getCharCount()
                                - (e.end - e.start));
        verify(text);

    }

    /**
     * @param text
     *            The text to verify.
     */
    private void verify(String text) {
        String message;
        ScriptErrorReporter reporter = new ScriptErrorReporter();
        try {
            Context context = Context.enter();
            context.setErrorReporter(reporter);
            String scriptText = prefix == null ? text : prefix + text;
            if (preVerifyScriptTransformer != null) {
                scriptText = preVerifyScriptTransformer.transform(scriptText);
            }
            System.out.println(scriptText);
            Script script = context.compileString(scriptText, "", 0, null); //$NON-NLS-1$
            ScriptableObject scope = context.initStandardObjects();
            if (fields != null) {
                for (String field : fields.keySet()) {
                    scope.put(field, scope, fields.get(field));
                }
            }
            script.exec(context, scope);
            if (reporter.hasErrors()) {
                message = reporter.getErrorMessage();
            } else {
                message = ""; //$NON-NLS-1$
                valid = true;
            }
        } catch (Exception ex) {
            message = ex.getMessage();
            valid = false;
        }
        setError(valid, message);
    }

    /**
     * @param valid
     *            true if valid.
     * @param message
     *            The error message.
     */
    private void setError(boolean valid, String message) {
        decoration.setDescription(message);
        if (valid) {
            df.hideDecoration(decoration);
        } else {
            df.showDecoration(decoration);
        }
    }

    /**
     * @param text
     *            Sets the text.
     */
    public void setText(String text) {
        String script;
        if (text != null) {
            script = text.substring(0, text.lastIndexOf(';'));
        } else {
            script = ""; //$NON-NLS-1$
        }
        field.setText(script);
        verify(script);
    }

    /**
     * @return The script text with trailing semicolon.
     */
    public String getText() {
        return field.getText() + ";"; //$NON-NLS-1$
    }

    /**
     * @return true if the script is valid.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param prefix
     *            The script prefix to use for validation.
     */
    public void setScriptPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @param preVerifyScriptTransformer
     *            The pre-verification script transformer.
     */
    public void setPreVerifyScriptTransformer(
            IScriptTransformer preVerifyScriptTransformer) {
        this.preVerifyScriptTransformer = preVerifyScriptTransformer;
    }
}
