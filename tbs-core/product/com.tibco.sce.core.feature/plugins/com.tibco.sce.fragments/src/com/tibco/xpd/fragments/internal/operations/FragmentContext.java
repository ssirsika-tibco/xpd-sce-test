/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments.internal.operations;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoContext;

import com.tibco.xpd.fragments.internal.Messages;
import com.tibco.xpd.fragments.internal.utils.FragmentsUtil;

/**
 * Undo context for the fragments view.
 * 
 * @author njpatel
 * 
 */
public class FragmentContext implements IUndoContext {

    private final String editorID;
    private final boolean canUndo;
    private final List<String> providerIds;

    /**
     * <code>FragmentContext</code> that represents all fragment contexts. Used
     * to flush the undo history. If this context is encountered in the
     * {@link #matches(IUndoContext) matches} method then it will always return
     * <code>true</code> indicating that any <code>FragmentContext</code> will
     * match <code>ALL</code>.
     */
    public static final FragmentContext ALL = new FragmentContext(null);

    /**
     * Undo context for the fragments view.
     * 
     * @param providerIds
     *            fragments contributor ids.
     */
    public FragmentContext(String[] providerIds) {
        this(providerIds, true);
    }

    /**
     * Undo context for the fragments view.
     * 
     * @param providerIds
     *            fragments contributor ids
     * @param canUndo
     *            set to <code>true</code> if the operation is undo-able,
     *            <code>false</code> otherwise.
     */
    public FragmentContext(String[] providerIds, boolean canUndo) {
        this.providerIds = providerIds != null ? Arrays.asList(providerIds)
                : null;
        editorID = FragmentsUtil.getActiveEditorID();
        this.canUndo = canUndo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.commands.operations.IUndoContext#getLabel()
     */
    public String getLabel() {
        return Messages.FragmentContext_label;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.commands.operations.IUndoContext#matches(org.eclipse
     * .core.commands.operations.IUndoContext)
     */
    public boolean matches(IUndoContext context) {

        if (context instanceof FragmentContext) {

            // If any of the contexts is ALL then return true
            if (this == ALL || context == ALL) {
                return true;
            }

            FragmentContext other = (FragmentContext) context;

            if (other.editorID != null) {
                return other.editorID.equals(editorID)
                        && matches(other.providerIds)
                        && other.canUndo == canUndo;
            }
        }

        return false;
    }

    /**
     * Match the provider ids. If the 'other' context has a provider id that is
     * contained in this context's provider id list then the contexts match.
     * 
     * @param otherProviderIds
     *            the provider ids list of the 'other' context being compared.
     * @return <code>true</code> if context match, <code>false</code> otherwise.
     */
    private boolean matches(List<String> otherProviderIds) {
        boolean ret = providerIds == null;

        if (!ret && otherProviderIds != null) {
            // Both have empty provider id list
            ret = providerIds.size() == 0 && otherProviderIds.size() == 0;

            if (!ret) {
                // Check that the other provider has an id in it's list that is
                // contained in this context's list

                for (String id : otherProviderIds) {
                    if (ret = providerIds.contains(id)) {
                        break;
                    }
                }
            }
        }

        return ret;
    }

}
