/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.unspecified;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.script.ui.internal.IScriptDetailsProvider;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Editor Section for "Unspecified" grammar scripts.
 * 
 * @author rsomayaj
 * 
 */
public class UnspecifiedScriptEditorSection extends AbstractScriptEditorSection {

    public UnspecifiedScriptEditorSection(EClass eClass) {
        super(eClass);
    }

    public UnspecifiedScriptEditorSection() {
        super(null);
    }

    @Override
    public Command doGetCommand(Object obj) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    public Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite scriptComposite = toolkit.createComposite(parent);

        scriptComposite.setLayout(new GridLayout());
        toolkit.paintBordersFor(scriptComposite);

        return scriptComposite;
    }

    @Override
    public void doRefresh() {
        // Do nothing
    }

    private static final String UNDEFINED_SCRIPTGRAMMAR = "Unspecified"; //$NON-NLS-1$

    @Override
    protected String getScriptGrammar() {
        return UNDEFINED_SCRIPTGRAMMAR;
    }

    /**
     * @see com.tibco.xpd.script.ui.api.AbstractScriptEditorSection#getSetScriptGrammarCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject)
     * 
     * @param editingDomain
     * @param object
     * @return
     */
    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject object) {
        IScriptDetailsProvider scriptDetailsProvider =
                getScriptDetailsProvider();

        if (scriptDetailsProvider != null) {
            return scriptDetailsProvider
                    .getSetScriptGrammarCommand(editingDomain,
                            object,
                            getScriptGrammar());
        }
        return null;
    }
}
