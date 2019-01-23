/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.text;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Script Editor section for Plain Text Grammar
 * 
 * @author rsomayaj
 * 
 */
public class PlainTextScriptEditor extends AbstractScriptEditorSection {

    public PlainTextScriptEditor(EClass eClass) {
        super(eClass);
    }

    public PlainTextScriptEditor() {
        super(null);
    }

    private Text conditionText;

    public static final String PLAIN_TEXT_GRAMMAR_ID = "Text"; //$NON-NLS-1$

    private Label scriptDescLabel = null;

    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite condition = toolkit.createComposite(parent);
        condition.setData("condition"); //$NON-NLS-1$		
        condition.setLayout(new GridLayout());
        scriptDescLabel = toolkit.createLabel(condition, ""); //$NON-NLS-1$
        scriptDescLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        conditionText =
                toolkit.createText(condition,
                        "", SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.WRAP); //$NON-NLS-1$
        conditionText.setData("name", "textCondition"); //$NON-NLS-1$ //$NON-NLS-2$
        GridData data = new GridData(GridData.FILL_BOTH);
        data.minimumHeight = 10;
        data.minimumWidth = 10;
        data.heightHint = 25;
        conditionText.setLayoutData(data);
        manageControl(conditionText);
        toolkit.paintBordersFor(condition);
        return condition;
    }

    @Override
    public Command doGetCommand(Object obj) {
        String scriptText = conditionText.getText();

        Command cmd =
                getScriptDetailsProvider()
                        .getSetScriptCommand(getEditingDomain(),
                                getInput(),
                                scriptText,
                                getScriptGrammar());

        return cmd;
    }

    @Override
    public void doRefresh() {
        String scriptDesc = getScriptDescLabel();
        String strScript = null;

        if (getInput() != null) {
            strScript = getScriptDetailsProvider().getScript(getInput());
        }

        scriptDescLabel.setText(scriptDesc);
        updateText(conditionText, strScript);
    }

    @Override
    protected String getScriptGrammar() {
        return PLAIN_TEXT_GRAMMAR_ID;
    }

    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject eObject) {
        /*
         * Sid SIA-1: Used to be some code here that used classes from internal
         * package in scripts feature that appeared to be unnecsessary - so just
         * called super instead as it seemed to do the same thing anyhow.
         */
        return super.getSetScriptGrammarCommand(editingDomain, eObject);
    }

    /**
     * Sid XPD-7575 - Set offset margin as we have nested sections that waste a
     * lot of space.
     * 
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getSectionMarginOffset()
     * 
     * @return
     */
    @Override
    public Point getSectionMarginOffset() {
        return new Point(0, -10);

    }

}
