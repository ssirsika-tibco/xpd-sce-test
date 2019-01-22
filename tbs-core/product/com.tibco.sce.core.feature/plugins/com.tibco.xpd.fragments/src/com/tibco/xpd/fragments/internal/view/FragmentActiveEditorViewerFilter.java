/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.view;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.ui.IEditorPart;

import com.tibco.xpd.fragments.internal.FragmentsManager;
import com.tibco.xpd.fragments.internal.FragmentsExtensionHelper.FragmentsProvider;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Fragments viewer filter for the active editor. This will filter for all
 * providers bound to the currently active editor.
 * 
 * @author njpatel
 * 
 */
public class FragmentActiveEditorViewerFilter extends FragmentRootViewerFilter {

    /**
     * Fragments viewer filter for the active editor.
     */
    public FragmentActiveEditorViewerFilter() {
        super(null);
    }

    @Override
    protected Set<String> getProviderIds() {
        Set<String> providerIds = new HashSet<String>();
        IEditorPart editorPart = FragmentsUtil.getActiveEditor();

        if (editorPart != null) {
            FragmentsProvider[] providers = FragmentsManager.getInstance()
                    .getExtensionHelper().getBoundProviders(editorPart);

            if (providers != null) {
                for (FragmentsProvider provider : providers) {
                    providerIds.add(provider.getId());
                }
            }
        }

        return providerIds;
    }

}
