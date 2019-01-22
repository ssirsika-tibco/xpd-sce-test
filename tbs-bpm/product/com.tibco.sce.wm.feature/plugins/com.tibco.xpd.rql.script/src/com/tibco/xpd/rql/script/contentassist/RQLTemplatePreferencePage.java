/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.script.contentassist;

import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

import com.tibco.xpd.rql.script.RQLScriptPlugin;

/**
 * RQL Template Preference Page
 * 
 * @author rsomayaj
 * 
 */
public class RQLTemplatePreferencePage extends TemplatePreferencePage implements
        IWorkbenchPreferencePage {

    public RQLTemplatePreferencePage() {
        setPreferenceStore(RQLScriptPlugin.getDefault().getPreferenceStore());
        setTemplateStore(RQLScriptPlugin.getDefault().getRQLTemplateStore());
        setContextTypeRegistry(RQLScriptPlugin.getDefault()
                .getContextTypeRegistry());
    }

    protected boolean isShowFormatterSetting() {
        return false;
    }

    public boolean performOk() {
        boolean ok = super.performOk();
        RQLScriptPlugin.getDefault().savePluginPreferences();
        return ok;
    }
}