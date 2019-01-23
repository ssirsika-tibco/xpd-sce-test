/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules.baserules;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.tibco.xpd.xpdl2.Activity;

/**
 * An implementation of {@link AbstractMappingRuleBase} for mappings that are
 * contained within {@link Activity}'s. This was the original base superclass
 * for mapping rules.
 * <p>
 * Most of the logic has moved to {@link AbstractMappingRuleBase} because Studio
 * v3.7 introduces some mapping requirements whereby mappings are not contained
 * within Activity. Therefore {@link AbstractMappingRuleBase} deals with
 * mappings contained within EObject as parent object and this class implements
 * an Activity variant of that (in order to continue to support the existing
 * class hierarchy as-is).
 * 
 * @author aallway
 * @since 3.3 (16 Jun 2010)
 */
public abstract class MappingRuleContentInfoProvider extends
        MappingRuleContentInfoProviderBase {

    /**
     * Construct a mapping rule info provider. The provider wraps a tree content
     * provider (for either the source or target side objects in a given mapping
     * situation.
     * <p>
     * In order that the mapping validations exactly agree with what the user
     * sees when viewing the mappings in the UI, the mapping content provider
     * should be exactly the same class as that used by the relevant mapping
     * section.
     * </p>
     * 
     * @param contentProvider
     *            The content provider for the tree of data representing this
     *            side of the mapping data
     */
    protected MappingRuleContentInfoProvider(
            ITreeContentProvider contentProvider) {
        super(contentProvider);
    }

    /**
     * An object that signifies the context in which this inbfor provider is
     * being used. This should be set prior to any use (or change of context
     * after forst use) of this class.
     * 
     * @param contextObject
     */
    public void setContextActivity(Activity contextActivity) {
        super.setContextObject(contextActivity);
    }

    /**
     * Get the context object that has been set on this info provider prior to
     * it's use in any given context (for instance activity mapping validation
     * rule) will set the context to activity.
     * 
     * @return the contextObject
     */
    protected Activity getContextActivity() {
        return (Activity) super.getContextObject();
    }

}
