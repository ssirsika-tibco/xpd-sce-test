package com.tibco.xpd.processeditor.xpdl2.properties.seqflow;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.script.BaseProcessScriptSection;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.xpdl2.Condition;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;

public class SequenceFlowScriptSection extends BaseProcessScriptSection
        implements IPluginContribution {

    public SequenceFlowScriptSection() {
        super(Xpdl2Package.eINSTANCE.getTransition());
    }

    @Override
    public String getCurrentSetScriptGrammarId() {
        String grammarId = null;

        Expression expression = getTransitionConditionExpression();
        if (expression != null) {
            grammarId = expression.getScriptGrammar();
        }
        return grammarId;
    }

    private Transition getTransition() {
        return (Transition) getInput();

    }

    private Condition getTransitionCondition() {
        if (getTransition() != null) {
            return getTransition().getCondition();
        }
        return null;
    }

    private Expression getTransitionConditionExpression() {
        Condition cnd = getTransitionCondition();
        if (cnd != null) {
            return cnd.getExpression();
        }
        return null;
    }

    @Override
    public String getScriptContext() {
        return "SequenceFlow"; //$NON-NLS-1$
    }

    @Override
    protected Command getSetScriptGrammarCommand(String grammar,
            AbstractScriptEditorSection editorSection) {
        Command cmd = null;
        Transition transitionObject = getTransition();
        EditingDomain ed = getEditingDomain();
        if (transitionObject != null && ed != null && editorSection != null) {
            cmd =
                    editorSection.getSetScriptGrammarCommand(ed,
                            transitionObject);
        }
        return cmd;
    }

    public String getLocalId() {
        return "analyst.conditionalSequenceFlowSection"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

}
