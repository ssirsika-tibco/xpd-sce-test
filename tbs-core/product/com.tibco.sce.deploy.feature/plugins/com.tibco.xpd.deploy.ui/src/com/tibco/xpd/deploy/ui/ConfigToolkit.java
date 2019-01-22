/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.ui;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import com.tibco.xpd.deploy.ui.components.FileControl;
import com.tibco.xpd.deploy.ui.components.StringControl;
import com.tibco.xpd.deploy.ui.components.TestableServerProvider;

/**
 * Toolkit used for deployment controls.
 * <p>
 * <i>Created: 2 Jul 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ConfigToolkit {

    /** Obtains shared instance of toolkit */
    public static final ConfigToolkit INSTANCE = new ConfigToolkit();

    /**
     * Private constructor. To obtain instance use {@link #INSTANCE}.
     */
    private ConfigToolkit() {
    }

    public Label createLabel(Composite parent, String text) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(text);
        return label;
    }

    public Combo createCombo(Composite parent) {
        Combo combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
        combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return combo;
    }

    public Text createText(Composite parent, String defaultText) {
        Text textFeld = new Text(parent, SWT.SINGLE | SWT.BORDER);
        textFeld.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textFeld.setText(defaultText);
        return textFeld;
    }

    public StringControl createStringControl(Composite parent,
            String defaultText, Map<String, String> options) {
        return createStringControl(parent, defaultText, options, null);
    }

    public StringControl createStringControl(Composite parent,
            String defaultText, Map<String, String> options,
            TestableServerProvider serverProvider) {
        StringControl stringControl =
                new StringControl(parent, defaultText, options, serverProvider);
        stringControl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        return stringControl;
    }

    public Button createCheckbox(Composite parent, boolean defaultValue) {
        Button button = new Button(parent, SWT.CHECK);
        button.setSelection(defaultValue);
        return button;
    }

    public Button createPushButton(Composite parent, String text) {
        Button button = new Button(parent, SWT.PUSH);
        button.setText(text);
        return button;
    }

    public Table createTable(Composite parent) {
        Table table =
                new Table(parent, SWT.FULL_SELECTION | SWT.SINGLE
                        | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        return table;
    }

    public Tree createTree(Composite parent) {
        Tree tree =
                new Tree(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL
                        | SWT.BORDER);
        return tree;
    }

    public Tree createCheckedTree(Composite parent) {
        Tree tree =
                new Tree(parent, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL
                        | SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
        return tree;
    }

    public Group createGroup(Composite parent, String defaultText) {
        Group group = new Group(parent, SWT.NULL);
        group.setText(defaultText);
        return group;
    }

    public Composite createComposite(Composite parent) {
        return new Composite(parent, SWT.NONE);
    }

    public FileControl createFileControl(Composite parent,
            Map<String, String> options) {
        return new FileControl(parent, options);
    }

}
