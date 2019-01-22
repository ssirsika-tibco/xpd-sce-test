/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tibco.xpd.rql.script.preferences.ui;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.sse.core.internal.ltk.parser.RegionParser;
import org.eclipse.wst.sse.ui.internal.preferences.ui.AbstractColorPage;
import org.eclipse.wst.sse.ui.internal.preferences.ui.StyledTextColorPicker;

import com.tibco.xpd.rql.script.RQLScriptPlugin;
import com.tibco.xpd.rql.script.styles.IStyleConstantsRQL;
import com.tibco.xpd.script.sourceviewer.internal.Messages;
import com.tibco.xpd.script.sourceviewer.internal.preferences.OverlayPreferenceStore;
import com.tibco.xpd.script.sourceviewer.internal.preferences.OverlayPreferenceStore.OverlayKey;

public class RQLColorPage extends AbstractColorPage {
    private OverlayPreferenceStore fOverlayStore;

    protected RegionParser fParser = new RQLTokenParser();

    /**
     * Set up all the style preference keys in the overlay store
     */
    protected OverlayKey[] createOverlayKeys() {
        ArrayList overlayKeys = new ArrayList();

        ArrayList styleList = new ArrayList();
        initStyleList(styleList);
        Iterator i = styleList.iterator();
        while (i.hasNext()) {
            overlayKeys.add(new OverlayPreferenceStore.OverlayKey(
                    OverlayPreferenceStore.STRING, (String) i.next()));
        }

        OverlayPreferenceStore.OverlayKey[] keys =
                new OverlayPreferenceStore.OverlayKey[overlayKeys.size()];
        overlayKeys.toArray(keys);
        return keys;
    }

    public String getSampleText() {
        return "resource( name = 'ResName' and type = 'TypeName').group( name = 'GroupName') union orgunit( name = 'OrgName') "; //$NON-NLS-1$
    }

    protected void initContextSytleMap(Dictionary contextStyleMap) {

        contextStyleMap.put(IStyleConstantsRQL.DEFAULT,
                IStyleConstantsRQL.DEFAULT);
        contextStyleMap.put(IStyleConstantsRQL.KEYWORDENTITIES,
                IStyleConstantsRQL.KEYWORDENTITIES);
        contextStyleMap.put(IStyleConstantsRQL.KEYWORDATTRIBUTES,
                IStyleConstantsRQL.KEYWORDATTRIBUTES);
        contextStyleMap.put(IStyleConstantsRQL.KEYWORDOPERATORS,
                IStyleConstantsRQL.KEYWORDOPERATORS);
    }

    protected void initDescriptions(Dictionary descriptions) {

        descriptions.put(IStyleConstantsRQL.DEFAULT, Messages.Default_Code_UI_);
        descriptions.put(IStyleConstantsRQL.KEYWORDENTITIES,
                Messages.Keywords_UI_Entities);
        descriptions.put(IStyleConstantsRQL.KEYWORDATTRIBUTES,
                Messages.Keywords_UI_Attributes);
        descriptions.put(IStyleConstantsRQL.KEYWORDOPERATORS,
                Messages.Keywords_UI_OperatorCombinator);

    }

    /**
     * Initializes this preference page for the given workbench.
     * <p>
     * This method is called automatically as the preference page is being
     * created and initialized. Clients must not call this method.
     * </p>
     * 
     * @param workbench
     *            the workbench
     */
    public void init(IWorkbench workbench) {
        fOverlayStore =
                new OverlayPreferenceStore(getPreferenceStore(),
                        createOverlayKeys());
        fOverlayStore.load();
        fOverlayStore.start();
    }

    protected void initStyleList(ArrayList list) {
        list.add(IStyleConstantsRQL.DEFAULT);
        list.add(IStyleConstantsRQL.KEYWORDENTITIES);
        list.add(IStyleConstantsRQL.KEYWORDATTRIBUTES);
        list.add(IStyleConstantsRQL.KEYWORDOPERATORS);
    }

    protected void setupPicker(StyledTextColorPicker picker) {

        picker.setParser(fParser);

        // create descriptions for hilighting types
        Dictionary descriptions = new Hashtable();
        initDescriptions(descriptions);

        // map region types to hilighting types
        Dictionary contextStyleMap = new Hashtable();
        initContextSytleMap(contextStyleMap);

        ArrayList styleList = new ArrayList();
        initStyleList(styleList);

        picker.setContextStyleMap(contextStyleMap);
        picker.setDescriptions(descriptions);
        picker.setStyleList(styleList);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent) {
        Control c = super.createContents(parent);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(c,
                IRQLHelpContextIds.RQL_PREFWEBX_STYLES_HELPID2);
        return c;
    }

    protected void performDefaults() {
        fOverlayStore.loadDefaults();
        fPicker.refresh();
    }

    protected IPreferenceStore doGetPreferenceStore() {
        return RQLScriptPlugin.getDefault().getPreferenceStore();
    }

    /**
     * Creates the StyledTextColorPicker used in createContents This method can
     * be overwritten to set up StyledTextColorPicker differently
     */
    protected void createContentsForPicker(Composite parent) {
        // create the color picker
        fPicker = new StyledTextColorPicker(parent, SWT.NULL);
        GridData data = new GridData(GridData.FILL_BOTH);
        fPicker.setLayoutData(data);

        fPicker.setPreferenceStore(fOverlayStore);
        setupPicker(fPicker);

        fPicker.setText(getSampleText());
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.IPreferencePage#performOk()
     */
    public boolean performOk() {
        fOverlayStore.propagate();
        savePreferences();
        return true;
    }

    /* (non-Javadoc)
     * @see org.eclipse.wst.sse.ui.preferences.ui.AbstractColorPage#savePreferences()
     */
    protected void savePreferences() {
        RQLScriptPlugin.getDefault().savePluginPreferences();
    }
}
