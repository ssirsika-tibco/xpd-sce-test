package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.xpdExtension.UserTaskScripts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;

public abstract class BaseUserTaskScriptSection extends
        BaseProcessScriptSection {

    public BaseUserTaskScriptSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    private TabbedPropertySheetPage sheetPage;

    @Override
    protected TabbedPropertySheetPage getPropertySheetPage() {
        return this.sheetPage;
    }

    protected void setPropertySheetPage(TabbedPropertySheetPage sheetPage) {
        this.sheetPage = sheetPage;
    }

    protected abstract EObject getScriptNotifierObject(
            UserTaskScripts userTaskScripts);

    @Override
    protected Command getSetScriptGrammarCommand(String grammar,
            AbstractScriptEditorSection editorSection) {
        Command cmd = null;

        Activity act = getActivity();

        EditingDomain ed = getEditingDomain();
        if (act != null && ed != null && editorSection != null) {
            cmd = editorSection.getSetScriptGrammarCommand(ed, act);
        }
        return cmd;
    }

    /**
     * Get xpdl2 Activity from the input
     * 
     * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
     */
    protected Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

}
