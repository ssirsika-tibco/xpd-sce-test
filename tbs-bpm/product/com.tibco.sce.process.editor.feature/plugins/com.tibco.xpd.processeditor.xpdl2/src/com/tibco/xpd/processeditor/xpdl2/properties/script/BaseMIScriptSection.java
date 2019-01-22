/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * 
 * <p>
 * <i>Created: 4 Dec 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public abstract class BaseMIScriptSection extends BaseProcessScriptSection {

    public BaseMIScriptSection() {
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
