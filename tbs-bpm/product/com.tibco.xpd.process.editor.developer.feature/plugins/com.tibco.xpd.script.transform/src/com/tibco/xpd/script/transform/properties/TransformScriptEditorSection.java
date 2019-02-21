/**
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.script.transform.properties;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.osgi.framework.Bundle;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.script.transform.Activator;
import com.tibco.xpd.script.transform.internal.Messages;
import com.tibco.xpd.script.transform.util.TransformUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.TransformScript;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author mtorres
 * 
 */
public class TransformScriptEditorSection extends AbstractScriptEditorSection {

    public TransformScriptEditorSection(EClass eClass) {
        super(eClass);
    }

    public TransformScriptEditorSection() {
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

        Hyperlink linkToTransformTab =
                toolkit
                        .createHyperlink(scriptComposite,
                                Messages.TransformEditorSection_GoToTransformMappings,
                                SWT.NONE,
                                "linkToTransformInstrumentationName");//$NON-NLS-1$
        linkToTransformTab.addHyperlinkListener(new IHyperlinkListener() {

            @Override
            public void linkActivated(HyperlinkEvent e) {
                Bundle b = Activator.getDefault().getBundle();
                String tabName =
                        Platform.getResourceString(b,
                                Activator.TRANSFORM_TAB_NAME);
                showTab(tabName);

            }

            @Override
            public void linkEntered(HyperlinkEvent e) {
                // Do nothing
            }

            @Override
            public void linkExited(HyperlinkEvent e) {
                // Do nothing
            }

        });
        return scriptComposite;
    }

    @Override
    public void doRefresh() {
        // Do nothing
    }

    @Override
    protected String getScriptGrammar() {
        return TransformUtil.TRANSFORM_SCRIPTGRAMMAR;
    }

    @Override
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject eObject) {
        String scriptContext = getScriptContext();
        Command toReturn = null;
        if (scriptContext.equals(ProcessScriptContextConstants.SCRIPT_TASK)) {
            toReturn =
                    getSetTransformScriptTaskGrammarCommand(editingDomain,
                            getScriptGrammar(),
                            eObject);
        }

        return toReturn;
    }

    private Command getSetTransformScriptTaskGrammarCommand(
            EditingDomain editingDomain, String scriptGrammar, EObject eObject) {
        Activity activity = (Activity) eObject;
        if (activity == null) {
            return null;
        }
        Implementation currentImpl = activity.getImplementation();
        if (!(currentImpl instanceof Task)) {
            return null;
        }
        Task task = (Task) currentImpl;
        TaskScript taskScript = task.getTaskScript();
        if (taskScript == null) {
            return null;
        }

        Expression newExpr = Xpdl2Factory.eINSTANCE.createExpression();
        newExpr.setScriptGrammar(scriptGrammar);

        TransformScript transformScript =
                XpdExtensionFactory.eINSTANCE.createTransformScript();

        newExpr.getMixed().add(XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_TransformScript(),
                transformScript);
        CompoundCommand cmd = new CompoundCommand();
        cmd.append(SetCommand.create(editingDomain,
                taskScript,
                Xpdl2Package.eINSTANCE.getTaskScript_Script(),
                newExpr));

        cmd
                .setLabel(Messages.TransformScriptEditorSection_SetScriptTaskGrammar);
        return cmd;
    }

    /**
     * @see org.eclipse.ui.IPluginContribution#getPluginId()
     * 
     * @return
     */
    public String getPluginId() {
        return Activator.PLUGIN_ID;
    }

    /**
     * Contribution local identifier.
     * 
     * @see org.eclipse.ui.IPluginContribution#getLocalId()
     */
    public String getLocalId() {
        return "developer.editor"; //$NON-NLS-1$
    }
}
