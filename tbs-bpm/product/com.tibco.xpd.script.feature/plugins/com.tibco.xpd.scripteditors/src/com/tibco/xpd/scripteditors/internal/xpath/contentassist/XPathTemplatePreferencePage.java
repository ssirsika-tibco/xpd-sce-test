/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.scripteditors.internal.xpath.contentassist;

import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;

import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;

/**
 * @author rsomayaj
 * 
 */
public class XPathTemplatePreferencePage extends TemplatePreferencePage
        implements IWorkbenchPreferencePage {

    public XPathTemplatePreferencePage() {
        setPreferenceStore(ScriptEditorsPlugin.getDefault()
                .getPreferenceStore());
        setTemplateStore(ScriptEditorsPlugin.getDefault()
                .getXPathTemplateStore());
        setContextTypeRegistry(ScriptEditorsPlugin.getDefault()
                .getXPathContextTypeRegistry());
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