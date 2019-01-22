/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.javascript.contentassist;

import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;

/** Javascript script preference page
 * @author rsomayaj
 *
 */
public class JSTemplatePreferencePage extends TemplatePreferencePage implements
        IWorkbenchPreferencePage {

    public JSTemplatePreferencePage() {
        setPreferenceStore(ScriptEditorsPlugin.getDefault()
                .getPreferenceStore());
        setTemplateStore(ScriptEditorsPlugin.getDefault()
                .getJavaScriptTemplateStore());
        setContextTypeRegistry(ScriptEditorsPlugin.getDefault()
                .getJSContextTypeRegistry());
    }

    protected boolean isShowFormatterSetting() {
        return false;
    }

    public boolean performOk() {
        boolean ok = super.performOk();
        ScriptEditorsPlugin.getDefault().savePluginPreferences();
        return ok;
    }
}